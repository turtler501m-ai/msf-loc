package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;
import com.ktmmobile.mcp.mypage.dto.CallASDto;

public interface CsInquiryDao {

	List<HashedMap> selectGroupCode(String qId);

	int inquiryInsert(InquiryBoardDto inquiryBoardDto);

	int deleteInquiry(int qnaSeq);

	int getTotalCount(InquiryBoardDto inquiryBoardDto);

	List<InquiryBoardDto> inquiryBoardList(InquiryBoardDto inquiryBoardDto,	int skipResult, int maxResult);

	String getRPhone1(String userId);

	String getRPhone2(String userId);

	int getRPhoneCount(String userId);

	int callASInsert(CallASDto callASDto);
	int callASListCnt(CallASDto callASDto);
}
