<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">개인화 URL</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.06.10"></script>
		<script type="text/javascript" src="/resources/js/personal/mobile/pAuthView.js?version=25.06.10"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="seq" name="seq" value="${seq}"/>
		<input type="hidden" id="pageType" name="pageType" value="${pageType}"/>
		<input type="hidden" id="landing" name="landing" value="${landing}"/>

		<div class="ly-content" id="main-content">

			<div class="ly-page-sticky">
				<h2 class="ly-page__head">개인화 URL (SMS 인증)<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<c:if test="${viewType eq '0000'}">
				<div class="c-form u-mt--40">
					<div class="c-form-group" role="group" aria-labelledby="formGroupA">
						<div class="c-input-wrap" id="inputPhoneNum">
						  <input class="c-input" type="text" id="maskedPhoneNum" name="maskedPhoneNum" placeholder="휴대폰 번호" title="휴대폰 번호 입력" maxlength="11" value="${maskedPhoneNum}" style="margin-top: 0px;" disabled>
							<button class="c-button c-button--sm c-button--underline" id="certifyCallBtn" onclick="certifyCallBtn();">인증번호 받기</button>
						</div>
						<p class="c-text--form" id="phoneChk" style="display: none">휴대폰 번호가 올바르지 않습니다.</p>

						<div class="c-input-wrap">
							<input class="c-input numOnly" type="tel" id="certifySms" placeholder="인증번호" maxlength="6" title="인증번호 입력">
							<button class="c-button c-button--sm c-button--underline" id="regularCertBtn" >인증번호 확인</button>
						</div>

						<!-- case1 : 인증번호 확인 전-->
						<p class="c-text--form" id="countdown" style="display: none">휴대폰으로 전송된 인증번호를 3분안에 입력해 주세요.</p>
						<!-- case2 : 인증번호 확인 후-->
						<p class="c-text--form"  id="countdownTime" style="display: none">
							남은시간<span class="u-co-red u-ml--8" id="timer"></span>
							<button class="c-button--underline c-button--sm u-co-sub-4 u-ml--8" id="regularCertTimeBtn">시간연장</button>
						</p>
						<!-- case3 : 인증시간 초과-->
						<p class="c-text--form" id="timeover" style="display: none">인증번호 유효시간이 지났습니다. 인증번호를 다시 받아주세요.</p>

						<input type="hidden" id="certifyYn"  name="certifyYn"   value="N" />
						<input type="hidden" id="certify" 	 name="certify"     value="N" />
						<input type="hidden" id="timeOverYn" name="timeOverYn"  value="" />
						<input type="hidden" id="timeYn"     name="timeYn"      value="N"/>
					</div>
				</div>

				<h3 class="c-heading c-heading--type2 u-mb--12">유의사항</h3>
				<ul class="c-text-list c-bullet c-bullet--fyr">
					<li class="c-text-list__item u-co-gray">SMS(단문메시지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
					<li class="c-text-list__item u-co-gray">인증번호는 114로 발송 되오니 문자 수신이 되지 않는 경우 해당번호의 단말기 스팸 설정 여부 및 스팸편지함을 확인해주세요.</li>
				</ul>
				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary" type="button" onclick="btn_reg();" id="btn_reg" disabled="disabled">확인</button>
				</div>
			</c:if>

			<c:if test="${viewType ne '0000'}">
				<div class="error u-ta-center">
					<i aria-hidden="true">
						<img src="/resources/images/mobile/content/img_error.png" alt="">
					</i>
					<strong class="error__title">
						<b>사이트 이용에<br>불편을 드려 죄송합니다.</b>
					</strong>
					<p class="error__text">
						개인화 URL 재발급 부탁드립니다.
					</p>
					<div class="c-button-wrap">
						<a class="c-button c-button--full c-button--primary" href="/m/main.do">홈페이지로 이동</a>
					</div>
				</div>
			</c:if>

		</div>
	</t:putAttribute>
</t:insertDefinition>