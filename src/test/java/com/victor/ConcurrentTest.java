package com.victor;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.victor.dao.master.ApiMasterDao;
import com.victor.model.UserDto;
import com.victor.serviceImpl.ResultService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrentTest {

    final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String LOCK = "task-job-lock";
	
	private static final String KEY = "tasklock";
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ApiMasterDao apiMasterDao;
    
	@Test
	public void test() throws Exception {
		//模拟10000人并发请求，用户钱包
        CountDownLatch latch=new CountDownLatch(1);
        //模拟10000个用户
        for(int i=0;i<10000;i++){
            AnalogUser analogUser = new AnalogUser("victor"+i,"58899dcd-46b0-4b16-82df-bdfd0d123"+i,"1","10.024",latch);
            analogUser.start();
        }
        //计数器減一  所有线程释放 并发访问。
        latch.countDown();
	
		TimeUnit.SECONDS.sleep(200);
        System.out.println("所有模拟请求结束  at "+sdf.format(new Date()));
	}
	
	  class AnalogUser extends Thread{
	        //模拟用户姓名
	        String workerName;
	        String openId;
	        String openType;
	        String amount;
	        CountDownLatch latch;

	        public AnalogUser(String workerName, String openId, String openType, String amount,
	                          CountDownLatch latch) {
	            super();
	            this.workerName = workerName;
	            this.openId = openId;
	            this.openType = openType;
	            this.amount = amount;
	            this.latch = latch;
	        }

	        @Override
	        public void run() {
	            // TODO Auto-generated method stub
	            try {
	                latch.await(); //一直阻塞当前线程，直到计时器的值为0
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            ConcurrentOperation();//并发操作


	        }

	        public void ConcurrentOperation(){
	        	boolean lock = false;
	        	try{
//	        		Random r = new Random();
//	        		int c = r.nextInt(10) + 1;
//	        		TimeUnit.SECONDS.sleep(c);
		    		// 获取锁

		        	lock = redisTemplate.opsForValue().setIfAbsent(KEY, LOCK);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		        	System.out.println("是否获取到锁:"+lock);
		        	if(lock){
		        		// 如果在执行任务的过程中，程序突然挂了，为了避免程序因为中断而造成一直加锁的情况产生，20分钟后，key值失效，自动释放锁，
		        		redisTemplate.expire(KEY, 1, TimeUnit.MINUTES);
		        		List<UserDto> users = apiMasterDao.findUserList();
		        		if(null != users && !users.isEmpty()){
		        			for(UserDto u:users){
		        				System.out.println("name:"+u.getUserName());
		        			}
		        		}
		        		// 模拟长时间任务
//		        		TimeUnit.MINUTES.sleep(3);
		        	}else{
		        		System.out.println("没有获取到锁，不执行任务!");
		        		return;
		        	}
//		    	} catch (InterruptedException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
				}finally{// 无论如何，最终都要释放锁
		    		if(lock){// 如果获取了锁，则释放锁
		    			redisTemplate.delete(KEY);
		    			System.out.println("任务结束，释放锁!");
		    		}else{
		    			System.out.println("没有获取到锁，无需释放锁!");
		    		}
		    	}
	            
	            System.out.println("模拟用户： "+workerName+" 模拟请求结束  at "+sdf.format(new Date()));

	        }
	    }

}
