package com.backend.tjtablepartyspringboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.TrpgPrivate;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.backend.tjtablepartyspringboot.mapper.TrpgPrivateMapper;
import com.backend.tjtablepartyspringboot.mapper.TrpgPublicMapper;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.backend.tjtablepartyspringboot.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/2:24 PM
 * @Description:
 */
@Service
public class TrpgServiceImpl implements TrpgService {
    @Autowired
    TrpgPublicMapper trpgPublicMapper;
    @Autowired
    TrpgPrivateMapper trpgPrivateMapper;
    @Override
    public List<TrpgPublic> getAllPublicTrpg(){
        QueryWrapper<TrpgPublic> queryWrapper=new QueryWrapper<>();
        List<TrpgPublic> list= trpgPublicMapper.selectList(queryWrapper);
        return list;
    }


    @Override
    public List<TrpgPrivate> getAllPrivateTrpg(){
        QueryWrapper<TrpgPrivate> queryWrapper=new QueryWrapper<>();
        List<TrpgPrivate> list= trpgPrivateMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Map<String,Object> setWholeDBPublicTrpg(String folderPath){
        //insert计数
        int num=0;
        //返回的result
        Map<String, Object> resultMap = new HashMap<String, Object>();


        String lastId="-2";
        //读取本地数据
        try{
            //建议读取服务器上的绝对路径
            //打开文件夹
            File folder= ResourceUtils.getFile(folderPath);
            //遍历文件夹内容
            File[] fs = folder.listFiles();

            for(File f:fs){
                //存储一个文件内容的map,读取文件内容
                Map<String, Object> map = new HashMap<String, Object>();
                String json= FileUtils.readFileToString(f,"UTF-8");
                JSONObject obj= JSON.parseObject(json);
                Iterator it =obj.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                    map.put(entry.getKey(), entry.getValue());
                }
                String fileName=f.getName();
//                resultMap.put(fileName,map);
                //解析字符串,准备写入数据库
                //id
                String id=(String)map.get("id");
                //id=-1，说明文件无效,跳过
                if (id.equals("-1")){
                    continue;
                }else {
                    //文件有效，继续处理
                    lastId=id;
                    String targetId="15431";
                    if (id.equals(targetId)){
                        System.out.println(id);
                    }


                    //判断是否已经存在同id的，保留旧者，不插入新者
                    TrpgPublic old_trpg= trpgPublicMapper.selectById(id);
                    if (old_trpg!=null){
                        continue;
                    }

                    //解析字符串,准备写入数据库
                    String poster=(String) map.get("poster");

                    String titleName=(String) map.get("title_name");

                    String synopsis=(String) map.get("synopsis");

                    //pictures,空格间隔
                    List<String> pictures_list=(List<String>)map.get("pictures");
                    String pictures="";
                    for (String item:pictures_list){
                        pictures=pictures+" "+item;
                    }

                    List<String> designers_list=(List<String>)map.get("designers");
                    String designers="";
                    for (String item:designers_list){
                        designers=designers+"#"+item;
                    }

                    List<String> publishers_list=(List<String>)map.get("publishers");
                    String publishers="";
                    for (String item:publishers_list){
                        publishers=publishers+"#"+item;
                    }

                    List<String> publishLanguages_list=(List<String>)map.get("publish_languages");
                    String publishLanguages="";
                    for (String item:publishLanguages_list){
                        publishLanguages=publishLanguages+"#"+item;
                    }

                    String publishYear=(String) map.get("publish_year");

                    String publishState=(String) map.get("publish_state");

                    List<String> genre_list=(List<String>)map.get("genre");
                    String genre="";
                    for (String item:genre_list){
                        genre=genre+"#"+item;
                    }

                    String gameMode=(String) map.get("game_mode");


                    String portability=(String) map.get("portability");


                    String desktopRequirement=(String) map.get("desktop_requirement");


                    String suitableAge=(String) map.get("suitable_age");


                    List<String> supportNum_list=(List<String>)map.get("support_num");
                    String supportNum="";
                    for (String item:supportNum_list){
                        supportNum=supportNum+"#"+item;
                    }

                    List<String> recommendNum_list=(List<String>)map.get("recommend_num");
                    String recommendNum="";
                    for (String item:recommendNum_list){
                        recommendNum=recommendNum+"#"+item;
                    }

                    String averageDuration=(String) map.get("average_duration");

                    String difficulty=(String) map.get("difficulty");

                    String setDuration=(String) map.get("set_duration");

                    String languageRequirement=(String) map.get("language_requirement");



                    //包装数据成实体
                    TrpgPublic trpg=new TrpgPublic(id,poster,titleName,synopsis,pictures,designers,publishers,publishLanguages,publishYear,publishState,genre,gameMode,portability,desktopRequirement,suitableAge,supportNum,recommendNum,averageDuration,difficulty,setDuration,languageRequirement);
                    System.out.println(trpg);

                    //insert数据库
                    trpgPublicMapper.insert(trpg);



                }


//                break;


            }







        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            resultMap.put("last",lastId);
            return resultMap;
        }






    }

    @Override
    public Map<String,Object> insertOnePublicTrpg(TrpgPublic trpg){
        Map<String, Object> map = new HashMap<String, Object>();


        return map;
    }


    @Override
    public TrpgPublic getDetail_public(String trpgId ){

        QueryWrapper<TrpgPublic> qw=new QueryWrapper<>();
        qw.eq("trpg_id",trpgId);
        TrpgPublic trpg=trpgPublicMapper.selectOne(qw);
        return trpg;



    }

    @Override
    public TrpgPrivate getDetail_private(String trpgId ){

        QueryWrapper<TrpgPrivate> qw=new QueryWrapper<>();
        qw.eq("trpg_id",trpgId);
        TrpgPrivate trpg=trpgPrivateMapper.selectOne(qw);
        return trpg;



    }

    @Override
    public List<String> getSearchHint(String key){

        final String _key=key.toLowerCase(Locale.ROOT);

        List<String>list=new ArrayList<>();
        list.add(key);

        //遍历public trpg 表，任何标题包含了key的，都add
        QueryWrapper<TrpgPublic>qw=new QueryWrapper<>();
        qw.select("title_name");
        List<TrpgPublic>allList=trpgPublicMapper.selectList(qw);

        allList=allList.stream().filter(t->t.getTitleName().toLowerCase().contains(_key)).collect(Collectors.toList());

        list=allList.stream().map(t->t.getTitleName()).collect(Collectors.toList());


        return list;
    }


    @Override
    public Map<String,Object>search(String key,Map<String,String>filterData,
                                    Map<String,String>sortData,
                                  Integer pageSize,Integer pageNo){

        //解析filter数据
        List<String>filter_supportPeople=List.of(filterData.get("filter_supportPeople").split(","));
        filter_supportPeople=filter_supportPeople.stream().filter(ele->ele!="").collect(Collectors.toList());

        List<String>filter_recommendPeople=List.of(filterData.get("filter_recommendPeople").split(","));
        filter_recommendPeople=filter_recommendPeople.stream().filter(ele->ele!="").collect(Collectors.toList());


        List<String>filter_genre=List.of(filterData.get("filter_genre").split(","));
        filter_genre=filter_genre.stream().filter(ele->ele!="").collect(Collectors.toList());


        List<String>filter_difficulty=List.of(filterData.get("filter_difficulty").split(","));
        filter_difficulty=filter_difficulty.stream().filter(ele->ele!="").collect(Collectors.toList());



        Map<String,Object>map=new HashMap<>();

        List<TrpgPublic>resultList=new ArrayList<>();

        //1.关键词
        final String _key=key.toLowerCase(Locale.ROOT);

        //遍历public trpg 表，任何标题包含了key的，都add
        QueryWrapper<TrpgPublic>qw1=new QueryWrapper<>();
        qw1.select("trpg_id","title_name","poster","genre","support_num","recommend_num","average_duration","publish_year");

        resultList=trpgPublicMapper.selectList(qw1);
        resultList=resultList.stream().filter(t->t.getTitleName().toLowerCase().contains(_key)).collect(Collectors.toList());


        //filter
        //filter_supportPeople_str
        for (String supportPeople :filter_supportPeople){
            resultList=resultList.stream().filter(t->t.getSupportNum().contains("#"+supportPeople+"#")).collect(Collectors.toList());
        }

        //filter_recommendPeople_str
        for (String recommendPeople :filter_recommendPeople){
            List<TrpgPublic>notNullList=resultList.stream().filter(t->t.getRecommendNum()!=null).collect(Collectors.toList());
            resultList=notNullList.stream().filter(t->t.getRecommendNum().contains("#"+recommendPeople+"#")).collect(Collectors.toList());
        }

        //filter_genre_str
        for (String genre :filter_genre){
            List<TrpgPublic>notNullList=resultList.stream().filter(t->t.getGenre()!=null).collect(Collectors.toList());
            resultList=notNullList.stream().filter(t->t.getGenre().contains("#"+genre)).collect(Collectors.toList());
        }



        //sort 排序
        //0 不排序，-1逆序，1正序
        String sort_titleName=sortData.get("sort_titleName");
        String sort_publishYear=sortData.get("sort_publishYear");

        if (sort_titleName.equals("1")){
            resultList=resultList.stream().sorted(Comparator.comparing(TrpgPublic::getTitleName)).collect(Collectors.toList());

        }else if (sort_titleName.equals("-1")){
            resultList=resultList.stream().sorted(Comparator.comparing(TrpgPublic::getTitleName).reversed()).collect(Collectors.toList());

        }

        if (sort_publishYear.equals("1")){
            resultList=resultList.stream().sorted(Comparator.comparing(TrpgPublic::getPublishYear)).collect(Collectors.toList());


        }else if (sort_publishYear.equals("-1")){
            resultList=resultList.stream().sorted(Comparator.comparing(TrpgPublic::getPublishYear).reversed()).collect(Collectors.toList());

        }




        //分页
        int totalNum=resultList.size();
        int startIndex=(pageNo-1)*pageSize;
        int endIndex=startIndex+pageSize;
        //判断是否有下一页
        Boolean hasNext=true;

        if (startIndex+pageSize<=totalNum){

        }else{
            endIndex=totalNum;
            hasNext=false;
        }
        resultList=resultList.subList(startIndex,endIndex);

        map.put("list",resultList);
        map.put("totalNum",totalNum);
        map.put("hasNext",hasNext);
        return map;

    }


    @Override
    public Map<String,Object>getPrivateTrpgByUserId(String userId){
        Map<String,Object>map=new HashMap<>();
        List<TrpgPrivate>resultList=new ArrayList<>();
        QueryWrapper<TrpgPrivate>qw=new QueryWrapper<>();
        qw.select("trpg_id","title_name","poster","genre","support_num","recommend_num","average_duration")
                .eq("user_id",userId);

        resultList=trpgPrivateMapper.selectList(qw);
        map.put("list",resultList);
        return map;
    }

    @Override
    public  Map<String,Object>parseTrpgEntity(TrpgPublic trpg){
        Map<String,Object>map=new HashMap<>();
        //trpg id
        map.put("trpgId",trpg.getTrpgId());

        //poster
        map.put("poster",trpg.getPoster());

        //titleName
        map.put("titleName",trpg.getTitleName());

        //synopsis
        map.put("synopsis",trpg.getSynopsis());

        //pictures
        map.put("picures",StringUtil.splitStringToList(trpg.getPictures()," "));

        //designers
        map.put("designers",StringUtil.splitStringToList(trpg.getDesigners(),"#"));

        //publishers
        map.put("publishers",StringUtil.splitStringToList(trpg.getPublishers(),"#"));

        map.put("publishLanguages",StringUtil.splitStringToList(trpg.getPublishLanguages(),"#"));
        map.put("publishYear",trpg.getPublishYear());
        map.put("publishState",trpg.getPublishState());

        map.put("genre",StringUtil.splitStringToList(trpg.getGenre(),"#"));
        map.put("portability",trpg.getPortability());
        map.put("desktopRequirement",trpg.getDesktopRequirement());
        map.put("suitableAge",trpg.getSuitableAge());
        map.put("supportNum",StringUtil.splitStringToList(trpg.getSupportNum(),"#"));
        map.put("recommendNum",StringUtil.splitStringToList(trpg.getRecommendNum(),"#"));
        map.put("averageDuration",trpg.getAverageDuration());
        map.put("difficulty",trpg.getDifficulty());
        map.put("setDuration",trpg.getSetDuration());
        map.put("languageRequirement",trpg.getLanguageRequirement());
        map.put("gameMode",trpg.getGameMode());


        return map;
    }


    @Override
    public  Map<String,Object>parseTrpgEntity(TrpgPrivate trpg){
        Map<String,Object>map=new HashMap<>();
        map.put("userId",trpg.getUserId());
        //trpg id
        map.put("trpgId",trpg.getTrpgId());

        //poster
        map.put("poster",trpg.getPoster());

        //titleName
        map.put("titleName",trpg.getTitleName());

        //synopsis
        map.put("synopsis",trpg.getSynopsis());

        //pictures
        map.put("picures",StringUtil.splitStringToList(trpg.getPictures()," "));

        //designers
        map.put("designers",StringUtil.splitStringToList(trpg.getDesigners(),"#"));

        //publishers
        map.put("publishers",StringUtil.splitStringToList(trpg.getPublishers(),"#"));

        map.put("publishLanguages",StringUtil.splitStringToList(trpg.getPublishLanguages(),"#"));
        map.put("publishYear",trpg.getPublishYear());
        map.put("publishState",trpg.getPublishState());

        map.put("genre",StringUtil.splitStringToList(trpg.getGenre(),"#"));
        map.put("portability",trpg.getPortability());
        map.put("desktopRequirement",trpg.getDesktopRequirement());
        map.put("suitableAge",trpg.getSuitableAge());
        map.put("supportNum",StringUtil.splitStringToList(trpg.getSupportNum(),"#"));
        map.put("recommendNum",StringUtil.splitStringToList(trpg.getRecommendNum(),"#"));
        map.put("averageDuration",trpg.getAverageDuration());
        map.put("difficulty",trpg.getDifficulty());
        map.put("setDuration",trpg.getSetDuration());
        map.put("languageRequirement",trpg.getLanguageRequirement());
        map.put("gameMode",trpg.getGameMode());


        return map;
    }


    @Override
    public Map<String,Object>addTrpgPrivate(TrpgPrivate trpg){
        Map<String,Object>resultMap=new HashMap<>();
        Integer i=trpgPrivateMapper.insert(trpg);
        String trpgId=trpg.getTrpgId();
        //修改id，保证A开头
        trpgPrivateMapper.update(
                null,
                Wrappers.<TrpgPrivate>lambdaUpdate()
                        .eq(TrpgPrivate::getTrpgId,trpgId)
                        .set(TrpgPrivate::getTrpgId,"A"+trpgId)
                );
        trpgId="A"+trpgId;
        resultMap.put("i",i);
        resultMap.put("trpgId",trpgId);
        return resultMap;
    }





    @Override
    public Map<String,Object>updatePoster(String posterUrl,String trpgId){
        Map<String,Object>resultMap=new HashMap<>();
        Integer i=0;
        //区分private 和public
        if (trpgId.charAt(0)=='A'){
            i = trpgPrivateMapper.update(
                    null,
                    Wrappers.<TrpgPrivate>lambdaUpdate()
                            .eq(TrpgPrivate::getTrpgId,trpgId)
                            .set(TrpgPrivate::getPoster,posterUrl)
            );
        }
        else {
            i = trpgPublicMapper.update(
                    null,
                    Wrappers.<TrpgPublic>lambdaUpdate()
                            .eq(TrpgPublic::getTrpgId,trpgId)
                            .set(TrpgPublic::getPoster,posterUrl)
            );
        }
        resultMap.put("i",i);

        return resultMap;
    }



    @Override
    public Integer deleteTrpgPrivate(String trpgId){
        QueryWrapper<TrpgPrivate>qw=new QueryWrapper<>();
        qw.eq("trpg_id",trpgId);
        Integer i=trpgPrivateMapper.delete(qw);

        return i;
    }

}
