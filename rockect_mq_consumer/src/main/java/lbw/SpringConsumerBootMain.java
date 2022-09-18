package lbw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/8/19 14:09
 * @description：TODO
 */
@SpringBootApplication(scanBasePackages = "lbw.*")
public class SpringConsumerBootMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringConsumerBootMain.class, args);
    }

}
