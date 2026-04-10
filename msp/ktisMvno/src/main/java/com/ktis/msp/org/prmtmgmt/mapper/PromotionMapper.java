package com.ktis.msp.org.prmtmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtCopyVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.RecommenIdStateVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("promotionMapper")
public interface PromotionMapper {
	
	// 채널별 요금 할인 목록 조회
	List<ChrgPrmtMgmtVO> getChrgPrmtList(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 조직 목록 조회
	List<ChrgPrmtMgmtSubVO> getChrgPrmtOrgnList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException;
	
	// 채널별 요금 할인 요금제 목록 조회
	List<ChrgPrmtMgmtSubVO> getChrgPrmtSocList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException;
	
	// 채널별 요금 할인 부가서비스 목록 조회
	List<ChrgPrmtMgmtSubVO> getChrgPrmtAddList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException;
	
	// 채널별 요금 할인 중복 체크
	List<ChrgPrmtMgmtVO> getChrgPrmtDupByInfo(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 마스터 추가
	int insertMspChrgPrmt(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 조직 추가
	int insertMspChrgPrmtOrg(ChrgPrmtMgmtSubVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 요금제 추가
	int insertMspChrgPrmtSoc(ChrgPrmtMgmtSubVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 부가서비스 추가
	int insertMspChrgPrmtAdd(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 정보 변경
	int updChrgPrmtByInfo(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 상세 목록 조회
	List<ChrgPrmtMgmtVO> getChrgPrmtDtlList(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 모집경로 목록 조회
	List<ChrgPrmtMgmtSubVO> getChrgPrmtOnoffList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException;
	
	// 모집경로 추가
	int insertMspChrgPrmtOnOff(ChrgPrmtMgmtSubVO chrgPrmtMgmtVO) throws EgovBizException;
	
	// 채널별 요금 할인 조직 복사
	int insertMspChrgPrmtOrgCopy(ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO) throws EgovBizException;
	
	// 채널별 요금 할인 요금제 복사
	int insertMspChrgPrmtSocCopy(ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO) throws EgovBizException;
	
	// 채널별 요금 할인 부가서비스 복사
	int insertMspChrgPrmtAddCopy(ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO) throws EgovBizException;
	
	// 모집경로 복사
	int insertMspChrgPrmtOnOffCopy(ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO) throws EgovBizException;

	// 채널별 요금 할인 마스터 복사
	int insertMspChrgPrmtCopy(ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO) throws EgovBizException;
	
	// 포로모션ID 조회
	String getPrmtId() throws EgovBizException;
	
	// 추천인ID 발급 현황 조회
	List<RecommenIdStateVO> getRecommenIdStateList(RecommenIdStateVO recommenIdStateVO) throws EgovBizException;

	// 추천인ID 발급 현황 엑셀다운로드
	List<RecommenIdStateVO> getRecommenIdStateListByExcel(RecommenIdStateVO recommenIdStateVO) throws EgovBizException;
	
	// 추천이력조회
	List<RecommenIdStateVO> getRecommenHstList(RecommenIdStateVO recommenIdStateVO) throws EgovBizException;
	
	// 추천이력 엑셀다운로드
	List<RecommenIdStateVO> getRecommenHstListByExcel(RecommenIdStateVO recommenIdStateVO) throws EgovBizException;
	
	// 혜택관련 피추천인 정보 중복 체크
	int getdContractDupChk(String uploadContractNum) throws EgovBizException;
	
	// 혜택관련 피추천인 계약번호 체크
	int getdContractChk(String uploadContractNum) throws EgovBizException;
	
	// 혜택적용
	void updateBenefits(RecommenIdStateVO vo) throws EgovBizException;

	// ********************** 엑셀 업로드 시작 **********************
	// 채널별 요금 할인 엑셀 업로드 중복 체크
	List<ChrgPrmtMgmtVO> getChrgPrmtDupByInfoExl(ChrgPrmtMgmtVO chrgPrmtMgmtVO);

	// **********************Chk 테이블로 INSERT **********************
	// 엑셀데이터 채널별요금할인 마스터 Chk테이블에 추가
	int insertChrgPrmtMstChk(ChrgPrmtMgmtVO chrgPrmtMgmtVO);

	// 엑셀데이터 채널별요금할인 조직 Chk테이블에 추가
	int insertChrgPrmtOrgChk(ChrgPrmtMgmtVO chrgPrmtMgmtVO);

	// 엑셀데이터 채널별요금할인 요금제 Chk테이블에 추가
	int insertChrgPrmtSocChk(ChrgPrmtMgmtVO chrgPrmtMgmtVO);

	// 엑셀데이터 채널별요금할인 모집경로 Chk테이블에 추가
	int insertChrgPrmtOnOffChk(ChrgPrmtMgmtVO chrgPrmtMgmtVO);

	// 엑셀데이터 채널별요금할인 부가서비스 Chk테이블에 추가
	int insertChrgPrmtAddChk(ChrgPrmtMgmtVO chrgPrmtMgmtVO);


	// ********************** Chk 테이블자료 실제 테이블로 INSERT **********************
	// Chk데이터 채널별요금할인 마스터 실테이블에 추가
	int insertChrgPrmtMstExl();

	// Chk데이터 채널별요금할인 조직 실테이블에 추가
	int insertChrgPrmtOrgExl();

	// Chk데이터 채널별요금할인 요금제 실테이블에 추가
	int insertChrgPrmtSocExl();

	// Chk데이터 채널별요금할인 모집경로 실테이블에 추가
	int insertChrgPrmtOnOffExl();

	// Chk데이터 채널별요금할인 부가서비스 실테이블에 추가
	int insertChrgPrmtAddExl();

	// ********************** Chk 테이블자료 DELETE  **********************
	// 채널별요금할인 마스터 Chk테이블 DELETE
	int deleteChrgPrmtMstChk();

	// 채널별요금할인 조직 Chk테이블 DELETE
	int deleteChrgPrmtOrgChk();

	// 채널별요금할인 요금제 Chk테이블 DELETE
	int deleteChrgPrmtSocChk();

	// 채널별요금할인 모집경로 Chk테이블 DELETE
	int deleteChrgPrmtOnOffChk();

	// 채널별요금할인 부가서비스 Chk테이블 DELETE
	int deleteChrgPrmtAddChk();

	// ********************** 엑셀 업로드 끝 **********************

	//채널별요금할인 목록 콤보 조회
	List<EgovMap> getChrgPrmtListCombo(RcpDetailVO vo);
	
	ChrgPrmtMgmtVO getChrgPrmtDtlInfo(ChrgPrmtMgmtVO vo);
	
	List<ChrgPrmtMgmtVO> getChoChrgPrmtListExcelDown(ChrgPrmtMgmtVO vo);

	//채널별요금할인 엑셀 업로드시 채널유형,대상조직 관련 검사
	int getOrgnTypeChkCnt(ChrgPrmtMgmtVO vo);
	
	// 채널별요금할인 상세 조직 목록 조회
 	List<ChrgPrmtMgmtSubVO> getChrgPrmtDtlOrgnList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO);

 	// 채널별 요금 할인 전체 중복 체크
 	List<ChrgPrmtMgmtVO> getChrgPrmtDtlAllChk(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException;
}
