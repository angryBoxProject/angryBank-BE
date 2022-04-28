package com.teamY.angryBox.service;


import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.error.customException.SQLInquiryException;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    private final SimpMessagingTemplate template; // 특정 broker 로 메세지 전달

    @Transactional
    public void addDiary(DiaryVO diaryVO, MultipartFile[] file) {

        if (diaryRepository.checkAngryId(diaryVO.getAngryPhaseId()) == 0) {
            throw new InvalidRequestException("분노수치 잘못 들어옴");
        } else if ( diaryRepository.checkCoinBankExpired(diaryVO.getCoinBankId(), diaryVO.getMemberId()) != 1) {
            throw new InvalidRequestException("적금 번호 잘못 들어옴");
        } else {
            DiaryVO insertedDiary = diaryRepository.insertDiary(diaryVO);
            log.info("inserted diary : " + insertedDiary);
            int diaryId = insertedDiary.getId();
            log.info("방금 작성된 다이어리 id : " + diaryId);

            List<Integer> fileIdList = new ArrayList<>();
            if (file != null) {
                for (MultipartFile f : file) {
                    fileIdList.add(fileRepository.uploadFile(f).getId());
                }
                for (int i = 0; i < fileIdList.size(); i++) {
                    diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), i + 1);
                }
            }


            if(diaryVO.isPublic() == true) {
                log.info(insertedDiary.toString());
                template.convertAndSend("/sub/topic/bamboo", insertedDiary);
            }
        }
    }

    public List<DiaryVO> getDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size) {
        if(diaryRepository.checkCoinBankMemberId(coinBankId, memberId) == 0) {
            throw new InvalidRequestException("적금 번호 확인 필요");
        }
        if(lastDiaryId == 0) {
            lastDiaryId = diaryRepository.selectLastIdInCoinBank(memberId, coinBankId) + 1;
            if(lastDiaryId == 0) {
                throw new InvalidRequestException("해당 적금에 작성한 다이어리 없음");
            }
        }
        return diaryRepository.selectDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size);
    }

    public List<DiaryVO> getDiaryListInMonth(int memberId, String date, int lastDiaryId, int size) {
        int writeYear = Integer.parseInt(date.substring(0, 4));
        int writeMonth = Integer.parseInt(date.substring(5, 7));

        if(lastDiaryId == 0) {
            lastDiaryId = diaryRepository.selectLastIdInMonth(memberId, writeYear, writeMonth) + 1;
            if(lastDiaryId == 0) {
                throw new InvalidRequestException("해당 월에 작성한 다이어리 없음");
            }
        }
        return diaryRepository.selectDiaryListInMonth(memberId, writeYear, writeMonth, lastDiaryId, size);
    }

    public List<DiaryVO> getDailyTop(String date, int lastDiaryId, int size) {
        int writeYear = Integer.parseInt(date.substring(0, 4));
        int writeMonth = Integer.parseInt(date.substring(5, 7));
        int writeDay = Integer.parseInt(date.substring(8, 10));

        if(diaryRepository.selectDailyLastId(writeYear, writeMonth, writeDay) == -1) {
            throw new InvalidRequestException("해당 날짜에 TOP 다이어리 없음");
        } else {
            return diaryRepository.selectDailyTop(writeYear, writeMonth, writeDay, lastDiaryId, size);
        }
    }

    public List<DiaryVO> getTodayTop(int lastDiaryId, int size) {
        if(diaryRepository.selectTodayLastId() == -1) {
            throw new SQLInquiryException("오늘의 TOP 다이어리 없음");
        } else {
            return diaryRepository.selectTodayTop(lastDiaryId, size);
        }
    }



    public Map<String, Object> getDiaryDetail(int diaryId, int memberId) {
        List<DiaryFileVO> dfVO = diaryRepository.selectDiaryDetail(diaryId);
        if (dfVO.size() == 0) {
            throw new InvalidRequestException("해당 다이어리 존재하지 않음");
        } else if (dfVO.get(0).getDiaryVO().isPublic() == false //비공개 상태이고
                && dfVO.get(0).getDiaryVO().getMemberId() != memberId) {  //작성자와 조회자가 같지 않을 경우
            throw new InvalidRequestException("비밀글 당사자 외 조회 불가");
        } else {
            Map<String, Object> data = new LinkedHashMap<>();

            for (int i = 0; i < dfVO.size(); i++) {
                data.put("diary", dfVO.get(i).getDiaryVO());
                if (dfVO.get(i).getFileVO() != null) {
                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("fileLink", "/images/" + dfVO.get(i).getFileVO().getSystemFileName());
                    fileInfo.put("fileNo", dfVO.get(i).getFileVO().getFileNo());
                    fileInfo.put("fileId", dfVO.get(i).getFileVO().getId());
                    data.put("file" + (i + 1), fileInfo);
                }
            }
            return data;
        }
    }

    public void removeDiary(int diaryId, int memberId) {
        if(diaryRepository.checkDiaryId(diaryId) == 0) {
            throw new InvalidRequestException("해당 다이어리 존재하지 않음");
        } else if(diaryRepository.checkDiaryMemberId(diaryId, memberId) == 0) {
            throw new InvalidRequestException("작성자와 삭제자 불일치");
        } else {
            diaryRepository.deleteDiary(diaryId, memberId);
        }
    }

    @Transactional
    public void changeDiary(DiaryVO diary, MultipartFile[] file, List removedFileId) {

        if (diaryRepository.checkAngryId(diary.getAngryPhaseId()) == 0) {
            throw new InvalidRequestException("분노수치 잘못 들어옴");
        } else if(diaryRepository.checkDiaryMemberId(diary.getId(), diary.getMemberId()) == 0) {
            throw new InvalidRequestException("작성자와 수정자 불일치");
        }

        //다이어리 내용 변경
        diaryRepository.updateDiary(diary);

        //삭제된 파일 지우기
        if (removedFileId != null) {
            for (int i = 0; i < removedFileId.size(); i++) {
                int fileId = Integer.parseInt(removedFileId.get(i).toString());
                if(diaryRepository.checkFileInDiary(diary.getId(), fileId) == 0) {
                    throw new InvalidRequestException("삭제된 파일 번호 확인 필요");
                }
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


    public List<DiaryVO> searchDiary(String keyword, int lastDiaryId, int size) {
        return diaryRepository.searchDiary(keyword, lastDiaryId, size);
    }

    public List<DiaryVO> bambooGrove(int lastDiaryId, int size) {
        if(lastDiaryId == 0)
            lastDiaryId = diaryRepository.selectLastId() + 1;
//        else if(lastDiaryId < 0)
//            throw new

        return diaryRepository.bambooGrove(lastDiaryId, size);
    }
}
