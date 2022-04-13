package com.teamY.angryBox.service;

import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public void registerMember(MemberVO member) {
        memberRepository.insertMember(member);
    }

    public void setLogoutToken(String key, long expire) {
        //key = key.substring(7);
        stringRedisTemplate.opsForValue().set(key, "logout");
        stringRedisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    public String getIsLogout(String key) {
        //key = key.substring(7);
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();

        if(stringValueOperations.get(key) == null){
            log.info("Redis key null");
            return null;
        }

        log.info("Redis key : {}", key);
        log.info("Redis value : {}", stringValueOperations.get(key));
        return stringValueOperations.get(key);
    }

}
