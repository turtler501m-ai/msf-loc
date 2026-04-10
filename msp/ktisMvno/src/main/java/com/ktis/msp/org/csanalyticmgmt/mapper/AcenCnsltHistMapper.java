package com.ktis.msp.org.csanalyticmgmt.mapper;

import com.ktis.msp.org.csanalyticmgmt.vo.AcenCnsltHistVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("AcenCnsltHistMapper")
public interface AcenCnsltHistMapper {

    /** A'Cen 상담이력 SR 목록 조회 */
    List<EgovMap> getAcenCnsltSrList(AcenCnsltHistVO searchVO);

    /** A'Cen 상담이력 SR 월 통계 조회 */
    List<EgovMap> getAcenCnsltSrStatsList(AcenCnsltHistVO searchVO);

}
