package com.ktmmobile.msf.system.common.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ktmmobile.msf.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.form.newchange.service.FormDtlSvc;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.service.SfMypageSvc;
import com.ktmmobile.msf.system.common.dto.NowDlvryReqDto;
import com.ktmmobile.msf.system.common.dto.PopupDto;
import com.ktmmobile.msf.system.common.dto.SiteMenuDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.BannAccessTxnDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.McpErropPageException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.NowDlvryResDto;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.CommonHttpClient;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.ParseHtmlTagUtil;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : CommonController.java
 * 날짜     : 2016. 1. 15. 오전 10:29:23
 * 작성자   : papier
 * 설명     : 공통 Controller
 * </pre>
 */
@Controller
public class FCommonController {

    private static final Logger logger = LoggerFactory.getLogger(FCommonController.class);

    @Autowired
    private FCommonSvc fCommonSvc;

    @Value("${juso.interface.server}")
    private String propertiesService;

    @Value("${juso.key}")
    private String jusoKey;

    @Autowired
    private SfMypageSvc mypageService;

    @Autowired
    FormDtlSvc formDtlSvc;

    @Autowired
    private MplatFormService mPlatFormService;

//    @Autowired
//    private BannerStatService bannerStatService;

    @Autowired
    private IpStatisticService ipStatisticService;


    /**
     * <pre>
     * 설명     : 주소 popup
     * @param locale
     * @param model
     * @return
     * @return: String
     * </pre>
     */
    @RequestMapping(value = {"/addPopup.do", "/m/addPopup.do"})
    public String addPopup() {
        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/popup/jusoPopup";
        }else {
            return "/portal/popup/jusoPopup";
        }
    }

    @RequestMapping(value = {"/dlvryInfo.do", "/m/dlvryInfo.do" })
    public String dlvryInfo(HttpServletRequest request, ModelMap model) {
        String nfcYn = StringUtil.NVL(request.getParameter("nfcYn"),"N");
        model.addAttribute("nfcYn", nfcYn);

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/popup/dlvryInfo";
        }else {
            return "/portal/popup/dlvryInfo";
        }
    }

    /**
     * <pre>
     * 설명     : 주소 popup
     * @param locale
     * @param model
     * @return
     * @return: String
     * </pre>
     */
    @RequestMapping(value = "/addrPop.do")
    public String addrPop(HttpServletRequest request, ModelMap model) {

        model.addAttribute("jusoKey", jusoKey);
        return "/popup/addrPop";
    }

    /**
     * <pre>
     * 설명     : 주소 검색 <![CDATA[  확인 필요
     * @param locale
     * @param model
     * @return
     * @return: String
     * </pre>
     */
    @RequestMapping(value = "/addrlink/addrLinkApi.do")
    @ResponseBody
    public HttpEntity<byte[]> addrLinkApi(
            @RequestParam(value = "currentPage") String currentPage
            ,@RequestParam(value = "countPerPage") String countPerPage
            ,@RequestParam(value = "keyword") String keyword
            , ModelMap model) {


        String keywordTemp = keyword ;
        try {
            keywordTemp= URLEncoder.encode(keyword, "UTF-8");
        } catch (Exception e) {
            logger.error("addrLinkApi error");
        }

        keywordTemp = keywordTemp.toUpperCase();
        keywordTemp = keywordTemp.replaceAll("OR", "");
        keywordTemp = keywordTemp.replaceAll("SELECT", "");
        keywordTemp = keywordTemp.replaceAll("INSERT", "");
        keywordTemp = keywordTemp.replaceAll("DELETE", "");
        keywordTemp = keywordTemp.replaceAll("UPDATE", "");
        keywordTemp = keywordTemp.replaceAll("CREATE", "");
        keywordTemp = keywordTemp.replaceAll("DROP", "");
        keywordTemp = keywordTemp.replaceAll("EXEC", "");
        keywordTemp = keywordTemp.replaceAll("UNION", "");
        keywordTemp = keywordTemp.replaceAll("FETCH", "");
        keywordTemp = keywordTemp.replaceAll("DECLARE", "");
        keywordTemp =keywordTemp.replaceAll("TRUNCATE", "");




        String param = "";
        try {
            param = "currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+keywordTemp+"&confmKey="+jusoKey ;
        } catch (Exception e) {
            logger.error("addrLinkApi error");
        }

        String url = propertiesService + "?" + param;

        HttpEntity<byte[]> result = null;
        CommonHttpClient client = new CommonHttpClient(url);
        try {
            String xml = client.get();

            byte[] documentBody = xml.getBytes("UTF-8");

            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "xml"));
            header.setContentLength(documentBody.length);
            result = new HttpEntity<byte[]>(documentBody, header);

        } catch (Exception e) {
            logger.error("addrLinkApi error");
        }

        return result;
    }


    /**
     * 설명 : 팝업조회 ajax
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param popupDto
     * @return
     */
    @RequestMapping("/getComPopupListAjax.do")
    @ResponseBody
    public List<PopupDto> getPopupListAjax(HttpServletRequest request, ModelMap model,
           PopupDto popupDto)  {
        List<PopupDto> popupList = null;
        if(popupDto != null && popupDto.getMenuCode() != null) {
            if( "SMMainPc".equals(popupDto.getMenuCode())){//pc메인경우 배너목록조회
    //        if(popupDto.getMenuCode().equals("SMMainPc")){//pc메인경우 배너목록조회   널포인트
                popupList = fCommonSvc.getPopupMainList(popupDto.getMenuCode());
            }else{
                popupList = fCommonSvc.getPopupList(popupDto);
            }
        }

        return popupList;
    }


    /**
     * 설명 : 팝업 상세
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param popupSeq
     * @return
     */
    @RequestMapping("/popupDetail.do")
    public String popupDetail(HttpServletRequest request, ModelMap model,
           String popupSeq)  {
        PopupDto popupDetail = null;

        popupDetail = fCommonSvc.getPopupDetail(popupSeq);
        popupDetail.setPopupSbst(ParseHtmlTagUtil.convertHtmlchars(popupDetail.getPopupSbst()));

        model.addAttribute("popupDetail", popupDetail);

        return "common/popup";
    }


    /**
     * 설명 : 팝업 조회 모바일 ajax
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param popupDto
     * @return
     */
    @RequestMapping("/m/getComPopupListAjax.do")
    @ResponseBody
    public List<PopupDto> getMobilePopupListAjax(HttpServletRequest request, ModelMap model,
           PopupDto popupDto)  {
        List<PopupDto> popupList = null;
        String menuCode = popupDto.getMenuCode();
            popupList = fCommonSvc.getPopupMainList(menuCode);
        return popupList;
    }


//    /**
//     * <pre>
//     * 설명     : 통신자료제공내역신청 등록
//     * @param appformReqDto
//     * @return
//     * @return: Map<String,Object>
//     * </pre>
//     */
//    @RequestMapping(value ="/comm/insertmspCommDatPrvAjax.do")
//    @ResponseBody
//    public Map<String, Object> insertmspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto){
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
//        if (userSessionDto ==null) {
//            throw new McpCommonJsonException("0001" ,NO_FRONT_SESSION_EXCEPTION);
//        }
//
//        //TODO 1.본인인증한 DI ,pin 검증
//        NiceResDto sessNiceRes =  SessionUtils.getNiceResCookieBean() ;
//        if (sessNiceRes == null
//                || !userSessionDto.getPin().equals(sessNiceRes.getDupInfo()) ) {
//            throw new McpCommonJsonException("0002" ,NICE_CERT_EXCEPTION);
//        }
//
//
//        if (!StringUtils.isBlank(mspCommDatPrvTxnDto.getTgtSvcNo())) {
//            //전화번호 리스트
//            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
//
//            if (cntrList != null && cntrList.size() > 0) {
//                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
//                    if (mspCommDatPrvTxnDto.getTgtSvcNo().equals(mcpUserCntrMngDto.getContractNum())) {
//                        mspCommDatPrvTxnDto.setTgtSvcNo(mcpUserCntrMngDto.getCntrMobileNo());
//                    }
//                }
//            }
//
//        }
//        mspCommDatPrvTxnDto.setApyId(userSessionDto.getUserId());
//        mspCommDatPrvTxnDto.setApyNm(userSessionDto.getName());
//        mspCommDatPrvTxnDto.setMyslfAthnCi(sessNiceRes.getConnInfo());
//
//
//        if (fCommonSvc.insertmspCommDatPrvTxn(mspCommDatPrvTxnDto)) {
//            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//            SessionUtils.saveNiceRes(null);
//        } else {
//            rtnMap.put("RESULT_CODE", "-1");
//        }
//
//
//        return rtnMap;
//    }


    /**
     * 약관상세내용 보기
     * NMCP_FORM_DTL
     */
    @RequestMapping("/clauseDetailView.do")
    public String clauseDetailView(FormDtlDTO oFormDtlDTO
            ,Model model )  {

        FormDtlDTO rtnObj = formDtlSvc.FormDtlView(oFormDtlDTO);
        model.addAttribute("ClauseObj", rtnObj);

        return "/common/clauseDetail.view";

    }

    /**
     * <pre>
     * 설명     : 공통코드에 해당 요금제 존재 여부 확인
     * @param cdGroupId : 그룹 코드
     *         rateCd     : 요금제 코드
     * @return  is this have rate
     * @return: int
     * </pre>
     */
    @RequestMapping(value="/termsPop.do")
    @ResponseBody
    public FormDtlDTO termsPop(FormDtlDTO oFormDtlDTO){
        if(StringUtils.isEmpty(oFormDtlDTO.getDocType())) {
            oFormDtlDTO.setDocType("01");
        }

        FormDtlDTO rtnObj = formDtlSvc.FormDtlView(oFormDtlDTO);

        if(rtnObj !=null) {
            String docContent = StringUtil.NVL(rtnObj.getDocContent(),"");
            rtnObj.setDocContent(ParseHtmlTagUtil.convertHtmlchars(docContent));

            // 상세페이지 댓글이벤트
            if("INHTML001".equals(rtnObj.getCdGroupId2()) || "INHTML002".equals(rtnObj.getCdGroupId2())) {

                UserSessionDto userSession = SessionUtils.getUserCookieBean();

                if(Optional.ofNullable(userSession).isPresent() && Optional.ofNullable(userSession.getUserId()).isPresent()){

                    List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

                    rtnObj.setCntrList(cntrList);
                }

                FormDtlDTO formDtlDTO = new FormDtlDTO();
                formDtlDTO.setCdGroupId1("INFOPRMT");
                formDtlDTO.setCdGroupId2("INSTRUCTION002");
                FormDtlDTO eventHolder = formDtlSvc.FormDtlView(formDtlDTO);
                rtnObj.setpHolder(eventHolder.getDocContent());
            }
            // 제휴사 약관
            if("02".equals(oFormDtlDTO.getDocType()) || "03".equals(oFormDtlDTO.getDocType())) {
                String dtlCdNm = "";
                if(!StringUtil.isEmpty(oFormDtlDTO.getName())) {
                    dtlCdNm = rtnObj.getDtlCdNm().replace("#{name}", oFormDtlDTO.getName() + " ");
                } else {
                    dtlCdNm = rtnObj.getDtlCdNm().replace("#{name}", "");
                }
                rtnObj.setDtlCdNm(dtlCdNm);
            }
        }

        return rtnObj;
    }

    /**
     * <pre>
     * 설명     : 공통코드에 해당 요금제 존재 여부 확인
     * @param cdGroupId : 그룹 코드
     *         rateCd     : 요금제 코드
     * @return  is this have rate
     * @return: int
     * </pre>
     */
    @RequestMapping(value="/termsPopByDate.do")
    @ResponseBody
    public FormDtlDTO termsPopByDate(FormDtlDTO oFormDtlDTO){
        if(StringUtils.isEmpty(oFormDtlDTO.getDocType())) {
            oFormDtlDTO.setDocType("01");
        }

        FormDtlDTO rtnObj = formDtlSvc.FormDtlViewByDate(oFormDtlDTO);

        if(rtnObj !=null) {
            String docContent = StringUtil.NVL(rtnObj.getDocContent(),"");
            rtnObj.setDocContent(ParseHtmlTagUtil.convertHtmlchars(docContent));

            // 상세페이지 댓글이벤트
            if("INHTML001".equals(rtnObj.getCdGroupId2()) || "INHTML002".equals(rtnObj.getCdGroupId2())) {

                UserSessionDto userSession = SessionUtils.getUserCookieBean();

                if(Optional.ofNullable(userSession).isPresent() && Optional.ofNullable(userSession.getUserId()).isPresent()){

                    List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

                    rtnObj.setCntrList(cntrList);
                }

                FormDtlDTO formDtlDTO = new FormDtlDTO();
                formDtlDTO.setCdGroupId1("INFOPRMT");
                formDtlDTO.setCdGroupId2("INSTRUCTION002");
                FormDtlDTO eventHolder = formDtlSvc.FormDtlView(formDtlDTO);
                rtnObj.setpHolder(eventHolder.getDocContent());
            }
            // 제휴사 약관
            if("02".equals(oFormDtlDTO.getDocType()) || "03".equals(oFormDtlDTO.getDocType())) {
                String dtlCdNm = "";
                if(!StringUtil.isEmpty(oFormDtlDTO.getName())) {
                    dtlCdNm = rtnObj.getDtlCdNm().replace("#{name}", oFormDtlDTO.getName() + " ");
                } else {
                    dtlCdNm = rtnObj.getDtlCdNm().replace("#{name}", "");
                }
                rtnObj.setDtlCdNm(dtlCdNm);
            }
        }

        return rtnObj;
    }

    /**
     * <pre>
     * 설명     : 공통코드에 해당 요금제 존재 여부 확인
     * @param cdGroupId : 그룹 코드
     *         rateCd     : 요금제 코드
     * @return  is this have rate
     * @return: int
     * </pre>
     */
    @RequestMapping(value="/isThisHaveRateAjax.do")
    @ResponseBody
    public int isThisHaveRate(
            @RequestParam(value = "cdGroupId") String cdGroupId
            ,@RequestParam(value = "rateCd") String rateCd ){

        String codeNm = NmcpServiceUtils.getCodeNm(cdGroupId,rateCd) ;
        if ("".equals(codeNm)) {
            return 0 ; //존재 하지 않음
        } else {
            return 1 ; //존재함
        }

    }


    /**
     * 설명 : 에러 페이지 이동
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param url
     * @return
     */
    @RequestMapping("/appWorkInfo.do")
    @ResponseBody
    public Map<String, Object> appWorkInfo(@RequestParam(value = "url") String url)  {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> valueMap = new HashMap<String, Object>();

        //if (url.equals("/m/get/appWidgetDataAjax.do")) {

            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("mplatFormServiceWorkMsg", "widgetMsg");
            String  widgetMsg = "App 리뉴얼 작업중입니다.\r\n"
                    + "PC/모바일 브라우저에서는 정상이용 가능합니다.";
            if (nmcpCdDtlDto != null) {
                widgetMsg = StringUtil.NVL(nmcpCdDtlDto.getExpnsnStrVal1(), "");
            }
            valueMap.put("RESULT_CD", "0202");
            valueMap.put("RESULT_DESC", widgetMsg);
            returnMap.put("CODE", "0000");
            returnMap.put("ERRORDESC", "");
            returnMap.put("VALUE", valueMap);
        //}

        return returnMap;

    }


    /**
     * 설명 : 작업 공지 페이지
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param model
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value={"/workInfo.do","/m/workInfo.do"})
    public String workInfo(Model model, @RequestParam(value = "redirectUrl", required=false) String redirectUrl)  {

        String returnUrl = "/common/workInfo";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "/common/mWorkInfo";
        }

        NmcpCdDtlDto allCode = NmcpServiceUtils.getCodeNmDto("mplatFormServiceWork", "ALL");

        String title = "<b>사이트 이용에 <br>불편을 드려 죄송합니다.</b>";
        String msg = "시스템 점검중으로 해당 페이지에 접속할 수 없습니다. <br>잠시 후 다시 확인해주시기 바랍니다."; //-수동 하드코딩 PC, 모바일 작업공지 페이지 문구-

        if(allCode != null) {
            NmcpCdDtlDto workInfoMsg = NmcpServiceUtils.getCodeNmDto("mplatFormServiceWorkMsg", "htmlMsg");
            if (workInfoMsg != null) {
                //전체 작업공지
                title = "<b>작업공지</b>";
                msg = StringUtil.NVL(workInfoMsg.getExpnsnStrVal1(), "");
            }
        }

        //model.addAttribute("redirectUrl", redirectUrl); //없으면 history back
        model.addAttribute("alertTitle", title);
        model.addAttribute("alertMsg", msg);

        return returnUrl;
    }


    /**
     * 설명 : 에러 페이지 이동
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param e
     * @return
     */
    @RequestMapping(value={"/errorPage.do","/m/errorPage.do"})
    public String errorPage(McpErropPageException e )  {

        String returnUrl = "/portal/errmsg/errorPage";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "/mobile/errmsg/errorPage";
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("errMsg", e.getErrorMsg());
        mv.addObject("redirectUrl", e.getRedirectUrl());

        return returnUrl;
    }

    @RequestMapping("/dlvryAddrChkAjax.do")
    @ResponseBody
    public Map<String, Object> dlvryAddrChkAjax(HttpServletRequest request, ModelMap model, NowDlvryReqDto nowDlvryReqDto)  {

        Map<String, Object> map = new HashMap<String,Object>();

        String rsltCd = "99"; // 정상 : "00",  오류 : "99"
        String rsltMsg = ""; // 정상 : "성공", 오류 : "존재하지 않는 주문입니다."
        String psblYn = "E"; // Y 배달가능, N 배달불가
        String bizOrgCd = "";
        String acceptTime = "";
        NowDlvryResDto nowDlvryResDto = new NowDlvryResDto();
        try{

            String jibunAddr = StringEscapeUtils.unescapeXml(StringUtil.NVL(nowDlvryReqDto.getJibunAddr(),""));
            nowDlvryReqDto.setJibunAddr(jibunAddr);

            nowDlvryResDto = mPlatFormService.inqrPsblDeliveryAddr(nowDlvryReqDto);
            rsltCd = nowDlvryResDto.getRsltCd();
            rsltMsg = nowDlvryResDto.getRsltMsg();
            psblYn = nowDlvryResDto.getPsblYn();
            bizOrgCd = nowDlvryResDto.getBizOrgCd();
            acceptTime = nowDlvryResDto.getAcceptTime();

        }catch(Exception e){
            rsltMsg = "서비스 오류입니다.";
        }

        map.put("rsltCd", rsltCd);
        map.put("rsltMsg", rsltMsg);
        map.put("psblYn", psblYn);
        map.put("bizOrgCd", bizOrgCd);
        map.put("acceptTime", acceptTime);

        return map;
    }


    /**
     * 설명 : 배너클릭 정보 저장
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param bannAccessTxnDto
     * @return
     */
    @RequestMapping(value ="/insertBannAccessAjax.do")
    @ResponseBody
    public int insertBannAccessAjax(BannAccessTxnDto bannAccessTxnDto){

        SiteMenuDto curmenu = SessionUtils.getCurrentMenuDto();
        SiteMenuDto bemenu = SessionUtils.getBeforeMenuDto();

        if(curmenu != null) {
            String menuSeq = String.valueOf(bannAccessTxnDto.getMenuSeq()).replaceAll("null", "");

            bannAccessTxnDto.setPlatformCd(curmenu.getPlatformCd());
            bannAccessTxnDto.setMenuSeq(curmenu.getMenuSeq());
            bannAccessTxnDto.setAccessIp(ipStatisticService.getClientIp());
            bannAccessTxnDto.setUrlSeq(curmenu.getRepUrlSeq());

            UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
            String userId = "";
            if (userSessionDto != null) {
                userId = userSessionDto.getUserId();
            }
            bannAccessTxnDto.setUserId(userId);
            bannAccessTxnDto.setReqTrtCd("CLK");

            if(bannAccessTxnDto.getMenuSeq() != 999999) {
//                bannerStatService.insertBannAccessTxn(bannAccessTxnDto);
            }
        }
        return 1;
    }


    @RequestMapping(value={"/authCommon.do","/m/authCommon.do"})
    public String mainController(HttpServletRequest request, Model model){

        model.addAttribute("isMobile", NmcpServiceUtils.isMobile());

        return "/common/authCommon";
    }


    @RequestMapping(value = {"/error/commonError.do"})
    public String errorRedirect(){
        return "/errmsg/commonErrorRedirect";
    }

    @RequestMapping(value = {"/error/commonErrorNotFound.do"})
    public String errorNotFoundRedirect(){
        return "/errmsg/commonErrorNotFoundRedirect";
    }

    @RequestMapping(value = {"/m/error/notFound.do", "/error/notFound.do"})
    public String notFound(){
        String rtnPageNm = "/portal/errmsg/notFound";
        if("Y".equals(NmcpServiceUtils.isMobile())){
              rtnPageNm = "/mobile/errmsg/notFound";
        }
        return rtnPageNm;
    }

    @RequestMapping(value = {"/m/error/error.do", "/error/error.do"})
    public String error(){
        String rtnPageNm = "/portal/errmsg/error";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            rtnPageNm = "/mobile/errmsg/error";
        }
        return rtnPageNm;
    }

    @RequestMapping(value = {"/m/getContentAjax.do", "/getContentAjax.do"})
    @ResponseBody
    public Map<String, Object> getContentAjax(HttpServletRequest request, @ModelAttribute("formDtlDTO") FormDtlDTO formDtlDTO, Model model )  {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCd", "-1");

        FormDtlDTO rtnObj = formDtlSvc.FormDtlView(formDtlDTO);

        if(rtnObj != null) {
            result.put("resultCd", "0000");
            result.put("data", rtnObj);
        }

        return result;
    }

    @RequestMapping(value = "/getPrdMnpCmpnListAjax.do")
    @ResponseBody
    public Map<String, Object> getPrdMnpCmpnList()  {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCd", "-1");

        fCommonSvc.prdMnpCmpnList();

        result.put("resultCd", "0000");

        return result;
    }

    @RequestMapping(value = "/checkServiceAvailable.do")
    @ResponseBody
    public Map<String, String> checkServiceAvailable(@RequestParam(value = "serviceName", required = true) String serviceName)  {
        Map<String, String> result = new HashMap<>();

        Map<String, String> checkMap = fCommonSvc.checkServiceAvailable(serviceName);
        if (checkMap == null) {
            throw new McpCommonJsonException(AJAX_SUCCESS, "이용가능");
        }

        if ("0000".equals(checkMap.get("code"))) {
            result.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            result.put("RESULT_CODE", checkMap.get("code"));
        }
        result.put("RESULT_MSG", checkMap.get("message"));

        return result;
    }
    /**
     * <pre>
     * 설명     : 요금제 변경 시, 제휴요금제 여부 확인 및 저휴요금제 정보 전달
     * @param   *rateCd     : 요금제 코드
     * </pre>
     */
    @RequestMapping(value="/getJehuInfoAjax.do")
    @ResponseBody
    public Map<String, String> getJehuInfo(@RequestParam(value = "rateCd") String rateCd,
                                           @RequestParam(value = "pCntpntShopId") String pCntpntShopId){
        Map<String, String> jehuInfo = new HashMap<>();

        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(rateCd);
        NmcpCdDtlDto jehuPartnerInfo = fCommonSvc.getJehuPartnerInfo(pCntpntShopId);

        SessionUtils.saveJehuPartnerType(jehuPartnerInfo.getExpnsnStrVal1());
        jehuInfo.put("jehuProdType", jehuProdInfo.getDtlCd());
        jehuInfo.put("jehuProdName", jehuProdInfo.getDtlCdNm());
        jehuInfo.put("jehuPartnerType", jehuPartnerInfo.getDtlCd());
        jehuInfo.put("jehuPartnerName", jehuPartnerInfo.getDtlCdNm());

        return jehuInfo;
    }
}
