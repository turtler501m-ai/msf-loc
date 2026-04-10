package com.ktmmobile.mcp.usim.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimSendDto;
import com.ktmmobile.mcp.usim.service.ReplaceUsimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableScheduling
public class ReplaceUsimController {

	private static final Logger logger = LoggerFactory.getLogger(ReplaceUsimController.class);

	@Autowired
	private ReplaceUsimService replaceUsimService;

	@Autowired
	private BathHistService bathHistService;

	/** 설명 : 유심무상교체 신청내역 전송 */
	@Scheduled(cron = "0 2/10 * * * *")
	public void replaceUsimSendBatch() {

		//배치 실행 내역
		String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
		String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		int batchExeCascnt = 0;
		String msgDtl = "";
		int failCascnt = 0;
		int successCascnt = 0;
		String batchCd = "";
		String exeMthd = "";

		try {

			List<ReplaceUsimSendDto> replaceUsimSendDtos = replaceUsimService.selectReplaceUsimSendList();

			if(replaceUsimSendDtos == null || replaceUsimSendDtos.isEmpty()){
				logger.debug("## [replaceUsimSendBatch] replaceUsimSendBatch Target is empty");
				return;
			}

			batchExeCascnt = replaceUsimSendDtos.size();

			for(ReplaceUsimSendDto replaceUsimSendDto : replaceUsimSendDtos){
				boolean isSuccess = replaceUsimService.sendReplaceUsimRcpInfo(replaceUsimSendDto);
				if(isSuccess){
					successCascnt++;
				}else{
					failCascnt++;  // MP연동 시 타임아웃, 빈 메세지, 기타오류인 경우만 실패 카운트
				}
			}

		} catch (Exception e) {
			logger.error("replaceUsimSendBatch() Exception = {}", e.getMessage());
		} finally {
			try {
				batchCd = "24";
				exeMthd = "replaceUsimSendBatch";
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
			} catch (Exception e) {
				logger.debug("replaceUsimSendBatch error");
			}
		}
	}

}
