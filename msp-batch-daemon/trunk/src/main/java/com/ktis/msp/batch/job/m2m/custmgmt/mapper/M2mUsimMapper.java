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
package com.ktis.msp.batch.job.m2m.custmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("M2mUsimMapper")
public interface M2mUsimMapper {

	/**
	 * 배송중으로 3일경과한 M2M USIM 주문 목록
	 * @return
	 */
	List<Map<String, Object>> getStatus4SmsTrgtList();
}
