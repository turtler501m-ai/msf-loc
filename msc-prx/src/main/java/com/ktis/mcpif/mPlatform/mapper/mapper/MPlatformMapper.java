package com.ktis.mcpif.mPlatform.mapper.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.mcpif.mPlatform.vo.MPlatformReqVO;
import com.ktis.mcpif.mPlatform.vo.MPlatformResVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 개통간소화 서비스를 위한 Mapper
 */
//@Mapper("MPlatformMapper")
public interface MPlatformMapper {
	//TIMEOUT 조회
//	public int getNstepConnectTimeout();
	
	//기본정보조회
//	public MPlatformResVO getRequestInfo(MPlatformReqVO vo);
	
	//사전체크
//	public MPlatformResVO getXmlMessagePC0(String resNo);
	
	//번호조회
//	public MPlatformResVO getXmlMessageNU1(MPlatformResVO vo);
	
	//번호예약
//	public MPlatformResVO getXmlMessageNU2(MPlatformResVO vo);
	
	//개통
//	public MPlatformResVO getXmlMessageOP0(String resNo);
	
	//상태조회
//	public MPlatformResVO getXmlMessageST1(String resNo);
	
	//해지
//	public MPlatformResVO getXmlMessageEP0(String resNo);
	
	//M2M해지
//	public MPlatformResVO getXmlMessageMP0(String resNo);
	
	//번호이동납부주장
//	public MPlatformResVO getXmlMessageNP1(MPlatformReqVO vo);
	
	//번호이동납부주장
//	public MPlatformResVO getXmlMessageNP2(String resNo);
	
	//상품조회
//	public List<MPlatformResVO> getXmlMessageOP0Prod(String resNo);
	
	//연동결과
//	public void insertRequestOsst(MPlatformReqVO vo);
	
	//부가서비스 처리여부
//	public void updateRequestAddition(MPlatformResVO vo);
	
	// 2018-11-16, selfcare 연동로그 생성
//	public void insertSelfCareLog(Map<String, String> param);
}
