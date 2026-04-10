<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />

        <script type="text/javascript" src="/resources/js/portal/mypage/multiPhoneLinePop.js?ver=${jsver}"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/vendor/swiper.min.js?ver=${jsver}"></script>

<%--
    <input type="hidden" name="custNm" id="custNm"  value="${custNm}"/> --%>

    <div class="c-modal c-modal--md" id="pw_confirm-dialog" role="dialog" aria-labelledby="#modalAddTitle">
    <div class="c-modal__dialog" role="document">
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="modalAddTitle">${userVO.userDivision == '01' ? '다회선 추가' : '정회원 인증' }</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <p class="c-text c-text--fs20">kt M모바일에 가입된 휴대폰번호로 ${userVO.userDivision == '01' ? '다회선 추가 인증을 하시면' : '정회원 인증을 받으시면' } 해당 번호에 대해서 편의서비스 이용이 가능합니다.</p>

          <%@ include file="/WEB-INF/views/portal/mypage/sendCertSmsLogin.jsp"%>
        <input type="hidden" value="000000" id="contNum"/>
        <input type="hidden" value="multiPhone" id="menuLoc"/>
        <input type="hidden" value="N" id="timeYn"/>
          <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--16">개인정보 수집·이용 동의</h3>
          <hr class="c-hr c-hr--title">
          <div class="c-agree c-agree--type3">
            <div class="c-agree__item">
              <input class="c-checkbox" id="chkAgreeA" type="checkbox" name="chkAgreeA">
              <label class="c-label" for="chkAgreeA">개인정보를 수집하는 것에 동의합니다.</label>
            </div>
            <div class="c-agree__inside">
              <ul class="c-text-list">
                <li class="c-text-list__item">수집하는 개인정보 항목<span class="c-bullet c-bullet--hyphen u-co-gray">이름, 휴대폰번호</span>
                </li>
                <li class="c-text-list__item">수입, 이용목적<span class="c-bullet c-bullet--hyphen u-co-gray"> ${userVO.userDivision == '01' ? '다회선 추가를' : '정회원 등록을' } 위한 회원 정보 확인</span>
                </li>
                <li class="c-text-list__item">수집하는 개인정보의 보유기간<span class="c-bullet c-bullet--hyphen u-co-gray">회원탈퇴 시 파기</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="c-modal__footer">
          <div class="c-button-wrap">
            <button class="c-button c-button--lg c-button--gray u-width--220" id="modalCancel">취소</button>
            <button class="c-button c-button--lg c-button--primary u-width--220" id="addMultiBut">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
