package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.Trpg;
import com.backend.tjtablepartyspringboot.service.ClubService;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/2:28 PM
 * @Description:
 */


@Api(tags = {"Trpg"})
@RestController
@RequestMapping("trpg")
public class TrpgController {
    @Autowired
    private TrpgService trpgService;


    /*
    * 获取所有trpg信息,主要为测试
    * */
    @ApiOperation("获取所有trpg信息")
    @PostMapping("/getAllTrpg")
    public Result<List<Trpg>> getAllTrpg()
    {
        List<Trpg> list=trpgService.getAllTrpg();
        return Result.success(list);
    }

    /*
    * 读取本地数据，批量insert数据库的trpg表
    * */
    @ApiOperation("本地数据，批量insert数据库的trpg表")
    @PostMapping("/setDBByLocal")
    public Result<Map<String,Object>> setDBByLocal(
            @ApiParam(name="folderPath", value="读取的本地文件夹路径", required = false)
            @RequestParam(value = "folderPath",required = false) String folderPath
    )
    {
        Map<String,Object> map=new HashMap<>();
        if (folderPath==null){
            folderPath="D:\\Language_Programming\\pro_assignment\\python\\crawler_1\\JiShi\\material\\details\\";
        }
        System.out.println(folderPath);
        Map<String,Object> resultMap= trpgService.setDBByLocal(folderPath);
        map.put("folderPath",folderPath);
        map.put("resultMap",resultMap);
        return Result.success(map);
    }
}

