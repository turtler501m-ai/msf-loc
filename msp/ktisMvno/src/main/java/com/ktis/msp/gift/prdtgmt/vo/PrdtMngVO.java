package com.ktis.msp.gift.prdtgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class PrdtMngVO extends BaseVo implements Serializable {

	/** 사은품 프로모션 제품 관리 **/
	private String prdtId;               /** 제품ID       */                         
	private String maker;              /** 제조사         */
	private String prdtNm;             /** 제품명          */
	private String prdtDesc;            /** 제품상세설명 */
	private String outUnitPric;        /** 단가           */
	private String useYn;              /** 사용여부        */
	private String imgFile;               /** 저장된이미지파일명    */
	private String webUrl;               /** 이미지파일명    */
	
	private String orgImgFile;          /** 이미지파일명       */
	
	private String fileId1;                /** 이미지 파일 id     */
	private String fileName1;           /** 이미지 파일 Name */
	/** 사용자별 제품관계를 위한 추가 */
	private String orgnId;           /**발주사 조직ID   */
	
	
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getPrdtDesc() {
		return prdtDesc;
	}
	public void setPrdtDesc(String prdtDesc) {
		this.prdtDesc = prdtDesc;
	}
	public String getOutUnitPric() {
		return outUnitPric;
	}
	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getImgFile() {
		return imgFile;
	}
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getOrgImgFile() {
		return orgImgFile;
	}
	public void setOrgImgFile(String orgImgFile) {
		this.orgImgFile = orgImgFile;
	}
	public String getFileId1() {
		return fileId1;
	}
	public void setFileId1(String fileId1) {
		this.fileId1 = fileId1;
	}
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	
	

	
}
