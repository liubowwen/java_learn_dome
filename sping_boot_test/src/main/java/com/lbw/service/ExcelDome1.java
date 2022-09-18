package com.lbw.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：com.lbw
 * @date ：Created in 2022/1/13 11:00
 * @description：TODO
 */
public class ExcelDome1 {
    public static void main(String args[]) {
        // 文件输出位置
        String fileName = "C:/Users/85422/Desktop/测试.xlsx";
        ExcelWriterBuilder builder = EasyExcel.write(fileName);
        List<Object> data = new ArrayList<>();
        List<List<String>> header = head(Lists.newArrayList("城市1", "城市2", "城市3", "城市4", "城市5","城市1", "城市2", "城市3", "城市4", "城市5"));
        System.out.println(data.toString());
        List<List<Object>> list = new ArrayList<>();
        list.add(data);
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setShrinkToFit(true);
        // 背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HorizontalCellStyleStrategy myExcelFillCellMergeStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        builder.head(header).registerWriteHandler(myExcelFillCellMergeStrategy)
                .registerWriteHandler(new MyExcelFillCellMergeStrategy())
                .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)25,(short)25))
                .sheet("模板").doWrite(contentData());

    }

    private static List<List<String>> head(List<String> cityName) {
        List<List<String>> list = new ArrayList<List<String>>();
        String title = "密接、次密接情况统计表(截至2022年01月11日12时)";
        String company = "单位：\u202D\u202D\u202D\u202D";
        String person = "填表人：\u202D\u202D\u202D\u202D";
        String phone = "联系电话：\u202D\u202D\u202D\u202D";
        String lineTime = "截至日期：\u202D\u202D\u202D\u202D";
        String date = "日期:\u202D\u202D\u202D\u202D";
        //二级目录
        List<String> averageHeaders = Lists.newArrayList(company, person, phone, lineTime, date);
        //城市数量
        Integer size = cityName.size();
        //计算五个标题平均分
        Integer average = (size + 2) / averageHeaders.size();
        int i = 2;
        int index = 0;
        list.add(Lists.newArrayList(title, averageHeaders.get(0), "序号"));
        list.add(Lists.newArrayList(title, averageHeaders.get(0), "市区"));
        for (int j = 0; j < cityName.size(); j++) {
            String tertiaryHeader;
            if (cityName.size() / 2 > j) {
                tertiaryHeader = "密接";
            } else {
                tertiaryHeader = "次密接";
            }
            if (average > i) {
                list.add(Lists.newArrayList(title, averageHeaders.get(index), tertiaryHeader, cityName.get(j)));
                i++;
            } else {
                index++;
                if (index>=averageHeaders.size()){
                    index--;
                }
                i = 1;
                list.add(Lists.newArrayList(title, averageHeaders.get(index), tertiaryHeader, cityName.get(j)));
            }
        }

//        list.add(Lists.newArrayList(title, company, "序号"));
//        list.add(Lists.newArrayList(title, company, "区市"));
//        list.add(Lists.newArrayList(title, company, "密接"));
//        list.add(Lists.newArrayList(title, company, "密接", "济宁"));
//        list.add(Lists.newArrayList(title, company, "密接", "烟台"));
//        list.add(Lists.newArrayList(title, company, "密接", "济南"));
//        list.add(Lists.newArrayList(title, person));
//        list.add(Lists.newArrayList(title, phone));
//        list.add(Lists.newArrayList(title, date));
        return list;
    }

    private  static List<Map<String, Long>> getData() {
        List<Map<String, Long>> list=new ArrayList<>();
        //{"10017904-1":
        //{"":32,"1011":42,"1010":8,"1008":52,"0006":3,"0004":86,"1004":2,"0013":7,"0002":7,"1003":4,"0003":56,"1002":18,"0011":13,"1001":12,"0012":6,"0001":66,"1009":42}},
        Map<String, Long> data = new HashMap<>();
        data.put("城市1", 1L);
        data.put("城市2", 2L);
        data.put("城市3", 3L);
        data.put("城市4", 4L);
        data.put("城市5", 5L);
        list.add(data);
        return list;
    }

    private static List <List<Object>> contentData(){
        List<List<Object>> contentList = Lists.newArrayList();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        contentList.add( Lists.newArrayList("测试", "商品A","苹果🍎") );
        contentList.add( Lists.newArrayList("测试", "商品B","橙子🍊") );
        return contentList;
    }



}
