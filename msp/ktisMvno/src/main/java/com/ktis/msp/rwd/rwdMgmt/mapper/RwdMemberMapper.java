package com.ktis.msp.rwd.rwdMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rwd.rwdMgmt.vo.RwdMemberVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RwdMemberMapper")
public interface RwdMemberMapper {
	
	// 보상서비스 신청 정보
	List<RwdMemberVO> getMemberJoinList(RwdMemberVO rwdMemberVO);
	
	// 보상서비스 신청 정보 엑셀
	List<RwdMemberVO> getRwdMemberJoinListByExcel(RwdMemberVO rwdMemberVO);

	// 보상서비스 가입 정보
	List<RwdMemberVO> getMemberList(RwdMemberVO rwdMemberVO);
	
	// 보상서비스 가입 정보 엑셀
	List<RwdMemberVO> getRwdMemberListByExcel(RwdMemberVO rwdMemberVO);
	
	// 보상서비스 도래대상조회  
	List<RwdMemberVO> getCmpnMemberList(RwdMemberVO rwdMemberVO);
	
	// 보상서비스 도래대상조회 엑셀
	List<RwdMemberVO> getCmpnMemberListByExcel(RwdMemberVO rwdMemberVO);
	
	// 보상서비스 계약 확인
	RwdMemberVO getRwdCntrInfo(RwdMemberVO rwdMemberVO);
	
	// 보상서비스 신청 정보 등록
	int regMspRwdOrder(RwdMemberVO rwdMemberVO);
	
	// 신청 정보 등록전 MSP_RWD_ORDER 계약번호 유효성 체크
	int getRwdOrderCntrCnt(RwdMemberVO rwdMemberVO);
	
	// 신청 정보 등록전 MSP_RWD_ORDER IMEI 유효성 체크
	int getRwdOrderImeiCnt(RwdMemberVO rwdMemberVO);
	
	// 신청 정보 등록전 MSP_RWD_MEMBER 계약번호 유효성 체크
	int getRwdMemCntrCnt(RwdMemberVO rwdMemberVO);
	
	// 신청 정보 등록전 MSP_RWD_MEMBER IMEI 유효성 체크
	int getRwdMemImeiCnt(RwdMemberVO rwdMemberVO);
	
	String getOrderIfTrgtCd(RwdMemberVO rwdMemberVO);
	
	int cancelRwdOrder(RwdMemberVO rwdMemberVO);
	
	int updateRwdOrder(RwdMemberVO rwdMemberVO);
	
	List<?> getFile1(RwdMemberVO rwdMemberVO);
	
	String getFileNm(String fileId);

	int deleteFile(String rwdSeq);	
	
    int insertFile(RwdMemberVO rwdMemberVO);
    
    String getFileName();
    
    String getFileNmSeq();
    
    int insertImageFile(RwdMemberVO rwdMemberVO);
    
    //보상서비스명 가져오기
    String getItemCdNm(Map<String, Object> paramMap);
    
    //보상서비스 청구수납 목록
    List<?> getRwdBillPayList(Map<String, Object> paramMap);
    
    //보상서비스 청구수납 목록 엑셀 다운로드
    List<?> getRwdBillPayListByExcel(Map<String, Object> paramMap);
    
    //보상서비스 권리실행 접수현황
    List<?> getRwdMemCmpnList(Map<String, Object> paramMap);
    
    //보상서비스 권리실행 접수현황 엑셀 다운로드
    List<?> getRwdMemCmpnListByExcel(Map<String, Object> paramMap);
    
    //보상서비스 보상지급현황
    List<?> getRwdMemPayList(Map<String, Object> paramMap);
    
    //보상서비스 보상지급현황 엑셀 다운로드
    List<?> getRwdMemPayListByExcel(Map<String, Object> paramMap);
}