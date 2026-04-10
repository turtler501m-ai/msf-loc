package com.ktmmobile.mcp.review.dao;

import com.ktmmobile.mcp.review.dto.ReviewDto;
import com.ktmmobile.mcp.review.dto.ReviewImgDto;

import java.util.List;

public interface ReviewDao {

    int selectReviewTotalCnt(ReviewDto reviewDto);

    List<ReviewDto> selectReviewListByNoti(ReviewDto reviewDto, int skipResult, int maxResult);

    List<ReviewDto> selectBestReviewList(ReviewDto reviewDto);

    List<ReviewImgDto> selectReviewImgList(ReviewDto reviewDto);

    boolean deleteReview(int reviewSeq);
    boolean deleteAllReviewImg(int reviewSeq);

    ReviewDto selectReviewByReviewSeq(int reviewSeq);

    int insertReview(ReviewDto reviewDto);

    int insertReviewImg(ReviewImgDto reviewImgDto);

}
