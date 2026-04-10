package com.ktmmobile.mcp.common.service;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ktmmobile.mcp.common.dao.SnsDao;
import com.ktmmobile.mcp.common.dto.LoginHistoryDto;
import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.SiteMenuDto;
import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;

@Service
public class SnsSvcImpl implements SnsSvc{

    private static final Logger logger = LoggerFactory.getLogger(SnsSvcImpl.class);

    @Autowired
    SnsDao snsDao;

    @Autowired
    LoginSvc loginSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    IpStatisticService ipstatisticService;

    @Override
    public SnsLoginDto selectSnsIdCheck(String snsId) {
        return snsDao.selectSnsIdCheck(snsId);
    }

    @Override
    public UserSessionDto selectSnsMcpUser(String userId) {
        return snsDao.selectSnsMcpUser(userId);
    }

    @Override
    public String snsLoginProcess(HttpServletRequest request, UserSessionDto userSessionDto, String snsId) {

        SessionUtils.invalidateSession();
        SessionUtils.saveUserSession(userSessionDto);	//사용자 세션저장

        /*조회수 증가*/
        String userId = userSessionDto.getUserId();
        loginSvc.updateHit(userId);

        //로그인 히스토리에 저장 시작
        LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
        loginHistoryDto.setUserid(userSessionDto.getUserId());
        if("Y".equals(NmcpServiceUtils.isMobile())){
            loginHistoryDto.setIntype("M");
        }else {
            loginHistoryDto.setIntype("P");
        }
        loginHistoryDto.setName(userSessionDto.getName());
        loginHistoryDto.setPhone(userSessionDto.getMobileNo());

        fCommonSvc.insertLoginHistory(loginHistoryDto);

        //SNS 로그인 정보 저장
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("loginResult", "0");
        param.put("snsId", snsId);
        param.put("userId", userId);
        updateSnsLoginInfo(param);

        SnsLoginDto snsLoginDto = selectSnsIdCheck(snsId);
        if(snsLoginDto != null) {
            try {
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setIp(ipstatisticService.getClientIp());
                mcpIpStatisticDto.setUrl(request.getRequestURI());
                mcpIpStatisticDto.setParameter("SNS_LOGIN");

                mcpIpStatisticDto.setPrcsMdlInd(InetAddress.getLocalHost().getHostName());
                String connect = NmcpServiceUtils.getDeviceType();
                if(!"APP".equals(connect)) {
                    String url = request.getServletPath();
                    if(url == null || "".equals(url)  ){
                        connect = "NONE";
                    }else{	//url 이있으면 모바일, pc 분기함
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            connect = "MOBILE";
                        }else{
                            connect = "PC";
                        }
                    }
                }
                mcpIpStatisticDto.setPrcsSbst(connect);
                String strUserId = userId;
                if ( strUserId.length() >= 20) {
                    strUserId = strUserId.substring(0, 20);
                }
                mcpIpStatisticDto.setUserid(strUserId);
                mcpIpStatisticDto.setLoginDivCd(snsLoginDto.getSnsCd());
                mcpIpStatisticDto.setLoginSeq(String.valueOf(snsLoginDto.getSnsLoginSeq()));
                SiteMenuDto siteMenuDto = SessionUtils.getCurrentMenuDto();
                if(siteMenuDto != null) {
                    mcpIpStatisticDto.setMenuSeq(siteMenuDto.getMenuSeq()+"");
                    mcpIpStatisticDto.setUrlSeq(siteMenuDto.getRepUrlSeq()+"");
//                } else {
//                    mcpIpStatisticDto.setMenuSeq("999999");
//                    mcpIpStatisticDto.setUrlSeq("999999");
                }
                ipstatisticService.insertAccessTrace(mcpIpStatisticDto);

            } catch(DataAccessException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch(Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }


        //로그인 히스토리에 저장 끝
        if(userSessionDto.getBirthday() == null || "".equals(userSessionDto.getBirthday()) ){	// 생일,성별이 없으면 입력받음
            return "-2";
        }

        if(userSessionDto.getMonCnt() >= 3) {	//비밀번호 변경후 3개월 경과하면
            if(userSessionDto.getDayCnt() != -1 && userSessionDto.getDayCnt() <= 14) {
                logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 비밀번호 변경후 3개월 경과 and 2주 안지남");
            }else {
                return "-1";
            }
            /*
            boolean checkCookie = false;

            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if("pwChgCookie".equals(cookie.getName()) && (cookie.getValue() != null && !"".equals(cookie.getValue()))) {
                    checkCookie = true;
                    break;
                }
            }

            //pwChgCookie 값이 있으면 비밀번호 변경후 3개월 경과해도 그냥 통과시킴
            if(checkCookie) {
                logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 비밀번호 변경후 3개월 경과 and 쿠키값있음");
            } else {	//비밀번호 변경후 3개월 경과하고 pwChgCookie 쿠키값도 없으면 비밀번호 변경선택 페이지로 이동
                return "-1";
            }
            */
        }
        return "0000";
    }

    @Override
    public int updateSnsLoginInfo(HashMap<String, String> param) {
        return snsDao.updateSnsLoginInfo(param);
    }

}
