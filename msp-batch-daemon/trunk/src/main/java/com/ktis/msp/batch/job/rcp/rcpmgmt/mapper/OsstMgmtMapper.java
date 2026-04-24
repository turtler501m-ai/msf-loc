package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpDetailVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.ScanVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("OsstMgmtMapper")
public interface OsstMgmtMapper {
	
	/**
	 * 신청정보 미생성 OSST 개통정보 조회
	 */
	List<RcpDetailVO> selectNonOsstRcpList(String jobCd);
	List<RcpDetailVO> selectNonKmvRcpList();

	public RcpDetailVO maxRequestkey();
	
	int insertRcpRequest(RcpDetailVO rcpDetailVO);
	int insertRcpCustomer(RcpDetailVO rcpDetailVO);
	int insertRcpSale(RcpDetailVO rcpDetailVO);
	int insertRcpDelivery(RcpDetailVO rcpDetailVO);
	int insertRcpReq(RcpDetailVO rcpDetailVO);
	int insertRcpMove(RcpDetailVO rcpDetailVO);
	int insertRcpPay(RcpDetailVO rcpDetailVO);
	int insertRcpAgent(RcpDetailVO rcpDetailVO);
	int getStateKey(RcpDetailVO rcpDetailVO);
	int insertRcpState(RcpDetailVO rcpDetailVO);
	int insertRcpChange(RcpDetailVO rcpDetailVO);
	int insertRcpDvcChg(RcpDetailVO rcpDetailVO);
	int deleteRcpAddition(RcpDetailVO rcpDetailVO);
	int insertRcpAddition(RcpDetailVO rcpDetailVO);
	
	/*20240904 MSP 데이터 추가 개발 (이승국)*/
	int insertMspRequest(RcpDetailVO rcpDetailVO);
	int insertMspCustomer(RcpDetailVO rcpDetailVO);
	int insertMspSale(RcpDetailVO rcpDetailVO);
	int insertMspDelivery(RcpDetailVO rcpDetailVO);
	int insertMspReq(RcpDetailVO rcpDetailVO);
	int insertMspMove(RcpDetailVO rcpDetailVO);
	int insertMspPay(RcpDetailVO rcpDetailVO);
	int insertMspAgent(RcpDetailVO rcpDetailVO);
	int insertMspState(RcpDetailVO rcpDetailVO);
	int insertMspChange(RcpDetailVO rcpDetailVO);
	int insertMspDvcChg(RcpDetailVO rcpDetailVO);
	int deleteMspAddition(RcpDetailVO rcpDetailVO);
	int insertMspAddition(RcpDetailVO rcpDetailVO);
	
	public Map<String, String> getAppFormData(ScanVO vo);
	
	// 서식지 생성
	public void updateAppFormXmlYn(ScanVO vo);
	public List<Map<String, String>> getAppFormPointGroupList(String groupCode);
	public List<Map<String, Object>> getMcpCodeList(String groupId);
	public Map<String, String> getAppFormUserData(ScanVO vo);
	
	public void updateOsstRcpCrYn(RcpDetailVO rcpDetailVO);	
	public void updateOsstJuoSubInfo(RcpDetailVO rcpDetailVO);
	public void updateOsstRequestDtl(RcpDetailVO rcpDetailVO);
	
	public String selectScanId(ScanVO vo);
	
	public void updateMspRequestDtl(RcpDetailVO rcpDetailVO);

	public RcpDetailVO selectMcpRequestDtl(String requestKey);
	public void insertMcpRequestDtl(RcpDetailVO rcpDetailVO);
}
