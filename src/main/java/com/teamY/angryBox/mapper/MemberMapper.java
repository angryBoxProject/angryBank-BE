package com.teamY.angryBox.mapper;


import com.teamY.angryBox.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Member;

@Mapper
public interface MemberMapper {
    MemberVO selectByEmail(String email);
    void insertMember(MemberVO member);
    void updateMemberNickname(int id, String nickname);
    void updateMemberPassword(int id, String password);
    MemberVO selectById(int id);
}
