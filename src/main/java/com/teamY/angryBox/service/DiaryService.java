package com.teamY.angryBox.service;



import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.DiaryFileVO;
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

    public List<DiaryVO> retrieveDiaryListInCoinBank(int memberId, int coinBankId) {
        return diaryRepository.selectDiaryListInCoinBank(memberId, coinBankId);
    }

    public List<DiaryFileVO> retrieveDiaryDetaile(int diaryId) {
        return diaryRepository.selectDiaryDetail(diaryId);
    }


}
