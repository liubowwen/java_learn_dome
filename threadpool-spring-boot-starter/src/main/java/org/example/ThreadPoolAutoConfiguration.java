package org.example;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：lbw
 * @date ：Created in 2022/6/21 15:05
 * @description：TODO
 */
@Configuration
public class ThreadPoolAutoConfiguration {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ThreadPoolAutoConfiguration.class);

    @Bean
    @ConditionalOnClass(ThreadPoolExecutor.class)
    public ThreadPoolExecutor MyThreadPool() {
        LOGGER.info("init  MyThreadPool");
        return new ThreadPoolExecutor(90, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));


    }
}
