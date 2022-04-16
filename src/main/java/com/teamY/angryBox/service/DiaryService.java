package com.teamY.angryBox.service;



import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.DiaryVO;
import com.teamY.angryBox.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FileRepository fileRepository;

    //if 파일 있으면 -> diary insert + select + file insert 프로시저
    //else 파일 없으면 -> diary insert만

    public int registerDiary(DiaryVO diary) {
        //diaryRepository.insertDiary(diary);
        return diaryRepository.insertDiary(diary);
    }

    public void registerDiaryFile(int diaryId, int fileId) {
        diaryRepository.insertDiaryFile(diaryId, fileId);
    }



    //diary 등록 메소드 생성 + diary id 조회 후 리턴

    //file 저장 메소드 호출 + file id 리턴 -> controller에서 for each로 메소드 호출 ex) https://hyeounstory.tistory.com/90

    //file_diary 저장 메소드 생성

}
