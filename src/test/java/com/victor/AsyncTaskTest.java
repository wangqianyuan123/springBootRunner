package com.victor;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.victor.task.AsyncTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncTaskTest {

	@Autowired
	private AsyncTask asyncTask;

	/**
	 * 测试一  （去掉 三个函数的@Async ）
	 * doTaskOne、doTaskTwo、doTaskThree三个函数顺序的执行完成。
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		asyncTask.doTaskOne();
		asyncTask.doTaskTwo();
		asyncTask.doTaskThree();
	}
	
	
	/**
	 * 测试二 （ 三个函数加上了 @Async ）
	 * 	此时可以反复执行单元测试，您可能会遇到各种不同的结果，比如：
	 * 没有任何任务相关的输出
	 * 有部分任务相关的输出
	 * 乱序的任务相关的输出
	 * 
	 * 原因是目前doTaskOne、doTaskTwo、doTaskThree三个函数的时候已经是异步执行了。
	 * 主程序在异步调用之后，主程序并不会理会这三个函数是否执行完成了，
	 * 由于没有其他需要执行的内容，所以程序就自动结束了
	 * ，导致了不完整或是没有输出任务相关内容的情况。
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		asyncTask.doTaskOne();
		asyncTask.doTaskTwo();
		asyncTask.doTaskThree();
	}
	


	/**
	 * 异步回调
	 * 统计一下三个任务并发执行共耗时多少，这就需要等到上述三个函数都完成调动之后记录时间，并计算结果。
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {

		long start = System.currentTimeMillis();

		Future<String> task1 = asyncTask.doTaskOne2();
		Future<String> task2 = asyncTask.doTaskTwo2();
		Future<String> task3 = asyncTask.doTaskThree2();

		while(true) {
			if(task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}

		long end = System.currentTimeMillis();

		System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

	}
	
	/**
	 * 线程池 执行异步  指定 线程池资源 taskExecutor
	 * @throws Exception
	 */
	 @Test
	 public void test3() throws Exception {
		 asyncTask.doTaskOne3();
		 asyncTask.doTaskTwo3();
		 asyncTask.doTaskThree3();
	     Thread.currentThread().join();
	    }

	 
	 /**
	  * 测试
	  * Redis连接池先销毁了，异步任务还要要访问Redis的操作
	  * 1.配置 setWaitForTasksToCompleteOnShutdown 的作用
	  * 确保 异步任务的销毁就会先于Redis线程池的销毁
	  * 2.setAwaitTerminationSeconds(60)
	  * 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
	  * @throws Exception
	  */
	  @Test
	  public void test4() throws Exception {

	        for (int i = 0; i < 10000; i++) {
	        	asyncTask.doTaskOne4();
	        	asyncTask.doTaskTwo4();
	        	asyncTask.doTaskThree4();
	            if (i == 9999) {
	                System.exit(0);
	            }
	        }
	    }
	
}
