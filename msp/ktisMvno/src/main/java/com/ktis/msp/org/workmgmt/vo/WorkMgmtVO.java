package com.ktis.msp.org.workmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.io.Serializable;
import java.util.List;

public class WorkMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = -6510970033252597452L;
	private String workId;
	private String workNm;
	private String workTmplId;
	private String workTmplNm;
	private String itemId;
	private String itemNm;
	private String ownerId;
	private String ownerNm;
	private String rqstrId;
	private String rqstrNm;
	private String smsTemplateId;
	private String name;
	private String status;
	private String requestYn;
	private String deleteDate;
	private String startDt;
	private String endDt;
	private String searchBaseDate;
	private String rowCheck;
	private String useYn;
	private String useYnNm;

	List<WorkMgmtVO> itemList;
	List<WorkMgmtVO> ownerList;
	List<WorkMgmtVO> rqstrList;
	List<WorkMgmtVO> smsTemplateList;

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getWorkNm() {
		return workNm;
	}

	public void setWorkNm(String workNm) {
		this.workNm = workNm;
	}

	public String getWorkTmplId() {
		return workTmplId;
	}

	public void setWorkTmplId(String workTmplId) {
		this.workTmplId = workTmplId;
	}

	public String getWorkTmplNm() {
		return workTmplNm;
	}

	public void setWorkTmplNm(String workTmplNm) {
		this.workTmplNm = workTmplNm;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public String getRqstrId() {
		return rqstrId;
	}

	public void setRqstrId(String rqstrId) {
		this.rqstrId = rqstrId;
	}

	public String getRqstrNm() {
		return rqstrNm;
	}

	public void setRqstrNm(String rqstrNm) {
		this.rqstrNm = rqstrNm;
	}

	public String getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestYn() {
		return requestYn;
	}

	public void setRequestYn(String requestYn) {
		this.requestYn = requestYn;
	}

	public String getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getSearchBaseDate() {
		return searchBaseDate;
	}

	public void setSearchBaseDate(String searchBaseDate) {
		this.searchBaseDate = searchBaseDate;
	}

	public String getRowCheck() {
		return rowCheck;
	}

	public void setRowCheck(String rowCheck) {
		this.rowCheck = rowCheck;
	}

	public String getUseYn() {
		return useYn;
	}

	public String getUseYnNm() {
		return useYnNm;
	}

	public void setUseYnNm(String useYnNm) {
		this.useYnNm = useYnNm;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public List<WorkMgmtVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<WorkMgmtVO> itemList) {
		this.itemList = itemList;
	}

	public List<WorkMgmtVO> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(List<WorkMgmtVO> ownerList) {
		this.ownerList = ownerList;
	}

	public List<WorkMgmtVO> getRqstrList() {
		return rqstrList;
	}

	public void setRqstrList(List<WorkMgmtVO> rqstrList) {
		this.rqstrList = rqstrList;
	}

	public List<WorkMgmtVO> getSmsTemplateList() {
		return smsTemplateList;
	}

	public void setSmsTemplateList(List<WorkMgmtVO> smsTemplateList) {
		this.smsTemplateList = smsTemplateList;
	}
}