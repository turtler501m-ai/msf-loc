//package com.ktmmobile.msf.common.dao;
//
//import com.ktmmobile.msf.common.dto.NiceLogDto;
//import com.ktmmobile.msf.common.dto.NiceTryLogDto;
//
//public interface NiceLogDao {
//    public void insert(NiceLogDto nicelogDto);
//
//
//    public long insertMcpNiceHist(NiceLogDto niceLogDto);
//
//
//    public boolean updateMcpNiceHist(NiceLogDto niceLogDto);
//
//
//    public NiceLogDto getMcpNiceHist(NiceLogDto niceLogDto);
//
//    public NiceLogDto getMcpNiceHistWithSeq(long niceHistSeq);
//
//    public NiceLogDto getMcpNiceHistWithReqSeq(NiceLogDto niceLogDto);
//
//    public NiceLogDto getMcpNiceHistWithReqSeq2(NiceLogDto niceLogDto);
//
//    public int dupChkSelfSmsAuth(NiceLogDto niceLogDto);
//    public long insertSelfSmsAuth(NiceLogDto niceLogDto);
//
//    // ============= 본인인증 시도 이력(네이버, 패스, 토스) =============
//    public long insertMcpNiceTryHist(NiceTryLogDto niceTryLogDto);
//
//    public NiceTryLogDto getMcpNiceTryHist(NiceTryLogDto niceTryLogDto);
//
//    public boolean updateMcpNiceTryHist(NiceTryLogDto niceTryLogDto);
//
//    public NiceLogDto getMcpNiceHistWithTime(NiceLogDto niceLogDto);
//}
