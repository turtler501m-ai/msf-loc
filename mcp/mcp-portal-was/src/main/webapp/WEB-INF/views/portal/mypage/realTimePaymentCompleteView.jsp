<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">즉시납부</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/portal/mypage/realTimePaymentView.js"></script>
          <script type="text/javascript">
               history.pushState(null, null,location.href);
                  window.onpopstate = function (event){
                    location.href = "/mypage/unpaidChargeList.do";
               }


           </script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
      <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">즉시납부</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="complete u-ta-center">
          <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
          <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--32">
            <b>요금</b>이 정상적으로
            <br>
            <b>납부</b>되었습니다
          </strong>
        </div>
        <form name="frm" id="frm" method="post" action="/mypage/unpaidChargeList.do">
       </form>
        <h3 class="c-heading c-heading--fs16 u-mt--64 u-mb--16">즉시납부 내역</h3>
        <div class="c-table">
          <table>
            <caption>납부금액, 납부정보를 포함한 즉시납부 내역 정보</caption>
            <colgroup>
              <col style="width: 50%">
              <col>
            </colgroup>
            <thead>
              <tr>
                <th scope="col">납부금액</th>
                <th scope="col">납부정보</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>${payMentMoney}원</td>
                <c:choose>
                  <c:when test="${rmnyChId eq 'C'}">
                      <td>신용/체크카드</td>
                  </c:when>
                  <c:when test="${rmnyChId eq 'K'}">
                      <td>카카오페이</td>
                  </c:when>
                  <c:when test="${rmnyChId eq 'P'}">
                      <td>페이코</td>
                  </c:when>
                   <c:when test="${rmnyChId eq 'N'}">
                      <td>네이버</td>
                  </c:when>
                    <c:when test="${rmnyChId eq 'D'}">
                        <td>계좌이체</td>
                    </c:when>
              </c:choose>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--primary u-width--460" href="javascript:void(0);" onclick="$('#frm').submit();">확인</a>
        </div>
      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>
