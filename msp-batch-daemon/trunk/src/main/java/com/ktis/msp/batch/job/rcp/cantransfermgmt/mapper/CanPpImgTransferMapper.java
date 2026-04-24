package com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.rcp.cantransfermgmt.vo.CanTransferVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CanPpImgTransferMapper")
public interface CanPpImgTransferMapper {
	
	// 선불해지고객 중 180일이 지난 고객 조회
	List<CanTransferVO> getTmntPpImgList(int stdrCnt);
	
	// 선불해지고객 이미지 이관
	void insertCanPpImg(String requestKey);
	
	// CONTRACT_NUM 조회
	List<CanTransferVO> getContractNum(String contractNum);

	//선불 서식지 정보 가져오기
	List<CanTransferVO> getScanFilePpsCustomerImage(CanTransferVO vo);
	
	//msp의 선불 서식지 경로를 data2로 수정
	void updateMspPpsCustomerImage(CanTransferVO updateVo);
	
	//can에 선불 서식지 정보 존재하는지 체크
	int chkCanPpsCustomerImage(CanTransferVO updateVo);
	
	//can에 선불 서식지 정보 update
	void updateCanPpsCustomerImage(CanTransferVO updateVo);
	
	//can에 선불 서식지 정보 insert
	void insertCanPpsCustomerImage(CanTransferVO updateVo);
}
