<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="mlayoutDefaultTitle">

	<t:putAttribute name="titleAttr">교체 유심 신청</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
		<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
		<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
		<script type="text/javascript" src="/resources/js/common/validator.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/usim/replaceUsimView.js?version=25-11-07"></script>
		<script type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=25-10-31"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="isSmsAuth" name="isSmsAuth" value=""/>
		<input type="hidden" id="osstNo" name="osstNo" value=""/>
		<input type="hidden" id="possibleCnt" name="possibleCnt" value=""/>

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					교체 유심 신청
					<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="req-self-content">
				<p class="c-text u-fs-14">
					유심 교체를 원하시는 고객님께는 택배로 새 유심을 보내드립니다.(무료)
					<c:if test="${guideVideo ne null}">
						<a class="u-co-sub-4 c-text--line u-ml--6" id="guideTag" href="javascript:void(0);" data-iframe-src="${guideVideo.mobileLinkUrl}" title="${guideVideo.ntcartTitle}">유심 교체 가이드 보기</a>
					</c:if>
				</p>
				<p class="c-text u-fs-14 u-mt--4">
					유심을 수령하신 후 안내 문자에 포함된 URL 또는 마이페이지에서 유심변경을 진행해 주세요.
					<a class="u-co-sub-4 c-text--line u-ml--6" href="/m/mypage/usimSelfChg.do" title="유심 셀프 변경 페이지 바로가기">마이페이지 바로가기</a>
				</p>
				<p class="c-bullet c-bullet--fyr u-co-gray u-mt--12">
					마이페이지에서 진행하시는 경우 로그인이 필요합니다.<br>
					- 경로 : 전체메뉴(≡) > 가입정보 조회/변경 > 유심 셀프변경
				</p>

				<div class="req-self-wrap u-mt--40">
					<h3 class="req-self-title">본인인증 및 회선 확인</h3>
					<div class="req-self-form-wrap">
						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>가입정보 확인</h4>
								</div>
								<div class="c-form">
									<div class="req-self-form__form">
										<div class="c-form__input">
											<c:if test="${not empty userName}">
												<input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" value="${userName}" onkeyup="requiredFieldChk();" readonly>
											</c:if>
											<c:if test="${empty userName}">
												<input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" maxlength="90" onkeyup="requiredFieldChk();">
											</c:if>
											<label class="c-form__label" for="cstmrName">이름 입력</label>
										</div>
									</div>
								</div>
								<div class="c-button-wrap u-mt--14">
									<button title="새 창 열림" class="c-button c-button--full c-button--h48 c-button--mint" id="btnSmsAuth">휴대폰 인증</button>
								</div>
								<div class="req-self-form__bullet">
									<ul>
										<c:if test="${userName ne null and !empty userName }">
											<li>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
										</c:if>
										<li>인증이 완료되면 고객님의 사용중인 번호와 신청 가능 여부가 표시됩니다.</li>
										<li>
											아래 경우에는 배송 신청이 제한됩니다.
											<ul class="c-text-list c-bullet c-bullet--hyphen">
												<li class="c-text-list__item u-co-gray">만 19세 미만 고객 : 법정대리인이 고객센터로 연락 필요</li>
												<li class="c-text-list__item u-co-gray">법인/선불 회선, 정지 또는 eSIM 사용 회선 : 고객센터 문의 필요</li>
											</ul>
										</li>
										<li class="u-fw--bold">본 유심은 kt M모바일에서만 사용 가능합니다.</li>
									</ul>
								</div>
							</div>
						</div>

						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title u-mb--16">
									<h4>회선 확인</h4>
								</div>
								<div class="c-form">
									<div id="befcntrCntWrap">본인인증 후 회선 정보가 표시됩니다.</div>
									<div id="replaceUsimCntrText" style="display: none;">
										총 <span class="u-fw--bold">3개 회선</span> 중 <span class="u-fw--bold">2개 회선</span>이 신청 가능하며, <span class="u-fw--bold">배송 수량 2</span>개가 자동 적용됩니다.
									</div>
									<div id="replaceUsimCntrInfo">
										<div class="c-table c-table--row u-mt--16">
											<table>
												<caption>번호, 신청가능 여부, 불가사율로 구성된 정보</caption>
												<colgroup>
													<col style="width: 24%"/>
													<col/>
												</colgroup>
												<tbody>
												<tr>
													<th scope="row">번호</th>
													<td class="u-ta-left"></td>
												</tr>
												<tr>
													<th scope="row">신청</th>
													<td class="u-ta-left"></td>
												</tr>
												<tr>
													<th scope="row">사유</th>
													<td class="u-ta-left"></td>
												</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<hr class="c-hr c-hr--type1 c-expand">

				<div class="req-self-wrap">
					<h3 class="req-self-title">배송지 정보</h3>
					<div class="req-self-form-wrap">
						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>받는 분</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form">
										<div class="c-form__input">
											<input class="c-input" name="dlvryName" id="dlvryName" maxlength="20" type="text" placeholder="이름 입력" onkeyup="requiredFieldChk();" autocomplete="off" style="ime-mode:active;">
											<label class="c-form__label" for="dlvryName">이름 입력</label>
										</div>
									</div>
									<div class="c-form">
										<div class="c-form__input-group has-value">
											<div class="c-form__input c-form__input-division has-value">
												<input class="c-input--div3 onlyNum" name="dlvryTelFn" id="dlvryTelFn" type="tel" maxlength="3" placeholder="숫자입력" value="" onkeyup="requiredFieldChk();nextFocus(this, 3, 'dlvryTelMn');" autocomplete="off"> <span>-</span>
												<input class="c-input--div3 onlyNum" name="dlvryTelMn" id="dlvryTelMn" type="tel" maxlength="4" placeholder="숫자입력" value="" onkeyup="requiredFieldChk();nextFocus(this, 4, 'dlvryTelRn');" autocomplete="off"> <span>-</span>
												<input class="c-input--div3 onlyNum" name="dlvryTelRn" id="dlvryTelRn" type="tel" maxlength="4" placeholder="숫자입력" value="" onkeyup="requiredFieldChk();" autocomplete="off">
												<label class="c-form__label" for="dlvryTelFn">연락처</label>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>배송지</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form">
										<div class="c-form__group" role="group" aria-labeledby="inpAddress">
											<div class="c-input-wrap">
												<input class="c-input" type="text" placeholder="우편번호" id="dlvryPost" title="우편번호 입력" readonly>
												<button class="c-button c-button--sm c-button--underline _btnAddr">우편번호 찾기</button>
											</div>
											<input class="c-input u-co-gray-9" type="text" id="dlvryAddr" placeholder="주소" title="주소 입력" readonly>
											<input class="c-input u-co-gray-9" type="text" id="dlvryAddrDtl" placeholder="상세주소" title="상세 주소 입력" readonly>
										</div>
									</div>
									<div class="c-form" id="dlvryMemoWrap">
										<div class="c-textarea-wrap">
											<textarea class="c-textarea" placeholder="배송 요청사항 (선택)" name="dlvryMemo" id="dlvryMemo" maxlength="50" autocomplete="off"></textarea>
											<div class="c-textarea-wrap__sub">
												<span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span id="dlvryMemoChk">0/50</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="c-agree u-mt--40">
					<div class="c-agree__item">
						<input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree" />
						<label class="c-label u-fs-16" for="chkAgree">개인정보 수집이용 동의(필수)</label>
						<button type="button" class="c-button c-button--xsm" onclick="openPage('termsPop','/termsPop.do','cdGroupId1=FormEtcCla&cdGroupId2=clauseReqUsimFlag');">
							<span class="c-hidden">상세보기</span>
							<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
						</button>
					</div>
				</div>
				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary is-disabled" id="saveBtn">신청</button>
				</div>
			</div>
		</div>

		<!-- 동영상 보기 팝업 -->
		<div class="c-modal quickguide" id="modalContent" role="dialog" tabindex="-1" aria-describedby="modalContentTitle">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__body">
						<div class="quickguide-box">
							<i class="c-icon c-icon--reset" aria-hidden="true" data-dialog-close></i>
							<iframe id="youTubeIframe" src="about:blank" frameborder="0" allowfullscreen="1" style="width: 100%; height: 250px;" allow="autoplay; encrypted-media"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>

</t:insertDefinition>