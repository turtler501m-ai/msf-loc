package com.ktis.msp.org.csanalyticmgmt.controller;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.cdmgmt.service.CmnCdMgmtService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.csanalyticmgmt.service.AcenCnsltHistService;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenCnsltHistVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
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
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AcenCnsltHistController extends BaseController {

    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Autowired
    private MenuAuthService menuAuthService;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private UserInfoMgmtService userInfoMgmtService;

    @Autowired
    private BtchMgmtService btchMgmtService;

    @Autowired
    private AcenCnsltHistService acenCnsltHistService;

    @Autowired
    private CmnCdMgmtService cmnCdMgmtService;


    /** (A'Cen) 상담이력 페이지 진입 */
    @RequestMapping(value = "/org/csAnalyticMgmt/acenCnsltHist.do", method = {POST, GET})
    public String acenCnsltHist(HttpServletRequest pRequest
                               ,HttpServletResponse pResponse
                               ,@RequestParam Map<String, Object> pRequestParamMap
                               ,@ModelAttribute("searchVO") AcenCnsltHistVO searchVO
                               ,ModelMap model) {

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            model.addAttribute("loginInfo", loginInfo);
            model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

            // 엑셀 다운로드 기간제약 조회 (공통코드)
            String cnsltSrExcelLimit = null;
            String cnsltConSrExcelLimit = null;

            HashMap<String, String> cdParam = new HashMap<String, String>();
            cdParam.put("grpId", "ACEN0013");
            cdParam.put("cdVal", "01");
            cnsltSrExcelLimit = cmnCdMgmtService.getCmnGrpCdMst(cdParam).get("ETC1");

            cdParam.put("cdVal", "02");
            cnsltConSrExcelLimit = cmnCdMgmtService.getCmnGrpCdMst(cdParam).get("ETC1");

            model.addAttribute("cnsltSrExcelLimit", cnsltSrExcelLimit);
            model.addAttribute("cnsltConSrExcelLimit", cnsltConSrExcelLimit);

            return "/org/csanalyticmgmt/acenCnsltHist";

        } catch (Exception e) {
            throw new MvnoRunException(-1, "");
        }
    }

    /** (A'Cen) 상담이력 SR 목록 조회 */
    @RequestMapping(value = "/org/csAnalyticMgmt/getAcenCnsltSrList.json")
    public String getAcenCnsltSrList(HttpServletRequest pRequest
                                    ,HttpServletResponse pResponse
                                    ,@RequestParam Map<String, Object> pRequestParamMap
                                    ,@ModelAttribute("searchVO") AcenCnsltHistVO searchVO
                                    ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 날짜 유효성검사
            if(KtisUtil.isEmpty(searchVO.getCallStrtDt()) || KtisUtil.isEmpty(searchVO.getCallEndDt())){
                throw new MvnoServiceException("통화연도가 누락되었습니다.");
            }

            // 검색필터 유효성 검사
            if(!KtisUtil.isEmpty(searchVO.getSearchCd()) && KtisUtil.isEmpty(searchVO.getSearchVal())){
                throw new MvnoServiceException("검색조건이 누락되었습니다.");
            }

            // SR 목록 조회
            List<EgovMap> acenCnsltSrList = acenCnsltHistService.getAcenCnsltSrList(searchVO, pRequestParamMap);
            resultMap = makeResultMultiRow(pRequestParamMap, acenCnsltSrList);

        }catch (Exception e){
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002204", "A'Cen 상담이력(SR)")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** (A'Cen) 상담이력 상담내용포함 엑셀 다운로드 */
    @RequestMapping("/org/csAnalyticMgmt/getAcenCnsltConSrListExcelDown.json")
    public String getAcenCnsltConSrListExcelDown(@ModelAttribute("searchVO") AcenCnsltHistVO searchVO
                                                ,@RequestParam Map<String, Object> pRequestParamMap
                                                ,HttpServletRequest request
                                                ,HttpServletResponse response
                                                ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            // 본사 화면
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 날짜 유효성검사
            if(KtisUtil.isEmpty(searchVO.getCallStrtDt()) || KtisUtil.isEmpty(searchVO.getCallEndDt())){
                throw new MvnoServiceException("통화연도가 누락되었습니다.");
            }

            // 검색필터 유효성 검사
            if(!KtisUtil.isEmpty(searchVO.getSearchCd()) && KtisUtil.isEmpty(searchVO.getSearchVal())){
                throw new MvnoServiceException("검색조건이 누락되었습니다.");
            }

            // 엑셀다운로드 HISTORY 저장
            Map<String, Object> excelMap = new HashMap<String, Object>();
            int exclDnldId = fileDownService.getSqCmnExclDnldHst();
            excelMap.put("EXCL_DNLD_ID", exclDnldId);
            excelMap.put("MENU_ID", request.getParameter("menuId"));
            excelMap.put("USR_ID", loginInfo.getUserId());
            excelMap.put("USR_NM", loginInfo.getUserName());
            excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
            excelMap.put("DUTY_NM", "ORG");
            excelMap.put("MENU_NM", "A'Cen상담이력(SR)");
            String searchVal = "통화연도:" + searchVO.getCallStrtDt() + "~" + searchVO.getCallEndDt() +
                               "|검색구분:[" + searchVO.getSearchCd() + "," + searchVO.getSearchVal() + "]" +
                               "|통화유형:" + searchVO.getSearchTalkType() +
                               "|상담사ID:" + searchVO.getSearchCnsltId() +
                               "|SR(대):" + searchVO.getSearchSrFir() +
                               "|SR(중):" + searchVO.getSearchSrSec() +
                               "|SR(소):" + searchVO.getSearchSrThi() +
                               "|SR(세):" + searchVO.getSearchSrDtl();

            if(searchVal.length() > 500) {
              searchVal = searchVal.substring(0, 500);
            }

            excelMap.put("SEARCH_VAL", searchVal);
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            BatchJobVO vo = new BatchJobVO();
            vo.setExecTypeCd("REQ");
            vo.setBatchJobId("BATCH00248");
            vo.setSessionUserId(loginInfo.getUserId());
            vo.setExclDnldId(exclDnldId);

            // admin 권한 또는 DEV 권한 없으면 exclDnldYn +1 처리
            if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
              && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())){
              vo.setExclDnldYn(vo.getExclDnldYn() + 1);
            }

            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
              ipAddr = request.getHeader("REMOTE_ADDR");
            }

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
              ipAddr = request.getRemoteAddr();
            }

            vo.setExecParam("{\"callStrtDt\":" + "\"" + searchVO.getCallStrtDt() + "\""
                          + ",\"callEndDt\":" + "\"" + searchVO.getCallEndDt() + "\""
                          + ",\"searchCd\":" + "\"" + searchVO.getSearchCd() + "\""
                          + ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\""
                          + ",\"searchTalkType\":" + "\"" + searchVO.getSearchTalkType() + "\""
                          + ",\"searchCnsltId\":" + "\"" + searchVO.getSearchCnsltId() + "\""
                          + ",\"searchSrFir\":" + "\"" + searchVO.getSearchSrFir() + "\""
                          + ",\"searchSrSec\":" + "\"" + searchVO.getSearchSrSec() + "\""
                          + ",\"searchSrThi\":" + "\"" + searchVO.getSearchSrThi() + "\""
                          + ",\"searchSrDtl\":" + "\"" + searchVO.getSearchSrDtl() + "\""
                          + ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
                          + ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
                          + ",\"ipAddr\":" + "\"" + ipAddr + "\""
                          + ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\""
                          + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");

            btchMgmtService.insertBatchRequest(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");

        }catch (Exception e) {
          resultMap.clear();
          if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2002204", "A'Cen 상담이력(SR)")){
            throw new MvnoErrorException(e);
          }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** (A'Cen) 상담이력 엑셀 다운로드 */
    @RequestMapping("/org/csAnalyticMgmt/getAcenCnsltSrListExcelDown.json")
    public String getAcenCnsltSrListExcelDown(@ModelAttribute("searchVO") AcenCnsltHistVO searchVO
                                             ,@RequestParam Map<String, Object> pRequestParamMap
                                             ,HttpServletRequest request
                                             ,HttpServletResponse response
                                             ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            // 본사 화면
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 날짜 유효성검사
            if(KtisUtil.isEmpty(searchVO.getCallStrtDt()) || KtisUtil.isEmpty(searchVO.getCallEndDt())){
                throw new MvnoServiceException("통화연도가 누락되었습니다.");
            }

            // 검색필터 유효성 검사
            if(!KtisUtil.isEmpty(searchVO.getSearchCd()) && KtisUtil.isEmpty(searchVO.getSearchVal())){
                throw new MvnoServiceException("검색조건이 누락되었습니다.");
            }

            // 엑셀다운로드 HISTORY 저장
            Map<String, Object> excelMap = new HashMap<String, Object>();
            int exclDnldId = fileDownService.getSqCmnExclDnldHst();
            excelMap.put("EXCL_DNLD_ID", exclDnldId);
            excelMap.put("MENU_ID", request.getParameter("menuId"));
            excelMap.put("USR_ID", loginInfo.getUserId());
            excelMap.put("USR_NM", loginInfo.getUserName());
            excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
            excelMap.put("DUTY_NM", "ORG");
            excelMap.put("MENU_NM", "A'Cen상담이력(SR)");
            String searchVal = "통화연도:" + searchVO.getCallStrtDt() + "~" + searchVO.getCallEndDt() +
                               "|검색구분:[" + searchVO.getSearchCd() + "," + searchVO.getSearchVal() + "]" +
                               "|통화유형:" + searchVO.getSearchTalkType() +
                               "|상담사ID:" + searchVO.getSearchCnsltId() +
                               "|SR(대):" + searchVO.getSearchSrFir() +
                               "|SR(중):" + searchVO.getSearchSrSec() +
                               "|SR(소):" + searchVO.getSearchSrThi() +
                               "|SR(세):" + searchVO.getSearchSrDtl();

            if(searchVal.length() > 500) {
              searchVal = searchVal.substring(0, 500);
            }

            excelMap.put("SEARCH_VAL", searchVal);
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            BatchJobVO vo = new BatchJobVO();
            vo.setExecTypeCd("REQ");
            vo.setBatchJobId("BATCH00249");
            vo.setSessionUserId(loginInfo.getUserId());
            vo.setExclDnldId(exclDnldId);

            // admin 권한 또는 DEV 권한 없으면 exclDnldYn +1 처리
            if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
              && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())){
                vo.setExclDnldYn(vo.getExclDnldYn() + 1);
            }

            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
              ipAddr = request.getHeader("REMOTE_ADDR");
            }

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
              ipAddr = request.getRemoteAddr();
            }

            vo.setExecParam("{\"callStrtDt\":" + "\"" + searchVO.getCallStrtDt() + "\""
                          + ",\"callEndDt\":" + "\"" + searchVO.getCallEndDt() + "\""
                          + ",\"searchCd\":" + "\"" + searchVO.getSearchCd() + "\""
                          + ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\""
                          + ",\"searchTalkType\":" + "\"" + searchVO.getSearchTalkType() + "\""
                          + ",\"searchCnsltId\":" + "\"" + searchVO.getSearchCnsltId() + "\""
                          + ",\"searchSrFir\":" + "\"" + searchVO.getSearchSrFir() + "\""
                          + ",\"searchSrSec\":" + "\"" + searchVO.getSearchSrSec() + "\""
                          + ",\"searchSrThi\":" + "\"" + searchVO.getSearchSrThi() + "\""
                          + ",\"searchSrDtl\":" + "\"" + searchVO.getSearchSrDtl() + "\""
                          + ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
                          + ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
                          + ",\"ipAddr\":" + "\"" + ipAddr + "\""
                          + ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\""
                          + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");

            btchMgmtService.insertBatchRequest(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");

        } catch (Exception e) {
            resultMap.clear();
            if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2002204", "A'Cen 상담이력(SR)")){
              throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

    /** (A'Cen) 상담이력 SR 월 통계 조회 */
    @RequestMapping(value = "/org/csAnalyticMgmt/getAcenCnsltSrStatsList.json")
    public String getAcenCnsltSrStatsList(HttpServletRequest pRequest
                                         ,HttpServletResponse pResponse
                                         ,@RequestParam Map<String, Object> pRequestParamMap
                                         ,@ModelAttribute("searchVO") AcenCnsltHistVO searchVO
                                         ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            // 본사 화면
            if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 날짜 유효성검사
            if(KtisUtil.isEmpty(searchVO.getStrtDttm()) || KtisUtil.isEmpty(searchVO.getEndDttm())){
                throw new MvnoServiceException("조회일이 누락되었습니다.");
            }

            if(KtisUtil.isEmpty(searchVO.getStrtDt()) || KtisUtil.isEmpty(searchVO.getEndDt())){
                throw new MvnoServiceException("조회월이 누락되었습니다.");
            }

            // SR 월 통계 조회
            List<EgovMap> acenCnsltstatsList = acenCnsltHistService.getAcenCnsltSrStatsList(searchVO);
            resultMap = makeResultMultiRow(pRequestParamMap, acenCnsltstatsList);

        }catch (Exception e){
            resultMap.clear();
            if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002204", "A'Cen 상담이력(SR)")) {
                throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }
    
    /** (A'Cen) 상담이력 SR 월 통계 엑셀 다운로드 */
    @RequestMapping("/org/csAnalyticMgmt/getAcenCnsltSrStatsListExcelDown.json")
    public String getAcenCnsltSrStatsListExcelDown(@ModelAttribute("searchVO") AcenCnsltHistVO searchVO
                                                  ,@RequestParam Map<String, Object> pRequestParamMap
                                                  ,HttpServletRequest request
                                                  ,HttpServletResponse response
                                                  ,ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            // Login check
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToVo(searchVO);

            // 본사 화면
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            // 날짜 유효성검사
            if(KtisUtil.isEmpty(searchVO.getStrtDttm()) || KtisUtil.isEmpty(searchVO.getEndDttm())){
                throw new MvnoServiceException("조회일이 누락되었습니다.");
            }

            if(KtisUtil.isEmpty(searchVO.getStrtDt()) || KtisUtil.isEmpty(searchVO.getEndDt())){
                throw new MvnoServiceException("조회월이 누락되었습니다.");
            }

            // 엑셀다운로드 HISTORY 저장
            Map<String, Object> excelMap = new HashMap<String, Object>();
            int exclDnldId = fileDownService.getSqCmnExclDnldHst();
            excelMap.put("EXCL_DNLD_ID", exclDnldId);
            excelMap.put("MENU_ID", request.getParameter("menuId"));
            excelMap.put("USR_ID", loginInfo.getUserId());
            excelMap.put("USR_NM", loginInfo.getUserName());
            excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
            excelMap.put("DUTY_NM", "ORG");
            excelMap.put("MENU_NM", "A'Cen상담이력(SR)");
            String searchVal = "조회일:" + searchVO.getStrtDttm() + "~" + searchVO.getEndDttm() +
                               "|조회월:" + searchVO.getStrtDt() + "~" + searchVO.getEndDt() +
                               "|SR(대):" + searchVO.getSearchSrFir() +
                               "|SR(중):" + searchVO.getSearchSrSec() +
                               "|SR(소):" + searchVO.getSearchSrThi() +
                               "|SR(세):" + searchVO.getSearchSrDtl();

            if(searchVal.length() > 500) {
              searchVal = searchVal.substring(0, 500);
            }

            excelMap.put("SEARCH_VAL", searchVal);
            fileDownService.insertCmnExclDnldHst(excelMap);
            excelMap.clear();

            BatchJobVO vo = new BatchJobVO();
            vo.setExecTypeCd("REQ");
            vo.setBatchJobId("BATCH00250");
            vo.setSessionUserId(loginInfo.getUserId());
            vo.setExclDnldId(exclDnldId);

            // admin 권한 또는 DEV 권한 없으면 exclDnldYn +1 처리
            if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
              && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())){
                vo.setExclDnldYn(vo.getExclDnldYn() + 1);
            }

            String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
                ipAddr = request.getHeader("REMOTE_ADDR");
            }

            if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
                ipAddr = request.getRemoteAddr();
            }

            vo.setExecParam("{\"strtDttm\":" + "\"" + searchVO.getStrtDttm() + "\""
                          + ",\"endDttm\":" + "\"" + searchVO.getEndDttm() + "\""
                          + ",\"strtDt\":" + "\"" + searchVO.getStrtDt() + "\""
                          + ",\"endDt\":" + "\"" + searchVO.getEndDt() + "\""
                          + ",\"searchSrFir\":" + "\"" + searchVO.getSearchSrFir() + "\""
                          + ",\"searchSrSec\":" + "\"" + searchVO.getSearchSrSec() + "\""
                          + ",\"searchSrThi\":" + "\"" + searchVO.getSearchSrThi() + "\""
                          + ",\"searchSrDtl\":" + "\"" + searchVO.getSearchSrDtl() + "\""
                          + ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
                          + ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
                          + ",\"ipAddr\":" + "\"" + ipAddr + "\""
                          + ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\""
                          + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");

            btchMgmtService.insertBatchRequest(vo);

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
            resultMap.put("msg", "다운로드성공");

        } catch (Exception e) {
            resultMap.clear();
            if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2002204", "A'Cen 상담이력(SR)")){
              throw new MvnoErrorException(e);
            }
        }

        model.addAttribute("result", resultMap);
        return "jsonView";
    }

}
