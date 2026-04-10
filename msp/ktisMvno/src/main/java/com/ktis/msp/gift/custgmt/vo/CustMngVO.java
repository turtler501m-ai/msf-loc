package com.ktis.msp.gift.custgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class CustMngVO extends BaseVo {

	private static final long serialVersionUID = 3234747999341497150L;
	
	/** 고객사은품 관리 **/
	private String prmtId;              /** 프로모션ID   */
	private String contractNum;         /** 가입계약번호   */
	private String smsSendDate;        /** 문자전송일자   */
	private String giftRegstDate;      /** 사은품신청일   */
	private String mngNm;               /** 배송지담당자   */
	private String telFn1;              /** 전화지역번호1  */
	private String telMn1;              /** 전화국번1    */
	private String telRn1;              /** 전화번호1    */
	private String telFn2;              /** 전화지역번호2  */
	private String telMn2;              /** 전화국번2    */
	private String telRn2;              /** 전화번호2    */
	private String post;                 /** 우편번호     */
	private String addr;                 /** 주소       */
	private String addrBjd;             /** 법정동주소    */
	private String addrDtl;             /** 상세주소     */
	

	/* 주문수정 시 선택된 최종 제품정보*/
	private String itemData;       /**제품정보*/
	
	//List<PrmtResultVO> prmtResultList;   /** 사은품 프로모션 결과 */
	
	private String frDate;             /** 정산월 from     */
	private String toDate;             /** 정산월 to     */
	private String searchGbn;             /** 검색구분     */
	private String searchTxt;             /** 검색어    */
	private String showYn;				/** 노출여부 */
	private String showYnNm;			/** 노출여부명 */
	private String billDt;				/** 정산기준일 */
	private String prmtOrgnId;			/** 주관부서 */
	
	private String requestKey; 				/** request_key 신청등록에서 조회 시 사용 */
	private String menuId;					/** 신청관리: MSP1000015, 개통관리: MSP1000099 */
	
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSmsSendDate() {
		return smsSendDate;
	}
	public void setSmsSendDate(String smsSendDate) {
		this.smsSendDate = smsSendDate;
	}
	public String getGiftRegstDate() {
		return giftRegstDate;
	}
	public void setGiftRegstDate(String giftRegstDate) {
		this.giftRegstDate = giftRegstDate;
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
	
	public String getItemData() {
		return itemData;
	}
	public void setItemData(String itemData) {
		this.itemData = itemData;
	}
	/*
	public List<PrmtResultVO> getPrmtResultList() {
		return prmtResultList;
	}
	public void setPrmtResultList(List<PrmtResultVO> prmtResultList) {
		this.prmtResultList = prmtResultList;
	}
	*/
	public String getFrDate() {
		return frDate;
	}
	public void setFrDate(String frDate) {
		this.frDate = frDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSearchTxt() {
		return searchTxt;
	}
	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}
	
	public String getShowYn() {
		return showYn;
	}
	public void setShowYn(String showYn) {
		this.showYn = showYn;
	}
	public String getShowYnNm() {
		return showYnNm;
	}
	public void setShowYnNm(String showYnNm) {
		this.showYnNm = showYnNm;
	}
	public String getBillDt() {
		return billDt;
	}
	public void setBillDt(String billDt) {
		this.billDt = billDt;
	}
	public String getPrmtOrgnId() {
		return prmtOrgnId;
	}
	public void setPrmtOrgnId(String prmtOrgnId) {
		this.prmtOrgnId = prmtOrgnId;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
