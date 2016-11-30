package com.my.shiroredis.shiro.sessionservice;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.shiroredis.shiro.cacheservice.CachingShiroSessionDao;

public class ShiroSessionListener implements SessionListener  {

	private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);  
	    
    private CachingShiroSessionDao cachingShiroSessionDao;  
    

	public CachingShiroSessionDao getCachingShiroSessionDao() {
		return cachingShiroSessionDao;
	}

	public void setCachingShiroSessionDao(CachingShiroSessionDao cachingShiroSessionDao) {
		this.cachingShiroSessionDao = cachingShiroSessionDao;
	}

	@Override  
    public void onStart(Session session) {  
        // 会话创建时触发  
        logger.info("ShiroSessionListener session {} 被创建", session.getId());  
    }  
  
    @Override  
    public void onStop(Session session) {  
    	cachingShiroSessionDao.delete(session);  
        // 会话被停止时触发  
        logger.info("ShiroSessionListener session {} 被销毁", session.getId());  
    }  
  
    @Override  
    public void onExpiration(Session session) {  
    	cachingShiroSessionDao.delete(session);  
        //会话过期时触发  
        logger.info("ShiroSessionListener session {} 过期", session.getId());  
    }

}
