package com.ktis.msp.org.csanalyticmgmt.mapper;

import com.ktis.msp.org.csanalyticmgmt.vo.AcenHistVO;
import com.ktis.msp.org.csanalyticmgmt.vo.AcenRcpMgmtVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;
import java.util.Map;

@Mapper("AcenRcpMgmtMapper")
public interface AcenRcpMgmtMapper {

  /** A'Cen 신청 리스트 조회 */
  List<EgovMap> getAcenRcpMgmtList(AcenRcpMgmtVO searchVO);

  /**  A'Cen 연동이력 조회 */
  List<AcenHistVO> getAcenHist(AcenRcpMgmtVO searchVO);

  /**  A'Cen 실패이력 조회 */
  List<EgovMap> getAcenFailHist(AcenRcpMgmtVO searchVO);

  /**  A'Cen 재실행이력 조회 */
  List<EgovMap> getAcenRetryHist(AcenRcpMgmtVO searchVO);

  /** A'Cen 재실행 가능여부 판단 데이터 조회 */
  Map<String, String> getAcenProcStatus(String requestKey);

  /** A'Cen 재실행 처리 */
  int updateAcenWithRty(Map<String, String> param);

  /** 신청서 상태 update */
  int updateMcpRequestState(Map<String, String> param);

  /** A'Cen 재실행 이력 insert */
  int insertAcenRtyHist(Map<String, String> param);

}
