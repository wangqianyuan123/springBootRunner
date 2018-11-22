package com.victor;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

	
    @Resource
    RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void test() throws Exception {

		// 保存字符串
		redisTemplate.opsForValue().set("aaa", "小李子");
		System.out.println(redisTemplate.opsForValue().get("aaa"));
		Assert.assertEquals("小李子", redisTemplate.opsForValue().get("aaa"));

    }
}
