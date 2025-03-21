package classic;

import com.rabbitmq.client.*;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;

/**
 * @author chenxuegui
 * @since 2025/3/19
 */
public class TestQueueMaxConsumer1 {


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

               channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });




    }
}
