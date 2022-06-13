package com.teamY.angryBox.service;


import com.teamY.angryBox.dto.DiaryDTO;
import com.teamY.angryBox.dto.FilterDTO;
import com.teamY.angryBox.dto.InterimDiaryDTO;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.CoinBankRepository;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.FileRepository;
import com.teamY.angryBox.vo.*;

import com.teamY.angryBox.repository.MemberRepository;

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
    private final CoinBankRepository coinBankRepository;
    private final MemberRepository memberRepository;

    private final SimpMessagingTemplate template; // 특정 broker 로 메세지 전달


    @Transactional
    public void addDiary(DiaryDTO diaryDTO, MultipartFile[] file, boolean isPublic, int memberId) {

        checkAngryPhaseId(diaryDTO.getAngryPhaseId());
        int coinBankId = memberRepository.selectMemberCurBank(memberId);
        checkCoinBank(coinBankId, memberId);
        if(diaryDTO.getTitle().length() == 0) {
            throw new InvalidRequestException("title 빈 문자열 불가");
        }

        DiaryDTO diary = new DiaryDTO(memberId, diaryDTO.getTitle(), diaryDTO.getContent(), diaryDTO.getAngryPhaseId(), isPublic, coinBankId);

        DiaryVO insertedDiary = diaryRepository.insertDiary(diary);
        int diaryId = insertedDiary.getId();

        List<Integer> fileIdList = new ArrayList<>();
        if (file != null) {
            for (MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for (int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), i + 1);
            }
        }

        if (diary.isPublic() == true) {
            log.info(insertedDiary.toString());
            template.convertAndSend("/sub/topic/bamboo", insertedDiary);
        }
    }


    public Map<String, Object> getDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size) {
        checkCoinBank(coinBankId, memberId);

        Map<String, Object> data = new HashMap<>();
        if (lastDiaryId == 0) {
            lastDiaryId = diaryRepository.selectLastIdInCoinBank(memberId, coinBankId) + 1;
            if (lastDiaryId == 0) {
                data.put("null", null);
            } else {
                data.put("diaryListInCoinBank", diaryRepository.selectDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size));
            }
        } else {
            data.put("diaryListInCoinBank", diaryRepository.selectDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size));
        }
        return data;
    }

    public Map<String, Object> getDiaryListInMonth(int memberId, String writeDate, int lastDiaryId, int size) {
        Map<String, Object> data = new HashMap<>();
        if (lastDiaryId == 0) {
            lastDiaryId = diaryRepository.selectLastIdInMonth(memberId, writeDate) + 1;
            if (lastDiaryId == 0) {
                data.put("null", null);
            } else {
                data.put("diaryListInMonth", diaryRepository.selectDiaryListInMonth(memberId, writeDate, lastDiaryId, size));
            }
        } else {
            data.put("diaryListInMonth", diaryRepository.selectDiaryListInMonth(memberId, writeDate, lastDiaryId, size));
        }
        return data;
    }

    public Map<String, Object> getDailyTop(String writeDate, int lastDiaryId, int size) {
        Map<String, Object> data = new HashMap<>();
        if (diaryRepository.selectDailyLastId(writeDate) == -1) {
            data.put("null", null);
        } else {
            data.put("dailyTopDiary", diaryRepository.selectDailyTop(writeDate, lastDiaryId, size));
        }
        return data;
    }

    public Map<String, Object> getTodayTop(int lastDiaryId, int size) {
        Map<String, Object> data = new HashMap<>();
        if (diaryRepository.selectTodayLastId() == -1) {
            data.put("null", null);
        } else {
            data.put("todayTopDiary", diaryRepository.selectTodayTop(lastDiaryId, size));
        }
        return data;
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
        checkDiary(diaryId, memberId);
        diaryRepository.deleteDiary(diaryId, memberId);
    }


    @Transactional
    public void changeDiary(DiaryDTO diaryDTO, MultipartFile[] file) {
        int diaryId = diaryDTO.getId();
        checkDiary(diaryId, diaryDTO.getMemberId());
        checkAngryPhaseId(diaryDTO.getAngryPhaseId());

        //다이어리 내용 변경
        diaryRepository.updateDiary(diaryDTO);

        //삭제된 파일 지우기
        List<Integer> removedFileId = diaryDTO.getRemovedFileId();
        if (removedFileId != null) {
            for (int i = 0; i < removedFileId.size(); i++) {
                int fileId = Integer.parseInt(removedFileId.get(i).toString());
                if (diaryRepository.checkFileInDiary(diaryId, fileId) == 0) {
                    throw new InvalidRequestException("삭제된 파일id 확인 필요");
                }
                diaryRepository.deleteFileInDiary(fileId);
            }
        }

        //file 추가된 파일 업로드
        if (file != null) {
            List<Integer> fileIdList = new ArrayList<>();
            int fileNo = diaryRepository.selectMaxFileNo(diaryId);
            for (MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadFile(f).getId());
            }
            for (int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertDiaryFile(diaryId, fileIdList.get(i), fileNo + i + 1);
            }
        }
    }


    public List<DiaryVO> searchDiary(String keyword, int lastDiaryId, int size) {
        return diaryRepository.searchDiary(keyword, lastDiaryId, size);
    }

    public List<DiaryVO> bambooGrove(int lastDiaryId, int size, FilterDTO filter) {
        if (lastDiaryId == 0)
            lastDiaryId = diaryRepository.selectLastId() + 1;
//        else if(lastDiaryId < 0)
//            throw new

        StringBuilder filterStr;

        if(filter == null) {
            filterStr = new StringBuilder();
            filterStr.append("1");
        }
        else {
            filterStr = filter.getFilter();
        }

        return diaryRepository.bambooGrove(lastDiaryId, size, filterStr.toString());
    }


    @Transactional
    public void addInterimDiary(InterimDiaryDTO interimDiaryDTO, MultipartFile[] file) {
        checkAngryPhaseId(interimDiaryDTO.getAngryPhaseId());

        int diaryId = diaryRepository.insertInterimDiary(interimDiaryDTO);
        List<Integer> fileIdList = new ArrayList<>();
        if (file != null) {
            for (MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadInterimFile(f).getId());
            }
            for (int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertInterimDiaryFile(diaryId, fileIdList.get(i), i + 1);
            }
        }
    }

    public Map<String, Object> getInterimDiaryDetail(int diaryId, int memberId) {
        checkInterimDiary(diaryId, memberId);

        List<InterimDiaryFileVO> dfVO = diaryRepository.selectInterimDiaryDetail(diaryId);
        Map<String, Object> data = new LinkedHashMap<>();
        for (int i = 0; i < dfVO.size(); i++) {
            data.put("diary", dfVO.get(i).getInterimDiaryVO());
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


    public int getInterimDiaryCount(int memberId) {
        return diaryRepository.countInterimDiary(memberId);
    }

    public Map<String, Object> getInterimDiaryList(int memberId, int lastDiaryId, int size) {
        Map<String, Object> data = new HashMap<>();
        if (lastDiaryId == 0) {
            lastDiaryId = diaryRepository.selectInterimLastId(memberId) + 1;
            if (lastDiaryId == 0) {
                data.put("null", null);
            } else {
                data.put("diary", diaryRepository.selectInterimDiaryList(memberId, lastDiaryId, size));
            }
        } else {
            data.put("diary", diaryRepository.selectInterimDiaryList(memberId, lastDiaryId, size));
        }
        return data;
    }


    @Transactional
    public void removeInterimDiary(int diaryId, int memberId) {
        checkInterimDiary(diaryId, memberId);

        List<Integer> fileList = diaryRepository.selectFileInInterimDiary(diaryId);

        diaryRepository.deleteInterimDiary(diaryId);

        if (fileList.size() != 0) {
            for (int i = 0; i < fileList.size(); i++) {
                diaryRepository.deleteInterimFile(fileList.get(i));
            }
        }
    }

    @Transactional
    public void changeInterimDiary(InterimDiaryDTO interimDiaryDTO, MultipartFile[] file) {

        checkAngryPhaseId(interimDiaryDTO.getAngryPhaseId());
        checkInterimDiary(interimDiaryDTO.getId(), interimDiaryDTO.getMemberId());

        //다이어리 내용 변경
        diaryRepository.updateInterimDiary(interimDiaryDTO);
        int diaryId = interimDiaryDTO.getId();

        //삭제된 파일 지우기
        List<Integer> removedFileId = interimDiaryDTO.getRemovedFileId();
        if (removedFileId != null) {
            for (int i = 0; i < removedFileId.size(); i++) {
                int fileId = Integer.parseInt(removedFileId.get(i).toString());
                if (diaryRepository.checkFileInInterimDiary(diaryId, fileId) == 0) {
                    throw new InvalidRequestException("삭제된 파일 번호 확인 필요");
                }
                diaryRepository.deleteFileInInterimDiary(fileId);
            }
        }

        //file 추가된 파일 업로드
        if (file != null) {
            List<Integer> fileIdList = new ArrayList<>();
            int fileNo = diaryRepository.selectMaxInterimFileNo(diaryId);
            for (MultipartFile f : file) {
                fileIdList.add(fileRepository.uploadInterimFile(f).getId());
            }
            for (int i = 0; i < fileIdList.size(); i++) {
                diaryRepository.insertInterimDiaryFile(diaryId, fileIdList.get(i), fileNo + 1 + i);
            }
        }
    }

    public void checkDiary(int diaryId, int memberId) {
        DiaryVO diaryVO = diaryRepository.checkDiary(diaryId);
        if (diaryVO == null) {
            throw new InvalidRequestException("해당 다이어리 존재하지 않음");
        } else if (diaryVO.isDeleted()) {
            throw new InvalidRequestException("삭제된 다이어리");
        } else if (diaryVO.getMemberId() != memberId) {
            throw new InvalidRequestException("작성자와 요청자 불일치");
        }
    }

    public void checkInterimDiary(int diaryId, int memberId) {
        if (diaryRepository.checkInterimDiaryId(diaryId) == 0) {
            throw new InvalidRequestException("해당 임시 다이어리 존재하지 않음");
        } else if (diaryRepository.checkInterimDiaryMemberId(diaryId, memberId) == 0) {
            throw new InvalidRequestException("임시 다이어리 작성자와 요청자 불일치");
        }
    }

    public void checkAngryPhaseId(int angryPhaseId) {
        if (diaryRepository.checkAngryId(angryPhaseId) == 0) {
            throw new InvalidRequestException("분노수치 잘못 들어옴");
        }
    }

    public void checkCoinBank(int coinBankId, int memberId) {
        if(coinBankId == 0) {
            throw new InvalidRequestException("사용 가능한 적금 없음(적금 생성 필요)");
        } else {
            CoinBankVO coinBankVO = coinBankRepository.selectById(coinBankId);
            if (coinBankVO == null || coinBankVO.getMemberId() != memberId) {
                throw new InvalidRequestException("적금 번호 확인 필요");
            }
        }

        //else if (coinBankRepository.selectBankExpired(coinBankId).isExpired()) { //0 : 사용 가능인데 false / 1 : 만료인데 ture -> true이면 에러
        //            throw new InvalidRequestException("만료된 적금");
        //        }
    }

    public List<GalleryDiaryVO> getGallery(int lastDiaryId, int size) {

        if(lastDiaryId == 0)
            lastDiaryId = diaryRepository.selectGalleryLastId();

        return diaryRepository.selectGallery(lastDiaryId, size);
    }
}
