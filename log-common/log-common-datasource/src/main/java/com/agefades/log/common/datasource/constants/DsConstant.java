package com.agefades.log.common.datasource.constants;

/**
 * 数据源相关常量
 *
 * @author DuChao
 * @date 2021/12/6 10:09 上午
 */
public interface DsConstant {

    /**
     * 默认排序字段
     */
    String DEFAULT_SORT_FIELD = "create_time";

    /**
     * 逻辑删除字段
     */
    String LOGIC_DELETE_FIELD = "deleted";

    /**
     * 操作日志 MyBatis Mapper 扫描包
     */
    String BASE_MAPPER_PACKAGE = "com.agefades.log.**.mapper";

}
