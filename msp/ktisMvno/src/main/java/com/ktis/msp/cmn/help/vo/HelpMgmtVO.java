/**
 * 
 */
package com.ktis.msp.cmn.help.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : HelpMgmtVO
 * @Description : 도움말관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017. 1. 11. 강무성 최초생성
 * @
 * @author : 강무성
 * @Create Date : 2017. 1. 11.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="helpMgmtVO")
public class HelpMgmtVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = -98940809836720841L;
	
	private String menuId;
	private String menuNm;
	private String fileId;
	private String orgnId;
	private String attSctn;
	private String attDttm;
	private String fileNm;
	private String attDsc;
	private String attRout;
	private String regstId;
	private String regDttm;
	private String rvisnId;
	private String rvisnDttm;
	private String fileSeq;

	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getAttSctn() {
		return attSctn;
	}
	public void setAttSctn(String attSctn) {
		this.attSctn = attSctn;
	}
	public String getAttDttm() {
		return attDttm;
	}
	public void setAttDttm(String attDttm) {
		this.attDttm = attDttm;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getAttDsc() {
		return attDsc;
	}
	public void setAttDsc(String attDsc) {
		this.attDsc = attDsc;
	}
	public String getAttRout() {
		return attRout;
	}
	public void setAttRout(String attRout) {
		this.attRout = attRout;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
}
