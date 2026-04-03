package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_EXCEPTION_LIST_SOC_CODE;
import static com.ktmmobile.msf.system.common.constants.Constants.REQ_BUY_TYPE_USIM;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.INVALID_REFERER_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NOT_FOUND_DATA_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NO_SESSION_EXCEPTION;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
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
import com.ktmmobile.msf.form.newchange.service.AppformSvc;
import com.ktmmobile.msf.form.servicechange.dto.FarPricePlanResDto;
import com.ktmmobile.msf.form.servicechange.dto.MapWrapper;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpFarPriceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.msf.form.servicechange.service.MsfFarPricePlanService;
import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.rate.dto.RateAdsvcBnfitGdncDtlDTO;
import com.ktmmobile.msf.rate.dto.RateAdsvcBnfitGdncDtlXML;
import com.ktmmobile.msf.rate.service.RateAdsvcGdncService;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.McpRequestDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceChgDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceChgDto.M_messageDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscFarPriceResDto;
import com.ktmmobile.msf.system.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpCommonXmlVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpCustInfoAgreeVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.system.common.mspservice.MspService;
import com.ktmmobile.msf.system.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;

/**
 * 마이페이지 > 요금제/부가서비스 > 요금제 변경
 */

@Controller
public class MsfFarPricePlanController {

    private static final Logger logger = LoggerFactory.getLogger(MsfFarPricePlanController.class);

    @Autowired
    private IpStatisticService ipstatisticService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private AppformSvc appformSvc;

    @Autowired
    private MspService mspService;

    @Autowired
    private MsfMypageSvc mypageService;

    @Autowired
    MsfFarPricePlanService farPricePlanService;

    @Autowired
    RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MplatFormService mplatFormService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    /**
     * 요금제 변경 목록
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/farPricePlanView.do", "/m/mypage/farPricePlanView.do" })
    public String doFarPricePlanView(HttpServletRequest request, Model model,
                                     @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String returnUrl = "/portal/mypage/farPricePlanView";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/mypage/farPricePlanView";
        }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSessionDto != null) { // 취약성 318
            cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSessionDto);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // 요금제 정보
        McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(searchVO.getContractNum());
        if (mcpFarPriceDto == null) {
            throw new McpCommonException(NOT_FOUND_DATA_EXCEPTION);
        }


        //이용중인 부가서비스 조회
        MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
        try {
            getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(),searchVO.getCustId());
        } catch (SocketTimeoutException e) {
            throw new McpCommonException( COMMON_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException( COMMON_EXCEPTION);
        }

        if(getAddSvcInfo != null) {
            mcpFarPriceDto.setPromotionDcAmt(-getAddSvcInfo.getDiscountRate());
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("isPriceChange", "TRUE");
        model.addAttribute("mcpFarPriceDto", mcpFarPriceDto);

        return returnUrl;

    }

    /**
     * x89 해당하는 사용자 요금조회 조회_요금제 예약변경 정보 조회 Ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param prvRateCd
     * @return
     */

    @RequestMapping(value = { "/mypage/myRateResvViewAjax.do", "/m/mypage/myRateResvViewAjax.do" })
    @ResponseBody
    public HashMap<String, Object> doMyRateResvViewAjax(HttpServletRequest request, Model model,
                                                        @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                        @RequestParam(value = "prvRateCd", required = false) String prvRateCd) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSessionDto != null) { // 취약성 315
            cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
        }

        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSessionDto);
        if(!chk){
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION, url);
        }

        // 요금제 정보
        McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(searchVO.getContractNum());
        if (mcpFarPriceDto == null) {
            throw new McpCommonException(NOT_FOUND_DATA_EXCEPTION);
        }

        String prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();
        mcpFarPriceDto.setPrvRateCd(mcpFarPriceDto.getPrvRateCd());

        // 현재 요금제 정보 XML (데이터, 전화, 문자, seq)
        FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
        // 예약된 요금제 조회
        MoscFarPriceResDto outRes = farPricePlanService.doFarPricePlanRsrvSearch(searchVO);

        if (outRes.isSuccess()) {
            mcpFarPriceDto.setRsrvPrdcCd(outRes.getPrdcCd()); // 예약상품요금제코드
            mcpFarPriceDto.setRsrvPrdcNm(outRes.getPrdcNm()); // 예약 상품요금제명
            mcpFarPriceDto.setRsrvBasicAmt(outRes.getBasicAmt()); // 예약 요금제변경 대상기본료
            mcpFarPriceDto.setRsrvAplyDate(outRes.getAplyDate()); // 예약요금제 변경 신청일자
            mcpFarPriceDto.setRsrvEfctStDate(outRes.getEfctStDate()); // 예약요금제 변경 적용일자
        }

        // int total = 0;
        int total = mypageService.countFarPricePlanList(mcpFarPriceDto.getPrvRateCd());

        rtnMap.put("total", total);
        rtnMap.put("prvRateGrpNm", prvRateGrpNm);
        rtnMap.put("rateAdsvcLteDesc", StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "-"));
        rtnMap.put("rateAdsvcCallDesc", StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "-"));
        rtnMap.put("rateAdsvcSmsDesc", StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "-"));
        rtnMap.put("myPriceInfo", mcpFarPriceDto);
        rtnMap.put("prvRateGdncSeq", farPricePlanResDto.getRateAdsvcGdncSeq()); // 현재 요금제 안내 일련번호
        rtnMap.put("RESULT_CODE", "S");
        return rtnMap;

    }

    /**
     * 요금제 변경 확인 팝업
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param contractNum
     * @param instAmt
     * @param prvRateGrpNm
     * @param rateNm
     * @param baseAmt
     * @param rateAdsvcCd  : 변경할 요금제
     * @return
     */

    @RequestMapping(value = { "/mypage/regServicePop.do", "/m/mypage/regServicePop.do" })
    public String doRegServicePop(HttpServletRequest request, Model model,
                                  @RequestParam(value = "contractNum", required = true) String contractNum,
                                  @RequestParam(value = "instAmt", required = true) String instAmt,
                                  @RequestParam(value = "prvRateGrpNm", required = true) String prvRateGrpNm,
                                  @RequestParam(value = "rateNm", required = true) String rateNm,
                                  @RequestParam(value = "baseAmt", required = true) String baseAmt,
                                  @RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd,
                                  @RequestParam(value = "nxtRateGdncSeq", required = true) int nxtRateGdncSeq,
                                  @RequestParam(value = "prvRateGdncSeq", required = false, defaultValue = "0") int prvRateGdncSeq,
                                  @RequestParam(value = "isActivatedThisMonth", required = false, defaultValue = "N") String isActivatedThisMonth,
                                  @RequestParam(value = "isFarPriceThisMonth", required = false, defaultValue = "N") String isFarPriceThisMonth,
                                  @RequestParam(value = "chkRadion", required = false, defaultValue = "S") String chkRadion) {

        // 현재 요금제는 DB에서 가져오고, 바꿀 요금제는 xml에서 가져온다.
        // 현재 요금제의 안내 일련번호는 없을 수도 있음....

        String returnUrl = "/portal/mypage/regServicePop";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/regServicePop";
        }

        // 제휴요금제 확인여부
        String socAlliacYn = "";
        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(rateAdsvcCd);
        if (StringUtils.isNotEmpty(jehuProdInfo.getDtlCd())) {
            socAlliacYn = "Y";
        }

        // 기본 요금제 혜택
        RateAdsvcCtgBasDTO prvFarPriceDto= new RateAdsvcCtgBasDTO();

        try{
            prvFarPriceDto.setRateAdsvcGdncSeq(prvRateGdncSeq);
        } catch(DataAccessException e) {
            prvFarPriceDto.setRateAdsvcGdncSeq(0);
        } catch(Exception e){
            prvFarPriceDto.setRateAdsvcGdncSeq(0);
        }

        List<RateAdsvcBnfitGdncDtlXML> prvBnfitList = null;
        if(prvFarPriceDto.getRateAdsvcGdncSeq() > 0){ // 현재 요금제 일련번호가 존재하는 경우
            prvBnfitList= rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(prvFarPriceDto);
        }

        if(prvBnfitList != null && prvBnfitList.size() > 0) {
            for(RateAdsvcBnfitGdncDtlXML bnfitVo : prvBnfitList) {
                // 기본 요금제 혜택_데이터
                if("RATEBE01".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }
                // 기본 요금제 혜택_음성
                if("RATEBE02".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }
                // 기본 요금제 혜택_문자
                if("RATEBE03".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }
                // 기본 요금제 혜택_WiFi
                if("RATEBE04".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setBnfitWifi(bnfitVo.getRateAdsvcItemDesc());
                }
                /*
                프로모션 정보는 변경 팝업에서 보여주지 않으므로 주석 처리

                // 기본 요금제 혜택_프로모션 데이터
                if("RATEBE31".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setPromotionBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }
                // 기본 요금제 혜택_프로모션 음성
                if("RATEBE32".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setPromotionBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }
                // 기본 요금제 혜택_프로모션 문자
                if("RATEBE33".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    prvFarPriceDto.setPromotionBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }
                */
                // 기본 요금제 혜택_제휴혜택
                if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());
                    prvFarPriceDto.getBnfitList().add(bnfitDto);
                }
            }
        } // end of if------------------------------------------------

        // 변경할 요금제 혜택
        RateAdsvcCtgBasDTO nxtFarPriceDto= new RateAdsvcCtgBasDTO();
        try{
            nxtFarPriceDto.setRateAdsvcGdncSeq(nxtRateGdncSeq);
        }catch(DataAccessException e) {
            nxtFarPriceDto.setRateAdsvcGdncSeq(0);
        } catch(Exception e){
            nxtFarPriceDto.setRateAdsvcGdncSeq(0);
        }

        List<RateAdsvcBnfitGdncDtlXML> nxtBnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(nxtFarPriceDto);

        if(nxtBnfitList != null && nxtBnfitList.size() > 0) {
            for(RateAdsvcBnfitGdncDtlXML bnfitVo : nxtBnfitList) {
                // 변경할 요금제 혜택_데이터
                if("RATEBE01".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }
                // 변경할 요금제 혜택_음성
                if("RATEBE02".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }
                // 변경할 요금제 혜택_문자
                if("RATEBE03".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }
                // 변경할 요금제 혜택_WiFi
                if("RATEBE04".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setBnfitWifi(bnfitVo.getRateAdsvcItemDesc());
                }
                /*
                프로모션 정보는 변경 팝업에서 보여주지 않으므로 주석 처리

                // 변경할 요금제 혜택_프로모션 데이터
                if("RATEBE31".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setPromotionBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }
                // 변경할 요금제 혜택_프로모션 음성
                if("RATEBE32".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setPromotionBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }
                // 변경할 요금제 혜택_프로모션 문자
                if("RATEBE33".equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    nxtFarPriceDto.setPromotionBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }
                */

                // 변경할 요금제 혜택_제휴혜택 노출문구
                if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())) {
                    RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());
                    nxtFarPriceDto.getBnfitList().add(bnfitDto);
                }
            }
        } // end of if------------------------------------------------

        model.addAttribute("socAlliacYn", socAlliacYn);
        model.addAttribute("contractNum", contractNum);
        model.addAttribute("instAmtDisp", instAmt);
        model.addAttribute("instAmt", instAmt.trim().replaceAll(",", "").replaceAll("원", ""));
        model.addAttribute("prvRateGrpNm", prvRateGrpNm);
        model.addAttribute("rateNm", rateNm);
        model.addAttribute("baseAmtDisp", baseAmt);
        model.addAttribute("baseAmt", baseAmt.trim().replaceAll(",", "").replaceAll("원", ""));
        model.addAttribute("rateAdsvcCd", rateAdsvcCd);
        model.addAttribute("prvFarPriceDto", prvFarPriceDto);
        model.addAttribute("nxtFarPriceDto", nxtFarPriceDto);
        model.addAttribute("jehuProdType", jehuProdInfo.getDtlCd());
        model.addAttribute("jehuProdName", jehuProdInfo.getDtlCdNm());
        model.addAttribute("isActivatedThisMonth", isActivatedThisMonth); //이번달 개통여부
        model.addAttribute("isFarPriceThisMonth", isFarPriceThisMonth); //이번달 요금제 변경여부
        model.addAttribute("chkRadion", chkRadion); //P:예약, S:즉시변경

        return returnUrl;
    }

    /**
     * 요금제 조회_변경 대상 요금제 정보 Ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param mcpFarPriceDto
     * @return
     */

    @RequestMapping(value = { "/mypage/chgRateInfoViewAjax.do", " /m/mypage/chgRateInfoViewAjax.do" })
    @ResponseBody
    public HashMap<String, Object> chgRateInfoViewAjax(HttpServletRequest request, Model model,
                                                       @ModelAttribute("mcpFarPriceDto") McpFarPriceDto mcpFarPriceDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(mcpFarPriceDto.getPrvRateCd())) {
            throw new McpCommonJsonException("9999", INVALID_PARAMATER_EXCEPTION);
        }

        List<McpFarPriceDto> mcpFarPriceDtoList = mypageService.selectFarPricePlanList(mcpFarPriceDto.getPrvRateCd());

        rtnMap.put("RESULT_CODE", "S");
        rtnMap.put("DATA_OBJ_LIST", mcpFarPriceDtoList);

        return rtnMap;
    }

    /**
     * 요금제 예약 변경 신청 Ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param contractNum
     * @param soc
     * @param ftrNewParam
     * @param searchVO
     * @param request1
     * @return
     * @throws ParseException
     * @throws SocketTimeoutException
     */
    @RequestMapping(value = { "/mypage/farPricePlanResChgAjax.do", "/m/mypage/farPricePlanResChgAjax.do" })
    @ResponseBody
    public HashMap<String, Object> farPricePlanResChgAjax(HttpServletRequest request, Model model
            , @RequestParam(value = "contractNum", required = false) String contractNum
            , @RequestParam(value = "soc", required = false) String soc
            , @RequestParam(value = "ftrNewParam", required = false) String ftrNewParam
            , @RequestParam(required=false, defaultValue="") String befChgRateCd
            , @RequestParam(required=false, defaultValue="0") String befChgRateAmnt
            , @ModelAttribute MyPageSearchDto searchVO
            , HttpServletRequest request1)
            throws ParseException, SocketTimeoutException {



        // 요금제 예약변경시에 ftrNewParam 부가파라미터 보내는게 있는데
        // 어떤 요금제일떄 보내는거인지?? 상관이 있는지 없는지 확인 필요
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String ip = request1.getRemoteAddr();

        if (!StringUtil.isNotNull(contractNum)) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSessionDto != null) { // 취약성 320
            cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
        }
        searchVO.setNcn(contractNum);

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSessionDto);
        if(!chk){
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        String resultCode = "";
        String message = "";
        String userId = userSessionDto.getUserId();
        try {
            MoscFarPriceChgDto resOut = farPricePlanService.doFarPricePlanRsrvChg(searchVO, soc, ftrNewParam, ip,userId, befChgRateCd, befChgRateAmnt);

            List<M_messageDto> list = resOut.getMsgList();
            resultCode = "00";
            message = "";

            if (resOut.isSuccess()) {


//            	RESULT_CODE: "S"
//            		message: ""
//            		messge: "현재 선택한 요금제에서는 가입할 수 없는 부가서비스[LTE 데이터 추가제공 2GB]입니다."
//            		resultCode: "00"
//            		rsltCd: "N"
                resultCode = "00";// N:성공, S:시스템에서, E:Biz 에러
                if ("Y".equals(resOut.getRsltYn())) {
                    rtnMap.put("RESULT_CODE", "S");
                } else {
                    //실패..........
                    if (list != null && list.size() > 0) {
                        for (M_messageDto dto : list) {
                            if ("N".equals(dto.getRsltCd())) {
                                rtnMap.put("RESULT_CODE", "F");
                                rtnMap.put("rsltCd", dto.getRsltCd());
                                rtnMap.put("message2", dto.getRuleMsgSbst());
                            }
                        }
                    }
                }
            }
        } catch (SelfServiceException e) {
            resultCode = getErrCd(e.getMessage());// N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(e.getMessage());
        }

        rtnMap.put("resultCode", resultCode);
        rtnMap.put("message", message);

        return rtnMap;

    }

    /**
     * 요금제 예약 변경 부가서비스 일괄 처리
     * @author bsj
     * @Date : 2021.12.30
     * @param ipStatistic
     * @return
     * @throws InterruptedException
     */

    @RequestMapping(value = "/batch/resChangeAddPrdAjax.do")
    @ResponseBody
    public String resChangeAddPrd(McpIpStatisticDto ipStatistic ,@RequestParam( required=false, defaultValue="9")String modPara) throws InterruptedException  {

        if (StringUtils.isEmpty(ipStatistic.getResChgApyDate())) {
            String nowDate = DateTimeUtil.getFormatString("yyyyMM")+ "01";
            ipStatistic.setResChgApyDate(nowDate);
        }

        //1.예약정보 조회
        List<McpIpStatisticDto> changeAddPrdList = fCommonSvc.getRateResChgList(ipStatistic);

        int sCnt = 0;
        int fCnt = 0;
        int modInt = Integer.parseInt(modPara, 10);


        for (McpIpStatisticDto ipStatisticItem : changeAddPrdList) {
            int rateResChgSeq = Integer.parseInt(ipStatisticItem.getRateResChgSeq(), 10);


            /* 구분 처리
             * 다건 처리
             * 일련번호 기준으로
             * /batch/resChangeAddPrdAjax.do?modPara=0
             * /batch/resChangeAddPrdAjax.do?modPara=1
             * /batch/resChangeAddPrdAjax.do?modPara=2
             * /batch/resChangeAddPrdAjax.do?modPara=3
             */
            if ( modInt < 5) {
                if(rateResChgSeq % 4 != modInt)    continue;
            }


            //할인 금액 조회
            MspSaleSubsdMstDto rtnRateInfo = farPricePlanService.getRateInfo(ipStatisticItem.getResChgRateCd());

            try{

                String toPriceSocCode = ipStatisticItem.getResChgRateCd();//변경해야 할 요금제
                String userid = ipStatisticItem.getUserid();//등록한 아이디
                String svcCntrNo = ipStatisticItem.getSvcCntrNo();
                String rsltCd = "00000";
                String parameter = "" ;
                String contractNum = "";
                String nowPriceSocCode = "";  //현재 요금제

                // 검증 추가
                List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userid);

                if (cntrList != null && cntrList.size() > 0) {
                    for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                        if (StringUtil.equals(svcCntrNo, String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))) {
                            contractNum = mcpUserCntrMngDto.getContractNum();
                            //logger.info("userid["+userid+"]contractNum==>" + contractNum + "#svcCntrNo===>" + svcCntrNo);
                            break;
                        }
                    }
                } else {
                    rsltCd = "99991";
                    parameter = userid + " 회선 없음" ;
                }

                if (StringUtil.isBlank(contractNum)) {
                    rsltCd = "99992";
                    parameter = svcCntrNo + " 계약번호 없음" ;
                }
                //"99999"
                //"19. 요금 상품 변경 실패"


                if (StringUtil.equals("00000", rsltCd) ) {
                    //1.해당 회선이 실제 요금제 예약변경 대상 요금으로 요금제 변경 처리 되었는지 체크
                    //요금제조회
                    rsltCd = "99999";
                    parameter =  "19. 요금 상품 변경 실패" ;

                    McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(contractNum);
                    if (mcpFarPriceDto != null) {
                        nowPriceSocCode = mcpFarPriceDto.getPrvRateCd();
                    }
                }


                if (nowPriceSocCode.equals(toPriceSocCode)) {
                    /*
                     * 3. 결과 업데이트 처리
                     * 요금제가 성공적으로 처리 했으며 성공으로 처리
                     * 부가서비스 해지 , 가입 여부 상관 없이
                     */
                    ipStatisticItem.setBatchRsltCd("00000");
                    fCommonSvc.updateNmcpRateResChgBas(ipStatisticItem);

                    //202312 wooki - MSP_DIS_APD(평생할인 부가서비스 기적용 대상) insert START
                    String prmtId = mypageService.getChrgPrmtIdSocChg(toPriceSocCode); //프로모션아이디 가져오기
                    McpUserCntrMngDto apdDto = new McpUserCntrMngDto();
                    apdDto.setPrmtId(prmtId); //위에서 조회한 prmtId set - prmtId는 있을수도 있고 없을수도 있음
                    apdDto.setSocCode(toPriceSocCode);
                    apdDto.setContractNum(contractNum);
                    mypageService.insertDisApd(apdDto); //MSP_DIS_APD insert
                    //MSP_DIS_APD insert END

                    //2. 부가서비스 처리
                    Map<String, Object> resultMap = farPricePlanService.batchResChangeAddPrdCall(ipStatisticItem , rtnRateInfo.getPayMnthChargeAmt());

                    String resultCode = (String)resultMap.get("RESULT_CODE");


                    if ("00000".contentEquals(resultCode)) {
                        sCnt++;
                    } else {
                        fCnt++;
                    }

                } else {


                    ipStatisticItem.setBatchRsltCd(rsltCd);
                    fCommonSvc.updateNmcpRateResChgBas(ipStatisticItem);

                    //결과 저장
                    String prcsMdlInd = "BA_" + ipStatisticItem.getRateResChgSeq();
                    McpServiceAlterTraceDto serviceAlterTrace = new McpServiceAlterTraceDto();
                    serviceAlterTrace.setNcn(ipStatisticItem.getSvcCntrNo());
                    serviceAlterTrace.setContractNum(ipStatisticItem.getSvcCntrNo());
                    serviceAlterTrace.setSubscriberNo(ipStatisticItem.getMobileNo());
                    serviceAlterTrace.setPrcsMdlInd(prcsMdlInd);
                    serviceAlterTrace.setaSocCode(ipStatisticItem.getBefChgRateCd());
                    serviceAlterTrace.settSocCode(ipStatisticItem.getResChgRateCd());
                    serviceAlterTrace.setEventCode("FIN");
                    serviceAlterTrace.setTrtmRsltSmst("FAIL");
                    serviceAlterTrace.setaSocAmnt(ipStatisticItem.getBefChgRateAmnt());
                    serviceAlterTrace.settSocAmnt(rtnRateInfo.getPayMnthChargeAmt());
                    serviceAlterTrace.setParameter(parameter);
                    mypageService.insertServiceAlterTrace(serviceAlterTrace);


                    serviceAlterTrace.setChgType("R");  //-- (즉시 : I , 예약 : R)
                    serviceAlterTrace.setSuccYn("N");
                    mypageService.insertSocfailProcMst(serviceAlterTrace);

                    fCnt++;



                }
            } catch (Exception e){
                fCnt++;
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return sCnt +"" ;
    }

    /**
     *  요금제 즉시 변경 신청
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param toSocCode
     * @param paraAlterTrace
     * @return
     * @throws ParseException
     * @throws InterruptedException
     */

    @RequestMapping(value = { "/mypage/farPricePlanChgAjax.do", "/m/mypage/farPricePlanChgAjax.do" })
    @ResponseBody
    public Map<String, Object> proPriceChg(@ModelAttribute MyPageSearchDto searchVO, @RequestParam String toSocCode,
                                           @ModelAttribute McpServiceAlterTraceDto paraAlterTrace) throws ParseException, InterruptedException {

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("9999", NO_SESSION_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("9998", NOT_FULL_MEMBER_EXCEPTION);
        }

        /*
         * 번호유효성 체크 여부 확인
         */
        String sessionOtp = SessionUtils.getOtpInfo();
        if (!searchVO.getNcn().equals(sessionOtp)) {
            throw new McpCommonJsonException("9997", F_BIND_EXCEPTION);
        }

        // 2-1현재 요금제 조회
        McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(searchVO.getContractNum());
        String nowPriceSocCode = mcpFarPriceDto.getPrvRateCd();

        //월 1회 초과 체크
        Map<String, String> map = new HashMap<String, String>();
        map.put("soc", mcpFarPriceDto.getPrvRateCd());
        map.put("cntrNo", searchVO.getContractNum());

        //현재 요금제에 대한 상품 현행화 정보에서(MSP_JUO_FEATURE_INFO) 변경일 패치
        String getMonth = mypageService.selectFarPriceAddInfo(map);

        // 현재 날짜 YYYYMM
        Date nowDay = new Date();
        String thisMonth = DateTimeUtil.changeFormat(nowDay, "yyyyMM");

        //이번 달 개통일자 체크
        String isActivatedThisMonth = "N";

        // 개통일자가 이번 달에 해당하면 해당 계약은 요금제 변경 제한을 받지 않음(예약변경 가능, 즉시변경 불가능)
        for (McpUserCntrMngDto cntr : cntrList) {
            if (cntr.getContractNum().equals(searchVO.getContractNum())) {
                String lstComActvDate = cntr.getLstComActvDate();

                if (lstComActvDate != null && lstComActvDate.substring(0, 6).equals(thisMonth)) {
                    isActivatedThisMonth = "Y";
                }

                break;
            }
        }

        //당월 개통자는 즉시변경 불가
        if ("Y".equals(isActivatedThisMonth)) {
            rtnMap.put("RESULT_CODE", "9996");
            rtnMap.put("RESULT_MSG", "개통 당월에는 요금제 즉시 변경이 불가합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.");
            return rtnMap;
        }

        //당월 요금제 변경자는 즉시변경 불가
        if ("N".equals(isActivatedThisMonth) && thisMonth.equals(getMonth)) {
            rtnMap.put("RESULT_CODE", "9995");
            rtnMap.put("RESULT_MSG", "요금제는 월 1회만 변경 가능합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.");
            return rtnMap;
        }

        // tobe 서비스로 소스 변환
        // 해지할 부가 서비스 들 ?????데이득
        // 1.부가 서비스 해지 처리
        // 1-1. 부가 서비스 가입 여부
        // 1-2. 부가 서비스 해지 처리
        // 1-3. 현재에 대한 정보 NCN
        rtnMap = farPricePlanService.getAddSvcInfoDto(searchVO, toSocCode, nowPriceSocCode, paraAlterTrace);
        if (AJAX_SUCCESS.equals(rtnMap.get("RESULT_CODE"))) {
            MspRateMstDto rateInfo = mspService.getMspRateMst(toSocCode);
            if (StringUtils.isNotEmpty(rateInfo.getJehuProdType())) {
                try {
                    MpCustInfoAgreeVO agreeParam = MpCustInfoAgreeVO.builder()
                            .cnsgInfoAdvrRcvAgreYn("Y")
                            .build();
                    mplatFormService.moscContCustInfoAgreeChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), agreeParam);
                } catch (Exception ignored) {
                }
            }
        }

        return rtnMap;

    }


    /**
     * 요금제 예약 변경 취소 x90
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param contractNum
     * @return
     */

    @RequestMapping(value = { "/mypage/deleteMyRateResvAjax.do", "/m/mypage/deleteMyRateResvAjax.do" })
    @ResponseBody
    public HashMap<String, Object> deleteMyRateResvAjax(HttpServletRequest request, Model model,
                                                        @ModelAttribute MyPageSearchDto searchVO,
                                                        @RequestParam(value = "contractNum", required = true) String contractNum) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (!StringUtil.isNotNull(contractNum)) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        searchVO.setNcn(contractNum);
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null) { // 취약성 789
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        // 예약된 요금제 취소
        MpCommonXmlVO res = farPricePlanService.doFarPricePlanRsrvCancel(searchVO);

        if (res.isSuccess()) {
            rtnMap.put("RESULT_CODE", "S");
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        return rtnMap;

    }

    /**
     * 요금제 변경 첫번째 카테고리 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = { "/mypage/farPriceCtgTab1Ajax.do", "/m/mypage/farPriceCtgTab1Ajax.do" })
    @ResponseBody
    public MapWrapper farPriceCtgTab1Ajax(HttpServletRequest request, HttpServletResponse response) {
        MapWrapper mapWrapper = farPricePlanService.getCtgMapWrapper();
        return mapWrapper;
    }

    /**
     * 요금제 변경 두번째 카테고리 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param rateAdsvcCtgBasDTO
     * @param rateCd
     * @return
     * @throws JAXBException
     * @throws SocketTimeoutException
     */

    @RequestMapping(value = { "/mypage/farPriceCtgTab2Ajax.do", "/m/mypage/farPriceCtgTab2Ajax.do" })
    @ResponseBody
    public HashMap<String, Object> getFarPricePlanListAjax2(HttpServletRequest request, Model model,
                                                            @ModelAttribute("rateAdsvcCtgBasDTO") RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO,
                                                            @RequestParam(value = "rateCd", required = true) String rateCd)
            throws JAXBException, SocketTimeoutException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        List<RateAdsvcCtgBasDTO> buttonXmlList = null;

        if (rateAdsvcGdncService.getProdCount(rateAdsvcCtgBasDTO) == 0) { // 2Depth 카테고리에 상품목록이 없을경우
            rateAdsvcCtgBasDTO.setRateAdsvcDivCd("RATE");
            rateAdsvcCtgBasDTO.setDepthKey(2);
            buttonXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO);
        }

        rtnMap.put("upRateAdsvcCtgCd", rateAdsvcCtgBasDTO.getRateAdsvcCtgCd());
        rtnMap.put("buttonXmlList", buttonXmlList);
        return rtnMap;
    }

    /**
     * 요금제 변경 가능 목록 조회 Ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param rateAdsvcCtgBasDTO
     * @param rateCd
     * @return
     * @throws JAXBException
     * @throws SocketTimeoutException
     */

    @RequestMapping(value = { "/mypage/farPriceCtgTab3Ajax.do", "/m/mypage/farPriceCtgTab3Ajax.do" })
    @ResponseBody
    public HashMap<String, Object> getFarPricePlanListAjax3(HttpServletRequest request, Model model,
                                                            @ModelAttribute("rateAdsvcCtgBasDTO") RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO,
                                                            @RequestParam(value = "rateCd", required = true) String rateCd)
            throws JAXBException, IOException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        List<RateAdsvcCtgBasDTO> ctgXmlList = new ArrayList<RateAdsvcCtgBasDTO>();
        List<RateAdsvcCtgBasDTO> rtnXmlList = new ArrayList<RateAdsvcCtgBasDTO>();

        if (rateAdsvcGdncService.getProdCount(rateAdsvcCtgBasDTO) > 0) { // 2Depth 카테고리에 상품목록이 있을경우
            // 2depth 카테고리 목록 조회
            rateAdsvcCtgBasDTO.setDepthKey(2);
        } else {
            // 3depth 카테고리 목록 조회
            rateAdsvcCtgBasDTO.setDepthKey(3);
        }

        rateAdsvcCtgBasDTO.setRateAdsvcDivCd("RATE");
        ctgXmlList = rateAdsvcGdncService.getCtgXmlList(rateAdsvcCtgBasDTO);
        rtnXmlList = farPricePlanService.getStrCtgXmlList(rateAdsvcCtgBasDTO, ctgXmlList, rateCd);
        rtnMap.put("ctgXmlList", rtnXmlList);

        return rtnMap;
    }

    /**
     * 요금제 변경 가능목록 상세조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param rateAdsvcCtgBasDTO
     * @param rateCd
     * @return
     * @throws JAXBException
     */

    @RequestMapping(value = { "/mypage/farPriceContent.do", "/m/mypage/farPriceContent.do" })
    @ResponseBody
    public HashMap<String, Object> farPriceContent(HttpServletRequest request, HttpServletResponse response,
                                                   @ModelAttribute RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO,
                                                   @RequestParam(value = "rateCd", required = true) String rateCd) throws JAXBException, IOException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        List<RateAdsvcCtgBasDTO> prodXmlList = farPricePlanService.selectFarPricePlanList(rateAdsvcCtgBasDTO, rateCd);

        rtnMap.put("prodXmlList", prodXmlList);
        return rtnMap;
    }


    /**
     * 요금제 변경 가능 사전체크
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = { "/mypage/possibleStateCheckAjax.do", "/m/mypage/possibleStateCheckAjax.do" })
    @ResponseBody
    public Map<String, Object> possibleStateCheck(@ModelAttribute MyPageSearchDto searchVO) throws ParseException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        // 23시 45분부터 00시 15분까지는 상품변경이 불가합니다.<== 추가

        if (DateTimeUtil.isMiddleTime("23:29", "00:30")) { // 21
            rtnMap.put("RESULT_CODE", "006");
            rtnMap.put("RESULT_MSG", " 해당 시간은 번호변경이 불가 합니다. (23:30분 ~ 익일 00:30분, 1시간)");
            return rtnMap;
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("9999", NO_SESSION_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("9998", NOT_FULL_MEMBER_EXCEPTION);
        }

        // 1. 청소년
        if (mspService.checkKid(searchVO.getContractNum())) {
            rtnMap.put("RESULT_CODE", "001");
            rtnMap.put("RESULT_MSG", "만 19세이상 성인고객만 가능합니다.");
            return rtnMap;
        }

        /*
         * 2. 약정여부 : 단말/유심고객 중 무약정고객 진행. 약정고객(스폰서&simple)가입고객 변경 불가 팝업
         */
        MspJuoAddInfoDto enggInfo = mypageService.getEnggInfo(searchVO.getContractNum());

        if (enggInfo != null) {
            if ("Y".equals(enggInfo.getEnggYn())) {
                rtnMap.put("RESULT_CODE", "004");
                rtnMap.put("RESULT_MSG", "단말/유심 약정고객 요금제 변경이 불가능 합니다. ");
                return rtnMap;
            }
        }


        /**
         * 3. 요금제 변경 불가능 부가 서비스 확인
         *  arr.push("고객님께서는 고객센터(고객센터 114/무료) 를 통해 요금제 변경신청 가능하십니다.<br/>\n");
         *         arr.push("감사합니다.<br/><br/>");
         */
        boolean isPriceChange = true ;
        // 요금제 변경 불가능 socCode
        StringBuilder exceptionSocList = new StringBuilder("|");
        List<NmcpCdDtlDto> socCodeList = NmcpServiceUtils.getCodeList(GROUP_CODE_EXCEPTION_LIST_SOC_CODE);
        if (socCodeList != null) {
            for (NmcpCdDtlDto socCode : socCodeList) {
                exceptionSocList.append(socCode.getDtlCd());
                exceptionSocList.append( "|");
            }
        }

        //이용중인 부가서비스 조회
        MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
        try {
            getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(),searchVO.getCustId());
        } catch (SocketTimeoutException e) {
            throw new McpCommonException( COMMON_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException( COMMON_EXCEPTION);
        }

        if(getAddSvcInfo != null) {
            //요금제 변경 불가능 socCode 확인
            List<MpSocVO> getList = getAddSvcInfo.getList();
            if(getList !=null && !getList.isEmpty()) {
                for (MpSocVO socVo: getList) {
                    if (exceptionSocList.indexOf(socVo.getSoc()) > 0) {
                        isPriceChange = false ;
                        break;
                    }
                }
            }
        }

        if (!isPriceChange) {
            rtnMap.put("RESULT_CODE", "008");
            rtnMap.put("RESULT_MSG", "ExceptionListSocCode 요금제 변경 불가능한 부가서비스 존재 ");
            return rtnMap;
        }

        /**
         * 4. 결합 서비스 조회
         */
        // 결합 서비스 계약 조회 x87
        /*MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
        try {
            combSvcInfo = mplatFormService.moscCombSvcInfoList(searchVO.getCustId(), searchVO.getContractNum(), searchVO.getCtn() );
        } catch (SelfServiceException e) {
            throw new McpCommonException( COMMON_EXCEPTION);
        } catch (SocketTimeoutException e) {
            throw new McpCommonException( COMMON_EXCEPTION);
        }

        if (combSvcInfo != null && combSvcInfo.isSuccess()) {
            List<MoscCombDtlListOutDTO> combDtlList = combSvcInfo.getCombList();  //결합 리스트

            if (combDtlList !=null && combDtlList.size() > 0) {
                rtnMap.put("RESULT_CODE", "007");
                rtnMap.put("RESULT_MSG", "결합중인 회선은 마이페이지에서 요금제 변경이 불가합니다. 고객센터를 통해 요금제 변경 부탁드립니다. (고객센터 : 114)");
                return rtnMap;
            }
        }*/


        /*
         * 5. 상품 및 채널 a. 단말고객: 진행 b. 유심고객: 직영&제휴채널 4번진행 // 유통영업팀 산하 대리점을 통해 모집된 고객일 경우
         * 변경 불가 팝업
         */
        McpRequestDto requestInfo = appformSvc.getMcpRequestByContractNum(searchVO.getContractNum());
        // 값이 없을 경우 ???

        if (requestInfo != null) {
            if (REQ_BUY_TYPE_USIM.equals(requestInfo.getReqBuyType())) {
                MspJuoAddInfoDto channelInfo = mypageService.getChannelInfo(searchVO.getContractNum());
                /*
                 * 01 온라인 02 제휴
                 */
                if (channelInfo == null) {
                    rtnMap.put("RESULT_CODE", "005");
                    rtnMap.put("RESULT_MSG", "유통영업팀 산하 대리점을 통해 모집된 고객일 경우 변경 불가 ");
                    return rtnMap;
                } else {
                    if (!"01".equals(channelInfo.getChannelCd()) && !"02".equals(channelInfo.getChannelCd())) {
                        rtnMap.put("RESULT_CODE", "005");
                        rtnMap.put("RESULT_MSG", "유통영업팀 산하 대리점을 통해 모집된 고객일 경우 변경 불가");
                        return rtnMap;
                    }
                }
            }
        }

        //if (!"STG".equalsIgnoreCase(serverName)) { // STG 제외
            // 6. 91일이상고객만 가능
            /*
             * x83.회선 사용기간 조회 realUseDayNum 실사용기간
             */
            /*MoscWireUseTimeInfoRes moscWireUseTimeInfoRes = farPricePlanService.moscWireUseTimeInfo(searchVO.getNcn(),
                    searchVO.getCtn(), searchVO.getCustId());

            if (moscWireUseTimeInfoRes.getRealUseDayNum() != null) {
                String realUseDay = moscWireUseTimeInfoRes.getRealUseDayNum();

                int realUseDayInt = Integer.parseInt(realUseDay, 10);

                rtnMap.put("USE_DAY", realUseDay); // <== 검증을 위해 설정 향후 삭제
                rtnMap.put("USE_DAY_INT", realUseDayInt); // <== 검증을 위해 설정 향후 삭제
                if (realUseDayInt < 91) {
                    rtnMap.put("RESULT_CODE", "002");
                    rtnMap.put("RESULT_MSG", "무약정 고객 중 개통일로부터 91일이상 사용가능 ");
                    return rtnMap;
                }
            }*/

            // 7. 요금제 변경이력 관리 (월 1회초과)
            // 7-1. 현재 요금제 조회
            McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(searchVO.getContractNum());
            Map<String, String> map = new HashMap<String, String>();
            map.put("soc", mcpFarPriceDto.getPrvRateCd());
            map.put("cntrNo", searchVO.getContractNum());

            // 7-2. 현재 요금제에 대한 상품 현행화 정보에서(MSP_JUO_FEATURE_INFO) 변경일 패치
            String getMonth = mypageService.selectFarPriceAddInfo(map);

            // 현재 날짜 YYYYMM
            Date nowDay = new Date();
            String thisMonth = DateTimeUtil.changeFormat(nowDay, "yyyyMM");

            //이번 달 개통일자 체크
            String isActivatedThisMonth = "N";

            //이번 달 요금제 변경 체크
            String isFarPriceThisMonth = "N";

            //예약변경 가능, 즉시변경 불가능
            //그래도 요금제 변경 팝업은 노출되어야 함.
            for (McpUserCntrMngDto cntr : cntrList) {
                if (cntr.getContractNum().equals(searchVO.getContractNum())) {
                    String lstComActvDate = cntr.getLstComActvDate();

                    if (lstComActvDate != null && lstComActvDate.substring(0, 6).equals(thisMonth)) {
                        isActivatedThisMonth = "Y";
                    }

                    break;
                }
            }

            //이번달 요금제 변경 한 사람
            if ("N".equals(isActivatedThisMonth) && thisMonth.equals(getMonth)) {
                isFarPriceThisMonth = "Y";
            }

            rtnMap.put("isActivatedThisMonth",isActivatedThisMonth);
            rtnMap.put("isFarPriceThisMonth",isFarPriceThisMonth);

            /*if (thisMonth.equals(getMonth)) {
                rtnMap.put("RESULT_CODE", "003");
                rtnMap.put("RESULT_MSG", "요금제 변경은 한달에 한번만 가능합니다.");
                return rtnMap;
            }*/
        //}

        // 6. CHECK 여부 SESSION 저장
        /*
         * 인증번호 session저장 처리 체크 여부 확인 (보안)
         */
        SessionUtils.saveOtpInfo(searchVO.getNcn());
        rtnMap.put("RESULT_CODE", "S");

        return rtnMap;
    }

    /**
     * OTP 번호 확인 및 부가 서비스 가입 처리
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param cntrMng
     * @param otpNo
     * @return
     * @throws SocketTimeoutException
     */

    @ResponseBody
    @RequestMapping("/mypage/checkOtpAndReg.do")
    public Map<String, Object> checkOtpAndRegAjax(@ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                  @ModelAttribute McpUserCntrMngDto cntrMng, @RequestParam(value = "otpNo", required = true) String otpNo)
            throws SocketTimeoutException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001", INVALID_REFERER_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("0002", INVALID_REFERER_EXCEPTION);
        }

        String resultCode = "00";
        // 1.OTP번호 검증
        String sessionOtp = SessionUtils.getOtpInfo();
        if (sessionOtp == null || "".equals(sessionOtp)) {
            resultCode = "01";
            rtnMap.put("resultCode", resultCode);
            return rtnMap;
        }

        if (!otpNo.equals(sessionOtp)) {
            resultCode = "02";
            rtnMap.put("resultCode", resultCode);
            return rtnMap;
        }

        // 2.keyIn 회선정보 확인
        McpUserCntrMngDto rtnCntrMng = mypageService.selectCntrListNoLogin(cntrMng);
        if (rtnCntrMng == null) {
            resultCode = "03";
            rtnMap.put("resultCode", resultCode);
            return rtnMap;
        }

        // 3.자회선 부가 서비스 가입
        RegSvcChgRes regSvcChgSelfVO = null;
        String retvGubunCd = cntrMng.getRetvGubunCd();
        String soc = "";
        if ("G".equals(retvGubunCd)) {
            soc = "PL208J938";
        } else if ("R".equals(retvGubunCd)) {
            soc = "PL208J939";
        } else {
            throw new McpCommonJsonException("0002", INVALID_REFERER_EXCEPTION);
        }
        // x21 자회선 부가서비스 가입
        regSvcChgSelfVO = farPricePlanService.regSvcChgNe(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),
                soc, otpNo);
        // 결과 로그 저장 처리
        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        mcpIpStatisticDto.setPrcsMdlInd("REQUEST_SHAER_DATA");
        mcpIpStatisticDto.setParameter("NCN[" + searchVO.getNcn() + "]CTN[" + searchVO.getCtn() + "]SOC[" + soc
                + "]KEYIN[" + rtnCntrMng.getCntrMobileNo() + "]GLOBAL_NO[" + regSvcChgSelfVO.getGlobalNo()
                + "]RESLT_CD[" + regSvcChgSelfVO.getResultCode() + "]");
        mcpIpStatisticDto.setPrcsSbst(searchVO.getNcn() + "");
        if (regSvcChgSelfVO.isSuccess()) {
            mcpIpStatisticDto.setTrtmRsltSmst("REG_SVC");
            ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
        } else {
            mcpIpStatisticDto.setTrtmRsltSmst("REG_SVC_FAIL");
            ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
            resultCode = "04";
            rtnMap.put("resultCode", resultCode);
            rtnMap.put("RESULT_MSG", regSvcChgSelfVO.getSvcMsg());
            return rtnMap;
        }

        rtnMap.put("resultCode", resultCode);
        rtnMap.put("RESULT_MSG", "부가서비스 가입 완료");
        return rtnMap;
    }


    private ResponseSuccessDto getMessageBox() {
        ResponseSuccessDto mbox = new ResponseSuccessDto();

        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }

    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; // N:성공, S:시스템에서, E:Biz 에러
        return result;
    }

    private String getErrMsg(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        if (arg.length > 1) {
            result = arg[1];
        } else {
            result = arg[0];
        }
        return result;
    }

}
