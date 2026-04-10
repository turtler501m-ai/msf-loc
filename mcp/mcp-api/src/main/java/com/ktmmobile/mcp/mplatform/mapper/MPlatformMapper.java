package com.ktmmobile.mcp.mplatform.mapper;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.mplatform.dto.MPlatformErrVO;
import com.ktmmobile.mcp.mplatform.dto.MPlatformNstepVO;
import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.mplatform.dto.MPlatformReqVO;
import com.ktmmobile.mcp.mplatform.dto.MPlatformResVO;

/**
 * 개통간소화 서비스를 위한 Mapper
 */
@Mapper
public interface MPlatformMapper {
	//TIMEOUT 조회
	public int getNstepConnectTimeout();

	//기본정보조회
	public MPlatformResVO getRequestInfo(MPlatformReqVO vo);
	
	//유심셀프변경 UC0
	public MPlatformResVO getXmlMessageUC0(MPlatformReqVO vo);
	
	//사전체크
	public MPlatformResVO getXmlMessagePC0(String resNo);

	//번호조회
	public MPlatformResVO getXmlMessageNU1(MPlatformResVO vo);

	//번호예약
	public MPlatformResVO getXmlMessageNU2(MPlatformResVO vo);

	//개통
	public MPlatformResVO getXmlMessageOP0(String resNo);

	//상태조회
	public MPlatformResVO getXmlMessageST1(String resNo);

	//해지
	public MPlatformResVO getXmlMessageEP0(String resNo);

	//M2M해지
	public MPlatformResVO getXmlMessageMP0(String resNo);

	//번호이동납부주장
	public MPlatformResVO getXmlMessageNP1(MPlatformReqVO vo);

	//번호이동납부주장
	public MPlatformResVO getXmlMessageNP2(String resNo);

	/*사전동의 결과조회*/
	public MPlatformResVO getXmlMessageNP3(MPlatformReqVO vo);

	//상품조회
	public List<MPlatformResVO> getXmlMessageOP0Prod(String resNo);

	//온라인 서식지 등록
	public MPlatformNstepVO getXmlMessageFS5(String resNo);

	//연동결과
	public void insertRequestOsst(MPlatformReqVO vo);

	//부가서비스 처리여부
	public void updateRequestAddition(MPlatformResVO vo);

	// 2018-11-16, selfcare 연동로그 생성
	public void insertSelfCareLog(Map<String, String> param);

    int selectCheckMpCallCount(Map<String, String> param);

    public String getMplatformCryptionYn(String eventCd);

	// OSST exception 이력 저장
	void insertOsstErrLog(MPlatformErrVO mPlatformErrVO);

    //명의변경사전체크
    public MPlatformResVO getXmlMessageMC0(MPlatformReqVO vo);

    //명의변경사전체크
    public MPlatformResVO getXmlMessageMC0Nm(MPlatformReqVO vo);

    //명의변경처리
    public MPlatformResVO getXmlMessageMcnMP0(MPlatformReqVO vo);
}
