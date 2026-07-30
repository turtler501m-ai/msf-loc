package com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto;

import java.util.List;

import lombok.Builder;

/**
 * 이미지 시스템 파일 업로드 요청값
 * - Front / 내부 Service 호출용
 */
@Builder
public record ImageSystemFileUploadRequest(
    List<UploadFile> files,

    // 매핑을 위한 요청값
    String formTypeCd,     // newchange/servicechange/ownerchange/termination
    String operTypeCd,     // NAC(신규가입)/MNP(번호이동)/HCN(기기변경)/HDN(기기변경)

    // 공통 전달값
    String parentScanId,   // PARENT_SCAN_ID: UUID 기반 스캔 ID. 하이픈 제거 후 전달
    String rgstPrsnId,     // RGST_PRSN_ID: 등록자 사번
    String orgId,          // ORG_ID: 소속 부서 코드
    String custNm,         // CUST_NM: 고객명
    String memo,           // MEMO: 메모
    String onlineYn,       // ONLINE_YN: 온라인 여부
    String companyId       // COMPANY_ID: 소속 회사. 소속 부서 코드와 동일
) {

    @Builder
    public record UploadFile(
        String pathFileName, // 업로드할 파일 경로
        String fileTypeCd,   // FORM_DOC_TYPE_CD
        Integer filePageNo   // 파일 페이지 번호
    ) {
    }
}