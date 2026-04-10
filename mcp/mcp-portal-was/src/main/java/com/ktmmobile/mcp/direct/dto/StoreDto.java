package com.ktmmobile.mcp.direct.dto;

import java.util.Date;
import java.io.Serializable;
import java.util.Map;

public class StoreDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private int storSeq;
    private String  storCd;
    private String  storNm;
    private String  storDiv;
    private String  telNo;
    private String  stickerExp;
    private String  trtWrkjob;
    private String  handlProd;
    private String  zipCd;
    private String  storRoadnAdr;
    private String  storArnoAdr;
    private String  dtlAdr;
    private String  latitVal;
    private String  lngitVal;
    private String  markImg;
    private String  storItr;
    private String  showYn;
    private String  cretId;
    private String  amdId;
    private Date  cretDt;
    private Date  amdDt;
    private String subAddr;
    private String orderType;
    /*storeInsertForm 용 DTO*/
    private String roadFullAddr;
    private String roadAddrPart1;
    private String roadAddrPart2;
    private String addrDetail;
    private String engAddr;
    private String jibunAddr;
    private String zipNo;
    private String admCd;
    private String rnMgtSn;
    private String bdMgtSn;
    private String strLat;
    private String strLng;
    private String telNo1;
    private String telNo2;
    private String telNo3;
    private int boardNum;	//리스트의 역순 일련번호
    /*페이징용 map*/
    private int rnum;
    private Map<String, Object> pageMap; // 페이징
    //페이징
    private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
    private int pagingStartNo;		//페이지네이션 시작 변수
    private int pagingEndNo;		//페이지네이션 끝 변수
    private int pagingStart;		//페이지 처음 <<
    private int pagingFront;		//페이지 앞을호 <
    private int pagingNext;			//페이지 다음 >
    private int pagingEnd;			//페이지 마지막 >>
    private int pagingSize;			//페이지 사이즈>>
    private int pageNo;
    /*storeList 에서 검색어*/
    private String schStorDiv;
    private String schContDiv;
    private String searchStr;
    private String schDivStr;
    private String searchYn;
    private String lotateVal;

    private int nfcAbleVal = 0;
    private int nfcDisableVal = 0;
    private int storSort = 0;
    
    /*상품ID*/
    private String prodId;
    
    /*재고수량*/
    private int invCnt;
    
    public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public int getInvCnt() {
		return invCnt;
	}
	public void setInvCnt(int invCnt) {
		this.invCnt = invCnt;
	}
	public String getLotateVal() {
		return lotateVal;
	}
	public void setLotateVal(String lotateVal) {
		this.lotateVal = lotateVal;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSubAddr() {
		return subAddr;
	}
	public void setSubAddr(String subAddr) {
		this.subAddr = subAddr;
	}
	public int getNfcAbleVal() {
		return nfcAbleVal;
	}
	public int getStorSort() {
		return storSort;
	}
	public void setStorSort(int storSort) {
		this.storSort = storSort;
	}
	public void setNfcAbleVal(int nfcAbleVal) {
		this.nfcAbleVal = nfcAbleVal;
	}
	public int getNfcDisableVal() {
		return nfcDisableVal;
	}
	public void setNfcDisableVal(int nfcDisableVal) {
		this.nfcDisableVal = nfcDisableVal;
	}
	public int getStorSeq() {
        return storSeq;
    }
    public void setStorSeq(int storSeq) {
        this.storSeq = storSeq;
    }
    public String getStorCd() {
        return storCd;
    }
    public void setStorCd(String storCd) {
        this.storCd = storCd;
    }
    public String getStorNm() {
        return storNm;
    }
    public void setStorNm(String storNm) {
        this.storNm = storNm;
    }
    public String getStorDiv() {
        return storDiv;
    }
    public void setStorDiv(String storDiv) {
        this.storDiv = storDiv;
    }
    public String getTelNo() {
        return telNo;
    }
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    public String getStickerExp() {
    	return stickerExp;
    }
    public void setStickerExp(String stickerExp) {
        this.stickerExp = stickerExp;
    }
    public String getTrtWrkjob() {
        return trtWrkjob;
    }
    public void setTrtWrkjob(String trtWrkjob) {
        this.trtWrkjob = trtWrkjob;
    }
    public String getHandlProd() {
        return handlProd;
    }
    public void setHandlProd(String handlProd) {
        this.handlProd = handlProd;
    }
    public String getZipCd() {
        return zipCd;
    }
    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }
    public String getStorRoadnAdr() {
        return storRoadnAdr;
    }
    public void setStorRoadnAdr(String storRoadnAdr) {
        this.storRoadnAdr = storRoadnAdr;
    }
    public String getStorArnoAdr() {
        return storArnoAdr;
    }
    public void setStorArnoAdr(String storArnoAdr) {
        this.storArnoAdr = storArnoAdr;
    }
    public String getDtlAdr() {
        return dtlAdr;
    }
    public void setDtlAdr(String dtlAdr) {
        this.dtlAdr = dtlAdr;
    }
    public String getLatitVal() {
        return latitVal;
    }
    public void setLatitVal(String latitVal) {
        this.latitVal = latitVal;
    }
    public String getLngitVal() {
        return lngitVal;
    }
    public void setLngitVal(String lngitVal) {
        this.lngitVal = lngitVal;
    }
    public String getMarkImg() {
        return markImg;
    }
    public void setMarkImg(String markImg) {
        this.markImg = markImg;
    }
    public String getStorItr() {
        return storItr;
    }
    public void setStorItr(String storItr) {
        this.storItr = storItr;
    }
    public String getShowYn() {
        return showYn;
    }
    public void setShowYn(String showYn) {
        this.showYn = showYn;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }
    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }
    public String getRoadFullAddr() {
        return roadFullAddr;
    }
    public void setRoadFullAddr(String roadFullAddr) {
        this.roadFullAddr = roadFullAddr;
    }
    public String getRoadAddrPart1() {
        return roadAddrPart1;
    }
    public void setRoadAddrPart1(String roadAddrPart1) {
        this.roadAddrPart1 = roadAddrPart1;
    }
    public String getRoadAddrPart2() {
        return roadAddrPart2;
    }
    public void setRoadAddrPart2(String roadAddrPart2) {
        this.roadAddrPart2 = roadAddrPart2;
    }
    public String getAddrDetail() {
        return addrDetail;
    }
    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }
    public String getEngAddr() {
        return engAddr;
    }
    public void setEngAddr(String engAddr) {
        this.engAddr = engAddr;
    }
    public String getJibunAddr() {
        return jibunAddr;
    }
    public void setJibunAddr(String jibunAddr) {
        this.jibunAddr = jibunAddr;
    }
    public String getZipNo() {
        return zipNo;
    }
    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }
    public String getAdmCd() {
        return admCd;
    }
    public void setAdmCd(String admCd) {
        this.admCd = admCd;
    }
    public String getRnMgtSn() {
        return rnMgtSn;
    }
    public void setRnMgtSn(String rnMgtSn) {
        this.rnMgtSn = rnMgtSn;
    }
    public String getBdMgtSn() {
        return bdMgtSn;
    }
    public void setBdMgtSn(String bdMgtSn) {
        this.bdMgtSn = bdMgtSn;
    }

    public String getStrLat() {
        return strLat;
    }
    public void setStrLat(String strLat) {
        this.strLat = strLat;
    }
    public String getStrLng() {
        return strLng;
    }
    public void setStrLng(String strLng) {
        this.strLng = strLng;
    }
    public String getTelNo1() {
        return telNo1;
    }
    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1;
    }
    public String getTelNo2() {
        return telNo2;
    }
    public void setTelNo2(String telNo2) {
        this.telNo2 = telNo2;
    }
    public String getTelNo3() {
        return telNo3;
    }
    public void setTelNo3(String telNo3) {
        this.telNo3 = telNo3;
    }
    public int getBoardNum() {
        return boardNum;
    }
    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }
    public int getRnum() {
        return rnum;
    }
    public void setRnum(int rnum) {
        this.rnum = rnum;
    }
    public Map<String, Object> getPageMap() {
        return pageMap;
    }
    public void setPageMap(Map<String, Object> pageMap) {
        this.pageMap = pageMap;
    }
    public int getPagingPosition() {
        return pagingPosition;
    }
    public void setPagingPosition(int pagingPosition) {
        this.pagingPosition = pagingPosition;
    }
    public int getPagingStartNo() {
        return pagingStartNo;
    }
    public void setPagingStartNo(int pagingStartNo) {
        this.pagingStartNo = pagingStartNo;
    }
    public int getPagingEndNo() {
        return pagingEndNo;
    }
    public void setPagingEndNo(int pagingEndNo) {
        this.pagingEndNo = pagingEndNo;
    }
    public int getPagingStart() {
        return pagingStart;
    }
    public void setPagingStart(int pagingStart) {
        this.pagingStart = pagingStart;
    }
    public int getPagingFront() {
        return pagingFront;
    }
    public void setPagingFront(int pagingFront) {
        this.pagingFront = pagingFront;
    }
    public int getPagingNext() {
        return pagingNext;
    }
    public void setPagingNext(int pagingNext) {
        this.pagingNext = pagingNext;
    }
    public int getPagingEnd() {
        return pagingEnd;
    }
    public void setPagingEnd(int pagingEnd) {
        this.pagingEnd = pagingEnd;
    }
    public int getPagingSize() {
        return pagingSize;
    }
    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public String getSearchStr() {
        return searchStr;
    }
    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
    public String getSchStorDiv() {
        return schStorDiv;
    }
    public void setSchStorDiv(String schStorDiv) {
        this.schStorDiv = schStorDiv;
    }
    public String getSchContDiv() {
        return schContDiv;
    }
    public void setSchContDiv(String schContDiv) {
        this.schContDiv = schContDiv;
    }
    public String getSchDivStr() {
        return schDivStr;
    }
    public void setSchDivStr(String schDivStr) {
        this.schDivStr = schDivStr;
    }
    public String getSearchYn() {
        return searchYn;
    }
    public void setSearchYn(String searchYn) {
        this.searchYn = searchYn;
    }



}
