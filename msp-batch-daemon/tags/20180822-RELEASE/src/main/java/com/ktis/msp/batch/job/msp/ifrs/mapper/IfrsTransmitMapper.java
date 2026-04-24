package com.ktis.msp.batch.job.msp.ifrs.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ifrsTransmitMapper")
public interface IfrsTransmitMapper {
	
	// 계약정보현행화 1차
	List<String> getMspJuoSubInfoFirst();
	
	// 계약현행화이력정보
	List<String> getMspJuoSubInfoHist();
	
	// 상품현행화정보
	List<String> getMspJuoFeatureInfo();
	
	// 단말현행화정보
	List<String> getMspJuoModelInfo();
	
	// MVNO추가정보
	List<String> getMspJuoAddInfo();
	
	// 요금제
	List<String> getMspRateMst();
	
	// 요금제스펙
	List<String> getMspRateSpec();
	
	// 계약정보현행화 2차
	List<String> getMspJuoSubInfoSecond();
	
	// 수수료집계
	List<String> getCmsPrvdTrgtAgncyCttt();
	
	// 조직정보
	List<String> getOrgOrgnInfoMst();
	
	// 제품기기정보
	List<String> getLgsPrdtSrlMst();
	
	// 제품관리
	List<String> getCmnIntmMdl();
	
	// 청구수납자료
	List<String> getBillPymnData();
	
	// 단말기단가
	List<String> getCmnHndstAmt();
	
	// 공시지원금
	List<String> getOfclSubsdMst();
	
	// 신청정보
	List<String> getMspRequest();
	
	// 기변정보
	List<String> getMspDvcChgAgntHst();
	
}
