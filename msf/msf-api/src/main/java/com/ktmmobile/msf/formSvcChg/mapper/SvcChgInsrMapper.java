package com.ktmmobile.msf.formSvcChg.mapper;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.InsrProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 단말보험 Mapper.
 * 테이블: MSF_CUST_REQUEST_INSR (가입신청보험정보)
 * DB링크: MSP_INTM_INSR_MST@DL_MSP (단말보험 상품 마스터)
 */
@Mapper
public interface SvcChgInsrMapper {

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: AppformMapper.selectInsrProdList()
     * @param params reqBuyType (UU=최초, MM=재구매)
     */
    List<InsrProductDto> selectInsrProdList(Map<String, String> params);

    /**
     * 단말보험 가입 정보 저장.
     * ASIS: AppformMapper.insertInsrApplyMst()
     */
    int insertCustRequestInsr(InsrApplyReqDto dto);
}
