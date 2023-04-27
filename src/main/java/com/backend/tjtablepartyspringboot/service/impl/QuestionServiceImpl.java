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

import java.util.*;

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

    public static String questionTimeFormate(Date date){
        String str="";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);					//放入Date类型数据

        Integer year= calendar.get(Calendar.YEAR);					//获取年份
        Integer month=  calendar.get(Calendar.MONTH);					//获取月份
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
    public Map<String,Object>getQuesion(Long quesionId,Long userId){

        QueryWrapper<Question>qw=new QueryWrapper<>();
        qw.eq("question_id",quesionId);
        Question question=questionMapper.selectOne(qw);


        Map<String,Object>questionMap=new HashMap<>();
        questionMap.put("questionId",question.getQuestionId());
        questionMap.put("userId",question.getUserId());


        questionMap.put("title",question.getTitle());
        questionMap.put("content",question.getContent());

        Date createTime=question.getCreateTime();
        questionMap.put("createTime",createTime);
        questionMap.put("createTimeLabel",questionTimeFormate(createTime));


        questionMap.put("anonymity",question.getAnonymity());
        questionMap.put("displayName",question.getDisplayName());
        questionMap.put("displayAvatar",question.getDisplayAvatar());

        questionMap.put("likeTotal",question.getLikeTotal());
        questionMap.put("replyTotal",question.getReplyTotal());

        //该用户是否点过like
        Boolean iLike=false;
        QueryWrapper<UserLikeQuestion>qw_2=new QueryWrapper<>();
        qw_2.eq("question_id",question.getQuestionId()).eq("user_id",userId);
        UserLikeQuestion userLikeQuestion=userLikeQuestionMapper.selectOne(qw_2);
        if (userLikeQuestion!=null){
            iLike=true;
        }
        questionMap.put("iLike",iLike);






        return questionMap;
    }


    @Override
    public List<Map<String, Object>> getQuestionList(Long activityId,Long userId){
        List<Map<String, Object>>list=new ArrayList<>();

        QueryWrapper<Question> qw=new QueryWrapper<>();
        qw.eq("activity_id",activityId);
        List<Question>questionList=questionMapper.selectList(qw);

        for (Question qu:questionList){
            Map<String,Object> oneQuestion=new HashMap<>();
            oneQuestion.put("questionId",qu.getQuestionId());
            oneQuestion.put("userId",qu.getUserId());


            oneQuestion.put("title",qu.getTitle());
            oneQuestion.put("content",qu.getContent());

            Date createTime=qu.getCreateTime();
            oneQuestion.put("createTime",createTime);
            oneQuestion.put("createTimeLabel",questionTimeFormate(createTime));


            oneQuestion.put("anonymity",qu.getAnonymity());
            oneQuestion.put("displayName",qu.getDisplayName());
            oneQuestion.put("displayAvatar",qu.getDisplayAvatar());

            oneQuestion.put("likeTotal",qu.getLikeTotal());
            oneQuestion.put("replyTotal",qu.getReplyTotal());
            //该用户是否点过like
            Boolean iLike=false;
            QueryWrapper<UserLikeQuestion>qw_2=new QueryWrapper<>();
            qw_2.eq("question_id",qu.getQuestionId()).eq("user_id",userId);
            UserLikeQuestion userLikeQuestion=userLikeQuestionMapper.selectOne(qw_2);
            if (userLikeQuestion!=null){
                iLike=true;
            }
            oneQuestion.put("iLike",iLike);



            list.add(oneQuestion);
        }



        return list;
    }



    @Override
    public Map<String, Object> getReplyList(Long questionId,Long userId){
        Map<String, Object>resultMap=new HashMap<>();

        //repley list
        List<Map<String, Object>>list=new ArrayList<>();
        QueryWrapper<Reply>qw=new QueryWrapper<>();
        qw.eq("question_id",questionId);
        List<Reply>replyList=replyMapper.selectList(qw);
        for (Reply reply:replyList)
        {
            Map<String,Object>map=new HashMap<>();
            map.put("replyId",reply.getReplyId());
            map.put("questionId",reply.getQuestionId());
            map.put("content",reply.getContent());
            map.put("anonymity",reply.getAnonymity());
            map.put("displayAvatar",reply.getDisplayAvatar());
            map.put("displayName",reply.getDisplayName());
            map.put("userId",reply.getUserId());
            map.put("likeTotal",reply.getLikeTotal());

            Date createTime=reply.getCreateTime();
            map.put("createTime",createTime);
            map.put("createTimeLabel",questionTimeFormate(createTime));


            //该用户是否点过like
            Boolean iLike=false;
            QueryWrapper<UserLikeReply>qw_2=new QueryWrapper<>();
            qw_2.eq("reply_id",reply.getReplyId()).eq("user_id",userId);
            UserLikeReply userLike=userLikeReplyMapper.selectOne(qw_2);
            if (userLike!=null){
                iLike=true;
            }

            map.put("iLike",iLike);


            list.add(map);
        }
        resultMap.put("replyList",list);
        //question
        Map<String,Object>questionMap=getQuesion(questionId,userId);
        resultMap.put("question",questionMap);


        return resultMap;
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
