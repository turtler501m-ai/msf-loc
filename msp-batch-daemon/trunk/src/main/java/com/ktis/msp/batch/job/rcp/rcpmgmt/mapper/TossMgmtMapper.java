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
package com.ktis.msp.batch.job.rcp.rcpmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpDetailVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.ScanVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("TossMgmtMapper")
public interface TossMgmtMapper {
	
	/**
	 * 신청정보 미생성 Toss 개통정보 조회
	 */
	List<RcpDetailVO> selectNonTossRcpList();
	
	public RcpDetailVO maxRequestkey();
	
	int insertRcpRequest(RcpDetailVO rcpDetailVO);
	int insertRcpCustomer(RcpDetailVO rcpDetailVO);
	int insertRcpSale(RcpDetailVO rcpDetailVO);
	int insertRcpDelivery(RcpDetailVO rcpDetailVO);
	int insertRcpReq(RcpDetailVO rcpDetailVO);
	int insertRcpMove(RcpDetailVO rcpDetailVO);
	int insertRcpPay(RcpDetailVO rcpDetailVO);
	int insertRcpAgent(RcpDetailVO rcpDetailVO);
	int getStateKey(RcpDetailVO rcpDetailVO);
	int insertRcpState(RcpDetailVO rcpDetailVO);
	int insertRcpChange(RcpDetailVO rcpDetailVO);
	int insertRcpDvcChg(RcpDetailVO rcpDetailVO);
	int deleteRcpAddition(RcpDetailVO rcpDetailVO);
	int insertRcpAddition(RcpDetailVO rcpDetailVO);
	
	public Map<String, String> getAppFormData(ScanVO vo);
	
	// 서식지 생성
	public void updateAppFormXmlYn(ScanVO vo);
	public List<Map<String, String>> getAppFormPointGroupList(String groupCode);
	public List<Map<String, Object>> getMcpCodeList(String groupId);
	public Map<String, String> getAppFormUserData(ScanVO vo);
	
	public void updateTossRcpCrYn(RcpDetailVO rcpDetailVO);
	
	public void updateTossJueSubInfo(RcpDetailVO rcpDetailVO);
	public void updateTossRequestDtl(RcpDetailVO rcpDetailVO);
	
	public String selectScanId(ScanVO vo);
}
