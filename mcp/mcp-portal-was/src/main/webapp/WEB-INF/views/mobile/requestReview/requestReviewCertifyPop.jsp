 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>


<div class="c-modal" id="sms-dialog" role="dialog" tabindex="-1" aria-describedby="#sms-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="sms-title">SMS 인증</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <h3 class="c-heading c-heading--type3">사용후기 등록을 위한 개인정보 수집 및 이용 동의</h3>
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">(필수 수집항목) 성명, 휴대전화번호, 통신사</li>
            <li class="c-text-list__item u-co-gray">(선택 수집항목) SNS URL</li>
            <li class="c-text-list__item u-co-gray">이용목적 : kt M모바일 다이렉트 몰 이벤트 응모 당첨자 확인 및 경품 제공</li>
            <li class="c-text-list__item u-co-gray">개인정보의 보유기간 : 이벤트 종료일로부터 3개월까지</li>
          </ul>
          <p class="c-bullet c-bullet--fyr u-co-mint">본 동의는 서비스의 본질적 기능 제공을 위한 개인정보 수 집/이용에 대한 동의로서 동의하지 않으실 수 있으며, 동의 하지 않으실 경우 진행이 불가능합니다.</p>
          <div class="c-agree">
            <div class="c-agree__item">
              <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
              <label class="c-label" for="chkAgree">위 내용을 확인하였으며, 이에 동의합니다.</label>
            </div>
          </div>
          <div class="c-form">
            <span class="c-form__title" id="inpG">가입정보 확인</span>
			<c:if test="${nmcp:hasLoginUserSessionBean()}">
              <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSmsLogin.jsp"%>
              </c:if>
              <c:if test="${!nmcp:hasLoginUserSessionBean()}">
              <%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
              </c:if>


          </div>
          <hr class="c-hr c-hr--type2">
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">SMS(단문메세지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
            <li class="c-text-list__item u-co-gray">인증번호는 1899-5000로 발송 되오니 해당번호를 단말기 스팸설정 여부및 스팸편지함을 확인 해주세요.</li>
            <li class="c-text-list__item u-co-gray">현재사용중인 휴대폰이 USIM단독 상태이거나 휴대폰과 USIM카드가 분리된 상태에서는 SMS인증을 받으실 수 없습니다.</li>
          </ul>
          <div class="c-button-wrap">
            <button class="c-button c-button--lg c-button--full c-button--primary" id="${buttonType}">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div><!-- [START]-->