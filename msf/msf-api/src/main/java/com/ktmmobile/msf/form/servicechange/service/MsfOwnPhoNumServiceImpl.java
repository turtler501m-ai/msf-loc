/**
 *
 */
package com.ktmmobile.msf.form.servicechange.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.form.servicechange.dto.OwnPhoNumDto;

@Service
public class MsfOwnPhoNumServiceImpl implements MsfOwnPhoNumService{

    private static final Logger logger = LoggerFactory.getLogger(MsfOwnPhoNumServiceImpl.class);

    @Value("${SERVER_NAME}")
    private String serverName;
    
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

	@Override
	public List<OwnPhoNumDto> selectOwnPhoNumList(OwnPhoNumDto ownPhoNumDto) {
        //---- API 호출 S ----//
        Map<String, String> params = new HashMap<String, String>();
        params.put("cstmrName", ownPhoNumDto.getCstmrName());
        params.put("cstmrNativeRrn", ownPhoNumDto.getCstmrNativeRrn01() + ownPhoNumDto.getCstmrNativeRrn02());

        RestTemplate restTemplate = new RestTemplate();
        OwnPhoNumDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/cs/selectOwnPhoNumList", params, OwnPhoNumDto[].class);

        List<OwnPhoNumDto> list = null;
        if(Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
        }
        //---- API 호출 E ----//
        return list;
	}

	@Override
	public int insertOwnPhoNumChkHst(OwnPhoNumDto ownPhoNumDto) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("cstmrName", ownPhoNumDto.getCstmrName());
        params.put("cstmrNativeRrn", ownPhoNumDto.getCstmrNativeRrn01() + ownPhoNumDto.getCstmrNativeRrn02());
        params.put("onlineAuthType", ownPhoNumDto.getOnlineAuthType());
        params.put("onlineAuthInfo", ownPhoNumDto.getOnlineAuthInfo());
        
        RestTemplate restTemplate = new RestTemplate();

        int resultCnt = restTemplate.postForObject(apiInterfaceServer + "/cs/insertOwnPhoNumChkHst", params, Integer.class);

        return resultCnt;
	}

}
