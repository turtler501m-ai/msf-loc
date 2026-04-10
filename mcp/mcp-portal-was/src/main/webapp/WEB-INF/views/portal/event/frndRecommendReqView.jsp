<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutMainDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript"
            src="/resources/js/portal/event/frndRecommendReqView.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" id="userDivision" value="${userDivision}"/>
        <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">친구초대</h2>
          </div>
          <div class="friend-banner">
                <div class="friend-banner__wrap">
                    <h3>
                        친구도 나도 함께 받는 혜택<br />
                        <span>친구초대 이벤트</span>
                    </h3>
                    <div class="friend-banner__button">
                        <a href="/mypage/reCommendState.do" title="내 초대 내역 확인 페이지 바로가기">
                            <span>내 초대 내역 확인하기</span>
                            <i class="c-icon c-icon--anchor-purple" aria-hidden="true"></i>
                        </a>
                    </div>
                    <img src="../../resources/images/portal/event/friend_banner.png" alt="" aria-hidden="true">
                </div>
          </div>
          <div class="c-tabs c-tabs--type1">
            <div class="c-tabs__list">
              <a class="c-tabs__button" id="tab1" href="/event/frndRecommend.do" role="tab" aria-selected="false">친구초대란?</a>
              <a class="c-tabs__button is-active" id="tab2" href="/event/frndRecommendReqView.do" aria-current="page" role="tab" aria-selected="true">친구 초대하기</a>
            </div>
          </div>
          <div class="c-tabs__panel" id="tabB1panel">

          </div>
          <div class="c-tabs__panel u-block" id="tabB2panel">
            <div>
              <div class="c-row c-row--lg">
              	<c:if test="${empty maskingSession}">
		      	  	<div class="masking-badge-wrap">
				        <div class="masking-badge">
				        	<a class="masking-badge__button" href="/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
				        		<i class="masking-badge__icon" aria-hidden="true"></i>
				 				<p>가려진(*)<br />정보보기</p>
				       		</a>
				        </div>
			        </div>
		        </c:if>
                <p class="c-text c-text--fs17">친구초대 ID 발급을 위해서는 개인정보 제공을 위한 동의 및 가입 여부의 확인이 필요합니다.내용을 확인하시고 동의하여 주시기 바랍니다.</p>
                <p class="c-bullet c-bullet--fyr u-co-sub-4">친구초대ID 발급 및 혜택 적용을 위하여 고객님의 개인정보가 이용될 수 있으며, 초대ID 활용에 대한 책임은 고객에게 있습니다.</p>
                <div class="c-form-wrap u-mt--24">
                  <div class="c-agree">
                    <div class="c-agree__item">
                      <div class="c-agree__inner">
                        <input class="c-checkbox" id="chkAgreeA" type="checkbox" name="chkAgreeA">
                        <label class="c-label" for="chkAgreeA">위 개인정보 이용 및 책임에 대한 내용을 확인하였으며, 이에 동의합니다.</label>
                      </div>
                    </div>
                  </div>
                  <div class="c-agree u-mt--8">
                    <div class="c-agree__item">
                        <button type="button" class="c-button c-button--xsm" onclick="fnSetEventId('chkMstoreAgree'); openPage('termsPop','/termsPop.do','cdGroupId1=FormEtcCla&cdGroupId2=${Constants.FRND_MSTORE_TERMS_CD_1}', '0');"  >
                            <span class="c-hidden">결합을 위한 필수 확인 사항 동의 상세보기</span>
                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                        <div class="c-agree__inner">
                            <input class="c-checkbox" id="chkMstoreAgree" type="checkbox" name="chkMstoreAgree">
                            <label class="c-label" for="chkMstoreAgree">개인정보 제 3자 제공 동의(필수)</label>
                        </div>
                    </div>
                  </div>
                </div>
                <c:if test="${nmcp:hasLoginUserSessionBean()}">
                	<%@ include file="/WEB-INF/views/portal/mypage/sendCertSmsLogin.jsp"%>
                </c:if>
                <c:if test="${!nmcp:hasLoginUserSessionBean()}">
                <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
                </c:if>
                <p class="c-bullet c-bullet--fyr u-co-sub-4">'나에게 문자 발송' 선택 시 인증한 휴대폰 번호로 초대ID 및 URL이 발송됩니다.</p>
                <p class="c-bullet c-bullet--fyr u-co-sub-4">전달한 친구초대 URL을 통해 접속하셨을 경우 신규 가입 신청서에 추천인ID가 신청서에 자동 기입됩니다.</p>
                <p class="c-bullet c-bullet--fyr u-co-sub-4">전용 URL을 사용하지않을 경우 발급받으신 친구초대ID를 피추천인이 신청서에 직접 입력하셔야 혜택을 받으실 수 있습니다.</p>

                <div class="c-button-wrap u-mt--40">
                  <!-- [2022-01-20] c-buuton-roun--lg &gt; c-button-roun--md 클래스 수정-->
                  <button class="c-button c-button-round--md c-button--kakao u-width--220" id="frndKakaoSend">
                    <i class="c-icon c-icon--message" aria-hidden="true"></i>
                    <span>카카오톡 공유</span>
                  </button><!-- [$2022-01-20] c-buuton-roun--lg &gt; c-button-roun--md 클래스 수정-->
                  <button class="c-button c-button-round--md c-button--sub-4 u-width--220" id="frndSmsSend">
                    <i class="c-icon c-icon--message--white" aria-hidden="true"></i>
                    <span>나에게 문자 발송</span>
                  </button>
                </div>
                 <div class="note-box">
                <h3 class="c-heading c-title--type2">유의사항</h3>
                <hr class="c-hr c-hr--title">
                <ul class="c-text-list c-bullet c-bullet--dot" id="frndTerms">
                </ul>
                </div>
              </div>
            </div>
          </div>
        </div>

    <script src="../../resources/js/portal/vendor/swiper.min.js"></script>

    </t:putAttribute>

</t:insertDefinition>