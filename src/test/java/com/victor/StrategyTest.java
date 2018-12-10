package com.victor;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.victor.serviceImpl.ResultService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {

	
	
    @Autowired
    private ResultService resultService;
    
	@Test
	public void test() throws Exception {

		System.out.println(resultService.dealList(2, 1, 1, 1, 1));

    }
}
