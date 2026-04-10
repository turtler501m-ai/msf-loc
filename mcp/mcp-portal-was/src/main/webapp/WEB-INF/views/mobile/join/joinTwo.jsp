<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/join/joinTwo.js?version=24-12-17"></script>
        <script type="text/javascript" src="/resources/js/mobile/join/joinCommon.js?version=22-10-12"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content" style="display: none;">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    회원가입
                    <button class="header-clone__gnb"></button>
                </h2>
                <div class="c-indicator">
                    <span style="width: 66.66%"></span>
                </div>
            </div>
            <h3 class="c-heading c-heading--type1">정보입력</h3>
            <p class="c-text c-text--type2 u-co-gray">회원가입을 위해 고객님의 정확한 정보를 입력하여 주세요.</p>

            <form id="inputForm" name="inputForm" method="post">
                <input type="hidden" name="snsAddYn" id="snsAddYn" value="${snsAddYn}" />
                <input type="hidden" name="diVal" id="diVal" value="${diVal}" />
                <input type="hidden" name="snsCd" id="snsCd" value="${snsCd}" />
                <input type="hidden" name="snsId" id="snsId" value="${snsId}" />
                <input type="hidden" name="snsMobileNo" id="snsMobileNo" value="${snsMobileNo}"/>
                <input type="hidden" name="snsEmail" id="snsEmail" value="${snsEmail}"/>
                <input type="hidden" id="checkKid" name="checkKid" value="${checkKid}" />
                <input type="hidden" id="niceHistSeq" name="niceHistSeq"  value="${joinObj.niceHistSeq}" />
                <input type="hidden" id="niceAgentHistSeq" name="niceAgentHistSeq"  value="${joinObj.niceAgentHistSeq}" />
                <input type="hidden" id="isAuthAgent"  value="-1" />
                <c:set var="gender" value="${getInfo.gender}"/>
                <c:set var="birthday" value="${getInfo.birthDate}"/>
                <c:if test="${not empty snsGender}"><c:set var="gender" value="${snsGender}"/></c:if>
                <c:if test="${not empty snsBirthday}"><c:set var="birthday" value="${snsBirthday}"/></c:if>
                <input type="hidden" name="gender" id="gender"  value="${gender}"/>
                <input type="hidden" name="birthday" id="birthday" value="${birthday}"/>
                <input type="hidden" name="clausePriAdFlag" id="clausePriAdFlag" value="${clausePriAdFlag}" />
                <input type="hidden" name="personalInfoCollectAgree" id="personalInfoCollectAgree" value="${personalInfoCollectAgree}" />
                <input type="hidden" name="othersTrnsAgree" id="othersTrnsAgree" value="${othersTrnsAgree}" />
                <input type="hidden" name="minorAgentAgree" id="minorAgentAgree" />
                <input type="hidden" name="minorAgentName" id="minorAgentName" />

                <div class="c-form">
                    <!-- 에러인 경우 has-error클래스 추가-->
                    <div class="c-form__input has-error" id="idCheckDiv">
                        <input type="hidden" id="isPushBtn" name="isPushBtn" value="N" />
                        <input type="hidden" id="isChkPw" value="N" />
                        <input class="c-input" id="userId" name="userId" type="text" placeholder="4~12자의 영문 소문자, 숫자 사용" aria-invalid="true" aria-describedby="error1" maxlength="20" autocomplete="off">
                        <label class="c-form__label" for="userId">아이디</label>
                        <button type="button" class="c-button c-button--sm c-button--underline" id="dupleCheck">중복확인</button>
                    </div>
                    <p class="c-form__text" id="use_ok"></p>
                </div>



                <div class="c-form">
                    <span class="c-form__title" id="inpBB">비밀번호</span>
                    <div class="c-form__group" role="group" aria-labeledby="inpBB">
                        <div class="c-form__input has-error" id="pwCheckDiv">
                            <input class="c-input" id="password" onkeyup="pwChange();" type="password" name="password" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="15">
                            <label class="c-form__label" for="password">비밀번호</label>
                        </div>
                        <p class="c-form__text" id="pwdOneChk"></p>
                        <div class="c-form__input has-error" id="pwCheckConfirmDiv">
                            <input class="c-input" id="passwordChk" onkeyup="pwConfirm();" type="password" name="passwordChk" placeholder="비밀번호를 한번 더 입력해주세요" aria-invalid="true" aria-describedby="error2" maxlength="15">
                            <label class="c-form__label" for="passwordChk">비밀번호 확인</label>
                        </div>
                        <p class="c-form__text" id="pwdSndChk"></p>
                    </div>
                </div>

                <div class="c-form c-form--lg">
                    <div class="c-form__input has-value">
                        <input class="c-input onlyNum" id="mobileNo" type="tel" name="mobileNo" placeholder="숫자만 입력해주세요" onkeyup="nextStepChk();" maxlength="11" value="${getInfo.sMobileNo}" readonly>
                        <label class="c-form__label" for="mobileNo">휴대폰</label>
                    </div>
                </div>

                <div class="c-form">
                    <div class="c-form__input has-error" id="emailCheckDiv">
                        <input class="c-input" id="email" type="email" name="email" placeholder="예: ktm@ktm.com" aria-invalid="true" aria-describedby="error3" onkeyup="emailValiChk();" maxlength="50">
                        <label class="c-form__label" for="email">이메일</label>
                    </div>
                    <p class="c-form__text" id="error4"></p>
                </div>

            </form>
            <c:if test="${checkKid eq 'Y' }">
                <div class="c-form c-form--lg">
                    <div class="c-form__group" role="group"aria-labeledby="inpG">
                        <div class="c-input-wrap">
                            <input class="c-input" name="inputMinorAgentName" id="inputMinorAgentName" type="text" placeholder="법정대리인 성명" title="법정대리인 성명" alt="법정대리인 성명"  maxlength="20">
                            <!-- <label class="c-form__label" for="inputMinorAgentName">법정대리인 성명</label> -->
                            <button class="c-button c-button--sm c-button--underline" id="btnAgentAut">휴대폰 인증</button>
                        </div>
                        <p class="c-bullet c-bullet--dot u-co-gray">만 14세 미만 어린이는 법정대리인(부모님)의 동의절차를 거쳐 회원가입이 진행됩니다.</p>
                        <p class="c-bullet c-bullet--dot u-co-gray">부 혹은 모와 동일한 법정대리인의 휴대폰인증을 해야 합니다.</p>
                    </div>
                </div>
                <div class="c-form u-mt--40">
                    <div class="c-agree__item">
                        <input class="c-checkbox " id="minorAgentAgreeCheck" name="minorAgentAgree" type="checkbox" >
                        <label class="c-label" for="minorAgentAgreeCheck">본인은 아래 동의사항을 확인하였습니다. <span class="u-co-gray">(필수)</span></label>
                    </div>
                    <div class="c-agree__inside">
                        <div class="self-agree__inside">
                            <ul class="c-text-list c-bullet c-bullet--dot">
                                <li class="c-text-list__item u-co-gray">
                                    본인은 미성년자(신청인)의 법정대리인으로서 ‘kt M모바일의 서비스 이용‘ 관련하여 아동(신청인)의 개인정보 수집이용에 동의합니다.
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </c:if>


            <div class="c-button-wrap">
                <button type="button" class="c-button c-button--full c-button--primary is-disabled" id="btn_submit">가입하기</button>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
