package com.ktmmobile.msf.domains.form.common.dto;

import com.ktmmobile.msf.domains.form.common.mspservice.dto.CmnIntmMdl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

/**
 * @Class Name : MspSalePrdtMstDto
 * @Description : MSP 의 MSP_SALE_PRD_MST 테이블과 대응된다.
 * (상품,정책) 별 신규수수료,MNP수수료,기변수수료 등의 정보를 가지고있다.
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
@Getter
@Setter
@NoArgsConstructor
public class MspSalePrdtMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private CmnIntmMdl cmnIntmMdl; // msp 상품상세정보
    private String salePlcyCd; // 판매정책코드
    private String prdtId; // 제품id(nrds code)
    private String oldYn; // 중고여부
    private BigInteger newCmsnAmt; // 신규수수료
    private BigInteger mnpCmsnAmt; // mnp 수수료
    private BigInteger hcnCmsnAmt; // 기변수수료
    private String regstId; // 등록자id
    private Date regstDttm; // 등록일시
    private String rvisnId; // 수정자id
    private Date rvisnDttm; // 수정일시
    private String orgnId; // 조직코드
}
