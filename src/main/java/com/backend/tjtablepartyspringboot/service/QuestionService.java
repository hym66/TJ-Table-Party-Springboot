package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.UserLikeQuestion;
import com.backend.tjtablepartyspringboot.entity.UserLikeReply;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/9:27 PM
 * @Description:
 */
@Service
public interface QuestionService {
    /**
     * 输入 activity id，获取该活动对应的所有question
     *
     * @return List
     */
    List<Map<String,Object>> getQuestionList(Long activityId,Long userId);


    /**
     * 获取一个question的详细信息
     */
    Map<String,Object>getQuesion(Long quesionId,Long userId);

    /**
     * 输入question id，获取该问题的reply列表
     *
     * @return List
     */
    Map<String, Object> getReplyList(Long questionId,Long userId);

    /**
     * 输入question id，获取UserLikeQuestion,计数用
     */
    List<UserLikeQuestion>getUserLikeQuestionList(Long questionId);


    /**
     * 输入reply id，获取UserLikeReply,计数用
     */
    List<UserLikeReply>getUserLikeReplyList(Long replyId);


    /**
     * 创建一条新的reply
     */
    Integer addReply(Long questionId,Long userId,String content,String anonymity);

    /**
     * 创建一条新的question
     */
    Integer addQuestion(Long activityId,Long userId,String content,String title,String anonymity);


    /**
     * user 点赞/取消点赞 一条reply
     */
    Integer userLikeOneReply(Long userId,Long replyId);

    /**
     * user 点赞/取消点赞 一条 question
     */
    Integer userLikeOneQuestion(Long userId,Long questionId);


    /**
     * 删除一条reply
     */
    Integer deleteReply(Long replyId);

    /**
     * 删除一条 question
     */
    Integer deleteQuestion(Long questionId);
}
