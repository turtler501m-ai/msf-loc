package com.ktmmobile.mcp.common.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.NiceTryLogDto;

public interface NiceLogSvc {
    public void insert(HttpServletRequest request , NiceResDto niceDto , NiceLogDto nicelogDto );
    public void insert(HttpServletRequest request, HashMap map);
    public void insert(HttpServletRequest request, HashMap map , String sReserved1);


    public long insertMcpNiceHist(NiceLogDto niceLogDto);

    public long saveMcpNiceHist(NiceLogDto niceLogDto);

    public boolean updateMcpNiceHist(NiceLogDto niceLogDto);


    public NiceLogDto getMcpNiceHist(NiceLogDto niceLogDto);

    /* nice 본인인증 로그 조회 with seq */
    public NiceLogDto getMcpNiceHistWithSeq(long niceHistSeq);

    public NiceLogDto showMcpNiceHist(NiceLogDto niceLogDto);

    //셀프개통 신규 SMS 본인인증 정보
    public long insertSelfSmsAuth(NiceLogDto niceLogDto);

    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록
    public long insertMcpNiceTryHist(NiceTryLogDto niceTryLogDto);

    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 조회 by seq (pk)
    public NiceTryLogDto getMcpNiceTryHist(NiceTryLogDto niceTryLogDto);

    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록 update
    public boolean updateMcpNiceTryHist(NiceTryLogDto niceTryLogDto);


    public NiceLogDto getMcpNiceHistWithReqSeq(NiceLogDto niceLogDto);

    // 본인인증 이력 조회
    public NiceLogDto getMcpNiceHistWithTime(NiceLogDto niceLogDto);

}
