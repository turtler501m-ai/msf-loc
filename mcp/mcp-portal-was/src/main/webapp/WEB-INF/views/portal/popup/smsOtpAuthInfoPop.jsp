<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

    <script type="text/javascript" charset="UTF-8" src="/resources/js/portal/popup/smsOtpAuthInfoPop.js"></script>


<div class="c-modal__content">
  <div class="c-modal__content">
    <div class="c-modal__header">
      <h2 class="c-modal__title" id="modalcombSMSTitle">SMS 인증</h2>
      <button class="c-button" data-dialog-close>
        <i class="c-icon c-icon--close" aria-hidden="true"></i>
        <span class="c-hidden">팝업닫기</span>
      </button>
    </div>
    <div class="c-modal__body">
      <div class="c-form-wrap">
        <div class="c-form-group" role="group" aria-labelledby="formGroupA">
          <div class="c-form">
            <div class="c-input-wrap">
              <!-- 에러 발생 시 .has-error(aria-invalid="true") validation 통과 시 .has-safe(aria-invalid="false") 클래스를 사용-->
              <input class="c-input numOnly" id="searchCtn" name="searchCtn" type="text" placeholder="휴대폰 번호" aria-invalid="true" maxlength="11" value="${searchCtn}"  disabled="disabled" aria-describedby="phoneChk">
              <label class="c-form__label c-hidden" for="searchCtn">휴대폰 번호 입력</label>
              <button class="c-button c-button--sm c-button--underline" id="btnReqAutNo" href="javascript:void(0);"  onclick="fn_btnReqAutNo();">인증번호 받기</button>
            </div>
            <p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
          </div>
          <div class="c-form">
            <div class="c-input-wrap">
              <!--aria-describedby 와 .c-form__text의 ID를 동일하게 연결시켜주세요-->
              <input class="c-input numOnly" id="authValue" type="text" placeholder="인증번호"  maxlength="6" aria-describedby="countdownTime">
              <label class="c-form__label c-hidden" for="authValue">인증번호 입력</label>
            </div><!-- case1 : 인증번호 확인 전-->
            <p class="c-form__text" id="countdown"   style="display: none" aria-labelledby="authValue">휴대폰으로 전송된 인증번호를 10분안에 입력해 주세요.</p><!-- case2 : 인증번호 확인 후-->
            <p class="c-form__text" id="countdownTime" style="display: none">남은시간<span class="u-co-red u-ml--8" id="timer"></span>
             	<button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint"  id="regularCertTimeBtn">시간연장</button>
            </p><!-- case3 : 인증시간 초과-->
            <p class="c-form__text" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>
	         <input  type="hidden" 	  id="certifyYn"   	value="N" />
			 <input  type="hidden"	  id="certify" 	 	value="N" />
			 <input  type="hidden"    id="menuType"	 	value="${menuType}"/>
			 <input  type="hidden"	  id="retvGubunCd" 	value="${retvGubunCd}"/>
			 <input  type="hidden"	  id="contractNum"  name="contractNum" value="${contractNum}"/>
             <input  type="hidden"	  id="ncn"  name="ncn" value="${ncn}"/>
			 <input  type="hidden" 	  id="timeOverYn" name="timeOverYn" value=""/>
			 <input  type="hidden" 	  id="reqSvcNo" name="reqSvcNo" value="${reqSvcNo}"/>
			 <input  type="hidden" 	  id="timeYn" name="timeYn" value="N"/>
          </div>
        </div>
      </div>
      <div class="c-button-wrap u-mt--56">
        <button class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0);" onclick="fn_btnAutNoCheck();">확인</button>
      </div>
      <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">유의사항</h3>
      <hr class="c-hr c-hr--title">
      <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">SMS(단문메세지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
            <li class="c-text-list__item u-co-gray">인증번호는114로 발송 되오니 문자 수신이 되지 않는 경우 해당번호의 단말기 스팸 설정여부 및 스팸편지함을 확인해주세요.</li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</div>
