package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.cmmn.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

import java.util.*;
import java.util.stream.Collectors;


import com.ktmmobile.mcp.mypage.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.msp.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.mypage.mapper.MypageMapper;


@RestController
public class MypageController {

    private static final Logger logger = LoggerFactory.getLogger(MypageController.class);

    @Autowired
    MypageMapper mypageMapper;

    /**
     * MCP 휴대폰 회선관리 리스트를 가지고 온다.
     * @param userId
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/mypage/cntrList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> cntrList(@RequestBody HashMap<String, String> map) {

        List<McpUserCntrMngDto> cntrList = null;
        try {
            // Database 에서 조회함.
            cntrList = mypageMapper.selectCntrList(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return cntrList;
    }

    /**
     * MCP 휴대폰 회선관리 검증 리스트
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/mypage/cntrMngList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> cntrMngList(@RequestBody HashMap<String, String> map) {

        List<McpUserCntrMngDto> cntrList = null;
        try {
            // Database 에서 조회함.
            cntrList = mypageMapper.selectCntrMngList(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return cntrList;
    }

    /**
     * 고객번호의 고객유형 조회
     * @param userId
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/mypage/customerType", method = {RequestMethod.POST,RequestMethod.GET})
    public String customerType(@RequestBody String custId) {

        String customerType = "";

        try {
            // Database 에서 조회함.
            customerType = mypageMapper.selectCustomerType(custId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return customerType;
    }

    /**
     * 휴대폰 회선에 따른 요금제 정보를 가지고 온다.
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     */
    @RequestMapping(value = "/mypage/socDesc", method = {RequestMethod.POST,RequestMethod.GET})
    public McpUserCntrMngDto socDesc(@RequestBody String svcCntrNo) {

        McpUserCntrMngDto socDesc = null;

        try {

            // Database 에서 조회함.
            socDesc = mypageMapper.selectSocDesc(svcCntrNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return socDesc;
    }

    /**
     * 휴대폰 회선에 따른 단말기할인 정보를 가지고 온다.
     * @param svcCntrNo
     * @return MspJuoAddInfoDto
     */
    @RequestMapping(value = "/mypage/mspAddInfo", method = {RequestMethod.POST,RequestMethod.GET})
    public MspJuoAddInfoDto mspAddInfo(@RequestBody String svcCntrNo) {

        MspJuoAddInfoDto mspAddInfo = null;

        try {

            // Database 에서 조회함.
            mspAddInfo = mypageMapper.selectMspAddInfo(svcCntrNo);

            return mspAddInfo;

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * 회원아이디 정회원 정보 조회
     * @param userId
     * @return List<UserVO>
     */
    @RequestMapping(value = "/mypage/userMultiLine", method = {RequestMethod.POST,RequestMethod.GET})
    public List<UserVO> userMultiLine(@RequestBody HashMap<String, String> map) {

        List<UserVO> userMultiLine = null;

        try {

            // Database 에서 조회함.
            userMultiLine = mypageMapper.selectUserMultiLine(map);

            return userMultiLine;

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * 전화번호(개통, 일시정지), 고객명의 전화번호 저보 조회
     * @param name
     * @param phone1
     * @param phone2
     * @param phone3
     * @return String
     */
    @RequestMapping(value = "/mypage/contractNum", method = RequestMethod.POST)
    public String contractNum(@RequestBody HashMap<String, String> map) {

        String contractNum = null;

        try {

            // Database 에서 조회함.
            contractNum = mypageMapper.selectContractNum(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return contractNum;
    }

    /**
     * 회선 계약정보 조회
     * @param
     * @return Map<String, String>
     */
    @RequestMapping(value = "/mypage/selectContractObj", method = RequestMethod.POST)
    public Map<String, String> selectContractObj(@RequestBody HashMap<String, String> paraMap) {

        Map<String, String> rtnMap = null;

        try {
            // Database 에서 조회함.
            rtnMap = mypageMapper.selectContractObj(paraMap);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnMap;
    }

    /**
     * 충전 내역조회
     * @param contractNum
     * @return List<UserVO>
     */
    @RequestMapping(value = "/mypage/ppsList", method = RequestMethod.POST)
    public List<MspPpsRcgTesDto> ppsList(@RequestBody String contractNum) {

        List<MspPpsRcgTesDto> ppsList = null;

        try {

            // Database 에서 조회함.
            ppsList = mypageMapper.selectPPSList(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return ppsList;
    }

    /**
     * 부가서비스 조회
     * @param ncn
     * @return List<McpRegServiceDto>
     */
    @RequestMapping(value = "/mypage/regService", method = RequestMethod.POST)
    public List<McpRegServiceDto> regService(@RequestBody String ncn) {

        List<McpRegServiceDto> regService = null;

        try {

            // Database 에서 조회함.
            regService = mypageMapper.selectRegService(ncn);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return regService;
    }

    /**
     * 서비스계약의 요금제 정보 조회
     * @param ncn
     * @return McpFarPriceDto
     */
    @RequestMapping(value = "/mypage/farPricePlan", method = RequestMethod.POST)
    public McpFarPriceDto farPricePlan(@RequestBody String ncn) {

        McpFarPriceDto farPricePlan = null;

        try {

            // Database 에서 조회함.
            farPricePlan = mypageMapper.selectFarPricePlan(ncn);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return farPricePlan;
    }

    /**
     * 요금제 변경 가능 리스트 조회
     * @param ncn
     * @return McpFarPriceDto
     */
    @RequestMapping(value = "/mypage/farPricePlanList", method = RequestMethod.POST)
    public List<McpFarPriceDto> farPricePlanList(@RequestBody String rateCd){

        List<McpFarPriceDto> farPricePlanList = null;

        try {

            // Database 에서 조회함.
            farPricePlanList  = mypageMapper.selectFarPricePlanList(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return farPricePlanList;
    }

    /**
     * 요금제 할인금액
     * @param ncn
     * @return int
     */
    @RequestMapping(value = "/mypage/getPromotionDcSumAmt", method = RequestMethod.POST)
    public int getPromotionDcSumAmt(@RequestBody String rateCd){

        int promotionDcSumAmt = 0;

        try {
            promotionDcSumAmt  = mypageMapper.getPromotionDcSumAmt(rateCd);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return promotionDcSumAmt;
    }


    /**
     * 요금제  월 납부 금액 확인
     * @param ncn
     * 월납부금액 (VAT) - 프로모션 할인 금액
     * @return int
     */
    @RequestMapping(value = "/mypage/getRateInfo", method = RequestMethod.POST)
    public MspSaleSubsdMstDto getRateInfo(@RequestBody String rateCd){

        MspSaleSubsdMstDto rtnRateInfo = null;

        try {
            rtnRateInfo  = mypageMapper.getRateInfo(rateCd);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        //logger.info("[WOO]payMnthChargeAmtMap"+ObjectUtils.convertObjectToString(rtnRateInfo));

        return rtnRateInfo;
    }

    /**
     * 요금제  월 납부 금액 확인
     * @param ncn
     * 월납부금액 (VAT) - 프로모션 할인 금액
     * @return int
     */
    @RequestMapping(value = "/mypage/getRateInfoListByPromotionDate", method = RequestMethod.POST)
    public List<MspSaleSubsdMstDto> getRateInfoListByPromotionDate(@RequestBody String promotionDate){

        List<MspSaleSubsdMstDto> rtnRateList = null;

        try {
            rtnRateList  = mypageMapper.getRateInfoListByPromotionDate(promotionDate);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnRateList;
    }

    /**
     * 요금제 변경 가능 개수 조회
     * @param ncn
     * @return McpFarPriceDto
     */
    @RequestMapping(value = "/mypage/countFarPricePlanList", method = RequestMethod.POST)
    public int countFarPricePlanList(@RequestBody String rateCd){


        int countFarPricePlanList = 0;
        try {

            // Database 에서 조회함.
            countFarPricePlanList  = mypageMapper.countFarPricePlanList(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return countFarPricePlanList;
    }

    /**
     * 요금제 변경 가능 개수 조회
     * @param rateCd
     * @return int
     */
    @RequestMapping(value = "/mypage/farPricePlanListCount", method = RequestMethod.POST)
    public int farPricePlanListCount(@RequestBody String rateCd) {

        int count = 0;

        try {

            // Database 에서 조회함.
            count = mypageMapper.selectFarPricePlanListCount(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return count;
    }

    /**
     * 요금제 변경 정보 조회 select
     * tobe
     * @param rateCd
     * @return int
     */
    @RequestMapping(value = "/mypage/farPriceAddInfo", method = RequestMethod.POST)
    public String farPriceAddInfo(@RequestBody HashMap<String, String> map) {

        String farPriceAddInfo = null;

        try {

            // Database 에서 조회함.
            farPriceAddInfo = mypageMapper.selectFarPriceAddInfo(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return farPriceAddInfo;
    }

    /**
     * 제주항공 포인트 리스트
     * @param contractNum
     * @return int
     */
    @RequestMapping(value = "/mypage/jehuList", method = RequestMethod.POST)
    public List<JehuDto> jehuList(@RequestBody HashMap<String, Object> map) {

        List<JehuDto> jehuList = null;

        try {

            HashMap<String,String> param = new HashMap<String,String>();
            param.put("contractNum", (String)map.get("contractNum"));

            // Database 에서 조회함.
            jehuList = mypageMapper.selectJehuList(param).stream().skip((int)map.get("skipResult")).limit((int)map.get("maxResult")).collect(Collectors.toList());

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return jehuList;
    }

    /**
     * 제휴 요금 갯수 조회
     * @param contractNum
     * @return int
     */
    @RequestMapping(value = "/mypage/jehuListCount", method = RequestMethod.POST)
    public int jehuListCount(@RequestBody String contractNum) {

        int count = 0;

        try {

            // Database 에서 조회함.
            count = mypageMapper.selectJehuListCount(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return count;
    }

    /**
     * 제주항공 포인트 리스트갯수
     * @param contractNum
     * @return int
     */
    @RequestMapping(value = "/mypage/jehuListAll", method = RequestMethod.POST)
    public List<JehuDto> jehuListAll(@RequestBody String contractNum) {

        List<JehuDto> jehuListAll = null;

        try {

            // Database 에서 조회함.
            jehuListAll = mypageMapper.selectJehuListAll(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return jehuListAll;
    }

    /**
     * 회선별 마케팅 동의 여부 조회
     * @param contractNum
     * @return Map<String, String>
     */
    @RequestMapping(value = "/mypage/mspMrktAgrYn", method = RequestMethod.POST)
    public Map<String, String> mspMrktAgrYn(@RequestBody String contractNum) {

        Map<String, String> map = null;

        try {

            // Database 에서 조회함.
            map = mypageMapper.selectMspMrktAgrYn(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return map;
    }

    /**
     * 회선별 마케팅 동의 여부 저장
     * @param contractNum
     * @return
     */
    @RequestMapping(value = "/mypage/callMspMrktAgrYn", method = RequestMethod.POST)
    public void callMspMrktAgrYn(@RequestBody HashMap<String, String> map) {

        try {

            // Database 에서 조회함.
            mypageMapper.callMspMrktAgr(map);

        } catch(Exception e) {
            throw e;
        }

    }

    /**
     * 선불 요금제 사용 여부 조회
     * @param contractNum
     * @return int
     */
    @RequestMapping(value = "/mypage/prePayment", method = RequestMethod.POST)
    public int prePayment(@RequestBody String contractNum) {

        int count = 0;

        try {

            // Database 에서 조회함.
            count = mypageMapper.selectPrePayment(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return count;
    }

    /**
     * 친구 추천 현황 조회
     * @param commendId
     * @return List<CommendStateDto>
     */
    @RequestMapping(value = "/mypage/commendStateList", method = RequestMethod.POST)
    public List<CommendStateDto> commendStateList(@RequestBody String commendId) {

        List<CommendStateDto> commendStateList = null;

        try {

            // Database 에서 조회함.
            commendStateList = mypageMapper.selectCommendStateList(commendId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return commendStateList;
    }


    /**
     * 친구 추천 현황 조회  && KT인터넷 가입 현황
     * @return List<CommendStateDto>
     */
    @RequestMapping(value = "/mypage/selectCommendAllStateList", method = RequestMethod.POST)
    public List<CommendStateDto> selectCommendAllStateList(@RequestBody HashMap<String, String> map) {
        List<CommendStateDto> commendStateList = null;

        String internetId = map.get("internetId");
        String commendId = map.get("commendId");


        try {

            if (StringUtils.isNotBlank(internetId)) {
                // 존재 + null 아님 + "" 아님 + 공백 아님
                // Database 에서 조회함.
                commendStateList = mypageMapper.selectCommendAllStateList(map);
            } else {
                commendStateList = mypageMapper.selectCommendStateList(commendId);
            }


        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return commendStateList;
    }



    /**
     * 친구 추천 현황 조회
     * @param  map commendId : 친구 조대 아이디  , lstComActvDate : 개통일 기준
     *
     * @return List<CommendStateDto>
     */
    @RequestMapping(value = "/mypage/getFriendInvitationList", method = RequestMethod.POST)
    public List<CommendStateDto> getFriendInvitationList(@RequestBody HashMap<String, String> map) {

        List<CommendStateDto> commendStateList = null;
        try {
            // Database 에서 조회함.
            commendStateList = mypageMapper.getFriendInvitationList(map);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return commendStateList;
    }

    /**
     * 친구 추천 현황 조회
     * @param name
     * @param phone
     * @return String
     */
    @RequestMapping(value = "/mypage/svcCntrNo", method = RequestMethod.POST)
    public String svcCntrNo(@RequestBody HashMap<String, String> map) {

        String svcCntrNo = null;

        try {

            // Database 에서 조회함.
            svcCntrNo = mypageMapper.selectSvcCntrNo(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return svcCntrNo;
    }

    /**
     * 부가서비스 정보 조회
     * @param juoFeatureDto
     * @return
     */
    @RequestMapping(value = "/mypage/getJuoFeature", method = RequestMethod.POST)
    public JuoFeatureDto getJuoFeature(@RequestBody JuoFeatureDto juoFeatureDto) {

        JuoFeatureDto rtn = new JuoFeatureDto();

        try {

            // Database 에서 조회함.
            rtn = mypageMapper.getJuoFeature(juoFeatureDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtn;
    }

    /**
     * 정회원 아닌 사용자 요금제 조회
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     */
    @RequestMapping(value = "/mypage/socDescNoLogin", method = RequestMethod.POST)
    public McpUserCntrMngDto socDescNoLogin(@RequestBody String svcCntrNo) {


        McpUserCntrMngDto socDescNoLogin = null;

        try {

            // Database 에서 조회함.
            socDescNoLogin = mypageMapper.selectSocDescNoLogin(svcCntrNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return socDescNoLogin;
    }

    /**
     * 휴대폰 회선정보 조회
     * @param McpUserCntrMngDto
     * @return McpUserCntrMngDto
     */
    @RequestMapping(value = "/mypage/cntrListNoLogin", method = RequestMethod.POST)
    public McpUserCntrMngDto cntrListNoLogin(@RequestBody McpUserCntrMngDto param) {

        McpUserCntrMngDto cntrListNoLogin = null;

        try {

            // Database 에서 조회함.
            cntrListNoLogin = mypageMapper.selectCntrListNoLogin(param);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return cntrListNoLogin;
    }

    /**
     * 부가서비스 정보 조회
     * @param JuoFeatureDto
     * @return JuoFeatureDtos
     */
    @RequestMapping(value = "/mypage/juoFeature", method = RequestMethod.POST)
    public JuoFeatureDto juoFeature(@RequestBody JuoFeatureDto param) {

        JuoFeatureDto juoFeature = null;

        try {

            // Database 에서 조회함.
            juoFeature = mypageMapper.selectJuoFeature(param);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return juoFeature;
    }


    /**
     * userId 기준 마케팅 수신 동의 여부 히스토리 저장 프로시져 호출(신규가입시)
     * @param JoinDto
     * @return
     */
    @RequestMapping(value = "/mypage/callMcpMrktAgrNew", method = {RequestMethod.POST,RequestMethod.GET})
    public void callMcpMrktAgrNew(@RequestBody JoinDto joinDto) {


        try {

            HashMap<String,String> hm = new HashMap<String,String>();
            hm.put("userId", joinDto.getUserId());
            hm.put("emailAgrYn", joinDto.getAgreeEmail());
            hm.put("smsAgrYn", joinDto.getAgreeSMS());
            mypageMapper.callMcpMrktAgrNew(hm);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * userId 기준 마케팅 수신 동의 여부 히스토리 저장 프로시져 호출
     * @param JoinDto
     * @return
     */
    @RequestMapping(value = "/mypage/callMcpMrktAgr", method = {RequestMethod.POST,RequestMethod.GET})
    public void callMcpMrktAgr(@RequestBody JoinDto joinDto) {


        try {

            HashMap<String,String> hm = new HashMap<String,String>();
            hm.put("userId", joinDto.getUserId());
            hm.put("emailAgrYn", joinDto.getAgreeEmail());
            hm.put("smsAgrYn", joinDto.getAgreeSMS());
            mypageMapper.callMcpMrktAgr(hm);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * 금융제휴상품요금제 변경 서식지 이미지 합본 프로시져
     * @param JoinDto
     * @return
     */
    @RequestMapping(value = "/mypage/callMcpRateChgImg", method = {RequestMethod.POST,RequestMethod.GET})
    public void callMcpRateChgImg(@RequestBody McpRateChgDto mcpRateChgDto) {


        Map<String, String> map = null;

        try {
            map = mypageMapper.callMcpRateChgImg(mcpRateChgDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * 약정정보
     * @param JoinDto
     * @return
     */
    @RequestMapping(value = "/mypage/enggInfo1")
    public MspJuoAddInfoDto enggInfo1(@RequestBody String contractNum) {


        MspJuoAddInfoDto  mspJuoAddInfoDto= new MspJuoAddInfoDto();

        try {
            mspJuoAddInfoDto = mypageMapper.getEnggInfo(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspJuoAddInfoDto;
    }

    /**
     * 개통 채널정보
     * @param contractNum
     * @return
     */

    @RequestMapping(value = "/mypage/channelInfo", method = {RequestMethod.POST,RequestMethod.GET})
    public MspJuoAddInfoDto channelInfo(@RequestBody String contractNum) {


        MspJuoAddInfoDto  mspJuoAddInfoDto= new MspJuoAddInfoDto();

        try {
            mspJuoAddInfoDto = mypageMapper.getChannelInfo(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspJuoAddInfoDto;
    }

    /**
     * @Description : 해지 해야 할 부가 서비스 조회
     * @param  ctn : 전화번호
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    @RequestMapping(value = "/mypage/closeSubList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> closeSubList(@RequestBody String contractNum) {

        List<McpUserCntrMngDto> rtnList = new ArrayList<McpUserCntrMngDto>();


        try {
            rtnList = mypageMapper.getCloseSubList(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnList;
    }

    /**
     * 요금제셀프변경실패 INSERT
     * @param contractNum
     * @return
     */
    @RequestMapping(value = "/mypage/insertSocfailProcMst", method = {RequestMethod.POST})
    public boolean insertSocfailProcMst(@RequestBody McpServiceAlterTraceDto serviceAlterTrace) {

        boolean isSucssesYn = false;

        try {

            isSucssesYn = mypageMapper.insertSocfailProcMst(serviceAlterTrace);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return isSucssesYn;
    }

    /**
     * @Description : 가입 해야 할 부가 서비스 조회
     * @param
     * @return List<McpRequestSaleinfoDto>
     * @Author : papier
     * @Create Date : 2021. 11. 05.
     */
    @RequestMapping(value = "/mypage/romotionDcList", method = {RequestMethod.POST})
    public List<McpUserCntrMngDto> romotionDcList(@RequestBody String rateCd) {

        List<McpUserCntrMngDto>  rtnList = new ArrayList<McpUserCntrMngDto>();


        try {

            rtnList  = mypageMapper.getromotionDcList(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnList;
    }

    /**
     * @Description : My 사은품 목록
     * @param MyGiftDto
     * @return List<MyGiftDto>
     */
    @RequestMapping(value = "/mypage/giftCustList", method = {RequestMethod.POST})
    public List<MyGiftDto> giftCustList(@RequestBody MyGiftDto myGiftDto) {

        int skipResult = myGiftDto.getApiParam1();
        int maxResult = myGiftDto.getApiParam2();

        List<MyGiftDto> giftCustList = null;

        try {
            giftCustList = mypageMapper.getCustList(myGiftDto, new RowBounds(skipResult, maxResult));
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return giftCustList;
    }

    /**
     * @Description : My 사은품 수
     * @param MyGiftDto
     * @return int
     */
    @RequestMapping(value = "/mypage/giftCustListCount", method = {RequestMethod.POST})
    public int getCustListCount(@RequestBody MyGiftDto myGiftDto) {

        int getCustListCount = 0;
        try {

            getCustListCount = mypageMapper.getCustListCount(myGiftDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return getCustListCount;
    }

    /**
     * @Description : 회원가입시 회선 정보 조회
     * @param HashMap<String, String>
     * @return JuoSubInfoDto
     */
    @RequestMapping(value = "/mypage/juoSubInfo", method = RequestMethod.POST)
    public JuoSubInfoDto juoSubInfo(@RequestBody HashMap<String, String> map) {

        JuoSubInfoDto juoSubInfoDto = null;

        try {

            // Database 에서 조회함.
            juoSubInfoDto = mypageMapper.selectJuoSubInfo(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return juoSubInfoDto;
    }

    /**
     * 데이터 쉐어링 개통시 청구계정 정보 조회
     * @param custId
     * @return
     */
    @RequestMapping(value = "/mypage/selectBanSel", method = {RequestMethod.POST,RequestMethod.GET})
    public String selectBanSel(@RequestBody String contractNum) {

        String ban = "";

        try {
            // Database 에서 조회함.
            ban = mypageMapper.selectBanSel(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return ban;
    }

    /**
     * 명세서 재발송을 위한 정보 조회
     * @param custId
     * @return
     */
    @RequestMapping(value = "/mypage/billWayReSend")
    public BillWayChgDto billWayReSend(@RequestBody String contractNum) {

        BillWayChgDto resDto = new BillWayChgDto();

        try {
            // Database 에서 조회함.
            resDto = mypageMapper.billWayReSend(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return resDto;
    }



    /**
     * 유심변경 결과 확인
     * @param map
     * @return String
     */
    @RequestMapping(value = "/mypage/usimChgChk", method = {RequestMethod.POST,RequestMethod.GET})
    public String usimChgChk(@RequestBody HashMap<String, String> map) {

        String result = "";

        try {
            // Database 에서 조회함.
            result = mypageMapper.usimChgChk(map) + "";

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return result;
    }


    /**
     * MCP 휴대폰 회선관리 리스트를 가지고 온다. (명의변경)
     * @param userId
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/mypage/cntrListNmChg", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> cntrListNmChg(@RequestBody HashMap<String, String> map) {

        List<McpUserCntrMngDto> cntrList = null;
        try {
            // Database 에서 조회함.
            cntrList = mypageMapper.selectCntrListNmChg(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return cntrList;
    }


    /**
     * 통신자료 제공내역 요청리스트 가져오기
     */
    @RequestMapping(value = "/mypage/pvcCommDataList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<MspCommDatPrvTxnDto> pvcCommDataList(@RequestBody HashMap<String, String> map) {
        List<MspCommDatPrvTxnDto> prvDataList = null;
        try {
            // Database 에서 조회함.
            prvDataList = mypageMapper.pvcCommDataList(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return prvDataList;
    }

    /**
     * 통신정보 조회 신청
     * @param mspCommDatPrvTxnDto
     * @return
     */
    @RequestMapping(value = "/mypage/insertPrvCommData", method = RequestMethod.POST)
    public int mspCommDatPrvTxn(@RequestBody HashMap<String, String> map) {
        int insertCount = 0;

        try {
            insertCount = mypageMapper.insertPrvCommData(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }


    /**
     * 통신자료 제공내역상세 출력정보 가져오기
     */
    @RequestMapping(value = "/mypage/pvcCommDataDtlList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<MspCommDatPrvTxnDto> pvcCommDataDtlList(@RequestBody HashMap<String, String> map) {
        List<MspCommDatPrvTxnDto> prvDataList = null;
        try {
            // Database 에서 조회함.
            prvDataList = mypageMapper.pvcCommDataDtlList(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return prvDataList;
    }


    /**
     * 고객이 가입한 모든 회선정보를 가져온다.
     * @param userId
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/cs/selectOwnPhoNumList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> selectOwnPhoNumList(@RequestBody HashMap<String, String> map) {

        List<McpUserCntrMngDto> ownPhoNumList = null;
        try {
            // Database 에서 조회함.
            ownPhoNumList = mypageMapper.selectOwnPhoNumList(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return ownPhoNumList;
    }

    /**
     * 가입번호 조회이력을 저장한다.
     * @param map
     * @return
     */
    @RequestMapping(value = "/cs/insertOwnPhoNumChkHst", method = RequestMethod.POST)
    public int insertOwnPhoNumChkHst(@RequestBody HashMap<String, String> map) {
        int insertCount = 0;

        try {
            insertCount = mypageMapper.insertOwnPhoNumChkHst(map);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }

    /**
     * @Description : 안심보험가입 여부 조회
     * @param contractNum
     * @return String(보험명)
     * @Author : wooki
     * @CreateDate : 2022.10.04
     */
    @RequestMapping(value = "/mypage/getInsrInfo", method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String, String> getInsrInfo(@RequestBody String contractNum) {

        Map<String, String> insrInfo = null;

        try {
            insrInfo = mypageMapper.getInsrInfo(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return insrInfo;
    }

    /**
     * @Description : 휴대폰안심보험 정보 가져오기
     * @param insrCd
     * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.20
     */
    @RequestMapping(value = "/mypage/getInsrInfoByCd", method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String, String> getInsrInfoByCd(@RequestBody String insrCd) {

        Map<String, String> insrInfo = null;

        try {
            insrInfo = mypageMapper.getInsrInfoByCd(insrCd);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return insrInfo;
    }

    /**
     * @Description : 안심보험 가입신청 내역 조회
     * @param contractNum
     * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.20
     */
    @RequestMapping(value = "/mypage/getReqInsrData", method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String, String> getReqInsrData(@RequestBody long custReqKey) {

        Map<String, String> insrInfo = null;

        try {
            insrInfo = mypageMapper.getReqInsrData(custReqKey);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return insrInfo;
    }

    /**
     * @Description : org 스캔아이디 조회
     * @param contractNum
     * @return String
     * @Author : wooki
     * @CreateDate : 2022.10.20
     */
    @RequestMapping(value = "/mypage/getOrgScanId", method = {RequestMethod.POST,RequestMethod.GET})
    public String getOrgScanId(@RequestBody String contractNum) {

        String orgScanId = null;

        try {
            orgScanId = mypageMapper.getOrgScanId(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return orgScanId;
    }

    /**
     * @Description : 자급제 보상서비스 가입 상태 조회
     * @param contractNum
     * @return RwdOrderDto
     * @Author : hsy
     * @CreateDate : 2023.03.02
     */
    @RequestMapping(value = "/mypage/selectRwdInfoByCntrNum", method = {RequestMethod.POST})
    public RwdOrderDto selectRwdInfoByCntrNum(@RequestBody String contractNum) {

        RwdOrderDto rwdOrderDto = null;

        try {
            rwdOrderDto = mypageMapper.selectRwdInfoByCntrNum(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rwdOrderDto;
    }


    /**
     * @Description : 자급제 보상서비스 리스트 조회
     * @return List<RwdDto>
     * @Author : hsy
     * @CreateDate : 2023.03.02
     */
    @RequestMapping(value = "/mypage/selectRwdProdList", method = {RequestMethod.POST})
    public List<RwdDto> selectRwdProdList(@RequestBody(required = false) String rwdProdCd) {

        List<RwdDto> rwdProdList = null;

        try {
            rwdProdList = mypageMapper.selectRwdProdList(rwdProdCd);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rwdProdList;
    }

    /**
     * 2023.03.10 hsy
     * 자급제 보상서비스 가입 시 해당 imei 사용가능 여부 체크
     * @param  rwdOrderDto
     * @return String
     */
    @RequestMapping(value = "/mypage/checkImeiForRwd", method = {RequestMethod.POST})
    public String checkImeiForRwd(RwdOrderDto rwdOrderDto) {

        String possibleYn= "N";
        int usedCnt= 0;
        try {
            usedCnt = mypageMapper.checkImeiForRwd(rwdOrderDto);
        } catch(Exception e) {
            usedCnt= 1; // exception 발생 시 해당 imei로 가입불가처리
        }

        if(usedCnt <= 0) possibleYn= "Y";

        return possibleYn;
    }


    /**
     * @Description : 계약번호로 신청정보 조회
     * @param contractNum
     * @return List<RwdOrderDto>
     * @Author : hsy
     * @CreateDate : 2023.02.28
     */
    @RequestMapping(value = "/mypage/selectMcpRequestInfoByCntrNum", method = {RequestMethod.POST})
    public List<RwdOrderDto> selectMcpRequestInfoByCntrNum(@RequestBody String contractNum) {

        List<RwdOrderDto> rwdOrderDto = null;

        try {
            rwdOrderDto = mypageMapper.selectMcpRequestInfoByCntrNum(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rwdOrderDto;
    }

    /**
     * @Description : 자급제 보상서비스 order insert
     * @return HashMap<String, String>
     * @Author : hsy
     * @CreateDate : 2023.02.22
     */
    @RequestMapping(value = "/mypage/insertRwdOrder", method = {RequestMethod.POST})
    public HashMap<String, String> insertRwdOrder(@RequestBody RwdOrderDto rwdOrderDto) {

        HashMap<String, String> rtnMap = new HashMap<>();

        try {
            int result = mypageMapper.insertRwdOrder(rwdOrderDto);
            if(result == 1){
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청이 완료되었습니다.");
                rtnMap.put("RWD_SEQ", String.valueOf(rwdOrderDto.getRwdSeq()));
            }else{

                rtnMap.put("RESULT_CODE", "FAIL5");
                rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
            }
        } catch(Exception e) {
            rtnMap.put("RESULT_CODE", "FAIL6");
            rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
        }

        return rtnMap;
    }

    /**
     * 2023.03.13 hsy
     * 자급제 보상서비스 물리 파일명 추출
     * @return String
     */
    @RequestMapping(value = "/mypage/selectRwdNewFileNm", method = {RequestMethod.POST})
    public String selectRwdNewFileNm() {

        String fileNm = null;

        try {
            fileNm = mypageMapper.selectRwdNewFileNm();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return fileNm;
    }

    /**
     * 2023.05.19
     * 자급제 보상서비스 서식지 정보 조회
     * @return Map<String, String>
     */
    @RequestMapping(value = "/mypage/getRwdOrderData", method = {RequestMethod.POST})
    public Map<String, String> getRwdOrderData(@RequestBody String rwdSeq) {

        Map<String, String> rwdOrderData = null;

        try {
            rwdOrderData = mypageMapper.getRwdOrderData(rwdSeq);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rwdOrderData;
    }

    /**
     * @Description : 자급제 보상서비스 가입 상태 조회 by resNo (order테이블만 조회)
     * @param rwdOrderDto
     * @return String
     * @Author : hsy
     */
    @RequestMapping(value = "/mypage/selectRwdInfoByResNo", method = {RequestMethod.POST})
    public String selectRwdInfoByResNo(@RequestBody RwdOrderDto rwdOrderDto) {

        String existYn= "Y";
        int existCnt= 0;
        try {
            existCnt = mypageMapper.selectRwdInfoByResNo(rwdOrderDto);
        } catch(Exception e) {
            existCnt= 1;
        }

        if(existCnt <= 0) existYn= "N";

        return existYn;
    }

    /**
     * @Description : 자급제 보상서비스 order update
     * @return HashMap<String, String>
     */
    @RequestMapping(value = "/mypage/updateRwdOrder", method = {RequestMethod.POST})
    public HashMap<String, String> updateRwdOrder(@RequestBody RwdOrderDto rwdOrderDto) {

        HashMap<String, String> rtnMap = new HashMap<>();
        try {
            // 1. update 대상 get
            long rwdSeq= mypageMapper.getRwdOrderSeq(rwdOrderDto);
            rwdOrderDto.setRwdSeq(rwdSeq);

            // 2. update 처리
            int result = mypageMapper.updateRwdOrder(rwdOrderDto);
            if(result >= 1){
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청이 완료되었습니다.");
                rtnMap.put("RWD_SEQ",  String.valueOf(rwdOrderDto.getRwdSeq()));
            }else{
                rtnMap.put("RESULT_CODE", "FAIL5");
                rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
            }
        } catch(Exception e) {
            rtnMap.put("RESULT_CODE", "FAIL6");
            rtnMap.put("RESULT_MSG", "자급제 보상서비스 신청에 실패했습니다.");
        }

        return rtnMap;
    }

    /**
     * 회선별 동의 여부 및 동의시간 (M전산기준)
     * @param contractNum
     * @return
     */
    @RequestMapping(value = "/mypage/selectMspMrktAgrTime", method = RequestMethod.POST)
    public AgreeDto selectMspMrktAgrTime(@RequestBody String contractNum) {

        AgreeDto agreeInfo = null;
        try {
            agreeInfo = mypageMapper.selectMspMrktAgrTime(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return agreeInfo;
    }

    /**
     * cusInfo 이름, 생년월일 조회
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/mypage/getUserCusInfo", method = RequestMethod.POST)
    public Map<String, String> getUserCusInfo(@RequestBody String customerId) {

        Map<String, String> map = null;
        try {
            map = mypageMapper.getUserCusInfo(customerId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return map;
    }

    /**
     * @Description : 평생할인 부가서비스 기적용 대상(MSP_DIS_APD) insert
     * @param McpUserCntrMngDto
     * @return int
     * @Author : wooki
     * @CreateDate : 2023.11.10
     */
    @RequestMapping(value = "/mypage/insertDisApd", method = RequestMethod.POST)
    public int insertDisApd(@RequestBody McpUserCntrMngDto mngDto) {

        int cnt = 0;
        try {
            cnt  = mypageMapper.insertDisApd(mngDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return cnt;
    }


    /**
     * @Description : 요금제 변경 온라인 프로모션ID 조회
     * @param String
     * @return String
     * @Author : 개발팀 김동혁
     * @CreateDate : 2024.01.19
     */
    @RequestMapping(value = "/mypage/getChrgPrmtIdSocChg", method = {RequestMethod.POST})
    public String getChrgPrmtIdSocChg(@RequestBody String rateCd) {

        String prmtId = "";
        try {
            prmtId = mypageMapper.getChrgPrmtIdSocChg(rateCd);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return prmtId;
    }

    /**
     * 회선 계약정보 조회(이름,주민번호,전화번호).
     * @param
     * @return Map<String, String>
     */
    @RequestMapping(value = "/mypage/selectConSsnObj", method = RequestMethod.POST)
    public Map<String, String> selectConSsnObj(@RequestBody HashMap<String, String> paraMap) {

        Map<String, String> rtnMap = null;

        try {
            // Database 에서 조회함.
            rtnMap = mypageMapper.selectConSsnObj(paraMap);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnMap;
    }

    /**
     * 가족관계 법정대리인 조회
     * @param minorAgentSvcCntrNo
     * @return String
     */
    @RequestMapping(value = "/mypage/getMinorAgentInfo", method = RequestMethod.POST)
    public ChildInfoDto getMinorAgentInfo(@RequestBody String minorAgentSvcCntrNo) {

        ChildInfoDto minorAgent = null;
        try {
            // Database 에서 조회함.
            minorAgent = mypageMapper.selectMinorAgentInfo(minorAgentSvcCntrNo);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return minorAgent;
    }

    /**
     * 가족관계 자녀 회선 목록 조회
     * @param famSeq
     * @return List<ChildInfoDto>
     */
    @RequestMapping(value = "/mypage/childCntrList", method = RequestMethod.POST)
    public List<ChildInfoDto> childCntrList(@RequestBody String famSeq) {

        List<ChildInfoDto> childList = null;
        try {
            // Database 에서 조회함.
            childList = mypageMapper.selectChildCntrList(famSeq);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return childList;
    }

    /**
     * 가족관계 자녀 회선 조회
     * @param childInfoDto
     * @return ChildInfoDto
     */
    @RequestMapping(value = "/mypage/selectChildCntr", method = RequestMethod.POST)
    public ChildInfoDto selectChildCntr(@RequestBody ChildInfoDto childInfoDto) {

        ChildInfoDto childCntr = null;
        try {
            // Database 에서 조회함.
            childCntr = mypageMapper.selectChildCntr(childInfoDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return childCntr;
    }
}