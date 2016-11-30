package com.my.shiroredis.sys.user.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.my.shiroredis.sys.user.entity.User;
import com.my.shiroredis.sys.user.mapper.UserMapper;
import com.my.shiroredis.sys.user.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findUserbyName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findUserbyName(name).get(0);
	}

	@Override
	public User ginfUserById(Long id) {
		// TODO Auto-generated method stub
		return userMapper.findUserById(id);
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userMapper.save(user);
	}

}
