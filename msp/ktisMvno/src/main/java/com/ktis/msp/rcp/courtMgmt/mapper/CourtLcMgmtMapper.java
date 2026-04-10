package com.ktis.msp.rcp.courtMgmt.mapper;

import com.ktis.msp.rcp.courtMgmt.vo.CourtLcMgmtVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper("CourtLcMgmtMapper")
public interface CourtLcMgmtMapper {

    List<?> getCourtLcMgmtList(Map<String, Object> param);

    List<?> getCourtLcMgmtListByExcel(Map<String, Object> param);

    List<?> getCourtLcMgmtDtlList(Map<String, Object> param);
    
    int checkLcMst(CourtLcMgmtVO courtLcMgmtVO);
    
    int checkLcSeq(CourtLcMgmtVO courtLcMgmtVO);

    String getLcMstSeqNvl();
    
    int insertLcMst(CourtLcMgmtVO courtLcMgmtVO);

    int insertLcDtl(CourtLcMgmtVO courtLcMgmtVo);

    void updateLcDtl(CourtLcMgmtVO courtLcMgmtVO);

    void deleteLcDtl(CourtLcMgmtVO courtLcMgmtVO);
    
    int cntLcYear();
    
    void issueLcMst(CourtLcMgmtVO courtLcMgmtVO);
    
    void discardLcMst(CourtLcMgmtVO courtLcMgmtVO);

}