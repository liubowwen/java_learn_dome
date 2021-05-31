package com.lbw.kafka;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class Consumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        Properties props = new Properties();
        props.put("bootstrap.servers", "broker1:9092, broker2:9092");
// group.id，指定了消费者所属群组
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer", "org.apache.kafka.common.serializaiton.StrignDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serializaiton.StrignDeserializer");

        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<String, String>(props);



        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("duanjt_test"));// 订阅消息

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic:%s,offset:%d,消息:%s", //
                        record.topic(), record.offset(), record.value()));
            }
        }
    }
}
