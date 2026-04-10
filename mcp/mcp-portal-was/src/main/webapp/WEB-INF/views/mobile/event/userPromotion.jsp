
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
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/event/userPromotion.js?version=24-04-11"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
         <input type="hidden" id="codeEndDd" value="${codeEndDd}" />
         <input type="hidden" id="promoFinish" value="${promoFinish}" />
         <c:choose>
           <c:when test="${nmcp:hasLoginUserSessionBean()}">
            <input type="hidden" id="userlogin" value="Y"/>
          </c:when>
          <c:otherwise>
            <input type="hidden" id="userlogin" value="N"/>
          </c:otherwise>
          </c:choose>
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                      홈 회원가입 이벤트
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-board c-board--type2">
                <div class="c-board__content u-mt--0 pd-0">
                    <article class="c-editor c-expand">
                          <div class="bind-box">
                            ${promoImg.docContent}
                          </div>
                    </article>
                 </div>
               </div>
            <div class="c-button-wrap c-button-wrap--column u-mt--40">
                <button name="userPromoBtn" class="c-button c-button--full c-button--primary" data-dialog-trigger="#userPromotionJoinModal">회원가입하고 이벤트 응모하기</button>
                <button class="c-button c-button--full c-button--primary" onclick="promoMstore('uPrize')">당첨내역 보기</button>
                <button id="promoFinishBtn" data-dialog-trigger="#userPromotionCompleteModal" style="display: none;"></button>
            </div>
            <ul class="c-text-list c-bullet c-bullet--fyr u-fs-14 u-mt--20">
                  <li class="c-text-list__item u-co-gray">본 프로모션은 현 페이지를 통해 회원가입 시 자동 응모됩니다.</li>
                  <li class="c-text-list__item u-co-gray">이벤트 참여를 위해서는 마케팅동의 및 본인인증 절차가 필요합니다.</li>
            </ul>
            <div class="c-notice-wrap">
                <h5 class="c-notice__title">
                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                    <span>유의사항</span>
                </h5>
                <ul class="c-notice__list">
                    <li>본 프로모션은 현 페이지의 “회원가입하고 이벤트 응모하기”를 선택 후 완료하여야 이벤트 참여 가능합니다.</li>
                    <li>“회원가입하고 이벤트 응모하기” 를 통해 회원가입을 하지 않고, 홈페이지 내 회원가입 한 경우 이벤트 응모대상에서 제외 됩니다.</li>
                    <li>기존에 회원 가입하신 고객은 응모대상에서 제외 됩니다.</li>
                    <li>비정상적인 회원가입 및 이벤트 응모 사례가 발생될 경우, 당사 규정에 따라 예외처리 될 수 있습니다.</li>
                    <li>이벤트 응모시 전화번호/이메일 오기입으로 인한 오발송, 미수령시 재발송되지 않으니, 정보기입에 오류가 없도록 확인부탁드립니다.</li>
                    <li>이벤트 혜택은 월 진행되는 프로모션에 따라 변동 될 수 있습니다.</li>
                    <li>프로모션 내용은 당사 사정에 따라 변경될 수 있으며, 혜택은 동급 또는 동일 수준의 타 경품으로 대체될 수 있습니다.</li>
                </ul>
            </div>
        </div>

        <!-- 이벤트 참여 팝업 -->
        <div class="c-modal" id="userPromotionJoinModal" role="dialog" tabindex="-1" aria-describedby="userPromotionJoinTitle">
            <div class="c-modal__dialog" role="document">
                  <div class="c-modal__content">
                    <div class="c-modal__header">
                          <h1 class="c-modal__title" id="userPromotionJoinTitle">이벤트 참여</h1>
                          <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                          </button>
                    </div>
                    <div class="c-modal__body">
                          <div class="u-ta-center">
                            <p class="u-fw--medium">본 이벤트 참여를 위해서는<br />회원가입 및 정보/광고 수신 동의가 필요하며,<br />회원가입 완료 시 자동 응모됩니다.</p>
                          </div>
                          <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
                            <button class="c-button c-button--full c-button--primary" data-dialog-close onclick="pop_nice()" >휴대폰인증</button>
                          </div>
                      </div>
                </div>
            </div>
        </div>

        <!-- 이벤트 참여 완료 -->
        <div class="c-modal" id="userPromotionCompleteModal" role="dialog" tabindex="-1" aria-describedby="userPromotionCompleteTitle">
            <div class="c-modal__dialog" role="document">
                  <div class="c-modal__content">
                    <div class="c-modal__header">
                          <h1 class="c-modal__title" id="userPromotionCompleteTitle">이벤트 참여</h1>
                          <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                          </button>
                    </div>
                    <div class="c-modal__body">
                          <div class="u-ta-center">
                            <p class="u-fs-18 u-fw--bold">회원가입 및 응모가 완료되었습니다.</p>
                            <p class="u-fs-14 u-co-gray u-mt--8">정보/광고 수신 동의일자(<span id="agreeDate"></span>)</p>
                            <p class="u-fs-14 u-co-gray u-mt--32">※ 정보/광고동의는 마이페이지에서 수정 가능하나 수정 시 이벤트혜택 지급이 불가합니다.</p>
                          </div>
                          <div class="c-button-wrap u-mt--48">
                            <button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
                          </div>
                      </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>