package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 변경 가능한 번호 목록 조회
 */
@Getter
@Setter
@NoArgsConstructor
public class NumChgeRequest {


    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID

    /*
        미입력 시 0으로 설정
        예) 채번된 번호가 총 10개라고 가정했을 때
        inqrBase - 0, inqrCascnt - 2 입력 시 0, 1번째 번호 추출
        inqrBase - 2, inqrCascnt - 3 입력 시 2, 3, 4번째 번호 추출
    */
    // 조회시작번호
    private String inqrBase;
    // 조회건수 (최대값 : 20, 미입력 시 20으로 설정)
    private String inqrCascnt;
    // 조회할 마지막 4자리 번호
    private String chkCtn;

}
