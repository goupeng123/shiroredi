package com.my.shiroredis.sys.login;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;


/**
 * 
 * @ClassName: FormCustomRealm
 * @Description: 验证验证码
 * @author: goupeng
 * @date: 2016-1-29 下午6:27:27
 */
public class FormCustomRealm extends FormAuthenticationFilter{
       @Override
    protected boolean onAccessDenied(ServletRequest request,
    		ServletResponse response) throws Exception {
    	// TODO Auto-generated method stub
    	HttpServletRequest req = (HttpServletRequest) request;
    	String yyzm = (String) req.getParameter("yyzm");
    	String yzm = (String)SecurityUtils.getSubject().getSession().getAttribute("yzm");
    	if(yyzm==null && yzm==null){
    		req.setAttribute("shiroLoginFailure", "First");
    		return true;
    	}
    	//前端输入的验证码为空
    	if(yyzm == null || yyzm.trim().equals("")){
    		req.setAttribute("shiroLoginFailure", "NoYzm");
    		return true;
    	}
    	//前端输入的验证码和生成的验证码不一样
    	if(yyzm != null && !yyzm.equalsIgnoreCase(yzm)){
    		req.setAttribute("shiroLoginFailure", "randomCodeError");
    		return true;
    	}
    	return super.onAccessDenied(request, response);
    }
}
