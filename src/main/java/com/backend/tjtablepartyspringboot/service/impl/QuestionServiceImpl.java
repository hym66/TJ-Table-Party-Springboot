package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.entity.Question;
import com.backend.tjtablepartyspringboot.entity.Reply;
import com.backend.tjtablepartyspringboot.entity.UserLikeQuestion;
import com.backend.tjtablepartyspringboot.entity.UserLikeReply;
import com.backend.tjtablepartyspringboot.mapper.QuestionMapper;
import com.backend.tjtablepartyspringboot.mapper.ReplyMapper;
import com.backend.tjtablepartyspringboot.mapper.UserLikeQuestionMapper;
import com.backend.tjtablepartyspringboot.mapper.UserLikeReplyMapper;
import com.backend.tjtablepartyspringboot.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/9:29 PM
 * @Description:
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    UserLikeQuestionMapper userLikeQuestionMapper;

    @Autowired
    UserLikeReplyMapper userLikeReplyMapper;

    @Override
    public List<Question> getListByActivityId(Long activityId){
        QueryWrapper<Question> qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<Question>list=questionMapper.selectList(qw);
        return list;
    }



    @Override
    public List<Reply> getListByQuestionId(Long questionId){
        QueryWrapper<Reply>qw=new QueryWrapper<>();
        qw.eq("question_id",questionId);
        List<Reply>list=replyMapper.selectList(qw);
        return list;
    }

    @Override
    public List<UserLikeQuestion>getUserLikeQuestionList(Long questionId){
        QueryWrapper<UserLikeQuestion>qw=new QueryWrapper<>();
        qw.eq("question_id",questionId);
        List<UserLikeQuestion>list=userLikeQuestionMapper.selectList(qw);
        return list;

    }

    @Override
    public List<UserLikeReply>getUserLikeReplyList(Long replyId){
        QueryWrapper<UserLikeReply>qw=new QueryWrapper<>();
        qw.eq("reply_id",replyId);
        List<UserLikeReply>list=userLikeReplyMapper.selectList(qw);
        return list;
    }
}
