package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.ProfileMapper;
import com.teamY.angryBox.vo.ProfileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProfileRepository {
    private final ProfileMapper profileMapper;

    public void insertProfile(ProfileVO profileVO) {
        profileMapper.insertProfile(profileVO);
    }
}
