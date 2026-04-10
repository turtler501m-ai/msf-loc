package com.ktmmobile.msf.domains.form.common.service;

import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;

// [ASIS] NiceCertifySvcImpl — 기존 구현체는 MCP 패키지 참조로 미완성. 기동용 스텁 구현.
@Service
public class NiceCertifySvcImpl implements NiceCertifySvc {

    @Override public String checkNiceAccount(NiceResDto niceResDto) { return ""; }
    @Override public String niceAccountOtpName(NiceResDto niceResDto) { return ""; }
    @Override public String niceAccountOtpConfirm(NiceResDto niceResDto) { return ""; }
    @Override public String pushPassAlram(HttpServletRequest request) { return ""; }
    @Override public String passCertifyInfo(HttpServletRequest request) { return ""; }
    @Override public boolean getRegularAuthChk(Map<String, String> paraMap) { return false; }
    @Override public boolean getAssociateAuthChk(UserSessionDto userSession) { return false; }
    @Override public boolean preChkSimpleAuth() { return false; }
    @Override public boolean chkUsimBuySmsReqInfo(NiceLogDto niceLogDto) { return false; }
    @Override public Map<String, String> chkUsimBuySmsResInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn) { return new HashMap<>(); }
    @Override public Map<String, String> chkAgentInfo(String cstmrName, NiceResDto childniceResDto, NiceLogDto niceLogRtn, UserSessionDto userSession) { return new HashMap<>(); }
    @Override public Map<String, String> chkReplaceUsimSmsResInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn) { return new HashMap<>(); }
    @Override public Map<String, String> chkReplaceUsimSelfAuthInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn) { return new HashMap<>(); }
}
