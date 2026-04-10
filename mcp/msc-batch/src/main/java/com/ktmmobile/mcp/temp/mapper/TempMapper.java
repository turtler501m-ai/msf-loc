package com.ktmmobile.mcp.temp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.rate.dto.McpIpStatisticDto;

@Mapper
public interface TempMapper {

    List<Long> selectTempRequestKey();

    void deleteNmcpRequestAdditionTempData(long requestKey);

    void deleteNmcpRequestAgentTempData(long requestKey);

    void deleteNmcpRequestApdSaleinfoTempData(long requestKey);

    void deleteNmcpRequestApdTempData(long requestKey);

    void deleteNmcpRequestChangeTempData(long requestKey);

    void deleteNmcpRequestClauseTempData(long requestKey);

    void deleteNmcpRequestCstmrTempData(long requestKey);

    void deleteNmcpRequestDlvryTempData(long requestKey);

    void deleteNmcpRequestDvcChgTempData(long requestKey);

    void deleteNmcpRequestMoveTempData(long requestKey);

    void deleteNmcpRequestPaymentTempData(long requestKey);

    void deleteNmcpRequestReqTempData(long requestKey);

    void deleteNmcpRequestSaleinfoTempData(long requestKey);

    void deleteNmcpRequestTempData(long requestKey);

    /**
     * <pre>
     * 설명     : 요금제 예약번호 리스트 , 배치로 부가서비스 해지 등록 처리
     * @param McpIpStatisticDto
     * @return
     * @return: List<McpIpStatisticDto>
     * </pre>
     */
    public List<McpIpStatisticDto> getRateResChgList(McpIpStatisticDto ipStatistic) ;

}
