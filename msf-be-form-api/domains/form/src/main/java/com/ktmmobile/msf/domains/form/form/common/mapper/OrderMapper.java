package com.ktmmobile.msf.domains.form.form.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ktmmobile.msf.domains.form.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.msf.domains.form.form.common.dto.OrderDto;

@Mapper
public interface OrderMapper {

	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderList(OrderDto orderDto);

	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderSelfList(OrderDto orderDto);

	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderList(OrderDto orderDto, RowBounds rowBounds);


	/**
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	int selectOrderListCount(OrderDto orderDto);



	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	OrderDto selectOrderView(OrderDto orderDto);

	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	OrderDto selectOrderApdView(OrderDto orderDto);

	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderTempList(OrderDto orderDto);

	/**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderTempList(OrderDto orderDto, RowBounds rowBounds);

    int selectOrderTempListCount(OrderDto orderDto);

    /**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderTempGroupList(OrderDto orderDto);

    int selectOrderTempGroupListCount(OrderDto orderDto);

    /**
	 *
	 * @param orderDto
	 * @return
	 * @throws Exception
	 */
	List<OrderDto> selectOrderGroupList(OrderDto orderDto);

    int selectOrderGroupListCount(OrderDto orderDto);

}
