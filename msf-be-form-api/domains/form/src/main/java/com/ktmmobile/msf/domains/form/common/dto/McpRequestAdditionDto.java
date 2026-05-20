package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestAdditionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;            // 가입신청_키
    private long additionKey;           // 부가서비스_키
    private String additionName;        // 부가서비스명
    private long rantal;                // 사용료
    private String chargeFlag;          // 부가 서비스 구분
    private String osstYn;              // 개통간소화 시 부가서비스 가능 여부
    private String rateAdsvcProdRelSeq; // 개통간소화 시 부가서비스 가능 여부

    public long getVatRantal() {
        return (long) (rantal * 1.1);
    }

}
