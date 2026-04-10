package com.ktis.msp.org.workmgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.workmgmt.service.DocRcvService;
import com.ktis.msp.org.workmgmt.vo.DocRcvDetailVO;
import com.ktis.msp.org.workmgmt.vo.DocRcvRequestVO;
import com.ktis.msp.org.workmgmt.vo.DocRcvUrlOtpVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class DocRcvController extends BaseController {
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    MenuAuthService menuAuthService;

    @Autowired
    MaskingService maskingService;

    @Autowired
    private DocRcvService docRcvService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RcpMgmtService rcpMgmtService;

    /**
     * @Description : 서류접수 문자발송 팝업 화면
     */
    @RequestMapping(value = "/org/workMgmt/docRcvRequestView.do", method={POST, GET})
    public String docRcvRequestView(HttpServletRequest pRequest,
                                HttpServletResponse pResponse,
                                ModelMap model,
                                DocRcvRequestVO docRcvRequest) {
        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            if (StringUtils.isBlank(docRcvRequest.getResNo())) {
                throw new MvnoServiceException("요청이 유효하지 않습니다.", null);
            }

            String mobileNo = docRcvService.getMobileNoByResNo(docRcvRequest);
            String maskedMobileNo = maskingService.getMaskString(mobileNo, "PHONE", docRcvRequest.getSessionUserId());

            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
            model.addAttribute("maskedMobileNo", maskedMobileNo);
            model.addAttribute("resNo", docRcvRequest.getResNo());
            model.addAttribute("viewType", docRcvRequest.getViewType());

            return "/org/workmgmt/docRcvRequestView";
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    @RequestMapping("/org/workMgmt/requestDocRcv.json")
    public String requestDocRcv(HttpServletRequest pRequest,
                                HttpServletResponse pResponse,
                                DocRcvRequestVO docRcvRequest,
                                ModelMap model)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if(!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            if (StringUtils.isBlank(docRcvRequest.getMobileNo())) {
                docRcvRequest.setMobileNo(docRcvService.getMobileNoByResNo(docRcvRequest));
            }

            docRcvService.requestDocRcv(docRcvRequest);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        }catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";

    }

    /**
     * @Description : 서류요청 접수현황 화면
     */
    @RequestMapping(value = "/org/workMgmt/docRcvList.do", method={POST, GET})
    public String docRcvList(HttpServletRequest pRequest,
                                    HttpServletResponse pResponse,
                                    ModelMap model,
                                    @RequestParam Map<String, Object> pRequestParamMap) {
        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 권한
            if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

            return "/org/workmgmt/docRcvList";
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /**
     * @Description : 서류요청 접수현황 목록 조회
     */
    @RequestMapping(value = "/org/workMgmt/getDocRcvList.json")
    public String getDocRcvList(HttpServletRequest pRequest,
                             HttpServletResponse pResponse,
                             ModelMap model,
                             DocRcvRequestVO docRcvRequest) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<EgovMap> resultList = docRcvService.getDocRcvList(docRcvRequest);
            resultMap = makeResultMultiRow(docRcvRequest, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002302", "서류요청 접수현황")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description 서류요청 접수현황 목록 엑셀 다운도르
     * @Param : searchVO
     * @Param : model
     * @Param : pRequest
     * @Param : pResponse
     * @Param : pRequestParamMap
     * @Return : String
     * @CreateDate : 2024.05.29
     */
    @RequestMapping(value = "/org/workMgmt/getDocRcvListByExcel.json")
    @ResponseBody
    public String getDocRcvListByExcel(DocRcvRequestVO docRcvRequest,
                                       ModelMap model,
                                       HttpServletRequest pRequest,
                                       HttpServletResponse pResponse,
                                       @RequestParam Map<String, Object> pRequestParamMap) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        try {

            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 화면인 경우
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 엑셀 데이터 조회
            List<EgovMap> resultList = docRcvService.getDocRcvListByExcel(docRcvRequest);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo + "서류요청접수현황_";    // 파일명
            String strSheetname = "서류요청 접수현황";                // 시트명

            // 엑셀 첫줄
            String[] strHead = {"요청번호", "업무명", "업무유형", "요청일자", "URL 상태",
                    "고객명", "문자수신번호", "접수상태", "접수일", "처리상태",
                    "처리일", "접수자"};	// 12
            String[] strValue = {"docRcvId","workTmplNm","workNm","requestDay","urlStatusNm",
                    "cstmrName","mobileNo","rcvYnNm","rcvDt","procStatusNm",
                    "procDt","cretNm"}; // 12

            // 엑셀 컬럼 사이즈
            int[] intWidth = {6000, 15000, 4000, 4000, 4000,
                    4000, 5000, 4000, 4000, 5000,
                    4000, 4000}; // 12

            int[] intLen = {0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0}; // 12

            String strFileName = "";

            try {
                strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
                        strValue, pRequest, pResponse, intLen);
            } catch (Exception e) {
                throw new MvnoErrorException(e);
            }

            file = new File(strFileName);

            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());

            in = new FileInputStream(file);
            out = pResponse.getOutputStream();

            int temp = -1;
            while ((temp = in.read()) != -1) {
                out.write(temp);
            }

            // 파일다운로드사유 로그 STRAT
            if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
                String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

                if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                    ipAddr = pRequest.getHeader("REMOTE_ADDR");

                if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
                    ipAddr = pRequest.getRemoteAddr();

                pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
                pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
                pRequestParamMap.put("DUTY_NM", "ORG"); // 업무명 (기준)
                pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
                pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
                pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
                pRequestParamMap.put("DATA_CNT", 0); // 자료건수

                // CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
                fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
            }
            // 파일다운로드사유 로그 END

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

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /**
     * @Description : 서류접수 문자발송 팝업 화면
     */
    @RequestMapping(value = "/org/workMgmt/docRcvVerifyView.do", method={POST, GET})
    public String docRcvVerifyView(HttpServletRequest pRequest,
                                    HttpServletResponse pResponse,
                                    ModelMap model,
                                    DocRcvRequestVO docRcvRequest) {
        try {
            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            String scanSearchUrl =  propertiesService.getString("scan.search.url");
            String scanDownloadUrl =  propertiesService.getString("scan.download.url");
            String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

            Map<String, Object> resultMap = new HashMap<String, Object>();
            List<?> macInfoList = loginService.selectMacChkInfo();
            for (int i=0;i<macInfoList.size();i++){
                Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
                resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
            }
            String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
            String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
            if ("D".equals(serverUrl) || "S".equals(serverUrl)) {
                serverUrl = "S";
            }

            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
            model.addAttribute("docRcvId", docRcvRequest.getDocRcvId());

            model.addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
            model.addAttribute("scanSearchUrl", scanSearchUrl);
            model.addAttribute("agentVersion", agentVersion);
            model.addAttribute("serverUrl", serverUrl);
            model.addAttribute("scanDownloadUrl", scanDownloadUrl);
            model.addAttribute("maskingYn", maskingYn);

            return "/org/workmgmt/docRcvVerifyView";
        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /**
     * @Description : 서류요청 접수현황 상세 조회
     */
    @RequestMapping(value = "/org/workMgmt/getDocRcvDetail.json")
    public String getDocRcvDetail(HttpServletRequest pRequest,
                                HttpServletResponse pResponse,
                                ModelMap model,
                                DocRcvRequestVO docRcvRequest) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        DocRcvDetailVO docRcvDetailVO = null;
        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            docRcvDetailVO = docRcvService.getDocRcvDetail(docRcvRequest.getDocRcvId());
            docRcvDetailVO.setMobileNo(maskingService.getMaskString(docRcvDetailVO.getMobileNo(), "PHONE", docRcvRequest.getSessionUserId()));
            docRcvDetailVO.setCstmrName(maskingService.getMaskString(docRcvDetailVO.getCstmrName(), "NAME", docRcvRequest.getSessionUserId()));

        } catch (Exception e) {
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002302", "서류요청 접수현황")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", docRcvDetailVO);
        return "jsonView";
    }

    @RequestMapping("/org/workMgmt/resendNewOtp.json")
    public String resendNewOtp(HttpServletRequest pRequest,
                               HttpServletResponse pResponse,
                               DocRcvRequestVO docRcvRequest,
                               ModelMap model)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if(!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            DocRcvUrlOtpVO docRcvUrlOtpVO = docRcvService.resendNewOtp(docRcvRequest.getDocRcvId());

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            resultMap.put("data", docRcvUrlOtpVO);

        }catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", e.getMessage());
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";

    }

    @RequestMapping("/org/workMgmt/verifyDocRcvItems.json")
    public String verifyDocRcvItems(HttpServletRequest pRequest,
                                HttpServletResponse pResponse,
                                @RequestBody String requestString,
                                ModelMap model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            DocRcvRequestVO docRcvRequest = new ObjectMapper().readValue(requestString, DocRcvRequestVO.class);

            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            docRcvService.verifyDocRcvItems(docRcvRequest);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "");

        } catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", e.getMessage());
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";

    }

    @RequestMapping("/org/workMgmt/retryDocRcvItems.json")
    public String retryDocRcvItems(HttpServletRequest pRequest,
                                    HttpServletResponse pResponse,
                                    @RequestBody String requestString,
                                    ModelMap model)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            DocRcvRequestVO docRcvRequest = new ObjectMapper().readValue(requestString, DocRcvRequestVO.class);

            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if(!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            docRcvService.retryDocRcvItems(docRcvRequest);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        }catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", e.getMessage());
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";

    }

    @RequestMapping("/org/workMgmt/completeDocRcv.json")
    public String completeDocRcv(HttpServletRequest pRequest,
                                   HttpServletResponse pResponse,
                                   @RequestBody String requestString,
                                   ModelMap model)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            DocRcvRequestVO docRcvRequest = new ObjectMapper().readValue(requestString, DocRcvRequestVO.class);

            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if(!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            docRcvService.completeDocRcv(docRcvRequest);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

        }catch (Exception e) {
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", e.getMessage());
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);
        return "jsonView";

    }

    /**
     * @Description : 서류요청 접수현황 목록 조회
     */
    @RequestMapping(value = "/org/workMgmt/getDocRcvItemFileListByItemId.json")
    public String getDocRcvItemFileListByItemId(HttpServletRequest pRequest,
                                HttpServletResponse pResponse,
                                ModelMap model,
                                DocRcvRequestVO docRcvRequest) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(docRcvRequest);

            // 본사 권한
            if (!"10".equals(docRcvRequest.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<EgovMap> resultList = docRcvService.getDocRcvItemFileListByItemId(docRcvRequest);
            resultMap = makeResultMultiRow(docRcvRequest, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002302", "서류요청 접수현황")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }
}
