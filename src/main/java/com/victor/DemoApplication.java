package com.victor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.err.println("The service DemoApplication to start.");
		SpringApplication.run(DemoApplication.class, args);
		System.err.println("The service DemoApplication to end.");
	}
}
