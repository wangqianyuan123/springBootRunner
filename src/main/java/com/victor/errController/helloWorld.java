package com.victor.errController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victor.model.MyDataException;
import com.victor.model.MyPageException;

/**
 * 统一异常处理
 * 虽然，Spring Boot中实现了默认的error映射，
 * 但是在实际应用中，上面你的错误页面对用户来说并不够友好，
 * 我们通常需要去实现我们自己的异常提示。
 * @author victor
 *
 */
@RestController
public class helloWorld {

	/**
	 * 1.默认的异常处理 界面显示（http://localhost:8080/hello）
	 * Whitelabel Error Page
	 * 2.统一异常处理 之后 变成 json格式
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("data/hello")
	public String hello1() throws Exception {
	    throw new MyDataException("发生错误");
	}
	
	/**
	 * 1.默认的异常处理 界面显示（http://localhost:8080/hello）
	 * Whitelabel Error Page
	 * 2.统一异常处理 之后 变成err.html 显示的值
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping("page/hello")
	public String hello2() throws Exception {
	    throw new MyPageException("发生错误");
	}
}
