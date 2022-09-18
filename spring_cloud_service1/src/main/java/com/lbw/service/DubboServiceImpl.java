package com.lbw.service;


import com.lbw.domain.Student;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author ：com.lbw
 * @date ：Created in 2022/3/6 14:07
 * @description：TODO
 */
@Service(group ="service1")
public class DubboServiceImpl implements DubboService {


    @Override
    public Student create(Student student) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        System.out.println(requestAttributes.getSessionId());
        System.out.println("服务2");
        student.setId(1L);
        return student;
    }
}
