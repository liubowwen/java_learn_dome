package com.lbw.domain.repository;

import com.lbw.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/19 14:01
 * @description：TODO
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findAllById(Long id);

}
