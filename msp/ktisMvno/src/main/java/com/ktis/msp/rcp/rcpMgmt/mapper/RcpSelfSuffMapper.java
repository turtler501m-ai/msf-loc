package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSuffVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rcpSelfSuffMapper")
public interface RcpSelfSuffMapper {

	List<?> getRcpMgmtListSelfSuff(Map<String, Object> param);

	List<?> getRcpMgmtListSelfSuffDtl(Map<String, Object> param);
	
	List<?> getSelfSuffListByExcel(Map<String, Object> param);
	
	
	void updateMcpReq(Map<String, Object> param);
	void updateMcpApdReq(Map<String, Object> param);
	
	void updateMcpReqStateUsim(Map<String, Object> param);
	
	void updateMcpReqStatePhone(Map<String, Object> param);
	
	void updateMcpReqDlvry(Map<String, Object> param);
	void updateMcpReqApdDlvry(Map<String, Object> param);

	void updateMcpReqMask(Map<String, Object> param);
	void updateMcpApdReqMask(Map<String, Object> param);
	
	
	String getDlvryTbCd(String tbCd);
	
	String isPhoneDlvryInfoChk(String resNo);
	
	int updatePhoneDlvryNo(RcpSelfSuffVO vo);	

	String isUsimDlvryInfoChk(String resNo);
	
	int updateUsimDlvryNo(RcpSelfSuffVO vo);

	int updateReqSrNo(RcpSelfSuffVO vo);
	int updateReqApdSrNo(RcpSelfSuffVO vo);

	String isPhoneDlvryOkChk(String dlvryNo);
	String isUsimDlvryOkChk(String dlvryNo);	
	int updatePhoneDlvryOk(RcpSelfSuffVO vo);
	int updateUsimDlvryOk(RcpSelfSuffVO vo);
	int updateReqUsimDlvryOk(RcpSelfSuffVO vo);
	int updateApdUsimDlvryOk(RcpSelfSuffVO vo);
}
