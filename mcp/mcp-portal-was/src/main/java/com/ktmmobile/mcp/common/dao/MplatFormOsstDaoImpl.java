package com.ktmmobile.mcp.common.dao;

import com.ktmmobile.mcp.common.mplatform.vo.MpErrVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class MplatFormOsstDaoImpl implements MplatFormOsstDao {

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public void insertOsstErrLog(MpErrVO mpErrVO) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(apiInterfaceServer + "/mPlatform/insertOsstErrLog", mpErrVO, Void.class);
    }

}
