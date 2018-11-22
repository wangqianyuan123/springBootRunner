package com.victor.aop;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/****************************************************************************************
实现AOP的切面主要有以下几个要素：

使用@Aspect注解将一个java类定义为切面类
使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
根据需要在切入点不同位置的切入内容
使用@Before在切入点开始处切入内容
使用@After在切入点结尾处切入内容
使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
使用@Order(i)注解来标识切面的优先级。i的值越小，优先级越高

/****************************************************************************************
执行日志顺序：

2018-11-22 16:19:15.511 [http-nio-8080-exec-3] INFO  com.victor.aop.WebLogHeadAspect - (Order(1))************** (classMethod)  : com.victor.controller.userController.user
2018-11-22 16:19:15.511 [http-nio-8080-exec-3] INFO  com.victor.aop.WebLogHeadAspect - (Order(1))************** (RequestBefore)  startTime: 1542874755511
2018-11-22 16:19:15.511 [http-nio-8080-exec-3] INFO  com.victor.aop.WebLogHeadAspect - (Order(1))************** (RequestBefore) UUID : c055e3d58a0341338968b5d30859cfdf
2018-11-22 16:19:15.517 [http-nio-8080-exec-3] INFO  com.victor.aop.WebLogHeadAspect - (Order(1))************** (doAfterReturning)  SpendTime: 6
2018-11-22 16:19:15.517 [http-nio-8080-exec-3] INFO  com.victor.aop.WebLogHeadAspect - (Order(1))************** (doAfterReturning) UUID : c055e3d58a0341338968b5d30859cfdf

总结：doBefore 方法先从优先级到低优先级依次执行完，然后 doAfterReturning 方法从低优先级到高优先级依次执行完；也就是进入从高到低，出来从低到高；
****************************************************************************************/

/**
* Web层日志切面。
*
*
*/
@Aspect
@Order(1)
@Component
public class WebLogHeadAspect {

	private static Logger log = LoggerFactory.getLogger(WebLogHeadAspect.class);

	//ThreadLocal 保证了请求栈中数据一致性
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	ThreadLocal<String> uuid = new ThreadLocal<>();

	private static final String PRE_TAG = "(Order(1))************** ";

	@Pointcut("execution(public * com.victor.controller..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内宿
		
		if (request.getMethod().equals("POST")) {
			startTime.set(System.currentTimeMillis());
			uuid.set(UUID.randomUUID().toString().replace("-", "")+"");
		} else {
			
		}
		log.info(PRE_TAG + "(classMethod)  : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info(PRE_TAG + "(RequestBefore)  startTime: " + startTime.get());
		log.info(PRE_TAG + "(RequestBefore) UUID : " + uuid.get());
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		log.info(PRE_TAG + "(doAfterReturning)  SpendTime: " + (System.currentTimeMillis()-startTime.get()));
		log.info(PRE_TAG + "(doAfterReturning) UUID : " + uuid.get());

	}
}