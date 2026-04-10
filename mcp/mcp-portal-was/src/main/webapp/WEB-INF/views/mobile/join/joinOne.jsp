<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/join/joinOne.js?version=23-11-20"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="targetTerms"/>
        <form name="frm" id="frm" method="post" action="${pageContext.request.contextPath}/m/join/sndPage.do">
            <input type="hidden" name="snsCd" id="snsCd" value="${snsCd}"/>
            <input type="hidden" name="snsId" id="snsId" value="${snsId}"/>
            <input type="hidden" name="snsMobileNo" id="snsMobileNo" value="${snsMobileNo}"/>
            <input type="hidden" name="snsGender" id="snsGender" value="${snsGender}"/>
            <input type="hidden" name="snsEmail" id="snsEmail" value="${snsEmail}"/>
            <input type="hidden" name="snsBirthday" id="snsBirthday" value="${snsBirthday}"/>
            <input type="hidden" name="clausePriAdFlag" id="clausePriAdFlag"/>
            <input type="hidden" name="personalInfoCollectAgree" id="personalInfoCollectAgree"/> <!-- 정보수집 privacyAgree-->
            <input type="hidden" name="othersTrnsAgree" id="othersTrnsAgree"/> <!-- 3자동의 thirdAgree-->
        </form>

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head"> 회원가입 <button class="header-clone__gnb"></button> </h2>
                <div class="c-indicator">
                    <span style="width: 33.33%"></span>
                </div>
            </div>

            <h3 class="c-heading c-heading--type1">약관동의/본인인증</h3>
            <h4 class="c-heading c-heading--type2">kt M모바일 회원약관</h4>
            <div class="c-agree">
                <input class="c-checkbox" id="checkAll" type="checkbox">
                <label class="c-label" for="checkAll">이용약관 및 개인정보 수집/이용에 모두 동의합니다.</label>
                <c:set var="termsMemList" value="${nmcp:getCodeList(Constants.TERMS_MEM_CD)}" />
                <c:forEach var="item" items="${termsMemList}" varStatus="status">
                 <c:if test="${item.dtlCd ne 'TERMSMEM06'}">
                    <div class="c-agree__item">
                        <input class="c-checkbox c-checkbox--type2" id="terms${status.index}" name="terms" inheritance="${item.expnsnStrVal2}" data-dtl-cd="${item.dtlCd}" type="checkbox" data-mand-yn="<c:out value="${item.expnsnStrVal1 eq '필수' ? 'Y' : 'N'}"/>" onclick="setChkbox(this)" />
                        <label class="c-label" for="terms${status.index}">
                                ${item.dtlCdNm} <span class="u-co-gray">(${item.expnsnStrVal1})</span>
                        </label>
                        <button class="c-button c-button--xsm" type="button" onclick="viewTerms('terms${status.index}', 'cdGroupId1=${Constants.TERMS_MEM_CD}&cdGroupId2=${item.dtlCd}');">
                            <span class="c-hidden">상세보기</span>
                            <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        </button>
                    </div>
                  </c:if>
                </c:forEach>
            </div>
           <div class="c-button-wrap u-mt--40">
	          <button class="c-button c-button--full c-button--h48 c-button--mint" id="auth_phone">휴대폰 인증</button>
	        </div>
            <ul class="c-text-list c-bullet c-bullet--dot u-mt--40">
                <li class="c-text-list__item u-co-gray">만14세 미만 어린이는
                    법정대리인(부모님)의 동의절차를 거쳐 회원가입이 진행됩니다.</li>
                <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당
                    인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                <li class="c-text-list__item u-co-gray">회원가입은 휴대폰 인증한 본인의 번호로만 가입이 가능합니다.</li>
            </ul>
        </div>
    </t:putAttribute>
</t:insertDefinition>