<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>

<c:if test="${isMobile eq 'Y'}">
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
	    <div class="ly-content" id="main-content">
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">정회원인증<button class="header-clone__gnb"></button>
	        </h2>
	      </div>
	      <div class="c-table c-table--plr-8 u-mt--32">
	        <p class="c-table-title u-co-black fs-16 u-fw--medium">내 회선 정보</p>
	        <table>
	          <caption>내 회선 정보</caption>
	          <colgroup>
	            <col style="width: 34%">
	            <col style="width: 32%">
	            <col style="width: 34%">
	          </colgroup>
	          <thead>
	            <tr>
	              <th scope="col">휴대폰 번호</th>
	              <th scope="col">단말기정보</th>
	              <th scope="col">대표번호선택</th>
	            </tr>
	          </thead>
	          <tbody>
	            <tr>
	              <td colspan="3">kt M모바일의 모든 서비스를 이용하시기 위해
	                <br>정회원 인증을 해주세요.
	              </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
	      <div class="c-button-wrap u-mt--48">
	        <a class="c-button c-button--full c-button--primary" href="/m/mypage/multiPhoneLine.do#">정회원 인증</a>
	      </div>
	    </div>
    </t:putAttribute>
</t:insertDefinition>
</c:if>


<c:if test="${isMobile ne 'Y'}">
<t:insertDefinition name="layoutGnbDefault">
	<t:putAttribute name="scriptHeaderAttr">

	</t:putAttribute>

    <t:putAttribute name="contentAttr">
	    <div class="ly-content" id="main-content">
	      <div class="ly-page--title">
	        <h2 class="c-page--title">정회원인증</h2>
	      </div>
	      <div class="c-row c-row--lg">
	        <h3 class="c-heading c-heading--fs20">내 회선 정보</h3>
	        <hr class="c-hr c-hr--title">
	        <div class="nodata u-ta-center u-co-black">
	          <p>kt M모바일의 모든 서비스를 이용하시기 위해
	            <br>
	            <b>정회원 인증</b>을 해주세요.
	          </p>
	        </div>
	        <div class="c-button-wrap u-mt--56">
	          <button class="c-button c-button--lg c-button--primary u-width--460" type="button" onclick="location.href = '/mypage/multiPhoneLine.do'">정회원 인증</button>
	        </div>
	      </div>
	    </div>
    </t:putAttribute>
</t:insertDefinition>
</c:if>
