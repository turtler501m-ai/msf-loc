package com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CanTransferMapper")
public interface CanTransferMapper {
	
	// 신청후 미개통고객 조회
	List<CanTransferVO> getRcpCustNotOpenList(int stdrCnt);
	
	// kos 직개통고객 확인을 위한 can 생성
	void insertMcpRequest(String requestKey);
	void insertMcpRequestAddition(String requestKey);
	void insertMcpRequestAgent(String requestKey);
	void insertMcpRequestChange(String requestKey);
	void insertMcpRequestClause(String requestKey);
	void insertMcpRequestCstmr(String requestKey);
	void insertMcpRequestDlvry(String requestKey);
	void insertMcpRequestDvcChg(String requestKey);
	void insertMcpRequestMove(String requestKey);
	void insertMcpRequestPayment(String requestKey);
	void insertMcpRequestReq(String requestKey);
	void insertMcpRequestSaleinfo(String requestKey);
	void insertMcpRequestState(String requestKey);
	
	// MCP 신청삭제
	void deleteMcpRequest(String requestKey);
	void deleteMcpRequestAddition(String requestKey);
	void deleteMcpRequestAgent(String requestKey);
	void deleteMcpRequestChange(String requestKey);
	void deleteMcpRequestClause(String requestKey);
	void deleteMcpRequestCstmr(String requestKey);
	void deleteMcpRequestDlvry(String requestKey);
	void deleteMcpRequestDvcChg(String requestKey);
	void deleteMcpRequestMove(String requestKey);
	void deleteMcpRequestPayment(String requestKey);
	void deleteMcpRequestReq(String requestKey);
	void deleteMcpRequestSaleinfo(String requestKey);
	void deleteMcpRequestState(String requestKey);
	
	// 서식지정보 조회
	List<CanTransferVO> getScanFileList(String scanId);
	
	// 서식지이관
	void insertEmvScanMst(String scanId);
	void insertEmvScanFile(String scanId);
	
	// 서식지삭제
	void deleteEmvScanMst(String scanId);
	void deleteEmvScanFile(String scanId);
	void deleteMspScanMst(String scanId);
	void deleteMspScanFile(String scanId);
	void deleteCanScanMst(String scanId);
	void deleteCanScanFile(String scanId);
	
	// 해지후 180일이 지난 고객 조회
	List<CanTransferVO> getTmntCntrList(int stdrCnt);
	
	// 미납체크
	CanTransferVO getUnpayAmtCheck(CanTransferVO vo);
	
	// REQUEST_KEY 조회
	List<CanTransferVO> getRequestKey(String contractNum);
	
	// 해지고객 이관
	void insertMspRequest(String requestKey);
	void insertMspRequestAddition(String requestKey);
	void insertMspRequestAgent(String requestKey);
	void insertMspRequestChange(String requestKey);
	void insertMspRequestCstmr(String requestKey);
	void insertMspRequestDlvry(String requestKey);
	void insertMspRequestDtl(String requestKey);
	void insertMspRequestDvcChg(String requestKey);
	void insertMspRequestMove(String requestKey);
	void insertMspRequestPayment(String requestKey);
	void insertMspRequestReq(String requestKey);
	void insertMspRequestSaleinfo(String requestKey);
	void insertMspRequestState(String requestKey);
	
	// 현행화 이관
	void insertMspJuoSubInfo(String contractNum);
	void insertMspJuoSubInfoHist(String contractNum);
	void insertMspJuoCusInfo(String customerId);
	void insertMspJuoCusInfoHist(String customerId);
//		void insertMspJuoAddInfo(String contractNum);
	
	// 납부계정 체크 
	int getBanOtherCntrExist(CanTransferVO vo);
	
	// 납부계정 이관
	void insertMspJuoBanInfo(String ban);
	void insertMspJuoBanInfoHist(String ban);
	
//	// MCP 고객정보 변경
//	void updateMcpRequestAgent(String requestKey);
//	void updateMcpRequestChange(String requestKey);
//	void updateMcpRequestCstmr(String requestKey);
//	void updateMcpRequestDlvry(String requestKey);
//	void updateMcpRequestMove(String requestKey);
//	void updateMcpRequestReq(String requestKey);
	
	// MSP 고객정보 변경
	void updateMspRequestAgent(String requestKey);
	void updateMspRequestChange(String requestKey);
	void updateMspRequestCstmr(String requestKey);
	void updateMspRequestDlvry(String requestKey);
	void updateMspRequestDtl(String requestKey);
	void updateMspRequestMove(String requestKey);
	void updateMspRequestReq(String requestKey);
	
	// 현행화 정보 변경
	void updateMspJuoSubInfo(String contractNum);
	void updateMspJuoSubInfoHist(String contractNum);
	void updateMspJuoCusInfo(String customerId);
	void updateMspJuoCusInfoHist(String customerId);
	void updateMspJuoBanInfo(String ban);
	void updateMspJuoBanInfoHist(String ban);
	
	// MSP 신청삭제
	void deleteMspRequest(String requestKey);
	void deleteMspRequestAddition(String requestKey);
	void deleteMspRequestAgent(String requestKey);
	void deleteMspRequestChange(String requestKey);
	void deleteMspRequestCstmr(String requestKey);
	void deleteMspRequestDlvry(String requestKey);
	void deleteMspRequestDvcChg(String requestKey);
	void deleteMspRequestMove(String requestKey);
	void deleteMspRequestPayment(String requestKey);
	void deleteMspRequestReq(String requestKey);
	void deleteMspRequestSaleinfo(String requestKey);
	void deleteMspRequestState(String requestKey);
	void deleteMspRequestDtl(String contractNum);
	
	// CAN 신청삭제
	void deleteCanRequest(String requestKey);
	void deleteCanRequestAddition(String requestKey);
	void deleteCanRequestAgent(String requestKey);
	void deleteCanRequestChange(String requestKey);
	void deleteCanRequestClause(String requestKey);
	void deleteCanRequestCstmr(String requestKey);
	void deleteCanRequestDlvry(String requestKey);
	void deleteCanRequestDvcChg(String requestKey);
	void deleteCanRequestMove(String requestKey);
	void deleteCanRequestPayment(String requestKey);
	void deleteCanRequestReq(String requestKey);
	void deleteCanRequestSaleinfo(String requestKey);
	void deleteCanRequestState(String requestKey);
	void deleteCanRequestDtl(String contractNum);
	
	// 통신자료제공내역삭제
	int deleteCommDatPrvTxn(int stdrCnt);
	
	// 가입사실확인삭제
	int deleteInvstReqMst(int stdrCnt);
		
	// 삭제대상조회
	List<CanTransferVO> getTmntCustDataList(int stdrCnt);
	
	// MSP 현행화 삭제
	void deleteMspJuoSubInfo(String contractNum);
	void deleteMspJuoSubInfoHist(String contractNum);
	void deleteMspJuoCusInfo(String customerId);
	void deleteMspJuoCusInfoHist(String customerId);
	void deleteMspJuoBanInfo(String ban);
	void deleteMspJuoBanInfoHist(String ban);
	
	// CAN 현행화 삭제
	void deleteCanJuoSubInfo(String contractNum);
	void deleteCanJuoSubInfoHist(String contractNum);
	void deleteCanJuoCusInfo(String customerId);
	void deleteCanJuoCusInfoHist(String customerId);
	void deleteCanJuoBanInfo(String ban);
	void deleteCanJuoBanInfoHist(String ban);
	
	
}
