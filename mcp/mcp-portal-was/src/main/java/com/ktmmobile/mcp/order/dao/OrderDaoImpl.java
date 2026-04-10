package com.ktmmobile.mcp.order.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.mcp.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.mcp.order.dto.OrderDto;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Override
	public String selectOrderaImg(OrderDto dto) {
		return sqlSessionTemplate.selectOne("OrderMapper.selectOrderaImg",dto);
	}

	@Override
	public String selectOrderaImgMM(OrderDto dto) {
		return sqlSessionTemplate.selectOne("OrderMapper.selectOrderaImgMM",dto);
	}

	@Override
	public int selectOrderListCount(OrderDto dto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderListCount", dto, int.class);
	}


	@Override
	public int selectOrderTempListCount(OrderDto dto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderTempListCount", dto, int.class);
	}

	@Override
	public List<OrderDto> selectOrderTempList(OrderDto dto ,int skipResult,int maxResult) {
		dto.setApiParam1(skipResult);
		dto.setApiParam2(maxResult);
        RestTemplate restTemplate = new RestTemplate();
        OrderDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderTempPageList", dto, OrderDto[].class);
		List<OrderDto> list = Arrays.asList(resultList);
		return list;
	}

	@Override
	public int selectOrderTempGroupListCount(OrderDto dto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderTempGroupListCount", dto, int.class);
	}

	@Override
	public List<OrderDto> selectOrderTempGroupList(OrderDto dto, int skipResult, int maxResult) {
		dto.setApiParam1(skipResult);
		dto.setApiParam2(maxResult);
        RestTemplate restTemplate = new RestTemplate();
        OrderDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderTempGroupList", dto, OrderDto[].class);
		List<OrderDto> list = Arrays.asList(resultList);
		return list;
	}

	@Override
	public int selectMcpRequestSelfDlvryCount(McpRequestSelfDlvryDto dto) {
		Object resultObj = sqlSessionTemplate.selectOne("OrderMapper.selectMcpRequestSelfDlvryCount",dto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "OrderMapper.selectMcpRequestSelfDlvryCount"));
        }
	}

	@Override
	public List<McpRequestSelfDlvryDto> selectMcpRequestSelfDlvry(McpRequestSelfDlvryDto dto, int skipResult, int maxResult) {
    	return sqlSessionTemplate.selectList("OrderMapper.selectMcpRequestSelfDlvry",dto,new RowBounds(skipResult, maxResult));

	}

	@Override
	public int selectOrderGroupListCount(OrderDto dto) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderGroupListCount", dto, int.class);
	}

	@Override
	public List<OrderDto> selectOrderGroupList(OrderDto dto, int skipResult, int maxResult) {
		dto.setApiParam1(skipResult);
		dto.setApiParam2(maxResult);
        RestTemplate restTemplate = new RestTemplate();
        OrderDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/order/selectOrderGroupList", dto, OrderDto[].class);
		List<OrderDto> list = Arrays.asList(resultList);
		return list;
	}

	@Override
	public List<OrderDto> selectMcpRequestSelfDlvryStateCount(McpRequestSelfDlvryDto dto) {
		return sqlSessionTemplate.selectList("OrderMapper.selectMcpRequestSelfDlvryStateCount",dto);
	}

	@Override
	public List<OrderDto> selectMcpRequestNowDlvryStateCount(McpRequestSelfDlvryDto dto) {
		return sqlSessionTemplate.selectList("OrderMapper.selectMcpRequestNowDlvryStateCount",dto);
	}


	@Override
	public List<OrderDto> selectRequestOrderStateCount(OrderDto dto) {
		return sqlSessionTemplate.selectList("OrderMapper.selectRequestOrderStateCount",dto);
	}

}
