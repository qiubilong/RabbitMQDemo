import com.rabbitmq.client.*;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @author chenxuegui
 * @since 2025/3/19
 */
public class TestQueueMaxConsumer {


    public static void main(String[] args) throws Exception{

        Connection connection = RabbitMQUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.basicQos(1);
        channel.basicConsume("queue-max",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("------------------------");
                System.out.println(consumerTag);
                System.out.println(envelope);
                System.out.println(properties);
                System.out.println(new String(body));

               // channel.basicAck(envelope.getDeliveryTag(),false);

                /* requeue=true -- 消息重新入队 --> 其他消费者消费 --> 一直消费不了造成死循环 */
                /* requeue=false -- 如果有死信队列，则入死信对了，否则丢弃 */

                /**
                 * multiple：布尔值，决定确认的范围：
                 * false：仅确认 deliveryTag 指定的单条消息。
                 * true：确认 deliveryTag 及其之前的所有未确认消息。
                 */
                channel.basicNack(envelope.getDeliveryTag(),false,true);
            }
        });




    }
}
