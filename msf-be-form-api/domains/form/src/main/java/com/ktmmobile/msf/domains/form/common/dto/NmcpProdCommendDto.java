package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NmcpProdCommendDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int prodCommendId;      // 상품 추천 일련번호 (PK)
    private String prodType;        // 상품 분류 (휴대폰 :01,유심 :02)
    private String prodId;          // NMCP_PROD_BAS PK 휴대폰 , 유심 상품코드
    private String prodNm;          // 상품명
    private String salePlcyCd;      // 판매정책코드
    private String prdtSctnCd;      // 제품구분코드(LTE,3G)(유심타입)
    private String payClCd;         // 선불,후불
    private String prdtId;          // 제품ID(단말기코드)
    private String operType;        // 가입유형 ( * HCN3:기기변경, MNP3:번호이동, NAC3:신규개통)
    private String agrmTrm;         // 약정 기간 (약정유형)
    private String rateCd;          // 요금제코드
    private String rateNm;          // 요금제명
    private int payMnthAmt;         // 월납부 금액
    private String prodOption;      // 상품옵션 인기:01,최대지원금:02,신규:03,사은품:04,특가:05,추천:06
    private String showText;        // 노출문구
    private String imgPath;         // 상품 이미지 경로
    private String imgDesc;         // 이미지설명
    private String linkType;        // 링크 타겟 (현재창 :01,새창 :02)
    private String linkUrl;         // LINK_URL
    private int indcOdrg;           // 정렬순서
    private String status;          // 상태값 A:활성, C:취소
    private String cretId;          // 등록자 아이디
    private String amdId;           // 수정자 아이디
    private Date cretDt;            // 등록일
    private Date amtDt;             // 수정일
    private String usimStar;        // 유심 별 갯수
    private String freeData;        // 무료데이터
    private String freeVoice;       // 무료음성
    private String freeMsg;         // 무료문자
    private String prodTypePhone;   // 휴대폰종류 핫딜폰:A,리퍼폰:B,최저0원폰:C
    private String bgColor;         // backgroundcolor(유심용)
    private String payMnthChargeAmt;
    private String payMnthInstAmt;
    private String ctgGroupCode;    // mainRatePlan:추천요금제, mainPhoneRatePlan:추천휴대폰
    private String ctgCode;
    private String pcRateNm;
    private String moRateNm;
    private String pcRateDesc;
    private String moRateDesc;
    private String pageNo;
    private String requestKey;
    private String shareYn;
    private String modelId;
    private String modelMonthly;
    private String socCode;
    private String modelSalePolicyCode;
    private String onOffType;
    private String orgnId;
    private String prodCtgType;
    private String selPric;

    public String getProdTypeNm() {
        if ("01".equals(prodType)) {
            return "휴대폰";
        } else if ("02".equals(prodType)) {
            return "유심";
        } else {
            return "";
        }
    }

    public String getOperTypeNm() {
        if ("MNP3".equals(operType)) {
            return "번호이동";
        } else if ("NAC3".equals(operType)) {
            return "신규가입";
        } else if ("HCN3".equals(operType)) {
            return "기기변경";
        }
        return operType;
    }
}
