package com.my.shiroredis.tst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

/**
 * redis的消息订阅需要实现一个JedisPubSub，相当于redis消息的Listener
 * @author hello
 *
 */

public class JedisPubSubListener extends JedisPubSub {

	/**
	 * redis的消息订阅需要实现一个JedisPubSub，相当于redis消息的Listener
	 */
	private static Logger logger = LoggerFactory.getLogger(JedisPubSubListener.class);
	
	/**
	 * 取得订阅的消息后处理
	 */
	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		logger.info("取得订阅的消息后的处理："+channel+"="+message);
		super.onMessage(channel, message);
	}
	
	/**
	 * 初始化订阅时候的处理
	 */
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		logger.info("初始化订阅时候的处理："+channel+"="+subscribedChannels);
		super.onSubscribe(channel, subscribedChannels);
		
	}
	
	/**
	 * 取消订阅时候的处理
	 */
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		logger.info("取消订阅时候的处理："+channel+"="+subscribedChannels);
		super.onUnsubscribe(channel, subscribedChannels);
	}
	
	/**
	 * 取消按表达式的方式订阅时候的处理
	 */
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		logger.info("取消按表达式的方式订阅时候的处理"+pattern+"="+subscribedChannels);
		super.onPUnsubscribe(pattern, subscribedChannels);
	}
	
	/**
	 * 初始化按表达式的方式订阅时候处理
	 */
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		logger.info("初始化按表达式的方式订阅时候处理："+pattern+"="+subscribedChannels);
		super.onPSubscribe(pattern, subscribedChannels);
	}
	
	/**
	 * 取得按表达式的方式订阅的消息后处理
	 */
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		logger.info("取得按表达式的方式订阅的消息后处理："+pattern+"="+channel+"="+message);
		super.onPMessage(pattern, channel, message);
	}
	
}
