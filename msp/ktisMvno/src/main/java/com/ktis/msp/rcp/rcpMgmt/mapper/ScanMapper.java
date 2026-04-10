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
package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.ScanVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ScanMapper")
public interface ScanMapper {
	
	public Map<String, String> getAppFormData(ScanVO vo); 
	
	public Map<String, String> getAppFormUserData(ScanVO vo);
	
	// 서식지 생성완료
	public void updateAppFormXmlYn(ScanVO vo);
	
	// 서식지 위치정보
	public List<Map<String, String>> getAppFormPointList();
	
	public List<Map<String, String>> getAppFormPointGroupList(String groupCode);
	
	public List<Map<String, Object>> getMcpCodeList(String groupId);
}

