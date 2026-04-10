package com.ktmmobile.mcp.sms.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.cmmn.util.NmcpServiceUtils;
import com.ktmmobile.mcp.cmmn.util.StringUtil;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.sms.dto.ApiMapDto;
import com.ktmmobile.mcp.sms.mapper.SmsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@RestController
public class SmsController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    SmsMapper smsMapper;

    //@Value("${SERVER_NAME}")
    //private String serverName;

    /**
     * 서식지 등록 갯수 조회
     * @param map
     * @return
     */
    @RequestMapping(value = "/sms/mspApplyCount", method = RequestMethod.POST)
    public int mspApplyCount(ApiMapDto apiMapDto) {


        int mspApplyCount = 0;

        try {
            mspApplyCount = smsMapper.selectMspApplyCount(apiMapDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspApplyCount;
    }

    /**
     * SMS 발송 등록
     * @param map
     * @return
     */
    @RequestMapping(value = "/sms/addSms", method = {RequestMethod.POST,RequestMethod.GET})
    public int addSms(ApiMapDto apiMapDto) {
        if("LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME")) && (!"BATCH".equals(StringUtil.NVL(apiMapDto.getReqServer(), "")))) {
            try {
                apiMapDto.setI_SUBJECT(new String(apiMapDto.getI_SUBJECT().getBytes("ISO-8859-1"), "UTF-8"));
                apiMapDto.setI_MSG(new String(apiMapDto.getI_MSG().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }


        int insertCount = 0;

        try {
            insertCount = smsMapper.insertSms(apiMapDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }

    /**
     * 카카오톡 발송 등록
     * @param map
     * @return
     */
    @RequestMapping(value = "/sms/addKakaoNoti", method = {RequestMethod.POST,RequestMethod.GET})
    public int addKakaoNoti(ApiMapDto apiMapDto) {

        if("LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME"))) {
            try {
                apiMapDto.setI_SUBJECT(new String(apiMapDto.getI_SUBJECT().getBytes("ISO-8859-1"), "UTF-8"));
                apiMapDto.setI_MSG(new String(apiMapDto.getI_MSG().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }


        int insertCount = 0;

        try {
            insertCount = smsMapper.insertKakaoNoti(apiMapDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }

    @RequestMapping(value = "/sms/qStackCnt")
    public int qStackCnt(@RequestBody AuthSmsDto authSmsDto) {

        return this.smsMapper.selectQstackCount(authSmsDto);
    }

    /**
     * SMS 신규 모듈 발송 등록
     * @param map
     * @return
     */
    @RequestMapping(value = "/sms/addNewSms", method = {RequestMethod.POST,RequestMethod.GET})
    public int addNewSms(ApiMapDto apiMapDto) {

        if("LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME"))) {
            try {
                apiMapDto.setI_SUBJECT(new String(apiMapDto.getI_SUBJECT().getBytes("ISO-8859-1"), "UTF-8"));
                apiMapDto.setI_MSG(new String(apiMapDto.getI_MSG().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                logger.error("addNewSms error : {}", e1.getMessage());
            }
        }

        //전화번호 검증
        if(!StringUtil.checkMobile(apiMapDto.getI_CTN())){
            return 0;
        }

        int insertCount = 0;

        try {
            insertCount = smsMapper.insertNewSms(apiMapDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }

    /**
     * SMS 신규 모듈 발송 건수 체크(제한 확인)
     * @param map
     * @return
     */
    @RequestMapping(value = "/sms/qStackNewCnt")
    public int qStackNewCnt(@RequestBody AuthSmsDto authSmsDto) {

        return this.smsMapper.selectQstackNewCount(authSmsDto);
    }

    /*
     * 카카오 알림톡 신규 모듈 발송
     */
    @RequestMapping(value = "/sms/addNewKakaoNoti", method = {RequestMethod.POST,RequestMethod.GET})
    public int addNewKakaoNoti(ApiMapDto apiMapDto) {

        if("LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME"))) {
            try {
                apiMapDto.setI_SUBJECT(new String(apiMapDto.getI_SUBJECT().getBytes("ISO-8859-1"), "UTF-8"));
                apiMapDto.setI_MSG(new String(apiMapDto.getI_MSG().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                logger.error("addNewKakaoNoti error : {}", e1.getMessage());
            }
        }

        //전화번호 검증
        if(!StringUtil.checkMobile(apiMapDto.getI_CTN())){
            return 0;
        }

        int insertCount = 0;

        try {
            insertCount = smsMapper.insertNewKakaoNoti(apiMapDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }
}
