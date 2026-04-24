package com.ktis.msp.batch.job.rcp.selfhpc.mapper;

import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.rcp.selfhpc.vo.SelfHpcVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SelfHpcMgmtMapper")
public interface SelfHpcMgmtMapper {

	void getRcpSelfHpcListExcel(SelfHpcVO selfHpcVO, ResultHandler resultHandler);
}
