package com.victor.config.dynamicDataSource;

import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * 参考https://blog.csdn.net/cs373616511/article/details/82827635
 */
public class MyDynamicDataSource extends AbstractRoutingDataSource {

    private static Logger logger = LoggerFactory.getLogger(MyDynamicDataSource.class);

    @Override
    public Object determineCurrentLookupKey() {
        // 获取当前线程的数据源，如果不存在使用master数据源
        String datasource = DBContextHolder.getDataSource();
        if (StringUtils.isNullOrEmpty(datasource)) {
            datasource = "master";
        }
        logger.info("datasource = " + datasource);
        return datasource;
    }

}
