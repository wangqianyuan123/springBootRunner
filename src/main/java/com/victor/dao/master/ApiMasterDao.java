package com.victor.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.victor.model.UserDto;


@Mapper
public interface ApiMasterDao {

	// 获取 key值对应sql
	@Select("select U.ID as \"id\" ,U.USER_NAME as \"userName\" ,U.BIRTHDAY  as \"birthday\" from springboot.userInfo U")
	public List<UserDto> findUserList();
	/**
	 * xml形式
	 * @param userDto
	 * @return
	 */
	public int insertData(UserDto userDto);
	
	/**
	 * 注解形式
	 * @param userDto
	 * @return
	 */
	@Insert("insert into userInfo(ID,USER_NAME,BIRTHDAY) values(#{id},#{userName},#{birthday})")
	public int insertUser(UserDto userDto);
	
	/**
	 * 注解形式
	 * @param userDto
	 * @return
	 */
	@Insert("update userInfo(ID,USER_NAME,BIRTHDAY) values(#{id},#{userName},#{birthday})")
	public int updateUser(UserDto userDto);
}
