package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.Activity;
import com.backend.tjtablepartyspringboot.entity.ActivityHasTrpg;
import com.backend.tjtablepartyspringboot.entity.UserInterestActivity;
import com.backend.tjtablepartyspringboot.entity.UserJoinActivity;
import com.backend.tjtablepartyspringboot.mapper.ActivityMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/8:30 PM
 * @Description:
 */
@Service
public interface ActivityService {


    /**
     * 获取所有activity 实体
     *
     * @return List</Activity>
     */
    List<Activity> getAllEntity();

    /**
     * 根据activity id，获取activity 实体
     *
     * @return Activity
     */
    Activity getEntityByActivityId(Long activityId);

    /**
     *
     * 输入activity id，获取该活动对应的所有trpg，包括public与private
     *
     */
    Map<String,Object> getActivityHasTrpgEntity(Long activityId);


    /**
     * 输入activity id，获取所有interest 该活动的UserInterestActivity 实体,
     * 需要进一步查找user表，获取user的具体信息
     * @return List of user entity
     *
     */
    List<UserInterestActivity> getUserInterestActivityList(Long activityId);


    /**
     * 输入activity id，获取所有参与该activity的user实体
     * 需要进一步查找user表，获取user的具体信息
     */
    List<UserJoinActivity> getUserJoinActivityList(Long activityId);


}
