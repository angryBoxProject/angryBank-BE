package com.teamY.angryBox.service;



import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.DiaryVO;
import com.teamY.angryBox.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void registerDiary(DiaryVO diary, MultipartFile[] file) {
        int diaryId = diaryRepository.insertDiary(diary);

        List<Integer> fileIdList = new ArrayList<>();
        if(file != null) {
            for(MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for(int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i));
            }
        }
    }


    //diary 등록 메소드 생성 + diary id 조회 후 리턴

    //file 저장 메소드 호출 + file id 리턴 -> controller에서 for each로 메소드 호출 ex) https://hyeounstory.tistory.com/90

    //file_diary 저장 메소드 생성

}
