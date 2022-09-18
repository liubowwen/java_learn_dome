package com.lbw.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：com.lbw
 * @date ：Created in 2022/1/11 15:47
 * @description：TODO
 */
public class ExcelService {


    public static void main(String[] args) {
        ExcelService excelService =new ExcelService();
        // 生成Excel路径
        String fileName = "C:/Users/85422/Desktop/测试.xlsx";
        EasyExcel.write(fileName, User.class).sheet("模板").doWrite(excelService.data());
    }

    public List<User> data() {
        List<User> userList = new ArrayList<>();
        User user;
        for (int i = 1; i <= 10; i++) {
            user = new User();
            user.setName("张三" + i);
            user.setSex("男");
            user.setAge(i);
            user.setCardid("440582xxxx");
            user.setKultur("本科");
            user.setDegree("学士");
            user.setMajor("计算机");
            user.setGraduatetime("2021");
            user.setGrade("17");
            user.setSchool("山东工商学院");
            userList.add(user);
        }
        return userList;
    }
    @Data
    public static class User {
        @ExcelProperty("姓名")
        private String name;

        @ExcelProperty("性别")
        private String sex;

        @ExcelProperty("年龄")
        private Integer age;

        @ExcelProperty("身份证")
        private String cardid;

        @ExcelProperty({"普通高等学校全日制教育", "学历"})
        private String kultur;

        @ExcelProperty({"普通高等学校全日制教育", "学位"})
        private String degree;

        @ExcelProperty({"普通高等学校全日制教育", "学院","专业"})
        private String major;

        @ExcelProperty({"普通高等学校全日制教育", "学院","年级"})
        private String grade;

        @ExcelProperty({"普通高等学校全日制教育", "获得学历时间"})
        private String graduatetime;

        @ExcelProperty({"普通高等学校全日制教育", "毕业院校"})
        private String school;

    }
}
