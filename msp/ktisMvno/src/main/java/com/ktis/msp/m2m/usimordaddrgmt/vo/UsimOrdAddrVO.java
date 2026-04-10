package com.ktis.msp.m2m.usimordaddrgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class UsimOrdAddrVO extends BaseVo implements Serializable {

	/** M2M USIM 발주사 배송지 관리 **/
	private String seq;             /**SEQUENCE  */
	private String orgnId;          /**조직ID  */
	
	private String areaNm;          /**배송지명  */
	private String mngNm;           /**배송지담당자  */
	
	private String telFn1;          /**배송지담당자유선전화번호 지역번호  */
	private String telMn1;         /**배송지담당자유선전화번호 국번  */
	private String telRn1;          /**배송지담당자유선전화번호 전화번호  */

	private String telFn2;          /**배송지담당자유선전화번호 지역번호  */
	private String telMn2;         /**배송지담당자유선전화번호 국번  */
	private String telRn2;          /**배송지담당자유선전화번호 전화번호  */

	private String useYn;           /**주소사용여부  */
	private String post;            /**우편번호  */
	private String addr;            /**주소  */
	private String addrBjd;         /**법정동주소  */
	private String addrDtl;         /**상세주소  */
	
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
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
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	
	
	
}
