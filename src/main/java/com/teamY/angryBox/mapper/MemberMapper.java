package com.teamY.angryBox.mapper;


import com.teamY.angryBox.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Member;

@Mapper
public interface MemberMapper {
    public MemberVO selectByEmail(String email);
}
