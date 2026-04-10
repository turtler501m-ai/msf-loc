package com.ktis.msp.pps.orgmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsOnlineOrderVo
 * @Description : 온라인주문내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsOnlineOrderVo")
public class PpsOnlineOrderVo extends BaseVo  implements Serializable {
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	
	public static final String TABLE = "PPS_ONLINE_ORDER";

	/**
	 * SEQ:number(20)
	 */
	private int orderSeq;

	/**
	 * 품목:varchar2(50)
	 */
	private String orderItem;

	/**
	 * 모델명:varchar2(50)
	 */
	private String modelNm;

	/**
	 * 수량:number(20)
	 */
	private int amount;

	/**
	 * 배송일자:date(0)
	 */
	private String shipDate;

	/**
	 * 배송주소:varchar2(100)
	 */
	private String shipAddress;

	/**
	 * 담당자명:varchar2(20)
	 */
	private String opNm;

	/**
	 * 담당자핸드폰번호:varchar2(20)
	 */
	private String opPhone;

	/**
	 * 대리점메모:varchar2(200)
	 */
	private String agentMemo;

	/**
	 * 등록자:varchar2(20)
	 */
	private String agentId;

	/**
	 * 요청일자:date(0)
	 */
	private String reqDate;

	/**
	 * 등록일자:date(0)
	 */
	private String recordDate;

	/**
	 * 처리상태:char(1)
	 */
	private String status;

	/**
	 * STATUS 변경날짜:date(0)
	 */
	private String opDate;

	/**
	 * 등록대리점 ID:varchar2(20)
	 */
	private String adminId;

	/**
	 * 본사관리자:varchar2(20)
	 */
	private String bonsaAdminId;

	/**
	 * 관리자메모:varchar2(200)
	 */
	private String adminMemo;

	/**
	 * 완료일자:date(0)
	 */
	private String finishDate;

	

	

	/**
	 * @return the orderSeq
	 */
	public int getOrderSeq() {
		return orderSeq;
	}





	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}





	/**
	 * @return the orderItem
	 */
	public String getOrderItem() {
		return orderItem;
	}





	/**
	 * @param orderItem the orderItem to set
	 */
	public void setOrderItem(String orderItem) {
		this.orderItem = orderItem;
	}





	/**
	 * @return the modelNm
	 */
	public String getModelNm() {
		return modelNm;
	}





	/**
	 * @param modelNm the modelNm to set
	 */
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}





	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}





	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}





	/**
	 * @return the shipDate
	 */
	public String getShipDate() {
		return shipDate;
	}





	/**
	 * @param shipDate the shipDate to set
	 */
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}





	/**
	 * @return the shipAddress
	 */
	public String getShipAddress() {
		return shipAddress;
	}





	/**
	 * @param shipAddress the shipAddress to set
	 */
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}





	/**
	 * @return the opNm
	 */
	public String getOpNm() {
		return opNm;
	}





	/**
	 * @param opNm the opNm to set
	 */
	public void setOpNm(String opNm) {
		this.opNm = opNm;
	}





	/**
	 * @return the opPhone
	 */
	public String getOpPhone() {
		return opPhone;
	}





	/**
	 * @param opPhone the opPhone to set
	 */
	public void setOpPhone(String opPhone) {
		this.opPhone = opPhone;
	}





	/**
	 * @return the agentMemo
	 */
	public String getAgentMemo() {
		return agentMemo;
	}





	/**
	 * @param agentMemo the agentMemo to set
	 */
	public void setAgentMemo(String agentMemo) {
		this.agentMemo = agentMemo;
	}





	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}





	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}





	/**
	 * @return the reqDate
	 */
	public String getReqDate() {
		return reqDate;
	}





	/**
	 * @param reqDate the reqDate to set
	 */
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}





	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}





	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}





	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}





	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}





	/**
	 * @return the opDate
	 */
	public String getOpDate() {
		return opDate;
	}





	/**
	 * @param opDate the opDate to set
	 */
	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}





	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}





	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}





	/**
	 * @return the bonsaAdminId
	 */
	public String getBonsaAdminId() {
		return bonsaAdminId;
	}





	/**
	 * @param bonsaAdminId the bonsaAdminId to set
	 */
	public void setBonsaAdminId(String bonsaAdminId) {
		this.bonsaAdminId = bonsaAdminId;
	}





	/**
	 * @return the adminMemo
	 */
	public String getAdminMemo() {
		return adminMemo;
	}





	/**
	 * @param adminMemo the adminMemo to set
	 */
	public void setAdminMemo(String adminMemo) {
		this.adminMemo = adminMemo;
	}





	/**
	 * @return the finishDate
	 */
	public String getFinishDate() {
		return finishDate;
	}





	/**
	 * @param finishDate the finishDate to set
	 */
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}





	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	

}
