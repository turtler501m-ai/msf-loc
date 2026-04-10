package com.ktmmobile.mcp.requestReview.service;

import java.util.List;

import com.ktmmobile.mcp.requestReview.dto.McpRequestReviewImgDto;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;
import com.ktmmobile.mcp.requestReview.dto.ReviewDataInfo;

public interface RequestReviewService {

         /**
        * @Description : 유심 > 후불유심 > 개통후기
        * @param RequestReviewDto
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 20.
        */
        public int selectUsimRequestReviewTotalCnt(RequestReviewDto requestReviewDto);
        public List<RequestReviewDto> selectUsimRequestReviewList(RequestReviewDto requestReviewDto,int skipResult,int maxResult);



        public List<RequestReviewDto> selectReviewDataList(ReviewDataInfo reviewDataInfo, int skipResult, int maxResult) ;

        /**
        * @Description : 사용자 정보 조회
        * @param contractNum
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 20.
        */
        public RequestReviewDto selectMcpRequestData(String contractNum);
        public RequestReviewDto selectMspJuoSubInfo(String contractNum);
        public String selectUserInfo(String contractNum);

        /**
        * @Description : 리뷰조회
        * @param contractNum
        * @return
        * @Author : ant
        * @Create Date : 2016. 1. 20.
        */
        public int selectCustReviewCnt(String contractNum);

        public int insertMcpRequestReview(RequestReviewDto requestReviewDto);
        public int insertMcpRequestReviewImg(McpRequestReviewImgDto mcpRequestReviewImgDto);





        /**
         * @Description : 관리자 리뷰 업데이트
         * @param requestReviewDto
         * @return
         * @Author : paier
         * @Create Date : 2020. 10. 29.
         */
        public boolean updateMcpRequestReview(RequestReviewDto requestReviewDto);

        /**
         * @Description : 관리자 리뷰 삭제
         * @param requestReviewDto
         * @return
         * @Author : paier
         * @Create Date : 2020. 10. 29.
         */
        public boolean deleteMcpRequestReview(RequestReviewDto requestReviewDto);
        public boolean deleteMcpRequestReviewImg(RequestReviewDto requestReviewDto);
        public boolean deleteAnswerReview(RequestReviewDto requestReviewDto);

        public List<RequestReviewDto> selectMypageReviewList(RequestReviewDto requestReviewDto, int skipResult, int maxResult);

        /** 코드관리로 review 좋아요 표현 제한 */
        public RequestReviewDto selectReviewLimit(RequestReviewDto rquestReviewDto);

        public List<RequestReviewDto> selectRequestReviewList(RequestReviewDto requestReviewDto, int skipResult,
                int maxResult);

        public void modifyReviewHit(RequestReviewDto requestReviewDto);

        public RequestReviewDto selectRequestReview(RequestReviewDto requestReviewDto);

        public List<RequestReviewDto> reviewCodeList(RequestReviewDto requestReviewDto);

        public List<RequestReviewDto> reviewAnswerList(RequestReviewDto requestReviewDto);

        public List<RequestReviewDto> reviewMakeList(RequestReviewDto requestReviewDto);

        public int insertReviewMake(RequestReviewDto requestReviewDto);

        public List<RequestReviewDto> selectBestReviewList(RequestReviewDto requestReviewDto);

        public List<RequestReviewDto> reviewIdList(RequestReviewDto requestReviewDto);
}
