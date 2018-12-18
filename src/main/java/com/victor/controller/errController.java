package com.victor.controller;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victor.model.MyDataException;
import com.victor.model.MyPageException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 统一异常处理
 * 虽然，Spring Boot中实现了默认的error映射，
 * 但是在实际应用中，上面你的错误页面对用户来说并不够友好，
 * 我们通常需要去实现我们自己的异常提示。
 * @author victor
 *
 */
@RestController
public class errController {

	  @Resource
	    RedisTemplate<String, Object> redisTemplate;
	  
	  
	/**
	 * 1.默认的异常处理 界面显示（http://localhost:8080/hello）
	 * Whitelabel Error Page
	 * 2.统一异常处理 之后 变成 json格式
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="返回json", notes="json格式的异常统一处理")
    @ApiImplicitParam(name = "json", value = "无需输入参数", required = true, dataType = "demo1")
	@RequestMapping("data/hello")
	public String hello1(String datakey) throws Exception {
//	    throw new MyDataException("发生错误");
		//RELATION_M1168
//		System.out.println(redisTemplate.delete(datakey));
		return "123";

	}
	
	/**
	 * 1.默认的异常处理 界面显示（http://localhost:8080/hello）
	 * Whitelabel Error Page
	 * 2.统一异常处理 之后 变成err.html 显示的值
	 * @return
	 * @throws Exception
	 */	
	@ApiOperation(value="返回json", notes="页面的异常统一处理")
    @ApiImplicitParam(name = "json", value = "无需输入参数", required = true, dataType = "demo2")
	@RequestMapping("page/hello")
	public String hello2() throws Exception {
	    throw new MyPageException("发生错误");
	}
}
