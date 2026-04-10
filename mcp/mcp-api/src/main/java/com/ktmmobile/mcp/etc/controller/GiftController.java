package com.ktmmobile.mcp.etc.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.etc.dto.ConsentGiftDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.etc.dto.GiftPromotionDto;
import com.ktmmobile.mcp.etc.mapper.GiftMapper;

@RestController
public class GiftController {

    private static Logger logger = LoggerFactory.getLogger(GiftController.class);

    @Autowired
    private GiftMapper giftMapper;


    /**
     * 사은품 대상 조회
     * @param GiftPromotionBas
     * @return
     */
    @RequestMapping(value = "/appForm/getPrmtBasList", method = RequestMethod.POST)
    public List<GiftPromotionBas> getPrmtBasList(@RequestBody GiftPromotionBas giftPromotionBas){


        List<GiftPromotionBas> result = null;
        try {
            // Database 에서 조회함.
            result = giftMapper.getPrmtBasList(giftPromotionBas);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return result;
    }


    /**
     * 사은품 프로모션 아이디 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getPrmtId", method = RequestMethod.POST)
    public List<GiftPromotionDto> getPrmtId(@RequestBody GiftPromotionDto giftPromotionDto){


        List<GiftPromotionDto> result = null;
        try {
            // Database 에서 조회함.
            result = giftMapper.getPrmtId(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 그룹 사은품 정보 조회
     * @param String[]
     * @return
     */
    @RequestMapping(value = "/appForm/getGiftArrList", method = RequestMethod.POST)
    public List<GiftPromotionDto> getGiftArrList(@RequestBody String[] prmtIdArr){


        List<GiftPromotionDto> result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getGiftArrList(prmtIdArr);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 그룹 사은품 정보 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getPrmtChk", method = RequestMethod.POST)
    public GiftPromotionDto getPrmtChk(@RequestBody GiftPromotionDto giftPromotionDto){


        GiftPromotionDto result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getPrmtChk(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 그룹 사은품 정보 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getGiftList", method = RequestMethod.POST)
    public List<GiftPromotionDto> getGiftList(@RequestBody GiftPromotionDto giftPromotionDto){


        List<GiftPromotionDto> result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getGiftList(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 회선 정보 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getMspJuoSubInfoData", method = RequestMethod.POST)
    public GiftPromotionDto getMspJuoSubInfoData(@RequestBody GiftPromotionDto giftPromotionDto){


        GiftPromotionDto result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getMspJuoSubInfoData(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 서비스계약 사은품 신청 정보 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getChkMspGiftPrmt", method = RequestMethod.POST)
    public List<GiftPromotionDto> getChkMspGiftPrmt(@RequestBody GiftPromotionDto giftPromotionDto){


        List<GiftPromotionDto> result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getChkMspGiftPrmt(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 발송 일자 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getMspGiftPrmtCustomer", method = RequestMethod.POST)
    public GiftPromotionDto getMspGiftPrmtCustomer(@RequestBody GiftPromotionDto giftPromotionDto){


        GiftPromotionDto result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getMspGiftPrmtCustomer(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 신청 정보 변경
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/updateMspGiftPrmtCustomer", method = RequestMethod.POST)
    public boolean updateMspGiftPrmtCustomer(@RequestBody GiftPromotionDto giftPromotionDto){


        boolean result;

        try {
            // Database 에서 조회함.
            result = 0 < giftMapper.updateMspGiftPrmtCustomer(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 신청 정보 등록
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/insertMspGiftPrmtResult", method = RequestMethod.POST)
    public int insertMspGiftPrmtResult(@RequestBody GiftPromotionDto giftPromotionDto){


        int result;
        try {
            // Database 에서 조회함.
            result = giftMapper.insertMspGiftPrmtResult(giftPromotionDto);
            if( result > 0 ) {
                try {
                    giftMapper.insertMspGiftPrmtResultHist(giftPromotionDto);
                }catch(Exception e) {
                    logger.debug("insertMspGiftPrmtResultHist error");
                }
            }
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * 사은품 신청 정보 조회
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/getSaveGiftList", method = RequestMethod.POST)
    public List<GiftPromotionDto> getSaveGiftList(@RequestBody GiftPromotionDto giftPromotionDto){


        List<GiftPromotionDto> result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getSaveGiftList(giftPromotionDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 제세공과금 주소 노출 여부 및 발송 시간 체크
     * @param ConsentGiftDto
     * @return
     */
    @RequestMapping(value = "/appForm/addYnChk", method = RequestMethod.POST)
    public ConsentGiftDto addYnChk(@RequestBody ConsentGiftDto consentGiftDto){


        ConsentGiftDto result;

        try {
            // Database 에서 조회함.
            result = giftMapper.addYnChk(consentGiftDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 제세공과금 개인별 조회
     * @param ConsentGiftDto
     * @return
     */
    @RequestMapping(value = "/appForm/answerYnChk", method = RequestMethod.POST)
    public ConsentGiftDto answerYnChk(@RequestBody ConsentGiftDto consentGiftDto){


        ConsentGiftDto result;

        try {
            // Database 에서 조회함.
            result = giftMapper.answerYnChk(consentGiftDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 회선 정보 조회(제세공과금)
     * @param ConsentGiftDto
     * @return
     */
    @RequestMapping(value = "/appForm/getConMspJuoSubInfoData", method = RequestMethod.POST)
    public ConsentGiftDto getConMspJuoSubInfoData(@RequestBody ConsentGiftDto consentGiftDto){


        ConsentGiftDto result;

        try {
            // Database 에서 조회함.
            result = giftMapper.getConMspJuoSubInfoData(consentGiftDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /**
     * 회신일자 및 (주소가 있는 경우 주소 포함)업데이트.
     * @param GiftPromotionDto
     * @return
     */
    @RequestMapping(value = "/appForm/updateReplyDate", method = RequestMethod.POST)
    public boolean updateReplyDate(@RequestBody ConsentGiftDto consentGiftDto){


        boolean result;

        try {
            // Database 에서 조회함.
            result = 0 < giftMapper.updateReplyDate(consentGiftDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

}
