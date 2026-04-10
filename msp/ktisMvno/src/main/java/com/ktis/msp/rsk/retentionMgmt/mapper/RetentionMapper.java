package com.ktis.msp.rsk.retentionMgmt.mapper;

import java.util.List;

import com.ktis.msp.rsk.retentionMgmt.vo.RetentionVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("retentionMapper")
public interface RetentionMapper {
    
	/**
     * @Description : 해지 후 재가입 정보 리스트를 보여준다.
     * @Param  : retentionVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.08.31 
     */
    List<?> retentionMgmtList(RetentionVO retentionVO);
    
	/**
     * @Description : 해지 후 재가입 정보 화면 엑셀다운로드
     * @Param  : retentionVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.09.02
     */
    List<?> retentionMgmtEx(RetentionVO retentionVO);    
}
