package com.backend.tjtablepartyspringboot.mapper;

import com.backend.tjtablepartyspringboot.entity.PublicSite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<PublicSite> {
    //查询所有未审核的场地
    @Select("SELECT * FROM public_site WHERE ISNULL(check_time)")
    List<PublicSite> selectUnchecked();

}
