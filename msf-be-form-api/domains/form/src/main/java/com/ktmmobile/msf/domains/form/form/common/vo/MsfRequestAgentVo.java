package com.ktmmobile.msf.domains.form.form.common.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAgentVo {

    Long requestKey;

    String cstmrTypeCode;

    String minorAgentNm; //미성년자 이름

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String minorAgentRrn; //미성년자 주민번호

    String minorAgentBirth;
    String minorAgentGenderCd;
    String minorAgentRelTypeCd;

    String minorAgentTelFnNo; //미성년자 법정대리인 연락처
    String minorAgentTelMnNo; //미성년자 법정대리인 연락처
    String minorAgentTelRnNo; //미성년자 법정대리인 연락처

    String minorAgentAgrmYn;
    String minorAgentSelfInqryAgrmYn;
    String minorAgentSelfCertTypeCd;
    String minorAgentCiInfo;

    String jrdclAgentNm; //법인대리명
    String jrdclAgentGender; //법인대리성별
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String jrdclAgentRrn; //법인대리인등록번호 - 화면에서 생년월일 8자리+성별로 암호화 주석처리하였으나, 2026.07.20 MCP연동 시 13자리(0 채움) 연동 (MCP 암호화로 스마트도 암호화처리)

    String jrdclAgentRelTypeCd;

    String jrdclAgentTelFnNo; //법인대리인 연락처
    String jrdclAgentTelMnNo; //법인대리인 연락처
    String jrdclAgentTelRnNo; //법인대리인 연락처

    public void setupData() {
        setupGover();
    }

    public void setupGover() {
        List<String> gover = List.of("GO", "JP");

        if (!gover.contains(this.cstmrTypeCode)) {
            this.jrdclAgentNm = null;
            this.jrdclAgentRrn = null;
            this.jrdclAgentRelTypeCd = null;
            this.jrdclAgentTelFnNo = null;
            this.jrdclAgentTelMnNo = null;
            this.jrdclAgentTelRnNo = null;
        } else if (StringUtils.hasText(this.jrdclAgentRrn)) {
            String gender = "M".equals(this.jrdclAgentGender) ? "1" : "2";
            String rrn = StringUtil.rpad("0", this.jrdclAgentRrn + gender, 13);
            this.jrdclAgentRrn = rrn;
        }
    }

}
