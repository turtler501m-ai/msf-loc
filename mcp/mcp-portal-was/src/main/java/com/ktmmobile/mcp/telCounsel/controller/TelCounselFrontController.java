package com.ktmmobile.mcp.telCounsel.controller;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.cti.CtiService;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.telCounsel.dto.TelCounselDto;
import com.ktmmobile.mcp.telCounsel.service.TelCounselService;


/**
 * @Class Name : TelCounselFrontController
 * @Description : 전화상담 프론트 controller
 *
 * @author : ant
 * @Create Date : 2016. 3. 8.
 */
@Controller
public class TelCounselFrontController {
    private static final Logger logger = LoggerFactory.getLogger(TelCounselFrontController.class);

    @Autowired
    TelCounselService telCounselService;

    @Autowired
    private CtiService ctiService;

    /**
    * @Description :전화상담입력폼
    * @return
    * @Author : ant
    * @Create Date : 2016. 3. 8.
    */

	@RequestMapping(value = {"/telCounsel/telCounselForm.do", "/m/telCounsel/telCounselView.do"})
	public String telCounselView() {
		if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/telCounsel/telCounselView";
        } else {
            return "/portal/telCounsel/telCounselForm";
        }
	}


    /**
    * @Description :전화상담 저장처리 ajax 호출
    * @param telCounselDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 3. 8.
    */
    @RequestMapping(value = "/m/telCounsel/telCounselAjax.do")
    @ResponseBody
    public Map<String, Object> telCounselAjax(HttpServletRequest request,  @ModelAttribute TelCounselDto telCounselDto) {
        int rs = 0;

        String counselCtg = telCounselDto.getCounselCtg();
        if(counselCtg == null) {
        	counselCtg = "";
        }
        try {
            HttpSession session = request.getSession();
            String answer = (String)session.getAttribute("getAnswer");
        	//String answer = request.getParameter("answer");

            UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
            String userDivision = "";
            if(userSession !=null){
            	userDivision = userSession.getUserDivision();
            }

            if(counselCtg.contentEquals("01")) {
            	counselCtg = "GAA01";
            } else {
            	counselCtg = "GAA02";
            }

            if(telCounselDto.getAnswer().equals(answer)){
                //rs =1; 0(CTI 연계 실패 /1 성공)
                rs = ctiService.ctiTelCounsel(0, telCounselDto.getFullTextForMsp(), "INF_APP_004"
                                          , telCounselDto.getCounselNm(), "", telCounselDto.getMobileNo()
                                          , "법인상담", "N", counselCtg,userDivision);
                // ,telCounselDto.getJuridicalNumber(),telCounselDto.getAgentNm()

                // GAA02 홈페이지 간편가입(유심)
                // GAA01 홈페이지 간편가입(단말)
            }else{	//캡챠가 틀린경우
                rs = 2;
            }
        } catch(SocketTimeoutException e) {
            throw new McpCommonJsonException(ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonJsonException(ExceptionMsgConstant.COMMON_EXCEPTION);
        }

        // CTI 전송 성공시
        if(rs == 1){
        	// 신청 정보 저장
        	telCounselDto.setCounselCtg(counselCtg);
        	telCounselService.insertCtiTelCounsel(telCounselDto);
        }

        Map<String, Object> rtnJson = new HashMap<String, Object>();
        rtnJson.put("rs", rs);
        return rtnJson;
    }

    /**
    * @Description : 전화상담,신청서 30일이내건 중복체크
    * @param telCounselDto
    * @return
    * @Author : ant
    * @Create Date : 2019. 3. 20.
    */
    @RequestMapping(value = "/m/telCounsel/telCounselDupChk.do")
    @ResponseBody
    public Map<String, Object> telCounselDupChk(HttpServletRequest request,  @ModelAttribute TelCounselDto telCounselDto) {
        int rs = 0;
        try {
        	int telCounselCnt = 0;
        	int ctiReqCnt = 0;
        	// 전화상담 신청 30일 이내건 확인
        	telCounselCnt = telCounselService.selectCtiTelCounselCnt(telCounselDto);
        	// 직영온라인 신청 30일 이내건 확인
        	ctiReqCnt = telCounselService.selectCtiReqCnt(telCounselDto);

        	if(telCounselCnt > 0 || ctiReqCnt > 0){
        		rs = 1;
        	} else {
        		rs = 0;
        	}
        } catch(DataAccessException e) {
            throw new McpCommonJsonException(ExceptionMsgConstant.DB_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonJsonException(ExceptionMsgConstant.COMMON_EXCEPTION);
        }

        Map<String, Object> rtnJson = new HashMap<String, Object>();
        rtnJson.put("rs", rs);
        return rtnJson;
    }
}
