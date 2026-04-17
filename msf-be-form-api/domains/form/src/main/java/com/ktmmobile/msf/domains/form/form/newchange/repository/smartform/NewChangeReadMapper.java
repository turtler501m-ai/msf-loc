package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewChangeReadMapper {

    String generateSmartResNo();

    long generateSmartRequestKey();

    long getSmartCustRequestSeq();


    //MsfRequestDto selectMsfRequestInfo(Integer requestKey);

    //MsfRequestAgentVo selectMsfRequestAgentInfo(Integer requestKey);

    //MsfRequestCstmrDto selectMsfRequestCstmrInfo(Integer requestKey);

    //MsfRequestSaleDto selectMsfRequestSaleInfo(Integer requestKey);

    //MsfRequestBillReqDto selectMsfRequestBillReqInfo(Integer requestKey);


}
