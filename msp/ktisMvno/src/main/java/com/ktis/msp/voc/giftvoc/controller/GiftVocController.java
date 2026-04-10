package com.ktis.msp.voc.giftvoc.controller;

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
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListExVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.util.ExcelFilesUploadUtil;
import com.ktis.msp.voc.giftvoc.service.GiftVocService;
import com.ktis.msp.voc.giftvoc.vo.GiftPayStatVo;
import com.ktis.msp.voc.giftvoc.vo.GiftVocVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class GiftVocController extends BaseController {

    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private OrgCommonService orgCommonService;

    @Autowired
    private RcpMgmtService rcpMgmtService;

    @Autowired
    private GiftVocService giftVocService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private BtchMgmtService btchMgmtService;

    @Autowired
    private UserInfoMgmtService userInfoMgmtService;

    /** 사은품 VOC 화면 */
    @RequestMapping(value = "/voc/giftvoc/giftVoc.do", method = {POST, GET})
    public String giftVoc(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @RequestParam Map<String, Object> pRequestParamMap
            , @ModelAttribute("searchVO") GiftVocVo searchVO
            , ModelMap model) {

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
            String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }
            String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
            model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
            model.addAttribute("srchEndDt", orgCommonService.getToDay());
            model.addAttribute("maskingYn", maskingYn);

            return "/voc/giftvoc/giftVoc";

        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /** 사은품 VOC 목록 조회 */
    @RequestMapping(value = "/voc/giftvoc/giftVocList.json", method = {POST, GET})
    public String giftVocList(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@RequestParam Map<String, Object> pRequestParamMap
            ,@ModelAttribute("searchVO") GiftVocVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
            String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 사은품 VOC 목록 조회
            searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
            List<?> resultList = giftVocService.getGiftVocList(searchVO, pRequestParamMap);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001080", "사은품 VOC 목록")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 VOC 상세 조회 */
    @RequestMapping(value = "/voc/giftvoc/giftVocDtl.json", method = {POST, GET})
    public String giftVocDtl(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@RequestParam Map<String, Object> pRequestParamMap
            ,@ModelAttribute("searchVO") GiftVocVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
            String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 사은품 VOC 목록 조회
            searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
            GiftVocVo result = giftVocService.getGiftVocDtl(searchVO, pRequestParamMap);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("giftVocVo", result);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001080", "사은품 VOC 상세 조회")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 VOC 수정 */
    @RequestMapping(value = "/voc/giftvoc/updateGiftVoc.json", method = {POST, GET})
    public String updateGiftVoc(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@ModelAttribute("searchVO") GiftVocVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            String orgnTypeCd = searchVO.getSessionUserOrgnTypeCd();
            String orgnLvlCd = searchVO.getSessionUserOrgnLvlCd();

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            searchVO.setRvisnId(loginInfo.getUserId());
            searchVO.setMaskingYn(loginService.getUsrMskYn(loginInfo.getUserId()));

            // VOC 내용 수정
            giftVocService.updateGiftVoc(searchVO);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1001080", "사은품 VOC 수정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 VOC 답변 수정 */
    @RequestMapping(value = "/voc/giftvoc/updateGiftVocAns.json", method = {POST, GET})
    public String updateGiftVocAns(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@ModelAttribute("searchVO") GiftVocVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            String orgnTypeCd = searchVO.getSessionUserOrgnTypeCd();
            String orgnLvlCd = searchVO.getSessionUserOrgnLvlCd();

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            searchVO.setRvisnId(loginInfo.getUserId());
            searchVO.setAnsProcId(loginInfo.getUserId());

            // VOC 내용 답변 수정
            giftVocService.updateGiftVocAns(searchVO);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1001080", "사은품 VOC 답변 수정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 고객정보 가져오기 */
    @RequestMapping(value = "/voc/giftvoc/getCustInfo.json", method = {POST, GET})
    public String getCustInfo(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@RequestParam Map<String, Object> pRequestParamMap
            ,@ModelAttribute("searchVO") GiftVocVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
            String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 사은품 VOC 목록 조회
            searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
            int vocCnt = giftVocService.dupChkGiftVoc(searchVO, pRequestParamMap);
            GiftVocVo custInfo = giftVocService.getCustInfo(searchVO, pRequestParamMap);
            resultMap.put("vocCnt", vocCnt);
            resultMap.put("custInfo", custInfo);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001080", "사은품 VOC 상세 조회")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 VOC 등록 */
    @RequestMapping(value = "/voc/giftvoc/insertGiftVoc.json", method = {POST, GET})
    public String insertGiftVoc(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@ModelAttribute("searchVO") GiftVocVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(searchVO);

            String orgnTypeCd = searchVO.getSessionUserOrgnTypeCd();
            String orgnLvlCd = searchVO.getSessionUserOrgnLvlCd();

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            searchVO.setRegstId(loginInfo.getUserId());

            // 사은품 VOC 등록
            giftVocService.insertGiftVoc(searchVO);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "VOC1001080", "사은품 VOC 답변 수정")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 VOC 엑셀 자료생성 */
    @RequestMapping("/voc/giftvoc/getGiftVocListExcelDownload.json")
    public String getGiftVocListExcelDownload(@ModelAttribute("searchVO") GiftVocVo searchVO,
                                               Model model,
                                               @RequestParam Map<String, Object> pRequestParamMap,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사은품 VOC 자료생성 START"));
        logger.info(generateLogMsg("Return Vo [GiftVocVo] = " + searchVO.toString()));
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
            excelMap.put("MENU_NM","사은품 VOC");
            String searchVal = "접수일자:"+searchVO.getSrchStrtDt()+"~"+searchVO.getSrchEndDt()+
                    "|처리일자:"+searchVO.getProcStrtDt()+"~"+searchVO.getProcEndDt()+
                    "|지급예정월:"+searchVO.getPlanStrtDt()+"~"+searchVO.getPlanEndDt()+
                    "|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"+
                    "|담당부서:"+searchVO.getVocMngOrgn()+
                    "|답변상태:"+searchVO.getAnsStat()+
                    "|VOC구분:"+searchVO.getVocType()
                    ;
            if(searchVal.length() > 500) {
                searchVal = searchVal.substring(0, 500);
            }
            excelMap.put("SEARCH_VAL",searchVal);
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            BatchJobVO vo = new BatchJobVO();

            vo.setExecTypeCd("REQ");
            vo.setBatchJobId("BATCH00259");
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
                    + ",\"procStrtDt\":" + "\"" + searchVO.getProcStrtDt() + "\""
                    + ",\"procEndDt\":" + "\"" + searchVO.getProcEndDt() + "\""
                    + ",\"planStrtDt\":" + "\"" + searchVO.getPlanStrtDt() + "\""
                    + ",\"planEndDt\":" + "\"" + searchVO.getPlanEndDt() + "\""
                    + ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
                    + ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
                    + ",\"vocMngOrgn\":" + "\"" + searchVO.getVocMngOrgn() + "\""
                    + ",\"ansStat\":" + "\"" + searchVO.getAnsStat() + "\""
                    + ",\"vocType\":" + "\"" + searchVO.getVocType() + "\""
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


    /** 사은품 지급 리스트 화면 */
    @RequestMapping(value = "/voc/giftvoc/giftPayStat.do", method = {POST, GET})
    public String giftPayStat(HttpServletRequest pRequest
            , HttpServletResponse pResponse
            , @RequestParam Map<String, Object> pRequestParamMap
            , @ModelAttribute("searchVO") GiftPayStatVo searchVO
            , ModelMap model) {

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사, 대리점 화면
            String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
            String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
            model.addAttribute("srchStrtDt", orgCommonService.getLastMonth());
            model.addAttribute("srchEndDt", orgCommonService.getToMonth());

            return "/voc/giftvoc/giftPayStat";

        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /** 사은품 지급 리스트 목록 조회 */
    @RequestMapping(value = "/voc/giftvoc/giftPayStatList.json", method = {POST, GET})
    public String giftPayStatList(HttpServletRequest pRequest
            ,HttpServletResponse pResponse
            ,@RequestParam Map<String, Object> pRequestParamMap
            ,@ModelAttribute("searchVO") GiftPayStatVo searchVO
            ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            String orgnTypeCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD");
            String orgnLvlCd = (String) pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD");

            // 본사 권한 체크
            if(!"10".equals(orgnTypeCd)){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 사은품 지급 리스트 목록 조회
            searchVO.setSessionUserOrgnTypeCd(orgnTypeCd);
            List<?> resultList = giftVocService.giftPayStatList(searchVO, pRequestParamMap);
            resultMap = makeResultMultiRow(pRequestParamMap, resultList);

        } catch (Exception e) {
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "VOC1001080", "사은품 지급 리스트 목록")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 지급 리스트 엑셀 업로드 파일 읽기 */
    @RequestMapping(value="/voc/giftvoc/readGiftPayStatExcelUpList.json")
    public String readGiftPayStatExcelUpList(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @ModelAttribute("searchVO") GiftPayStatVo searchVO,
                                          ModelMap model,
                                          @RequestParam Map<String, Object> pReqParamMap){

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            String baseDir = propertiesService.getString("fileUploadBaseDirectory");

            String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();

            String[] arrCell = {
                    "payMon", "contractNum", "custNm", "ctn", "taxRplyYn",
                    "promNm", "giftType", "giftAmt"};

            List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.voc.giftvoc.vo.GiftPayStatVo", sOpenFileName, arrCell);
            resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", e.getMessage());
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** 사은품 지급 리스트 엑셀등록 */
    @RequestMapping(value="/voc/giftvoc/regGiftPayStatExcel.json")
    public String regGiftPayStatExcel(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @ModelAttribute("searchVO") GiftPayStatVo searchVO,
                                   @RequestBody String data,
                                   ModelMap model,
                                   @RequestParam Map<String, Object> pReqParamMap){

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            GiftPayStatVo vo = new ObjectMapper().readValue(data, GiftPayStatVo.class);
            vo.setRegstId(loginInfo.getUserId());

            int resultCnt = giftVocService.regGiftPayStatExcel(vo);
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", resultCnt+"건 등록되었습니다");

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", e.getMessage());
        }
        model.addAttribute("result", resultMap);
        return "jsonView";
    }


    /** 사은품 지급 리스트 엑셀 자료생성 */
    @RequestMapping("/voc/giftvoc/getGiftPayStatExcelDownload.json")
    public String getGiftPayStatExcelDownload(@ModelAttribute("searchVO") GiftPayStatVo searchVO,
                                              Model model,
                                              @RequestParam Map<String, Object> pRequestParamMap,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사은품 지급 리스트 자료생성 START"));
        logger.info(generateLogMsg("Return Vo [GiftPayStatVo] = " + searchVO.toString()));
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
            excelMap.put("MENU_NM","사은품 지급 리스트");
            String searchVal = "지급월:"+searchVO.getSrchStrtDt()+"~"+searchVO.getSrchEndDt()+
                    "|검색구분:["+searchVO.getSearchGbn()+","+searchVO.getSearchName()+"]"+
                    "|사은품종류:"+searchVO.getGiftType()+
                    "|프로모션명:"+searchVO.getPromNm()
                    ;
            if(searchVal.length() > 500) {
                searchVal = searchVal.substring(0, 500);
            }
            excelMap.put("SEARCH_VAL",searchVal);
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            BatchJobVO vo = new BatchJobVO();

            vo.setExecTypeCd("REQ");
            vo.setBatchJobId("BATCH00260");
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
                    + ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
                    + ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
                    + ",\"giftType\":" + "\"" + searchVO.getGiftType() + "\""
                    + ",\"promNm\":" + "\"" + searchVO.getPromNm() + "\""
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
