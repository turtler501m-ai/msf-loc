package com.ktmmobile.mcp.app.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppMapper {

	/**
	 * 사용자 요금제 정보를 가지고 온다.
	 * @param contractNum
	 * @return
	 * @throws Exception
	 */
	Map<String, String> selectUsrRateInfo(String contractNum);

	void pAppSms(HashMap<String, Object> paramMap);

	void pAppUserInfo(HashMap<String, Object> paramMap);

	void pAppChgVac(HashMap<String, Object> paramMap);

	void pAppPpReq(HashMap<String, Object> paramMap);

	void pAppPpRes(HashMap<String, Object> paramMap);

	void pAppPpRst(HashMap<String, Object> paramMap);

}
