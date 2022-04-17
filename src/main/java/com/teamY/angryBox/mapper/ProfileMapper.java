package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.ProfileJoinVO;
import com.teamY.angryBox.vo.ProfileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    void insertProfile(ProfileVO profileVO);
    ProfileVO selectProfileByMemberId(int id);
    void updateProfileImage(int id, int fileId);

    ProfileJoinVO selectJoinedProfile(int id);
}
