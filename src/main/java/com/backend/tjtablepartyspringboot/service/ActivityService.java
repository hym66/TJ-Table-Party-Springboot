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



    List<Map<String,Object>>getActivityWishGame(String wishGame);

    /**
     * 根据activity id，获取activity detail的所有相关数据
     */
    Map<String,Object>getDetail(Long activityId,String userId);




    /**
     * 输入user id，返回该user的所有的活动
     */
    Map<String,Object> getUserList(String userId);

    /**
     * 输入user id，返回该user的所有的 “集合中”、“正在进行”  活动
     */
    Map<String,Object> getUserDoingList(String userId);

    /**
     * 输入user id，返回该user的所有的 “已经完成” 的活动
     */
    Map<String,Object> getUserDoneList(String userId);


    /**
     * 输入user id，返回该user 关注的活动
     */
    Map<String,Object> getUserInterestList(String userId);


    /**
     *
     * 输入activity id，获取该活动对应的所有trpg，包括public与private
     *
     */
    List<Map<String,Object>> getActivityHasTrpgEntity(Long activityId);


    /**
     * 输入activity id，获取所有interest 该活动的UserInterestActivity 实体,
     * 需要进一步查找user表，获取user的具体信息
     * @return List of user entity
     *
     */
    List<Map<String,Object>> getUserInterestorList(Long activityId);


    /**
     * 输入activity id，获取所有参与该activity的user实体
     * 需要进一步查找user表，获取user的具体信息
     */
    List<UserJoinActivity> getUserJoinActivityList(Long activityId);


    /**
     * 新建一个activity，输入是一个不完整的activity，自动补全信息
     */
    Map<String,Object> addActivity(Activity activity,String wishGame);


    String toStateLabel(String state);

    /*
    * 删除一个活动
    * */
    Map<String,Object>deleteOne(Long activityId);


    /**
     *  更新一个活动的信息
     */
    Map<String,Object>updateActivity(Activity activity,Long activityId);

    /**
     * 重置一个activity的poster字段
     */
    Map<String,Object>updatePoster(String posterUrl,Long activityId);


    /**
     * user id和activity id，user参与活动
     */
    Integer addParticipator(String userId,Long activityId);


    /**
     * 输入activity id，获取所有参与者的信息
     */
    List<Map<String,Object>> getActivityParticipatorList(Long activityId,String creatorId);
    Map<String,Object> getActivityParticipator(Long activityId);


    /**
     * 删除一个user 参与活动
     */
    Integer deleteUserJoin(Long activityId,String userId);


    /**
     * 输入 筛选参数、排序参数，分页返回activity list
     *
     */
    Map<String,Object> getList(String key,Map<String,String>filterData,
                               Map<String,String>sortData,
                               Integer pageSize,Integer pageNo);


    /**
     *  喜欢 或者 取消喜欢
     */
    Integer interest(String userId,Long activityId);


    /**
     * 参与 / 取消参与
     */
    Integer doJoin(String userId,Long activityId);

    /**
     * 修改活动,不包括图片
     *  不是完全覆盖，传入的activity含有什么字段，才更新什么字段
     */
    Map<String,Object>modify(Activity activity,String wishGame);


    /**
     * 修改活动的状态
     */
    Integer modifyState(Long activityId,String state);


    /**
     * 输入场地id，场地类型
     * 返回相应的活动列表
     */
    List<Activity> getActBySite(Long siteId,Integer siteType);


}
