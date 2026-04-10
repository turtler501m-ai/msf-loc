<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/mypage/moscSdsSvcRegView.js"></script>
	<script type="text/javascript">
	history.pushState(null, null,"/m/mypage/reSpnsrPlcyDc.do");
	window.onpopstate = function (event){   	   				
		history.go("/m/mypage/reSpnsrPlcyDc.do");
	}
    </script>
</t:putAttribute>

 <t:putAttribute name="contentAttr">
 <form id="frm" name="frm" method="post">
    	<input type="hidden" name="hPhoneNum" id="hPhoneNum" value="${param.phoneNum}">
   		<input type="hidden" name="engtPerd" id="engtPerd" value="${param.engtPerd}">
   		<input type="hidden" name="ctn" id="ctn" value="${param.ctn}">
   		<input type="hidden" name="ncn" id="ncn" value="${param.svcCntrNo}">
    </form>
	 <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">요금할인 재약정 신청<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-form">
        <span class="c-form__title" id="radCardType">선택 가능 사은품</span>
        <span class="c-text c-text--type3 u-co-gray"></span>
        <div class="c-filter c-filter__type2 c-filter--accordion c-expand" data-sc-change-initialize="true">
          <!-- <button class="c-filter--accordion__button">
            <div class="c-hidden">필터 펼치기</div>
          </button> -->
          <div class="c-filter__inner">
          <c:forEach items="${presentList}" var="item" varStatus="eachIndex">
				<div class="c-chk-wrap">
	              <input class="c-checkbox c-checkbox--button c-checkbox--button--type4" data-isdlvry="${item.expnsnStrVal2}" id="chkBS<c:out value="${eachIndex.count +1}"></c:out>"  value="${item.dtlCd}" type="checkbox" name="chkSelectBasicService" onclick="chgChk(this);">
	              <label class="c-label" for="chkBS<c:out value="${eachIndex.count +1}"></c:out>">
	                <span class="filter-prefix">
	                  <img src='<c:out value="${item.expnsnStrVal1}"></c:out>' alt="">
	                </span><c:out value="${item.dtlCdNm}"></c:out>
	              </label>
	            </div>
		  </c:forEach>
          </div>
        </div>
        <span id="divInpCsArea" style="display: none;">
        <span class="c-form__title" id="divInpCsArea">주문자정보</span>
        <div class="c-form__input">
          <input class="c-input" maxlength="20" id="inpOrdererName" type="text" placeholder="이름 입력">
          <label class="c-form__label" for="inpOrdererName">이름</label>
        </div>
        <div class="c-form__input">
          <input class="c-input" id="inpTel2" type="tel" name="inpTel2" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력">
          <!--[2021-11-22] 인풋타입 변경-->
          <label class="c-form__label" for="inpTel2">휴대폰</label>
        </div>
        <span class="c-form__title">배송주소</span>
        <div class="c-form__group" role="group" aria-labelledby="inpG">
          <div class="c-input-wrap">
            <input id="inpAdressNum" class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" readonly="">
            <button id="btnSearchAddrPost" type="button" class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/m/addPopup.do');">우편번호 찾기</button>
          </div>
          <!---->
          <!--[2022-02-11] 속성 중복 삭제-->
          <input id="inpAdress1" class="c-input" type="text" placeholder="주소 입력" title="주소 입력" value="" readonly="">
          <!---->
          <!--[2022-02-11] 속성 중복 삭제-->
          <input id="inpAddress2" class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" value="" readonly="">
        </div>
      </div>
      </span>
      <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
        <li class="c-text-list__item u-co-gray">직접 배송이 필요한 사은품 선택시에만 배송주소 입력이 가능합니다.</li>
        <li class="c-text-list__item u-co-gray">잘못된 배송 주소 입력 시 사은품의 재발송 및 반품이 불가하오니 반드시 배송 주소를 확인/변경 해 주시기 바랍니다.</li>
        <li class="c-text-list__item u-co-gray">사은품은 익월 말까지 회선 사용 고객에 한해 제공됩니다.</li>
      </ul>
      <div class="c-form">
        <div class="c-agree">
          <input class="c-checkbox" id="chkStepAgree1" type="checkbox" name="chkStepAgree" onclick="agreeChk(this);">
          <label class="c-label" for="chkStepAgree1">요금할인 재약정 동의 및 본인확인 절차 동의</label>
          <div class="c-agree__item">
            <input class="c-checkbox c-checkbox--type2" id="chkStepAgreeAll" type="checkbox" name="chkD" onclick="agreeChk(this);">
            <label class="c-label" for="chkStepAgreeAll">재약정 및 본인확인 절차 동의</label>
            <button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMPLCY&cdGroupId2=TERMPLCY01');">
              <span class="c-hidden">상세보기</span>
              <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="c-button-wrap">
        <a class="c-button c-button--full c-button--gray" href="/m/mypage/reSpnsrPlcyDc.do">취소</a>
        <button id="btnSmsAuth" type="button" class="c-button c-button--full c-button--primary" onclick="authSms();" disabled="disabled">SMS 본인인증</button>
      </div>
    </div>
  </t:putAttribute>
</t:insertDefinition>