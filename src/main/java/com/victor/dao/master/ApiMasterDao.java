package com.victor.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.victor.model.UserDto;



public interface ApiMasterDao {

	// 获取 key值对应sql
	@Select("select U.ID as \"id\" ,U.USER_NAME as \"userName\" ,U.BIRTHDAY  as \"birthday\" from springboot.userInfo U")
	public List<UserDto> findUserList();
}
