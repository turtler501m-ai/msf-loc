package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.OpenInfoVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpBnftVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNatnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNewMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpPrdtListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpPrmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RntlPrdtInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("RcpNewMgmtMapper")
public interface RcpNewMgmtMapper {
	
	public List<RcpPrdtListVO> checkNewPhoneSN(RcpDetailVO searchVO);
	
	public List<RcpPrdtListVO> getPrdtStatList(RcpPrdtListVO searchVO);
		
	public String getRateGrpByInfo(RcpNewMgmtVO searchVO);
	
	int insertCopyRcpRequest(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpCustomer(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpSale(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpDelivery(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpReq(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpMove(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpPay(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpAgent(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpState(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpChange(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpDvcChg(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpAddition(RcpDetailVO rcpDetailVO);
	
	public List<OpenInfoVO> getOpenList(RcpListVO searchVO);
	
	public RcpNewMgmtVO getUserInfo(RcpDetailVO searchVO);
	
	public List<EgovMap> rcpNewMgmtList(RcpListVO searchVO);
	
	
	public List<RntlPrdtInfoVO> getRntlPrdtInfo(RntlPrdtInfoVO rntlPrdtInfoVO);
	
	public List<RcpPrmtVO> getRcpPrmtPrdcList(RcpPrmtVO rcpPrmtVO);
	
	public List<RcpPrmtVO> getRcpPrmtList(RcpPrmtVO rcpPrmtVO);
	
	public String getpContractNum(String pSearchName);
	
	public List<RcpBnftVO> getRcpBnftList(RcpBnftVO rcpCommonVO);
	
	int insertRcpBenefit(RcpDetailVO rcpDetailVO);
	
	int insertCopyRcpBenefit(RcpDetailVO rcpDetailVO);
	
	int deleteRcpBenefit(RcpDetailVO rcpDetailVO);
	
	int getAppFormMakeCheck(RcpDetailVO vo);
	
	public List<RcpNatnVO> getRcpNatnList(RcpNatnVO rcpNatnVO);
	
	void updateRequestState(RcpDetailVO vo);
	
	public List<?> getSaleinfo(RcpDetailVO rcpDetailVO);
	
	/**
	 * [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
	 * 기기변경 권한체크
	 */
	String getDvcChgAuthYn(String typeCd);

	/**
	 * 90일 이내 동일명의 개통회선 10회초과 체크
	 */
	public int chkCstmrCnt(RcpDetailVO rcpDetailVO);
	
	/**
	 * 90일 이내 개통회선 공통코드 Y/N 유무
	 */
	public String rcpChkCodeYn();

	/**
	 * 20230718 자급제보상서비스 신청정보 복사
	 */
	int insertCopyRwdOrder(RcpDetailVO rcpDetailVO);
	
	// 20231123 M포탈에서 선택한 사은품 선택 데이터 복사 
	int insertGiftPrmtMPortalTrgt(RcpDetailVO rcpDetailVO);
	
	String gettReCommendFalg(RcpDetailVO rcpDetailVO);
	
	int getCommendId(RcpDetailVO rcpDetailVO);

	public Map<String, Object> getCommendData(RcpDetailVO rcpDetailVO);
	
	int insertCopyRecommendId(RcpDetailVO rcpDetailVO);
	
	int insertCopyKtInterId(RcpDetailVO rcpDetailVO); 
	
	/** ACEN 연동대상 확인 */
	String getAcenReqStatus(String requestKey);

	/** ACEN 번호이동 위약금 입력대상 확인 */
	int getAcenPenaltyStatus(String requestKey);

	/** 번호이동 위약금 조회 */
    String getMovePenalty(String requestKey);

	/** 위약금 변경 이력 적재 */
	int insertPenaltyHist(RcpDetailVO rcpDetailVO);

	/** 신청관리(일시납) 조회 */
	public List<EgovMap> rcpMgmtPayFullList(RcpListVO searchVO);

	/** 신청관리(일시납) 결제상태 수정 */
	public void rcpMgmtPayFullUpdate(RcpListVO searchVO);

	String getCustomerIdBySvcCntrNo(String svcCntrNo);

	/** 제휴처 조회 */
	String getJehuProdType(String rateCd);

	/** 제휴사 조회 */
	String getPartnerOfferType(String agentCode);

	/** acen 처리를 위한 신청정보 조회 */
	RcpDetailVO getMcpRequestInfoForAcen(String requestKey);

	/** acen 대상 등록 */
	int insertAcenReqStatus(Map<String, Object> params);

	/**
	 * 신청관리 목록 조회
	 */
	public List<EgovMap> getRcpRequestList(RcpListVO searchVO);

	/** 데이터쉐어링 목록 조회 */
	public List<EgovMap> getSharingList(String grpId);

}
