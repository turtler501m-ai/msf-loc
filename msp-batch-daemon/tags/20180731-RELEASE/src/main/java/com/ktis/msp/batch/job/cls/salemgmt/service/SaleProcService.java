package com.ktis.msp.batch.job.cls.salemgmt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.cls.crdtloanmgmt.service.CrdtLoanProcService;
import com.ktis.msp.batch.job.cls.salemgmt.mapper.SaleMgmtMapper;
import com.ktis.msp.batch.job.cls.salemgmt.vo.SaleMgmtVO;

@Service
public class SaleProcService extends BaseService {
	
	@Autowired
	private SaleMgmtMapper saleMgmtMapper;
	
	@Autowired
	private CrdtLoanProcService crdtLoanProcService;
	
	
	/**
	 * 스케줄러매출처리
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveSaleInfoSchd(Map<String, Object> param) throws MvnoServiceException {
		try {
			// 매출정보등록
			SaleMgmtVO saleVO = new SaleMgmtVO();
			saleVO.setSaleDt(param.get("SALE_DT").toString());	// 매출일자
			saleVO.setOrgnId(param.get("ORGN_ID").toString());	// 조직ID
			saleVO.setSaleSrlNum(saleMgmtMapper.getSaleSrlNum());	// 매출일련번호
			saleVO.setSaleCnt(Integer.parseInt(param.get("SALE_CNT").toString()));	// 매출건수
			saleVO.setSaleAmt(Long.parseLong(param.get("SALE_AMT").toString()));	// 매출금액
			saleVO.setVat(Long.parseLong(param.get("VAT").toString()));		// 부가세
			saleVO.setDpstProcAmt(0);	// 입금처리금액
			saleVO.setSaleStatCd(ClsAcntConfig.SALE_STAT_REG);	// 등록
			saleVO.setPrepayYn("N");	// 선납여부
			saleVO.setSaleOccrDt(param.get("SALE_DT").toString());	// 매출일자
			saleVO.setSaleRecvnpayIssuYn("Y");	// 수불반영여부
			saleVO.setSaleRecvnpayIssuDt(param.get("SALE_DT").toString());	// 수불반영일자
			saleVO.setClsAcntProcYn("N");	// 결산처리여부
			saleVO.setUserId(ClsAcntConfig.USER_ID);	// 사용자
			saleVO.setSaleItmCd(param.get("SALE_ITM_CD").toString());	// 매출항목코드
			if("KTIS0030".equals(param.get("SALE_ITM_CD"))) {
//	    		saleVO.setClsAcntStdrCd(ClsAcntConfig.CLS_ACNT_STDR_NNXT_CDT);	// 결산기준코드
				saleVO.setClsAcntStdrCd(ClsAcntConfig.CLS_ACNT_STDR_NXT_CDT);	// 결산기준코드
				saleVO.setSaleOccrSctnCd(ClsAcntConfig.SALE_OCCR_STCK_CMPS);	// 매출발생구분코드
//	    		saleVO.setMvReqNum((String) param.get("PRDT_ID"));		// 2015-02-03, 재고보상시 송장ID 컬럼에 제품ID 입력
				saleVO.setPrdtId((String) param.get("PRDT_ID"));
			}
			else if("KTIS0060".equals(param.get("SALE_ITM_CD"))) {
				saleVO.setClsAcntStdrCd(ClsAcntConfig.CLS_ACNT_STDR_THS_GEN);
				saleVO.setSaleOccrSctnCd(ClsAcntConfig.SALE_OCCR_EQT_DLY);
			}
			// 매출등록
			LOGGER.debug("매출 saleVO={}", saleVO);
			saleMgmtMapper.insertSaleInfo(saleVO);
			
			// 매출수불정보 등록
			saleVO.setSaleYm(param.get("SALE_DT").toString().substring(0, 6));
			LOGGER.debug("매출수불 saleVO={}", saleVO);
			saleMgmtMapper.saveSaleRecvnpayInfo(saleVO);
			
//	    	// 2014-12-05, 계산서생성
//	    	String rmk = "";
//	    	if("KTIS0030".equals(param.get("SALE_ITM_CD"))){
//	    		rmk = "재고보상-" + param.get("PRDT_ID").toString();
//	    	}
//	    	else if("KTIS0060".equals(param.get("SALE_ITM_CD"))){
//	    		rmk = "단말연체가산금";
//	    	}
			
//	    	TaxbillMgmtVO taxVO = new TaxbillMgmtVO();
//	    	taxVO.setOrgnId(param.get("ORGN_ID").toString());
//			taxVO.setIssuStatCd("10"); // 매출
//			taxVO.setIssuTypeCd("10"); // 등록
//			taxVO.setSuplyAmt(Long.parseLong(param.get("SALE_AMT").toString()));
//			taxVO.setVat(Long.parseLong(param.get("VAT").toString()));
//			taxVO.setUserId(saleVO.getUserId());
//			taxVO.setSaleDt(saleVO.getSaleDt());
//			taxVO.setSaleItmCd(saleVO.getSaleItmCd());
//			taxVO.setSaleSrlNum(saleVO.getSaleSrlNum());
//			taxVO.setRmk(rmk);
//			
//			logger.debug("계산서 taxVO=" + taxVO);
//			taxbillMgmtService.insertTaxBillInfo(taxVO);
			
			// 여신부활처리
			if("KTIS0030".equals(param.get("SALE_ITM_CD"))) {
				Map<String, Object> crdtMap = new HashMap<String, Object>();
				crdtMap.put("CRDT_LOAN_APPL_YN",	"Y");							/* 여신적용여부 */
				crdtMap.put("CRDT_LOAN_TSK",		"R");							/* 여신업무(부활) */
				crdtMap.put("TSK_TP",				"31");							/* 사용/부활유형 */
				crdtMap.put("CRDT_LOAN_CL",			"4");							/* 익익월 */
				crdtMap.put("ORGN_ID",				param.get("ORGN_ID"));			/* 개통대리점 */
				crdtMap.put("CRDT_LOAN_AMT",		param.get("CRDT_RVL_AMT"));		/* 여신부활금액 */
				crdtMap.put("CRDT_LOAN_DT",			param.get("SALE_DT"));			/* 여신일자 */
				crdtMap.put("QNTY",					param.get("SALE_CNT"));			/* 수량 */
				crdtMap.put("SRVC_TELNUM",			param.get("PRDT_ID"));			/* 제품ID */
				crdtMap.put("USER_ID",				ClsAcntConfig.USER_ID);
				
				LOGGER.debug("crdtMap={}", crdtMap);
				crdtLoanProcService.setCrdtLoanInfoProc(crdtMap);
			}
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1043", e);
		}
		
	}

}
