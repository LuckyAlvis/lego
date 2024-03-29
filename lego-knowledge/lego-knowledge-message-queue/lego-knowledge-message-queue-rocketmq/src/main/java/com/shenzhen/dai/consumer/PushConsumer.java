package com.shenzhen.dai.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * 推模式消费消息
 */
public class PushConsumer {
    public static void main(String[] args) throws MQClientException {
        // 初始化一个DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("SyncProducer");
        // 设置NameServer地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅一个Topic，可以使用Tag来过滤需要消费的消息，这里暂时不做过滤
        consumer.subscribe("TopicTestAAA", "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            // 打印消息
            list.forEach(e -> System.out.println(new String(e.getBody())));
            // 返回消费状态
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动consumer
        consumer.start();
        // 等待消费结束
        System.out.println("Consumer Started.");
    }
}
