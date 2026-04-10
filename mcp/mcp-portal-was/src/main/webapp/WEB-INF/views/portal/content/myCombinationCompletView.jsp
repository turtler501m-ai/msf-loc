<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">유무선결합</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
       <script type="text/javascript">
               history.pushState(null, null,location.href);
                  window.onpopstate = function (event){
                    location.href = "/myCombinationInfo.do";
               }


           </script>
    </t:putAttribute>

   <t:putAttribute name="contentAttr">
      <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">유무선결합</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="complete u-ta-center">
          <div class="c-icon c-icon--complete" aria-hidden="true"> </div>
          <p class="c-heading c-heading--fs24 c-heading--regular u-mt--32">
            <b>유무선결합 신청</b>이
            <br>
            <b>완료</b>되었습니다
          </p>
        </div>
        <h3 class="c-heading c-heading--fs16 u-mt--64 u-mb--16">내 정보</h3>
        <div class="c-table">
          <table>
            <caption>전화번호, 요금제를 포함한 내 정보</caption>
            <colgroup>
              <col style="width: 15%">
              <col>
            </colgroup>
            <tbody class="c-table__left">
              <tr>
                <th scope="row">전화번호</th>
                <td>${myCombinationResDto.svcNo}</td>
              </tr>
              <tr>
                <th scope="row">요금제</th>
                <td>${myCombinationResDto.socNm}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <h3 class="c-heading c-heading--fs16 u-mt--48 u-mb--16">유무선결합 회선</h3>
        <div class="c-table">
          <table>
            <caption>전화번호, 요금제를 포함한 내 정보</caption>
            <colgroup>
              <col style="width: 15%">
              <col>
            </colgroup>
            <tbody class="c-table__left">
              <tr>
                <c:choose>
                    <c:when test="${'IT' eq myCombinationResDto.combiSvcType}">
                         <th scope="row">KT 인터넷</th>
                    </c:when>
                    <c:when test="${'M' eq myCombinationResDto.combiSvcType}">
                         <th scope="row">kt M모바일</th>
                    </c:when>
                    <c:otherwise>
                        <th scope="row">KT 모바일</th>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${!empty myCombinationResDto.reqSocNm }">
                        <td>${myCombinationResDto.reqSocNm}(${myCombinationResDto.combiSvcNo}) </td>
                    </c:when>
                    <c:otherwise>
                       <td>${myCombinationResDto.combiSvcNo} </td>
                    </c:otherwise>
                </c:choose>
              </tr>
            </tbody>
          </table>
        </div>
        <hr class="c-hr c-hr--basic u-mt--48 u-mb--32">
        <p class="c-bullet c-bullet--dot u-co-gray">유무선 결합 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
        <div class="c-button-wrap u-mt--56">
          <a class="c-button c-button--lg c-button--primary u-width--460" href="/myCombinationInfo.do">확인</a>
        </div>
      </div>
    </div>
   </div>
    </t:putAttribute>
</t:insertDefinition>
