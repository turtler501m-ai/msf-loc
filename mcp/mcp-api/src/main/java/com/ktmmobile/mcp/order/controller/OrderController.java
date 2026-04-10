package com.ktmmobile.mcp.order.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.msp.controller.MspController;
import com.ktmmobile.mcp.order.dto.OrderDto;
import com.ktmmobile.mcp.order.mapper.OrderMapper;

@RestController
public class OrderController {

	 private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderMapper orderMapper;

	/**
	 * 신청서 신청 리스트 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/orderList", method = RequestMethod.POST)
	public List<OrderDto> orderList(@RequestBody OrderDto orderDto) {

		List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderList(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}

	/**
	 * 셀프개통 신청서 신청 리스트 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/orderSelfList", method = RequestMethod.POST)
	public List<OrderDto> orderSelfList(@RequestBody OrderDto orderDto) {

		List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderSelfList(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}

	/**
	 * 신청서 상세 정보 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/orderView", method = RequestMethod.POST)
	public OrderDto orderView(@RequestBody OrderDto orderDto) {

		OrderDto orderView = null;

		try {
			orderView = orderMapper.selectOrderView(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderView;
	}

	/**
	 * 신청서 상세 정보 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/orderApdView", method = RequestMethod.POST)
	public OrderDto orderApdView(@RequestBody OrderDto orderDto) {

		OrderDto orderView = null;

		try {
			orderView = orderMapper.selectOrderApdView(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderView;
	}

	/**
	 * 신청서 신청 리스트 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderList", method = RequestMethod.POST)
	public List<OrderDto> selectOrderList(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		int skipResult = orderDto.getApiParam1();
	    int maxResult = orderDto.getApiParam2();
		List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderList(orderDto, new RowBounds(skipResult, maxResult));

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}

	/**
	 * 신청서 신청 갯수 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderListCount", method = RequestMethod.POST)
	public int selectOrderListCount(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		int result = 0;

		try {
			result = orderMapper.selectOrderListCount(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

	/**
	 * 서식지 임시저장 리스트 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderTempList", method = RequestMethod.POST)
	public List<OrderDto> selectOrderTempList(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

        List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderTempList(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}

	/**
	 * 서식지 임시저장 리스트 추가 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderTempPageList", method = RequestMethod.POST)
	public List<OrderDto> selectOrderTempPageList(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		int skipResult = orderDto.getApiParam1();
	    int maxResult = orderDto.getApiParam2();
		List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderTempList(orderDto, new RowBounds(skipResult, maxResult));

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}

	/**
	 * 서식지 임시저장 갯수 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderTempListCount", method = RequestMethod.POST)
	public int selectOrderTempListCount(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		int result = 0;

		try {
			result = orderMapper.selectOrderTempListCount(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

	/**
	 * 서식지 임시저장 갯수 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderTempGroupListCount", method = RequestMethod.POST)
	public int selectOrderTempGroupListCount(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		int result = 0;

		try {
			result = orderMapper.selectOrderTempGroupListCount(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

	/**
	 * 서식지 임시저장 리스트 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderTempGroupList", method = RequestMethod.POST)
	public List<OrderDto> selectOrderTempGroupList(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderTempGroupList(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}

	/**
	 * 서식지 등록 갯수 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderGroupListCount", method = RequestMethod.POST)
	public int selectOrderGroupListCount(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		int result = 0;

		try {
			result = orderMapper.selectOrderGroupListCount(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

	/**
	 * 서식지 등록 리스트 조회
	 * @param orderDto
	 * @return
	 */
	@RequestMapping(value = "/order/selectOrderGroupList", method = RequestMethod.POST)
	public List<OrderDto> selectOrderGroupList(@RequestBody OrderDto orderDto) {

		ObjectMapper om = new ObjectMapper();

		List<OrderDto> orderList = null;

		try {
			orderList = orderMapper.selectOrderGroupList(orderDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return orderList;
	}


}
