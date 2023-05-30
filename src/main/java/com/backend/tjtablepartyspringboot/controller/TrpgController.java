package com.backend.tjtablepartyspringboot.controller;

import com.backend.tjtablepartyspringboot.common.Result;
import com.backend.tjtablepartyspringboot.entity.Club;
import com.backend.tjtablepartyspringboot.entity.TrpgPrivate;
import com.backend.tjtablepartyspringboot.entity.TrpgPublic;
import com.backend.tjtablepartyspringboot.service.ClubService;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import com.backend.tjtablepartyspringboot.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 输入trpg id，获取一个trpg的详细信息
     * private和public的id都通用的接口
     */
    @ApiOperation("输入private/public trpg id，获取一个trpg的详细信息")
    @GetMapping("/getTrpgDetail")
    public Result<Map<String,Object>>getTrpgDetail(
            @ApiParam(name = "trpgId", value = "桌游id", required = true)
            @RequestParam("trpgId") String trpgId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            //区分public，private
            if (trpgId.charAt(0)=='A'){
                //private
                resultMap.put("mode","private");
                TrpgPrivate trpg=trpgService.getDetail_private(trpgId);

                //处理数据
                resultMap.put("trpg",trpgService.parseTrpgEntity(trpg));
            }else{
                //public
                resultMap.put("mode","public");
                TrpgPublic trpg=trpgService.getDetail_public(trpgId);

                //处理数据
                resultMap.put("trpg",trpgService.parseTrpgEntity(trpg));
            }




            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

    @ApiOperation("输入userId，返回该user所有的private trpg 简要信息")
    @GetMapping("/getPrivateTrpgByUserId")
    public Result<Map<String,Object>>getPrivateTrpgByUserId(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=trpgService.getPrivateTrpgByUserId(userId);




            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }



    /**
     * 输入string标题关键词，返回标题相似的trpg的名字列表
     *
     */
    @ApiOperation("输入string标题关键词，返回标题相似的trpg的名字列表")
    @GetMapping("/getSearchHint")
    public Result<Map<String,Object>>getSearchHint(
            @ApiParam(name = "key", value = "原关键词", required = true)
            @RequestParam("key") String key
    ){
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("key",key);
        try
        {
            List<String>hintList=trpgService.getSearchHint(key);
            resultMap.put("hint",hintList);


            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }




    /**
     * trpg public的搜索，分页返回结果列表
     *
     */
    @ApiOperation("trpg public的搜索，分页返回结果列表")
    @GetMapping("/search")
    public Result<Map<String,Object>>search(
            @ApiParam(name = "key", value = "标题关键词", required = false)
            @RequestParam(name="key",required = false) String key,

            @ApiParam(name = "filter_supportPeople", value = "筛选-支持人数", required = false)
            @RequestParam(name = "filter_supportPeople",required = false) String filter_supportPeople,

            @ApiParam(name = "filter_recommendPeople", value = "筛选-推荐人数", required = false)
            @RequestParam(name = "filter_recommendPeople",required = false) String filter_recommendPeople,

            @ApiParam(name = "filter_genre", value = "筛选-种类", required = false)
            @RequestParam(name = "filter_genre",required = false) String filter_genre,

            @ApiParam(name = "filter_difficulty", value = "筛选-难度", required = false)
            @RequestParam(name = "filter_difficulty",required = false) String filter_difficulty,


            @ApiParam(name = "pageSize", value = " 单页容量", required = false)
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @ApiParam(name = "pageNo", value = "要求第几页", required = false)
            @RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo,

            @ApiParam(name = "sort_titleName", value = "排序-标题", required = false)
            @RequestParam(name = "sort_titleName",required = false,defaultValue = "0") String sort_titleName,

            @ApiParam(name = "sort_publishYear", value = "排序-出版年份", required = false)
            @RequestParam(name = "sort_publishYear",required = false,defaultValue = "-1") String sort_publishYear


    ){
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("key",key);
        try
        {
            //组装filter数据
            Map<String,String>filterData=new HashMap<>();
            filterData.put("filter_supportPeople",filter_supportPeople);
            filterData.put("filter_recommendPeople",filter_recommendPeople);
            filterData.put("filter_genre",filter_genre);
            filterData.put("filter_difficulty",filter_difficulty);

            //组装排序数据
            Map<String,String>sortData=new HashMap<>();
            sortData.put("sort_titleName",sort_titleName);
            sortData.put("sort_publishYear",sort_publishYear);

            resultMap=trpgService.search(key,filterData,sortData,pageSize,pageNo);


            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }



    @ApiOperation("新建一个private trpg")
    @PostMapping("/postTrpgPrivate")
    public Result<Map<String,Object>>getByActivityId(
            @ApiParam(name = "trpgPrivate", value = "trpg private", required = true)
            @RequestBody(required = true) TrpgPrivate trpgPrivate
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            resultMap=trpgService.addTrpgPrivate(trpgPrivate);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }


    @ApiOperation("提交trpg海报")
    @PostMapping("/postPoster")
    public Result<Map<String,Object>>postPoster(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("trpgId") String trpgId
    ){
        Map<String,Object>resultMap=new HashMap<>();
//        System.out.println(multipartFile);

        try
        {
            String url = FileUtil.uploadFile("/trpg/poster/"+trpgId.toString()+"/", multipartFile);
            Map<String,Object> map1=trpgService.updatePoster(url,trpgId);
            resultMap.put("url",url);
            resultMap.put("update",map1);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }
    }


    @ApiOperation("删除一个私人trpg")
    @DeleteMapping("/deletePrivateTrpg")
    public Result<Map<String,Object>>deletePrivateTrpg(
            @ApiParam(name = "trpgId", value = "private trpg id", required = true)
            @RequestParam(name ="trpgId",required = true) String trpgId
    ){
        Map<String,Object>resultMap=new HashMap<>();
        try
        {
            Integer i= trpgService.deleteTrpgPrivate(trpgId);
            resultMap.put("i",i);
            return Result.success(resultMap);
        }catch (Exception e){
            return Result.fail(0,e.getMessage());
        }

    }

}

