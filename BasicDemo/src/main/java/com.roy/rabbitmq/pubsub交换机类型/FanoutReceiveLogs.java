package com.roy.rabbitmq.pubsub交换机类型;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.roy.rabbitmq.RabbitMQUtil;

public class FanoutReceiveLogs {

	private static final String EXCHANGE_NAME = "fanoutExchange";

	public static void main(String[] args) throws Exception {
		Connection connection = RabbitMQUtil.getConnection();
		Channel channel = connection.createChannel();
		
	    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	    String queueName = channel.queueDeclare().getQueue();/* 生成临时独占队列，关闭连接后删除*/
		System.out.println(queueName);
		//绑定队列
	    channel.queueBind(queueName, EXCHANGE_NAME, "");

		Consumer myconsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body)
					throws IOException {
				 System.out.println("========================");
				 String routingKey = envelope.getRoutingKey();
				 System.out.println("routingKey >"+routingKey);
				 String contentType = properties.getContentType();
				 System.out.println("contentType >"+contentType);
				 long deliveryTag = envelope.getDeliveryTag();
				 System.out.println("deliveryTag >"+deliveryTag);
				 System.out.println("content:"+new String(body,"UTF-8"));
				 // (process the message components here ...)
				 //消息处理完后，进行答复。答复过的消息，服务器就不会再次转发。
				 //没有答复过的消息，服务器会一直不停转发。
//				 channel.basicAck(deliveryTag, false);
			}
		};
		
		channel.basicConsume(queueName,true, myconsumer);
		
//		channel.close();
//		connection.close();
	}
}
