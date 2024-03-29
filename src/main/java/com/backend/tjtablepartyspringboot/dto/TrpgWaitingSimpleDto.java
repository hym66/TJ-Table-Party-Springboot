package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.TrpgPublicWaiting;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrpgWaitingSimpleDto {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    String trpgId;
    String poster;
    String titleName;
    String genre;
    String averageDuration;
    String supportNum;
    String recommendNum;

    public TrpgWaitingSimpleDto(TrpgPublicWaiting trpgPublicWaiting){
        this.trpgId = trpgPublicWaiting.getTrpgId();
        this.poster = trpgPublicWaiting.getPoster();
        this.titleName = trpgPublicWaiting.getTitleName();
        this.genre = trpgPublicWaiting.getGenre();
        this.averageDuration = trpgPublicWaiting.getAverageDuration();

        //支持人数
        String[] supportNumList = trpgPublicWaiting.getSupportNum().split("#");
        if(supportNumList.length > 2){
            this.supportNum = "支持" + supportNumList[1] + "-" + supportNumList[supportNumList.length-1] + "人";
        }
        else if(supportNumList.length == 2){
            this.supportNum = "支持" + supportNumList[1] + "人";
        }
        else{
            this.supportNum = null;
        }

        //推荐人数
        String[] recommentNumList = trpgPublicWaiting.getRecommendNum().split("#");
        if(recommentNumList.length > 2){
            this.recommendNum = "推荐" + recommentNumList[1] + "-" + recommentNumList[recommentNumList.length-1] + "人";
        }
        else if(recommentNumList.length == 2){
            this.recommendNum = "推荐" + recommentNumList[1] + "人";
        }
        else{
            this.recommendNum = null;
        }


    }
}
