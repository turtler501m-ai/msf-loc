package com.ktis.msp.org.rqstmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.rqstmgmt.vo.RqstMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : RqstMgmtMapper
 * @Description : 청구 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("rqstMgmtMapper")
public interface RqstMgmtMapper {
	
	/**
	 * @Description : 청구 LIST 항목을 가져온다.
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectRqstMgmtList(RqstMgmtVo rqstMgmtVo);
	
	/**
	 * @Description : 청구 LIST 항목을 가져온다. 엑셀용
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectRqstMgmtListEx(RqstMgmtVo rqstMgmtVo);
	
	
	/**
	 * @Description : 번호이동 LIST 항목을 가져온다.
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getMnpList(RqstMgmtVo rqstMgmtVo);
	
	/**
	 * @Description : 번호이동 LIST 항목을 가져온다. 엑셀용
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> getMnpListEx(RqstMgmtVo rqstMgmtVo);
	
	/**
	 * @Description : 청구상세(TAX&VAT용) LIST 항목을 가져온다.
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectRqstMgmtDetailList(RqstMgmtVo rqstMgmtVo);
	
	/**
	 * @Description : 청구상세(TAX&VAT용) 항목을 가져온다. 엑셀용
	 * @Param  : RqstMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectRqstMgmtDetailListEx(RqstMgmtVo rqstMgmtVo);
	
	
	String getpSvcCntrNo(String contractNum);
}
