package com.ktmmobile.msf.domains.form.common.service;

import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.form.common.dao.FCommonDao;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.WorkNotiDto;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
/**
 *
 */
@Slf4j
@Service
public class IpStatisticService {

    @Autowired
    private FCommonDao fCommonDao;


    public boolean insertAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        mcpIpStatisticDto.setIp(RequestUtils.getClientIp());
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if (userSession != null) {
            mcpIpStatisticDto.setUserid(userSession.getUserId());
        }
        mcpIpStatisticDto.setUrl(request.getRequestURI());
        mcpIpStatisticDto.setPlatformCd(NmcpServiceUtils.getPlatFormCd());

        // FIXME: SessionUtils 의존성 제거
        WorkNotiDto workNotiDto = SessionUtils.getCurrentMenuUrl();
        if (workNotiDto != null) {
            mcpIpStatisticDto.setMenuSeq(workNotiDto.getMenuSeq());
            mcpIpStatisticDto.setUrlSeq(workNotiDto.getUrlSeq());
            //        } else
            //            mcpIpStatisticDto.setMenuSeq("999999999");
            //            mcpIpStatisticDto.setUrlSeq("999999999");
        }
        log.debug("getMenuSeq:{},getUrlSeq:{}", mcpIpStatisticDto.getMenuSeq(), mcpIpStatisticDto.getUrlSeq());

        return 0 < fCommonDao.insertIpStat(mcpIpStatisticDto);
    }

    /**
     * 요금제 예약변경 이력 삭제
     * @param rateResChgSeq
     * @return
     */
    public int deleteRateResChgAccessTrace(String rateResChgSeq) {
        return fCommonDao.deleteRateResChgAccessTrace(rateResChgSeq);
    }

    /**
     * 요금제 예약변경 이력조회
     * @param mcpIpStatisticDto
     * @return
     */
    public String selectRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return fCommonDao.selectRateResChgAccessTrace(mcpIpStatisticDto);
    }


    public String getReferer() {
        String referer = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        referer = request.getHeader("referer");
        return referer;
    }

    /*
     * MCP_ADMIN_ACCESS_TRACE  관리자 접속 이력 추적 정보 테이블
     * 개발자 추적 정보도 같이 저장 처리 ...
     */

    public boolean insertAdminAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        mcpIpStatisticDto.setIp(RequestUtils.getClientIp());
        UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if (userSession != null) {
            mcpIpStatisticDto.setUserid(userSession.getUserId());
        }
        mcpIpStatisticDto.setUrl(request.getRequestURI());

        return 0 < fCommonDao.insertIpStatAdmin(mcpIpStatisticDto);
    }

    /**
     * recaptcha 로그 기록
     * @param recaptchaLogMap
     */
    public int insertRecaptchaLog(Map<String, String> recaptchaLogMap) {
        return fCommonDao.insertRecaptchaLog(recaptchaLogMap);
    }

    //private static String getPrcsSbst(HttpServletRequest request) {
    //    String connect = NmcpServiceUtils.getDeviceType();
    //    if (!"APP".equals(connect)) {
    //        String url = request.getServletPath();
    //        if (url == null || url.isEmpty()) {
    //            connect = "NONE";
    //        } else {    //url 이있으면 모바일, pc 분기함
    //            if ("Y".equals(NmcpServiceUtils.isMobile())) {
    //                connect = "MOBILE";
    //            } else {
    //                connect = "PC";
    //            }
    //        }
    //    }
    //    return connect;
    //}

}
