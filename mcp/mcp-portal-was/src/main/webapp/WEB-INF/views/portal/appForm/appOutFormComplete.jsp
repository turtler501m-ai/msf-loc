<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="layoutOutFormGnbDefault">
    <t:putAttribute name="titleAttr">
        대리점 단말 가입신청서 | kt M모바일 공식 다이렉트몰
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/appForm/appFormComplete.js?ver=${jsver}"></script>
        <!-- 카카오 광고 분석  -->
        <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
        <script type="text/javascript">
              kakaoPixel('5981604067138488988').pageView();
              kakaoPixel('5981604067138488988').completeRegistration();
        </script>
        <!-- 카카오 광고 분석 end-->
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="requestKey" name="requestKey" value="${AppformReq.requestKey}" >

        <input type="hidden" id="modelSalePolicyCode" name="modelSalePolicyCode" value="${AppformReq.modelSalePolicyCode}" >
        <input type="hidden" id="enggMnthCnt" name="enggMnthCnt" value="${AppformReq.enggMnthCnt}" >
        <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
        <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
        <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
        <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
        <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
        <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="" />
        <input type="hidden" id="reqModelName" name="reqModelName" value="${AppformReq.reqModelName}" >
        <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
        <input type="hidden" id="cstmrName" name="cstmrName" value="${AppformReq.cstmrName}" />
        <input type="hidden" id="prdtCode" name="prdtCode"  value="${AppformReq.prdtNm}" />

        <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" >
        <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" >
        <input type="hidden" id="joinPrice" name="joinPrice" >
        <input type="hidden" id="usimPrice" name="usimPrice" >




        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <c:choose>
                    <c:when test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                        <h2 class="c-page--title">대리점 유심 가입신청서</h2>
                    </c:when>
                     <c:otherwise>
                         <h2 class="c-page--title">대리점 단말 가입신청서</h2>
                     </c:otherwise>
                </c:choose>

            </div>

            <div class="c-row c-row--xlg c-row--divider-top"></div>
            <div class="c-row c-row--md">
                <div class="complete u-mt--80 u-ta-center">
                  <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
                  <h3 class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
                    <b>가입신청</b>이 정상적으로
                    <br>
                    <b>완료</b>되었습니다
                  </h3>
                  <div class="c-button-wrap u-mt--64">
                    <a id="btnNewForm" class="c-button c-button--lg c-button--primary c-button--w460 c-hidden" href="javascript:void(0);">새로운 신청서 작성</a>
                  </div>
                </div>
            </div>


        </div>





    </t:putAttribute>

</t:insertDefinition>
