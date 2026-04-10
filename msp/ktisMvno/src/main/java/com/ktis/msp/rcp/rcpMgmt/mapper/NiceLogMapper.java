package com.ktis.msp.rcp.rcpMgmt.mapper;

import com.ktis.msp.rcp.rcpMgmt.vo.NiceLogVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface NiceLogMapper {

    public long insertMcpNiceHist(NiceLogVO niceLogVO);

}
