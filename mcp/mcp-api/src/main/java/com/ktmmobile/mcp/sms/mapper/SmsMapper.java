package com.ktmmobile.mcp.sms.mapper;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.sms.dto.ApiMapDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsMapper {

    /**
     *
     * @param apiMapDto
     * @return
     * @throws Exception
     */
    int selectMspApplyCount(ApiMapDto apiMapDto);

    /**
     *
     * @param apiMapDto
     * @return
     * @throws Exception
     */
    int insertSms(ApiMapDto apiMapDto);

    /**
     *
     * @param apiMapDto
     * @return
     * @throws Exception
     */
    int insertKakaoNoti(ApiMapDto apiMapDto);

    int selectQstackCount(AuthSmsDto authSmsDto);

    int insertNewSms(ApiMapDto apiMapDto);

    int selectQstackNewCount(AuthSmsDto authSmsDto);

    int insertNewKakaoNoti(ApiMapDto apiMapDto);
}
