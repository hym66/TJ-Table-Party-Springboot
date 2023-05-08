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

    /**
     * 输入public trpg id，获取一个trpg public的详细信息
     *
     */
    TrpgPublic getDetail_public(String trpgId );
    TrpgPrivate getDetail_private(String trpgId );

    /**
     * 输入string标题关键词，返回标题相似的trpg的名字列表
     */
    List<String> getSearchHint(String key);

    /**/
    Map<String,Object>parseTrpgEntity(TrpgPublic trpg);
    Map<String,Object>parseTrpgEntity(TrpgPrivate trpg);


    /**
     * 标题要contains 关键词
     * 筛选字段1==某一筛选值1
     * 排序策略
     * 一页容量
     * 需要第几页
     */
    Map<String,Object>search(String key,Map<String,String>filterData,
                             Map<String,String>sortData,
                             Integer pageSize,Integer pageNo);

    /**
     * 新增一条trpg private
     */
    Map<String,Object>addTrpgPrivate(TrpgPrivate trpg);

    /**
     * 输入trpg id，删除这个私人 trpg
     */
    Integer deleteTrpgPrivate(String trpgId);


    /**
     * 输入userId，返回该user所有的private trpg 简要信息
     */
    Map<String,Object>getPrivateTrpgByUserId(String userId);

    /**
     * 重置一个 trpg 的poster字段
     */
    Map<String,Object>updatePoster(String posterUrl,String trpgId);




}
