package com.victor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.victor.dao.master.ApiMasterDao;
import com.victor.model.UserDto;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/test")
public class userController {
	
	@Autowired
	private ApiMasterDao apiMasterDao;
	
	 @ApiOperation(value="返回实体", notes="json格式的异常统一处理")
	 @RequestMapping(value="/user",method = RequestMethod.POST)
	 @ResponseBody
     public UserDto user(@RequestBody UserDto userDto) {
         return userDto;
     }
	 
	 @ApiOperation(value="返回实体列表", notes="json格式的异常统一处理")
	 @RequestMapping(value="/getUserList",method = RequestMethod.POST)
	 @ResponseBody
     public List<UserDto> user() {
         return apiMasterDao.findUserList();
     }
}
