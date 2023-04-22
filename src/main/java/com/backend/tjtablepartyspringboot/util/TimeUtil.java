package com.backend.tjtablepartyspringboot.util;

import java.sql.Timestamp;
import java.text.DateFormat;
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
    public static String getCurrentTime() {
//        java.util.Date date = new Date();//获得当前时间
//        Timestamp t = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
//        return t;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        java.util.Date date = new Date();//获得当前时间
        String str = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
        return str;
    }
}
