import com.rabbitmq.client.*;
import com.roy.rabbitmq.Message;
import com.roy.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxuegui
 * @since 2025/3/19
 */
public class TestQueueMaxProduct {

    public static void main(String[] args) throws Exception{


        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                /* deliveryTag 信道channel内是唯一的，deliveryTag 仅在当前信道有效，信道关闭后不再有意义*/
                System.out.println("handleAck="+deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleNack="+deliveryTag);

            }
        });


        Map<String, Object> queueParams = new HashMap<>();
        queueParams.put("x-max-length",3);
        queueParams.put("x-overflow","reject-publish");
        channel.queueDeclare("queue-max",true,false,false,queueParams);
        for (int i = 0; i < 10; i++) {
            AMQP.BasicProperties props = new AMQP.BasicProperties();
            String message = new Message("test"+i).toString();
            channel.basicPublish("","queue-max", props,message.getBytes(StandardCharsets.UTF_8));
        }

        Thread.sleep(500);

        channel.close();
        connection.close();
    }
}
