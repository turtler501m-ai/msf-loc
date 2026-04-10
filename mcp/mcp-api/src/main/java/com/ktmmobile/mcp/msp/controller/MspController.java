package com.ktmmobile.mcp.msp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.cmmn.util.StringUtil;
import com.ktmmobile.mcp.msp.dto.*;
import com.ktmmobile.mcp.msp.mapper.MspMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.BIDING_EXCEPTION;
import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;


@RestController
public class MspController {

    private static final Logger logger = LoggerFactory.getLogger(MspController.class);

    @Autowired
    MspMapper mspMapper;

    /**자급제 조직 코드  */
    String sesplsOrgnId = "V000019481";

    /**
     * 현재 단품 ID 로 해당 대표모델ID를 조회한다.
     * @param prdtId
     * @return
     */

    @RequestMapping(value = "/msp/mspPhoneInfo", method = RequestMethod.POST)
    public PhoneMspDto mspPhoneInfo(@RequestBody String prdtId) {


        PhoneMspDto phoneMspDto = null;

        try {
            // Database 에서 조회함.
            phoneMspDto = mspMapper.findMspPhoneInfo(prdtId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return phoneMspDto;
    }

    /**
     * 판매정책정보를 조회한다.상품과 상관없이 기관별 조회를 한다.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspSalePlcyInfoByOnlyOrgn", method = RequestMethod.POST)
    public List<MspSalePlcyMstDto> mspSalePlcyInfoByOnlyOrgn(@RequestBody MspSalePlcyMstDto mspSalePlcyMstDto) {

        ObjectMapper om = new ObjectMapper();

        List<MspSalePlcyMstDto> mspSalePlcyMstDtoList = null;

        try {
            // Database 에서 조회함.
            mspSalePlcyMstDtoList = mspMapper.findMspSalePlcyInfoByOnlyOrgn(mspSalePlcyMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspSalePlcyMstDtoList;
    }

    /**
     * 판매정책정보를 조회한다.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspSaleOrgnMst", method = RequestMethod.POST)
    public List<MspSalePlcyMstDto> mspSaleOrgnMst(@RequestBody MspSalePlcyMstDto mspSalePlcyMstDto) {


        List<MspSalePlcyMstDto> mspSalePlcyMstDtoList = null;
        MspSalePlcyMstDto mspSalePlcy = null;


        try {
            // Database 에서 조회함.
            mspSalePlcyMstDtoList = mspMapper.findMspSaleOrgnMst(mspSalePlcyMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspSalePlcyMstDtoList;
    }

    /**
     * MSP 판매 상품및 수수료 정보 조회.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspSalePrdMst", method = RequestMethod.POST)
    public MspSalePrdtMstDto mspSalePrdMst(@RequestBody MspSalePrdtMstDto mspSalePrdtMstDto) {


        MspSalePrdtMstDto resultMspSalePrdtMstDto = null;

        try {
            // Database 에서 조회함.
            resultMspSalePrdtMstDto = mspMapper.findMspSalePrdMst(mspSalePrdtMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return resultMspSalePrdtMstDto;
    }

    /**
     * MSP 에서 제조사/공급사 정보를 조회한다.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     */
    @RequestMapping(value = "/msp/orgnMnfctMst", method = RequestMethod.POST)
    public List<MspOrgDto> orgnMnfctMst(@RequestBody MspOrgDto mspOrgDto) {


        List<MspOrgDto> mspOrgDtoList = null;

        try {
            // Database 에서 조회함.
            mspOrgDtoList = mspMapper.listOrgnMnfctMst(mspOrgDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspOrgDtoList;
    }

    /**
     * MSP 에서 제조사/공급사 정보를 조회한다.
     * @param MspSalePlcyMstDto mspSalePlcyMstDto
     * @return
     */
    @RequestMapping(value = "/msp/orgnMnfctMstRe", method = RequestMethod.POST)
    public List<MspOrgDto> orgnMnfctMstRe(@RequestBody String prodType) {

        List<MspOrgDto> mspOrgDtoList = null;
        try {
            // Database 에서 조회함.
            prodType = StringUtil.NVL(prodType, "01");
            logger.info("api prodType==>"+prodType);
            mspOrgDtoList = mspMapper.listOrgnMnfctMstRe(prodType);
        } catch(Exception e) {
            e.printStackTrace();
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspOrgDtoList;
    }

    /**
     * 해당정책코드에 해당하는 판매요금제 정보 리스트 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     */
    @RequestMapping(value = "/msp/listMspRateMst", method = RequestMethod.POST)
    public List<MspRateMstDto> listMspRateMst(@RequestBody MspRateMstDto mspRateMstDto) {


        List<MspRateMstDto> mspRateMstDtoList = null;

        try {
            // Database 에서 조회함.
            mspRateMstDtoList = mspMapper.listMspRateMst(mspRateMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspRateMstDtoList;
    }


    /**
     * 해당정책코드에 해당하는 판매요금제 정보 리스트 조회.
     * @param MspRateMstDto mspRateMstDto
     * noArgmYn Y 무약정 요금제
     *          N 약정 요금제
     * @return
     */
    @RequestMapping(value = "/msp/listMspRateAgrm", method = RequestMethod.POST)
    public List<MspRateMstDto> listMspRateAgrm(@RequestBody MspRateMstDto mspRateMstDto) {


        List<MspRateMstDto> mspRateMstDtoList = null;

        try {
            // Database 에서 조회함.
            mspRateMstDtoList = mspMapper.listMspRateAgrm(mspRateMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspRateMstDtoList;
    }




    /**
     * 해당정책코드에 해당하는 판매요금제 정보 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     */
    @RequestMapping(value = "/msp/findMspRateMst", method = RequestMethod.POST)
    public MspRateMstDto findMspRateMst(@RequestBody MspRateMstDto mspRateMstDto) {


        MspRateMstDto mspRateMstDtoList = null;

        try {
            // Database 에서 조회함.
            mspRateMstDtoList = mspMapper.findMspRateMst(mspRateMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspRateMstDtoList;
    }





    /**
     * 해당정책코드에 해당하는 판매요금제 정보 리스트 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspSaleAgrmMst", method = RequestMethod.POST)
    public List<MspSaleAgrmMst> mspSaleAgrmMst(@RequestBody String salePlcyCd) {


        List<MspSaleAgrmMst> mspSaleAgrmMst = null;

        try {
            // Database 에서 조회함.
            mspSaleAgrmMst = mspMapper.listMspSaleAgrmMst(salePlcyCd);
//            mspSaleAgrmMst = mspMapper.selectMspSaleAgrmMst(salePlcyCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspSaleAgrmMst;
    }


    @RequestMapping(value = "/msp/mspSaleAgrmMstSing", method = RequestMethod.POST)
    public List<MspSaleAgrmMst> mspSaleAgrmMstSing(@RequestBody String salePlcyCd) {

        List<MspSaleAgrmMst> mspSaleAgrmMst = new ArrayList<>();
        try {
            mspSaleAgrmMst = this.mspMapper.selectMspSaleAgrmMst(salePlcyCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);

        }
        return mspSaleAgrmMst;
    }


    /**
     * 해당정책코드에 해당하는 판매요금제 정보 리스트 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspSaleAgrmMst2", method = RequestMethod.POST)
    public List<MspSaleAgrmMst> mspSaleAgrmMst2(@RequestBody MspSaleAgrmMst mspSaleAgrmMstin) {


        List<MspSaleAgrmMst> mspSaleAgrmMst = null;

        String orgnId = mspSaleAgrmMstin.getOrgnId();
        String salePlcyCd = mspSaleAgrmMstin.getSalePlcyCd();
        if(orgnId == null) {
            orgnId = "";
        }
        if(salePlcyCd == null) {
            salePlcyCd = "";
        }

        try {
            // Database 에서 조회함.
            if(orgnId.contentEquals(sesplsOrgnId)) {
                // 자급제인 경우
                mspSaleAgrmMst = mspMapper.listMspSaleAgrmMst2(mspSaleAgrmMstin);
            } else {
                // 휴대폰/중고폰인 경우
                mspSaleAgrmMst = mspMapper.listMspSaleAgrmMst(salePlcyCd);
            }

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspSaleAgrmMst;
    }


    /**
     * 해당정책코드에 해당하는 판매요금제 정보 리스트 조회.
     * @param MspRateMstDto mspRateMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspSalePlcyMst", method = RequestMethod.POST)
    public MspSalePlcyMstDto mspSalePlcyMst(@RequestBody String salePlcyCd) {


        MspSalePlcyMstDto result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.getMspSalePlcyMst(salePlcyCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 코드 정보 조회
     * @param grpCd , cdVal
     * @return
     */
    @RequestMapping(value = "/msp/cmnGrpCdMst", method = RequestMethod.POST)
    public CmnGrpCdMst cmnGrpCdMst(@RequestBody CmnGrpCdMst cmnGrpCdMst) {


        CmnGrpCdMst result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.findCmnGrpCdMst(cmnGrpCdMst);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 요금 정보 조회
     * @param orgnId, sprtTp, plcySctnCd, prdtSctnCd, plcyTypeCd
     * @return
     */
    @RequestMapping(value = "/msp/rateByOrgnInfos", method = RequestMethod.POST)
    public List<MspRateMstDto> rateByOrgnInfos(@RequestBody MspSalePlcyMstDto mspSalePlcyMstDto) {


        List<MspRateMstDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.listRateByOrgnInfos(mspSalePlcyMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 정책 요금 정보 조회
     * @param MspRateMstDto mspRateMstDto
     * @return
     */
    @RequestMapping(value = "/msp/mspRateMspBySalePlcyCd", method = RequestMethod.POST)
    public List<MspRateMstDto> mspRateMspBySalePlcyCd(@RequestBody MspRateMstDto mspRateMstDto) {


        List<MspRateMstDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.listMspRateMspBySalePlcyCd(mspRateMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 판매정책정보를 조회
     * @param salePlcyCd
     * @return
     */
    @RequestMapping(value = "/msp/mspSalePlcyInfo", method = RequestMethod.POST)
    public MspSalePlcyMstDto mspSalePlcyInfo(@RequestBody String salePlcyCd) {


        MspSalePlcyMstDto result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.findMspSalePlcyInfo(salePlcyCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 정책에 해당하는 상품의 상세정보를 가져온다.
     * @param salePlcyCd
     * @return
     */
    @RequestMapping(value = "/msp/mspSalePrdtMstBySalePlcyCd", method = RequestMethod.POST)
    public List<MspSalePrdtMstDto> mspSalePrdtMstBySalePlcyCd(@RequestBody String salePlcyCd) {


        List<MspSalePrdtMstDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.listMspSalePrdtMstBySalePlcyCd(salePlcyCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 요금제 목록을 가져온다.
     * @param
     * @return
     */
    @RequestMapping(value = "/msp/mspOfficialSupportRateNm", method = RequestMethod.POST)
    public List<MspNoticSupportMstDto> mspOfficialSupportRateNm() {


        List<MspNoticSupportMstDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.listMspOfficialSupportRateNm();

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 공시지원금 목록을 가져온다.
     * @param MspNoticSupportMstDto mspNoticSupportMstDto, skipResult, maxResult
     * @return
     */
    @RequestMapping(value = "/msp/mspOfficialNoticeSupport", method = RequestMethod.POST)
    public List<MspNoticSupportMstDto> mspOfficialNoticeSupport(@RequestBody MspNoticSupportMstDto mspNoticSupportMstDto) {

        // skipResult, maxResult 값은 dto 에 포함
        int skipResult = mspNoticSupportMstDto.getApiParam1();
        int maxResult = mspNoticSupportMstDto.getApiParam2();
        List<MspNoticSupportMstDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.listMspOfficialNoticeSupport(mspNoticSupportMstDto, new RowBounds(skipResult, maxResult));

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 공시지원금 목록 카운트를 가져온다.
     * @param MspNoticSupportMstDto mspNoticSupportMstDto, skipResult, maxResult
     * @return
     */
    @RequestMapping(value = "/msp/mspOfficialNoticeSupportCount", method = RequestMethod.POST)
    public int mspOfficialNoticeSupportCount(@RequestBody MspNoticSupportMstDto mspNoticSupportMstDto) {


        int result = 0;

        try {
            // Database 에서 조회함.
            result = mspMapper.listMspOfficialNoticeSupportCount(mspNoticSupportMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 계약번호의 주민번호.
     * @param MspNoticSupportMstDto mspNoticSupportMstDto, skipResult, maxResult
     * @return
     */
    @RequestMapping(value = "/msp/customerSsn", method = RequestMethod.POST)
    public String customerSsn(@RequestBody String contractNum) {


        String result = "";

        try {
            // Database 에서 조회함.
            result = mspMapper.getCustomerSsn(contractNum);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 유심정보 개수 조회
     * @param searchUsimNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/sellUsimMgmtCount", method = RequestMethod.POST)
    public int sellUsimMgmtCount(@RequestBody String searchUsimNo) {


        int result = 0;

        try {
            // Database 에서 조회함.
            result = mspMapper.sellUsimMgmtCount(searchUsimNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 유심 제공처 조직 조회
     * @param searchUsimNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/sellUsimMgmtOrgnId", method = RequestMethod.POST)
    public String sellUsimMgmtOrgnId(@RequestBody String searchUsimNo) {


        String resultStr = "";

        try {
            // Database 에서 조회함.
            resultStr = mspMapper.sellUsimMgmtOrgnId(searchUsimNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }


        return resultStr;
    }

    /**
     * 요금제 정보 리스트 조회
     * @param rateCd
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/mspRateMst", method = RequestMethod.POST)
    public MspRateMstDto mspRateMst(@RequestBody String rateCd) {


        MspRateMstDto result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.getMspRateMst(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 전화번호(개통, 일시정지) 개수 조회
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/juoSubIngoCount", method = RequestMethod.POST)
    public int juoSubIngoCount(@RequestBody String subscriberNo) {


        //기본값 설정 처리
        if (StringUtils.isBlank(subscriberNo)) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        int result = 0;

        try {
            // Database 에서 조회함.
            result = mspMapper.juoSubIngoCount(subscriberNo);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 할인금액을 조회
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/mspSaleMst", method = RequestMethod.POST)
    public List<MspSaleSubsdMstDto> mspSaleMst(@RequestBody MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        ObjectMapper om = new ObjectMapper();

        List<MspSaleSubsdMstDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.listMspSaleMst(mspSaleSubsdMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 상품의 판매금액관련 상세내역을 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/mspSaleSubsdMst", method = RequestMethod.POST)
    public MspSaleSubsdMstDto mspSaleSubsdMst(@RequestBody MspSaleSubsdMstDto mspSaleSubsdMstDto) {


        MspSaleSubsdMstDto result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.findMspSaleSubsdMst(mspSaleSubsdMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 최저가를 구하기 위한 해당 상품의 요금제 정보 1건 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/lowPriceChargeInfoByProd", method = RequestMethod.POST)
    public MspSaleSubsdMstDto lowPriceChargeInfoByProd(@RequestBody MspSaleSubsdMstDto mspSaleSubsdMstDto) {


        ObjectMapper om = new ObjectMapper();

        MspSaleSubsdMstDto result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.getLowPriceChargeInfoByProd(mspSaleSubsdMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 최저가를 구하기 위한 해당 상품의 요금제 정보 리스트를 조회 한다.
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/mspSaleSubsdMstList", method = RequestMethod.POST)
    public List<MspSaleSubsdMstDto> mspSaleSubsdMstList(@RequestBody MspSaleSubsdMstDto mspSaleSubsdMstDto) {


        List<MspSaleSubsdMstDto> result = null;

        try {
            // Database 에서 조회함.
            if ("Y".equals(mspSaleSubsdMstDto.getForFrontFastYn())) {
                result = mspMapper.selectMspSaleSubsdMstListForLowPrice(mspSaleSubsdMstDto);
            } else {
                result = mspMapper.selectMspSaleSubsdMstListWithRateInfo(mspSaleSubsdMstDto);
            }
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 정책정보를 조회
     * @param subscriberNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/salePlcyMstList", method = RequestMethod.POST)
    public List<MspSaleSubsdMstDto> salePlcyMstList(@RequestBody MspSaleSubsdMstDto mspSaleSubsdMstDto) {
        List<MspSaleSubsdMstDto> result = null;
        try {
            result = mspMapper.salePlcyMstList(mspSaleSubsdMstDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 프로모션 금액 조회
     * @param subscriberNo
     * @return
     */
    @RequestMapping(value = "/msp/getromotionDcAmt", method = RequestMethod.POST)
    public Integer getromotionDcAmt(@RequestBody MspSaleSubsdMstDto mspSaleSubsdMstDto) {

        Integer result = 0;

        try {
            // Database 에서 조회함.
            result = mspMapper.getromotionDcAmt(mspSaleSubsdMstDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }



    /**
     * 스캔 이미지 보정 처리
     * MSP_REQUEST_DTL@DL_MSP  T1.SCAN_ID = '999999' 업데이트 처리
     * 배치에서 호출
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/updateScanIdForDtl", method = RequestMethod.POST)
    public int updateScanIdForDtl() {

        logger.info("api:{},updateScanIdForDtl:{}","/msp/updateScanIdForDtl");

        int result = 0;

        try {
            // Database 에서 조회함. sellUsimMgmtCount
            result = mspMapper.updateScanIdForDtl();

        } catch(Exception e) {
            e.printStackTrace();
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 스캔 이미지 보정 처리
     * MSP_JUO_SUB_INFO@DL_MSP  T1.SCAN_ID = '999999' 업데이트 처리
     * 배치에서 호출
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/updateScanIdForSubInfo", method = RequestMethod.POST)
    public int updateScanIdForSubInfo() {

        logger.info("api:{},updateScanIdForSubInfo:{}","/msp/updateScanIdForSubInfo");

        int result = 0;

        try {
            // Database 에서 조회함. sellUsimMgmtCount
            result = mspMapper.updateScanIdForSubInfo();

        } catch(Exception e) {
            e.printStackTrace();
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 결합 가능 요금제코드 및 부가서비스 코드
     * MSP_COMB_RATE_MAPP@DL_MSP
     * 결합하기
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/msp/mspCombRateMapp", method = RequestMethod.POST)
    public MyCombinationResDto mspCombRateMapp(@RequestBody String pRateCd) {

        logger.info("mspCombRateMapp: pRateCd=>"+pRateCd);



        MyCombinationResDto myCombinationResDto = new MyCombinationResDto();

        try {
            // Database 에서 조회함. sellUsimMgmtCount
            myCombinationResDto = mspMapper.selectMspCombRateMapp(pRateCd);

        } catch(Exception e) {
            e.printStackTrace();
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return myCombinationResDto;
    }

    /**
     * 쿠폰 조회
     * @param arrTest
     * @return
     */
    @RequestMapping(value = "/msp/selectAllCoupons")
    public List<CouponOutsideDto> selectAllCoupons(@RequestBody List<String> arrTest) {

        List<CouponOutsideDto> couponOutsideDtos = new ArrayList<>();
        try {
            couponOutsideDtos = this.mspMapper.selectAllCoupons(arrTest);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
         return couponOutsideDtos;
    }

    @RequestMapping(value = "/msp/selectMspMemeberCheck")
    public int selectMspMemeberCheck(@RequestBody String adminId) {

        String reValue = "N";
        int selectCnt = 0;
        try {
            selectCnt = this.mspMapper.selectMspMemeberCheck(adminId);
//            ObjectUtil1s.isEmpty(selectDto) ? "N" : "Y";
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return selectCnt;
    }


    /**
     * KtTriple 할인 금액 조회
     * @param rateCd : 요금제 코드
     * @return
     */
    @RequestMapping(value = "/msp/getKtTripleDcAmt", method = RequestMethod.POST)
    public Integer getKtTripleDcAmt(@RequestBody String rateCd) {

        Integer result = 0;

        try {
            // Database 에서 조회함.
            result = mspMapper.getKtTripleDcAmt(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    @RequestMapping(value = "/mcp/mcpJuoSubInfoCnt", method = RequestMethod.POST)
    public int mcpJuoSubInfoCnt(@RequestBody String customerId) {
        int result = 0;
        try {
            result = this.mspMapper.getMspJuoSubInfoCount(customerId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 평생할인 판매정책에 따른 요금제 리스트
     * @param prmtId : 판매정책ID
     * @return
     */
    @RequestMapping(value = "/msp/mspDisAddList", method = RequestMethod.POST)
    public List<Map<String, String>> mspDisAddList(@RequestBody String prmtId) {
        List<Map<String, String>> disAddList = null;
        int result = 0;
        try {
            disAddList = this.mspMapper.mspDisAddList(prmtId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return disAddList;
    }

    /**
     * API 연동 request INSERT
     */

    @RequestMapping(value = "/msp/insertApiTrace", method = RequestMethod.POST)
    public String insertApiTrace(@RequestBody ApiTraceDto apiTraceDto) {

        String trSeq= "";
        try {
            mspMapper.insertApiTrace(apiTraceDto);
            trSeq = String.valueOf(apiTraceDto.getTrSeq());
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return trSeq;
    }
    /**
     * API 연동 request update
     */

    @RequestMapping(value = "/msp/updateApiTrace", method = RequestMethod.POST)
    public void updateApiTrace(@RequestBody ApiTraceDto apiTraceDto) {

        try {
            if(apiTraceDto.getTrSeq() == null || "".equals(apiTraceDto.getTrSeq())) {
                throw new Exception();
            }

            apiTraceDto.setResStr( StringUtil.subStrBytes(apiTraceDto.getResStr(), 4000) );

            mspMapper.updateApiTrace(apiTraceDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

    }

    /**
     * 계약 현행화 상태 조회
     */
    @RequestMapping(value = "/msp/getMspJuoSubStatus", method = RequestMethod.POST)
    public String getMspJuoSubStatus(@RequestBody String contractNum) {
        String subStatus = "";
        try {
            subStatus = mspMapper.getMspJuoSubStatus(contractNum);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return subStatus;
    }






    /**
     * 결합 인터넷 아이디 현황 조회
     * @param  map internetId : 결합 인터넷 아이디  , openDt : 설치일  기준
     *
     */
    @RequestMapping(value = "/msp/getInetInfo", method = RequestMethod.POST)
    public String getInetInfo(@RequestBody HashMap<String, String> map) {

        String status = "";
        try {
            // Database 에서 조회함.
            status = mspMapper.getInetInfo(map);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return status;
    }

    /*
     * 전체 요금제 상품(프리퀀시)
     */
    @RequestMapping(value = "/msp/selectAllMspRateList", method = RequestMethod.POST)
    public List<MspRateMstDto> selectAllMspRateList() {

        List<MspRateMstDto> selectAllMspRateList = null;

        try {
            selectAllMspRateList = mspMapper.selectAllMspRateList();

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return selectAllMspRateList;
    }

    /*
     * 프리퀀시 정책 참여 대상 요금제
     */
    @RequestMapping(value = "/msp/selectFqcPlcyPlaList", method = RequestMethod.POST)
    public List<FqcDto> selectFqcPlcyPlaList(@RequestBody String fqcPlcyCd) {
        List<FqcDto> fqcPlcyPlaList = null;
        try {
            fqcPlcyPlaList = mspMapper.selectFqcPlcyPlaList(fqcPlcyCd);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return fqcPlcyPlaList;
    }

    /*
     * 프리퀀시 참여 카운트
     */
    @RequestMapping(value = "/msp/selectFqcBasListCnt", method = RequestMethod.POST)
    public int selectFqcBasListCnt(@RequestBody FqcDto fqcDto) {
        int result = 0;

        try {
            // Database 에서 조회함.
            result = mspMapper.selectFqcBasListCnt(fqcDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /*
     * 프리퀀시 참여 리스트
     */
    @RequestMapping(value = "/msp/selectFqcBasList", method = RequestMethod.POST)
    public List<FqcDto>selectFqcBasList(@RequestBody FqcDto fqcDto){
        int skipResult = fqcDto.getSkipResult();
        int maxResult = fqcDto.getMaxResult();
        List<FqcDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.selectFqcBasList(fqcDto, new RowBounds(skipResult, maxResult));

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /*
     * 프리퀀시 참여 엑셀다운로드 리스트
     */
    @RequestMapping(value = "/msp/selectFqcBasExcelList", method = RequestMethod.POST)
    public List<FqcDto>selectFqcBasExcelList(@RequestBody FqcDto fqcDto){

        List<FqcDto> result = null;

        try {
            // Database 에서 조회함.
            result = mspMapper.selectFqcBasExcelList(fqcDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** VOC 대리점 담당자 정보 조회 */
    @RequestMapping(value = "/msp/getAcenVocAgntListByOrgnId", method = RequestMethod.POST)
    public List<String> getAcenVocAgntListByOrgnId(@RequestBody String vocAgntCd){

      List<String> result = null;

      try{
        result = mspMapper.getAcenVocAgntListByOrgnId(vocAgntCd);
      }catch(Exception e) {
        throw new McpCommonJsonException(COMMON_EXCEPTION);
      }

      return result;
    }

    /** 코드 정보 조회 (리스트) */
    @RequestMapping(value = "/msp/getCmnGrpCdList", method = RequestMethod.POST)
    public List<CmnGrpCdMst> getCmnGrpCdList(@RequestBody CmnGrpCdMst cmnGrpCdMst) {

      List<CmnGrpCdMst> result = null;

      try{
        result = mspMapper.getCmnGrpCdList(cmnGrpCdMst);
      }catch(Exception e) {
        throw new McpCommonJsonException(COMMON_EXCEPTION);
      }

      return result;
    }

    /** 월별 유지고객 정보 저장 */
    @RequestMapping(value = "/msp/insertNmcpUseRateInfo", method = RequestMethod.POST)
    public int insertNmcpUseRateInfo(@RequestBody Map<String, String> paramMap) {

        int result = 0;

        try {
            result = mspMapper.insertNmcpUseRateInfo(paramMap);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    @RequestMapping("/msp/getSyncCombineSoloListWithMspInfo")
    public List<MspCombineDto> getSyncCombineSoloListWithMspInfo(@RequestParam("baseDate") int baseDate) {
        List<MspCombineDto> syncCombineList;

        try {
            syncCombineList = mspMapper.getSyncCombineSoloListWithMspInfo(baseDate);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return syncCombineList;
    }
}
