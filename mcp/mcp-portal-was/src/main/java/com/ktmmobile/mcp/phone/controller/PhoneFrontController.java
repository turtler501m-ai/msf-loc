package com.ktmmobile.mcp.phone.controller;

import static com.ktmmobile.mcp.common.constants.Constants.OPER_TYPE_EXCHANGE;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.*;
import static com.ktmmobile.mcp.phone.dto.OrderEnum.CHARGE_ROW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpProdCommendDto;
import com.ktmmobile.mcp.common.mspservice.dto.*;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.cprt.dto.AlliCardContDto;
import com.ktmmobile.mcp.cprt.service.CprtService;
import org.apache.commons.lang.StringEscapeUtils;
//
//import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
import com.ktmmobile.mcp.phone.dto.PhoneMspDto;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;
import com.ktmmobile.mcp.phone.dto.PhoneSntyBasDto;
import com.ktmmobile.mcp.phone.dto.stragety.ListOrderNeEnum;
import com.ktmmobile.mcp.phone.service.PhoneService;
import com.ktmmobile.mcp.requestReview.service.RequestReviewService;


/**
 * @Class Name : PhoneFrontController
 * @Description : 상품 Front Controller
 *
 * @author : ant
 * @Create Date : 2016. 1. 20.
 */
@Controller
public class PhoneFrontController {

    private static Logger logger = LoggerFactory.getLogger(PhoneFrontController.class);

    /**조직 코드  */
    @Value("${sale.orgnId}")
    private String orgnId;

    /**자급제 조직 코드  */
    @Value("${sale.sesplsOrgnId}")
    private String sesplsOrgnId;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    PhoneService phoneService;

    @Autowired
    MspService mspService;

    @Autowired
    private CprtService cprtService;

    @Autowired
    FCommonSvc fCommonSvc;

    @Autowired
    RequestReviewService requestReviewService;

    @Autowired
    private AppformSvc appformSvc;

    /**
     * @Description : 상품리스트
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = "/product/phone/phoneList.do")
    public String phoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result, @RequestParam(defaultValue = "") String rateCd,
            @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
            Model model) {

            //refurPhoneList.do

        String reDirectUrl = "/product/phone/phoneList.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            reDirectUrl = "/m/product/phone/phoneList.do";
        }

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(reDirectUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        model.addAttribute("mspOrgList", mspService.findMspOrgListRe("01")); //  제조사 정보 공시
        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P001"));
        model.addAttribute("rateCd", rateCd);
        model.addAttribute("prodType", "01");//휴대폰

        String returnUrl = "/portal/phone/phoneList";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/phone/phoneList";
        }

           return returnUrl;

    }


    /**
     * @Description : 상품리스트
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = {"/product/phone/refurPhoneList.do","/m/product/phone/refurPhoneList.do"})
    public String refurPhoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result, @RequestParam(defaultValue = "") String rateCd,
                            @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                            Model model) {

        //refurPhoneList.do

        String reDirectUrl = "/product/phone/refurPhoneList.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            reDirectUrl = "/m/product/phone/refurPhoneList.do";
        }

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(reDirectUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        model.addAttribute("mspOrgList", mspService.findMspOrgListRe("01")); //  제조사 정보 공시
        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P005"));
        model.addAttribute("rateCd", rateCd);
        model.addAttribute("prodType", "06");   //리퍼폰

        String returnUrl = "/portal/phone/phoneList";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/phone/phoneList";
        }

        return returnUrl;

    }

    /**
     * 설명 : 공시지원금 화면
     * @author key
     * @Date 2021.12.30
     * @param mspNoticSupportMstDto
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value="/product/phone/officialNoticeSupportList.do")
    public String officialNoticeSupportList(@ModelAttribute MspNoticSupportMstDto mspNoticSupportMstDto, BindingResult result,Model model) {

          //요금제 목록 조회
          List<MspNoticSupportMstDto> rateList = mspService.listMspOfficialSupportRateNm();

          model.addAttribute("rateList", rateList);

          return "/portal/phone/officialNoticeSupportList";
    }

    /**
     * @Description : 상품리스트
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = "/product/phone/usedPhoneList.do")
    public String usedPhoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result, @RequestParam(defaultValue = "") String rateCd,
            @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
            Model model) {

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/product/phone/usedPhoneList.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        model.addAttribute("mspOrgList", mspService.findMspOrgListRe("04")); // 제조사 정보 중고폰
        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P003")); // bannCtg P001 공시지원금목록 데이터없어서 일단M001로
        model.addAttribute("rateCd", rateCd);																			// 임시처리

        return "/portal/phone/usedPhoneList";
    }


    /**
     * @Description : 상품리스트
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = "/product/phone/exhibitList.do")
    public String exhibitList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result, @RequestParam(defaultValue = "") String rateCd,
                                @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                                Model model) {

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/product/phone/exhibitList.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P004")); // P004
        model.addAttribute("rateCd", rateCd);																			// 임시처리

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/phone/exhibitList";
        } else {
            return "/portal/phone/exhibitList";
        }

    }

    /**
     * @Description : 상품리스트
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = "/product/phone/selfPhoneList.do")
    public String selfPhoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result, @RequestParam(defaultValue = "") String rateCd,
            @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
            Model model) {

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/product/phone/selfPhoneList.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        model.addAttribute("mspOrgList", mspService.findMspOrgListRe("05")); // 제조사 정보 자급제
        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P002"));
        model.addAttribute("rateCd", rateCd);

        return "/portal/phone/selfPhoneList";
    }

    /**
     * @Description : 상품리스트
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = "/product/phone/phoneCompareList.do")
    public String phoneCompareList(Model model,HttpServletRequest request) {

        return "/portal/phone/phoneCompareList";
    }

    /**
     * @Description : 상품상세 페이지
     * @param phoneProdBasDto
     * @param result
     * @param operType
     * @param hndsetModelId
     * @param instNom
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 2. 16.
     */
    @RequestMapping(value = "/product/phone/phoneView_old.do")
    public String phoneView(@ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
            @RequestParam(defaultValue = "") String operType, @RequestParam(defaultValue = "") String hndsetModelId,
            @RequestParam(defaultValue = "") String instNom, @RequestParam(defaultValue = "") String bannerCd,
            @RequestParam(defaultValue = "") String rateCd, @RequestParam(defaultValue = "") String sprtTp,
            Model model) {

        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }


        String orgnId2 = orgnId;
        //String plcySctnCd = PhoneConstant.PHONE_FOR_MSP;


        /*
         * ## 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
         */
        PhoneProdBasDto phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

        if (phoneProdBas == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION);
        }
        if (phoneProdBas.doNotSale()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP);
        if (PROD_LTE_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(LTE_FOR_MSP);
        } else if (PROD_3G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(THREE_G_FOR_MSP);
        } else if (PROD_5G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(FIVE_G_FOR_MSP);
        } else if (PROD_LTE5G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(LTEFIVE_G_FOR_MSP);
        }
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP);
        mspSalePlcyMstDto.setOrgnId(orgnId);

        // 자급제폰인 경우 강제 세팅
        String sesplsYn = phoneProdBas.getSesplsYn();
        if(sesplsYn == null) {
            sesplsYn = "";
        }
        String prodType = phoneProdBas.getProdType();


        /*
         * ## 2.정책 조회 (가입유형 조회) ## 이하 아래는 정책정보를 조회 하는 부분이다. 상품별 정책은(단말할인,요금할인)에 따라 최대
         * 2개까지 나올 가능성이 있다. 해당 단품에 정책이 2개가 연결되있을경우 단말할인을 으로 선택해서 조회 한다. 정책조회 정보에 [가입유형
         * 가능여부]가 포함되있다.
         */
        String hndsetModelIdParam = hndsetModelId;
        if ("".equals(hndsetModelId)) { // 단말(nrds)id가 없을경우 단품을 순회하면서 대표속성을 찾아서 세팅
            for (PhoneSntyBasDto snty : phoneProdBas.getPhoneSntyBasDtosList()) {
                if ("Y".equals(snty.getRprsPrdtYn())) {
                    hndsetModelIdParam = snty.getHndsetModelId();
                }
            }
        }

        // 현재 단품 ID 로 해당 대표모델ID를 조회한다.
        PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(hndsetModelIdParam);

        if (phoneMspDto == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION);
        }
        String rprsPrdtIdCurrent = phoneMspDto.getRprsPrdtId(); // 해당 단품의 대표모델ID

        phoneProdBas.setHndsetModelId(hndsetModelIdParam);// 대표상품을 현대 BasDto 에 핸드셋 모델ID로 set


        // 자급제인 경우 강제 세팅
        if("Y".equals(sesplsYn)) {
            mspSalePlcyMstDto.setOrgnId(sesplsOrgnId);
            mspSalePlcyMstDto.setPlcySctnCd("02");
        }

        MspSaleDto mspSaleDto = mspService.getMspSale(rprsPrdtIdCurrent, mspSalePlcyMstDto);

        /*
         * ## 3.단말할부기간조회 ##
         */
        // List<MspSaleAgrmMst> mspSaleAgrmMstList =
        // mspService.listMspSaleAgrmMst(mspSaleDto.getMspSalePlcyMstDto().get(0).getSalePlcyCd());

        List<MspSaleAgrmMst> mspSaleAgrmMstList = null;
        if("Y".equals(sesplsYn)) {
            // 자급제인 경우 강제로 변환 처리
            //판매정책코드
            String salePlcyCd = mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd();
            MspSaleAgrmMst mspSaleAgrmMst = new MspSaleAgrmMst();
            mspSaleAgrmMst.setSalePlcyCd(salePlcyCd);
            mspSaleAgrmMst.setOrgnId(sesplsOrgnId); // MSP_SALE_ORGN_MST@DL_MSP - ORG.ORGN_ID  = 'V000019481'

            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst2(mspSaleAgrmMst);

        } else {
            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst(mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd());
        }


        /*
         * ## 4.요금리스트 조회 ##
         */
        String instNomParam = instNom;

        if ("".equals(instNomParam)) {
            if(mspSaleAgrmMstList.size() == 1) {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();
            } else {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();// 리스트의 요금제정보의 할부개월은 모두같은것을 조회 했기때문에 임의이 1개의를 가져와 set
                if ("0".equals(instNomParam)) { // 임시로 처리 하는중 데이터가 없음
                    instNomParam = mspSaleAgrmMstList.get(1).getInstNom();
                }
            }
        }


        String operTypeParam = operType;
        if ("".equals(operType)) { // 가입유형이 없을경우
            if ("Y".equals(mspSaleDto.getMspSalePlcyMstDtoSimbol().getMnpYn())) { // 번호이동이 우선수위가 제일 높다.번호이동여부가 Y 일경우
                                                                                    // 업무유형을 번호이동코드로 set
                operTypeParam = OPER_PHONE_NUMBER_TRANS;
            } else if ("Y".equals(mspSaleDto.getMspSalePlcyMstDtoSimbol().getNewYn())) {
                operTypeParam = OPER_NEW;
            } else if ("Y".equals(mspSaleDto.getMspSalePlcyMstDtoSimbol().getHdnYn())) {
                operTypeParam = OPER_TYPE_EXCHANGE;
            }
        }


        // 자급제폰인 경우 강제 세팅
        if("Y".equals(sesplsYn)) {
            orgnId2 = sesplsOrgnId; // 기관코드
            //sprtTp = ""; // 할인유형 (단말할인)
            //plcySctnCd = "02"; // 유심으로 설정
        }


        List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId2,
                operTypeParam, instNomParam, null, null);

        // 최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
        PhoneProdBasDto temp = new PhoneProdBasDto();
        temp.setMspSaleSubsdMstListForLowPrice(chargeList);
        temp.setOrderEnum(CHARGE_ROW);
        temp.doSort();

        //최고가 최저가
        if(!"Y".equals(sesplsYn)) {
            MspSaleSubsdMstDto maxObj = chargeList.stream().max(Comparator.comparing(v -> v.getInstAmt())).get();
            MspSaleSubsdMstDto minObj = chargeList.stream().min(Comparator.comparing(v -> v.getInstAmt())).get();

            model.addAttribute("maxValue", maxObj.getInstAmt());
            model.addAttribute("minValue", minObj.getInstAmt());
        }

        phoneProdBas.setSntyDisp(StringEscapeUtils.unescapeHtml(phoneProdBas.getSntyDisp()));
        model.addAttribute("phoneProdBas", phoneProdBas);
        model.addAttribute("mspSaleAgrmMstList", mspSaleAgrmMstList);
        model.addAttribute("chargeList", chargeList);
        model.addAttribute("operType", operTypeParam);
        model.addAttribute("hndsetModelId", hndsetModelIdParam);
        model.addAttribute("sprtTp", sprtTp);
        model.addAttribute("rateCd", rateCd);

        return "/portal/phone/phoneView";
    }



    /* 휴대폰 간소화 샘플	 시작 */
    @RequestMapping(value = {"/product/phone/phoneView.do","/m/product/phone/phoneView.do"})
    public String phoneViewNe(
            NmcpProdCommendDto prodCommendDto,
            @ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
            @RequestParam(defaultValue = "") String operType, @RequestParam(defaultValue = "") String hndsetModelId,
            @RequestParam(defaultValue = "") String instNom, @RequestParam(defaultValue = "") String bannerCd,
            @RequestParam(defaultValue = "") String rateCd, @RequestParam(defaultValue = "KD") String sprtTp,
            Model model) {

        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        String hndsetModelIdParam = hndsetModelId;
        String operTypeParam = operType;

        // 임시 저장 정보 패치
        if (StringUtils.isBlank(prodCommendDto.getPrdtSctnCd())) {
            prodCommendDto.setPrdtSctnCd("LTE");
        }

        if (StringUtils.isNotBlank(prodCommendDto.getRateCd())) {
            MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(prodCommendDto.getRateCd());
            if (rateInfo != null && rateInfo.getDataType() != null) {
                prodCommendDto.setPrdtSctnCd(rateInfo.getDataType());
            }
        }

        String instNomParam = instNom;
        String sprtTpParam = sprtTp;
        if (prodCommendDto.getRequestKey() != null && !"".equals(prodCommendDto.getRequestKey())) {
            //임시저장키로 데이터 셋팅
            AppformReqDto appformReqTemp = appformSvc.getAppFormTemp(Long.parseLong(prodCommendDto.getRequestKey()));
            if (appformReqTemp != null) {
                prodCommendDto.setOnOffType(appformReqTemp.getOnOffType());
                prodCommendDto.setOperType(appformReqTemp.getOperType());
                prodCommendDto.setModelId(appformReqTemp.getModelId());
                prodCommendDto.setPrdtSctnCd(appformReqTemp.getPrdtSctnCd());
                prodCommendDto.setModelMonthly(appformReqTemp.getModelMonthly());
                prodCommendDto.setRateCd(appformReqTemp.getSocCode());
                phoneProdBasDto.setProdId(appformReqTemp.getProdId());
                sprtTpParam = appformReqTemp.getSprtTp();
                instNomParam = String.valueOf(appformReqTemp.getEnggMnthCnt());

                /*hndsetModelId = appformReqTemp.getModelId();
                operType = appformReqTemp.getOperType();*/
                hndsetModelIdParam = appformReqTemp.getModelId();
                operTypeParam = appformReqTemp.getOperType();
            }
        }

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            if ("5".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("7");
            } else if ("0".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("3");
            } else if ("6".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("8");
            }
        } else {
            if ("7".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("5");
            } else if ("3".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("0");
            } else if ("8".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("6");
            }
        }

        model.addAttribute("ProdCommendDto", prodCommendDto);

        String orgnId2 = orgnId;
        /*
         * ## 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
         */
        PhoneProdBasDto phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

        if (phoneProdBas == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION);
        }
        if (phoneProdBas.doNotSale()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP);
        if (PROD_LTE_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(LTE_FOR_MSP);
        } else if (PROD_3G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(THREE_G_FOR_MSP);
        } else if (PROD_5G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(FIVE_G_FOR_MSP);
        } else if (PROD_LTE5G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(LTEFIVE_G_FOR_MSP);
        }
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP);
        mspSalePlcyMstDto.setOrgnId(orgnId);

        /*
         * ## 2.정책 조회 (가입유형 조회) ## 이하 아래는 정책정보를 조회 하는 부분이다. 상품별 정책은(단말할인,요금할인)에 따라 최대
         * 2개까지 나올 가능성이 있다. 해당 단품에 정책이 2개가 연결되있을경우 단말할인을 으로 선택해서 조회 한다. 정책조회 정보에 [가입유형
         * 가능여부]가 포함되있다.
         */
        //String hndsetModelIdParam = hndsetModelId;
        //if ("".equals(hndsetModelId)) { // 단말(nrds)id가 없을경우 단품을 순회하면서 대표속성을 찾아서 세팅
        if ("".equals(hndsetModelIdParam)) {
            for (PhoneSntyBasDto snty : phoneProdBas.getPhoneSntyBasDtosList()) {
                if ("Y".equals(snty.getRprsPrdtYn())) {
                    hndsetModelIdParam = snty.getHndsetModelId();
                }
            }
        }

        // 현재 단품 ID 로 해당 대표모델ID를 조회한다.
        PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(hndsetModelIdParam);
        if (phoneMspDto == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION);
        }
        String rprsPrdtIdCurrent = phoneMspDto.getRprsPrdtId(); // 해당 단품의 대표모델ID
        phoneProdBas.setHndsetModelId(hndsetModelIdParam);// 대표상품을 현대 BasDto 에 핸드셋 모델ID로 set
        String agrmTrmBase = phoneProdBas.getAgrmTrmBase();  //요금 약정 기간 기본값 처리
        MspSaleDto mspSaleDto = mspService.getMspSale(rprsPrdtIdCurrent, mspSalePlcyMstDto);


        //String operTypeParam = operType;
        //if ("".equals(operType)) { // 가입유형이 없을경우
        if ("".equals(operTypeParam)) { // 가입유형이 없을경우
            if ("Y".equals(mspSaleDto.getMspSalePlcyMstDtoSimbol().getMnpYn())) { // 번호이동이 우선수위가 제일 높다.번호이동여부가 Y 일경우
                operTypeParam = OPER_PHONE_NUMBER_TRANS;                        // 업무유형을 번호이동코드로 set
            } else if ("Y".equals(mspSaleDto.getMspSalePlcyMstDtoSimbol().getNewYn())) {
                operTypeParam = OPER_NEW;
            } else if ("Y".equals(mspSaleDto.getMspSalePlcyMstDtoSimbol().getHdnYn())) {
                operTypeParam = OPER_TYPE_EXCHANGE;
            }
        }

        /*
         *  3.단말할부기간조회  ##
         */
        List<MspSaleAgrmMst> mspSaleAgrmMstList = null;
        mspSaleAgrmMstList = mspService.listMspSaleAgrmMst(mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd());

        /*
         * 요금 약정 기간 기본값 처리
         */
        if ("".equals(instNomParam)) {
            if (StringUtils.isBlank(agrmTrmBase)) {
                if (mspSaleAgrmMstList.size() == 1) {
                    instNomParam = mspSaleAgrmMstList.get(0).getInstNom();
                } else {
                    instNomParam = mspSaleAgrmMstList.get(0).getInstNom();//리스트의 요금제정보의 할부개월은 모두같은것을 조회 했기때문에 임의이 1개의를 가져와 set
                    if ("0".equals(instNomParam)) {        //임시로 처리 하는중 데이터가 없음
                        instNomParam = mspSaleAgrmMstList.get(1).getInstNom(); //<=== 왜?????
                    }
                }
            } else {
                instNomParam = agrmTrmBase;
            }
        }

        /**
         * 전제 제휴 카테고리 xml 정보 조회
         */
        List<AlliCardContDto> cardList = new ArrayList<AlliCardContDto>();
        cardList = cprtService.cprtCardGdncListXml();

        phoneProdBas.setSntyDisp(StringEscapeUtils.unescapeHtml(phoneProdBas.getSntyDisp()));
        model.addAttribute("phoneProdBas", phoneProdBas);
        model.addAttribute("operType", operTypeParam);
        model.addAttribute("hndsetModelId", hndsetModelIdParam);
        model.addAttribute("mspSaleDto", mspSaleDto); //정책에 정보 색상 정보가 변경 되었도 변경 되지 않는다...
        model.addAttribute("mspSaleAgrmMstList", mspSaleAgrmMstList);   //단말기 약정
        model.addAttribute("instNom", instNomParam);
        model.addAttribute("sprtTp", sprtTpParam);
        model.addAttribute("cardList", cardList);

        //단말할인만 있을경우 phoneView.jsp 할인유형 미노출 , 요금할인 있을경우 할인유형 노출

        MspSalePlcyMstDto mspSaleMstDto = new MspSalePlcyMstDto();
        mspSaleMstDto.setPrdtId(rprsPrdtIdCurrent);
        mspSaleMstDto.setSprtTp("PM"); //요금할인
        List<MspSalePlcyMstDto> plcyMstList = mspService.findMspSalePlcyMst(mspSaleMstDto);

        if(plcyMstList != null && plcyMstList.size() > 0) {
            model.addAttribute("discountType", "Y");
        }else {
            model.addAttribute("discountType", "N");
        }

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/phone/phone.view";
        } else {
            return "/portal/phone/phone.view";
        }

    }
    /* 휴대폰 간소화 샘플	 끝 */




    /**
     * 설명 : 폰 구매하기 매장 화면
     * @author key
     * @Date 2021.12.30
     * @param model
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/product/phone/phoneInventoryStoreList.do")
    public String phoneInventoryStoreList(Model model)
            throws RuntimeException {

        return "/portal/phone/phoneInventoryStoreList";

    }

    /**
     * 설명 : 폰 구매하기 매장 지도화면
     * @author key
     * @Date 2021.12.30
     * @param model
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/product/phone/phoneInventoryStoreMapInfo.do")
    public String phoneInventoryStoreMapInfo(Model model)
            throws RuntimeException {

        return "/portal/phone/phoneInventoryStoreMapInfo";

    }

}
