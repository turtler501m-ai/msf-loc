package com.ktmmobile.msf.domains.form.form.servicechange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

@Service
public class MsfChangPageSvcImpl implements MsfChangPageSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfChangPageSvcImpl.class);

    @Autowired
    @Qualifier("sqlSession2")
    private SqlSessionTemplate sqlSession2;

    // 기존 interface 호출 소스 보관
    // @Value("${api.interface.server}")
    // private String apiInterfaceServer;

    @Override
    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        logger.debug("[MsfChangPage][selectMspAddInfo] start: ncn={}, queryId={}",
            svcCntrNo, "ChangPageApiMapper.selectMspAddInfo");
        try {
            // 기존 interface 호출 소스 보관
            // String callUrl = apiInterfaceServer + "/mypage/mspAddInfo";
            // RestTemplate restTemplate = new RestTemplate();
            // MspJuoAddInfoDto response = restTemplate.postForObject(callUrl, svcCntrNo, MspJuoAddInfoDto.class);
            MspJuoAddInfoDto response = sqlSession2.selectOne("ChangPageApiMapper.selectMspAddInfo", svcCntrNo);
            logger.debug("[MsfChangPage][selectMspAddInfo] response: ncn={}, hasBody={}, remainPay={}, remainMonth={}",
                svcCntrNo, response != null, response != null ? response.getRemainPay() : null, response != null ? response.getRemainMonth() : null);
            return response;
        } catch (Exception e) {
            logger.error("[MsfChangPage][selectMspAddInfo] error: ncn={}, queryId={}",
                svcCntrNo, "ChangPageApiMapper.selectMspAddInfo", e);
            throw e;
        }
    }
}
