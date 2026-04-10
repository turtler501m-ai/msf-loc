package com.ktmmobile.mcp.phone.dto;

import java.io.Serializable;
import java.util.List;

import com.ktmmobile.mcp.common.mspservice.dto.MspSalePlcyMstDto;

/**
 * @Class Name : CommonSearchDto
 * @Description : 공통으로 사요되는 조회 조건 정보를 가지고 있는 DTO클래스이다.
 *
 * @author : ant
 * @Create Date : 2015. 12. 31.
 */
public class CommonSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 업체코드 */
    private String orgnId;

    /** 검색카테고리 */
    private String searchCategoryId;

    /** 검색값 */
    private String searchValue;

    /** 판매여부YN */
    private String saleYn;

    /** 판매여부yn(검색조건용) */
    private String searchSaleYn;

    /** 노출여부YN */
    private String showYn;

    /** 노출여부YN(검색조건용) */
    private String searchShowYn;

    /** 중고폰 여부 YN*/
    private String shandYn;

    /** 정책type cd*/
    private String plcyTypeCd;

    /** 제조사 id */
    private String makrCd;

    /** 핸드폰 type (LTE:04,3G:03 )*/
    private String prodCtgId;

    /** 중고폰분류값 (중고폰:01,외산폰:02) */
    private String shandType;

    /** 대표상품엽 YN*/
    private String rprsYn;

    /** 팝업형태 */
    private String popupMakeClick;

    /** page no*/
    private int pageNo;

    /** 상품 분류 (일반 :01, 0원 상품 :02)*/
    private String prodType;

    /** 기획전 구분 코드 */
    private String ctgCd ;

    private int apiParam1 = 0;
    private int apiParam2 = 0;

    /** 자급제폰 여부(Y : 자급제폰, N: 자급제폰아님) */
    private String sesplsYn;

    private String prodId;

    /** 정책 리스트(상품리스트에서 정책정보가 존재하는 상품만 끓어오기위한 정책정보 리스트) */
    private List<MspSalePlcyMstDto> listMspSaleDto;

    public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

    public List<MspSalePlcyMstDto> getListMspSaleDto() {
        return listMspSaleDto;
    }

    public void setListMspSaleDto(List<MspSalePlcyMstDto> listMspSaleDto) {
        this.listMspSaleDto = listMspSaleDto;
    }

    public String getShandType() {
        return shandType;
    }

    public void setShandType(String shandType) {
        this.shandType = shandType;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public String getSearchCategoryId() {
        return searchCategoryId;
    }

    public void setSearchCategoryId(String searchCategoryId) {
        this.searchCategoryId = searchCategoryId;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSaleYn() {
        return saleYn;
    }

    public void setSaleYn(String saleYn) {
        this.saleYn = saleYn;
    }

    public String getShowYn() {
        return showYn;
    }

    public void setShowYn(String showYn) {
        this.showYn = showYn;
    }

    public String getShandYn() {
        return shandYn;
    }

    public void setShandYn(String shandYn) {
        this.shandYn = shandYn;
    }

    public String getPlcyTypeCd() {
        return plcyTypeCd;
    }

    public void setPlcyTypeCd(String plcyTypeCd) {
        this.plcyTypeCd = plcyTypeCd;
    }

    public String getMakrCd() {
        return makrCd;
    }

    public void setMakrCd(String makrCd) {
        this.makrCd = makrCd;
    }

    public String getProdCtgId() {
        return prodCtgId;
    }

    public void setProdCtgId(String prodCtgId) {
        this.prodCtgId = prodCtgId;
    }

    public String getRprsYn() {
        return rprsYn;
    }

    public void setRprsYn(String rprsYn) {
        this.rprsYn = rprsYn;
    }

    public String getpopupMakeClick() {
        return popupMakeClick;
    }

    public void setpopupMakeClick(String popupMakeClick) {
        this.popupMakeClick = popupMakeClick;
    }

    public String getSearchSaleYn() {
        return searchSaleYn;
    }

    public void setSearchSaleYn(String searchSaleYn) {
        this.searchSaleYn = searchSaleYn;
    }

    public String getSearchShowYn() {
        return searchShowYn;
    }

    public void setSearchShowYn(String searchShowYn) {
        this.searchShowYn = searchShowYn;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

	public int getApiParam1() {
		return apiParam1;
	}

	public void setApiParam1(int apiParam1) {
		this.apiParam1 = apiParam1;
	}

	public int getApiParam2() {
		return apiParam2;
	}

	public void setApiParam2(int apiParam2) {
		this.apiParam2 = apiParam2;
	}

	public String getSesplsYn() {
		return sesplsYn;
	}

	public void setSesplsYn(String sesplsYn) {
		this.sesplsYn = sesplsYn;
	}


    public String getCtgCd() {
        return ctgCd;
    }

    public void setCtgCd(String ctgCd) {
        this.ctgCd = ctgCd;
    }
}