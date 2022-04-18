package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.service.ProfileService;
import com.teamY.angryBox.vo.FileVO;
import com.teamY.angryBox.vo.MemberVO;
import com.teamY.angryBox.vo.ProfileJoinVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final FileRepository fileRepository;
    private final MemberService memberService;
    private final ProfileService profileService;

    @PostMapping("filetest")
    public FileVO fileTest(MultipartFile file){

        return fileRepository.uploadFile(file);
    }

    @GetMapping("profile")
    public ResponseEntity<ResponseDataMessage> retrieveProfile() {
        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        ProfileJoinVO profile = profileService.inquiryFullProfile(memberVO.getId());

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nickname", profile.getMember().getNickname());
        data.put("file", "/images/" + profile.getFile().getSystemFileName());
        data.put("diaryCount", profile.getMember().getDiaryCount());
        data.put("sendTodakCount", profile.getMember().getSendTodakCount());
        data.put("recieveTodakCount", profile.getMember().getRecieveTodakCount());

        return new ResponseEntity<ResponseDataMessage>(new ResponseDataMessage(true, "프로필 조회 성공", "", data), HttpStatus.OK);

    }


    @PutMapping("profile/{nickname}")
    public ResponseEntity<ResponseMessage> putNickname(@PathVariable String nickname) {
        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        memberService.updateMemberNickname(memberVO.getId(), nickname);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, "닉네임 변경 성공", ""), HttpStatus.OK);

    }

    @PutMapping("profile/image")
    public ResponseEntity<ResponseDataMessage> putProfileImage(@RequestParam MultipartFile file) {
        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();
        log.info("fileSize : " + file.getSize());
        FileVO fileVO = profileService.changeProfileImage(memberVO.getId(), file);

        Map<String, Object> data = new HashMap<>();
        //log.info("filename? " + fileVO.getSystemFileName());
        data.put("file", "/images/" + fileVO.getSystemFileName());
        return new ResponseEntity<ResponseDataMessage>(new ResponseDataMessage(true, "이미지 변경 성공", "", data), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource showImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + "/upload/" + filename);
    }

}
