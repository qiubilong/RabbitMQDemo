package quorum;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.roy.rabbitmq.Message;
import com.roy.rabbitmq.RabbitMQUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxuegui
 * @since 2025/3/21
 */
public class TestQuorumQueueProductor {

    static String queue =  "quorum-can-dead";

    public static void main(String[] args) throws Exception{

        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> queueParams = new HashMap<>();
        queueParams.put("x-queue-type","quorum");
        queueParams.put("x-dead-letter-exchange","deadLetterExchange");
        queueParams.put("x-delivery-limit",3);/* 处理失败时，额外重新入队次数 -->防止重新消费死循环 */
        channel.queueDeclare(queue,true,false,false,queueParams);

        for (int i = 0; i < 5; i++) {

            String message = new Message("gg"+i).toString();
            //无交换机填"", routingKey=队列名
            channel.basicPublish("",queue,null,message.getBytes(StandardCharsets.UTF_8));
        }

        Thread.sleep(500);

        channel.close();
        connection.close();
    }
}
