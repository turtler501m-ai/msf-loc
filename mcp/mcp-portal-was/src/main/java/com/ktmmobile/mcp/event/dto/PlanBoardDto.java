package com.ktmmobile.mcp.event.dto;

import java.util.List;
import java.io.Serializable;


/**
 * @Class Name : PlanBoardDto
 * @Description : 기획전 상품기획 정보
 * @author : ant
 * @Create Date : 2016. 1. 13
 */
public class PlanBoardDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int plnSeq;//이벤트중 기획전 일련번호
    
    private int eventBasCd;//상위 이벤트 seq

    private String plnOdrg;//기획전 소분류 노출순서

    private String plnTitle;//기획전 소분류 제목

    private String plnRmark;//기획전 소분류 비고

    private String cretId;//생성자 id

    private String amdId;//수정자 id

    private String cretDt;//생성일자

    private String amdDt;//수정일자

    private String eventUrlAdr;

    private String plnSmallTitle;

    private String plnContent;

    private List<PlanBoardDto> multiPlanBoardDto;	//여러세트가 들어오는것을 처리하기위한 list 모델

    private List<PlanProductDto> prodList;	//기획전의 상품들의 세트

    public int getPlnSeq() {
        return plnSeq;
    }

    public void setPlnSeq(int plnSeq) {
        this.plnSeq = plnSeq;
    }

    public int getEventBasCd() {
        return eventBasCd;
    }

    public void setEventBasCd(int eventBasCd) {
        this.eventBasCd = eventBasCd;
    }

    public String getPlnOdrg() {
        return plnOdrg;
    }

    public void setPlnOdrg(String plnOdrg) {
        this.plnOdrg = plnOdrg;
    }



    public String getPlnTitle() {
        return plnTitle;
    }

    public void setPlnTitle(String plnTitle) {
        this.plnTitle = plnTitle;
    }

    public String getPlnRmark() {
        return plnRmark;
    }

    public void setPlnRmark(String plnRmark) {
        this.plnRmark = plnRmark;
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

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }

    public String getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }

    public String getEventUrlAdr() {
        return eventUrlAdr;
    }

    public void setEventUrlAdr(String eventUrlAdr) {
        this.eventUrlAdr = eventUrlAdr;
    }

    public String getPlnSmallTitle() {
        return plnSmallTitle;
    }

    public void setPlnSmallTitle(String plnSmallTitle) {
        this.plnSmallTitle = plnSmallTitle;
    }

    public String getPlnContent() {
        return plnContent;
    }

    public void setPlnContent(String plnContent) {
        this.plnContent = plnContent;
    }

    public List<PlanBoardDto> getMultiPlanBoardDto() {
        return multiPlanBoardDto;
    }

    public void setMultiPlanBoardDto(List<PlanBoardDto> multiPlanBoardDto) {
        this.multiPlanBoardDto = multiPlanBoardDto;
    }

    public List<PlanProductDto> getProdList() {
        return prodList;
    }

    public void setProdList(List<PlanProductDto> prodList) {
        this.prodList = prodList;
    }


}
