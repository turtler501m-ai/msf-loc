package com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

/**
 * 이미지 시스템 PDF 업로드 요청값
 */
@Builder
public record ImageSystemPdfUploadRequest(
    String docCd,          // DOC_CD: 문서 양식 분류 코드
    String parentScanId,   // PARENT_SCAN_ID: UUID 기반 스캔 ID. 하이픈 제거 후 전달
    String fileId,         // FILE_ID: EID. YYYYMMDDHHMMSSfffffff 형식
    String workCd,         // WORK_CD: 업무 유형 코드
    String workNm,         // WORK_NM: 업무 유형 이름
    String rgstPrsnId,     // RGST_PRSN_ID: 등록자 사번
    String orgId,          // ORG_ID: 소속 부서 코드
    String custNm,         // CUST_NM: 고객명
    String memo,           // MEMO: 메모
    String onlineYn,       // ONLINE_YN: 온라인 여부
    String companyId,      // COMPANY_ID: 소속 회사. 소속 부서 코드와 동일
    MultipartFile file     // 업로드할 PDF 파일
) {
}
