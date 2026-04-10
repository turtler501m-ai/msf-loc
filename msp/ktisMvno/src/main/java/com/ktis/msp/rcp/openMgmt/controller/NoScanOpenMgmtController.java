package com.ktis.msp.rcp.openMgmt.controller;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class NoScanOpenMgmtController extends BaseController {

    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** rcpMgmtService */
    @Autowired
    private RcpMgmtService rcpMgmtService;

    @Autowired
    private MenuAuthService menuAuthService;

    /**
     * @Description 무서식지 개통 관리 진입 페이지
     * @Param : pRequest
     * @Param : pResponse
     * @Param : model
     * @Param : pRequestParamMap
     * @Return : String
     * @CreateDate : 2024.05.07
     */
    @RequestMapping(value = "/rcp/openMgmt/getNoScanOpenMgmt.do", method = {POST, GET})
    public ModelAndView getNoScanOpenMgmt(HttpServletRequest pRequest, HttpServletResponse pResponse, @ModelAttribute("searchVO") RcpListVO searchVO, @RequestParam Map<String, Object> pRequestParamMap) {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("무서식지 개통 관리 진입 페이지 START"));
        logger.info(generateLogMsg("================================================================="));

        ModelAndView modelAndView = new ModelAndView();
        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            // 사전승낙제 대상 대리점
            String knoteYn = rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId());
            if("10".equals(loginInfo.getUserOrgnTypeCd())){
                knoteYn = "Y";
            } else { // 본사 권한자가 아니면서 사전승낙제 대상 대리점이 아닌경우 접근 불가
                if (!"Y".equals(knoteYn)) throw new MvnoRunException(-1, "");
            }
            // KosId 존재 여부 확인
            String kosId = null;
            kosId = rcpMgmtService.getKosId(loginInfo.getUserId());
            if(kosId == null){
                throw new MvnoRunException(-1, "");
            }


            modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
            modelAndView.getModelMap().addAttribute("kosId",kosId);
            modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

            //----------------------------------------------------------------
            // jsp 지정
            //----------------------------------------------------------------
            modelAndView.setViewName("/rcp/openMgmt/noScanOpenMgmt");

            return modelAndView;
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

}