package com.nat.domain.repository;

import com.nat.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/8/19 14:01
 * @description：TODO
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findFirstById(Long id);


}
