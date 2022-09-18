package com.nat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author ：lbw
 * @date ：Created in 2022/6/27 17:15
 * @description：TODO
 */
@SpringBootApplication
@EnableAsync
public class NatMain {
    public static void main(String[] args) {
        SpringApplication.run(NatMain.class, args);
    }

}
