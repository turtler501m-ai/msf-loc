/*
 * Copyright 2008-2009 the original author or authors.
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
package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 * @Description : SampleDefaultVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class OsstReqVO extends BaseVo implements Serializable {
	
	public String resNo;
	public String appEventCd;
	public String gubun;
	// 번호이동사전인증요청 파라미터
	public String npTlphNo;				// 번호이동전화번호
	public String moveCompany;			// 이동전통신사
	public String cstmrType;			// 고객구분
//	public String indvBizrYn;			// 개인사업자여부
	public String selfCertType;			// 본인인증방법
	public String custIdntNo;			// 개인식별번호
	public String crprNo;				// 법인번호
	public String cstmrName;			// 고객명
	public String cntpntShopId;			// 접점ID
	// 번호조회파라미터
	public String tlphNoPtrn;			// 희망번호
	// 번호예약파라미터
	public String tlphNoStatCd;			// 번호상태코드
	public String asgnAgncId;			// 할당대리점ID
	public String tlphNoOwnCmncCmpnCd;	// 번부사업자코드
	public String openSvcIndCd;			// 개통서비스구분코드
	public String encdTlphNo;			// 암호화전화번호
	public String tlphNo;				// 전화번호

	public String billAcntNo;			// 청구계정번호

	public String requestKey;

	// =======2024-02-21 K-NOTE 추가 박민건 시작=======
	public String mngmAgncId;			//관리대리점코드
	public String cntpntCd;				//접점코드
	public String retvStrtDt;			//조회시작일시        (yyyyMMdd 현재일시보다 큰 경우 불가)
	public String retvEndDt;			//조회종료일시        (yyyyMMdd 현재일시보다 큰 경우 불가)
	public String frmpapId;				//서식지아이디		(ex.0x62E50320B59E11EE8A320080C74455C600)
	public String svcApyTrtStatCd;		//처리상태코드		(1 : 접수, 2: 진행, 3: 완료, 4: 취소)
	public String frmpapStatCd;			//서식지상태변경코드	(P:진행, R:복구, C:취소)
	// =======2024-02-21 K-NOTE 추가 박민건 끝=======


	// 2024-05-07
	public String oderTrtOrgId;     //오더 처리 조직아이디
	public String oderTypeCd;       //오더유형코드
	public String retvStDt;         //yyyyMMdd(현재일시보다 큰 경우 불가)
	public String retvFnsDt;        //yyyyMMdd(현재일시보다 큰 경우 불가) *조회기간 최대 30일
	public String oderTrtId;        //오더 처리자 아이디 ( = KOS ID)
	public String retvSeq;          //조회시작번호(ex. 0) List 에 나오는 최초 Row의 번호 미 입력시 0
	public String retvCascnt;       //조회건수    (ex. 30) => Max 30  List 에 나오는 데이터 Row 수 미 입력시 30
	public String itgFrmpapSeq;     //무서식지 오더 데이터 조회(FS3)를 통해 취득 ex)20240323102606X667387804
	public String scanDt;           //YYYYMMDD(현재일 보다 큰 경우 불가)   신분증 스캔일자
	public String svcContId;        //서비스계약번호

	public String custNo; 				  //고객 아이디
	public String onlineOfflnDivCd; //온라인오프라인구분코드 (ONLINE:온라인 채널(비대면), OFFLINE:오프라인 채널(대면))
	public String orgId;            //대리점ID
	public String cpntId;           //접점ID
	public String retvCdVal;        //조회코드값 신분증유형코드 (REGID:주민등록증, DRIVE:운전면허증, HANDI:장애인등록증, MERIT:국가유공자증, NAMEC:대한민국여권)
	public String smsRcvTelNo;      //SMS수신전화번호 (비대면일때 SMS로 URL을 받을 전화번호)
	public String fathSbscDivCd;    //안면인증 가입 구분 코드 (1:신규가입, 2:번호이동, 3:명의변경, 4:기기변경, 5:고객정보변경)
	public String fathTransacId;    //안면인증트랜잭션아이디
	public String retvDivCd;        //조회구분코드 (1 : 안면인증 트랜잭션 아이디로 조회, 2 : 서식지아이디의 최종 안면인증 내역 조회)

    public String mcnResNo;

	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getAppEventCd() {
		return appEventCd;
	}
	public void setAppEventCd(String appEventCd) {
		this.appEventCd = appEventCd;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getNpTlphNo() {
		return npTlphNo;
	}
	public void setNpTlphNo(String npTlphNo) {
		this.npTlphNo = npTlphNo;
	}
	public String getMoveCompany() {
		return moveCompany;
	}
	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}
//	public String getIndvBizrYn() {
//		return indvBizrYn;
//	}
//	public void setIndvBizrYn(String indvBizrYn) {
//		this.indvBizrYn = indvBizrYn;
//	}
	public String getSelfCertType() {
		return selfCertType;
	}
	public void setSelfCertType(String selfCertType) {
		this.selfCertType = selfCertType;
	}
	public String getCustIdntNo() {
		return custIdntNo;
	}
	public void setCustIdntNo(String custIdntNo) {
		this.custIdntNo = custIdntNo;
	}
	public String getCrprNo() {
		return crprNo;
	}
	public void setCrprNo(String crprNo) {
		this.crprNo = crprNo;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getTlphNoPtrn() {
		return tlphNoPtrn;
	}
	public void setTlphNoPtrn(String tlphNoPtrn) {
		this.tlphNoPtrn = tlphNoPtrn;
	}
	public String getTlphNoStatCd() {
		return tlphNoStatCd;
	}
	public void setTlphNoStatCd(String tlphNoStatCd) {
		this.tlphNoStatCd = tlphNoStatCd;
	}
	public String getAsgnAgncId() {
		return asgnAgncId;
	}
	public void setAsgnAgncId(String asgnAgncId) {
		this.asgnAgncId = asgnAgncId;
	}
	public String getTlphNoOwnCmncCmpnCd() {
		return tlphNoOwnCmncCmpnCd;
	}
	public void setTlphNoOwnCmncCmpnCd(String tlphNoOwnCmncCmpnCd) {
		this.tlphNoOwnCmncCmpnCd = tlphNoOwnCmncCmpnCd;
	}
	public String getOpenSvcIndCd() {
		return openSvcIndCd;
	}
	public void setOpenSvcIndCd(String openSvcIndCd) {
		this.openSvcIndCd = openSvcIndCd;
	}
	public String getEncdTlphNo() {
		return encdTlphNo;
	}
	public void setEncdTlphNo(String encdTlphNo) {
		this.encdTlphNo = encdTlphNo;
	}
	public String getTlphNo() {
		return tlphNo;
	}
	public void setTlphNo(String tlphNo) {
		this.tlphNo = tlphNo;
	}
	public String getBillAcntNo() {
		return billAcntNo;
	}
	public void setBillAcntNo(String billAcntNo) {
		this.billAcntNo = billAcntNo;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getMngmAgncId() {
		return mngmAgncId;
	}

	public void setMngmAgncId(String mngmAgncId) {
		this.mngmAgncId = mngmAgncId;
	}

	public String getCntpntCd() {
		return cntpntCd;
	}

	public void setCntpntCd(String cntpntCd) {
		this.cntpntCd = cntpntCd;
	}

	public String getRetvStrtDt() {
		return retvStrtDt;
	}

	public void setRetvStrtDt(String retvStrtDt) {
		this.retvStrtDt = retvStrtDt;
	}

	public String getRetvEndDt() {
		return retvEndDt;
	}

	public void setRetvEndDt(String retvEndDt) {
		this.retvEndDt = retvEndDt;
	}

	public String getFrmpapId() {
		return frmpapId;
	}

	public void setFrmpapId(String frmpapId) {
		this.frmpapId = frmpapId;
	}

	public String getFrmpapStatCd() {
		return frmpapStatCd;
	}

	public void setFrmpapStatCd(String frmpapStatCd) {
		this.frmpapStatCd = frmpapStatCd;
	}

	public String getSvcApyTrtStatCd() {
		return svcApyTrtStatCd;
	}

	public void setSvcApyTrtStatCd(String svcApyTrtStatCd) {
		this.svcApyTrtStatCd = svcApyTrtStatCd;
	}

	public String getOderTrtOrgId() {
		return oderTrtOrgId;
	}

	public void setOderTrtOrgId(String oderTrtOrgId) {
		this.oderTrtOrgId = oderTrtOrgId;
	}

	public String getOderTypeCd() {
		return oderTypeCd;
	}

	public void setOderTypeCd(String oderTypeCd) {
		this.oderTypeCd = oderTypeCd;
	}

	public String getOderTrtId() {
		return oderTrtId;
	}

	public void setOderTrtId(String oderTrtId) {
		this.oderTrtId = oderTrtId;
	}

	public String getRetvSeq() {
		return retvSeq;
	}

	public void setRetvSeq(String retvSeq) {
		this.retvSeq = retvSeq;
	}

	public String getRetvCascnt() {
		return retvCascnt;
	}

	public void setRetvCascnt(String retvCascnt) {
		this.retvCascnt = retvCascnt;
	}

	public String getRetvStDt() {
		return retvStDt;
	}

	public void setRetvStDt(String retvStDt) {
		this.retvStDt = retvStDt;
	}

	public String getRetvFnsDt() {
		return retvFnsDt;
	}

	public void setRetvFnsDt(String retvFnsDt) {
		this.retvFnsDt = retvFnsDt;
	}

	public String getItgFrmpapSeq() {
		return itgFrmpapSeq;
	}

	public void setItgFrmpapSeq(String itgFrmpapSeq) {
		this.itgFrmpapSeq = itgFrmpapSeq;
	}

	public String getScanDt() {
		return scanDt;
	}

	public void setScanDt(String scanDt) {
		this.scanDt = scanDt;
	}

	public String getSvcContId() {
		return svcContId;
	}

	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

    public String getMcnResNo() {
        return mcnResNo;
    }

    public void setMcnResNo(String mcnResNo) {
        this.mcnResNo = mcnResNo;
    }
	public String getOnlineOfflnDivCd() {
		return onlineOfflnDivCd;
	}

	public void setOnlineOfflnDivCd(String onlineOfflnDivCd) {
		this.onlineOfflnDivCd = onlineOfflnDivCd;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCpntId() {
		return cpntId;
	}

	public void setCpntId(String cpntId) {
		this.cpntId = cpntId;
	}

	public String getRetvCdVal() {
		return retvCdVal;
	}

	public void setRetvCdVal(String retvCdVal) {
		this.retvCdVal = retvCdVal;
	}

	public String getSmsRcvTelNo() {
		return smsRcvTelNo;
	}

	public void setSmsRcvTelNo(String smsRcvTelNo) {
		this.smsRcvTelNo = smsRcvTelNo;
	}

	public String getFathSbscDivCd() {
		return fathSbscDivCd;
	}

	public void setFathSbscDivCd(String fathSbscDivCd) {
		this.fathSbscDivCd = fathSbscDivCd;
	}

	public String getFathTransacId() {
		return fathTransacId;
	}

	public void setFathTransacId(String fathTransacId) {
		this.fathTransacId = fathTransacId;
	}

	public String getRetvDivCd() {
		return retvDivCd;
	}

	public void setRetvDivCd(String retvDivCd) {
		this.retvDivCd = retvDivCd;
	}
}
