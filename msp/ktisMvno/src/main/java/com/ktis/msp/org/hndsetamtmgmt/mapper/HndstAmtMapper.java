package com.ktis.msp.org.hndsetamtmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.hndsetamtmgmt.vo.HndstAmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : HndstAmtMapper
 * @Description : 제품 단가 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("hndstAmtMapper")
public interface HndstAmtMapper {
    
	/**
	 * @Description : 단통법 관련 보조금 MAX를 찾아온다.
	 * @Param  : HndstAmtVo
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    String selectMaxAmt(HndstAmtVo hndstAmtVo);  
}
