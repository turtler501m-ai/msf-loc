<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
<t:putAttribute name="scriptHeaderAttr">
    <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/event/frndRecommendReqView.js"></script>
</t:putAttribute>
<t:putAttribute name="contentAttr">
    <input type="hidden" id="userDivision" value="${userDivision}"/>
    <div class="ly-content" id="main-content">
        <div class="ly-page-sticky">
            <h2 class="ly-page__head">친구초대<button class="header-clone__gnb"></button></h2>
        </div>
        <div class="friend-banner-container">
            <div class="friend-banner__wrap">
                <h3>
                    친구도 나도 함께 받는 혜택<br />
                    <span>친구초대 이벤트</span>
                </h3>
                <a href="/m/mypage/reCommendState.do">
                    내 초대 내역 확인하기
                    <i class="c-icon c-icon--anchor-purple" aria-hidden="true"></i>
                </a>
            </div>
            <div class="friend-banner__image">
                <img src="/resources/images/mobile/event/friend_banner.png" alt="">
            </div>
        </div>
        <div class="c-tabs c-tabs--type2" data-tab-active="1">
            <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                <button class="c-tabs__button" data-tab-header="#tabA1-panel">친구초대란?</button>
                <button class="c-tabs__button" data-tab-header="#tabA2-panel">친구 초대하기</button>
            </div>
            <div class="c-tabs__panel" id="tabA1-panel"></div>
            <input type="hidden" id="certifyYn" value="N"/>
            <div class="c-tabs__panel" id="tabA2-panel">
            	<c:if test="${empty maskingSession}">
				  <div class="masking-badge-wrap">
			          <div class="masking-badge" style="top: -2rem;">
					   	  <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
					    	<i class="masking-badge__icon" aria-hidden="true"></i>
						   	<p>가려진(*) 정보보기</p>
						  </a>
				      </div>
			      </div>
			    </c:if>
                <p class="c-text c-text--type2 u-mt--40"> 친구초대 ID 발급을 위해서는 개인정보 제공을 위한 동의 및 가입 여부의 확인이 필요합니다. 내용을 확인하시고 동의하여 주시기 바랍니다.</p>
                <p class="c-bullet c-bullet--fyr u-co-mint"> 친구초대ID 발급 및 혜택 적용을 위하여 고객님의 개인정보가 이용될 수 있으며, 초대ID 활용에 대한 책임은 고객에게 있습니다.</p>
                  <div class="c-agree">
                    <div class="c-agree__item">
                          <input class="c-checkbox" id="chkA1" type="checkbox" name="chkA">
                          <label class="c-label" for="chkA1">위 개인정보 이용 및 책임에 대한 내용을 확인하였으며, 이에 동의합니다.</label>
                    </div>
                  </div>
                <div class=" c-agree u-mt--8">
                    <div class="c-agree__item">
                        <input class="c-checkbox" id="chkMstoreAgree" type="checkbox" name="chkMstoreAgree">
                        <label class="c-label" for="chkMstoreAgree">개인정보 제 3자 제공 동의(필수)</label>
                        <button type="button" class="c-button c-button--xsm" onclick="fnSetEventId('chkMstoreAgree');openPage('termsPop','/termsPop.do','cdGroupId1=FormEtcCla&cdGroupId2=${Constants.FRND_MSTORE_TERMS_CD_2}');"  >
                            <span class="c-hidden">상세보기</span>
                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
                  <div class="c-form">
                    <span class="c-form__title" id="certSmsTitle">가입정보 확인</span>
                    <input type="hidden" value="N"/>
                    <c:if test="${nmcp:hasLoginUserSessionBean()}">
	                    <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
                    </c:if>
                    <c:if test="${!nmcp:hasLoginUserSessionBean()}">
                    	<%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
                    </c:if>
                  </div>
                  <div class="c-button-wrap c-button-wrap--share">
                    <a class="c-button c-button--full c-button--kakao" id="frndKakaoSend">
                            <i class="c-icon c-icon--balloon-brown" aria-hidden="true"></i>카카오톡 공유
                    </a>
                    <a class="c-button c-button--full c-button--msg" id="frndSmsSend">
                          <i class="c-icon c-icon--balloon-white" aria-hidden="true"></i>나에게 문자발송
                       </a>
                  </div>
                  <p class="c-bullet c-bullet--fyr u-co-gray">'나에게 문자 발송' 선택 시 인증한 휴대폰 번호로 초대ID 및 URL이 발송됩니다.</p>
                  <p class="c-bullet c-bullet--fyr u-co-gray">전달한 친구초대 URL을 통해 접속하셨을 경우 신규 가입 신청서에 추천인ID가 신청서에 자동 기입됩니다.</p>
                  <p class="c-bullet c-bullet--fyr u-co-gray">전용 URL을 사용하지않을 경우 발급받으신 친구초대ID를 피추천인이 신청서에 직접 입력하셔야 혜택을 받으실 수 있습니다.</p>
                  <div class="note-box">
                      <h3 class="c-heading c-heading--type2">유의사항</h3>
                      <hr class="c-hr c-hr--title" id="docCont">
                      <ul class="c-text-list c-bullet c-bullet--dot">
                      </ul>
                  </div>
               </div>
        </div>
        <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>
        <script>
            //친구초대 스와이퍼
            var swipeGuideWrap = new Swiper("#swiperInvitBanner", {
              spaceBetween: 10,
              slidesPerView: 1,
            });
        </script>
    </div>



</t:putAttribute>
</t:insertDefinition>