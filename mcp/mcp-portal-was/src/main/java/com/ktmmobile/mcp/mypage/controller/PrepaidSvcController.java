package com.ktmmobile.mcp.mypage.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.MspPpsRcgTesDto;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MessageBox;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;



@Controller
public class PrepaidSvcController {

	private static Logger logger = LoggerFactory.getLogger(PrepaidSvcController.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private MypageService mypageService;

    /*
     * 청소년 요금제 충전 내역
     *
     */
    @RequestMapping("/m/mypage/prepaidList.do")
    public String ppList(
            ModelMap model
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/m/mypage/prepaidList.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }


        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        if( !this.checkUserType(searchVO, cntrList, userSession) ){
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setRedirectUrl("/m/mypage/updateForm.do");
            responseSuccessDto.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        model.addAttribute("cntrList", cntrList);//고객정보 리스트
        List<MspPpsRcgTesDto> result = null;

        //---- API 호출 S ----//
		RestTemplate restTemplate = new RestTemplate();
		MspPpsRcgTesDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/ppsList", searchVO.getNcn(), MspPpsRcgTesDto[].class); // mypagePrepaidService.selectPPSList
		result = Arrays.asList(resultList);
		//---- API 호출 E ----//

        model.addAttribute("result", result);//청소년 요금제 충전 내역 리스트
        return "mobile/mypage/M_prepaidList";

    }

    @Deprecated
    private MessageBox getMessageBox(){
        MessageBox mbox = new MessageBox();
        mbox.setMessage("정회원 인증 후 이용하실 수 있습니다.");
        mbox.putParam("menuKey", "403");
        mbox.setUrl("/user/informationMember.do");

        return mbox;
    }

    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession){
        boolean rtnObj = false;
        if( ! StringUtil.equals(userSession.getUserDivision(), "01") ){
            return false;
        }

		if(cntrList == null) {
        	return false;
        }

        if(cntrList.size() <= 0){
            return false;
        }

        //log.debug("param searchVO.getMenuKey():"+searchVO.getMenuKey());
        //log.debug("param searchVO.getNcn():"+searchVO.getNcn());
        if( StringUtil.isEmpty(searchVO.getNcn()) ){
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo() );
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo() );
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
        }

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            if(StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))){
                searchVO.setNcn( ncn );
                searchVO.setCtn( ctn );
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                rtnObj = true;
            }
        }

        /*
         * 확인용도 로그
        log.debug("searchVO.getNcn():"+searchVO.getNcn());
        log.debug("searchVO.getCtn():"+searchVO.getCtn());
        log.debug("searchVO.getCustId():"+searchVO.getCustId());
        log.debug("searchVO.getModelName():"+searchVO.getModelName());
        */

        return rtnObj;
    }
}
