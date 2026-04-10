package com.ktis.msp.org.mnfctmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.mnfctmgmt.vo.MnfctMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : MnfctMgmtMapper
 * @Description : 제조사 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("mnfctMgmtMapper")
public interface MnfctMgmtMapper {
	
	/**
	 * @Description : 제조사 LIST 항목을 가져온다.
	 * @Param  : mnfctMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectMnfctMgmtList(MnfctMgmtVo mnfctMgmtVo);
	
	/**
	 * @Description : 신규 제조사/공급사를 생성한다.
	 * @Param  : mnfctMgmtVo
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertMnfctMgmt(MnfctMgmtVo mnfctMgmtVo);
    
    /**
     * @Description : 제조사/공급사를 수정한다
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 27.
     */
    int updateMnfctMgmt(MnfctMgmtVo mnfctMgmtVo);
    
    /**
     * @Description : 제조사명 리스트를 가져온다
     * @Param  : 
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 8. 27.
     */
    List<?> selectMnfctList(MnfctMgmtVo mnfctMgmtVo);

}
