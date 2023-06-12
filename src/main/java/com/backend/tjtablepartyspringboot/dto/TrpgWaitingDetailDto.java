package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrpgWaitingDetailDto {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private String trpgId;
    private String poster;
    private String titleName;
    private String synopsis;
    private List<String> pictures;
    private List<String> designers;
    private List<String> publishers;
    private List<String> publishLanguages;
    private String publishYear;
    private String publishState;
    private List<String> genre;
    private String gameMode;
    private String portability;
    private String desktopRequirement;
    private String suitableAge;
    private List<String> supportNum;
    private List<String> recommendNum;
    private String averageDuration;
    private String difficulty;
    private String setDuration;
    private String languageRequirement;

    private static List<String> extractItemsSharp(String input) {
        List<String> items = new ArrayList<>();

        String[] splitItems = input.split("#");
        int i = 0;
        for (String item : splitItems) {
            if(i == 0){
                i++;
                continue;
            }
            items.add(item);
        }

        return items;
    }

    public TrpgWaitingDetailDto(TrpgPublicWaiting t){
        this.trpgId = t.getTrpgId();
        this.poster = t.getPoster();
        this.titleName = t.getTitleName();
        this.synopsis = t.getSynopsis();
        this.pictures = extractItemsSharp(t.getPictures());
        this.designers = extractItemsSharp(t.getDesigners());
        this.publishers = extractItemsSharp(t.getPublishers());
        this.publishLanguages = extractItemsSharp(t.getPublishLanguages());
        this.publishYear = t.getPublishYear();
        this.publishState = t.getPublishState();
        this.genre = extractItemsSharp(t.getGenre());
        this.gameMode = t.getGameMode();
        this.portability = t.getPortability();
        this.desktopRequirement = t.getDesktopRequirement();
        this.suitableAge = t.getSuitableAge();
        this.supportNum = extractItemsSharp(t.getSupportNum());
        this.recommendNum = extractItemsSharp(t.getRecommendNum());
        this.averageDuration = t.getAverageDuration();
        this.difficulty = t.getDifficulty();
        this.setDuration = t.getSetDuration();
        this.languageRequirement = t.getLanguageRequirement();
    }
}
