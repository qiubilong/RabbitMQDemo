package quorum;

import com.rabbitmq.client.*;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @author chenxuegui
 * @since 2025/3/21
 */
public class TestQuorumQueueConsumer1 {
    static String queue =  "quorum-can-dead";

    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(1);//预处理能力
        channel.basicConsume(queue,false,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("------------------------");
                System.out.println(consumerTag);
                System.out.println(envelope);
                System.out.println(properties);
                System.out.println(new String(body));

                /* requeue=false -->不再重新入队 --> 进关联的死信队列  */
                //channel.basicNack(envelope.getDeliveryTag(),false,false);

                //channel.basicNack(envelope.getDeliveryTag(),false,true);//重新入队限制 --delivery-limit"

                //throw new NullPointerException();//消费者无应答会阻塞消息消费
                channel.basicAck(envelope.getDeliveryTag(),false);
            }

        });
    }
}
