package com.nat;

import com.nat.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * @author ：com.lbw
 * @date ：Created in 2021/8/19 14:09
 * @description：TODO
 */
@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class SpringBootTest {
    @Resource
    private StudentService studentService;

    @Test
    public void t1() {
        studentService.updateService(2L);
    }



}
