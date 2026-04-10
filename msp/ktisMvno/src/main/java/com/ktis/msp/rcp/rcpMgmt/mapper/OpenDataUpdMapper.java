/*
\ * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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

import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.OpenDataUpdVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("OpenDataUpdMapper")
public interface OpenDataUpdMapper {
	
	Map<String, Object> getMcpRequestInfo(OpenDataUpdVO vo);
    
    int insertEmvScanMst(OpenDataUpdVO vo);
    
    int insertEmvScanFile(OpenDataUpdVO vo);
    
    Map<String, Object> setCrdtLoanRvlProc(OpenDataUpdVO vo);
        
    void deleteMspRequest(OpenDataUpdVO vo);
    
    void deleteMspRequestAddition(OpenDataUpdVO vo);
    
    void deleteMspRequestAgent(OpenDataUpdVO vo);
    
    void deleteMspRequestChange(OpenDataUpdVO vo);
    
    void deleteMspRequestCstmr(OpenDataUpdVO vo);
    
    void deleteMspRequestMove(OpenDataUpdVO vo);
    
    void deleteMspRequestPayment(OpenDataUpdVO vo);
    
    void deleteMspRequestReq(OpenDataUpdVO vo);
    
    void deleteMspRequestSaleinfo(OpenDataUpdVO vo);
    
    void deleteMspRequestState(OpenDataUpdVO vo);
    
    void deleteMspRequestDlvry(OpenDataUpdVO vo);
    
    void deleteEmvScanMst(OpenDataUpdVO vo);
    
    void deleteEmvScanFile(OpenDataUpdVO vo);
    
    void deleteMspRequestDvcChg(OpenDataUpdVO vo);
}

