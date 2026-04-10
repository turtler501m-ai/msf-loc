package com.ktis.msp.rcp.canCustMgmt.mapper;

import com.ktis.msp.rcp.canCustMgmt.vo.CanBatchVo;
import com.ktis.msp.rcp.canCustMgmt.vo.CanCustVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

/**
 * @Class Name : CanCustMapper
 * @Description : CanCust Mapper
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.08.10  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 8. 10.
 */
@Mapper("canCustMapper")
public interface CanCustMapper {
    
	/**
     * @Description : 해지자 정보 리스트를 보여준다.
     * @Param  : CanCustVO
     * @Return : List<?>
     * @Author : 장익준
     * @Create Date : 2016. 7. 11.
     */
    List<?> selectCanCustList(CanCustVO canCustVO);
    
	/**
     * @Description : 해지복구자 정보 리스트를 보여준다.
     * @Param  : CanCustVO
     * @Return : List<?>
     * @Author : 장준화
     * @Create Date : 2019. 3. 26.
     */
    List<?> selectRclCustList(CanCustVO canCustVO);
    /**
     * @Description 일괄직권해지 목록 조회
     */
    List<EgovMap> getCanBatchList(CanBatchVo searchVo);

    /**
     * @Description 일괄직권해지 엑셀 다운로드 목록 조회
     */
    List<EgovMap> getCanBatchListExcelDown(CanBatchVo canBatchVo);

    /**
     * @Description contractNum 유효성 검사
     */
    String getJuoSubStatus(String contractNum);

    /**
     * @Description 일괄직권해지 배치 대상 등록
     */
    int insertCanBatch(CanBatchVo canBatchVo);
    int insertCanBatchSkip(CanBatchVo canBatchVo);

    /**
     * @Description 일괄직권해지 미처리 대상 count
     */
    String getCanBatchTargetCount();

    /**
     * @Description 일괄직권해지 요청 count
     */
    String getCanBatchRequestCount();
    
    /**
     * @Description : 해지상담신청 정보 리스트를 보여준다.
     */
    List<?> selectCanCslList(CanCustVO canCustVO);

    /**
     * @Description : 해지상담신청 정보 리스트 엑셀 다운로드
     */
    List<?> selectCanCslListByExcel(CanCustVO canCustVO);
    
    String getCustStatus(String custReqSeq);
	String getCanCslFileNum(String oldScanId);
	int insertCanCslEmvFile(CanCustVO canCustVO);
	int updateCanCslProcCd(CanCustVO canCustVO);
	int updateCanCslEmvFileMst(CanCustVO canCustVO);
}