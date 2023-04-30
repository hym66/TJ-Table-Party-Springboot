package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.UserDto;
import com.backend.tjtablepartyspringboot.entity.Question;
import com.backend.tjtablepartyspringboot.entity.Reply;
import com.backend.tjtablepartyspringboot.entity.UserLikeQuestion;
import com.backend.tjtablepartyspringboot.entity.UserLikeReply;
import com.backend.tjtablepartyspringboot.mapper.QuestionMapper;
import com.backend.tjtablepartyspringboot.mapper.ReplyMapper;
import com.backend.tjtablepartyspringboot.mapper.UserLikeQuestionMapper;
import com.backend.tjtablepartyspringboot.mapper.UserLikeReplyMapper;
import com.backend.tjtablepartyspringboot.service.QuestionService;
import com.backend.tjtablepartyspringboot.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    @Autowired
    UserService userService;

    public static String questionTimeFormate(Date date){
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


    @Override
    public Integer addReply(Long questionId,Long userId,String content,String anonymity){
        Map<String,Object>resultMap=new HashMap<>();


        //组装成Reply entity
        Date now=new Date();
        Reply reply=new Reply();
        reply.setQuestionId(questionId);
        reply.setUserId(userId);
        reply.setAnonymity(anonymity);
        reply.setContent(content);
        reply.setCreateTime(now);
        //是否匿名
        String displayName,displayAvatar;
        if (anonymity.equals("1")){
            displayAvatar="/icon/user_avatar.png";
            displayName="匿名";
        }else{
            //从user表获取信息
            UserDto userDto=userService.getNameAndAvatarUrl(userId);

            displayAvatar=userDto.getAvatarUrl();
            displayName=userDto.getNickName();
        }
        reply.setDisplayAvatar(displayAvatar);
        reply.setDisplayName(displayName);
        reply.setLikeTotal(0);

        Integer i= replyMapper.insert(reply);

        //对应question，回复计数+1
        if (i.equals(1)){
            QueryWrapper<Question>qw_2=new QueryWrapper<>();
            qw_2.eq("question_id",questionId);
            Question question=questionMapper.selectOne(qw_2);
            Integer replyTotal=question.getReplyTotal()+1;
            questionMapper.update(
                    null,
                    Wrappers.<Question>lambdaUpdate()
                            .eq(Question::getQuestionId,questionId)
                            .set(Question::getReplyTotal,replyTotal)
            );
        }

        return i;

    }


    @Override
    public Integer addQuestion(Long activityId,Long userId,String content,String title,String anonymity){
        Map<String,Object>resultMap=new HashMap<>();


        //组装成 entity
        Date now=new Date();
        Question question=new Question();
        question.setUserId(userId);
        question.setActivityId(activityId);
        question.setAnonymity(anonymity);
        question.setContent(content);
        question.setTitle(title);
        question.setCreateTime(now);

        //是否匿名
        String displayName,displayAvatar;
        if (anonymity.equals("1")){
            displayAvatar="/icon/user_avatar.png";
            displayName="匿名";
        }else{
            //从user表获取信息
            UserDto userDto=userService.getNameAndAvatarUrl(userId);

            displayAvatar=userDto.getAvatarUrl();
            displayName=userDto.getNickName();
        }
        question.setDisplayAvatar(displayAvatar);
        question.setDisplayName(displayName);
        question.setLikeTotal(0);
        question.setReplyTotal(0);

        Integer i= questionMapper.insert(question);


        return i;
    }

    @Override
    public Integer userLikeOneReply(Long userId,Long replyId){
        Integer i=0;
        //判断是否like过
        QueryWrapper<UserLikeReply>qw=new QueryWrapper<>();
        qw.eq("user_id",userId).eq("reply_id",replyId);
        UserLikeReply oldOne=userLikeReplyMapper.selectOne(qw);

        //找到对应的reply，修改其likeTotal
        QueryWrapper<Reply>qw_reply=new QueryWrapper<>();
        qw_reply.eq("reply_id",replyId);

        Reply reply=replyMapper.selectOne(qw_reply);
        Integer likeTotal=reply.getLikeTotal();

        if (oldOne==null){
            //like
            UserLikeReply userLikeReply=new UserLikeReply(userId,replyId,new Date());
            i=userLikeReplyMapper.insert(userLikeReply);

            //like total +1
            replyMapper.update(
                    null,
                    Wrappers.<Reply>lambdaUpdate()
                            .eq(Reply::getReplyId,replyId)
                            .set(Reply::getLikeTotal,likeTotal+1)
            );

        }else{
            //cancel like
            i=userLikeReplyMapper.delete(qw);

            //like total -1
            replyMapper.update(
                    null,
                    Wrappers.<Reply>lambdaUpdate()
                            .eq(Reply::getReplyId,replyId)
                            .set(Reply::getLikeTotal,likeTotal-1)
            );
        }

        return i;
    }


    @Override
    public Integer userLikeOneQuestion(Long userId,Long questionId){
        Integer i=0;
        //判断是否like过
        QueryWrapper<UserLikeQuestion>qw=new QueryWrapper<>();
        qw.eq("user_id",userId).eq("question_id",questionId);
        UserLikeQuestion oldOne=userLikeQuestionMapper.selectOne(qw);

        //找到对应的question，修改其likeTotal
        QueryWrapper<Question>qw_2=new QueryWrapper<>();
        qw_2.eq("question_id",questionId);

        Question question=questionMapper.selectOne(qw_2);
        Integer likeTotal=question.getLikeTotal();

        if (oldOne==null){
            //like
            UserLikeQuestion userLikeQuestion=new UserLikeQuestion(userId,questionId,new Date());
            i=userLikeQuestionMapper.insert(userLikeQuestion);

            //like total +1
            questionMapper.update(
                    null,
                    Wrappers.<Question>lambdaUpdate()
                            .eq(Question::getQuestionId,questionId)
                            .set(Question::getLikeTotal,likeTotal+1)
            );

        }else{
            //cancel like
            i=userLikeQuestionMapper.delete(qw);

            //like total -1
            questionMapper.update(
                    null,
                    Wrappers.<Question>lambdaUpdate()
                            .eq(Question::getQuestionId,questionId)
                            .set(Question::getLikeTotal,likeTotal-1)
            );
        }

        return i;
    }



    @Override
    public Integer deleteReply(Long replyId){
        QueryWrapper<Reply>qw=new QueryWrapper<>();
        qw.eq("reply_id",replyId);
        Reply reply=replyMapper.selectOne(qw);
        Long questionId=reply.getQuestionId();

        Integer i= replyMapper.delete(qw);
        if (i.equals(1)){
            //question reply个数-1
            QueryWrapper<Question>qw_2=new QueryWrapper<>();
            qw_2.eq("question_id",questionId);
            Question question=questionMapper.selectOne(qw_2);
            Integer replyTotal=question.getReplyTotal()-1;
            questionMapper.update(
                    null,
                    Wrappers.<Question>lambdaUpdate()
                            .eq(Question::getQuestionId,questionId)
                            .set(Question::getReplyTotal,replyTotal)
            );
        }

        return i;
    }


    @Override
    public Integer deleteQuestion(Long questionId){
        QueryWrapper<Question>qw=new QueryWrapper<>();
        qw.eq("question_id",questionId);
        Integer i=questionMapper.delete(qw);
        return i;
    }
}
