package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.cmn.sellusim.vo.SellUsimVO;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpNowDlvryUsimVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RcpNowDlvryUsimMapper")
public interface RcpNowDlvryUsimMapper {

	
	// 바로배송 온라인신청서 고객 3일 이내 유심 미등록 고객 조회
	List<RcpNowDlvryUsimVO> getNotRegUsimCust(int stdrCnt);
	
	void insertMspNowDlvrySms(RcpNowDlvryUsimVO rcpNowDlvryUsimVO);
	
	List<RcpNowDlvryUsimVO> getNotOpenNowDlvry(int stdrCnt);
	
	// 신청서상태 변경  >>>   관리자삭제(유심번호 미입력)
    void updateMcpRequestPstate(String requestKey);
    
}
