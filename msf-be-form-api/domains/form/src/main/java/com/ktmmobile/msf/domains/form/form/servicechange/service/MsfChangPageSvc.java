package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.List;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import jakarta.servlet.http.HttpServletRequest;

public interface MsfChangPageSvc {

    /** 휴대폰 회선에 따른 단말기할인 정보 조회 */
    MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);

    /** MCP 휴대폰 회선관리 리스트 조회 */
    List<McpUserCntrMngDto> selectCntrList(String userid);

    /** 휴대폰 회선정보 조회 (비로그인) */
    McpUserCntrMngDto selectCntrListNoLogin(String contractNum);

    McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto);

    /** 서비스변경 화면의 가입정보 초기 데이터 조회 */
    FormResponse<ChangInfoViewResDto> getChangInfoView(HttpServletRequest request, MyPageSearchDto searchVO);

}
