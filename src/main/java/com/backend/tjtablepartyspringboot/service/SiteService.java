package com.backend.tjtablepartyspringboot.service;

import com.backend.tjtablepartyspringboot.dto.PrivateSiteDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteBriefDto;
import com.backend.tjtablepartyspringboot.dto.PublicSiteDto;
import com.backend.tjtablepartyspringboot.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/17
 * @JDKVersion 17.0.4
 */
@Service
public interface SiteService {

    List<PublicSiteBriefDto> selectAllPublicSite();

    PublicSiteDto selectPublicSiteById(Long publicSiteId);

    List<PublicSiteBriefDto> selectPublicSiteByCreatorId(String creatorId);


    List<PrivateSite> selectPrivateSiteByCreatorId(String creatorId);

    PrivateSiteDto selectPrivateSiteById(Long privateSiteId);

    List<SiteType> selectAllSiteType();

    List<SiteTag> selectAllSiteTag();

    int insertPublicSite(PublicSite publicSite);

    int insertPublicSiteTime(PublicSiteTime publicSiteTime);

    int modifyPublicSiteTime(PublicSiteTime publicSiteTime);

    int modifyPublicSite(PublicSite PublicSite);

    int deletePublicSite(Long publicSiteId);

    int insertPrivateSite(PrivateSite privateSite);

    int modifyPrivateSite(PrivateSite privateSite);

    int deletePrivateSite(Long privateSiteId);

    public List<PublicSite> selectByKeyword(String keyword);

    List<SiteHasTrpg> selectTrpgsBySite(Long siteId, int siteType);

    public Integer addSiteTrpg(Long siteId, String trpgId, int siteType);

    public Integer deleteSiteTrpg(Long siteId, int siteType);

    /**
     * 输入 activity id，获取该活动对应的所有question
     *
     * @return List
     */
    List<Map<String, Object>> getQuestionList(Long publicSiteId, String userId);


    /**
     * 获取一个question的详细信息
     */
    Map<String, Object> getQuesion(Long quesionId, String userId);

    /**
     * 输入question id，获取该问题的reply列表
     *
     * @return List
     */
    Map<String, Object> getReplyList(Long questionId, String userId);

    /**
     * 输入question id，获取UserLikeQuestion,计数用
     */
    List<SiteUserLikeQuestion> getUserLikeQuestionList(Long questionId);


    /**
     * 输入reply id，获取UserLikeReply,计数用
     */
    List<SiteUserLikeReply> getUserLikeReplyList(Long replyId);


    /**
     * 创建一条新的reply
     */
    Integer addReply(Long questionId, String userId, String content, String anonymity);

    /**
     * 创建一条新的question
     */
    Integer addQuestion(Long publicSiteId, String userId, String content, String title, String anonymity);


    /**
     * user 点赞/取消点赞 一条reply
     */
    Integer userLikeOneReply(String userId, Long replyId);

    /**
     * user 点赞/取消点赞 一条 question
     */
    Integer userLikeOneQuestion(String userId, Long questionId);


    /**
     * 删除一条reply
     */
    Integer deleteReply(Long replyId);

    /**
     * 删除一条 question
     */
    Integer deleteQuestion(Long questionId);
}
