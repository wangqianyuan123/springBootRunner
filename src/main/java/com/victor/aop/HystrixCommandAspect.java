package com.victor.aop;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.victor.model.UserDto;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
@Order(3)
public class HystrixCommandAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;





    /**
     * 拦截 controller 请求
     */

    //@Pointcut("execution(public * com.easipass.api.controller.DataConfigureController.*(..))")
    @Pointcut("execution(public * com.victor.controller..*.*(..))")
    public void hystrixPointcut() {

        System.out.printf("shuchu");
    }

    @Around("hystrixPointcut()")
    public Object runCommand(final ProceedingJoinPoint pJoinPoint) throws Throwable {
        return wrapWithHystrixCommand(pJoinPoint).execute();
    }

    private HystrixCommand<Object> wrapWithHystrixCommand(final ProceedingJoinPoint pJoinPoint) {
        String method = pJoinPoint.getSignature().getName();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //String orginalUrl = request.getAttribute("javax.servlet.forward.servlet_path") == null ? "" : request.getAttribute("javax.servlet.forward.servlet_path").toString();
        String orginalUrl = request.getAttribute("requestExternalUrl") == null ? "" : request.getAttribute("requestExternalUrl").toString();
        if (StringUtils.isBlank(orginalUrl)) {
            orginalUrl = request.getRequestURI();
        }
        String dataKey = "";
        //设置接口超时时间
        int timeOut=3000;
        dataKey = "notDefined";
        // HystrixPropertiesFactory.reset();

        // 重写，构造 HystrixCommandKey
        return new HystrixCommand<Object>(setter(dataKey, timeOut)) {

            @Override
            protected Object run() throws Exception {
                try {
                    return pJoinPoint.proceed();
                } catch (Throwable throwable) {
                    throw (Exception) throwable;
                }
            }

            @Override
            protected Object getFallback() {
            	//错误返回的格式
            	List<UserDto> uer=new ArrayList<UserDto>();
            	UserDto userDto=new UserDto();
            	userDto.setUserName("Service Time Out");
            	uer.add(userDto);
//                BaseApiReqDataResponse<Object> baseApiReqDataResponse = new BaseApiReqDataResponse<>();
//                baseApiReqDataResponse.setStatusCode(StatusCodes.SERVICE_TIMEOUT);
//                baseApiReqDataResponse.setMsg("");
                return uer;
            }
        };
    }

    /**
     * 自定义配置Hystrix
     * @param dataKey
     * @param timeOut /ms
     * @return
     */
    private HystrixCommand.Setter setter(String dataKey, int timeOut) {
        // 初始化超时配置
        HystrixCommandProperties.Setter builder = HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(timeOut)
                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withCircuitBreakerRequestVolumeThreshold(1);
        //System.out.printf(String.valueOf(timeOut));
        return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(dataKey == null ? "groupName" : dataKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(dataKey == null ? "groupName" : dataKey))
                .andCommandPropertiesDefaults(builder)
                ;

    }

}
