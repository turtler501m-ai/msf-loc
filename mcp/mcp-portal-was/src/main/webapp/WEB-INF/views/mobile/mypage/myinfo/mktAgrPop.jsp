<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<script src="../../resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/resources/js/mobile/mypage/myinfo/mktAgrPop.js?ver=${jsver}"></script>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="find-cvs-modal__title">정보/광고 수신동의</h2>
		<button class="c-button" data-dialog-close id="closePop">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>

	<input type="hidden" id="orgPersonalInfoCollectAgree" name="volutionAgree" dtl-cd="personalInfoCollectAgree" value="${agreeInfo.personalInfoCollectAgree}"/>
	<input type="hidden" id="orgAgrYn" name="volutionAgree" dtl-cd="mrkAgrYn" value="${agreeInfo.agrYn}"/>
	<input type="hidden" id="orgOthersTrnsAgree" name="volutionAgree" dtl-cd="othersTrnsAgree" value="${agreeInfo.othersTrnsAgree}"/>
	<input type="hidden" id="orgOthersTrnsKtAgree" name="volutionAgree" dtl-cd="othersTrnsKtAgree" value="${agreeInfo.othersTrnsKtAgree}"/>
	<input type="hidden" id="orgOthersAdReceiveAgree" name="volutionAgree" dtl-cd="othersAdReceiveAgree" value="${agreeInfo.othersAdReceiveAgree}"/>

	<input type="hidden" id="personalInfoCollectAgreeTime" name="volutionTm" dtl-cd="personalInfoCollectAgree" value="${agreeInfo.personalInfoCollectAgreeTime}"/>
	<input type="hidden" id="agrYnTime" name="volutionTm" dtl-cd="mrkAgrYn" value="${agreeInfo.agrYnTime}"/>
	<input type="hidden" id="othersTrnsAgreeTime" name="volutionTm" dtl-cd="othersTrnsAgree" value="${agreeInfo.othersTrnsAgreeTime}"/>
	<input type="hidden" id="othersTrnsKtAgreeTime" name="volutionTm" dtl-cd="othersTrnsKtAgree" value="${agreeInfo.othersTrnsKtAgreeTime}"/>
	<input type="hidden" id="othersAdReceiveAgreeTime" name="volutionTm" dtl-cd="othersAdReceiveAgree" value="${agreeInfo.othersAdReceiveAgreeTime}"/>

	<div class="c-modal__body">
		<h3 class="c-heading--fs22">고객 혜택 제공 동의 (선택)</h3>
		<div class="c-accordion c-accordion--nested">
			<div class="c-accordion__box">
				<div class="c-accordion__item">
					<div class="c-accordion__head">
						<div class="c-accordion__check">
							<input class="c-checkbox agreeWrap" id="agreeWrap5" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
							<label class="c-label" for="agreeWrap5">고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의
							</label>
						</div>
						<button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree5">
							<div class="c-accordion__trigger"></div>
						</button>
					</div>
					<div class="c-accordion__panel expand" id="agree5">
						<div class="c-accordion__inside">
							<div class="c-accordion c-accordion--nested__sub">
								<div class="c-accordion__box">
									<div class="c-accordion__item">
										<div class="c-accordion__head">
											<div class="c-accordion__check">
												<input class="c-checkbox c-checkbox--type2 agreeCheck" id="personalInfoCollectAgree" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeClick(this)">
												<label class="c-label" for="personalInfoCollectAgree">${personalInfoCollectAgree.dtlCdNm}
												</label>
											</div>
											<button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree5_1">
												<div class="c-accordion__trigger"></div>
											</button>
										</div>
										<div class="c-accordion__panel expand" id="agree5_1">
											<div class="c-accordion__inside">${personalInfoCollectAgree.unescapedDocContent}</div>
										</div>
									</div>
									<div class="c-accordion__item">
										<div class="c-accordion__head">
											<div class="c-accordion__check">
												<input class="c-checkbox c-checkbox--type2 agreeCheck" id="mrkAgrYn" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeClick(this)" required-agree-id="personalInfoCollectAgree">
												<label class="c-label" for="mrkAgrYn">${mrkAgrYn.dtlCdNm}
												</label>
											</div>
											<button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree5_2">
												<div class="c-accordion__trigger"></div>
											</button>
										</div>
										<div class="c-accordion__panel expand" id="agree5_2">
											<div class="c-accordion__inside">${mrkAgrYn.unescapedDocContent}</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="c-accordion__item">
					<div class="c-accordion__head">
						<div class="c-accordion__check">
							<input class="c-checkbox agreeWrap" id="agreeWrap6" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
							<label class="c-label" for="agreeWrap6">혜택 제공을 위한 제3자 제공 및 광고 수신 동의
							</label>
						</div>
						<button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree6">
							<div class="c-accordion__trigger"></div>
						</button>
					</div>
					<div class="c-accordion__panel expand" id="agree6">
						<div class="c-accordion__inside">
							<div class="c-accordion c-accordion--nested__sub">
								<div class="c-accordion__box">
									<div class="c-accordion__item">
										<div class="c-accordion__head">
											<div class="c-accordion__check">
												<input class="c-checkbox c-checkbox--type2 agreeWrap" id="agreeWrap61" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeWrapClick(this)">
												<label class="c-label" for="agreeWrap61">${othersTrnsAllAgree.dtlCdNm}
												</label>
											</div>
											<button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree6_1">
												<div class="c-accordion__trigger"></div>
											</button>
										</div>
										<div class="c-accordion__panel expand" id="agree6_1">
											<div class="c-accordion__inside" id="othersTrnsAgreesBox">${othersTrnsAllAgree.unescapedDocContent}</div>
										</div>
									</div>
									<div class="c-accordion__item">
										<div class="c-accordion__head">
											<div class="c-accordion__check">
												<input class="c-checkbox c-checkbox--type2 agreeCheck" id="othersAdReceiveAgree" name="agreeChk" type="checkbox" onclick="handleOptionalAgreeClick(this)" required-agree-id="agreeWrap5">
												<label class="c-label" for="othersAdReceiveAgree">${othersAdReceiveAgree.dtlCdNm}
												</label>
											</div>
											<button class="c-accordion__button" type="button" aria-expanded="false" data-acc-header="#agree6_2">
												<div class="c-accordion__trigger"></div>
											</button>
										</div>
										<div class="c-accordion__panel expand" id="agree6_2">
											<div class="c-accordion__inside">${othersAdReceiveAgree.unescapedDocContent}</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="c-modal__footer">
		<div class="c-button-wrap">
			<button class="c-button c-button--full c-button--primary" id="setMktArgBtn">확인</button>
		</div>
	</div>
</div>

<input type="hidden" id="contractNum" name="contractNum" value="${contractNum}">
<input type="hidden" id="mspMrktAgrYn" name="mspMrktAgrYn" value="${mspMrktAgrYn}">