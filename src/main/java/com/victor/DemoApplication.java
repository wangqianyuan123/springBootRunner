package com.victor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.victor.config.dynamicDataSource.DataSourceUtil;
import com.victor.utils.SpringContextUtil;

/**
 * @SpringBootApplication 项目启动注解
 * @EnableScheduling 启动定时任务
 * @EnableAsync 让@Async注解能够生效 需要在主程序中加入这个注解
 * @author victor
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) {
		System.err.println("The service DemoApplication to start.");
		 ConfigurableApplicationContext applicationContext=SpringApplication.run(DemoApplication.class, args);
        // 初始化加载数据源
        SpringContextUtil.setApplicationContext(applicationContext);
        DataSourceUtil.initDataSource();
		System.err.println("The service DemoApplication to end.");

	}

}
