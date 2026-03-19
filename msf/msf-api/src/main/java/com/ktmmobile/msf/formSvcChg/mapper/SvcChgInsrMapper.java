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
     * 보험 가입 상태 조회.
     * ASIS: MypageMapper.getInsrInfo() — insrViewType(A/B/C/D/E/F/ING) 결정에 사용.
     * @param ncn 서비스계약번호
     */
    Map<String, String> selectInsrInfo(String ncn);

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: AppformMapper.selectInsrProdList()
     * @param params reqBuyType(UU/MM), rprsPrdtId(단말ID, MM일 때 필수)
     */
    List<InsrProductDto> selectInsrProdList(Map<String, String> params);

    /**
     * 단말보험 가입 정보 저장.
     * ASIS: CustRequestDaoImpl.insertCustRequestInsr()
     */
    int insertCustRequestInsr(InsrApplyReqDto dto);
}
