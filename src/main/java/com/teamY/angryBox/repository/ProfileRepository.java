package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.ProfileMapper;
import com.teamY.angryBox.vo.ProfileJoinVO;
import com.teamY.angryBox.vo.ProfileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProfileRepository {
    private final ProfileMapper mapper;

    public void insertProfile(ProfileVO profileVO) {
        mapper.insertProfile(profileVO);
    }

    public ProfileVO selectProfileByMemberId(int id) {
        return mapper.selectProfileByMemberId(id);
    }

    public void updateProfileImage(int id, int fileId) {
        mapper.updateProfileImage(id, fileId);
    }

    public ProfileJoinVO selectJoinedProfile(int id) {
        return mapper.selectJoinedProfile(id);
    }
}
