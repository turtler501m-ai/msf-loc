package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 주태강
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class McpRateChgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SEQ
     */
    private int chgSeq;

    /**
     * 계약번호
     */
    private String contractNum;

    /**
     * 고객명
     */
    private String cstmrName;

    /**
     * 나이스 인증 서명
     */
    private String resNo;

    /**
     * 파일경로
     */
    private String filePath;

    /**
     * 파일 확장자
     */
    private String ext;

    /**
     * 파일명
     */
    private String fileNm;

    /**
     * 생성여부
     */
    private String createYn;

    /**
     * 등록자 ID
     */
    private String regstId;

    /**
     * 등록일시
     */
    private String regstDttm;

    /**
     * 수정자ID
     */
    private String rvisnId;

    /**
     * 수정일시
     */
    private String rvisnDttm;

    /**
     * 오류메시지
     */
    private String errorDesc;

    /**
     * 요금제 코드
     */
    private String rateCd;

    private boolean resultFlag;
    private String resultMsg;
    private String endImgFullPath;
    private String birthDate;

	public boolean isResultFlag() {
		return resultFlag;
	}
}