package com.victor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @SpringBootApplication 项目启动注解
 * @EnableScheduling 启动定时任务
 * @author victor
 *
 */
@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		System.err.println("The service DemoApplication to start.");
		SpringApplication.run(DemoApplication.class, args);
		System.err.println("The service DemoApplication to end.");
	}

}
