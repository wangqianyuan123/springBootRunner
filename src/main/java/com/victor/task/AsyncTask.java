package com.victor.task;

import java.util.Random;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;


/**
 * 
 * 注： @Async所修饰的函数不要定义为static类型，这样异步调用不会生效
 * 
 * 3.异步调用的执行任务使用这个线程池中的资源只需要在@Async注解中指定线程池名即可  比如 doTaskOne3
 * @author victor
 *
 */
@Component
public class AsyncTask {
	private static Logger log = LoggerFactory.getLogger(AsyncTask.class);
	
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    
	public static Random random =new Random();

	
	@Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }

	
	@Async
	public Future<String> doTaskOne2() throws Exception {
		System.out.println("开始做任务一");
		long start = System.currentTimeMillis();
		Thread.sleep(random.nextInt(10000));
		long end = System.currentTimeMillis();
		System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
		return new AsyncResult<>("任务一完成");
	}
	
	 @Async("taskExecutor")
	 public void doTaskOne3() throws Exception {
	        log.info("开始做任务一");
	        long start = System.currentTimeMillis();
	        Thread.sleep(random.nextInt(10000));
	        long end = System.currentTimeMillis();
	        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
	    }
	 
	 @Async("taskExecutor")
	 public void doTaskOne4() throws Exception {
	        log.info("开始做任务一");
	        long start = System.currentTimeMillis();
	        log.info(redisTemplate.randomKey());
	        long end = System.currentTimeMillis();
	        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
	    }
	 
	 
	 
	@Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

	@Async
	public Future<String> doTaskTwo2() throws Exception {
	    System.out.println("开始做任务二");
	    long start = System.currentTimeMillis();
	    Thread.sleep(random.nextInt(10000));
	    long end = System.currentTimeMillis();
	    System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
	    return new AsyncResult<>("任务二完成");
	}
	
	
    @Async("taskExecutor")
    public void doTaskTwo3() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }
    
    @Async("taskExecutor")
    public void doTaskTwo4() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        log.info(redisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }
    
	@Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }
	
	@Async
	public Future<String> doTaskThree2() throws Exception {
	    System.out.println("开始做任务三");
	    long start = System.currentTimeMillis();
	    Thread.sleep(random.nextInt(10000));
	    long end = System.currentTimeMillis();
	    System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
	    return new AsyncResult<>("任务三完成");
	}
	
	
    @Async("taskExecutor")
    public void doTaskThree3() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }
    
    @Async("taskExecutor")
    public void doTaskThree4() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        log.info(redisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }
}
