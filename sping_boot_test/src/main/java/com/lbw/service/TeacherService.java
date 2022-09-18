package com.lbw.service;

import com.lbw.domain.Teacher;
import com.lbw.domain.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author ：com.lbw
 * @date ：Created in 2021/11/17 11:08
 * @description：TODO
 */
@Service
public class TeacherService {
    @Resource
    private TeacherRepository teacherRepository;


    public Teacher getTeacher() {
        Teacher teacher = teacherRepository.findFirstById(2L);
        teacher.setAge("2111");
        teacher.setName("221");
        return teacher;
    }


}
