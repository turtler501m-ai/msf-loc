package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgShareDataReqDto;

import java.util.Map;

/**
 * 데이터쉐어링 서비스 인터페이스.
 * ASIS MyShareDataSvc + MyShareDataController 의 업무 로직 담당.
 * X69(사전체크)/X70(가입·해지)/X71(목록조회) + 화면 데이터 조합.
 */
public interface SvcChgShareDataSvc {

    /**
     * 데이터쉐어링 Step2 화면 데이터 조합.
     * X71 결합목록 조회 + isSimpleApplyObj(개통가능시간) 조합.
     * ASIS MyShareDataController.dataSharingStep2() 와 동일.
     */
    Map<String, Object> dataSharingStep2(SvcChgShareDataReqDto req);

    /**
     * 셀프개통(NAC) 가능 여부 조회.
     * 시간대 체크 (08:00 ~ 21:50).
     * ASIS AppformSvcImpl.isSimpleApplyObj() 와 동일.
     */
    Map<String, Object> isSimpleApplyObj();

    /**
     * 데이터쉐어링 개통요청 뷰 데이터 조회.
     * 계약번호로 고객/회선 정보 반환.
     * ASIS MyShareDataController.dorReqSharingView() 와 동일.
     */
    Map<String, Object> dorReqSharingView(String ncn);

    /**
     * 데이터쉐어링 개통 신청 (X69 사전체크 + X70 가입).
     * ASIS MyShareDataController.doinsertOpenRequestAjax() 와 동일.
     */
    Map<String, Object> doinsertOpenRequestAjax(SvcChgShareDataReqDto req);
}
