package com.agefades.log.common.core.util.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 *
 * @author DuChao
 * @date 2021/5/18 3:16 下午
 */
@Data
public class TreeNode {

    protected Object id;

    protected Object parentId;

    protected Integer weight;

    protected List<TreeNode> children;

    public void add(TreeNode node){
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

}
