package com.ktmmobile.mcp.etc.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscSdsInfoVo;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscSdsSvcPreChkVo;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;

@Controller
public class RecontractController {

    private static Logger logger = LoggerFactory.getLogger(RecontractController.class);

    public static final String COMM_AUTH_SMS_INFO = "COMM_AUTH_SMS_INFO";

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MspService mspService;

    @Autowired
    SmsSvc smsSvc ;

    @Autowired
    CertService certService;

    /**
     * 요금할인 재약정 신청
     */
    @RequestMapping(value = {"/m/rate/spnsrRecontract.do"})
    public String reSpnsrPlcyDc(Model model , HttpServletRequest request){

        List<NmcpCdDtlDto> presentList = NmcpServiceUtils.getCodeList(Constants.GROUP_CODE_PRESENT_CODE);
        String menuType = "rate"; // sms 인증창에서 사용
        model.addAttribute("menuType",menuType);
        model.addAttribute("presentList",presentList);
        return "/mobile/etc/spnsrRecontract";
    }

    @RequestMapping(value = "/rate/userSmsCheckAjax.do")
    @ResponseBody
    public Map<String, Object> userSmsCheckAjax(HttpServletRequest request
            ,@RequestParam(value = "custNm", required = false) String custNm
            ,@RequestParam(value = "phoneNum", required = false) String phoneNum
            ,@RequestParam(value = "certifySms", required = false) String certifySms
            ){

        String RESULT_CODE = "00000";
        String MSG = "";
        String menuType = "rate";

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        try{

            // ============ STEP START ============
            // 이름, 핸드폰번호
            String[] certKey = {"urlType", "name", "mobileNo"};
            String[] certValue = {"chkMemberAuth", custNm , phoneNum};
            Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                rtnMap.put("RESULT_CODE", "STEP01");
                rtnMap.put("MSG", vldReslt.get("RESULT_DESC"));
                return rtnMap;
            }
            // ============ STEP END ============

            String svcCntrNo = mypageUserService.selectSvcCntrNo(custNm,phoneNum);

            if(svcCntrNo==null ){
                RESULT_CODE = "00001";
                MSG = "고객정보가 없습니다.";
                rtnMap.put("RESULT_CODE", RESULT_CODE);
                rtnMap.put("MSG", MSG);
                return rtnMap;
            }

            AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);
            if (authSmsDto==null) {
                RESULT_CODE = "00002";
                MSG = "인증정보가 없습니다.";
                rtnMap.put("RESULT_CODE", RESULT_CODE);
                rtnMap.put("MSG", MSG);
                return rtnMap;
            }

            McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDescNoLogin(svcCntrNo);

            String soc = "";
            String rateNm = "";
            if(mcpUserCntrMngDto!=null){

                soc = mcpUserCntrMngDto.getSoc();
                rateNm = mcpUserCntrMngDto.getRateNm();
            } else {
                RESULT_CODE = "00003";
                MSG = "계약이 없습니다.";
                rtnMap.put("RESULT_CODE", RESULT_CODE);
                rtnMap.put("MSG", MSG);
                return rtnMap;
            }
            rtnMap.put("RESULT_CODE", RESULT_CODE);
            rtnMap.put("MSG", MSG);
            rtnMap.put("soc",soc);
            rtnMap.put("rateNm",rateNm);
            rtnMap.put("svcCntrNo",svcCntrNo);

            // ============ STEP START ============
            // 서비스계약번호
            certKey = new String[]{"urlType", "contractNum"};
            certValue = new String[]{"memberAuth", svcCntrNo};
            certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============

        }catch(Exception e){
            RESULT_CODE = "00004";
            MSG = "서비스 오류입니다.";
            rtnMap.put("RESULT_CODE", RESULT_CODE);
            rtnMap.put("MSG", MSG);
            return rtnMap;

        }

        return rtnMap;
    }

    /**
     * 심플할인 사전체크(X59) chargeDetailItemAjax
     *
     */
    @RequestMapping(value="/rate/moscSdsSvcPreChkAjax.do")
    @ResponseBody
    public Map<String, Object> moscSdsSvcPreChk(@ModelAttribute MyPageSearchDto myPageSearchDto,HttpServletRequest request) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/rate/moscSdsSvcPreChkAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            rtnMap.put("RESULT_CODE", "00003");
            rtnMap.put("RESULT_MSG", "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
            return rtnMap;
        }

        // 인증 받았는지 확인
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append("rate");

        AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());

        if( sessionAuthSmsDto == null ) {
            rtnMap.put("RESULT_CODE", "00005");
            rtnMap.put("RESULT_MSG", "휴대폰 인증 정보가 없습니다. ");
            return rtnMap;
        }

        try {

            // ============ STEP START ============
            // 계약번호
            String[] certKey = {"urlType", "contractNum"};
            String[] certValue = {"chkPreSimple", myPageSearchDto.getNcn()};
            Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
            }
            // ============ STEP END ============

            String svcCntrNo = StringUtil.NVL(myPageSearchDto.getNcn(),"");
            McpUserCntrMngDto dto = new McpUserCntrMngDto();
            dto.setSvcCntrNo(svcCntrNo);
            McpUserCntrMngDto resDto = mypageService.selectCntrListNoLogin(dto);



            if( resDto==null ){
                rtnMap.put("RESULT_CODE", "00002");
                rtnMap.put("RESULT_MSG", "DATA 정보가 없습니다.");
                return rtnMap;
            }

            String cntrMobileNo = resDto.getCntrMobileNo();
            String custId = resDto.getCustId();

            MpMoscSdsSvcPreChkVo moscSdsSvcPreChkVo = mPlatFormService.moscSdsSvcPreChk(svcCntrNo, cntrMobileNo, custId);



            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("GLOBAL_NO", moscSdsSvcPreChkVo.getGlobalNo());
            rtnMap.put("SBSC_YN", moscSdsSvcPreChkVo.getSbscYn());
            rtnMap.put("RESULT_MSG", moscSdsSvcPreChkVo.getResltMsg());
            return rtnMap;
        } catch (SelfServiceException e) {
           rtnMap.put("RESULT_CODE", "00002");
           rtnMap.put("RESULT_MSG", getErrMsg(e.getMessage()));
           rtnMap.put("RESULT_ERR_CD", getErrCd(e.getMessage()));
           return rtnMap;
       } catch (SocketTimeoutException e) {
           throw new McpCommonJsonException("0095" ,SOCKET_TIMEOUT_EXCEPTION);
       } catch(Exception e){
           throw new McpCommonJsonException("0095" ,SOCKET_TIMEOUT_EXCEPTION);
       }
    }

    /**
     * 심플할인 금액 조회
     *
     */
    @RequestMapping(value="/rate/getReSpnsrPriceInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getReSpnsrPriceInfoAjax(@ModelAttribute MyPageSearchDto myPageSearchDto
            ,@RequestParam(value = "rateSoc", required = false) String rateSoc
            ,@RequestParam(value = "engtPerd", required = false) String engtPerd
            ) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/rate/getReSpnsrPriceInfoAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
            return rtnMap;
        }

        try{

            // ============ STEP START ============
            // 서비스계약번호
            String[] certKey = {"urlType", "contractNum"};
            String[] certValue = {"getSimplePrice", myPageSearchDto.getNcn()};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
            }
            // ============ STEP END ============


            String svcCntrNo = StringUtil.NVL(myPageSearchDto.getNcn(),"");

            McpUserCntrMngDto dto = new McpUserCntrMngDto();
            dto.setSvcCntrNo(svcCntrNo);
            McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectCntrListNoLogin(dto);

            if( mcpUserCntrMngDto==null ){
                rtnMap.put("RESULT_CODE", "00002");
                rtnMap.put("RESULT_MSG", "DATA 정보가 없습니다.");
                return rtnMap;
            }

           //현재 요금제 조회
            McpUserCntrMngDto resDto = mypageService.selectSocDescNoLogin(svcCntrNo);


            if(resDto == null) {
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_MSG", "해당 사용자의 요금제 데이터가 없습니다.");
                return rtnMap;
            }

            //요금제 정보 조회
            MspRateMstDto mspRateMst = mspService.getMspRateMst(rateSoc) ;
            rtnMap.put("mspRateMst", mspRateMst);

            //할인 금액 확인
            MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();

            mspSaleSubsdMstDto.setAgrmTrm(engtPerd);//입력받은 할부기간을 약정기간
            mspSaleSubsdMstDto.setRateCd(rateSoc);//요금제코드
            List<MspSaleSubsdMstDto> chargeList = mspService.listMspSaleMst(mspSaleSubsdMstDto);

            MspSaleSubsdMstDto saleSubsdMst = null;
            if (chargeList != null && chargeList.size() > 0 ) {
               saleSubsdMst = chargeList.get(0);
               saleSubsdMst.setBaseAmt(mspRateMst.getBaseAmt());
            }

            if ("LOCAL".equals(serverName) && saleSubsdMst == null ) {
                saleSubsdMst = new MspSaleSubsdMstDto();
                saleSubsdMst.setRateCd("KTI3GM035");
                saleSubsdMst.setRateNm("M 3G 망내 38");
                saleSubsdMst.setAgrmTrm("12");
                saleSubsdMst.setDcAmt(9000);
                saleSubsdMst.setAddDcAmt(5200);
            }

            rtnMap.put("saleSubsdMst", saleSubsdMst);

        }catch(Exception e){
            logger.debug("error");
        }

       return rtnMap;

    }

    /**
     * 심플할인 가입(X60) moscSdsSvcChg
     *
     */
    @RequestMapping(value="/rate/moscSdsSvcChgAjax.do")
    @ResponseBody
    public Map<String, Object> moscSdsSvcChgAjax(
            @ModelAttribute("searchVO") MyPageSearchDto myPageSearchDto
            , McpRequestAgrmDto mcpRequestAgrm
            ,@RequestParam(value = "engtPerd", required = false) String engtPerd
            ,@RequestParam(value = "mCode", required = false) String mCode
            ) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/rate/moscSdsSvcChgAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
            return rtnMap;
        }

        try {

            // ============ STEP START ============
            // 서비스계약번호, 전화번호
            String[] certKey = {"urlType", "contractNum", "mobileNo"};
            String[] certValue = {"saveSimpleForm", myPageSearchDto.getNcn(), myPageSearchDto.getCtn()};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
            }
            // ============ STEP END ============

            String svcCntrNo = myPageSearchDto.getNcn();
            McpUserCntrMngDto dto = new McpUserCntrMngDto();
            dto.setSvcCntrNo(svcCntrNo);
            McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectCntrListNoLogin(dto);

            if( mcpUserCntrMngDto==null ){
                rtnMap.put("RESULT_CODE", "00001");
                rtnMap.put("RESULT_MSG", "DATA 정보가 없습니다.");
                return rtnMap;
            }

            String custId = mcpUserCntrMngDto.getCustId();

            AuthSmsDto authSmsDto = SessionUtils.getSmsSession(mCode);
            if (authSmsDto==null) {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_MSG", "휴대폰 인증 정보가 없습니다. ");
                return rtnMap;
            }

            MpCommonXmlVO commonXmlVO = mPlatFormService.moscSdsSvcPreChk(svcCntrNo, myPageSearchDto.getCtn(), custId,engtPerd) ;
            mcpRequestAgrm.setGlobalNo(commonXmlVO.getGlobalNo());
            mcpRequestAgrm.setContractNum(svcCntrNo);
            mcpRequestAgrm.setOrderType("02"); // 문자발송으로 신청되는 경우
            if(mypageService.insertMcpRequestArm(mcpRequestAgrm)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RESULT_MSG", commonXmlVO.getResultCode());
            } else {
                //재약정 취소 처리
                MpCommonXmlVO commonXmlVO2 = mPlatFormService.moscSdsSvcCanChg(svcCntrNo, myPageSearchDto.getCtn(), custId);
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_MSG", "실패 하였습니다. 다시 시도 하시기 바랍니다. ");
                rtnMap.put("RESULT_MSG2", commonXmlVO2.getResultCode());
            }

            return rtnMap;
        } catch (SelfServiceException e) {
            throw new McpCommonJsonException("0096" ,e.getMessage());
        } catch (SocketTimeoutException e) {
            throw new McpCommonJsonException("0095" ,SOCKET_TIMEOUT_EXCEPTION);
        } catch(Exception e){
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "실패 하였습니다. 다시 시도 하시기 바랍니다. ");
            return rtnMap;
        }
    }


    /**
     * 완료화면
     *
     */
    @RequestMapping(value = {"/m/rate/spnsrRecontractComplete.do"})
    public String reSpnsrPlcyDcComplete(HttpServletRequest request, ModelMap model, @ModelAttribute MyPageSearchDto myPageSearchDto
                                                    ,@RequestParam(value = "svcCntrNo", required = false) String svcCntrNo ){


        try {

            // ============ STEP START ============
            // 1. 최소 스텝 수 체크
            if(certService.getStepCnt() < 7 ){
               throw new McpCommonException(STEP_CNT_EXCEPTION, "/m/rate/spnsrRecontract.do");
            }

            // 2. 최종 데이터 체크: 스텝종료 여부, 서비스계약번호
            String[] certKey = {"urlType", "stepEndYn", "contractNum"};
            String[] certValue = {"completeRecontract", "Y", svcCntrNo};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) throw new McpCommonException(vldReslt.get("RESULT_DESC"), "/m/rate/spnsrRecontract.do");
            // ============ STEP END ============

            // 회선정보 조회
            McpUserCntrMngDto dto = new McpUserCntrMngDto();
            dto.setSvcCntrNo(svcCntrNo);
            McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectCntrListNoLogin(dto);

            String cntrMobileNo = mcpUserCntrMngDto.getCntrMobileNo();
            String custId = mcpUserCntrMngDto.getCustId();

            // 요금정보 조회
            McpUserCntrMngDto mcpUserCntrMngDto1 = mypageService.selectSocDescNoLogin(svcCntrNo);

            String soc = "";
            String rateNm = "";
            if(mcpUserCntrMngDto1 != null){
                soc = mcpUserCntrMngDto1.getSoc();
                rateNm = mcpUserCntrMngDto1.getRateNm();
                mcpUserCntrMngDto.setRateNm(rateNm);
                mcpUserCntrMngDto.setSubLinkName(StringMakerUtil.getName(mcpUserCntrMngDto1.getSubLinkName()));
            }

            //X62. 심플할인 정보조회(X62)
            MpMoscSdsInfoVo moscSdsInfo = mPlatFormService.moscSdsInfo(svcCntrNo, cntrMobileNo, custId) ;

            BigDecimal dcSuprtAmt = new BigDecimal(moscSdsInfo.getDcSuprtAmt() + ""); //월 할인 금액
            BigDecimal addRate = new BigDecimal("1.1"); //부가가치세율
            int dcSuprtAmtVat = dcSuprtAmt.multiply(addRate).setScale(0, RoundingMode.UP).intValue();




            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(cntrMobileNo));

            model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
            model.addAttribute("moscSdsInfo", moscSdsInfo);
            model.addAttribute("dcSuprtAmtVat", dcSuprtAmtVat);

            // 문자 발송
            try{

                MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SPNSR_RECONTRACT);
                if (mspSmsTemplateMstDto != null) {

                    String engtPerdMonsNum = moscSdsInfo.getEngtPerdMonsNum();
                    DecimalFormat decimalFormat = new DecimalFormat("###,###");
                    String strDcSuprtAmtVat = String.valueOf(decimalFormat.format(dcSuprtAmtVat));
                    String text = mspSmsTemplateMstDto.getText();
                    String smsMsg = String.format(text, engtPerdMonsNum,strDcSuprtAmtVat);

                    //smsSvc.sendLms( mspSmsTemplateMstDto.getSubject(), cntrMobileNo, smsMsg , mspSmsTemplateMstDto.getCallback() );
                    smsSvc.sendLms( mspSmsTemplateMstDto.getSubject(), cntrMobileNo, smsMsg ,
                            mspSmsTemplateMstDto.getCallback(),String.valueOf(Constants.SPNSR_RECONTRACT),"SYSTEM");
                }

            }catch(Exception e){
                logger.error("error=>"+e.getMessage());
            }

        } catch (SelfServiceException e) {
            logger.error("위약금 조회 오류 SelfServiceException :: " + e.getMessage());
        } catch (SocketTimeoutException e){
            throw new McpCommonJsonException("0095" ,SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            logger.error("위약금 조회 오류 :: " + e.getMessage());
        }

        return "/mobile/etc//spnsrRecontractComplete";

    }


    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
    }

    private String getErrMsg(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        if(arg.length > 1) {
            result = arg[1];
        } else {
            result = arg[0];
        }
        return result;
    }


}
