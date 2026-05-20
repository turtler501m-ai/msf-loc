package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공통으로 사용되는 조회 조건 정보를 가지고 있는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class CommonSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orgnId;          // 업체코드
    private String searchCategoryId; // 검색카테고리
    private String searchValue;     // 검색값
    private String saleYn;          // 판매여부YN
    private String searchSaleYn;    // 판매여부YN (검색조건용)
    private String showYn;          // 노출여부YN
    private String searchShowYn;    // 노출여부YN (검색조건용)
    private String shandYn;         // 중고폰 여부 YN
    private String plcyTypeCd;      // 정책type cd
    private String makrCd;          // 제조사 id
    private String prodCtgId;       // 핸드폰 type (LTE:04, 3G:03)
    private String shandType;       // 중고폰분류값 (중고폰:01, 외산폰:02)
    private String rprsYn;          // 대표상품여부 YN
    private String popupMakeClick;  // 팝업형태
    private int pageNo;             // page no
    private String prodType;        // 상품 분류 (일반:01, 0원상품:02)
    private int apiParam1 = 0;
    private int apiParam2 = 0;
    private String sesplsYn;        // 자급제폰 여부 (Y:자급제폰, N:자급제폰아님)
    private String prodId;
    private String ctgCd;           // 기획전 구분 코드
    private List<MspSalePlcyMstDto> listMspSaleDto; // 정책 리스트

    // popupMakeClick 비표준 getter/setter 유지
    public String getpopupMakeClick() {
        return popupMakeClick;
    }

    public void setpopupMakeClick(String popupMakeClick) {
        this.popupMakeClick = popupMakeClick;
    }

}
