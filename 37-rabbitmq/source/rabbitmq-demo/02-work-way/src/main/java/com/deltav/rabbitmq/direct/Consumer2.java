package com.deltav.rabbitmq.direct;

import com.deltav.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws Exception {

        Connection connection = RabbitMqUtil.getConnection();

        Channel channel = connection.createChannel();

        String queue1Name = "test_direct_queue2";

        channel.queueDeclare(queue1Name, true, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("body：" + new String(body));
                System.out.println("Consumer2 将日志信息打印到控制台.....");

            }

        };

        channel.basicConsume(queue1Name, true, consumer);

    }
}
