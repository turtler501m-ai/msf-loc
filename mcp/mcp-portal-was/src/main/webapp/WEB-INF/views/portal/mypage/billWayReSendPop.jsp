<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<script type="text/javascript" src="/resources/js/portal/mypage/billWayReSendPop.js?version=${jsver}"></script>
<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="Fbill-reissue-modal__Title">명세서 재발송</h2>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="c-table">
			<table>
				<caption>청구 회선, 청구 월로 구성된 명세서 재발송 정보</caption>
				<colgroup>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">청구 회선</th>
						<th scope="col">청구 월</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${mobileNo}</td>
						<td>
							<fmt:parseDate value="${thisMonth}" pattern="yyyyMM" var="view" />
							<fmt:formatDate value="${view}" pattern="yyyy년 MM월"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="c-form-wrap u-mt--32">
			<div class="c-form-group" role="group" aira-labelledby="inpDepInfoTitle">
				<h3 class="c-group--head" id="inpDepInfoTitle">명세서 발송 유형 선택</h3>
				<div class="c-form-row c-col2">
					<div class="c-form">
						<label class="c-label c-hidden" for="billTypeCd">명세서 발송 유형</label>
						<select class="c-select" id="billTypeCd" name="billTypeCd">
							<option value="1">이메일</option>
							<option value="2">모바일(MMS)</option>
						</select>
					</div>
					<div class="c-form eArea" >
						<label class="c-label c-hidden" for="email">이메일 주소</label>
						<input class="c-input" id="email" type="text" name="email" placeholder="예 : ktm@ktm.com">
					</div>
					<div class="c-form moArea" style="display:none;">
						<label class="c-label c-hidden" for="mobileNo">휴대폰</label>
						<input class="c-input" id="mobileNo" name="mobileNo" type="text" value="${mobileNo}" readonly="readonly">
					</div>
				</div>
				<div class="c-form-row u-mt--4 moArea" style="display:none;">
					<p class="c-bullet c-bullet--fyr u-co-sub-4">모바일(MMS) 선택 시 청구 회선으로 명세서가 발송되며 수신번호 변경이 불가합니다.</p>
				</div>
			</div>
		</div>
	</div>
	<div class="c-modal__footer">
		<div class="c-button-wrap">
			<button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>취소</button>
			<button class="c-button c-button--lg c-button--primary u-width--220" id="applyBtn">신청</button>
		</div>
	</div>
	<input type="hidden" id="thisMonth" name="thisMonth" value="${thisMonth}">
	<input type="hidden" id="selNcn" name="selNcn" value="${ncn}">
</div>