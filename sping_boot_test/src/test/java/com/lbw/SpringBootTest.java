package com.lbw;

import com.lbw.domain.Student;
import com.lbw.domain.Teacher;
import com.lbw.domain.repository.StudentRepository;
import com.lbw.domain.repository.TeacherRepository;
import com.lbw.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/19 14:09
 * @description：TODO
 */
@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootTest {
    private StudentService studentService;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Test
    public void t1() {
        Teacher teacher = teacherRepository.getOne(1L);
        Student student = new Student();
        student.setName("小明");
        student.setAge("15");
        student.setIdcard("123456");
        student.setTeacher(teacher);
        studentRepository.save(student);
    }

    @Test
    public void t2() {
        Student student = new Student();
        student.setName("小汪");
        student.setAge("15");
        student.setIdcard("1234156");
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("小王1aaa");
        teacher.setAge("123465");
        student.setTeacher(teacher);
        studentRepository.save(student);
        Optional<Teacher> optional = teacherRepository.findById(1L);
        Teacher teacher2 = optional.get();
        teacher2.setAge("11111");
        teacherRepository.save(teacher2);


    }

    @Test
    public void t3() {

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("小王1");
        teacher.setAge("123465");
        teacher.setStudent(null);

        teacherRepository.save(teacher);
    }

    @Test
    public void t4() {

    }

    public static void invokeShell() {
//方法1 执行字符串命令（各个参数1234之间需要有空格）
        String path = "sh ps-ef";
//方法2 在单独的进程中执行指定命令和变量。
//第一个变量是sh命令，第二个变量是需要执行的脚本路径，从第三个变量开始是我们要传到脚本里的参数。
//        String[] paths=new String[]{"sh","/root/zpy/zpy.sh","1","2","3","4"};
        try {
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(path);
            int status = pro.waitFor();
            if (status != 0) {
                System.out.println("Failed to call shell's command");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                strbr.append(line).append("\n");
            }

            String result = strbr.toString();
            System.out.println(result);

        } catch (IOException ec) {
            ec.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();

        }
    }

}
