package com.ktmmobile.mcp.event.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.event.dto.FrndInviUsimDto;
import com.ktmmobile.mcp.event.service.FrndInviUsimSvc;

@Controller
public class FrndInviUsimController {

    private static Logger logger = LoggerFactory.getLogger(FrndInviUsimController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    private FrndInviUsimSvc frndInviUsimSvc;    

    @Autowired
    private AppformSvc appformSvc;

    @RequestMapping(value = {"/event/frndInviUsimPop.do" , "/m/event/frndInviUsimPop.do"})
    public String frndInviUsimPop(HttpServletRequest request, @ModelAttribute FrndInviUsimDto frndInviUsimDto, Model model ) {

        String rtnPageNm = "/portal/event/frndInviUsimPop";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/event/frndInviUsimPop";
        }

        // 유의사항 1
        FormDtlDTO formDtlDTO1 = new FormDtlDTO();
        String cdGroupId1 = "FormEtcCla";
        String cdGroupId2 = "FrndInviUsim1";
        formDtlDTO1.setCdGroupId1(cdGroupId1);
        formDtlDTO1.setCdGroupId2(cdGroupId2);
        FormDtlDTO formDtlRtn1 = appformSvc.getFormDesc(formDtlDTO1);// docver
        String docVer1 = "";
        if(formDtlRtn1 !=null){
            docVer1 = formDtlRtn1.getDocVer();
        }
        FormDtlDTO oFormDtlDTO1 = new FormDtlDTO();
        oFormDtlDTO1.setCdGroupId1(cdGroupId1);
        oFormDtlDTO1.setCdGroupId2(cdGroupId2);
        oFormDtlDTO1.setDocVer(docVer1);
        FormDtlDTO rtnObj1 = formDtlSvc.FormDtlView(oFormDtlDTO1); // 내용가져오기
        String docContent1 = "";
        if (rtnObj1 != null) {
            docContent1 = StringEscapeUtils.unescapeHtml(rtnObj1.getDocContent());
        }

        // 유의사항 2
        FormDtlDTO formDtlDTO2 = new FormDtlDTO();
        cdGroupId2 = "FrndInviUsim2";
        formDtlDTO2.setCdGroupId1(cdGroupId1);
        formDtlDTO2.setCdGroupId2(cdGroupId2);
        FormDtlDTO formDtlRtn2 = appformSvc.getFormDesc(formDtlDTO2);// docver
        String docVer2 = "";
        if(formDtlRtn2 !=null){
            docVer2 = formDtlRtn2.getDocVer();
        }
        FormDtlDTO oFormDtlDTO2 = new FormDtlDTO();
        oFormDtlDTO2.setCdGroupId1(cdGroupId1);
        oFormDtlDTO2.setCdGroupId2(cdGroupId2);
        oFormDtlDTO2.setDocVer(docVer2);
        FormDtlDTO rtnObj2 = formDtlSvc.FormDtlView(oFormDtlDTO2); // 내용가져오기
        String docContent2 = "";
        if (rtnObj2 != null) {
            docContent2 = StringEscapeUtils.unescapeHtml(rtnObj2.getDocContent());
        }

        model.addAttribute("docContent1", docContent1);
        model.addAttribute("docContent2", docContent2);
        return rtnPageNm;
    }

    
    /**
     * 친구초대 가입자정보 확인
     * @param frndInviUsimDto
     * @return
     */
    @RequestMapping(value = "/event/cstmrInfoChkAjax.do")
    @ResponseBody
    public Map<String,Object> cstmrInfoChkAjax(@ModelAttribute FrndInviUsimDto frndInviUsimDto , Model model ) {

        Map<String,Object> hm = new HashMap<String,Object>();
        int cnt = frndInviUsimSvc.chkCstmrInfo(frndInviUsimDto);
        String resultCode = "S";
        if (cnt == 0) {
        	resultCode = "E";
        }
        hm.put("resultCode", resultCode);
        return hm;
    }

    @RequestMapping(value = "/event/frndInviUsimRegAjax.do")
    @ResponseBody
    public Map<String,Object> frndInviUsimRegAjax(@ModelAttribute FrndInviUsimDto frndInviUsimDto , Model model ) {
        Map<String,Object> hm = new HashMap<String,Object>();
        int cnt = frndInviUsimSvc.frndInviUsimReg(frndInviUsimDto);
        String resultCode = "S";
        if (cnt == 0) {
            resultCode = "D";
        }
        hm.put("resultCode", resultCode);
        return hm;
    }

}
