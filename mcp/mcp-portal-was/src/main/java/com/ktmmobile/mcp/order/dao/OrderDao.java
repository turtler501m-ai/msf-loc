package com.ktmmobile.mcp.order.dao;

import java.util.List;

import com.ktmmobile.mcp.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.mcp.order.dto.OrderDto;

public interface OrderDao {

	public String selectOrderaImg(OrderDto dto);

	public String selectOrderaImgMM(OrderDto dto);

	public int selectOrderListCount(OrderDto dto);

    public int selectOrderTempListCount(OrderDto dto);

    public List<OrderDto> selectOrderTempList(OrderDto dto ,int skipResult,int maxResult);

    public int selectOrderTempGroupListCount(OrderDto dto);

    public List<OrderDto> selectOrderTempGroupList(OrderDto dto ,int skipResult,int maxResult);

    public int selectMcpRequestSelfDlvryCount(McpRequestSelfDlvryDto dto);

    public List<McpRequestSelfDlvryDto> selectMcpRequestSelfDlvry(McpRequestSelfDlvryDto dto,int skipResult, int maxResult);

    public int selectOrderGroupListCount(OrderDto dto);

    public List<OrderDto> selectOrderGroupList(OrderDto dto ,int skipResult,int maxResult);

    public List<OrderDto> selectMcpRequestSelfDlvryStateCount(McpRequestSelfDlvryDto dto);

    public List<OrderDto> selectMcpRequestNowDlvryStateCount(McpRequestSelfDlvryDto dto);

    public List<OrderDto> selectRequestOrderStateCount(OrderDto dto);

}
