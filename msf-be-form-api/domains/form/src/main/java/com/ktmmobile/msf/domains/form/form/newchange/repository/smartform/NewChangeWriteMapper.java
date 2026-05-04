package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.*;
import org.apache.ibatis.annotations.Mapper;

@AutoAuditing
@Mapper
public interface NewChangeWriteMapper {

    //INSERT
    void insertMsfRequestTemp(MsfRequestVo msfRequestVo);

    void insertMsfRequestAgentTemp(MsfRequestAgentVo msfRequestAgentVo);

    void insertMsfRequestCstmrTemp(MsfRequestCstmrVo msfRequestCstmrVo);

    void insertMsfRequestSaleTemp(MsfRequestSaleVo msfRequestSaleVo);

    void insertMsfRequestBillReqTemp(MsfRequestBillReqVo msfRequestBillReqVo);

    void insertMsfRequestMoveTemp(MsfRequestMoveVo msfRequestMoveVo);

    void insertMsfRequestDvcChgTemp(MsfRequestDvcChgVo msfRequestMoveVo);

    //UPDATE
    void updateMsfRequestTemp(MsfRequestVo msfRequestVo);

    void updateMsfRequestAgentTemp(MsfRequestAgentVo msfRequestAgentVo);

    void updateMsfRequestCstmrTemp(MsfRequestCstmrVo msfRequestCstmrVo);

    void updateMsfRequestSaleTemp(MsfRequestSaleVo msfRequestSaleVo);

    void updateMsfRequestBillReqTemp(MsfRequestBillReqVo msfRequestBillReqVo);

    void updateMsfRequestMoveTemp(MsfRequestMoveVo msfRequestMoveVo);

    void updateMsfRequestDvcChgTemp(MsfRequestDvcChgVo msfRequestDvcChgVo);

    //void updateMsfRequestTemp(NewChangeInfoRequest request);
    //void updateMsfRequestAgentTemp(NewChangeInfoRequest request);
    //void updateMsfRequestCstmrTemp(NewChangeInfoRequest request);
    //void updateMsfRequestSaleTemp(NewChangeInfoRequest request);
    //void updateMsfRequestBillReqTemp(NewChangeInfoRequest request);
    //void updateMsfRequestMoveTemp(NewChangeInfoRequest request);

}
