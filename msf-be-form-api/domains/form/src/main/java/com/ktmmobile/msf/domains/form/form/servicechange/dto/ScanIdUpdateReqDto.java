package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 이폼싸인 서명 완료 후 SCAN_ID 후처리 업데이트 요청 DTO
 * 서비스변경 전용: complete API 이후 이폼서버 업로드가 완료되면 호출
 */
@Getter
@Setter
@NoArgsConstructor
public class ScanIdUpdateReqDto {

    /** 서비스변경 신청 접수번호 */
    private Long requestKey;

    /** 이폼싸인 신청서 document ID 목록 (순서 중요: servicechange → dataSharing → insurance) */
    private List<String> documentId = new ArrayList<>();

    /** 선택된 서비스 타입 코드 목록 (R15 데이터쉐어링 가입 여부 판단용) */
    private List<String> serviceSelect = new ArrayList<>();

    /** 처리 성공한 서비스 타입 코드 목록: 성공 상세건만 SCAN_ID 업데이트 */
    private List<String> successServiceSelect = new ArrayList<>();

    /** 처리 성공한 상세 결과 목록: R11/R12는 SOC 단위로 SCAN_ID 업데이트 */
    private List<ServiceChangeCompleteResVO.ProcessResult> successProcessResults = new ArrayList<>();

    /** 이폼 전달 파라미터 JSON → MSF_REQUEST_SVC_CHG.SIGN_TGT_SBST */
    private String signTgtSbst;

    /** R15 데이터쉐어링 가입 여부 (true이면 documentId[1]이 dataSharingForm) */
    private Boolean dataSharing;

    /** 대표 신청서 파일명 저장용 파일 목록 */
    private List<ServiceChangeCompleteReqDto.ImageSystemUploadFile> reportFiles = new ArrayList<>();
}
