package com.ktmmobile.mcp.dormant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.dormant.dto.DormantDto;
import com.ktmmobile.mcp.dormant.service.DormantSvc;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;

@Controller
@EnableAsync
@EnableScheduling
public class DormantController {

    private static final Logger logger = LoggerFactory.getLogger(DormantController.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

	@Autowired
    private DormantSvc dormantSvc;

	@Autowired
	private BathHistService bathHistService;

	private final static int SMS_TEMPLATE_ID_DORMANT = 37;


	@RequestMapping("/dormant")
	public String dormant() {

		dormantSmsGdncSend();
		deleteAssociateMember();
		dormantRegularMemberProcess();

		return "dormantController";
	}



	/**
	 * 11개월 이내로 로그인한 이력이 없는 경우 LMS 발송

	@Scheduled(cron = "0 0 8 * * *") // 일 1회	 08:00:00
	SRM24100857443 휴면 전환 중지
	 */
	public void dormantSmsGdncSend() {

		String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
		String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		int batchExeCascnt = 0;
		String msgDtl = "";
		int failCascnt = 0;
		int successCascnt = 0;
		String batchCd = "";
		String exeMthd = "";

		//준회원 로그인 한지 11개월 유저 조회
		try {
			List<DormantDto> dormantList = dormantSvc.selectDormantList();
			if(dormantList != null && dormantList.size() > 0) {
				RestTemplate restTemplate = new RestTemplate();

				MspSmsTemplateMstDto mspSmsTemplateMstDto = restTemplate.postForObject(apiInterfaceServer + "/common/mspSmsTemplateMst", SMS_TEMPLATE_ID_DORMANT, MspSmsTemplateMstDto.class);

				if (mspSmsTemplateMstDto != null && dormantList !=null && !dormantList.isEmpty()) {
					batchExeCascnt = dormantList.size();
					//ktmmobile 고객센터 유료 전화번호
					String callBack = "18995000";
					MultiValueMap<String, Object> params = null;

					for(DormantDto item : dormantList) {
						try {

							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{year}", item.getYearText()));
							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{month}", item.getMonthText()));
							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{lastDay}", item.getDateText()));

							params = new LinkedMultiValueMap<String,Object>();
							params.add("I_SUBJECT", mspSmsTemplateMstDto.getSubject());
							params.add("I_MSG_TYPE", "3");
							params.add("I_CTN", item.getMobileNo().replace("-", ""));
							params.add("I_CALLBACK", callBack);
							params.add("I_MSG", mspSmsTemplateMstDto.getText());
							params.add("reqServer", "BATCH");
							params.add("Success", "100");

							restTemplate = new RestTemplate();
							restTemplate.postForObject(apiInterfaceServer + "/sms/addSms", params, Integer.class);

						}catch(Exception e) {
							msgDtl = e.getMessage();
							failCascnt++;
							continue;
						}
						successCascnt++;
					}
				}
			}

		}catch(Exception e) {
			logger.error("dormantSvc.selectDormantList() Exception = {}", e.getMessage());
		} finally {
			try {
				batchCd = "030";
				exeMthd = "dormantSmsGdncSend";
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
				logger.debug("alarmBatch error");
			}
		}
	}

	/**
	 * 준회원 12개월 이내로 로그인한 이력이 없는 경우 회원 정보 삭제 처리
	 * 삭제 안하고 논리적 삭제로 변경
	 *
	 * SRM24100857443 휴면 전환 중지
	 *
	 * @Scheduled(cron = "0 30 0 * * *") // 일 1회	 00:30:00
	 *
	 */
	@Transactional
	public void deleteAssociateMember() {
		String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
		String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		int batchExeCascnt = 1;
		String msgDtl = "";
		int failCascnt = 0;
		int successCascnt = 0;
		String batchCd = "";
		String exeMthd = "";

		try {
			//기존 삭제는 주석 처리 관련 레퍼런스는 모두 변경
			//int deleteResult = dormantSvc.deleteAssociateMember();
			int updateResult = dormantSvc.updateAssociateMember();
			if(updateResult > 0) {
				successCascnt = 1;
			} else {
				failCascnt = 1;
			}
		}catch(Exception e) {
			logger.error("deleteAssociateMember() Exception = {}", e.getMessage());
		} finally {
			try {
				batchCd = "031";
				exeMthd = "updateAssociateMember";
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
				logger.debug("updateAssociateMember error");
			}
		}
	}

	/**
	 * 정회원 12개월 이내로 로그인한 이력이 없는 경우
	 * - 휴면 회원 테이블 insert : 회원상태는 '휴면', 이력생성일은 현재일자로 등록하고 나머지 정보는 정상 회원 테이블 데이터를 그대로 세팅
     * - 정상 회원 테이블에서 delete
     *
     * SRM24100857443 휴면 전환 중지
     * @Scheduled(cron = "0 40 0 * * *") // 일 1회	 00:40:00
	 *
	 */
	@Transactional
	public void dormantRegularMemberProcess() {
		String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
		String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		int batchExeCascnt = 0;
		String msgDtl = "";
		int failCascnt = 0;
		int successCascnt = 0;
		String batchCd = "";
		String exeMthd = "";

		try {
			List<DormantDto> dormantList = dormantSvc.selectDormantRegularList();
			if(dormantList != null && dormantList.size() > 0) {
				batchExeCascnt = dormantList.size();
				int insertResult = -1;
				for(DormantDto item : dormantList) {
					try {
						insertResult = dormantSvc.insertDormantRegularMember(item.getUserid());
						if(insertResult == 1) {
							dormantSvc.deleteDormantRegularMember(item.getUserid());
						}
					}catch(Exception e) {
						msgDtl = e.getMessage();
						failCascnt++;
						continue;
					}
					successCascnt++;
				}
			}
		}catch(Exception e) {
			logger.error("dormantRegularMemberProcess() Exception = {}", e.getMessage());
		} finally {
			try {
				batchCd = "032";
				exeMthd = "dormantRegularMemberProcess";
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
				logger.debug("dormantRegularMemberProcess error");
			}
		}
	}
}
