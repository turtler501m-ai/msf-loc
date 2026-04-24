package com.ktis.msp.batch.job.cms.cmsnMgmt.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.cms.cmsnMgmt.vo.CmsnMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("cmsnMgmtMapper")
public interface CmsnMgmtMapper {
	
	List<?> getGrdCmsnPrvdListExcel(CmsnMgmtVO searchVO);
	
	// 수수료지급상세 엑셀로 저장
	List<?> getCmsnPrvdListExcel(CmsnMgmtVO searchVO);
	
	// 수수료생성근거 엑셀로 저장
	List<?> getCalcCrebasisListExcel(CmsnMgmtVO searchVO);
	
}
