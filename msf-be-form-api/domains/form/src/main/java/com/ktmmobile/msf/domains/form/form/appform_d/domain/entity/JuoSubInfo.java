package com.ktmmobile.msf.domains.form.form.appform_d.domain.entity;

public record JuoSubInfo(

    /** 가입계약번호 */
    String contractNum,

    /** 주민등록번호 */
    String customerSsn,

    /** 고객명 */
    String customerLinkName,

    /** 전화번호_이전CTN */
    String subscriberNo,

    /* 법정대리인 주민번호 */
    String lglAgntSsn,

    /**  기변 여부 */
    String dvcChgYn

) {
}