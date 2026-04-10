package com.ktis.msp.rcp.statsMgmt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.statsMgmt.vo.StatsMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("StatsMgmtMapper")
public interface StatsMgmtMapper {
		
	List<?> getOpenStatList(Map<String, Object> pRequestParamMap);

	List<?> getOpenStatListEx(Map<String, Object> pRequestParamMap);

	List<?> getCanStatList(Map<String, Object> pRequestParamMap);

	List<?> getStatsRealTimeJson(Map<String, Object> pRequestParamMap);
	
	List<?> getRateStatsJson(Map<String, Object> pRequestParamMap);
	
	List<?> getAgntStatsJson(Map<String, Object> pRequestParamMap);
	
	Map<String, Object> getTimerVal(Map<String, Object> pReqParamMap) ;
	
	List<EgovMap> getStatsSimList(StatsMgmtVo statsMgmtVo);
	
	List<EgovMap> getStatsSimListExcel(StatsMgmtVo statsMgmtVo);
	
    /**
     * @Description : 재약정현황 배송정보 수정
     * @Param  : 
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 10. 10.
     */
    int updateDlvryInfo(StatsMgmtVo statsMgmtVo);
    
    List<StatsMgmtVo> getModelOpenList(StatsMgmtVo statsMgmtVo);
    
    List<?> getStatsOsstMgmtList(Map<String, Object> pRequestParamMap);
    List<?> getStatsOsstMgmtDaily(Map<String, Object> pRequestParamMap);
    List<?> getStatsOsstMgmtDetail(Map<String, Object> pRequestParamMap);
    List<?> getStatsOsstMgmtDetailExcel(Map<String, Object> pRequestParamMap);
    
    /* 명세서 재발송 */
    List<EgovMap> getStatsBillingStatList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getStatsBillingStatListExcel(StatsMgmtVo statsMgmtVo);

    /**
     * @Description : 유심셀프변경
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
    List<EgovMap> getUsimChgList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getUsimChgListExcel(StatsMgmtVo statsMgmtVo);

    /**
     * @Description : 통화내역열람
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
    List<EgovMap> getCallList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getCallListExcel(StatsMgmtVo statsMgmtVo);
	String getOldScanId(StatsMgmtVo statsMgmtVo);
	String getFileNum(String oldScanId);
	String getNewScanId(String custReqSeq);
	int insertEmvFile(StatsMgmtVo statsMgmtVo);
	int updateProcCd(StatsMgmtVo statsMgmtVo);
	
    /**
     * @Description : 명의변경
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : 장익준
     * @Create Date : 2022. 06. 16.
     */
    List<EgovMap> getNameChgList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getNameListExcel(StatsMgmtVo statsMgmtVo);
	List<EgovMap> getNameChgDtl(Map<String, Object> param);
	String getNewScanIdNm(String custReqSeq);
	int updateProcCdNm(StatsMgmtVo statsMgmtVo);
    Map<String, String> getNameChgState(String custReqSeq);
    int copyNameChgMst(StatsMgmtVo statsMgmtVo);
    int copyNameChgDtl(StatsMgmtVo statsMgmtVo);
	int copyNameChgAgent(StatsMgmtVo statsMgmtVo);
	
	/** 
	 * @Description : 명의변경현황
	 * @Param  : StatsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : wooki
	 * @CreateDate : 2025.10.21
	 */
	List<EgovMap> getNameChgStateList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getNameStateListExcel(StatsMgmtVo statsMgmtVo);
	
	/** 
     * @Description : 가입신청서 출력요청
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
    List<EgovMap> getReqJoinFormList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getReqJoinFormListExcel(StatsMgmtVo statsMgmtVo);
	int updateReqJoinForm(StatsMgmtVo statsMgmtVo);
	
	/**
     * @Description : 유심PUK번호 조회
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : wooki
     * @CreateDate : 2022.08.02
     */
    List<EgovMap> getReqUsimPukList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getReqUsimPukListExcel(StatsMgmtVo statsMgmtVo);
	
	/**
     * @Description : OMD단말등록현황
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : JUNA
     * @CreateDate : 2022.09.14
     */
    List<EgovMap> getOmdRegList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getOmdRegListExcel(StatsMgmtVo statsMgmtVo);	

    /**
     * @Description : 가입번호조회
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : 박준성
     * @Create Date : 2022. 10. 07.
     */
    List<EgovMap> getOwnPhoNumReqList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getOwnPhoNumReqListExcel(StatsMgmtVo statsMgmtVo);
	
	/**
     * @Description : 통화품질불량 접수
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : 박효진
     * @CreateDate : 2022. 10. 24.
     */
    List<EgovMap> getCallQualityAsList(StatsMgmtVo statsMgmtVo);	
	List<EgovMap> getCallQualityAsListExcel(StatsMgmtVo statsMgmtVo);
	int updateCallQualityAs(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description 예약상담 리스트 조회
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.18
	 */
    List<EgovMap> getCsResInfoList(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description 예약상담 리스트 상세 조회
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.19
	 */
	List<EgovMap> getCsResDtlInfoList(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description 시간대별 예약상담 허용인원 조회
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.21
	 */
	List<EgovMap> getCsResPerCntByDt(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description 시간대별 예약상담 허용인원 등록/수정
	 * @Param : csResMergeMap
	 * @Return : void
	 * @Author : hsy
	 * @CreateDate : 2023.04.24
	 */
	void mergeCsResPerCntByDt(Map<String, String> csResMergeMap);

	/**
	 * @Description 예약상담 최대 허용인원
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.25
	 */
	List<EgovMap> getMaxPerCnt();

	/**
	 * @Description 예약상담 신청현황 엑셀 다운로드
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.25
	 */
	List<EgovMap> getCsResListExcel(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description 시간대별 예약상담 예약인원 조회
	 * @Param : statsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.04.27
	 */
	List<EgovMap> getCsResCntByDt(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description 평생할인 프로모션 가입이력 조회
	 * @Param : searchVO
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.09.27
	 */
	List<EgovMap> getPrmtAutoAddList(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 처리여부 수정
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	int updatePrmtAutoAddProc(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 가입이력 상세 조회
	 * @Param : searchVO
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	List<EgovMap> getPrmtAutoAddDetail(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 가입이력 엑셀 다운로드
	 * @Param : searchVO
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	List<EgovMap> getPrmtAutoAddListExcel(StatsMgmtVo searchVO);

	/**
	 * @Description (평생할인 자동 가입 재처리 금지 조건 체크) 1. 최종상태 확인
	 * @Param : searchVO
	 * @Return : Map<String, String>
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	Map<String, String> getCstmrChk(StatsMgmtVo searchVO);

	/**
	 * (평생할인 자동 가입 재처리 금지 조건 체크) 1-1. C07인 경우, 우수기변 취소 고객인지 확인
	 * @param : searchVO
	 * @return : List
	 */
	String getDvcAgntEndDttm(String contractNum);

	/**
	 * @Description (평생할인 자동 가입 재처리 금지 조건 체크) 2. 고객 정보 가져오기
	 * @Param : searchVO
	 * @Return : Map<String,String>
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	Map<String,String> getCstmrSubInfo(String contractNum);

	/**
	 * @Description (평생할인 자동 가입 재처리 금지 조건 체크) 3. 프로모션 중복 고객 확인 (max값 가져오기)
	 * @Param : String
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	String getMaxTrgEffDt(HashMap<String, Object> param);

	/**
	 * @Description (평생할인 자동 가입 재처리 금지 조건 체크) 4. 재처리 가능기간 확인 (공통코드)
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	String getRtyCanDt();

	/**
	 * (평생할인 자동 가입 재처리 금지 조건 체크) 5.부가서비스 성공여부 / 재처리 횟수 / 재처리 가능일 체크
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getPrmtAutoChkList(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 부가서비스 재처리
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	int insertRtyPrmtAutoAddDtl(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 부가서비스 가입 조회
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.10
	 */
	int getPrmtnotAddSocCnt(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 최종 성공여부 update
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.10.10
	 */
	int updatePrmtFnlSuccYn(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 부가서비스 재처리 이력 seq 생성
	 * @Param : searchVO
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.10
	 */
	String getPrcRtySeq();


	/**
	 * 평생할인 프로모션 적용 대상 회원 조회
	 * @param : searchVO
	 * @return : Map<String, Object>
	 */
	List<EgovMap> getDisPrmtTrgMgmtList(StatsMgmtVo searchVO);

	/**
	 * 평생할인 프로모션 적용 대상 회원 엑셀 다운
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtTrgMgmtListExcel(StatsMgmtVo searchVO);

	/**
	 * 업무구분별 평생할인 정책 적용 대상 상세 이력
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtTrgMgmtDtl(StatsMgmtVo searchVO);

	/**
	 * 업무구분별 평생할인 정책 적용 대상 상세 이력 팝업 전체보기
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtTrgMgmtDtlPop(StatsMgmtVo searchVO);

	/**
	 * 업무구분별 평생할인 정책 적용 대상 상세 이력 엑셀 다운로드
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtTrgMgmtDtlPopExcel(StatsMgmtVo searchVO);


	/**
	 * 평생할인 프로모션 지연 관리 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtDelayMst(StatsMgmtVo searchVO);

	/**
	 * 평생할인 프로모션 지연 관리 엑셀 다운로드
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtDelayMstExcel(StatsMgmtVo searchVO);
	
	/**
     * @Description : KT인터넷 개통현황
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     * @Author : 이승국
     * @Create Date : 2024. 06. 05.
     */
	List<EgovMap> getStatsInetList(StatsMgmtVo statsMgmtVo);

	/**
	 * @Description : KT인터넷 개통현황 변경이력
	 * @Param  : StatsMgmtVo
	 * @Return : List<EgovMap>
	 * @Author : 이승국
	 * @Create Date : 2024. 07. 10.
	 */
	List<EgovMap> getStatsInetHistList(StatsMgmtVo statsMgmtVo);
	List<EgovMap> getStatsInetListExcel(StatsMgmtVo statsMgmtVo);

	/**
	 * M캐시 회원정보 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getMcashJoinCustMgmtList(StatsMgmtVo statsMgmtVo);

	/**
	 * M캐시 회원 수납 이력 팝업
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getMcashListDtlPop(StatsMgmtVo statsMgmtVo);

	/**
	 * M캐시 회원 수납 이력 합계 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getMcashDisTotal(StatsMgmtVo statsMgmtVo);

	/**
	 * M캐시 회원정보 이력 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getMcashJoinCustMgmtHist(StatsMgmtVo statsMgmtVo);

	/**
	 * M캐시 회원정보 엑셀 다운로드
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getMcashJoinCustMgmtListExcel(StatsMgmtVo statsMgmtVo);

	/**
	 * 서식지 연동 현황 조회
	 */
	List<EgovMap> getAppFormSyncList(StatsMgmtVo statsMgmtVo);

	/**
	 * 서식지 연동 현황 상세 조회
	 */
	List<EgovMap> getAppFormSyncDetailList(StatsMgmtVo statsMgmtVo);

	/**
	 * 서식지 연동 현황 엑셀 다운로드
	 */
	List<EgovMap> getAppFormSyncListExcel(StatsMgmtVo statsMgmtVo);

	/**
	 * 평생할인 프로모션 자동 가입 검증 목록 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtChkList(StatsMgmtVo searchVO);

	/**
	 * 평생할인 프로모션 자동 가입 검증 목록 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisPrmtChkListExcel(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 검증 처리여부 수정
	 * @Param : searchVO
	 * @Return : int
	 */
	int updatePrmtChkProc(StatsMgmtVo searchVO);

	List<String> getDisPrmtAddList(StatsMgmtVo searchVO);



	/**
	 * 평생할인 프로모션 가입 검증 목록 조회
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisAddChkList(StatsMgmtVo searchVO);


	/**
	 * 평생할인 프로모션 가입 검증 목록 엑셀
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisAddChkListExcel(StatsMgmtVo searchVO);

	/**
	 * 가입중인 평생할인 부가서비스 가져오기
	 * @param : searchVO
	 * @return : List
	 */
	List<EgovMap> getDisAddList(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 가입 검증 검증 완료 처리 수정
	 * @Param : searchVO
	 * @Return : int
	 */
	int updateDisAddChkComp(StatsMgmtVo searchVO);

	/**
	 * @Description 평생할인 프로모션 가입 검증 재처리 수정
	 * @Param : searchVO
	 * @Return : int
	 */
	int updateDisAddChkRty(StatsMgmtVo searchVO);

	/**
	 * 평생할인 프로모션 가입 검증 데이터 조회
	 * @param : searchVO
	 * @return : StatsMgmtVo
	 */
	StatsMgmtVo getDisAddChk(StatsMgmtVo searchVO);

	/**
	 * 검증 이후 추가 이벤트가 발생했는지 확인
	 * @param : searchVO
	 * @return : EgovMap
	 */
	int disAddEvntChk(StatsMgmtVo searchVO);

	List<String> getDisAddSvcListByPrmtId(String prmtId);
	
    /**
     * @Description : 유심변경현황
     * @Param  : StatsMgmtVo
     * @Return : List<EgovMap>
     */
    List<EgovMap> getUsimChgHst(StatsMgmtVo statsMgmtVo);
}
