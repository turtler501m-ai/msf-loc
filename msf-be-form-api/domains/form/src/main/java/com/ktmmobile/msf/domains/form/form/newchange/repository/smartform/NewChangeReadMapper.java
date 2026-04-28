package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoCondition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewChangeReadMapper {

    String generateSmartResNo();

    long generateSmartRequestKey();

    long getSmartCustRequestSeq();

    //NewChangeInfoDto selectNewChangeInfo(NewChangeInfoCondition request);

    MsfRequestVo selectMsfRequestInfo(NewChangeInfoCondition condition);

    MsfRequestCstmrVo selectMsfRequestCstmrInfo(NewChangeInfoCondition condition);

    MsfRequestAgentVo selectMsfRequestAgentInfo(NewChangeInfoCondition condition);

    MsfRequestSaleVo selectMsfRequestSaleInfo(NewChangeInfoCondition condition);

    MsfRequestBillReqVo selectMsfRequestBillReqInfo(NewChangeInfoCondition condition);


}
