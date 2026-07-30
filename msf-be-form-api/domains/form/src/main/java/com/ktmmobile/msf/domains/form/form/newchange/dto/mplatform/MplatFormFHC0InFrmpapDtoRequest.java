package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inFrmpapDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormFHC0InFrmpapDtoRequest {

    //private String mngmAgncId; //관리대리점 아이디
    //private String frmpapUseYn; //서식지 사용여부?

    private String cntpntCd; //접점코드
    private String frmpapId; //서식지 아이디
    private String iselfFrmpapYn; //자체 서식지 여부 2026.07.14 - 자체 서식지 사용하는 경우 필수 Y: 자체 서식지 사용
}
