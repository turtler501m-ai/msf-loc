package com.ktis.msp.rcp.idntMgmt.mapper;

import java.util.List;

import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.rcp.idntMgmt.vo.IdntMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("idntMgmtMapper")
public interface IdntMgmtMapper {
	
	/**
	 * 가입자확인 목록조회
	 */
	public List<?> getInvstReqList(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인 엑셀다운
	 */
	public List<?> getInvstReqListEx(IdntMgmtVO idntMgmtVO);
	
	
	/**
	 * 엑셀다운로드(포함된 수사기관명,문서번호) 조회
	 */
	public List<?> getInvstNmValListEx(IdntMgmtVO idntMgmtVO);
	

	/**
	 * 가입자확인 엑셀저장(선택된 정보만 조회)
	 */
	public List<?> getInvstReqListSelectedEx(IdntMgmtVO idntMgmtVO);
	
	
	/**
	 * 가입자확인(전화번호조회)
	 */
	public List<?> getCusInfoByTelnum(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인(주민번호)
	 */
	public List<?> getCusInfoByIdntNo(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인(법인번호)
	 */
	public List<?> getCusInfoByDriNo(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인(사업자번호)
	 */
	public List<?> getCusInfoByTaxNo(IdntMgmtVO idntMgmtVO);
	/**
	 * 가입자확인(계약번호)
	 */
	public List<?> getCusInfoByContractNum(IdntMgmtVO idntMgmtVO);
	
	
	/**
	 * 가입자확인 저장
	 */
	public int savInvstReq(IdntMgmtVO idntMgmtVO);

	/**
	 * 가입자확인 저장전 중복 체크
	 */
	public int getBeforeSaveCheck(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 처리번호 채번
	 */
	public String getSeqNum();
	
	/**
	 * 고객유형
	 */
	public String getCusType(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인 엑셀업로드 데이터 validation
	 */
	public String getInvstCusInfoValidation(CmnCdMgmtVo cmnCdMgmtVo);
	
	public String getAppOdtyNm(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 번호이동 상세 이력
	 */
	public List<?> getInvstCusInfoDtl(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인 삭제
	 */
	public int deleteMspInvstReqMst(IdntMgmtVO idntMgmtVO);
	
	/**
	 * 가입자확인 이력 생성
	 */
	public int savMspInvstReqHist(IdntMgmtVO idntMgmtVO);
	
	
}
