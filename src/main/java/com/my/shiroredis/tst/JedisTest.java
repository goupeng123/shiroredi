package com.my.shiroredis.tst;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.my.shiroredis.shiro.redisservice.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class JedisTest {

	/**
	 * redis连接测试
	 */
	@Test
	public void tst(){
		Jedis jedis = new Jedis("101.200.136.55", 6379);
		jedis.auth("goupeng");
		jedis.set("haha", "苟鹏");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String haha = jedis.get("haha");
		System.out.println(haha);
		jedis.close();
		
	}
	
	@Test
	public void show1(){
		Jedis jedis = new RedisService().getResource();
		Map<String, Double> memberScore = Maps.newHashMap();
		memberScore.put("foo", -1.0);
		memberScore.put("bar",-1.5);
		memberScore.put("one",2d);
		jedis.zadd("myzset", memberScore);
		jedis.zadd("myzset", 1d, "苟鹏");
		Set<Tuple> myzset = jedis.zrangeWithScores("myzset", 0, -1);
		for(Tuple tuple : myzset){
			System.out.println(tuple.getScore()+"  "+tuple.getElement());
		}
	}
	
}
