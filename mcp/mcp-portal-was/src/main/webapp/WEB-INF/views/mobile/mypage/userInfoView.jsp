<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/userInfoView.js?version=22-10-12"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">


        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    정보수정
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-form u-mt--40">
                <div class="c-form__input u-mt--0">
                    <input class="c-input" id="inpA" type="text" placeholder="이름 입력" name="name" value="${userVO.mkNm }" readonly>
                    <input type="text" id="userId" value="${userVO.mkId }" hidden/>
                    <label class="c-form__label" for="inpA">이름</label>
                </div>
            </div>
            <div class="c-form">
                <div class="c-form__input">
                    <input class="c-input" id="inpB" type="text" placeholder="4~12자의 영문 소문자, 숫자 사용" name="userid" value="${userVO.mkId }" readonly>
                    <label class="c-form__label" for="inpB">아이디</label>
                </div>
            </div>
            <div class="c-form">
                <div class="c-form__input">
                    <input class="c-input" id="inpC" type="password" name="password" placeholder="10~15자리 영문/숫자/특수기호 조합" value="password" readonly>
                    <label class="c-form__label" for="inpC">비밀번호</label>
                    <button type="button" class="c-button c-button--sm c-button--underline" data-dialog-trigger="#pw_confirm-dialog" onclick="initPwChg()">비밀번호 변경</button>
                </div>
            </div>
            <div class="c-form">
                <div class="c-form__input">
                    <input class="c-input" id="inpD" type="tel" name="mobileNo" placeholder="숫자만 입력해주세요" value="${userVO.mobileNo }" maxlength="11">
                    <label class="c-form__label" for="inpD">휴대폰</label>
                    <input type="hidden" id="isAuth"  value="-1" />
                </div>
            </div>
            <div class="c-form">
                <div class="c-form__input">
                    <input class="c-input" id="inpE" type="email" name="email" placeholder="예: ktm@ktm.com" aria-invalid="true" aria-describedby="error3" value="${userVO.email1}@${userVO.email2}">
                    <label class="c-form__label" for="inpE">이메일</label>
                </div>
            </div>
            <div id="isAuthComplete" style="display:none" aria-hidden="true">
                <c:if test="${checkKid eq 'Y' }">
                    <input type="hidden" id="isAuthAgent"  value="-1" />
                    <div class="c-form c-form--lg">
                        <div class="c-form__group" role="group"aria-labeledby="inpG">
                            <div class="c-input-wrap">
                                <input class="c-input" name="minorAgentName" id="minorAgentName" type="text" placeholder="법정대리인 성명" title="법정대리인 성명" alt="법정대리인 성명"  maxlength="20">
                                <!-- <label class="c-form__label" for="minorAgentName">법정대리인 성명</label> -->
                                <button class="c-button c-button--sm c-button--underline" id="btnAgentAut">휴대폰 인증</button>
                            </div>
                            <p class="c-bullet c-bullet--dot u-co-gray">만 14세 미만 어린이는 법정대리인(부모님)의 동의절차를 거쳐 회원 정보 수정이 진행됩니다.</p>
                            <p class="c-bullet c-bullet--dot u-co-gray">부 혹은 모와 동일한 법정대리인의 휴대폰인증을 해야 합니다.</p>
                        </div>
                    </div>
                </c:if>
            </div>
                <%--    테스트     --%>
            <form id="inputForm" name="inputForm" method="post">
                <input type="hidden" name="agree" id="TERMSMEM04" value2="personalInfoCollectAgree" value="${userVO.personalInfoCollectAgree}" />
                <input type="hidden" name="agree" id="TERMSMEM03" value2="clausePriAdFlag" value="${userVO.clausePriAdFlag}" />
                <input type="hidden" name="agree" id="TERMSMEM05" value2="othersTrnsAgree" value="${userVO.othersTrnsAgree}" />
                <input type="hidden" name="agree" id="emailRcvYn" value="${userVO.emailRcvYn}" />
                <input type="hidden" name="agree" id="smsRcvYn" value="${userVO.smsRcvYn}" />
                <input type="hidden" name="agree" id="targetTerms" value=""/>
            </form>
            <div class="c-agree u-mt--0 u-bt--0">
                <input class="c-checkbox" id="checkAll" type="checkbox">
                <label class="c-label" for="checkAll">전체 동의</label>
                <c:set var="termsMemList" value="${nmcpList}" />
                <c:forEach var="item" items="${termsMemList}" varStatus="status">
                    <c:if test="${'선택' eq item.expnsnStrVal1}">
                        <div class="c-agree__item">
                            <input class="c-checkbox c-checkbox--type2 hidden _agree" id="terms${status.index}" inheritance="${item.expnsnStrVal2}" name="terms" data-dtl-cd="${item.dtlCd}" type="checkbox"  href="javascript:void(0);" onclick="setChkbox(this)">
                            <label class="c-label" id="terms${status.index}label" for="terms${status.index}">${item.dtlCdNm}<span class="u-co-gray">(${item.expnsnStrVal1})</span></label>
                            <button class="c-button c-button--xsm" onclick="viewTerms('${item.dtlCd}', 'cdGroupId1=${Constants.TERMS_MEM_CD}&cdGroupId2=${item.dtlCd}');">
                                <span class="c-hidden">${item.dtlCdNm} 상세 팝업보기</span>
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </button>
                        </div>
                       </c:if>
                </c:forEach>
            </div>
            <div class="c-button-wrap u-mt--48">
                <button class="c-button c-button--full c-button--gray" onclick="history.go(-1)">취소</button>
                <button class="c-button c-button--full c-button--primary" onclick="modifySubmit();">변경</button>
            </div>
        </div>
        <div class="c-modal" id="pw_confirm-dialog" role="dialog" tabindex="-1" aria-describedby="#pw_confirm-title">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="pw_confirm-title">비밀번호 변경</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <p class="c-text c-text--type2 u-co-gray u-mt--32">사용하실 비밀번호를 입력해 주세요.</p>
                        <div class="c-form u-mt--32">
                            <div class="c-form__group" role="group" aria-labeledby="inpA">
                                <div class="c-form__input">
                                    <input class="c-input" id="nowPw" type="password" name="" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="20">
                                    <label class="c-form__label" for="nowPw">현재 비밀번호</label>
                                </div>
                                <!-- 에러 아닐 경우 경우 .has-error 삭제 -->
                                <div class="c-form__input" id="npw">
                                    <input class="c-input" id="newPw" type="password" name="" placeholder="10~15자리 영문/숫자/특수기호 조합" maxlength="15">
                                    <label class="c-form__label" for="newPw">새 비밀번호</label>
                                </div>
                                <p class="c-form__text" id="error2" hidden>사용 불가능한 비밀번호입니다.</p>
                                <div class="c-form__input" id="cpw">
                                    <input class="c-input" id="checkPw" type="password" name="" placeholder="비밀번호를 한번 더 입력해주세요" aria-invalid="true" aria-describedby="error2" maxlength="15">
                                    <label class="c-form__label" for="checkPw">비밀번호 확인</label>
                                </div>
                                <p class="c-form__text" id="error3" hidden>비밀번호가 일치하지 않습니다.</p>
                            </div>
                        </div>
                        <div class="c-button-wrap u-mt--48">
                            <button type="button" class="c-button c-button--full c-button--gray" id="pwChgBut" data-dialog-close>취소</button>
                            <button type="button" class="c-button c-button--full c-button--primary" onclick="pwChange()">확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- [START]-->




    </t:putAttribute>
</t:insertDefinition>