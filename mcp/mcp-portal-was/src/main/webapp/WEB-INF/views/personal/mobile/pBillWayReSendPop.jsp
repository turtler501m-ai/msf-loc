<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script type="text/javascript" src="/resources/js/personal/mobile/pBillWayReSendPop.js?version=25.06.10"></script>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="serv-apply4-title">명세서 재발송</h1>
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
					<th>청구 회선</th>
					<th>청구 월</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>${searchVO.ctn}</td>
					<td>
						<fmt:parseDate value="${thisMonth}" pattern="yyyyMM" var="view" />
						<fmt:formatDate value="${view}" pattern="yyyy년 MM월"/>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<h3 class="c-heading c-heading--type2 u-mt--40 u-mb--12">명세서 발송 유형 선택</h3>
		<div class="c-form__group" role="group">
			<div class="c-form">
				<label class="c-label c-hidden">명세서 발송 유형</label>
				<select class="c-select" id="billTypeCd">
					<option value="1">이메일</option>
					<option value="2">모바일(MMS)</option>
				</select>
			</div>
			<div class="c-form eArea">
				<div class="c-form__title">이메일 주소</div>
				<label class="c-label c-hidden" for="email">이메일 주소</label>
				<input class="c-input" id="email" name="email" type="text" placeholder="예 : ktm@ktm.com">
			</div>
			<div class="c-form moArea" style="display:none;">
				<div class="c-form__title">휴대폰 번호</div>
				<label class="c-label c-hidden" for="mobileNo">휴대폰 번호</label>
				<input class="c-input" id="mobileNo" type="text" title="휴대폰 번호" readonly="readonly" value="${searchVO.ctn}">
				<p class="c-bullet c-bullet--fyr u-co-sub-4 u-mt--8 moArea" style="display:none;">모바일(MMS) 선택 시 청구 회선으로 명세서가 발송되며 수신번호 변경이 불가합니다.</p>
			</div>
		</div>
		<div class="c-button-wrap u-mt--48">
			<button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
			<button class="c-button c-button--full c-button--primary" id="applyBtn">신청</button>
		</div>
		<input type="hidden" id="thisMonth" name="thisMonth" value="${thisMonth}">
		<input type="hidden" id="selNcn" name="selNcn" value="${searchVO.ncn}">
	</div>
</div>