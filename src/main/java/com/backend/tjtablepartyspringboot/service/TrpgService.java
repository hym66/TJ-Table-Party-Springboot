package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.TrpgPrivate;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
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

    //返回所有的公开trpg实体
    List<TrpgPublic> getAllPublicTrpg();

    //返回所有的个人trpg实体
    List<TrpgPrivate> getAllPrivateTrpg();

    //读取本地数据，批量insert数据库的trpg public表
    Map<String,Object> setWholeDBPublicTrpg(String folderPath);

    //insert 一条新的公开trpg实体
    Map<String,Object> insertOnePublicTrpg(TrpgPublic trpgPublic);

}
