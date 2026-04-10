package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.NstepQueryVo;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpCommonVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpRateVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("RcpMgmtMapper")
public interface RcpMgmtMapper {

    public List<?> getRcpList(RcpListVO searchVO);
    
    public List<?> getRcpCodeList();
    
    public List<?> getRcpCommon(RcpCommonVO searchVO);
    
    public List<?> getRcpSimPrdtInfo(RcpCommonVO searchVO);
    
    public List<?> getRcpRateList(RcpRateVO rcpRateVO);
    
    public NstepQueryVo getNstepCallDataByRequestKey(String requestKey);
    
    int insertRcpRequest(RcpDetailVO rcpDetailVO);
	int updateRcpRequest(RcpDetailVO rcpDetailVO);
	
	int updateRcpCustomer(RcpDetailVO rcpDetailVO);
    int insertRcpCustomer(RcpDetailVO rcpDetailVO);
    
    int updateRcpSale(RcpDetailVO rcpDetailVO);
    int insertRcpSale(RcpDetailVO rcpDetailVO);
    
    int updateRcpDelivery(RcpDetailVO rcpDetailVO);
    int insertRcpDelivery(RcpDetailVO rcpDetailVO);
    
    int updateRcpReq(RcpDetailVO rcpDetailVO);
    int insertRcpReq(RcpDetailVO rcpDetailVO);
    
    int updateRcpMove(RcpDetailVO rcpDetailVO);
    int insertRcpMove(RcpDetailVO rcpDetailVO);
    
    int updateRcpAgent(RcpDetailVO rcpDetailVO);
    int insertRcpAgent(RcpDetailVO rcpDetailVO);
    
    int updateRcpState(RcpDetailVO rcpDetailVO);
    int insertRcpState(RcpDetailVO rcpDetailVO);
    
    int updateRcpPay(RcpDetailVO rcpDetailVO);
    int insertRcpPay(RcpDetailVO rcpDetailVO);
    
    int updateRcpChange(RcpDetailVO rcpDetailVO);
    int insertRcpChange(RcpDetailVO rcpDetailVO);
    
    int updateRcpDvcChg(RcpDetailVO rcpDetailVO);
    int insertRcpDvcChg(RcpDetailVO rcpDetailVO);
    
    
    int updateRcpRequestRecYn(RcpDetailVO rcpDetailVO);
    
    int deleteRcpAddition(RcpDetailVO rcpDetailVO);
    
    int insertRcpAddition(RcpDetailVO rcpDetailVO);
    
    int getStateKey(RcpDetailVO rcpDetailVO);
    
    int updateRcpAlter(RcpDetailVO rcpDetailVO);

	List<?> getRcpPlc(RcpListVO rcpListVO);
	List<?> comboList(RcpListVO rcpListVO);
	
	public RcpDetailVO maxRequestkey();
	public EgovMap getShopCd(String userId);
	public EgovMap orgnInfo(String orgnId);
	public EgovMap checkPhoneSN(RcpDetailVO searchVO);
	public RcpDetailVO selectRequest(String requestKey);

	int scanFileCount(String requestKey);
	
	//2020.12.10 엑셀업로드 관련  유심일련번호 업데이트할 예약번호 체크 및 유심일련번호 업데이트
	String isUsimInfoChkLinkus(String selfDlvryIdx);
	int updateUsimSnLinkus(RcpListVO vo);
	
	int updateRequestStateLinkus(RcpListVO vo);
	
	/**
	 * 신청관리(링커스)
	 * @param param
	 * @return
	 */
	public List<?> getRcpMgmtListLinkus(Map<String, Object> param);
	
	/**
	 * 신청관리(링커스) 상세조회
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtListLinkusDtl(Map<String, Object> paramMap);	
	
	/**
	
	/**
	 * 신청관리(링커스) 엑셀다운
	 * @param param
	 * @return
	 */
	public List<?> getRcpMgmtListLinkusExcel(Map<String, Object> param);
	
	/**
	 * 신청정보 수정
	 * @param param
	 */
	void setMcpRequestLinkus(Map<String, Object> param);
	
	/**
	 * 상태정보 수정
	 * @param param
	 */
	void setMcpRequestStateLinkus(Map<String, Object> param);
	
	/**
	 * 배송정보 수정
	 * @param param
	 */
	void setMcpRequestDlvryLinkus(Map<String, Object> param);
	
	/**
	 * 신청정보(링커스) 유심일련번호 수정 관련 처리 로직
	 * @param param
	 * @return
	 */
	int selectLinkusUsimMst(Map<String, Object> param);
	void insertLinkusUsimMst(Map<String, Object> param);
	void updateLinkusUsimMst(Map<String, Object> param);
	void deleteLinkusUsimMst(Map<String, Object> param);
	void insertLinkusUsimHst(Map<String, Object> param);
	
	/**
	 * 스캔ID 조회
	 * @param param
	 */
	HashMap<String, String> getMcpRequestScanId(HashMap<String, String> params);
	
	/**
	 * 신청관리(렌탈)
	 * @param param
	 * @return
	 */
	public List<?> getRcpMgmtRentalList(Map<String, Object> param);
	
	/**
	 * 신청관리(렌탈) 엑셀다운
	 * @param param
	 * @return
	 */
	public List<?> getRcpMgmtRentalListExcel(Map<String, Object> param);
	
	/**
	 * 문자메시지 전송 대리점 조회
	 * @param param
	 * @return
	 */	
	int selectMsgCnt(String cntpntShopId);
	
	// 개통이력확인
	public List<?> getCheckCstmr(RcpDetailVO vo);
	
	// 개통이력확인
	public List<?> getCheckCstmrList(RcpDetailVO vo);
	
	/**
	 * 사전인증조회
	 * @param param
	 * @return
	 */	
	public List<?> selectPreAuthList(Map<String, Object> param);
	

	/**
	 * 사전인증조회 신청서 메모 수정
	 * @param param
	 * @return
	 */	
	public void updatePreAuthMcpMemo(Map<String, Object> param);

	public void updatePreAuthMspMemo(Map<String, Object> param);
	
	/**
	 * 사용가능한 유심번호인지 확인
	 * @param param
	 * @return
	 */	
	/**
	 * 유심 LOCK 등록 여부
	 */
	int getImpoUsimNo(RcpDetailVO vo);
	
    int insertRcpKtInter(RcpDetailVO rcpDetailVO);
	int updateRcpKtInter(RcpDetailVO rcpDetailVO);
	
	int selectKtInter(String resNo);

	int updateKisKnoteScanInfo(RcpDetailVO rcpDetailVO);

	int getKnoteYn(String orgnId);

	int getKnoteShopYn(RcpDetailVO rcpDetailVO);

	HashMap<String, String> checkAuth(RcpDetailVO rcpDetailVO);

	int checkSendFS5();

	String getKosId(String userId);

	/**
	 * 신청정보 상세 존재하는지 체크
	 */
	int chkMcpReqDtl(String requestKey);

	/**
	 * 신청정보 상세 조회
	 */
	RcpDetailVO getMcpReqDtl(String requestKey);

	/**
	 * 신청정보 상세 생성
	 */
	int insertMcpReqDtl(RcpDetailVO rcpDetailVO);

	/**
	 * 신청정보 상세 현행화
	 */
	int updateMcpReqDtl(RcpDetailVO rcpDetailVO);
}
