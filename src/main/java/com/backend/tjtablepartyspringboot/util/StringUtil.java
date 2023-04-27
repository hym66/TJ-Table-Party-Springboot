package com.backend.tjtablepartyspringboot.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 杨严
 * @Date: 2023/04/25/4:08 PM
 * @Description:
 */
public class StringUtil {
    public static List<String> splitStringToList(String str,String separator){
        List<String>list=List.of(str.split(separator));
        list=list.stream().filter(e->e.length()!=0).collect(Collectors.toList());
        return list;

    }
}
