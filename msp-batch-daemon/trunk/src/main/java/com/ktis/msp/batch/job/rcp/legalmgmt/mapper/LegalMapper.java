/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.batch.job.rcp.legalmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LegalMapper")
public interface LegalMapper {

	/**
	 * 만19세이상이면서 미성년자인 고객 목록
	 * @return
	 */
	List<Map<String, Object>> getTrgtList();
	/**
	 * MSP_REQUEST_DTL 계약정보 법정대리인 정보 삭제
	 * @return
	 */
	int updateMspRequestDtl(String contractNum );
	/**
	 * MSP_REQUEST_AGENT 가입신청_대리인 법정대리인 정보 삭제
	 * @return
	 */
	int updateMspRequestAgent(String contractNum );

	/**
	 * MCP_REQUEST 계약정보 법정대리인 정보 삭제
	 * @return
	 */
	int updateMcpRequest(String contractNum );
	/**
	 * MCP_REQUEST_AGENT 가입신청_대리인 법정대리인 정보 삭제
	 * @return
	 */
	int updateMcpRequestAgent(String contractNum );

	/**
	 * 만19세이상이면서 미성년자인 사용자 목록
	 * @return
	 */
	List<Map<String, Object>> getTrgtUserList();

	/**
	 * MCP_USER_AGENT 회원정보 법정대리인 정보 삭제
	 * @return
	 */
	int deleteMcpUserAgent(String contractNum );
}
