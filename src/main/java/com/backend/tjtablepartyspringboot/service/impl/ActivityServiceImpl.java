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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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
                stateLabel="集合中";
                break;
            case "1":
                stateLabel="正在进行";
                break;
            case "2":
                stateLabel="活动结束";
                break;
            case "3":
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
    public Map<String,Object>getDetail(Long activityId,String userId){
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
        detailMap.put("nowPeople",act.getNowPeople());



        //start time
        Date startTime=act.getStartTime();
        detailMap.put("startTime",startTime);
        detailMap.put("startTimeLabel",activityTimeFormate(startTime));

        //end time
        Date endTime=act.getEndTime();
        detailMap.put("endTime",endTime);
        detailMap.put("endTimeLabel",activityTimeFormate(endTime));

        //create time
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


        //参与者
        List<Map<String,Object>>participatorList=getActivityParticipatorList(activityId, act.getUserId());
        detailMap.put("participatorList",participatorList);



        //创建者的信息
        Map<String,Object>userData=new HashMap<>();
        String creatorId=act.getUserId();
        UserDto creatorDto =userService.getNameAndAvatarUrl(creatorId);
        userData.put("id",creatorId);
        userData.put("avatar",creatorDto.getAvatarUrl());
        userData.put("name",creatorDto.getNickName());
        detailMap.put("creatorInfo",userData);


        //请求信息的user，对于这个活动是什么role身份
        List<String>roles=new ArrayList<>();
        //interest
        QueryWrapper<UserInterestActivity>qw_insterest=new QueryWrapper<>();
        qw_insterest.eq("activity_id",activityId).eq("user_id",userId);
        UserInterestActivity interestAct=userInterestActivityMapper.selectOne(qw_insterest);
        //join
        QueryWrapper<UserJoinActivity>qw_join=new QueryWrapper<>();
        qw_join.eq("activity_id",activityId).eq("user_id",userId);
        UserJoinActivity joinAct=userJoinActivityMapper.selectOne(qw_join);

        //passer

        if(userId.equals(creatorId)){
            roles.add("creator");
        }
        if (interestAct!=null){
            roles.add("interest");

        }
        if (joinAct!=null) {
            roles.add("participator");

        }
        if (!roles.contains("creator")&&!roles.contains("participator")){
            roles.add("passer");
        }

        detailMap.put("roles",roles);
        detailMap.put("userId",userId);




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
    public List<Map<String,Object>>getActivityWishGame(String wishGame){
        List<Map<String,Object>> list=new ArrayList<>();
        //获取该活动对应的所有trpg的id
        List<String> wishGameIdList=List.of(wishGame.split("#"));
        wishGameIdList.stream().filter(ele->!ele.equals("")).collect(Collectors.toList());


        for (String trpgId:wishGameIdList){
            //根据trpg id，分为public与private
            //分别获取trpg信息
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
    public List<UserJoinActivity> getUserJoinActivityList(Long activityId){
        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<UserJoinActivity>list=userJoinActivityMapper.selectList(qw);

        return list;
    }

    @Override
    public List<Map<String,Object>> getUserInterestorList(Long activityId){
        List<Map<String,Object>> result=new ArrayList<>();

        QueryWrapper<UserInterestActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<UserInterestActivity>entityList=userInterestActivityMapper.selectList(qw);

        //check user table
        for (UserInterestActivity userInterest:entityList){
            Map<String,Object>oneData=new HashMap<>();
            String userId=userInterest.getUserId();
            UserDto userDto=userService.getNameAndAvatarUrl(userId);

            oneData.put("userId",userId);
            oneData.put("interestTime",userInterest.getInterestTime());
            oneData.put("avatar",userDto.getAvatarUrl());
            oneData.put("name",userDto.getNickName());



            result.add(oneData);
        }
        return result;
    }

    public static String activityTimeFormate(Date date){
        if (date==null){
            return "";
        }
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
    public Map<String,Object> getList(String key,Map<String,String>filterData,
                                            Map<String,String>sortData,
                                            Integer pageSize,Integer pageNo){
        //解析filter数据

        //date数据解析成date型
        String startDate_str=filterData.get("startDate");
        String endDate_str=filterData.get("endDate");
        Date startDate=null;
        Date endDate=null;


        try {
            //把字符串类型的时间转换为sql类型的时间 parse(需要转换的字符串)
            //因为parse(可能值为空所以需要 try catch 块)
            startDate= new Date(new SimpleDateFormat("yyyy-MM-dd").parse(startDate_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            endDate= new Date(new SimpleDateFormat("yyyy-MM-dd").parse(endDate_str).getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }

        final Date _startDate=startDate;
        final Date _endDate=endDate;



        Map<String,Object>map=new HashMap<>();
        List<Activity> actList=new ArrayList<>();

        //先获取所有的activity,list
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.select("activity_id","title","summary","poster","start_time",
                "user_id","site_id","fee","max_people","now_people","state");
        actList=activityMapper.selectList(qw);

        //key关键词匹配
        final String _key=key.toLowerCase(Locale.ROOT);
        actList=actList.stream().filter(t->t.getTitle().toLowerCase().contains(_key)).collect(Collectors.toList());


        //filter筛选
        //日期筛选
        if (_startDate!=null){
            actList=actList.stream().filter(t->
                            t.getStartTime()!=null&&
                                    t.getStartTime().after(_startDate)
                    ).collect(Collectors.toList());

        }
        if (_endDate!=null){
            actList=actList.stream().filter(t->
                    t.getStartTime()!=null&&
                            t.getStartTime().before(_endDate)
            ).collect(Collectors.toList());

        }


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
            String userId=act.getUserId();
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
        map.put("totalNum",totalNum);
        map.put("hasNext",hasNext);
        return map;
    }


    @Override
    public Map<String,Object> getUserList(String userId){
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

        //sort排序

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
    public Map<String,Object> getUserDoingList(String userId){
        Map<String,Object>map=new HashMap<>();
        List<Activity> actList=new ArrayList<>();

        //用户自己创建的
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.select("activity_id","title","summary","poster","start_time",
                        "user_id","site_id","fee","max_people","now_people","state")
                .eq("user_id",userId)
                .ne("state","2");
        actList=activityMapper.selectList(qw);

        /**
         * 筛选
         */

        //sort排序

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
    public Map<String,Object> getUserDoneList(String userId){
        Map<String,Object>map=new HashMap<>();
        List<Activity> actList=new ArrayList<>();

        //用户自己创建的
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.select("activity_id","title","summary","poster","start_time",
                        "user_id","site_id","fee","max_people","now_people","state")
                .eq("user_id",userId)
                .eq("state","2");
        actList=activityMapper.selectList(qw);

        /**
         * 筛选
         */

        //sort排序

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
    public Map<String,Object> getUserInterestList(String userId){
        Map<String,Object>map=new HashMap<>();
        List<Activity> actList=new ArrayList<>();

        // 关注的所有活动
        QueryWrapper<UserInterestActivity>qw_interest=new QueryWrapper<>();
        qw_interest.eq("user_id",userId);
        List<UserInterestActivity>interestActivities=userInterestActivityMapper.selectList(qw_interest);
        List<Long>activityIdList=interestActivities.stream().map(ele->ele.getActivityId()).collect(Collectors.toList());


        List<Map<String,Object>> datalist=new ArrayList<>();
        for (Long activityId:activityIdList){
            // 每一个activity id
            QueryWrapper<Activity>qw_activity=new QueryWrapper<>();
            qw_activity.eq("activity_id",activityId);
            Activity act=activityMapper.selectOne(qw_activity);

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
    public Map<String,Object> addActivity(Activity activity,String wishGame){
        Map<String,Object> resultMap=new HashMap<>();
        //补全信息
        Integer nowPeople=0;
        Date createTime=new Date();
        String state="0";
        activity.setNowPeople(nowPeople);
        activity.setCreateTime(createTime);
        activity.setState(state);
        //insert
        activityMapper.insert(activity);
        resultMap.put("id",activity.getActivityId());

        //联系trpg
        List<String> trpgIdList=List.of(wishGame.split(","));
        trpgIdList=trpgIdList.stream().filter(ele->!ele.equals("")).collect(Collectors.toList());

        Long activityId=activity.getActivityId();
        for (String trpgId:trpgIdList){
            ActivityHasTrpg activityHasTrpg=new ActivityHasTrpg(activityId,trpgId);
            activityHasTrpgMapper.insert(activityHasTrpg);
        }


        //创建者是参与者
        addParticipator(activity.getUserId(),activityId);

        return resultMap;
    }


    @Override
    public Map<String,Object>deleteOne(Long activityId){
        Map<String,Object>resultMap=new HashMap<>();
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        Integer i= activityMapper.delete(qw);
        resultMap.put("i",i);
        if (i.equals(1)){
            //删除activity has trpg
            QueryWrapper<ActivityHasTrpg>qw_2=new QueryWrapper<>();
            qw_2.eq("activity_id",activityId);
            activityHasTrpgMapper.delete(qw_2);
        }

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



    @Override
    public Integer addParticipator(String userId,Long activityId){
        Integer i=0;
        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId)
                .eq("user_id",userId);
        UserJoinActivity oldOne=userJoinActivityMapper.selectOne(qw);
        if (oldOne==null){
            i=userJoinActivityMapper.insert(new UserJoinActivity(userId,activityId,new Date()));
        }
        //参与人数+1
        if (i.equals(1)){
            QueryWrapper<Activity>qw_act=new QueryWrapper<>();
            qw_act.eq("activity_id",activityId);
            Activity activity=activityMapper.selectOne(qw_act);
            Integer nowPeople=activity.getNowPeople();
            activityMapper.update(
                    null,
                    Wrappers.<Activity>lambdaUpdate()
                            .eq(Activity::getActivityId,activityId)
                            .set(Activity::getNowPeople,nowPeople+1)
            );

        }



        return i;
    }

    @Override
    public List<Map<String,Object>> getActivityParticipatorList(Long activityId,String creatorId){
        List<Map<String,Object>> list=new ArrayList<>();
        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<UserJoinActivity>joinList=userJoinActivityMapper.selectList(qw);
        for (UserJoinActivity userJoin:joinList){
            Map<String,Object>oneData=new HashMap<>();
            String userId=userJoin.getUserId();
            oneData.put("id",userId);
            oneData.put("joinTime",userJoin.getJoinTime());

            if (userId.equals(creatorId)){
                oneData.put("role","creator");
                oneData.put("roleLabel","创建者");
            }else{
                oneData.put("role","participator");
                oneData.put("roleLabel","参与者");

            }

            //user detail
            UserDto userDto=userService.getNameAndAvatarUrl(userId);
            oneData.put("avatar",userDto.getAvatarUrl());
            oneData.put("name",userDto.getNickName());


            list.add(oneData);
        }

        return list;
    }


    @Override
    public Map<String,Object> getActivityParticipator(Long activityId){
        Map<String,Object>resultMap=new HashMap<>();
        Activity activity=activityMapper.selectById(activityId);
        String creatorId=activity.getUserId();


        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object>creatorInfo=new HashMap<>();

        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<UserJoinActivity>joinList=userJoinActivityMapper.selectList(qw);
        for (UserJoinActivity userJoin:joinList){
            Map<String,Object>oneData=new HashMap<>();
            String userId=userJoin.getUserId();
            oneData.put("id",userId);
            oneData.put("joinTime",userJoin.getJoinTime());



            //user detail
            UserDto userDto=userService.getNameAndAvatarUrl(userId);
            oneData.put("avatar",userDto.getAvatarUrl());
            oneData.put("name",userDto.getNickName());

            if (userId.equals(creatorId)){
                oneData.put("role","creator");
                oneData.put("roleLabel","创建者");
                creatorInfo=oneData;
            }else{
                oneData.put("role","participator");
                oneData.put("roleLabel","参与者");

            }
            list.add(oneData);
        }


        resultMap.put("list",list);
        resultMap.put("activityId",activityId);
        resultMap.put("creatorInfo",creatorInfo);

        return resultMap;
    }

    @Override
    public Integer deleteUserJoin(Long activityId, String userId) {
        Integer i=0;
        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId)
                .eq("user_id",userId);
        i=userJoinActivityMapper.delete(qw);
        //参与人数-1
        if (i.equals(1)){
            QueryWrapper<Activity>qw_act=new QueryWrapper<>();
            qw_act.eq("activity_id",activityId);
            Activity activity=activityMapper.selectOne(qw_act);
            Integer nowPeople=activity.getNowPeople();
            activityMapper.update(
                    null,
                    Wrappers.<Activity>lambdaUpdate()
                            .eq(Activity::getActivityId,activityId)
                            .set(Activity::getNowPeople,nowPeople-1)
            );

        }

        return i;
    }

    @Override
    public Integer interest(String userId,Long activityId){
        Integer i=0;
        //判断是否interest过
        QueryWrapper<UserInterestActivity>qw=new QueryWrapper<>();
        qw.eq("user_id",userId).eq("activity_id",activityId);
        UserInterestActivity oldOne=userInterestActivityMapper.selectOne(qw);
        if (oldOne==null){
            UserInterestActivity userInterestActivity=new UserInterestActivity(userId,activityId,new Date());
            i = userInterestActivityMapper.insert(userInterestActivity);
        }else {
            i=userInterestActivityMapper.delete(qw);
        }

        return i;
    }

    @Override
    public Integer doJoin(String userId,Long activityId){
        Integer i=0;
        QueryWrapper<UserJoinActivity>qw=new QueryWrapper<>();
        qw.eq("user_id",userId).eq("activity_id",activityId);
        UserJoinActivity oldOne=userJoinActivityMapper.selectOne(qw);
        if (oldOne==null){
            UserJoinActivity userJoinActivity=new UserJoinActivity(userId,activityId,new Date());
            i=userJoinActivityMapper.insert(userJoinActivity);
            //参与人数+1
            if (i.equals(1)){
                QueryWrapper<Activity>qw_act=new QueryWrapper<>();
                qw_act.eq("activity_id",activityId);
                Activity activity=activityMapper.selectOne(qw_act);
                Integer nowPeople=activity.getNowPeople();
                activityMapper.update(
                        null,
                        Wrappers.<Activity>lambdaUpdate()
                                .eq(Activity::getActivityId,activityId)
                                .set(Activity::getNowPeople,nowPeople+1)
                );

            }
        }
        else {
            i=userJoinActivityMapper.delete(qw);
            //参与人数-1
            if (i.equals(1)){
                QueryWrapper<Activity>qw_act=new QueryWrapper<>();
                qw_act.eq("activity_id",activityId);
                Activity activity=activityMapper.selectOne(qw_act);
                Integer nowPeople=activity.getNowPeople();
                activityMapper.update(
                        null,
                        Wrappers.<Activity>lambdaUpdate()
                                .eq(Activity::getActivityId,activityId)
                                .set(Activity::getNowPeople,nowPeople-1)
                );

            }
        }


        return i;
    }


    @Override
    public Map<String,Object>modify(Activity activity,String wishGame){
        Map<String,Object>resultMap=new HashMap<>();
        //修改activity表内容
        Long activityId=activity.getActivityId();
        QueryWrapper<Activity>qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        Activity newAct=activityMapper.selectOne(qw);

        newAct.setTitle(activity.getTitle());
        newAct.setFee(activity.getFee());
        newAct.setMaxPeople(activity.getMaxPeople());
        newAct.setMinPeople(activity.getMinPeople());
        newAct.setStartTime(activity.getStartTime());
        newAct.setEndTime(activity.getEndTime());
        newAct.setSummary(activity.getSummary());
        newAct.setDescription(activity.getDescription());

        activityMapper.update(newAct,qw);


        //修改activity has trpg

        //联系trpg
        List<String> trpgIdList=List.of(wishGame.split(","));
        trpgIdList=trpgIdList.stream().filter(ele->!ele.equals("")).collect(Collectors.toList());

        for (String trpgId:trpgIdList){
            ActivityHasTrpg activityHasTrpg=new ActivityHasTrpg(activityId,trpgId);
            activityHasTrpgMapper.insert(activityHasTrpg);
        }


        return resultMap;


    }


    @Override
    public Integer modifyState(Long activityId,String state){
        Integer i=0;
        i=activityMapper.update(
                null,
                Wrappers.<Activity>lambdaUpdate()
                        .eq(Activity::getActivityId,activityId)
                        .set(Activity::getState,state)
                );


        return i;
    }
}
