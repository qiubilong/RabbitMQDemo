package com.roy.rabbitmq.pubsub交换机类型;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.roy.rabbitmq.RabbitMQUtil;

public class TopicEmitLog单词匹配 {

	private static final String EXCHANGE_NAME = "topicExchange";
	/**
	 * exchange有四种类型， fanout topic headers direct
	 * topic类型的exchange在根据routingkey转发消息时，可以对rouytingkey做一定的规则，比如anonymous.info可以被*.info匹配到。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		Connection connection = RabbitMQUtil.getConnection();
		Channel channel = connection.createChannel();
		//发送者只管往exchange里发消息，而不用关心具体发到哪些queue里。
		channel.exchangeDeclare(EXCHANGE_NAME, "topic"); /* routingKey单词匹配 */

		String message = "LOG INFO";
		channel.basicPublish(EXCHANGE_NAME, "anonymous.info", null, message.getBytes());/* 单词用.分开 */
		channel.basicPublish(EXCHANGE_NAME, "tuling.loulan.debug", null, message.getBytes());

		channel.close();
		connection.close();
	}
}
