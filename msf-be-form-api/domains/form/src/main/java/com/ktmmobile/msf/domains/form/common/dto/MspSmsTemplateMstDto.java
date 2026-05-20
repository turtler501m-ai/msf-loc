package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSmsTemplateMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subject;         // 제목
    private String workType;        // 업무구분
    private int retry;              // 재시도횟수
    private int templateId;         // Template ID
    private String templateNm;      // Template 명
    private String templateDsc;     // Template 상세
    private String mgmtOrgnId;      // 관리조직ID
    private String msgType;         // 메시지 타입(1:SMS,)
    private String callback;        // 발신자번호
    private String text;            // Template 내용
    private String regstId;         // 등록자ID
    private Date regstDttm;         // 등록일시
    private String rvisnId;         // 수정자ID
    private Date rvisnDttm;         // 수정일시
    private int expireHour;         // 만료시간(HOUR)
    private String kTemplateCode;   // 카카오 알림톡 템플릿 코드

    public String getkTemplateCode() {
        return kTemplateCode;
    }

    public void setkTemplateCode(String kTemplateCode) {
        this.kTemplateCode = kTemplateCode;
    }
}
