package com.ktis.msp.org.prmtmgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.prmtmgmt.service.EventCodePrmtService;
import com.ktis.msp.org.prmtmgmt.vo.EventCodePrmtVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.voc.abuse.vo.AbuseVO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class EventCodePrmtController extends BaseController {

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private EventCodePrmtService eventCodePrmtService;

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private BtchMgmtService btchMgmtService;

    @Autowired
    private UserInfoMgmtService userInfoMgmtService;

    @Autowired
    private OrgCommonService orgCommonService;


    /**
     * 이벤트코드 이력 관리 화면호출
     */
    @RequestMapping(value="/org/prmtmgmt/getEventCodePrmtView.do")
    public ModelAndView getEventCodePrmtView(HttpServletRequest pRequest,
                                            HttpServletResponse pResponse,
                                            @RequestParam Map<String, Object> commandMap,
                                            ModelMap model) throws EgovBizException {

        logger.debug("**********************************************************");
        logger.debug("* 이벤트코드 이력 관리 화면 : msp/org/prmtmgmt/getEventCodePrmtView *");
        logger.debug("**********************************************************");

        ModelAndView modelAndView = new ModelAndView();

        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);

            //본사화면일경우
            if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new EgovBizException("권한이 없습니다.");
            }

            modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
            modelAndView.getModelMap().addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
            modelAndView.getModelMap().addAttribute("srchEndDt", orgCommonService.getToDay());
            modelAndView.setViewName("/org/prmtmgmt/eventCodePrmtMgmt");

            return modelAndView;

        } catch (Exception e) {
            //logger.debug(e.getMessage());
            throw new MvnoRunException(-1, "");
        }
    }

    /**
     * 이벤트코드 이력 관리 목록 조회
     */
    @RequestMapping(value = "/org/prmtmgmt/getEventCodePrmtList.do")
    public String getEventCodePrmtList(@ModelAttribute("searchVO") EventCodePrmtVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
            pRequestParamMap.put("SESSION_USER_MASKING",maskingYn);	//마스킹 여부

            List<EgovMap> resultList = eventCodePrmtService.getEventCodePrmtList(searchVO,pRequestParamMap);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002910", "이벤트코드 이력관리")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * 이벤트코드 이력관리 목록 엑셀 자료생성
     */
    @RequestMapping("/org/prmtmgmt/getEventCodePrmtListExcelDown.json")
    public String getEventCodePrmtListExcelDown(@ModelAttribute("searchVO") EventCodePrmtVO searchVO, 
                                                ModelMap model, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response,
                                                @RequestParam Map<String, Object> pRequestParamMap) {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("이벤트코드 이력관리 엑셀 저장 START."));
        logger.info(generateLogMsg("Return Vo [EventCodePrmtVO] = " + searchVO.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            //----------------------------------------------------------------
            // 엑셀다운로드 HISTORY 저장
            Map<String, Object> excelMap = new HashMap<String, Object>();
            int exclDnldId = fileDownService.getSqCmnExclDnldHst();
            excelMap.put("EXCL_DNLD_ID", exclDnldId);
            excelMap.put("MENU_ID",request.getParameter("menuId"));
            excelMap.put("USR_ID",loginInfo.getUserId());
            excelMap.put("USR_NM",loginInfo.getUserName());
            excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
            excelMap.put("DUTY_NM","CMN");
            excelMap.put("MENU_NM","이벤트코드 이력관리");
            String searchVal = "지급월:"+searchVO.getSrchStrtDt()+"~"+searchVO.getSrchEndDt()+
                    "|이벤트코드:"+searchVO.getSrchEventCd()
                    ;
            if(searchVal.length() > 500) {
                searchVal = searchVal.substring(0, 500);
            }
            excelMap.put("SEARCH_VAL",searchVal);
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            BatchJobVO vo = new BatchJobVO();

            vo.setExecTypeCd("REQ");
            vo.setBatchJobId("BATCH00261");
            vo.setSessionUserId(loginInfo.getUserId());
            vo.setExclDnldId(exclDnldId);	// 엑셀다운로드ID
            //admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
            if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId()))
                vo.setExclDnldYn(vo.getExclDnldYn()+1);

            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getHeader("REMOTE_ADDR");

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                ipAddr = request.getRemoteAddr();

            vo.setExecParam("{\"srchStrtDt\":" + "\"" + searchVO.getSrchStrtDt() + "\""
                    + ",\"srchEndDt\":" + "\"" + searchVO.getSrchEndDt() + "\""
                    + ",\"srchEventCd\":" + "\"" + searchVO.getSrchEventCd() + "\""
                    + ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
                    + ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
                    + ",\"ipAddr\":" + "\"" + ipAddr + "\""
                    + ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\""
                    + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}"
            );

            btchMgmtService.insertBatchRequest(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "다운로드성공");


        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, resultMap))
                throw new MvnoErrorException(e);
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";
    }
}
