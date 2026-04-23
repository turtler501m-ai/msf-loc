package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.vo.*;
import org.apache.ibatis.annotations.Mapper;

@AutoAuditing
@Mapper
public interface NewChangeWriteMapper {

    void insertMsfRequestTemp(MsfRequestVo msfRequestVo);

    void insertMsfRequestAgentTemp(MsfRequestAgentVo msfRequestAgentVo);

    void insertMsfRequestCstmrTemp(MsfRequestCstmrVo msfRequestCstmrVo);

    void insertMsfRequestSaleTemp(MsfRequestSaleVo msfRequestSaleVo);

    void insertMsfRequestBillReqTemp(MsfRequestBillReqVo msfRequestBillReqVo);

    void updateMsfRequestTemp(NewChangeInfoRequest request);

    void updateMsfRequestAgentTemp(NewChangeInfoRequest request);

    void updateMsfRequestCstmrTemp(NewChangeInfoRequest request);

    void updateMsfRequestSaleTemp(NewChangeInfoRequest request);

    void updateMsfRequestBillReqTemp(NewChangeInfoRequest request);

}
