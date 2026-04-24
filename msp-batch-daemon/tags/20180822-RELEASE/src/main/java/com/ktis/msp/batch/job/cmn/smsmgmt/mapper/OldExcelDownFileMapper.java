package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("oldExcelDownFileMapper")
public interface OldExcelDownFileMapper {
	List<String> getOldExcelDownFileList();
}
