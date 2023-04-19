package com.backend.tjtablepartyspringboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.tjtablepartyspringboot.entity.Trpg;
import com.backend.tjtablepartyspringboot.mapper.TrpgMapper;
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
    TrpgMapper trpgMapper;
    @Override
    public List<Trpg> getAllTrpg(){
        QueryWrapper<Trpg> queryWrapper=new QueryWrapper<>();
        List<Trpg> list=trpgMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Map<String,Object> setDBByLocal(String folderPath){
        //insert计数
        int num=0;
        //返回的result
        Map<String, Object> resultMap = new HashMap<String, Object>();



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
                resultMap.put(fileName,map);

                //insert数据库



            }


            //包装数据




        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {

            return resultMap;
        }






    }

    @Override
    public Map<String,Object> insertOneTrpg(Trpg trpg){
        Map<String, Object> map = new HashMap<String, Object>();


        return map;
    }
}
