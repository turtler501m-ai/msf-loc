/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import java.util.List;

import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyNameChgReqDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface MyNameChgService {
    /**
    * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.(명의변경)
    */
    public List<McpUserCntrMngDto> selectCntrListNmChg(String userid, String contractNum);

    /**
    * @Description : 명의변경 신청
    */
    public String myNameChgRequest(MyNameChgReqDto myNameChgReqDto);

    /**
    * @Description : 양도인 신청가능여부 체크
    */
    public String grantorReqChk(MyNameChgReqDto myNameChgReqDto);

    public String nameChgChkTelNo(MyNameChgReqDto myNameChgReqDto);
}
