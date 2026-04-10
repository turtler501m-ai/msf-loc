package com.ktis.msp.voc.abuse.mapper;

import com.ktis.msp.rcp.canCustMgmt.vo.CanBatchVo;
import com.ktis.msp.voc.abuse.vo.AbuseVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("abuseMapper")
public interface AbuseMapper {

    /**
     * 부정사용주장 단말설정 목록 조회
     */
    List<EgovMap> getAbuseList(AbuseVO searchVo);

    /**
     * 부정사용주장 단말설정 목록 엑셀 다운로드
     */
    List<EgovMap> getAbuseInfoListExcelDown(AbuseVO canBatchVo);

    /**
     * 부정사용주장 단말설정 등록
     */
    void insertAbuseInfo(AbuseVO searchVo);

    /**
     * 부정사용주장 단말설정 imei 등록
     */
    void insertAbuseImeiInfo(AbuseVO searchVo);

    /**
     * 부정사용주장 imei 목록 조회
     */
    List<EgovMap> getAbuseImeiList(AbuseVO searchVo);

    /**
     * 부정사용주장 단말설정 update
     */
    void updateAbuseInfo(AbuseVO searchVo);

    /**
     * 부정사용주장 단말설정 이력 insert
     */
    void insertAbuseInfoHist(AbuseVO searchVo);

    /**
     * 부정사용주장 단말설정 imei 수정(기존 목록 update)
     */
    void updateAbuseOrgImei(AbuseVO searchVo);

    /**
     * 부정사용주장 단말설정 imei 수정(신규 목록 insert)
     */
    void insertAbuseNewImei(AbuseVO searchVo);

    /**
     * 계약정보조회
     */
    List<?> getContractInfo(AbuseVO vo);
}
