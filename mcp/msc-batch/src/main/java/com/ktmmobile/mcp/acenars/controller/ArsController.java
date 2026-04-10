package com.ktmmobile.mcp.acenars.controller;

import com.ktmmobile.mcp.acenars.dto.ArsDto;
import com.ktmmobile.mcp.acenars.service.ArsService;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableScheduling
@EnableAsync
public class ArsController {

	private static final Logger logger = LoggerFactory.getLogger(ArsController.class);

	@Autowired
	private ArsService arsService;

	@Autowired
	private BathHistService bathHistService;


	/**
	 * 설명 : Acen Ars VOC 기한경과 처리 배치
	 * @Date : 2025.05.26
	 */
	@Scheduled(cron = "0 10 2 * * *")
	public void acenVocEndBatch(){

		//배치 실행 내역
		String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
		String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		int batchExeCascnt = 0;
		String msgDtl = "";
		int failCascnt = 0;
		int successCascnt = 0;
		String batchCd = "";
		String exeMthd = "";

		try{

			// 기한경과 대상 조회
			List<ArsDto> list = arsService.getAcenVocEndTrg();
			batchExeCascnt = list.size();

			// 기한경과 처리
			for(ArsDto arsDto : list){
				try{
					arsService.updateAcenVocInfo(arsDto);
					successCascnt++;
				}catch(McpCommonJsonException e){
					failCascnt++;
				}catch(Exception e){
					failCascnt++;
				}
			}

		}catch (Exception e) {
			logger.error("acenVocEndBatch() Exception={}", e.getMessage());
		}finally {
			try{
				batchCd = "19";
				exeMthd = "acenVocEndBatch";
				BathHistDto bathHistDto = new BathHistDto();
				bathHistDto.setBatchCd(batchCd);                // 배치 코드
				bathHistDto.setExeMthd(exeMthd);                // 배치 메소드
				bathHistDto.setBatchExeDate(batchExeDate);      // 시작일
				bathHistDto.setBatchExeStDate(batchExeStDate);  // 시작일시
				bathHistDto.setBatchExeCascnt(batchExeCascnt);  // 배치실행건수
				bathHistDto.setSuccessCascnt(successCascnt);    // 성공건수
				bathHistDto.setFailCascnt(failCascnt);          // 실패건수
				bathHistDto.setMsgDtl(msgDtl);                  // 실패 시 에러메세지
				bathHistService.insertHist(bathHistDto);
			}catch (Exception e){
				logger.debug("acenVocEndBatch() Exception={}", e.getMessage());
			}
		}
	}

}
