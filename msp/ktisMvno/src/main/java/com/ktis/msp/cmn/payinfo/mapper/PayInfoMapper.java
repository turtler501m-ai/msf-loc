package com.ktis.msp.cmn.payinfo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.payinfo.vo.PayInfoDtlVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoMstVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoNonBindVO;
import com.ktis.msp.cmn.payinfo.vo.PayInfoVO;
import com.ktis.msp.org.common.vo.FileInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PayInfoMapper
 * @Description : PayInfo mapper
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.07.11  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 7. 11.
 */
@Mapper("payInfoMapper")
public interface PayInfoMapper {
    
	/**
     * @Description : 대리점 등록 리스트를 보여준다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<?> orgPayInfoList(PayInfoVO payInfoVO);
    
    /**
     * @Description : 대리점 등록 리스트 엑셀다운로드.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<?> orgPayInfoListExcel(PayInfoVO payInfoVO);
    
	/**
     * @Description : 개통 고객 리스트 찾기
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<?> custmgmtLists(PayInfoVO payInfoVO);

    /**
     * @Description : 고객 찾기
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2024. 09. 21.
     */
    List<?> getCustomerInfo(PayInfoVO payInfoVO);
    
    /**
     * @Description : 고객 정보 등록
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 18.
     */
    int insertOffline(Map<String, Object> pRequestParamMap);
    
    /**
	 * @Description : 파일의 기본정보를 등록한다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertFile(FileInfoVO fileInfoVO);
    
    /**
	 * @Description : 파일의 기본정보를 가져온다.
	 * @Param  : FileInfoVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */   
    List<?> getFile1(PayInfoVO payInfoVO);
    
    /**
     * @Description : 자동이체동의서상세 - 파일의 기본정보를 가져온다.
     * @Param  : FileInfoDtlVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */   
    List<?> getFileDtl1(PayInfoDtlVO payInfoDtlVO);

    /**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	String getFileNm(String fileId);
	
	/**
     * @Description : 변경동의서관리 데이터와 현행화 MST 목록 조회
     * @Param  : void
     * @Return : List<Integer>
     * @Author : 김용문
     * @Create Date : 2016. 9. 02.
     */
	List<Integer> getPayinfoDataSynMST();
	
	/**
     * @Description : 변경동의서관리 데이터와 현행화 DTL 목록 조회
     * @Param  : void
     * @Return : List<?>
     * @Author : 김용문
     * @Create Date : 2016. 9. 02.
     */
	List<PayInfoDtlVO> getPayinfoDataSynDTL(int iMstSeq);
	
	/**
     * @Description : 변경동의서관리 데이터와 현행화 DTL UPDATE
     * @Param  : PayInfoDtlVO
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 9. 02.
     */
	int setPayinfoDataSynDTL(PayInfoDtlVO payinfodtlvo);
	
	/**
     * @Description : 자동이체동의서상세 - 파일 명을 찾아온다.
     * @Param  : String
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    String getFileNmDtl(String fileId);

	/**
	 * @Description : 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	String getFileNm2(String fileId);
    
	/**
	 * @Description : 파일 삭제
	 * @Param  : String
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	int deleteFile(String fileId);	
	
	/**
     * @Description : 자동이체동의서상세 - 파일 삭제
     * @Param  : String
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    int updateFileDtl(HashMap<String, String> map);   

    /**
     * @Description : 고객 정보 등록
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 18.
     */
    int insertOffline(PayInfoVO payInfoVO);
    
    /**
     * @Description : 고객 정보 수정
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 7. 28.
     */
    int updateOffline(PayInfoVO payInfoVO);
    
    /**
     * @Description : 자동이체동의서상세 - 고객 정보 등록
     * @Param  : PayInfoDtlVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 18.
     */
    int updateDtl(PayInfoDtlVO payInfoDtlVO);
    
    /**
     * @Description : 
     * @Param  : HashMap<String, String> param
     * @Return : void
     * @Author : 김용문
     * @Create Date : 2016. 7. 22.
     */
    void mspPayinfoImg(HashMap<String, String> param);

    /**
     * @Description : 파일이름 시퀀스 찾기
     * @Param  : 
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2016. 7. 14.
     */
    String getFileNmSeq();
    
	/**
     * @Description : 배치 파일 마스터 정보를 가져온다.
     * @Param  : PayInfoMstVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    List<?> payInfoMstList(PayInfoMstVO payInfoMstVO);
    
	/**
     * @Description : 배치 파일 상세 정보를 가져온다.
     * @Param  : PayInfoDtlVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    List<?> payInfoDtlList(PayInfoDtlVO payInfoDtlVO);
    
    /**
     * @Description : 관리 상세 승인 정보 변경
     * @Param  : PayInfoMstVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    int updateApprovalYn(PayInfoMstVO payInfoMstVO);
    
    /**
     * @Description : 미등록건수 체크
     * @Param  : PayInfoMstVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    int failCnt(PayInfoMstVO payInfoMstVO);
    
	/**
     * @Description : KT 연동자료 조회
     * @Param  : PayInfoDtlVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    List<?> payInfoKtList(PayInfoDtlVO payInfoDtlVO);
    
    /**
     * @Description : 개통/변경 등록서 검증 처리
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    int updateCheYn(PayInfoVO payInfoVO);    

    /**
     * @Description : KT연동자료 등록서 검증 처리
     * @Param  : PayInfoVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2016. 7. 12.
     */
    int updateAppYn(PayInfoDtlVO payInfoDtlVO);
    
    /**
     * @Description : 
     * @Param  : HashMap<String, String> param
     * @Return : void
     * @Author : 김용문
     * @Create Date : 2016. 7. 29.
     */
    List<String> getImgBindTarget(String strGrpId);
    
    /**
     * @Description : 
     * @Param  : HashMap<String, String> param
     * @Return : void
     * @Author : 김용문
     * @Create Date : 2016. 8. 01.
     */
    int insertCmnKftcNonBindFileHst(PayInfoNonBindVO payinfononbindvo);
    
    /**
     * @Description : 
     * @Param  : HashMap<String, String> param
     * @Return : void
     * @Author : 김용문
     * @Create Date : 2016. 8. 01.
     */
    List<String> getBankCdComboList();
    
	/**
     * @Description : 파일 이력을 가져온다.
     * @Param  : PayInfoVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<?> fileList(PayInfoVO payInfoVO);
    
    /**
     * @Description : 증거파일 목록 조회(파일유형에 따라서 조회)
     * @Param  : String
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<String> getEvidenceFileExtList(String strEvidenceTypeCd);
    
    /**
     * @Description : 증거파일 리사이징 확장자 목록 조회
     * @Param  : String
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<String> getEvidenceFileResizeExtList(String strEtc3);
    
    /**
     * @Description : 증거파일 용량 조회
     * @Param  : PayInfoVO
     * @Return : long
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    long getEvidenceFileSize(String strEvidenceFileSizeMsg);
    
    /**
     * @Description : 증거파일 용량 체크 대상 체크
     * @Param  : PayInfoVO
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    String getEvidenceFileSizeTargetChk(String strExt);
    
    /**
     * @Description : 증거파일 용량 메시지 조회
     * @Param  : PayInfoVO
     * @Return : long
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    String getEvidenceFileSizeMsg(String strstrEvidenceFileSizeMsg);
    
	/**
     * @Description : 관리화면 엑셀 다운로드
     * @Param  : payInfoMstVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    List<?> selectPayinfoMstEx(PayInfoMstVO payInfoMstVO);    
    
    
	/**
     * @Description : 관리화면상세 엑셀 다운로드
     * @Param  : payInfoDtlVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    List<?> selectPayinfoDtlEx(PayInfoDtlVO payInfoDtlVO);    
    
    
	/**
     * @Description : KT연동동의서관리 엑셀 다운로드
     * @Param  : payInfoDtlVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    List<?> selectPayinfoKtEx(PayInfoDtlVO payInfoDtlVO);
    
    

    
	/**
     * @Description : 동의서 파일 이력 엑셀 다운로드
     * @Param  : payInfoVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.01
     */
    List<?> searchFileInfoEx(PayInfoVO payInfoVO);
    
    
    /**
     * @Description : 
     * @Param  : PayInfoDtlVO payinfodtlvo
     * @Return : int
     * @Author : 김용문
     * @Create Date : 2016. 7. 29.
     */
    int getDtlReadyChk(PayInfoDtlVO payInfoDtlVO);
 
    /**
     * @Description : 
     * @Param  : 
     * @Return : void
     * @Author : 박준성
     * @Create Date : 2017. 08. 10.
     */
    List<String> getReqBankComboList();    
    
    
    /**
     * 카드사 공통코드 조회시 expsns_str_val 이용
     */
    List<String> getReqCardComboList(PayInfoVO vo);
    
    
    /**
	 * @Description : 동의서파일이력조회 상세 파일 명을 찾아온다.
	 * @Param  : String
	 * @Return : String
	 * @Author : 
	 * @Create Date : 2021. 6. 14.
	 */
	String getFileNmSec(String fileId);
}