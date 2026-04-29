package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewChangeReadMapper {

    String generateSmartResNo();

    long generateSmartRequestKey();

    long getSmartCustRequestSeq();

    //NewChangeInfoDto selectNewChangeInfo(NewChangeInfoCondition request);

    MsfRequestVo selectMsfRequestInfo(NewChangeRequest condition);

    MsfRequestCstmrVo selectMsfRequestCstmrInfo(NewChangeRequest condition);

    MsfRequestAgentVo selectMsfRequestAgentInfo(NewChangeRequest condition);

    MsfRequestSaleVo selectMsfRequestSaleInfo(NewChangeRequest condition);

    MsfRequestBillReqVo selectMsfRequestBillReqInfo(NewChangeRequest condition);


}
