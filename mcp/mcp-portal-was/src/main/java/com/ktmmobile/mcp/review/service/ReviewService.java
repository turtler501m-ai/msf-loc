package com.ktmmobile.mcp.review.service;

import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.review.dto.ReviewDto;
import com.ktmmobile.mcp.review.dto.ReviewImgDto;

import java.util.List;

public interface ReviewService {

    int selectReviewTotalCnt(ReviewDto reviewDto);

    List<ReviewDto> selectReviewListByNoti(ReviewDto reviewDto, int skipResult, int maxResult);

    List<ReviewDto> selectBestReviewList(ReviewDto reviewDto);

    boolean deleteReview(int reviewSeq);

    ReviewDto getReviewByReviewSeq(int reviewSeq);

    int insertReviewAndImage(ReviewDto reviewDto, ReviewImgDto reviewImgDto);

    boolean checkOwnReview(int reviewSeq);

    List<NmcpCdDtlDto> getReviewEventCdList(String reviewEventType);
}
