package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 杨严
 * @Date: 2023/04/19/2:22 PM
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "trpg_public")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrpgPublic {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private String trpgId;
    private String poster;
    private String titleName;
    private String synopsis;
    private String pictures;
    private String designers;
    private String publishers;
    private String publishLanguages;
    private String publishYear;
    private String publishState;
    private String genre;
    private String gameMode;
    private String portability;
    private String desktopRequirement;
    private String suitableAge;
    private String supportNum;
    private String recommendNum;
    private String averageDuration;
    private String difficulty;
    private String setDuration;
    private String languageRequirement;

    public TrpgPublic(TrpgPublicWaiting t){
        this.trpgId = t.getTrpgId();
        this.poster = t.getPoster();
        this.titleName = t.getTitleName();
        this.synopsis = t.getSynopsis();
        this.pictures = t.getPictures();
        this.designers = t.getDesigners();
        this.publishers = t.getPublishers();
        this.publishLanguages = t.getPublishLanguages();
        this.publishYear = t.getPublishYear();
        this.publishState = t.getPublishState();
        this.genre = t.getGenre();
        this.gameMode = t.getGameMode();
        this.portability = t.getPortability();
        this.desktopRequirement = t.getDesktopRequirement();
        this.suitableAge = t.getSuitableAge();
        this.supportNum = t.getSupportNum();
        this.recommendNum = t.getRecommendNum();
        this.averageDuration = t.getAverageDuration();
        this.difficulty = t.getDifficulty();
        this.setDuration = t.getSetDuration();
        this.languageRequirement = t.getLanguageRequirement();
    }

}
