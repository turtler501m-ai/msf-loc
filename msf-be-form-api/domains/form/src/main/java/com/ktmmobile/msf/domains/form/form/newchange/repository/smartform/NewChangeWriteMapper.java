package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestOsstDto;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDocVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestRecVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestStateVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

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

    //@AutoAuditing(value = false)
    void insertMsfAdditionInfoListTemp(List<MsfRequestAdditionVo> additionDtoList);

    void deleteMsfAdditionTemp(long requestKey);

    void insertMsfRequestDocListTemp(List<MsfRequestDocVo> msfRequestDocVoList);

    void deleteMsfRequestDocTemp(long requestKey);

    void insertMsfRequestRecListTemp(List<MsfRequestRecVo> msfRequestRecVoList);

    void deleteMsfRequestRecTemp(long requestKey);


    //UPDATE

    void updateMsfRequestTempClose(NewChangeRequest newChangeRequest);

    void updateMsfRequestTemp(MsfRequestVo msfRequestVo);

    void updateMsfRequestAgentTemp(MsfRequestAgentVo msfRequestAgentVo);

    void updateMsfRequestCstmrTemp(MsfRequestCstmrVo msfRequestCstmrVo);

    void updateMsfRequestSaleTemp(MsfRequestSaleVo msfRequestSaleVo);

    void updateMsfRequestBillReqTemp(MsfRequestBillReqVo msfRequestBillReqVo);

    void updateMsfRequestMoveTemp(MsfRequestMoveVo msfRequestMoveVo);

    void updateMsfRequestDvcChgTemp(MsfRequestDvcChgVo msfRequestDvcChgVo);

    //작성완료 시점에 MSF_REQUEST 상태변경
    void updateMsfRequestInfo(NewChangeRequest request);

    //기기변경 개통전 사전체크 후 MSF_REQUEST_TEMP 테이블의 계약번호 업데이트
    void updateMsfRequest(NewChangeRequest request);

    //임시저장 진입 시 RES_NO 신규발급받아 저장
    void updateMsfRequestResNo(NewChangeRequest request);

    //평생할인 프로모션 ID 및 기본료 임시 테이블 업데이트 (완료 단계용)
    void updateMsfRequestPrmtIdTemp(@Param("requestKey") Long requestKey, @Param("disPrmtId") String disPrmtId);
    void updateMsfRequestSalePrmtAmtTemp(@Param("requestKey") Long requestKey, @Param("disPrmtAmt") Long disPrmtAmt);


    //insert ~ select
    void insertMsfRequest(Long requestKey);

    void insertMsfRequestCstmr(Long requestKey);

    void insertMsfRequestAgent(Long requestKey);

    void insertMsfRequestSale(Long requestKey);

    void insertMsfRequestBillReq(Long requestKey);

    void insertMsfRequestMove(Long requestKey);

    void insertMsfRequestDvcChg(Long requestKey);

    void insertMsfRequestAddition(Long requestKey);

    void insertMsfRequestDoc(Long requestKey);

    void insertMsfRequestRec(Long requestKey);

    void insertMsfRequestState(MsfRequestStateVo msfRequestStateVo);


    //copy : insert ~ select
    void insertCopyMsfRequest(NewChangeRequest request);

    void insertCopyMsfRequestCstmr(NewChangeRequest request);

    void insertCopyMsfRequestAgent(NewChangeRequest request);

    void insertCopyMsfRequestSale(NewChangeRequest request);

    void insertCopyMsfRequestBillReq(NewChangeRequest request);

    void insertCopyMsfRequestMove(NewChangeRequest request);

    void insertCopyMsfRequestDvcChg(NewChangeRequest request);

    void insertCopyMsfRequestAddition(NewChangeRequest request);

    //void insertCopyMsfRequestState(MsfRequestStateVo msfRequestStateVo);
    //void updateMsfRequestTemp(NewChangeInfoRequest request);
    //void updateMsfRequestAgentTemp(NewChangeInfoRequest request);
    //void updateMsfRequestCstmrTemp(NewChangeInfoRequest request);
    //void updateMsfRequestSaleTemp(NewChangeInfoRequest request);
    //void updateMsfRequestBillReqTemp(NewChangeInfoRequest request);
    //void updateMsfRequestMoveTemp(NewChangeInfoRequest request);

    //신규변경 MCP 저장 시 오류발생하면 전체 삭제
    void deleteMsfRequest(Long requestKey);

    void deleteMsfRequestCstmr(Long requestKey);

    void deleteMsfRequestAgent(Long requestKey);

    void deleteMsfRequestSale(Long requestKey);

    void deleteMsfRequestBillReq(Long requestKey);

    void deleteMsfRequestMove(Long requestKey);

    void deleteMsfRequestDvcChg(Long requestKey);

    void deleteMsfRequestAddition(Long requestKey);

    void deleteMsfRequestDoc(Long requestKey);

    void deleteMsfRequestRec(Long requestKey);


    //MSF_UPLOAD_PHONE_INFO - eSIM 에서 넘어온 입력값 저장
    @AutoAuditing(value = false)
    int insertMsfUploadPhoneInfo(MsfUploadPhoneInfoVo msfUploadPhoneInfoVo);

    //MSF_UPLOAD_PHONE_INFO - eSIM 에서 넘어온 입력값 저장
    @AutoAuditing(value = false)
    int updateMsfUploadPhoneInfo(MsfUploadPhoneInfoVo msfUploadPhoneInfoVo);

    //MSF_REQUEST_OSST 저장
    boolean insertMsfRequestOsst(MsfRequestOsstDto request);
}
