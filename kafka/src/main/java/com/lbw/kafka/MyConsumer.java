package com.lbw.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Arrays;
import java.util.Properties;

public class MyConsumer{
 
    private final static String TOPIC = "mykafka";
    private final static String KAFKA_SERVER_URL = "10.200.0.102";
    private final static String KAFKA_SERVER_PORT = "9092";
    public static final String HOST_NAME = KAFKA_SERVER_URL;

    public static KafkaConsumer<Integer, String> getConsumer() {
        KafkaConsumer<Integer, String> consumer;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put("host.name",HOST_NAME);
 
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<Integer, String>(props);
        return consumer;
    }
 
    public static void main(String[] args) {
        KafkaConsumer<Integer, String> consumer = getConsumer();
        consumer.subscribe(Arrays.asList(TOPIC));
        ConsumerRecords<Integer, String> records ;
        records = consumer.poll(1);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
        }
    }
}
