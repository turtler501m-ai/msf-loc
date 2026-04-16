package com.ktmmobile.msf.domains.form.form.servicechange.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.MyNameChgReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CustRequestDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
/**
 * <pre>
 * 프로젝트 :
 * 파일명   : CustRequestDao.java
 * 날짜     : 2022.06
 * 작성자   : wooki
 * 설명     : 고객요청(SelfCare) 관련 처리 서비스
 * </pre>
 */
public interface CustRequestDao {

    /**
     * <pre>
     * 설명     : 고객요청(SelfCare)시 seq 번호 가져오기
     * @param
     * @return long
     * </pre>
     */
    public long getCustRequestSeq();

    /**
     * <pre>
     * 설명     : 고객요청(SelfCare)시 NMCP_CUST_REQUEST_MST(마스터테이블) insert
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean insertCustRequestMst(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 통화내역열람신청 시 NMCP_CUST_REQUEST_CALL_LIST insert
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean insertCustRequestCallList(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 통화내역열람신청 시 서식지 만들기 전 기본정보 조회
     * @param long(seq)
     * @return Map<String, String>
     * </pre>
     */
    public Map<String, String> getReqCallListData(long custRequestSeq);

    /**
     * <pre>
     * 설명     : 통화내역열람신청 시 서식지 생성 후에 EMV_SCAN_MST의 WORK_ID, DOC_TITLE update
     *          (서식지 자동생성은 WORK_ID가 무조건 S0001로 들어가기 때문에 S0010로 바꿔줘야함)
     * @param String(스캔아이디)
     * @return boolean
     * </pre>
     */
    public boolean updateEmvScanMstCL(String scanId);

    /**
     * <pre>
     * 설명     : 통화내역열람신청 시 서식지 생성 후에 EMV_SCAN_FILE의 DOC_ID update
     *          (서식지 자동생성은 DOC_ID가 무조건 E0001로 들어가기 때문에 E0010로 바꿔줘야함)
     * @param String(스캔아이디)
     * @return boolean
     * </pre>
     */
    public boolean updateEmvScanFileCL(String scanId);

    /**
     * <pre>
     * 설명     : 통화내역열람신청 시 NMCP_CUST_REQUEST_CALL_LIST의 SCAN_ID update
     *          (서식지 자동생성 시 만들어진 SCAN_ID를 update 해줌)
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean updateCustRequestCallListScanId(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 명의변경신청 시 서식지 만들기 전 기본정보 조회
     * @param long(seq)
     * @return Map<String, String>
     * </pre>
     */
    public Map<String, String> getReqNmChgData(long custRequestSeq);

    /**
     * <pre>
     * 설명     : 명의변경신청 시 서식지 생성 후에 EMV_SCAN_MST의 WORK_ID, DOC_TITLE update
     *          (서식지 자동생성은 WORK_ID가 무조건 S0001로 들어가기 때문에 S0003로 바꿔줘야함)
     * @param String(스캔아이디)
     * @return boolean
     * </pre>
     */
    public boolean updateEmvScanMstNC(String scanId);

    /**
     * <pre>
     * 설명     : 명의변경신청 시 서식지 생성 후에 EMV_SCAN_FILE의 DOC_ID update
     *          (서식지 자동생성은 DOC_ID가 무조건 E0001로 들어가기 때문에 E0003로 바꿔줘야함)
     * @param String(스캔아이디)
     * @return boolean
     * </pre>
     */
    public boolean updateEmvScanFileNC(String scanId);

    /**
     * <pre>
     * 설명     : 명의변경신청 시 NMCP_CUST_REQUEST_NAME_CHG의 SCAN_ID update
     *          (서식지 자동생성 시 만들어진 SCAN_ID를 update 해줌)
     * @param MyNameChgReqDto
     * @return boolean
     * </pre>
     */
    public boolean updateReqNmChgScanId(MyNameChgReqDto myNameChgReqDto);

    /**
     * <pre>
     * 설명     : 서식지 그룹별 좌표 조회
     * @param groupCode : 서식지 그룹코드
     * @return List<HashMap<String, String>>
     * </pre>
     */
    public List<HashMap<String, String>> getAppFormPointList(String groupCode);

    /**
     * <pre>
     * 설명     : 가입신청서 출력요청시 NMCP_CUST_REQUEST_JOIN_FORM insert
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean insertCustRequestJoinForm(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 서식지 생성 후에 EMV_SCAN_MST의 WORK_ID, DOC_TITLE update
     *          (서식지 자동생성은 WORK_ID가 무조건 S0001로 들어가기 때문에 바꿔줘야함)
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean updateEmvScanMst(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 서식지 생성 후에 EMV_SCAN_FILE의 DOC_ID update
     *          (서식지 자동생성은 DOC_ID가 무조건 E0001로 들어가기 때문에 바꿔줘야함)
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean updateEmvScanFile(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 서식지합본처리-maxFileNum 가져오기
     * @param orgScanId
     * @return int
     * </pre>
     */
    public int getMaxFileNum(String orgScanId);

    /**
     * <pre>
     * 설명     : 서식지합본처리-EMV_SACN_FILE에 insert
     * @param custRequestDto
     * @return boolean
     * </pre>
     */
    public boolean insertEmvScanFile(CustRequestDto custRequestDto);

    /**
     * <pre>
     * 설명     : 안심보험가입신청 시 서식지 만들기 전 기본정보 조회
     * @param long(seq)
     * @return Map<String, String>
     * </pre>
     */
    public Map<String, String> getReqInsrData(long custRequestSeq);

    /**
     * <pre>
     * 설명     : 안심보험 가입신청시 NMCP_CUST_REQUEST_INSR insert
     * @param CustRequestDto
     * @return boolean
     * </pre>
     */
    public boolean insertCustRequestInsr(CustRequestDto custReuqestDto);

    /**
     * <pre>
     * 설명     : 해지상담신청 시 서식지 만들기 전 기본정보 조회
     * @param long(seq)
     * @return Map<String, String>
     * </pre>
     */
    public Map<String, String> getCancelConsultData(long custRequestSeq);

    public boolean updateCancelReqScanId(CancelConsultDto cancelConsultDto);

    public boolean updateEmvScanMstCC(String scanId);

    public boolean updateEmvScanFileCC(String scanId);
}
