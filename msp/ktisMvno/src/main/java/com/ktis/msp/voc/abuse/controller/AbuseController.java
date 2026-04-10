package com.ktis.msp.voc.abuse.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.voc.abuse.service.AbuseService;
import com.ktis.msp.voc.abuse.vo.AbuseVO;
import com.ktis.msp.voc.reuserip.vo.ReuseRipMgmtVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
public class AbuseController extends BaseController {

    /** propertiesService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private AbuseService abuseService;

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private FileDownService fileDownService;

    /**
     * 부정사용주장 단말설정 화면 진입
     */
    @RequestMapping(value = "/voc/abuse/getAbuseInfo.do")
    public ModelAndView getAbuseInfo(@ModelAttribute("searchVO") ReuseRipMgmtVO vo,
                                        HttpServletRequest request,
                                        HttpServletResponse response,
                                        ModelMap model) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(vo);


            modelAndView.getModelMap().addAttribute("info", vo);
            modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

            modelAndView.setViewName("/voc/abuse/abuseList");

            return modelAndView;
        } catch(Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /**
     * 부정사용주장 단말설정 목록 조회
     */
    @RequestMapping(value = "/voc/abuse/getAbuseInfoList.json")
    public String getAbuseInfoList(@ModelAttribute("searchVO") AbuseVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

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

            List<EgovMap> resultList = abuseService.getAbuseList(searchVO,pRequestParamMap);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1003030", "부정사용주장 단말설정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * 부정사용주장 단말설정 목록 엑셀 다운로드
     */
    @RequestMapping("/voc/abuse/getAbuseInfoListExcelDown.json")
    @ResponseBody
    public String getAbuseInfoListExcelDown(@ModelAttribute("searchVO") AbuseVO searchVO, ModelMap model, HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam Map<String, Object> pRequestParamMap) {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("부정사용주장 단말설정 엑셀 저장 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        try {

            // ----------------------------------------------------------------
            // Login check
            // ----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면인 경우
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(
                        messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
            pRequestParamMap.put("SESSION_USER_MASKING",maskingYn);	//마스킹 여부

            List<?> resultList = abuseService.getAbuseInfoListExcelDown(searchVO, pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");

            String strFilename = serverInfo + "부정사용주장단말설정_";// 파일명
            String strSheetname = "부정사용주장단말설정";// 시트명

            // 엑셀 첫줄
            String[] strHead = {"계약번호","고객명","휴대폰번호","구매유형","업무구분","최초요금제코드","최초요금제","현재요금제코드","현재요금제","상태",
                                "해지사유","모집경로","대리점","개통일자","해지일자","유심접점","최초유심접점","본인인증방식","최초유심일련번호","단말상태",
                                "유심구분","최초ESIM여부","단말모델명","단말일련번호","최초단말모델명","최초단말일련번호","등록사유","등록일시","등록자","수정일시",
                                "수정자"}; //31

            String[] strValue = {"contractNum","subLinkName","subscriberNo","reqBuyTypeNm","operTypeNm","fstRateCd","fstRateNm","lstRateCd","lstRateNm","subStatusNm",
                                "canRsn","onOffTypeNm","openAgntNm","lstComActvDate","canDate","usimOrgNm","fstUsimOrgNm","authType","fstUsimNo","openYnNm",
                                "usimType","fstEsimYn","lstModelNm","lstModelSrlNum","fstModelNm","fstModelSrlNum","reasonNm","regstDttm","regstNm","rvisnDttm",
                                "rvisnNm"}; //31

            // 엑셀 컬럼 사이즈
            int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 10000, 5000, 10000, 5000,
                                5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
                                5000, 5000, 5000, 8000, 5000, 8000, 5000, 5000, 5000, 5000,
                                5000 }; //31

            int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //31

            String strFileName = "";

            try {
                strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
                        strValue, request, response, intLen);
            } catch (Exception e) {
                throw new MvnoErrorException(e);
            }
            file = new File(strFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while ((temp = in.read()) != -1) {
                out.write(temp);
            }

            // =======파일다운로드사유 로그
            // START==========================================================
            if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
                String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

                if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                    ipAddr = request.getHeader("REMOTE_ADDR");

                if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                    ipAddr = request.getRemoteAddr();

                pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
                pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
                pRequestParamMap.put("DUTY_NM", "VOC"); // 업무명
                pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
                pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
                pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
                pRequestParamMap.put("DATA_CNT", 0); // 자료건수

                fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
            }
            // =======파일다운로드사유 로그
            // END==========================================================

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");

        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
            resultMap.put("msg", returnMsg);
            throw new MvnoErrorException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new MvnoErrorException(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    throw new MvnoErrorException(e);
                }
            }
        }
        file.delete();
        // ----------------------------------------------------------------
        // return json
        // ----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * 부정사용주장 단말설정 등록
     */
    @RequestMapping(value = "/voc/abuse/insertAbuseInfo.json")
    public String insertAbuseInfo(@ModelAttribute("searchVO") AbuseVO searchVO, @RequestBody String data, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            AbuseVO vo = new ObjectMapper().readValue(data, AbuseVO.class);
            vo.setSessionUserId(searchVO.getSessionUserId());
            abuseService.insertAbuseInfo(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
        }catch ( Exception e){
            if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "VOC1003030", "부정사용주장 단말설정"))
            {
                throw new MvnoErrorException(e);
            }
        }
        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * 부정사용주장 IMEI 목록 조회
     */
    @RequestMapping(value = "/voc/abuse/getAbuseImeiList.json")
    public String getAbuseImeiList(@ModelAttribute("searchVO") AbuseVO searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<EgovMap> resultList = abuseService.getAbuseImeiList(searchVO);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1003030", "부정사용주장 단말설정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * 부정사용주장 단말설정 수정
     */
    @RequestMapping(value = "/voc/abuse/updateAbuseInfo.json")
    public String updateAbuseInfo(@ModelAttribute("searchVO") AbuseVO searchVO, @RequestBody String data, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            AbuseVO vo = new ObjectMapper().readValue(data, AbuseVO.class);
            vo.setSessionUserId(searchVO.getSessionUserId());
            abuseService.updateAbuseInfo(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
        }catch ( Exception e){
            if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(),
                    messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
                    e.getMessage(), "VOC1003030", "부정사용주장 단말설정"))
            {
                throw new MvnoErrorException(e);
            }
        }
        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * 등록 > CONTRACTNUM으로 계약 정보 조회
     */
    @RequestMapping("/voc/abuse/getContractInfo.json")
    public String getContractInfo(HttpServletRequest request
            , HttpServletResponse response
            , ModelMap model
            , @ModelAttribute("searchVO") AbuseVO vo
            , @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(vo);

            List<?> list = abuseService.getContractInfo(vo);

            resultMap = makeResultMultiRow(vo, list);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, resultMap)) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }
}
