package com.ktis.msp.batch.job.org.orgmgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.mapper.AcenCnsltCanMapper;
import com.ktis.msp.batch.job.org.orgmgmt.vo.AcenCnsltMstCanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcenCnsltCanService extends BaseService {

    @Autowired
    private AcenCnsltCanMapper acenCnsltCanMapper;

    /**
     * A'Cen 일배치 상담이력 고객 민감정보 이관
     * @return
     * @throws MvnoServiceException
     */
    @Transactional(rollbackFor=Exception.class)
    public int procAcenCnsltTransfer() throws MvnoServiceException {

        int procCnt = 0;

        LOGGER.info("A'Cen 일배치 상담이력 이관 START");

        // 해지이관 기준일자 조회
        String stdrDay = acenCnsltCanMapper.getAcenCnsltTransferDate("ACEN_TRANS_DT");
        if(stdrDay == null || "".equals(stdrDay)){
          LOGGER.info("A'Cen 일배치 상담이력 이관 END");
          return procCnt;
        }

        // A'Cen 일배치 상담이력 이관대상 조회(MST)
        List<Long> cnsltSeqList = acenCnsltCanMapper.getAcenCnsltMstList(Integer.parseInt(stdrDay));

        try{

          for(Long cnsltSeq : cnsltSeqList){
            LOGGER.info("A'Cen 일배치 상담이력 이관 현황 === " + cnsltSeqList.size() + " / " + procCnt);
            LOGGER.info("상담순번=" + cnsltSeq);

            // NMCP_ACEN_CNSLT_MST 이관
            AcenCnsltMstCanVO acenCnsltMstCanVo = acenCnsltCanMapper.getAcenCnsltMst(cnsltSeq);
            acenCnsltCanMapper.insertAcenCnsltMstCan(acenCnsltMstCanVo);
            acenCnsltCanMapper.updateAcenCnsltMst(cnsltSeq);

            // NMCP_ACEN_CNSLT_DTL 이관
            acenCnsltCanMapper.insertAcenCnsltDtl(cnsltSeq);
            acenCnsltCanMapper.updateAcenCnsltDtl(cnsltSeq);

            procCnt++;
          }
        }catch(Exception e){
          throw new MvnoServiceException("상담이력(SR) 이관처리 에러", e);
        }

        LOGGER.info("A'Cen 일배치 상담이력 이관 END");
        return procCnt;
    }

    /**
     * A'Cen 일배치 상담이력 고객 민감정보 삭제
     * @return
     * @throws MvnoServiceException
     */
    @Transactional(rollbackFor=Exception.class)
    public int procAcenCnsltDelete() throws MvnoServiceException {

        int procCnt = 0;

        LOGGER.info("A'Cen 일배치 상담이력 삭제 START");

        // 삭제 기준일자 조회
        String stdrDay = acenCnsltCanMapper.getAcenCnsltTransferDate("ACEN_DELETE_DT");
        if(stdrDay == null || "".equals(stdrDay)){
          LOGGER.info("A'Cen 일배치 상담이력 삭제 END");
          return procCnt;
        }

        // A'Cen 일배치 상담이력 삭제대상 조회(MST)
        List<Long> cnsltSeqList = acenCnsltCanMapper.getAcenCnsltMstCanList(Integer.parseInt(stdrDay));

        try{
            for(Long cnsltSeq : cnsltSeqList){

              LOGGER.info("A'Cen 일배치 상담이력 삭제 현황 === " + cnsltSeqList.size() + " / " + procCnt);
              LOGGER.info("상담순번=" + cnsltSeq);

              // NMCP_ACEN_CNSLT_MST@DL_CAN 삭제
              acenCnsltCanMapper.deleteAcenCnsltMstCan(cnsltSeq);
              acenCnsltCanMapper.deleteAcenCnsltMstMcp(cnsltSeq);

              // NMCP_ACEN_CNSLT_DTL@DL_CAN 삭제
              acenCnsltCanMapper.deleteAcenCnsltDtlCan(cnsltSeq);
              acenCnsltCanMapper.deleteAcenCnsltDtlMcp(cnsltSeq);

              procCnt++;
            }
        }catch(Exception e){
          throw new MvnoServiceException("상담이력(SR) 삭제처리 에러", e);
        }

        LOGGER.info("A'Cen 일배치 상담이력 삭제 END");
        return procCnt;
    }
}
