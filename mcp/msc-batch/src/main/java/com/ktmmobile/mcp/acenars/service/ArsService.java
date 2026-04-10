package com.ktmmobile.mcp.acenars.service;

import com.ktmmobile.mcp.acenars.dao.ArsDao;
import com.ktmmobile.mcp.acenars.dto.ArsDto;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.texting.service.TextingService;
import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ArsService {

	private static final Logger logger = LoggerFactory.getLogger(ArsService.class);

	@Value("${acen.req.url}")
	private String acenReqUrl;

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Autowired
	private ArsDao arsDao;

	@Autowired
	private TextingService textingService;

	private static final int VOC_SMS_TEMPLATE = 373;


	public List<ArsDto> getAcenVocEndTrg() {
		return arsDao.getAcenVocEndTrg();
	}

	@Transactional(transactionManager = "bootOraTransactionManager", rollbackFor = Exception.class)
	public void updateAcenVocInfo(ArsDto searchDto) {

		// 기한경과 처리
		arsDao.updateAcenVocInfo(searchDto.getReqSeq());
		arsDao.insertAcenVocHist(searchDto.getReqSeq());

		// 기한경과 처리항목 상세조회
		ArsDto arsDto = arsDao.getAcenVocInfo(searchDto.getReqSeq());

		// 상태변경 ARS 연동
		boolean result = this.callAcenArsService(arsDto);
		if(!result){
			throw new McpCommonJsonException("-1", "ARS 연동 오류");
		}

		// 담당자 문자전송 (기존 상태가 진행중인 건만)
		if("01".equals(searchDto.getStatus())){
			this.sendAcenVocSms(arsDto);
		}
	}

	private boolean callAcenArsService(ArsDto arsDto) {

		boolean callSuccess = true;
		String responseBody = "";

		try{

			String ansContent = (arsDto.getAnsContent() == null) ? "" : arsDto.getAnsContent();

		  // 연동 데이터 세팅
		  NameValuePair[] data = {
		  	new NameValuePair("ifCode", "A10"),
		  	new NameValuePair("qnaSeq", arsDto.getVocSeq()),
		  	new NameValuePair("ansContent", URLEncoder.encode(ansContent, "UTF-8")),
		  	new NameValuePair("ansStatus", arsDto.getStatus()),
		  	new NameValuePair("ansDate", arsDto.getRvisnDttm()),
		  	new NameValuePair("ansId", arsDto.getRvisnId()),
		  	new NameValuePair("contractNum", arsDto.getContractNum())
		  };

			responseBody = HttpClientUtil.post(acenReqUrl + "/aiccApp/custReqArsServiceCall.do", data, "UTF-8");

			if(StringUtil.isEmpty(responseBody)){
				return false;
			}

		  // 연동 결과 PARSING
		  JSONObject jsonObject = new JSONObject(responseBody);
			String returncode= jsonObject.has("returncode") ? jsonObject.getString("returncode") : "0";

			if("0".equals(returncode)){
				String errordescription= jsonObject.has("errordescription") ? jsonObject.getString("errordescription") : "ARS 연동오류";
				throw new Exception("[" + arsDto.getVocSeq() + "] " + errordescription);
			}

	  }catch(SocketTimeoutException e){
	  	logger.error("## callAcenArsService SocketTimeoutException={}", e.getMessage());
	    callSuccess= false;
	  }catch(JSONException e){
	  	logger.error("## callAcenArsService JSONException={}", e.getMessage());
			callSuccess= false;
	  }catch(Exception e){
	  	logger.error("## callAcenArsService Exception={}", e.getMessage());
			callSuccess= false;
	  }

		return callSuccess;
	}

	private void sendAcenVocSms(ArsDto arsDto) {

		RestTemplate restTemplate = new RestTemplate();

		// 대리점 담당자 정보 조회
		String[] mobileNums = restTemplate.postForObject(apiInterfaceServer + "/msp/getAcenVocAgntListByOrgnId", arsDto.getVocAgntCd(), String[].class);
		if(mobileNums == null || mobileNums.length == 0){
			return;
		}

		// 문자템플릿 조회
		MspSmsTemplateMstDto smsDto = restTemplate.postForObject(apiInterfaceServer + "/common/mspSmsTemplateMst", VOC_SMS_TEMPLATE, MspSmsTemplateMstDto.class);
		if(smsDto == null){
			return;
		}

		String dueDt = arsDto.getDueDt().substring(0,4) + "년 " +
									 arsDto.getDueDt().substring(4,6) + "월 " +
									 arsDto.getDueDt().substring(6) + "일";

		String message = smsDto.getText().replaceAll(Pattern.quote("#{vocSeq}"), arsDto.getVocSeq())
																		 .replaceAll(Pattern.quote("#{dueDt}"), dueDt);

		String scheduleTime = DateTimeUtil.getFormatString("yyyyMMdd") + "090000";

		// 문자전송
		for(String mobileNum : mobileNums){
			textingService.sendLmsWithSchedule(smsDto.getSubject(), mobileNum, message, smsDto.getCallback(), String.valueOf(VOC_SMS_TEMPLATE), "acenVocEndBatch", scheduleTime);
		}
	}

}
