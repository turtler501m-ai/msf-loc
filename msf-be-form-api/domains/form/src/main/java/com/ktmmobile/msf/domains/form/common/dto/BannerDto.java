package com.ktmmobile.msf.domains.form.common.dto;

import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class BannerDto implements Serializable {

    private static final long serialVersionUID = -1207742026359034810L;

    private String bannApdDesc;         // 배너추가설명
    private String mobileBannImgNm;     // 모바일배너이미지
    private String bannBgColor;         // 배너배경색상
    private String mobileLinkUrl;       // 모바일링크url
    private int bannSeq;                // 배너일련번호
    private String bannerCd;            // 배너 일련번호 (인자값)
    private String bannCtg;             // 배너카테고리
    private String bannType;            // 배너종류
    private String bannNm;              // 배너명
    private String bannDesc;            // 배너설명
    private String bannImg;             // 배너이미지
    private String imgDesc;             // 이미지설명(alt)
    private String linkTarget;          // 링크타겟
    private String linkUrlAdr;          // 링크URL
    private String statVal;             // 상태
    private String prodNm;              // 상품명
    private int prodId;                 // 상품아이디
    private int sntyProdSeq;            // 단품상품일련번호
    private String sbscrbTypeCd;        // 가입유형코드
    private String agrmTypeCd;          // 약정유형코드
    private String chrgPlanCd;          // 요금제코드
    private String cretId;              // 생성자아이디
    private String amdId;               // 수정자아이디
    private String cretDt;              // 생성일시
    private String amdDt;               // 수정일시
    private String useYn;               // 사용여부
    private String cdGroupId = "MBN";   // 코드그룹아이디
    private String dtlCd;               // 상세코드
    private String dtlCdNm;             // 상세코드명
    private String dtlCdDesc;           // 상세코드설명
    private int indcOdrg;               // 표시순서
    private int dtlCnt;                 // 하위 배너 숫자
    private int rowNum;                 // 게시글 넘버
    private String resultCode;          // 처리 코드
    private int totalCnt;
    private String bannSeqArr;
    private String bannOdrgArr;
    private MultipartFile file;         // 파일
    private String fileRootPath;
    private String updateYn;
    private String atribValCd1;         // 색상
    private String atribValCd2;         // 용량
    private String atribValNm1;         // 색상명
    private String atribValNm2;         // 용량명
    private String sdoutYn;             // 품절여부
    private String salePlcyCd;          // 요금제코드
    private String agrmTrm;             // 요금제명
    private String rateCd;              // 약정명
    private String rateNm;              // 약정코드
    private String newYn;               // 가입유형 신규
    private String mnpYn;               // 가입유형 번호이동
    private String hcnYn;               // 가입유형 기기변경
    private String[] editorPhotoSeqArr; // 에디터 사진업로드 seqarr
    private String expnsnStrVal1;       // 분류
    private String expnsnStrVal2;       // 분류 2
    private String expnsnStrVal3;       // 분류 3
    private String bgColor;             // 배너배경색
    private String textColor1;          // 서브타이틀색상1
    private String textColor2;          // 서브타이틀색상2
    private Map<String, Object> pageMap; // 페이징
    private String searchNm;
    private String searchOpt;
    private int rstCnt;
    private String rebuildLinkl;        // 핸드폰,유심 배너 링크 재가공 처리
    private String newTarget;           // 핸드폰 새창처리
    private String payClCd;
    private String phoneYn;
    private String dataType;            // LTE,3G
    private int skipResult;             // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;              // Pagesize
    private int pageNo;
    private int boardNum;
    private String adDesc;
    private String eventType;           // 이벤트배너타입 이벤트:E,제휴:J,제휴카드:C
    private String eventStartDt;        // 이벤트시작일
    private String eventEndDt;          // 이벤트종료일
    private MultipartFile moreFile;     // 더보기 파일
    private String moreBannImg;         // 더보기 배너이미지
    private String bannDtlSeq;
    private String imgNm;
    private String linkNm;
    private String sortOdrg;
    private String pstngStartDate;
    private String pstngEndDate;
    private String bannImgSec;          // 배너이미지
    private String imgDescSec;          // 이미지설명(alt)
    private String bgColorSec;          // 배너배경색
    private String mobileBannImgNmSec;  // 모바일배너이미지
    private String pstngStartDateSec;
    private String pstngEndDateSec;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getRebuildLinkl() {

        String agrmTypeCdNull = StringUtils.defaultIfEmpty(agrmTypeCd, "");
        String operTypeCh = "";

        if ("Y".equals(newYn)) {
            operTypeCh = PhoneConstant.OPER_NEW;
        } else if ("Y".equals(mnpYn)) {
            operTypeCh = PhoneConstant.OPER_PHONE_NUMBER_TRANS;
        } else if ("Y".equals(hcnYn)) {
            operTypeCh = Constants.OPER_TYPE_EXCHANGE;
        }

        if ("M".equals(bannType)) {	//모바일
            if (null != linkUrlAdr && linkUrlAdr.indexOf("/m/") > -1) {
                rebuildLinkl = "/m/product/phone/phoneView.do" + "?bannerCd=" + bannSeq + "&prodId=" + prodId + "&"
                        + "instNom=" + agrmTrm + "&rateCd=" + rateCd
                        + "&sprtTp=" + agrmTypeCdNull + "&operType=" + operTypeCh + "&hndsetModelId=" + sntyProdSeq;
            } else {
                rebuildLinkl = "/product/phone/phoneView.do" + "?bannerCd=" + bannSeq + "&prodId=" + prodId + "&"
                        + "instNom=" + agrmTrm + "&rateCd=" + rateCd
                        + "&sprtTp=" + agrmTypeCdNull + "&operType=" + operTypeCh + "&hndsetModelId=" + sntyProdSeq;
            }
        } else if ("U".equals(bannType)) {	//유심
            String prodViewPath = "";
            if ("PO".equals(payClCd)) {		//후불유심
                prodViewPath = "/appForm/reqSelfDlvry.do";
            } else if ("PP".equals(payClCd)) {	//선불유심
                prodViewPath = "/appForm/reqSelfDlvry.do";
            }
            rebuildLinkl = prodViewPath + "?bannerCd=" + bannSeq + "&prodId=" + prodId + "&atribValCd1=" + atribValCd1 + "&atribValCd2=" + atribValCd2
                    + "&rateCd=" + rateCd;
        } else {
            rebuildLinkl = linkUrlAdr;
        }
        return rebuildLinkl;
    }

    public String getNewTarget() {
        if ("Y".equals(linkTarget)) {
            newTarget = "target=\"_blank\", title=\"새창열림\"";
        } else {
            newTarget = "";
        }
        return newTarget;
    }

    // 파라미터명 버그 원본 유지 (this.textColor2 = textColor2 는 필드를 자기 자신에 대입하는 no-op)
    public void setTextColor2(String textColor1) {
        this.textColor2 = textColor2;
    }

}
