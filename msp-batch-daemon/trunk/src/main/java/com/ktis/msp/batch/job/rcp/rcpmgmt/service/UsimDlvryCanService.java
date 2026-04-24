package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.UsimDlvryCanMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.UsimDlvryCanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsimDlvryCanService extends BaseService {

    @Autowired
    private UsimDlvryCanMapper usimDlvryCanMapper;

    private final static int USIM_TRANSFER_DT = 180;    // 이관 기준일자
    private final static int USIM_DELETE_DT = 1825;     // 삭제 기준일자

    /**
     * 유심 구매 고객 정보 해지 이관
     * @return Map<String, Integer>
     * @throws MvnoServiceException
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Integer> procUsimDlvryCanTransfer() throws MvnoServiceException {

        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        int usimDlvryCnt = 0;           // 택배 처리 건수
        int usimDlvryNowCnt = 0;        // 배로배송 처리 건수

        LOGGER.info("유심 구매 고객 정보 해지 이관 START");

        // 이관 최대 건수 조회 (공통코드: DEV0003)
        String targetCnt = usimDlvryCanMapper.getUsimDlvryCanTransferTargetCnt("USIM_TRANS_CNT");
        if(targetCnt == null || "".equals(targetCnt)){
            LOGGER.info("유심 구매 고객 정보 해지 이관 END");
            resultMap.put("usimDlvryCnt", usimDlvryCnt);
            resultMap.put("usimDlvryNowCnt", usimDlvryNowCnt);
            return resultMap;
        }

        paramMap.put("stdrDay", USIM_TRANSFER_DT);
        paramMap.put("targetCnt", Integer.parseInt(targetCnt));

        try{

            LOGGER.info("[택배] 유심 구매 고객 정보 해지 이관 START");
            List<UsimDlvryCanVO> usimDlvryList = usimDlvryCanMapper.getUsimDlvryCanTransferList(paramMap);

            for (UsimDlvryCanVO usimDlvryCanVO : usimDlvryList) {

                // 택배구매 시도 이력 이관(MCP_REQUEST_SELF_DLVRY_HIST)
                usimDlvryCanMapper.insertMcpRequestSelfDlvryHistCan(usimDlvryCanVO);
                usimDlvryCanMapper.updateMcpRequestSelfDlvryHist(usimDlvryCanVO);

                if("N".equals(usimDlvryCanVO.getHistYn())){

                    // 택배구매 이력 이관(MCP_REQUEST_SELF_DLVRY)
                    usimDlvryCanMapper.insertMcpRequestSelfDlvryCan(usimDlvryCanVO);
                    usimDlvryCanMapper.updateMcpRequestSelfDlvry(usimDlvryCanVO);

                    // 결제정보 이관(NMCP_DAOUPAY_INFO)
                    usimDlvryCanMapper.insertNmcpDaouPayInfoCan(usimDlvryCanVO);
                    usimDlvryCanMapper.updateNmcpDaouPayInfo(usimDlvryCanVO);
                }

                usimDlvryCnt++;
            }

            LOGGER.info("[바로배송] 유심 구매 고객 정보 해지 이관 START");
            List<UsimDlvryCanVO> usimDlvryNowList = usimDlvryCanMapper.getUsimDlvryNowCanTransferList(paramMap);

            for (UsimDlvryCanVO usimDlvryCanVO : usimDlvryNowList) {

                //바로배송 구매 시도 이력 이관(MCP_REQUEST_NOW_DLVRY_HIST)
                usimDlvryCanMapper.insertMcpRequestNowDlvryHistCan(usimDlvryCanVO);
                usimDlvryCanMapper.updateMcpRequestNowDlvryHist(usimDlvryCanVO);

                if("N".equals(usimDlvryCanVO.getHistYn())) {

                    //바로배송 구매 이력 이관(MCP_REQUEST_NOW_DLVRY)
                    usimDlvryCanMapper.insertMcpRequestNowDlvryCan(usimDlvryCanVO);
                    usimDlvryCanMapper.updateMcpRequestNowDlvry(usimDlvryCanVO);

                    // 결제정보 이관(NMCP_DAOUPAY_INFO)
                    usimDlvryCanMapper.insertNmcpDaouPayInfoCan(usimDlvryCanVO);
                    usimDlvryCanMapper.updateNmcpDaouPayInfo(usimDlvryCanVO);
                }

                usimDlvryNowCnt++;
            }

            resultMap.put("usimDlvryCnt", usimDlvryCnt);
            resultMap.put("usimDlvryNowCnt", usimDlvryNowCnt);

        }catch(Exception e){
            throw new MvnoServiceException("유심 구매 고객 정보 해지 이관처리 에러", e);
        }

        LOGGER.info("유심 구매 고객 정보 해지 이관 END");
        return resultMap;
    }


    /**
     * 유심 구매 고객 정보 삭제
     * @return
     * @throws MvnoServiceException
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Integer> procUsimDlvryCanDelete() throws MvnoServiceException {

        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        int usimDlvryCnt = 0;           // 택배 처리 건수
        int usimDlvryNowCnt = 0;        // 배로배송 처리 건수

        LOGGER.info("유심 구매 고객 정보 삭제 START");

        // 삭제 최대 건수 조회 (공통코드: DEV0003)
        String targetCnt = usimDlvryCanMapper.getUsimDlvryCanTransferTargetCnt("USIM_DELETE_CNT");
        if(targetCnt == null || "".equals(targetCnt)){
            LOGGER.info("유심 구매 고객 정보 삭제 END");
            resultMap.put("usimDlvryCnt", usimDlvryCnt);
            resultMap.put("usimDlvryNowCnt", usimDlvryNowCnt);
            return resultMap;
        }

        paramMap.put("stdrDay", USIM_DELETE_DT);
        paramMap.put("targetCnt", Integer.parseInt(targetCnt));

        try{

            LOGGER.info("[택배] 유심 구매 고객 정보 삭제 START");
            List<UsimDlvryCanVO> usimDlvryList = usimDlvryCanMapper.getUsimDlvryCanDeleteList(paramMap);

            for (UsimDlvryCanVO usimDlvryCanVO : usimDlvryList) {

                // 택배구매 시도 이력 삭제(MCP_REQUEST_SELF_DLVRY_HIST)
                usimDlvryCanMapper.deleteMcpRequestSelfDlvryHistCan(usimDlvryCanVO);
                usimDlvryCanMapper.deleteMcpRequestSelfDlvryHist(usimDlvryCanVO);

                if("N".equals(usimDlvryCanVO.getHistYn())){

                    // 택배구매 이력 삭제(MCP_REQUEST_SELF_DLVRY)
                    usimDlvryCanMapper.deleteMcpRequestSelfDlvryCan(usimDlvryCanVO);
                    usimDlvryCanMapper.deleteMcpRequestSelfDlvry(usimDlvryCanVO);

                    // 결제정보 삭제(NMCP_DAOUPAY_INFO)
                    usimDlvryCanMapper.deleteNmcpDaouPayInfoCan(usimDlvryCanVO);
                    usimDlvryCanMapper.deleteNmcpDaouPayInfo(usimDlvryCanVO);
                }

                usimDlvryCnt++;
            }

            LOGGER.info("[바로배송] 유심 구매 고객 정보 삭제 START");
            List<UsimDlvryCanVO> usimDlvryNowList = usimDlvryCanMapper.getUsimDlvryNowCanDeleteList(paramMap);

            for (UsimDlvryCanVO usimDlvryCanVO : usimDlvryNowList) {

                // 바로배송 구매 시도 이력 삭제(MCP_REQUEST_NOW_DLVRY_HIST)
                usimDlvryCanMapper.deleteMcpRequestNowDlvryHistCan(usimDlvryCanVO);
                usimDlvryCanMapper.deleteMcpRequestNowDlvryHist(usimDlvryCanVO);

                if("N".equals(usimDlvryCanVO.getHistYn())){

                    // 바로배송 구매 이력 삭제(MCP_REQUEST_NOW_DLVRY)
                    usimDlvryCanMapper.deleteMcpRequestNowDlvryCan(usimDlvryCanVO);
                    usimDlvryCanMapper.deleteMcpRequestNowDlvry(usimDlvryCanVO);

                    // 결제정보 삭제(NMCP_DAOUPAY_INFO)
                    usimDlvryCanMapper.deleteNmcpDaouPayInfoCan(usimDlvryCanVO);
                    usimDlvryCanMapper.deleteNmcpDaouPayInfo(usimDlvryCanVO);

                    // 바로배송 상담이력 삭제(MSP_REQUEST_NOW_DLVRY_HST)
                    usimDlvryCanMapper.deleteMspRequestNowDlvryHst(usimDlvryCanVO);
                }

                usimDlvryNowCnt++;
            }

            resultMap.put("usimDlvryCnt", usimDlvryCnt);
            resultMap.put("usimDlvryNowCnt", usimDlvryNowCnt);

        }catch(Exception e){
            throw new MvnoServiceException("유심 구매 고객 정보 삭제처리 에러", e);
        }

        LOGGER.info("유심 구매 고객 정보 삭제 END");
        return resultMap;
    }
}
