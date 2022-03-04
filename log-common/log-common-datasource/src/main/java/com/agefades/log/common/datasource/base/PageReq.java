package com.agefades.log.common.datasource.base;

import com.agefades.log.common.datasource.constants.DsConstant;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页请求Vo
 *
 * @author DuChao
 * @date 2020/10/20 5:36 下午
 */
@Data
public class PageReq<T> {

    @ApiModelProperty("当前页")
    private int curPage;

    @ApiModelProperty("当前页条数")
    private int size;

    public Page<T> page() {
        return page(true);
    }

    public Page<T> page(boolean orderByCreateTime) {
        if (curPage <= 0 || size <= 0) {
            curPage = 1;
            size = 10;
        }

        Page<T> page = new Page<>(curPage, size);
        if (orderByCreateTime) {
            page.addOrder(OrderItem.desc(DsConstant.DEFAULT_SORT_FIELD));
        }
        return page;
    }

}
