package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.TrpgPrivate;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.backend.tjtablepartyspringboot.service.ClubService;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @ApiOperation("获取所有公开trpg信息")
    @GetMapping("/getAllPublicTrpg")
    public Result<List<TrpgPublic>> getAllPublicTrpg()
    {
        List<TrpgPublic> list=trpgService.getAllPublicTrpg();
//        List<TrpgPublic> list2=new ArrayList<>();
//        list2.add(list.get(2));
        return Result.success(list);
    }


    @ApiOperation("获取所有个人trpg信息")
    @GetMapping("/getAllPrivateTrpg")
    public Result<List<TrpgPrivate>> getAllPrivateTrpg()
    {
        List<TrpgPrivate> list=trpgService.getAllPrivateTrpg();
//        List<TrpgPrivate> list2=new ArrayList<>();
//        list2.add(list.get(2));
        return Result.success(list);
    }



    /*
    * 读取本地数据，批量insert数据库的public trpg表
    * */
    @ApiOperation("本地数据，批量insert数据库的public trpg表")
    @PostMapping("/setWholeDBPublicTrpg")
    public Result<Map<String,Object>> setWholeDBPublicTrpg(
            @ApiParam(name="folderPath", value="读取的本地文件夹路径", required = false)
            @RequestParam(value = "folderPath",required = false) String folderPath
    )
    {
        Map<String,Object> map=new HashMap<>();
        if (folderPath==null){
            folderPath="D:\\Language_Programming\\pro_assignment\\python\\crawler_1\\JiShi\\material\\details\\";
        }
        System.out.println(folderPath);
        Map<String,Object> resultMap= trpgService.setWholeDBPublicTrpg(folderPath);
        map.put("folderPath",folderPath);
        map.put("resultMap",resultMap);
        return Result.success(map);
    }
}

