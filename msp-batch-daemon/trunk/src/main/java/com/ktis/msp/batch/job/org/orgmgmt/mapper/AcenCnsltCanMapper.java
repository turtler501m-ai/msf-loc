package com.ktis.msp.batch.job.org.orgmgmt.mapper;

import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenCnsltMstCanVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;


@Mapper("AcenCnsltCanMapper")
public interface AcenCnsltCanMapper {

    /**A'Cen 일배치 상담이력 이관 주기 조회*/
    String getAcenCnsltTransferDate(String cdVal);

    /**A'Cen 일배치 상담이력 이관 대상 조회*/
    List<Long> getAcenCnsltMstList(Integer stdrDay);

    /**A'Cen 일배치 상담이력 이관 대상 단건 조회*/
    AcenCnsltMstCanVO getAcenCnsltMst(Long cnsltSeq);

    /**A'Cen 일배치 상담이력 NMCP_ACEN_CNSLT_MST 이관*/
    void insertAcenCnsltMstCan(AcenCnsltMstCanVO acenCnsltMstCanVO);
    void updateAcenCnsltMst(Long cnsltSeq);

    /**A'Cen 일배치 상담이력 NMCP_ACEN_CNSLT_DTL 이관*/
    void insertAcenCnsltDtl(Long cnsltSeq);
    void updateAcenCnsltDtl(Long cnsltSeq);

    /**A'Cen 일배치 상담이력 삭제 대상 조회*/
    List<Long> getAcenCnsltMstCanList(Integer stdrDay);

    /**A'Cen 일배치 상담이력 NMCP_ACEN_CNSLT_MST@DL_CAN 삭제*/
    void deleteAcenCnsltMstCan(Long cnsltSeq);
    void deleteAcenCnsltMstMcp(Long cnsltSeq);

    /**A'Cen 일배치 상담이력 NMCP_ACEN_CNSLT_DTL@DL_CAN 삭제*/
    void deleteAcenCnsltDtlCan(Long cnsltSeq);
    void deleteAcenCnsltDtlMcp(Long cnsltSeq);

}
