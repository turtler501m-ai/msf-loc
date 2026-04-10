package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.Map;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpUsimPukVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CustRequestDto;

/**
 * <pre>
 * 파일명   : MsfCustRequestService.java
 * 날짜    : 2022.07.14
 * 작성자   : wooki
 * 설명    : 
 * </pre>
 */
public interface MsfCustRequestService {
	
	/**
     * <pre>
     * 설명     : 고객요청서 기본키 요청
     * @param
     * @return: long
     * </pre>
     */
    public long getCustRequestSeq();

	/**
     * <pre>
     * 설명     : 고객요청 마스터 테이블 insert
     * @param : CustRequestDto
     * @return: boolean
     * </pre>
     */
	public boolean insertCustRequestMst(CustRequestDto custRequestDto);
	
	/**
     * <pre>
     * 설명     : 통화열람내역 신청 테이블 insert
     * @param : CustRequestDto 
     * @return: boolean
     * </pre>
     */
	public boolean insertCustRequestCallList(CustRequestDto custRequestDto);
	
	/**
     * <pre>
     * 설명     : 가입신청서 출력요청 테이블 insert
     * @param : CustRequestDto 
     * @return: boolean
     * </pre>
     */
	public boolean insertCustRequestJoinForm(CustRequestDto custReuqestDto);
	
	/**
     * <pre>
     * 설명     : Y07 USIM PUK 번호 조회
     * @param : String(계약번호), String(전화번호), String(고객번호)
     * @return: MpUsimPukVO
     * </pre>
     */
 	public MpUsimPukVO getUsimPukByMP(String ncn, String ctn, String custId); 

	/**
	 * @Description : 안심보험가입 여부 조회(API호출)
	 * @param contractNum
	 * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.04
	 */
	public Map<String, String> getInsrInfo(String contractNum);

	/**
	 * @Description : 안심보험정보 조회(by insrCd)(API호출)
	 * @param insrCd
	 * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.20
	 */
	public Map<String, String> getInsrInfoByCd(String insrCd);
	
	/**
	 * @Description : 안심보험 신청한 내역 조회(API호출)
	 * @param custReqKey
	 * @return Map<String, String>
     * @Author : wooki
     * @CreateDate : 2022.10.20
	 */
	public Map<String, String> getReqInsrData(long custReqKey);
	
	/**
	 * @Description : org 스캔아이디 조회(API호출)
	 * @param contractNum
	 * @return String
     * @Author : wooki
     * @CreateDate : 2022.10.20
	 */
	public String getOrgScanId(String contractNum); 
	
	/**
     * <pre>
     * 설명     : 안심보험 신청 테이블 insert
     * @param : CustRequestDto 
     * @return: boolean
     * </pre>
     */
	public boolean insertCustRequestInsr(CustRequestDto custReuqestDto);
}