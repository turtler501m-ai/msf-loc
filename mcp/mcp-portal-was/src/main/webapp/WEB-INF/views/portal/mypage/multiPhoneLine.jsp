<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="layoutGnbDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/portal/mypage/multiPhoneLine.js?ver=${jsver}"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/vendor/swiper.min.js?ver=${jsver}"></script>


	</t:putAttribute>

	<t:putAttribute name="contentAttr">

	<div class="ly-content" id="main-content">
<%-- 	  <input type="hidden" id="unMkNm" value="${unMkNm}"/> --%>
      <div class="ly-page--title">
        <h2 class="c-page--title" id="mid-title"></h2>
      </div>
      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs20">내 회선 정보</h3>
        <hr class="c-hr c-hr--title no-multiLine">
        <div class="c-nodata u-ta-center u-co-black no-multiLine">
          <p>kt M모바일의 모든 서비스를 이용하시기 위해
            <br>
            <b>정회원 인증</b>을 해주세요
          </p>
        </div>
        <div class="c-table u-mt--24 ex-multiLine">
          <table>
            <caption>휴대폰 번호, 단말기정보, 대표번호선택을 포함한 내 회선 정보</caption>
            <colgroup>
              <col style="width: 33%">
              <col style="width: 33%">
              <col>
            </colgroup>
            <thead>
              <tr>
                <th scope="col">휴대폰 번호</th>
                <th scope="col">단말기정보</th>
                <th scope="col">대표번호선택</th>
              </tr>
            </thead>
            <tbody>

            </tbody>
          </table>
        </div>
        <div class="c-button-wrap u-mt--56">
          <button class="c-button c-button--lg c-button--primary u-width--460" type="button" id="mainBut"></button>
        </div>
        <div class="c-notice-wrap u-mt--64">
            <h5 class="c-notice__title">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                <span>알려드립니다</span>
            </h5>
            <ul class="c-notice__list">
                <li>다회선 중 대표번호를 선택(변경) 시 회원정보의 휴대폰 번호가 선택한 대표회선 번호로 변경됩니다.</li>
                <li>대표회선과 다른 번호로 변경을 원하실 경우 회원정보를 수정해주시기 바랍니다.</li>
            </ul>
        </div>
      </div>
    </div>
	</t:putAttribute>

</t:insertDefinition>