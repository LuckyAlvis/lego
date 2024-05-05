package com.shenzhen.dai.consumer;

import com.shenzhen.dai.producer.OrderProducer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 顺序消费消息
 *
 * @see OrderProducer
 */
public class OrderConsumer {
    public static void main(String[] args) throws MQClientException {
        // 初始化一个DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("SyncProducer");
        // 设置NameServer地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅一个Topic，可以使用Tag来过滤需要消费的消息，这里暂时不做过滤
        consumer.subscribe("OrderTopic", "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.setMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
            for (MessageExt msg : list) {
                System.out.println("队列id = " + msg.getQueueId() + "【" + "线程名称 = " + Thread.currentThread().getName() + "】" + new String(msg.getBody()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        /*
        队列id = 0【线程名称 = ConsumeMessageThread_SyncProducer_8】数据0|{"order":1,"step":"步骤1"}
        队列id = 0【线程名称 = ConsumeMessageThread_SyncProducer_9】数据0|{"order":2,"step":"步骤2"}
        队列id = 0【线程名称 = ConsumeMessageThread_SyncProducer_9】数据0|{"order":3,"step":"步骤3"}
        队列id = 1【线程名称 = ConsumeMessageThread_SyncProducer_10】数据1|{"order":1,"step":"步骤1"}
        队列id = 1【线程名称 = ConsumeMessageThread_SyncProducer_12】数据1|{"order":2,"step":"步骤2"}
        队列id = 1【线程名称 = ConsumeMessageThread_SyncProducer_12】数据1|{"order":3,"step":"步骤3"}
        队列id = 2【线程名称 = ConsumeMessageThread_SyncProducer_11】数据2|{"order":1,"step":"步骤1"}
        队列id = 2【线程名称 = ConsumeMessageThread_SyncProducer_11】数据2|{"order":2,"step":"步骤2"}
        队列id = 2【线程名称 = ConsumeMessageThread_SyncProducer_13】数据2|{"order":3,"step":"步骤3"}
        队列id = 3【线程名称 = ConsumeMessageThread_SyncProducer_15】数据3|{"order":1,"step":"步骤1"}
        队列id = 3【线程名称 = ConsumeMessageThread_SyncProducer_15】数据3|{"order":2,"step":"步骤2"}
        队列id = 3【线程名称 = ConsumeMessageThread_SyncProducer_14】数据3|{"order":3,"step":"步骤3"}
         */
        // 启动consumer
        consumer.start();
        // 等待消费结束
        System.out.println("Consumer Started.");
    }
}
