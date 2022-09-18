package lbw.conteoller;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/11/17 11:09
 * @description：TODO
 */
@RestController
@RequestMapping("api/test")
public class TestController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("get")
    public Object get(String message) {
        rocketMQTemplate.convertAndSend("first-topic", "你好,Java旅途" + message);
        return "ok";
    }

    /**
     * 异步
     *
     * @param message
     * @return
     */
    @GetMapping("async")
    public Object async(String message) {
        rocketMQTemplate.asyncSend("first-topic", "你好,Java旅途" + message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消费成功" + sendResult);
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("消费失败" + e.getMessage());
            }
        });
        return "ok";
    }

    /**
     * 同步
     *
     * @param message
     * @return
     */
    @GetMapping("sync")
    public Object sync(String message) {
        rocketMQTemplate.syncSend("first-topic", "你好,Java旅途" + message);
        return "ok";
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("code", "123");
        Object o = Optional.ofNullable(map)
                .map(m -> m.get("code"))
                .map(Object::toString)
                .orElse(null);
        System.out.println(o);
    }
}
