package com.ktmmobile.mcp.common.interceptor;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.net.InetAddress;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktmmobile.mcp.common.dto.SiteMenuDto;
import com.ktmmobile.mcp.common.dto.WorkNotiDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;

public class IpstatisticInterceptor extends HandlerInterceptorAdapter {
	@Deprecated
    private static final Logger logger = LoggerFactory.getLogger(IpstatisticInterceptor.class);
    @Autowired
    IpStatisticService ipStatisticService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            if ("/workInfo.do".equals(request.getRequestURI()) || "/m/workInfo.do".equals(request.getRequestURI()) || "/appWorkInfo.do".equals(request.getRequestURI())) {
                return true;
            }

            //전체 작업공지는 코드 테이블의 값 이용
            NmcpCdDtlDto allCode = NmcpServiceUtils.getCodeNmDto("mplatFormServiceWork", "ALL");

        	//작업공지 우회 IP
        	String curIP = ipStatisticService.getClientIp();
        	boolean accessAllow =  NmcpServiceUtils.getAcesAlwdList(curIP);

        	//작업공지 우회 URL - as-is app, , 캐시 리프레시
        	String[] passUrl = {"/resetWorkNotiListAjax.do","/resetComCodeListAjax.do","/resetMenuListAjax.do","/resetBannerListAjax.do","/resetPopupListAjax.do"
        			,"/app/getVersionJson.do","/app/setPushSendJson.do","/app/getWidgetDataDecJson.do"};

        	for(String purl : passUrl) {
        		if(purl.equals(request.getRequestURI())) {
        			accessAllow = true;
        			break;
        		}
        	}

        	//전체코드 테스트
        	//-수동 하드코딩- db작업으로 캐쉬 정보 없을경우 수동으로 전체 작업 공지
        	//allCode = new NmcpCdDtlDto();

        	if(!accessAllow) {

            	//app URL - app 연동
            	String[] allowUrl = {"/m/set/appVersionInfoAjax.do","/m/set/appPushInfoAjax.do","/m/get/appWidgetDataAjax.do"};
            	boolean appCallUrl = false;

            	for(String aurl : allowUrl) {
            		if(aurl.equals(request.getRequestURI())) {
            			appCallUrl = true;
            			break;
            		}
            	}

                if (allCode != null) { //전체 작업 공지
                    boolean ajax="XMLHttpRequest".equals(request.getHeader("X-Requested-With")); //요청 url AJAX 여부

                    if (appCallUrl) { //앱에서 사용하는 url일경우
                    	RequestDispatcher rd = request.getRequestDispatcher("/appWorkInfo.do?url="+request.getRequestURI());
                    	rd.forward(request, response);
                        return false;

                    } else if (ajax) { //ajax url인경우
                    	NmcpCdDtlDto workInfoMsg = NmcpServiceUtils.getCodeNmDto("mplatFormServiceWorkMsg", "msg");
                        String  msg = "kt전산 업그레이드로 인한 서비스 중단";  //-수동 하드코딩 ajax alert 문구-
                        if (workInfoMsg != null) {
                            msg = StringUtil.NVL(workInfoMsg.getExpnsnStrVal1(), "");
                        }
                        throw new McpCommonJsonException(msg);

                    } else { //ajax url아닌 경우
                    	String redirectUrl = "";
                        RequestDispatcher rd = request.getRequestDispatcher("/workInfo.do?redirectUrl="+ redirectUrl);
                        if("Y".equals(NmcpServiceUtils.isMobile())) {
                            rd = request.getRequestDispatcher("/m/workInfo.do?redirectUrl="+ redirectUrl);
                        }
                        rd.forward(request, response);
                        return false;
                    }
                } else { //URL 작업공지

                	//url 별 작업공지는 메뉴목록
                	List<WorkNotiDto> workNotiList =  NmcpServiceUtils.getWorkNotiList("workNoti");

                    if (workNotiList != null  && workNotiList.size() > 0) {
                        boolean ajax="XMLHttpRequest".equals(request.getHeader("X-Requested-With")); //요청 url AJAX 여부
                        for (WorkNotiDto workNotiDto : workNotiList) {

                            if (request.getRequestURI().equals(StringUtil.NVL(workNotiDto.getUrl(), ""))) { //작업공지 url일경우

                                if (appCallUrl) { //앱에서 사용하는 ajax url일경우
                                	RequestDispatcher rd = request.getRequestDispatcher("/appWorkInfo.do?url="+request.getRequestURI());
                                	rd.forward(request, response);
                                    return false;

                                } else if (ajax) { //ajax url인경우
                                	NmcpCdDtlDto workInfoMsg = NmcpServiceUtils.getCodeNmDto("mplatFormServiceWorkMsg", "msg");
                                    String  msg = "";
                                    if (workInfoMsg != null) {
                                        msg = StringUtil.NVL(workInfoMsg.getExpnsnStrVal1(), "");
                                    }
                                    throw new McpCommonJsonException(msg);

                                } else { //ajax url아닌 경우
                                    String redirectUrl = "";
                                    RequestDispatcher rd = request.getRequestDispatcher("/workInfo.do?redirectUrl="+ redirectUrl);
                                    if("Y".equals(NmcpServiceUtils.isMobile())) {
                                        rd = request.getRequestDispatcher("/m/workInfo.do?redirectUrl="+ redirectUrl);
                                    }
                                    rd.forward(request, response);
                                    return false;
                                }
                            }
                        }
                    }
                }
        	}

        } catch  (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        //KT차세대 관련 작업 공지 -S-

        try {

            //ajax 호출여부 확인
            boolean ajax="XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
            //ajax호출은 로그 기록 안함
            if(!ajax){//
            	int existDo = request.getRequestURI().lastIndexOf(".do");
            	if(existDo > 0) {
                	//url 세션에 저장
	            	List<SiteMenuDto> allMenuList =  NmcpServiceUtils.getMenuList("");
	            	SiteMenuDto siteMenuDto = null;
	            	for(SiteMenuDto menudto : allMenuList) {
	            		if (request.getRequestURI().equals(menudto.getUrlAdr())) { //앱 위젯 url일경우
	            			if(!"".equals(menudto.getPrntsKey())) {
	            				String menuCode = menudto.getMenuCode();
	            				if(menuCode.indexOf("MENU") > 0){
	            					//menuCode = menuCode.replaceAll("PCMENU", "").replaceAll("MOMENU", "");
	            					//if(menuCode.length() >= 4) {
				            			siteMenuDto = menudto;
				            			//break;
	            					//}
	            				}
	            			}
	            		}
	            	}

	            	// workNotiList
                	List<WorkNotiDto> workNotiList =  NmcpServiceUtils.getWorkNotiList("");

                	WorkNotiDto workNotiDto = null;
                    for (WorkNotiDto workNoti : workNotiList) {
                        if (request.getRequestURI().equals(StringUtil.NVL(workNoti.getUrl(), ""))) {
                        	workNotiDto = workNoti;
                        	break;
                        }
                    }

                	if(workNotiDto != null) {

                		String menuseq = "";
                		String urlseq = "";
                		String menucode = "";

            			urlseq = workNotiDto.getUrlSeq();

		            	if(siteMenuDto != null) {
	            			menuseq = String.valueOf(siteMenuDto.getMenuSeq()).replaceAll("null", "");
		            	}

	                    // beforeDto
		                SiteMenuDto beforeDto = SessionUtils.getCurrentMenuDto();

		                if(beforeDto != null) {
		            		if("".equals(menuseq)) {
		            			menuseq = String.valueOf(beforeDto.getMenuSeq()).replaceAll("null", "");
		            		}
		            		menucode = beforeDto.getMenuCode().replaceAll("null", "");
		                }

		            	if(siteMenuDto == null) {
		            		siteMenuDto = new SiteMenuDto();
		                	siteMenuDto.setPlatformCd(NmcpServiceUtils.getPlatFormCd());
		                	siteMenuDto.setCretDt(DateTimeUtil.getUTCTimeString());
		                	siteMenuDto.setMenuCode(menucode);
		                	siteMenuDto.setMenuNm(workNotiDto.getUrlNm());
		                	siteMenuDto.setUrlAdr(workNotiDto.getUrl());
		            	}

		            	if("".equals(menuseq)) {
		            		menuseq = this.getDirectMenuSeq(request.getRequestURI());
	            		}

	            		if("".equals(urlseq)) {
	            			urlseq = "999999";
	            		}
	                	siteMenuDto.setMenuSeq(Integer.parseInt(menuseq));
	                	siteMenuDto.setRepUrlSeq(Integer.parseInt(urlseq));
		    			SessionUtils.saveCurrentMenuDto(siteMenuDto);

		    			workNotiDto.setMenuSeq(menuseq);
		    			workNotiDto.setUrlSeq(urlseq);

                    	SessionUtils.saveCurrentMenuUrlDto(workNotiDto);

                    	if("Y".equals(workNotiDto.getStatLdupYn())) {
                        	ipStatisticService.insertIpStat(request);
							this.saveRefererToSession(request);
                    	}
                	}
            	}
            }
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        return true;
    }

	private String getDirectMenuSeq(String requestURI) {

		String menuseq = "999999";
		if("/appForm/appFormDesignView.do".equals(requestURI)) {
			menuseq = "10013";
    	}
    	if("/m/appForm/appFormDesignView.do".equals(requestURI)) {
    		menuseq = "10095";
    	}
		return menuseq;
	}

	private void saveRefererToSession(HttpServletRequest request) {
		/*
		 * 유입경로 session 저장 처리
		 * 인자값 referer 대해 session 저장후 온라인 신청서 등록 할때.. 저장 처리 .
		 */
		try {
			String referer = request.getHeader("REFERER");
			if(StringUtils.isNotEmpty(referer)){
				if(!referer.contains("ktmmobile.com") && !referer.contains("nice.checkplus.co.kr")){ // 타 도메인일경우
					referer = referer.replaceAll("(?i:https?://([^/]+)/.*)", "$1");
				} else {
					//ktmmobile.com , nice.checkplus.co.kr 제외
					referer = "";
				}
			}

			if(StringUtils.isNotEmpty(referer)){
				SessionUtils.saveEtcDomainReferer(referer);
			}

			// 유입 제휴 코드 세션 저장
			String partner = request.getParameter("partner");
			if (StringUtils.isNotEmpty(partner)) {
				SessionUtils.saveCoalitionInflow(partner);
			}
			// 2025-05-23, partner=kbank 로 진입할 경우 유입 제휴 코드 세션 삭제
			if ("kbank".equals(partner)) {
				SessionUtils.saveCoalitionInflow(null);
			}

		} catch (Exception e1) {
			logger.error("referer error==>{}", e1.getMessage());
		}
	}
}
