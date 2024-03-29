package com.shenzhen.dai.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashSet;
import java.util.Set;

/**
 * 拉模式消费消息
 */
public class PullConsumer {
    public static void main(String[] args) throws MQClientException {
        // 初始化一个DefaultMQPullConsumer
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PullConsumer");
        // 设置NameServer地址
        consumer.setNamesrvAddr("localhost:9876");
        Set<String> topics = new HashSet<>();
        topics.add("TopicTestAAA");
        consumer.setRegisterTopics(topics);
        consumer.getRegisterTopics().forEach(n ->{
            try {
                System.out.println("n = " + n);
                Set<MessageQueue> messageQueueSet = consumer.fetchSubscribeMessageQueues(n);
                System.out.println(messageQueueSet);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        });
        // 启动consumer
        consumer.start();
    }
}
