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

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.MplatformOpenReqVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.MplatformOpenResVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MplatformOpenMapper")
public interface MplatformOpenMapper {
	
	//당일접수건 개통대상조회
	List<MplatformOpenResVO> getMplatformOpenTrgtList();
	
	//전일번호예약 미개통 재처리대상
	List<MplatformOpenResVO> getMplatformOpenRetryList();
	
	void insertCopyRequest(MplatformOpenResVO vo);
	
	void insertCopyRequestCstmr(MplatformOpenResVO vo);
	
	void insertCopyRequestSaleinfo(MplatformOpenResVO vo);
	
	void insertCopyRequestDlvry(MplatformOpenResVO vo);
	
	void insertCopyRequestReq(MplatformOpenResVO vo);
	
	void insertCopyRequestMove(MplatformOpenResVO vo);
	
	void insertCopyRequestPay(MplatformOpenResVO vo);
	
	void insertCopyRequestAgent(MplatformOpenResVO vo);
	
	void insertCopyRequestState(MplatformOpenResVO vo);
	
	void insertCopyRequestChange(MplatformOpenResVO vo);
	
	void insertCopyRequestDvcChg(MplatformOpenResVO vo);
	
	void insertCopyRequestAddition(MplatformOpenResVO vo);
	
	void updateRequestState(MplatformOpenResVO vo);
	
	void insertRequestOsstHist(MplatformOpenResVO vo);
	
	int getTcpResult(MplatformOpenReqVO vo);
	
	
	/**
	 * Mplatform 개통 후 msp_request 정보가 없는 계약번호 조회
	 * @param vo
	 * @return
	 */
	List<MplatformOpenResVO> getOpenCntrList(MplatformOpenReqVO vo);
	
	void updateMcpRequest(MplatformOpenResVO vo);
	
	void insertMspRequest(MplatformOpenResVO vo);
	
	void insertMspRequestCstmr(MplatformOpenResVO vo);
	
	void insertMspRequestSaleinfo(MplatformOpenResVO vo);
	
	void insertMspRequestDlvry(MplatformOpenResVO vo);
	
	void insertMspRequestReq(MplatformOpenResVO vo);
	
	void insertMspRequestMove(MplatformOpenResVO vo);
	
	void insertMspRequestPay(MplatformOpenResVO vo);
	
	void insertMspRequestAgent(MplatformOpenResVO vo);
	
	void insertMspRequestState(MplatformOpenResVO vo);
	
	void insertMspRequestChange(MplatformOpenResVO vo);
	
	void insertMspRequestDvcChg(MplatformOpenResVO vo);
	
	void insertMspRequestAddition(MplatformOpenResVO vo);
	
	void updateMspRequestDtl(MplatformOpenResVO vo);
	
	void updateJuoSubInfo(MplatformOpenResVO vo);
	
	/**
	 * 미개통신청정보 삭제대상조회
	 */
	List<MplatformOpenResVO> deleteOpenFailList();
	
	void deleteRequest(MplatformOpenResVO vo);
	void deleteRequestCstmr(MplatformOpenResVO vo);
	void deleteRequestSaleinfo(MplatformOpenResVO vo);
	void deleteRequestDvlry(MplatformOpenResVO vo);
	void deleteRequestReq(MplatformOpenResVO vo);
	void deleteRequestMove(MplatformOpenResVO vo);
	void deleteRequestPayment(MplatformOpenResVO vo);
	void deleteRequestAgent(MplatformOpenResVO vo);
	void deleteRequestState(MplatformOpenResVO vo);
	void deleteRequestChange(MplatformOpenResVO vo);
	void deleteRequestDvcChg(MplatformOpenResVO vo);
	void deleteRequestAddition(MplatformOpenResVO vo);
	void deleteRequestOsst(MplatformOpenResVO vo);
	
	// [SRM18113000814] ATM즉시개통고객대상 단체상해보험가입을 위한 서식지합본 전산개발요청
	List<MplatformOpenResVO> getAtmOpenList();
	void updateAtmOpenSubInfo(MplatformOpenResVO vo);
	void updateAtmOpenRequestDtl(MplatformOpenResVO vo);
	
	// TOSS 개통시 스캔ID 정보가 없으므로 해당 계약에 대하여 ON_OFF_TYPE UPDATE
	List<MplatformOpenResVO> getTossOpenList();
	void updateTossOpenSubInfo(MplatformOpenResVO vo);
	void updateTossOpenRequestDtl(MplatformOpenResVO vo);
	
	// 2022.11.22.wooki
	// KMV 개통시 서식지 정보가 없으므로 해당 계약에 대하여 ON_OFF_TYPE UPDATE
	List<MplatformOpenResVO> getKmvOpenList();
	void updateKmvOpenSubInfo(MplatformOpenResVO vo);
	void updateKmvOpenRequestDtl(MplatformOpenResVO vo);

	// 자급제 보상서비스 가입 대상 UPDATE
	void updateMspRwdOrder(MplatformOpenResVO vo);

	// 셀프개통 해피콜 대상 INSERT
	void insertAcenReqTrg(MplatformOpenResVO resVO);
}
