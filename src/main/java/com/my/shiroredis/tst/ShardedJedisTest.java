package com.my.shiroredis.tst;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

/**
 * jedis独有的分布式
 * 根据List<JedisShardInfo>中redis顺序和key，指定固定的Redis
 * @author hello
 *
 */

public class ShardedJedisTest {

	private static Logger logger = LoggerFactory.getLogger(ShardedJedisTest.class);
	
	/**
	 * 不同的key分配根据List<JedisShardInfo>中的顺序，分配不同的Redis连接
	 */
	@Test
	public void show(){
		
		List<JedisShardInfo> infos = new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = new JedisShardInfo("101.200.136.55", 6380);
		info1.setPassword("goupeng");
		JedisShardInfo info = new JedisShardInfo("101.200.136.55", 6379);
		info.setPassword("goupeng");
		infos.add(info);
		infos.add(info1);
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), infos);
		ShardedJedis shardedJedis = pool.getResource();
		for(int i=0;i<20;i++){
			try{
				logger.info(i+" - "+shardedJedis.getShard(""+i).getClient().getHost()+" : "+shardedJedis.getShard(i+"").getClient().getPort());
				logger.info(shardedJedis.set(""+i, "true"));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		shardedJedis.close();
		pool.close();
		
	}
	
	/**
	 * 修改List<JedisShardInfo>中的顺序，无法得到结果，确定该顺序是有意义的。也就是info和info1的顺序不能变化
	 */
	@Test
	public void show1(){
		List<JedisShardInfo> infos = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo("101.200.136.55", 6379);
		info.setPassword("goupeng");
		JedisShardInfo info1 = new JedisShardInfo("101.200.136.55", 6380);
		info1.setPassword("goupeng");
		infos.add(info1);
		infos.add(info);
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), infos);
		ShardedJedis shardedJedis = pool.getResource();
		for (int i = 0; i < 20; i++) {  
            try {  
                logger.info(i + " - " + shardedJedis.getShard(i + "").getClient().getHost()  
                        + ":" + shardedJedis.getShard(i + "").getClient().getPort() + " ? " + shardedJedis.get("" + i));  
  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
	    shardedJedis.close();
	    pool.close();
	}
	
}
