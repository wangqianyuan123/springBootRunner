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

//		System.out.println(redisTemplate.delete("RELATION_1178_1.1"));
		System.out.println(redisTemplate.delete("tasklock"));

    }
}
