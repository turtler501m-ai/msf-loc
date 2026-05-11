package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestStateVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;

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

    //insert ~ select
    void insertMsfRequest(Long requestKey);

    void insertMsfRequestCstmr(Long requestKey);

    void insertMsfRequestAgent(Long requestKey);

    void insertMsfRequestSale(Long requestKey);

    void insertMsfRequestBillReq(Long requestKey);

    void insertMsfRequestMove(Long requestKey);

    void insertMsfRequestDvcChg(Long requestKey);

    void insertMsfRequestAddition(Long requestKey);

    void insertMsfRequestState(MsfRequestStateVo msfRequestStateVo);

    //void updateMsfRequestTemp(NewChangeInfoRequest request);
    //void updateMsfRequestAgentTemp(NewChangeInfoRequest request);
    //void updateMsfRequestCstmrTemp(NewChangeInfoRequest request);
    //void updateMsfRequestSaleTemp(NewChangeInfoRequest request);
    //void updateMsfRequestBillReqTemp(NewChangeInfoRequest request);
    //void updateMsfRequestMoveTemp(NewChangeInfoRequest request);

}
