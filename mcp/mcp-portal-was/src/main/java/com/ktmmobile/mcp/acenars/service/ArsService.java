package com.ktmmobile.mcp.acenars.service;

import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.cs.dto.ArsQnaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@Service
public class ArsService {

	@Value("${acen.req.url}")
	private String acenReqUrl;

	public boolean arsServiceCall(ArsQnaDto arsQnaDto) {

		boolean result = true;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("ifCode", arsQnaDto.getIfCode());
		param.add("qnaSeq", arsQnaDto.getQnaSeq());
		param.add("qnaType", arsQnaDto.getQnaType());
		param.add("userNm", EncryptUtil.ace256Enc(arsQnaDto.getUserNm()));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);

		try{

			Map<String, String> response = restTemplate.postForObject(acenReqUrl + "/aiccApp/custReqArsServiceCall.do", request, Map.class);
			if(response == null || "0".equals(response.get("returncode"))){
				throw new McpCommonJsonException(COMMON_EXCEPTION);
			}

		}catch(McpCommonJsonException e){
			result = false;
		}catch (Exception e){
			result = false;
		}

		return result;
	}

}
