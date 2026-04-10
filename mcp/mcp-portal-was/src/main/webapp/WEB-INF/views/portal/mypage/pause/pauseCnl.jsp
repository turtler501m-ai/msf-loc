<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutGnbDefault">

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/portal/mypage/pause/pauseCnl.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">일시정지</h2>
			</div>
			<div class="c-row c-row--lg">
				<h3 class="c-heading c-heading--fs16">일시정지 현황</h3>
				<div class="c-table u-mt--16">
					<table>
						<caption>일시정지 신청일, 고객명, 휴대폰번호, 정지유형, 일시정지 기간 정보를 포함한 표</caption>
						<colgroup>
							<col style="width: 20%">
							<col>
						</colgroup>
						<tbody class="c-table__left">
							<tr>
								<th scope="row">일시정지 신청일</th>
								<td>${subStatusDate}</td>
							</tr>
							<tr>
								<th scope="row">고객명</th>
								<td>${userName}</td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호</th>
								<td>${ctn}</td>
							</tr>
							<tr>
								<th scope="row">정지유형</th>
								<td>${sndarvStatCdNm}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<h3 class="c-heading c-heading--fs16 u-mt--48">일시정지 해제 비밀번호</h3>
				<div class="c-form u-width--460">
					<div class="c-input-wrap">
						<label class="c-label c-hidden" for="cpPwdInsert">일시정지 해제 비밀번호 입력</label>
						<input class="c-input onlyNum" type="password" placeholder="비밀번호를 입력(4~8자)" id="cpPwdInsert" name="cpPwdInsert" value="" maxlength="8">
					</div>
				</div>
				<div class="c-button-wrap u-mt--56">
					<a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:;" id="cnlBtn">취소</a>
					<button class="c-button c-button--lg c-button--primary u-width--220" id="applyBtn">일시정지 해제</button>
				</div>
				<h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
					<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
					<span class="u-ml--4">알려드립니다</span>
				</h3>
				<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
					<li class="c-text-list__item u-co-gray">발신정지는 30일만 가능하며, 30일 이후에는 착/발신 정지로 자동 전환됩니다.</li>
					<li class="c-text-list__item u-co-gray">일시정지 기간 종료 시 휴대폰 이용 중 상태로 자동 복구되며(문자 안내), 일시정지 이전 사용 요금제 기준으로 이용요금이 부과됩니다.</li>
					<li class="c-text-list__item u-co-gray">분실정지 일수는 일시정지 신청가능일수(90일)에 포함되지 않습니다.</li>
					<li class="c-text-list__item u-co-gray">단말기 분할상환 금액이 남은 경우 일시정지 기간에도 청구됩니다.</li>
					<li class="c-text-list__item u-co-gray">군입대, 장기해외체류 등 특별한 경우에 해당할 경우 최대 3년까지 일시정지가 가능합니다. 장기 일시정지의 경우 고객센터(1899-5000)를 통해 신청을 부탁드립니다.</li>
				</ul>
			</div>
		</div>

<input type="hidden" id="ncn" name="ncn" value="${ncn}"/>
<input type="hidden" id="sndarvStatCd" name="sndarvStatCd" value="${sndarvStatCd}"/>
<input type="hidden" id="subStatusDate" name="subStatusDate" value="${subStatusDate}"/>
	</t:putAttribute>
</t:insertDefinition>