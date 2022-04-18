package com.teamY.angryBox.service;


import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FileRepository fileRepository;

    public int retrieveAngryId(int angryPhaseId) {
        return diaryRepository.selectAngryId(angryPhaseId);
    }

    @Transactional
    public void registerDiary(DiaryVO diary, MultipartFile[] file) {
        int diaryId = diaryRepository.insertDiary(diary);

        List<Integer> fileIdList = new ArrayList<>();
        if(file != null) {
            for(MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for(int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), i+1);
            }
        }
    }

    public List<DiaryVO> retrieveDiaryListInCoinBank(int memberId, int coinBankId) {
        return diaryRepository.selectDiaryListInCoinBank(memberId, coinBankId);
    }

    public List<DiaryVO> retrieveDiaryListInMonth(int memberId, int year, int month) {
        return diaryRepository.selectDiaryListInMonth(memberId, year, month);
    }

    public List<DiaryFileVO> retrieveDiaryDetail(int diaryId, int memberId) {
        if(diaryRepository.selectDiaryDetail(diaryId) == null) {
            //수정 예정
            return null;
        } else if(diaryRepository.selectDiaryDetail(diaryId).get(0).getDiaryVO().getIsPublic() == 0 //비공개 상태이고
        && diaryRepository.selectDiaryDetail(diaryId).get(0).getDiaryVO().getMemberId() != memberId) {  //작성자와 조회자가 같지 않을 경우
            //수정 예정
            return null;
        } else {
            return diaryRepository.selectDiaryDetail(diaryId);
        }

    }

    public int retrieveDiaryMemberId(int diaryId, int memberId) {
        return diaryRepository.selectDiaryMemberId(diaryId, memberId);
    }

    public void removeDiary(int diaryId) {
        diaryRepository.deleteDiary(diaryId);
    }

    @Transactional
    public void modifyDiary(DiaryVO diary, MultipartFile[] file, List removedFileId) {
        //다이어리 내용 변경
        diaryRepository.updateDiary(diary);

        //삭제된 파일 지우기
        if(removedFileId != null) {
            for(int i = 0; i < removedFileId.size(); i++) {
                int fileId = Integer.parseInt(removedFileId.get(i).toString());
                diaryRepository.deleteFileInDiary(fileId);
            }
        }

        //file 추가된 파일 업로드
        if(file != null) {
            List<Integer> fileIdList = new ArrayList<>();
            int diaryId = diary.getId();
            int fileNo = diaryRepository.selectMaxFileNo(diaryId);
            for(MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for(int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), fileNo+1);
            }
        }
    }

}
