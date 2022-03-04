package com.agefades.log.common.core.util;

import cn.hutool.core.collection.CollUtil;
import com.agefades.log.common.core.util.dto.TreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 建树工具类
 *
 * @author DuChao
 * @date 2021/5/18 3:24 下午
 */
public class TreeUtil {

    /**
     * 程序查找根节点集合,递归建树,默认没有权重属性,不进行排序
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> treeNodes) {
        return buildTree(treeNodes, null, false);
    }

    /**
     * 指定根节点递归建树,默认没有权重属性,不进行排序
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> treeNodes, Object rootId) {
        return buildTree(treeNodes, Collections.singletonList(rootId), false);
    }

    /**
     * 递归建树
     *
     * @param treeNodes 树节点集合
     * @param rootIds   根节点Id集合
     * @param <T>       节点类型
     * @param hasWeight 是否有权重属性
     * @return 树结构数据
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> treeNodes, Collection<Object> rootIds, boolean hasWeight) {
        // 判空
        if (CollUtil.isEmpty(treeNodes)) {
            return null;
        }

        if (CollUtil.isEmpty(rootIds)) {
            // 没有指定根节点时，需自行查找根节点集合
            Map<Object, Object> map = treeNodes.stream().map(TreeNode::getId).collect(Collectors.toMap(v -> v, k -> k));
            Set<Object> noSubParentId = new HashSet<>();
            for (T treeNode : treeNodes) {
                if (!map.containsKey(treeNode.getParentId())) {
                    noSubParentId.add(treeNode.getParentId());
                }
            }

            // 没有子节点的节点，即一级节点
            rootIds = noSubParentId;
        }

        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (rootIds.contains(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes, hasWeight));
            }
        }
        if (hasWeight) {
            trees.sort(Comparator.comparing(TreeNode::getWeight));
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes, boolean hasWeight) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes, hasWeight));
            }
        }
        if (hasWeight) {
            if (treeNode.getChildren() != null) {
                treeNode.getChildren().sort(Comparator.comparing(TreeNode::getWeight));
            }
        }
        return treeNode;
    }

}
