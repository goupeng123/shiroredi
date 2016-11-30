package com.my.shiroredis.sys.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.shiroredis.sys.user.entity.User;


/**
 * 
 * @ClassName: LoginController
 * @Description: 用户登录
 * @author: goupeng
 * @date: 2016-2-1 上午11:25:16
 */
@Controller
public class LoginController {
	

    /**
     * 用户登录
     */
    @RequestMapping(value="login")
    public String login(HttpServletRequest request,ModelMap map){
    	//shiro登录成功后会自动跳转到成功页面
    	String executeName = (String) request.getAttribute("shiroLoginFailure");
    	if(executeName == null){
    		map.put("msg", "");
    	}else if(executeName.equals("First")){
    		map.put("msg", "");
    	}else if(executeName.equals("NoYzm")){
    		map.put("msg", "验证码不能为空！");
    	}else if(executeName.equals(UnknownAccountException.class.getName())){
    		map.put("msg", "用户名不存在！");
    	}else if(executeName.equals(IncorrectCredentialsException.class.getName())){
    		map.put("msg", "用户名或者密码错误！");
    	}else if(executeName.equals("randomCodeError")){
    		map.put("msg", "验证码错误！");
    	}else{
    		map.put("msg", "未知错误！");
    	}
    	//清除session中上次记忆的url链接
    	SecurityUtils.getSubject().getSession().setTimeout(0);
    	return "index";
    }
    /**
     * 登录成功！
     */
    @RequestMapping(value="welcome")
    public String welcome(ModelMap map){
    	Subject subject = SecurityUtils.getSubject();
    	User user = (User) subject.getPrincipal();
    	return "welcome";
    }
    
    /**
     * 退出
     * @return
     */
    @RequestMapping(value="logout")
    public String logout(){
    	
    	Subject subject = SecurityUtils.getSubject();
    	subject.logout();
    	return "index";
    }
    
    
    
}
