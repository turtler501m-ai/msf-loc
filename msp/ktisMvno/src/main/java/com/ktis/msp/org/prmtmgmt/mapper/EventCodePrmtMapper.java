package com.ktis.msp.org.prmtmgmt.mapper;

import com.ktis.msp.org.prmtmgmt.vo.EventCodePrmtVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("EventCodePrmtMapper")
public interface EventCodePrmtMapper {

    List<EgovMap> getEventCodePrmtList(EventCodePrmtVO searchVO);

}
