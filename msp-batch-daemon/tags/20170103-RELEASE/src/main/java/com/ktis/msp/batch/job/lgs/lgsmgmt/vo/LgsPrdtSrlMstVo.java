package com.ktis.msp.batch.job.lgs.lgsmgmt.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.BaseVo;

/**
 * @Class Name  : LgsPrdtSrlMstVo.java
 * @Description : LgsPrdtSrlMstVo Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.16  IB		 최초생성
 *
 * @author IB
 * @since 2014.09.16
 * @version 1.0
 */

public class LgsPrdtSrlMstVo extends BaseVo implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -3706774807505057211L;
	
	/** 제품시리얼마스터 */
	private String prdtId; /** 제품ID */
	private String prdtSrlNum; /** 제품일련번호 */
	private String oldYn; /** 중고여부 */
	private String orgnId; /** 조직ID */
	private String inOrgnId; /** 입고조직ID */
	private String outOrgnId; /** 출고조직ID */
	private String imei; /** IMEI */
	private String prdtSrlNumFmt; /** 제조번호FMT */
	private String prdtPrposCd; /** 용도코드 */
	private String lastInoutId; /** 최종수불ID */
	private String prdtStatCd; /** 상태코드 */
	private String suplyCpnyOrdNum; /** 공급사주문번호 */
	private String lastChngHstNum; /** 최종변경이력번호 */
	private String logisProcStatCd; /** 물류처리상태코드 */
	private String mycpnyReqYn; /** 자사등록여부 */
	private String prdtColrCd; /** 색상코드 */
	private String svcProcStatCd; /** 서비스처리상태코드 */
	private String inputDt; /** 등록일자 */
	private String prdtTypeCd; /** 제품유형코드 */
	private String creDt; /** 제조일자 */
	private String wifiMacId; /** WIFI_MAC_ID */
	private String svcCtNum; /** 서비스계약번호 */
	private String svcOpenDt; /** 서비스오픈일자 */
	private String svcOpenTm; /** 서비스오픈시간 */
	private String esn; /** ESN */
	private String certKey; /** 인증키 */
	private String tcnStdCnfmCertno; /** 기술기준확인증명번호 */
	private String wibroImei; /** WIBRO_IMEI */
	private String fixedId; /** FIXED_ID */
	private String rfno; /** RFNO */
	private String naming; /** NAMING */
	private String nsn; /** NSN */
	private String wibroMacId; /** WIBRO_MAC_ID */
	private int invtrDayCnt; /** 재고누적일수 */
	private int inUnitPrice; /** 입고단가 */
	private String inDt; /** 입고일자 */
	private String outDt; /** 출고일자 */
	private int outUnitPrice; /** 출가단가 */
	private String ifQueNum;	// 인터페이스넘버
	private String slsDt;	//판매일자
	private String canDt;	//해지일자
	private String userId; /** 사용자ID */
	
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
	public String getOldYn() {
		return oldYn;
	}
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPrdtSrlNumFmt() {
		return prdtSrlNumFmt;
	}
	public void setPrdtSrlNumFmt(String prdtSrlNumFmt) {
		this.prdtSrlNumFmt = prdtSrlNumFmt;
	}
	public String getPrdtPrposCd() {
		return prdtPrposCd;
	}
	public void setPrdtPrposCd(String prdtPrposCd) {
		this.prdtPrposCd = prdtPrposCd;
	}
	public String getLastInoutId() {
		return lastInoutId;
	}
	public void setLastInoutId(String lastInoutId) {
		this.lastInoutId = lastInoutId;
	}
	public String getPrdtStatCd() {
		return prdtStatCd;
	}
	public void setPrdtStatCd(String prdtStatCd) {
		this.prdtStatCd = prdtStatCd;
	}
	public String getSuplyCpnyOrdNum() {
		return suplyCpnyOrdNum;
	}
	public void setSuplyCpnyOrdNum(String suplyCpnyOrdNum) {
		this.suplyCpnyOrdNum = suplyCpnyOrdNum;
	}
	public String getLastChngHstNum() {
		return lastChngHstNum;
	}
	public void setLastChngHstNum(String lastChngHstNum) {
		this.lastChngHstNum = lastChngHstNum;
	}
	public String getLogisProcStatCd() {
		return logisProcStatCd;
	}
	public void setLogisProcStatCd(String logisProcStatCd) {
		this.logisProcStatCd = logisProcStatCd;
	}
	public String getMycpnyReqYn() {
		return mycpnyReqYn;
	}
	public void setMycpnyReqYn(String mycpnyReqYn) {
		this.mycpnyReqYn = mycpnyReqYn;
	}
	public String getPrdtColrCd() {
		return prdtColrCd;
	}
	public void setPrdtColrCd(String prdtColrCd) {
		this.prdtColrCd = prdtColrCd;
	}
	public String getSvcProcStatCd() {
		return svcProcStatCd;
	}
	public void setSvcProcStatCd(String svcProcStatCd) {
		this.svcProcStatCd = svcProcStatCd;
	}
	public String getInputDt() {
		return inputDt;
	}
	public void setInputDt(String inputDt) {
		this.inputDt = inputDt;
	}
	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}
	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}
	public String getCreDt() {
		return creDt;
	}
	public void setCreDt(String creDt) {
		this.creDt = creDt;
	}
	public String getWifiMacId() {
		return wifiMacId;
	}
	public void setWifiMacId(String wifiMacId) {
		this.wifiMacId = wifiMacId;
	}
	public String getSvcCtNum() {
		return svcCtNum;
	}
	public void setSvcCtNum(String svcCtNum) {
		this.svcCtNum = svcCtNum;
	}
	public String getSvcOpenDt() {
		return svcOpenDt;
	}
	public void setSvcOpenDt(String svcOpenDt) {
		this.svcOpenDt = svcOpenDt;
	}
	public String getSvcOpenTm() {
		return svcOpenTm;
	}
	public void setSvcOpenTm(String svcOpenTm) {
		this.svcOpenTm = svcOpenTm;
	}
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public String getCertKey() {
		return certKey;
	}
	public void setCertKey(String certKey) {
		this.certKey = certKey;
	}
	public String getTcnStdCnfmCertno() {
		return tcnStdCnfmCertno;
	}
	public void setTcnStdCnfmCertno(String tcnStdCnfmCertno) {
		this.tcnStdCnfmCertno = tcnStdCnfmCertno;
	}
	public String getWibroImei() {
		return wibroImei;
	}
	public void setWibroImei(String wibroImei) {
		this.wibroImei = wibroImei;
	}
	public String getFixedId() {
		return fixedId;
	}
	public void setFixedId(String fixedId) {
		this.fixedId = fixedId;
	}
	public String getRfno() {
		return rfno;
	}
	public void setRfno(String rfno) {
		this.rfno = rfno;
	}
	public String getNaming() {
		return naming;
	}
	public void setNaming(String naming) {
		this.naming = naming;
	}
	public String getNsn() {
		return nsn;
	}
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public String getWibroMacId() {
		return wibroMacId;
	}
	public void setWibroMacId(String wibroMacId) {
		this.wibroMacId = wibroMacId;
	}
	public int getInvtrDayCnt() {
		return invtrDayCnt;
	}
	public void setInvtrDayCnt(int invtrDayCnt) {
		this.invtrDayCnt = invtrDayCnt;
	}
	public int getInUnitPrice() {
		return inUnitPrice;
	}
	public void setInUnitPrice(int inUnitPrice) {
		this.inUnitPrice = inUnitPrice;
	}
	public String getInDt() {
		return inDt;
	}
	public void setInDt(String inDt) {
		this.inDt = inDt;
	}
	public String getOutDt() {
		return outDt;
	}
	public void setOutDt(String outDt) {
		this.outDt = outDt;
	}
	public int getOutUnitPrice() {
		return outUnitPrice;
	}
	public void setOutUnitPrice(int outUnitPrice) {
		this.outUnitPrice = outUnitPrice;
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

	public String getInOrgnId() {
		return inOrgnId;
	}

	public void setInOrgnId(String inOrgnId) {
		this.inOrgnId = inOrgnId;
	}

	public String getOutOrgnId() {
		return outOrgnId;
	}

	public void setOutOrgnId(String outOrgnId) {
		this.outOrgnId = outOrgnId;
	}

	public String getSlsDt() {
		return slsDt;
	}

	public void setSlsDt(String slsDt) {
		this.slsDt = slsDt;
	}

	public String getCanDt() {
		return canDt;
	}

	public void setCanDt(String canDt) {
		this.canDt = canDt;
	}
}