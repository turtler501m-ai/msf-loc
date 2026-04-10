package com.ktis.msp.org.hndsetmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.hndsetmgmt.vo.HndstModelVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : HndstModelMapper
 * @Description : 제품 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("hndstModelMapper")
public interface HndstModelMapper {
	
	/**
	 * @Description : 제품 LIST 항목을 가져온다.
	 * @Param  : hndstModelVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectHndstModelList(HndstModelVo hndstModelVo);
	
	/**
	 * @Description : 제품 정보를 생성한다.
	 * @Param  : hndstModelVo
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertHndstModel(HndstModelVo hndstModelVo);
    
    /**
     * @Description : 제품 정보를 수정한다
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
    int updateHndstModel(HndstModelVo hndstModelVo);
    
    /**
     * @Description : 제품명 중복 체크
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
    int isExistHndstModel(HndstModelVo hndstModelVo);
    
    /**
     * @Description : KT 제품명 중복 체크
     * @Param  : HndstModelVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 29.
     */
    int isExistHndstModel2(HndstModelVo hndstModelVo);
    
    /**
    * @Description : 제품 색상코드
    * @Param  : 
    * @Return : List<?>
    * @Author : 고은정
    * @Create Date : 2014. 10. 27.
     */
    List<?> listPrdtColorCd(String mnfctCd);
}
