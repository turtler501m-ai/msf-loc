<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">약관동의/본인인증</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/portal/join/joinOne.js?version=23-11-20"></script>
        <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="targetTerms"/>
        <form name="frm" id="frm" method="post" action="${pageContext.request.contextPath}/join/sndPage.do">
            <input type="hidden" name="snsCd" id="snsCd" value="${snsCd}"/>
            <input type="hidden" name="snsId" id="snsId" value="${snsId}"/>
            <input type="hidden" name="snsMobileNo" id="snsMobileNo" value="${snsMobileNo}"/>
            <input type="hidden" name="snsGender" id="snsGender" value="${snsGender}"/>
            <input type="hidden" name="snsEmail" id="snsEmail" value="${snsEmail}"/>
            <input type="hidden" name="snsBirthday" id="snsBirthday" value="${snsBirthday}"/>
            <input type="hidden" name="clausePriAdFlag" id="clausePriAdFlag"/> <!-- 정보/광고 -->
            <input type="hidden" name="personalInfoCollectAgree" id="personalInfoCollectAgree"/> <!-- 정보수집 privacyAgree-->
            <input type="hidden" name="othersTrnsAgree" id="othersTrnsAgree"/> <!-- 3자동의 thirdAgree-->
        </form>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">약관동의/본인인증</h2>
                <div class="c-stepper-wrap">
                    <div class="c-hidden">회원가입 총 2단계 중 현재 1단계(약관 동의·본인인증)</div>
                    <ul class="c-stepper" data-step="2" aria-hidden="true">
                        <li class="c-stepper__item c-stepper__item--active">
                            <span class="c-stepper__num">1</span>
                            <span class="c-stepper__title">약관 동의·본인인증</span>
                        </li>
                        <li class="c-stepper__item">
                            <span class="c-stepper__num">2</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="c-row c-row--md-join">
                <h3 class="c-heading--fs22 c-heading--bold">kt M모바일 회원약관</h3>
                <div class="c-accordion c-accordion--type1 u-mt--48" data-ui-accordion>
                    <div class="c-accordion__box">
                        <div class="c-accordion__item">
                            <div class="c-accordion__head">
                                <button class="runtime-data-insert c-accordion__button" id="acc_headerA1" type="button" aria-expanded="false" aria-controls="acc_contentA1">
                                    <span class="c-hidden">이용약관 및 개인정보 수집/이용에 모두 동의합니다. 상세열기</span>
                                </button>
                                <div class="c-accordion__check">
                                    <input class="c-checkbox c-checkbox--type5" id="checkAll" type="checkbox">
                                    <label class="c-label" for="checkAll">이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>
                                </div>
                            </div>
                            <div class="c-accordion__panel expand" id="acc_contentA1" aria-labelledby="acc_headerA1">
                                <div class="c-accordion__inside">
                                    <c:set var="termsMemList" value="${nmcp:getCodeList(Constants.TERMS_MEM_CD)}" />
                                    <c:forEach var="item" items="${termsMemList}" varStatus="status">
                                        <c:if test="${item.dtlCd ne 'TERMSMEM06'}">
                                            <div class="c-accordion__sub-check">
                                                <button class="c-button c-button--xsm" onclick="viewTerms('terms${status.index}', 'cdGroupId1=${Constants.TERMS_MEM_CD}&cdGroupId2=${item.dtlCd}');">
                                                    <span class="c-hidden">${item.dtlCdNm} 상세 팝업보기</span>
                                                    <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                                </button>
                                                <input class="c-checkbox c-checkbox--type2" id="terms${status.index}" name="terms" data-dtl-cd="${item.dtlCd}" inheritance="${item.expnsnStrVal2}" type="checkbox" data-mand-yn="<c:out value="${item.expnsnStrVal1 eq '필수' ? 'Y' : 'N'}"/>" href="javascript:void(0);" onclick="setChkbox(this)">
                                                <label class="c-label" for="terms${status.index}">${item.dtlCdNm}<span class="u-co-gray">(${item.expnsnStrVal1})</span></label>
                                            </div>
                                       </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c-button-wrap u-mt--48">
	              <button class="c-button c-button-round--md c-button--mint c-button--w460" id="auth_phone">휴대폰 인증</button>
	            </div>
                <ul class="c-text-list c-bullet c-bullet--dot u-ta-left u-mt--48">
                    <li class="c-text-list__item u-co-gray">만14세 미만 어린이는 법정대리인(부모님)의 동의절차를 거쳐 회원가입이 진행됩니다.</li>
                    <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                    <li class="c-text-list__item u-co-gray">회원가입은 휴대폰 인증한 본인의 번호로만 가입이 가능합니다.</li>
                </ul>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
