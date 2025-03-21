package quorum.延迟队列;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.roy.rabbitmq.Message;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author chenxuegui
 * @since 2025/3/21
 */
public class TestDelayQueueProductor {
    static String queue = "delayQueueOrigin";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleAck"+deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleNack"+deliveryTag);

            }
        });

        for (int i = 0; i < 3; i++) {
            String message = new Message("ddd"+i).toString();
            channel.basicPublish("",queue, null,message.getBytes(StandardCharsets.UTF_8));
            Thread.sleep(3000);
        }



        Thread.sleep(5000);
        channel.close();
        connection.close();
    }
}
