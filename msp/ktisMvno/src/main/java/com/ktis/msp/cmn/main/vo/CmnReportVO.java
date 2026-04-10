package com.ktis.msp.cmn.main.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : 
 * @Description : 
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 * @
 * @author : 심정보
 * @Create Date : 2015. 7. 28.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="cmnReportVO")
public class CmnReportVO extends BaseVo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String jrf;			/* jrt 파일명 */
	private String arg;		/* arg */
	private String wgap;			/* 가로크기 */
	private String hgap;		/* 세로크기 */
	
	
	/**
	 * @return the arg
	 */
	public String getArg() {
		return arg;
	}
	/**
	 * @param arg the arg to set
	 */
	public void setArg(String arg) {
		this.arg = arg;
	}
	
	/**
	 * @return the jrf
	 */
	public String getJrf() {
		return jrf;
	}
	/**
	 * @param jrf the jrf to set
	 */
	public void setJrf(String jrf) {
		this.jrf = jrf;
	}
	/**
	 * @return the wgap
	 */
	public String getWgap() {
		return wgap;
	}
	/**
	 * @param wgap the wgap to set
	 */
	public void setWgap(String wgap) {
		this.wgap = wgap;
	}
	/**
	 * @return the hgap
	 */
	public String getHgap() {
		return hgap;
	}
	/**
	 * @param hgap the hgap to set
	 */
	public void setHgap(String hgap) {
		this.hgap = hgap;
	}
	
	
}