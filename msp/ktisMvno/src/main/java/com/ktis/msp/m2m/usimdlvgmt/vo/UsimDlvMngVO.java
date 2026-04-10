package com.ktis.msp.m2m.usimdlvgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class UsimDlvMngVO extends BaseVo implements Serializable {

	/** M2M USIM 주문 관리 **/
	private String ordId;            /**주문번호   */
	private String ordDate;          /**주문일자   */
	private String orgnId;           /**발주사 조직ID   */
	private String areaNm;           /**배송지명   */
	private String mngNm;            /**배송지담당자   */
	private String telFn1;           /**전화지역번호1   */
	private String telMn1;           /**전화국번1   */
	private String telRn1;           /**전화번호1   */
	private String telFn2;           /**전화지역번호2   */
	private String telMn2;           /**전화국번2   */
	private String telRn2;           /**전화번호2   */
	private String ordStatus;       /**배송상태   */
	private String ordInfo;          /**요청사항   */

	private String dlvMethod;    /**배송방식코드 */
	private String dlvTelNo;       /**배송연락처 */
	private String tbCd;          /**택배사코드(RCP0025)   */
	private String invNo;        /**송장번호   */
	private String takeDate;    /**인수일자   */
	private String willTakeDate;    /**인수희망일자   */
	private String sendDate;   /**발송일자   */
	
	private String post;             /**우편번호   */
	private String addr;             /**주소   */
	private String addrBjd;          /**법정동주소   */
	private String addrDtl;          /**상세주소   */
	
	private String seq;              /**주문상품순번   */
	private String rprsPrdtId;       /**대표제품ID   */
	private String ordCnt;           /**주문수량   */
	private String delYn;            /**삭제여부(Y이면삭제)   */
	
	/* 주문수정 시 선택된 최종 제품정보*/
	private String itemData;       /**제품정보*/
	
	private String printGubun;    /**1:발주서,2:인수확인서,3:견적서구분*/
	
	
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getOrdDate() {
		return ordDate;
	}
	public void setOrdDate(String ordDate) {
		this.ordDate = ordDate;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getAreaNm() {
		return areaNm;
	}
	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}
	public String getMngNm() {
		return mngNm;
	}
	public void setMngNm(String mngNm) {
		this.mngNm = mngNm;
	}
	public String getTelFn1() {
		return telFn1;
	}
	public void setTelFn1(String telFn1) {
		this.telFn1 = telFn1;
	}
	public String getTelMn1() {
		return telMn1;
	}
	public void setTelMn1(String telMn1) {
		this.telMn1 = telMn1;
	}
	public String getTelRn1() {
		return telRn1;
	}
	public void setTelRn1(String telRn1) {
		this.telRn1 = telRn1;
	}
	public String getTelFn2() {
		return telFn2;
	}
	public void setTelFn2(String telFn2) {
		this.telFn2 = telFn2;
	}
	public String getTelMn2() {
		return telMn2;
	}
	public void setTelMn2(String telMn2) {
		this.telMn2 = telMn2;
	}
	public String getTelRn2() {
		return telRn2;
	}
	public void setTelRn2(String telRn2) {
		this.telRn2 = telRn2;
	}
	public String getOrdStatus() {
		return ordStatus;
	}
	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}
	public String getOrdInfo() {
		return ordInfo;
	}
	public void setOrdInfo(String ordInfo) {
		this.ordInfo = ordInfo;
	}
	
	
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public String getInvNo() {
		return invNo;
	}
	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}
	public String getTakeDate() {
		return takeDate;
	}
	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}
	
	public String getWillTakeDate() {
		return willTakeDate;
	}
	public void setWillTakeDate(String willTakeDate) {
		this.willTakeDate = willTakeDate;
	}
	
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddrBjd() {
		return addrBjd;
	}
	public void setAddrBjd(String addrBjd) {
		this.addrBjd = addrBjd;
	}
	public String getAddrDtl() {
		return addrDtl;
	}
	public void setAddrDtl(String addrDtl) {
		this.addrDtl = addrDtl;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getRprsPrdtId() {
		return rprsPrdtId;
	}
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}
	public String getOrdCnt() {
		return ordCnt;
	}
	public void setOrdCnt(String ordCnt) {
		this.ordCnt = ordCnt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getItemData() {
		return itemData;
	}
	public void setItemData(String itemData) {
		this.itemData = itemData;
	}
	public String getPrintGubun() {
		return printGubun;
	}
	public void setPrintGubun(String printGubun) {
		this.printGubun = printGubun;
	}
	public String getDlvMethod() {
		return dlvMethod;
	}
	public void setDlvMethod(String dlvMethod) {
		this.dlvMethod = dlvMethod;
	}
	public String getDlvTelNo() {
		return dlvTelNo;
	}
	public void setDlvTelNo(String dlvTelNo) {
		this.dlvTelNo = dlvTelNo;
	}
	
	
	
	
	
}
