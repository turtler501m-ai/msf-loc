package com.ktis.msp.pps.hdofccustmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.pps.hdofccustmgmt.vo.MspRateMstVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerDiaryVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtJuoBanVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtJuoCusVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtJuoSubVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsRcgVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsRealPayInfoVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsVacVo;









import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsHdofcCustMgmtMapper
 * @Description :   선불 고객관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("ppsHdofcCustMgmtMapper")
public interface PpsHdofcCustMgmtMapper {
	
	 //List<?> getList(Map<String, Object> pRequestParamMap) throws Exception;
	/**
	 * 개통정보 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOpenInfoMngList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 개통정보 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getOpenInfoMngExcel(Map<String, Object> pRequestParamMap);
	
	
	
	
	/**
	 * 상담내역관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCnslDtlsMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 상담내역관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCnslDtlsMngExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 문자발송내역관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustSmsDtlsMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 문자발송내역관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustSmsDtlsMngExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 문자발송내역관리 목록조회(과거)
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustSmsDtlsMngListOld(Map<String, Object> pRequestParamMap);
	
	/**
	 * 문자발송내역관리 목록조회 엑셀출력(과거)
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getCustSmsDtlsMngExcelOld(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 해지자관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getTmntCustMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 해지자관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getTmntCustMngExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불CDR관리 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPrepaidCdrMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불CDR관리 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPrepaidCdrMngExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불일사용내역 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPrepaidCdrDailyMngList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불일사용내역 목록조회 엑셀
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPrepaidCdrDailyMngExcel(Map<String, Object> pRequestParamMap);
	
	
	
	/**
	 * 선불 고객정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsCustomerVo getPpsCustmerInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 KT현행화 고객정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsKtJuoCusVo getPpsKtJuoCusInfo(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 선불 KT현행화 계약정보
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsKtJuoSubVo getPpsKtJuoSubInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 KT현행화 청구정보
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsKtJuoBanVo getPpsKtJuoBanInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 *    요금제 정보 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	MspRateMstVo getPpsCodeServiceInfo(Map<String, Object> pRequestParamMap);
	

	
	
	/**
	 * 요금제 총충전정보(총회숫, 총충전금액)조회 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsRcgVo getPpsRcgSumData(Map<String, Object> pRequestParamMap);
	
	
	
	/**
	 * 가입한 부가서비스 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsKtJuoFeatureInfoList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 가상계좌정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsVacVo getPpsVacData(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 실시간 자동이체tab 목록 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsRcgRealCmsTabList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 충전tab목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsRcgTabList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 통화내역tab목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCdrPpTabList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 통화내역tab목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCdrPpTabListExcel(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 일사용내역tab목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCdrPktTabList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 일사용내역tab목록조회엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCdrPktTabListExcel(Map<String, Object> pRequestParamMap);

	/**
	 * 문자발송내역tab목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsSmsTabList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 상담내역tab목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCustomerDiaryTabList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 상담내역 상세조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsCustomerDiaryVo getPpsCustomerDiaryInfo(Map<String, Object> pRequestParamMap);
	
	
	
	void procPpsDiaryWriteUpdate(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 충전처리 
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void procPpsRecharge(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 선불 pin 정보 조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsPinInfoData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 가상계좌 처리 
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void procPpsVac(Map<String, Object> pRequestParamMap);
	
	/**
	 * 선불 문자발송처리 
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	void procPpsSms(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간 이체 설정
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	
	void procPpsRcgRealCmsSetting(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객 잔액갱신
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsCusRemains(Map<String, Object> pRequestParamMap);
	
	/**
	 * 잔액 문자발송
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsRemainsSms(Map<String, Object> pRequestParamMap);
	
	/**
	 * 충전가능/불가능 변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsCusRcgFlagChg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객 문자전송여부 변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsCusSmsFlagChg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객 비자접수여부 변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsCusVizaFlagChg(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객 동보전송 문자번호변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsCusSmsPhoneUp(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객관리 상세 국가리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCustomerNationInfoData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객관리 상세 언어리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCustomerLanguageInfoData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객관리 대리점 리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCustomerOrgnInfoData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객관리 은행 코드리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsVacCodeListData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객관리 요금제 코드리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsFeatureCodeListData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객관리 요금제 코드리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsFeatureAddtionCodeListData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 국적/SMS 변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsCusLangUpdatePop(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 개통 상세 가상계좌
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsAgencyVacInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점일괄변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsAllAgentChg(Map<String, Object> pRequestParamMap);	
	
	/**
	 * 고객관리 CMS 정보
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsCustomerVo getPpsHdofcUserCmsInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 상담내역tab목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsCustomerDiaryVo getPpsCustomerDiaryTabDetail(Map<String, Object> pRequestParamMap);
	
	/**
	 * 개통리스트 대리점변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPpsAgentInfoChg(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 고객관리 은행 코드리스트
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsRcgCodeListData(Map<String, Object> pRequestParamMap);
	
	/**
	 * 고객 수동등록 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void getPpsCustomerProc(Map<String, Object> pRequestParamMap);
	
	/**
	 * 얘치금 충전 설정
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	
	void procPpsRcgDepositSetting(Map<String, Object> pRequestParamMap);


	/**
	 * 얘치금 충전 설정
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	PpsCustomerVo getPpsHdofcUserDpRcgInfo(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간이체 PayInfo
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsRealPayinfoList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간이체등록내역
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsCustCmsList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간이체등록내역 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsRealPayinfoListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * PPS35관리 PPS35요금제코드리스트 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPpsPPS35SocListData(Map<String, Object> pRequestParamMap);
	
	/**
	 * PPS35 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPps35InfoMgmtList(Map<String, Object> pRequestParamMap);
	
	
	/**
	 * 개통정보 목록조회 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getPps35InfoMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 체류기간 변경
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getppsCusStayExpirUpdatePop(Map<String, Object> pRequestParamMap);
}
