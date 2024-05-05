package com.shenzhen.dai.producer;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 顺序消息
 */
@Data
public class OrderProducer {

    @Data
    @AllArgsConstructor
    public static class OrderStep {
        private Integer order;
        private String step;
    }

    private static Map<Integer, List<OrderStep>> initOrderSteps() {
        Map<Integer, List<OrderStep>> userMap = new HashMap<>();
        List<OrderStep> orderStepList = new ArrayList<>();
        orderStepList.add(new OrderStep(1, "步骤1"));
        orderStepList.add(new OrderStep(2, "步骤2"));
        orderStepList.add(new OrderStep(3, "步骤3"));
        // 模拟四组数据
        for (int i = 0; i < 4; i++) {
            userMap.put(i, orderStepList);
        }
        return userMap;
    }

    public static void main(String[] args) throws Exception {
        // 初始化一个DefaultMQProducer
        DefaultMQProducer producer = new DefaultMQProducer("SyncProducer");
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动producer
        producer.start();
        // 创建消息对象，指定Topic、Tag和消息体
        for (Map.Entry<Integer, List<OrderStep>> entry : initOrderSteps().entrySet()) {
            for (OrderStep orderStep : entry.getValue()) {
                Message message = new Message("OrderTopic", ("数据" + entry.getKey() + "|" + JSONObject.toJSONString(orderStep)).getBytes(StandardCharsets.UTF_8));
                // 发送消息并获取发送结果
                SendResult send = producer.send(message, new MessageQueueSelector() {
                    /**
                     *
                     * @param list      所有消息队列
                     * @param message   传入的消息
                     * @param o         这个就是放进去的消息标识 Object arg，即entry.getKey()
                     * @return
                     */
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        Integer key = (Integer) o;
                        int index = key % list.size();
                        System.out.println("队列idx=" + index);
                        return list.get(index);
                    }
                }, entry.getKey());
                System.out.println("send = " + send + "msg = " + entry.getKey() + orderStep);
            }
        }
        // 关闭producer
        producer.shutdown();

    }
}
