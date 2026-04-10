package com.ktmmobile.mcp.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dto.OwnPhoNumDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.OwnPhoNumService;
import com.ktmmobile.mcp.usim.service.UsimService;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;

@Controller
public class OwnPhoNumController {

    private static Logger logger = LoggerFactory.getLogger(OwnPhoNumController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**조직 코드  */
    @Value("${sale.orgnId}")
    private String orgnId;

    @Autowired
    private OwnPhoNumService ownPhoNumService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    UsimService usimService ;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    CertService certService;


    
    /**
     * 가입번호 조회 메뉴
     */
    @RequestMapping(value = {"/cs/ownPhoNum.do", "/m/cs/ownPhoNum.do"}  )
    public String prvCommData(ModelMap model , HttpServletRequest request){

        String jspPageName = "/portal/cs/ownPhoNum";
        String thisPageName ="/cs/ownPhoNum.do";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            jspPageName = "/mobile/cs/ownPhoNum";
            thisPageName ="/m/cs/ownPhoNum.do";
        }
        
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }
        return jspPageName;
    }

    /**
     * 가입한 모든 전화번호 가져오기
     */
    @RequestMapping(value = {"/cs/getOwnPhoNumListAjax.do"})
	@ResponseBody
	public Map<String, Object> getOwnPhoNumListAjax(HttpServletRequest request, @ModelAttribute("ownPhoNumDto") OwnPhoNumDto ownPhoNumDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // ================ STEP START ================
        // 최소 스텝개수 확인
        if(certService.getStepCnt() < 1 ){
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("RESULT_DESC", STEP_CNT_EXCEPTION);
            return rtnMap;
        }

        // 스텝종료여부, 이름, 생년월일, 본인인증 유형, reqSeq, resSeq
        String[] certKey= {"urlType", "stepEndYn", "name", "birthDate", "authType"
                         , "reqSeq", "resSeq"};
        String[] certValue= {"getOwnPhoNum", "Y", ownPhoNumDto.getCstmrName(), ownPhoNumDto.getCstmrNativeRrn01(), ownPhoNumDto.getOnlineAuthType()
                           , ownPhoNumDto.getReqSeq(), ownPhoNumDto.getResSeq()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", "STEP02");
            rtnMap.put("RESULT_DESC", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }

        ownPhoNumDto.setOnlineAuthInfo("ReqNo:" + ownPhoNumDto.getReqSeq() + ", ResNo:" + ownPhoNumDto.getResSeq());
        // ================ STEP END ================

        List<OwnPhoNumDto> ownPhoNumList = ownPhoNumService.selectOwnPhoNumList(ownPhoNumDto);
        int cnt = ownPhoNumService.insertOwnPhoNumChkHst(ownPhoNumDto);
		
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		rtnMap.put("list", ownPhoNumList);
		return rtnMap;
	}

}
