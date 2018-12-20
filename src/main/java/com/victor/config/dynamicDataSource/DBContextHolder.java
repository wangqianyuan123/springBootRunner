package com.victor.config.dynamicDataSource;



/**
 * 管控数据源
 */
public class DBContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 切换数据源
     *
     * @param dataSource
     */
    public static void setDataSource(String dataSource) {
        if (DataSourceUtil.dataSourceMap.containsKey(dataSource)) {
            contextHolder.set(dataSource);
        } else {
            throw new RuntimeException("数据源:" + dataSource + "不存在");
        }
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 删除数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }


}
