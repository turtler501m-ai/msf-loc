package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import com.ktmmobile.msf.domains.form.common.dto.MspSalePlcyMstDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Class Name : MspSaleOrgnMstDto
 * @Description :
 * MSP 의 MSP_SALE_ORGN_MST 테이블과 대응되는 DTO 이다.
 * 조직별 정책등록정보를 가지고있다.
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
@Getter
@Setter
@NoArgsConstructor
public class MspSaleOrgnMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private MspSalePlcyMstDto mspSalePlcyMstDto; // 판매정책정보
    private String salePlcyCd; // 판매정책코드
    private String orgnId; // 조직id
    private String regstId; // 등록자id
    private Date regstDttm; // 등록일시
    private String rvisnId; // 수정자id
    private Date rvisnDttm; // 수정일시
}
