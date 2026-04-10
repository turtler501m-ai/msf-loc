package com.ktmmobile.mcp.mcash.dao;

import com.ktmmobile.mcp.mcash.dto.McashUserDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
public class McashDaoImpl implements McashDao {

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public McashUserDto getMcashUserByUserid(String userid) {
        return sqlSessionTemplate.selectOne("McashMapper.getMcashUserByUserid", userid);
    }

    @Override
    public McashUserDto getMcashUserByCustomerId(String customerId) {
        return sqlSessionTemplate.selectOne("McashMapper.getMcashUserByCustomerId", customerId);
    }

    @Override
    public McashUserDto getMcashUserBySvcCntrNo(McashUserDto mcashDto) {
        return sqlSessionTemplate.selectOne("McashMapper.getMcashUserBySvcCntrNo", mcashDto);
    }

    @Override
    public McashUserDto getUserInfoBySvcCntrNo(String svcCntrNo) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mcash/getUserInfoBySvcCntrNo", svcCntrNo, McashUserDto.class);
    }

    @Override
    public List<McashUserDto> getMcashAvailableCntrList(String userid) {
        RestTemplate restTemplate = new RestTemplate();

        McashUserDto[] mcashUserList = restTemplate.postForObject(apiInterfaceServer + "/mcash/getMcashAvailableCntrList", userid, McashUserDto[].class);
        List<McashUserDto> mcashAvailableCntrList = Arrays.asList(mcashUserList);
        return mcashAvailableCntrList;
    }

    @Override
    public McashUserDto getMcashJoinInfo(String userid) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/mcash/getMcashJoinInfo", userid, McashUserDto.class);
    }

    @Override
    public boolean insertMcashUserInfo(McashUserDto mcashUserDto) {
        return 0 < sqlSessionTemplate.insert("McashMapper.insertMcashUserInfo", mcashUserDto);
    }

    @Override
    public boolean updateMcashUserInfo(McashUserDto mcashUserDto) {
        return 0 < sqlSessionTemplate.update("McashMapper.updateMcashUserInfo", mcashUserDto);
    }

    @Override
    public boolean insertMcashUserHist(McashUserDto mcashUserDto) {
        return 0 < sqlSessionTemplate.insert("McashMapper.insertMcashUserHist", mcashUserDto);
    }

    @Override
    public int getMcashJoinCnt(String userid) {
        Object resultObj = sqlSessionTemplate.selectOne("McashMapper.getMcashJoinCnt", userid);
        if (resultObj instanceof Number) {
            Number number = (Number) resultObj;
            return number.intValue();
        } else {
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "CommonMapper.checkCrawlingCount"));
        }
    }

    @Override
    public int getMcashMenuAccessCnt() {
        Object resultObj = sqlSessionTemplate.selectOne("McashMapper.getMcashMenuAccessCnt");
        if (resultObj instanceof Number) {
            Number number = (Number) resultObj;
            return number.intValue();
        } else {
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), "CommonMapper.checkCrawlingCount"));
        }
    }
}