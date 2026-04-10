package com.ktmmobile.mcp.order.service;

import java.util.List;

import com.ktmmobile.mcp.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.mcp.order.dto.OrderDto;

/**
 * 주문조회 인터페이스
 * @author key
 * @Date 2021.12.30
 */
public interface OrderService {

	/**
	 * 주문조회 상세 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return OrderDto
	 */
	public OrderDto selectOrderView(OrderDto dto);

	/**
	 * 주문조회 목록 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectOrderList(OrderDto dto);

	/**
	 * 주문조회 목록조회 페이징
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectOrderList(OrderDto dto ,int skipResult,int maxResult);

	/**
	 * 주문조회 목록수 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return int
	 */
	public int selectOrderListCount(OrderDto dto);

	/**
	 * 임시저장 목록 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectOrderTempList(OrderDto dto ,int skipResult,int maxResult);

	/**
	 * 임시저장 목록수 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return int
	 */
	public int selectOrderTempListCount(OrderDto dto);

	/**
	 * 임시저장 목록 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectOrderTempGroupList(OrderDto dto ,int skipResult,int maxResult);

	/**
	 * 임시저장(자급제포함) 목록수 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return int
	 */
	public int selectOrderTempGroupListCount(OrderDto dto);

	/**
	 * 샐프 유심 목록수 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return int
	 */
	public int selectMcpRequestSelfDlvryCount(McpRequestSelfDlvryDto dto);

	/**
	 * 셀프 유심 목록 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<McpRequestSelfDlvryDto>
	 */
	public List<McpRequestSelfDlvryDto> selectMcpRequestSelfDlvry(McpRequestSelfDlvryDto dto,int skipResult, int maxResult);

	/**
	 * 배송(자급제 포함) 목록 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectOrderGroupList(OrderDto dto ,int skipResult,int maxResult);

	/**
	 * 배송(자급제 포함) 목록수 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return int
	 */
	public int selectOrderGroupListCount(OrderDto dto);

	/**
	 * 샐프 유심 상태별 수량 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectMcpRequestSelfDlvryStateCount(McpRequestSelfDlvryDto dto);

	/**
	 * 샐프 유심 퀵배송 상태별 수량 조회
	 * @author key
	 * @Date 2021.12.30
	 * @return List<OrderDto>
	 */
	public List<OrderDto> selectMcpRequestNowDlvryStateCount(McpRequestSelfDlvryDto dto);

    /**
     * 배송주문 상태별 수량 조회
     * @author key
     * @Date 2021.12.30
     * @return List<OrderDto>
     */
    public List<OrderDto> selectRequestOrderStateCount(OrderDto dto);

}
