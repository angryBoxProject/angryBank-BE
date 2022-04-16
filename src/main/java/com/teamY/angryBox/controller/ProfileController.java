package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.vo.FileVO;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final FileRepository fileRepository;
    private final MemberService memberService;

    @PostMapping("filetest")
    public FileVO fileTest(MultipartFile file){

        return fileRepository.uploadFile(file);
    }

    @GetMapping("profile")
    public ResponseEntity<ResponseDataMessage> retrieveProfile() {
        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();


        return null;
    }


    @PutMapping("profile/{nickname}")
    public ResponseEntity<ResponseMessage> putNickname(@PathVariable String nickname) {
        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        memberService.updateMemberNickname(memberVO.getId(), nickname);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, "닉네임 변경 성공", ""), HttpStatus.OK);

    }


}
