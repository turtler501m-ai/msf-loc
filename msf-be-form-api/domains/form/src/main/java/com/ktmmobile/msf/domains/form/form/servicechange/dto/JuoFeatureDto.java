package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class JuoFeatureDto implements Serializable {

    //상품 현행화 정보

    private static final long serialVersionUID = 1L;

    /** 가입계약번호 */
    private String contractNum;
    /** 상품코드 */
    private String soc;
    /** 만기일시 */
    private String expirationDate;
    /**
     * 3개월 이내 결합이력이 존재하여 결합이 불가
     *  만기일시 3개월 연장 */
    private int intAddMonths = 0;

    public int getIntAddMonths() {
        return intAddMonths;
    }
    public void setIntAddMonths(int intAddMonths) {
        this.intAddMonths = intAddMonths;
    }

}
