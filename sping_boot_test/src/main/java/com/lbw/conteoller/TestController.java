package com.lbw.conteoller;

import com.lbw.service.StudentService;
import com.lbw.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/11/17 11:09
 * @description：TODO
 */
@RestController
@RequestMapping("api/test")
public class TestController {
    private TeacherService teacherService;
    private StudentService studentService;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping("add")
    public String t(){
        studentService.addStudent1();
      return "ok";
    }

    @GetMapping("get")
    public Object get(){
        return studentService.getAll();
    }
}
