<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/mypage/pause/pauseCnl.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					일시정지
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-form">
				<div class="c-form__group" role="group" aria-labelledby="inpG">
					<span class="c-form__title">일시정지 현황</span>
					<div class="info-box">
						<dl>
							<dt>일시정지 신청일</dt>
							<dd>${subStatusDate}</dd>
						</dl>
						<dl>
							<dt>고객명</dt>
							<dd>${userName}</dd>
						</dl>
						<dl>
							<dt>휴대폰번호</dt>
							<dd>${ctn}</dd>
						</dl>
						<dl>
							<dt>정지유형</dt>
							<dd>${sndarvStatCdNm}</dd>
						</dl>
					</div>
					<span class="u-mt--40 c-form__title" id="inpG">일시정지 해제 비밀번호</span>
					<div class="c-input-wrap u-mt--0">
						<input class="c-input onlyNum" type="password" placeholder="비밀번호를 입력 (4~8자)" title="비밀번호 입력" id="cpPwdInsert" name="cpPwdInsert" value="" maxlength="8">
					</div>
					<div class="c-button-wrap u-mt--48">
						<a class="c-button c-button--full c-button--gray" href="javascript:;" id="cnlBtn">취소</a>
						<a class="c-button c-button--full c-button--primary" href="javascript:;" id="applyBtn">일시정지 해제</a>
					</div>
				</div>
			</div>
			<hr class="c-hr c-hr--type2">
			<b class="c-text c-text--type3">
				<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
			</b>
			<ul class="c-text-list c-bullet c-bullet--dot">
				<li class="c-text-list__item u-co-gray">발신정지는 30일만 가능하며, 30일 이후에는 착/발신 정지로 자동 전환됩니다.</li>
				<li class="c-text-list__item u-co-gray">일시정지 기가 종료 시 휴대폰 이용 중 상태로 자동 복구되며(문자안내), 일시정지 이전 사용 요금제 기준으로 이용요금이 부과됩니다.</li>
				<li class="c-text-list__item u-co-gray">분실정지 일수는 일시정지 신청가능일수(90일)에 포함되지 않습니다.</li>
				<li class="c-text-list__item u-co-gray">단말기 분할상환 금액이 남은 경우 일시정지 기간에도 청구됩니다.</li>
				<li class="c-text-list__item u-co-gray">군입대, 장기해외체류 등 특별한 경우에 해당할 경우 최대 3년까지 일시정지가 가능합니다.</li>
				<li class="c-text-list__item u-co-gray">장기 일시정지의 경우 고객센터(1899-5000)를 통해 신청을 부탁드립니다.</li>
			</ul>
		</div>
<input type="hidden" id="ncn" name="ncn" value="${ncn}"/>
<input type="hidden" id="sndarvStatCd" name="sndarvStatCd" value="${sndarvStatCd}"/>
<input type="hidden" id="subStatusDate" name="subStatusDate" value="${subStatusDate}"/>
	</t:putAttribute>
</t:insertDefinition>