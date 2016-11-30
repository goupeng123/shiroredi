package com.my.shiroredis.tst;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * redis的主从复制
 * @author goupeng
 *
 */

public class JedisMasSlave {

	@Test
	public void show(){
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(5000);
	    config.setMaxIdle(256);
	    config.setMaxWaitMillis(5000L);
	    config.setTestOnBorrow(true);
	    config.setTestOnReturn(true);
	    config.setTestWhileIdle(true);
	    config.setMinEvictableIdleTimeMillis(60000L);
	    config.setTimeBetweenEvictionRunsMillis(3000L);
	    config.setNumTestsPerEvictionRun(-1);
	    
	    //redis属性配置
	    Set<String> sentinels = new HashSet<String>();
	    sentinels.add("101.200.136.55:26379"); // 此处放置ip及端口为 sentinel
	                                            // 服务地址，如果有多个sentinel 则逐一add即可
	    //JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, config);
	    JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, config, "goupeng");
	    Jedis jedis = null;
	    try {
	        jedis = jedisSentinelPool.getResource();
            System.out.println("use的是："+jedis.getClient().getPort()+"   "+jedis.getClient().getHost());
	        jedis.set("tst", "123");
	    } catch (Exception e) {
	    	jedisSentinelPool.returnBrokenResource(jedis);

	    	e.printStackTrace();
	    } finally {
	    	//jedis.close();
	    	jedisSentinelPool.returnResource(jedis);
	    	jedisSentinelPool.destroy();
	    }
	    
	}
	
	
	@Test
	public void show1(){
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(5000);
	    config.setMaxIdle(256);
	    config.setMaxWaitMillis(5000L);
	    config.setTestOnBorrow(true);
	    config.setTestOnReturn(true);
	    config.setTestWhileIdle(true);
	    config.setMinEvictableIdleTimeMillis(60000L);
	    config.setTimeBetweenEvictionRunsMillis(3000L);
	    config.setNumTestsPerEvictionRun(-1);
	    
	    //redis属性配置
	    Set<String> sentinels = new HashSet<String>();
	    sentinels.add("101.200.136.55:26379"); // 此处放置ip及端口为 sentinel
	                                            // 服务地址，如果有多个sentinel 则逐一add即可
	    //JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, config);
	    JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, config, "goupeng");
	    Jedis jedis = null;
	    for(int i=0;i<50;i++){
		    try {
		        jedis = jedisSentinelPool.getResource();
	            System.out.println("use的是："+jedis.getClient().getPort()+"   "+jedis.getClient().getHost());
	            System.out.println(jedis.get("tst"));
		        jedis.set("tst", "123");
		    } catch (Exception e) {
		    	jedisSentinelPool.returnBrokenResource(jedis);
	
		    	e.printStackTrace();
		    } finally {
		    	//jedis.close();
		    	jedisSentinelPool.returnResource(jedis);
		    	jedisSentinelPool.destroy();
		    }
	    }
	}
}
