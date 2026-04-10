package com.ktmmobile.mcp.temp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.temp.service.TempSvc;

@Controller
@EnableAsync
@EnableScheduling
public class TempController {

    private static final Logger logger = LoggerFactory.getLogger(TempController.class);

	@Autowired
    private TempSvc tempSvc;

	@Autowired
	private BathHistService bathHistService;


	/**
	 * 가입신청 임시저장 데이터 주기적 삭제 처리
	 * - 현재일 기준 D-8 이전 데이터 모두 삭제 처리
	 * - 일 1회 배치 실행
	 */
	@Scheduled(cron = "0 0 2 * * *") // 일 1회	 02:00:00
	public void deleteTempData() {
		String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
		String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		int batchExeCascnt = 0;
		String msgDtl = "";
		int failCascnt = 0;
		int successCascnt = 0;
		String batchCd = "";
		String exeMthd = "";
		try {

			List<Long> requestKeyList = tempSvc.selectTempRequestKey();
			if(requestKeyList != null && requestKeyList.size() > 0) {
				batchExeCascnt = requestKeyList.size();
				for(Long item : requestKeyList) {
					try {
						tempSvc.deleteNmcpRequestAdditionTempData(item);
						tempSvc.deleteNmcpRequestAgentTempData(item);
						tempSvc.deleteNmcpRequestApdSaleinfoTempData(item);
						tempSvc.deleteNmcpRequestApdTempData(item);
						tempSvc.deleteNmcpRequestChangeTempData(item);
						tempSvc.deleteNmcpRequestClauseTempData(item);
						tempSvc.deleteNmcpRequestCstmrTempData(item);
						tempSvc.deleteNmcpRequestDlvryTempData(item);
						tempSvc.deleteNmcpRequestDvcChgTempData(item);
						tempSvc.deleteNmcpRequestMoveTempData(item);
						tempSvc.deleteNmcpRequestPaymentTempData(item);
						tempSvc.deleteNmcpRequestReqTempData(item);
						tempSvc.deleteNmcpRequestSaleinfoTempData(item);
						tempSvc.deleteNmcpRequestTempData(item);
					}catch(Exception e) {
						msgDtl = e.getMessage();
						failCascnt++;
						continue;
					}
					successCascnt++;
				}
			}
		}catch(Exception e) {
			logger.error("deleteTempData() Exception = {}", e.getMessage());
		} finally {
			try {
				batchCd = "01";
				exeMthd = "deleteTempData";
				BathHistDto bathHistDto = new BathHistDto();
				bathHistDto.setBatchCd(batchCd);
				bathHistDto.setExeMthd(exeMthd);
				bathHistDto.setBatchExeDate(batchExeDate);
				bathHistDto.setBatchExeStDate(batchExeStDate);
				bathHistDto.setBatchExeCascnt(batchExeCascnt);
				bathHistDto.setSuccessCascnt(successCascnt);
				bathHistDto.setFailCascnt(failCascnt);
				bathHistDto.setMsgDtl(msgDtl);
				bathHistService.insertHist(bathHistDto);
			}catch(Exception e) {
				logger.debug("insertHist error");
			}
		}
	}

}
