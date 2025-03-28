package com.roy.rabbitmq.pubsub交换机类型;

import java.io.IOException;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.roy.rabbitmq.RabbitMQUtil;

public class DirectReceiveLogs {

    private static final String EXCHANGE_NAME = "directExchange";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//        String queueName = channel.queueDeclare().getQueue();
        String queueName="direct_queue";
        channel.queueDeclare(queueName,false,false,false,null);

        /* 使用routingKey绑定 交换机和队列 */
        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        channel.queueBind(queueName, EXCHANGE_NAME, "debug");

        Consumer myconsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("========================");
                String routingKey = envelope.getRoutingKey();
                System.out.println("routingKey >" + routingKey);
                String contentType = properties.getContentType();
                System.out.println("contentType >" + contentType);
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("deliveryTag >" + deliveryTag);
                System.out.println("content:" + new String(body, "UTF-8"));
                // (process the message components here ...)
                //消息处理完后，进行答复。答复过的消息，服务器就不会再次转发。
                //没有答复过的消息，服务器会一直不停转发。
//				 channel.basicAck(deliveryTag, false);
            }
        };
        channel.basicConsume(queueName, true, myconsumer);
    }
}
