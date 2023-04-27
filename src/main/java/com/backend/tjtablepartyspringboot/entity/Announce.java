package com.backend.tjtablepartyspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "announce")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Announce implements Comparable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    Long announceId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long clubId;
    String announceContent;
    @JsonSerialize(using= ToStringSerializer.class)
    Long announceUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date announcePubTime;


    @Override
    public int compareTo(Object o) {
        Announce a = (Announce)o;
        if(announcePubTime.before(a.announcePubTime)){
            return -1;
        }
        else if(announcePubTime.after(a.announcePubTime)){
            return 1;
        }
        else{
            return 0;
        }
    }
}
