package com.lbw.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：com.lbw
 * @date ：Created in 2022/1/11 17:12
 * @description：TODO
 */
public class CloseContactService {
    public static void main(String[] args) {
        CloseContactService closeContactService = new CloseContactService();
        // 生成Excel路径
        String fileName = "C:/Users/85422/Desktop/测试.xlsx";
        closeContactService.horizontalFill();

    }

    public void horizontalFill() {
        String templateFileName =
                "C:/Users/85422/Desktop/密接.xlsx";
        String fileName = "C:/Users/85422/Desktop/测试.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        int[] mergeColumnIndex = {0, 1};
        WriteSheet writeSheet1 = EasyExcel.writerSheet().registerWriteHandler(new ExcelFillCellMergeStrategy(2, mergeColumnIndex)).build();

        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        excelWriter.fill(data1(), fillConfig, writeSheet1);
        excelWriter.finish();
    }

    public List data1() {
        List data = new ArrayList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("city", "高新区");
        map.put("count", "11");
        data.add(map);
        data.add(map);
        data.add(map);
        data.add(map);
        return data;

    }

    public List data() {
        List data = new ArrayList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("area", "高新区");
        map.put("type", "在用隔离观察场所");
        map.put("no", "1");
        map.put("name", "烟台");
        map.put("location", "20");
        map.put("totalPlaceNum", "20");
        map.put("useLeftRoomNum", "20");
        map.put("leader", "20");
        map.put("phone", "20");
        map.put("workerNum", "20");
        map.put("supporterNum", "20");
        map.put("mainContactNum", "20");
        map.put("secondaryContactNum", "20");
        map.put("otherPersonNum", "20");
        map.put("totalPersonNum", "20");
        map.put("mainUnusualNum", "20");
        map.put("mainOneNatNum", "20");
        map.put("mainTwoNatNum", "20");
        map.put("mainThreeNatNum", "20");
        map.put("mainFourNatNum", "20");
        map.put("secondaryUnusualNum", "20");
        map.put("secondaryOneNatNum", "20");
        map.put("secondaryTwoNatNum", "20");
        map.put("secondaryThreeNatNum", "20");
        map.put("secondaryFourNatNum", "20");
        map.put("otherUnusualNum", "20");
        map.put("otherOneNatNum", "20");
        map.put("otherTwoNatNum", "20");
        map.put("otherFourNatNum", "20");
        map.put("remark", "20");
        data.add(map);
        data.add(map);
        data.add(map);
        data.add(map);
        data.add(map);
        data.add(map);
        return data;
    }
}
