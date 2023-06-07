package com.backend.tjtablepartyspringboot.service.impl;

import com.backend.tjtablepartyspringboot.dto.*;
import com.backend.tjtablepartyspringboot.entity.*;
import com.backend.tjtablepartyspringboot.mapper.*;
import com.backend.tjtablepartyspringboot.service.SiteService;
import com.backend.tjtablepartyspringboot.service.TrpgService;
import com.backend.tjtablepartyspringboot.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Service
public class SiteServiceImpl implements SiteService {
    @Autowired
    private PublicSiteMapper publicSiteMapper;

    @Autowired
    private SiteTypeMapper siteTypeMapper;

    @Autowired
    private SiteTagMapper siteTagMapper;

    @Autowired
    private PublicSiteTimeMapper publicSiteTimeMapper;

    @Autowired
    private PrivateSiteMapper privateSiteMapper;

    @Autowired
    private SiteHasTrpgMapper siteHasTrpgMapper;

    @Autowired
    private TrpgService trpgService;

    @Autowired
    private PublicSiteQuestionMapper publicSiteQuestionMapper;

    @Autowired
    private PublicSiteReplyMapper publicSiteReplyMapper;

    @Autowired
    private SiteUserLikeQuestionMapper siteUserLikeQuestionMapper;

    @Autowired
    private SiteUserLikeReplyMapper siteUserLikeReplyMapper;

    @Autowired
    private UserService userService;

    private static String weekdayTrans(int weekday) {
        if (weekday == 1) return "周一";
        else if (weekday == 2) return "周二";
        else if (weekday == 3) return "周三";
        else if (weekday == 4) return "周四";
        else if (weekday == 5) return "周五";
        else if (weekday == 6) return "周六";
        else return "周日";
    }


    @Override
    public List<PublicSiteBriefDto> selectAllPublicSite() {
        List<PublicSite> publicSites = publicSiteMapper.selectAllPublicSite();
        ArrayList<PublicSiteBriefDto> res = new ArrayList<>();
        for (PublicSite ps : publicSites) {
            String[] type = ps.getType().split(",");
            for (int i = 0; i < type.length; i++) {
                type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
            }
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getStatus());
            res.add(publicSiteBriefDto);
        }
        return res;
    }

    @Override
    public PublicSiteDto selectPublicSiteById(Long publicSiteId) {
        PublicSite ps = publicSiteMapper.selectPublicSiteById(publicSiteId);
        // 转换场地类型
        String[] type = ps.getType().split(",");
        for (int i = 0; i < type.length; i++) {
            type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
        }
        // 转换场地标签
        String[] tag = ps.getTag().split(",");
        for (int i = 0; i < tag.length; i++) {
            tag[i] = siteTagMapper.selectTagNameById(Long.valueOf(tag[i]));
        }
        // 获取场地时间信息
        List<PublicSiteTime> publicSiteTimes = publicSiteTimeMapper.selectTimeById(ps.getPublicSiteId());
        ArrayList<PublicSiteTimeDto> openTime = new ArrayList<>();
        for (PublicSiteTime pst : publicSiteTimes) {
            PublicSiteTimeDto publicSiteTimeDto = new PublicSiteTimeDto(weekdayTrans(pst.getWeekday()), pst.getStartTime(), pst.getEndTime(), pst.isOpen());
            openTime.add(publicSiteTimeDto);
        }
        // 获取场地游戏信息
        List<SiteHasTrpg> siteHasTrpgs = siteHasTrpgMapper.selectTrpgsBySite(ps.getPublicSiteId(), 0);
        List<TrpgPublic> games = new ArrayList<>();
        for (SiteHasTrpg sht : siteHasTrpgs) {
            TrpgPublic detail_public = trpgService.getDetail_public(sht.getTrpgId());
            games.add(detail_public);
        }
        // 创建dto对象
        PublicSiteDto publicSiteDto = new PublicSiteDto(ps.getPublicSiteId(), ps.getCreatorId(), ps.getName(), ps.getCity(), ps.getLocation(), ps.getPicture(), ps.getIntroduction(), ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getPhone(), ps.getUploadTime(), ps.getCheckTime(), type, tag, ps.getStatus(), openTime, ps.getLatitude(), ps.getLongitude(), games);
        return publicSiteDto;
    }

    @Override
    public List<PublicSiteBriefDto> selectPublicSiteByCreatorId(String creatorId) {
        List<PublicSite> publicSiteList = publicSiteMapper.selectPublicSiteByCreatorId(creatorId);
        ArrayList<PublicSiteBriefDto> res = new ArrayList<>();
        for (PublicSite ps : publicSiteList) {
            String[] type = ps.getType().split(",");
            for (int i = 0; i < type.length; i++) {
                type[i] = siteTypeMapper.selectTypeNameById(Long.valueOf(type[i]));
            }
            PublicSiteBriefDto publicSiteBriefDto = new PublicSiteBriefDto(ps.getPublicSiteId(), ps.getName(), ps.getPicture(), ps.getCity(), ps.getLocation(), type, ps.getAvgCost(), ps.getCapacity(), ps.getGameNum(), ps.getStatus());
            res.add(publicSiteBriefDto);
        }
        return res;
    }

    @Override
    public List<PrivateSite> selectPrivateSiteByCreatorId(String creatorId) {
        return privateSiteMapper.selectPrivateSiteByCreatorId(creatorId);
    }

    @Override
    public PrivateSiteDto selectPrivateSiteById(Long privateSiteId) {
        PrivateSite ps = privateSiteMapper.selectPrivateSiteById(privateSiteId);
        // 获取场地游戏信息
        List<SiteHasTrpg> siteHasTrpgs = siteHasTrpgMapper.selectTrpgsBySite(ps.getPrivateSiteId(), 1);
        List<TrpgPublic> games = new ArrayList<>();
        for (SiteHasTrpg sht : siteHasTrpgs) {
            TrpgPublic detail_public = trpgService.getDetail_public(sht.getTrpgId());
            games.add(detail_public);
        }
        return new PrivateSiteDto(ps.getPrivateSiteId(), ps.getCreatorId(), ps.getName(), ps.getLocation(), ps.getPicture(), ps.getLatitude(), ps.getLongitude(), ps.getLocationTitle(), ps.getGameNum(), games);

    }

    @Override
    public List<SiteType> selectAllSiteType() {
        return siteTypeMapper.selectAllSiteType();
    }

    @Override
    public List<SiteTag> selectAllSiteTag() {
        return siteTagMapper.selectAllSiteTag();
    }

    @Override
    public int insertPublicSite(PublicSite publicSite) {
        return publicSiteMapper.insertPublicSite(publicSite);
    }

    @Override
    public int insertPublicSiteTime(PublicSiteTime publicSiteTime) {
        return publicSiteTimeMapper.insertPublicSiteTime(publicSiteTime);
    }

    @Override
    public int insertPrivateSite(PrivateSite privateSite) {
        return privateSiteMapper.insertPrivateSite(privateSite);
    }

    @Override
    public int modifyPrivateSite(PrivateSite privateSite) {
        return privateSiteMapper.updatePrivateSiteInfo(privateSite);
    }

    @Override
    public int deletePrivateSite(Long privateSiteId) {
        return privateSiteMapper.deletePrivateSite(privateSiteId);
    }

    @Override
    public List<PublicSite> selectByKeyword(String keyword) {
        return publicSiteMapper.selectByKeyword(keyword);
    }

    @Override
    public List<SiteHasTrpg> selectTrpgsBySite(Long siteId, int siteType) {
        return siteHasTrpgMapper.selectTrpgsBySite(siteId, siteType);
    }

    public Integer addSiteTrpg(Long siteId, String trpgId, int siteType) {
        return siteHasTrpgMapper.addSiteTrpg(siteId, trpgId, siteType);
    }

    @Override
    public Integer deleteSiteTrpg(Long siteId, int siteType) {
        return siteHasTrpgMapper.deleteSiteTrpg(siteId, siteType);
    }

    public static String questionTimeFormate(Date date) {
        if (date == null) {
            return "";
        }
        String str = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);                    //放入Date类型数据

        Integer year = calendar.get(Calendar.YEAR);                    //获取年份
        Integer month = calendar.get(Calendar.MONTH) + 1;                    //获取月份
        Integer day = calendar.get(Calendar.DATE);                    //获取日

        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);                //时（24小时制）
        Integer minute = calendar.get(Calendar.MINUTE);                    //分
        Integer second = calendar.get(Calendar.SECOND);                    //秒
        //今年
        calendar.setTime(new Date());
        Integer year_now = calendar.get(Calendar.YEAR);

        if (!year.equals(year_now)) {
            str = year + "年";
        }
        str = str + month + "月" + day + "日 " + hour + ":" + minute;
        return str;
    }

    @Override
    public Map<String, Object> getQuesion(Long quesionId, String userId) {

        QueryWrapper<PublicSiteQuestion> qw = new QueryWrapper<>();
        qw.eq("question_id", quesionId);
        PublicSiteQuestion publicSiteQuestion = publicSiteQuestionMapper.selectOne(qw);


        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("questionId", publicSiteQuestion.getQuestionId());
        questionMap.put("userId", publicSiteQuestion.getUserId());


        questionMap.put("title", publicSiteQuestion.getTitle());
        questionMap.put("content", publicSiteQuestion.getContent());

        Date createTime = publicSiteQuestion.getCreateTime();
        questionMap.put("createTime", createTime);
        questionMap.put("createTimeLabel", questionTimeFormate(createTime));


        questionMap.put("anonymity", publicSiteQuestion.getAnonymity());
        questionMap.put("displayName", publicSiteQuestion.getDisplayName());
        questionMap.put("displayAvatar", publicSiteQuestion.getDisplayAvatar());

        questionMap.put("likeTotal", publicSiteQuestion.getLikeTotal());
        questionMap.put("replyTotal", publicSiteQuestion.getReplyTotal());

        //该用户是否点过like
        Boolean iLike = false;
        QueryWrapper<SiteUserLikeQuestion> qw_2 = new QueryWrapper<>();
        qw_2.eq("question_id", publicSiteQuestion.getQuestionId()).eq("user_id", userId);
        SiteUserLikeQuestion siteUserLikeQuestion = siteUserLikeQuestionMapper.selectOne(qw_2);
        if (siteUserLikeQuestion != null) {
            iLike = true;
        }
        questionMap.put("iLike", iLike);


        return questionMap;
    }


    @Override
    public List<Map<String, Object>> getQuestionList(Long publicSiteId, String userId) {
        List<Map<String, Object>> list = new ArrayList<>();

        QueryWrapper<PublicSiteQuestion> qw = new QueryWrapper<>();
        qw.eq("public_site_id", publicSiteId);
        List<PublicSiteQuestion> publicSiteQuestions = publicSiteQuestionMapper.selectList(qw);

        for (PublicSiteQuestion qu : publicSiteQuestions) {
            Map<String, Object> oneQuestion = new HashMap<>();
            oneQuestion.put("questionId", qu.getQuestionId());
            oneQuestion.put("userId", qu.getUserId());


            oneQuestion.put("title", qu.getTitle());
            oneQuestion.put("content", qu.getContent());

            Date createTime = qu.getCreateTime();
            oneQuestion.put("createTime", createTime);
            oneQuestion.put("createTimeLabel", questionTimeFormate(createTime));


            oneQuestion.put("anonymity", qu.getAnonymity());
            oneQuestion.put("displayName", qu.getDisplayName());
            oneQuestion.put("displayAvatar", qu.getDisplayAvatar());

            oneQuestion.put("likeTotal", qu.getLikeTotal());
            oneQuestion.put("replyTotal", qu.getReplyTotal());
            //该用户是否点过like
            Boolean iLike = false;
            QueryWrapper<SiteUserLikeQuestion> qw_2 = new QueryWrapper<>();
            qw_2.eq("question_id", qu.getQuestionId()).eq("user_id", userId);
            SiteUserLikeQuestion siteUserLikeQuestion = siteUserLikeQuestionMapper.selectOne(qw_2);
            if (siteUserLikeQuestion != null) {
                iLike = true;
            }
            oneQuestion.put("iLike", iLike);


            list.add(oneQuestion);
        }


        return list;
    }


    @Override
    public Map<String, Object> getReplyList(Long questionId, String userId) {
        Map<String, Object> resultMap = new HashMap<>();

        //repley list
        List<Map<String, Object>> list = new ArrayList<>();
        QueryWrapper<PublicSiteReply> qw = new QueryWrapper<>();
        qw.eq("question_id", questionId);
        List<PublicSiteReply> publicSiteReplies = publicSiteReplyMapper.selectList(qw);
        for (PublicSiteReply reply : publicSiteReplies) {
            Map<String, Object> map = new HashMap<>();
            map.put("replyId", reply.getReplyId());
            map.put("questionId", reply.getQuestionId());
            map.put("content", reply.getContent());
            map.put("anonymity", reply.getAnonymity());
            map.put("displayAvatar", reply.getDisplayAvatar());
            map.put("displayName", reply.getDisplayName());
            map.put("userId", reply.getUserId());
            map.put("likeTotal", reply.getLikeTotal());

            Date createTime = reply.getCreateTime();
            map.put("createTime", createTime);
            map.put("createTimeLabel", questionTimeFormate(createTime));


            //该用户是否点过like
            Boolean iLike = false;
            QueryWrapper<SiteUserLikeReply> qw_2 = new QueryWrapper<>();
            qw_2.eq("reply_id", reply.getReplyId()).eq("user_id", userId);
            SiteUserLikeReply siteUserLikeReply = siteUserLikeReplyMapper.selectOne(qw_2);
            if (siteUserLikeReply != null) {
                iLike = true;
            }

            map.put("iLike", iLike);


            list.add(map);
        }
        resultMap.put("replyList", list);
        //question
        Map<String, Object> questionMap = getQuesion(questionId, userId);
        resultMap.put("question", questionMap);


        return resultMap;
    }

    @Override
    public List<SiteUserLikeQuestion> getUserLikeQuestionList(Long questionId) {
        QueryWrapper<SiteUserLikeQuestion> qw = new QueryWrapper<>();
        qw.eq("question_id", questionId);
        List<SiteUserLikeQuestion> siteUserLikeQuestions = siteUserLikeQuestionMapper.selectList(qw);
        return siteUserLikeQuestions;

    }

    @Override
    public List<SiteUserLikeReply> getUserLikeReplyList(Long replyId) {
        QueryWrapper<SiteUserLikeReply> qw = new QueryWrapper<>();
        qw.eq("reply_id", replyId);
        List<SiteUserLikeReply> siteUserLikeReplies = siteUserLikeReplyMapper.selectList(qw);
        return siteUserLikeReplies;
    }


    @Override
    public Integer addReply(Long questionId, String userId, String content, String anonymity) {
        Map<String, Object> resultMap = new HashMap<>();


        //组装成Reply entity
        Date now = new Date();
        PublicSiteReply reply = new PublicSiteReply();
        reply.setQuestionId(questionId);
        reply.setUserId(userId);
        reply.setAnonymity(anonymity);
        reply.setContent(content);
        reply.setCreateTime(now);
        //是否匿名
        String displayName, displayAvatar;
        if (anonymity.equals("1")) {
            displayAvatar = "/icon/user_avatar.png";
            displayName = "匿名";
        } else {
            //从user表获取信息
            UserDto userDto = userService.getNameAndAvatarUrl(userId);

            displayAvatar = userDto.getAvatarUrl();
            displayName = userDto.getNickName();
        }
        reply.setDisplayAvatar(displayAvatar);
        reply.setDisplayName(displayName);
        reply.setLikeTotal(0);

        Integer i = publicSiteReplyMapper.insert(reply);

        //对应question，回复计数+1
        if (i.equals(1)) {
            QueryWrapper<PublicSiteQuestion> qw_2 = new QueryWrapper<>();
            qw_2.eq("question_id", questionId);
            PublicSiteQuestion publicSiteQuestion = publicSiteQuestionMapper.selectOne(qw_2);
            Integer replyTotal = publicSiteQuestion.getReplyTotal() + 1;
            publicSiteQuestionMapper.update(
                    null,
                    Wrappers.<PublicSiteQuestion>lambdaUpdate()
                            .eq(PublicSiteQuestion::getQuestionId, questionId)
                            .set(PublicSiteQuestion::getReplyTotal, replyTotal)
            );
        }

        return i;

    }


    @Override
    public Integer addQuestion(Long publicSiteId, String userId, String content, String title, String anonymity) {
        Map<String, Object> resultMap = new HashMap<>();


        //组装成 entity
        Date now = new Date();
        PublicSiteQuestion question = new PublicSiteQuestion();
        question.setUserId(userId);
        question.setPublicSiteId(publicSiteId);
        question.setAnonymity(anonymity);
        question.setContent(content);
        question.setTitle(title);
        question.setCreateTime(now);

        //是否匿名
        String displayName, displayAvatar;
        if (anonymity.equals("1")) {
            displayAvatar = "/icon/user_avatar.png";
            displayName = "匿名";
        } else {
            //从user表获取信息
            UserDto userDto = userService.getNameAndAvatarUrl(userId);

            displayAvatar = userDto.getAvatarUrl();
            displayName = userDto.getNickName();
        }
        question.setDisplayAvatar(displayAvatar);
        question.setDisplayName(displayName);
        question.setLikeTotal(0);
        question.setReplyTotal(0);

        Integer i = publicSiteQuestionMapper.insert(question);


        return i;
    }

    @Override
    public Integer userLikeOneReply(String userId, Long replyId) {
        Integer i = 0;
        //判断是否like过
        QueryWrapper<SiteUserLikeReply> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("reply_id", replyId);
        SiteUserLikeReply oldOne = siteUserLikeReplyMapper.selectOne(qw);

        //找到对应的reply，修改其likeTotal
        QueryWrapper<PublicSiteReply> qw_reply = new QueryWrapper<>();
        qw_reply.eq("reply_id", replyId);

        PublicSiteReply publicSiteReply = publicSiteReplyMapper.selectOne(qw_reply);
        Integer likeTotal = publicSiteReply.getLikeTotal();

        if (oldOne == null) {
            //like
            SiteUserLikeReply siteUserLikeReply = new SiteUserLikeReply(userId, replyId, new Date());
            i = siteUserLikeReplyMapper.insert(siteUserLikeReply);

            //like total +1
            publicSiteReplyMapper.update(
                    null,
                    Wrappers.<PublicSiteReply>lambdaUpdate()
                            .eq(PublicSiteReply::getReplyId, replyId)
                            .set(PublicSiteReply::getLikeTotal, likeTotal + 1)
            );

        } else {
            //cancel like
            i = siteUserLikeReplyMapper.delete(qw);

            //like total -1
            publicSiteReplyMapper.update(
                    null,
                    Wrappers.<PublicSiteReply>lambdaUpdate()
                            .eq(PublicSiteReply::getReplyId, replyId)
                            .set(PublicSiteReply::getLikeTotal, likeTotal - 1)
            );
        }

        return i;
    }


    @Override
    public Integer userLikeOneQuestion(String userId, Long questionId) {
        Integer i = 0;
        //判断是否like过
        QueryWrapper<SiteUserLikeQuestion> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("question_id", questionId);
        SiteUserLikeQuestion oldOne = siteUserLikeQuestionMapper.selectOne(qw);

        //找到对应的question，修改其likeTotal
        QueryWrapper<PublicSiteQuestion> qw_2 = new QueryWrapper<>();
        qw_2.eq("question_id", questionId);

        PublicSiteQuestion publicSiteQuestion = publicSiteQuestionMapper.selectOne(qw_2);
        Integer likeTotal = publicSiteQuestion.getLikeTotal();

        if (oldOne == null) {
            //like
            SiteUserLikeQuestion siteUserLikeQuestion = new SiteUserLikeQuestion(userId, questionId, new Date());
            i = siteUserLikeQuestionMapper.insert(siteUserLikeQuestion);

            //like total +1
            publicSiteQuestionMapper.update(
                    null,
                    Wrappers.<PublicSiteQuestion>lambdaUpdate()
                            .eq(PublicSiteQuestion::getQuestionId, questionId)
                            .set(PublicSiteQuestion::getLikeTotal, likeTotal + 1)
            );

        } else {
            //cancel like
            i = siteUserLikeQuestionMapper.delete(qw);

            //like total -1
            publicSiteQuestionMapper.update(
                    null,
                    Wrappers.<PublicSiteQuestion>lambdaUpdate()
                            .eq(PublicSiteQuestion::getQuestionId, questionId)
                            .set(PublicSiteQuestion::getLikeTotal, likeTotal - 1)
            );
        }

        return i;
    }


    @Override
    public Integer deleteReply(Long replyId) {
        QueryWrapper<PublicSiteReply> qw = new QueryWrapper<>();
        qw.eq("reply_id", replyId);
        PublicSiteReply publicSiteReply = publicSiteReplyMapper.selectOne(qw);
        Long questionId = publicSiteReply.getQuestionId();

        Integer i = publicSiteReplyMapper.delete(qw);
        if (i.equals(1)) {
            //question reply个数-1
            QueryWrapper<PublicSiteQuestion> qw_2 = new QueryWrapper<>();
            qw_2.eq("question_id", questionId);
            PublicSiteQuestion publicSiteQuestion = publicSiteQuestionMapper.selectOne(qw_2);
            Integer replyTotal = publicSiteQuestion.getReplyTotal() - 1;
            publicSiteQuestionMapper.update(
                    null,
                    Wrappers.<PublicSiteQuestion>lambdaUpdate()
                            .eq(PublicSiteQuestion::getQuestionId, questionId)
                            .set(PublicSiteQuestion::getReplyTotal, replyTotal)
            );
        }

        return i;
    }


    @Override
    public Integer deleteQuestion(Long questionId) {
        QueryWrapper<PublicSiteQuestion> qw = new QueryWrapper<>();
        qw.eq("question_id", questionId);
        Integer i = publicSiteQuestionMapper.delete(qw);
        return i;
    }
}
