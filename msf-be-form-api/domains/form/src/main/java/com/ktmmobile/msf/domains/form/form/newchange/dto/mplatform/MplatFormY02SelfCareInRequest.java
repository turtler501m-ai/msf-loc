package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "selfCareIn") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormY02SelfCareInRequest {

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String ctn; //사용자 전화번호
    private String custId; //고객번호
    private String ncn; //사용자 서비스계약번호
    private String clntIp; //Client IP
    private String clntUsrId; //사용자 User ID
}
