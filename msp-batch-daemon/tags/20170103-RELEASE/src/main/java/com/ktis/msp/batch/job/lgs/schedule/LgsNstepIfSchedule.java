package com.ktis.msp.batch.job.lgs.schedule;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.service.LgsNstepIfService;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo.LgsNstepIfVo;
import com.ktis.msp.batch.job.lgs.lgscommon.LgsComCode;
import com.ktis.msp.batch.job.lgs.lgscommon.LgsOpenEnum;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * NSTEP 개통정보 연동처리 스케줄러
 * @Description : 개통.기변.해지.해지복구 연동정보 처리 
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @
 * @author : IB
 * @Create Date : 2015. 8. 3.
 */
@Component
public class LgsNstepIfSchedule extends BaseSchedule {
	
	static final String BATCH_NAME = "NSTEP_IF";
	
	@Autowired
	private LgsNstepIfService lgsNstepIfService;
	
	public LgsNstepIfSchedule() {
//		LOGGER.info("Nstep Link InterFace Batch Start");
	}
	
	@Scheduled(cron="${lgsNstepIfSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			batchCommonVO.setBatchDate(KtisUtil.toStr(new Date(), "yyyyMMdd"));
			int dailyBatchIngCount = batchCommonService.getDailyBatchIngCount(batchCommonVO);
			if(dailyBatchIngCount <= 0) {
				this.startLgsNstepIf(batchCommonVO);
			} else {
				batchCommonVO.setRemrk("진행중인 스케줄이 있습니다.");
			}
			
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	public void startLgsNstepIf(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		try {
			/** 연동 처리 대상 리스트 내역 조회 **/
			List<?> linkDataLst = lgsNstepIfService.getLinkNonProcData();
			Iterator<?> dataLst = linkDataLst.iterator();
			
			LgsNstepIfVo dataVo = null;
			
			StringBuffer sb = new StringBuffer();
			
			while(dataLst.hasNext()) {
				
				Map<?,?> dataInfo = (Map<?,?>) dataLst.next();
				
				dataVo = new LgsNstepIfVo();
				String ifQueNum = dataInfo.get("ifQueNum").toString();
				dataVo.setIfQueNum(ifQueNum);
				dataVo.setUserId(BATCH_NAME);
				
				try {
					
					/** 연동 데이터 상세 조회 **/
					dataVo = lgsNstepIfService.getCheckedLinkDataProc(dataVo);
					
					
					/** 연동 데이터 유효성 검증 처리 **/
					dataVo = lgsNstepIfService.verifyLinkedDataProc(dataVo);
					
					
					/** CASE : 개통(NAC) **/
					if( LgsOpenEnum.EVNT_NAC.equals(dataVo.getAplEvntCd()) && LgsComCode.YN_Y.equals(dataVo.getTakeYn()) ) {
						lgsNstepIfService.intmOpenProc(dataVo);
					}
					/** CASE : 인수전개통(NAC) **/
					if( LgsOpenEnum.EVNT_NAC.equals(dataVo.getAplEvntCd()) && LgsComCode.YN_N.equals(dataVo.getTakeYn()) ) {
						lgsNstepIfService.intmOpenNonTakeProc(dataVo);
					}
					/** CASE : 기변(C07) **/
					else if( LgsOpenEnum.EVNT_C07.equals(dataVo.getAplEvntCd()) ) {
						
						// 우수기변
						if( LgsOpenEnum.EVNT_RSN_VP.equals(dataVo.getEvntRsnCd()) ) {
							lgsNstepIfService.intmChngVpProc(dataVo);
						} 
						// 우수기변 취소
						else if( LgsOpenEnum.EVNT_RSN_VC.equals(dataVo.getEvntRsnCd()) ) {
							lgsNstepIfService.intmChngVpCanProc(dataVo);
						} 
						
						else {
							lgsNstepIfService.intmChngProc(dataVo);
						}
					}
					/** CASE : 해지(CAN) **/
					else if( LgsOpenEnum.EVNT_CAN.equals(dataVo.getAplEvntCd()) ) {
						lgsNstepIfService.intmCanProc(dataVo);
					}
					/** CASE : 당일해지복구(RCL) **/
					else if( LgsOpenEnum.EVNT_RCL.equals(dataVo.getAplEvntCd()) ) {
						lgsNstepIfService.intmRstProc(dataVo);
					}
					
				} catch( Exception e ) {
					LOGGER.error(e.getMessage());
					LOGGER.error("Nstep Link InterFace Batch - ERROR [{}]", ifQueNum);
					
					sb.append(" [ 에러 : ("+ifQueNum+") ] ");
				}
			}
			
			batchCommonVO.setRemrk(sb.toString());
			LOGGER.info("Nstep Link InterFace Batch End");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new MvnoServiceException("ELGS1002", e);
		}
		
	}
	
}