package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.MemberMapper;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final MemberMapper mapper;

    public MemberVO findByEmail(String email) {
        return mapper.selectByEmail(email);
    }
    public void insertMember(MemberVO member) { mapper.insertMember(member);}
}
