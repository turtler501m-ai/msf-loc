package com.ktmmobile.mcp.etc.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.etc.dto.*;
import com.ktmmobile.mcp.etc.mapper.RateGiftPrmtMapper;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@RestController
public class RateGiftPrmtController {

    private static Logger logger = LoggerFactory.getLogger(RateGiftPrmtController.class);

    @Autowired
    private RateGiftPrmtMapper rateGiftPrmtMapper;

    /** @Description : 요금제 혜택요약 총 개수 조회 */
    @RequestMapping(value = "/rate/selectRateGiftPrmtCount", method = RequestMethod.POST)
    public int selectRateGiftPrmtCount(@RequestBody RateGiftPrmtDto searchDto){

        int result = 0;

        try {
            result = rateGiftPrmtMapper.selectRateGiftPrmtCount(searchDto);
        } catch(Exception e) {
            logger.debug("selectRateGiftPrmtCount Exception={}", e.getMessage());
        }

        return result;
    }

    /** @Description : 요금제 혜택요약 리스트 조회 */
    @RequestMapping(value = "/rate/selectRateGiftPrmtList", method = RequestMethod.POST)
    public List<RateGiftPrmtDto> selectRateGiftPrmtList(@RequestBody RateGiftPrmtDto searchDto){

        List<RateGiftPrmtDto> result = null;

        try {
            int skipResult = searchDto.getSkipResult();
            int maxResult = searchDto.getMaxResult();
            result = rateGiftPrmtMapper.selectRateGiftPrmtList(searchDto, new RowBounds(skipResult, maxResult));

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 총 개수 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtCount", method = RequestMethod.POST)
    public int selectGiftPrmtCount(@RequestBody RateGiftPrmtDtlDto searchDto){

        int result = 0;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtCount(searchDto);
        } catch(Exception e) {
            logger.debug("selectGiftPrmtCount Exception={}", e.getMessage());
        }

        return result;
    }

    /** @Description : 사은품 프로모션 리스트 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtList", method = RequestMethod.POST)
    public List<RateGiftPrmtDtlDto> selectGiftPrmtList(@RequestBody RateGiftPrmtDtlDto searchDto){

        List<RateGiftPrmtDtlDto> result = null;

        try {
            int skipResult = searchDto.getSkipResult();
            int maxResult = searchDto.getMaxResult();
            result = rateGiftPrmtMapper.selectGiftPrmtList(searchDto, new RowBounds(skipResult, maxResult));

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 마스터 테이블 정보 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtMstInfo", method = RequestMethod.POST)
    public RateGiftPrmtDtlDto selectGiftPrmtMstInfo(@RequestBody String prmtId){

        RateGiftPrmtDtlDto result = null;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtMstInfo(prmtId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 대상조직 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtOrgnList", method = RequestMethod.POST)
    public List<RateGiftPrmtSubDto> selectGiftPrmtOrgnList(@RequestBody String prmtId){

        List<RateGiftPrmtSubDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtOrgnList(prmtId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 대상요금제 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtRateList", method = RequestMethod.POST)
    public List<RateGiftPrmtSubDto> selectGiftPrmtRateList(@RequestBody String prmtId){

        List<RateGiftPrmtSubDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtRateList(prmtId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 모집경로 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtOnoffList", method = RequestMethod.POST)
    public List<RateGiftPrmtSubDto> selectGiftPrmtOnoffList(@RequestBody String prmtId){

        List<RateGiftPrmtSubDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtOnoffList(prmtId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 대상제품 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtModelList", method = RequestMethod.POST)
    public List<RateGiftPrmtSubDto> selectGiftPrmtModelList(@RequestBody String prmtId){

        List<RateGiftPrmtSubDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtModelList(prmtId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 사은품 프로모션 사은품 조회 */
    @RequestMapping(value = "/rate/selectGiftPrmtPrdtList", method = RequestMethod.POST)
    public List<RateGiftPrmtSubDto> selectGiftPrmtPrdtList(@RequestBody String prmtId){

        List<RateGiftPrmtSubDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectGiftPrmtPrdtList(prmtId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 요금제 혜택요약 XML 리스트 조회 */
    @RequestMapping(value = "/rate/selectRateGiftPrmtXmlList", method = RequestMethod.POST)
    public List<RateGiftPrmtDto> selectRateGiftPrmtXmlList(){

        List<RateGiftPrmtDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectRateGiftPrmtXmlList();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 요금제부가서비스안내상품관계 XML 리스트 조회 */
    @RequestMapping(value = "/rate/selectRateAdsvcGdncProdRelXmlList", method = RequestMethod.POST)
    public List<RateAdsvcGdncProdRelDto> selectRateAdsvcGdncProdRelXmlList(){

        List<RateAdsvcGdncProdRelDto> result = null;

        try {
            result = rateGiftPrmtMapper.selectRateAdsvcGdncProdRelXmlList();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 요금제 혜택요약 총 금액 조회 */
    @RequestMapping(value = "/rate/selectRateGiftPrmtTotalPrice", method = RequestMethod.POST)
    public int selectRateGiftPrmtTotalPrice(@RequestBody String rateAdsvcCd){

        int result = 0;

        try {
            result = rateGiftPrmtMapper.selectRateGiftPrmtTotalPrice(rateAdsvcCd);
        } catch(Exception e) {
            logger.debug("selectRateGiftPrmtTotalPrice Exception={}", e.getMessage());
        }

        return result;
    }
}
