package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.Trpg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/2:24 PM
 * @Description:
 */
@Service
public interface TrpgService {

    //返回所有的trpg实体
    List<Trpg> getAllTrpg();

    //读取本地数据，批量insert数据库的trpg表
    Map<String,Object> setDBByLocal(String folderPath);

    //insert 一条新trpg实体
    Map<String,Object> insertOneTrpg(Trpg trpg);

}
