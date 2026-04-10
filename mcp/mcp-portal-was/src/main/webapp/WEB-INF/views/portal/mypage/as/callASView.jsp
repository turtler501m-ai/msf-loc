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
<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="titleAttr">통화품질불량 접수</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/mypage/as/callASView.js?version=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
        <div class="ly-page--title">
          <h2 class="c-page--title">통화품질불량 접수</h2>
        </div>
        <div class="c-row c-row--lg">
            <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
            <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
            <hr class="c-hr c-hr--title">
            <div class="voice-err-info">
                <div class="voice-err-info__list">
                    <h4>통화품질불량 접수란?</h4>
                    <div class="voice-err-info__box">
                        <ul>
                            <li class="voice-err-info__item">특정지역 또는 지역무관하여 통화, 문자, 데이터가 불안정할 경우 통화품질불량을 접수하실 수 있는 서비스입니다.</li>
                            <li class="voice-err-info__item">핸드폰에서 유심칩을 빼신 뒤 다시 장착 후 전원을 2~3회 껐다 켜보시고 안되시면 접수 부탁드립니다.</li>
                            <li class="voice-err-info__item">통화품질 불량 접수 시, 상담원이 접수내용 확인 후 고객님께 전화 드리고 있습니다. 연락가능하신 연락처를 꼭 남겨주세요.</li>
                        </ul>
                        <div class="voice-err-guide">
                            <h4>진행절차</h4>
                            <ul class="voice-err-guide__list">
                                <li class="voice-err-guide__item">통화품질 불량접수 신청</li>
                                <li class="voice-err-guide__item">고객님께서 신청하신 내용 확인 후 상담원이 전화 드리며,<br />상담을 통해 필요할 경우 기사님이 방문하여 점검할 수 있습니다.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="voice-err-content">
                <div class="voice-err-title">
                    <h3>통화품질불량 접수</h4>
                    <hr class="c-hr c-hr--title">
                </div>
                <div class="c-form-wrap u-mt--32">
                    <div class="c-form-group" role="group" aira-labelledby="inpNameTitle">
                        <h4 class="c-group--head" id="inpNameTitle">고객정보</h4>
                        <div class="c-form-row c-col2">
                            <div class="c-form">
                                <input type="text" class="c-input" id="cstmrName" name="cstmrName" value="${userName}" placeholder="이름 입력" maxlength="60" readonly>
                                <label class="c-form__label c-hidden" for="cstmrName">이름</label>
                            </div>
                            <div class="c-form">
                                <input type="tel" class="c-input" id="mobileNo" name="mobileNo" value="${ctn}" placeholder="핸드폰" maxlength="11" readonly>
                                <label class="c-form__label c-hidden" for="mobileNo">핸드폰</label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="errordateTitle">
                        <h4 class="c-group--head" id="errordateTitle">발생일시</h4>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field c-form-field--datepicker">
                                <div class="c-form__input">
                                    <input type="text" class="c-input flatpickr flatpickr-input" id="errorStartDate" name="errorStartDate" placeholder="YYYY-MM-DD" readonly>
                                    <label class="c-form__label" for="errorStartDate">시작일</label>
                                    <span class="c-button">
                                        <span class="c-hidden">날짜선택</span>
                                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                    </span>
                                </div>
                            </div>
                            <div class="c-form-field c-form-field--datepicker">
                                <div class="c-form__input">
                                    <input type="text" class="c-input flatpickr flatpickr-input" id="errorEndDate" name="errorEndDate" placeholder="YYYY-MM-DD" readonly>
                                    <label class="c-form__label" for="errorEndDate">종료일</label>
                                    <span class="c-button">
                                        <span class="c-hidden">날짜선택</span>
                                        <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="errortypeTitle">
                        <h4 class="c-group--head" id="errortypeTitle">증상</h4>
                    </div>
                    <div class="c-checktype-row c-col2">
                        <input type="radio" class="c-radio c-radio--button" id="errorTypeCd01" name="errorTypeCd" value="01" checked="checked">
                        <label class="c-label" for="errorTypeCd01">일시현상</label>
                        <input type="radio" class="c-radio c-radio--button" id="errorTypeCd02" name="errorTypeCd" value="02">
                        <label class="c-label" for="errorTypeCd02">지속현상</label>
                    </div>
                    <div class="c-checktype-row voice-err--type">
                        <input type="checkbox" class="c-checkbox c-checkbox--type3 errorChk" id="errorVoice" name="errorVoice" value="Y" checked="checked">
                        <label class="c-label" for="errorVoice">통화음질 불량</label>
                        <input type="checkbox" class="c-checkbox c-checkbox--type3 errorChk" id="errorCall" name="errorCall" value="Y">
                        <label class="c-label" for="errorCall">통화 수/발신 불가</label>
                        <input type="checkbox" class="c-checkbox c-checkbox--type3 errorChk" id="errorSms" name="errorSms" value="Y">
                        <label class="c-label" for="errorSms">문자 수/발신 불가</label>
                        <input type="checkbox" class="c-checkbox c-checkbox--type3 errorChk" id="errorWifi" name="errorWifi" value="Y">
                        <label class="c-label" for="errorWifi">WiFI 이용 불가</label>
                        <input type="checkbox" class="c-checkbox c-checkbox--type3 errorChk" id="errorData" name="errorData" value="Y">
                        <label class="c-label" for="errorData">무선데이터 이용 불가</label>
                    </div>
                </div>
                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="errorLocTitle">
                        <h4 class="c-group--head" id="errorLocTitle">증상발생지(주 거주지)</h4>
                    </div>
                    <div class="c-checktype-row c-col2">
                        <input type="radio" class="c-radio c-radio--button" id="errorLocTypeCd01" name="errorLocTypeCd" value="01" checked="checked">
                        <label class="c-label" for="errorLocTypeCd01">지역무관</label>
                        <input type="radio" class="c-radio c-radio--button" id="errorLocTypeCd02" name="errorLocTypeCd" value="02">
                        <label class="c-label" for="errorLocTypeCd02">특정지역</label>
                    </div>
                    <div class="c-form-row c-col2">
                        <div class="c-form">
                            <div class="c-input-wrap">
                                <input type="text" class="c-input" id="cstmrPost" name="cstmrPost" value="" placeholder="우편번호" title="우편번호 입력" readonly>
                                <label class="c-form__label c-hidden" for="cstmrPost">우편번호</label>
                                <button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/addPopup.do','','1');">우편번호찾기</button>
                            </div>
                        </div>
                        <div class="c-form">
                            <div class="c-input-wrap">
                                <input type="text" class="c-input" id="cstmrAddr" name="cstmrAddr" value="" placeholder="주소" readonly>
                                <label class="c-form__label c-hidden" for="cstmrAddr">주소</label>
                            </div>
                        </div>
                    </div>
                    <div class="c-form u-mt--16">
                        <input type="text" class="c-input" id="cstmrAddrDtl" name="cstmrAddrDtl" value="" placeholder="상세주소" readonly>
                        <label class="c-label c-hidden" for="cstmrAddrDtl">상세주소</label>
                    </div>
                    <div class="voice-err-info__box--type2">
                        <p class="bullet">상세주소를 모르실 경우 인근 가장 가까운 주소로 입력 부탁드립니다.</p>
                    </div>
                </div>

                <div class="c-form-wrap">
                    <div class="c-form-group" role="group" aira-labelledby="regNameTitle">
                        <h4 class="c-group--head" id="regNameTitle">신청자</h4>
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input">
                                    <input type="text" class="c-input" id="regNm" name="regNm" value="" placeholder="이름 입력" maxlength="60">
                                    <label class="c-form__label" for="regNm">이름</label>
                                </div>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input-group u-mt--0">
                                    <div class="c-form__input c-form__input-division">
                                        <input type="text" class="c-input--div3 numOnly" id="regMobileNo1" name="regMobileNo1" value="" placeholder="숫자입력" maxlength="3" title="휴대폰 첫자리">
                                        <span>-</span>
                                        <input type="text" class="c-input--div3 numOnly" id="regMobileNo2" name="regMobileNo2" value="" placeholder="숫자입력" maxlength="4" title="휴대폰 중간자리">
                                        <span>-</span>
                                        <input type="text" class="c-input--div3 numOnly" id="regMobileNo3" name="regMobileNo3" value="" placeholder="숫자입력" maxlength="4" title="휴대폰 뒷자리">
                                        <label class="c-form__label" for="regMobileNo1">연락처</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-button-wrap">
                    <button class="c-button c-button--lg c-button--primary c-button--w460" id="saveBtn">신청</button>
                </div>
            </div>

        </div>
    </div>

    </t:putAttribute>
</t:insertDefinition>