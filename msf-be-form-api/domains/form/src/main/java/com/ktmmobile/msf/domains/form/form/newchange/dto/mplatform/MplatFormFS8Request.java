package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormFS8Request {

    private String orgIdorgId; //대리점아이디

    //custFathInfoDTO

    //CustFathInfoDTO
    //orgId	대리점아이디
    //cpntId	접점아이디
    //onlineOfflnDivCd	온라인오프라인구분코드
    //fathSbscDivCd	안면인증 가입 구분 코드
    //photoAthnNcstYn	사진인증필요여부
    //scanTypeCd	스캐너 유형 코드
    //photoAthnTxnSeq	사진인증일련번호
    //frmpapId	서식지ID
    //retvCdVal	조회코드값
    //fathRglsEnvTestYn	안면인증상용환경테스트여부
    //crprAgntYn	법인대리인여부
    //fathBizrNo	안면인증사업자번호
    //fathAgntCustNm	안면인증대리인고객명
    //fathAgntBthday	안면인증대리인생년월일

    //PhotoAthnRqtInDTO (대면 개통(onlineOfflnDivCd=OFFLINE)이고, MIS에서 사진인증이 필요한 경우(photoAthnNcstYn=Y) 해당 DTO 하위 항목 필수)"
    //photoAthnRqtDivCd	사진인증요청구분코드
    //photoAthnIndvDivCd	사진인증개인구분코드
    //photoAthnSvcDivCd	사진인증서비스구분코드
    //photoAthnSbscChCd	사진인증가입채널코드
    //photoAthnSbscDivCd	사진인증가입구분코드
    //photoAthnRetvPotimCd	사진인증조회시점코드
    //photoAthnAgreeDivYn	사진인증동의구분여부
    //photoAthnConnIpadr	사진인증접속IP주소
    //photoAthnAgncyId	사진인증대리점아이디
    //photoAthnRetvPrsnId	사진인증조회자아이디
    //photoAthnAgncyNm	사진인증대리점명
    //photoAthnSalerCd	사진인증판매자코드


}
