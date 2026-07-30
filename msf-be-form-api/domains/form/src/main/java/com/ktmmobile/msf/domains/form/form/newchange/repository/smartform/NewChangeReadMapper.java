package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

@Mapper
public interface NewChangeReadMapper {

    //String generateSmartResNo();
    //long generateSmartRequestKey();
    //long getSmartCustRequestSeq();
    //long generateSmartRequestStateSeq();
    //NewChangeInfoDto selectNewChangeInfo(NewChangeInfoCondition request);

    //MSF_REQUEST 테이블조회
    MsfRequestVo selectMsfRequestInfo(NewChangeRequest request);

    //MSF_REQUEST_CSTMR 테이블조회
    MsfRequestCstmrVo selectMsfRequestCstmrInfo(NewChangeRequest request);

    //MSF_REQUEST_AGENT 테이블조회
    MsfRequestAgentVo selectMsfRequestAgentInfo(NewChangeRequest request);

    //MSF_REQUEST_SALE 테이블조회
    MsfRequestSaleVo selectMsfRequestSaleInfo(NewChangeRequest request);

    //MSF_REQUEST_BILL_REQ 테이블조회
    MsfRequestBillReqVo selectMsfRequestBillReqInfo(NewChangeRequest request);

    //MSF_REQUEST_MOVE 테이블조회
    MsfRequestMoveVo selectMsfRequestMoveInfo(NewChangeRequest request);

    //MSF_REQUEST_DVC_CHG 테이블조회
    MsfRequestDvcChgVo selectMsfRequestDvcChgInfo(NewChangeRequest request);

    //MSF_REQUEST_ADDITION 테이블조회
    List<MsfRequestAdditionVo> selectMsfRequestAdditionInfo(NewChangeRequest request);

    //MSF_UPLOAD_PHONE_INFO 테이블조회 (ESIM 등록정보)
    MsfUploadPhoneInfoVo selectMsfUploadPhoneInfo(long requestKey);

    //신청서 진입 시 로그인정보와 비교 (MSF_REQUEST_TEMP 기준)
    Integer checkNewChangeForm(NewChangeRequest request);

    //사용용도 정리 할 필요 있음
    Integer checkNewChangeFormUser(NewChangeRequest request);

    //MSF_REQUEST_TEMP.RES_NO 조회
    String getMsfResNo(long requestKey);

    //대량 법인 개통관련 정보추출 (작성완료, 개통완료)
    BulkCorporateInfoResponse selectCorporateOpenInfo(BulkCorporateInfoRequest request);

    //대량 법인 개통관련 가입가능건수 조회 (db 저장값)
    BulkCorporateInfoResponse selectCorporateLimitQnty(BulkCorporateInfoRequest request);

    //대리점 목록조회 시 대량 법인 개통 가능여부 조회
    List<AgentInfoResponse> selectBulkCorporateOpenYnList(
        @Param("shopOrgnId") String shopOrgnId,
        @Param("orgnIds") List<String> orgnIds
    );


    //사용하지 않는듯
    String selectNewChangeFormStep(NewChangeRequest request);

    //사용하지 않는듯
    List<String> getMsfResNoByMoveMobileNum(Map<String, Object> paramMap);

    //MSF_REQUEST_OSST.MVNO_ORD_NO 조회 (사용안하는듯)
    String getMsfMvnoOrdNo(String resNo);

    //사용하지 않는 것 같음. (사용안하는듯)
    int getMsfPreCheckTryCnt(Map<String, Object> paramMap);

    //예약번호 존재여부 확인 (사용안하는듯)
    boolean existsResNo(@Param("resNo") String resNo);

}
