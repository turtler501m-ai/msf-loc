package com.ktis.msp.ptnr.ptnrmgmt.mapper;

import java.util.List;

import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ptnrPrmtMapper")
public interface PtnrPrmtMapper {

    
    List<?> ptnrPrmtList(PtnrInfoVO searchVO);

    void ptnrPrmtInsert(PtnrInfoVO searchVO);
    
    int ptnrPrmtUpdate(PtnrInfoVO searchVO);
        
    List<?> ptnrPrmtListEx(PtnrInfoVO searchVO);
    
}
