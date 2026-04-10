package com.ktmmobile.mcp.cs.mapper;

import com.ktmmobile.mcp.cs.dto.BookingUserDto;
import com.ktmmobile.mcp.msp.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.cs.dto.BookingDateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface CsBookingMapper {

    List<CmnGrpCdMst> selectBookingCd(CmnGrpCdMst cmnGrpCdMst);

    String checkBookingAvailableByDt(String csResDt);

    List<BookingDateDto> getBookingTimeList(BookingDateDto bookingDateDto);

    Map<String, Object> overNum(BookingUserDto bookingUserDto);

    int insertBooking(BookingUserDto bookingUserDto);

    int updateBookingInfo(BookingUserDto bookingUserDto);

    int bookingTotalCount(BookingUserDto bookingUserDto);

    List<BookingUserDto> bookingList(BookingUserDto bookingUserDto, RowBounds rowBounds);


}
