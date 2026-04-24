package com.ktis.msp.batch.job.cmn.payinfo.mapper;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.batch.job.cmn.payinfo.vo.PayInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PayInfo")
public interface PayInfoMapper {
	/*변경동의서 엑셀다운로드 조회*/
	List<PayInfoVO> selectPayInfoListExcel(PayInfoVO searchVO);
	void selectPayInfoListExcel(PayInfoVO searchVO, ResultHandler resultHandler);
	
	/*KT연동동의서 엑셀다운로드 조회*/
	List<PayInfoVO> selectPayInfoKtListExcel(PayInfoDtlVO searchVO);
	void selectPayInfoKtListExcel(PayInfoDtlVO searchVO, ResultHandler resultHandler);
}
