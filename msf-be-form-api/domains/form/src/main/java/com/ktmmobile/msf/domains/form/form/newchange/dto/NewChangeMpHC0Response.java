package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeMpHC0Response {

    private Long requestKey;
    private String reqBuyType;
    private String operTypeCd;

    //InDto
    private String mvnoOrdNo; //MVNO 오더 번호
    private String custNo; //고객번호
    private String svcContId; //서비스계약번호
    private String tlphNo; //전화번호
    private String oderTypeCd; //오더유형코드
    private String instYn; //단말기할부여부
    private String indvInfoInerPrcuseAgreYn; //개인정보내부활용동의여부
    private String crdtInfoAgreYn; //신용정보동의여부
    private String spamPrvdAgreYn; //스팸제공동의여부
    private String custIdntNoIndCd; //고객식별번호구분코드

    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String custIdntNo; //고객식별번호
    private String crprNo; //법인번호
    private String custNm; //고객명
    private String rsdcrtIssuDate; //주민등록증발급일자
    private String nativeRlnamAthnEvdnPprCd; //실명인증증빙서류코드
    private String lcnsNo; //면허번호
    private String lcnsRgnCd; //면허지역코드
    private String mrtrPrsnNo; //유공자번호
    private String nationalityCd; //국적코드
    private String fornBrthDate; //외국인생일일자
    private String athnRqstcustCntplcNo; //인증요청고객연락처번호
    private String myslAgreYn; //본인동의여부
    private String agntRltnCd; //대리인관계코드
    private String agntBrthDate; //대리인생년월일
    private String intmModelId; //기기모델아이디
    private String intmSrlNo; //기기일련번호
    private String iccId; //USIM 일련번호
    private String spclSlsNo; //판매정책번호
    private String spnsrTermTypeCd; //스폰서약정 유형코드
    private String enggMnthCnt; //약정 개월 수
    private String saleEngtOptnCd; //할인 유형 코드
    private String esimUseYn; //eSIM 사용여부
    private String euiccId; //EID

    private String jrdclAgentRrn; //대리인생년월일 - 복호화 처리를 위한 변수로 MP연동시에는 사용하진 않음.

    //inPrdcDto
    private String prdcCd; //상품코드
    private String prdcTypeCd; //상품타입코드

    private List<MsfRequestAdditionVo> additionVoList;

    //inFrmpapDto
    private String cntpntCd; //접점코드
    private String frmpapId; //서식지 아이디
    private String iselfFrmpapYn; //자체 서식지 여부 2026.07.14 - 자체 서식지 사용하는 경우 필수 Y: 자체 서식지 사용

    private String agentCd; //대리점코드
}
