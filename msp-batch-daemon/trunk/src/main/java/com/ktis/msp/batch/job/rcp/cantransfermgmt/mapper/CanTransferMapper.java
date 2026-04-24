package com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.job.cmn.sellusim.vo.SellUsimVO;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.UsimDlvryCanVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CanTransferMapper")
public interface CanTransferMapper {

	// 신청후 미개통고객 조회
	List<CanTransferVO> getRcpCustNotOpenList(int stdrCnt);
	
	// 개통 된 서식지 여부 조회
	String isOpenScanId(String scanId);

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

	// MCP서식지 정보 변경
	void updateEmvScanMst(String scanId);
	// MSP서식지 정보 변경
	void updateMspScanMst(String scanId);
	
	// 서식지삭제
	void deleteEmvScanMst(String scanId);
	void deleteEmvScanFile(String scanId);
	void deleteMspScanMst(String scanId);
	void deleteMspScanFile(String scanId);
	void deleteCanScanMst(String scanId);
	void deleteCanScanFile(String scanId);

	// 해지후 180일이 지난 고객 조회
	List<CanTransferVO> getTmntCntrList(Map<String, Object> paramMap);
	
	// 해지후 180일이 지난 고객 건수 조회
	int getTmntCntrListCnt(Map<String, Object> paramMap);
			
	// REQUEST_KEY 조회
	List<CanTransferVO> getRequestKey(String contractNum);
	// MCP_REQUEST REQUEST_KEY 조회
	List<CanTransferVO> getMcpRequestKey(String contractNum);
	// CAN.MCP_REQEUST REQUEST_KEY 조회
	List<CanTransferVO> getCanRequestKey(String contractNum);

	// 해지고객 이관
	int insertMspRequest(String requestKey);
	void insertMspRequest2(String requestKey);
	void insertMspRequestAddition(String requestKey);
	void insertMspRequestAgent(String requestKey);
	void insertMspRequestChange(String requestKey);
	void insertMspRequestCstmr(String requestKey);
	void insertMspRequestDlvry(String requestKey);
	void insertMspRequestDtl(String contractNum);
	void insertMspRequestDvcChg(String requestKey);
	void insertMspRequestMove(String requestKey);
	void insertMspRequestPayment(String requestKey);
	void insertMspRequestReq(String requestKey);
	void insertMspRequestSaleinfo(String requestKey);
	void insertMspRequestState(String requestKey);

	// 현행화 이관
	void insertMspJuoSubInfo(CanTransferVO canTransferVO);
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
	
	// 납부계정 체크
	int getOtherCntrExist(CanTransferVO vo);

	// MSP 고객정보 변경
	void updateMspRequestAgent(String requestKey);
	void updateMspRequestChange(String requestKey);
	void updateMspRequestCstmr(String requestKey);
	void updateMspRequestDlvry(String requestKey);
	void updateMspRequestDtl(String contractNum);
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

	//MSP 해지이력테이블 삭제
	void deleteMspReentranceMst(String contractNum);

	// CAN 현행화 삭제
	void deleteCanJuoSubInfo(String contractNum);
	void deleteCanJuoSubInfoHist(String contractNum);
	void deleteCanJuoCusInfo(String customerId);
	void deleteCanJuoCusInfoHist(String customerId);
	void deleteCanJuoBanInfo(String ban);
	void deleteCanJuoBanInfoHist(String ban);

	// 판매유심 해지 후 180일 지난 고객 이관
	List<SellUsimVO> selectSellUsimTransferList(int stdrCnt);
	void insertMcpSellUsim(String sellUsimKey);
	void deleteMcpSellUsim(String sellUsimKey);

	// 판매유심 해지 후 1825일 지난 고객 증 MSP_CAN 으로 이관된 데이터 최종삭제
	void deleteCanMcpSellUsim(int stdrCnt);
	
	// 1년이상 홈페이지 미 사용한 준회원 5년 뒤 삭제
	int deleteMcpUser();
	// 6개월 후 데이터 이관
	int insertMcpRecUser();
	// 이관 된 데이터 삭제
	int deleteMcpRecUser();
	// 이관 후 4년 6개월 이후 최종 삭제
	int deleteMcpRecUserCan();
	
	// 단말보험해지자 개인정보 마스킹 처리
	int updateIntmInsrMember(int stdrCnt);
	int updateIntmInsrIfOut(int stdrCnt);
	int updateIntmInsrCmpnMstIf(int stdrCnt);	
	
	//명의도용주장 IP 설정 고객 데이터 삭제
	int deleteReuseRipMstData(int stdrCnt);
	int deleteReuseRipHistData(int stdrCnt);
	
	// CAN DB 서식지 경로 UPDATE
	void updateCanEmvScanFile(CanTransferVO updateVo);
	//MCP DB 서식지 경로 UPDATE
	void updateMcpEmvScanFile(CanTransferVO updateVo);
	// MSP서식지 정보 변경
	void updateMspEmvScanFile(CanTransferVO updateVo);
	// 서식지파일정보 조회
	List<CanTransferVO> getScanFileInfoList(String scanId);
	
	// 중복스캔아이디 체크
	int getOtherScanExist(CanTransferVO vo);

	// 010셀프개통 신청정보 이관
	int insertMcpSelfSmsAuth(String requestKey);
	int updateMcpSelfSmsAuth(String requestKey);
	// TOSS 가입자 오더정보 이관
	int insertMdsTossOrder(String subscriberNo);
	int updateMdsTossOrder(String subscriberNo);
	// OSST 가입자 오더정보 이관
	int insertMdsOsstOrder(String subscriberNo);
	int updateMdsOsstOrder(String subscriberNo);
	// 신한요금제 데이터쿠폰 대상자 정보 이관
	int insertMdsShinhanProdSbsc(String svcCntrNo);
	int updateMdsShinhanProdSbsc(String svcCntrNo);
	// 3G 유지가입자 현황 고객정보 치환
	int updateMspDaily3gSub(String contractNum);	
	// 보상서비스 가입자목록 고객정보 치환
	int updateMspRwdInfoOut(String contractNum);
	int updateMspRwdMember(String contractNum);

	// 010셀프개통 신청정보 삭제
	int deleteMcpSelfSmsAuth(String requestKey);
	int deleteCanSelfSmsAuth(String requestKey);
	// TOSS 가입자 오더정보 삭제
	int deleteMdsTossOrder(String subscriberNo);
	int deleteCanTossOrder(String subscriberNo);
	// OSST 가입자 오더정보 삭제
	int deleteMdsOsstOrder(String subscriberNo);
	int deleteCanOsstOrder(String subscriberNo);
	// 신한요금제 데이터쿠폰 대상자 정보 삭제
	int deleteMdsShinhanProdSbsc(String svcCntrNo);
	int deleteCanShinhanProdSbsc(String svcCntrNo);
	// 예약상담 고객 정보 삭제
	int deleteMspCsResCstmr(String contractNum);
	// 3G 유지가입자 현황 삭제
	int deleteMspDaily3gSub(String contractNum);
	// 보상서비스 가입자목록 삭제
	int deleteMspRwdInfoOut(String contractNum);
	int deleteMspRwdMember(String contractNum);

	//마케팅수신동의 내역 해지후 5년 경과 고객데이터 삭제
	int deleteMarketingSendTargetdata(String contractNum);
	
	// 단말 보험 가입자 정보 이관
	int insertCanIntmInsrIfOut(String contractNum);
	int insertCanIntmInsrCmpnMstIf(String contractNum);
	int insertCanIntmInsrOrder(String contractNum);
	int insertCanIntmInsrMember(String contractNum);
	int insertCanIntmInsrCmpnDtl(String contractNum);
	int insertCanIntmInsrCanInfo(String contractNum);
	
	// 단말 보험 가입자 정보 치환
	int updateMspIntmInsrIfOut(String contractNum);
	int updateMspIntmInsrCmpnMstIf(String contractNum);
	int updateMspIntmInsrCanInfo(String contractNum);
	int updateMspIntmInsrCmpnDtl(String contractNum);
	int updateMspIntmInsrMember(String contractNum);
//	int updateMspIntmInsrOrder(String contractNum); -- 고객정보없음

	// 단말 보험 가입자 정보 삭제
	int deleteMspIntmInsrIfOut(String contractNum);
	int deleteMspIntmInsrCmpnMstIf(String contractNum);
	int deleteMspIntmInsrCanInfo(String contractNum);
	int deleteMspIntmInsrCmpnDtl(String contractNum);
	int deleteMspIntmInsrMember(String contractNum);
	int deleteMspIntmInsrOrder(String contractNum);
	int deleteMspIntmInsrReady(String contractNum);
	
	// CAN 단말 보험 가입자 정보 삭제
	int deleteCanIntmInsrIfOut(String contractNum);
	int deleteCanIntmInsrCmpnMstIf(String contractNum);
	int deleteCanIntmInsrCanInfo(String contractNum);
	int deleteCanIntmInsrCmpnDtl(String contractNum);
	int deleteCanIntmInsrMember(String contractNum);
	int deleteCanIntmInsrOrder(String contractNum);
	
	// 2026.04.16 바로배송유심관리 데이터 정보 삭제
	int deleteMspDirDlvryUsimMst(String contractNum);

	// 배치 긴급 정지 여부
	String getTmntCustProcStopYn(Map<String, Object> paramMap);


	void insertMcpRequestDtlCan(String requestKey);
	void deleteMcpRequestDtl(String requestKey);
	void deleteMcpRequestDtlCan(String requestKey);

	// 유심구매고객 이관 대상 조회
	List<UsimDlvryCanVO> getUsimDlvryDlvryIdx(Map<String, String> paramMap);
	List<UsimDlvryCanVO> getUsimDlvryNowDlvryIdx(Map<String, String> paramMap);

	// 유심구매고객 삭제 대상 조회
	List<UsimDlvryCanVO> getUsimDlvryCanDlvryIdx(Map<String, String> paramMap);
	List<UsimDlvryCanVO> getUsimDlvryCanNowDlvryIdx(Map<String, String> paramMap);

	// 유심교체고객 이관
	void insertNmcpFreeUsimReqMst(String customerId);
	void updateNmcpFreeUsimReqMst(String customerId);
	void deleteMcpNmcpFreeUsimReqMst(String customerId);
	void deleteMcpNmcpFreeUsimReqDtl(String contractNum);
	void deleteCanNmcpFreeUsimReqMst(String customerId);
	
	// 사은품 VOC
	void insertCanGiftVocMgmt(String contractNum);
	void insertCanGiftPayStat(String contractNum);
	void deleteMspGiftVocMgmt(String contractNum);
	void deleteMspGiftPayStat(String contractNum);
	void deleteCanGiftVocMgmt(String contractNum);
	void deleteCanGiftPayStat(String contractNum);

	//신청정보(유심교체) 다회선 신청유무 체크
	int getOtherUsimChgExist(CanTransferVO vo);
	
}
