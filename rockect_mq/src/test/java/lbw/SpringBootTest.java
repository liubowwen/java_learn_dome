package lbw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author ：com.lbw
 * @date ：Created in 2021/8/19 14:09
 * @description：TODO
 */
@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class SpringBootTest {
   @Autowired
   private ThreadPoolExecutor myThreadPool;

    @Test
    public void t1() {
        System.out.printf(""+ myThreadPool.getCorePoolSize());


    }


}
