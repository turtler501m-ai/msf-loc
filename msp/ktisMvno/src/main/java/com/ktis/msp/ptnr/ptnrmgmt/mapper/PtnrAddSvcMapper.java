package com.ktis.msp.ptnr.ptnrmgmt.mapper;

import java.util.List;

import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ptnrAddSvcMapper")
public interface PtnrAddSvcMapper {

    
    List<?> ptnrAddSvcList(PtnrInfoVO searchVO);

    List<?> ptnrAddSvcHist(PtnrInfoVO searchVO);
    
    void ptnrAddSvcInsert(PtnrInfoVO searchVO);
    
    int ptnrAddSvcUpdate(PtnrInfoVO searchVO);
    
    void ptnrAddSvcHistInsert(PtnrInfoVO searchVO);

    List<?> getRateComboList(PtnrInfoVO searchVO);

    List<?> ptnrAddSvcInfo(PtnrInfoVO searchVO);
    
    List<?> ptnrAddSvcListEx(PtnrInfoVO searchVO);
    
    List<?> ptnrRateAddSvcComboList(PtnrInfoVO searchVO);
}
