package com.victor.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.easipass.api.util.Secret;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * cluster节点数据源配置
 * dataSource = 8
 */
@Configuration
@MapperScan(basePackages = {MBCluster7DataSourceConfig.PACKAGE},
        sqlSessionFactoryRef = "cluster7SqlSessionFactory")
public class MBCluster7DataSourceConfig {

	private static Logger log = LoggerFactory.getLogger(MBCluster7DataSourceConfig.class);
	
	static final String PACKAGE = "com.easipass.api.dao.clusterSeven";
	  
    @Value("${spring.datasource.cluster7.clusterMapperLocations}")
    private String clusterMapperLocations;

	@Value("${spring.datasource.cluster7.driverClassName}")
	private String driverClassName;
    @Value("${spring.datasource.cluster7.url}")
	private String dbUrl;
	@Value("${spring.datasource.cluster7.username}")
	private String username;
	@Value("${spring.datasource.cluster7.password}")
	private String password;
	
	@Value("${spring.datasource.druid.initialSize}")
	private int initialSize;
	@Value("${spring.datasource.druid.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.druid.maxActive}")
	private int maxActive;
	@Value("${spring.datasource.druid.maxWait}")
	private int maxWait;
	@Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${spring.datasource.druid.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.druid.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.druid.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.druid.testOnReturn}")
	private boolean testOnReturn;
	@Value("${spring.datasource.druid.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("{spring.datasource.druidconnectionProperties}")
	private String connectionProperties;
//  @ConfigurationProperties(prefix = "spring.datasource.cluster")
    @Bean(name = "cluster7DataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
		try {
			dataSource.setUrl(this.dbUrl);
			dataSource.setUsername(username);
			String psw = java.net.URLDecoder.decode(Secret.decrypt(password, "EASIPASS"), "utf-8");
			dataSource.setPassword(psw);
			dataSource.setDriverClassName(driverClassName);
			dataSource.setFilters("stat,wall,log4j");
			dataSource.setUseGlobalDataSourceStat(true);
			dataSource.setInitialSize(initialSize);
			dataSource.setMinIdle(minIdle);
			dataSource.setMaxActive(maxActive);
			dataSource.setMaxWait(maxWait);
			dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
			dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
			dataSource.setValidationQuery(validationQuery);
			dataSource.setTestWhileIdle(testWhileIdle);
			dataSource.setTestOnBorrow(testOnBorrow);
			dataSource.setTestOnReturn(testOnReturn);
			dataSource.setPoolPreparedStatements(poolPreparedStatements);
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		} catch (SQLException e) {
        	log.error("druid configuration initialization filter: "+ e);  
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("druid configuration initialization filter: "+ e);  
		} catch (Exception e) {
			e.printStackTrace();
			log.error("druid configuration initialization filter: "+ e);  
		}
        return dataSource;
    }

    /**
     * SqlSessionFactory配置
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "cluster7SqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(
            @Qualifier("cluster7DataSource") DataSource dataSource
    ) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //配置mapper文件位置
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(clusterMapperLocations));
        //Springboot整合Mybatis的CallSettersOnNulls配置问题 当值为空时属性也会没有
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
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
        sqlSessionFactoryBean.setConfigurationProperties(properties);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置事物管理器
     *
     * @return
     */
    @Bean(name = "cluster7TransactionManager")
    public DataSourceTransactionManager clusterTransactionManager(
            @Qualifier("cluster7DataSource") DataSource dataSource
    ) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}