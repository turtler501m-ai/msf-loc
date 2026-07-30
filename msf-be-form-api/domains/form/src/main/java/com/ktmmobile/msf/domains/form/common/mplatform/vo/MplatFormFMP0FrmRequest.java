package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "inFrmpapDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormFMP0FrmRequest {

    private String cntpntCd;
    private String frmpapId;            // 서식지아이디

}
