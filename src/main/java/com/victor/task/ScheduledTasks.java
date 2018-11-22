package com.victor.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
 * @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
 * @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
 * 通过cron表达式定义规则  在线网站 http://cron.qqe2.com/
 * @author victor
 *
 */
@Component
public class ScheduledTasks {
	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
	// @Scheduled(cron="*/5 * * * * *") 通过cron表达式定义规则
	    @Scheduled(fixedRate = 5000)
	    public void reportCurrentTime() {
	        System.out.println("现在时间：" + dateFormat.format(new Date()));
	    }
}
