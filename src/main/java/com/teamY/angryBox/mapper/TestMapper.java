package com.teamY.angryBox.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {
    @Select("SELECT email FROM member where id = #{id}")
    public String test(int id);
}
