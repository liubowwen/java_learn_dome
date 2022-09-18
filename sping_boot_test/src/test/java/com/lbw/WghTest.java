package com.lbw;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.lbw.domain.Area;
import com.lbw.domain.AreaKaka;
import com.lbw.domain.Grid;
import com.lbw.domain.repository.AreaKakaRepository;
import com.lbw.domain.repository.AreaRepository;
import com.lbw.domain.repository.GridRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/12/28 9:49
 * @description：TODO
 */
@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class WghTest {
    @Autowired
    private AreaKakaRepository areaKakaRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private GridRepository gridRepository;

    @Test
    public void test() {
        List<AreaKaka> areaKakas = areaKakaRepository.findAllByCodeLikeAndIsGrid("370682%", 0);
        for (AreaKaka areaKaka : areaKakas) {
            findParent(areaKaka);
        }
    }

    public void findParent(AreaKaka areaKaka) {
        AreaKaka areaKakaParent = areaKakaRepository.findFirstById(areaKaka.getParentId());
        if (areaKaka.getCode().equals("370682")) {
            return;
        }
        Area areaParent = areaRepository.findByCode(areaKakaParent.getCode());
        //存在父级 就直接保存
        if (areaParent != null) {

            Area area = new Area();
            area.setCode(areaKaka.getCode());
            area.setAddrType(areaKaka.getAreaLevel());
            area.setParent(areaParent);
            area.setName(areaKaka.getName());
            area.setFullName(areaParent.getName() + area.getName());
            areaRepository.save(area);
        } else { //不存在父级先保存父级
            findParent(areaKakaParent);
        }
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/85422/Desktop/莱阳网格WKT.xls");
        ExcelReader reader = ExcelUtil.getReader(file);
        List<Map<String, Object>> lists = reader.read(0, 1, Integer.MAX_VALUE);
        System.out.println(lists);

    }

    @Test
    public void addGrid() throws Exception {
        File file = new File("C:/Users/85422/Desktop/莱阳网格数据.xlsx");
        ExcelReader reader = ExcelUtil.getReader(file);
        List<Map<String, Object>> lists = reader.read(0, 1, Integer.MAX_VALUE);
        for (Map<String, Object> map : lists) {
            try {
                Grid grid = new Grid();
                String code = map.get("code").toString();
                grid.setCode(code);
                String regionCd = code.substring(0, code.length() - 3);
                Area byCode = areaRepository.findByCode(regionCd);
                if (byCode == null) {
                    throw new IllegalStateException(regionCd + "不存在");
                }
                grid.setRegionCd(regionCd);
                grid.setAreaName(map.get("name").toString());
                grid.setSname(map.get("name").toString());
                String color= getColor();
                grid.setColor(color);
                grid.setType(2);
                grid.setCoverType(1);
                grid.setIsDelete(0);

                if (map.get("m") == null) {
                    System.out.println(JSON.toJSONString(map));
                    continue;
                }
//                List<List<WKTUtility.GeoPoint>> listList = WKTUtility.wktMultiPolygonToGeoPointList(map.get("m").toString(), 0, 2);
                grid.setPoints(map.get("m").toString());
                grid.setIntro(grid.getName());
                gridRepository.save(grid);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(map.get("code").toString());
            }

        }
    }

    public String getColor() {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length() == 1 ? "0" + red : red;
        //判断绿色代码的位数
        green = green.length() == 1 ? "0" + green : green;
        //判断蓝色代码的位数
        blue = blue.length() == 1 ? "0" + blue : blue;
        //生成十六进制颜色值
        String color = "#" + red + green + blue;
        return color;
    }


}
