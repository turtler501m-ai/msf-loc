/**
 *
 */
package com.ktmmobile.msf.form.servicechange.dao;

import java.util.List;

import com.ktmmobile.msf.common.dto.db.MspPpsRcgTesDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface MypagePrepaidDao {
    /**
    * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.
    * @param userid
    * @return McpUserCntrMngDto
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */
    public List<MspPpsRcgTesDto> selectPPSList(String parameter);

}
