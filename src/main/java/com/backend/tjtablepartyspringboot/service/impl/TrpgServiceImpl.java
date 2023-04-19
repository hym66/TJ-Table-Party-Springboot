package com.backend.tjtablepartyspringboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.tjtablepartyspringboot.entity.TrpgPrivate;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.backend.tjtablepartyspringboot.mapper.TrpgPrivateMapper;
import com.backend.tjtablepartyspringboot.mapper.TrpgPublicMapper;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
}
