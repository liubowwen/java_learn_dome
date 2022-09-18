package com.lbw.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：com.lbw
 * @date ：Created in 2022/1/13 11:00
 * @description：TODO
 */
public class ExcelDome {
    public static void main(String args[]) {
        // 文件输出位置
        String fileName = "C:/Users/85422/Desktop/测试.xlsx";
        String templateFileName="C:/Users/85422/Desktop/密接1.xlsx";
        ExcelWriterBuilder builder = EasyExcel.write(fileName);
        List<Object> data = new ArrayList<>();
        List<List<String>> header = head(data);
        System.out.println(data.toString());
        List<List<Object>> list = new ArrayList<>();
        list.add(data);
        builder.head(header).sheet("模板").doWrite(list);
        builder.head(header).sheet("模板").doWrite(list);

    }

    /**
     * 返回的数据结构如下
     * {"data":{"xxxxxxx":{"":32,"1011":42,"1010":8,"1008":52,"0006":3,"0004":86,"1004":2,"0013":7,"0002":7,"1003":4,"0003":56,"1002":18,"0011":13,"1001":12,"0012":6,"0001":66,"1009":42}},
     * "deviceTypeSet":{"switching":["1008","1004","1003","1002","1001","1011","1010"],
     * "analog":["0006","0004","0013","0002","0003","0011","0012","0001"],
     * "switchingOff":["1009"],"substation":[""]},
     * "success":true}
     *
     * @return
     */
    private static List<List<String>> head(List<Object> data) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("序号");
        list.add(head0);
        data.add("1");
        List<String> head1 = new ArrayList<String>();
        head1.add("市区");
        list.add(head1);
        data.add("高新区");
        Map<String, List<String>> map = getHeader();
        Map<String, Long> dataMap = getData();
        map.forEach((k, v) -> {
            String deviceCategory = k;
            List<String> ls = v;
            ls.forEach(e -> {
                List<String> head = new ArrayList<>();
                head.add(deviceCategory);
                head.add(e);
                list.add(head);
                if (dataMap.containsKey(e)) {
                    data.add(dataMap.get(e));
                } else {
                    data.add(0);
                }
            });

        });
        List<String> headn = new ArrayList<String>();
        headn.add("合计");
        list.add(headn);
        data.add(dataMap.get("合计"));
        return list;
    }

    private static Map<String, Long> getData() {
        //{"10017904-1":
        //{"":32,"1011":42,"1010":8,"1008":52,"0006":3,"0004":86,"1004":2,"0013":7,"0002":7,"1003":4,"0003":56,"1002":18,"0011":13,"1001":12,"0012":6,"0001":66,"1009":42}},
        Map<String, Long> data = new HashMap<>();
        long atotal = 0;
        long stotal = 0;
        long subtotal = 0;
        for (int i = 0; i < 10; i++) {
            String column = "温度" + i;
            atotal += Math.round(Math.random()) + i;
            data.put(column, Math.round(Math.random()) + i);
            String scolumn = "风门" + i;
            stotal += Math.round(Math.random()) + i;
            data.put(scolumn, Math.round(Math.random()) + i);
        }
        data.put("小计1", atotal);
        data.put("小计2", stotal);
        String subColumn = "其它";
        data.put(subColumn, 55L);
        data.put("小计3", 55L);
        data.put("合计", 55 + atotal + stotal);
        return data;
    }

    private static Map<String, List<String>> getHeader() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> aList = new ArrayList<>();
        List<String> sList = new ArrayList<>();
        List<String> subList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String column = "温度" + i;
            aList.add(column);
            String scolumn = "风门" + i;
            sList.add(scolumn);
        }
        aList.add("小计1");
        sList.add("小计2");
        String subColumn = "其它";
        subList.add(subColumn);
        subList.add("小计3");
        map.put("密接", aList);
        map.put("次密接", sList);
        return map;
    }



}
