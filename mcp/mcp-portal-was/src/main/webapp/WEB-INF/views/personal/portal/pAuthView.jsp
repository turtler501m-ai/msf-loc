<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

	<t:putAttribute name="titleAttr">개인화 URL</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.06.10"></script>
		<script type="text/javascript" src="/resources/js/personal/portal/pAuthView.js?version=25.06.10"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="seq" name="seq" value="${seq}"/>
		<input type="hidden" id="pageType" name="pageType" value="${pageType}"/>
		<input type="hidden" id="landing" name="landing" value="${landing}"/>

		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">개인화 URL (SMS 인증)</h2>
			</div>

			<c:if test="${viewType eq '0000'}">
				<div class="c-row c-row--lg">
					<div class="c-form-wrap u-width--460 u-margin-auto">
						<div class="c-form-group" role="group" aria-labelledby="formGroupA">
							<div class="c-form">
								<div class="c-input-wrap">
									<input class="c-input" id="maskedPhoneNum" name="maskedPhoneNum" type="text" placeholder="휴대폰 번호" aria-invalid="true" maxlength="11" value="${maskedPhoneNum}" aria-describedby="phoneChk" style="margin-top: 0px;" disabled>
									<label class="c-form__label c-hidden" for="maskedPhoneNum">휴대폰 번호 입력</label>
									<button class="c-button c-button--sm c-button--underline" id="certifyCallBtn" href="javascript:void(0);" onclick="certifyCallBtn();">인증번호 받기</button>
								</div>
								<p class="c-form__text" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>
							</div>

							<div class="c-form">
								<div class="c-input-wrap">
									<input class="c-input numOnly" id="certifySms" type="text" placeholder="인증번호" maxlength="6" aria-describedby="countdownTime">
									<label class="c-form__label c-hidden" for="certifySms">인증번호 입력</label>
									<button class="c-button c-button--sm c-button--underline" id="regularCertBtn">인증번호 확인</button>
								</div>

								<!-- case1 : 인증번호 확인 전-->
								<p class="c-form__text" id="countdown"  style="display: none" aria-labelledby="certifySms">
									휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.
								</p>
								<!-- case2 : 인증번호 확인 후-->
								<p class="c-form__text" id="countdownTime" style="display: none">
									남은시간
									<span class="u-co-red u-ml--8" id="timer"></span>
									<button class="c-button c-button--xsm c-button--underline u-ml--8 u-co-mint"  id="regularCertTimeBtn">시간연장</button>
								</p>
								<!-- case3 : 인증시간 초과-->
								<p class="c-form__text" id="timeover" style="display: none">
									인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.
								</p>

								<input type="hidden" id="certifyYn"  name="certifyYn"   value="N" />
								<input type="hidden" id="certify" 	 name="certify"     value="N" />
								<input type="hidden" id="timeOverYn" name="timeOverYn"  value="" />
								<input type="hidden" id="timeYn"     name="timeYn"      value="N"/>
							</div>
						</div>
					</div>

					<div class="c-button-wrap u-mt--52">
						<button class="c-button c-button--lg c-button--primary u-width--460" id="btn_reg" disabled="disabled" href="javascript:void(0);" onclick="btn_reg();">확인</button>
					</div>

					<h3 class="c-heading c-heading--fs20 u-mt--60">유의사항</h3>
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">SMS인증번호 발송이 지연되는 경우 인증번호 재전송 버튼을 선택 해주세요.</li>
						<li class="c-text-list__item u-co-gray">SMS(단문메세지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
						<li class="c-text-list__item u-co-gray">인증번호는 1899-5000로 발송되오니 해당번호를 단말기 스팸설정 여부 및 스팸편지함을 확인 해주세요.</li>
						<li class="c-text-list__item u-co-gray">현재사용중인 휴대폰이 USIM단독 상태이거나 휴대폰과 USIM카드가 분리된 상태에서는 SMS인증을 받으실 수 없습니다.</li>
					</ul>
				</div>
			</c:if>

			<c:if test="${viewType ne '0000'}">
				<div class="c-row c-row--lg">
					<div class="u-ta-center">
						<i aria-hidden="true">
							<img src="/resources/images/portal/common/img_404_error.png" alt="">
						</i>
						<strong class="c-heading c-heading--fs24 u-block u-mt--48">
							<b>사이트 이용에 불편을 드려 죄송합니다</b>
						</strong>
						<p class="c-text c-text--fs17 u-co-gray u-mt--16">
							개인화 URL 재발급 부탁드립니다.
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