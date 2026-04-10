<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

    <script type="text/javascript" charset="UTF-8" src="/resources/js/portal/popup/smsAuthInfoPop.js"></script>


<div class="c-modal__content">
  <div class="c-modal__content">
    <div class="c-modal__header">
      <h2 class="c-modal__title" id="modalcombSMSTitle">SMS 인증</h2>
      <button class="c-button" data-dialog-close id="smsClosePopBtn">
        <i class="c-icon c-icon--close" aria-hidden="true"></i>
        <span class="c-hidden">팝업닫기</span>
      </button>
    </div>
    <div class="c-modal__body">
      <div class="c-form-wrap">
        <div class="c-form-group" role="group" aria-labelledby="formGroupA">
          <div class="c-form">
            <div class="c-input-wrap">
              
              <!--  2022.10.05 연락처 마스킹작업 추가 -->
              <c:if test="${empty maskedPhoneNum}">
              	<!-- 에러 발생 시 .has-error(aria-invalid="true") validation 통과 시 .has-safe(aria-invalid="false") 클래스를 사용-->
              	<input class="c-input numOnly" id="phoneNum" name="phoneNum" type="text" placeholder="휴대폰 번호" aria-invalid="true" maxlength="11" value="${phoneNum}" aria-describedby="phoneChk">
              </c:if>
              
              <c:if test="${not empty maskedPhoneNum}">
              	<input class="c-input numOnly" id="phoneNum" name="phoneNum" type="hidden" placeholder="휴대폰 번호" aria-invalid="true" maxlength="11" value="${phoneNum}" aria-describedby="phoneChk">
              	<input class="c-input" id="maskedPhoneNum" name="maskedPhoneNum" type="text" placeholder="휴대폰 번호" aria-invalid="true" maxlength="11" value="${maskedPhoneNum}" aria-describedby="phoneChk"  style="margin-top: 0px;">
              </c:if>
              <!-- // 2022.10.05 연락처 마스킹작업 추가 -->
              
              <label class="c-form__label c-hidden" for="phoneNum">휴대폰 번호 입력</label>
              <button class="c-button c-button--sm c-button--underline" id="certifyCallBtn" href="javascript:void(0);"  onclick="certifyCallBtn();">인증번호 받기</button>
            </div>
            <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
          </div>
          <div class="c-form">
            <div class="c-input-wrap">
              <!--aria-describedby 와 .c-form__text의 ID를 동일하게 연결시켜주세요-->
              <input class="c-input numOnly" id="certifySms" type="text" placeholder="인증번호"  maxlength="6" aria-describedby="countdownTime">
              <label class="c-form__label c-hidden" for="certifySms">인증번호 입력</label>
              <button class="c-button c-button--sm c-button--underline" id="regularCertBtn">인증번호 확인</button>              
            </div><!-- case1 : 인증번호 확인 전-->
            
            <p class="c-form__text" id="countdown"  style="display: none" aria-labelledby="certifySms">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후-->
            <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
            	<button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint"  id="regularCertTimeBtn">시간연장</button>
            </p><!-- case3 : 인증시간 초과-->
            <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
	         <input id="certifyYn"   type="hidden" 	 value="N" />
			 <input id="certify" 	 type="hidden"   value="N" />
			 <input id="menuType" 	 type="hidden"   value="${menuType}" />
			 <input type="hidden"	 id="svcCntrNo"  name="svcCntrNo" value=""/>
			 <input type="hidden"	 id="svcContractNum"  name="svcContractNum" value="${contractNum}"/>
			 <input type="hidden"	 id="timeOverYn"  name="timeOverYn" value=""/>
          	 <input type="hidden"	 id="timeYn"      name="timeYn" value="N"/>
          
          </div>
        </div>
      </div>
      <div class="c-button-wrap u-mt--56">
        <button class="c-button c-button--lg c-button--primary u-width--460" id="btn_reg" disabled="disabled" href="javascript:void(0);" onclick="btn_reg();">확인</button>
      </div>
      <c:choose>
      <c:when test="${'phoneNumChange' eq menuType || 'login' eq menuType}">
      	  <b class="c-flex c-text c-text--fs15 u-mt--48">
          	<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          	<span class="u-ml--4">알려드립니다</span>
          </b>
          <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          	<li class="c-text-list__item u-co-gray">SMS(단문메세지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
          	<li class="c-text-list__item u-co-gray">인증번호는 1899-5000로 발송 되오니 해당번호를 단말기 스팸설정 여부및 스팸편지함을 확인 해주세요</li>
          </ul>
      </c:when>
      <c:otherwise>
	      <h3 class="c-heading c-heading--fs20 u-mt--48">유의사항</h3>
	      <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
	      <c:if test="${'combi' eq menuType}">
		        <li class="c-text-list__item u-co-gray">SMS 인증 시 유무선결합 신청이 완료됩니다.</li>
		        <li class="c-text-list__item u-co-gray">유무선결합 신청은 휴대폰과 USIM카드가 결합된 상태에서 가능합니다.<ul class="c-text-list c-bullet c-bullet--fyr">
		  </c:if>
	            <li class="c-text-list__item u-co-gray">SMS인증번호 발송이 지연되는 경우 인증번호 재전송 버튼을 선택 해주세요.</li>
	            <li class="c-text-list__item u-co-gray">SMS(단문메세지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
	            <li class="c-text-list__item u-co-gray">인증번호는 1899-5000로 발송되오니 해당번호를 단말기 스팸설정 여부및 스팸편지함을 확인 해주세요.</li>
	            <li class="c-text-list__item u-co-gray">현재사용중인 휴대폰이 USIM단독 상태이거나 휴대폰과 USIM카드 가 분리된 상태에서는 SMS인증을 받으실 수 없습니다.</li>
	          </ul>
	        </li>
	      </ul>
      </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
