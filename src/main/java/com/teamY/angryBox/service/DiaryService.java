package com.teamY.angryBox.service;


import com.teamY.angryBox.error.customException.InvalidRequestException;
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

//    public int getAngryId(int angryPhaseId) {
//        return diaryRepository.selectAngryId(angryPhaseId);
//    }
//
//    public int getCoinBankMemberId(int coinBankId, int memberId) {
//        return diaryRepository.selectCoinBankMemberId(coinBankId, memberId);
//    }

    @Transactional
    public void addDiary(DiaryVO diary, MultipartFile[] file) {

        if (diaryRepository.selectAngryId(diary.getAngryPhaseId()) == 0) {
            throw new InvalidRequestException("분노수치 잘못 들어옴");
        } else if ( diaryRepository.selectCoinBankMemberId(diary.getCoinBankId(), diary.getMemberId()) != 1) {
            throw new InvalidRequestException("적금 번호 잘못 들어옴");
        } else {
            int diaryId = diaryRepository.insertDiary(diary);

            List<Integer> fileIdList = new ArrayList<>();
            if (file != null) {
                for (MultipartFile f : file) {
                    fileIdList.add(fileRepository.uploadFile(f).getId());
                }
                for (int i = 0; i < fileIdList.size(); i++) {
                    diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), i + 1);
                }
            }
        }
    }

    public List<DiaryVO> getDiaryListInCoinBank(int memberId, int coinBankId) {
        return diaryRepository.selectDiaryListInCoinBank(memberId, coinBankId);
    }

    public List<DiaryVO> getDiaryListInMonth(int memberId, int year, int month) {
        return diaryRepository.selectDiaryListInMonth(memberId, year, month);
    }

    public List<DiaryFileVO> getDiaryDetail(int diaryId, int memberId) {
        if (diaryRepository.selectDiaryDetail(diaryId) == null) {
            throw new InvalidRequestException("diaryId 잘못 들어옴");
        } else if (diaryRepository.selectDiaryDetail(diaryId).get(0).getDiaryVO().getIsPublic() == 0 //비공개 상태이고
                && diaryRepository.selectDiaryDetail(diaryId).get(0).getDiaryVO().getMemberId() != memberId) {  //작성자와 조회자가 같지 않을 경우
            throw new InvalidRequestException("비밀글 당사자 외 조회 불가");
        } else {
            return diaryRepository.selectDiaryDetail(diaryId);
        }

    }

    public int getDiaryMemberId(int diaryId, int memberId) {
        return diaryRepository.selectDiaryMemberId(diaryId, memberId);
    }

    public void removeDiary(int diaryId, int memberid) {
        diaryRepository.deleteDiary(diaryId, memberid);
    }

    @Transactional
    public void changeDiary(DiaryVO diary, MultipartFile[] file, List removedFileId) {

        if (diaryRepository.selectAngryId(diary.getAngryPhaseId()) == 0) {
            throw new InvalidRequestException("분노수치 잘못 들어옴");
        } else if(diaryRepository.selectDiaryMemberId(diary.getId(), diary.getMemberId()) == 0) {
            throw new InvalidRequestException("작성자와 수정자 불일치");
        }

        //다이어리 내용 변경
        diaryRepository.updateDiary(diary);

        //삭제된 파일 지우기
        if (removedFileId != null) {
            for (int i = 0; i < removedFileId.size(); i++) {
                int fileId = Integer.parseInt(removedFileId.get(i).toString());
                diaryRepository.deleteFileInDiary(fileId);
            }
        }

        //file 추가된 파일 업로드
        if (file != null) {
            List<Integer> fileIdList = new ArrayList<>();
            int diaryId = diary.getId();
            int fileNo = diaryRepository.selectMaxFileNo(diaryId);
            for (MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for (int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), fileNo + 1);
            }
        }
    }

}
