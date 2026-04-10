package com.ktmmobile.mcp.cs.controller;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.cs.dto.BookingUserDto;
import com.ktmmobile.mcp.cs.mapper.CsBookingMapper;
import com.ktmmobile.mcp.msp.dto.CmnGrpCdMst;
import com.ktmmobile.mcp.cs.dto.BookingDateDto;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@RestController
public class CsInquiryController {

    private static final Logger logger = LoggerFactory.getLogger(CsInquiryController.class);

    @Autowired
    CsBookingMapper csBookingMapper;

    /**
     * @Description : 예약상담제 공통코드 조회
     * @param cmnGrpCdMst
     * @return List<CmnGrpCdMst>
     */
    @RequestMapping(value="/cs/selectBookingCd", method = RequestMethod.POST)
    public List<CmnGrpCdMst> selectBookingCd(@RequestBody CmnGrpCdMst cmnGrpCdMst){

        List<CmnGrpCdMst> inquiryCategoryList = null;

        try {
            inquiryCategoryList = csBookingMapper.selectBookingCd(cmnGrpCdMst);

        }catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return inquiryCategoryList;
    }

    /**
     * @Description : 특정 예약 일시에 예약가능 인원 수 존재여부 확인
     * @param bookingDateDto
     * @return String
     */
    @RequestMapping(value="/cs/checkBookingAvailableByDt", method = RequestMethod.POST)
    public String checkBookingAvailableByDt(@RequestBody BookingDateDto bookingDateDto){

        String remainYn= "N";  // default 세팅

        try {
            remainYn = csBookingMapper.checkBookingAvailableByDt(bookingDateDto.getCsResDt());
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return remainYn;
    }

    /**
     * @Description : 특정 예약 일시에 해당하는 예약시간대 조회
     * @param bookingDateDto
     * @return List<BookingDateDto>
     */
    @RequestMapping(value= "/cs/getBookingTimeList", method = RequestMethod.POST)
    public List<BookingDateDto> getBookingTimeList(@RequestBody BookingDateDto bookingDateDto) {

        List<BookingDateDto> bookingTimeList = null;

        try {
            bookingTimeList = csBookingMapper.getBookingTimeList(bookingDateDto);

        }catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return bookingTimeList;
    }


    /**
     * @Description : 예약상담 접수하기
     * @param bookingUserDto
     * @return BookingUserDto
     */
    @RequestMapping(value = "/cs/insertBooking", method = RequestMethod.POST)
    public BookingUserDto insertBooking(@RequestBody BookingUserDto bookingUserDto) {

        try {
             int insertResult= csBookingMapper.insertBooking(bookingUserDto);

             if(insertResult <= 0){
                 throw new McpCommonJsonException(COMMON_EXCEPTION);
             }

        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return bookingUserDto;
    }

    /**
     * @Description : 사용자 예약 가능여부 체크
     * @param bookingUserDto
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/cs/overNum", method = RequestMethod.POST)
    public Map<String, Object> overNum(@RequestBody BookingUserDto bookingUserDto) {

        Map<String,Object> result = new HashMap<String,Object>();
        try {
            result = csBookingMapper.overNum(bookingUserDto);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;
    }


    /**
     * @Description : 예약상담 접수 연동 실패로 예약상담 삭제 처리 (DEL_YN 컬럼 Y로 업데이트)
     * @param bookingUserDto
     * @return int
     */
    @RequestMapping(value = "/cs/updateBookingInfo", method = RequestMethod.POST)
    public int updateDelYn(@RequestBody BookingUserDto bookingUserDto) {

        int updateResult;
        try {
             updateResult = csBookingMapper.updateBookingInfo(bookingUserDto);
            if (updateResult <= 0) {
                throw new McpCommonJsonException(COMMON_EXCEPTION);
            }

        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return updateResult;
    }




    //================상담 예약 결과보기================

    @RequestMapping(value = "/cs/bookingTotalCount", method = RequestMethod.POST)
    public int bookingTotalCount(@RequestBody BookingUserDto bookingUserDto) {

        int totalCnt;

        try {
            totalCnt = csBookingMapper.bookingTotalCount(bookingUserDto);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return totalCnt;
    }


    @RequestMapping(value= "/cs/bookingList", method = RequestMethod.POST)
    public List<BookingUserDto> bookingDate(@RequestBody BookingUserDto bookingUserDto) {

        List<BookingUserDto> bookingList = null;

        int skipResult = bookingUserDto.getSkipResult();
        int maxResult = bookingUserDto.getMaxResult();

        try {
            bookingList = csBookingMapper.bookingList(bookingUserDto, new RowBounds(skipResult, maxResult));

        }catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return bookingList;

    }





}
