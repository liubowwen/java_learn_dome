package lbw.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ：lbw
 * @date ：Created in 2022/4/20 17:10
 * @description：TODO
 */
@Component
@RocketMQMessageListener(topic = "first-topic",consumerGroup = "my-consumer-group")
public class Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }
}
