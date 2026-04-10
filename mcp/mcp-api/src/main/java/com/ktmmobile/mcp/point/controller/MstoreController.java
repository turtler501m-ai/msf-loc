package com.ktmmobile.mcp.point.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.point.dto.MstoreCanTrgDto;
import com.ktmmobile.mcp.point.dto.MstoreDto;
import com.ktmmobile.mcp.point.mapper.MstoreMapper;
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
public class MstoreController {

    private static final Logger logger = LoggerFactory.getLogger(MstoreController.class);

    @Autowired
    MstoreMapper mstoreMapper;

    /**
     * Mstore SSO 연동 파라미터 조회 (해지가 아닌 회선 전부 조회)
     * @param mstoreDto
     * @return List<MstoreDto>
     */
    @RequestMapping(value = "/mstore/getMspCusInfo", method = RequestMethod.POST)
    public List<MstoreDto> getMspCusInfo(@RequestBody MstoreDto mstoreDto) {

        List<MstoreDto> cusInfoList = null;

        try {
            cusInfoList = mstoreMapper.getMspCusInfo(mstoreDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return cusInfoList;
    }

    /**
     * MCP 신청정보 조회 (CUSTOMER_ID에 엮인 신청정보 전부 조회)
     * @param customerId
     * @return List<MstoreDto>
     */
    @RequestMapping(value = "/mstore/getMcpRequestInfo", method = RequestMethod.POST)
    public List<MstoreDto> getMcpRequestInfo(@RequestBody String customerId) {

        List<MstoreDto> mcpRequestInfo = null;

        try {
            mcpRequestInfo = mstoreMapper.getMcpRequestInfo(customerId);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mcpRequestInfo;
    }

    /**
     * 탈퇴연동을 위한 CI값 조회 (20231205 이전 M스토어 사용자)
     * @param customerId
     * @return String
     */
    @RequestMapping(value = "/mstore/getMstoreTmpEmpen", method = RequestMethod.POST)
    public String getMstoreTmpEmpen(@RequestBody String customerId) {

        String tmpEmpen= null;

        try {
            // 1. 개통정보의 CI값 조회
            tmpEmpen = mstoreMapper.getMspCstmrCI(customerId);

            // 2. 신청정보의 CI값 조회
            if(tmpEmpen == null || "".equals(tmpEmpen)){
                List<MstoreDto> mcpRequestInfo=  mstoreMapper.getMcpRequestInfo(customerId);
                if(mcpRequestInfo != null && !mcpRequestInfo.isEmpty()){
                    tmpEmpen= mcpRequestInfo.get(0).getEmpen();
                }
            }

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return tmpEmpen;
    }

    /**
     * [AS-IS] MSTORE 탈퇴 연동 대상 조회 (최대 50건)
     * @return List<MstoreCanTrgDto>
     */
    @RequestMapping(value = "/mstore/selectMstoreCanList", method = RequestMethod.POST)
    public List<MstoreCanTrgDto> selectMstoreCanList() {

        List<MstoreCanTrgDto> mstoreCanTrgList = null;

        try {
            mstoreCanTrgList = mstoreMapper.selectMstoreCanTrgList();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mstoreCanTrgList;
    }


    /**
     * [TO-BE] MSTORE 탈퇴 연동 대상 조회 (최대 50건)
     * @return List<MstoreCanTrgDto>
     */
    @RequestMapping(value = "/mstore/selectMstoreCanListNew", method = RequestMethod.POST)
    public List<MstoreCanTrgDto> selectMstoreCanListNew() {

        List<MstoreCanTrgDto> mstoreCanTrgList = null;

        try {
            mstoreCanTrgList = mstoreMapper.selectMstoreCanListNew();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mstoreCanTrgList;
    }

    /**
     * [TO-BE] MSTORE 탈퇴 연동 대상 조회 (최대 50건) - 재시도
     * @return List<MstoreCanTrgDto>
     */
    @RequestMapping(value = "/mstore/selectMstoreCanListRty", method = RequestMethod.POST)
    public List<MstoreCanTrgDto> selectMstoreCanListRty() {

        List<MstoreCanTrgDto> mstoreCanTrgList = null;

        try {
            mstoreCanTrgList = mstoreMapper.selectMstoreCanListRty();
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return mstoreCanTrgList;
    }


}
