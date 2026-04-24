package com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper;

import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper("CanPpTransferMapper")
public interface CanPpTransferMapper {

	// 해지후 180일이 지난 선불 고객 조회
	List<CanTransferVO> getTmntPpCntrList(Map<String, Object> paramMap);
	
	//PPS_RCG_REMOTE_REQ_LOG 테이블 대상 로그 입력날짜 기준 180일 지난 데이터 리스트
	List<CanTransferVO> getRcgRmoteReqLogList(Map<String, Object> paramMap);
	
	List<CanTransferVO> getRcgRmoteReqLogDelList(int stdrCnt);

	// 선불 테이블 이관
	int insertCanPpsCustomer(String contractNum);
	int insertCanPpsCustomerHist(String contractNum);
	int insertCanPpsAgentStmOpen(String contractNum);
	int insertCanPpsRcgRealCms(String contractNum);
	int insertCanPpsRcgRealCmsSetup(String contractNum);
	int insertCanPpsRealPayinfoCmsLog(String contractNum);
	int insertCanPpsVirAccountKibnet(String contractNum);
	int insertCanPpsRcgArs(String contractNum);
	int insertCanPpsRcgRemoteReqLog(CanTransferVO vo);
	
	// 선불 데이터 치환
	int updatePpsCustomer(String contractNum);
	int updatePpsCustomerHist(String contractNum);
	int updatePpsAgentStmOpen(String contractNum);
	int updatePpsRcgRealCms(String contractNum);
	int updatePpsRcgRealCmsSetup(String contractNum);
	int updatePpsRealPayinfoCmsLog(String contractNum);
	int updatePpsVirAccountKibnet(String contractNum);
	int updatePpsRcgArs(String contractNum);
	int updatePpsRcgRemoteReqLog(CanTransferVO vo);

	// 선불 테이블 삭제
	int deletePpsCustomer(String contractNum);
	int deletePpsCustomerHist(String contractNum);
	int deletePpsAgentStmOpen(String contractNum);
	int deletePpsRcgRealCms(String contractNum);
	int deletePpsRcgRealCmsSetup(String contractNum);
	int deletePpsRealPayinfoCmsLog(String contractNum);
	int deletePpsVirAccountKibnet(String contractNum);
	int deletePpsRcgArs(String contractNum);
	int deletePpsRcgRemoteReqLog(CanTransferVO vo);

	// 선불 이관 테이블 삭제
	int deleteCanPpsCustomer(String contractNum);
	int deleteCanPpsCustomerHist(String contractNum);
	int deleteCanPpsAgentStmOpen(String contractNum);
	int deleteCanPpsRcgRealCms(String contractNum);
	int deleteCanPpsRcgRealCmsSetup(String contractNum);
	int deleteCanPpsRealPayinfoCmsLog(String contractNum);
	int deleteCanPpsVirAccountKibnet(String contractNum);
	int deleteCanPpsRcgArs(String contractNum);
	int deleteCanPpsRcgRemoteReqLog(CanTransferVO vo);

}
