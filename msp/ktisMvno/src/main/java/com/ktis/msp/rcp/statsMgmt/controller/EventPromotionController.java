package com.ktis.msp.rcp.statsMgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.statsMgmt.service.EventPromotionService;
import com.ktis.msp.rcp.statsMgmt.vo.EventPromotionVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class EventPromotionController extends BaseController {

    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private OrgCommonService orgCommonService;

    @Autowired
    private LoginService loginService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private EventPromotionService eventPromotionService;


    /** 게임 참여자 목록 페이지 진입 */
    @RequestMapping(value = "/stats/statsMgmt/eventPromotion.do", method = {POST, GET})
    public String eventPromotion(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @RequestParam Map<String, Object> pRequestParamMap
            , @ModelAttribute("searchVO") EventPromotionVo searchVO
            , ModelMap model) {

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
            model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
            model.addAttribute("srchEndDt", orgCommonService.getToDay());

            return "/rcp/statsMgmt/statsEventPromotion";

        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /**
     * 프로모션명 콤보 목록 조회
     */
    @RequestMapping("/stats/statsMgmt/getPromotionComboList.json")
    public String getPromotionComboList(@ModelAttribute("searchVO") EventPromotionVo searchVO,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam Map<String, Object> paramMap,
                                   ModelMap model)
    {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            List<?> resultList = eventPromotionService.getPromotionComboList();

            // 페이징처리를 위한 부분
            resultMap =  makeResultMultiRow(searchVO, resultList);
        }
        catch(Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, resultMap))
                throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    /**
     * 당첨결과 콤보 목록 조회
     */
    @RequestMapping("/stats/statsMgmt/getPrizeComboList.json")
    public String getPrizeComboList(@ModelAttribute("searchVO") EventPromotionVo searchVO,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam Map<String, Object> paramMap,
                                   ModelMap model)
    {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            List<?> resultList = eventPromotionService.getPrizeComboList(searchVO);

            // 페이징처리를 위한 부분
            resultMap =  makeResultMultiRow(searchVO, resultList);
        }
        catch(Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, resultMap))
                throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
    }

    /**
     * 게임 참여자 목록 조회
     */
    @RequestMapping(value = "/stats/statsMgmt/getEventPromotionList.json")
    public String getAbuseInfoList(@ModelAttribute("searchVO") EventPromotionVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

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

            List<EgovMap> resultList = eventPromotionService.getEventPromotionList(searchVO,pRequestParamMap);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010056", "게임 참여자 목록")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 게임 참여자 목록 엑셀 다운로드 */
    @RequestMapping(value="/stats/statsMgmt/getEventPromotionListExcel.json")
    public String getEventPromotionListExcel(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@RequestParam Map<String, Object> pRequestParamMap
            ,@ModelAttribute("searchVO") EventPromotionVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 엑셀 리스트 조회
            List<?> resultList = eventPromotionService.getEventPromotionListExcel(searchVO, pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "게임참여자목록_";  //파일명
            String strSheetname = "게임참여자목록";                //시트명

            // 엑셀 첫줄
            String [] strHead = {"참여일시","계약번호","프로모션명","당첨결과","본인인증 수단","본인인증(이름)","본인인증(통신사)","휴대폰번호","생년월일","성별",
                                    "개인정보 수집 이용동의","광고성 정보 수신 동의","단말모델명","이동전통신사","신청일자","개통일자","회선상태","최초요금제코드","최초요금제","현재요금제코드",
                                    "현재요금제","유심접점","대리점","판매점"};	// 24개
            String [] strValue = {"participateDate","contractNum","promotionName","prizeName","certType","customerName","certCompany","ctn","birthDate","gender",
                                    "personalInfoCollectYn","marketingYn","modelName","moveCompany","reqInDay","lstComActvDate","subStatus","fstRateCd","fstRateNm","lstRateCd",
                                    "lstRateNm","usimOrgNm","openAgntNm","shopNm"}; // 24개

            // 엑셀 컬럼 사이즈
            int[] intWidth = {7000,5000,7000,7000,5000,5000,5000,5000,5000,5000,
                                8000,8000,5000,5000,5000,5000,5000,5000,5000,5000,
                                7000,7000,7000,8000}; // 24개
            int[] intLen = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; // 24개

            String strFileName = "";
            try {
                strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, pRequest, pResponse, intLen);
            } catch (Exception e) {
                throw new MvnoErrorException(e);
            }

            file = new File(strFileName);

            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());

            in = new FileInputStream(file);
            out = pResponse.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
                out.write(temp);
            }

            // 파일 다운로드 사유 로그
            if(KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))){

                String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

                if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                    ipAddr = pRequest.getHeader("REMOTE_ADDR");

                if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                    ipAddr = pRequest.getRemoteAddr();

                pRequestParamMap.put("FILE_NM"   ,file.getName());                        // 파일명
                pRequestParamMap.put("FILE_ROUT" ,file.getPath());                        // 파일경로
                pRequestParamMap.put("DUTY_NM"   ,"STATS");                                 // 업무명
                pRequestParamMap.put("IP_INFO"   ,ipAddr);                                // IP정보
                pRequestParamMap.put("FILE_SIZE" ,(int) file.length());                   // 파일크기
                pRequestParamMap.put("menuId"    ,pRequest.getParameter("menuId")); // 메뉴ID
                pRequestParamMap.put("DATA_CNT"  ,0);                                     // 자료건수

                fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
            }

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");

        }catch(Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
            resultMap.put("msg", (e.getMessage() == null) ? "다운로드실패" : e.getMessage());

        }finally {
            if(in != null){try{in.close();}catch(Exception e){throw new MvnoErrorException(e);}}
            if(out != null){try{out.close();}catch(Exception e){throw new MvnoErrorException(e);}}
            if(file != null){file.delete();}
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }
}
