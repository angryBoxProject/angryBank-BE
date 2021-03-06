package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.MemberMapper;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final MemberMapper mapper;
    private final StringRedisTemplate stringRedisTemplate;

    public MemberVO findByEmail(String email) {
        return mapper.selectByEmail(email);
    }

    public void insertMember(MemberVO member) { mapper.insertMember(member);}

    public void setLogoutToken(String key, long expire) {
        stringRedisTemplate.opsForValue().set(key, "logout");
        stringRedisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    public void setRefreshToken(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    public void deleteRefreshToken(String key) {
        stringRedisTemplate.delete(key);
    }
    public String getIsLogout(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        return stringValueOperations.get(key);
    }

    public String getTokenSet(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        return stringValueOperations.get(key);
    }

    public String findPassword(String email) {
        return mapper.selectByEmail(email).getPassword();
    }

    public void updateMemberNickname(int id, String nickname) {
        mapper.updateMemberNickname(id, nickname);
    }

    public void updateMemberPassword(int id, String password) {
        mapper.updateMemberPassword(id, password);
    }

    public MemberVO selectById(int id) {
        return mapper.selectById(id);
    }

    public void updateCoinBankId(int id, int bankId){
        mapper.updateCoinBankId(id, bankId);
    }

    public void updateCoinBankIdToNull(int id){
        mapper.updateCoinBankIdToNull(id);
    }
    public int selectMemberCurBank(int id){
        return mapper.selectMemberCurBank(id);
    }

    public void updateLastLogin(int id) {
        mapper.updateLastLogin(id);
    }

    public void deleteMember(int id) {
        mapper.deleteMember(id);
    }

    public int checkDeleted(int id) {
        return mapper.checkDeleted(id);
    }
}
