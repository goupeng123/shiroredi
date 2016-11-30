package com.my.shiroredis.tst;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *slaveof和slaveofNoOne方法可以在运行中动态设置服务器为Master或slave 
 * @author hello
 *
 */


public class SlaveofTest {

	private static Logger logger = LoggerFactory.getLogger(SlaveofTest.class);
	
	@Test
	public void show(){
		JedisPool pool1 = null;
		Jedis jedis1 = null;
		JedisPool pool2 = null;
		Jedis jedis2 = null;
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		pool1 = new JedisPool(poolConfig, "101.200.136.55", 6379);
		pool2 = new JedisPool(poolConfig, "101.200.136.55",6380);
		jedis1 = pool1.getResource();
		jedis1.auth("goupeng");
		jedis2 = pool2.getResource();
		jedis2.auth("goupeng");
		logger.info("还没有master和slave！");
		jedis1.slaveofNoOne();
		logger.info("设置redis2为redis1的slave！");
		jedis2.slaveof("101.200.136.55", 6379);
		try{
			jedis1.set("key1", "jedis2 is master!");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("由于设置原因slave为read only！");
		}
		
		jedis2.close();
		jedis1.close();
		pool1.close();
		pool2.close();
	}
	
	
	
}
