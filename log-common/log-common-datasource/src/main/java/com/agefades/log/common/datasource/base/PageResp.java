package com.agefades.log.common.datasource.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * MyBatis Plus 分页包装类
 *
 * @author DuChao
 * @date 2021/1/12 4:39 下午
 */
@Data
@NoArgsConstructor
public class PageResp<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("每页记录数")
    private long size;

    @ApiModelProperty("总页数")
    private long pages;

    @ApiModelProperty("当前页数")
    private long current;

    @ApiModelProperty("列表数据")
    private List<T> records;

    public PageResp(IPage<T> page) {
        this.records = page.getRecords();
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }

}
