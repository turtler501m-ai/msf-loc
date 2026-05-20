package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BannerFloatDto implements Serializable {

    private static final long serialVersionUID = -1207742026359034810L;

    private int bannFloatSeq;        // 플로팅 배너 일련번호
    private int ntcartSeq;           // 이벤트 일련번호
    private String bannFloatNm;      // 플로팅 배너 이름
    private String useYn;            // 사용 여부
    private String pstngStartDate;   // 노출 시작 날짜
    private String pstngEndDate;     // 노출 종료 날짜
    private String bannFloatPcImg;   // PC용 이미지 파일 경로
    private String bannFloatMoImg;   // 모바일 이미지 파일 경로
    private String bannFloatImgAlt;  // 이미지 ALT 텍스트
    private String bannFloatPcUrl;   // PC용 링크 URL
    private String bannFloatMoUrl;   // 모바일 링크 URL
    private String bannFloatUrlType; // 링크 타입 (새창:N / 현재창:P)

    // 비표준 setter명 유지 (필드명과 다름)
    public void setBannFloatPcImgPath(String bannFloatPcImg) {
        this.bannFloatPcImg = bannFloatPcImg;
    }

}
