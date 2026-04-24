package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.Map;

@Mapper("partnerUsimRegMapper")
public interface PartnerUsimRegMapper {

	/** 유심-조직 매핑 정보 초기화 */
	void deleteUsimOrgMatch(String workDt);

	/** 대리점 유심-조직 매핑 */
	void insertAgentMatch(String workDt);

	/** 판매점 유심-조직 매핑 */
	void insertShopMatch(String workDt);
	
	/** 제휴유심 등록 프로시저 호출 */
	void callPartnerUsimData(Map<String, Object> param);
	
}
