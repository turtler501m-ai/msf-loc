package com.ktis.mcpif.nstep.web;

import java.net.MalformedURLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktis.mcpif.common.StringUtil;
import com.ktis.mcpif.nstep.NstepQueryVo;
import com.ktis.mcpif.nstep.NstepResponse;
import com.ktis.mcpif.nstep.NstepVo;
import com.ktis.mcpif.nstep.service.NstepService;



@Controller
public class NstepController {  
	protected Logger logger = LogManager.getLogger(getClass());

	@Resource(name="nStepService")
	private NstepService nStepService;
	
	
	@RequestMapping("/nStep/serviceCall.do")
	public @ResponseBody String nStepPassController(HttpServletRequest request, HttpServletResponse response , ModelMap model,
			@ModelAttribute("nStepVo") NstepQueryVo nStepQueryVo ) throws MalformedURLException, SOAPException {
		String resultMessage = "";
		String status = "";
		String err_msg = "";
		String sNStepCode = "";
		String sNStepMsg = "";
		String sLogKey = "";
		try {
			sLogKey = nStepQueryVo.getRes_no();
			
			logger.error("[grep:" + sLogKey + "]+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			logger.error("[grep:" + sLogKey + "]N-STEP SEND START");
			logger.error("[grep:" + sLogKey + "]+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// -------------------------------------------------------------------
			// Initialize.
			// -------------------------------------------------------------------
			NstepResponse nStepResponse = new NstepResponse();
			String clientIp = "";
			clientIp = StringUtil.NVL(request.getRemoteAddr(),"");
			NstepVo nStepVo = new NstepVo();

			// -------------------------------------------------------------------
			// Test Vo
			// -------------------------------------------------------------------
			logger.error("[grep:" + sLogKey + "]     =====>    MSP에서 전송받은 값 출력     <=====");
			nStepService.voPrint(nStepQueryVo, sLogKey);    
			nStepService.nStepDataChange(nStepVo, nStepQueryVo);
			
			// -------------------------------------------------------------------
			// Service Process
			// -------------------------------------------------------------------
			//특정 아이피만 수행 가능 하도록 한다. IP는 프로퍼티로 작성
			if(!clientIp.equals("")) {
				nStepResponse = nStepService.nStepWebServiceCall(nStepVo);
			}
	
			status = StringUtil.NVL(nStepResponse.getSvc_status(), "");
			err_msg = StringUtil.NVL(nStepResponse.getErr_msg(), "");
			sNStepCode = status;
			sNStepMsg = err_msg;
			if(status.equals("00")) {
				err_msg = "전송을 성공하였습니다.";
			} else {
				if(status.equals("88")) {
					err_msg = "KOS 시스템 에러.";
				} else {
					status = "99";
					if("업무처리안내:[일련번호가 중복되었습니다.]".equals(err_msg)) {
						err_msg = "일련번호가 중복되었습니다.";
					} else if("업무처리오류:[일련번호가 중복되었습니다.]".equals(err_msg)) {
						err_msg = "일련번호가 중복되었습니다.";
					} else if("업무 처리 에러:등록을 정상적으로 마치지 못했습니다.".equals(err_msg)) {
						err_msg = "신청정보가 옳바르지 않습니다.";
					} else if("[서비스온라인신청일련번호가 중복되었습니다.]".equals(err_msg)) {
						err_msg = "일련번호가 중복되었습니다.";
					} else if("고객식별번호 가(이) 존재하지 않습니다".equals(err_msg)) {
						err_msg = "고객식별번호가 옳바르지 않습니다.";
					} else {
						err_msg = "KOS전산 지연으로 인해 잠시 후 이용바랍니다.";
					}
				}
			}
		} catch(Exception e) {
			status = "99";
			err_msg = "KOS전산 지연으로 인해 잠시 후 이용바랍니다.";
			sNStepCode = status;
			sNStepMsg = e.getMessage();
			logger.error("[grep:" + sLogKey + "]" + StringUtil.getPrintStackTrace(e));
		}
		resultMessage = status +","+err_msg;
		logger.error("[grep:" + sLogKey + "]+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.error("[grep:" + sLogKey + "]N-STEP SEND END(grep:" + sLogKey + ") - {N-STEP OUT[" + sNStepCode + "," + sNStepMsg + "]} , {PRX OUT[" + resultMessage + "]}");
		logger.error("[grep:" + sLogKey + "]+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		return resultMessage;
	}
}
