package com.ktmmobile.mcp.common.interceptor;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

public class SessionChkInterceptor extends HandlerInterceptorAdapter {

    @Deprecated
    private static final Logger logger = LoggerFactory.getLogger(SessionChkInterceptor.class);


    /**
     * 설명 : 인증 유지 관리, 등록한 url 기준
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param dataBinder
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        /* 주문배송 비로그인 상태 인증 유효값 제거 임시...(각 세션정보들의URL 공토코드로 처리해야할듯함) */
        try {
            boolean ajax="XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
            if(!ajax){

                AuthSmsDto orderSms = SessionUtils.getSmsSession("order");
                AuthSmsDto qnaSms = SessionUtils.getSmsSession("QNA");
                AuthSmsDto shareDataSms = SessionUtils.getSmsSession("shareData");
                AuthSmsDto sharingDataSms = SessionUtils.getSmsSession("sharing");
                AuthSmsDto fathSms = SessionUtils.getSmsSession("fath");

//            	AuthSmsDto mypageSms = SessionUtils.getSmsSession("mypage");

                if(orderSms != null) {
                    ArrayList<String> orderUrlList = new ArrayList<String>();
                    orderUrlList.add("/order/orderList.do");
                    orderUrlList.add("/order/orderTempList.do");
                    orderUrlList.add("/appForm/reqSelfDlvryList.do");
                    orderUrlList.add("/certSmsNomemberView.do");

                    boolean sessionRemove = true;
                    for (String urlString : orderUrlList) {
                        if(request.getRequestURI().indexOf(urlString) > -1) {
                            sessionRemove = false;
                            continue;
                        }
                    }

                    if(sessionRemove) {
                           request.getSession().removeAttribute(SessionUtils.COMM_AUTH_SMS_INFO.concat("_").concat("order"));
                    }
                }

                if(qnaSms != null) {
                    ArrayList<String> qnaUrlList = new ArrayList<String>();
                    qnaUrlList.add("/cs/csInquiryInt.do");
                    qnaUrlList.add("/cs/csInquiryIntHist.do");
                    qnaUrlList.add("/m/cs/csInquiryInt.do");
                    qnaUrlList.add("/m/cs/csInquiryIntHist.do");

                    boolean sessionRemove = true;
                    for (String urlString : qnaUrlList) {
                        if(request.getRequestURI().indexOf(urlString) > -1) {
                            sessionRemove = false;
                            continue;
                        }
                    }

                    if(sessionRemove) {
                           request.getSession().removeAttribute(SessionUtils.COMM_AUTH_SMS_INFO.concat("_").concat("QNA"));
                    }
                }

                if(shareDataSms != null) {
                    ArrayList<String> orderUrlList = new ArrayList<String>();
                    orderUrlList.add("/content/myShareDataView.do");
                    orderUrlList.add("/myShareDataCntrInfo.do");
                    orderUrlList.add("/content/myShareDataList.do");
                    orderUrlList.add("/content/reqShareDataView.do");
                    orderUrlList.add("/content/reShareDataComplete.do");

                    boolean sessionRemove = true;
                    for (String urlString : orderUrlList) {
                        if(request.getRequestURI().indexOf(urlString) > -1) {
                            sessionRemove = false;
                            continue;
                        }
                    }

                    if(sessionRemove) {
                           request.getSession().removeAttribute(SessionUtils.COMM_AUTH_SMS_INFO.concat("_").concat("shareData"));
                    }
                }

                if(sharingDataSms != null) {
                    ArrayList<String> orderUrlList = new ArrayList<String>();
                    orderUrlList.add("/mySharingCntrInfo.do");
                    orderUrlList.add("/content/mySharingView.do");
                    orderUrlList.add("/content/reqSharingView.do");
                    orderUrlList.add("/content/reqSharingCompleteView.do");


                    orderUrlList.add("/content/dataSharingStep1.do");
                    orderUrlList.add("/content/dataSharingStep2.do");
                    orderUrlList.add("/content/dataSharingStep3.do");


                    boolean sessionRemove = true;
                    for (String urlString : orderUrlList) {
                        if(request.getRequestURI().indexOf(urlString) > -1) {
                            sessionRemove = false;
                            continue;
                        }
                    }

                    if(sessionRemove) {
                           request.getSession().removeAttribute(SessionUtils.COMM_AUTH_SMS_INFO.concat("_").concat("sharing"));
                    }
                }


                if(fathSms != null) {
                    ArrayList<String> fathUrlList = new ArrayList<String>();
                    fathUrlList.add("/fath/fathAuth.do");
                    fathUrlList.add("/fath/fathSelf.do");
                    
                    boolean sessionRemove = true;
                    for (String urlString : fathUrlList) {
                        if(request.getRequestURI().indexOf(urlString) > -1) {
                            sessionRemove = false;
                            continue;
                        }
                    }

                    if(sessionRemove) {
                           request.getSession().removeAttribute(SessionUtils.COMM_AUTH_SMS_INFO.concat("_").concat("fath"));
                    }
                }


                ArrayList<String> certUrlList = new ArrayList<String>();
                certUrlList.add("/findPassword.do"); 				// 아이디찾기, 비밀번호 찾기
                certUrlList.add("/login/dormantUserView.do"); 		// 휴먼회원 해제
                certUrlList.add("/join/fstPage.do"); 				// 회원가입
                certUrlList.add("/mypage/suspendView01.do"); 		// 일시정지/해제
                certUrlList.add("/mypage/unpaidChargeList.do");  	// 즉시납부
                certUrlList.add("/mypage/chargeView05.do"); 		// 납부방법변경
                certUrlList.add("/cs/ownPhoNum.do"); 				// 가입번호 조회
                certUrlList.add("/m/appform/setUsimNoDlvey.do");  	// 유심번호 등록인증(모바일만 존재)
                certUrlList.add("/mypage/myNameChg.do"); 			// 명의변경 신청
                certUrlList.add("/mypage/reqCallList.do"); 			// 통화내역 열람신청
                certUrlList.add("/mypage/reqJoinForm.do"); 			// 가입신청서 출력 요청
                certUrlList.add("/mypage/reqUsimPuk.do"); 			// 유심puk번호 열람신청
                certUrlList.add("/mypage/prvCommData.do"); 		 	// 통신자료 제공내역 조회 요청
                certUrlList.add("/mypage/reqInsr.do"); 				// 휴대폰 안심보험 신청
                certUrlList.add("/mypage/reqRwd.do"); 				// 자급제 보상서비스
                certUrlList.add("/point/mstoreView.do"); 			// M스토어
                certUrlList.add("/mypage/usimSelfChg.do"); 			// USIM 셀프변경
                certUrlList.add("/m/mcash/mcashView.do"); 			// M쇼핑할인
                certUrlList.add("/mypage/userInfoView.do"); 		// 회원정보 변경
                certUrlList.add("/mypage/cancelConsult.do");		// 해지 상담 신청
                certUrlList.add("/apply/replaceUsimView.do");		// 교체 유심 신청
                certUrlList.add("/m/apply/replaceUsimSelf.do");	// 교체 유심 등록(쎌프 변경)

                // SMS 팝업 -------------------------------------------------------------------
                certUrlList.add("/mypage/reSpnsrPlcyDc.do"); 		// 요금할인 재약정 신청
                certUrlList.add("/mypage/numberView01.do");			// 번호변경
                certUrlList.add("/mypage/pullData01.do");			// 당겨쓰기
                certUrlList.add("/mypage/myinfoView.do");			// 가입증명원 인쇄
                certUrlList.add("/mypage/multiPhoneLine.do");		// 정회원 인증, 다회선 추가
                certUrlList.add("/requestReView/requestReviewForm.do");  // 사용후기 작성
                certUrlList.add("/requestReView/requestReView.do");  // 사용후기 삭제
                certUrlList.add("/mcash/review/mcashReviewForm.do");  // 사용후기 작성

                // SMS JSP -------------------------------------------------------------------
                certUrlList.add("/m/rate/spnsrRecontract.do"); 		// 요금할인 재약정 신청2 (모바일만 존재)
                certUrlList.add("/event/frndRecommend.do"); 	// 친구초대
                certUrlList.add("/event/frndRecommendReqView.do"); 	// 친구초대
                certUrlList.add("/myShareDataCntrInfo.do");  		// 함께쓰기 (비로그인)
                certUrlList.add("/content/myShareDataView.do");     // 함께쓰기 (로그인)
                certUrlList.add("/content/combineWireless.do"); 	// 아무나결합
                certUrlList.add("/content/combineKt.do"); 			// 아무나 가족결합
                certUrlList.add("/m/gift/giftPromotion.do"); 		// 사은품 신청 (모바일만 존재)
                certUrlList.add("/certSmsNomemberView.do"); 		// 신청조회
                certUrlList.add("/cs/csInquiryIntHist.do"); 		// 내문의 확인하기
                certUrlList.add("/cs/csInquiryInt.do");          	// 1:1 문의작성
                certUrlList.add("/m/gift/consentGift.do");			// 사은품 지급 (제세공과금 신고를 위한 개인정보 수집 동의)
                certUrlList.add("/fath/fathAuth.do");	    		// 셀프안면인증

                for (String urlString : certUrlList) {
                    if(request.getRequestURI().indexOf(urlString) > -1) {

                        String pageType= urlString.substring(urlString.lastIndexOf('/')+1,urlString.lastIndexOf('.'));

                        // 로그인 상태만 세션 제거
                        if("myShareDataView".equals(pageType)){
                            UserSessionDto userSession = SessionUtils.getUserCookieBean();
                            if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
                                break;
                            }
                        }

                        // 비로그인 상태만 세선 제거
                        if("requestReView".equals(pageType) || "csInquiryInt".equals(pageType)){
                            UserSessionDto userSession = SessionUtils.getUserCookieBean();
                            if(userSession!=null && !StringUtils.isEmpty(userSession.getUserId())) {
                                break;
                            }
                        }

                        // sms인증하지 않은 경우 세선 제거
                        if("csInquiryInt".equals(pageType)){
                            AuthSmsDto smsDto= SessionUtils.getSmsSession("QNA");
                            if(smsDto != null && smsDto.isResult()) {
                                break;
                            }
                        }

                        SessionUtils.removeCertSession();
                        SessionUtils.setPageSession(pageType);
                        break;
                    }
                }

//            	if(mypageSms != null) {
//                	ArrayList<String> mypageUrlList = new ArrayList<String>();
//
//                	boolean sessionRemove = true;
//                	for (String urlString : mypageUrlList) {
//    					if(request.getRequestURI().indexOf(urlString) > -1) {
//    						sessionRemove = false;
//    						continue;
//    					}
//    				}
//
//                	if(sessionRemove) {
//               			request.getSession().removeAttribute(SessionUtils.COMM_AUTH_SMS_INFO.concat("_").concat("mypage"));
//                	}
//            	}

            }
        } catch(RuntimeException e){
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        return true;
    }
}
