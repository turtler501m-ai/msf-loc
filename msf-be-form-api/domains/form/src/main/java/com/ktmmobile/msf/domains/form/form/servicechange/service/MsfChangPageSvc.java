package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

public interface MsfChangPageSvc {

    /** 휴대폰 회선에 따른 단말기할인 정보 조회 */
    MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);

    /** MCP 휴대폰 회선관리 리스트 조회 */
    List<McpUserCntrMngDto> selectCntrList(String userid);

    /** 휴대폰 회선정보 조회 (비로그인) */
    McpUserCntrMngDto selectCntrListNoLogin(String contractNum);

    McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto);

}

