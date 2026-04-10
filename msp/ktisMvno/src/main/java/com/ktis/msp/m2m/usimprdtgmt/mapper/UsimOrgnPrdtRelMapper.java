package com.ktis.msp.m2m.usimprdtgmt.mapper;

import java.util.List;

import com.ktis.msp.m2m.usimprdtgmt.vo.UsimPrdtMngVO;


import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("usimOrgnPrdtRelMapper")
public interface UsimOrgnPrdtRelMapper {

    
    List<?> usimOrgnList(UsimPrdtMngVO searchVO);

    List<?> usimPrdtRelList(UsimPrdtMngVO searchVO);
    
    void usimPrdtRelInsert(UsimPrdtMngVO searchVO);
    
    int usimPrdtRelDelete(UsimPrdtMngVO searchVO);
    
}
