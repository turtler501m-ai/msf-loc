
package com.ktmmobile.msf.domains.form.common.service;

import java.net.URLDecoder;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.domains.form.common.dao.FCommonDao;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.WorkNotiDto;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

import java.net.InetAddress;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

/**
 *
 */

@Service
public class IpStatisticService {
    private static final Logger logger = LoggerFactory.getLogger(IpStatisticService.class);
    @Autowired
    private FCommonDao fCommonDao;

    /**
     * <pre>
     * 설명     :로그인 있을때만 넣기
     * @param
     * @return
     * @return:
     * </pre>
     */
    public void insertIpStat(HttpServletRequest request)  {
        try{
            if(StringUtils.endsWith(request.getRequestURI(), "jsp")) {
                return;
            }

            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setIp(this.getClientIp());
            mcpIpStatisticDto.setUrl(request.getRequestURI());
            if(request.getQueryString()!=null){
                mcpIpStatisticDto.setParameter(URLDecoder.decode(request.getQueryString(), UTF_8));
            }

            UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
            if(userSession !=null){
                String strUserId = userSession.getUserId();
                if (strUserId.length() > 20) {
                    strUserId = strUserId.substring(0, 20);
                }
                mcpIpStatisticDto.setUserid(strUserId);
                mcpIpStatisticDto.setLoginDivCd(StringUtil.NVL(userSession.getLoginDivCd(),""));
            }

            mcpIpStatisticDto.setPrcsMdlInd(InetAddress.getLocalHost().getHostName());
            mcpIpStatisticDto.setPrcsSbst(getPrcsSbst(request));
            this.insertAccessTrace(mcpIpStatisticDto);
            this.increasePageViewCount();
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public boolean insertAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        mcpIpStatisticDto.setIp(this.getClientIp());
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession !=null){
            mcpIpStatisticDto.setUserid(userSession.getUserId());
        }
        mcpIpStatisticDto.setUrl(request.getRequestURI());
        mcpIpStatisticDto.setPlatformCd(NmcpServiceUtils.getPlatFormCd());

        WorkNotiDto workNotiDto = SessionUtils.getCurrentMenuUrl();
        if(workNotiDto != null) {
            mcpIpStatisticDto.setMenuSeq(workNotiDto.getMenuSeq());
            mcpIpStatisticDto.setUrlSeq(workNotiDto.getUrlSeq());
//        } else
//            mcpIpStatisticDto.setMenuSeq("999999999");
//            mcpIpStatisticDto.setUrlSeq("999999999");
        }
        logger.debug("getMenuSeq:{},getUrlSeq:{}", mcpIpStatisticDto.getMenuSeq(),mcpIpStatisticDto.getUrlSeq());

        return 0 < fCommonDao.insertIpStat(mcpIpStatisticDto);
    }

    /**
     * 요금제 예약변경 이력 저장
     * @param mcpIpStatisticDto
     * @return
     */
    public boolean insertRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return 0 < fCommonDao.insertRateResChgAccessTrace(mcpIpStatisticDto);
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

    public String getClientIp()  {
        String clientIp = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if(request.getHeader("X-Forwarded-For") == null) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp !=null && ! clientIp.equals("") && clientIp.indexOf(",")>-1) {
                clientIp =  clientIp.split("\\,")[0].trim();
            }
        }

        return clientIp;
    }


    public String getReferer()  {
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
        mcpIpStatisticDto.setIp(this.getClientIp());
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession !=null){
            mcpIpStatisticDto.setUserid(userSession.getUserId());
        }
        mcpIpStatisticDto.setUrl(request.getRequestURI());

        return 0 < fCommonDao.insertIpStatAdmin(mcpIpStatisticDto) ;
    }
    /**
     * <pre>
     * 설명     : 이력정보 조회
     * @param McpIpStatisticDto
     * @param
     * @return
     * @return: McpIpStatisticDto
     * </pre>
     */
    public List<McpIpStatisticDto> getAdminAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        return fCommonDao.getAdminAccessTrace(mcpIpStatisticDto) ;
    }

    /**
     * recaptcha 로그 기록
     * @param recaptchaLogMap
     */
    public int insertRecaptchaLog(Map<String, String> recaptchaLogMap) {
        return fCommonDao.insertRecaptchaLog(recaptchaLogMap);
    }

    private static String getPrcsSbst(HttpServletRequest request) {
        String connect = NmcpServiceUtils.getDeviceType();
        if(!"APP".equals(connect)) {
            String url = request.getServletPath();
            if(url == null || url.isEmpty()){
                connect = "NONE";
            }else{	//url 이있으면 모바일, pc 분기함
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    connect = "MOBILE";
                }else{
                    connect = "PC";
                }
            }
        }
        return connect;
    }

    private void increasePageViewCount() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();

        mcpIpStatisticDto.setUrl(request.getRequestURI());
        mcpIpStatisticDto.setPlatformCd(NmcpServiceUtils.getPlatFormCd());

        fCommonDao.updatePageViewCount(mcpIpStatisticDto);
    }
}
