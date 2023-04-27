package com.backend.tjtablepartyspringboot.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: 黄彦铭
 * date: 2023/4/22
 */
public class TimeUtil {
    /**
     * 获取当前时间戳
     * @return   返回值为java的Timestamp对象，能够对应mysql的datetime类型
     */
    public static String getDateFormat(Date timestamp){
        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return str;
    }
}
