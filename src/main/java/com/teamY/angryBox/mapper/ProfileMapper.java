package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.ProfileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    public void insertProfile(ProfileVO profileVO);
}
