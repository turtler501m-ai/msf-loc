package com.ktis.msp.batch.job.cmn.usrobjhst.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("userObjHstMapper")
public interface UserObjHstMapper {

	int selectUserObject();
	
	int insertUserObjectHst();
}
