package com.teamY.angryBox.service;

import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.repository.ProfileRepository;
import com.teamY.angryBox.utils.FileManager;
import com.teamY.angryBox.vo.FileVO;
import com.teamY.angryBox.vo.MemberVO;
import com.teamY.angryBox.vo.ProfileJoinVO;
import com.teamY.angryBox.vo.ProfileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final FileRepository fileRepository;

    public void registerProfile(MemberVO memberVO, int fileId) {

        ProfileVO profileVO = new ProfileVO(memberVO.getId(), fileId);
        profileRepository.insertProfile(profileVO);
    }

    @Transactional
    public FileVO changeProfileImage(int memberId, MultipartFile file) {

        ProfileVO profileVO = profileRepository.selectProfileByMemberId(memberId);
        FileVO currentFileVO = fileRepository.findById(profileVO.getFileId());

        FileVO newFileVO = null;
        if(file.getSize() <= 0)
            newFileVO = fileRepository.findById(1); // 기본 프로필 이미지로 변경else
        else
            newFileVO = fileRepository.uploadFile(file);


        profileRepository.updateProfileImage(profileVO.getId(), newFileVO.getId());

        if(currentFileVO.getId() != 1) { // 기본 프로필 이미지가 아닐 때
            log.info("currentFile : " + currentFileVO.getSystemFileName());
            fileRepository.deleteFile(currentFileVO);
        }
        return newFileVO;
    }

    public ProfileJoinVO inquiryFullProfile(int memberId) {
        return profileRepository.selectJoinedProfile(memberId);
    }

}
