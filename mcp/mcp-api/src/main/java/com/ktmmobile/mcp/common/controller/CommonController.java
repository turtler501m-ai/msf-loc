package com.ktmmobile.mcp.common.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.dto.MspCommDatPrvTxnDto;
import com.ktmmobile.mcp.common.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.SearchDto;
import com.ktmmobile.mcp.common.mapper.CommonMapper;

@RestController
public class CommonController {

    @Autowired
    CommonMapper commonMapper;

    /**
     * 요금제 정보 조회
     * @param rateCd
     * @return
     */
    @RequestMapping(value = "/common/mspRateMst", method = RequestMethod.POST)
    public MspRateMstDto mspRateMst(String rateCd) {

        MspRateMstDto mspRateMst = null;

        try {
            mspRateMst = commonMapper.selectMspRateMst(rateCd);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspRateMst;
    }

    /**
     * SMS/톡 발송 탬플릿 정보 조회
     * @param rateCd
     * @return
     */
    @RequestMapping(value = "/common/mspSmsTemplateMst", method = RequestMethod.POST)
    public MspSmsTemplateMstDto mspSmsTemplateMst(@RequestBody int templateId) {

        MspSmsTemplateMstDto mspSmsTemplateMst = null;

        try {
            mspSmsTemplateMst = commonMapper.selectMspSmsTemplateMst(templateId);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mspSmsTemplateMst;
    }

    /**
     * 통신정보 조회 신청
     * @param mspCommDatPrvTxnDto
     * @return
     */
    @RequestMapping(value = "/common/mspCommDatPrvTxn", method = RequestMethod.POST)
    public int mspCommDatPrvTxn(MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {

        int insertCount = 0;

        try {
            insertCount = commonMapper.insertMspCommDatPrvTxn(mspCommDatPrvTxnDto);

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return insertCount;
    }


    /**
     * 휴대폰 정보 검색
     * @param String
     * @return
     */
    @RequestMapping(value = "/common/phoneIndexingBatch", method = {RequestMethod.POST,RequestMethod.GET})
	public List<SearchDto> phoneIndexingBatch(String dummy) {

    	List<SearchDto> phoneList = null;
		try {
			// Database 에서 조회함.
			phoneList = commonMapper.selectPhoneIndexing();

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		return phoneList;
	}
}
