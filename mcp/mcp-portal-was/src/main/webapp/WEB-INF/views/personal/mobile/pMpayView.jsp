<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">소액결제 조회 및 한도변경</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.06.10"></script>
		<script type="text/javascript" src="/resources/js/personal/mobile/pMpayView.js?version=25.06.10"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">개인화 URL (소액결제 조회 및 한도변경)<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<%@ include file="/WEB-INF/views/personal/mobile/pCommCtn.jsp" %>

			<h4 class="c-heading c-heading--type2 u-mb--12">소액결제 이용한도</h4>
			<div class="info-box">
				<dl>
					<dt>소액결제 한도</dt>
					<dd id="tmonLmtAmt">0원</dd>
				</dl>
				<dl>
					<dt>결제금액</dt>
					<dd id="nowMonthAmt">0원</dd>
				</dl>
				<dl>
					<dt>잔여한도</dt>
					<dd id="rmndLmtAmt">0원</dd>
				</dl>
			</div>
			<ul class="c-text-list c-bullet c-bullet--fyr u-mt--8">
				<li class="c-text-list__item u-co-sub-4">소액결제 사용 및 한도 변경을 위해서는 최초 1회 이용동의가 필요합니다.</li>
				<li class="c-text-list__item u-co-sub-4"><span class="u-co-red u-fs-17">이용동의 및 한도 변경은 PASS앱에서 가능합니다.<br/>
                    [PASS 앱에서 설정 방법]<br/>
                    PASS 앱 로그인 > 전체 > 결제 > 휴대폰결제 > 한도변경하기 > 결제한도에서 ‘차단(0원)’ 또는 원하는 금액 선택</span></li>
			</ul>
			<hr class="c-hr c-hr--type1 c-expand">
			<div class="c-flex--between">
				<h4 class="c-heading c-heading--type2 u-m--0">이용내역 조회</h4>
				<div class="phone-line-wrap u-mt--0 u-w70p">
					<select class="u-width--120" id="monthTitle" onchange="searchMpayHistory();">
						<c:forEach items="${dateList}" var="date" varStatus="status">
							<option value="${date.monthTitle}" <c:if test="${status.index eq 0}">selected</c:if>>
									${fn:substring(date.monthTitle,0,4)}년 ${fn:substring(date.monthTitle,4,6)}월
							</option>
						</c:forEach>
					</select>
					<select class="u-width--120 u-ml--8" id="mPayStatus" onchange="searchMpayHistory();" style="text-align:center;">
						<option value= "ALL" selected>전체</option>
						<c:forEach items="${mPayStatusCd}" var="item">
							<option value="${item.dtlCd}">
									${item.dtlCdNm}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="c-card c-card--type4 u-mb--8">
				<div class="c-card__box u-mt--16 u-pb--16">
					<div class="c-card__title">
						<b>총 이용금액</b>
					</div>
					<div class="c-card__price-box">
            <span id="datePeriod">
	            <c:forEach items="${dateList}" var="date" varStatus="status">
	           	<span class="c-text c-text--type4 u-co-gray" name="firstLastDay" data-month="${date.monthTitle}" <c:if test="${status.index ne 0}">style="display:none"</c:if>>
	           		${date.monthFirstDay} ~ ${date.monthLastDay}
	           	</span>
	            </c:forEach>
            </span>
					  <span class="c-text c-text--type3 u-co-sub-4">
              <b id="selMonthAmt">0</b>
              <span>원</span>
            </span>
					</div>
				</div>
			</div>
			<ul class="c-text-list c-bullet c-bullet--fyr u-mb--24">
				<li class="c-text-list__item u-co-sub-4">소액결제 이용내역은 최근 13개월 이내까지 가능합니다.<br/>(2022년 7월 이전 내역은 조회 불가)</li>
			</ul>
			<div class="c-table">
				<table>
					<caption>이용업체명, 이용서비스, 구매상품, 결제대행사, 결제일시, 금액, 상태로 구성된 이용내역 정보</caption>
					<colgroup>
						<col>
						<col style="width: 32%">
						<col>
						<col style="width: 17%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">이용<br/>서비스</th>
							<th scope="col">결제일시</th>
							<th scope="col">금액</th>
							<th scope="col">상세보기</th>
						</tr>
					</thead>
					<tbody id="mPayListBody">
						<tr>
							<td colspan="4">내역이 존재하지 않습니다.</td>
						</tr>
					</tbody>
				</table>
			</div>
			<hr class="c-hr c-hr--type2 u-mt--40 u-mb--40">
			<h3 class="c-heading c-heading--type6">
			  <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
				<span class="u-ml--4">알려드립니다.</span>
			</h3>
			<ul class="c-text-list c-bullet c-bullet--dot">
				<li class="c-text-list__item u-co-gray">
					소액결제 대행사
					<button class="c-button c-button--underline u-co-sub-4 fs-13 u-mt--m2 u-ml--2" data-dialog-trigger="#agency-contact-modal">연락처 보기</button>
				</li>
				<li class="c-text-list__item u-co-gray">이용내역조회는 당월포함 13개월까지 가능하며 1개월 단위로 조회 가능합니다.<br/>(2022년 7월 이전 내역은 조회 불가)</li>
				<li class="c-text-list__item u-co-gray">소액결제 월 최대 한도는 100만원이며, 고객님의 통신요금 미납 및 연체이력 등에 따라 월 최대 한도가 30만원으로 제한될 수 있습니다.</li>
				<li class="c-text-list__item u-co-gray">신규개통시 일정기간 부정사용 피해방지와 고객보호를 위하여 이용한도가 4만원으로 제한 될 수 있습니다.</li>
				<li class="c-text-list__item u-co-gray">월 이용한도는 편의에 따라 4만원, 30만원, 50만원, 60만원, 80만원, 100만원 또는 만원단위 금액으로 직접 조정할 수 있지만 통신요금 미납과 연체이력 등에 따라 한도 상향이 제한될 수 있습니다.</li>
				<li class="c-text-list__item u-co-gray">안전한 결제를 위해 이용한도 상향시에는 추가적인 동의 절차가 필요합니다.</li>
				<li class="c-text-list__item u-co-gray">
					부정개통으로 인한 금전 피해 방지를 위해 개통일 포함 3일 후 24시까지 소액결제 이용이 제한 됩니다.<br />예) 월요일 개통 시 수요일 23:59분까지 소액결제 이용 제한
				</li>
			</ul>
		</div>

		<!-- 소액결제 상세내역 팝업 -->
		<div class="c-modal" id="micropay-detail_dialog" role="dialog" tabindex="-1" aria-describedby="#micropay-detail-title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="micropay-detail-title">소액결제 상세정보</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="c-table c-table--row">
							<table>
								<caption>이용업체명, 이용서비스, 구매상품, 결제대행사, 결제일시, 금액, 상태, 결제유형으로 구성된 이용내역 정보</caption>
								<colgroup>
									<col style="width: 38%">
									<col>
								</colgroup>
								<tbody >
								<tr>
									<th class="u-ta-left" scope="row">이용업체명</th>
									<td class="u-ta-right" id="onePtnrCpNm"></td>
								</tr>
								<tr>
									<th class="u-ta-left" scope="row">이용서비스</th>
									<td class="u-ta-right" id="onePtnrSvcNm"></td>
								</tr>
								<tr>
									<th class="u-ta-left" scope="row">구매상품</th>
									<td class="u-ta-right" id="oneSettlNm"></td>
								</tr>
								<tr>
									<th class="u-ta-left" scope="row">결제대행사</th>
									<td class="u-ta-right" id="onePtnrPgNm"></td>
								</tr>
								<tr>
									<th class="u-ta-left" scope="row">결제일시</th>
									<td class="u-ta-right" id="oneSettlDtFmt"></td>
								</tr>
								<tr>
									<th class="u-ta-left" scope="row">금액</th>
									<td class="u-ta-right" id="oneAmt"></td>
								</tr>
								<tr>
									<th class="u-ta-left" scope="row">상태</th>
									<td class="u-ta-right" id="onePayStatusNm"></td>
								</tr>
								</tbody>
							</table>
						</div>
						<div class="c-button-wrap u-mt--48">
							<button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 소액결제 대행사 연락처 상세 팝업 -->
		<div class="c-modal" id="agency-contact-modal" role="dialog" tabindex="-1" aria-describedby="#agency-contact-title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="agency-contact-title">소액결제대행사 연락처</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="c-table">
							<table>
								<caption>이번달 소액결제 한도, 이번 달 결제금액, 이번달 잔여한도 으로 구성된 소액결제 이용한도 정보</caption>
								<colgroup>
									<col style="width: 28%">
									<col>
									<col>
								</colgroup>
								<thead>
								<tr>
									<th scope="col">결제대행사</th>
									<th scope="col">고객센터<br/>전화번호</th>
									<th scope="col">홈페이지</th>
								</tr>
								</thead>
								<tbody>
								<tr>
									<td>페이레터</td>
									<td>1599-2583</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.payletter.com/ko/" title="페이레터 바로가기(새창)" target="_blank">www.payletter.com</a>
									</td>
								</tr>
								<tr>
									<td>모빌리언스</td>
									<td>1800-0678</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.mobilians.co.kr/" title="모빌리언스 바로가기(새창)" target="_blank">www.mobilians.co.kr</a>
									</td>
								</tr>
								<tr>
									<td>다날</td>
									<td>1566-3355</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.danal.co.kr" title="다날 바로가기(새창)" target="_blank">www.danal.co.kr</a>
									</td>
								</tr>
								<tr>
									<td>갤럭시아머니트리(주)</td>
									<td>1566-0123</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.galaxiamoneytree.co.kr" title="갤럭시아머니트리(주) 바로가기(새창)" target="_blank">www.galaxiamoneytree.co.kr</a>
									</td>
								</tr>
								<tr>
									<td>NHN한국사이버결제</td>
									<td>1688-6410</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.kcp.co.kr" title="NHN한국사이버결제 바로가기(새창)" target="_blank">www.kcp.co.kr</a>
									</td>
								</tr>
								<tr>
									<td>효성FMS</td>
									<td>1544-5162</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.hyosungfms.co.kr" title="효성FMS 바로가기(새창)" target="_blank">www.hyosungfms.co.kr</a>
									</td>
								</tr>
								<tr>
									<td>세틀뱅크</td>
									<td>1600-5220</td>
									<td>
										<a class="c-button u-td-underline u-co-sub-4 fs-13 u-word-break" href="https://www.settlebank.co.kr" title="세틀뱅크 바로가기(새창)" target="_blank">www.settlebank.co.kr</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
						<div class="c-button-wrap u-mt--48">
							<button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>