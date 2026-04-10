package com.ktis.msp.rcp.openMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.custMgmt.vo.FileInfoCuVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListExVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtListVO;
import com.ktis.msp.rcp.openMgmt.vo.OpenMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("OpenMgmtMapper")
public interface OpenMgmtMapper {

	// 개통관리목록조회
	public List<OpenMgmtListVO> getOpenMgmtListNew(OpenMgmtListVO searchVO);

	// 개통관리엑셀다운
	public List<OpenMgmtListExVO> getOpenMgmtListExNew(OpenMgmtListExVO searchVO);
	
	//20180809추가 --> 해지고객인경우 스캔프로그램 실행 X
	List<?> validationCanScan(Map<String, Object> pReqParamMap);
	
	// 가입자 정보
	public List<OpenMgmtVO> getOpenMgmtListDtl(Map<String, Object> param);
	
	// 계약 이력
	public List<OpenMgmtVO> getSubInfoHist(Map<String, Object> param);
	
	// 단말 이력
	public List<OpenMgmtVO> getModelHist(Map<String, Object> param);
	
	// 상품 이력
	public List<OpenMgmtVO> getFeatureHist(Map<String, Object> param);
	
	// 납부 방법
	public List<OpenMgmtVO> getBanHist(Map<String, Object> param);
	
	// 청구수납 이력
	public List<OpenMgmtVO> getInvPymList(Map<String, Object> param);
			
	// 법정대리인
	public List<OpenMgmtVO> getAgentInfo(Map<String, Object> param);
	
	// 법정대리인(앱설치확인)
	void updateAppInstYnCnfm(OpenMgmtVO searchVO);

	// 할인유형이력
	public List<OpenMgmtVO> getAddInfoList(Map<String, Object> param);
	
	// 기기변경이력
	public List<OpenMgmtVO> getDvcChgList(Map<String, Object> param);
	
	// 신청서정보조회
	public List<OpenMgmtVO> getApplFormInfo(OpenMgmtVO searchVO);
	
	// 녹취파일조회
	List<?> getFile1(FileInfoCuVO fileInfoVO);
	
	// 녹취파일업로드
	int fileInsertByCust(FileInfoCuVO fileInfoVO);
	
	
}
