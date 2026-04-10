package com.ktmmobile.mcp.common.mspservice.dto;

import java.util.List;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * @Class Name : MspSaleDto
 * @Description : Msp 판매 단품정보와
 *   Msp 판매정책정보를 멤버 필드로 가지고 있는
 *   판매Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public class MspSaleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** MSP판매상품 */
    private MspSalePrdtMstDto mspSalePrdMstDto;

    /** MSP판매정책 (단말할인,요금할인)에 따른 2가지까지 나올수잇다.*/
    private List<MspSalePlcyMstDto>	mspSalePlcyMstDto;

    /** MSP 판매정책 정보 한개일경우 해당정책을 2개일경우 단말할인을 가져온다. */
    private MspSalePlcyMstDto mspSalePlcyMstDtoSimbol;

    /** SQL 문에서 비고(RMK) 필드 조회 여부를 위해서 */
    private String forCompareYn;

    public String getForCompareYn() {
        return forCompareYn;
    }

    public void setForCompareYn(String forCompareYn) {
        this.forCompareYn = forCompareYn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public MspSalePlcyMstDto getMspSalePlcyMstDtoSimbol() {
        return mspSalePlcyMstDtoSimbol;
    }

    public void setMspSalePlcyMstDtoSimbol(MspSalePlcyMstDto mspSalePlcyMstDtoSimbol) {
        this.mspSalePlcyMstDtoSimbol = mspSalePlcyMstDtoSimbol;
    }

    public MspSalePrdtMstDto getMspSalePrdMstDto() {
        return mspSalePrdMstDto;
    }

    public void setMspSalePrdMstDto(MspSalePrdtMstDto mspSalePrdMstDto) {
        this.mspSalePrdMstDto = mspSalePrdMstDto;
    }

    public void setMspSalePlcyMstDto(List<MspSalePlcyMstDto> mspSalePlcyMstDto) {
        this.mspSalePlcyMstDto = mspSalePlcyMstDto;
    }

    public List<MspSalePlcyMstDto> getMspSalePlcyMstDto() {
        return mspSalePlcyMstDto;
    }
}
