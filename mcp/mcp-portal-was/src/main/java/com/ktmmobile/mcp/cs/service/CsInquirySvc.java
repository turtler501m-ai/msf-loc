package com.ktmmobile.mcp.cs.service;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.cs.dto.BookingDateDto;
import com.ktmmobile.mcp.cs.dto.BookingUserDto;
import org.apache.commons.collections.map.HashedMap;

import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;
import com.ktmmobile.mcp.mypage.dto.CallASDto;

public interface CsInquirySvc {

	List<HashedMap> selectGroupCode(String qId);

	void inquiryInsert(InquiryBoardDto inquiryBoardDto);

	int deleteInquiry(int qnaSeq);

	int getTotalCount(InquiryBoardDto inquiryBoardDto);

	List<InquiryBoardDto> inquiryBoardList(InquiryBoardDto inquiryBoardDto,	int skipResult, int maxResult);

	String getRPhone1(String userId);

	String getRPhone2(String userId);

	int getRPhoneCount(String userId);

	int callASInsert(CallASDto callASDto);
	int callASListCnt(CallASDto callASDto);

	/**
	 * @Description : 예약상담제 공통코드 조회
	 * @param cmnGrpCdMst
	 * @return List<CmnGrpCdMst>
	 */
	List<CmnGrpCdMst> selectBookingCd(CmnGrpCdMst cmnGrpCdMst);

	/**
	 * @Description : 특정 예약 일시에 예약가능 인원 수 존재여부 확인
	 * @param bookingDateDto
	 * @return String
	 */
	String checkBookingAvailableByDt(BookingDateDto bookingDateDto);


	/**
	 * @Description : 특정 예약 일시에 해당하는 예약시간대 조회
	 * @param bookingDateDto
	 * @return List<BookingDateDto>
	 */
	List<BookingDateDto> getBookingTimeList(BookingDateDto bookingDateDto);

	/**
	 * @Description : 사용자 예약 가능여부 체크
	 * @param bookingUserDto
	 * @return Map<String, Object>
	 */
	Map<String, Object> overNum(BookingUserDto bookingUserDto);

	/**
	 * @Description : 예약상담 접수하기
	 * @param bookingUserDto
	 * @return void
	 */
	void insertBooking(BookingUserDto bookingUserDto);

	/**
	 * @Description : 예약상담 접수 연동 실패로 예약상담 삭제 처리 (DEL_YN 컬럼 Y로 업데이트)
	 * @param bookingUserDto
	 * @return int
	 */
	int updateBookingInfo(BookingUserDto bookingUserDto);

	int bookingTotalCount(BookingUserDto bookingUserDto);

	List<BookingUserDto> bookingList(BookingUserDto bookingUserDto);

}
