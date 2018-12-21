//package com.victor.task;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.victor.dao.master.ApiMasterDao;
//import com.victor.model.UserDto;
//
///**
// * 定时任务
// * @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
// * @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
// * @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
// * 通过cron表达式定义规则  在线网站 http://cron.qqe2.com/
// * @author victor
// *
// */
//@Component
//public class ScheduledTasks {
//	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
//	 
//		
//	    @Resource
//	    RedisTemplate<String, Object> redisTemplate;
//	    
//	    
//		@Autowired
//		private ApiMasterDao apiMasterDao;
//	// @Scheduled(cron="*/5 * * * * *") 通过cron表达式定义规则
////	    @Scheduled(fixedRate = 5000)
////	    public void reportCurrentTime() {
////	        System.out.println("现在时间：" + dateFormat.format(new Date()));
////	    }
//	
//		
//		private static final String LOCK = "task-job-lock";
//		
//		private static final String KEY = "tasklock";
//		
//		/**
//		 * 每15秒执行一次
//		 * 分布式环境下的定时任务方案
//		 * setnx / del / expire
//		 * @throws InterruptedException
//		 */
//	    @Scheduled(cron = "0/15 * * * * ?")
//	    public void reportCurrentByCron() throws InterruptedException{
//	    	boolean lock = false;
//	    	try{
//	    		// 获取锁
//	        	lock = redisTemplate.opsForValue().setIfAbsent(KEY, LOCK);
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
//	        	System.out.println("是否获取到锁:"+lock);
//	        	if(lock){
//	        		// 如果在执行任务的过程中，程序突然挂了，为了避免程序因为中断而造成一直加锁的情况产生，20分钟后，key值失效，自动释放锁，
//	        		redisTemplate.expire(KEY, 1, TimeUnit.MINUTES);
//	        		List<UserDto> users = apiMasterDao.findUserList();
//	        		if(null != users && !users.isEmpty()){
//	        			for(UserDto u:users){
//	        				System.out.println("name:"+u.getUserName());
//	        			}
//	        		}
//	        		// 模拟长时间任务
////	        		TimeUnit.MINUTES.sleep(3);
//	        	}else{
//	        		System.out.println("没有获取到锁，不执行任务!");
//	        		return;
//	        	}
//	    	}finally{// 无论如何，最终都要释放锁
//	    		if(lock){// 如果获取了锁，则释放锁
//	    			redisTemplate.delete(KEY);
//	    			System.out.println("任务结束，释放锁!");
//	    		}else{
//	    			System.out.println("没有获取到锁，无需释放锁!");
//	    		}
//	    	}
//	    }
//
//
//}
