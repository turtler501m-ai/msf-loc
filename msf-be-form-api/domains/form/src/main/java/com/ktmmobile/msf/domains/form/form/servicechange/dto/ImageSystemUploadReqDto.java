package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 서비스변경 신청서 및 구비서류 이미징 시스템 업로드 요청 DTO */
@Getter
@Setter
@NoArgsConstructor
public class ImageSystemUploadReqDto {

    /** 서비스변경 신청 접수번호 */
    private Long requestKey;

    /** 이폼싸인 신청서 PDF 파일 목록 */
    private List<ServiceChangeCompleteReqDto.ImageSystemUploadFile> reportFiles = new ArrayList<>();

    /** 필수 첨부 서류 파일 목록 */
    private List<ServiceChangeCompleteReqDto.ImageSystemUploadFile> requiredDocFiles = new ArrayList<>();

    private String cstmrNm;
    private String memo;
    private String managerCd;

    /** 이미징 업로드 orgId 결정용 */
    private String shopCd;
    private String cpntId;
    private String agentCd;

    /** 이미징 시스템 업로드 메타데이터 */
    private String parentScanId;
}
