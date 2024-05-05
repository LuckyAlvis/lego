package com.shenzhen.dai.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 单向发送
 */
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        // 初始化一个DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer("OneWayProducer");
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定Topic、Tag和消息体
        Message message = new Message();
        message.setTopic("TopicTestAAA");
        message.setBody("Hello RocketMQ, This is a one way message.".getBytes());
        // 发送单向消息，没有任何返回结果
        producer.sendOneway(message);
        // 关闭producer
        producer.shutdown();
    }
}
