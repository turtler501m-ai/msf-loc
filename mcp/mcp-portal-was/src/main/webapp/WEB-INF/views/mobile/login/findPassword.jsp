<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/join/joinCommon.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/login/findPassword.js?version=03-11-17"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
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

            <div class="ly-page-sticky">
                <h2 class="ly-page__head">아이디 / 비밀번호찾기<button class="header-clone__gnb"></button></h2>
            </div>

            <div class="c-tabs c-tabs--type2" style="display:none;">
                <div class="c-tabs__list sticky-header c-expand" data-tab-list="">
                    <button class="c-tabs__button" data-tab-header="#tab1-panel" id="idTab">아이디 찾기</button>
                    <button class="c-tabs__button" data-tab-header="#tab2-panel" id="passTab">비밀번호 찾기</button>
                </div>

                <div class="c-tabs__panel" id="tab1-panel">
                    <!-- 본인인증 전-->
                    <h3 class="c-heading c-heading--type1 searchIdDiv" style="display:none;">본인인증 방법 선택</h3>
                    <div class="c-form u-mb--32 searchIdDiv" style="display:none;">
                        <div class="c-check-wrap">
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="findIdBtn" type="radio" name="radCertification">
                                <label class="c-label" for="findIdBtn"> <i class="c-icon c-icon--phone" aria-hidden="true"></i> <span>휴대폰</span></label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="findIdCredit" type="radio" name="radCertification">
                                <label class="c-label" for="findIdCredit"> <i class="c-icon c-icon--card" aria-hidden="true"></i> <span>신용카드</span></label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="findIdIpin" type="radio" name="radCertification">
                                <label class="c-label" for="findIdIpin"> <i class="c-icon c-icon--ipin" aria-hidden="true"></i> <span>아이핀</span>
                                </label>
                            </div>
                        </div>
                    </div>

                    <ul class="c-text-list c-bullet c-bullet--dot c-border-top searchIdDiv" style="display:none;">
                        <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                    </ul>
                    <!-- //-->

                    <!-- 본인인증 후-->
                    <p class="c-text c-text--type2 u-ta-center searchIdStep1" style="display:none;">
                        고객님의 아이디 <br>
                        <b class="c-text c-text--type1 u-co-mint"><span id="maskId"></span></b>
                    </p>
                    <div class="c-form searchIdStep1" style="display:none;">
                        <div class="c-form__select has-value c-form__select--phone">
                            <select class="c-select" name="selMobileNo" id="selMobileNo">
                                <option value="USER"></option>
                            </select>
                            <label class="c-form__label" for="selA">휴대폰</label>
                            <button type="button" class="c-button c-button--sm c-button--underline" id="sendSmsBtn">아이디 전체 전송</button>
                        </div>
                    </div>
                    <hr class="c-hr c-hr--type2 searchIdStep1" style="display:none;">
                    <p class="c-bullet c-bullet--dot u-co-gray searchIdStep1" style="display:none;">
                        등록하신 휴대폰으로 아이디전체가 발송됩니다.
                        <br>번호변경 등으로 SMS 수신이 불가능 할 경우
                        <br>고객센터(1899-5000)으로 문의하여 주시기 바랍니다.
                    </p>
                    <!-- //-->

                    <!-- 완료-->
                    <div id="searchIdStep3" class="complete" style="display: none">
                        <div class="c-icon c-icon--complete" aria-hidden="true">
                            <span></span>
                        </div>
                        <p class="complete__heading">
                            <b>아이디 전송 완료</b>
                        </p>
                        <p class="complete__text">
                            등록하신 휴대폰 번호로 고객님의 <br>아이디가 전송되었습니다.
                        </p>
                    </div>
                    <!-- //-->
                    <div class="c-button-wrap c-button-wrap--column">
                        <a class="c-button c-button--full c-button--primary" href="/m/loginForm.do">로그인</a>
                    </div>
                </div>

                <div class="c-tabs__panel" id="tab2-panel">
                    <!-- @@@ 비밀번호 찾기 -->
                    <!-- 가입정보 입력 -->
                    <h3 class="c-heading c-heading--type1 searchPwDiv">가입정보 입력</h3>
                    <div class="c-form searchPwDiv">
                        <div class="c-form__input">
                            <input class="c-input" type="text" id="userId" name="userId" onkeyup="nextChk();" autocomplete="off" maxlength="20">
                            <label class="c-form__label" for="inpF">아이디</label>
                        </div>
                    </div>
                    <div class="c-form searchPwDiv">
                        <div class="c-form__input">
                            <input class="c-input" type="text" id="userName" name="userName" onkeyup="nextChk();"  autocomplete="off" maxlength="60">
                            <label class="c-form__label" for="inpF">이름</label>
                        </div>
                    </div>
                    <div class="c-bullet c-bullet--dot u-co-gray searchPwDiv">비밀번호 확인을 위하여 고객님이 가입하신 아이디/이름을 입력해주시기 바랍니다.</div>
                    <!-- //-->
                    <div class="c-button-wrap searchPwDiv">
                        <button type="button" class="c-button c-button--full c-button--primary is-disabled" id="schPwStep1Btn">확인</button>
                    </div>

                    <!-- 본인인증 전-->
                    <h3 class="c-heading c-heading--type1 searchPwStep1" style="display:none;">본인인증 방법 선택</h3>
                    <div class="c-form u-mb--32 searchPwStep1" style="display: none">
                        <div class="c-check-wrap">
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="findPwBtn" type="radio" name="radCertificationPw">
                                <label class="c-label" for="findPwBtn"> <i class="c-icon c-icon--phone" aria-hidden="true"></i> <span>휴대폰</span></label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="findPwCredit" type="radio" name="radCertificationPw">
                                <label class="c-label" for="findPwCredit"> <i class="c-icon c-icon--card" aria-hidden="true"></i> <span>신용카드</span></label>
                            </div>
                            <div class="c-chk-wrap">
                                <input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="findPwIPin" type="radio" name="radCertificationPw">
                                <label class="c-label" for="findPwIPin"> <i class="c-icon c-icon--ipin" aria-hidden="true"></i> <span>아이핀</span></label>
                            </div>
                        </div>
                    </div>

                    <ul class="c-text-list c-bullet c-bullet--dot c-border-top searchPwStep1" style="display:none;">
                        <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                    </ul>
                    <!-- //-->

                    <!-- 본인인증 후-->
                    <p class="c-text c-text-type2 u-ta-center searchPwStep2" style="display:none;">
                        고객님의 아이디 <br> <b class="c-text c-text--type1 u-co-mint" id="dispalyId"></b>
                    </p>
                    <span class="c-form__title" id="inpBB searchPwStep2" style="display:none;">새 비밀번호 입력</span>
                    <div class="c-form__group searchPwStep2" style="display:none;" role="group" aria-labeledby="newPass">
                        <div class="c-form__input" id="pwCheckDiv">
                            <input class="c-input" id="password" type="password" name="" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="20" onkeyup="pwChange();">
                            <label class="c-form__label" for="password">비밀번호</label>
                        </div>
                        <p class="c-form__text" id="pwdOneChk"></p>
                        <div class="c-form__input" id="pwCheckConfirmDiv">
                            <input class="c-input" id="passwordChk" type="password" name="" placeholder="비밀번호를 한번 더 입력해주세요" aria-invalid="true" aria-describedby="newPassChk" maxlength="15" onkeyup="pwConfirm();">
                            <label class="c-form__label" for="passwordChk">비밀번호 확인</label>
                        </div>
                        <p class="c-form__text" id="pwdSndChk"></p>
                    </div>

                    <div class="c-button-wrap searchPwStep2" style="display:none;">
                        <button type="button" class="c-button c-button--full c-button--primary is-disabled" id="initBtn">취소</button>
                        <button type="button" class="c-button c-button--full c-button--primary is-disabled" id="searchPwStep2Btn">확인</button>
                    </div>

                    <!-- //-->

                    <!-- 완료-->
                    <div id="searchPwStep3" class="complete" style="display: none">
                        <div class="c-icon c-icon--complete" aria-hidden="true">
                            <span></span>
                        </div>
                        <p class="complete__heading">
                            <b>비밀번호 변경 완료</b>
                        </p>
                        <p class="complete__text">
                            비밀번호가 변경되었습니다. <br>변경한 비밀번호로 로그인해 주시기 바랍니다.
                        </p>
                        <div class="c-button-wrap">
                            <a class="c-button c-button--full c-button--primary" href="/m/loginForm.do">로그인</a>
                        </div>
                    </div>
                    <!-- //-->
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>