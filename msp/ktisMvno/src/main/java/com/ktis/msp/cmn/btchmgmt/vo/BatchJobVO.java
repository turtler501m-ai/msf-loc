package com.ktis.msp.cmn.btchmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @author TREXSHIN
 *
 */
public class BatchJobVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3288964752232587032L;
	
	private String searchBatchJobNm;
	private String searchDutySctn;
	private String searchUsgYn;
	private String searchBatchTypeCd;

	private String searchStartDate;
	private String searchEndDate;
	private String searchBatchJobId;
	private String searchStatCd;
	private String searchExecService;
	
	private String batchJobId;
	private String batchJobNm;
	private String batchJobDsc;
	private String dutySctn;
	private String execService;
	private String batchTypeCd;
	private String execCron;
	private String usgYn;
	private String execTmCd;
	
	private String saveType;
	
	private String execParam;
	private String execTypeCd;
	
	private String logYn;
	
	
	private String menuId;
	private String usrId;
	private String usrNm;
	private String orgnId;
	private String dutyNm;
	private String menuNm;
	private String searchVal;
	private Integer exclDnldId;
	//2024-04-28  박민건 추가
	private String serverType;	//배치번호(01: 기존배치, 02: 신규배치)
	private Integer exclDnldYn = 0; //엑셀 다운로드 권한(admin,DEV제외처리용)




	public String getSearchBatchTypeCd() {
		return searchBatchTypeCd;
	}
	public void setSearchBatchTypeCd(String searchBatchTypeCd) {
		this.searchBatchTypeCd = searchBatchTypeCd;
	}
	public String getSearchBatchJobNm() {
		return searchBatchJobNm;
	}
	public void setSearchBatchJobNm(String searchBatchJobNm) {
		this.searchBatchJobNm = searchBatchJobNm;
	}
	public String getSearchDutySctn() {
		return searchDutySctn;
	}
	public void setSearchDutySctn(String searchDutySctn) {
		this.searchDutySctn = searchDutySctn;
	}
	public String getSearchUsgYn() {
		return searchUsgYn;
	}
	public void setSearchUsgYn(String searchUsgYn) {
		this.searchUsgYn = searchUsgYn;
	}
	
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public String getSearchBatchJobId() {
		return searchBatchJobId;
	}
	public void setSearchBatchJobId(String searchBatchJobId) {
		this.searchBatchJobId = searchBatchJobId;
	}
	public String getSearchStatCd() {
		return searchStatCd;
	}
	public void setSearchStatCd(String searchStatCd) {
		this.searchStatCd = searchStatCd;
	}
	public String getSearchExecService() {
		return searchExecService;
	}
	public void setSearchExecService(String searchExecService) {
		this.searchExecService = searchExecService;
	}
	
	public String getBatchJobId() {
		return batchJobId;
	}
	public void setBatchJobId(String batchJobId) {
		this.batchJobId = batchJobId;
	}
	public String getBatchJobNm() {
		return batchJobNm;
	}
	public void setBatchJobNm(String batchJobNm) {
		this.batchJobNm = batchJobNm;
	}
	public String getBatchJobDsc() {
		return batchJobDsc;
	}
	public void setBatchJobDsc(String batchJobDsc) {
		this.batchJobDsc = batchJobDsc;
	}
	public String getDutySctn() {
		return dutySctn;
	}
	public void setDutySctn(String dutySctn) {
		this.dutySctn = dutySctn;
	}
	public String getExecService() {
		return execService;
	}
	public void setExecService(String execService) {
		this.execService = execService;
	}
	public String getBatchTypeCd() {
		return batchTypeCd;
	}
	public void setBatchTypeCd(String batchTypeCd) {
		this.batchTypeCd = batchTypeCd;
	}
	public String getExecCron() {
		return execCron;
	}
	public void setExecCron(String execCron) {
		this.execCron = execCron;
	}
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
	}
	public String getExecTmCd() {
		return execTmCd;
	}
	public void setExecTmCd(String execTmCd) {
		this.execTmCd = execTmCd;
	}
	
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	
	public String getExecParam() {
		return execParam;
	}
	public void setExecParam(String execParam) {
		this.execParam = execParam;
	}
	public String getExecTypeCd() {
		return execTypeCd;
	}
	public void setExecTypeCd(String execTypeCd) {
		this.execTypeCd = execTypeCd;
	}
	public String getLogYn() {
		return logYn;
	}
	public void setLogYn(String logYn) {
		this.logYn = logYn;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getDutyNm() {
		return dutyNm;
	}
	public void setDutyNm(String dutyNm) {
		this.dutyNm = dutyNm;
	}
	public Integer getExclDnldId() {
		return exclDnldId;
	}
	public void setExclDnldId(Integer exclDnldId) {
		this.exclDnldId = exclDnldId;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public Integer getExclDnldYn() {
		return exclDnldYn;
	}
	public void setExclDnldYn(Integer exclDnldYn) {
		this.exclDnldYn = exclDnldYn;
	}
	
}
