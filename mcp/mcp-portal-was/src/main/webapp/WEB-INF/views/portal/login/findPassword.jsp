<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/portal/join/joinCommon.js"></script>
        <script type="text/javascript" src="/resources/js/portal/login/findPassword.js?version=03-11-17"></script>
        <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">

            <input type="hidden" id="tabId" value="${tabId}" />
            <input type="hidden" id="name" name="name" value="" />
            <input type="hidden" id="birthday" name="birthday" value="" />
            <input type="hidden" id="authType" name="authType" value="" />
            <input type="hidden" id="idPwSel" name="idPwSel" value="" />
            <input type="hidden" id="mapping" name="mapping" value="${mapping}" />
            <input type="hidden" id="pin" name="pin" value="" />
            <input type="hidden" id="isChkPw" name="isChkPw" value="N" />
            <input type="hidden" id="isNice" name="isNice" value="N" />

            <div class="ly-page--title">
                <h2 class="c-page--title">아이디/비밀번호 찾기</h2>
            </div>
            <div class="c-tabs c-tabs--type1" data-ui-tab style="display:none;">
                <div class="c-tabs__list" role="tablist">
                    <button class="c-tabs__button" role="tab" aria-controls="tab1-panel" aria-selected="false" tabindex="-1" id="idTab">아이디 찾기</button>
                    <button class="c-tabs__button" role="tab" aria-controls="tab2-panel" aria-selected="false" tabindex="-1" id="passTab">비밀번호 찾기</button>
                </div>
            </div>

            <div class="c-tabs__panel" id="tab1-panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                <div class="c-row c-row--sm searchIdDiv" style="display:none;">
                    <h3 class="c-heading--fs22 c-heading--bold u-ta-left u-mt--80" id="radCertificationTitleA">본인인증 방법 선택</h3>
                    <div class="c-form-wrap u-mt--48 searchIdDiv">
                        <div class="c-form-group" role="group" aira-labelledby="radCertificationTitleA">
                            <div class="c-checktype-row c-col3">
                                <!--[2022-01-04] .c-radio--button--icon2 클래스 삭제-->
                                <input class="c-radio c-radio--button c-radio--button--type2" id="findIdBtn" type="radio" name="radCertification">
                                <label class="c-label" for="findIdBtn">
                                    <i class="c-icon c-icon--phone" aria-hidden="true"></i> <span>휴대폰</span>
                                </label>
                                <!--[2022-01-04] .c-radio--button--icon2 클래스 삭제-->
                                <input class="c-radio c-radio--button c-radio--button--type2" id="findIdCredit" type="radio" name="radCertification">
                                <label class="c-label" for="findIdCredit">
                                    <i class="c-icon c-icon--card" aria-hidden="true"></i> <span>신용카드</span>
                                </label>
                                <!--[2022-01-04] .c-radio--button--icon2 클래스 삭제-->
                                <input class="c-radio c-radio--button c-radio--button--type2" id="findIdIpin" type="radio" name="radCertification">
                                <label class="c-label" for="findIdIpin">
                                    <i class="c-icon c-icon--ipin" aria-hidden="true"></i> <span>아이핀</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot c-border-top searchIdDiv">
                        <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                    </ul>
                </div>

                <!--[2022-01-07] 센터정렬 추가 클래스-->
                <div class="c-row c-row--sm u-ta-center searchIdStep1" style="display:none;">
                    <h3 class="c-heading--fs24 u-mb--48">고객님의 아이디</h3>
                    <p class="c-text--fs28 c-text--bold u-co-mint" id="maskId"></p>
                    <div class="c-form u-mt--56">
                        <div class="c-form__select has-value">
                            <select class="c-select with-btn" name="selMobileNo" id="selMobileNo">
                                <option value="USER"></option>
                            </select>
                            <label class="c-form__label" for="selMobileNo">휴대폰</label>
                            <button class="c-button c-button--sm c-button--underline" id="sendSmsBtn">아이디 전체 전송</button>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16 u-ta-left">
                        <li class="c-text-list__item u-co-gray">등록하신 휴대폰으로 아이디전체가 발송됩니다. 번호변경 등으로 SMS 수신이 불가능 할 경우 고객센터(1899-5000)으로 문의하여 주시기 바랍니다.</li>
                    </ul>
                </div>
                <div class="c-row c-row--sm u-ta-center" id="searchIdStep3" style="display:none;">
                    <!---->
                    <!-- 아이디 전송  완료 후 =================================-->
                    <div class="complete u-mt--80 u-ta-center">
                        <div class="c-icon c-icon--complete" aria-hidden="true">
                            <span></span>
                        </div>
                        <p class="c-heading--fs24 u-mt--32">아이디 전송 완료</p>
                        <!-- 일반회원 가입완료-->
                        <p class="u-co-gray-8 u-mt--16">등록하신 휴대폰 번호로 고객님의 아이디가 전송되었습니다</p>
                    </div>
                    <!-- 아이디 전송 완료 후 끝 ==============================-->
                    <button class="c-button c-button--full c-button--primary u-mt--56" onclick="javascript:location.href='/loginForm.do';">로그인</button>

                    <a class="c-button c-button--underline u-mt--40 u-co-black" href="/findPassword.do?tabId=p">비밀번호 찾기 바로가기</a>
                </div>
            </div>
            <!---->
            <!-- 비밀번호 찾기-->
            <div class="c-tabs__panel" id="tab2-panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
                <div class="c-row c-row--sm searchPwDiv">
                    <h3 class="c-heading--fs22 c-heading--bold u-ta-left">가입정보 입력</h3>
                    <p class="u-ta-left u-co-gray-8 u-mt--20">
                        비밀번호 확인을 위하여 고객님이 <br>가입하신 아이디/이름을 입력해주시기 바랍니다.
                    </p>
                    <div class="c-form-wrap u-mt--48">
                        <div class="c-form">
                            <label class="c-label c-hidden" for="userId">아이디 입력</label>
                            <input class="c-input" id="userId" name="userId" onkeyup="nextChk();" autocomplete="off" maxlength="20" type="text" placeholder="아이디">
                        </div>
                        <div class="c-form">
                            <label class="c-label c-hidden" for="userName">이름 입력</label>
                            <input class="c-input" id="userName" name="userName" onkeyup="nextChk();"  autocomplete="off" maxlength="60" type="text" placeholder="이름">
                        </div>
                    </div>
                    <button class="c-button c-button--full c-button--primary u-mt--56 is-disabled" id="schPwStep1Btn">확인</button>
                    <!---->
                    <!-- 비밀번호 찾기 - 본인인증 방법 선택-->
                </div>

                <div class="c-row c-row--sm searchPwStep1" style="display:none;">
                    <h3 class="c-heading--fs22 c-heading--bold u-ta-left u-mt--80" id="radCertificationTitleB">본인인증 방법 선택</h3>
                    <div class="c-form-wrap u-mt--48">
                        <div class="c-form-group" role="group" aira-labelledby="radCertificationTitleB">
                            <div class="c-checktype-row c-col3">
                                <!--[2022-01-04] .c-radio--button--icon2 클래스 삭제-->
                                <input class="c-radio c-radio--button c-radio--button--type2" id="findPwBtn" type="radio" name="radCertificationPw">
                                <label class="c-label" for="findPwBtn">
                                    <i class="c-icon c-icon--phone" aria-hidden="true"></i> <span>휴대폰</span>
                                </label>
                                <!--[2022-01-04] .c-radio--button--icon2 클래스 삭제-->
                                <input class="c-radio c-radio--button c-radio--button--type2" id="findPwCredit" type="radio" name="radCertificationPw">
                                <label class="c-label" for="findPwCredit">
                                    <i class="c-icon c-icon--card" aria-hidden="true"></i> <span>신용카드</span>
                                </label>
                                <!--[2022-01-04] .c-radio--button--icon2 클래스 삭제-->
                                <input class="c-radio c-radio--button c-radio--button--type2" id="findPwIPin" type="radio" name="radCertificationPw">
                                <label class="c-label" for="findPwIPin">
                                    <i class="c-icon c-icon--ipin" aria-hidden="true"></i> <span>아이핀</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot c-border-top searchPwStep1" style="display:none;">
                        <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                    </ul>
                </div>

                <div class="c-row c-row--sm  u-ta-center searchPwStep2" style="display:none;">
                    <!---->
                    <!-- 새 비밀번호 입력-->
                    <h3 class="c-heading--fs24 u-mb--48">고객님의 아이디</h3>
                    <p class="c-text--fs28 c-text--bold u-co-mint" id="dispalyId"></p>
                </div>
                <div class="c-row c-row--sm  u-ta-center searchPwStep2" style="display:none;">
                    <div class="c-form-wrap searchPwStep2" style="display:none;">
                        <div class="c-form-group" role="group" aira-labelledby="newPasswordInput">
                            <h3 class="c-group--head" id="newPasswordInput">새비밀번호 입력</h3>
                            <div class="c-form-field">
                                <div class="c-form__input" id="pwCheckDiv">
                                    <input class="c-input" id="password" type="password" placeholder="10~15자리 영문/숫자/특수기호 조합" aria-invalid="true" aria-describedby="pwdOneChk" maxlength="20" onkeyup="pwChange();">
                                    <label class="c-form__label" for="password">비밀번호</label>
                                </div>
                                <p class="c-form__text" id="pwdOneChk"></p>
                                <!-- value값을 지정할 때는 .has-value를 이용해주세요.-->
                                <div class="c-form__input" id="pwCheckConfirmDiv">
                                    <input class="c-input" id="passwordChk" type="password" placeholder="10~15자리 영문/숫자/특수기호 조합" aria-invalid="true" aria-describedby="pwdSndChk" maxlength="20" onkeyup="pwConfirm();">
                                    <label class="c-form__label" for="passwordChk">비밀번호 확인</label>
                                </div>
                                <p class="c-form__text" id="pwdSndChk"></p>
                            </div>
                        </div>
                    </div>

                    <div class="c-button-wrap u-mt--56 searchPwStep2" style="display:none;">
                        <!-- disbled 상태를 설정하려면 .is-disabled 추가-->
                        <button class="c-button c-button--full c-button--primary" type="button" id="searchPwStep2Btn">확인</button>
                    </div>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>




