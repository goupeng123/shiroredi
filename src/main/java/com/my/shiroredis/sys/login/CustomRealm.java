package com.my.shiroredis.sys.login;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.my.shiroredis.shiro.cacheservice.CachingShiroSessionDao;
import com.my.shiroredis.sys.user.entity.User;
import com.my.shiroredis.sys.user.service.UserService;


/**
 * 
 * @ClassName: CustomRealm
 * @Description: 用户的认证和授权
 * @author: goupeng
 * @date: 2016-1-29 下午6:10:09
 */
public class CustomRealm extends AuthorizingRealm{
    @Resource
    private UserService userService;
   
    private CachingShiroSessionDao cachingShiroSessionDao;

	public CachingShiroSessionDao getCachingShiroSessionDao() {
		return cachingShiroSessionDao;
	}

	public void setCachingShiroSessionDao(CachingShiroSessionDao cachingShiroSessionDao) {
		this.cachingShiroSessionDao = cachingShiroSessionDao;
	}

	@Override
	//用户认证
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		//获取用户名
		String name = (String) token.getPrincipal();
		User user = userService.findUserbyName(name);
		if(user==null){
			return null;
		}
		String password = user.getPassword();
		String salt = "hello";
		
		// 把账号信息放到Session中，并更新缓存,用于会话管理
		/*Subject subject = SecurityUtils.getSubject();
		Serializable sessionId = subject.getSession().getId();
		ShiroSession session = (ShiroSession) cachingShiroSessionDao.doReadSessionWithoutExpire(sessionId);
		session.setAttribute("userId", user.getId());
		session.setAttribute("loginName", name);
		cachingShiroSessionDao.update(session);*/
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, ByteSource.Util.bytes(salt),this.getName());
		return info;
	}

	@Override
	//用户授权
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		User user = (User) principals.getPrimaryPrincipal();
		
		//伪造权限
		List<String> permissions = new ArrayList<String>();
        permissions.add("user:add");
        permissions.add("user:update");
       
        permissions.add("user:delete");
		SimpleAuthorizationInfo simple = new SimpleAuthorizationInfo();
		simple.addStringPermissions(permissions);
		return simple;
	}
    //清除缓存
	private void clearcached() {
		// TODO Auto-generated method stub
        PrincipalCollection collection = SecurityUtils.getSubject().getPreviousPrincipals();
        super.clearCache(collection);
	}
	
}
