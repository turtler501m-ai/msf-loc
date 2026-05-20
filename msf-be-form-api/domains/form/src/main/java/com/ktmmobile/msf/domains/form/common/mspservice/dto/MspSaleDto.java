package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import com.ktmmobile.msf.domains.form.common.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.domains.form.common.dto.MspSalePrdtMstDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @Class Name : MspSaleDto
 * @Description : Msp 판매 단품정보와
 *   Msp 판매정책정보를 멤버 필드로 가지고 있는
 *   판매Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
@Getter
@Setter
@NoArgsConstructor
public class MspSaleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private MspSalePrdtMstDto mspSalePrdMstDto; // MSP판매상품
    private List<MspSalePlcyMstDto> mspSalePlcyMstDto; // MSP판매정책 (단말할인,요금할인)에 따른 2가지까지 나올수잇다.
    private MspSalePlcyMstDto mspSalePlcyMstDtoSimbol; // MSP 판매정책 정보 한개일경우 해당정책을 2개일경우 단말할인을 가져온다.
    private String forCompareYn; // SQL 문에서 비고(RMK) 필드 조회 여부를 위해서

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
