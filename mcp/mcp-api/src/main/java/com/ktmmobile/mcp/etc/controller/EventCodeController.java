package com.ktmmobile.mcp.etc.controller;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.etc.dto.EventCodePrmtDto;
import com.ktmmobile.mcp.etc.dto.EventCodeRateDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionBas;
import com.ktmmobile.mcp.etc.dto.RateEventCodePrmtDto;
import com.ktmmobile.mcp.etc.mapper.EventCodeMapper;
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
public class EventCodeController {

    private static Logger logger = LoggerFactory.getLogger(EventCodeController.class);

    @Autowired
    private EventCodeMapper eventCodeMapper;


    /** @Description : 요금제 리스트 조회 */
    @RequestMapping(value = "/event/selectEventCodeRateList", method = RequestMethod.POST)
    public List<EventCodeRateDto> selectEventCodeRateList(@RequestBody EventCodeRateDto eventCodeRateDto){

        List<EventCodeRateDto> result = null;

        try {

            result = eventCodeMapper.selectEventCodeRateList(eventCodeRateDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 이벤트코드 상세 요금제 리스트 조회 */
    @RequestMapping(value = "/event/selectEventCodeRateListByEcpSeq", method = RequestMethod.POST)
    public List<EventCodeRateDto> selectEventCodeRateListByEcpSeq(@RequestBody int ecpSeq){

        List<EventCodeRateDto> result = null;

        try {

            result = eventCodeMapper.selectEventCodeRateListByEcpSeq(ecpSeq);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 이벤트코드 상세 프로모션 리스트 조회 */
    @RequestMapping(value = "/event/selectEventCodePrmtListByEcpSeq", method = RequestMethod.POST)
    public List<EventCodePrmtDto> selectEventCodePrmtListByEcpSeq(@RequestBody int ecpSeq){

        List<EventCodePrmtDto> result = null;

        try {

            result = eventCodeMapper.selectEventCodePrmtListByEcpSeq(ecpSeq);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 요금제코드 프로모션 XML 리스트 조회 */
    @RequestMapping(value = "/event/selectRateEventCodePrmtMstXmlList", method = RequestMethod.POST)
    public List<RateEventCodePrmtDto> selectRateEventCodePrmtMstXmlList(){

        List<RateEventCodePrmtDto> result = null;

        try {
            result = eventCodeMapper.selectRateEventCodePrmtMstXmlList();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 요금제코드 프로모션 XML 리스트 조회 */
    @RequestMapping(value = "/event/selectRateEventCodePrmtXmlList", method = RequestMethod.POST)
    public List<RateEventCodePrmtDto> selectRateEventCodePrmtXmlList(){

        List<RateEventCodePrmtDto> result = null;

        try {
            result = eventCodeMapper.selectRateEventCodePrmtXmlList();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }

    /** @Description : 이벤트코드 상세 프로모션 리스트 조회 */
    @RequestMapping(value = "/event/selectEventPrmtPrdtList", method = RequestMethod.POST)
    public List<EventCodePrmtDto> selectEventPrmtPrdtList(@RequestBody int ecpSeq){

        List<EventCodePrmtDto> result = null;

        try {
            result = eventCodeMapper.selectEventPrmtPrdtList(ecpSeq);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }
}
