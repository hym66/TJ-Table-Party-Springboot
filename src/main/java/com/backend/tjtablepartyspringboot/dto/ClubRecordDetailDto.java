package com.backend.tjtablepartyspringboot.dto;

import com.backend.tjtablepartyspringboot.entity.ClubRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubRecordDetailDto {
    List<ClubRecord> clubRecordList;
}
