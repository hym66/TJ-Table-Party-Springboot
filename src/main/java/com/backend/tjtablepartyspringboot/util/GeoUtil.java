package com.backend.tjtablepartyspringboot.util;

public class GeoUtil {
    private static final double EARTH_RADIUS = 6371; // 地球半径，单位km

    /**
     * Author: hym
     * Description: 获取地图上两点间距离
     * return: 两点间距离，单位为km
     */
    public static float getDistance(float lat1, float lon1, float lat2, float lon2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lon1) - Math.toRadians(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s *= EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000; // 精确到小数点后4位
        return (float) s;
    }
}
