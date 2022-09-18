package com.lbw.controller;

import com.lbw.domain.Student;
import com.lbw.service.DubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class HelloController {
    @Reference(group = "service1")
    DubboService dubboService;

    @GetMapping("/hello")
    public Student hello() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        System.out.println(requestAttributes.getSessionId());
        Student student = new Student();
        student.setName("张三");
        student.setAddress("山东烟台");
        student.setAge("20");
        student.setIdCard("3707800000");
        return dubboService.create(student);
    }
}