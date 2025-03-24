package quorum;

import com.rabbitmq.client.*;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @author chenxuegui
 * @since 2025/3/21
 */
public class TestQuorumQueueConsumer_autoAck {
    static String queue =  "quorum-can-dead";

    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(1);//预处理能力
        channel.basicConsume(queue,true, new DefaultConsumer(channel){ /* 自动ACK --> 最多消费一次 */

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("------------------------");
                System.out.println(consumerTag);
                System.out.println(envelope);
                System.out.println(properties);
                System.out.println(new String(body));

                throw new NullPointerException(); /* 自动ACK，消息投递时就ack，不管消费者有没异常，消息不会再重新投递（spring框架下的消费者会重新投递） */
            }

        });
    }
}
