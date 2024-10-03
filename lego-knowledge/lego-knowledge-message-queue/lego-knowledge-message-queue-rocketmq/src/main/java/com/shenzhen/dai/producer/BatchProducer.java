package com.shenzhen.dai.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量发送
 */
public class BatchProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 初始化一个DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer("SyncProducer");
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();
        List<Message> messageList = new ArrayList<>();
        // 创建消息对象，指定Topic、Tag和消息体
        Message messageA = new Message();
        messageA.setTopic("BatchTopic");
        messageA.setBody("Hello RocketMQ, This is a batch messageA.".getBytes(StandardCharsets.UTF_8));
        Message messageB = new Message();
        messageB.setTopic("BatchTopic");
        messageB.setBody("Hello RocketMQ, This is a batch messageB.".getBytes(StandardCharsets.UTF_8));
        messageList.add(messageA);
        messageList.add(messageB);
        // 发送消息并获取发送结果
        SendResult send = producer.send(messageList);
        System.out.println("send = " + send);
        // 关闭producer
        producer.shutdown();


    }
}
