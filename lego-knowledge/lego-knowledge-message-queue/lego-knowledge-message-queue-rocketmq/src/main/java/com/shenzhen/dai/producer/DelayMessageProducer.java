package com.shenzhen.dai.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 同步发送
 */
public class DelayMessageProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 初始化一个DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer("SyncProducer");
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定Topic、Tag和消息体
        Message message = new Message();
        message.setTopic("DelayTopic");
        message.setDelayTimeLevel(2);
        message.setBody("Hello RocketMQ, This is a delay message.".getBytes(StandardCharsets.UTF_8));
        // 发送消息并获取发送结果
        SendResult send = producer.send(message);
        System.out.println("send = " + send);
        // 关闭producer
        producer.shutdown();


    }
}
