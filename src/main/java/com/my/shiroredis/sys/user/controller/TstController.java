package com.my.shiroredis.sys.user.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.shiroredis.sys.user.entity.User;
import com.my.shiroredis.sys.user.service.UserService;


@Controller
@RequestMapping(value="tst")
public class TstController {

	@Resource
	private UserService userService;
	
	
	/**
	 * 测试权限是否有查看用户权限信息
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value="user:find")
	@RequestMapping(value="find")
	public String find(ModelMap map){
		
		User user = userService.findUserbyName("goupeng");
		
		map.put("user", user);
		return "list";
	}
	
	
	@RequestMapping(value="noqx")
	public String noqx(ModelMap map){
        User user = userService.findUserbyName("goupeng");
		
		map.put("user", user);
		return "list";
	}
	
}
