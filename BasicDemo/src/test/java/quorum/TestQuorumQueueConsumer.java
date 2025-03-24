package quorum;

import com.rabbitmq.client.*;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @author chenxuegui
 * @since 2025/3/21
 */
public class TestQuorumQueueConsumer {
    static String queue =  "quorum-can-dead";

    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(1);//预处理能力
        channel.basicConsume(queue,false,new DefaultConsumer(channel){ /* 手动ACK -->消息至少消费一次 */

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("------------------------");
                System.out.println(consumerTag);
                System.out.println(envelope);
                System.out.println(properties);
                System.out.println(new String(body));

                /* 消费者异常，消息者将不再收到新消息*/ //--跟spring框架封装的消费者不同
                throw new NullPointerException();
            }

        });
    }
}
