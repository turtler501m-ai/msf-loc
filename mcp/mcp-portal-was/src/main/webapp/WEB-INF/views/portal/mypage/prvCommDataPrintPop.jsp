<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<!-- <script type="text/javascript" src="/resources/js/portal/mypage/myinfo/joinCertPop.js"></script> -->

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="modalJoinInfoTitle">통신이용자정보 제공내역 결과 통지서</h2>
		<button class="c-button no-print" data-dialog-close="">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="certi-box">
			<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--0">고객사항</h3>
			<div class="c-button-wrap c-button-wrap--right print-btn">
				<button class="c-button c-button--sm c-button--white c-button-round--sm" onclick="window.print(); return false;">
					<i class="c-icon c-icon--print" aria-hidden="true"></i>
					<span>인쇄</span>
				</button>
			</div>
			<div class="c-table u-mt--32 u-mb--16">
				<table>
					<caption>고객사항</caption>
					<colgroup>
						<col style="width: 20%">
						<col style="width: 30%">
						<col style="width: 20%">
						<col style="width: 30%">
					</colgroup>
					<tbody>
						<tr>
							<th class="u-ta-center" scope="row">가입자명</th>
							<td class="u-ta-left">${cstmrInfo.apyNm}</td>
							<th class="u-ta-center" scope="row">대상전화번호</th>
							<td class="u-ta-left">${cstmrInfo.tgtSvcNo}</td>
						</tr>
						<tr>
							<th class="u-ta-center" scope="row">생년월일</th>
							<td class="u-ta-left">${cstmrInfo.bthday}</td>
							<th class="u-ta-center" scope="row">연락처</th>
							<td class="u-ta-left">${cstmrInfo.cntcTelNo}</td>
						</tr>
						<tr>
							<th class="u-ta-center" scope="row">신청일</th>
							<td class="u-ta-left" colspan="3">${cstmrInfo.apyDt}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--32">결과통지</h3>
			<div class="c-table u-mt--32 u-mb--16">
				<table>
					<caption>결과통지</caption>
					<colgroup>
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 40%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" colspan="5">통신이용자정보 제공현황</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="col">상품</th>
							<th scope="col">제공일자</th>
							<th scope="col">요청기관</th>
							<th scope="col">요청사유</th>
							<th scope="col">제공내역</th>
						</tr>
						<c:forEach items="${offerList}" var="dataList">
							<tr>
								<td class="u-ta-center">${dataList.sbscPrdtCd}</td>
								<td class="u-ta-center">
									<fmt:parseDate value="${fn:substring(dataList.reqDttm, 0,8)}" var="reqDttmDate" pattern="yyyyMMdd" />
									<fmt:formatDate value="${reqDttmDate}" pattern="yyyy.MM.dd" />
								</td>
								<td class="u-ta-center">${dataList.invstNm}</td>
								<td class="u-ta-center">${dataList.reqRsn}</td>
								<td class="u-ta-left">
									성함 : ${dataList.cstmrNm}<br/>
									핸드폰번호 : ${dataList.subscriberNo}<br/>
									주민번호 : ${dataList.userSsn}<br/>
									<c:if test="${dataList.subStatus != 'C'}">
									가입일 : ${dataList.openDt} ~ 사용중<br/>
									</c:if>
									<c:if test="${dataList.subStatus == 'C'}">
									가입일 : ${dataList.openDt} ~ ${dataList.tmntDt} 해지<br/>
									</c:if>
									가입주소지 : ${dataList.cstmrAddr}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--32">통신이용자정보 제공근거</h3>
			<div class="certi-box--bottom u-ta-left">
				<p class="c-text c-text--fs16">통신자료는 법원, 검사 또는 수사관서의 장, 정보수사기관의 장이 재판, 수사, 형의 집행 또는 국가안전보장에 대한 위해를 방지하기 위하여 요청하는 경우 전기통신사업법 제83조에 근거하여 제공하게 됩니다.</p>
			</div>

			<div class="certi-box--bottom u-ta-center">
				<p class="c-text c-text--fs14 u-co-gray u-mt--8">${today}</p>
				<p class="c-text c-text--fs16 u-mt--12">
					<b>주식회사 kt M mobile</b>
				</p>
				<i class="certi-box__image" aria-hidden="true"> <img
					src="/resources/images/portal/content/img_stamp.png" alt="">
				</i>
			</div>
		</div>
	</div>
</div>