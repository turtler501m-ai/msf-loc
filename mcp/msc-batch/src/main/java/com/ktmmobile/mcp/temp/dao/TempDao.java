package com.ktmmobile.mcp.temp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.rate.dto.McpIpStatisticDto;


@Repository
public class TempDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;

    @Autowired
    @Qualifier(value="booteventSqlSession")
    private SqlSession booteventSqlSession;


    public List<Long> selectTempRequestKey() {
        return bootoraSqlSession.selectList("TempMapper.selectTempRequestKey");
    }


    public void deleteNmcpRequestAdditionTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestAdditionTempData", requestKey);

    }


    public void deleteNmcpRequestAgentTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestAgentTempData", requestKey);

    }


    public void deleteNmcpRequestApdSaleinfoTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestApdSaleinfoTempData", requestKey);

    }


    public void deleteNmcpRequestApdTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestApdTempData", requestKey);

    }


    public void deleteNmcpRequestChangeTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestChangeTempData", requestKey);

    }


    public void deleteNmcpRequestClauseTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestClauseTempData", requestKey);

    }


    public void deleteNmcpRequestCstmrTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestCstmrTempData", requestKey);

    }


    public void deleteNmcpRequestDlvryTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestDlvryTempData", requestKey);

    }


    public void deleteNmcpRequestDvcChgTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestDvcChgTempData", requestKey);

    }


    public void deleteNmcpRequestMoveTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestMoveTempData", requestKey);

    }


    public void deleteNmcpRequestPaymentTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestPaymentTempData", requestKey);

    }


    public void deleteNmcpRequestReqTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestReqTempData", requestKey);

    }


    public void deleteNmcpRequestSaleinfoTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestSaleinfoTempData", requestKey);

    }


    public void deleteNmcpRequestTempData(long requestKey) {
        bootoraSqlSession.delete("TempMapper.deleteNmcpRequestTempData", requestKey);

    }

    public List<McpIpStatisticDto> getRateResChgList(McpIpStatisticDto ipStatistic)  {
        return bootoraSqlSession.selectList("TempMapper.getRateResChgList", ipStatistic);
    }

}
