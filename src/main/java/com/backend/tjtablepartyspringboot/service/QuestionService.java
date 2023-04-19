package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.entity.Question;
import com.backend.tjtablepartyspringboot.entity.Reply;
import com.backend.tjtablepartyspringboot.entity.UserLikeQuestion;
import com.backend.tjtablepartyspringboot.entity.UserLikeReply;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/9:27 PM
 * @Description:
 */
@Service
public interface QuestionService {
    /**
     * 输入 activity id，获取该活动对应的所有question
     * @return List
     */
    List<Question> getListByActivityId(Long activityId);

    /**
     * 输入question id，获取该问题的reply列表
     * @return List
     */
    List<Reply> getListByQuestionId(Long questionId);

    /**
     * 输入question id，获取UserLikeQuestion,计数用
     */
    List<UserLikeQuestion>getUserLikeQuestionList(Long questionId);


    /**
     * 输入reply id，获取UserLikeReply,计数用
     */
    List<UserLikeReply>getUserLikeReplyList(Long replyId);
}
