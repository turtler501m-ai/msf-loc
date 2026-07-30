package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.McpUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeNUInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeNUInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.PreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;

@Mapper
public interface FormCommReadMapper {

    String generateResNo();

    long generateRequestKey();

    long getCustRequestSeq();

    List<AgentInfoResponse> selectAgentInfo(AgentInfoRequest request);

    //개통전 사전체크 시 특별판매번호 조회
    String selectSlsNo(PreCheckRequest request);

    //MCP_REQUEST 및 MCP_REQUEST_OSST 에서 개통전 사전체크 성공여부에 사용.
    int selectOsstCount(McpRequestOsstRequest request);

    //MCP_REQUEST 에서 RES_NO 추출 (MCP_REQUSET_OSST 테이블 조인함)
    String selectMvnoOrdNo(McpRequestOsstRequest request);

    //MCP_REQEUST_OSST 테이블에서 리턴받은 OSST_ORD_NO 추출
    String selectOsstOrdNo(McpRequestOsstRequest request);

    //신규가입 번호조회를 위한 데이타 조회 (NU1)
    NewChangeNUInfoResponse selectXmlMessageNU1(String resNo);

    //신규가입 번호예약를 위한 데이타 조회 (NU2)
    NewChangeNUInfoResponse selectXmlMessageNU2(NewChangeNUInfoRequest request);

    //1년이내 사용회선
    int selectActYearCnt(SubscriptionRequest request);

    //1년이내 해지건수
    int selectCancelYearCnt(SubscriptionRequest request);

    //당월개통 회선
    int selectActThisMonthCnt(SubscriptionRequest request);

    //미납조회 건수
    int selectUnpaidCnt(SubscriptionRequest request);

    //전체 개통 회선
    int selectActTotalCnt(SubscriptionRequest request);

    //개통이력조회
    int selectOpenHistory(SubscriptionRequest request);

    //개통전 사전체크 성공여부 확인
    MpPreCheckResponse selectMpPreCheckResult(MpPreCheckRequest request);

    //eSIM 등록여부 확인 MCP_UPLOAD_PHONE_INFO
    McpUploadPhoneInfoVo selectMcpUploadPhoneInfo(MsfUploadPhoneInfoVo request);

    //기존 계약정보 조회
    Map<String, String> selectContractObj(HashMap<String, String> paramMap);
}
