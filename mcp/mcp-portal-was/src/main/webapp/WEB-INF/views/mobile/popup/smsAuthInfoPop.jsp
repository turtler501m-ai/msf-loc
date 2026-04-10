<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<link href="/resources/css/mobile/styles.css" rel="stylesheet" />

<style>
    .countdown {color:#e31f26;margin-top:10px}
	.countdown span {color:#000}
	.timeover {color:#e31f26;margin-top:10px}
</style>
<script type="text/javascript" src="/resources/js/mobile/popup/smsAuthInfoPop.js">

</script>
<div class="c-modal__content">
   <div class="c-modal__header">
     <h1 class="c-modal__title" id="sms-certify-title">SMS 인증</h1>
     <button class="c-button" data-dialog-close>
       <i class="c-icon c-icon--close" aria-hidden="true"></i>
       <span class="c-hidden">팝업닫기</span>
     </button>
   </div>
   <div class="c-modal__body">
     <div class="c-form u-mt--40">
       <div class="c-form__group" role="group" aria-labeledby="inpCertify">
         <div class="c-input-wrap" id="inputPhoneNum">
           
           <!--  2022.10.05 연락처 마스킹작업 추가 -->
           <c:if test="${empty maskedPhoneNum}">
            	<input class="c-input numOnly" type="tel" id="phoneNum" name="phoneNum" placeholder="휴대폰 번호" title="휴대폰 번호 입력" maxlength="11" value="${phoneNum}">
           </c:if>
           <c:if test="${not empty maskedPhoneNum}">
           		<input class="c-input numOnly" type="hidden" id="phoneNum" name="phoneNum" placeholder="휴대폰 번호" title="휴대폰 번호 입력" maxlength="11" value="${phoneNum}">
           		<input class="c-input" type="text" id="maskedPhoneNum" name="maskedPhoneNum" placeholder="휴대폰 번호" title="휴대폰 번호 입력" maxlength="11" value="${maskedPhoneNum}" style="margin-top: 0px;">
           </c:if>
           
           <button class="c-button c-button--sm c-button--underline" id="certifyCallBtn"  onclick="certifyCallBtn();">인증번호 받기</button>
         </div>
         <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
         <div class="c-input-wrap">
           <input class="c-input numOnly" type="tel"  id="certifySms" placeholder="인증번호" maxlength="6" title="인증번호 입력">
           <button class="c-button c-button--sm c-button--underline" id="regularCertBtn" >인증번호 확인</button>
         </div><!-- case1 : 인증번호 확인 전-->
         <p class="c-text--form"  id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
         <p class="c-text--form"  id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
         	<button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
         </p><!-- case3 : 인증시간 초과-->
         <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
         <input id="certifyYn"   type="hidden" 	 value="N" />
		 <input id="certify" 	 type="hidden"   value="N" />
		 <input id="menuType" 	 type="hidden"   value="${menuType}" />
		 <input type="hidden"	 id="svcCntrNo"  name="svcCntrNo" value=""/>
		 <input type="hidden"	 id="svcContractNum"  name="svcContractNum" value="${contractNum}"/>
		 <input type="hidden"	 id="timeOverYn"  name="timeOverYn" value=""/>
         <input type="hidden"	 id="timeYn"      name="timeYn" 	value="N"/>

       </div>
     </div>
     <h3 class="c-heading c-heading--type2 u-mb--12">유의사항</h3>
     <c:if test="${'combi' eq menuType}">
	     <p class="c-bullet c-bullet--dot"> SMS 인증 시 유무선결합 신청이 완료됩니다.
	       <br>유무선결합 신청은 휴대폰과 USIM 카드가 결합된 상태에서 가능합니다.
	     </p>
     </c:if>
     <ul class="c-text-list c-bullet c-bullet--fyr">
       <li class="c-text-list__item u-co-gray">SMS(단문메시지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
       <li class="c-text-list__item u-co-gray">인증번호는 114로 발송 되오니 문자 수신이 되지 않는 경우 해당번호의 단말기 스팸 설정 여부 및 스팸편지함을 확인해주세요.</li>
     </ul>
     <div class="c-button-wrap" data-dialog-close>
       <button class="c-button c-button--full c-button--primary" type="button" onclick="btn_reg();" id="btn_reg" disabled="disabled">확인</button>
     </div>
   </div>
 </div>
