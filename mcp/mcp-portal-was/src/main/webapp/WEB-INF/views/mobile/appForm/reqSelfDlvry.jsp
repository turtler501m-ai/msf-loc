<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<c:set var="usimBuyCdDto1" value="${nmcp:getCodeNmDto('usimProdInfo','01')}" />
<c:choose>
	<c:when test="${empty usimBuyCdDto1 or empty usimBuyCdDto1.expnsnStrVal3}">
		<c:set var="usimBuyLimit1" value="1" />
	</c:when>
	<c:otherwise>
		<c:set var="usimBuyLimit1" value="${usimBuyCdDto1.expnsnStrVal3}" />
	</c:otherwise>
</c:choose>
<c:set var="usimBuyCdDto2" value="${nmcp:getCodeNmDto('usimProdInfo','02')}" />
<c:choose>
	<c:when test="${empty usimBuyCdDto2 or empty usimBuyCdDto2.expnsnStrVal3}">
		<c:set var="usimBuyLimit2" value="1" />
	</c:when>
	<c:otherwise>
		<c:set var="usimBuyLimit2" value="${usimBuyCdDto2.expnsnStrVal3}" />
	</c:otherwise>
</c:choose>
<c:set var="usimBuyCdDto3" value="${nmcp:getCodeNmDto('usimProdInfo','03')}" />
<c:choose>
	<c:when test="${empty usimBuyCdDto3 or empty usimBuyCdDto3.expnsnStrVal3}">
		<c:set var="usimBuyLimit3" value="1" />
	</c:when>
	<c:otherwise>
		<c:set var="usimBuyLimit3" value="${usimBuyCdDto3.expnsnStrVal3}" />
	</c:otherwise>
</c:choose>

<t:insertDefinition name="mlayoutDefaultTitle">

	<t:putAttribute name="titleAttr">다이렉트몰 구매하기</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">

		<script type="text/javascript" src="/resources/js/common/validator.js?version=24.12.17"></script>
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=24.12.17"></script>
		<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=24.12.17"></script>
		<script type="text/javascript" src="/resources/js/mobile/appForm/reqSelfDlvry.js?version=25.09.16"></script>
		<script type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=25.09.16"></script>
		<script src="${smartroScriptUrl}?version=${today}"></script>

		<style type="text/css">
        .input-kor{-webkit-ime-mode:active;-moz-ime-mode:active;-ms-ime-mode:active;ime-mode:active;}
		</style>

	</t:putAttribute>

	<t:putAttribute name="bottomScript">
		<!-- naver DA script  통계  전환페이지 설정 -->
		<script type="text/javascript">
      if(window.wcs){
        // (5) 결제완료 전환 이벤트 전송
        var _conv = {}; // 이벤트 정보를 담을 object 생성
        _conv.type = "custom001";  // object에 구매(purchase) 이벤트 세팅
        wcs.trans(_conv); // 이벤트 정보를 담은 object를 서버에 전송
      }
		</script>
		<!-- naver DA script  통계  전환페이지 end -->
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<form id="tranMgr" name="tranMgr" method="post">
			<!-- 각 값들을 가맹점에 맞게 설정해 주세요. -->
			<input type="hidden" name="PayMethod"		 id="PayMethod"			value="CARD"/>
			<input type="hidden" name="GoodsCnt"		 id="GoodsCnt"			value=""/>
			<input type="hidden" name="GoodsName"		 id="GoodsName"			value=""/> 	<!-- 거래 상품명 -->
			<input type="hidden" name="Amt"					 id="Amt"						value=""/> 	<!-- 설정한 Amt -->
			<input type="hidden" name="Moid"				 id="Moid"					value=""/> 	<!-- 주문번호 특수문자 포함 불가 -->
			<input type="hidden" name="Mid"				   id="Mid"						value=""/> 	<!-- 설정한 Mid -->
			<input type="hidden" name="ReturnUrl"		 id="ReturnUrl"			value=""/> 	<!-- 가맹점 ReturnUrl 설정 -->
			<input type="hidden" name="StopUrl"			 id="StopUrl"				value=""/> 	<!-- 결제중단 URL -->
			<input type="hidden" name="BuyerName"		 id="BuyerName"			value=""/> 	<!-- 구매자명 -->
			<input type="hidden" name="BuyerTel"		 id="BuyerTel"			value=""/> 	<!-- 전화번호 -->
			<input type="hidden" name="BuyerEmail"	 id="BuyerEmail"		value=""/> 	<!-- 이메일 -->
			<input type="hidden" name="MallIp"			 id="MallIp"				value=""/>
			<input type="hidden" name="VbankExpDate" id="VbankExpDate" 	value=""/> 	<!-- 가상계좌 이용 시 필수 -->
			<input type="hidden" name="EncryptData"	 id="EncryptData" 	value=""/> 	<!-- 위/변조방지 HASH 데이터 -->
			<input type="hidden" name="GoodsCl"			 id="GoodsCl"				value="1"/> <!-- 0: 컨텐츠, 1: 실물(가맹점 설정에 따라 셋팅, 휴대폰 결제 시 필수)-->
			<input type="hidden" name="EdiDate"			 id="EdiDate"				value=""/> 	<!-- 설정한 EdiDate -->
			<input type="hidden" name="TaxAmt"			 id="TaxAmt"				value=""/> 	<!-- 과세 : 부가세 직접계산 가맹점 필수, 숫자만 가능, 문장부호 제외  -->
			<input type="hidden" name="TaxFreeAmt"	 id="TaxFreeAmt"		value=""/> 	<!-- 비과세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
			<input type="hidden" name="VatAmt"			 id="VatAmt"				value=""/> 	<!-- 부가세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
			<input type="hidden" name="MallReserved" id="MallReserved"	value=""/> 	<!-- 상점예비필드 -->
		</form>

		<input type="hidden" id="mode" name="mode"/>
		<input type="hidden" id="selfDlvryIdx" name="selfDlvryIdx" value=""/>
		<input type="hidden" id="userEmail" name="userEmail" value="guest@ktmmobile.com"/>

		<input type="hidden" id="usimBuyLimit1" name="usimBuyLimit1" value="${usimBuyLimit1}"/>
		<input type="hidden" id="usimBuyLimit2" name="usimBuyLimit2" value="${usimBuyLimit2}"/>
		<input type="hidden" id="usimBuyLimit3" name="usimBuyLimit3" value="${usimBuyLimit3}"/>
		<input type="hidden" id="isTimeCheck" name="isTimeCheck" value="${isTimeCheck}"/>
		<input type="hidden" id="isTimeMsg" name="isTimeMsg" value="${isTimeMsg}"/>
		<input type="hidden" id="dView" name="dView" value="${dView}"/>
		<input type="hidden" id="ableTime" name="ableTime" value="${ableTime}"/>
		<input type="hidden" id="bizOrgCd" name="bizOrgCd" value=""/>
		<input type="hidden" id="psblYn" name="psblYn" value=""/>
		<input type="hidden" id="acceptTime" name="acceptTime" value=""/>
		<input type="hidden" id="dlvryJibunAddr" name="dlvryJibunAddr" value=""/>
		<input type="hidden" id="dlvryJibunAddrDtl" name="dlvryJibunAddrDtl" value=""/>
		<input type="hidden" id="entY" name="entY" value=""/>
		<input type="hidden" id="entX" name="entX" value=""/>
		<input type="hidden" id="isSmsAuth" name="isSmsAuth" value=""/>
		<input type="hidden" id="isSmsMinorAgentAuth" name="isSmsMinorAgentAuth" value=""/>

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					유심구매
					<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="req-self-content">
				<div class="req-self-wrap">
					<h3 class="req-self-title">주문 정보</h3>
					<div class="req-self-form-wrap">

						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>유심 선택</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form">
										<div class="c-check-wrap">
											<c:forEach items="${usimProdIdList}" var="usimProdInfo" varStatus="eachIndex">
												<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" type="radio" name="usimProdId" id="usimProdId0${eachIndex.index+1}" value="${usimProdInfo.dtlCd}" title="${eachIndex.index+1}" amount="${usimProdInfo.expnsnStrVal1}" data-prodNm="${usimProdInfo.dtlCdNm}" data-indcOdrg="${usimProdInfo.indcOdrg}" data-dtlCdDesc="${usimProdInfo.dtlCdDesc}">
												<label class="c-label" for="usimProdId0${eachIndex.index+1}">
													<c:if test="${usimProdInfo.dtlCd eq '01'}"><i class="c-icon c-icon--phone" aria-hidden="true"></i></c:if>
													<c:if test="${usimProdInfo.dtlCd eq '02'}"><i class="c-icon c-icon--card" aria-hidden="true"></i></c:if>
													<c:if test="${usimProdInfo.dtlCd eq '03'}"><i class="c-icon c-icon--ipin" aria-hidden="true"></i></c:if>
														${usimProdInfo.dtlCdNm} <br><fmt:formatNumber value="${usimProdInfo.expnsnStrVal1 }" pattern="#,###" />원
												</label>
											</c:forEach>
										</div>
									</div>
									<div class="req-self-form__bullet">
										<ul>
											<li id="moNfcPoss"></li>
										</ul>
									</div>
								</div>
							</div>
						</div>

						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>구매 수량</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form">
										<div class="count-wrap">
                      <span class="c-counter">
                        <button class="c-button c-button--xsm c-button--gray minus">-</button>
                        <input type="number" min="1" max="${usimBuyLimit1}" step="1" readonly id="reqBuyQnty">
                        <button class="c-button c-button--xsm c-button--primary plus">+</button>
                      </span>
											<span class="c-price"> <b id="usimPrice">0</b> 원</span>
										</div>
									</div>
									<div class="req-self-form__bullet">
										<ul>
											<li>택배는 최대 <span id="limitSpan">${usimBuyLimit1}</span>개까지, 바로배송(당일 퀵)은 주문 당 한 개의 유심만 구매하실 수 있습니다.</li>
										</ul>
									</div>
								</div>
							</div>
						</div>

						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>주문자 정보</h4>
								</div>
								<div class="c-form">
									<div class="req-self-form__form">
										<div class="c-form__input">
											<c:if test="${not empty userName}">
												<input class="c-input" id="orderName" name="oderName" type="text" placeholder="이름 입력" value="${userName}" onkeyup="inputChk();" readonly>
											</c:if>
											<c:if test="${empty userName}">
												<input class="c-input" id="orderName" name="oderName" type="text" placeholder="이름 입력" maxlength="20" onkeyup="inputChk();">
											</c:if>
											<label class="c-form__label" for="orderName">이름 입력</label>
										</div>
									</div>
								</div>
								<div class="req-self-form__bullet">
									<ul>
										<c:if test="${userName ne null and !empty userName }">
										<li>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
										</c:if>
										<li>만 14세 미만 사용자는 법정대리인의 동의 후 결제를 진행할 수 있습니다.</li>
									</ul>
								</div>
								<div class="c-button-wrap u-mt--14">
									<button title = "새 창 열림" class="c-button c-button--full c-button--h48 c-button--mint" id="btnSmsAuth">휴대폰 인증</button>
								</div>
							</div>
						</div>

						<div class="req-self-form-group" id="minorAgentWrap" style="display: none;">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>법정대리인 동의</h4>
								</div>
								<div class="c-form">
									<div class="c-checkbox-wrap u-mt--12">
										<input class="c-checkbox" id="minorAgentAgrmYn" type="checkbox" name="minorAgentAgrmYn">
										<label class="c-label u-mr--0" for="minorAgentAgrmYn">
											<span class="u-co-red">(필수)</span> 본인은 주문자(만 14세 미만)의 법정대리인으로서 주문자의 개인정보 수집∙이용 및 결제에 동의합니다.
										</label>
									</div>
									<div class="req-self-form__form">
										<div class="c-form__input">
											<input class="c-input" id="minorAgentName" name="minorAgentName" type="text" placeholder="법정대리인 이름 입력" maxlength="20" onkeyup="inputChk();">
											<label class="c-form__label" for="minorAgentName">법정대리인 이름 입력</label>
										</div>
									</div>
								</div>
								<div class="c-button-wrap u-mt--14">
									<button title = "새 창 열림" class="c-button c-button--full c-button--h48 c-button--mint" id="btnSmsMinorAgentAuth">휴대폰 인증</button>
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
									<h4>배송유형 선택</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form dlvryKind">
										<div class="c-check-wrap">
											<input class="c-radio dlvryKind02" name="dlvryKind" id="dlvryKind2" value="02" type="radio">
											<label class="c-label dlvryKind02" for="dlvryKind2">택배</label>
											<input class="c-radio dlvryKind01" name="dlvryKind" id="dlvryKind1" value="01" type="radio">
											<label class="c-label dlvryKind01" for="dlvryKind1">바로배송(당일 퀵)</label>
										</div>
									</div>
									<div class="req-self-form__bullet dlvryType1" style="display:none;">
										<ul>
											<li>택배로 신청하실 경우 평일 13시까지 주문 시 당일 출고됩니다.(무료배송)</li>
										</ul>
									</div>
									<div class="req-self-form__bullet dlvryType2" style="display:none;">
										<ul>
											<li class="link">
												<button class="c-button c-button--underline u-co-mint u-co-sub-4 fs-14" data-dialog-trigger="#deliveryDialog">바로배송(당일 퀵)이란
													<span class="c-hidden">바로배송(당일 퀵) 설명 링크(새창)</span>
												</button>
											</li>
											<li class="u-mt--12">바로배송(당일 퀵)은 매일 오전 09시부터 오후 22시까지 이용 가능합니다. (무료배송/휴무일 없음)</li>
											<li>바로배송(당일 퀵)은 선결제 방식으로 미리 유심비를 결제하며, 결제 후 배송접수 실패 시 결제하신 내역은 영업일 기준 7일 이내 자동 환불 처리 됩니다.</li>
										</ul>
									</div>
								</div>
							</div>
						</div>

						<div class="req-self-form-group">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>받는 분</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form">
										<div class="c-form__input">
											<input class="c-input" name="dlvryName" id="dlvryName" maxlength="20" type="text" placeholder="이름 입력" onkeyup="inputChk();" autocomplete="off" style="ime-mode:active;">
											<label class="c-form__label" for="dlvryName">이름 입력</label>
										</div>
									</div>
									<div class="c-form">
										<div class="c-form__input-group has-value">
											<div class="c-form__input c-form__input-division has-value">
												<input class="c-input--div3 onlyNum" name="dlvryTelFn" id="dlvryTelFn" type="tel" maxlength="3" placeholder="숫자입력" value="" onkeyup="inputChk();" autocomplete="off"> <span>-</span>
												<input class="c-input--div3 onlyNum" name="dlvryTelMn" id="dlvryTelMn" type="tel" maxlength="4" placeholder="숫자입력" value="" onkeyup="inputChk();" autocomplete="off"> <span>-</span>
												<input class="c-input--div3 onlyNum" name="dlvryTelRn" id="dlvryTelRn" type="tel" maxlength="4" placeholder="숫자입력" value="" onkeyup="inputChk();" autocomplete="off">
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
												<button class="c-button c-button--sm c-button--underline" id="dlvryChk" style="display:none;">바로배송(당일 퀵) 지역 조회</button>
												<button class="c-button c-button--sm c-button--underline _btnAddr">우편번호 찾기</button>
											</div>
											<input class="c-input u-co-gray-9" type="text" id="dlvryAddr" placeholder="주소" title="주소 입력" readonly>
											<input class="c-input u-co-gray-9" type="text" id="dlvryAddrDtl" placeholder="상세주소" title="상세 주소 입력" readonly>
										</div>
									</div>
									<div class="c-form nowArea1" style="display:none;">
										<div class="c-form__select has-value">
											<select class="c-select" id="orderReqMsg" name="orderReqMsg">
												<option value="0" selected>배송 메시지를 선택해 주세요</option>
												<option value="1">문 앞에 부착 해주세요.</option>
												<option value="2">우편함에 넣어주세요.</option>
												<option value="3">직접 받을께요.(부재시 문 앞 부착)</option>
												<option value="4">직접입력</option>
											</select>
											<label class="c-form__label" for="orderReqMsg">배송 요청사항(선택)</label>
										</div>
									</div>
									<div class="c-form" id="dlvryMemoWrap">
										<div class="c-textarea-wrap">
											<textarea class="c-textarea" placeholder="배송 요청사항(선택)" name="dlvryMemo" id="dlvryMemo" maxlength="50" onkeyup="inputChk();" autocomplete="off"></textarea>
											<div class="c-textarea-wrap__sub">
												<span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span id="dlvryMemoChk">0/50</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="req-self-form-group nowArea2" style="display:none;">
							<div class="req-self-form">
								<div class="req-self-form__title">
									<h4>공동현관 출입방법</h4>
								</div>
								<div class="req-self-form__form">
									<div class="c-form">
										<div class="nowArea2 c-form__select has-value" style="display:none;">
											<select class="c-select" name="exitPw" id="exitPw">
												<option value="0">선택 안함</option>
												<option value="1">비밀번호</option>
												<option value="2">경비실 호출</option>
												<option value="3">자유출입가능</option>
											</select>
											<label class="c-form__label" for="exitPw">공동현관 출입방법</label>
										</div>
										<input class="nowArea2Sub c-input u-mt--8" type="tel" placeholder="비밀번호 입력" title="비밀번호 입력" id="homePw" name="homePw" value="" maxlength="10" style="display:none;" onkeyup="inputChk();">
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
						<button type="button" class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF04');">
							<span class="c-hidden">상세보기</span>
							<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
						</button>
					</div>
				</div>
				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary is-disabled" id="saveBtn">결제 후 신청완료</button>
				</div>
			</div>
		</div>


		<a href="javascript:void(0);" id="modalDirectBtn" data-dialog-trigger="#modalDirect"></a>
		<div class="c-modal" id="modalDirect" role="dialog" tabindex="-1" aria-describedby="#modalDirectNotTime">
			<div class="c-modal__dialog" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header u-hide">
						<h1 class="c-modal__title" id="modalDirectNotTime">바로배송(당일 퀵) 가능시간이 아닙니다.</h1>
					</div>
					<div class="c-modal__body u-ta-center">
						<div class="c-alert--time">
							<p class="c-text-label c-text-label--type2 c-text-label--red">바로배송(당일 퀵) 가능시간</p>
							<p>09:00 ~ 22:00</p>
						</div>
						<p class="c-text c-text--type3 u-mt--16">
							바로배송(당일 퀵) 가능시간이 아닙니다. <br>택배를 이용해주세요.
						</p>
					</div>
					<div class="c-modal__footer u-pt--0 u-pb--24">
						<button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
					</div>
				</div>
			</div>
		</div>


		<div class="c-modal" id="deliveryDialog" role="dialog" tabindex="-1" aria-describedby="#deliveryDialogTitle">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="deliveryDialogTitle">바로배송(당일 퀵)이란</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body overflow-x-hidden">
						<div class="delivery-image u-mt--24">
							<img src="/resources/images/mobile/content/img_usim_delivery.png" alt="Quick 바로배송(당일 퀵)유심 평균 배송시간 2시간 주말에도 당일 배송!">
						</div>
						<h3 class="c-heading c-heading--type3 u-mt--40">바로배송(당일 퀵) 방법</h3>
						<div class="delivery-info">
							<dl>
								<dt class="c-text-box c-text-box--blue c-text--area">주문</dt>
								<dd>
									09:00 ~ 22:00
									<span class="c-text c-text--type4 u-co-gray u-ml--4">(무료배송)</span>
								</dd>
							</dl>
							<dl>
								<dt class="c-text-box c-text-box--blue c-text--area">배송일</dt>
								<dd>
									매일
									<span class="c-text c-text--type4 u-co-gray u-ml--4">(휴무일 없음)</span>
								</dd>
							</dl>
							<dl>
								<dt class="c-text-box c-text-box--blue c-text--area">서비스 지역</dt>
								<dd>
									서울 / 수도권 및 광역시 / 제주도
									<p class="c-text c-text--type4 u-co-gray u-mt--8">세종/천안/공주/청주/충주/동해/삼척/속초/원주/창원/김해/순천/목포</p>
								</dd>
							</dl>
						</div>
						<hr class="c-hr c-hr--type1 u-mt--40 c-expand">
						<h3 class="c-heading c-heading--type3 u-mt--0">
							바로배송(당일 퀵) 가능지역을 <br>확인해주세요
						</h3>
						<div class="delivery-image c-expand">
							<img src="/resources/images/mobile/content/delivery_area_01.png" alt="바로배송(당일 퀵) 지역 조회 버튼을 눌러주세요.">
							<img src="/resources/images/mobile/content/delivery_area_02.png" alt="도로명 또는 지번 검색 후 주소를 입력해주세요.">
						</div>
						<hr class="c-hr c-hr--type2">
						<b class="c-text c-text--type3 u-mb--16">
							<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
						</b>
						<ul class="c-text-list c-bullet c-bullet--dot">
							<li class="c-text-list__item u-co-gray">일부 지역은 서비스가 제외되오니 바로배송(당일 퀵) 가능 지역을 꼭 확인하세요</li>
							<li class="c-text-list__item u-co-gray">배송 시간은 배송물량 급증, 도로/배송업체 사정 등으로 지연될 수 있습니다.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>