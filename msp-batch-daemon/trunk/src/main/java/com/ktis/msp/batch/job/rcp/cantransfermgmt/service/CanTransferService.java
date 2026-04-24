package com.ktis.msp.batch.job.rcp.cantransfermgmt.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanPpTransferMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.UsimDlvryCanMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.UsimDlvryCanService;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.UsimDlvryCanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.CanTransferMapper;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class CanTransferService extends BaseService {

	@Autowired
	private CanTransferMapper transferMapper;

	@Autowired
	private CanPpTransferMapper pPtransferMapper;

	@Autowired
	private UsimDlvryCanMapper usimDlvryCanMapper;

	// 미개통고객 신청정보 삭제
	final static int NOT_OPEN_DT       = 60;
	// 해지고객 정보이관
	final static int TMNT_CUST_DT      = 180;
	// 해지고객 정보삭제
//	final static int TMNT_CUST_DEL_DT  = 1645;
	final static int TMNT_CUST_DEL_DT  = 1825;
	// 통신자료 제공내역 삭제
	final static int COMM_DATA_DEL_DT  = 365;
	// 가입사실 제공내역 삭제
	final static int INVST_REQ_DEL_DT  = 365;
	// 단말보험 해지자 고객정보 마스킹 처리
	final static int INTM_INSR_MASK_DT = 365;
	//서식지 경로 선언
	final static String ORIGIN_DIR = "ORIGIN_DIR";
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

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

				// 데이터 이관
				// KOS 직개통 고객확인을 위하여 CAN 에 임시생성
				//2020년 09월 16일 
				//고객의 요청으로 인하여 7일동안 신청서를 보관하고 can 서버에 이관하지 않고 삭제
//				transferMapper.insertMcpRequest(vo.getRequestKey());
//				transferMapper.insertMcpRequestAddition(vo.getRequestKey());
//				transferMapper.insertMcpRequestAgent(vo.getRequestKey());
//				transferMapper.insertMcpRequestChange(vo.getRequestKey());
//				transferMapper.insertMcpRequestClause(vo.getRequestKey());
//				transferMapper.insertMcpRequestCstmr(vo.getRequestKey());
//				transferMapper.insertMcpRequestDlvry(vo.getRequestKey());
//				transferMapper.insertMcpRequestDvcChg(vo.getRequestKey());
//				transferMapper.insertMcpRequestMove(vo.getRequestKey());
//				transferMapper.insertMcpRequestPayment(vo.getRequestKey());
//				transferMapper.insertMcpRequestReq(vo.getRequestKey());
//				transferMapper.insertMcpRequestSaleinfo(vo.getRequestKey());
//				transferMapper.insertMcpRequestState(vo.getRequestKey());

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

				transferMapper.deleteMcpRequestDtl(vo.getRequestKey());
				// 010셀프개통 신청정보 삭제
				transferMapper.deleteMcpSelfSmsAuth(vo.getRequestKey());

				if("N".equals(transferMapper.isOpenScanId(vo.getScanId()))) {
					
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
//					transferMapper.insertEmvScanMst(vo.getScanId());
//					transferMapper.insertEmvScanFile(vo.getScanId());

					// 서식지정보 삭제
					transferMapper.deleteEmvScanMst(vo.getScanId());
					transferMapper.deleteEmvScanFile(vo.getScanId());
				}

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

		LOGGER.info("해지후 180일 지난 고객 이관 START");
		String canImageTransferDirOld = propertiesService.getString("canImageTransferDirOld");
		String canImageTransferDir = propertiesService.getString("canImageTransferDir");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stdrCnt", TMNT_CUST_DT);
		
		int targetCnt = transferMapper.getTmntCntrListCnt(paramMap);
		paramMap.put("targetCnt", targetCnt);
		
		paramMap.put("cdVal", "CAN_TRANS_STOP");
		
		// 해지고객 이관대상 조회
		List<CanTransferVO> list = transferMapper.getTmntCntrList(paramMap);
		
		String errContNum = "";
		
		try{
			for(CanTransferVO vo : list){
				//강제 종료 기능 추가
				LOGGER.info("해지 고객 이관 배치 처리 현황 ("+list.size()+"/"+procCnt+")");
				String stopYn = transferMapper.getTmntCustProcStopYn(paramMap);
				if(stopYn != null && stopYn.equals("Y")) {
					LOGGER.info("======================= STOP =======================");
					break;
				}
				
				errContNum = vo.getContractNum();
				
				LOGGER.debug("계약번호=" + vo.getContractNum());
				LOGGER.debug("서비스계약번호=" + vo.getSvcCntrNo());
				LOGGER.debug("고객ID=" + vo.getCustomerId());
				LOGGER.debug("납부계정번호=" + vo.getBan());
		       	
				vo.setTmntCustDt(TMNT_CUST_DT);

				List<CanTransferVO> reqList = transferMapper.getRequestKey(vo.getContractNum());
				if (reqList.isEmpty()) {
				    reqList = transferMapper.getMcpRequestKey(vo.getContractNum());
				}
				
				for(CanTransferVO reqVO : reqList){
					LOGGER.debug("requestKey=" + reqVO.getRequestKey());
					LOGGER.debug("scanId=" + reqVO.getScanId());
					reqVO.setContractNum(vo.getContractNum());
					// MSP 테이블 처리
					// 신청관련 테이블 이관 (전부 @DL_CAN에 insert)
					if (transferMapper.insertMspRequest(reqVO.getRequestKey()) == 0) {
						transferMapper.insertMspRequest2(reqVO.getRequestKey());
					}
					//transferMapper.insertMspRequestAddition(reqVO.getRequestKey());
					//transferMapper.insertMspRequestAgent(reqVO.getRequestKey());
					//transferMapper.insertMspRequestChange(reqVO.getRequestKey());
					//transferMapper.insertMspRequestCstmr(reqVO.getRequestKey());
					//transferMapper.insertMspRequestDlvry(reqVO.getRequestKey());
					//transferMapper.insertMspRequestDtl(reqVO.getRequestKey());
					//transferMapper.insertMspRequestDvcChg(reqVO.getRequestKey());
					//transferMapper.insertMspRequestMove(reqVO.getRequestKey());
					//transferMapper.insertMspRequestPayment(reqVO.getRequestKey());
					//transferMapper.insertMspRequestReq(reqVO.getRequestKey());
					//transferMapper.insertMspRequestSaleinfo(reqVO.getRequestKey());
					//transferMapper.insertMspRequestState(reqVO.getRequestKey());

					// 서식지 정보 이관 (전부 @DL_CAN에 insert)
					transferMapper.insertEmvScanMst(reqVO.getScanId());
					transferMapper.insertEmvScanFile(reqVO.getScanId());

					// 원본 테이블 고객정보 변경 (전부 msp 테이블의 개인정보 update 예)이름->이관고객)
					transferMapper.updateMspRequestAgent(reqVO.getRequestKey());
					transferMapper.updateMspRequestChange(reqVO.getRequestKey());
					transferMapper.updateMspRequestCstmr(reqVO.getRequestKey());
					transferMapper.updateMspRequestDlvry(reqVO.getRequestKey());
					//검색조건 REQUEST_KEY -> CONTRACT_NUM 으로 변경(2023.11.07)
					transferMapper.updateMspRequestDtl(reqVO.getContractNum());
					transferMapper.updateMspRequestMove(reqVO.getRequestKey());
					transferMapper.updateMspRequestReq(reqVO.getRequestKey());

					// MCP서식지 정보 변경(개인정보 update)
					transferMapper.updateEmvScanMst(reqVO.getScanId());
					// MSP서식지 정보 변경(개인정보 update)
					transferMapper.updateMspScanMst(reqVO.getScanId());

					// MCP 테이블 처리 (전부 @DL_CAN에 insert)
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
					transferMapper.insertMcpRequestDtlCan(reqVO.getRequestKey());

					// 데이터 삭제 (전부 @DL_MCP delete)
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
					transferMapper.deleteMcpRequestDtl(reqVO.getRequestKey());

					// 010셀프개통 신청정보 이관
					transferMapper.insertMcpSelfSmsAuth(reqVO.getRequestKey());
					transferMapper.updateMcpSelfSmsAuth(reqVO.getRequestKey());

					// 유심구매 정보 이관
					this.transferUsimDlvryInfoByCondition("01", reqVO.getRequestKey());

					//중복스캔 아이디가 존재하는지 체크
					if(transferMapper.getOtherScanExist(reqVO) == 0){
					
						//이관고객 서식지 nas2 로 서식지 이동
						//data_can nas로 서식지 이동 20251028
						//emv_scan_file 테이블에서 서식지파일 조회
						List<CanTransferVO> scanList = transferMapper.getScanFileInfoList(reqVO.getScanId());
						
						LOGGER.debug("fileinfo 건수 ");
						for(CanTransferVO scanVO : scanList){
							LOGGER.debug("fileInfo=" + scanVO.getFileInfo());
							
							///jboss/data/webscan/prd/image/ORIGIN_DIR/E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG => /E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG
							String[]  strArray = scanVO.getFileInfo().split(ORIGIN_DIR);
							///E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG
							String fileDirDtl = strArray[1]; 
							///jboss/data/webscan/prd/image/ORIGIN_DIR
							String frontDir = strArray[0] + ORIGIN_DIR;
							LOGGER.debug("fileDirDtl===  " + fileDirDtl);
							LOGGER.debug("frontDir===  " + frontDir);
							LOGGER.debug("canImageTransferDir===  " + canImageTransferDir);
							
							///E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG => jboss/data2/webscan/prd/image/ORIGIN_DIR/E0001/2017/07/27/EA9DD96A63DC62499AFF08BC0680F2AD.JPG
						   String tobeFileInfo = canImageTransferDir + fileDirDtl;
						   LOGGER.debug("tobeFileInfo === " + tobeFileInfo);
						   
							//as-is 경로 , to-be 경로선언
						   File asIsFile = new File(scanVO.getFileInfo());
						   File toBeFile = new File(tobeFileInfo);
						   
						 boolean mkdir = false;					 
						 //이미 data2, data_can으로 이관된 서식지는 작업하지 않는다.
						   if (!canImageTransferDirOld.equals(frontDir) && !canImageTransferDir.equals(frontDir)){
							   try{
								 //경로추출&선언
								   String [] dirArry = tobeFileInfo.split("/" + scanVO.getFileId());
								   String fileDir = dirArry[0];
								   File Folder = new File(fileDir);  
		
									//디렉토리가 없으면 디렉토리 생성
									if(!Folder.exists()){
										mkdir = Folder.mkdirs();
										LOGGER.debug("디렉토리 생성 == " + fileDir);
										  if (mkdir == false)
								            {
								               throw new Exception("[" + fileDir + "] Create Directory Fail");
								            }
									}else{
										mkdir = true;
										LOGGER.debug("디렉토리가 존재합니다.");
									}
							   }catch(Exception e){
								   throw new MvnoServiceException("ERCP1006", e);
							   }
								
								//서식지 파일 이관
								try {
									
									CanTransferVO updateVo = new CanTransferVO();
									updateVo.setScanId(scanVO.getScanId());  //파일아이디 set
									updateVo.setFileId(scanVO.getFileId());      //파일아이디 set
									updateVo.setTobeFileInfo(tobeFileInfo);      //to-be nas2 경로 set
							
									
									int data = 0;
										if(fileIsLive(scanVO.getFileInfo())) {
											FileInputStream fis = new FileInputStream(asIsFile);
											FileOutputStream fos = new FileOutputStream(toBeFile);
											LOGGER.debug("파일있음 --> " + scanVO.getFileInfo());
											while((data=fis.read())!=-1){
												fos.write(data);
											}
											fis.close();
											fos.close();
											LOGGER.debug("파일삭제 >>>> ");
											//이미 이관된 서식지의 경우 삭제하지 않는다.
											
											//as -is 경로 서식지 삭제
											asIsFile.delete();
											
											}else{
												LOGGER.debug("FAILED] 파일 없음 --> " + asIsFile);
											}
										//can db 에 바뀐경로 update
										transferMapper.updateCanEmvScanFile(updateVo);
										//mcp db에 바뀐경로 및 정보 update
										transferMapper.updateMcpEmvScanFile(updateVo);
										//msp db에 바뀐경로 및 정보 update
										transferMapper.updateMspEmvScanFile(updateVo);	
										
								}catch(Exception e){
									throw new MvnoServiceException("ERCP1006", e);
								}
						   }
						   
						}
					}
				}

				// kt 연동테이블 이관
				transferMapper.insertMspJuoSubInfo(vo);
				transferMapper.insertMspJuoSubInfoHist(vo.getContractNum());
				//transferMapper.insertMspJuoCusInfo(vo.getCustomerId());
				//transferMapper.insertMspJuoCusInfoHist(vo.getCustomerId());

				// 원부테이블 고객정보 변경
				transferMapper.updateMspJuoSubInfo(vo.getContractNum());
				transferMapper.updateMspJuoSubInfoHist(vo.getContractNum());
				//transferMapper.updateMspJuoCusInfo(vo.getCustomerId());
				//transferMapper.updateMspJuoCusInfoHist(vo.getCustomerId());
				
				// 해지 후 180일 미만 또는 사용중인 계약이 없는 경우 에만 고객 정보 이관
				if(transferMapper.getOtherCntrExist(vo) == 0) { 
					transferMapper.insertMspJuoCusInfo(vo.getCustomerId());
					transferMapper.insertMspJuoCusInfoHist(vo.getCustomerId());
					transferMapper.updateMspJuoCusInfo(vo.getCustomerId());
					transferMapper.updateMspJuoCusInfoHist(vo.getCustomerId());
				} else {
					transferMapper.insertMspJuoCusInfo(vo.getCustomerId());
				}
				
				
				//해지이력테이블 데이터삭제
				transferMapper.deleteMspReentranceMst(vo.getContractNum());

				// 납부자정보 체크				
				if(transferMapper.getBanOtherCntrExist(vo) == 0){
					// 다른 계약이 없으므로 이관처리
					transferMapper.insertMspJuoBanInfo(vo.getBan());
					transferMapper.insertMspJuoBanInfoHist(vo.getBan());

					// 원부테이블 고객정보 변경
					transferMapper.updateMspJuoBanInfo(vo.getBan());
					transferMapper.updateMspJuoBanInfoHist(vo.getBan());
				} else {
					transferMapper.insertMspJuoBanInfo(vo.getBan());
				}
				
				// TOSS, OSST 가입자 오더정보 이관
				transferMapper.insertMdsTossOrder(vo.getSubscriberNo());
				transferMapper.updateMdsTossOrder(vo.getSubscriberNo());
				transferMapper.insertMdsOsstOrder(vo.getSubscriberNo());
				transferMapper.updateMdsOsstOrder(vo.getSubscriberNo());
				
				// 신한요금제 데이터쿠폰 대상자 정보 이관
				transferMapper.insertMdsShinhanProdSbsc(vo.getSvcCntrNo());
				transferMapper.updateMdsShinhanProdSbsc(vo.getSvcCntrNo());

				// 3G 유지가입자 현황 고객정보 치환
				transferMapper.updateMspDaily3gSub(vo.getContractNum());

				// 보상서비스 가입자목록 고객정보 치환
				transferMapper.updateMspRwdInfoOut(vo.getContractNum());
				transferMapper.updateMspRwdMember(vo.getContractNum());
				
				// 단말 보험 가입자 고객정보 이관
				transferMapper.insertCanIntmInsrIfOut(vo.getContractNum());
				transferMapper.insertCanIntmInsrCmpnMstIf(vo.getContractNum());
				transferMapper.insertCanIntmInsrOrder(vo.getContractNum());
				transferMapper.insertCanIntmInsrMember(vo.getContractNum());
				transferMapper.insertCanIntmInsrCmpnDtl(vo.getContractNum());
				transferMapper.insertCanIntmInsrCanInfo(vo.getContractNum());
				
				// 단말 보험 가입자 정보 치환
				transferMapper.updateMspIntmInsrIfOut(vo.getContractNum());
				transferMapper.updateMspIntmInsrCmpnMstIf(vo.getContractNum());
				transferMapper.updateMspIntmInsrCanInfo(vo.getContractNum());
				transferMapper.updateMspIntmInsrCmpnDtl(vo.getContractNum());
				transferMapper.updateMspIntmInsrMember(vo.getContractNum());

				// 유심구매 정보 이관
				this.transferUsimDlvryInfoByCondition("02", vo.getContractNum());

				// 신청정보(유심교체) 정보 이관
				if(transferMapper.getOtherUsimChgExist(vo) == 0) {
					transferMapper.insertNmcpFreeUsimReqMst(vo.getCustomerId());
					transferMapper.updateNmcpFreeUsimReqMst(vo.getCustomerId());
				}
				
				// 사은품 VOC
				transferMapper.insertCanGiftVocMgmt(vo.getContractNum());
				transferMapper.insertCanGiftPayStat(vo.getContractNum());
				transferMapper.deleteMspGiftVocMgmt(vo.getContractNum());
				transferMapper.deleteMspGiftPayStat(vo.getContractNum());
				procCnt++;
			}

		}catch(Exception e){
			LOGGER.error("CONT_NUM : "+errContNum);
			throw new MvnoServiceException("ERCP1006", e);
		}

		LOGGER.info("해지후 180일 지난 고객 이관 End");

//		종료일자 기준값이 모호하여 주석처리 2019.07.15
//		LOGGER.info("판매유심 해지 후 180일 지난 고객 이관 START");
//
//		/*이관대상 해지고객정보 조회*/
//		List<SellUsimVO> sellUsimList = transferMapper.selectSellUsimTransferList(TMNT_CUST_DT);
//
//		int totalCnt = 0;
//		int successCnt = 0;
//
//		for(SellUsimVO vo : sellUsimList) {
//
//			totalCnt++;
//
//			String sellUsimKey = vo.getSellUsimKey();
//
//			try{
//				/*해지고객정보 이관*/
//				transferMapper.insertMcpSellUsim(sellUsimKey);
//
//				/*해지고객정보 삭제*/
//				transferMapper.deleteMcpSellUsim(sellUsimKey);
//
//				successCnt++;
//				procCnt++;
//			}catch(Exception e){
//				LOGGER.info("MCP_SELL_USIM 정보 이관 실패 : " + sellUsimKey);
//			}
//		}
//
//		LOGGER.info("총 이관 대상 수 : " + totalCnt);
//		LOGGER.info("이관 성공 수 : " + successCnt);
//
//		LOGGER.info("판매유심 해지 후 180일 지난 고객 이관 End");

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
		
		LOGGER.info("이관 후 삭제 처리 START");

		// 이관 후 삭제대상 조회
		List<CanTransferVO> list = transferMapper.getTmntCustDataList(TMNT_CUST_DEL_DT);
		
		//PPS_RCG_REMOTE_REQ_LOG 테이블은 CONTRACT_NUM이 없는 경우도 있어서 REQ_SEQ와 IN_REQ_DATE로 대상 찾아옴
		List<CanTransferVO> logList = pPtransferMapper.getRcgRmoteReqLogDelList(TMNT_CUST_DEL_DT); //202508 추가
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("cdVal", "CAN_DELETE_STOP");

		try{
			for(CanTransferVO vo : list){
				//강제 종료 기능 추가
				LOGGER.info("이관 후 삭제 배치 처리 현황 ("+list.size()+"/"+procCnt+")");
				String stopYn = transferMapper.getTmntCustProcStopYn(paramMap);
				if(stopYn != null && stopYn.equals("Y")) {
					LOGGER.info("======================= STOP =======================");
					break;
				}

				List<CanTransferVO> reqList = transferMapper.getRequestKey(vo.getContractNum());
				if (reqList.isEmpty()) {
				    reqList = transferMapper.getCanRequestKey(vo.getContractNum());
				}
				
				for(CanTransferVO reqVO : reqList){
					LOGGER.debug("requestKey=" + reqVO.getRequestKey());
					LOGGER.debug("scanId=" + reqVO.getScanId());
					reqVO.setContractNum(vo.getContractNum());
					
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

					transferMapper.deleteMcpRequestDtlCan(reqVO.getRequestKey());
					//검색조건 REQUEST_KEY -> CONTRACT_NUM 으로 변경(2023.11.07)
					transferMapper.deleteMspRequestDtl(reqVO.getContractNum());
					//데이터 미존재로 주석처리(2023.11.07)
					//transferMapper.deleteCanRequestDtl(reqVO.getContractNum());

					// 010셀프개통 신청정보 삭제
					transferMapper.deleteMcpSelfSmsAuth(reqVO.getRequestKey());
					transferMapper.deleteCanSelfSmsAuth(reqVO.getRequestKey());

					// 유심구매 정보 삭제
					this.deleteUsimDlvryInfoByCondition("01", reqVO.getRequestKey());

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
				
				vo.setTmntCustDt(TMNT_CUST_DEL_DT);
				if(transferMapper.getOtherCntrExist(vo) == 0) {
					transferMapper.deleteMspJuoCusInfo(vo.getCustomerId());
					transferMapper.deleteMspJuoCusInfoHist(vo.getCustomerId());
				}
				
				if(transferMapper.getBanOtherCntrExist(vo) == 0){
					transferMapper.deleteMspJuoBanInfo(vo.getBan());
					transferMapper.deleteMspJuoBanInfoHist(vo.getBan());
				}
				

				// CAN 현행화 삭제
				transferMapper.deleteCanJuoSubInfo(vo.getContractNum());
				transferMapper.deleteCanJuoSubInfoHist(vo.getContractNum());
				transferMapper.deleteCanJuoCusInfo(vo.getCustomerId());
				transferMapper.deleteCanJuoCusInfoHist(vo.getCustomerId());
				transferMapper.deleteCanJuoBanInfo(vo.getBan());
				transferMapper.deleteCanJuoBanInfoHist(vo.getBan());

				// TOSS 가입자 오더정보 삭제
				transferMapper.deleteMdsTossOrder(vo.getSubscriberNo());
				transferMapper.deleteCanTossOrder(vo.getSubscriberNo());
				
				// OSST 가입자 오더정보 삭제
				transferMapper.deleteMdsOsstOrder(vo.getSubscriberNo());
				transferMapper.deleteCanOsstOrder(vo.getSubscriberNo());

				// 신한요금제 데이터쿠폰 대상자 정보 삭제
				transferMapper.deleteMdsShinhanProdSbsc(vo.getSvcCntrNo());
				transferMapper.deleteCanShinhanProdSbsc(vo.getSvcCntrNo());

				// 예약상담 고객 정보 삭제
				transferMapper.deleteMspCsResCstmr(vo.getContractNum());

				// 3G 유지가입자 현황 삭제
				transferMapper.deleteMspDaily3gSub(vo.getContractNum());

				// 보상서비스 가입자목록 삭제
				transferMapper.deleteMspRwdInfoOut(vo.getContractNum());
				transferMapper.deleteMspRwdMember(vo.getContractNum());
				
				// 마케팅수신동의 내역 해지후 5년 경과 고객데이터 삭제
				transferMapper.deleteMarketingSendTargetdata(vo.getContractNum());
				
				// 단말 보험 가입자 정보 삭제
				transferMapper.deleteMspIntmInsrIfOut(vo.getContractNum());
				transferMapper.deleteMspIntmInsrCmpnMstIf(vo.getContractNum());
				transferMapper.deleteMspIntmInsrCanInfo(vo.getContractNum());
				transferMapper.deleteMspIntmInsrCmpnDtl(vo.getContractNum());
				transferMapper.deleteMspIntmInsrMember(vo.getContractNum());
				transferMapper.deleteMspIntmInsrOrder(vo.getContractNum());
				transferMapper.deleteMspIntmInsrReady(vo.getContractNum());
				
				transferMapper.deleteCanIntmInsrIfOut(vo.getContractNum());
				transferMapper.deleteCanIntmInsrCmpnMstIf(vo.getContractNum());
				transferMapper.deleteCanIntmInsrCanInfo(vo.getContractNum());
				transferMapper.deleteCanIntmInsrCmpnDtl(vo.getContractNum());
				transferMapper.deleteCanIntmInsrMember(vo.getContractNum());
				transferMapper.deleteCanIntmInsrOrder(vo.getContractNum());
				
				// 2026.04.16 바로배송유심관리 데이터 정보 삭제
				transferMapper.deleteMspDirDlvryUsimMst(vo.getContractNum());

				// 선불 테이블 삭제
				pPtransferMapper.deletePpsCustomer(vo.getContractNum());
				pPtransferMapper.deletePpsCustomerHist(vo.getContractNum());
				pPtransferMapper.deletePpsAgentStmOpen(vo.getContractNum());
				pPtransferMapper.deletePpsRcgRealCms(vo.getContractNum());
				pPtransferMapper.deletePpsRcgRealCmsSetup(vo.getContractNum());
				pPtransferMapper.deletePpsRealPayinfoCmsLog(vo.getContractNum());
				pPtransferMapper.deletePpsVirAccountKibnet(vo.getContractNum());
				pPtransferMapper.deletePpsRcgArs(vo.getContractNum());

				pPtransferMapper.deleteCanPpsCustomer(vo.getContractNum());
				pPtransferMapper.deleteCanPpsCustomerHist(vo.getContractNum());
				pPtransferMapper.deleteCanPpsAgentStmOpen(vo.getContractNum());
				pPtransferMapper.deleteCanPpsRcgRealCms(vo.getContractNum());
				pPtransferMapper.deleteCanPpsRcgRealCmsSetup(vo.getContractNum());
				pPtransferMapper.deleteCanPpsRealPayinfoCmsLog(vo.getContractNum());
				pPtransferMapper.deleteCanPpsVirAccountKibnet(vo.getContractNum());
				pPtransferMapper.deleteCanPpsRcgArs(vo.getContractNum());

				// 유심구매 정보 삭제
				this.deleteUsimDlvryInfoByCondition("02", vo.getContractNum());

				// 신청정보(유심교체) 정보 삭제
				transferMapper.deleteMcpNmcpFreeUsimReqDtl(vo.getContractNum());
				if(transferMapper.getOtherUsimChgExist(vo) == 0) {
					transferMapper.deleteMcpNmcpFreeUsimReqMst(vo.getCustomerId());
					transferMapper.deleteCanNmcpFreeUsimReqMst(vo.getCustomerId());
				}

				// 사은품 VOC 정보 삭제
				transferMapper.deleteCanGiftVocMgmt(vo.getContractNum());
				transferMapper.deleteCanGiftPayStat(vo.getContractNum());
				procCnt++;
			}
			
			//PPS_RCG_REMOTE_REQ_LOG 테이블 삭제
			for(CanTransferVO logVo : logList){
				LOGGER.debug("PPS_RCG_REMOTE_REQ_LOG 삭제 : reqSeq=" + logVo.getReqSeq() + ", inReqDt=" + logVo.getInReqDt());
				
				pPtransferMapper.deletePpsRcgRemoteReqLog(logVo);
				pPtransferMapper.deleteCanPpsRcgRemoteReqLog(logVo);
			}
			
			// 판매유심 해지 후 1825일 지난 고객 증 MSP_CAN 으로 이관된 데이터 최종삭제
			// 종료일자 기준값이 모호하여 주석처리 2019.07.15
//			transferMapper.deleteCanMcpSellUsim(TMNT_CUST_DEL_DT);

		}catch(Exception e){
			throw new MvnoServiceException("ERCP1007", e);
		}
		
		LOGGER.info("이관 후 삭제 처리 END");

		return procCnt;
	}
	
	/**
	 * 홈페이지 고객정보 이관 및 삭제
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteTmntMcpCustData() throws MvnoServiceException {
		int delCnt = 0;
		int movCnt = 0;
		int recCanDelCnt = 0;
		
		try{
			delCnt = transferMapper.deleteMcpUser();
			movCnt = transferMapper.insertMcpRecUser();
			transferMapper.deleteMcpRecUser();
			recCanDelCnt = transferMapper.deleteMcpRecUserCan();
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1007", e);
		}
		
		LOGGER.info("홈페이지 해지자 삭제건수(USER:"+delCnt+", REC:"+recCanDelCnt+") 이관건수:"+movCnt);

		return delCnt + movCnt + recCanDelCnt;
	}
	
	/**
	 * 단말보험 해지자 고객정보 마스킹 처리
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int maskingIntmInsrMember() throws MvnoServiceException {
		int procCnt01 = 0;
		int procCnt02 = 0;
		int procCnt03 = 0;
		try {
			procCnt02 = transferMapper.updateIntmInsrIfOut(INTM_INSR_MASK_DT);
			procCnt03 = transferMapper.updateIntmInsrCmpnMstIf(INTM_INSR_MASK_DT);
			procCnt01 = transferMapper.updateIntmInsrMember(INTM_INSR_MASK_DT);
		
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1009", e);
		}

		LOGGER.info("단말보험 해지자 고객정보 마스킹 처리건수(가입정보:"+procCnt01+", 연동정보:"+procCnt02+", 보상정보:"+procCnt03+")");
		
		return procCnt01 + procCnt02 + procCnt03;
	}
	

	/**
	 * 명의도용주장 IP 설정 고객 데이터 삭제
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteReuseRipData() throws MvnoServiceException {
		int delMstCnt = 0;
		int delHistCnt = 0;
		try{
			delMstCnt = transferMapper.deleteReuseRipMstData(TMNT_CUST_DT);
			delHistCnt = transferMapper.deleteReuseRipHistData(TMNT_CUST_DT);
		}catch(Exception e){
			throw new MvnoServiceException("ERCP1007", e);
		}
		
		LOGGER.info("명의도용주장 IP 설정 고객 데이터 삭제 건수(MST:"+delMstCnt+", HIST:"+delHistCnt+")");

		return delMstCnt + delHistCnt;
	}
	
	/**
	 * 해지고객 고객정보 치환(해지일기준 D+180)
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procTmntCustChange() throws MvnoServiceException {
		int procCnt = 0;

		LOGGER.info("해지고객 고객정보 치환(해지일기준 D+180) START");
		
		try {
			int cnt = 0;
			// 3G 유지가입자 현황 치환
//			cnt = transferMapper.updateMspDaily3gSub(TMNT_CUST_DT);
			LOGGER.info("3G 유지가입자 현황 정보 치환 >> " + cnt);

			// 보상서비스 가입자목록 치환
//			cnt = transferMapper.updateMspRwdInfoOut(TMNT_CUST_DT);
			LOGGER.info("보상서비스 가입 신청현황 연동 정보 치환 >> " + cnt);
//			cnt = transferMapper.updateMspRwdMember(TMNT_CUST_DT);
			LOGGER.info("보상서비스 가입자목록 정보 치환 >> " + cnt);

		} catch(Exception e) {
			throw new MvnoServiceException("ERCP1013", e);
		}

		LOGGER.info("해지고객 고객정보 치환(해지일기준 D+180) End");
		return procCnt;
	}
	
	public static boolean fileIsLive(String isLivefile){
		File file = new File(isLivefile);
		if(file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**유심구매정보 이관*/
	private void transferUsimDlvryInfoByCondition(String cdType, String cdVal){

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("cdType", cdType);
		paramMap.put("cdVal", cdVal);

		// 유심구매고객(택배) 이관
		List<UsimDlvryCanVO> usimDlvryCanVOList = transferMapper.getUsimDlvryDlvryIdx(paramMap);
		for (UsimDlvryCanVO usimDlvryCanVO : usimDlvryCanVOList) {
			usimDlvryCanMapper.insertMcpRequestSelfDlvryHistCan(usimDlvryCanVO);
			usimDlvryCanMapper.updateMcpRequestSelfDlvryHist(usimDlvryCanVO);
			usimDlvryCanMapper.insertMcpRequestSelfDlvryCan(usimDlvryCanVO);
			usimDlvryCanMapper.updateMcpRequestSelfDlvry(usimDlvryCanVO);
			usimDlvryCanMapper.insertNmcpDaouPayInfoCan(usimDlvryCanVO);
			usimDlvryCanMapper.updateNmcpDaouPayInfo(usimDlvryCanVO);
		}

		// 유심구매고객(바로배송) 이관
		List<UsimDlvryCanVO> usimDlvryNowCanVOList = transferMapper.getUsimDlvryNowDlvryIdx(paramMap);
		for (UsimDlvryCanVO usimDlvryNowCanVO : usimDlvryNowCanVOList) {
			usimDlvryCanMapper.insertMcpRequestNowDlvryHistCan(usimDlvryNowCanVO);
			usimDlvryCanMapper.updateMcpRequestNowDlvryHist(usimDlvryNowCanVO);
			usimDlvryCanMapper.insertMcpRequestNowDlvryCan(usimDlvryNowCanVO);
			usimDlvryCanMapper.updateMcpRequestNowDlvry(usimDlvryNowCanVO);
			usimDlvryCanMapper.insertNmcpDaouPayInfoCan(usimDlvryNowCanVO);
			usimDlvryCanMapper.updateNmcpDaouPayInfo(usimDlvryNowCanVO);
		}
	}

	/**유심구매정보 삭제*/
	private void deleteUsimDlvryInfoByCondition(String cdType, String cdVal){

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("cdType", cdType);
		paramMap.put("cdVal", cdVal);

		// 유심구매고객(택배) 정보 삭제
		List<UsimDlvryCanVO> usimDlvryCanVOList = transferMapper.getUsimDlvryCanDlvryIdx(paramMap);
		for (UsimDlvryCanVO usimDlvryCanVO : usimDlvryCanVOList) {
			usimDlvryCanMapper.deleteMcpRequestSelfDlvryHistCan(usimDlvryCanVO);
			usimDlvryCanMapper.deleteMcpRequestSelfDlvryHist(usimDlvryCanVO);
			usimDlvryCanMapper.deleteMcpRequestSelfDlvryCan(usimDlvryCanVO);
			usimDlvryCanMapper.deleteMcpRequestSelfDlvry(usimDlvryCanVO);
			usimDlvryCanMapper.deleteNmcpDaouPayInfoCan(usimDlvryCanVO);
			usimDlvryCanMapper.deleteNmcpDaouPayInfo(usimDlvryCanVO);
		}

		// 유심구매고객(바로배송) 정보 삭제
		List<UsimDlvryCanVO> usimDlvryNowCanVOList = transferMapper.getUsimDlvryCanNowDlvryIdx(paramMap);
		for (UsimDlvryCanVO usimDlvryNowCanVO : usimDlvryNowCanVOList) {
			usimDlvryCanMapper.deleteMcpRequestNowDlvryHistCan(usimDlvryNowCanVO);
			usimDlvryCanMapper.deleteMcpRequestNowDlvryHist(usimDlvryNowCanVO);
			usimDlvryCanMapper.deleteMcpRequestNowDlvryCan(usimDlvryNowCanVO);
			usimDlvryCanMapper.deleteMcpRequestNowDlvry(usimDlvryNowCanVO);
			usimDlvryCanMapper.deleteNmcpDaouPayInfoCan(usimDlvryNowCanVO);
			usimDlvryCanMapper.deleteNmcpDaouPayInfo(usimDlvryNowCanVO);
			usimDlvryCanMapper.deleteMspRequestNowDlvryHst(usimDlvryNowCanVO);
		}
	}

}
