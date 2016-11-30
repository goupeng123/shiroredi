package com.my.shiroredis.tst;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

/**
 * jedisPool连接池
 * @author hello
 *
 */

public class JedisUtil {

	
	/**
	 * 执行400多秒
	 */
	@Test
	public void show(){
	    
		@SuppressWarnings("resource")
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "101.200.136.55", 6379, 0); 	
		
		Jedis jedis = pool.getResource();
		jedis.auth("goupeng");
		long start = System.currentTimeMillis();
		for(int i=0 ; i<10000;i++){
			String reult = jedis.set("np"+i, "vp"+i);
		}
		long end = System.currentTimeMillis();
		System.out.println("用时："+(end-start)/1000.0);
		
	jedis.close();
	}
	
	
	/**
	 * 测试事务
	 * 通过multi开启一个事务，exec（执行）、discard（放弃事务）
	 * 0.305
	 */
	@Test
	public void show1(){
		
		Jedis jedis = new Jedis("101.200.136.55",6379);
		jedis.auth("goupeng");
	    long start = System.currentTimeMillis();
	    Transaction transaction = jedis.multi();
	    for(int i=0;i<10000;i++){
	    	transaction.set("t"+i, "v"+i);
	    	
	    }
	    List<Object> list = transaction.exec();
	    long end = System.currentTimeMillis();
	    System.out.println((end-start)/1000.0);
	    jedis.close();
		
	}
	
	/**
	 * 0.289
	 * 管道，异步方式，一次发送多个指令
	 */
	@Test
	public void show2(){
		Jedis jedis = new Jedis("101.200.136.55",6379);
		jedis.auth("goupeng");
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		for(int i=0;i<10000;i++){
			pipeline.set("p"+i, "q"+i);
		}
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("用时："+(end-start)/1000.0);
		jedis.close();
	}
	
	/**
	 * 0.341
	 * 管道欧中调用事务
	 * 测试中会发生java.lang.StackOverflowError
	 */
	@Test
	public void show3(){
		Jedis jedis = new Jedis("101.200.136.55",6379);
		jedis.auth("goupeng");
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		for(int i=0;i<10000;i++){
			pipeline.set("i"+i, ""+i);
		}
		pipeline.exec();
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000.0);
	}
	
	/**
	 * 分布式直接连接同步调用
	 * 通过List<JedisShardInfo>中info的顺序和key，计算hash，确定key为固定的redis中
	 * 耗时很长
	 */
	@Test
	public void show4(){
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo = new JedisShardInfo("101.200.136.55", 6379);
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo("101.200.136.55", 6380);
		jedisShardInfo.setPassword("goupeng");
		jedisShardInfo1.setPassword("goupeng");
		shards.add(jedisShardInfo1);
		shards.add(jedisShardInfo);
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		long start = System.currentTimeMillis();
		for(int i=0;i<100;i++){
			String redult = shardedJedis.set("sn"+i, "val"+i);
		
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)/1000.0);
		shardedJedis.close();
	}
	
	/**
	 * 分布式直接连接管道异步调用 ，key的分配结果同分布式直接连接同步调用相同
	 * 3.338
	 */
	@Test
	public void show5(){
		List<JedisShardInfo> shardinfos = new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = new JedisShardInfo("101.200.136.55", 6379);
		info1.setPassword("goupeng");
		JedisShardInfo info = new JedisShardInfo("101.200.136.55", 6380);
		info.setPassword("goupeng");
		shardinfos.add(info);
		shardinfos.add(info1);
		ShardedJedis shardedJedis = new ShardedJedis(shardinfos);
		ShardedJedisPipeline pipeline = shardedJedis.pipelined();
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			pipeline.set("pip"+i, "pipVal"+i);
		}
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)/1000.0);
		shardedJedis.close();
	}
	
	/**
	 * 分布式连接池同步调用
	 * 3.843
	 */
	@Test
	public void testShardSimplePool(){
		List<JedisShardInfo> infos = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo("101.200.136.55", 6379);
		info.setPassword("goupeng");
		JedisShardInfo info1 = new JedisShardInfo("101.200.136.55", 6380);
		info1.setPassword("goupeng");
		infos.add(info);
		infos.add(info1);
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),infos);
		ShardedJedis shardedJedis =  pool.getResource();
		long start = System.currentTimeMillis();
		for(int i=0;i<100;i++){
			shardedJedis.set("key"+i, "val"+i);
		}
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000.0);
		shardedJedis.close();
		pool.close();
	}
	
	/**
	 * 分布式连接池异步调用相同，返回管道与分布式直接管道异步相同
	 */
	@Test
	public void test5(){
		List<JedisShardInfo> infos = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo("101.200.136.55", 6379);
		info.setPassword("goupeng");
		JedisShardInfo info1 = new JedisShardInfo("101.200.136.55", 6380);
		info1.setPassword("goupeng");
		infos.add(info);
		infos.add(info1);
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), infos);
		ShardedJedis shardedJedis = pool.getResource();
		ShardedJedisPipeline pipline = shardedJedis.pipelined();
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
               pipline.set("key"+i, "val"+i);
		}
		pipline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000.0);
		shardedJedis.close();
		pool.close();
	}
	
	
}
