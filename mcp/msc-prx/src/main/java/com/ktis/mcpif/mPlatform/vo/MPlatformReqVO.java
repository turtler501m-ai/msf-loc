package com.ktis.mcpif.mPlatform.vo;

import java.io.Serializable;

public class MPlatformReqVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String resNo;
    private String appEventCd;
    private String gubun;
    // 번호이동사전인증요청 파라미터
    private String npTlphNo;
    private String moveCompany;
    private String cstmrType;
    private String indvBizrYn;
    private String selfCertType;
    private String custIdntNo;
    private String crprNo;
    private String cstmrName;
    private String cntpntShopId;
    //번호조회
    private String tlphNo;
    //연동결과
    private String osstOrdNo;
    private String rsltCd;
    private String rsltMsg;
    private String nstepGlobalId;

    private String prgrStatCd;

    /** 청구계정번호 */
    private String billAcntNo;

    private String mvnoOrdNo; //MVNO 오더 번호

    /*사전승낙제(K-Note) 연동 param 시작*/
    private String mngmAgncId;       //관리대리점코드
    private String cntpntCd;         //접점코드
    private String retvStrtDt;       //조회시작일시
    private String retvEndDt;        //조회
    private String svcApyTrtStatCd;  //처리상태코드
    private String frmpapId;         //서식지아이디
    private String frmpapStatCd;     //서식지상태변경코드
    private String oderTrtOrgId;     //오더 처리 조직아이디
    private String oderTypeCd;       //오더유형코드
    private String retvStDt;         //yyyyMMdd(현재일시보다 큰 경우 불가)
    private String retvFnsDt;        //yyyyMMdd(현재일시보다 큰 경우 불가) *조회기간 최대 30일
    private String oderTrtId;        //오더 처리자 아이디 ( = KOS ID)
    private String retvSeq;          //조회시작번호(ex. 0) List 에 나오는 최초 Row의 번호 미 입력시 0
    private String retvCascnt;       //조회건수    (ex. 30) => Max 30  List 에 나오는 데이터 Row 수 미 입력시 30
    private String itgFrmpapSeq;     //무서식지 오더 데이터 조회(FS3)를 통해 취득 ex)20240323102606X667387804
    private String scanDt;           //YYYYMMDD(현재일 보다 큰 경우 불가)   신분증 스캔일자
    private String svcContId;        //서비스계약번호

    /*사전승낙제(K-Note) 연동 param 끝*/

    //2024-05-07
    private String prdcChkNotiMsg;   //OSST 연동Param

    //2024-11-19 OSST exception 이력 관련 param
    private String prntsContractNo;  //모회선 계약번호
    private String custNo;           //고객 아이디

    private String asgnAgncId;       //대리점코드
    private String onlineOfflnDivCd; //온라인오프라인구분코드 (ONLINE:온라인 채널(비대면), OFFLINE:오프라인 채널(대면))
    private String orgId;            //대리점ID
    private String cpntId;           //접점ID
    private String retvCdVal;        //조회코드값 신분증유형코드 (REGID:주민등록증, DRIVE:운전면허증, HENDI:장애인등록증, MERIT:국가유공자증, NAMEC:대한민국여권)
    private String smsRcvTelNo;      //SMS수신전화번호 (비대면일때 SMS로 URL을 받을 전화번호)
    private String fathSbscDivCd;    //안면인증 가입 구분 코드 (1:신규가입, 2:번호이동, 3:명의변경, 4:기기변경, 5:고객정보변경)
    private String fathTransacId;    //안면인증트랜잭션아이디
    private String retvDivCd;        //조회구분코드 (1 : 안면인증 트랜잭션 아이디로 조회, 2 : 서식지아이디의 최종 안면인증 내역 조회)
    private String mcnResNo;         //명의변경 예약번호
    
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
    public String getIndvBizrYn() {
        return indvBizrYn;
    }
    public void setIndvBizrYn(String indvBizrYn) {
        this.indvBizrYn = indvBizrYn;
    }
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
    public String getTlphNo() {
        return tlphNo;
    }
    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }
    public String getOsstOrdNo() {
        return osstOrdNo;
    }
    public void setOsstOrdNo(String osstOrdNo) {
        this.osstOrdNo = osstOrdNo;
    }
    public String getRsltCd() {
        return rsltCd;
    }
    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }
    public String getRsltMsg() {
        return rsltMsg;
    }
    public void setRsltMsg(String rsltMsg) {
        this.rsltMsg = rsltMsg;
    }
    public String getNstepGlobalId() {
        return nstepGlobalId;
    }
    public void setNstepGlobalId(String nstepGlobalId) {
        this.nstepGlobalId = nstepGlobalId;
    }

    public String getBillAcntNo() {
        return billAcntNo;
    }
    public void setBillAcntNo(String billAcntNo) {
        this.billAcntNo = billAcntNo;
    }
    public String getMvnoOrdNo() {
        return mvnoOrdNo;
    }
    public void setMvnoOrdNo(String mvnoOrdNo) {
        this.mvnoOrdNo = mvnoOrdNo;
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
    public String getSvcApyTrtStatCd() {
        return svcApyTrtStatCd;
    }
    public void setSvcApyTrtStatCd(String svcApyTrtStatCd) {
        this.svcApyTrtStatCd = svcApyTrtStatCd;
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

    public String getPrgrStatCd() {
        return prgrStatCd;
    }

    public void setPrgrStatCd(String prgrStatCd) {
        this.prgrStatCd = prgrStatCd;
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

    public String getPrdcChkNotiMsg() {
        return prdcChkNotiMsg;
    }

    public void setPrdcChkNotiMsg(String prdcChkNotiMsg) {
        this.prdcChkNotiMsg = prdcChkNotiMsg;
    }

    public String getPrntsContractNo() {
        return prntsContractNo;
    }

    public void setPrntsContractNo(String prntsContractNo) {
        this.prntsContractNo = prntsContractNo;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getAsgnAgncId() {
        return asgnAgncId;
    }

    public void setAsgnAgncId(String asgnAgncId) {
        this.asgnAgncId = asgnAgncId;
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

    public String getMcnResNo() {
        return mcnResNo;
    }

    public void setMcnResNo(String mcnResNo) {
        this.mcnResNo = mcnResNo;
    }
}