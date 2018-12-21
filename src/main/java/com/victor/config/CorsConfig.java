package com.victor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig  extends WebMvcConfigurerAdapter {

	
	 @Bean  
     public WebMvcConfigurer corsConfigurer() {  
         return new WebMvcConfigurerAdapter() {  
             @Override  
             public void addCorsMappings(CorsRegistry registry) {  
                 registry.addMapping("/**")  
                 .allowCredentials(true)  
                 .allowedMethods("GET", "POST");  
             }  
         };  
     }  
	 
//	 @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//         .allowedOrigins("*") //http://localhost:8879
//         .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
//         .allowCredentials(true)
//         .maxAge(3600);
//     }
}
