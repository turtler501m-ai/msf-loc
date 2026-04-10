package com.ktis.msp.org.prmtmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("DisPrmtMgmtMapper")
public interface DisPrmtMgmtMapper {

	//평생할인 정책 목록 조회
    List<DisPrmtMgmtVO> getDisPrmtList(DisPrmtMgmtVO vo);
    
    // 평생할인 할인 조직 목록 조회
 	List<DisPrmtMgmtSubVO> getDisPrmtOrgnList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);
 	
 	// 평생할인 할인 요금제 목록 조회
 	List<DisPrmtMgmtSubVO> getDisPrmtSocList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);
 	
 	// 평생할인 할인 부가서비스 목록 조회
 	List<DisPrmtMgmtSubVO> getDisPrmtAddList(DisPrmtMgmtSubVO disPrmtMgmtSubVO); 
 	
    // 평생할인 정책 상세 조회
    DisPrmtMgmtVO getDisPrmtDtlInfo(DisPrmtMgmtVO vo);
    
 	// 평생할인 상세 조직 목록 조회
 	List<DisPrmtMgmtSubVO> getDisPrmtDtlOrgnList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);
 	
 	// 평생할인 상세 요금제 목록 조회
 	List<DisPrmtMgmtSubVO> getDisPrmtDtlSocList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);
 	
 	// 평생할인 상세 부가서비스 목록 조회
 	List<DisPrmtMgmtSubVO> getDisPrmtDtlAddList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);

 	// 평생할인 중복 체크
 	List<DisPrmtMgmtVO> getDisPrmtDupByInfo(DisPrmtMgmtVO disPrmtMgmtVO);
 	
    //평생할인 마스터 추가
	int insertDisPrmtMst(DisPrmtMgmtVO vo);

	//평생할인 조직 추가
	int insertDisPrmtOrg(DisPrmtMgmtVO orgnId);

	//평생할인 요금제 추가
	int insertDisPrmtSoc(DisPrmtMgmtVO rateCd);

	//평생할인 부가서비스 추가
	int insertDisPrmtAdd(DisPrmtMgmtVO soc);
	
	//평생할인 정보 변경
	int updDisPrmtByInfo(DisPrmtMgmtVO disPrmtMgmtVO) ;
	
	//평생할인 상세정보 목록 조회
	List<DisPrmtMgmtVO> getDisPrmtDtlList(DisPrmtMgmtVO disPrmtMgmtVO);
	
	//평생할인 상세정보 엑셀다운로드
	List<DisPrmtMgmtVO> getDisPrmtDtlListExcel(DisPrmtMgmtVO disPrmtMgmtVO);

	// ********************** 엑셀 업로드 시작 **********************
	// Chk테이블에 데이터 있는지 확인
 	int getDisMstChk();
 	
	//평생할인 엑셀 업로드 중복 체크
 	List<DisPrmtMgmtVO> getDisPrmtDupByInfoExl(DisPrmtMgmtVO disPrmtMgmtVO);
 	
 
 	
 	// **********************Chk 테이블로 INSERT **********************
 	// 엑셀데이터 평생할인 마스터 Chk테이블에 추가
 	int insertDisPrmtMstChk(DisPrmtMgmtVO vo);
 	
 	// 엑셀데이터 평생할인 조직 Chk테이블에 추가
	int insertDisPrmtOrgChk(DisPrmtMgmtVO vo);
	
	// 엑셀데이터 평생할인 요금제 Chk테이블에 추가
	int insertDisPrmtSocChk(DisPrmtMgmtVO vo);

	// 엑셀데이터 평생할인 부가서비스 Chk테이블에 추가
	int insertDisPrmtAddChk(DisPrmtMgmtVO vo);
	
	// ********************** Chk 테이블자료 실제 테이블로 INSERT **********************
	// Chk데이터 채널별요금할인 마스터 실테이블에 추가
	int insertDisPrmtMstExl();
	
	// Chk데이터 채널별요금할인 조직 실테이블에 추가
	int insertDisPrmtOrgExl();
		
	// Chk데이터 채널별요금할인 요금제 실테이블에 추가
	int insertDisPrmtSocExl();
	
	// Chk데이터 채널별요금할인 부가서비스 실테이블에 추가
	int insertDisPrmtAddExl();

	// ********************** Chk 테이블자료 DELETE  **********************
	// 채널별요금할인 마스터 Chk테이블 DELETE
	int deleteDisPrmtMstChk();
	
	// 채널별요금할인 조직 Chk테이블 DELETE
	int deleteDisPrmtOrgChk();
	
	// 채널별요금할인 요금제 Chk테이블 DELETE
	int deleteDisPrmtSocChk();
	
	// 채널별요금할인 부가서비스 Chk테이블 DELETE
	int deleteDisPrmtAddChk();
	
	
	// ********************** 엑셀 업로드 끝 **********************
		
	// 평생할인 복사시 조직정보 조회
	List<DisPrmtMgmtSubVO> getDisPrmtDtlOrgnCopyList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);
	// 평생할인 복사시 요금제정보 조회
	List<DisPrmtMgmtSubVO> getDisPrmtDtlSocCopyList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);
	// 평생할인 복사시 부가서비스정보 조회
	List<DisPrmtMgmtSubVO> getDisPrmtDtlAddCopyList(DisPrmtMgmtSubVO disPrmtMgmtSubVO);

	//평생할인 정책 목록 콤보 조회
	List<EgovMap> getDisPrmtListCombo(DisPrmtMgmtVO vo);
	
	List<DisPrmtMgmtVO> getDisChoChrgPrmtListExcelDown(DisPrmtMgmtVO vo);
}