package com.victor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @SpringBootApplication 项目启动注解
 * @EnableScheduling 启动定时任务
 * @EnableAsync 让@Async注解能够生效 需要在主程序中加入这个注解
 * @author victor
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) {
		System.err.println("The service DemoApplication to start.");
		SpringApplication.run(DemoApplication.class, args);
		System.err.println("The service DemoApplication to end.");
	}

}
