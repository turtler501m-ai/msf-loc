package com.ktmmobile.msf.form.newchange.controller;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.form.newchange.dto.EsimDto;
import com.ktmmobile.msf.form.newchange.dto.McpUploadPhoneInfoDto;
import com.ktmmobile.msf.form.newchange.service.EsimSvc;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.*;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.service.NiceLogSvc;
import com.ktmmobile.msf.system.common.util.*;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.constants.Constants.CSTMR_TYPE_NM;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.*;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NICE_CERT_EXCEPTION_INSR;

@Controller
public class EsimController {

    private static final Logger logger = LoggerFactory.getLogger(EsimController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private EsimSvc eSimSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MsfMypageUserService mypageUserService;

    @Autowired
    NiceLogSvc nicelog ;

    @Autowired
    CertService certService;

    @Autowired
    MsfMypageSvc msfMypageSvc;

    /**
     * 설명 : eSim 다이렉트 구매 화면
     * @Author : kih
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/appForm/eSimInfo.do", "/m/appForm/eSimInfo.do"})
    public String eSimInfo(HttpServletRequest request, Model model) {

        String bannCtg = "U302";
        List<BannerDto> bannerList = NmcpServiceUtils.getBannerList(bannCtg);

        model.addAttribute("bannerList", bannerList);

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/eSimInfo";
        }else {
            return "/portal/appForm/eSimInfo";
        }
    }


    /**
     * 설명 : eSim 다이렉트 구매 화면
     * @Author : kih
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/appForm/eSimInfom.do", "/m/appForm/eSimInfom.do"})
    public String eSimInfom(HttpServletRequest request, Model model) {

        String bannCtg = "U302";
        List<BannerDto> bannerList = NmcpServiceUtils.getBannerList(bannCtg);

        model.addAttribute("bannerList", bannerList);

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/eSimInfom";
        }else {
            return "/portal/appForm/eSimInfom";
        }
    }


    /**
     * 설명 : eSim 다이렉트 구매 화면
     * @Author : kih
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/appForm/eSimView.do", "/m/appForm/eSimView.do"})
    public String eSimView(HttpServletRequest request, Model model) {

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/eSimView";
        }else {
            return "/portal/appForm/eSimView";
        }
    }

    /**
     * 설명 : eSim 다이렉트 구매 화면
     * @Author : kih
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/appForm/eSimSelfView.do", "/m/appForm/eSimSelfView.do"})
    public String eSimSelfView(@RequestParam(required = false , defaultValue = "") String rateCd) {

        if (StringUtils.isNotBlank(rateCd)) {
            return "redirect:/appForm/appFormDesignView.do?prdtIndCd=10"  + "&rateCd=" + rateCd;
        } else {
            return "redirect:/appForm/appFormDesignView.do?prdtIndCd=10" ;
        }

    }




    /**
     * 설명 : eSim Watch 다이렉트 구매 화면
     * @Author : papier
     * @Date : 2023.01.03
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/appForm/eSimWatch.do", "/m/appForm/eSimWatch.do"})
    public String eSimWatch(Model model) {

        // ================= STEP START =================
        // step 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();
        // ** esimWatchForm의 operType 설정은 step3 이후부터 진행. appform.do 진입시 상세 페이지명 재설정
        SessionUtils.setPageSession("esimWatchForm");
        // ================= STEP END =================

        //인가된 사용자 세션 이름 화면고정 2024-12-05 박민건
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if (userSession != null) {
            model.addAttribute("cstmrName", userSession.getName());
        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/eSimWatch";
        }else {
            return "/portal/appForm/eSimWatch";
        }
    }


    /**
     * 설명 : eSim Watch 다이렉트 구매 화면
     * @Author : 박성훈
     * @Date : 2023.02.16
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/appForm/eSimWatchInfo.do", "/m/appForm/eSimWatchInfo.do"})
    public String eSimWatchInfo() {
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/eSimWatchInfo";
        }else {
            return "/portal/appForm/eSimWatchInfo";
        }
    }





    /**
     * 설명 : eSim 제약체크
     * @Author : 김일환
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/appForm/eSimChkAjax.do","/m/appForm/eSimChkAjax.do"})
    @ResponseBody
    public Map<String,Object> eSimChkAjax(Model model,@ModelAttribute EsimDto esimDto){

        Map<String,Object> hm = new HashMap<String, Object>();
        EsimDto resDto = new EsimDto();

        String eid = esimDto.getEid();
        String imei1 = esimDto.getImei1();
        String imei2 = esimDto.getImei2();

        // 2025-05-19, 부정사용주장 단말 확인, 개발팀 김동혁
        if (eSimSvc.checkAbuseImeiList(Arrays.asList(imei1, imei2))) {
            EsimDto abuseResDto = new EsimDto();
            abuseResDto.setResultCode("9901");
            hm.put("resDto", abuseResDto);
            return hm;
        }

        int uploadPhoneSrlNo = this.mcpUploadPhoneInfo(eid,imei1,imei2);
        esimDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
        resDto = eSimSvc.eSimChk(esimDto);
        resDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);

        logger.info("eSimChk resDto==>"+ObjectUtils.convertObjectToString(resDto));

        // ============ STEP START ============
        String[] certKey= {"urlType", "uploadPhoneSrlNo"};
        String[] certValue= {"saveEsimSrlNo", uploadPhoneSrlNo+""};
        certService.vdlCertInfo("E", certKey, certValue);
        // ============ STEP END ============

        hm.put("resDto", resDto);
        return hm;
    }

    /**
     * 설명 : eSim 제약체크
     * @Author : 김일환
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/appForm/omdRegAjax.do","/m/appForm/omdRegAjax.do"})
    @ResponseBody
    public Map<String,Object> omdRegAjax(Model model,@ModelAttribute EsimDto esimDto){

        Map<String,Object> hm = new HashMap<String, Object>();
        EsimDto resDto = new EsimDto();

        // 2025-05-19, 부정사용주장 단말 확인, 개발팀 김동혁
        if (eSimSvc.checkAbuseImeiList(Arrays.asList(esimDto.getImei1(), esimDto.getImei2()))) {
            EsimDto abuseResDto = new EsimDto();
            abuseResDto.setResultCode("9901");
            hm.put("resDto", abuseResDto);
            return hm;
        }

        resDto = eSimSvc.omdReg(esimDto);
        logger.info("omdReg resDto==>"+ObjectUtils.convertObjectToString(resDto));
        hm.put("resDto", resDto);
        return hm;
    }

    /**
     * 설명 : eSim 제약체크
     * @Author : 김일환
     * @Date : 2022.08.17
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value={"/appForm/niceAuthChkAjax.do","/m/appForm/niceAuthChkAjax.do"})
    @ResponseBody
    public Map<String,Object> omdNiceChkAjax(Model model,@ModelAttribute EsimDto esimDto){

        Map<String,Object> hm = new HashMap<String, Object>();
        EsimDto resDto = new EsimDto();
        resDto = eSimSvc.niceAuthChk(esimDto);
        hm.put("resDto", resDto);
        return hm;
    }

    private int mcpUploadPhoneInfo(String eid,String imei1,String imei2) {
        return mcpUploadPhoneInfo(eid,imei1,imei2,"");
    }

    private int mcpUploadPhoneInfo(String eid,String imei1,String imei2 ,String prntsContractNo) {

        int uploadPhoneSrlNo = 0;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        McpUploadPhoneInfoDto mcpUploadPhoneInfoDto = new McpUploadPhoneInfoDto();

        try {
            String userId = "";
            String accessIp = ipstatisticService.getClientIp();
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            if(userSession !=null) {
                userId = userSession.getUserId();
            }

            mcpUploadPhoneInfoDto.setReqModelName("");
            mcpUploadPhoneInfoDto.setModelId("");
            mcpUploadPhoneInfoDto.setReqPhoneSn("");
            mcpUploadPhoneInfoDto.setReqUsimSn("");
            mcpUploadPhoneInfoDto.setEid(eid);
            mcpUploadPhoneInfoDto.setImei1(imei1);
            mcpUploadPhoneInfoDto.setImei2(imei2);
            mcpUploadPhoneInfoDto.setUploadPhoneImg("");
            mcpUploadPhoneInfoDto.setAccessIp(accessIp);
            mcpUploadPhoneInfoDto.setUserId(userId);
            mcpUploadPhoneInfoDto.setPrntsContractNo(prntsContractNo);
            uploadPhoneSrlNo = eSimSvc.insertMcpUploadPhoneInfoDto(mcpUploadPhoneInfoDto);//테스트 입니다...

        } catch(DataAccessException e) {
            logger.info("error :"+e.getMessage());
        } catch(Exception e) {
            logger.info("error :"+e.getMessage());
        }
        logger.info("uploadPhoneSrlNo==>"+uploadPhoneSrlNo);
        return uploadPhoneSrlNo;
    }



    /**
     * 설명 : eSim 제약체크
     * @Author : papier
     * @Date : 2023.01.04
     * @param
     * @param
     * @return
     */
    @RequestMapping(value="/appForm/eSimWatchAjax.do")
    @ResponseBody
    public Map<String,Object> eSimWatch(EsimDto esimDto, String contractNum) {

        Map<String,Object> hm = new HashMap<String, Object>();
        EsimDto resDto = new EsimDto();

        String eid = esimDto.getEid();
        String imei1 = esimDto.getImei1();

        // 2025-05-19, 부정사용주장 단말 확인, 개발팀 김동혁
        if (eSimSvc.checkAbuseImeiList(Collections.singletonList(imei1))) {
            throw new McpCommonJsonException("9901", ESIM_SELF_ABUSE_IMEI_EXCEPTION);
        }

        // ============ STEP START ============
        // esimWatch 고객인증 세션으로 청소년 확인 및 계약번호 검증
        AuthSmsDto autEsim = new AuthSmsDto();
        autEsim.setMenu("eSimWatch");
        autEsim = SessionUtils.getAuthSmsBean(autEsim);

        // autEsim의 svcCntrNo에 set되어져 있는 실제값은 서비스계약번호가 아닌 계약번호
        if (autEsim == null || StringUtils.isBlank(autEsim.getSvcCntrNo()) || !autEsim.getSvcCntrNo().equals(contractNum)) {
            throw new McpCommonJsonException("AUTH01", ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String ncType= ""; // 청소년 확인
        String customerSsn= StringUtil.NVL(autEsim.getUnSSn(), ""); // 복호화된 주민번호

        if(customerSsn.length() < 7) {
            throw new McpCommonJsonException("AUTH02", COMMON_EXCEPTION);
        }else{
            ncType= certService.getNcTypeForCrt(customerSsn, "");
        }
        // ============ STEP END ============

        int uploadPhoneSrlNo = this.mcpUploadPhoneInfo(eid,imei1,"",contractNum);
        esimDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
        resDto = eSimSvc.eSimWatch(esimDto);
        resDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
        logger.info("eSimWatch resDto==>"+ObjectUtils.convertObjectToString(resDto));

        // ============ STEP START ============
        // 대리인구분값, esimSeq
        String[] certKey= {"urlType", "ncType", "uploadPhoneSrlNo"};
        String[] certValue= {"saveWatchSrlNo", ncType, uploadPhoneSrlNo+""};
        certService.vdlCertInfo("E", certKey, certValue);
        // ============ STEP END ============

        hm.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        hm.put("resDto", resDto);
        return hm;

    }




    @RequestMapping(value="/appForm/getContractInfoAjax.do")
    @ResponseBody
    public Map<String,Object> getContractInfo(EsimDto esimDto){

        Map<String,Object> rtnMap = new HashMap<String, Object>();

        //인자값 확인
        if (StringUtils.isBlank(esimDto.getCstmrName())
            || StringUtils.isBlank(esimDto.getCstmrMobile())
            || StringUtils.isBlank(esimDto.getCstmrNativeRrn01())) {
            throw new McpCommonJsonException("9999" ,BIDING_EXCEPTION);
        }

        // 2024-12-17 인가된 사용자 체크
        Map<String, String> rtnChkAuthMap = msfMypageSvc.checkAuthUser(esimDto.getCstmrName(), esimDto.getCstmrNativeRrn01());
        if (!"0000".equals(rtnChkAuthMap.get("returnCode"))) {
            rtnMap.put("RESULT_CODE", rtnChkAuthMap.get("returnCode"));
            rtnMap.put("RESULT_MSG", rtnChkAuthMap.get("returnMsg"));
            return rtnMap;
        }

        Map<String, String> resOjb= mypageUserService.selectContractObj(esimDto.getCstmrName(), esimDto.getCstmrMobile() ,"") ;

        if (resOjb == null) {
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

       //logger.info("[WOO][WOO][WOO]==>"+ ObjectUtils.convertObjectToString(resOjb));

        String customerSsn = resOjb.get("USER_SSN") ;
        String reqPayType = resOjb.get("BL_BILLING_METHOD") ;
        String contractNum = resOjb.get("CONTRACT_NUM");
        try {
            customerSsn = EncryptUtil.ace256Dec(customerSsn);
        } catch (CryptoException e) {
            throw new McpCommonJsonException("9998" ,ACE_256_DECRYPT_EXCEPTION);
        }

        if ( customerSsn.equals(esimDto.getCstmrNativeRrn01()) ) {
            //외국인 청소년 구분
            if (customerSsn.length() < 7) {
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_DESC", "주민번호 형식이 일치하지 않습니다.");
                return rtnMap;
            }
        } else {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_DESC", "주민번호가 일치 하지 않습니다. ");
            return rtnMap;
        }

        // ============ STEP START ============
        // 청소년 확인
        String ncType= certService.getNcTypeForCrt(customerSsn, "");

        // 대리인구분값, 이름, 생년월일, 계약번호
        String[] certKey= {"urlType", "ncType", "name", "birthDate", "contractNum"};
        String[] certValue= {"memberAuth", ncType, esimDto.getCstmrName(), EncryptUtil.ace256Enc(customerSsn), contractNum};
        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============

        //보안을 위해 session 저장
        AuthSmsDto autEsim = new AuthSmsDto();
        autEsim.setUnSSn(customerSsn);
        autEsim.setCtn(resOjb.get("SUBSCRIBER_NO"));
        autEsim.setSvcCntrNo(contractNum);
        autEsim.setReqPayType(reqPayType);
        autEsim.setMenu("eSimWatch");
        //인증 정보 session 저장
        SessionUtils.setAuthSmsSession(autEsim);

        rtnMap.put("contractNum", contractNum);
        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        return rtnMap;
    }


    @RequestMapping(value="/appForm/getContractInfoByNumAjax.do")
    @ResponseBody
    public Map<String,Object> getContractInfoByNum(String prntsContractNo){
        //인자값 확인
        if (StringUtils.isBlank(prntsContractNo) ) {
            throw new McpCommonJsonException("9999" ,BIDING_EXCEPTION);
        }

        if (!"LOCAL".equals(serverName)) {
            //보안상.. 체크
            AuthSmsDto autEsim = new AuthSmsDto();
            autEsim.setMenu("eSimWatch");
            autEsim = SessionUtils.getAuthSmsBean(autEsim);

            logger.info("childVerifyAjax autEsim==>" + ObjectUtils.convertObjectToString(autEsim));
            if (autEsim == null || StringUtils.isBlank(autEsim.getSvcCntrNo()) || !prntsContractNo.equals(autEsim.getSvcCntrNo())) {
                throw new McpCommonJsonException("9999", ExceptionMsgConstant.F_BIND_EXCEPTION);
            }
        }


        Map<String,Object> rtnMap = new HashMap<String, Object>();

        Map<String, String> resOjb= mypageUserService.selectContractObj("", "" ,prntsContractNo) ;

        if (resOjb == null) {
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

        String customerSsn = resOjb.get("USER_SSN") ;
        String customerNm = resOjb.get("SUB_LINK_NAME") ;

        try {
            customerSsn = EncryptUtil.ace256Dec(customerSsn);
        } catch (CryptoException e) {
            throw new McpCommonJsonException("9998" ,ACE_256_DECRYPT_EXCEPTION);
        }


        rtnMap.put("CSTMR_NM", MaskingUtil.getMaskedName(customerNm) );


        //외국인 청소년 구분
        if (customerSsn.length() > 6) {
            //나이 확인
            int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));

            //외국인도????
            String diviVal = customerSsn.substring(6, 7);
            logger.info("diviVal===>" + diviVal);
            logger.info("age===>" + age);

            if ("|5|6|7|8".indexOf(diviVal) > -1) {
                if (19 > age ) {
                    rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_NM);  //????? 있을까?????  외국인 청소년.. 구분 할수 없음 .. 없을것 같음
                } else {
                    rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_FN);
                }
            } else {
                if (19 > age ) {
                    rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_NM);
                } else {
                    rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_NA);
                }
            }
        } else {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_DESC", "주민번호 형식이 일치하지 않습니다. 합니다.  ");
            return rtnMap;
        }

        if (customerSsn.length() > 6) {
            customerSsn = customerSsn.substring(0,6);
        }

        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        rtnMap.put("CSTMR_SSN", MaskingUtil.getMaskedByValue(customerSsn,4,false) );


        return rtnMap;

    }

    /**
     * 설명 : 본인인증 정보 비교
     * @Author : papier
     * @Date : 2023.01.18
     * @param  esimDto : 인증한 정보
     * @param  contractNum : 계약 정보
     *                     계약정보와 인증한 정보가 일치 여부 확인
     * @return
     */
    @RequestMapping(value="/appForm/getContractAuthAjax.do")
    @ResponseBody
    public Map<String,Object> getContractInfoAuth(HttpServletRequest request, String contractNum, NiceLogDto niceLogDto
                              , @RequestParam(value = "cstmrType", required = false, defaultValue = "") String cstmrType
                              , @RequestParam(value = "cstmrName", required = false, defaultValue = "") String cstmrName
                              , @RequestParam(value = "cstmrNativeRrn", required = false, defaultValue = "") String cstmrNativeRrn
                              , @RequestParam(value = "orgName", required = false, defaultValue = "") String orgName
                              , @RequestParam(value = "orgRrn", required = false, defaultValue = "") String orgRrn      ){

        //인자값 확인
        if (StringUtils.isBlank(contractNum) ) {
            throw new McpCommonJsonException("9999" ,BIDING_EXCEPTION);
        }

        String temCustomerNm = cstmrName;
        String temCustomerSsn = cstmrNativeRrn;

        if (CSTMR_TYPE_NM.equals(cstmrType)) {
            temCustomerNm = orgName;
            temCustomerSsn = orgRrn;
        }

        //계약번호 사용자 정보 조회
        Map<String,Object> rtnMap = new HashMap<String, Object>();
        Map<String, String> resOjb= mypageUserService.selectContractObj("", "" ,contractNum) ;

        if (resOjb == null) {
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

        String customerSsn = resOjb.get("USER_SSN") ;
        String customerNm = resOjb.get("SUB_LINK_NAME") ;

        try {
            customerSsn = EncryptUtil.ace256Dec(customerSsn);
        } catch (CryptoException e) {
            throw new McpCommonJsonException("9998" ,ACE_256_DECRYPT_EXCEPTION);
        }

        String fullCustomerSsn = customerSsn;
        if (customerSsn.length() > 6) {
            customerSsn = customerSsn.substring(0,6);
        }


        /** 계약 번호 하고 검증  */
        if (!StringUtils.isBlank(temCustomerNm) || !StringUtils.isBlank(temCustomerSsn) ) {
            //데이터 쉐어링 인증값 검증
            if ( !customerNm.equals(temCustomerNm) || temCustomerSsn.indexOf(customerSsn) < 0 ) {
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_DESC", "인증한 정보가 상이 합니다. ");
                return rtnMap;
            }
        }


        //인증 정보 조회
        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);

        if (niceLogRtn == null) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_DESC", NICE_CERT_EXCEPTION_INSR);
            return rtnMap;
        }
        String autBirthDate = niceLogRtn.getBirthDateDec();
        String autName = niceLogRtn.getName();
        long niceHistSeq = niceLogRtn.getNiceHistSeq();


        if (autBirthDate == null || autName == null)  {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_DESC", NICE_CERT_EXCEPTION_INSR);
            return rtnMap;
        }

        /** 인증한 정보하고 검증
         * 법정 대리인 정보로...
         * 청소년 주민 번호 검증 필요 할까...
         * */
        if (CSTMR_TYPE_NM.equals(cstmrType)) {

            /**
             * 계약정보 주민 번호 검증 ...
             */
            int age = NmcpServiceUtils.getAge(fullCustomerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            if (19 > age ) {
                customerNm = cstmrName;  // 인자값..    이름(법정 대리인 )
                customerSsn = cstmrNativeRrn;  // 인자값.. 생년 월일 (법정 대리인 )
            }
        }


        if ( customerNm.equals(autName) && autBirthDate.indexOf(customerSsn) > -1 ) {
            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);

            //ParamR3   및 완료 일시   UPDATE 처리
            NiceLogDto niceLogUp = new NiceLogDto();
            niceLogUp.setNiceHistSeq(niceHistSeq);
            niceLogUp.setParamR3("custAuth");
            //시작 시간 저장 처리
            niceLogUp.setSysRdate(niceLogDto.getStartTimeToDate());
            nicelog.updateMcpNiceHist(niceLogUp);

            //session 생성
            NiceResDto niceResDtoSess = new  NiceResDto();
            niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
            niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
            niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
            niceResDtoSess.setName(niceLogRtn.getName());
            niceResDtoSess.setBirthDate(autBirthDate);
            niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
            niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
            niceResDtoSess.setParam_r3("custAuth");  //인자값
            niceResDtoSess.setGender(niceLogRtn.getGender());
            niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
            niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
            niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());

            // ============ STEP START ============
            Map<String, String> resultMap = certService.isAuthStepApplyUrl(request);
            if("Y".equals(resultMap.get("isAuthStep"))) {

                // 계약번호, 이름, 생년월일, CID
                String[] certKey = {"urlType", "contractNum", "name", "birthDate", "connInfo"};
                String[] certValue = {"chkMemSimpleAuth", contractNum, autName, autBirthDate, niceLogRtn.getConnInfo()};

                if (CSTMR_TYPE_NM.equals(cstmrType)) {
                    /**
                     * 청소년
                      */
                    certKey = new String[]{"urlType", "contractNum",  "connInfo"};
                    certValue = new String[]{"chkMemSimpleAuth", contractNum, niceLogRtn.getConnInfo()};
                }

                // 2024-12-03 nicePin 인증 스텝 확인(esimWatch 내국인, 데이터쉐어링)
                if(0 >= certService.getModuTypeStepCnt("nicePin", "")){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }


                Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                }

                // 본인인증유형, 인증종류, 이름, 생년월일, CI, reqSeq, resSeq (reqSeq와 resSeq는 참조용)
                certKey = new String[]{"urlType", "authType", "moduType", "name", "birthDate"
                                     , "connInfo", "reqSeq", "resSeq"};
                certValue = new String[]{"memSimpleAuth", niceLogRtn.getAuthType(), "custAuth", autName, autBirthDate
                                        , niceLogRtn.getConnInfo(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()};
                certService.vdlCertInfo("C", certKey, certValue);
            }
            // ============ STEP END ============

            // 본인인증 요청 알람(push) 인증완료 응답 건 로그 update (성공)
            if("N".equals(niceLogRtn.getAuthType()) || "A".equals(niceLogRtn.getAuthType()) || "T".equals(niceLogRtn.getAuthType())){

                NiceTryLogDto niceTryDto= new NiceTryLogDto();
                niceTryDto.setReqSeq(niceLogRtn.getReqSeq());
                niceTryDto.setResSeq(niceLogRtn.getResSeq());

                NiceTryLogDto niceTryRtn = nicelog.getMcpNiceTryHist(niceTryDto);

                if(niceTryRtn != null){
                    // 최종 성공으로 시도이력 update
                    niceTryDto.setNiceTryHistSeq(niceTryRtn.getNiceTryHistSeq());
                    niceTryDto.setSysRdate(niceLogDto.getStartTimeToDate());
                    niceTryDto.setFnlSuccYn("Y");
                    nicelog.updateMcpNiceTryHist(niceTryDto);
                }
            }

            SessionUtils.saveNiceRes(niceResDtoSess);
        } else {
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_DESC", "인증한 정보가 상이 합니다. ");
            return rtnMap;
        }


        return rtnMap;
    }


}
