<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">셀프안면인증 URL</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.12.19"></script>
    <script type="text/javascript" src="/resources/js/portal/fath/fathAuth.js?version=25.12.19"></script>
  </t:putAttribute>
  
  <t:putAttribute name="contentAttr">

    <input type="hidden" id="resNo" name="resNo" value="${resNo}"/>
    <input type="hidden" id="operType" name="operType" value="${operType}"/>
    <input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv">
    
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">셀프안면인증 본인확인</h2>
      </div>

      <c:if test="${viewType eq '0000'}">        
        <div class="c-row c-row--lg">
          <%@ include file="/WEB-INF/views/portal/mypage/sendCertSms.jsp"%>
          <div class="c-button-wrap u-mt--52">
            <button class="c-button c-button--lg c-button--primary u-width--460" id="btn_regFath" disabled="disabled" href="javascript:void(0);" onclick="btn_regFath();">확인</button>
          </div>

          <h3 class="c-heading c-heading--fs20 u-mt--60">유의사항</h3>
          <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
            <li class="c-text-list__item u-co-gray">안면인증 고객의 이름과 연락처를 입력해주세요.</li>
            <li class="c-text-list__item u-co-gray">SMS인증번호 발송이 지연되는 경우 인증번호 재전송 버튼을 선택 해주세요.</li>
            <li class="c-text-list__item u-co-gray">SMS(단문메세지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
            <li class="c-text-list__item u-co-gray">인증번호는 1899-5000로 발송되오니 해당번호를 단말기 스팸설정 여부 및 스팸편지함을 확인 해주세요.</li>
            <li class="c-text-list__item u-co-gray">현재사용중인 휴대폰이 USIM단독 상태이거나 휴대폰과 USIM카드가 분리된 상태에서는 SMS인증을 받으실 수 없습니다.</li>
          </ul>
        </div>
      </c:if>

      <c:if test="${viewType eq '0001'}">
        <div class="c-row c-row--lg">
          <div class="u-ta-center">
            <i aria-hidden="true">
              <img src="/resources/images/portal/common/img_404_error.png" alt="">
            </i>
            <strong class="c-heading c-heading--fs24 u-block u-mt--48">
              <b>사이트 이용에 불편을 드려 죄송합니다</b>
            </strong>
            <p class="c-text c-text--fs17 u-co-gray u-mt--16">
              URL이 존재하지 않습니다. 문자로 수신받은 URL로 다시 이용부탁드립니다.
            </p>
          </div>
          <div class="c-button-wrap u-mt--40">
            <a class="c-button c-button--lg c-button--primary c-button--w460" href="/main.do">홈페이지로 이동</a>
          </div>
        </div>
      </c:if>

      <c:if test="${viewType eq '0002'}">
        <div class="c-row c-row--lg">
          <div class="u-ta-center">
            <i aria-hidden="true">
              <img src="/resources/images/portal/common/img_404_error.png" alt="">
            </i>
            <strong class="c-heading c-heading--fs24 u-block u-mt--48">
              <b>사이트 이용에 불편을 드려 죄송합니다</b>
            </strong>
            <p class="c-text c-text--fs17 u-co-gray u-mt--16">
              해당 URL은 만료된 URL입니다. 고객센터를 통해 재발급요청 부탁드립니다.
            </p>
          </div>
          <div class="c-button-wrap u-mt--40">
            <a class="c-button c-button--lg c-button--primary c-button--w460" href="/main.do">홈페이지로 이동</a>
          </div>
        </div>
      </c:if>


      <c:if test="${viewType eq '0003'}">
        <div class="c-row c-row--lg">
          <div class="u-ta-center">
            <i aria-hidden="true">
              <img src="/resources/images/portal/common/img_404_error.png" alt="">
            </i>
            <strong class="c-heading c-heading--fs24 u-block u-mt--48">
              <b>사이트 이용에 불편을 드려 죄송합니다</b>
            </strong>
            <p class="c-text c-text--fs17 u-co-gray u-mt--16">
              안면인증은 21시까지만 이용 가능합니다. 다음날 이용 부탁드립니다.
            </p>
          </div>
          <div class="c-button-wrap u-mt--40">
            <a class="c-button c-button--lg c-button--primary c-button--w460" href="/main.do">홈페이지로 이동</a>
          </div>
        </div>
      </c:if>

    </div>
    
  </t:putAttribute>
</t:insertDefinition>
