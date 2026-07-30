package com.ktmmobile.msf.domains.form.form.common.vo;

import java.util.List;

import lombok.Data;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Data
public class McpCustRequestChangeVo {

    private String nameChangeNm;            // 명의변경_고객명
    private String nameChangeTelFn;         // 명의변경_전화번호_앞자리
    private String nameChangeTelMn;         // 명의변경_전화번호_중간자리
    private String nameChangeTelRn;         // 명의변경_전화번호_끝자리
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String nameChangeRrn;           // 명의변경_주민등록번호
    private String nameChangePinstallment;  // 완납=P,승계=C
    private String rvisnId;                // 수정자 아이디
    private Long requestKey;              // 가입신청_키

    public void setup(MsfRequestNameChgVo request) {
        boolean isForeigner = List.of("FM", "FN").contains(request.getCstmrTypeCd());
        boolean isGovernment = List.of("JP", "GO").contains(request.getCstmrTypeCd());
        this.nameChangeRrn = isGovernment
            ? request.getCstmrJuridicalRrn()
            : isForeigner ? request.getCstmrForeignerRrn() : request.getCstmrNativeRrn();
    }
}
