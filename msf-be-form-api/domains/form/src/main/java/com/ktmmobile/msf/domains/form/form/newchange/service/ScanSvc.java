package com.ktmmobile.msf.domains.form.form.newchange.service
;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : ScanSvc.java
 * 날짜     : 2016. 1. 15. 오전 11:01:20
 * 작성자   : papier
 * 설명     : 서식지 데이터 SCAN 서버 연동 처리
 * </pre>
 */
public interface ScanSvc {


    /**
     * <pre>
     * 설명     : 서식지 데이터 SCAN 서버에 전송 처리
     * @param requestKey
     * @return: void
     * </pre>
     */
    public void prodSendScan(long requestKey, String cretId);


}
