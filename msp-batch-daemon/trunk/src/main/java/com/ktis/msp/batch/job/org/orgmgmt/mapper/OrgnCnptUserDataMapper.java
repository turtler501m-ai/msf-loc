package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.job.org.orgmgmt.vo.CpntUserVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("orgnCnptUserDataMapper")
public interface OrgnCnptUserDataMapper {
	
	/**
	 * M유통 판매점 사용자 연동
	 * @return
	 */
	public void callCnptUserData(Map<String, Object> param);

	// 조직현행화 대상 조회 
	//List<CpntUserVo> getCnptUserOrgnList();
	
	// 현행화 판매점 파일 처리내용 조회
	int getCnptUserDataFileYn(String fileNm);

	// 조직현행화 대상 조회 
	List<CpntUserVo> getCnptUserOrgnList();
	
	//MDS_AGNCY_MNGR_MST 테이블을 전체 삭제
	int deleteMdsAgncyMngrMst();	

	//MDS_AGNCY_MNGR_MST  테이블을 오늘자로 현행화
	void insertMdsAgncyMngrMst(String fileNm);
	
	//MDS_AGNCY_MNGR_MST 테이블에서 매니저 전화번호를 조회
	String getCnptUserMngrMphonNo(CpntUserVo vo);
	
	//M2M USIM 유통망인지 확인
	int getCnptUserDataM2MYn(CpntUserVo vo);
	
	//MDS_AGNCY_MNGR_MST 테이블에서 매니저 전화번호를 조회
	String getCnptUserBizRegNum(CpntUserVo vo);

	//M2M 계정이 사업자번호로 ID가 존재하는지 확인
	int getCnptUserDataBizRegNumYn(CpntUserVo vo);
	
	//CMN_USR_MST 테이블에 M2M 사업자번호로 된 아이디, 조직코드가 동일한 계정이 있는지 한번더 확인
	int getCnptUserDataBizRegNumOrgIdYn(CpntUserVo vo);
	
	//MDS_AGNCY_MNGR_MST 테이블에서 매니저 전화번호를 조회
	//String getCnptUserByOrgnId(CpntUserVo vo);

	//CMN_USR_MST 테이블에 최종 세팅된 userId로 계정이 있는지 조회  
	int getCnptUserDataIdUserCnt(CpntUserVo vo);
	
	//CMN_USR_MST에 INSERT 하여 계정 생성
	void insertCnptUserData(CpntUserVo vo);
	// CMN_USR_HST에 INSERT 하여 계정 이력 생성
	void insertCnptUserDataHst(CpntUserVo vo);
	//CMN_USG_GRP_ASGN_MST에 INSERT 하여 권한 생성
	void insertCnptUserDataAsgnMSt(CpntUserVo vo);
	//CMN_USG_GRP_ASGN_HST에 INSERT 하여 권한이력 생성
	void insertCnptUserDataAsgnHSt(CpntUserVo vo);

	//계정이 사용자명,전화번호,userId로 존재하는지 확인
	int getCnptUserDataMphonNumUsrNm(CpntUserVo vo);
	
	//userId(사업자 번호) 별 전화번호 or 사용자명 변경된 경우에 업데이트
	void updateCnptUserDataMphonNumUsrNm(CpntUserVo vo);
}
