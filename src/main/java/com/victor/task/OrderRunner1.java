package com.victor.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class OrderRunner1 implements CommandLineRunner  {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("OrderRunner1 初始化开始");	
		 new Thread(new Thread()).start();
	        try {
	             Thread.sleep(1);
	         } catch (Exception e) {
	            e.printStackTrace();
	        }
		   System.out.println("OrderRunner1 初始化结束！");	
	}

}
