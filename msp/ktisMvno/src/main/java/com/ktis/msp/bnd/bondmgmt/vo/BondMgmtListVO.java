package com.ktis.msp.bnd.bondmgmt.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="bondMgmtListVO")
public class BondMgmtListVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String bondSaleNum;
	private List<BondMgmtVO> items;
	private long bondSaleSeqNum;
	
	/**
	 * @return the bondSaleNum
	 */
	public String getBondSaleNum() {
		return bondSaleNum;
	}
	/**
	 * @param bondSaleNum the bondSaleNum to set
	 */
	public void setBondSaleNum(String bondSaleNum) {
		this.bondSaleNum = bondSaleNum;
	}
	/**
	 * @return the items
	 */
	public List<BondMgmtVO> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<BondMgmtVO> items) {
		this.items = items;
	}
	/**
	 * @return the bondSaleSeqNum
	 */
	public long getBondSaleSeqNum() {
		return bondSaleSeqNum;
	}
	/**
	 * @param bondSaleSeqNum the bondSaleSeqNum to set
	 */
	public void setBondSaleSeqNum(long bondSaleSeqNum) {
		this.bondSaleSeqNum = bondSaleSeqNum;
	}
	
}