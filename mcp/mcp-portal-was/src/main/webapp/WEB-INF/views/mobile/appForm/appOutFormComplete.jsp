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

<t:insertDefinition name="mlayoutOutFormFotter">
<t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/mobile/appForm/appFormComplete.js?ver=${jsver}"></script>
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
    <input type="hidden" id="enggMnthCnt" name="enggMnthCnt" value="${AppformReq.enggMnthCnt}" ><!--유심에서 약정????  -->
    <input type="hidden" id="rprsPrdtId" name="rprsPrdtId" value="${AppformReq.rprsPrdtId}">
    <input type="hidden" id="cntpntShopId" name="cntpntShopId" value="${AppformReq.cntpntShopId}" >
    <input type="hidden" id="operType" name="operType" value="${AppformReq.operType}" />
    <input type="hidden" id="socCode" name="socCode" value="${AppformReq.socCode}" >
    <input type="hidden" id="onOffType" name="onOffType" value="${AppformReq.onOffType}" />
    <input type="hidden" id="prdtSctnCd" name="prdtSctnCd" value="" />
    <input type="hidden" id="operTypeNm" name="operTypeNm" value="${AppformReq.operTypeNm}" />
    <input type="hidden" id="cstmrName" name="cstmrName" value="${AppformReq.cstmrName}" />
    <input type="hidden" id="prdtCode" name="prdtCode"  value="${AppformReq.prdtNm}" />
    <input type="hidden" id="joinPriceIsPay" name="joinPriceIsPay" >
    <input type="hidden" id="usimPriceIsPay" name="usimPriceIsPay" >
    <input type="hidden" id="joinPrice" name="joinPrice" >
    <input type="hidden" id="usimPrice" name="usimPrice" >


    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">

        <c:choose>
            <c:when test="${AppformReq.reqBuyType eq Constants.REQ_BUY_TYPE_USIM}">
                <h2 class="ly-page__head">대리점 유심 가입신청서<button class="header-clone__gnb"></button></h2>
            </c:when>
             <c:otherwise>
                 <h2 class="ly-page__head">대리점 단말 가입신청서<button class="header-clone__gnb"></button></h2>
             </c:otherwise>
        </c:choose>
        <div class="c-indicator">
          <span style="width: 100%"></span>
        </div>
      </div>
      <div class="complete">
        <div class="c-icon c-icon--complete" aria-hidden="true">
          <span></span>
        </div>
        <p class="complete__heading">
          <b>가입신청</b>이 정상적으로
          <br>
          <b>완료</b>되었습니다.
        </p>
      </div>
      <div class="c-button-wrap">
        <a id="btnNewForm"class="c-button c-button--full c-button--primary c-hidden" href="javascript:void(0);">새로운 신청서 작성</a>
      </div>
    </div>





</t:putAttribute>

<t:putAttribute name="popLayerAttr">











</t:putAttribute>



</t:insertDefinition>