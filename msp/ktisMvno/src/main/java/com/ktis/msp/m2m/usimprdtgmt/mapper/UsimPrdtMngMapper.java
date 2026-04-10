package com.ktis.msp.m2m.usimprdtgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.m2m.usimprdtgmt.vo.UsimPrdtMngVO;


import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usimPrdtMngMapper")
public interface UsimPrdtMngMapper {

    
    List<?> usimPrdtMngList(UsimPrdtMngVO searchVO);

    List<?> usimPrdtMngHist(UsimPrdtMngVO searchVO);
    
    void usimPrdtInsert(UsimPrdtMngVO searchVO);
    
    int usimPrdtUpdate(UsimPrdtMngVO searchVO);
    
    void usimPrdtHistInsert(UsimPrdtMngVO searchVO);

    List<?> usimPrdtMngListEx(UsimPrdtMngVO searchVO);

	List<?> getImageFile(UsimPrdtMngVO searchVO);
	
}
