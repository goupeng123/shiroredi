package com.my.shiroredis.tst;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试jedis的消息的发布/订阅
 * @author hello
 *
 */

public class TestPubSub{

	private static Logger logger = LoggerFactory.getLogger(TestPubSub.class);
	/**
	 * subscribe [channel....] 订阅一个匹配的通道
	 * psubscribe [pattern....] 订阅匹配的通道
	 * publish [channel][message] 将value推送到channelone通道中
	 * unsubscribe [channel....] 取消订阅消息
	 * web环境中可以编写一个JedisPubSub继承 redis.clients.jedis.JedisPubSub来实现监听
	 * jedis中通过使用 jedisPubSub.unsubscribe/punsubscribe来取消订阅
	 */
	
	
	@Test
	public void show(){
		
		final JedisPubSubListener listener = new JedisPubSubListener();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			    logger.info("subscribe channelA.test channelB.send_message");
			    
			}
		});
		
	}
}
