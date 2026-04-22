package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.vo.*;
import org.apache.ibatis.annotations.Mapper;

@AutoAuditing
@Mapper
public interface NewChangeWriteMapper {

    void insertMsfRequest(MsfRequestVo msfRequestVo);

    void insertMsfRequestAgent(MsfRequestAgentVo msfRequestAgentVo);

    void insertMsfRequestCstmr(MsfRequestCstmrVo msfRequestCstmrVo);

    void insertMsfRequestSale(MsfRequestSaleVo msfRequestSaleVo);

    void insertMsfRequestBillReq(MsfRequestBillReqVo msfRequestBillReqVo);

    void updateMsfRequest(NewChangeInfoRequest request);

    void updateMsfRequestAgent(NewChangeInfoRequest request);

    void updateMsfRequestCstmr(NewChangeInfoRequest request);

    void updateMsfRequestSale(NewChangeInfoRequest request);

    void updateMsfRequestBillReq(NewChangeInfoRequest request);

}
