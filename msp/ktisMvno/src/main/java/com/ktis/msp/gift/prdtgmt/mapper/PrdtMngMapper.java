package com.ktis.msp.gift.prdtgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.gift.prdtgmt.vo.PrdtMngVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("prdtMngMapper")
public interface PrdtMngMapper {

    
    List<?> prdtMngList(PrdtMngVO searchVO);

    List<?> prdtMngHist(PrdtMngVO searchVO);
    
    void prdtInsert(PrdtMngVO searchVO);
    
    int prdtUpdate(PrdtMngVO searchVO);
    
    void prdtHistInsert(PrdtMngVO searchVO);

    List<?> prdtMngListEx(PrdtMngVO searchVO);

	List<?> getImageFile(PrdtMngVO searchVO);
	
}
