package com.ktmmobile.mcp.temp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.rate.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.temp.dao.TempDao;

@Service
public class TempSvc {

    private static final Logger logger = LoggerFactory.getLogger(TempSvc.class);

    @Autowired
    private TempDao tempDao;

    public List<Long> selectTempRequestKey() {
        return tempDao.selectTempRequestKey();
    }

    public void deleteNmcpRequestAdditionTempData(long requestKey) {
        tempDao.deleteNmcpRequestAdditionTempData(requestKey);

    }

    public void deleteNmcpRequestAgentTempData(long requestKey) {
        tempDao.deleteNmcpRequestAgentTempData(requestKey);

    }

    public void deleteNmcpRequestApdSaleinfoTempData(long requestKey) {
        tempDao.deleteNmcpRequestApdSaleinfoTempData(requestKey);

    }

    public void deleteNmcpRequestApdTempData(long requestKey) {
        tempDao.deleteNmcpRequestApdTempData(requestKey);

    }

    public void deleteNmcpRequestChangeTempData(long requestKey) {
        tempDao.deleteNmcpRequestChangeTempData(requestKey);

    }

    public void deleteNmcpRequestClauseTempData(long requestKey) {
        tempDao.deleteNmcpRequestClauseTempData(requestKey);

    }

    public void deleteNmcpRequestCstmrTempData(long requestKey) {
        tempDao.deleteNmcpRequestCstmrTempData(requestKey);

    }

    public void deleteNmcpRequestDlvryTempData(long requestKey) {
        tempDao.deleteNmcpRequestDlvryTempData(requestKey);

    }

    public void deleteNmcpRequestDvcChgTempData(long requestKey) {
        tempDao.deleteNmcpRequestDvcChgTempData(requestKey);

    }

    public void deleteNmcpRequestMoveTempData(long requestKey) {
        tempDao.deleteNmcpRequestMoveTempData(requestKey);

    }

    public void deleteNmcpRequestPaymentTempData(long requestKey) {
        tempDao.deleteNmcpRequestPaymentTempData(requestKey);

    }

    public void deleteNmcpRequestReqTempData(long requestKey) {
        tempDao.deleteNmcpRequestReqTempData(requestKey);

    }

    public void deleteNmcpRequestSaleinfoTempData(long requestKey) {
        tempDao.deleteNmcpRequestSaleinfoTempData(requestKey);

    }

    public void deleteNmcpRequestTempData(long requestKey) {
        tempDao.deleteNmcpRequestTempData(requestKey);
    }

    public List<McpIpStatisticDto> getRateResChgList(McpIpStatisticDto ipStatistic) {
        return tempDao.getRateResChgList(ipStatistic);
    }


}
