package com.ktmmobile.msf.usim.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.DTL_CD_OBJ_BASE;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_DIRECT_USIM_PRICE;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_USIM_REGI_SITE_LIST;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_USIM_PRICE_INFO;
import static com.ktmmobile.msf.system.common.constants.Constants.GROUP_CODE_MARKET_JOIN_USIM_INFO;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.USIM_PRICE_EXCEPTION;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.LTE_FOR_MSP;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.ONLINE_FOR_MSP;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.USIM_FOR_MSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.AuthSmsDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mspservice.MspService;
import com.ktmmobile.msf.system.common.mspservice.dto.MspPlcyOperTypeDto;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.form.common.constant.PhoneConstant;
import com.ktmmobile.msf.common.dto.OrderEnum;
import com.ktmmobile.msf.common.dto.PhoneProdBasDto;
import com.ktmmobile.msf.requestReview.service.RequestReviewService;
import com.ktmmobile.msf.usim.dto.KtRcgDto;
import com.ktmmobile.msf.usim.dto.UsimBasDto;
import com.ktmmobile.msf.usim.dto.UsimMspRateDto;
//import com.ktmmobile.msf.usim.service.UsimService;
import com.ktmmobile.msf.usim.service.UsimService;


@Controller
public class UsimController {

    private static Logger logger = LoggerFactory.getLogger(UsimController.class);

    //private static final String String = null;

    /**조직 코드  */
    @Value("${sale.orgnId}")
    private String orgnId;

    /**자급제 조직 코드  */
    @Value("${sale.sesplsOrgnId}")
    private String sesplsOrgnId;

    /**유심ip  */
    @Value("${usim.selUsimIp}")
    private String selUsimIp;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    UsimService usimService;

    @Autowired
    MspService mspService;

    @Autowired
    RequestReviewService requestReviewService;

    /**
    * @Description : Usim 잔액조회
    * @param model
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 18.
    */
    @RequestMapping(value="/usim/selectKtRcgSeqAjax.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectKtRcgSeqAjax(@ModelAttribute KtRcgDto inputKtRcgDto, @ModelAttribute AuthSmsDto authSmsDtoPra,Model model) {

         Map<String, Object> map = new HashMap<String,Object>();

         //인증정보 체크
         AuthSmsDto authSmsDto = new AuthSmsDto();
         authSmsDto.setMenu("smsPrePaid");
         authSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);
         if (authSmsDto == null) {
             map.put("RESULT_CODE", "0001");
             return map;
         }

         if (!SessionUtils.authSmsBeanCheck(authSmsDtoPra)) {
             map.put("RESULT_CODE", "0002");
             return map;
         }

         try {

             //사용 요금제 조회
             AuthSmsDto userInfo = usimService.selectUserChargeInfo(authSmsDto);

             map.put("selectUserChargeInfo", userInfo);

             if (userInfo != null) {

                 //인증 받은 휴대폰번호 세팅
                 inputKtRcgDto.setiSubscriberNo(authSmsDto.getPhoneNum());

                 inputKtRcgDto.setiReqType("WEB_REMAINS_VIEW");
                 inputKtRcgDto.setiReqIp(selUsimIp);
                 usimService.selectKtRcgSeq(inputKtRcgDto);

                 //usimService.selectKtRcgSeq 호출 후 2초간 딜레이 후 usimService.selectRcg 호출
                 Thread.sleep(3000);

                 map.put("I_RCG_SEQ",inputKtRcgDto.getoRcgSeq());
                 map.put("O_RES_CODE","");
                 map.put("O_AMOUNT","");
                 map.put("O_OLD_REMAINS","");
                 map.put("O_REMAINS","");
                 map.put("O_OLD_EXPIRE","");
                 map.put("O_EXPIRE","");
                 map.put("O_RET_CODE","");
                 map.put("O_RET_MSG","");
                 usimService.selectRcg(map);

              }

              map.put("RESULT_CODE", Constants.AJAX_SUCCESS);

         } catch (Exception e) {
             map.put("RESULT_CODE", "0003");
         }

        return map;
    }

    /**
     * <pre>
     * 설명     : Usim 상품 정보 조회
     *           가입비 조회 , 할인 조회
     * @return
     * </pre>
     */
    @RequestMapping(value = {"/usim/selectUsimBasJoinPriceAjax.do", "/m/usim/selectUsimBasJoinPriceAjax.do"})
    @ResponseBody
    public Map<String, Object> selectUsimBasJoinPriceAjax(@ModelAttribute UsimBasDto usimBasDto, BindingResult bind) {
        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String,Object>();

        rtnMap.put("selectJoinUsimPrice",usimService.selectJoinUsimPriceNew(usimBasDto));
        //할인조회
        UsimMspRateDto selectUsimDcamt = usimService.selectUsimDcamt(usimBasDto.getRateCd());

        rtnMap.put("selectUsimDcamt",selectUsimDcamt);

        rtnMap.put("inputDto", usimBasDto);
        rtnMap.put("RESULT_CODE", "0001");

        return rtnMap;
    }

    /**
     * <pre>
     * 설명     :
     * 접점, 가입유형 , eSIM 경우 에 따라
     * USIM, eSIM 가입비 , SIM 무료, 유료
     * @return
     * </pre>
     */
    @RequestMapping(value = "/usim/getSimInfoAjax.do")
    @ResponseBody
    public Map<String, String> getSimInfoAjax(@ModelAttribute UsimBasDto usimBasDto) {

        if (StringUtils.isBlank(usimBasDto.getOperType()) || StringUtils.isBlank(usimBasDto.getDataType())) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }


        if (StringUtils.isBlank(usimBasDto.getOrgnId()) ) {
            usimBasDto.setOrgnId(orgnId);
        }


        return usimService.getSimInfo(usimBasDto);
    }



    /**
     * <pre>
     * 설명     : Usim 상품 약정없는 할인조회
     *           가입비 조회
     * @return
     * </pre>
     */
    @RequestMapping(value = "/usim/selectUsimDcamtAjax.do")
    @ResponseBody
    public Map<String, Object> selectUsimDcamtAjax(@ModelAttribute UsimMspRateDto usimMspRateDto, BindingResult bind) {
        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String,Object>();

        rtnMap.put("selectUsimDcamt",usimService.selectUsimDcamt(usimMspRateDto.getRateCd()));

        rtnMap.put("inputDto", usimMspRateDto);
        rtnMap.put("RESULT_CODE", "0001");

        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : Usim 상품 약정할인 조회 New 정책부터 죄다들고온다
     *           가입비 조회

     * @return
     * </pre>
     */
    @RequestMapping(value = {"/usim/selectUsimBasShopListAllAjax.do", "/m/usim/selectUsimBasShopListAllAjax.do"})
    @ResponseBody
    public Map<String, Object> getUsimProdInfoAjax(@ModelAttribute MspSalePlcyMstDto mspSalePlcyMstDto
            , @RequestParam(defaultValue="") String prdtIndUsimCd
            , BindingResult bind
            ,@RequestParam(defaultValue="CHARGE_ROW",value="orderEnum") OrderEnum orderEnum) {
        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String,Object>();
        MspSalePlcyMstDto inputDto = new MspSalePlcyMstDto();
        inputDto.setSalePlcyCd(mspSalePlcyMstDto.getSalePlcyCd());
        inputDto.setPrdtSctnCd(mspSalePlcyMstDto.getPrdtSctnCd());
        inputDto.setPrdtId(mspSalePlcyMstDto.getPrdtId());
        inputDto.setOperType(mspSalePlcyMstDto.getOperType());
        inputDto.setAgrmTrm(mspSalePlcyMstDto.getAgrmTrm());
        inputDto.setOrgnId(mspSalePlcyMstDto.getOrgnId());
        rtnMap.put("inputDto", inputDto);


        //필수값 체크
        if(null == mspSalePlcyMstDto.getPayClCd() || mspSalePlcyMstDto.getPayClCd().equals("")
                || null == mspSalePlcyMstDto.getPrdtSctnCd() ||  mspSalePlcyMstDto.getPrdtSctnCd().equals("")){
            throw new McpCommonJsonException("-1", ExceptionMsgConstant.NO_EXSIST_SALE_PRDT);
        }

        //약정안오면 강제로 무약정만듬
        if(null==mspSalePlcyMstDto.getAgrmTrm() || mspSalePlcyMstDto.getAgrmTrm().equals("")){
            mspSalePlcyMstDto.setAgrmTrm("0");
        }

        List<UsimBasDto> usimBasList = new ArrayList<UsimBasDto>();
        //상품정보가 없을경우 초기조회로 인식하고 BAS테이블 유심 상품 정보와 약정정보부터 들고온다.
        if(null == mspSalePlcyMstDto.getSalePlcyCd()  ||mspSalePlcyMstDto.getSalePlcyCd().equals("")){
            UsimBasDto usimBasDto = new UsimBasDto();
            usimBasDto.setPayClCd(mspSalePlcyMstDto.getPayClCd());
            usimBasDto.setDataType(mspSalePlcyMstDto.getPrdtSctnCd());
            usimBasDto.setPrdtIndCd(prdtIndUsimCd);

            //상품정보 조회
            usimBasList = usimService.selectUsimBasList(usimBasDto);

            if(usimBasList.size() < 1){
                throw new McpCommonJsonException("-2",ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
            }
            rtnMap.put("usimBasList", usimBasList);
            if (StringUtils.isBlank(mspSalePlcyMstDto.getPrdtId() )) {
                mspSalePlcyMstDto.setPrdtId(usimBasList.get(0).getPrdtId());
            } else {
                //정책이 있는 경우에 인자값으로 PrdtId 설정 한다.
                String prdtId = "";
                for(UsimBasDto itmeDto:usimBasList) {
                    if (mspSalePlcyMstDto.getPrdtId().equals(itmeDto.getPrdtId())) {
                        prdtId = itmeDto.getPrdtId();
                        break;
                    }
                }
                if (prdtId.equals("")) {
                    mspSalePlcyMstDto.setPrdtId(usimBasList.get(0).getPrdtId());
                } else {
                    mspSalePlcyMstDto.setPrdtId(prdtId);
                }
            }
        }

        //MSP 정책정보조회
        List<MspSalePlcyMstDto> selectUsimSalePlcyCdList = usimService.selectUsimSalePlcyCdList(mspSalePlcyMstDto);
        rtnMap.put("selectUsimSalePlcyCdList", selectUsimSalePlcyCdList);

        if(selectUsimSalePlcyCdList == null || selectUsimSalePlcyCdList.size()<1){
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
            return rtnMap;
            //throw new McpCommonJsonException("-2",ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }else {
            mspSalePlcyMstDto.setSalePlcyCd(selectUsimSalePlcyCdList.get(0).getSalePlcyCd());
        }

        //MSP 가입유형조회
        List<MspPlcyOperTypeDto> selectPlcyOperTypeList =  usimService.selectPlcyOperTypeList(mspSalePlcyMstDto);

        if(selectPlcyOperTypeList ==null || selectPlcyOperTypeList.size()<1){
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
            return rtnMap;
            //throw new McpCommonJsonException("-2",ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION);
        }
        rtnMap.put("selectPlcyOperTypeList", selectPlcyOperTypeList);
        rtnMap.put("RESULT_CODE", "0001");
        return rtnMap;
    }

    @SuppressWarnings("null")
    @RequestMapping(value = {"/usim/getUsimChargeListAjax.do", "/m/usim/getUsimChargeListAjax.do"})
    @ResponseBody
    public List<MspSaleSubsdMstDto> listChargeInfoUsim(
            @ModelAttribute MspSalePlcyMstDto mspSalePlcyMstDto
            , @RequestParam(defaultValue="CHARGE_ROW",value="orderEnum") OrderEnum orderEnum
            , @RequestParam(defaultValue="") String onOffType) {

        // 편의점용유심 / 자급제+유심
        if(!StringUtils.isBlank(mspSalePlcyMstDto.getOrgnId())){
            mspSalePlcyMstDto.setPlcyTypeCd(ONLINE_FOR_MSP); // 정책타입 (온라인직영)
            mspSalePlcyMstDto.setPlcySctnCd(USIM_FOR_MSP); // 정책유형 (유심)
            mspSalePlcyMstDto.setOrgnId(mspSalePlcyMstDto.getOrgnId()); // 기관코드

            List<MspSalePlcyMstDto> listMspSaleDto = mspService.listMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);

            if(listMspSaleDto != null && listMspSaleDto.size() > 0) {
                mspSalePlcyMstDto.setSalePlcyCd(listMspSaleDto.get(0).getSalePlcyCd());
            }
        }

        //MSP 요금제 조회
        ///MspSaleSubsdMstNewDto
        if (!StringUtils.isBlank(mspSalePlcyMstDto.getSalePlcyCd())) {
            MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
            mspSaleSubsdMstDto.setPrdtId(mspSalePlcyMstDto.getPrdtId());
            mspSaleSubsdMstDto.setSalePlcyCd(mspSalePlcyMstDto.getSalePlcyCd());
            mspSaleSubsdMstDto.setOldYn("N");
            if (StringUtils.isBlank(mspSalePlcyMstDto.getOrgnId()) ) {
                mspSaleSubsdMstDto.setOrgnId(orgnId);
            } else {
                mspSaleSubsdMstDto.setOrgnId(mspSalePlcyMstDto.getOrgnId());
            }

            mspSaleSubsdMstDto.setOperType(mspSalePlcyMstDto.getOperType());//가입유형
            mspSaleSubsdMstDto.setAgrmTrm(mspSalePlcyMstDto.getAgrmTrm());//입력받은 할부기간을 약정기간으로 세팅한다.
            mspSaleSubsdMstDto.setRateCd(null);
            mspSaleSubsdMstDto.setNoArgmYn(mspSalePlcyMstDto.getNoArgmYn());//Y:무약정요금은은 표기 하지 않는다.
            mspSaleSubsdMstDto.setPayClCd(mspSalePlcyMstDto.getPayClCd());//선불 후불여부
            mspSaleSubsdMstDto.setOnOffType(onOffType);

            if(mspSalePlcyMstDto.getPayClCd().equals("PP")){
                mspSaleSubsdMstDto.setForFrontFastYn("C");
            }

            //기본적으로 단말 설정
            if (StringUtils.isBlank(mspSalePlcyMstDto.getPlcySctnCd())) {
                mspSaleSubsdMstDto.setPlcySctnCd(PhoneConstant.PHONE_FOR_MSP);
            } else {
                mspSaleSubsdMstDto.setPlcySctnCd(mspSalePlcyMstDto.getPlcySctnCd());
            }

            /*
            As-Is salePlcyCd 하나로만 조회
            List<MspSaleSubsdMstDto> chargeList = mspService.listChargeInfoUsim(mspSaleSubsdMstDto);
            List<MspSaleSubsdMstDto> xmlList = usimService.listChargeInfoUsimXml(chargeList, "02"); //유심요금
            */

            //To-Be salePlcyCd 전체 조회
            List<MspSaleSubsdMstDto> chargeAllList = new ArrayList<MspSaleSubsdMstDto>();
            List<MspSaleSubsdMstDto> chargeList = null;
            List<MspSalePlcyMstDto> selectUsimSalePlcyCdList = null;
            String[] prdtIdArr = mspSaleSubsdMstDto.getPrdtId().split(",");
            boolean existChk;
            for(String prdtId : prdtIdArr) {
                mspSalePlcyMstDto.setPrdtId(prdtId);
                mspSalePlcyMstDto.setPlcySctnCd(mspSaleSubsdMstDto.getPlcySctnCd());
                selectUsimSalePlcyCdList = usimService.selectUsimSalePlcyCdList(mspSalePlcyMstDto);
                for(MspSalePlcyMstDto item : selectUsimSalePlcyCdList) {
                    mspSaleSubsdMstDto.setPrdtId(prdtId);
                    mspSaleSubsdMstDto.setSalePlcyCd(item.getSalePlcyCd());
                    mspSaleSubsdMstDto.setDesignYn("Y");
                    chargeList = mspService.listChargeInfoUsim(mspSaleSubsdMstDto);

                    for(MspSaleSubsdMstDto chargeInfo : chargeList) {
                        if(chargeAllList.size() == 0) {
                            chargeAllList.add(chargeInfo);

                        }else {
                            existChk = false;
                            for(MspSaleSubsdMstDto oriItem : chargeAllList) {
                                if(oriItem.getRateCd().equals(chargeInfo.getRateCd())) {
                                    existChk = true;
                                }
                            }
                            if(!existChk) {
                                chargeAllList.add(chargeInfo);
                            }
                        }
                    }
                }
            }

            List<MspSaleSubsdMstDto> xmlList = usimService.listChargeInfoUsimXml(chargeAllList, "02"); //유심요금

            //최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
            PhoneProdBasDto temp = new PhoneProdBasDto();
            temp.setMspSaleSubsdMstListForLowPrice(xmlList);
            temp.setOrderEnum(orderEnum);
            if(xmlList != null && xmlList.size() > 0) {
                temp.doSort();
            }
            return xmlList;

        }else {
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_REGI_SITE_LIST,mspSalePlcyMstDto.getOrgnId());

            if (nmcpCdDtlDto == null) {
                throw new McpCommonJsonException("0001",F_BIND_EXCEPTION);
            }

            if (StringUtils.isBlank(mspSalePlcyMstDto.getOperType()) ) {
                throw new McpCommonJsonException("0006" ,BIDING_EXCEPTION);
            }

            if (StringUtils.isBlank(nmcpCdDtlDto.getExpnsnStrVal3()) ) {
                throw new McpCommonJsonException("0007" ,BIDING_EXCEPTION);
            }

            // 요금제
            Map<String, String> inputMap = new HashMap<String, String>();
            inputMap.put("code", nmcpCdDtlDto.getExpnsnStrVal3());
            inputMap.put("orgnId", mspSalePlcyMstDto.getOrgnId());
            inputMap.put("onOffType", onOffType);
            inputMap.put("operType", mspSalePlcyMstDto.getOperType());
            inputMap.put("rateCd", mspSalePlcyMstDto.getRateCd());
            //inputMap.put("prdtSctnCd", LTE_FOR_MSP);  //--getPrdtSctnCd
            inputMap.put("prdtSctnCd", mspSalePlcyMstDto.getPrdtSctnCd());

            RestTemplate restTemplate = new RestTemplate();
            MspSaleSubsdMstDto[] chargeTemList = restTemplate.postForObject(apiInterfaceServer + "/prepia/rateList", inputMap, MspSaleSubsdMstDto[].class);
            List<MspSaleSubsdMstDto> chargeList  = Arrays.asList(chargeTemList);
            List<MspSaleSubsdMstDto> xmlList = usimService.listChargeInfoUsimXml(chargeList, "02"); //유심요금

            //최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
            PhoneProdBasDto temp = new PhoneProdBasDto();
            temp.setMspSaleSubsdMstListForLowPrice(xmlList);
            temp.setOrderEnum(orderEnum);
            if(xmlList != null && xmlList.size() > 0) {
                temp.doSort();
            }

            return xmlList;
        }
    }
}
