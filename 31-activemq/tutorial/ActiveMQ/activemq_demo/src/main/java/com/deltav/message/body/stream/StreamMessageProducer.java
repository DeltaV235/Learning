package com.deltav.message.body.stream;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.nio.charset.StandardCharsets;

/**
 * StreamMessage Producer 测试类
 *
 * @author DeltaV235
 * @version 1.0
 * @date 2021/3/6 23:20
 */
public class StreamMessageProducer {
    private static final String ACTIVE_MQ_URL = "tcp://192.168.2.200:61616";
    private static final String QUEUE_NAME = "stream_queue";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_MQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);

        for (int i = 0; i < 3; i++) {
            StreamMessage streamMessage = session.createStreamMessage();
            streamMessage.writeBytes((i + " -> test String").getBytes(StandardCharsets.UTF_8));

            producer.send(streamMessage);
        }

        producer.close();
        session.close();
        connection.close();
    }
}
