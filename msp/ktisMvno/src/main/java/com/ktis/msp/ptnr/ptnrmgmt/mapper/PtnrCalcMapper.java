package com.ktis.msp.ptnr.ptnrmgmt.mapper;

import java.util.List;

import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrCalcVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ptnrCalcMapper")
public interface PtnrCalcMapper {

    List<?> getPtnrCalcJejuList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcJejuListSum(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcGiftiList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcGiftiListSum(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcTmoneyList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcTmoneyListSum(PtnrCalcVO searchVO);

    List<?> getPtnrCalcGooglePlayList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcGooglePlayListSum(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcGooglePlayListEx(PtnrCalcVO searchVO);

    List<?> getPtnrCalcLpointList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcLpointListSum(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcLpointSttlList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcLpointListEx(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcMrAsstList(PtnrCalcVO searchVO);
    
    List<?> getPtnrCalcMrAsstListByExcel(PtnrCalcVO searchVO);
    
}
