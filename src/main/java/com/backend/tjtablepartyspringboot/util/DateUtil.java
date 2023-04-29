package com.backend.tjtablepartyspringboot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 杨严
 * @Date: 2023/04/25/8:00 PM
 * @Description:
 *  Date类型，格式化
 */
public class DateUtil {
    /**
     * format: "yyyy-MM-dd HH:mm:ss SS"
     * ref# https://blog.csdn.net/weixin_43599377/article/details/100125796
     */
    public static String formatToString(Date date,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String str=sdf.format(date);

        return str;

    }


}
