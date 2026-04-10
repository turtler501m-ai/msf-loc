package com.ktmmobile.mcp.rate.service;


import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import static com.ktmmobile.mcp.rate.RateConstants.*;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ktmmobile.mcp.common.mplatform.dto.*;
import com.ktmmobile.mcp.rate.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.rate.dao.RateDao;


@Service
public class RateSvc {

	private static final Logger logger = LoggerFactory.getLogger(RateSvc.class);

	@Autowired
	private RateDao rateDao;
	
	@Autowired
	private RateApiSvc rateApiSvc;
	
	@Autowired
	private RateMplatformSvc rateMplatformSvc;
	

    /**
     *  MCP_SERVICE_ALTER_TRACE에 이력 저장 시 IP를 set한 뒤 저장한다.
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param serviceAlterTraceDto
     * @return boolean
     */
	public int insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTraceDto) {
		try {
			String accessIp = InetAddress.getLocalHost().getHostAddress();
			serviceAlterTraceDto.setAccessIp(accessIp);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		
		return rateDao.insertServiceAlterTrace(serviceAlterTraceDto);
	}

    /**
     *  처리 되지 않은 전문 대상이 있는지 확인 
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param batchMod
     * @return boolean
     */
    public boolean checkRateResChgTrgByMod(int batchMod) {
    	int totalCnt = 0;
    	totalCnt = rateDao.selectRateResChgTrgCntByMod(batchMod);
        logger.info("예약 변경 전문 대상 확인 = {}", totalCnt);
    	
    	return (0 < totalCnt);
    }

    /**
     *  우선 실행되는 전문 대상을 처리하는 배치의 가동 시간 확인
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @return boolean
     * @throws ParseException
     */
    public boolean checkBatchRunTime() throws ParseException {
    	String nowMonth = DateTimeUtil.getFormatString("yyyyMM");
    	
    	//00:30~06:00일때만 배치 가동
    	return DateTimeUtil.isMiddleDateTime2(nowMonth + "01002959", nowMonth + "01060000");
    }

    /**
     *  최초 기준 시간(checkBatchRunTime())동안 가동하는 배치에서 사용 
     *  1일 00:10~06:00 동안 SYNCER에서 SCH 전문을 처리할 때 NMCP_RATE_RES_CHG_TRG에 예약 변경 대상을 INSERT함.
     *  NMCP_RATE_RES_CHG_TRG 정보로 NMCP_RATE_RES_CHG_BAS 대상을 조회해서 요금제 예약 변경 후처리를 한다.
     *  전달 받은 batchMod(0, 1, 2, 3)에 따라 대상을 나누어 4개의 배치에서 처리
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param batchMod
     * @return processResult
     */
    public Map<String, Object> processRateResChgByTrg(int batchMod) {
    	Map<String, Object> processResult = new HashMap<>();
    	int successCnt = 0;
    	int failCnt = 0;
    	
    	//1. 배치번호(batchMod)에 해당하는 처리 대상 목록 조회
    	List<RateResChgTrgDto> resChgTrgList = rateDao.selectRateResChgTrgListByMod(batchMod);
    	
    	//2. 조회 대상 건별 진행
    	for(RateResChgTrgDto trgDto : resChgTrgList) {
    		
    		try {
    			//3. NMCP_RATE_RES_CHG_TRG 처리여부(PROC_YN) 'Y'로 업데이트
                logger.info("batchMod[{}] 전문대상 시퀀스[{}] 신청대상 시퀀스[{}]", batchMod, trgDto.getResChgTrgSeq(), trgDto.getResChgBasSeq());
	    		rateDao.updateRateResChgTrgComplete(trgDto.getResChgTrgSeq());
	    		
	    		//4. 전문 대상으로 요금제 예약 변경 정보 조회(TRG 정보로 BAS 대상 조회)
	    		RateResChgBasDto rateResChgBasDto = rateDao.selectRateResChgBas(trgDto.getResChgBasSeq());
	    		
	    		//5. 건별 요금제 예약 변경 후처리 시작(성공/실패에 따라 카운트 증가)
	    		if(this.processRateResChg(rateResChgBasDto)) {
	    			successCnt++;
	    		} else {
	    			failCnt++;
	    		}
	    		
    		} catch (Exception e) {
    			failCnt++;
        		e.printStackTrace();
                logger.error("batchMod[{}] Exception e : {}", batchMod, e.getMessage());
    		}
    	}
    	
    	processResult.put("SUCCESS_CNT", successCnt);
    	processResult.put("FAIL_CNT", failCnt);
    	
    	return processResult;
    }
    
    /** 
     *  NMCP_RATE_RES_CHG_BAS 대상에 대해 요금제 예약 변경 후처리를 한다.
     *  1. 처리 작업 전 검증 진행(요금제 변경 처리 확인 포함)
     *  2. 검증 후 받은 결과로 평생할인 정책 기적용 테이블에 INSERT
     *  3. 선 해지 부가서비스 해지 및 평생할인 프로모션 부가서비스 가입 진행
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @return boolean
     * @throws InterruptedException
     */
    private boolean processRateResChg(RateResChgBasDto rateResChgBasDto) throws InterruptedException {
		if (rateResChgBasDto == null) {
			return false;
		}

		logger.info("processRateResChg ::: 처리대상 계약번호[{}] 처리대상 시퀀스[{}]", rateResChgBasDto.getSvcCntrNo(), rateResChgBasDto.getRateResChgSeq());

		RateResChgBasDto enrichedRateResChgBasDto = this.enrich(rateResChgBasDto);
		String rsltCd = "00000";
		try {
			this.validateTarget(enrichedRateResChgBasDto);
		} catch (McpCommonJsonException e) {
			rsltCd = e.getStatusCode();
			this.insertFailToTraceAndMst(enrichedRateResChgBasDto, e.getMessage());
			return false;
		} finally {
			rateDao.updateBatchRsltCd(enrichedRateResChgBasDto.getRateResChgSeq(), rsltCd);
		}

		this.insertDisApd(enrichedRateResChgBasDto);

		return this.batchResChangeAddPrdCall(enrichedRateResChgBasDto);
	}

	private RateResChgBasDto enrich(RateResChgBasDto rateResChgBasDto) {
		RateResChgBasDto enrichedRateResChgBasDto = new RateResChgBasDto(rateResChgBasDto);

		McpUserCntrMngDto mcpUserCntrMngDto = rateApiSvc.selectCntrListNoLogin(rateResChgBasDto.getSvcCntrNo());
		if (mcpUserCntrMngDto != null) {
			enrichedRateResChgBasDto.setContractNum(mcpUserCntrMngDto.getContractNum());
			enrichedRateResChgBasDto.setSubStatus(mcpUserCntrMngDto.getSubStatus());
		}

		MspSaleSubsdMstDto rateInfo = rateApiSvc.getRateInfo(rateResChgBasDto.getResChgRateCd());
		if (rateInfo != null) {
            enrichedRateResChgBasDto.setPayMnthChargeAmt(rateInfo.getPayMnthChargeAmt());
			enrichedRateResChgBasDto.setHasRateInfo(true);
		} else {
			enrichedRateResChgBasDto.setHasRateInfo(false);
		}

		if (!StringUtil.isEmpty(enrichedRateResChgBasDto.getContractNum())) {
			McpFarPriceDto mcpFarPriceDto = rateApiSvc.selectFarPricePlan(enrichedRateResChgBasDto.getContractNum());
			if  (mcpFarPriceDto != null) {
                enrichedRateResChgBasDto.setNowPriceSocCode(mcpFarPriceDto.getPrvRateCd());
                enrichedRateResChgBasDto.setAppStartDd(mcpFarPriceDto.getAppStartDd());
			}
		}

		return enrichedRateResChgBasDto;
	}

	private void validateTarget(RateResChgBasDto rateResChgBasDto) {
		if (StringUtil.isBlank(rateResChgBasDto.getContractNum())) {
			throw new McpCommonJsonException("99992", "svcCntrNo[" + rateResChgBasDto.getSvcCntrNo() + "] 계약번호 없음");
		}

		if ("S".equals(rateResChgBasDto.getSubStatus())) {
			throw new McpCommonJsonException("99996", "일시정지 회선");
		}

		if (!rateResChgBasDto.hasRateInfo()) {
			throw new McpCommonJsonException("99993", rateResChgBasDto.getResChgRateCd() + " 요금제 정보 없음");
		}

		if (StringUtil.isEmpty(rateResChgBasDto.getNowPriceSocCode())) {
			throw new McpCommonJsonException("99995", "현재 사용 중인 요금제 정보 없음");
		}

		if(!rateResChgBasDto.getResChgRateCd().equals(rateResChgBasDto.getNowPriceSocCode())) {
			throw new McpCommonJsonException("99999", "19. 요금 상품 변경 실패");
		}

		if (!DateTimeUtil.isToday(rateResChgBasDto.getAppStartDd())) {
			throw new McpCommonJsonException("99994", "당일 요금제 변경 대상 아님");
		}
	}
    
    /**
     *  해당하는 평생할인 온라인 프르모션 ID를 찾은 뒤 MSP_DIS_APD에 INSERT 한다.
     *  이미 온라인 정책이 적용되었다는 것을 의미한다.
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     */
    private void insertDisApd(RateResChgBasDto rateResChgBasDto) {
    	String prmtId = rateApiSvc.getChrgPrmtIdSocChg(rateResChgBasDto.getNowPriceSocCode());

        logger.info("대상 적용 온라인 프로모션ID[{}] 현재 요금제 코드[{}] 계약번호[{}]", prmtId, rateResChgBasDto.getNowPriceSocCode(), rateResChgBasDto.getContractNum());

    	McpUserCntrMngDto disApdDto = new McpUserCntrMngDto();
    	disApdDto.setPrmtId(prmtId);
    	disApdDto.setSocCode(rateResChgBasDto.getNowPriceSocCode());
    	disApdDto.setContractNum(rateResChgBasDto.getContractNum());
    	rateApiSvc.insertDisApd(disApdDto);
    }
    
    /**
     *  기준 시간(checkBatchRunTime()) 이후 첫 번째 재처리 로직
	 *	요금제 예약 변경 미처리 대상과(BATCH_RSLT_CD = '-1')
     *  요금제 변경 처리 미완료 대상(BATCH_RSLT_CD = '99999')
     *  에 대해 재검증 및 후처리
     *  
     * @author 김동혁
     * @Date : 2024.02.14
     * @return processResult
     */
    public Map<String, Object> reprocessRateResChgNotProcess() {
    	Map<String, Object> processResult = new HashMap<String, Object>();
    	int successCnt = 0;
    	int failCnt = 0;
    	
    	int notProcessNum = rateDao.selectRateResChgCntNotProcess();
        logger.info("예약 변경 미처리 대상 확인 = {}", notProcessNum);
    	
    	//1. 재처리 대상 조회
    	List<RateResChgBasDto> resChgBasList = rateDao.selectRateResChgListNotProcess();
    	
    	//2. 조회 대상 건별 진행
    	for(RateResChgBasDto rateResChgBasDto : resChgBasList) {
    		
    		try {
                logger.info("reprocessRateResChgNotProcess() 처리대상 계약번호[{}] 처리대상 시퀀스[{}]", rateResChgBasDto.getSvcCntrNo(), rateResChgBasDto.getRateResChgSeq());

	    		//3. 건별 요금제 예약 변경 후처리 시작(성공/실패에 따라 카운트 증가)
	    		if(this.processRateResChg(rateResChgBasDto)) {
	    			successCnt++;
	    		} else {
	    			failCnt++;
	    		}
	    		
    		} catch (Exception e) {
    			failCnt++;
        		e.printStackTrace();
                logger.error("reprocessRateResChgNotProcess() Exception e : {}", e.getMessage());
    		}
    	}
    	
    	processResult.put("SUCCESS_CNT", successCnt);
    	processResult.put("FAIL_CNT", failCnt);
    	
    	return processResult;
    }
    
    /**
     *  기준 시간(checkBatchRunTime()) 이후 두 번째 재처리 로직
     *  
     *  요금제 예약 변경 후처리 중 선 해지 부가서비스 해지 실패 대상
     *  해지 재시도 및 평생할인 프로모션 부가서비스 가입 진행
     *  
     * @author 김동혁
     * @Date : 2024.02.14
     * @return processResult
     */
    public Map<String, Object> reprocessRateResChgFailCancel() {
    	Map<String, Object> processResult = new HashMap<String, Object>();
    	int successCnt = 0;
    	int failCnt = 0;
    	
    	int failCancelNum = rateDao.selectRateResChgCntFailCancel();
    	logger.info("선해지 부가서비스 해지 실패 대상 확인 = " + failCancelNum);
    	
    	//1. 재처리 대상 조회
    	List<RateResChgBasDto> resChgBasList = rateDao.selectRateResChgListFailCancel();
    	
    	//2. 조회 대상 건별 진행
    	for(RateResChgBasDto rateResChgBasDto : resChgBasList) {
    		
    		try {
                logger.info("reprocessRateResChgFailCancel() 처리대상 계약번호[{}] 처리대상 시퀀스[{}]", rateResChgBasDto.getSvcCntrNo(), rateResChgBasDto.getRateResChgSeq());
    			
    			//3. 건별 선 해지 부가서비스 해지 재시도 및 평생할인 프로모션 부가서비스 가입 진행(성공/실패에 따라 카운트 증가)
    			if(this.retryRateResChgCancel(rateResChgBasDto)) {
    				successCnt++;
    			} else {
    				failCnt++;
    			}
    			
    		} catch (Exception e) {
    			failCnt++;
    			e.printStackTrace();
    			logger.error("reprocessRateResChgFailCancel() Exception e : {}", e.getMessage());
    		}
    	}
    	
    	processResult.put("SUCCESS_CNT", successCnt);
    	processResult.put("FAIL_CNT", failCnt);
    	
    	return processResult;
    }
    
    /**
     *  기준 시간(checkBatchRunTime()) 이후 세 번째 재처리 로직
     *  
     *  1. 부가서비스 가입 실패 이력 조회
     *  2. 조회한 실패 이력으로 부가서비스 가입에 필요한 정보 조회
     *  3. 부가서비스 가입 재시도
     *  
     * @author 김동혁
     * @Date : 2024.02.14
     * @return processResult
     */
    public Map<String, Object> reprocessRateResChgFailSubscribe() {
    	Map<String, Object> processResult = new HashMap<String, Object>();
    	int successCnt = 0;
    	int failCnt = 0;
    	
    	int failSubscribeNum = rateDao.selectRateResChgCntFailSubscribe();
        logger.info("부가서비스 가입 실패 대상 확인 = {}", failSubscribeNum);
    	
    	//1. 부가서비스 가입 실패 이력 조회
    	List<McpServiceAlterTraceDto> serviceAlterTraceList = rateDao.selectRateResChgListFailSubscribe();
    	
    	//조회 대상 건별 진행
    	for(McpServiceAlterTraceDto serviceAlterTraceDto : serviceAlterTraceList) {
    		try {
                logger.info("reprocessRateResChgFailSubscribe() serviceAlterTraceDto prcsMdlInd[{}] tSocCode[{}]", serviceAlterTraceDto.getPrcsMdlInd(), serviceAlterTraceDto.gettSocCode());
    			
    			//2-1. 조회한 실패 이력으로 예약 변경 대상 정보 조회
    			String rateResChgSeq = serviceAlterTraceDto.getPrcsMdlInd().substring(3); // ex) 'BA_1111' => '1111'
    			RateResChgBasDto rateResChgBasDto = rateDao.selectRateResChgBas(rateResChgSeq);
                logger.info("reprocessRateResChgFailSubscribe() 처리대상 계약번호[{}] 처리대상 시퀀스[{}]", rateResChgBasDto.getSvcCntrNo(), rateResChgBasDto.getRateResChgSeq());
    			
    			//2-2. 조회한 실패 이력으로 가입 시도할 부가서비스 정보 조회
    			McpUserCntrMngDto serviceInfo = rateDao.selectAddSvcInfo(serviceAlterTraceDto.gettSocCode());
    			
    	        //2-3. MP 연동 시 사용할 VO 생성
    	        MyPageSearchDto searchVO = this.getMyPageSearchDto(rateResChgBasDto);
    	        //조회 결과 없으면 일시 정지 회선
    	        if(ObjectUtils.isEmpty(searchVO)) {
    	        	failCnt++;
    	        	continue;
    	        }

    			//3. 부가서비스 가입 재시도(성공/실패에 따라 카운트 증가)
	        	if(this.subscribeAddSvc(rateResChgBasDto, searchVO, serviceInfo)) {
    				successCnt++;
	        	} else {
	        		failCnt++;
	        	}
    		} catch (Exception e) {
    			failCnt++;
    			e.printStackTrace();
    			logger.error("reprocessRateResChgFailSubscribe() Exception e : {}", e.getMessage());
    		}
    	}
    	
    	processResult.put("SUCCESS_CNT", successCnt);
    	processResult.put("FAIL_CNT", failCnt);
    	
    	return processResult;
    }
    
    /** 
     *  요금제 예약 변경 후처리 중 선 해지 부가서비스 해지 실패 대상에 대해 해지 재시도를 진행한다.
     *  
     *  1. 할인 금액 조회(검증이 완료된 대상이므로 검증 불필요)
     *  2. 선 해지 부가서비스 해지 및 평생할인 프로모션 부가서비스 가입 진행
     *  
     * @author 김동혁
     * @Date : 2024.02.14
     * @param rateResChgBasDto
     * @return boolean
     * @throws InterruptedException
     */
    private boolean retryRateResChgCancel(RateResChgBasDto rateResChgBasDto) throws InterruptedException {
    	
    	if(rateResChgBasDto == null) {
			return false;
		}

        logger.info("retryRateResChgCancel ::: 처리대상 계약번호[{}] 처리대상 시퀀스[{}]", rateResChgBasDto.getSvcCntrNo(), rateResChgBasDto.getRateResChgSeq());
    	
    	//1. 할인 금액 조회
    	MspSaleSubsdMstDto rateInfo = rateApiSvc.getRateInfo(rateResChgBasDto.getResChgRateCd());
        rateResChgBasDto.setPayMnthChargeAmt(rateInfo.getPayMnthChargeAmt());
        
        return this.batchResChangeAddPrdCall(rateResChgBasDto);
    }
    
    /**
     *  요금제 예약 변경 부가서비스 일괄 처리
     *  1. MP 연동 시 사용할 VO 생성
     *  2. 선해지 부가서비스 해지
     *  3. 평생할인 부가서비스 가입
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @return rtnMap
     * @throws InterruptedException
     */
    public boolean batchResChangeAddPrdCall(RateResChgBasDto rateResChgBasDto) throws InterruptedException {
        logger.info("batchResChangeAddPrdCall Start 사용자ID[{}] 계약번호[{}]", rateResChgBasDto.getUserId(), rateResChgBasDto.getSvcCntrNo());

		String resultCode;
		String resultMsg;

		try {
			//1. MP 연동 시 사용할 VO 생성
			MyPageSearchDto searchVO = this.getMyPageSearchDto(rateResChgBasDto);
			//조회 결과 없으면 해지 회선
			if (ObjectUtils.isEmpty(searchVO)) {
//				throw new McpCommonJsonException("-9", "일시 정지 회선");
				throw new McpCommonJsonException("-8", "해지 회선");
			}

			//2. 선해지 부가서비스 해지 프로세스
			if (!this.cancelAddSvc(rateResChgBasDto, searchVO)) {
				throw new McpCommonJsonException("-4", "부가서비스 해지 실패");
			}

			//3. 평생할인 부가서비스 가입 프로세스
			Map<String, String> subscribeResult = this.subscribePromotionAddSvc(rateResChgBasDto, searchVO);

			resultCode = subscribeResult.get("RESULT_CODE");
			resultMsg = subscribeResult.get("RESULT_MSG");
		} catch (McpCommonJsonException e) {
			resultCode = e.getStatusCode();
			resultMsg = e.getMessage();
		}
		logger.info("resultCode[{}] resultMsg[{}]", resultCode, resultMsg);

        return "00000".equals(resultCode);
    }
    
    /**
     *  checkValidationProcess에서 '00000'을 응답받지 못했을 때(FAIL)
     *  MCP_SERVICE_ALTER_TRACE와 MSP_SOCFAIL_PROC_MST에 별도의 이력을 쌓아준다.
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @param parameter
     */
    private void insertFailToTraceAndMst(RateResChgBasDto rateResChgBasDto, String parameter) {
        //결과 저장
        McpServiceAlterTraceDto serviceAlterTrace = new McpServiceAlterTraceDto();
        serviceAlterTrace.setNcn(rateResChgBasDto.getSvcCntrNo());
        serviceAlterTrace.setContractNum(rateResChgBasDto.getSvcCntrNo());
        serviceAlterTrace.setSubscriberNo(rateResChgBasDto.getMobileNo());
        serviceAlterTrace.setPrcsMdlInd("BA_" + rateResChgBasDto.getRateResChgSeq());
        serviceAlterTrace.setaSocCode(rateResChgBasDto.getBefChgRateCd());
        serviceAlterTrace.settSocCode(rateResChgBasDto.getResChgRateCd());
        serviceAlterTrace.setEventCode("FIN");
        serviceAlterTrace.setTrtmRsltSmst("FAIL");
        serviceAlterTrace.setaSocAmnt(rateResChgBasDto.getBefChgRateAmnt());
        serviceAlterTrace.settSocAmnt(rateResChgBasDto.getPayMnthChargeAmt());
        serviceAlterTrace.setParameter(parameter);
		serviceAlterTrace.setChgType("R");  //-- (즉시 : I , 예약 : R)
		serviceAlterTrace.setSuccYn("N");
		this.insertServiceAlterTrace(serviceAlterTrace);
        rateApiSvc.insertSocfailProcMst(serviceAlterTrace);
    }
    
    /**
     *  MP 연동 시 필요한 MyPageSearchDto를 검증 및 리턴.
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @return searchVO
     */
    private MyPageSearchDto getMyPageSearchDto(RateResChgBasDto rateResChgBasDto) {
    	
	    // MSP_JUO_SUB_INFO, MSP_JUO_CUS_INFO 테이블 조회 해서 계약정보 가져옴.
	    McpUserCntrMngDto mcpUserCntrMngDto = rateApiSvc.selectCntrListNoLogin(rateResChgBasDto.getSvcCntrNo());
	    
	    // 해지회선일 경우
	    if(ObjectUtils.isEmpty(mcpUserCntrMngDto)) {
	        return null;
	    }
	    
	    MyPageSearchDto searchVO = new MyPageSearchDto();
	    searchVO.setUserId(rateResChgBasDto.getUserId());
	    searchVO.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
	    searchVO.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
	    searchVO.setCustId(mcpUserCntrMngDto.getCustId());

	    return searchVO;
    }
    
    /**
     *  1. 이용중인 부가서비스를 조회(X20)하고
     *  2. 해지 해야할 부가서비스를 조회하여
     *  3. 일치하는 부가서비스를 해지한다.
     *  
     *  -4 : 해지 해야 할 부가서비스 해지 실패 >> 일 경우 이후 부가서비스 가입 진행하지 않음.
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @param searchVO
     * @return cancelResult
     * @throws InterruptedException
     */
    private boolean cancelAddSvc(RateResChgBasDto rateResChgBasDto, MyPageSearchDto searchVO) throws InterruptedException {
		boolean isSuccess = true;
    	try {

			List<String> addSvcList = getAddSvcList(searchVO);
	        List<McpUserCntrMngDto> closeSubList = rateApiSvc.getCloseSubList(rateResChgBasDto.getSvcCntrNo());
			// 이용 중인 선 해지 부가서비스 목록
			List<McpUserCntrMngDto> filteredCloseSubList = closeSubList.stream()
					.filter(closeSub -> addSvcList.contains(closeSub.getSocCode()))
					.collect(Collectors.toList());

	        //일치하는 부가서비스 모두 해지 성공하면 true 리턴
			isSuccess = this.cancelCloseSubList(filteredCloseSubList, rateResChgBasDto, searchVO);
    	} catch (McpCommonJsonException e) {
			logger.info("cancelAddSvc RESULT_CODE[{}] RESULT_MSG[{}]", e.getStatusCode(), e.getMessage());
    	}
    	
    	return isSuccess;
    }

	private List<String> getAddSvcList(MyPageSearchDto searchVO) {
		//1. 이용중인 부가서비스 조회
		MpAddSvcInfoDto addSvcInfo;
		try {
			addSvcInfo = rateMplatformSvc.getAddSvcInfoDto(searchVO);
		} catch (SocketTimeoutException e) {
			throw new McpCommonJsonException("-1", "getAddSvcInfoDto ::: SocketTimeoutException ::: " + SOCKET_TIMEOUT_EXCEPTION);
		} catch (SelfServiceException e) {
			throw new McpCommonJsonException("-2", "getAddSvcInfoDto ::: SelfServiceException ::: " + MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
		}

		if(ObjectUtils.isEmpty(addSvcInfo) || ObjectUtils.isEmpty(addSvcInfo.getList())) {
			throw new McpCommonJsonException("-3", "getAddSvcInfoDto ::: 이용중인 부가서비스 미존재");
		}
        return addSvcInfo.getList().stream()
                .map(MpSocVO::getSoc)
                .collect(Collectors.toList());
	}

	/**
     *  이용 중인 선 해지 부가서비스를 해지한다.(X38)
     *  ESB연동오류(ITL_SYS_E0001)에 한해 부가서비스 해지를 재처리 시도(최대 1회)
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param closeSubList
     * @param searchVO
     * @return boolean
     * @throws InterruptedException
     */
    private boolean cancelCloseSubList(List<McpUserCntrMngDto> closeSubList, RateResChgBasDto rateResChgBasDto, MyPageSearchDto searchVO) throws InterruptedException {
		for(McpUserCntrMngDto closeSubInfo : closeSubList) {
			logger.info("batchResChangeAddPrdCall 부가서비스 해지 ===== userid[{}] soc[{}]", rateResChgBasDto.getUserId(), closeSubInfo.getSocCode());

			//부가서비스 해지(X38)
			RegSvcChgRes regSvcCanChgNe = rateMplatformSvc.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode());
			McpServiceAlterTraceDto serviceAlterTraceSub = this.getServiceAlterTraceSub(rateResChgBasDto, closeSubInfo, regSvcCanChgNe, "X38");
			this.insertServiceAlterTrace(serviceAlterTraceSub);
			/*
			 * 1. 재처리는 ESB연동오류 (ITL_SYS_E0001) 에 한정한다.
			   2. 재처리는 1회 한정한다
			   3. 재처리에는 3초 gap time을 둔다.
			   4. 재처리에 대한 이력은 이력테이블에 신규로 insert 하며, 기존 이력에update 하지는않는다.
			*/
			//X38 결과가 ESB연동오류(ITL_SYS_E0001)일 경우 재처리 시도
			if ("ITL_SYS_E0001".equals(regSvcCanChgNe.getResultCode())) {
				//3초 sleep
				Thread.sleep(3000);

				//부가서비스 해지(X38) 재처리 시도
				regSvcCanChgNe = rateMplatformSvc.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode());
				serviceAlterTraceSub = this.getServiceAlterTraceSub(rateResChgBasDto, closeSubInfo, regSvcCanChgNe, "X38");
				this.insertServiceAlterTrace(serviceAlterTraceSub);
			}

			if(!regSvcCanChgNe.isSuccess()) {
				//부가서비스 해지 최종 이력 저장(실패 시)
				McpServiceAlterTraceDto serviceAlterTrace = this.getServiceAlterTraceFnl(rateResChgBasDto, false);
				serviceAlterTrace.setParameter("부가서비스 해지 실패");
				this.insertServiceAlterTrace(serviceAlterTrace);
				rateApiSvc.insertSocfailProcMst(serviceAlterTrace);
				return false;
			}
    	}
    	
    	return true;
    }
    
    /**
     *  1. 평생할인 온라인 프로모션 부가서비스 조회하고
     *  2. 조회한 부가서비스를 건별로 가입한다.(X21)
     *  
     * @author 김동혁
     * @Date : 2024.02.14
     * @param rateResChgBasDto
     * @param searchVO
     * @return subscribeResult
     * @throws InterruptedException
     */
    private Map<String, String> subscribePromotionAddSvc(RateResChgBasDto rateResChgBasDto, MyPageSearchDto searchVO) throws InterruptedException {
		//1. 평생할인 온라인 프로모션 부가서비스 조회
		int successCnt = 0;
		int failCnt = 0;
		List<McpUserCntrMngDto> serviceInfoList = rateApiSvc.getromotionDcList(rateResChgBasDto.getResChgRateCd());

		if (this.subscribeAddSvcList(rateResChgBasDto, searchVO, serviceInfoList)) {
			successCnt = serviceInfoList.size();
		} else {
			for (McpUserCntrMngDto serviceInfo : serviceInfoList) {
				//2. 조회한 부가서비스를 건별로 가입한다.(X21)
				if(this.subscribeAddSvc(rateResChgBasDto, searchVO, serviceInfo)) {
					successCnt++;
				} else {
					failCnt++;
				}
			}
		}

		//조회한 부가서비스를 모두 성공시키면 최종 성공
		boolean isSuccess = (successCnt == serviceInfoList.size());

		//부가서비스 가입 최종 이력 저장
		McpServiceAlterTraceDto serviceAlterTrace = this.getServiceAlterTraceFnl(rateResChgBasDto, isSuccess);
		serviceAlterTrace.setParameter("SCNT["+successCnt + "]FCNT["+failCnt+"]");
		this.insertServiceAlterTrace(serviceAlterTrace);
		rateApiSvc.insertSocfailProcMst(serviceAlterTrace);

		Map<String, String> subscribeResult = new HashMap<>();
		if (isSuccess) {
			subscribeResult.put("RESULT_CODE", "00000");
			subscribeResult.put("RESULT_MSG", "성공");
		} else {
			subscribeResult.put("RESULT_CODE", "30000");
			subscribeResult.put("RESULT_MSG", "부가서비스 가입 실패");
		}

        return subscribeResult;
    }

	private boolean subscribeAddSvcList(RateResChgBasDto rateResChgBasDto, MyPageSearchDto searchVO, List<McpUserCntrMngDto> serviceInfoList) {
		MoscPrdcTrtmRequest moscPrdcTrtmRequest = getMoscPrdcTrtmRequest(searchVO, serviceInfoList);

		RegSvcChgRes resultY24 = rateMplatformSvc.moscPrdcTrtmPreChk(moscPrdcTrtmRequest);
		if (resultY24.isSuccess() && "0000".equals(resultY24.getRsltCd())) {
			RegSvcChgRes resultY25 = rateMplatformSvc.moscPrdcTrtm(moscPrdcTrtmRequest);
            if (resultY25.isSuccess() && "0000".equals(resultY25.getRsltCd())) {
				for (McpUserCntrMngDto serviceInfo : serviceInfoList) {
					McpServiceAlterTraceDto serviceAlterTraceSub = this.getServiceAlterTraceSub(rateResChgBasDto, serviceInfo, resultY25, "Y25");
					this.insertServiceAlterTrace(serviceAlterTraceSub);
				}
				return true;
			}
		}
		return false;
	}

	private static MoscPrdcTrtmRequest getMoscPrdcTrtmRequest(MyPageSearchDto searchVO, List<McpUserCntrMngDto> serviceInfoList) {
		MoscPrdcTrtmRequest moscPrdcTrtmRequest = new MoscPrdcTrtmRequest();
		moscPrdcTrtmRequest.setUserid(searchVO.getUserId());
		moscPrdcTrtmRequest.setNcn(searchVO.getNcn());
		moscPrdcTrtmRequest.setCtn(searchVO.getCtn());
		moscPrdcTrtmRequest.setCustId(searchVO.getCustId());
		moscPrdcTrtmRequest.setActCode(ACT_CODE_ADD_CHANGE);
		List<PrdcTrtmVO> prdcList = new ArrayList<>();
		for (McpUserCntrMngDto serviceInfo : serviceInfoList) {
			PrdcTrtmVO prdcTrtmVO = new PrdcTrtmVO(serviceInfo.getSocCode(), PRDC_SBSC_TRTM_CD_SUBSCRIBE, PRDC_TYPE_CD_ADD);
			prdcList.add(prdcTrtmVO);
		}
		moscPrdcTrtmRequest.setPrdcList(prdcList);
		return moscPrdcTrtmRequest;
	}

	/**
     *  부가서비스를 가입하고(X21) 건별 이력을 저장한다.
     *  ESB연동오류(ITL_SYS_E0001)에 한해 부가서비스 가입을 재처리 시도(최대 1회)
     *  
     * @author 김동혁
     * @Date : 2024.02.14
     * @param rateResChgBasDto
     * @param searchVO
     * @param serviceInfo
     * @return subscribeResult
     * @throws InterruptedException
     */
    private boolean subscribeAddSvc(RateResChgBasDto rateResChgBasDto, MyPageSearchDto searchVO, McpUserCntrMngDto serviceInfo) throws InterruptedException {
		RegSvcChgRes regSvcInsert = rateMplatformSvc.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
		McpServiceAlterTraceDto serviceAlterTraceSub = this.getServiceAlterTraceSub(rateResChgBasDto, serviceInfo, regSvcInsert, "X21");
		this.insertServiceAlterTrace(serviceAlterTraceSub);
    		
    		//X21 결과가 ESB연동오류(ITL_SYS_E0001)일 경우 재처리 시도
    		if ("ITL_SYS_E0001".equals(regSvcInsert.getResultCode())) {
    			//3초 sleep
    			Thread.sleep(3000);

    			//부가서비스 가입(X21) 재처리 시도
    			regSvcInsert = rateMplatformSvc.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
				serviceAlterTraceSub = this.getServiceAlterTraceSub(rateResChgBasDto, serviceInfo, regSvcInsert, "X21");
				this.insertServiceAlterTrace(serviceAlterTraceSub);
    		}

		return regSvcInsert.isSuccess();
    }
    
    /**
     *  X38과 X21의 건별 이력 저장을 위해 serviceAlterTraceSub 값을 세팅 후 리턴한다.
     *  
     *  기존에	즉시 변경 부가서비스 해지 성공 시 RSLT_CD '0000' 으로 set.(정상)
     *  		예약 변경 후처리 배치에서 부가서비스 해지 성공 시 RSLT_CD '20000' 으로 set.(24년 2월까지)
     *  
     *  그러나 M전산 > 고객 > 현황자료 > '요금제 셀프변경 현황' 화면의 상세 처리 이력에서
     *  '0000'이 아닌 값은 실패로 표시(마스터에서는 성공이라고 표기되어서 문제는 되지 않았음.)하기 때문에
     *  정상적인 표시를 위해 예약 변경 후처리 배치에서도 부가서비스 해지 성공 시 RSLT_CD '0000' 으로 set.(24년 3월부터)
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @param serviceInfo
     * @param xmlVo
     * @param eventCd
     * @return serviceAlterTraceSub
     */
    private McpServiceAlterTraceDto getServiceAlterTraceSub(RateResChgBasDto rateResChgBasDto, McpUserCntrMngDto serviceInfo, CommonXmlNoSelfServiceException xmlVo, String eventCd) {
    	McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();

		switch (eventCd) {
			case "X38":
				serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
				serviceAlterTraceSub.setParameter("[" + serviceInfo.getSocNm() + "]");
				break;
			case "X21":
			case "Y25":
				serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
				serviceAlterTraceSub.setParameter("[" + serviceInfo.getSocNm() + "][" + serviceInfo.getSocPrice() + "]");
				break;
			default:
				throw new InvalidParameterException("eventCd[" + eventCd + "] is invalid");
		}

		String prcsMdlInd = "BA_" + rateResChgBasDto.getRateResChgSeq();
		String rsltCd = xmlVo.isSuccess() ? "0000" : xmlVo.getResultCode();
		serviceAlterTraceSub.setNcn(rateResChgBasDto.getSvcCntrNo());
		serviceAlterTraceSub.setContractNum(rateResChgBasDto.getSvcCntrNo());
		serviceAlterTraceSub.setSubscriberNo(rateResChgBasDto.getMobileNo());
		serviceAlterTraceSub.setPrcsMdlInd(prcsMdlInd);
		serviceAlterTraceSub.setaSocCode("");
		serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
		serviceAlterTraceSub.setGlobalNo(xmlVo.getGlobalNo());
		serviceAlterTraceSub.setPrcsSbst(xmlVo.getSvcMsg());
		serviceAlterTraceSub.setRsltCd(rsltCd);
		serviceAlterTraceSub.setEventCode(eventCd);

		return serviceAlterTraceSub;
    }
    
    /**
     *  최종 이력 저장(성공/실패)을 위해 serviceAlterTraceFnl에 값을 세팅 후 리턴한다.
     *  
     * @author 김동혁
     * @Date : 2024.02.07
     * @param rateResChgBasDto
     * @param isSuccess
     * @return serviceAlterTraceFnl
     */
    private McpServiceAlterTraceDto getServiceAlterTraceFnl(RateResChgBasDto rateResChgBasDto, boolean isSuccess) {
    	McpServiceAlterTraceDto serviceAlterTraceFnl = new McpServiceAlterTraceDto();

        serviceAlterTraceFnl.setNcn(rateResChgBasDto.getSvcCntrNo());
        serviceAlterTraceFnl.setContractNum(rateResChgBasDto.getSvcCntrNo());
        serviceAlterTraceFnl.setSubscriberNo(rateResChgBasDto.getMobileNo());
        serviceAlterTraceFnl.setPrcsMdlInd("BA_" + rateResChgBasDto.getRateResChgSeq());
        serviceAlterTraceFnl.settSocCode(rateResChgBasDto.getResChgRateCd());
        serviceAlterTraceFnl.settSocAmnt(rateResChgBasDto.getPayMnthChargeAmt());
        serviceAlterTraceFnl.setaSocCode(rateResChgBasDto.getBefChgRateCd());
        serviceAlterTraceFnl.setaSocAmnt(rateResChgBasDto.getBefChgRateAmnt());
        serviceAlterTraceFnl.setEventCode("FIN");
        serviceAlterTraceFnl.setChgType("R");  //-- (즉시 : I , 예약 : R)
    	
        if(isSuccess) {
        	serviceAlterTraceFnl.setTrtmRsltSmst("SUCCESS");
        	serviceAlterTraceFnl.setSuccYn("Y");
        } else {
        	serviceAlterTraceFnl.setTrtmRsltSmst("FAIL");
        	serviceAlterTraceFnl.setSuccYn("N");
        }
    	
    	return serviceAlterTraceFnl;
    }
}
