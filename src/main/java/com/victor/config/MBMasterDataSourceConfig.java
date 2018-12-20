//package com.victor.config;
//
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.github.pagehelper.PageHelper;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
///**
// * master节点数据源配置
// *
// */
//@Configuration
//@MapperScan(basePackages = {MBMasterDataSourceConfig.PACKAGE},
//        sqlSessionFactoryRef = "masterSqlSessionFactory")
//public class MBMasterDataSourceConfig {
//
//	private static Logger log = LoggerFactory.getLogger(MBMasterDataSourceConfig.class);
//	 
//	static final String PACKAGE = "com.victor.dao.master";
//	    
//    @Value("${spring.datasource.master.masterMapperLocations}")
//    private String masterMapperLocations;
//
//	@Value("${spring.datasource.master.driverClassName}")
//	private String driverClassName;
//    @Value("${spring.datasource.master.url}")
//	private String dbUrl;
//	@Value("${spring.datasource.master.username}")
//	private String username;
//	@Value("${spring.datasource.master.password}")
//	private String password;
//
//	
////  @ConfigurationProperties(prefix = "spring.datasource.master")
//    @Bean(name = "masterDataSource")
//    @Primary
//    public DataSource masterDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        try {
//        	 dataSource.setUrl(this.dbUrl);  
//             dataSource.setUsername(username);
////             String psw =  java.net.URLDecoder.decode(Secret.decrypt(password,"EASIPASS"), "utf-8") ;
//             dataSource.setPassword(password);  
//             dataSource.setDriverClassName(driverClassName); 
////             dataSource.setFilters("stat,wall,log4j");
//        } catch (Exception e) {
//        	log.error("druid configuration initialization filter: "+ e);
//        } 
//        return dataSource;
//    }
//
//    /**
//     * SqlSessionFactory配置
//     *
//     * @return
//     * @throws Exception
//     */
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory masterSqlSessionFactory(
//            @Qualifier("masterDataSource") DataSource dataSource
//    ) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        // 配置mapper文件位置
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(masterMapperLocations));
//
//        //配置分页插件
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        pageHelper.setProperties(properties);
//
//        //设置插件
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    /**
//     * 配置事物管理器
//     *
//     * @return
//     */
//    @Bean(name = "masterTransactionManager")
//    @Primary
//    public DataSourceTransactionManager masterTransactionManager(
//            @Qualifier("masterDataSource") DataSource dataSource
//    ) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(dataSource);
//        return dataSourceTransactionManager;
//    }
//}