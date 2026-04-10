package com.ktis.msp.pps.orgmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.pps.orgmgmt.vo.FaxHstVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
/**
 * @Class Name : PpsAgencyOrgMgmtMapper
 * @Description :   선불 대리점 판매점관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("ppsAgencyOrgMgmtMapper")
public interface PpsAgencyOrgMgmtMapper {
	
	/**
	 * 대리점 판매점목록 조회
	 * @param pRequestParamMap
	 * @return
	 */
	List<?> getAgencyOrgMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 대리점 판매점내역 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	List<?> getAgencyOrgMgmtListExcel(Map<String, Object> pRequestParamMap);
	
	/**
	 * 판매점 조직아이디 생성
	 * @param pRequestParamMap
	 * @throws Exception
	 */
	String getPpsSearchOrgnId();
	
	/**
     * @Description : 조직상세정보를 조회한다.
     * @Param  : OrgMgmtVO
     * @Return : OrgMgmtVO
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    OrgMgmtVO ppsDetailOrgMgmt(OrgMgmtVO orgMgmtVO);
    
    /**
	 * @Description : 신규 조직을 생성한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int ppsInsertOrgMgmt(OrgMgmtVO orgMgmtVO);
    
    /**
	 * @Description : 조직 이력 정보를 넣는다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int ppsInsertOrgMgmtHst(OrgMgmtVO orgMgmtVO);
    
    /**
     * @Description : FAX 리스트 건수조회
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 19.
     */
    int ppsIntCntFaxMgmt(FaxHstVO faxHstVO);
    
    /**
     * @Description : FAX 이력 SEQ MAX 조회
     * @Param  : 
     * @Return : String
     * @Author : 고은정
     * @Create Date : 2014. 8. 19.
     */
    String ppsMaxSeqFaxMgmt(FaxHstVO faxHstVO);
    
    /**
     * @Description : Fax 추가
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 18.
     */
    int ppsInsertFaxMgmt(FaxHstVO faxHstVO);
    
    /**
     * @Description : FAX 이력 업데이트
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 19.
     */
    int ppsUpdateFaxMgmt(FaxHstVO faxHstVO);
    
    /**
	 * @Description : 조직 FAX 정보를 변경한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */     
    int ppsUpdateOrgMgmtFax(OrgMgmtVO orgMgmtVO);
    
    /**
	 * 대리점 정보 조회
	 * @param orgMgmtVO
	 * @return
	 */
	Map<String, Object> ppsGetAgncyInfo(OrgMgmtVO orgMgmtVO);
	
	/**
	 * 대리점유형이력 수정
	 * @param map
	 */
	int ppsUpdateAgncyTypeHst(Map<String, Object> map);
	
	/**
	 * 대리점유형이력 최초 생성
	 * @param map
	 * @return
	 */
	void ppsInsertAgncyTypeHstFst(Map<String, Object> map);
	
	/**
	 * 대리점유형이력 생성
	 * @param map
	 */
	void ppsInsertAgncyTypeHst(Map<String, Object> map);
	
	/**
	 * @Description : 조직 정보를 변경한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */     
    int ppsUpdateOrgMgmtMasking(OrgMgmtVO orgMgmtVO);
    
    /**
	 * @Description : 조직 상태 가동점 체크로직
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int ppsSelectValCnt(OrgMgmtVO orgMgmtVO); 
    
    /**
	 * @Description : 조직 상태 가동점 체크로직
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int ppsSelectValCnt1(OrgMgmtVO orgMgmtVO); 
    
    /**
	 * @Description : 조직 정보를 변경한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */     
    int ppsUpdateOrgMgmt(OrgMgmtVO orgMgmtVO);
    
    /**
	 * @Description : 조직 이력 리스트 Count 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int ppsListCntOrgMgmtHst(OrgMgmtVO orgMgmtVO);
    
    /**
	 * @Description : 조직 이력의 MAX SEQ 조회한다.
	 * @Param  : OrgMgmtVO
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    String ppsListSeqOrgMgmtHst(OrgMgmtVO orgMgmtVO);
    
    /**
     * @Description : 조직 유형 대분류를 가져오기 위해 그룹ID 찾기
     * @Param  : orgnType
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    String ppsSelectOrgnTypeDtlCd(String orgnType);
    
    /**
     * @Description : 조직 유형 대분류를 가져온다
     * @Param  : orgnType
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2015. 6. 25.
     */
    List<?> ppsSelectOrgnTypeDtl(String orgnType);
	
	
}
