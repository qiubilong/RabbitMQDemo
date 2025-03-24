package com.roy.rabbitmq.pubsub交换机类型;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.roy.rabbitmq.RabbitMQUtil;

public class FanoutEmitLog广播 {

	private static final String EXCHANGE_NAME = "fanoutExchange";
	/**
	 * exchange有四种类型， fanout topic headers direct
	 * fanout类型的exchange会往其上绑定的所有queue转发消息。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		Connection connection = RabbitMQUtil.getConnection();
		Channel channel = connection.createChannel();
		//发送者只管往exchange里发消息，而不用关心具体发到哪些queue里。
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); /* 交换机上所有队列， --> 相当于广播 */
		String message = "LOG INFO 222";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		
		channel.close();
		connection.close();
		
		
	}
}
