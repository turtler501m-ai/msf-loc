package com.ktis.msp.batch.job.rcp.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.AtmMgmtService;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.TossMgmtService;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpDetailVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.ScanVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * TOSS 개통고객 신청정보 생성
 */
@Component
public class TossRcpRegSchedule extends BaseSchedule {
	
	@Autowired
	private TossMgmtService tossMgmtService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${tossRcpRegSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		String result = BatchConstants.BATCH_RESULT_OK;
		int procCnt = 0;
		
		try {
			batchStart(batchCommonVO);
			
			List<RcpDetailVO> list = tossMgmtService.selectNonTossRcpList();
			
			for(RcpDetailVO rcpDetailVO : list) {
				
				/*등록자 정보 설정*/
				rcpDetailVO.setRid("batch");
				rcpDetailVO.setRip("batch");
				rcpDetailVO.setShopUsmId("batch");
				
				/*신청서 생성*/
				int returnCnt = tossMgmtService.updateRcpNewDetail(rcpDetailVO);
				
				if(returnCnt == 0) {
					throw new MvnoServiceException("ERCP1008", "CONTRACT_NUM : " + rcpDetailVO.getContractNum());
				} else {
					/*ATM의 경우 신청서 생성 시 서식시 생성 필수*/
					ScanVO scanVo = new ScanVO();
					scanVo.setRequestKey(rcpDetailVO.getRequestKey());
					scanVo.setUserId(rcpDetailVO.getSessionUserId());
					scanVo.setResNo(rcpDetailVO.getNiceCertKey());
					scanVo.setScanPath(propertiesService.getString("scan.form.path"));
					scanVo.setScanUrl(propertiesService.getString("scan.form.url"));
					
					tossMgmtService.prodSendScan(scanVo, rcpDetailVO);
				}
				
				
				/* 신청정보 생성 건 수 */
				procCnt = procCnt + 1;
			}
			
			batchCommonVO.setExecCount(procCnt);
			batchEnd(batchCommonVO);
			
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

