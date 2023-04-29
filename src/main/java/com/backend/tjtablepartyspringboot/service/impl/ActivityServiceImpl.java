package com.backend.tjtablepartyspringboot.service.impl;
import com.backend.tjtablepartyspringboot.dto.UserDto;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import com.backend.tjtablepartyspringboot.service.UserService;
import com.backend.tjtablepartyspringboot.util.DateUtil;

import com.backend.tjtablepartyspringboot.service.ActivityService;
import com.backend.tjtablepartyspringboot.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/8:38 PM
 * @Description:
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ActivityHasTrpgMapper activityHasTrpgMapper;

    @Autowired
    UserInterestActivityMapper userInterestActivityMapper;

    @Autowired
    UserJoinActivityMapper userJoinActivityMapper;

    @Autowired
    TrpgService trpgService;

    @Autowired
    UserService userService;




    @Override
    public String toStateLabel(String state){
        String stateLabel="";
        switch (state){
            case "0":
                stateLabel="报名中";
                break;
            case "1":
                stateLabel="报名结束";
                break;
            case "2":
                stateLabel="正在进行";
                break;
            case "3":
                stateLabel="已结束";
                break;
            case "4":
                stateLabel="已删除";
                break;

        }
        return stateLabel;
    }

    @Override
    public List<Activity> getAllEntity(){
        List<Activity> list=new ArrayList<>();


        return list;
    }
    @Override
    public Activity getEntityByActivityId(Long activityId){
        QueryWrapper<Activity> qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        Activity activity=activityMapper.selectOne(qw);

        return activity;
    }

    @Override
    public Map<String,Object>getDetail(Long activityId){
        Map<String,Object>detailMap=new HashMap<>();
        //activity 实体
        QueryWrapper<Activity> qw_act=new QueryWrapper<>();
        qw_act.eq("activity_id",activityId);
        Activity act=activityMapper.selectOne(qw_act);
        detailMap.put("activityId",act.getActivityId());
        detailMap.put("userId",act.getUserId());
        detailMap.put("title",act.getTitle());
        detailMap.put("fee",act.getFee());
        detailMap.put("maxPeople",act.getMaxPeople());
        detailMap.put("minPeople",act.getMinPeople());
        //start time
        Date startTime=act.getStartTime();
        detailMap.put("startTime",startTime);
        detailMap.put("startTimeLabel",activityTimeFormate(startTime));

        //end time
        Date endTime=act.getEndTime();
        detailMap.put("endTime",endTime);
        detailMap.put("endTimeLabel",activityTimeFormate(endTime));

        //end time
        Date createTime=act.getCreateTime();
        detailMap.put("createTime",createTime);
        detailMap.put("createTimeLabel",activityTimeFormate(createTime));

        detailMap.put("summary",act.getSummary());
        detailMap.put("description",act.getDescription());
        detailMap.put("poster",act.getPoster());
        detailMap.put("pictures",act.getPictures());
        detailMap.put("siteId",act.getSiteId());
        detailMap.put("clubId",act.getClubId());

        //state
        String state=act.getState();
        detailMap.put("state",state);

        detailMap.put("stateLabel",toStateLabel(state));


        //想玩的游戏
        List<Map<String,Object>>wishGameList=getActivityHasTrpgEntity(act.getActivityId());
        detailMap.put("wishGameList",wishGameList);



        //联系user表
        Map<String,Object>userData=new HashMap<>();
        Long userId=act.getUserId();
        UserDto userDto =userService.getNameAndAvatarUrl(userId);
        userData.put("id",userId);
        userData.put("avatar",userDto.getAvatarUrl());
        userData.put("name",userDto.getNickName());
        detailMap.put("creatorInfo",userData);


        //联系club 表


        //联系site 表
        detailMap.put("mapAddress","测试用地址"+act.getActivityId());



        return detailMap;

    }



    @Override
    public List<Map<String,Object>> getActivityHasTrpgEntity(Long activityId){
        List<Map<String,Object>> list=new ArrayList<>();
        //获取该活动对应的所有trpg的id
        QueryWrapper<ActivityHasTrpg> qw1=new QueryWrapper<>();
        qw1.eq("activity_id",activityId);
        List<ActivityHasTrpg> hasTrpgList=activityHasTrpgMapper.selectList(qw1);
        for (ActivityHasTrpg hasTrpg:hasTrpgList){
            //根据trpg id，分为public与private
            //分别获取trpg信息
            String trpgId=hasTrpg.getTrpgId();
            Map<String,Object>trpgData=new HashMap<>();
            if (trpgId.charAt(0)=='A'){
                TrpgPrivate trpg=trpgService.getDetail_private(trpgId);

                trpgData.put("trpgId",trpg.getTrpgId());
                trpgData.put("poster",trpg.getPoster());
                trpgData.put("titleName",trpg.getTitleName());
                trpgData.put("picures", StringUtil.splitStringToList(trpg.getPictures()," "));
                trpgData.put("genre",StringUtil.splitStringToList(trpg.getGenre(),"#"));
                trpgData.put("supportNum",StringUtil.splitStringToList(trpg.getSupportNum(),"#"));
                trpgData.put("recommendNum",StringUtil.splitStringToList(trpg.getRecommendNum(),"#"));
                trpgData.put("averageDuration",trpg.getAverageDuration());
                trpgData.put("difficulty",trpg.getDifficulty());
                trpgData.put("languageRequirement",trpg.getLanguageRequirement());
                trpgData.put("gameMode",trpg.getGameMode());

            }else{
                TrpgPublic trpg=trpgService.getDetail_public(trpgId);

                trpgData.put("trpgId",trpg.getTrpgId());
                trpgData.put("poster",trpg.getPoster());
                trpgData.put("titleName",trpg.getTitleName());
                trpgData.put("picures", StringUtil.splitStringToList(trpg.getPictures()," "));
                trpgData.put("genre",StringUtil.splitStringToList(trpg.getGenre(),"#"));
                trpgData.put("supportNum",StringUtil.splitStringToList(trpg.getSupportNum(),"#"));
                trpgData.put("recommendNum",StringUtil.splitStringToList(trpg.getRecommendNum(),"#"));
                trpgData.put("averageDuration",trpg.getAverageDuration());
                trpgData.put("difficulty",trpg.getDifficulty());
                trpgData.put("languageRequirement",trpg.getLanguageRequirement());
                trpgData.put("gameMode",trpg.getGameMode());

            }
            list.add(trpgData);

        }



        return list;

    }


    @Override
    public List<UserInterestActivity> getUserInterestActivityList(Long activityId){
        QueryWrapper<UserInterestActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<UserInterestActivity>list=userInterestActivityMapper.selectList(qw);

        return list;
    }

    @Override
    public List<UserJoinActivity> getUserJoinActivityList(Long activityId){
        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<UserJoinActivity>list=userJoinActivityMapper.selectList(qw);

        return list;
    }

    public static String activityTimeFormate(Date date){
        String str="";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);					//放入Date类型数据

        Integer year= calendar.get(Calendar.YEAR);					//获取年份
        Integer month=  calendar.get(Calendar.MONTH)+1;					//获取月份
        Integer day= calendar.get(Calendar.DATE);					//获取日

        Integer hour= calendar.get(Calendar.HOUR_OF_DAY);				//时（24小时制）
        Integer minute= calendar.get(Calendar.MINUTE);					//分
        Integer second= calendar.get(Calendar.SECOND);					//秒
        //今年
        calendar.setTime(new Date());
        Integer year_now=calendar.get(Calendar.YEAR);

        if (!year.equals(year_now)){
            str=year+"年";
        }
        str=str+month+"月"+day+"日 "+hour+":"+minute;
        return str;
    }

    @Override
    public Map<String,Object> getList(Map<String,String>filterData,
                                            Map<String,String>sortData,
                                            Integer pageSize,Integer pageNo){


        Map<String,Object>map=new HashMap<>();
        List<Activity> actList=new ArrayList<>();

        //先获取所有的activity,list
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.select("activity_id","title","summary","poster","start_time",
                "user_id","site_id","fee","max_people","now_people","state");
        actList=activityMapper.selectList(qw);

        //filter筛选

        //sort排序


        //得到最后的list


        //分页返回
        //分页
        int totalNum=actList.size();
        int startIndex=(pageNo-1)*pageSize;
        int endIndex=startIndex+pageSize;
        //判断是否有下一页
        Boolean hasNext=true;

        if (startIndex+pageSize<=totalNum){

        }else{
            endIndex=totalNum;
            hasNext=false;
        }
        //最终需要返回的activity
        actList=actList.subList(startIndex,endIndex);

        //结合user表信息
        List<Map<String,Object>> datalist=new ArrayList<>();
        for (Activity act: actList){
            //对每一个activity，再获取相关的user、site信息
            Map<String,Object>oneActData=new HashMap<>();
            oneActData.put("activityId",act.getActivityId());
            oneActData.put("title",act.getTitle());
            oneActData.put("summary",act.getSummary());
            oneActData.put("poster",act.getPoster());
            oneActData.put("userId",act.getUserId());
            oneActData.put("siteId",act.getSiteId());
            oneActData.put("fee",act.getFee());
            oneActData.put("maxPeople",act.getMaxPeople());
            oneActData.put("nowPeople",act.getNowPeople());
            //state
            String state=act.getState();
            oneActData.put("state",state);

            oneActData.put("stateLabel",toStateLabel(state));

            //start time
            Date startTime=act.getStartTime();
            oneActData.put("startTime",startTime);
            oneActData.put("startTimeLabel",activityTimeFormate(startTime));

            //user信息
            Map<String,Object>userData=new HashMap<>();
            userData.put("id",act.getUserId());
            userData.put("avatar","https://tse1-mm.cn.bing.net/th/id/OIP-C.KaaPuoL3MiCMsjY3ACnD8gHaHa?pid=ImgDet&rs=1");
            userData.put("name",act.getUserId());
            oneActData.put("creatorInfo",userData);


            //site信息
            Map<String,Object>siteData=new HashMap<>();
            oneActData.put("mapAddress","测试用地址"+act.getActivityId());


            datalist.add(oneActData);
        }

        map.put("list",datalist);
        map.put("totalNum",totalNum);
        map.put("hasNext",hasNext);
        return map;
    }


    @Override
    public Map<String,Object> getUserList(Long userId){
        Map<String,Object>map=new HashMap<>();
        List<Activity> actList=new ArrayList<>();

        //用户自己创建的
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.select("activity_id","title","summary","poster","start_time",
                "user_id","site_id","fee","max_people","now_people","state")
                .eq("user_id",userId);
        actList=activityMapper.selectList(qw);

        /**
         * 筛选
         */




        //用户参与的





        //sort排序


        //得到最后的list



        //结合其他表信息
        List<Map<String,Object>> datalist=new ArrayList<>();
        for (Activity act: actList){
            //对每一个activity，再获取相关的user、site信息
            Map<String,Object>oneActData=new HashMap<>();
            oneActData.put("activityId",act.getActivityId());
            oneActData.put("title",act.getTitle());
            oneActData.put("summary",act.getSummary());
            oneActData.put("poster",act.getPoster());
            oneActData.put("userId",act.getUserId());
            oneActData.put("siteId",act.getSiteId());
            oneActData.put("fee",act.getFee());
            oneActData.put("maxPeople",act.getMaxPeople());
            oneActData.put("nowPeople",act.getNowPeople());
            //state
            String state=act.getState();
            oneActData.put("state",state);

            oneActData.put("stateLabel",toStateLabel(state));

            //start time
            Date startTime=act.getStartTime();
            oneActData.put("startTime",startTime);
            oneActData.put("startTimeLabel",activityTimeFormate(startTime));

            //user信息
            Map<String,Object>userData=new HashMap<>();

            UserDto userDto =userService.getNameAndAvatarUrl(userId);
            userData.put("id",userId);
            userData.put("avatar",userDto.getAvatarUrl());
            userData.put("name",userDto.getNickName());
            oneActData.put("creatorInfo",userData);


            //site信息
            Map<String,Object>siteData=new HashMap<>();
            oneActData.put("mapAddress","测试用地址"+act.getActivityId());


            datalist.add(oneActData);
        }

        map.put("list",datalist);
        return map;

    }

    @Override
    public Map<String,Object> addActivity(Activity activity){
        Map<String,Object> resultMap=new HashMap<>();
        //补全信息
        Integer nowPeople=1;
        Date createTime=new Date();
        String state="0";
        activity.setNowPeople(nowPeople);
        activity.setCreateTime(createTime);
        activity.setState(state);
        //insert
        activityMapper.insert(activity);
        resultMap.put("id",activity.getActivityId());
        return resultMap;
    }


    @Override
    public Map<String,Object>deleteOne(Long activityId){
        Map<String,Object>resultMap=new HashMap<>();
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        Integer i= activityMapper.delete(qw);
        resultMap.put("i",i);

        return resultMap;
    }

    @Override
    public Map<String,Object>updateActivity(Activity activity,Long activityId){
        Map<String,Object>resultMap=new HashMap<>();
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        Integer i=activityMapper.updateById(activity);
        resultMap.put("i",i);

        return resultMap;
    }

    @Override
    public Map<String,Object>updatePoster(String posterUrl,Long activityId){
        Map<String,Object>resultMap=new HashMap<>();
        Integer i= activityMapper.update(
                null,
                Wrappers.<Activity>lambdaUpdate()
                        .eq(Activity::getActivityId,activityId)
                        .set(Activity::getPoster,posterUrl)
        );
        resultMap.put("i",i);

        return resultMap;
    }
}
