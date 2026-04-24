package com.ktis.msp.batch.job.fdh.iif.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("fdhTransmitMapper")
public interface FdhTransmitMapper {
	// 계약정보현행화 1차
	List<String> getFdhInf001();
	
	// 계약현행화이력
	List<String> getFdhInf002();
	
	// 상품현행화정보
	List<String> getFdhInf003();
	
	// 단말현행화정보
	List<String> getFdhInf004();
	
	// MVNO추가정보
	List<String> getFdhInf005();
	
	// 요금제
	List<String> getFdhInf006();
	
	// 요금제스펙
	List<String> getFdhInf007();
	
	// 조직
	List<String> getFdhInf008();
	
	// 제품관리
	List<String> getFdhInf009();
	
	// 모델단가
	List<String> getFdhInf010();
	
	// 계약정보현행화 2차
	List<String> getFdhInf011();
	
	// 조직정보이력
	List<String> getFdhInf012();
	
	// 판매정책정보
	List<String> getFdhInf013();
	
	// 계약해지정보
	List<String> getFdhInf014();
}
