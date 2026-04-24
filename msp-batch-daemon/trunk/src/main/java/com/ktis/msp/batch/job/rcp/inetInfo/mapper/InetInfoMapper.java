package com.ktis.msp.batch.job.rcp.inetInfo.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("inetInfoMapper")
public interface InetInfoMapper {
	
	// 트리플할인 실적 대상 조회
	List<Map<String,Object>> selectInetInfoList();	
	// 트리플할인 실적 MST 등록 및 업데이트
	int updateInetInfoMst(String inetInfoSeq);
	// 트리플할인 실적 HIST 에 등록	
	int InsertInetInfoHist(String inetInfoSeq);
	// 트리플할인 실적 처리결과 업데이트
	int updateInetInfo(String inetInfoSeq);
}
