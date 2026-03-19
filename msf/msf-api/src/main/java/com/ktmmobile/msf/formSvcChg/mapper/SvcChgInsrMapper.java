package com.ktmmobile.msf.formSvcChg.mapper;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.InsrProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 단말보험 Mapper.
 * ASIS AppformMapper.selectInsrProdList() / insertInsrApplyMst() 와 동일 역할.
 */
@Mapper
public interface SvcChgInsrMapper {

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: AppformMapper.selectInsrProdList(IntmInsrRelDTO)
     * reqBuyType 기준 필터링.
     */
    List<InsrProductDto> selectInsrProdList(Map<String, Object> params);

    /**
     * 단말보험 가입 신청 이력 저장.
     * ASIS: AppformMapper.insertInsrApplyMst(AppformReqDto)
     */
    int insertInsrApplyMst(InsrApplyReqDto dto);
}
