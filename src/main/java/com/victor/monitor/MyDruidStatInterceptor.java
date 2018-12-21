package com.victor.monitor;
//package com.api.monitor;
//
//
//
//import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
//import org.springframework.aop.Advisor;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.aop.support.JdkRegexpMethodPointcut;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 配置Spring监控
// */
//@Configuration
//public class MyDruidStatInterceptor {
//
//    private static final String[] patterns = new String[]{"com.api.service.*"};
//
//    @Bean
//    public DruidStatInterceptor druidStatInterceptor() {
//        return new DruidStatInterceptor();
//    }
//
//    /**
//     * 切入点
//     * @return
//     */
//    @Bean
//    public JdkRegexpMethodPointcut druidStatPointcut() {
//        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
//        druidStatPointcut.setPatterns(patterns);
//        return druidStatPointcut;
//    }
//
//    /**
//     * 配置aop
//     * @return
//     */
//    @Bean
//    public Advisor druidStatAdvisor() {
//        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
//    }
//}
