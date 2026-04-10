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
<script type="text/javascript" src="/resources/js/mobile/popup/smsOtpAuthInfoPop.js">

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
          <input class="c-input numOnly" type="tel" id="searchCtn" name="searchCtn" placeholder="휴대폰 번호" title="휴대폰 번호 입력" maxlength="11" value="${searchCtn}" disabled="disabled">
          <button class="c-button c-button--sm c-button--underline" id="btnReqAutNo"  onclick="fn_btnReqAutNo();">인증번호 받기</button>
        </div>
        <p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
        <div class="c-input-wrap">
          <input class="c-input numOnly" type="tel"  id="authValue" placeholder="인증번호" maxlength="6" title="인증번호 입력">
          <!-- <button class="c-button c-button--sm c-button--underline" id="btnAutNoCheck" onclick >인증번호 확인</button> -->
        </div><!-- case1 : 인증번호 확인 전-->
        <p class="c-text--form"  id="countdown"  style="display: none">휴대폰으로 전송된 인증번호를 10분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후 -->
        <p class="c-text--form"  id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
          <button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
        </p><!-- case3 : 인증시간 초과-->
        <p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
        <input  type="hidden" 	  id="certifyYn"   	value="N" />
        <input  type="hidden"	  id="certify" 	 	value="N" />
        <input  type="hidden"    id="menuType"	 	value="${menuType}"/>
        <input  type="hidden"	  id="retvGubunCd" 	value="${retvGubunCd}"/>
        <input  type="hidden"	  id="contractNum"  name="contractNum" value="${contractNum}"/>
        <input  type="hidden"	  id="ncn"  name="ncn" value="${ncn}"/>
        <input  type="hidden"	  id="timeOverYn"  name="timeOverYn" value=""/>
        <input  type="hidden" 	   id="reqSvcNo" name="reqSvcNo" value="${reqSvcNo}"/>

      </div>
    </div>
    <h3 class="c-heading c-heading--type2 u-mb--12">유의사항</h3>
    <ul class="c-text-list c-bullet c-bullet--fyr">
      <li class="c-text-list__item u-co-gray">SMS(단문메시지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
      <li class="c-text-list__item u-co-gray">인증번호는 114로 발송 되오니 문자 수신이 되지 않는 경우 해당번호의 단말기 스팸 설정 여부 및 스팸편지함을 확인해주세요.</li>
    </ul>
    <div class="c-button-wrap" >
      <a class="c-button c-button--full c-button--primary" href="javascript:void(0);" id="btnAutNoCheck" onclick="fn_btnAutNoCheck();">확인</a>
    </div>
  </div>
</div>
