package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.ActivityHasTrpg;
import com.backend.tjtablepartyspringboot.entity.UserInterestActivity;
import com.backend.tjtablepartyspringboot.entity.UserJoinActivity;
import com.backend.tjtablepartyspringboot.mapper.ActivityHasTrpgMapper;
import com.backend.tjtablepartyspringboot.mapper.ActivityMapper;
import com.backend.tjtablepartyspringboot.mapper.UserInterestActivityMapper;
import com.backend.tjtablepartyspringboot.mapper.UserJoinActivityMapper;
import com.backend.tjtablepartyspringboot.service.ActivityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map<String,Object> getActivityHasTrpgEntity(Long activityId){
        Map<String,Object> map=new HashMap<>();
        //获取该活动对应的所有trpg的id
        QueryWrapper<ActivityHasTrpg> qw1=new QueryWrapper<>();
        qw1.eq("activity_id",activityId);
        List<ActivityHasTrpg> hasTrpgList=activityHasTrpgMapper.selectList(qw1);
        //根据trpg id，分为public与private
        //分别获取trpg信息

        map.put("hasTrpgList",hasTrpgList);
        return map;

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

}
