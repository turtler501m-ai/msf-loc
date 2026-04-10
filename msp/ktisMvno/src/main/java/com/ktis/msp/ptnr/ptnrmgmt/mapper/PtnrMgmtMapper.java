package com.ktis.msp.ptnr.ptnrmgmt.mapper;

import java.util.List;

import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ptnrMgmtMapper")
public interface PtnrMgmtMapper {

    List<?> ptnrMgmtList(PtnrInfoVO searchVO);
    
    List<?> ptnrMgmtLinkList(PtnrInfoVO searchVO);

    void ptnrMgmtInsert(PtnrInfoVO searchVO);
    
    void ptnrMgmtUpdate(PtnrInfoVO searchVO);
    
    void ptnrMgmtLinkInsert(PtnrInfoVO searchVO);
    
    ///////////////////////////////////////////////
    
    List<?> ptnrRateList(PtnrInfoVO searchVO);

    List<?> ptnrRateHist(PtnrInfoVO searchVO);
    
    void ptnrRateInsert(PtnrInfoVO searchVO);
    
    int ptnrRateUpdate(PtnrInfoVO searchVO);
    
    void ptnrRateHistInsert(PtnrInfoVO searchVO);

    List<?> ptnrRateInfo(PtnrInfoVO searchVO);
    
    List<?> ptnrRateListEx(PtnrInfoVO searchVO);
}
