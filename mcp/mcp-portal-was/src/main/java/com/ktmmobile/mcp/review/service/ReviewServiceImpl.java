package com.ktmmobile.mcp.review.service;

import com.ktmmobile.mcp.common.dto.FileBoardDTO;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mcash.service.McashServiceImpl;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.review.dao.ReviewDao;
import com.ktmmobile.mcp.review.dto.ReviewDto;
import com.ktmmobile.mcp.review.dto.ReviewImgDto;
import com.ktmmobile.mcp.review.util.ReviewUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ktmmobile.mcp.common.constants.Constants.REVIEW_TYPE_CD;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static Logger logger = LoggerFactory.getLogger(McashServiceImpl.class);

    @Autowired
    FileUtil fileUtil;

    @Autowired
    MypageService mypageService;

    @Autowired
    FCommonSvc fCommonSvc;

    /** 파일업로드 경로 */
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public int selectReviewTotalCnt(ReviewDto reviewDto) {
        return reviewDao.selectReviewTotalCnt(reviewDto);
    }

    @Override
    public List<ReviewDto> selectReviewListByNoti(ReviewDto reviewDto, int skipResult, int maxResult) {
        return reviewDao.selectReviewListByNoti(reviewDto, skipResult, maxResult);
    }

    @Override
    public List<ReviewDto> selectBestReviewList(ReviewDto reviewDto) {
        return reviewDao.selectBestReviewList(reviewDto);
    }

    @Override
    public boolean deleteReview(int reviewSeq) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewSeq(reviewSeq);
        List<ReviewImgDto> reviewImgDtoList = reviewDao.selectReviewImgList(reviewDto);
        if (reviewDao.deleteReview(reviewSeq)) {
            reviewDao.deleteAllReviewImg(reviewSeq);
            for (ReviewImgDto reviewImg : reviewImgDtoList) {
                // 실제 물리적 경로에 해당 파일명을 삭제한다.
                fileUtil.fileDelete(reviewImg.getFilePathNm());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ReviewDto getReviewByReviewSeq(int reviewSeq) {
        return reviewDao.selectReviewByReviewSeq(reviewSeq);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertReviewAndImage(ReviewDto reviewDto, ReviewImgDto reviewImgDto) {
        ReviewDto reviewInsertDto = ReviewUtil.getReviewInsertDto(reviewDto);
        int insertCnt = reviewDao.insertReview(reviewInsertDto);

        reviewImgDto.setReviewSeq(reviewInsertDto.getReviewSeq());
        this.insertReviewImgList(reviewImgDto, reviewDto.getReviewType());

        return insertCnt;
    }

    private int insertReviewImgList(ReviewImgDto reviewImgDto, String reviewType) {
        int insertImgCnt = 0;
        if (Optional.ofNullable(reviewImgDto.getFile()).isPresent()) {
            // 이미지 업로드 실패로 게시물 등록을 못하게 처리
            try {
                // 3. 파일 업로드
                FileBoardDTO fileBoardDTO = null;
                MultipartFile[] file = reviewImgDto.getFile();
                String[] fileAltList = null;
                if (Optional.ofNullable(reviewImgDto.getFileAlt()).isPresent()) {
                    fileAltList = reviewImgDto.getFileAlt().split(",");
                }
                String fileDir = "review" + File.separator + reviewType;

                if (file != null) {
                    ReviewImgDto reviewImgdto = null;
                    for (int i = 0; i < file.length; i++) {
                        FileUploadUtil fileUploadUtil = new FileUploadUtil(fileuploadBase, fileuploadWeb);
                        reviewImgdto = new ReviewImgDto();
                        MultipartFile multipartFile = file[i];

                        if (multipartFile != null && !"".equals(multipartFile.getOriginalFilename())) {   // 파일 유효성 체크
                            fileBoardDTO = fileUploadUtil.upload(multipartFile, fileDir); // 파일업로드
                            String fileNm = multipartFile.getOriginalFilename();
                            String filenameExt = fileNm.substring(fileNm.lastIndexOf(".")+1);
                            String filePathNm = fileBoardDTO.getFilePathNM();
                            if (fileAltList != null && fileAltList.length > 0) {
                                if (!"".equals(fileAltList[i])) {
                                    String fileAlt = fileAltList[i];
                                    if (StringUtil.isBlank(fileAlt)) {
                                        reviewImgdto.setFileAlt("");
                                    } else {
                                        reviewImgdto.setFileAlt(fileAlt);
                                    }
                                }
                            }
                            String fileCapa = StringUtil.NVL(fileBoardDTO.getFileCapa(), "0");
                            int intFileCapa = Integer.parseInt(fileCapa);

                            ReviewDto reviewInfo = this.getReviewByReviewSeq(reviewImgDto.getReviewSeq());
                            reviewImgdto.setReviewSeq(reviewInfo.getReviewSeq());
                            reviewImgdto.setImgSeq(i);
                            reviewImgdto.setFilePathNm(filePathNm);
                            reviewImgdto.setFileType(filenameExt);
                            reviewImgdto.setFileCapa(intFileCapa);
                            reviewImgdto.setRegstId(reviewImgDto.getRegstId());

                            insertImgCnt += reviewDao.insertReviewImg(reviewImgdto);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("exception e=>"+e.getMessage());
                throw e;
            }
        }
        return insertImgCnt;
    }

    @Override
    public boolean checkOwnReview(int reviewSeq) {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        ReviewDto reviewDto = reviewDao.selectReviewByReviewSeq(reviewSeq);

        String contractNum = reviewDto.getContractNum();
        for (McpUserCntrMngDto cntr : cntrList) {
            if (contractNum.equals(cntr.getContractNum())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<NmcpCdDtlDto> getReviewEventCdList(String reviewEventType) {
        List<NmcpCdDtlDto> reviewEventCdList = new ArrayList<>();

        NmcpCdDtlDto reviewTypeCdDto = NmcpServiceUtils.getCodeNmDto(REVIEW_TYPE_CD, reviewEventType);
        if (reviewTypeCdDto != null && !"".equals(reviewTypeCdDto.getExpnsnStrVal1())) {
            NmcpCdDtlDto searchCdDto = new NmcpCdDtlDto();
            searchCdDto.setCdGroupId(reviewTypeCdDto.getExpnsnStrVal1());
            reviewEventCdList = fCommonSvc.getCodeList(searchCdDto);
        }
        return reviewEventCdList;
    }
}
