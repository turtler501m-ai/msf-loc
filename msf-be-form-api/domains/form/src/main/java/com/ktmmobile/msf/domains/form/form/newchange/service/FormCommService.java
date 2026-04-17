package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.exception.NotFoundException;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;

@Service
@RequiredArgsConstructor
public class FormCommService {

    private final FormCommMapper formCommMapper;
    private final NewChangeReadMapper newChangeReadMapper;

    //SQ_RCP_RES_NO_01 생성 ( MSF_REQUEST.RES_NO )
    public String generateResNo() {
        //return formCommMapper.generateResNo();
        return newChangeReadMapper.generateSmartResNo(); //스마트에서 오픈전까지만 임시로 사용
    }

    //SQ_RCP_REQUEST_KEY_01 생성 ( MSF_REQUEST.REQUEST_KEY )
    public long generateRequestKey() {
        //return formCommMapper.generateRequestKey();
        return newChangeReadMapper.generateSmartRequestKey(); //스마트에서 오픈전까지만 임시로 사용
    }

    //NMCP_CUST_REQUEST_SEQ 생성
    public long getCustRequestSeq() {
        //return formCommMapper.getCustRequestSeq();
        return newChangeReadMapper.getSmartCustRequestSeq(); //스마트에서 오픈전까지만 임시로 사용
    }

    public AgentInfoDto getAgentInfo(String cntpntCd) {
        return Optional.ofNullable(formCommMapper.selectAgentInfo(cntpntCd))
            .orElseThrow(() -> new NotFoundException("조회하고자 하는 매장코드를 입력해주세요. cntpntCd:" + cntpntCd));
    }

}
