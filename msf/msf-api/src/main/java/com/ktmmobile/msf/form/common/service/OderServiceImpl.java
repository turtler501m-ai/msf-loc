package com.ktmmobile.msf.form.comm.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.form.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.msf.form.comm.dao.OrderDao;
import com.ktmmobile.msf.form.comm.dto.OrderDto;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    private OrderDao dao;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public OrderDto selectOrderView(OrderDto dto) {
    	RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/order/orderView", dto, OrderDto.class);
    }

    @Override
    public List<OrderDto> selectOrderList(OrderDto dto) {
        RestTemplate restTemplate = new RestTemplate();
        OrderDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/order/orderList", dto, OrderDto[].class);
		List<OrderDto> list = Arrays.asList(resultList);
		return list;
    }

    @Override
    public List<OrderDto> selectOrderList(OrderDto dto, int skipResult, int maxResult) {
    	dto.setApiParam1(skipResult);
		dto.setApiParam2(maxResult);
        RestTemplate restTemplate = new RestTemplate();
        OrderDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderList", dto, OrderDto[].class);
		List<OrderDto> list = Arrays.asList(resultList);
		return list;
    }

    @Override
    public int selectOrderListCount(OrderDto dto) {
    	return dao.selectOrderListCount(dto);
    }

    @Override
    public int selectOrderTempListCount(OrderDto dto) {
    	return dao.selectOrderTempListCount(dto);
    }

    @Override
    public List<OrderDto> selectOrderTempList(OrderDto dto ,int skipResult,int maxResult) {
    	return dao.selectOrderTempList(dto, skipResult, maxResult);
    }

    @Override
    public int selectOrderTempGroupListCount(OrderDto dto) {
    	return dao.selectOrderTempGroupListCount(dto);
    }

    @Override
    public List<OrderDto> selectOrderTempGroupList(OrderDto dto, int skipResult, int maxResult) {
    	return dao.selectOrderTempGroupList(dto, skipResult, maxResult);
    }

    @Override
    public int selectMcpRequestSelfDlvryCount(McpRequestSelfDlvryDto dto) {
    	return dao.selectMcpRequestSelfDlvryCount(dto);
    }

    @Override
    public List<McpRequestSelfDlvryDto> selectMcpRequestSelfDlvry(McpRequestSelfDlvryDto dto, int skipResult, int maxResult) {
    	return dao.selectMcpRequestSelfDlvry(dto, skipResult, maxResult);
    }

    @Override
    public int selectOrderGroupListCount(OrderDto dto) {
    	return dao.selectOrderGroupListCount(dto);
    }

    @Override
    public List<OrderDto> selectOrderGroupList(OrderDto dto, int skipResult, int maxResult) {
    	return dao.selectOrderGroupList(dto, skipResult, maxResult);
    }

    @Override
    public List<OrderDto> selectMcpRequestSelfDlvryStateCount(McpRequestSelfDlvryDto dto) {
    	return dao.selectMcpRequestSelfDlvryStateCount(dto);
    }

    @Override
    public List<OrderDto> selectMcpRequestNowDlvryStateCount(McpRequestSelfDlvryDto dto) {
    	return dao.selectMcpRequestNowDlvryStateCount(dto);
    }

    @Override
    public List<OrderDto> selectRequestOrderStateCount(OrderDto dto) {
    	return dao.selectRequestOrderStateCount(dto);
    }

}
