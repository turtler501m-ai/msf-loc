package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

@Mapper
public interface NewChangeReadMapper {

    String generateSmartResNo();

    long generateSmartRequestKey();

    long getSmartCustRequestSeq();

    long generateSmartRequestStateSeq();

    //NewChangeInfoDto selectNewChangeInfo(NewChangeInfoCondition request);

    MsfRequestVo selectMsfRequestInfo(NewChangeRequest request);

    MsfRequestCstmrVo selectMsfRequestCstmrInfo(NewChangeRequest request);

    MsfRequestAgentVo selectMsfRequestAgentInfo(NewChangeRequest request);

    MsfRequestSaleVo selectMsfRequestSaleInfo(NewChangeRequest request);

    MsfRequestBillReqVo selectMsfRequestBillReqInfo(NewChangeRequest request);

    MsfRequestMoveVo selectMsfRequestMoveInfo(NewChangeRequest request);

    MsfRequestDvcChgVo selectMsfRequestDvcChgInfo(NewChangeRequest request);

    List<MsfRequestAdditionVo> selectMsfRequestAdditionInfo(NewChangeRequest request);

    String getMsfResNo(long requestKey);

    Integer checkNewChangeFormUser(NewChangeRequest request);
}
