package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    @AutoAuditing(value = false)
    void insertAdditionInfoListTemp(List<MsfRequestAdditionVo> additionDtoList);

    void deleteMsfAdditionTemp(long requestKey);

    //UPDATE
    void updateMsfRequestTemp(MsfRequestVo msfRequestVo);

    void updateMsfRequestAgentTemp(MsfRequestAgentVo msfRequestAgentVo);

    void updateMsfRequestCstmrTemp(MsfRequestCstmrVo msfRequestCstmrVo);

    void updateMsfRequestSaleTemp(MsfRequestSaleVo msfRequestSaleVo);

    void updateMsfRequestBillReqTemp(MsfRequestBillReqVo msfRequestBillReqVo);

    void updateMsfRequestMoveTemp(MsfRequestMoveVo msfRequestMoveVo);

    void updateMsfRequestDvcChgTemp(MsfRequestDvcChgVo msfRequestDvcChgVo);

    //UPDATE RES_NO
    void updateMsfRequestInfo(NewChangeInfoRequest request);

    //void updateMsfRequestTemp(NewChangeInfoRequest request);
    //void updateMsfRequestAgentTemp(NewChangeInfoRequest request);
    //void updateMsfRequestCstmrTemp(NewChangeInfoRequest request);
    //void updateMsfRequestSaleTemp(NewChangeInfoRequest request);
    //void updateMsfRequestBillReqTemp(NewChangeInfoRequest request);
    //void updateMsfRequestMoveTemp(NewChangeInfoRequest request);

}
