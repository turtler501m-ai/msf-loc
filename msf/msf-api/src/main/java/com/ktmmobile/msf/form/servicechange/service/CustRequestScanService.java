package com.ktmmobile.msf.form.servicechange.service
;



/**
 * <pre>
 * 프로젝트 : 
 * 파일명  : ReqScanSvc.java
 * 날짜   : 20220-07-07
 * 작성자  : wooki
 * 설명   : 고객이 신청한 작업(통화내역 열람, 명의변경 등) 서식지 데이터 SCAN 서버 연동 처리
 * </pre>
 */
public interface CustRequestScanService {

    /**
     * <pre>
     * 설명     : 고객이 신청한 작업(통화내역 열람, 명의변경 등) 서식지 데이터 SCAN 서버 연동 처리
     * @param requestKey
     * @return: void
     * </pre>
     */
    public void prodSendScan(long custReqKey, String cretId, String reqType);


}
