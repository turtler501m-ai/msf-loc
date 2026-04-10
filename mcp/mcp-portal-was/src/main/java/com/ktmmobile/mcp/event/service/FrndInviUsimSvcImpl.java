package com.ktmmobile.mcp.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.event.dao.FrndInviUsimDao;
import com.ktmmobile.mcp.event.dto.FrndInviUsimDto;

@Service
public class FrndInviUsimSvcImpl implements FrndInviUsimSvc {

	@Autowired
	private FrndInviUsimDao frndInviUsimDao;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

	@Override
	public int chkCstmrInfo(FrndInviUsimDto frndInviUsimDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        int result = restTemplate.postForObject(apiInterfaceServer + "/appform/chkCstmrInfo", frndInviUsimDto, Integer.class);
        //---- API 호출 E ----//
        return result;
	}

	@Override
	public int frndInviUsimReg(FrndInviUsimDto frndInviUsimDto) {
		if ("".equals(frndInviUsimDto.getName())) {
			throw new McpCommonException("추천인 이름은 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getPhone())) {
			throw new McpCommonException("추천인 전화번호는 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getCommendId())) {
			throw new McpCommonException("친구추천ID는 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getFrndName())) {
			throw new McpCommonException("피추천인 성명은 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getFrndPhone())) {
			throw new McpCommonException("피추천인 전화번호는 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getFrndPost())) {
			throw new McpCommonException("피추천인 우편번호는 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getFrndAddr())) {
			throw new McpCommonException("피추천인 주소는 필수항목입니다.");
		}
		if ("".equals(frndInviUsimDto.getFrndAddrDtl())) {
			throw new McpCommonException("피추천인 상세주소는 필수항목입니다.");
		}
		
		int chk = 0;
		chk = frndInviUsimDao.chkDup(frndInviUsimDto);
		if (chk == 0) {
			return frndInviUsimDao.frndInviUsimReg(frndInviUsimDto);
		} else {
			return 0;
		}
	}
}
