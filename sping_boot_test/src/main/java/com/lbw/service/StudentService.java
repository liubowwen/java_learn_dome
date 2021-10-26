package com.lbw.service;

import com.lbw.domain.Student;
import com.lbw.domain.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/19 14:03
 * @description：TODO
 */
@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void add(Student student) {
        studentRepository.save(student);
    }

    public void update(Student student) {
//        Student oleStudent = studentRepository.findAllById(student.getId());
//        BeanUtil.copyProperties(student, oleStudent, CopyOptions.create().setIgnoreNullValue(true));
        studentRepository.save(student);
    }



}
