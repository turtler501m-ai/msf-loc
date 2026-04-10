<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">회원가입</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/portal/join/joinTwo.js?version=24-12-17"></script>
        <script type="text/javascript" src="/resources/js/portal/join/joinCommon.js?version=23-04-20"></script>
        <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content" style="display: none;">
            <div class="ly-page--title">
                <h2 class="c-page--title">회원가입</h2>
                <div class="c-stepper-wrap">
                    <div class="c-hidden">회원가입 총 2단계 중 현재 2단계(정보입력)</div>
                    <ul class="c-stepper" data-step="2" aria-hidden="true">
                        <li class="c-stepper__item">
                            <span class="c-stepper__num">1</span>
                        </li>
                        <li class="c-stepper__item c-stepper__item--active">
                            <span class="c-stepper__num">2</span> <span class="c-stepper__title">정보입력</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="c-row c-row--md">
                <h3 class="c-heading--fs22 c-heading--bold">정보입력</h3>
                <p class="c-text--fs16 u-co-gray-8 u-mt--20">회원가입을 위해 고객님의 정확한 정보를 입력하여 주세요.</p>

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

                    <div class="c-form u-mt--48">
                        <div class="c-form__input has-value" id="idCheckDiv">
                            <input type="hidden" id="isPushBtn" name="isPushBtn" value="N" />
                            <input type="hidden" id="isChkPw" value="N" />
                            <input class="c-input" id="userId" name="userId" type="text" placeholder="4~12자의 영문 소문자, 숫자 사용" aria-invalid="true" maxlength="12" autocomplete="off">
                            <label class="c-form__label" for="userId">아이디</label>
                            <button class="c-button c-button--sm c-button--underline" id="dupleCheck" onclick="return false;">중복확인</button>
                        </div>
                        <p class="c-form__text" id="use_ok"></p>
                    </div>
                    <div class="c-form-wrap">
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input" id="pwCheckDiv">
                                    <input class="c-input" id="password" onkeyup="pwChange();" type="password" name="password" placeholder="10~15자리 영문/숫자/특수기호 조합" aria-invalid="true" maxlength="15">
                                    <label class="c-form__label" for="password">비밀번호</label>
                                </div>
                                <p class="c-form__text" id="pwdOneChk"></p>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input" id="pwCheckConfirmDiv">
                                    <input class="c-input" id="passwordChk" onkeyup="pwConfirm();" type="password" name="passwordChk" placeholder="비밀번호를 한번 더 입력해주세요" aria-invalid="true" maxlength="15">
                                    <label class="c-form__label" for="passwordChk">비밀번호 확인</label>
                                </div>
                                <p class="c-form__text" id="pwdSndChk"></p>
                            </div>
                        </div>
                    </div>
                    <div class="c-form-wrap">
                        <div class="c-form-row c-col2">
                            <div class="c-form-field">
                                <div class="c-form__input" id="mobileNoCheckDiv">
                                    <input class="c-input onlyNum" id="mobileNo" name="mobileNo" type="number" placeholder="숫자만 입력해주세요." aria-invalid="true" onkeyup="mobileNoChk();" maxlength="11" value="${getInfo.sMobileNo}" readonly >
                                    <label class="c-form__label" for="mobileNo">휴대폰</label>
                                </div>
                                <p class="c-form__text" id="error3"></p>
                            </div>
                            <div class="c-form-field">
                                <div class="c-form__input" id="emailCheckDiv">
                                    <input class="c-input" id="email" name="email" type="text" placeholder="이메일을 입력해주세요." aria-invalid="true" onkeyup="emailValiChk();" maxlength="50">
                                    <label class="c-form__label" for="email">이메일</label>
                                </div>
                                <p class="c-form__text" id="error4"></p>
                            </div>
                        </div>
                    </div>
                </form>
                <c:if test="${checkKid eq 'Y' }">
                    <div class="c-form-wrap u-mt--32">
                        <div class="c-form-group" role="group" aria-labelledby="inputAddressHead">
                            <h3 class="c-group--head c-hidden" id="inputAddressHead">법정 대리인 정보</h3>
                            <!-- <div class="c-form-row c-col2"> -->
                            <div class="c-form u-mt--16">
                                <div class="c-form-field">
                                    <div class="c-form__input">
                                        <input class="c-input" name="inputMinorAgentName" id="inputMinorAgentName" type="text" placeholder="법정대리인 성명" aria-invalid="true" maxlength="20">
                                        <label class="c-form__label" for="inputMinorAgentName">법정대리인 성명</label>
                                        <button class="c-button c-button--sm c-button--underline" id="btnAgentAut">휴대폰 인증</button>
                                    </div>
                                </div>
                                <!--
                                <div class="c-form-field">
                                    <div class="c-form__select">
                                        <select class="c-select" id="relation">
                                            <option></option>
                                            <option label="부">부</option>
                                            <option label="모">모</option>
                                            <option label="관계자">관계자</option>
                                        </select>
                                        <label class="c-form__label" for="relation">법정대리인 관계</label>
                                    </div>
                                </div>
                                -->
                            </div>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot u-ta-left u-mt--16">
                        <li class="c-text-list__item u-co-gray">만14세 미만 어린이는 법정대리인(부모님)의 동의절차를 거쳐 회원가입이 진행됩니다.</li>
                        <li class="c-text-list__item u-co-gray">부 혹은 모와 동일한 법정대리인의 휴대폰인증을 해야 합니다.</li>
                    </ul>
                    <div class="c-form-wrap u-mt--48">
                        <div class="c-agree c-agree--type2">
                            <div class="c-agree__item">
                                <input class="c-checkbox " id="minorAgentAgreeCheck" type="checkbox" >
                                <label class="c-label" for="minorAgentAgreeCheck">본인은 아래 동의사항을 확인하였습니다.<span class="u-co-gray">(필수)</span></label>
                            </div>
                            <div class="c-agree__inside self-agree__inside">
                                <ul class="c-text-list c-bullet c-bullet--dot">
                                    <li class="c-text-list__item u-co-gray u-mt--0">
                                        본인은 미성년자(신청인)의 법정대리인으로서 ‘kt M모바일의 서비스 이용‘ 관련하여 아동(신청인)의 개인정보 수집이용에 동의합니다.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="c-hr c-hr--w1100-expand u-mt--56"></div>
                <div class="c-button-wrap u-mt--56">
                    <a class="c-button c-button--lg c-button--primary c-button--w460 is-disabled" href="javascript:void(0);" id="btn_submit">가입하기</a>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
