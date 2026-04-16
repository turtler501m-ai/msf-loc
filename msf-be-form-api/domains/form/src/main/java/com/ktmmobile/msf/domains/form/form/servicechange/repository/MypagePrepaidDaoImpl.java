/**
 *
 */
package com.ktmmobile.msf.domains.form.form.servicechange.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.common.dto.db.MspPpsRcgTesDto;

/**
 * @author ANT_FX700_02
 *
 */
@Repository
public class MypagePrepaidDaoImpl implements MypagePrepaidDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.
     * @param userid
     * @return McpUserCntrMngDto
     * @Author : ant
     * @Create Date : 2016. 1. 12.
     */
    @Override
    public List<MspPpsRcgTesDto> selectPPSList(String parameter) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("Mypage.selectPPSList", parameter);
    }

}
