package com.ktis.msp.rcp.sgiMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtCanListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtDmndDtlListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtDmndListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtOverPymListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtRejectListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : SgiMgmtMapper
 * @Description : 단말계약조회 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2018.06.04 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2018. 6. 04.
 */
@Mapper("sgiMgmtMapper")
public interface SgiMgmtMapper {
	
	/**
	 * @Description : 단말계약 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtDmndListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndList(SgiMgmtDmndListVO sgiMgmtVO);
	
	/**
	 * @Description : 단말계약 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtDmndListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndListEx(SgiMgmtDmndListVO sgiMgmtVO);
	
	/**
	 * @Description : 단말계약상세 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtDmndListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndDtlList(SgiMgmtDmndDtlListVO sgiMgmtVO);
	
	/**
	 * @Description : 단말계약상세 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtDmndDtlListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndDtlListEx(SgiMgmtDmndDtlListVO sgiMgmtVO);
	
	/**
	 * @Description : 청구취소조회 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtCanListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiCanList(SgiMgmtCanListVO sgiMgmtVO);
	
	/**
	 * @Description : 청구취소조회 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtCanListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiCanListEx(SgiMgmtCanListVO sgiMgmtVO);
	
	/**
	 * @Description : 과납내역조회 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtOverPymListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiOverPymList(SgiMgmtOverPymListVO sgiMgmtVO);
	
	/**
	 * @Description : 과납내역조회 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtOverPymListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiOverPymListEx(SgiMgmtOverPymListVO sgiMgmtVO);
	
	/**
	 * @Description : 불능처리조회 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtRejectListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiRejectList(SgiMgmtRejectListVO sgiMgmtVO);
	
	/**
	 * @Description : 불능처리조회 LIST 항목을 가져온다.
	 * @Param  : SgiMgmtRejectListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiRejectListEx(SgiMgmtRejectListVO sgiMgmtVO);
}
