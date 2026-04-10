package com.ktmmobile.mcp.review.dao;

import com.ktmmobile.mcp.review.dto.ReviewDto;
import com.ktmmobile.mcp.review.dto.ReviewImgDto;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int selectReviewTotalCnt(ReviewDto reviewDto) {
        return (Integer)sqlSessionTemplate.selectOne("ReviewMapper.selectReviewTotalCnt", reviewDto);
    }

    @Override
    public List<ReviewDto> selectReviewListByNoti(ReviewDto reviewDto, int skipResult, int maxResult) {
        return sqlSessionTemplate.selectList("ReviewMapper.selectReviewListByNoti", reviewDto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public List<ReviewDto> selectBestReviewList(ReviewDto reviewDto) {
        return sqlSessionTemplate.selectList("ReviewMapper.selectBestReviewList", reviewDto);
    }

    @Override
    public List<ReviewImgDto> selectReviewImgList(ReviewDto reviewDto) {
        return sqlSessionTemplate.selectList("ReviewMapper.selectReviewImgList", reviewDto);
    }

    @Override
    public boolean deleteReview(int reviewSeq) {
        return 0 < sqlSessionTemplate.delete("ReviewMapper.deleteReview", reviewSeq);
    }

    @Override
    public boolean deleteAllReviewImg(int reviewSeq) {
        return 0 < sqlSessionTemplate.delete("ReviewMapper.deleteAllReviewImg", reviewSeq);
    }

    @Override
    public ReviewDto selectReviewByReviewSeq(int reviewSeq) {
        return sqlSessionTemplate.selectOne("ReviewMapper.selectReviewByReviewSeq", reviewSeq);
    }

    @Override
    public int insertReview(ReviewDto reviewDto) {
        return (Integer)sqlSessionTemplate.insert("ReviewMapper.insertReview", reviewDto);
    }

    @Override
    public int insertReviewImg(ReviewImgDto reviewImgDto) {
        return (Integer)sqlSessionTemplate.insert("ReviewMapper.insertReviewImg", reviewImgDto);
    }
}
