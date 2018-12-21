package com.victor;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.victor.model.dataSourceConfig.DataSourceInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

	
    @Resource
    RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void test() throws Exception {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Object objectRelation = opsForValue.get("eir" + "_DB");
       
        List<DataSourceInfo>  dataSourceInfos = JSONObject.parseArray(JSONObject.toJSONString(objectRelation), DataSourceInfo.class);
     System.out.println(dataSourceInfos.size());
       
    }
}
