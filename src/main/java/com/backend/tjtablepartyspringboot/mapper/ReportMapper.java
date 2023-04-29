package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper extends BaseMapper<Report> {
    @Select("SELECT * FROM report WHERE report_id=#{report_id}")
    Report selectByReportId(Long reportId);

    //查找所有未审核的举报单
    @Select("SELECT * FROM report WHERE ISNULL(check_time)")
    List<Report> selectUnchecked();
}
