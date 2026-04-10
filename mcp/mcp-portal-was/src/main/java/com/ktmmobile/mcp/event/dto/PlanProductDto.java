package com.ktmmobile.mcp.event.dto;

import java.util.List;
import java.io.Serializable;


/**
 * @Class Name : PlanProductDto
 * @Description : 기획전 상품 정보
 * @author : ant
 * @Create Date : 2016. 1. 13
 */
public class PlanProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int plnDtlSeq;// 이밴트중 기획전 디테일 일련번호
    
    private int prodId;//이벤트중 기획전 상품아이디

    private String plnBasSeq;// 상위 기획전 bas 일련번호

    private String plnProdId;// nmcp_snty_prod_bas의 상품id

    private String plnAtribValCd1;// nmcp_snty_prod_bas의 색상id

    private String plnAtribValCd2;// nmcp_snty_prod_bas의 용량id

    private String tmpPlnBasSeq;
    private String tmpProdId;// nmcp_snty_prod_bas의 상품id의 임시저장소
    private String tmpAtribValCd1;// nmcp_snty_prod_bas의 색상id의 임시저장소
    private String tmpAtribValCd2;// nmcp_snty_prod_bas의 용량id의 임시저장소

    private String plnShowWrdn1;// '기획전 상품하단의 노출문구1

    private String plnShowWrdn2;// 기획전 상품하단의 노출문구2

    private String cretId;// 생성자 id

    private String amdId;// 수정자 id

    private String cretDt;// 생성일자

    private String amdDt;// 수정일자

    private String searchId; //검색카테고리

    private String searchValue;//검색값

    private String prodNm;

    private String atribValNm1;

    private String atribValNm2;

    private String imgPath;

    private List<PlanProductDto> multiPlanProductDto;	//여러세트가 들어오는것을 처리하기위한 list 모델

    private String plnSeq;
    private String eventBasCd;
    private String plnTitle;



    public String getPlnSeq() {
		return plnSeq;
	}

	public void setPlnSeq(String plnSeq) {
		this.plnSeq = plnSeq;
	}

	public String getEventBasCd() {
		return eventBasCd;
	}

	public void setEventBasCd(String eventBasCd) {
		this.eventBasCd = eventBasCd;
	}

	public String getPlnTitle() {
		return plnTitle;
	}

	public void setPlnTitle(String plnTitle) {
		this.plnTitle = plnTitle;
	}

	public int getPlnDtlSeq() {
        return plnDtlSeq;
    }

    public void setPlnDtlSeq(int plnDtlSeq) {
        this.plnDtlSeq = plnDtlSeq;
    }

    public String getPlnBasSeq() {
        return plnBasSeq;
    }

    public void setPlnBasSeq(String plnBasSeq) {
        this.plnBasSeq = plnBasSeq;
    }

    public String getPlnProdId() {
        return plnProdId;
    }

    public void setPlnProdId(String plnProdId) {
        this.plnProdId = plnProdId;
    }

    public String getPlnAtribValCd1() {
        return plnAtribValCd1;
    }

    public void setPlnAtribValCd1(String plnAtribValCd1) {
        this.plnAtribValCd1 = plnAtribValCd1;
    }

    public String getPlnAtribValCd2() {
        return plnAtribValCd2;
    }

    public void setPlnAtribValCd2(String plnAtribValCd2) {
        this.plnAtribValCd2 = plnAtribValCd2;
    }

    public String getTmpProdId() {
        return tmpProdId;
    }

    public void setTmpProdId(String tmpProdId) {
        this.tmpProdId = tmpProdId;
    }

    public String getTmpAtribValCd1() {
        return tmpAtribValCd1;
    }

    public void setTmpAtribValCd1(String tmpAtribValCd1) {
        this.tmpAtribValCd1 = tmpAtribValCd1;
    }

    public String getTmpAtribValCd2() {
        return tmpAtribValCd2;
    }

    public void setTmpAtribValCd2(String tmpAtribValCd2) {
        this.tmpAtribValCd2 = tmpAtribValCd2;
    }

    public String getPlnShowWrdn1() {
        return plnShowWrdn1;
    }

    public void setPlnShowWrdn1(String plnShowWrdn1) {
        this.plnShowWrdn1 = plnShowWrdn1;
    }

    public String getPlnShowWrdn2() {
        return plnShowWrdn2;
    }

    public void setPlnShowWrdn2(String plnShowWrdn2) {
        this.plnShowWrdn2 = plnShowWrdn2;
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

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getAtribValNm1() {
        return atribValNm1;
    }

    public void setAtribValNm1(String atribValNm1) {
        this.atribValNm1 = atribValNm1;
    }

    public String getAtribValNm2() {
        return atribValNm2;
    }

    public void setAtribValNm2(String atribValNm2) {
        this.atribValNm2 = atribValNm2;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<PlanProductDto> getMultiPlanProductDto() {
        return multiPlanProductDto;
    }

    public void setMultiPlanProductDto(List<PlanProductDto> multiPlanProductDto) {
        this.multiPlanProductDto = multiPlanProductDto;
    }

    /**
     * @return the tmpPlnBasSeq
     */
    public String getTmpPlnBasSeq() {
        return tmpPlnBasSeq;
    }

    /**
     * @param tmpPlnBasSeq the tmpPlnBasSeq to set
     */
    public void setTmpPlnBasSeq(String tmpPlnBasSeq) {
        this.tmpPlnBasSeq = tmpPlnBasSeq;
    }

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

}
