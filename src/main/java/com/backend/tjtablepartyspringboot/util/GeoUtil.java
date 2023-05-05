package com.backend.tjtablepartyspringboot.util;

public class GeoUtil {
    public static float getDistance(float longitude1, float latitude1, float longitude2, float latitude2){
        return (float) Math.sqrt(Math.pow(longitude1-longitude2, 2) + Math.pow(latitude1-latitude2, 2));
    }
}
