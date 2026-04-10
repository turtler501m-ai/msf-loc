package com.ktmmobile.mcp.alarm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.alarm.dto.AlarmDto;
import com.ktmmobile.mcp.alarm.mapper.AlarmMapper;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;

@Service
public class AlarmSvc {

    private static final Logger logger = LoggerFactory.getLogger(AlarmSvc.class);

    @Autowired
    private AlarmMapper alarmMapper;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    public List<AlarmDto> selectNotiInfo(){
        return alarmMapper.selectNotiInfo();
    }

    public void updateNotiInfo(AlarmDto dto) {
        alarmMapper.updateNotiInfo(dto);
    };

    public void deleteNotiInfo() {
        alarmMapper.deleteNotiInfo();
    };

    public MspSmsTemplateMstDto getMspSmsTemplateMst(int templateId){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Integer> params = new LinkedMultiValueMap<String, Integer>();
        params.add("templateId", templateId);
        return restTemplate.postForObject(apiInterfaceServer + "/common/mspSmsTemplateMst", templateId, MspSmsTemplateMstDto.class);
    }

    public int sendKakaoNoti(String subject,String rMobile, String message,String pCallCenter, String kTemplateCd, String senderKey, String reserved02){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
        params.add("I_MSG_TYPE","6");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);
        params.add("I_MSG",message);
        params.add("I_NEXT_TYPE", "2");
        params.add("I_TEMPLATE_CD", kTemplateCd);
        params.add("I_SENDER_KEY", senderKey);
        params.add("RESERVED02", reserved02);
        params.add("RESERVED03", "alarmBatch");

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(apiInterfaceServer + "/sms/addNewKakaoNoti", params, Integer.class);
    }

}
