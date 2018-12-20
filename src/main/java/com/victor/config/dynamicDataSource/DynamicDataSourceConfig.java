package com.victor.config.dynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DynamicDataSourceConfig {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceConfig.class);

    @Value("${spring.datasource.master.masterMapperLocations}")
    private String masterMapperLocations;
    @Value("${spring.datasource.master.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.master.url}")
    private String dbUrl;
    @Value("${spring.datasource.master.username}")
    private String username;
    @Value("${spring.datasource.master.password}")
    private String password;


  /*  @Bean(destroyMethod = "close", initMethod = "init", name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource masterDataSource() {
        logger.info("创建masterDataSource");
        //DruidDataSource druidDataSource = new DruidDataSource();
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        return druidDataSource;
    }*/

    /**
     * @return
     * @Bean 防止数据监控报错，无法查看数据源
     * @ConfigurationProperties 会把配置文件的参数自动赋值到dataSource里。
     * @Primary 用于标识默认使用的 DataSource Bean
     */
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
/*        logger.info("创建masterDataSource");
        //DruidDataSource druidDataSource = new DruidDataSource();
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        return druidDataSource;*/
        logger.info("创建masterDataSource");
        DruidDataSource dataSource = new DruidDataSource();
        try {
            System.out.println(dbUrl);
            dataSource.setUrl(this.dbUrl);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setFilters("stat,wall,log4j");

        } catch (SQLException e) {
            logger.error("druid configuration initialization filter: " + e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("druid configuration initialization filter: " + e);
        }
        return dataSource;
    }


    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        MyDynamicDataSource myDynamicDataSource = new MyDynamicDataSource();
        // 配置多数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        // targetDataSources.put("slave", slaveDataSource());
        myDynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源，在动态添加数据源的时候，就可以不再添加此数据源了
        // myDynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        // dynamicDataSource.setDefaultTargetDataSource(slaveDataSource());
        return myDynamicDataSource;
    }

    /**
     * 配置 SqlSessionFactoryBean
     */
    @Bean(value = "dynamicSqlSessionFactory")
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * 注入 DataSourceTransactionManager 用于事务管理
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    /**
     * SqlSessionFactory配置
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(
            @Qualifier("masterDataSource") DataSource dataSource
    ) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 配置mapper文件位置
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(masterMapperLocations));
        //配置分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //设置插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置事物管理器
     *
     * @return
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(
            @Qualifier("masterDataSource") DataSource dataSource
    ) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
