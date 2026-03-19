package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.NumberChangeReqDto;
import com.ktmmobile.msf.formSvcChg.dto.NumberReserveReqDto;
import com.ktmmobile.msf.formSvcChg.dto.NumberSearchReqDto;

import java.util.List;
import java.util.Map;

/**
 * 번호변경 서비스 인터페이스.
 * ASIS AppformSvcImpl.getPhoneNoList() / sendOsstService(NU2) / numChgeChg(X32) 와 동일 역할.
 */
public interface SvcChgNumberSvc {

    /**
     * NU1 희망번호 조회. 입력한 패턴에 맞는 가능한 번호 목록 반환.
     */
    List<String> searchNewNumber(NumberSearchReqDto req);

    /**
     * NU2 희망번호 예약.
     */
    Map<String, Object> reserveNumber(NumberReserveReqDto req);

    /**
     * NU2 희망번호 예약 취소.
     */
    Map<String, Object> cancelReservedNumber(NumberReserveReqDto req);

    /**
     * X32 번호변경 처리.
     */
    Map<String, Object> changeNumber(NumberChangeReqDto req);
}
