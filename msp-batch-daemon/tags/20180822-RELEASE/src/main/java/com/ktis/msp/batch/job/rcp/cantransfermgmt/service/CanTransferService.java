package com.ktis.msp.batch.job.rcp.cantransfermgmt.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanTransferMapper;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

@Service
public class CanTransferService extends BaseService {

	@Autowired
	private CanTransferMapper transferMapper;
	
	// 미개통고객 신청정보 삭제
	final static int NOT_OPEN_DT       = 365;
	// 해지고객 정보이관
	final static int TMNT_CUST_DT      = 180;
	// 해지고객 정보삭제
	final static int TMNT_CUST_DEL_DT  = 1645;
	// 통신자료 제공내역 삭제
	final static int COMM_DATA_DEL_DT  = 365;
	// 가입사실 제공내역 삭제
	final static int INVST_REQ_DEL_DT  = 365;
	
	/**
	 * 신청후 미개통고객 삭제
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procReqCustNotOpen() throws MvnoServiceException {
		int procCnt = 0;
		
		List<CanTransferVO> list = transferMapper.getRcpCustNotOpenList(NOT_OPEN_DT);
		
		try{
			for(CanTransferVO vo : list){
//				LOGGER.debug("requestKey=" + vo.getRequestKey() + ", scanId=" + vo.getScanId() + ", delYn=" + vo.getDelYn());
				
				// 데이터 이관
				// KOS 직개통 고객확인을 위하여 CAN 에 임시생성
				transferMapper.insertMcpRequest(vo.getRequestKey());
				transferMapper.insertMcpRequestAddition(vo.getRequestKey());
				transferMapper.insertMcpRequestAgent(vo.getRequestKey());
				transferMapper.insertMcpRequestChange(vo.getRequestKey());
				transferMapper.insertMcpRequestClause(vo.getRequestKey());
				transferMapper.insertMcpRequestCstmr(vo.getRequestKey());
				transferMapper.insertMcpRequestDlvry(vo.getRequestKey());
				transferMapper.insertMcpRequestDvcChg(vo.getRequestKey());
				transferMapper.insertMcpRequestMove(vo.getRequestKey());
				transferMapper.insertMcpRequestPayment(vo.getRequestKey());
				transferMapper.insertMcpRequestReq(vo.getRequestKey());
				transferMapper.insertMcpRequestSaleinfo(vo.getRequestKey());
				transferMapper.insertMcpRequestState(vo.getRequestKey());
				
				// 데이터 삭제
				transferMapper.deleteMcpRequest(vo.getRequestKey());
				transferMapper.deleteMcpRequestAddition(vo.getRequestKey());
				transferMapper.deleteMcpRequestAgent(vo.getRequestKey());
				transferMapper.deleteMcpRequestChange(vo.getRequestKey());
				transferMapper.deleteMcpRequestClause(vo.getRequestKey());
				transferMapper.deleteMcpRequestCstmr(vo.getRequestKey());
				transferMapper.deleteMcpRequestDlvry(vo.getRequestKey());
				transferMapper.deleteMcpRequestDvcChg(vo.getRequestKey());
				transferMapper.deleteMcpRequestMove(vo.getRequestKey());
				transferMapper.deleteMcpRequestPayment(vo.getRequestKey());
				transferMapper.deleteMcpRequestReq(vo.getRequestKey());
				transferMapper.deleteMcpRequestSaleinfo(vo.getRequestKey());
				transferMapper.deleteMcpRequestState(vo.getRequestKey());
				
				// 서식지삭제
				if("Y".equals(vo.getDelYn())){
					List<CanTransferVO> fileList = transferMapper.getScanFileList(vo.getScanId());
					for(CanTransferVO vo2 : fileList){
						LOGGER.debug("fileInfo=" + vo2.getFileInfo());
						
						File file = new File(vo2.getFileInfo());
						if(file.exists()){
							LOGGER.debug("파일 존재=" + vo2.getFileInfo());
							// 직개통 고객 이슈 존재
							file.delete();
						}else{
							LOGGER.debug("파일 미존재=" + vo2.getFileInfo());
						}
					}
				}
				
				// 서식지정보 이관
				transferMapper.insertEmvScanMst(vo.getScanId());
				transferMapper.insertEmvScanFile(vo.getScanId());
				
				// 서식지정보 삭제
				transferMapper.deleteEmvScanMst(vo.getScanId());
				transferMapper.deleteEmvScanFile(vo.getScanId());
				
				procCnt++;
			}
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1005", e);
		}
		
		return procCnt;
	}
	
	
	/**
	 * 해지후 180일 지난 고객 이관
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procTmntCustTransfer() throws MvnoServiceException {
		int procCnt = 0;
		LOGGER.info("======== 해지후 180일 지난 고객 조회 시작 =========");
		List<CanTransferVO> list = transferMapper.getTmntCntrList(TMNT_CUST_DT);
		LOGGER.info("======== 해지후 180일 지난 고객 조회 종료 =========");
		try{
			LOGGER.info("======== 해지후 180일 지난 고객 이관 시작 =========");
			for(CanTransferVO vo : list){
				LOGGER.debug("계약번호=" + vo.getContractNum());
				LOGGER.debug("서비스계약번호=" + vo.getSvcCntrNo());
				LOGGER.debug("고객ID=" + vo.getCustomerId());
				LOGGER.debug("납부계정번호=" + vo.getBan());
				
				// 미납체크
				CanTransferVO unpayVO = transferMapper.getUnpayAmtCheck(vo);
				
				LOGGER.debug("청구월=" + unpayVO.getBillYm());
				LOGGER.debug("미납금액=" + unpayVO.getUnpayAmnt());
				LOGGER.debug("할부원금=" + unpayVO.getInstOrignAmnt());
				LOGGER.debug("단말납부금=" + unpayVO.getEquistPymnAmnt());
				
				if(unpayVO.getUnpayAmnt() == null) unpayVO.setUnpayAmnt(0);
				if(unpayVO.getInstOrignAmnt() == null) unpayVO.setInstOrignAmnt(0);
				if(unpayVO.getEquistPymnAmnt() == null) unpayVO.setEquistPymnAmnt(0);
				
				// 미납금액이 없고 할부원금이 단말납부금액 이상인 경우 계약정보 이관
				if(unpayVO.getUnpayAmnt() <= 0 && (unpayVO.getEquistPymnAmnt() >= unpayVO.getInstOrignAmnt())){
					
					List<CanTransferVO> reqList = transferMapper.getRequestKey(vo.getContractNum());
					for(CanTransferVO reqVO : reqList){
						LOGGER.debug("requestKey=" + reqVO.getRequestKey());
						LOGGER.debug("scanId=" + reqVO.getScanId());
						
						// MSP 테이블 처리
						// 신청관련 테이블 이관
						transferMapper.insertMspRequest(reqVO.getRequestKey());
						transferMapper.insertMspRequestAddition(reqVO.getRequestKey());
						transferMapper.insertMspRequestAgent(reqVO.getRequestKey());
						transferMapper.insertMspRequestChange(reqVO.getRequestKey());
						transferMapper.insertMspRequestCstmr(reqVO.getRequestKey());
						transferMapper.insertMspRequestDlvry(reqVO.getRequestKey());
						transferMapper.insertMspRequestDtl(reqVO.getRequestKey());
						transferMapper.insertMspRequestDvcChg(reqVO.getRequestKey());
						transferMapper.insertMspRequestMove(reqVO.getRequestKey());
						transferMapper.insertMspRequestPayment(reqVO.getRequestKey());
						transferMapper.insertMspRequestReq(reqVO.getRequestKey());
						transferMapper.insertMspRequestSaleinfo(reqVO.getRequestKey());
						transferMapper.insertMspRequestState(reqVO.getRequestKey());
						
						// 서식지 정보 이관
						transferMapper.insertEmvScanMst(reqVO.getScanId());
						transferMapper.insertEmvScanFile(reqVO.getScanId());
						
						// 원본 테이블 고객정보 변경
						transferMapper.updateMspRequestAgent(reqVO.getRequestKey());
						transferMapper.updateMspRequestChange(reqVO.getRequestKey());
						transferMapper.updateMspRequestCstmr(reqVO.getRequestKey());
						transferMapper.updateMspRequestDlvry(reqVO.getRequestKey());
						transferMapper.updateMspRequestDtl(reqVO.getRequestKey());
						transferMapper.updateMspRequestMove(reqVO.getRequestKey());
						transferMapper.updateMspRequestReq(reqVO.getRequestKey());
						
						// MCP 테이블 처리
						transferMapper.insertMcpRequest(reqVO.getRequestKey());
						transferMapper.insertMcpRequestAddition(reqVO.getRequestKey());
						transferMapper.insertMcpRequestAgent(reqVO.getRequestKey());
						transferMapper.insertMcpRequestChange(reqVO.getRequestKey());
						transferMapper.insertMcpRequestClause(reqVO.getRequestKey());
						transferMapper.insertMcpRequestCstmr(reqVO.getRequestKey());
						transferMapper.insertMcpRequestDlvry(reqVO.getRequestKey());
						transferMapper.insertMcpRequestDvcChg(reqVO.getRequestKey());
						transferMapper.insertMcpRequestMove(reqVO.getRequestKey());
						transferMapper.insertMcpRequestPayment(reqVO.getRequestKey());
						transferMapper.insertMcpRequestReq(reqVO.getRequestKey());
						transferMapper.insertMcpRequestSaleinfo(reqVO.getRequestKey());
						transferMapper.insertMcpRequestState(reqVO.getRequestKey());
						
						// 데이터 삭제
						transferMapper.deleteMcpRequest(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestAddition(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestAgent(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestChange(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestClause(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestCstmr(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestDlvry(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestDvcChg(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestMove(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestPayment(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestReq(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestSaleinfo(reqVO.getRequestKey());
						transferMapper.deleteMcpRequestState(reqVO.getRequestKey());
					}
					
					// kt 연동테이블 이관
					transferMapper.insertMspJuoSubInfo(vo.getContractNum());
					transferMapper.insertMspJuoSubInfoHist(vo.getContractNum());
					transferMapper.insertMspJuoCusInfo(vo.getCustomerId());
					transferMapper.insertMspJuoCusInfoHist(vo.getCustomerId());
					
					// 원부테이블 고객정보 변경
					transferMapper.updateMspJuoSubInfo(vo.getContractNum());
					transferMapper.updateMspJuoSubInfoHist(vo.getContractNum());
					transferMapper.updateMspJuoCusInfo(vo.getCustomerId());
					transferMapper.updateMspJuoCusInfoHist(vo.getCustomerId());
					
					procCnt++;
					
				}
				
				// 납부자정보 체크
				if(transferMapper.getBanOtherCntrExist(vo) == 0){
					// 다른 계약이 없으므로 이관처리
					transferMapper.insertMspJuoBanInfo(vo.getBan());
					transferMapper.insertMspJuoBanInfoHist(vo.getBan());
					
					// 원부테이블 고객정보 변경
					transferMapper.updateMspJuoBanInfo(vo.getBan());
					transferMapper.updateMspJuoBanInfoHist(vo.getBan());
				}
			}
			LOGGER.info("======== 해지후 180일 지난 고객 이관 종료 =========");
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1006", e);
		}
		
		return procCnt;
	}
	
	/**
	 * 1년이 경과된 통신자료 제공내역 삭제
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteCommDatPrvTxn() throws MvnoServiceException {
		
		int procCnt = transferMapper.deleteCommDatPrvTxn(COMM_DATA_DEL_DT);
		
		return procCnt;
	}
	
	/**
	 * 1년이 경과된 가입사실 확인내역 삭제
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteInvstReqMst() throws MvnoServiceException {
		
		int procCnt = transferMapper.deleteInvstReqMst(INVST_REQ_DEL_DT);
		
		return procCnt;
	}
	
	/**
	 * MSP_CAN 으로 이관된 데이터를 최종 삭제
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteTmntCustData() throws MvnoServiceException {
		int procCnt = 0;
		
		// 이관 후 삭제대상 조회
		List<CanTransferVO> list = transferMapper.getTmntCustDataList(TMNT_CUST_DEL_DT);
		
		try{
			for(CanTransferVO vo : list){
				
				List<CanTransferVO> reqList = transferMapper.getRequestKey(vo.getContractNum());
				for(CanTransferVO reqVO : reqList){
					LOGGER.debug("requestKey=" + reqVO.getRequestKey());
					LOGGER.debug("scanId=" + reqVO.getScanId());
					
					transferMapper.deleteMcpRequest(reqVO.getRequestKey());
					transferMapper.deleteMspRequest(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestSaleinfo(reqVO.getRequestKey());
					transferMapper.deleteMspRequestSaleinfo(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestCstmr(reqVO.getRequestKey());
					transferMapper.deleteMspRequestCstmr(reqVO.getRequestKey());
					transferMapper.deleteCanRequestCstmr(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestAgent(reqVO.getRequestKey());
					transferMapper.deleteMspRequestAgent(reqVO.getRequestKey());
					transferMapper.deleteCanRequestAgent(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestDlvry(reqVO.getRequestKey());
					transferMapper.deleteMspRequestDlvry(reqVO.getRequestKey());
					transferMapper.deleteCanRequestDlvry(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestAddition(reqVO.getRequestKey());
					transferMapper.deleteMspRequestAddition(reqVO.getRequestKey());
					
					transferMapper.deleteMspRequestChange(reqVO.getRequestKey());
					transferMapper.deleteCanRequestChange(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestDvcChg(reqVO.getRequestKey());
					transferMapper.deleteMspRequestDvcChg(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestMove(reqVO.getRequestKey());
					transferMapper.deleteMspRequestMove(reqVO.getRequestKey());
					transferMapper.deleteCanRequestMove(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestPayment(reqVO.getRequestKey());
					transferMapper.deleteMspRequestPayment(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestReq(reqVO.getRequestKey());
					transferMapper.deleteMspRequestReq(reqVO.getRequestKey());
					transferMapper.deleteCanRequestReq(reqVO.getRequestKey());
					
					transferMapper.deleteMcpRequestState(reqVO.getRequestKey());
					transferMapper.deleteMspRequestState(reqVO.getRequestKey());
					
					transferMapper.deleteMspRequestDtl(reqVO.getRequestKey());
					transferMapper.deleteCanRequestDtl(reqVO.getRequestKey());
					
					// 서식지삭제
					List<CanTransferVO> fileList = transferMapper.getScanFileList(reqVO.getScanId());
					for(CanTransferVO scanVO : fileList){
						LOGGER.debug("fileInfo=" + scanVO.getFileInfo());
						
						File file = new File(scanVO.getFileInfo());
						if(file.exists()){
							LOGGER.debug("파일 존재=" + scanVO.getFileInfo());
							// 직개통 고객 이슈 존재
							file.delete();
						}else{
							LOGGER.debug("파일 미존재=" + scanVO.getFileInfo());
						}
					}
					
					// MCP SCAN 삭제
					transferMapper.deleteEmvScanMst(reqVO.getScanId());
					transferMapper.deleteEmvScanFile(reqVO.getScanId());
					
					// MSP SCAN 삭제
					transferMapper.deleteMspScanMst(reqVO.getScanId());
					transferMapper.deleteMspScanFile(reqVO.getScanId());
					
					// CAN SCAN 삭제
					transferMapper.deleteCanScanMst(reqVO.getScanId());
					transferMapper.deleteCanScanFile(reqVO.getScanId());
				}
				
				// MSP 현행화 삭제
				transferMapper.deleteMspJuoSubInfo(vo.getContractNum());
				transferMapper.deleteMspJuoSubInfoHist(vo.getContractNum());
				transferMapper.deleteMspJuoCusInfo(vo.getCustomerId());
				transferMapper.deleteMspJuoCusInfoHist(vo.getCustomerId());
				transferMapper.deleteMspJuoBanInfo(vo.getBan());
				transferMapper.deleteMspJuoBanInfoHist(vo.getBan());
				
				// CAN 현행화 삭제
				transferMapper.deleteCanJuoSubInfo(vo.getContractNum());
				transferMapper.deleteCanJuoSubInfoHist(vo.getContractNum());
				transferMapper.deleteCanJuoCusInfo(vo.getCustomerId());
				transferMapper.deleteCanJuoCusInfoHist(vo.getCustomerId());
				transferMapper.deleteCanJuoBanInfo(vo.getBan());
				transferMapper.deleteCanJuoBanInfoHist(vo.getBan());
				
				procCnt++;
			}
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1007", e);
		}
		
		return procCnt;
	}
	
}
