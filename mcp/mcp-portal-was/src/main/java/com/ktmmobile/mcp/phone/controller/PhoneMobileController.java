package com.ktmmobile.mcp.phone.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.constants.Constants.OPER_TYPE_EXCHANGE;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.FIVE_G_FOR_MSP;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.LTEFIVE_G_FOR_MSP;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.LTE_FOR_MSP;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.ONLINE_FOR_MSP;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.OPER_NEW;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.OPER_PHONE_NUMBER_TRANS;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.PHONE_DISCOUNT_FOR_MSP;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.PHONE_FOR_MSP;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.PROD_3G_CD;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.PROD_5G_CD;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.PROD_LTE5G_CD;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.PROD_LTE_CD;
import static com.ktmmobile.mcp.phone.constant.PhoneConstant.THREE_G_FOR_MSP;
import static com.ktmmobile.mcp.phone.dto.OrderEnum.CHARGE_ROW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.mspservice.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.phone.constant.PhoneConstant;
import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
import com.ktmmobile.mcp.phone.dto.OrderEnum;
import com.ktmmobile.mcp.phone.dto.PhoneComapreDataDto;
import com.ktmmobile.mcp.phone.dto.PhoneMspDto;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;
import com.ktmmobile.mcp.phone.dto.PhoneSntyBasDto;
import com.ktmmobile.mcp.phone.dto.stragety.ListOrderNeEnum;
import com.ktmmobile.mcp.phone.service.PhoneService;
import com.ktmmobile.mcp.requestReview.service.RequestReviewService;
import com.ktmmobile.mcp.usim.dto.UsimBasDto;
import com.ktmmobile.mcp.usim.dto.UsimMspRateDto;
import com.ktmmobile.mcp.usim.service.UsimService;

/**
 * @Class Name : PhoneFrontController
 * @Description : 상품 Front Controller
 *
 * @author : ant
 * @Create Date : 2016. 1. 20.
 */
@Controller
@RequestMapping("/m/product")
public class PhoneMobileController {

    private static Logger logger = LoggerFactory.getLogger(PhoneFrontController.class);

    @Autowired
    PhoneService phoneService;

    @Autowired
    MspService mspService;

    @Autowired
    UsimService usimService;

    @Autowired
    FarPricePlanService farPricePlanService;

    /** 조직 코드 */
    @Value("${sale.orgnId}")
    private String orgnId;

    /** 자급제 조직 코드 */
    @Value("${sale.sesplsOrgnId}")
    private String sesplsOrgnId;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    @Autowired
    RequestReviewService requestReviewService;

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
    @RequestMapping(value = "/phone/phoneList.do", method = { RequestMethod.GET, RequestMethod.POST })
    public String phoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result,
                            @RequestParam(defaultValue = "") String rateCd,
                            @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                            Model model) {

        String reDirectUrl = "/product/phone/phoneList.do";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
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

        model.addAttribute("mspOrgList", mspService.findMspOrgListRe("01")); // 제조사 정보 공시
        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P001")); // bannCtg P001 공시지원금목록 데이터없어서 일단M001로
        model.addAttribute("rateCd", rateCd); // 임시처리
        model.addAttribute("prodType", "01");//휴대폰

        String returnUrl = "/portal/phone/phoneList";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
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
    @RequestMapping(value = "/phone/usedPhoneList.do")
    public String usedPhoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result,
                                @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                                @RequestParam(defaultValue = "") String rateCd, Model model) {

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/m/product/phone/usedPhoneList.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto, 2)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("CommonSearchDto", commonSearchDto);
            model.addAttribute("ListOrderEnum", listOrderEnum);
            return "/common/successRedirect";
        }

        model.addAttribute("mspOrgList", mspService.findMspOrgListRe("04")); // 제조사 정보 중고폰
        model.addAttribute("listOrderEnum", listOrderEnum);
        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("P003")); // bannCtg P001 공시지원금목록 데이터없어서 일단M001로
        model.addAttribute("rateCd", rateCd); // 임시처리

        return "/mobile/phone/usedPhoneList";
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
    @RequestMapping(value = "/phone/selfPhoneList.do")
    public String selfPhoneList(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result,
                                @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                                @RequestParam(defaultValue = "") String rateCd, Model model) {

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/m/product/phone/selfPhoneList.do");

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

        return "/mobile/phone/selfPhoneList";
    }

    /**
     * 설명 : 폰목록 조회 AJAX
     *
     * @author key
     * @Date 2021.12.30
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     */
    @RequestMapping(value = "/phone/phoneListAjax.do")
    @ResponseBody
    public Map<String, Object> phoneListAjax(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result,
                                             @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                                             Model model) {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        // 중고폰 여부
        // commonSearchDto.setShandYn("N");
        if (StringUtil.isNotBlank(commonSearchDto.getShandYn()) && "Y".equals(commonSearchDto.getShandYn())) {
            commonSearchDto.setShandYn("N"); // AS-IS에서 중고폰도 N으로 처리해서 일단 똑같이함
            commonSearchDto.setProdType(PhoneConstant.PROD_TYPE_USED);
        }

        /*
         * //자급제 여부 디폴트 N처리 (단말기or중고폰 경우 N처리)
         * if(StringUtil.isBlank(commonSearchDto.getSesplsYn())) {
         * commonSearchDto.setSesplsYn("N"); }
         */

        // ========================1.정책정보 조회 시작 ===============================
        /*
         * 해당 기관정보로 정책정보를 조회 한다. 정책정보는 온라인직영,핸드폰,단말할인,기관코드를 기준으로 가져온다.
         */

        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP); // 정책타입 (온라인직영)
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP); // 정책유형 (핸드폰)
        mspSalePlcyMstDto.setSprtTp(PHONE_DISCOUNT_FOR_MSP); // 할인유형 (단말할인)
        mspSalePlcyMstDto.setOrgnId(orgnId); // 기관코드

        String orgnId2 = orgnId;
        String agrmTrm2 = "24";

        // 자급제폰인 경우 강제 세팅
        if (StringUtil.isNotEmpty(commonSearchDto.getSesplsYn()) && "Y".equals(commonSearchDto.getSesplsYn())) {
            orgnId2 = sesplsOrgnId;
            agrmTrm2 = "0";
        }

        // 자급제폰인 경우 강제 세팅
        if (StringUtil.isNotEmpty(commonSearchDto.getSesplsYn()) && "Y".equals(commonSearchDto.getSesplsYn())) {
            mspSalePlcyMstDto.setSprtTp(""); // 할인유형 (단말할인)
            mspSalePlcyMstDto.setOrgnId(orgnId2); // 기관코드
            mspSalePlcyMstDto.setPlcySctnCd("02"); // 유심으로 설정
        }

        List<MspSalePlcyMstDto> listMspSaleDto = mspService.listMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);

        // ========================1.정책정보 조회 끝 ===============================

        if (listMspSaleDto == null || listMspSaleDto.size() == 0) {
            throw new McpCommonException(NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }
        // ========================2.상품리스트 조회 시작 ===============================
        commonSearchDto.setListMspSaleDto(listMspSaleDto);// 정책정보가 존재하는 상품정보를 읽어오기위해 set
        List<PhoneProdBasDto> lteList = phoneService.listPhoneProdBasForFrontOneQuery(commonSearchDto);
        // ========================1.상품리슽 조회 끝 ===============================

        // 할부수수료 default 값 조회
        CmnGrpCdMst cdInfo = mspService.findCmnGrpCdMst("CMN0051", "20"); // 할부수수료 default 값

        // ========================3.상품별요금 조회 시작 ===============================
        for (PhoneProdBasDto item : lteList) {
            MspSalePlcyMstDto paramPlcy = null;

            /*
             * 동일한 LTE 단말기인 경우라도 단말기 마다. 정책이 상이 할수 있다.. 각 상품별 정책 정보를 설정 한다.
             */
            for (MspSalePlcyMstDto param : listMspSaleDto) {
                if (item.getSalePlcyCd().equals(param.getSalePlcyCd())) {
                    paramPlcy = param;
                    break;
                }
            }

            // 상품 리스트는 단말할인/24개월/번호이동이 기준값이다.
            MspSaleSubsdMstDto mspSaleSubsdMst = null;
            try {
                // list = mspService.listChargeInfoByProdListReTry(item.getRprsPrdtId(),
                // paramPlcy, "N", orgnId,OPER_PHONE_NUMBER_TRANS,"24",null,"Y",cdInfo);

                mspSaleSubsdMst = mspService.getLowPriceChargeInfoByProdList(item.getRprsPrdtId(), paramPlcy, "N",
                        orgnId2, OPER_PHONE_NUMBER_TRANS, agrmTrm2, null, "Y", cdInfo);
                if (mspSaleSubsdMst != null) {
                    item.setPayMnthAmt(mspSaleSubsdMst.getPayMnthAmt());
                    item.setInstAmt(mspSaleSubsdMst.getInstAmt());
                    item.setRateNm(mspSaleSubsdMst.getRateNm());
                }

            } catch (McpCommonException mce) {
                logger.debug("정책정보가 없는 상품 입니다.====>[" + item.getProdNm() + "]");
            }
        }
        // ========================3.상품별요금 조회 끝 ===============================

        // 상품정렬 처리
        listOrderEnum.orderStragety(lteList);

        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("phoneListLte", lteList);
        rtnJson.put("totalCount", lteList.size());
        return rtnJson;

    }

    /**
     * 설명 : 폰 전체 목록 조회 AJAX
     *
     * @author key
     * @Date 2021.12.30
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     */
    @RequestMapping(value = "/phone/phoneListAllAjax.do")
    @ResponseBody
    public Map<String, Object> phoneListAllAjax(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result,
                                                @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                                                Model model) {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        // 중고폰 여부
        // commonSearchDto.setShandYn("N");
        if (StringUtil.isNotBlank(commonSearchDto.getShandYn()) && "Y".equals(commonSearchDto.getShandYn())) {
            commonSearchDto.setShandYn("N"); // AS-IS에서 중고폰도 N으로 처리해서 일단 똑같이함
            commonSearchDto.setProdType(PhoneConstant.PROD_TYPE_USED);
        }

        // ========================1.정책정보 조회 시작 ===============================
        /*
         * 해당 기관정보로 정책정보를 조회 한다. 정책정보는 온라인직영,핸드폰,단말할인,기관코드를 기준으로 가져온다.
         */

        List<MspSalePlcyMstDto> listMspSaleDto = new ArrayList<MspSalePlcyMstDto>();

        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP); // 정책타입 (온라인직영)
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP); // 정책유형 (핸드폰)
        mspSalePlcyMstDto.setSprtTp(PHONE_DISCOUNT_FOR_MSP); // 할인유형 (단말할인)
        mspSalePlcyMstDto.setOrgnId(orgnId); // 기관코드
        List<MspSalePlcyMstDto> listMspBseSaleDto = mspService.listMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);

        // 자급제폰 세팅
        MspSalePlcyMstDto mspSelfPlcyMstDto = new MspSalePlcyMstDto();
        mspSelfPlcyMstDto.setSprtTp(""); // 할인유형 (단말할인)
        mspSelfPlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP); // 정책타입 (온라인직영)
        mspSelfPlcyMstDto.setOrgnId(sesplsOrgnId); // 기관코드
        mspSelfPlcyMstDto.setPlcySctnCd("02"); // 유심으로 설정
        List<MspSalePlcyMstDto> listMspSelfSaleDto = mspService.listMspSalePlcyInfoByOnlyOrgn(mspSelfPlcyMstDto);

        listMspSaleDto.addAll(listMspBseSaleDto);
        listMspSaleDto.addAll(listMspSelfSaleDto);

        // ========================1.정책정보 조회 끝 ===============================

        if (listMspSaleDto == null || listMspSaleDto.size() == 0) {
            throw new McpCommonException(NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }
        // ========================2.상품리스트 조회 시작 ===============================
        // commonSearchDto.setListMspSaleDto(listMspSaleDto);// 정책정보가 존재하는 상품정보를 읽어오기위해
        // set
        commonSearchDto.setListMspSaleDto(listMspBseSaleDto);// 정책정보가 존재하는 상품정보를 읽어오기위해 set
        commonSearchDto.setSesplsYn("N");
        List<PhoneProdBasDto> lteList = new ArrayList<PhoneProdBasDto>();
        List<PhoneProdBasDto> lteBaseList = phoneService.listPhoneProdBasForFrontOneQuery(commonSearchDto);
        commonSearchDto.setSesplsYn("Y");
        commonSearchDto.setListMspSaleDto(listMspSelfSaleDto);// 정책정보가 존재하는 상품정보를 읽어오기위해 set
        List<PhoneProdBasDto> lteSelfList = phoneService.listPhoneProdBasForFrontOneQuery(commonSearchDto);

        lteList.addAll(lteBaseList);
        lteList.addAll(lteSelfList);
        // ========================1.상품리슽 조회 끝 ===============================

        // 할부수수료 default 값 조회
        CmnGrpCdMst cdInfo = mspService.findCmnGrpCdMst("CMN0051", "20"); // 할부수수료 default 값

        // ========================3.상품별요금 조회 시작 ===============================
        for (PhoneProdBasDto item : lteList) {
            MspSalePlcyMstDto paramPlcy = null;

            /*
             * 동일한 LTE 단말기인 경우라도 단말기 마다. 정책이 상이 할수 있다.. 각 상품별 정책 정보를 설정 한다.
             */
            for (MspSalePlcyMstDto param : listMspSaleDto) {
                if (item.getSalePlcyCd().equals(param.getSalePlcyCd())) {
                    paramPlcy = param;
                    break;
                }
            }
            // 상품 리스트는 단말할인/24개월/번호이동이 기준값이다.
            MspSaleSubsdMstDto mspSaleSubsdMst = null;
            MspSaleSubsdMstDto mspSaleSelfSubsdMst = null;
            try {

                mspSaleSubsdMst = mspService.getLowPriceChargeInfoByProdList(item.getRprsPrdtId(), paramPlcy, "N",
                        orgnId, OPER_PHONE_NUMBER_TRANS, "24", null, "Y", cdInfo);
                if (mspSaleSubsdMst != null) {
                    item.setPayMnthAmt(mspSaleSubsdMst.getPayMnthAmt());
                    item.setInstAmt(mspSaleSubsdMst.getInstAmt());

                } else {

                    mspSaleSelfSubsdMst = mspService.getLowPriceChargeInfoByProdList(item.getRprsPrdtId(), paramPlcy,
                            "N", sesplsOrgnId, OPER_PHONE_NUMBER_TRANS, "0", null, "Y", cdInfo);
                    if (mspSaleSelfSubsdMst != null) {
                        item.setPayMnthAmt(mspSaleSelfSubsdMst.getPayMnthAmt());
                        item.setInstAmt(mspSaleSelfSubsdMst.getInstAmt());
                    }

                }

            } catch (McpCommonException mce) {
                logger.debug("정책정보가 없는 상품 입니다.====>[" + item.getProdNm() + "]");
            }
        }

        // ========================3.상품별요금 조회 끝 ===============================

        // 상품정렬 처리
        listOrderEnum.orderStragety(lteList);

        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("phoneListLte", lteList);
        rtnJson.put("totalCount", lteList.size());
        return rtnJson;

    }

    /**
     * 설명 : 공시지원금 목록
     *
     * @author key
     * @Date 2021.12.30
     * @param mspNoticSupportMstDto
     * @param model
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/phone/officialNoticeSupportList.do")
    public String officialNoticeSupportList(@ModelAttribute MspNoticSupportMstDto mspNoticSupportMstDto, Model model)
            throws RuntimeException {
        // 요금제 목록 조회
        List<MspNoticSupportMstDto> rateList = mspService.listMspOfficialSupportRateNm();

        model.addAttribute("rateList", rateList);

        return "/mobile/phone/officialNoticeSupportList";

    }

    /**
     * 설명 : 공시지원금 목록 AJAX
     *
     * @author key
     * @Date 2021.12.30
     * @param mspNoticSupportMstDto
     * @param result
     * @param model
     * @param pageInfoBean
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/phone/officialNoticeSupportListAjax.do")
    @ResponseBody
    public Map<String, Object> officialNoticeSupportListAjax(
            @ModelAttribute MspNoticSupportMstDto mspNoticSupportMstDto, BindingResult result, Model model,
            PageInfoBean pageInfoBean) throws RuntimeException {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        mspNoticSupportMstDto.setPrdtNm(ParseHtmlTagUtil.percentToEscape(mspNoticSupportMstDto.getPrdtNm()));

        /* 현재 페이지 번호 초기화 */
        if (pageInfoBean.getPageNo() == 0) {
            pageInfoBean.setPageNo(1);
        }
        /* 한페이지당 보여줄 리스트 수 */
        /* 카운터 조회 */
        int total = mspService.listMspOfficialNoticeSupportCount(mspNoticSupportMstDto);
        pageInfoBean.setTotalCount(total);

        // 총페이지와 요청 페이지 비교
        if (pageInfoBean.getTotalPageCount() < pageInfoBean.getPageNo()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount(); // Pagesize

        List<MspNoticSupportMstDto> mspOfficialNoticeSupportList;

        mspOfficialNoticeSupportList = mspService.listMspOfficialNoticeSupport(mspNoticSupportMstDto, skipResult,
                maxResult);
        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("mspOfficialNoticeSupportList", mspOfficialNoticeSupportList);
        rtnJson.put("pageInfoBean", pageInfoBean);

        return rtnJson;

    }

    /**
     * 설명 : 폰 비교하기 AJAX
     *
     * @author key
     * @Date 2021.12.30
     * @param phoneProdBasDto
     * @param result
     * @param rateCd
     * @param hndsetModelId
     * @param model
     * @return
     */
    @RequestMapping(value = "/phone/phoneListCompareAjax.do")
    @ResponseBody
    public Map<String, Object> phoneListCompareAjax(@ModelAttribute PhoneProdBasDto phoneProdBasDto,
                                                    BindingResult result, @RequestParam(defaultValue = "") String rateCd,
                                                    @RequestParam(defaultValue = "") String hndsetModelId, Model model) {

        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        String orgnId2 = orgnId;
        String agrmTrm2 = "24";
        String sprtTp = PhoneConstant.PHONE_DISCOUNT_FOR_MSP;
        String plcySctnCd = PhoneConstant.PHONE_FOR_MSP;

        /*
         * ## 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
         */
        PhoneProdBasDto phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

        if (phoneProdBas == null) {
            throw new McpCommonJsonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION);
        }
        String prdtSctnCd = "";
        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP);
        if (PROD_LTE_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(LTE_FOR_MSP);
            prdtSctnCd = PhoneConstant.LTE_FOR_MSP;
        } else if (PROD_3G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(THREE_G_FOR_MSP);
            prdtSctnCd = PhoneConstant.THREE_G_FOR_MSP;
        } else if (PROD_5G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(FIVE_G_FOR_MSP);
            prdtSctnCd = PhoneConstant.FIVE_G_FOR_MSP;
        } else if (PROD_LTE5G_CD.equals(phoneProdBas.getProdCtgId())) {
            mspSalePlcyMstDto.setPrdtSctnCd(LTEFIVE_G_FOR_MSP);
            prdtSctnCd = PhoneConstant.LTEFIVE_G_FOR_MSP;
        }
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP);
        mspSalePlcyMstDto.setOrgnId(orgnId);

        // 자급제폰인 경우 강제 세팅
        String sesplsYn = phoneProdBas.getSesplsYn();
        if (sesplsYn == null) {
            sesplsYn = "";
        }

        // 자급제폰인 경우 강제 세팅
        if ("Y".equals(sesplsYn)) {
            orgnId2 = sesplsOrgnId; // 기관코드
            agrmTrm2 = "0";
            sprtTp = ""; // 할인유형 (단말할인)
            plcySctnCd = "02"; // 유심으로 설정
        }

        // 자급제폰인 경우 강제 세팅
        if ("Y".equals(sesplsYn)) {
            mspSalePlcyMstDto.setSprtTp(""); // 할인유형 (단말할인)
            mspSalePlcyMstDto.setOrgnId(orgnId2); // 기관코드
            mspSalePlcyMstDto.setPlcySctnCd(plcySctnCd); // 유심으로 설정
        }

        String hndsetModelIdParam = hndsetModelId;
        if ("".equals(hndsetModelIdParam)) { // 단말(nrds)id가 없을경우 단품을 순회하면서 대표속성을 찾아서 세팅
            for (PhoneSntyBasDto snty : phoneProdBas.getPhoneSntyBasDtosList()) {
                if ("Y".equals(snty.getRprsPrdtYn())) {
                    hndsetModelIdParam = snty.getHndsetModelId();
                }
            }
        }

        /*
         * 대표모델 id 는 단품속성테이블 정보에는 존재하지 않는다. 하지만 대표모델 ID로만 정책과 관련 요금제를 조회 할수있다. select
         * option 에 단품을 변경 하면 입력받은 단뭄 모델 id로는 단품정보(이미지등)을 조회하고 정책과 요금 조회는 대표모델 ID로만 조회
         * 한다
         */
        phoneProdBas.setHndsetModelId(hndsetModelIdParam); // 이미지 추출만을 위한 phoneProdBas 의 모델id(hndsetModelId) 세팅

        // 현재 단품 ID 로 해당 대표모델ID를 조회한다.
        PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(hndsetModelIdParam);
        String rprsPrdtIdCurrent = phoneMspDto.getRprsPrdtId(); // 해당 단품의 대표모델ID

        MspSaleDto mspSaleDto = null;

        // 자급제인 경우 강제 세팅
        if (StringUtil.isNotEmpty(phoneProdBas.getSesplsYn()) && "Y".equals(phoneProdBasDto.getSesplsYn())) {
            mspSalePlcyMstDto.setOrgnId(sesplsOrgnId);
            mspSalePlcyMstDto.setPlcySctnCd("02");
        }

        try {
            mspSaleDto = mspService.getMspSale(rprsPrdtIdCurrent, mspSalePlcyMstDto);
        } catch (McpCommonException mce) {
            throw new McpCommonJsonException(mce.getErrorMsg());
        }

        agrmTrm2 = Optional.ofNullable(phoneProdBas.getAgrmTrmBase()).orElse("24");

        mspSaleDto.setForCompareYn("Y");// 비교하기 리스트용 SQL 문 호출을 위해서 Y 일경우 상품상세 성능 개선을 위해서 불필요한 필드 제거를 하지 않는다.
        List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId2,
                OPER_PHONE_NUMBER_TRANS, agrmTrm2, "24","", "N");

        List<MspSaleSubsdMstDto> baseChargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId2,
                OPER_PHONE_NUMBER_TRANS, agrmTrm2, "", "N");

        List<MspSaleSubsdMstDto> xmlList = usimService.listChargeInfoUsimXml(chargeList, "01");// 휴대폰요금제

        // 최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.

        PhoneProdBasDto temp = new PhoneProdBasDto();
        temp.setMspSaleSubsdMstListForLowPrice(xmlList);
        temp.setOrderEnum(CHARGE_ROW);
        temp.doSort();

        //List<MspRateMstDto> mspRateMstList = mspService.listRateByOrgnInfos(orgnId2, sprtTp, plcySctnCd, prdtSctnCd,
        //        PhoneConstant.ONLINE_FOR_MSP);

        Map<String, Object> jsonMap = new HashMap<String, Object>();

        jsonMap.put("phoneProdBas", phoneProdBas);
        jsonMap.put("mspSaleDto", mspSaleDto);
        jsonMap.put("chargeList", xmlList);
        jsonMap.put("baseChargeList", baseChargeList);
        jsonMap.put("prodId", phoneProdBas.getProdId());
        //jsonMap.put("mspRateMstList", mspRateMstList);

        return jsonMap;
    }

    /**
     * 설명 : 폰 요금 조회 AJX
     *
     * @author key
     * @Date 2021.12.30
     * @param phoneProdBasDto
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/phone/phoneRateListAjax.do")
    @ResponseBody
    public Map<String, Object> phoneRateListAjax(@ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
                                                 Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();
        List<MspRateMstDto> mspRateMstList = mspService.listRateByOrgnInfos(orgnId,
                PhoneConstant.PHONE_DISCOUNT_FOR_MSP, PhoneConstant.PHONE_FOR_MSP, PhoneConstant.LTE_FOR_MSP,
                PhoneConstant.ONLINE_FOR_MSP);
        rtnJsonMap.put("mspRateMstList", mspRateMstList);

        return rtnJsonMap;
    }

    /**
     * 설명 : 폰 비교하기 세션정보
     *
     * @author key
     * @Date 2021.12.30
     * @param phoneComapreData
     * @param result
     * @param model
     * @param request
     * @param process
     * @param idx
     * @return
     */
    @RequestMapping(value = "/phone/phoneCompareProcAjax.do")
    @ResponseBody
    public Map<String, Object> phoneCompareSaveAjax(@ModelAttribute PhoneComapreDataDto phoneComapreData,
                                                    BindingResult result, Model model, HttpServletRequest request,
                                                    @RequestParam(defaultValue = "") String process, @RequestParam(defaultValue = "") int idx) {
        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }
        int phoneCompareSaveCnt = 0;
        String addProc = "add";
        String removeProc = "remove";
        String updateProc = "update";
        String removeAllProc = "removeAll";
        // String searchProc = "search";

        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();
        ArrayList<PhoneComapreDataDto> phoneComapreDataArr = new ArrayList<PhoneComapreDataDto>();

        // 세션에 휴대폰 비교함 정보 세팅
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        ArrayList<PhoneComapreDataDto> PhoneComapreDtoList = (ArrayList<PhoneComapreDataDto>) session
                .getAttribute("phoneComapreList");
        if (PhoneComapreDtoList == null || PhoneComapreDtoList.size() <= 0) {

            if (addProc.equals(process)) {
                phoneComapreDataArr.add(phoneComapreData);
                session.setAttribute("phoneComapreList", phoneComapreDataArr);
                phoneCompareSaveCnt = 1;
                rtnJsonMap.put("phoneComapreData", phoneComapreDataArr);
            }

        } else {

            if (addProc.equals(process)) {
                PhoneComapreDtoList.add(phoneComapreData);
            } else if (removeProc.equals(process)) {
                for (Iterator<PhoneComapreDataDto> itr = PhoneComapreDtoList.iterator(); itr.hasNext();) {
                    PhoneComapreDataDto dto = itr.next();
                    if (phoneComapreData.getProdId() != null && !"".equals(phoneComapreData.getProdId())) {
                        if (phoneComapreData.getProdId().equals(dto.getProdId())) {
                            itr.remove();
                        }
                    } else {
                        if (dto.getIdx() == idx) {
                            itr.remove();
                        }
                    }
                }

            } else if (updateProc.equals(process)) {

                for (Iterator<PhoneComapreDataDto> itr = PhoneComapreDtoList.iterator(); itr.hasNext();) {
                    PhoneComapreDataDto dto = itr.next();
                    if (dto.getIdx() == idx) {
                        itr.remove();
                    }
                }

                PhoneComapreDtoList.add(phoneComapreData);

            } else if (removeAllProc.equals(process)) {
                PhoneComapreDtoList.clear();
            }

            for (PhoneComapreDataDto phoneComapreDataDto : PhoneComapreDtoList) {
                if (phoneComapreDataDto != null && !"".equals(phoneComapreDataDto.getProdId())) {
                    phoneCompareSaveCnt++;
                }
            }

            session.setAttribute("phoneComapreList", PhoneComapreDtoList);
            // idx순으로 정렬

            // rtnJsonMap.put("phoneComapreData", PhoneComapreDtoList);
            rtnJsonMap.put("phoneComapreData", PhoneComapreDtoList.stream()
                    .sorted(Comparator.comparing(PhoneComapreDataDto::getIdx)).collect(Collectors.toList()));
        }

        rtnJsonMap.put("phoneCompareSaveCnt", phoneCompareSaveCnt);

        return rtnJsonMap;
    }

    /**
     * 설명 : 폰 비교하기 팝업
     *
     * @author key
     * @Date 2021.12.30
     * @param mspNoticSupportMstDto
     * @param model
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/phone/phoneComparePop.do")
    public String phoneComparePop(@ModelAttribute MspNoticSupportMstDto mspNoticSupportMstDto, Model model)
            throws RuntimeException {

        return "/mobile/phone/phoneComparePop";

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
    @RequestMapping(value = "/phone/phoneView_old.do")
    public String phoneView(@ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
                            @RequestParam(defaultValue = "") String operType, @RequestParam(defaultValue = "") String hndsetModelId,
                            @RequestParam(defaultValue = "") String instNom, @RequestParam(defaultValue = "") String bannerCd,
                            @RequestParam(defaultValue = "") String rateCd, @RequestParam(defaultValue = "") String sprtTp,
                            Model model) {
        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        String orgnId2 = orgnId;
        // String plcySctnCd = PhoneConstant.PHONE_FOR_MSP;

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
        if (sesplsYn == null) {
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
        if ("Y".equals(sesplsYn)) {
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
        if ("Y".equals(sesplsYn)) {
            // 자급제인 경우 강제로 변환 처리
            // 판매정책코드
            String salePlcyCd = mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd();
            MspSaleAgrmMst mspSaleAgrmMst = new MspSaleAgrmMst();
            mspSaleAgrmMst.setSalePlcyCd(salePlcyCd);
            mspSaleAgrmMst.setOrgnId(sesplsOrgnId); // MSP_SALE_ORGN_MST@DL_MSP - ORG.ORGN_ID = 'V000019481'

            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst2(mspSaleAgrmMst);

        } else {
            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst(mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd());
        }

        /*
         * ## 4.요금리스트 조회 ##
         */
        String instNomParam = instNom;
        if ("".equals(instNomParam)) {
            if (mspSaleAgrmMstList.size() == 1) {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();
            } else {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();// 리스트의 요금제정보의 할부개월은 모두같은것을 조회 했기때문에 임의이 1개의를 가져와
                // set
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
        if ("Y".equals(sesplsYn)) {
            orgnId2 = sesplsOrgnId; // 기관코드
            // sprtTp = ""; // 할인유형 (단말할인)
            // plcySctnCd = "02"; // 유심으로 설정
        }

        List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId2,
                operTypeParam, instNomParam, null, null);

        // 최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
        PhoneProdBasDto temp = new PhoneProdBasDto();
        temp.setMspSaleSubsdMstListForLowPrice(chargeList);
        temp.setOrderEnum(CHARGE_ROW);
        temp.doSort();

        // 최고가 최저가
        if (!"Y".equals(sesplsYn)) {
            MspSaleSubsdMstDto maxObj = chargeList.stream().max(Comparator.comparing(v -> v.getInstAmt())).get();
            MspSaleSubsdMstDto minObj = chargeList.stream().min(Comparator.comparing(v -> v.getInstAmt())).get();

            model.addAttribute("maxValue", maxObj.getInstAmt());
            model.addAttribute("minValue", minObj.getInstAmt());
        }

        phoneProdBas.setSntyDisp(StringEscapeUtils.unescapeHtml(phoneProdBas.getSntyDisp()));
        // model.addAttribute("total", total);
        model.addAttribute("phoneProdBas", phoneProdBas);
        model.addAttribute("mspSaleDto", mspSaleDto);
        // model.addAttribute("mspSaleAgrmMstList", mspSaleAgrmMstList);
        model.addAttribute("chargeList", chargeList);
        model.addAttribute("operType", operTypeParam);
        model.addAttribute("hndsetModelId", hndsetModelIdParam);
        // model.addAttribute("instNom", instNomParam);
        model.addAttribute("sprtTp", sprtTp);
        // model.addAttribute("bannerCd", bannerCd);
        model.addAttribute("rateCd", rateCd);

        return "/mobile/phone/phoneView";
    }




    /* 휴대폰 간소화 샘플	 시작 */
    @RequestMapping(value = "/phone/phoneView2.do")
    public String phoneView2(@ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
                            @RequestParam(defaultValue = "") String operType, @RequestParam(defaultValue = "") String hndsetModelId,
                            @RequestParam(defaultValue = "") String instNom, @RequestParam(defaultValue = "") String bannerCd,
                            @RequestParam(defaultValue = "") String rateCd, @RequestParam(defaultValue = "") String sprtTp,
                            Model model) {
        if (result.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        String orgnId2 = orgnId;
        // String plcySctnCd = PhoneConstant.PHONE_FOR_MSP;

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
        if (sesplsYn == null) {
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
        if ("Y".equals(sesplsYn)) {
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
        if ("Y".equals(sesplsYn)) {
            // 자급제인 경우 강제로 변환 처리
            // 판매정책코드
            String salePlcyCd = mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd();
            MspSaleAgrmMst mspSaleAgrmMst = new MspSaleAgrmMst();
            mspSaleAgrmMst.setSalePlcyCd(salePlcyCd);
            mspSaleAgrmMst.setOrgnId(sesplsOrgnId); // MSP_SALE_ORGN_MST@DL_MSP - ORG.ORGN_ID = 'V000019481'

            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst2(mspSaleAgrmMst);

        } else {
            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst(mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd());
        }

        /*
         * ## 4.요금리스트 조회 ##
         */
        String instNomParam = instNom;
        if ("".equals(instNomParam)) {
            if (mspSaleAgrmMstList.size() == 1) {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();
            } else {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();// 리스트의 요금제정보의 할부개월은 모두같은것을 조회 했기때문에 임의이 1개의를 가져와
                // set
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
        if ("Y".equals(sesplsYn)) {
            orgnId2 = sesplsOrgnId; // 기관코드
            // sprtTp = ""; // 할인유형 (단말할인)
            // plcySctnCd = "02"; // 유심으로 설정
        }

        List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId2,
                operTypeParam, instNomParam, null, null);

        // 최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
        PhoneProdBasDto temp = new PhoneProdBasDto();
        temp.setMspSaleSubsdMstListForLowPrice(chargeList);
        temp.setOrderEnum(CHARGE_ROW);
        temp.doSort();

        // 최고가 최저가
        if (!"Y".equals(sesplsYn)) {
            MspSaleSubsdMstDto maxObj = chargeList.stream().max(Comparator.comparing(v -> v.getInstAmt())).get();
            MspSaleSubsdMstDto minObj = chargeList.stream().min(Comparator.comparing(v -> v.getInstAmt())).get();

            model.addAttribute("maxValue", maxObj.getInstAmt());
            model.addAttribute("minValue", minObj.getInstAmt());
        }

        phoneProdBas.setSntyDisp(StringEscapeUtils.unescapeHtml(phoneProdBas.getSntyDisp()));
        // model.addAttribute("total", total);
        model.addAttribute("phoneProdBas", phoneProdBas);
        model.addAttribute("mspSaleDto", mspSaleDto);
        // model.addAttribute("mspSaleAgrmMstList", mspSaleAgrmMstList);
        model.addAttribute("chargeList", chargeList);
        model.addAttribute("operType", operTypeParam);
        model.addAttribute("hndsetModelId", hndsetModelIdParam);
        // model.addAttribute("instNom", instNomParam);
        model.addAttribute("sprtTp", sprtTp);
        // model.addAttribute("bannerCd", bannerCd);
        model.addAttribute("rateCd", rateCd);

        return "/mobile/phone/phoneView2";
    }
    /* 휴대폰 간소화 샘플	 끝 */




    /**
     * 폰 상세보기 정보 조회 AJAX
     *
     * @param phoneProdBasDto
     * @param result
     * @param operType
     * @param hndsetModelId
     * @param instNom
     * @param orderEnum
     * @param chargeType
     * @param onOffType
     * @param model
     * @return
     */
    @RequestMapping(value = "/phone/phoneViewDataAjax.do")
    @ResponseBody
    public Map<String, Object> phoneViewDataAjax(@ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
                                                 @RequestParam(defaultValue = "") String operType, @RequestParam(defaultValue = "") String hndsetModelId,
                                                 @RequestParam(defaultValue = "") String instNom,
                                                 @RequestParam(defaultValue = "CHARGE_ROW", value = "orderEnum") OrderEnum orderEnum,
                                                 @RequestParam(defaultValue = "") String chargeType, @RequestParam(defaultValue = "") String onOffType,
                                                 Model model) {
        if (result.hasErrors()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        /*
         * ## 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
         */
        PhoneProdBasDto phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

        Map<String, Object> rtnJson = new HashMap<String, Object>();
        rtnJson.put("phoneProdBas", phoneProdBas);

        return rtnJson;
    }

    /**
     * 폰정보 조회 AJAX
     *
     * @param phoneProdBasDto
     * @param result
     * @param operType
     * @param hndsetModelId
     * @param instNom
     * @param orderEnum
     * @param chargeType
     * @param onOffType
     * @param model
     * @return
     */
    @RequestMapping(value = "/phone/phoneViewAjax")
    @ResponseBody
    public Map<String, Object> phoneViewAjax(@ModelAttribute PhoneProdBasDto phoneProdBasDto, BindingResult result,
                                             @RequestParam(defaultValue = "") String operType, @RequestParam(defaultValue = "") String hndsetModelId,
                                             @RequestParam(defaultValue = "") String instNom,
                                             @RequestParam(defaultValue = "CHARGE_ROW", value = "orderEnum") OrderEnum orderEnum,
                                             @RequestParam(defaultValue = "") String chargeType, @RequestParam(defaultValue = "") String onOffType,
                                             @RequestParam(defaultValue = "") String modelMonthly, @RequestParam(defaultValue = "") String noAgrmYn,
                                             Model model) {
        if (result.hasErrors()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        String orgnId2 = orgnId;
        // String plcySctnCd = PhoneConstant.PHONE_FOR_MSP;

        /*
         * ## 1.상품및 단품정보 조회 상품대표이미지,용량색상별 정보 등을 조회한다.
         */
        PhoneProdBasDto phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

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
        if (sesplsYn == null) {
            sesplsYn = "";
        }
        String prodType = phoneProdBas.getProdType();

        /*
         * ## 2.정책 조회 (가입유형 조회) ## 이하 아래는 정책정보를 조회 하는 부분이다. 상품별 정책은(단말할인,요금할인)에 따라 최대
         * 2개까지 나올 가능성이 있다. 해당 단품에 정책이 2개가 연결되있을경우 단말할인을 으로 선택해서 조회 한다. 정책조회 정보에 [가입유형
         * 가능여부]가 포함되있다.
         */
        String hndsetModelIdParam = hndsetModelId;
        if ("".equals(hndsetModelIdParam)) { // 단말(nrds)id가 없을경우 단품을 순회하면서 대표속성을 찾아서 세팅
            for (PhoneSntyBasDto snty : phoneProdBas.getPhoneSntyBasDtosList()) {
                if ("Y".equals(snty.getRprsPrdtYn())) {
                    hndsetModelIdParam = snty.getHndsetModelId();
                }
            }
        }

        // 현재 단품 ID 로 해당 대표모델ID를 조회한다.
        PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(hndsetModelIdParam);
        String rprsPrdtIdCurrent = phoneMspDto.getRprsPrdtId(); // 해당 단품의 대표모델ID

        // 자급제인 경우 강제 세팅
        if ("Y".equals(sesplsYn)) {
            mspSalePlcyMstDto.setOrgnId(sesplsOrgnId);
            mspSalePlcyMstDto.setPlcySctnCd("02");
        }

        MspSaleDto mspSaleDto = mspService.getMspSale(rprsPrdtIdCurrent, mspSalePlcyMstDto);

        /*
         * ## 3.단말할부기간조회 ##
         */
        List<MspSaleAgrmMst> mspSaleAgrmMstList = null;
        if ("Y".equals(sesplsYn)) {
            // 자급제인 경우 강제로 변환 처리
            // 판매정책코드
            String salePlcyCd = mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd();
            MspSaleAgrmMst mspSaleAgrmMst = new MspSaleAgrmMst();
            mspSaleAgrmMst.setSalePlcyCd(salePlcyCd);
            mspSaleAgrmMst.setOrgnId(sesplsOrgnId); // MSP_SALE_ORGN_MST@DL_MSP - ORG.ORGN_ID = 'V000019481'

            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst2(mspSaleAgrmMst);

        } else {
            mspSaleAgrmMstList = mspService.listMspSaleAgrmMst(mspSaleDto.getMspSalePlcyMstDtoSimbol().getSalePlcyCd());
        }

        /*
         * ## 4.요금리스트 조회 ##
         */
        String instNomParam = instNom;
        if ("".equals(instNomParam)) {
            if (mspSaleAgrmMstList.size() == 1) {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();
            } else {
                instNomParam = mspSaleAgrmMstList.get(0).getInstNom();// 리스트의 요금제정보의 할부개월은 모두같은것을 조회 했기때문에 임의이 1개의를 가져와
                // set
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
        if ("Y".equals(sesplsYn)) {
            orgnId2 = sesplsOrgnId; // 기관코드
            // sprtTp = ""; // 할인유형 (단말할인)
            // plcySctnCd = "02"; // 유심으로 설정
        }

        String modelMonthlyParam = modelMonthly;
        if ("null".equals(StringUtil.NVL(modelMonthlyParam, "null"))) {
            modelMonthlyParam = instNomParam;
        }
        String noAgrmYnParam = noAgrmYn;
        // List<MspSaleSubsdMstDto> chargeList =
        // mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N",
        // orgnId2,operTypeParam,instNomParam,instNomParam,null,null,onOffType); //10
        List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfo(rprsPrdtIdCurrent, mspSaleDto, "N", orgnId2,
                operTypeParam, instNomParam, modelMonthlyParam, null, noAgrmYnParam, onOffType); // 10
        List<MspSaleSubsdMstDto> xmlList = usimService.listChargeInfoUsimXml(chargeList, "01");// 휴대폰요금제

        String rchk = phoneProdBas.getRecommendRate();
        List<MspSaleSubsdMstDto> filterList = new ArrayList<MspSaleSubsdMstDto>();
        List<MspSaleSubsdMstDto> filterXmlList = new ArrayList<MspSaleSubsdMstDto>();
        if ("".equals(chargeType) || "01".equals(chargeType)) {
            filterList = chargeList;
            filterXmlList = chargeList;
        } else {
            if ("02".equals(chargeType)) { // 추천요금제 sprtTp
                for (MspSaleSubsdMstDto item : chargeList) {
                    if (rchk != null && rchk.indexOf(item.getRateCd() + "#" + item.getSprtTp()) > -1) {
                        filterList.add(item);
                        filterXmlList.add(item);
                    }
                }
            } else if ("03".equals(chargeType)) { // 약정 (단말할인)
                for (MspSaleSubsdMstDto item : chargeList) {
                    if (PhoneConstant.PHONE_DISCOUNT_FOR_MSP.equals(item.getSprtTp())
                            && !"0".equals(item.getAgrmTrm())) {
                        filterList.add(item);
                        filterXmlList.add(item);
                    }
                }
            } else if ("04".equals(chargeType)) { // 약정 (요금할인)
                for (MspSaleSubsdMstDto item : chargeList) {
                    if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(item.getSprtTp())
                            && !"0".equals(item.getAgrmTrm())) {
                        filterList.add(item);
                        filterXmlList.add(item);
                    }
                }
            } else if ("05".equals(chargeType)) { // 무약정요금제
                for (MspSaleSubsdMstDto item : chargeList) {
                    if ("0".equals(item.getAgrmTrm())) {
                        filterList.add(item);
                        filterXmlList.add(item);
                    }
                }
            }
        }

        // 최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
        PhoneProdBasDto temp = new PhoneProdBasDto();
        temp.setMspSaleSubsdMstListForLowPrice(filterList);
        temp.setOrderEnum(orderEnum);
        temp.doSort();

        PhoneProdBasDto temp2 = new PhoneProdBasDto();
        temp2.setMspSaleSubsdMstListForLowPrice(xmlList);
        temp2.setOrderEnum(orderEnum);
        temp2.doSort();

        Map<String, Object> rtnJson = new HashMap<String, Object>();
        rtnJson.put("chargeList", filterList);
        rtnJson.put("chargeXmlList", xmlList);
        rtnJson.put("phoneProdBas", phoneProdBas);

        return rtnJson;
    }

    /**
     * 구매하기 매장 조회
     *
     * @param mspNoticSupportMstDto
     * @param model
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/phone/phoneInventoryStoreList.do")
    public String phoneInventoryStoreList(@ModelAttribute MspNoticSupportMstDto mspNoticSupportMstDto, Model model)
            throws RuntimeException {

        return "/mobile/phone/phoneInventoryStoreList";

    }

    /**
     * 설명 : 폰 구매하기 매장 지도 조회
     *
     * @author key
     * @Date 2021.12.30
     * @param mspNoticSupportMstDto
     * @param model
     * @return
     * @throws RuntimeException
     */
    @RequestMapping(value = "/phone/phoneInventoryStoreMapInfo.do")
    public String phoneInventoryStoreMapInfo(@ModelAttribute MspNoticSupportMstDto mspNoticSupportMstDto, Model model)
            throws RuntimeException {

        return "/mobile/phone/phoneInventoryStoreMapInfo";

    }

    /**
     * 설명 : 유심 요금제조회 AJAX
     *
     * @author key
     * @Date 2021.12.30
     * @param operType
     * @param prdtSctnCd
     * @return
     */
    @RequestMapping(value = "/phone/getUsimChargeAjax.do")
    @ResponseBody
    public Map<String, Object> getUsimChargeAjax(@RequestParam(defaultValue = "") String operType,
                                                 @RequestParam(defaultValue = "") String prdtSctnCd) {

        UsimBasDto usimBasDtoParm = new UsimBasDto();
        usimBasDtoParm.setOperType(operType);
        usimBasDtoParm.setDataType(prdtSctnCd);

        List<UsimMspRateDto> usimPriceList = usimService.selectJoinUsimPriceNew(usimBasDtoParm);

        if (usimPriceList == null || usimPriceList.size() == 0) {
            throw new McpCommonJsonException(ExceptionMsgConstant.NO_EXSIST_RATE);
        }

        Map<String, Object> rtnJson = new HashMap<String, Object>();
        rtnJson.put("joinPrice", usimPriceList.get(0).getJoinPrice());
        rtnJson.put("usimPrice", usimPriceList.get(0).getUsimPrice());

        return rtnJson;
    }


    /**
     * 설명 : 폰목록 조회 AJAX
     *
     * @author key
     * @Date 2021.12.30
     * @param commonSearchDto
     * @param result
     * @param listOrderEnum
     * @param model
     * @return
     */
    @RequestMapping(value = "/phone/phoneAgrListAjax.do")
    @ResponseBody
    public Map<String, Object> phoneAgrListAjax(@ModelAttribute CommonSearchDto commonSearchDto, BindingResult result,
                                             @RequestParam(defaultValue = "RECOMMEND", value = "listOrderEnum") ListOrderNeEnum listOrderEnum,
                                             Model model) {

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        // 중고폰 여부
        // commonSearchDto.setShandYn("N");
        if (StringUtil.isNotBlank(commonSearchDto.getShandYn()) && "Y".equals(commonSearchDto.getShandYn())) {
            commonSearchDto.setShandYn("N"); // AS-IS에서 중고폰도 N으로 처리해서 일단 똑같이함
            commonSearchDto.setProdType(PhoneConstant.PROD_TYPE_USED);
        }

        // ========================1.정책정보 조회 시작 ===============================
        MspSalePlcyMstDto mspSalePlcyMstDto = new MspSalePlcyMstDto();
        mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP); // 정책타입 (온라인직영)
        mspSalePlcyMstDto.setPlcySctnCd(PHONE_FOR_MSP); // 정책유형 (핸드폰)
        mspSalePlcyMstDto.setSprtTp(PHONE_DISCOUNT_FOR_MSP); // 할인유형 (단말할인)
        mspSalePlcyMstDto.setOrgnId(orgnId); // 기관코드
        mspSalePlcyMstDto.setAgrmTrm("24");




        // 자급제폰인 경우 강제 세팅
        if (StringUtil.isNotEmpty(commonSearchDto.getSesplsYn()) && "Y".equals(commonSearchDto.getSesplsYn())) {
            mspSalePlcyMstDto.setSprtTp(""); // 할인유형 (단말할인)
            mspSalePlcyMstDto.setOrgnId(sesplsOrgnId); // 기관코드
            mspSalePlcyMstDto.setPlcySctnCd("02"); // 유심으로 설정
            mspSalePlcyMstDto.setAgrmTrm("0");
        }


        List<MspSalePlcyMstDto> listMspSaleDto = this.mspService.listMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);
        if (ObjectUtils.isEmpty(listMspSaleDto)) {
            throw new McpCommonException(NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }



        // ========================2.상품리스트 조회 시작 ===============================
        commonSearchDto.setListMspSaleDto(listMspSaleDto);// 정책정보가 존재하는 상품정보를 읽어오기위해 set
        List<PhoneProdBasDto> lteList = this.phoneService.listPhoneProdBasForFrontOneQuery(commonSearchDto);

        // ========================1.상품리스트 조회 끝 ===============================
        // 할부수수료 default 값 조회
        CmnGrpCdMst cdInfo = this.mspService.findCmnGrpCdMst("CMN0051", "20"); // 할부수수료 default 값
        String onOffType = "P".equals(NmcpServiceUtils.getPlatFormCd()) ? "0" : "3"; // PC = 0 / m = 3

        // ========================3.상품별요금 조회 시작 ===============================
        lteList.forEach(list -> {
            MspSalePlcyMstDto saleMstDto = listMspSaleDto.stream()
                                                         .filter(saleDto -> list.getSalePlcyCd().equals(saleDto.getSalePlcyCd()))
                                                         .findFirst().orElse(null);
            //PROD_ID   4250   REP_RATE LTEVCFL15  AGRM_TRM_BASE 0
            //TO_DO TEST 삭제 해야 함... 테스트용 ....
            /*if ("4250".equals(list.getProdId()))  {
                list.setRepRate("LTEVCFL15");
                list.setAgrmTrmBase("0");
            }*/

            //saleMstDto.setRateCd(StringUtils.isNotEmpty(list.getRepRate()) ? list.getRepRate() : saleMstDto.getRateCd());       /// ????????
            //list.setAgrmTrmBase(StringUtils.isEmpty(list.getAgrmTrmBase()) ? mspSalePlcyMstDto.getAgrmTrm() : list.getAgrmTrmBase());// nulld일 경우       ??????????????????

            saleMstDto.setRateCd(StringUtils.isNotEmpty(list.getRepRate()) ? list.getRepRate() : "");
            list.setAgrmTrmBase(StringUtils.isEmpty(list.getAgrmTrmBase()) ? "24" : list.getAgrmTrmBase());// nulld일 경우       ??????????????????

            //mspSalePlcyMstDto.setAgrmTrm("Y".equals(commonSearchDto.getSesplsYn()) ? mspSalePlcyMstDto.getAgrmTrm() : list.getAgrmTrmBase()); //자급제일때 (0)
            //mspSalePlcyMstDto.setAgrmTrm(!"Y".equals(commonSearchDto.getSesplsYn()) && "0".equals(list.getAgrmTrmBase()) ? "24" : mspSalePlcyMstDto.getAgrmTrm());//자급제가 아닌데 0이면 24    ???????????

            String modelMonthly = "0";
            String agrmTrmBase = list.getAgrmTrmBase();

            if ("Y".equals(commonSearchDto.getSesplsYn())) {
                mspSalePlcyMstDto.setAgrmTrm(mspSalePlcyMstDto.getAgrmTrm()); //자급제일때 (0)
                modelMonthly = "24";  //무약정 , 24개달 단말 할부
            } else {

                mspSalePlcyMstDto.setAgrmTrm(agrmTrmBase);

                if ("0".equals(agrmTrmBase)) {
                    modelMonthly = "24";  //무약정 , 24개달 단말 할부
                } else {
                    modelMonthly = agrmTrmBase;
                }
            }


            saleMstDto.setPlcySctnCd(PHONE_FOR_MSP);  //단말기 설정
            MspSaleSubsdMstDto mstDto = new MspSaleSubsdMstDto();
            try {
                mstDto = this.mspService.getLowPriceChargeInfoByProdList(list.getRprsPrdtId(), saleMstDto, "N",
                        mspSalePlcyMstDto.getOrgnId(), OPER_PHONE_NUMBER_TRANS, mspSalePlcyMstDto.getAgrmTrm(), saleMstDto.getRateCd(), "Y"
                        , cdInfo, onOffType,modelMonthly);

                if (!ObjectUtils.isEmpty(mstDto)) {
                    List<MspSaleSubsdMstDto> tempList = new ArrayList<MspSaleSubsdMstDto>();
                    tempList.add(mstDto);
                    list.setMspSaleSubsdMstListForLowPrice(tempList);
                }

                // 요금제 조회 null 일 경우 추천요금제로 재 조회     ????????????
                //if (ObjectUtils.isEmpty(mstDto)) {
                    /*mstDto = this.mspService.getLowPriceChargeInfoByProdList(list.getRprsPrdtId(), saleMstDto, "N",
                            mspSalePlcyMstDto.getOrgnId(), OPER_PHONE_NUMBER_TRANS, "24", null, "Y", cdInfo, onOffType);*/

                    //lteList.remove(list);
                //}

            } catch (McpCommonException e) {
                logger.debug("정책정보가 없는 상품 입니다.====>[" + list.getProdNm() + "]");
            } finally {
                if (!ObjectUtils.isEmpty(mstDto)) {
                    list.setPayMnthAmt(mstDto.getPayMnthUnContAmt(list.getAgrmTrmBase())); // 월 단말금
                    list.setInstAmt(mstDto.getInstAmt());       // 원금 최저가
                    list.setRateNm(mstDto.getRateNm());         // 대표/일반 요금제 명
                    list.setRateCd(mstDto.getRateCd());         // 대표/일반 요금제 코드
                    list.setBaseAmt(mstDto.getTotalPayAmout()); // 월 납부 부가세 포함
                    list.setRepRateNm(mstDto.getRateNm());      // 대표 요금제명
                }
            }
        });

        // 상품정렬 처리
        listOrderEnum.orderStragety(lteList);

        rtnJson.put("RESULT_CODE", AJAX_SUCCESS);
        rtnJson.put("phoneListLte", lteList);
        rtnJson.put("totalCount", lteList.size());
        return rtnJson;
    }

}