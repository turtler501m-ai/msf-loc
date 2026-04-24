package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.UsimDlvryCanVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper("UsimDlvryCanMapper")
public interface UsimDlvryCanMapper {

    /**유심 구매 고객 이관 및 해지 처리 최대 건수 조회*/
    String getUsimDlvryCanTransferTargetCnt(String cdVal);

    /**택배 유심 구매 고객 이관*/
    List<UsimDlvryCanVO> getUsimDlvryCanTransferList(Map<String, Object> paramMap);
    int insertMcpRequestSelfDlvryHistCan(UsimDlvryCanVO usimDlvryCanVO);
    int updateMcpRequestSelfDlvryHist(UsimDlvryCanVO usimDlvryCanVO);
    int insertMcpRequestSelfDlvryCan(UsimDlvryCanVO usimDlvryCanVO);
    int updateMcpRequestSelfDlvry(UsimDlvryCanVO usimDlvryCanVO);

    /**결제정보 이관*/
    int insertNmcpDaouPayInfoCan(UsimDlvryCanVO usimDlvryCanVO);
    int updateNmcpDaouPayInfo(UsimDlvryCanVO usimDlvryCanVO);

    /**바로배송 유심 구매 고객 이관*/
    List<UsimDlvryCanVO> getUsimDlvryNowCanTransferList(Map<String, Object> paramMap);
    int insertMcpRequestNowDlvryHistCan(UsimDlvryCanVO usimDlvryCanVO);
    int updateMcpRequestNowDlvryHist(UsimDlvryCanVO usimDlvryCanVO);
    int insertMcpRequestNowDlvryCan(UsimDlvryCanVO usimDlvryCanVO);
    int updateMcpRequestNowDlvry(UsimDlvryCanVO usimDlvryCanVO);

    /**택배 유심 구매 고객 삭제*/
    List<UsimDlvryCanVO> getUsimDlvryCanDeleteList(Map<String, Object> paramMap);
    void deleteMcpRequestSelfDlvryHistCan(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMcpRequestSelfDlvryHist(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMcpRequestSelfDlvryCan(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMcpRequestSelfDlvry(UsimDlvryCanVO usimDlvryCanVO);

    /**결제정보 삭제*/
    void deleteNmcpDaouPayInfoCan(UsimDlvryCanVO usimDlvryCanVO);
    void deleteNmcpDaouPayInfo(UsimDlvryCanVO usimDlvryCanVO);

    /**바로배송 유심 구매 고객 삭제*/
    List<UsimDlvryCanVO> getUsimDlvryNowCanDeleteList(Map<String, Object> paramMap);
    void deleteMcpRequestNowDlvryHistCan(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMcpRequestNowDlvryHist(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMcpRequestNowDlvryCan(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMcpRequestNowDlvry(UsimDlvryCanVO usimDlvryCanVO);
    void deleteMspRequestNowDlvryHst(UsimDlvryCanVO usimDlvryCanVO);

}
