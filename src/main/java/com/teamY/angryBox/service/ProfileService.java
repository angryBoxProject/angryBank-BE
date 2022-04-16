package com.teamY.angryBox.service;

import com.teamY.angryBox.repository.ProfileRepository;
import com.teamY.angryBox.vo.MemberVO;
import com.teamY.angryBox.vo.ProfileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public void registerProfile(MemberVO memberVO, int fileId) {

        ProfileVO profileVO = new ProfileVO(memberVO.getId(), fileId);
        profileRepository.insertProfile(profileVO);
    }
}
