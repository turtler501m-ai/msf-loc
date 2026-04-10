package com.ktis.msp.pps.rcpmgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("rcpMapper")
public interface RcpMapper {
	
	/**
	 * @Description : 신청 정보 상세내역 가져오기
	 * @Param  : RcpListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
    public List<?> getRcpList(RcpListVO searchVO);
	
    /**
	 * @Description : 신청 리스트 조회
	 * @Param  : RcpListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
     */
    public List<?> rcpMgmtList(RcpListVO searchVO);
    
    /**
	 * @Description : 엑셀 다운로드 쿼리
	 * @Param  : RcpListVO
	 * @Return : List<RcpListVO>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
     */
	public List<RcpListVO> rcpMgmtListEx(RcpListVO rcpListVO);

	/**
	 * @Description : 조직 정보 가져오기
	 * @Param  : orgnId
	 * @Return : EgovMap
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	public EgovMap orgnInfo(String orgnId);
}