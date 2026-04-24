
package com.ktis.msp.batch.job.dis.dismgmt.mapper;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper
public interface DisCusMapper {
	
	int updateApdProcT(DisVO disVo);
	
	int insertCusHst(DisVO disVo);
	
	int updateRequestDtlPrmtID(DisVO disVo);
	
	int updateApdProcY(DisVO disVo);
	
	int updateFnlInfProcY(DisVO disVom);
}