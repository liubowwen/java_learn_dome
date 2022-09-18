package com.lbw.Utility;

import java.util.ArrayList;
import java.util.List;

public class CoordinateUtility {
    public static double PI = 3.1415926535897932384626;
    public static double X_PI = PI * 3000.0 / 180.0;
    public static double AXIS = 6378245.0;
    public static double OFFSET = 0.00669342162296594323;


    //region 坐标系转换

    /**
     * WGS84 to 火星坐标系 (GCJ-02)
     *
     * @param lat 纬度
     * @param lon 经度
     * @return GCJ-02坐标
     */
    public static double[] gps84ToGcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - OFFSET * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * 火星坐标系 (GCJ-02) to 84
     *
     * @param lat 纬度
     * @param lon 经度
     * @return Gps84坐标
     */
    public static double[] gcj02ToGps84(double lat, double lon) {
        double[] gps = transform(lat, lon);
        double longitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        return new double[]{latitude, longitude};
    }

    /**
     * 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat 纬度
     * @param lon 经度
     * @return BD-09坐标
     */
    public static double[] gcj02ToBd09(double lat, double lon) {
        double z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * X_PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new double[]{tempLat, tempLon};
    }

    /**
     * 将 BD-09 坐标转换成GCJ-02 坐标
     *
     * @param lat 纬度
     * @param lon 经度
     * @return GCJ-02坐标
     */
    public static double[] bd09ToGcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        return new double[]{tempLat, tempLon};
    }

    /**
     * 将WGS84转为bd09
     *
     * @param lat 纬度
     * @param lon 经度
     * @return BD_09坐标
     */
    public static double[] gps84ToBd09(double lat, double lon) {
        double[] gcj02 = gps84ToGcj02(lat, lon);
        return gcj02ToBd09(gcj02[0], gcj02[1]);
    }

    /**
     * 将bd09转为gps84
     *
     * @param lat 纬度
     * @param lon 经度
     * @return GPS_84坐标
     */
    public static double[] bd09ToGps84(double lat, double lon) {
        double[] gcj02 = bd09ToGcj02(lat, lon);
        double[] gps84 = gcj02ToGps84(gcj02[0], gcj02[1]);
        //保留小数点后六位
        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }

    /**
     * 保留小数点后六位
     *
     * @param num 数字
     * @return 结果
     */
    private static double retain6(double num) {
        String result = String.format("%.6f", num);
        return Double.valueOf(result);
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0
                * PI)) * 2.0 / 3.0;
        return ret;
    }

    private static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - OFFSET * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        return lat < 0.8293 || lat > 55.8271;
    }

    //endregion

    //region 道格拉斯-普克 抽稀

    //1.对曲线的首末点虚连一条直线，求曲线上所有点与直线的距离，并找出最大距离值dMax，用dMax与事先给定的阈值D相比
    //2.若dMax<D，则将这条曲线上的中间点全部舍去；则该直线段作为曲线的近似，该段曲线处理完毕。
    //3.若dMax≥D，保留dMax对应的坐标点，并以该点为界，把曲线分为两部分，对这两部分重复使用该方法，即重复1，2步，直到所有dMax均<D，即完成对曲线的抽稀

    /**
     * 道格拉斯-普克抽稀(Douglas-Peucker Thin)
     *
     * @param points    原始坐标点集合
     * @param threshold 阈值
     * @return 抽稀后的坐标点集合
     */
    public static List<double[]> dpThin(List<double[]> points, double threshold) {
        // 找到最大阈值点，即操作（1）
        double maxH = 0;
        int index = 0;
        int end = points.size();
        for (int i = 1; i < end - 1; i++) {
            double h = distanceToSegment(points.get(i), points.get(0), points.get(end - 1));
            if (h > maxH) {
                maxH = h;
                index = i;
            }
        }
        // 如果存在最大阈值点，就进行递归遍历出所有最大阈值点
        List<double[]> result = new ArrayList<>();
        if (maxH > threshold) {
            List<double[]> leftPoints = new ArrayList<>();// 左曲线
            List<double[]> rightPoints = new ArrayList<>();// 右曲线
            // 分别提取出左曲线和右曲线的坐标点
            for (int i = 0; i < end; i++) {
                if (i <= index) {
                    leftPoints.add(points.get(i));
                    if (i == index)
                        rightPoints.add(points.get(i));
                } else {
                    rightPoints.add(points.get(i));
                }
            }
            // 分别保存两边遍历的结果
            List<double[]> leftResult = dpThin(leftPoints, threshold);
            List<double[]> rightResult = dpThin(rightPoints, threshold);
            // 将两边的结果整合
            rightResult.remove(0);
            leftResult.addAll(rightResult);
            result = leftResult;
        } else {//如果不存在最大阈值点则返回当前遍历的子曲线的起始点
            result.add(points.get(0));
            result.add(points.get(end - 1));
        }
        return result;
    }

    /**
     * 计算点到线所确定的最短直线的距离
     *
     * @param start 直线开始点
     * @param end   直线结束点
     * @param point 坐标点
     * @return 最短距离
     */
    private static double distanceToSegment(double[] start, double[] end, double[] point) {
        double a = Math.abs(distanceToPoint(start, end));
        double b = Math.abs(distanceToPoint(start, point));
        double c = Math.abs(distanceToPoint(end, point));
        double p = (a + b + c) / 2.0;
        double s = Math.sqrt(Math.abs(p * (p - a) * (p - b) * (p - c)));//海伦公式，已知三边求三角形面积
        return s * 2.0 / a;
    }

    /**
     * 计算两个坐标点之间的距离
     *
     * @param point1 点1
     * @param point2 点2
     * @return 距离
     */
    private static double distanceToPoint(double[] point1, double[] point2) {
        double lat1 = point1[0];
        double lat2 = point2[0];
        double lon1 = point1[1];
        double lon2 = point2[1];
        double radLat1 = lat1 * PI / 180.0;
        double radLat2 = lat2 * PI / 180.0;
        double a = radLat1 - radLat2;
        double b = (lon1 * PI / 180.0) - (lon2 * Math.PI / 180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        return s * 6370996.81;
    }


    //endregion


}
