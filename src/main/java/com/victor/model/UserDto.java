package com.victor.model;

import java.time.LocalDate;
import java.util.Date;

public class UserDto {

	private int id;
    private String userName;
    private Date  birthday;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	

	
}
