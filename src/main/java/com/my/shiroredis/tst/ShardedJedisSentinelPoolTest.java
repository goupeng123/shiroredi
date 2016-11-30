package com.my.shiroredis.tst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.ShardedJedis;

/**
 * 测试分片的高可用failover
 * @author hello
 *
 */

public class ShardedJedisSentinelPoolTest {

	
	@Test
	public void show(){
		
		Set<String> sentinels = new HashSet<>();
		sentinels.add("101.200.136.55:26379");
		sentinels.add("101.200.136.55:26380");
		sentinels.add("101.200.136.55:26381");
		
		List<String> masters = new ArrayList<String>();
		masters.add("mymaster");
		masters.add("mymaster1");
		
		System.out.println("开始测试.........");
		for(int i=0;i<50;i++){
			
			@SuppressWarnings("resource")
			ShardedJedisSentinelPool shardedJedisSentinelPool = new ShardedJedisSentinelPool(masters, sentinels, "goupeng");
		
			ShardedJedis shardedJedis = null ;
			try {
				
				shardedJedis = shardedJedisSentinelPool.getResource();
				
				shardedJedis.set("haha"+i, "val"+i);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				
				if(null != shardedJedis){
				     shardedJedis.close();	
				     shardedJedisSentinelPool.close();
				     
				}
			}
		}
		System.out.println("测试结束！");
	}
	
	@Test
	public void show1(){
		Set<String> sentinels = new HashSet<>();
		sentinels.add("101.200.136.55:26379");
		sentinels.add("101.200.136.55:26380");
		sentinels.add("101.200.136.55:26381");
		
		List<String> masters = new ArrayList<String>();
		masters.add("mymaster");
		masters.add("mymaster1");
		
		System.out.println("开始测试.........");
		for(int i=0;i<50;i++){
			
			@SuppressWarnings("resource")
			ShardedJedisSentinelPool shardedJedisSentinelPool = new ShardedJedisSentinelPool(masters, sentinels, "goupeng");
		
			ShardedJedis shardedJedis = null ;
			try {
				
				shardedJedis = shardedJedisSentinelPool.getResource();
				System.out.println("ip和端口："+shardedJedis.getShard("haha"+i).getClient().getHost()+" : "+shardedJedis.getShard("haha"+i).getClient().getPort());
				
				System.out.println("  result: "+shardedJedis.get("haha"+i));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				
				if(null != shardedJedis){
				     shardedJedis.close();	
				     shardedJedisSentinelPool.close();
				     
				}
			}
		}
		System.out.println("测试结束！");
	}
	
}
