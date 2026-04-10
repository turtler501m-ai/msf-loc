package com.ktmmobile.mcp.cs.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.acenars.service.ArsService;
import com.ktmmobile.mcp.common.cti.CtiService;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.cs.dto.ArsQnaDto;
import com.ktmmobile.mcp.cs.dto.BookingDateDto;
import com.ktmmobile.mcp.cs.dto.BookingUserDto;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;
import com.ktmmobile.mcp.mypage.dto.CallASDto;
import com.ktmmobile.mcp.cs.dao.CsInquiryDao;
import org.springframework.web.client.RestTemplate;

import static com.ktmmobile.mcp.common.constants.Constants.ACEN_ARS_SETTING;

@Service
public class CsInquirySvcImpl implements CsInquirySvc{

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Autowired
	CsInquiryDao csInquiryDao;

	@Autowired
	private CtiService ctiService;

	@Autowired
	private ArsService arsService;

	@Override
	public List<HashedMap> selectGroupCode(String qId) {
		return csInquiryDao.selectGroupCode(qId);
	}

	@Override
	public void inquiryInsert(InquiryBoardDto inquiryBoardDto) {

		// 1:1문의 저장
		csInquiryDao.inquiryInsert(inquiryBoardDto);

		// 연동서버 확인 (ARS 또는 CTI)
		NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(ACEN_ARS_SETTING, "csInquiry");
		boolean apResult = true;

		if(nmcpCdDtlDto != null){
			// ARS 연동
			ArsQnaDto arsQnaDto = new ArsQnaDto();
			arsQnaDto.setIfCode("A09");
			arsQnaDto.setQnaSeq(String.valueOf(inquiryBoardDto.getQnaSeq()));
			arsQnaDto.setQnaType("CN");
			arsQnaDto.setUserNm(inquiryBoardDto.getUserNmAll());

			apResult = arsService.arsServiceCall(arsQnaDto);

		}else{
			// CTI 연동
			apResult = ctiService.ctiCsInquiry(inquiryBoardDto.getQnaSeq(), inquiryBoardDto.getQnaContent(), "INF_APP_004"
																				,inquiryBoardDto.getQnaNM(), inquiryBoardDto.getQnaWriterID(), inquiryBoardDto.getMobileNo()
																				,inquiryBoardDto.getQnaSubject(), inquiryBoardDto.getQnaSmsSendYn(), inquiryBoardDto.getQnaSubCtgCd()
																				,inquiryBoardDto.getUserDivision());
		}

		// 연동결과에 따라 삭제처리
		if(!apResult){
			this.deleteInquiry(inquiryBoardDto.getQnaSeq());
			throw new McpCommonJsonException("-1", "홈페이지 문의 접수 실패");
		}
	}

	@Override
	public int deleteInquiry(int qnaSeq) {
		return csInquiryDao.deleteInquiry(qnaSeq);
	}

	@Override
	public int getTotalCount(InquiryBoardDto inquiryBoardDto) {

		return csInquiryDao.getTotalCount(inquiryBoardDto);
	}

	@Override
	public List<InquiryBoardDto> inquiryBoardList(InquiryBoardDto inquiryBoardDto, int skipResult, int maxResult) {

		return csInquiryDao.inquiryBoardList(inquiryBoardDto,skipResult,maxResult);
	}

	@Override
	public String getRPhone1(String userId) {
		return csInquiryDao.getRPhone1(userId);
	}

	@Override
	public String getRPhone2(String userId) {
		return csInquiryDao.getRPhone2(userId);
	}

	@Override
	public int getRPhoneCount(String userId) {
		return csInquiryDao.getRPhoneCount(userId);
	}

	@Override
	public int callASInsert(CallASDto callASDto) {
		return csInquiryDao.callASInsert(callASDto);
	}

	@Override
	public int callASListCnt(CallASDto callASDto) {
		return csInquiryDao.callASListCnt(callASDto);
	}


	/**
	 * @Description : 예약상담제 공통코드 조회
	 * @param cmnGrpCdMst
	 * @return List<CmnGrpCdMst>
	 */
	@Override
	public List<CmnGrpCdMst> selectBookingCd(CmnGrpCdMst cmnGrpCdMst) {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/cs/selectBookingCd", cmnGrpCdMst, List.class);

	}

	/**
	 * @Description : 특정 예약 일시에 예약가능 인원 수 존재여부 확인
	 * @param bookingDateDto
	 * @return String
	 */
	@Override
	public String checkBookingAvailableByDt(BookingDateDto bookingDateDto){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/cs/checkBookingAvailableByDt", bookingDateDto, String.class);
	}

	/**
	 * @Description : 특정 예약 일시에 해당하는 예약시간대 조회
	 * @param bookingDateDto
	 * @return List<BookingDateDto>
	 */
	@Override
	public List<BookingDateDto> getBookingTimeList(BookingDateDto bookingDateDto) {
		RestTemplate restTemplate = new RestTemplate();
		BookingDateDto[] rtnResult=  restTemplate.postForObject(apiInterfaceServer + "/cs/getBookingTimeList", bookingDateDto, BookingDateDto[].class);
		List<BookingDateDto> retList = Arrays.asList(rtnResult);
		return retList;
	}

	/**
	 * @Description : 사용자 예약 가능여부 체크
	 * @param bookingUserDto
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> overNum(BookingUserDto bookingUserDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/cs/overNum", bookingUserDto, Map.class);
	}


	/**
	 * @Description : 예약상담 접수하기
	 * @param bookingUserDto
	 * @return void
	 */
	@Override
	public void insertBooking(BookingUserDto bookingUserDto) {

		// 예약상담 저장
		RestTemplate restTemplate = new RestTemplate();
		BookingUserDto insertResult = restTemplate.postForObject(apiInterfaceServer + "/cs/insertBooking", bookingUserDto, BookingUserDto.class);

		bookingUserDto.setCsResSeq(insertResult.getCsResSeq());
		bookingUserDto.setRegstDt(insertResult.getRegstDt());

		// 연동서버 확인 (ARS 또는 CTI)
		NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(ACEN_ARS_SETTING, "csRes");
		boolean apResult = true;

		if(nmcpCdDtlDto != null){
			// ARS 연동
			ArsQnaDto arsQnaDto = new ArsQnaDto();
			arsQnaDto.setIfCode("A09");
			arsQnaDto.setQnaSeq(bookingUserDto.getCsResSeq());
			arsQnaDto.setQnaType("MN");

			apResult = arsService.arsServiceCall(arsQnaDto);

		}else{
      // CTI 연동
			String ctiVocSec = "APP".equals(bookingUserDto.getVocSec()) ? "01" : "02";
			bookingUserDto.setVocSec(ctiVocSec);

			apResult = ctiService.ctiCsBooking(bookingUserDto);
		}

		// 연동결과에 따라 삭제처리
		if(!apResult){
			this.updateBookingInfo(bookingUserDto);
			throw new McpCommonJsonException("-1", "예약상담 접수 실패");
		}
	}

	/**
	 * @Description : 예약상담 접수 연동 실패로 예약상담 삭제 처리 (DEL_YN 컬럼 Y로 업데이트)
	 * @param bookingUserDto
	 * @return int
	 */
	@Override
	public int updateBookingInfo(BookingUserDto bookingUserDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/cs/updateBookingInfo", bookingUserDto, Integer.class);
	}

	@Override
	public int bookingTotalCount(BookingUserDto bookingUserDto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/cs/bookingTotalCount", bookingUserDto, Integer.class);

	}

	@Override
	public List<BookingUserDto> bookingList(BookingUserDto bookingUserDto) {
		RestTemplate restTemplate = new RestTemplate();
		BookingUserDto[] rtnResult=  restTemplate.postForObject(apiInterfaceServer + "/cs/bookingList", bookingUserDto, BookingUserDto[].class);
		List<BookingUserDto> retList = Arrays.asList(rtnResult);
		return retList;

	}


}
