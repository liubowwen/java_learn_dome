package com.nat.service;

import com.nat.domain.Student;
import com.nat.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ：lbw
 * @date ：Created in 2022/6/27 17:18
 * @description：TODO
 */
@Service
public class StudentService {
    @Resource
    private StudentRepository studentRepository;
    @Transactional(rollbackFor = Exception.class)
    public void  updateService(Long id){
        Student student = studentRepository.findAllById(id);

        student.setAge("999");
    }


}
