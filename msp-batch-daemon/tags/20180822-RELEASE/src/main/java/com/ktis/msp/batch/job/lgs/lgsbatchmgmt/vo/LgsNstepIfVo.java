package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.BaseVo;

public class LgsNstepIfVo extends BaseVo implements Serializable {
	
	/** serialVersionUID **/
	private static final long serialVersionUID = 6539576355524354899L;
	
	
	/** BATCH **/
	private String btchPrgmId;					/* 배치프로그램ID */
	private String oprtDm;						/* 작업일월 */
	private String oprtStat;					/* 작업상태 */
	private String oprtType;					/* 작업종류 */
	private String oprtStrtDt;					/* 작업시작일시 */
	private String oprtEndDt;					/* 작업종료일시 */
	
	
	/** 모델정보 **/
	private String prdtTypeCd;					/* 모델유형코드 */
	private String rprsPrdtId;					/* 대표모델ID */
	private String prdtId;						/* 모델ID */
	private String prdtSrlNum;					/* 모델일련번호 */
	private String oldYn;						/* 중고여부 */
	private String invStatCd;					/* 모델처리상태코드 */
	private String norgInvStatCd;				/* 모델처리상태코드-조직확인제외 */
	private String mycpYn;						/* 자사여부 */
	private String snrCd;						/* 입출고코드 */
	private String prvRprsPrdtId;				/* 이전대표모델ID */
	private String prvPrdtId;					/* 이전모델ID */
	private String prvPrdtSrlNum;				/* 이전모델일련번호 */
	private String prvInvStatCd;				/* 이전모델처리상태코드 */
	private String norgPrvInvStatCd;			/* 이전모델처리상태코드-조직확인제외 */
	private String prvMycpYn;					/* 이전자사여부 */
	private String prvSnrCd;					/* 이전모델입출고코드 */
	private String prvOldYn;					/* 이전중고여부 */
	private String imei;						/* imei */
	private String wifiMacId;					/* MAC어드레스 */
	private int    unitPric;					/* 출고단가 */
	
	
	/** 조직정보 **/
	private String agnId;						/* 대리점ID */
	private String cntpntCd;					/* 접점CD */
	private String cntpntStatCd;				/* 접점상태코드 */
	private String rsvCntpntCd;					/* 입고예정조직ID */
	private String rsvCntpntStatCd;				/* 입고예정조직상태코드 */
	private String prvCntpntCd;					/* 이전조직ID */
	private String userId;						/* 사용자ID */
	
	
	/** 연동정보 **/
	private String ifQueNum;					/* 인터페이스번호 */
	private String contractNum;					/* 가입계약번호 */
	private String aplEvntCd;					/* 적용이벤트코드 */
	private String evntCd;						/* 이벤트코드 */
	private String evntRsnCd;					/* 이벤트사유코드 */
	private String prvEvntCd;					/* 이전이벤트코드 */
	private String prvEvntRsnCd;				/* 이전이벤트사유코드 */
	private String openDt;						/* 개통일자 */
	private String exchDt;						/* 기변일자 */
	private String procCd;						/* 처리코드 */
	private String procStatCd;					/* 처리상태코드 */
	
	
	/** 개통정보 **/
	private String applEndDttm;					/* 적용만료일시 */
	private String applStrtDttm;				/* 적용시작일시 */
	private String inoutId;						/* 입출고ID */
	private String prvInoutId;					/* 이전입출고ID */
	private String cusYn;						/* 고객여부 */
	private String poYn;						/* 후불여부 */
	private String hstYn;						/* 이력여부 */
	private String mdlYn;						/* 등록모델여부 */
	private String procYn;						/* 처리전문여부 */
	private String rstYn;						/* 복구여부 */
	private String exchYn;						/* 교체가능여부 */
	private String rtnYn;						/* 반납여부 */
	private String takeYn;						/* 인수여부 */
	private String requestKey;					/* 가입신청키 */
	private String openMdlYn;					/* 유심단독 확인 */
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String getBtchPrgmId() {
		return btchPrgmId;
	}
	
	public void setBtchPrgmId(String btchPrgmId) {
		this.btchPrgmId = btchPrgmId;
	}

	public String getOprtDm() {
		return oprtDm;
	}

	public void setOprtDm(String oprtDm) {
		this.oprtDm = oprtDm;
	}

	public String getOprtStat() {
		return oprtStat;
	}

	public void setOprtStat(String oprtStat) {
		this.oprtStat = oprtStat;
	}

	public String getOprtType() {
		return oprtType;
	}

	public void setOprtType(String oprtType) {
		this.oprtType = oprtType;
	}

	public String getOprtStrtDt() {
		return oprtStrtDt;
	}

	public void setOprtStrtDt(String oprtStrtDt) {
		this.oprtStrtDt = oprtStrtDt;
	}

	public String getOprtEndDt() {
		return oprtEndDt;
	}

	public void setOprtEndDt(String oprtEndDt) {
		this.oprtEndDt = oprtEndDt;
	}

	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}

	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}

	public String getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}

	public String getPrdtSrlNum() {
		return prdtSrlNum;
	}

	public void setPrdtSrlNum(String prdtSrlNum) {
		this.prdtSrlNum = prdtSrlNum;
	}

	public String getInvStatCd() {
		return invStatCd;
	}

	public void setInvStatCd(String invStatCd) {
		this.invStatCd = invStatCd;
	}

	public String getNorgInvStatCd() {
		return norgInvStatCd;
	}

	public void setNorgInvStatCd(String norgInvStatCd) {
		this.norgInvStatCd = norgInvStatCd;
	}

	public String getMycpYn() {
		return mycpYn;
	}

	public void setMycpYn(String mycpYn) {
		this.mycpYn = mycpYn;
	}

	public String getSnrCd() {
		return snrCd;
	}

	public void setSnrCd(String snrCd) {
		this.snrCd = snrCd;
	}

	public String getPrvPrdtId() {
		return prvPrdtId;
	}

	public void setPrvPrdtId(String prvPrdtId) {
		this.prvPrdtId = prvPrdtId;
	}

	public String getPrvPrdtSrlNum() {
		return prvPrdtSrlNum;
	}

	public void setPrvPrdtSrlNum(String prvPrdtSrlNum) {
		this.prvPrdtSrlNum = prvPrdtSrlNum;
	}

	public String getPrvInvStatCd() {
		return prvInvStatCd;
	}

	public void setPrvInvStatCd(String prvInvStatCd) {
		this.prvInvStatCd = prvInvStatCd;
	}

	public String getNorgPrvInvStatCd() {
		return norgPrvInvStatCd;
	}

	public void setNorgPrvInvStatCd(String norgPrvInvStatCd) {
		this.norgPrvInvStatCd = norgPrvInvStatCd;
	}

	public String getPrvMycpYn() {
		return prvMycpYn;
	}

	public void setPrvMycpYn(String prvMycpYn) {
		this.prvMycpYn = prvMycpYn;
	}

	public String getPrvSnrCd() {
		return prvSnrCd;
	}

	public void setPrvSnrCd(String prvSnrCd) {
		this.prvSnrCd = prvSnrCd;
	}

	public String getOldYn() {
		return oldYn;
	}

	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getWifiMacId() {
		return wifiMacId;
	}

	public void setWifiMacId(String wifiMacId) {
		this.wifiMacId = wifiMacId;
	}

	public String getAgnId() {
		return agnId;
	}

	public void setAgnId(String agnId) {
		this.agnId = agnId;
	}

	public String getCntpntCd() {
		return cntpntCd;
	}

	public void setCntpntCd(String cntpntCd) {
		this.cntpntCd = cntpntCd;
	}

	public String getCntpntStatCd() {
		return cntpntStatCd;
	}

	public void setCntpntStatCd(String cntpntStatCd) {
		this.cntpntStatCd = cntpntStatCd;
	}

	public String getRsvCntpntCd() {
		return rsvCntpntCd;
	}

	public void setRsvCntpntCd(String rsvCntpntCd) {
		this.rsvCntpntCd = rsvCntpntCd;
	}

	public String getRsvCntpntStatCd() {
		return rsvCntpntStatCd;
	}

	public void setRsvCntpntStatCd(String rsvCntpntStatCd) {
		this.rsvCntpntStatCd = rsvCntpntStatCd;
	}

	public String getPrvCntpntCd() {
		return prvCntpntCd;
	}

	public void setPrvCntpntCd(String prvCntpntCd) {
		this.prvCntpntCd = prvCntpntCd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIfQueNum() {
		return ifQueNum;
	}

	public void setIfQueNum(String ifQueNum) {
		this.ifQueNum = ifQueNum;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getAplEvntCd() {
		return aplEvntCd;
	}

	public void setAplEvntCd(String aplEvntCd) {
		this.aplEvntCd = aplEvntCd;
	}

	public String getEvntCd() {
		return evntCd;
	}

	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}

	public String getEvntRsnCd() {
		return evntRsnCd;
	}

	public void setEvntRsnCd(String evntRsnCd) {
		this.evntRsnCd = evntRsnCd;
	}

	public String getPrvEvntCd() {
		return prvEvntCd;
	}

	public void setPrvEvntCd(String prvEvntCd) {
		this.prvEvntCd = prvEvntCd;
	}

	public String getPrvEvntRsnCd() {
		return prvEvntRsnCd;
	}

	public void setPrvEvntRsnCd(String prvEvntRsnCd) {
		this.prvEvntRsnCd = prvEvntRsnCd;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getProcCd() {
		return procCd;
	}

	public void setProcCd(String procCd) {
		this.procCd = procCd;
	}

	public String getProcStatCd() {
		return procStatCd;
	}

	public void setProcStatCd(String procStatCd) {
		this.procStatCd = procStatCd;
	}

	public String getApplEndDttm() {
		return applEndDttm;
	}

	public void setApplEndDttm(String applEndDttm) {
		this.applEndDttm = applEndDttm;
	}

	public String getApplStrtDttm() {
		return applStrtDttm;
	}

	public void setApplStrtDttm(String applStrtDttm) {
		this.applStrtDttm = applStrtDttm;
	}

	public String getInoutId() {
		return inoutId;
	}

	public void setInoutId(String inoutId) {
		this.inoutId = inoutId;
	}

	public String getPrvInoutId() {
		return prvInoutId;
	}

	public void setPrvInoutId(String prvInoutId) {
		this.prvInoutId = prvInoutId;
	}

	public String getCusYn() {
		return cusYn;
	}

	public void setCusYn(String cusYn) {
		this.cusYn = cusYn;
	}

	public String getPoYn() {
		return poYn;
	}

	public void setPoYn(String poYn) {
		this.poYn = poYn;
	}

	public String getHstYn() {
		return hstYn;
	}

	public void setHstYn(String hstYn) {
		this.hstYn = hstYn;
	}

	public String getMdlYn() {
		return mdlYn;
	}

	public void setMdlYn(String mdlYn) {
		this.mdlYn = mdlYn;
	}

	public String getProcYn() {
		return procYn;
	}

	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}

	public String getRtnYn() {
		return rtnYn;
	}

	public void setRtnYn(String rtnYn) {
		this.rtnYn = rtnYn;
	}

	public String getTakeYn() {
		return takeYn;
	}

	public void setTakeYn(String takeYn) {
		this.takeYn = takeYn;
	}

	public String getRprsPrdtId() {
		return rprsPrdtId;
	}

	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}

	public String getPrvRprsPrdtId() {
		return prvRprsPrdtId;
	}

	public void setPrvRprsPrdtId(String prvRprsPrdtId) {
		this.prvRprsPrdtId = prvRprsPrdtId;
	}

	public String getPrvOldYn() {
		return prvOldYn;
	}

	public void setPrvOldYn(String prvOldYn) {
		this.prvOldYn = prvOldYn;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public int getUnitPric() {
		return unitPric;
	}

	public void setUnitPric(int unitPric) {
		this.unitPric = unitPric;
	}

	public String getRstYn() {
		return rstYn;
	}

	public void setRstYn(String rstYn) {
		this.rstYn = rstYn;
	}

	public String getExchDt() {
		return exchDt;
	}

	public void setExchDt(String exchDt) {
		this.exchDt = exchDt;
	}

	public String getExchYn() {
		return exchYn;
	}

	public void setExchYn(String exchYn) {
		this.exchYn = exchYn;
	}

	public String getOpenMdlYn() {
		return openMdlYn;
	}

	public void setOpenMdlYn(String openMdlYn) {
		this.openMdlYn = openMdlYn;
	}
	
	
}