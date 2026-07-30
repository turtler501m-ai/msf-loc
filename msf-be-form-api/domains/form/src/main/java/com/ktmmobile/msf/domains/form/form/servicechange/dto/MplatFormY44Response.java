package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormY44Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        private String resultCd;  // 결과코드
        private String resultMsg;  // 결과메세지 (0000: 성공, 9999:실패, 1000: 서브회선 부가서비스 가입 오류)
    }
}
