package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;

@Mapper
public interface FormCommReadMapper {

    String generateResNo();

    long generateRequestKey();

    long getCustRequestSeq();

    List<AgentInfoResponse> selectAgentInfo(AgentInfoRequest request);

    int selectOsstCount(McpRequestOsstRequest request);

    String selectOsstOrdNo(McpRequestOsstRequest request);

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


}
