<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/portal/mypage/myinfo/joinCertPop.js"></script>

<div class="c-modal__content" >
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="modalVerifyPrintTitle">가입 증명원 인쇄</h2>
		<button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
	</div>
	<div class="c-modal__body">
		<div class="certi-box">
			<div class="u-flex-between">
				<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--0">
					원부 증명서<span class="c-text c-text--fs13 u-co-gray u-ml--4">(통신서비스 가입증명원)</span>
				</h3>
				<div class="c-button-wrap print-btn">
					<button class="c-button c-button--sm c-button--white c-button-round--sm" onclick="window.print();">
						<i class="c-icon c-icon--print" aria-hidden="true"></i> <span>인쇄</span>
					</button>
				</div>
				<span class="c-text c-text--fs20 issue-num">발급번호 : ${seqSr}</span>
			</div>
			<div class="c-table u-mt--32 u-mb--16">
				<table>
					<caption>이용번호, 가입일, 이름(법인명), 생년월일(사업자등록번호), 기타를 포함한 가입 정보</caption>
					<colgroup>
						<col style="width: 12.5rem">
						<col>
						<col style="width: 7.5rem">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th class="u-ta-left" scope="row">이용번호</th>
							<td class="u-ta-left">${ctn}</td>
							<th class="u-ta-left" scope="row">서비스 구분</th>
							<td class="u-ta-left">모바일</td>
						</tr>
						<tr>
							<th class="u-ta-left" scope="row">가입일</th>
							<td class="u-ta-left">${perMyktfInfo.initActivationDate}</td>
							<th class="u-ta-left" scope="row">현재 상태</th>
							<td class="u-ta-left">${status}</td>
						</tr>
						<tr>
							<th class="u-ta-left" scope="row">이름(법인명)</th>
							<td class="u-ta-left" colspan="3">${searchVO.userName}</td>
						</tr>
						<tr>
							<th class="u-ta-left" scope="row">생년월일(사업자등록번호)</th>
							<td class="u-ta-left" colspan="3">${mcpUserCntrMngDto.dobyyyymmdd}</td>
						</tr>
						<tr>
							<th class="u-ta-left" scope="row">기타</th>
							<td class="u-ta-left" colspan="3">${mcpUserCntrMngDto.modelName} ,일련번호 : ${mcpUserCntrMngDto.unIntmSrlNo}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="certi-box--bottom u-ta-center">
				<p class="c-text c-text--fs16">위와 같이 통신서비스에 가입했음을 증명합니다.</p>
				<p class="c-text c-text--fs14 u-co-gray u-mt--8">${today}</p>
				<p class="c-text c-text--fs16 u-mt--12">
					<b>주식회사 케이티엠모바일</b>
				</p>
				<i class="certi-box__image" aria-hidden="true">
					<img src="/resources/images/portal/content/img_stamp.png" alt="">
				</i>
			</div>
			<ul class="c-text-list c-bullet c-bullet--dot u-mt--48">
	            <li class="c-text-list__item">발급번호 : ${seqSr}</li>
	            <li class="c-text-list__item">발행일 : <fmt:formatDate value="${nmcp:getDateToCurrent(0)}" pattern="yyyy년 MM월 dd일 HH:mm:ss" /></li>

	            <li class="c-text-list__item">발급처 : kt M mobile 홈페이지(www.ktmmobile.com)</li>
	            <li class="c-text-list__item">발급자 : kt M mobile (www.ktmmobile.com)</li>
	            <li class="c-text-list__item">고객센터 : 가입휴대폰에서 114[무료], 타사 휴대폰 또는 유선전화 1899-5000[유료]</li>
	        </ul>
		</div>
	</div>
</div>