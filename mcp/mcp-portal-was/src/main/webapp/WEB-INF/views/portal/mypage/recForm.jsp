<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutMainDefault">
	<t:putAttribute name="scriptHeaderAttr">
 		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/mypage/recForm.js"></script>
 		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/vendor/swiper.min.js"></script>


	</t:putAttribute>

	<t:putAttribute name="contentAttr">
	<form:form commandName="searchVO" id="frm" name="frm" method="post" action="">
		 <div class="ly-content" id="main-content">
	      <div class="ly-page--title">
	        <h2 class="c-page--title">회원탈퇴 신청</h2>
	      </div>
	      <div class="c-row c-row--lg">
	        <h3 class="c-heading c-heading--fs22">회원탈퇴 안내사항</h3>
	        <hr class="c-hr c-hr--title">
	        <ul class="c-text-list c-bullet c-bullet--dot">
	          <li class="c-text-list__item u-co-gray">탈퇴 즉시 회원님의 온라인 정보는 삭제 됩니다.</li>
	          <li class="c-text-list__item u-co-gray">사용하셨던 해당 아이디는 회원 탈퇴 후 사용이 불가능 합니다.</li>
	          <li class="c-text-list__item u-co-gray">회원탈퇴 이후 재가입은 바로 가능 합니다.</li>
	          <li class="c-text-list__item u-co-gray">탈퇴처리 이후에는 어떠한 방법으로도 회원님의 개인정보를 복원할 수 없습니다.</li>
	        </ul>
	        <div class="c-form-wrap u-mt--48">
	          <div class="c-form-group" role="group" aira-labelledby="radio_header1">
	            <h3 class="c-group--head c-hidden" id="radio_header1">탈퇴신청 정보</h3>
	            <div class="c-form-row c-col2">
	              <div class="c-form-field">
	                <div class="c-form__input">
	                  <input class="c-input" id="inpName" type="text" placeholder="아이디 입력" value="${searchVO.userName}" readonly>
	                  <label class="c-form__label" for="inpName">아이디</label>
	                  <input type="text" id="userId" value="${searchVO.userName}" hidden/>
	                </div>
	              </div>
	              <div class="c-form-field">
	                <div class="c-form__input">
	                  <input class="c-input" id="pw" name="pw" type="password" placeholder="비밀번호 입력" maxlength="20">
	                  <label class="c-form__label" for="inpName">비밀번호</label>
	                </div>
	              </div>
	            </div>
	          </div>
	          <div class="c-form-group u-mt--40" role="group" aira-labelledby="radio_header2">
	            <div class="c-group--head">
	              <h3 id="radio_header2">탈퇴사유<span class="c-form__sub">(필수)</span>
	              </h3>
	            </div>
	            <div class="c-checktype-row c-col2">
	              <input class="c-radio" id="secedeReason01" type="radio" name="secedeReason" value="01">
	              <label class="c-label" for="secedeReason01">다른 ID 변경</label>
	              <input class="c-radio" id="secedeReason02" type="radio" name="secedeReason" value="02">
	              <label class="c-label" for="secedeReason02">시스템 장애(속도 저조, 잦은 에러 등)</label>
	              <input class="c-radio" id="secedeReason03" type="radio" name="secedeReason" value="03">
	              <label class="c-label" for="secedeReason03">개인정보(통신 및 신용정보)의 유출 우려</label>
	              <input class="c-radio" id="secedeReason04" type="radio" name="secedeReason" value="04">
	              <label class="c-label" for="secedeReason04">이용빈도 저조</label>
	              <input class="c-radio" id="secedeReason05" type="radio" name="secedeReason" value="05">
	              <label class="c-label" for="secedeReason05">타사로 전환하기 위해서</label>
	              <input class="c-radio" id="secedeReason06" type="radio" name="secedeReason" value="06">
	              <label class="c-label" for="secedeReason06">알뜰 서비스 중단</label>
	              <input class="c-radio" id="secedeReason07" type="radio" name="secedeReason" value="07">
	              <label class="c-label" for="secedeReason07">서비스 불만족</label>
	              <input class="c-radio" id="secedeReason08" type="radio" name="secedeReason" value="08">
	              <label class="c-label" for="secedeReason08">기타</label>
	            </div><!-- 기타 선택 시 노출-->
	            <div class="c-form" id="reasonDesc" style="display:none;">
	              <div class="c-input-wrap c-input-wrap--textinp u-mt--4">
	                <label class="c-label c-hidden">기타 사유</label>
	                <input class="c-input" type="text" id="secedeReasonDesc" name="secedeReasonDesc" placeholder="기타 사유를 입력해주세요">
	                <div class="c-input-wrap__sub">
	                  <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
	                  <span>0/50</span>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
	        <div class="c-button-wrap u-mt--56">
	          <button class="c-button c-button--lg c-button--primary u-width--460">확인</button>
	        </div>
	      </div>
	    </div>
	    </form:form>
	</t:putAttribute>

</t:insertDefinition>