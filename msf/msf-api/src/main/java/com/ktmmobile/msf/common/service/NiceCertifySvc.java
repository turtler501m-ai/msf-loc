package com.ktmmobile.msf.common.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.ktmmobile.msf.common.dto.NiceLogDto;
import com.ktmmobile.msf.common.dto.NiceResDto;
import com.ktmmobile.msf.common.dto.UserSessionDto;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NiceCertifySvc.java
 * 날짜     : 2016. 1. 25. 오전 10:48:31
 * 작성자   : papier
 * 설명     : Nice 관련 서비스
 * </pre>
 */
public interface NiceCertifySvc {


    /**
     * <pre>
     * 설명     : 계좌 번호 유효성 체크 서비스
     * @param niceResDto
     * @return
     * @return: String
     * </pre>
     */
    public String checkNiceAccount(NiceResDto niceResDto) ;

    /**
     * <pre>
     * 설명     : 계좌 번호 점유인증 서비스 Otp Name 생성:1원입금
     * @param niceResDto
     * @return
     * @return: String
     * </pre>
     */
    public String niceAccountOtpName(NiceResDto niceResDto) ;

    /**
     * <pre>
     * 설명     : 계좌 번호 점유인증 서비스 Otp Confirm : Otp 확인
     * @param niceResDto
     * @return
     * @return: String
     * </pre>
     */
    public String niceAccountOtpConfirm(NiceResDto niceResDto) ;

    
    /**
     * <pre>
     * 설명     : PASS 본인인증 알람 전송
     * @param request
     * @return
     * @return: String
     * </pre>
     */
	public String pushPassAlram(HttpServletRequest request);

	/**
     * <pre>
     * 설명     : PASS 본인인증 결과 확인
     * @param request
     * @return
     * @return: String
     * </pre>
     */
	public String passCertifyInfo(HttpServletRequest request);
    
    /**
     * <pre>
     * 설명 : 개통정보와 본인인증 정보 비교
     * @param paraMap
     * @return: boolean
     * </pre>
     */
    public boolean getRegularAuthChk(Map<String,String> paraMap);

    /**
     * <pre>
     * 설명  : 로그인세션 정보와 본인인증 정보 비교
     * @param: userSession
     * @return: boolean
     * </pre>
     */
    public boolean getAssociateAuthChk(UserSessionDto userSession);


    /**
     * <pre>
     * 설명  : 간편본인인증 알림요청 전 SMS본인인증 완료 여부 확인 - 셀프개통
     * </pre>
     */
    public boolean preChkSimpleAuth();

    /** 유심구매하기 휴대폰 인증 요청 전, STEP 처리 */
    boolean chkUsimBuySmsReqInfo(NiceLogDto niceLogDto);

    /** 유심구매하기 휴대폰 인증 응답 후, STEP 처리 */
    Map<String, String> chkUsimBuySmsResInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn);

    /** 회원 가입 & 수정 시 법정대리인 정보 확인 */

    Map<String, String> chkAgentInfo(String cstmrName, NiceResDto childniceResDto, NiceLogDto niceLogRtn, UserSessionDto userSession);

    Map<String, String> chkReplaceUsimSmsResInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn);

    Map<String, String> chkReplaceUsimSelfAuthInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn);
}
