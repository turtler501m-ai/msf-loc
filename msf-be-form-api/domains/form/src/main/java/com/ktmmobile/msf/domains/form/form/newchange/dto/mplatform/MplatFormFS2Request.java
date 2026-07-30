package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormFS2Request {

    private String mngmAgncId; //관리대리점코드
    private String cntpntCd; //접점코드
    private String frmpapId; //서식지아이디
    private String frmpapStatCd; //서식지상태변경코드
    @XmlTransient
    private String mcnResNo;

}
