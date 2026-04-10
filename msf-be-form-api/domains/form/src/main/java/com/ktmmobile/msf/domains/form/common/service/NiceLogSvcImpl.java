package com.ktmmobile.msf.domains.form.common.service;

import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceTryLogDto;

// [ASIS] NiceLogSvcImpl — 기존 구현체는 MCP 패키지 참조로 미완성. 기동용 스텁 구현.
@Service
public class NiceLogSvcImpl implements NiceLogSvc {

    @Override public void insert(HttpServletRequest request, NiceResDto niceDto, NiceLogDto nicelogDto) {}
    @Override public void insert(HttpServletRequest request, HashMap map) {}
    @Override public void insert(HttpServletRequest request, HashMap map, String sReserved1) {}
    @Override public long insertMcpNiceHist(NiceLogDto niceLogDto) { return 0L; }
    @Override public long saveMcpNiceHist(NiceLogDto niceLogDto) { return 0L; }
    @Override public boolean updateMcpNiceHist(NiceLogDto niceLogDto) { return false; }
    @Override public NiceLogDto getMcpNiceHist(NiceLogDto niceLogDto) { return new NiceLogDto(); }
    @Override public NiceLogDto getMcpNiceHistWithSeq(long niceHistSeq) { return new NiceLogDto(); }
    @Override public NiceLogDto showMcpNiceHist(NiceLogDto niceLogDto) { return new NiceLogDto(); }
    @Override public long insertSelfSmsAuth(NiceLogDto niceLogDto) { return 0L; }
    @Override public long insertMcpNiceTryHist(NiceTryLogDto niceTryLogDto) { return 0L; }
    @Override public NiceTryLogDto getMcpNiceTryHist(NiceTryLogDto niceTryLogDto) { return new NiceTryLogDto(); }
    @Override public boolean updateMcpNiceTryHist(NiceTryLogDto niceTryLogDto) { return false; }
    @Override public NiceLogDto getMcpNiceHistWithReqSeq(NiceLogDto niceLogDto) { return new NiceLogDto(); }
    @Override public NiceLogDto getMcpNiceHistWithTime(NiceLogDto niceLogDto) { return new NiceLogDto(); }
}
