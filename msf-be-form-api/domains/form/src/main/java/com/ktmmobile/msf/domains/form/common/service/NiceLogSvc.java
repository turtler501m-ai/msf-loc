package com.ktmmobile.msf.domains.form.common.service;

import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;
import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceTryLogDto;

public interface NiceLogSvc {
    void insert(HttpServletRequest request , NiceResDto niceDto , NiceLogDto nicelogDto );
    void insert(HttpServletRequest request, HashMap map);
    void insert(HttpServletRequest request, HashMap map , String sReserved1);


    long insertMcpNiceHist(NiceLogDto niceLogDto);

    long saveMcpNiceHist(NiceLogDto niceLogDto);

    boolean updateMcpNiceHist(NiceLogDto niceLogDto);


    NiceLogDto getMcpNiceHist(NiceLogDto niceLogDto);

    /* nice 본인인증 로그 조회 with seq */
    NiceLogDto getMcpNiceHistWithSeq(long niceHistSeq);

    NiceLogDto showMcpNiceHist(NiceLogDto niceLogDto);

    //셀프개통 신규 SMS 본인인증 정보
    long insertSelfSmsAuth(NiceLogDto niceLogDto);

    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록
    long insertMcpNiceTryHist(NiceTryLogDto niceTryLogDto);

    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 조회 by seq (pk)
    NiceTryLogDto getMcpNiceTryHist(NiceTryLogDto niceTryLogDto);

    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록 update
    boolean updateMcpNiceTryHist(NiceTryLogDto niceTryLogDto);


    NiceLogDto getMcpNiceHistWithReqSeq(NiceLogDto niceLogDto);

    // 본인인증 이력 조회
    NiceLogDto getMcpNiceHistWithTime(NiceLogDto niceLogDto);

}
