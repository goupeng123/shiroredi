package com.my.shiroredis.sys.user.mapper;

import java.util.List;

import com.my.shiroredis.sys.user.entity.User;

public interface UserMapper {

	public List<User> findUserbyName(String name);
	
	public User findUserById(Long id);
	
	public void save(User user);
	
}
