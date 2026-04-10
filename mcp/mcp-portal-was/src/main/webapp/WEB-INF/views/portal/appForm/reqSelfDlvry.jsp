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

<t:insertDefinition name="layoutGnbDefaultTitle">

	<t:putAttribute name="titleAttr">다이렉트몰 구매하기</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">

		<script type="text/javascript" src="/resources/js/common/validator.js?version=24.12.17"></script>
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=24.12.17"></script>
		<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=24.12.17"></script>
		<script type="text/javascript" src="/resources/js/portal/appForm/reqSelfDlvry.js?version=25.09.16"></script>
		<script type="text/javascript" src="/resources/js/portal/popup/niceAuth.js?version=25.09.16"></script>
		<script src="${smartroScriptUrl}?version=${today}"></script>
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
			<input type="hidden" name="PayMethod"	   id="PayMethod"	   value="CARD"/>
			<input type="hidden" name="GoodsCnt"	   id="GoodsCnt"	   value=""/>
			<input type="hidden" name="GoodsName"	   id="GoodsName"	   value=""/> 	<!-- 거래 상품명 -->
			<input type="hidden" name="Amt"			     id="Amt"		       value=""/> 	<!-- 설정한 Amt -->
			<input type="hidden" name="Moid"		     id="Moid"		     value=""/> 	<!-- 주문번호 특수문자 포함 불가 -->
			<input type="hidden" name="Mid"			     id="Mid"		       value=""/> 	<!-- 설정한 Mid -->
			<input type="hidden" name="ReturnUrl"	   id="ReturnUrl"	   value=""/> 	<!-- 가맹점 ReturnUrl 설정 -->
			<input type="hidden" name="StopUrl"		   id="StopUrl"	     value=""/> 	<!-- 결제중단 URL -->
			<input type="hidden" name="BuyerName"	   id="BuyerName"	   value=""/> 	<!-- 구매자명 -->
			<input type="hidden" name="BuyerTel"	   id="BuyerTel"	   value=""/> 	<!-- 전화번호 -->
			<input type="hidden" name="BuyerEmail"	 id="BuyerEmail"	 value=""/> 	<!-- 이메일 -->
			<input type="hidden" name="MallIp"		   id="MallIp"		   value=""/>
			<input type="hidden" name="VbankExpDate" id="VbankExpDate" value=""/>   <!-- 가상계좌 이용 시 필수 -->
			<input type="hidden" name="EncryptData"	 id="EncryptData"  value=""/> 	<!-- 위/변조방지 HASH 데이터 -->
			<input type="hidden" name="GoodsCl"		   id="GoodsCl"		   value="1"/> 	<!-- 0: 컨텐츠, 1: 실물(가맹점 설정에 따라 셋팅, 휴대폰 결제 시 필수)-->
			<input type="hidden" name="EdiDate"		   id="EdiDate"	     value=""/> 	<!-- 설정한 EdiDate -->
			<input type="hidden" name="TaxAmt"		   id="TaxAmt"		   value=""/> 	<!-- 과세 : 부가세 직접계산 가맹점 필수, 숫자만 가능, 문장부호 제외  -->
			<input type="hidden" name="TaxFreeAmt"	 id="TaxFreeAmt"	 value=""/> 	<!-- 비과세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
			<input type="hidden" name="VatAmt"		   id="VatAmt"		   value=""/> 	<!-- 부가세 : 부가세 직접계산 가맹점 필수,숫자만 가능, 문장부호 제외 -->
			<input type="hidden" name="MallReserved" id="MallReserved" value=""/> 	<!-- 상점예비필드 -->
		</form>

		<!-- PC 연동의 경우에만 아래 승인폼이 필요합니다. (Mobile은 제외)-->
		<form id="approvalForm" name="approvalForm" method="post">
			<input type="hidden" name="Tid"/>
			<input type="hidden" name="TrAuthKey"/>
			<input type="hidden" name="MallReserved"/>
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
			<div class="ly-page--title">
				<h2 class="c-page--title">유심구매</h2>
			</div>

			<div class="req-self-content">
				<div class="req-self-content-wrap">

					<div class="req-self-wrap">
						<h3 class="req-self-title">주문 정보</h3>
						<div class="req-self-form-wrap">
							<div class="req-self-form-group">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4 id="radCertificationTitle">유심 선택</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form-wrap">
											<div class="c-form-group" role="group" aira-labelledby="radCertificationTitle">
												<div class="c-checktype-row c-col3">
													<c:forEach items="${usimProdIdList}" var="usimProdInfo" varStatus="eachIndex">
														<input class="c-radio c-radio--button c-radio--button--type1" type="radio" name="usimProdId" id="usimProdId0${eachIndex.index+1}" value="${usimProdInfo.dtlCd}" title="${eachIndex.index+1}" amount="${usimProdInfo.expnsnStrVal1}" data-prodNm="${usimProdInfo.dtlCdNm}" data-indcOdrg="${usimProdInfo.indcOdrg}" data-dtlCdDesc="${usimProdInfo.dtlCdDesc}">
														<label class="c-label" for="usimProdId0${eachIndex.index+1}">
															<c:if test="${usimProdInfo.dtlCd eq '01'}"><i class="c-icon c-icon--phone" aria-hidden="true"></i></c:if>
															<c:if test="${usimProdInfo.dtlCd eq '02'}"><i class="c-icon c-icon--phone-nfc" aria-hidden="true"></i></c:if>
															<c:if test="${usimProdInfo.dtlCd eq '03'}"><i class="c-icon c-icon--phone-5gnfc" aria-hidden="true"></i></c:if>
															<span>${usimProdInfo.dtlCdNm} <fmt:formatNumber value="${usimProdInfo.expnsnStrVal1}" pattern="#,###" />원</span>
														</label>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="req-self-form--type2">
									<div class="req-self-form__bullet">
										<ul>
											<li id="moNfcPoss"></li>
										</ul>
									</div>
								</div>
							</div>


							<div class="req-self-form-group">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4>구매 수량</h4>
									</div>
									<div class="req-self-form__form">
										<div class="count-wrap">
                      <span class="c-counter">
                        <button class="c-button c-button--xsm c-button--gray minus">
                          <span class="sr-only">수량 1 감소</span>-
                        </button>
                        <input type="number" min="1" max="${usimBuyLimit1}" step="1" title="현재 수량" readonly id="reqBuyQnty">
                        <button class="c-button c-button--xsm c-button--primary plus">
                          <span class="sr-only">수량 1 증가</span>+
                        </button>
                      </span>
											<span class="c-price">
                        <b id="usimPrice">0</b> 원
                      </span>
										</div>
									</div>
								</div>
								<div class="req-self-form--type2">
									<div class="req-self-form__bullet">
										<ul>
											<li>택배는 최대 <span id="limitSpan">${usimBuyLimit1}</span>개까지, 바로배송(당일 퀵)은 주문 당 한 개의 유심만 구매하실 수 있습니다.</li>
										</ul>
									</div>
								</div>
							</div>

							<div class="req-self-form-group">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4>주문자 정보</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form c-form--type2">
											<div class="c-input-wrap">
												<c:if test="${not empty userName}">
													<input class="c-input" id="orderName" type="text" placeholder="이름 입력" value="${userName}" onkeyup="inputChk();" readonly>
												</c:if>
												<c:if test="${empty userName}">
													<input class="c-input" id="orderName" type="text" placeholder="이름 입력" maxlength="20" onkeyup="inputChk();">
												</c:if>
												<label class="c-label sr-only" for="orderName">주문자 정보</label>
												<button title = "새 창 열림" class="c-button oder-name" id="btnSmsAuth">휴대폰 인증</button>
											</div>
										</div>
									</div>
								</div>

								<div class="req-self-form--type2 ">
									<div class="req-self-form__bullet">
										<ul>
											<c:if test="${userName ne null and !empty userName }">
											<li>다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</li>
											</c:if>
											<li>만 14세 미만 사용자는 법정대리인의 동의 후 결제를 진행할 수 있습니다.</li>
										</ul>
									</div>
								</div>
							</div>

							<div class="req-self-form-group" id="minorAgentWrap" style="display: none;">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4>법정대리인 동의</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form">
											<input class="c-checkbox" id="minorAgentAgrmYn" type="checkbox" name="minorAgentAgrmYn">
											<label class="c-label u-mr--0 u-mb--0" for="minorAgentAgrmYn">
												<span class="u-co-red">(필수)</span> 본인은 주문자(만 14세 미만)의 법정대리인으로서 주문자의 개인정보 수집∙이용 및 결제에 동의합니다.
											</label>
										</div>
									</div>
								</div>
								<div class="req-self-form req-self-form--type2 c-form c-form--type2">
									<div class="c-input-wrap u-mt--12">
										<input class="c-input" id="minorAgentName" type="text" placeholder="법정대리인 이름 입력" maxlength="20" onkeyup="inputChk();">
										<label class="c-label sr-only" for="minorAgentName">법정대리인 정보</label>
										<button title = "새 창 열림" class="c-button oder-name" id="btnSmsMinorAgentAuth">휴대폰 인증</button>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="req-self-wrap">
						<h3 class="req-self-title">배송지 정보</h3>
						<div class="req-self-form-wrap">
							<div class="req-self-form-group">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4 id="radDeliveryHead">배송유형 선택</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form-wrap">
											<div class="c-form-group" role="group" aira-labelledby="radDeliveryHead">
												<div class="c-checktype-row">
													<input class="c-radio dlvryKind02" name="dlvryKind" id="dlvryKind2" value="02" type="radio">
													<label class="c-label dlvryKind02" for="dlvryKind2">택배</label>
													<input class="c-radio dlvryKind01" name="dlvryKind" id="dlvryKind1" value="01" type="radio">
													<label class="c-label dlvryKind01" for="dlvryKind1">바로배송(당일 퀵)</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="req-self-form--type2">
									<div class="req-self-form__bullet" id="dlvryInfo" style="display:none;">
										<ul class="dlvryType1">
											<li>택배로 신청하실 경우 평일 13시까지 주문 시 당일 출고됩니다.(무료배송)</li>
										</ul>
										<ul class="dlvryType2">
											<li class="link">
												<button class="c-text c-text--underline u-ml--6 u-co-sub-4" data-dialog-trigger="#delivery-modal">바로배송(당일 퀵)이란
													<span class="sr-only">바로배송(당일 퀵) 설명 링크(새창)</span>
												</button>
											</li>
											<li class="u-mt--12">바로배송(당일 퀵)은 매일 오전 09시부터 오후 22시까지 이용 가능합니다. (무료배송/휴무일 없음)</li>
											<li>바로배송(당일 퀵)은 선결제 방식으로 미리 유심비를 결제하며, 결제 후 배송접수 실패 시 결제하신 내역은 영업일 기준 7일 이내 자동 환불 처리 됩니다.</li>
										</ul>
									</div>
								</div>
							</div>

							<div class="req-self-form-group">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4>받는 분</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form-row c-col2">
											<div class="c-form-field">
												<div class="c-form__input u-mt--0">
													<input class="c-input" aria-labelledby="dlvryNameLabel" name="dlvryName" id="dlvryName" maxlength="20" type="text" placeholder="이름 입력" aria-invalid="true" onkeyup="inputChk();" autocomplete="off">
													<label class="c-form__label" for="dlvryName" id="dlvryNameLabel">이름</label>
												</div>
											</div>
											<div class="c-form-field">
												<div class="c-form__input-group u-mt--0">
													<div class="c-form__input c-form__input-division">
														<input class="c-input--div3 onlyNum" aria-labelledby="dlvryTelFnLabel" name="dlvryTelFn" id="dlvryTelFn" type="text" maxlength="3" placeholder="숫자입력" onkeyup="inputChk();nextFocus(this, 3, 'dlvryTelMn');" autocomplete="off"> <span aria-hidden="true">-</span>
														<label class="c-form__label" for="dlvryTelFn" id="dlvryTelFnLabel">연락처</label>
														<input class="c-input--div3 onlyNum" aria-labelledby="dlvryTelMnLabel" name="dlvryTelMn" id="dlvryTelMn" type="text" maxlength="4" placeholder="숫자입력" onkeyup="inputChk();nextFocus(this, 4, 'dlvryTelRn');" autocomplete="off"> <span aria-hidden="true">-</span>
														<label class="c-form__label sr-only" for="dlvryTelMn" id="dlvryTelMnLabel">연락처 중간자리</label>
														<input class="c-input--div3 onlyNum" aria-labelledby="dlvryTelRnLabel" name="dlvryTelRn" id="dlvryTelRn" type="text" maxlength="4" placeholder="숫자입력" onkeyup="inputChk();" autocomplete="off">
														<label class="c-form__label sr-only" for="dlvryTelRn" id="dlvryTelRnLabel">연락처 뒷자리</label>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="req-self-form-group">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4 id="inpAddressHead">배송지</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form-wrap req-self-form__addr addr--type2">
											<div class="c-form-group" role="group" aira-labelledby="inpAddressHead">
												<div class="c-form-row c-col2">
													<div class="c-form">
														<div class="c-input-wrap u-mt--0">
															<label class="c-form__label sr-only" for="dlvryPost" id="dlvryPostLabel">우편번호</label>
															<input class="c-input u-pl--16 u-pr--0" type="text" placeholder="우편번호" id="dlvryPost" aria-labelledby="dlvryPostLabel" readonly>
														</div>
													</div>
													<div class="c-form">
														<div class="c-input-wrap u-mt--0">
															<label class="c-form__label sr-only" for="dlvryAddr" id="dlvryAddrLabel">주소</label>
															<input class="c-input u-co-gray-9" id="dlvryAddr" aria-labelledby="dlvryAddrLabel" type="text" placeholder="주소" readonly>
															<button class="c-button c-button--sm c-button--underline" id="dlvryChk" style="display:none;">바로배송(당일 퀵) 지역 조회</button>
															<button class="c-button c-button--sm c-button--underline _btnAddr">우편번호찾기</button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="req-self-form--type2">
									<div class="c-form u-mt--12">
										<label class="c-label sr-only" for="dlvryAddrDtl" id="dlvryAddrDtlLabel">상세주소</label>
										<input class="c-input u-co-gray-9" aria-labelledby="dlvryAddrDtlLabel" id="dlvryAddrDtl" type="text" placeholder="상세주소" readonly>
									</div>
								</div>
							</div>

							<div class="req-self-form-group u-mt--16">
								<div class="req-self-form" style="align-items: flex-start;">
									<div class="req-self-form__title" style="transform: translate(-0.0625rem, 2.25rem);">
										<h4 id="inputAddressHead">배송 요청사항<span>(선택)</span></h4>
									</div>
									<div style="flex: 1;">
										<div class="req-self-form__form nowArea1" style="display:none;">
											<div class="c-form-wrap">
												<div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
													<div class="c-form-row c-col2">
														<div class="c-form-field">
															<div class="c-form__select">
																<select class="c-select" id="orderReqMsg" name="orderReqMsg">
																	<option value="0" label="배송 메시지를 선택해 주세요" selected>배송 메시지를 선택해 주세요</option>
																	<option value="1" label="문 앞에 부착 해주세요.">문 앞에 부착 해주세요.</option>
																	<option value="2" label="우편함에 넣어주세요.">우편함에 넣어주세요.</option>
																	<option value="3" label="직접 받을께요.(부재시 문 앞 부착)">직접 받을께요.(부재시 문 앞 부착)</option>
																	<option value="4" label="직접입력">직접입력</option>
																</select>
																<label class="c-form__label" for="orderReqMsg">배송 요청사항(선택)</label>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="req-self-form__form" id="dlvryMemoWrap">
											<div class="c-form">
												<div class="c-input-wrap c-input-wrap--textinp">
													<label class="c-label sr-only" for="dlvryMemo" id="dlvryMemoLabel">배송 요청사항</label>
													<input class="c-input" type="text" aria-labelledby="dlvryMemoLabel" placeholder="배송 요청사항 입력" name="dlvryMemo" id="dlvryMemo" maxlength="50" onkeyup="inputChk();" autocomplete="off" style="ime-mode:active;">
													<div class="c-input-wrap__sub">
														<span class="sr-only">입력 된 글자 수/최대 입력 글자 수</span> <span id="dlvryMemoChk">0/50</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>


							<div class="req-self-form-group nowArea2" style="display:none;">
								<div class="req-self-form">
									<div class="req-self-form__title">
										<h4 id="radAccMethHead">공동현관 출입방법</h4>
									</div>
									<div class="req-self-form__form">
										<div class="c-form-wrap">
											<div class="c-form-group" role="group" aira-labelledby="radAccMethHead">
												<div class="c-checktype-row">
													<input class="c-radio" id="radAccMeth0" type="radio" name="exitPw" value="0" checked>
													<label class="c-label" for="radAccMeth0">선택 안함</label>
													<input class="c-radio" id="radAccMeth1" type="radio" name="exitPw" value="1">
													<label class="c-label" for="radAccMeth1">비밀번호</label>
													<input class="c-radio" id="radAccMeth2" type="radio" name="exitPw" value="2">
													<label class="c-label" for="radAccMeth2">경비실호출</label>
													<input class="c-radio" id="radAccMeth3" type="radio" name="exitPw" value="3">
													<label class="c-label" for="radAccMeth3">자유출입가능</label>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="req-self-form--type2 nowArea2Sub" style="display:none;">
									<div class="c-form">
										<div class="c-input-wrap">
											<label class="c-label sr-only" for="homePw">비밀번호 입력</label>
											<input class="c-input" type="text" placeholder="비밀번호 입력" id="homePw" name="homePw" value="" maxlength="10" onkeyup="inputChk();">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="c-agree u-mt--40">
						<div class="c-agree__item">
							<div class="c-agree__inner">
								<button type="button" class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF04');">
									<span class="sr-only">개인정보 수집이용 동의 상세팝업 보기</span>
									<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
								</button>
								<input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree" href="javascript:void(0);">
								<label class="c-label" for="chkAgree">개인정보 수집이용 동의(필수)</label>
							</div>
						</div>
					</div>

					<div class="c-button-wrap u-mt--56">
						<button class="c-button c-button--lg c-button--primary c-button--w460 is-disabled" id="saveBtn">결제 후 신청완료</button>
					</div>
				</div>
			</div>
		</div>

		<div class="c-modal c-modal--md" id="delivery-modal" role="dialog" aria-labelledby="#delivery-modal__title">
			<div class="c-modal__dialog" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h2 class="c-modal__title" id="delivery-modal__title">바로배송(당일 퀵)이란</h2>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="sr-only">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="delivery-image">
							<img src="/resources/images/portal/content/img_usim_delivery.png" alt="Quick 바로배송(당일 퀵)유심 평균 배송시간 2시간 주말에도 당일 배송!">
						</div>
						<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">바로배송(당일 퀵) 방법</h3>
						<div class="delivery-info u-mt--32">
							<dl>
								<dt class="c-text-box c-text-box--blue c-text--area">주문</dt>
								<dd>09:00 ~ 22:00<span class="c-text c-text--fs14 u-co-gray u-ml--6">(무료배송)</span></dd>
							</dl>
							<dl>
								<dt class="c-text-box c-text-box--blue c-text--area">배송일</dt>
								<dd>매일<span class="c-text c-text--fs14 u-co-gray u-ml--6">(휴무일 없음)</span></dd>
							</dl>
							<dl>
								<dt class="c-text-box c-text-box--blue c-text--area">서비스 지역</dt>
								<dd>서울 / 수도권 및 광역시 / 제주도<p class="c-text c-text--fs14 u-co-gray u-mt--8">세종/천안/공주/청주/충주/동해/삼척/속초/원주/창원/김해/순천/목포</p></dd>
							</dl>
						</div>
						<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">바로배송(당일 퀵) 가능지역을 확인해주세요</h3>
						<div class="delivery-image u-mt--32">
							<img src="/resources/images/portal/content/delivery_area_01.png" alt="바로배송(당일 퀵) 지역 조회 버튼을 눌러주세요.">
							<img src="/resources/images/portal/content/delivery_area_02.png" alt="도로명 또는 지번 검색 후 주소를 입력해주세요.">
						</div>
						<hr class="c-hr c-hr--basic u-mt--48 u-mb--24">
						<b class="c-flex c-text c-text--fs15">
							<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
							<span class="u-ml--4">알려드립니다</span>
						</b>
						<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
							<li class="c-text-list__item u-co-gray">일부 지역은 서비스가 제외되오니 바로배송(당일 퀵) 가능 지역을 꼭 확인하세요</li>
							<li class="c-text-list__item u-co-gray">배송 시간은 배송물량 급증, 도로/배송업체 사정 등으로 지연될 수 있습니다.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>
