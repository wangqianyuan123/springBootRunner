package com.victor;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.victor.utils.RedisUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuzzyQueryTest {

    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    RedisUtils redisUtils;
    
	@Test
	public void test() throws Exception {
		redisUtils.set("_dataKeyUrlLock_", "123qwt");
	    Set<String> keys = redisTemplate.keys("*dataKeyUrlLock*");
	    Iterator<String> iterator=keys.iterator();
	    while(iterator.hasNext()){
	    	System.out.println("符合要求的"+iterator.next());
	    }
	    
		System.out.println("准备删除keys");
		redisTemplate.delete(keys);
		
//		System.out.println("keys -->"+(String) opsForValue.get("wqy123"));
	}
}
