package com.my.shiroredis.shiro.redisservice;


import redis.clients.jedis.Jedis;


public class RedisService  {


	public Jedis getResource() {
		// TODO Auto-generated method stub
		
		Jedis jedis = null ;
		try {			
			jedis = new Jedis("101.200.136.55", 6379);
			jedis.auth("goupeng");
			return jedis;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	
	public void returnResource(Jedis jedis) {
		// TODO Auto-generated method stub
		try {
			if(null != jedis)
			    jedis.close(); 
		} catch (Exception e) {
			// TODO: handle exception
			
		}
	}

}
