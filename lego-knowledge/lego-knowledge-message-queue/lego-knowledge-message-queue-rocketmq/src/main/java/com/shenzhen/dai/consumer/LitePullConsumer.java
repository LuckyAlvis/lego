package com.shenzhen.dai.consumer;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * 拉模式消费消息
 */
public class LitePullConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("LitePullConsumer");
        consumer.setNamesrvAddr("192.168.31.68:9876");
        consumer.subscribe("TopicTestAAA", "*");
        consumer.start();
        while (true) {
            consumer.poll(1000)
                    .forEach(e -> System.out.println(new String(e.getBody())));
        }
    }
}
