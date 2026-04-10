package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;
import com.ktmmobile.mcp.mypage.dto.CallASDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;

@Repository
public class CsInquiryDaoImpl implements CsInquiryDao{

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<HashedMap> selectGroupCode(String qId) {

        return sqlSessionTemplate.selectList("boardMapper.selectGroupCode",qId);
    }

    @Override
    public int inquiryInsert(InquiryBoardDto inquiryBoardDto) {
    	sqlSessionTemplate.insert("CsInquiryMapper.inquiryInsert",inquiryBoardDto);
        return 1;
    }

		@Override
		public int deleteInquiry(int qnaSeq) {
			return sqlSessionTemplate.update("CsInquiryMapper.deleteInquiry", qnaSeq);
		}

    @Override
    public int getTotalCount(InquiryBoardDto inquiryBoardDto) {

        return (Integer)sqlSessionTemplate.selectOne("CsInquiryMapper.getTotalCount",inquiryBoardDto);
    }

    @Override
    public List<InquiryBoardDto> inquiryBoardList(InquiryBoardDto inquiryBoardDto, int skipResult, int maxResult) {

        return sqlSessionTemplate.selectList("CsInquiryMapper.inquiryBoardList",inquiryBoardDto,new RowBounds(skipResult, maxResult));
    }

	@Override
	public String getRPhone1(String userId) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("CsInquiryMapper.getRPhone1",userId);
	}

	@Override
	public String getRPhone2(String userId) {
		return sqlSessionTemplate.selectOne("CsInquiryMapper.getRPhone2",userId);
	}

	@Override
	public int getRPhoneCount(String userId) {
		return (Integer)sqlSessionTemplate.selectOne("CsInquiryMapper.getRPhoneCount",userId);
	}


	@Override
    public int callASInsert(CallASDto callASDto) {
        return sqlSessionTemplate.insert("CsInquiryMapper.callASInsert",callASDto);
    }

	@Override
    public int callASListCnt(CallASDto callASDto) {
		return (Integer)sqlSessionTemplate.selectOne("CsInquiryMapper.callASListCnt",callASDto);
    }

}
