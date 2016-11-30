package com.my.shiroredis.sys.user.service;

import com.my.shiroredis.sys.user.entity.User;

public interface UserService {

	public User findUserbyName(String name);
	
	public User ginfUserById(Long id);
	
	public void save(User user);
	
}
