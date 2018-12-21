package com.victor.config.dynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.victor.model.dataSourceConfig.DataSourceInfo;
import com.victor.utils.SpringContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置数据源
 */
@Component
public class DataSourceUtil {

    private static Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);

    @Value("${api.application.systemCode}")
    private String systemCode;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisTemplate<String, Object> redisTemplateStatic;

    private static String systemCodeStatic;

    @PostConstruct
    public void init() {
        redisTemplateStatic = redisTemplate;
        systemCodeStatic = systemCode;
    }

    public static final Map<Object, Object> dataSourceMap = new HashMap<>();

    /**
     * 初始化数据源
     */
    public static void initDataSource() {
        // 获取主数据源
        DruidDataSource masterDataSource = (DruidDataSource) SpringContextUtil.getBean("masterDataSource");
        addDataSource("master", masterDataSource);
        // 初始化其它数据源
        initOthersDataSource();
        // 刷新数据源
        flushDataSource();
        logger.info("数据源初始化成功！");
    }

    /**
     * 刷新数据源
     */
    public static void flushDataSource() {
        // 获取spring管理的dynamicDataSource
        MyDynamicDataSource myDynamicDataSource = (MyDynamicDataSource) SpringContextUtil.getBean("dynamicDataSource");
        // 将数据源设置到 targetDataSources
        myDynamicDataSource.setTargetDataSources(dataSourceMap);
        // 将 targetDataSources 中的连接信息放入 resolvedDataSources 管理
        myDynamicDataSource.afterPropertiesSet();
    }

    /**
     * 添加数据源
     *
     * @param key
     * @param masterDataSource
     */
    public static void addDataSource(String key, DruidDataSource masterDataSource) {
        dataSourceMap.put(key, masterDataSource);
    }

    /**
     * 初始化其他数据源
     */
    private static void initOthersDataSource() {
        //在此处可以查询出所有的数据源（例如，配置文件，数据库）然后添加
        ValueOperations<String, Object> opsForValue = redisTemplateStatic.opsForValue();
        Object objectRelation = opsForValue.get(systemCodeStatic + "_DB");
        List<DataSourceInfo> dataSourceInfos = null;
        dataSourceInfos = JSONObject.parseArray(JSONObject.toJSONString(objectRelation), DataSourceInfo.class);
        if (dataSourceInfos != null && dataSourceInfos.size() > 0) {
            for (DataSourceInfo dataSourceInfo : dataSourceInfos) {
                try{
                    String key = dataSourceInfo.getDbCode();
                    DruidDataSource druidDataSource = new DruidDataSource();
                    druidDataSource.setUsername(dataSourceInfo.getDbUserName());
                    String psw = java.net.URLDecoder.decode(dataSourceInfo.getDbPassWord(), "utf-8") ;
                    druidDataSource.setPassword(psw);
                    if ("oralce".equals(dataSourceInfo.getDbType())) {
                        druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
                    } else if ("mysql".equals(dataSourceInfo.getDbType())) {
                        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
                    }
                    druidDataSource.setUrl(dataSourceInfo.getDbUrl());
                    druidDataSource.setInitialSize(5);
                    druidDataSource.setMaxActive(dataSourceInfo.getDbMaxProcess());
                    druidDataSource.setMinIdle(dataSourceInfo.getDbMinProcess());
                    //添加数据源到map
//                    addDataSource(key, druidDataSource);
                    logger.info(dataSourceInfo.getDbCode() + " 动态数据源加载成功");
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(dataSourceInfo.getDbCode() + " 动态数据源加载失败");
                }
            }
        }
        logger.info("动态数据源加载完毕");
    }

}
