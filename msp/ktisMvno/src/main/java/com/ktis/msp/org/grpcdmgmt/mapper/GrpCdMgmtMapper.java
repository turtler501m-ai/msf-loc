package com.ktis.msp.org.grpcdmgmt.mapper;

import java.util.HashMap;
import java.util.List;

import com.ktis.msp.org.grpcdmgmt.vo.GrpCdMgmtVO;
import com.ktis.msp.org.grpcdmgmt.vo.GrpMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("grpCdMgmtMapper")
public interface GrpCdMgmtMapper {

    /**
     * @Description : 공통코드 조회
     * @Param  : GrpMgmtVO
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    List<?> selectGrpMgmtList(GrpMgmtVO grpMgmtVO);
    
    /**
     * @Description : 공통코드 추가
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int insertGrpMgmt(GrpMgmtVO grpMgmtVO);
    
    /**
     * @Description : 공통코드 수정
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int updateGrpMgmt(GrpMgmtVO grpMgmtVO);
    
    /**
     * @Description : 공통코드 삭제
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int deleteGrpMgmt(GrpMgmtVO grpMgmtVO);
    
    /**
     * @Description :GrpMgmtVO 공통코드 상세조회
     * @Param  : GrpCdMgmtVO
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    List<?> selectGrpCdMgmtList(GrpCdMgmtVO grpCdMgmtVO);
    
    /**
     * @Description : 공통코드 상세 추가
     * @Param  : GrpCdMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int insertGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO);
    
    /**
     * @Description : 공통코드 상세 수정
     * @Param  : GrpCdMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int updateGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO);
    
    /**
     * @Description : 공통코드 상세 삭제
     * @Param  : GrpCdMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int deleteGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO);
    
    /**
     * @Description : 공통코드 상세 그룹 삭제
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 28.
     */
    int deleteAllGrpCdMgmt(GrpMgmtVO grpMgmtVO);
    
    /**
    * @Description : 공통코드 LOV 파일
    * @Param  : 
    * @Return : List<HashMap<String,String>>
    * @Author : 고은정
    * @Create Date : 2014. 9. 18.
     */
    List<HashMap<String, String>> listGrpCdLOV();
    
    /**
    * @Description : 공통코드 그룹명 체크 
    * @Param  : GrpMgmtVO
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 20.
     */
    int existKorNmGrpMgmt(GrpMgmtVO grpMgmtVO);
    
    /**
     * @Description : 그룹ID 중복체크
     * @Param  : GrpMgmtVO
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 29.
     */
    int isExistGrpCdMgmt(GrpMgmtVO grpMgmtVO); 
    
    /**
     * @Description : 공통코드 조회 
     * @Param  : 
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 10. 7.
      */
     List<?> listCmnCd(String code);
}
