<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<script type="text/javascript" src="/resources/js/mobile/event/userPromotionPop.js?version=24-04-11"></script>
<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/mobile/join/joinCommon.js?version=22-10-12"></script>
<script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>

<div class="c-modal" id="userPromotionModal" role="dialog" tabindex="-1" 	aria-describedby="#userPromotionTitle">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
        <div class="c-modal__content">
            <div class="c-modal__header">
                <h1 class="c-modal__title" id="userPromotionTitle">회원가입</h1>
                <button id="promoClose" class="c-button" data-dialog-close>
                    <i class="c-icon c-icon--close" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                </button>
            </div>
            <div class="c-modal__body">

             <form id="inputForm" name="inputForm" method="post">
               <input type="hidden" id="snsAddYn" name="snsAddYn" value="${snsAddYn}" />
               <input type="hidden" name="diVal" id="diVal" value="${diVal}" />
               <input type="hidden" id="checkKid" name="checkKid" value="${checkKid}" />
               <input type="hidden" id="niceHistSeq" name="niceHistSeq"  value="${niceHistSeq}" />
               <input type="hidden" id="isAuthAgent"  value="-1" />
               <c:set var="gender" value="${getInfo.gender}"/>
               <c:set var="birthday" value="${getInfo.birthDate}"/>
               <input type="hidden" name="gender"  value="${gender}"/>
               <input type="hidden" name="birthday" value="${birthday}"/>
               <input type="hidden" name="clausePriAdFlag" id="clausePriAdFlag"/>
               <input type="hidden" name="personalInfoCollectAgree" id="personalInfoCollectAgree"/>
               <input type="hidden" name="othersTrnsAgree" id="othersTrnsAgree"/>

                <div class="c-form">
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
                <div class="c-form">
                    <div class="c-form__input has-value">
                        <input class="c-input onlyNum" id="mobileNo" type="tel" value="${mobileNo}" name="mobileNo" placeholder="숫자만 입력해주세요" onkeyup="nextStepChk();" maxlength="11" readonly>
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
                <c:if test="${checkKid eq 'Y' }">
                <input type="hidden" id="niceAgentHistSeq" name="niceAgentHistSeq"  value="${niceAgentHistSeq}" />
                    <div class="c-form">
                        <div class="c-form__input">
                            <input class="c-input" name="minorAgentName" id="minorAgentName" type="text" placeholder="법정대리인 성명" title="법정대리인 성명" alt="법정대리인 성명" maxlength="20">
                            <label class="c-form__label" for="minorAgentName">법정대리인 성명</label>
                            <button class="c-button c-button--sm c-button--underline" id="btnAgentAut">휴대폰 인증</button>
                        </div>
                        <p class="c-bullet c-bullet--dot u-co-gray">만 14세 미만 어린이는 법정대리인(부모님)의 동의절차를 거쳐 회원가입이 진행됩니다.</p>
                    </div>
                </c:if>
                <h4 class="c-form__title">관심사<span class="c-form__sub">(택1 필수)</span></h4>
                <div class="c-check-wrap c-check-wrap--col2">
                  <c:set var="interestList" value="${nmcp:getCodeList(Constants.INTEREST)}" />
                     <c:forEach var="item" items="${interestList}" varStatus="status">
                        <input class="c-checkbox c-checkbox--button" id="chk${status.index}" name="chkInterest" data-dtl-cd="${item.dtlCd}" inheritance="${item.expnsnStrVal2}" type="checkbox" value="${item.dtlCd}">
                        <label class="c-label" for="chk${status.index}">${item.dtlCdNm}</label>
                     </c:forEach>
                </div>
                <h4 class="c-form__title">회원약관 및 이벤트 참여 동의</h4>
                <div class="c-agree u-mt--0">
                    <input class="c-checkbox" id="checkAll" type="checkbox">
                    <label class="c-label" for="checkAll">전체 동의</label>
                    <c:set var="termsMemList" value="${nmcp:getCodeList(Constants.TERMS_MEM_CD)}" />
                    <c:forEach var="item" items="${termsMemList}" varStatus="status">
                        <div class="c-agree__item">
                            <input class="c-checkbox c-checkbox--type2" id="terms${status.index}" name="terms" inheritance="${item.expnsnStrVal2}" data-dtl-cd="${item.dtlCd}" type="checkbox" data-mand-yn="<c:out value="${item.expnsnStrVal1 eq '필수' ? 'Y' : 'N'}"/>" onclick="setChkbox(this)" />
                            <label class="c-label" for="terms${status.index}"> ${item.dtlCdNm} <span class="u-co-gray">(${item.expnsnStrVal1})</span></label>
                            <button class="c-button c-button--xsm" type="button" onclick="viewTerms('terms${status.index}', 'cdGroupId1=${Constants.TERMS_MEM_CD}&cdGroupId2=${item.dtlCd}');">
                                <span class="c-hidden">상세보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                        </div>
                    </c:forEach>
                </div>
                <ul class="c-text-list c-bullet c-bullet--fyr">
                    <li class="c-text-list__item u-co-gray">본 선택 동의는 거부하실 수 있으나, 미 동의 시 이벤트 응모에 참여하실 수 없습니다.</li>
                </ul>
                <div class="c-button-wrap">
                    <button type="button" class="c-button c-button--full c-button--primary is-disabled" id="btn_submit">회원가입 및 이벤트 응모</button>
                </div>
            </div>
        </div>
    </div>
</div>