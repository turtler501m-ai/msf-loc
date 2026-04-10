package com.ktis.msp.org.csanalyticmgmt.controller;

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
import com.ktis.msp.org.csanalyticmgmt.service.AcenRcpMgmtService;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenHistVO;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenRcpMgmtVO;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AcenRcpMgmtController extends BaseController {

  protected Logger logger = LogManager.getLogger(getClass());

  @Autowired
  private MenuAuthService menuAuthService;

  @Autowired
  private OrgCommonService orgCommonService;

  @Autowired
  private LoginService loginService;

  @Autowired
  private AcenRcpMgmtService acenRcpMgmtService;

  @Autowired
  private FileDownService fileDownService;

  @Autowired
  private UserInfoMgmtService userInfoMgmtService;

  @Autowired
  private BtchMgmtService btchMgmtService;

  /**
   * @Description A'Cen 자동개통 통계 페이지 진입
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/acenRcpMgmtView.do", method = {POST, GET})
  public ModelAndView acenRcpMgmtView(HttpServletRequest pRequest
                                     ,HttpServletResponse pResponse
                                     ,@RequestParam Map<String, Object> pRequestParamMap) {

    ModelAndView modelAndView = new ModelAndView();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

      modelAndView.getModelMap().addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
      modelAndView.getModelMap().addAttribute("srchEndDt", orgCommonService.getToDay());
      modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
      modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
      modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
      modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);

      modelAndView.setViewName("/org/csanalyticmgmt/acenRcpMgmt");
      return modelAndView;

    } catch (Exception e) {
      throw new MvnoRunException(-1, "");
    }
  }

  /**
   * @Description A'Cen 자동개통 통계 리스트 조회
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/getAcenRcpMgmtList.json")
  public String getAcenRcpMgmtList(HttpServletRequest pRequest
                                  ,HttpServletResponse pResponse
                                  ,@RequestParam Map<String, Object> pRequestParamMap
                                  ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                                  ,ModelMap model) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);
      loginInfo.putSessionToVo(searchVO);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getpSearchGbn())){
        // 검색구분이 없는 경우, 조회기간 필수
        if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())){
          throw new MvnoServiceException("신청일자가 누락되었습니다.");
        }
      }else if(KtisUtil.isEmpty(searchVO.getpSearchName())){
          throw new MvnoServiceException("검색어가 누락되었습니다.");
      }

      // acen 신청 리스트 조회
      List<EgovMap> acenRcpList = acenRcpMgmtService.getAcenRcpMgmtList(searchVO, pRequestParamMap);
      resultMap = makeResultMultiRow(pRequestParamMap, acenRcpList);

    }catch (Exception e){
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }

  /**
   * @Description A'Cen 자동개통 통계 리스트 엑셀 다운로드
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/acenRcpMgmtListExcelDownload.json")
  public String acenRcpMgmtListExcelDownload(HttpServletRequest pRequest
                                            ,HttpServletResponse pResponse
                                            ,@RequestParam Map<String, Object> pRequestParamMap
                                            ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                                            ,ModelMap model){

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getpSearchGbn())){
        // 검색구분이 없는 경우, 조회기간 필수
        if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())){
          throw new MvnoServiceException("신청일자가 누락되었습니다.");
        }
      }else if(KtisUtil.isEmpty(searchVO.getpSearchName())){
        throw new MvnoServiceException("검색어가 누락되었습니다.");
      }

      // 엑셀 다운로드 HISTORY 저장
      Map<String, Object> excelMap = new HashMap<String, Object>();
      int exclDnldId = fileDownService.getSqCmnExclDnldHst();
      excelMap.put("EXCL_DNLD_ID", exclDnldId);
      excelMap.put("MENU_ID", pRequest.getParameter("menuId"));
      excelMap.put("USR_ID", loginInfo.getUserId());
      excelMap.put("USR_NM", loginInfo.getUserName());
      excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
      excelMap.put("DUTY_NM", "ORG");
      excelMap.put("MENU_NM", "A'Cen 자동개통 통계");

      String searchVal = "신청일자:" + searchVO.getSrchStrtDt() + "~" + searchVO.getSrchEndDt() +
                         "|업무구분:" + searchVO.getpOperType() +
                         "|구매유형:" + searchVO.getpReqBuyType() +
                         "|대리점:" + searchVO.getpAgentCode() +
                         "|진행상태:" + searchVO.getpRequestStateCode() +
                         "|신청취소:" + searchVO.getpPstate() +
                         "|시나리오:" + searchVO.getpAcenEvntGrp() +
                         "|성공여부:" + searchVO.getpSuccYn() +
                         "|실패사유:" + searchVO.getpAcenErrCd() +
                         "|검색구분:[" + searchVO.getpSearchGbn() + "," + searchVO.getpSearchName() + "]" +
                         "|모집경로:" + searchVO.getpOnOffType() +
                         "|eSIM여부" + searchVO.getpEsimYn() +
                         "|셀프개통제외" + searchVO.getpSelfYn();

      if(searchVal.length() > 500) {
        searchVal = searchVal.substring(0, 500);
      }

      excelMap.put("SEARCH_VAL",searchVal);
      fileDownService.insertCmnExclDnldHst(excelMap);
      excelMap.clear();

      // 엑셀 다운로드 작업 INSERT
      BatchJobVO vo = new BatchJobVO();
      vo.setExecTypeCd("REQ");
      vo.setBatchJobId("BATCH00237");
      vo.setSessionUserId(loginInfo.getUserId());
      vo.setExclDnldId(exclDnldId);

      //admin 권한 또는 DEV 권한 없으면 exclDnldYn +1 처리
      if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
        && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())){
        vo.setExclDnldYn(vo.getExclDnldYn()+1);
      }

      String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

      if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
        ipAddr = pRequest.getHeader("REMOTE_ADDR");
      }

      if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
        ipAddr = pRequest.getRemoteAddr();
      }

      vo.setExecParam("{\"srchStrtDt\":" + "\"" + searchVO.getSrchStrtDt() + "\""
                    + ",\"srchEndDt\":" + "\"" + searchVO.getSrchEndDt() + "\""
                    + ",\"pOperType\":" + "\"" + searchVO.getpOperType() + "\""
                    + ",\"pReqBuyType\":" + "\"" + searchVO.getpReqBuyType() + "\""
                    + ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\""
                    + ",\"pRequestStateCode\":" + "\"" + searchVO.getpRequestStateCode() + "\""
                    + ",\"pPstate\":" + "\"" + searchVO.getpPstate() + "\""
                    + ",\"pAcenEvntGrp\":" + "\"" + searchVO.getpAcenEvntGrp() + "\""
                    + ",\"pSuccYn\":" + "\"" + searchVO.getpSuccYn() + "\""
                    + ",\"pAcenErrCd\":" + "\"" + searchVO.getpAcenErrCd() + "\""
                    + ",\"pSearchGbn\":" + "\"" + searchVO.getpSearchGbn() + "\""
                    + ",\"pSearchName\":" + "\"" + searchVO.getpSearchName() + "\""
                    + ",\"pOnOffType\":" + "\"" + searchVO.getpOnOffType() + "\""
                    + ",\"pEsimYn\":" + "\"" + searchVO.getpEsimYn() + "\""
                    + ",\"pSelfYn\":" + "\"" + searchVO.getpSelfYn() + "\""
                    + ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
                    + ",\"dwnldRsn\":" + "\"" + pRequest.getParameter("DWNLD_RSN") + "\""
                    + ",\"ipAddr\":" + "\"" + ipAddr + "\""
                    + ",\"menuId\":" + "\"" + pRequest.getParameter("menuId") + "\""
                    + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");


      btchMgmtService.insertBatchRequest(vo);

      resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
      resultMap.put("msg", "다운로드성공");

    }catch (Exception e){
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }

  /**
   * @Description A'Cen 자동개통 통계 리스트 실패상세 엑셀 다운로드
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/acenRcpMgmtFailListExcelDownload.json")
  public String acenRcpMgmtFailListExcelDownload(HttpServletRequest pRequest
                                                ,HttpServletResponse pResponse
                                                ,@RequestParam Map<String, Object> pRequestParamMap
                                                ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                                                ,ModelMap model){

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getpSearchGbn())){
        // 검색구분이 없는 경우, 조회기간 필수
        if(KtisUtil.isEmpty(searchVO.getSrchStrtDt()) || KtisUtil.isEmpty(searchVO.getSrchEndDt())){
          throw new MvnoServiceException("신청일자가 누락되었습니다.");
        }
      }else if(KtisUtil.isEmpty(searchVO.getpSearchName())){
        throw new MvnoServiceException("검색어가 누락되었습니다.");
      }

      // 엑셀 다운로드 HISTORY 저장
      Map<String, Object> excelMap = new HashMap<String, Object>();
      int exclDnldId = fileDownService.getSqCmnExclDnldHst();
      excelMap.put("EXCL_DNLD_ID", exclDnldId);
      excelMap.put("MENU_ID", pRequest.getParameter("menuId"));
      excelMap.put("USR_ID", loginInfo.getUserId());
      excelMap.put("USR_NM", loginInfo.getUserName());
      excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
      excelMap.put("DUTY_NM", "ORG");
      excelMap.put("MENU_NM", "A'Cen 자동개통 통계");

      String searchVal = "신청일자:" + searchVO.getSrchStrtDt() + "~" + searchVO.getSrchEndDt() +
                         "|업무구분:" + searchVO.getpOperType() +
                         "|구매유형:" + searchVO.getpReqBuyType() +
                         "|대리점:" + searchVO.getpAgentCode() +
                         "|진행상태:" + searchVO.getpRequestStateCode() +
                         "|신청취소:" + searchVO.getpPstate() +
                         "|시나리오:" + searchVO.getpAcenEvntGrp() +
                         "|성공여부:" + "" +   // 실패이력은 성공여부 필터 고려x
                         "|실패사유:" + searchVO.getpAcenErrCd() +
                         "|검색구분:[" + searchVO.getpSearchGbn() + "," + searchVO.getpSearchName() + "]" +
                         "|모집경로:" + searchVO.getpOnOffType() +
                         "|eSIM여부" + searchVO.getpEsimYn() +
                         "|셀프개통제외" + searchVO.getpSelfYn();

      if(searchVal.length() > 500) {
        searchVal = searchVal.substring(0, 500);
      }

      excelMap.put("SEARCH_VAL",searchVal);
      fileDownService.insertCmnExclDnldHst(excelMap);
      excelMap.clear();

      // 엑셀 다운로드 작업 INSERT
      BatchJobVO vo = new BatchJobVO();
      vo.setExecTypeCd("REQ");
      vo.setBatchJobId("BATCH00238");
      vo.setSessionUserId(loginInfo.getUserId());
      vo.setExclDnldId(exclDnldId);

      //admin 권한 또는 DEV 권한 없으면 exclDnldYn +1 처리
      if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId())
        && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())){
        vo.setExclDnldYn(vo.getExclDnldYn()+1);
      }

      String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

      if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
        ipAddr = pRequest.getHeader("REMOTE_ADDR");
      }

      if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")){
        ipAddr = pRequest.getRemoteAddr();
      }

      vo.setExecParam("{\"srchStrtDt\":" + "\"" + searchVO.getSrchStrtDt() + "\""
                    + ",\"srchEndDt\":" + "\"" + searchVO.getSrchEndDt() + "\""
                    + ",\"pOperType\":" + "\"" + searchVO.getpOperType() + "\""
                    + ",\"pReqBuyType\":" + "\"" + searchVO.getpReqBuyType() + "\""
                    + ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\""
                    + ",\"pRequestStateCode\":" + "\"" + searchVO.getpRequestStateCode() + "\""
                    + ",\"pPstate\":" + "\"" + searchVO.getpPstate() + "\""
                    + ",\"pAcenEvntGrp\":" + "\"" + searchVO.getpAcenEvntGrp() + "\""
                    + ",\"pSuccYn\":" + "\"\""  // 실패이력은 성공여부 필터 고려x
                    + ",\"pAcenErrCd\":" + "\"" + searchVO.getpAcenErrCd() + "\""
                    + ",\"pSearchGbn\":" + "\"" + searchVO.getpSearchGbn() + "\""
                    + ",\"pSearchName\":" + "\"" + searchVO.getpSearchName() + "\""
                    + ",\"pOnOffType\":" + "\"" + searchVO.getpOnOffType() + "\""
                    + ",\"pEsimYn\":" + "\"" + searchVO.getpEsimYn() + "\""
                    + ",\"pSelfYn\":" + "\"" + searchVO.getpSelfYn() + "\""
                    + ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
                    + ",\"dwnldRsn\":" + "\"" + pRequest.getParameter("DWNLD_RSN") + "\""
                    + ",\"ipAddr\":" + "\"" + ipAddr + "\""
                    + ",\"menuId\":" + "\"" + pRequest.getParameter("menuId") + "\""
                    + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");


      btchMgmtService.insertBatchRequest(vo);

      resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
      resultMap.put("msg", "다운로드성공");

    }catch (Exception e){
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }

  /**
   * @Description A'Cen 연동이력 조회
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/getAcenHist.json")
  public String getAcenHist(HttpServletRequest pRequest
                           ,HttpServletResponse pResponse
                           ,@RequestParam Map<String, Object> pRequestParamMap
                           ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                           ,ModelMap model) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getRequestKey())) {
        throw new MvnoServiceException("필수정보가 없습니다.");
      }

      // acen 연동이력 조회
      List<AcenHistVO> acenHistList = acenRcpMgmtService.getAcenHist(searchVO);

      int totalCount = 0;

      if(acenHistList.size() > 0){
        totalCount = acenHistList.get(0).getTOTAL_COUNT();
      }

      resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, acenHistList, totalCount);

    }catch (Exception e){
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }

  /**
   * @Description A'Cen 실패이력 조회
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/getAcenFailHist.json")
  public String getAcenFailHist(HttpServletRequest pRequest
                               ,HttpServletResponse pResponse
                               ,@RequestParam Map<String, Object> pRequestParamMap
                               ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                               ,ModelMap model) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getRequestKey())) {
        throw new MvnoServiceException("필수정보가 없습니다.");
      }

      // acen 실패이력 조회
      List<EgovMap> acenFailList = acenRcpMgmtService.getAcenFailHist(searchVO, pRequestParamMap);
      resultMap = makeResultMultiRow(pRequestParamMap, acenFailList);

    }catch (Exception e){
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }

  /**
   * @Description A'Cen 재실행 이력 조회
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/getAcenRetryHist.json")
  public String getAcenRetryHist(HttpServletRequest pRequest
                               ,HttpServletResponse pResponse
                               ,@RequestParam Map<String, Object> pRequestParamMap
                               ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                               ,ModelMap model) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getRequestKey())) {
        throw new MvnoServiceException("필수정보가 없습니다.");
      }

      // acen 재실행이력 조회
      List<EgovMap> acenFailList = acenRcpMgmtService.getAcenRetryHist(searchVO, pRequestParamMap);
      resultMap = makeResultMultiRow(pRequestParamMap, acenFailList);

    }catch (Exception e){
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }

  /**
   * @Description A'Cen 재실행 가능여부 조회
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/acenRtyPreChk.json")
  public String getCsResPerCntByDt(HttpServletRequest pRequest
                                  ,HttpServletResponse pResponse
                                  ,@RequestParam Map<String, Object> pRequestParamMap
                                  ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                                  ,ModelMap model) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getRequestKey())) {
        throw new MvnoServiceException("필수정보가 없습니다.");
      }

      // 재실행 가능 여부 확인
      Map<String, String> preChkMap = acenRcpMgmtService.acenRtyPreChk(searchVO);
      if(!"0000".equals(preChkMap.get("resultCode"))){
        throw new MvnoServiceException(preChkMap.get("resultMsg"));
      }

      resultMap.put("preChkMap", preChkMap);
      resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

    } catch (Exception e) {
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }


  /**
   * @Description A'Cen 재실행 처리
   * @Author : hsy
   * @CreateDate : 2025.03.06
   */
  @RequestMapping(value = "/org/csAnalyticMgmt/updateAcenWithRty.json")
  public String updateAcenWithRty(HttpServletRequest pRequest
                                  ,HttpServletResponse pResponse
                                  ,@RequestParam Map<String, Object> pRequestParamMap
                                  ,@ModelAttribute("searchVO") AcenRcpMgmtVO searchVO
                                  ,ModelMap model) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      // Login check
      LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
      loginInfo.putSessionToParameterMap(pRequestParamMap);

      // 본사 화면
      if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
        throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
      }

      // 필수값 체크
      if(KtisUtil.isEmpty(searchVO.getRequestKey())) {
        throw new MvnoServiceException("필수정보가 없습니다.");
      }

      // 재실행 세팅값 조회
      Map<String, String> preChkMap = acenRcpMgmtService.acenRtyPreChk(searchVO);
      if(!"0000".equals(preChkMap.get("resultCode"))){
        throw new MvnoServiceException(preChkMap.get("resultMsg"));
      }

      // 재실행 처리
      preChkMap.put("requestKey", searchVO.getRequestKey());
      preChkMap.put("sessionUserId", String.valueOf(pRequestParamMap.get("SESSION_USER_ID")));
      int updCnt = acenRcpMgmtService.updateAcenWithRty(preChkMap);

      if(updCnt <= 0){
        throw new MvnoServiceException("재실행 처리 중 오류가 발생했습니다.");
      }

      resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

    } catch (Exception e) {
      resultMap.clear();
      if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP2002201", "A'Cen 자동개통 통계")) {
        throw new MvnoErrorException(e);
      }
    }

    model.addAttribute("result", resultMap);
    return "jsonView";
  }





}
