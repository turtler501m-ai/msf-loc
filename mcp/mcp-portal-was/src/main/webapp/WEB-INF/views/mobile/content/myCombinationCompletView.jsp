<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
   		<script type="text/javascript">
 			history.pushState(null, null,location.href);
				window.onpopstate = function (event){   	   				
				location.href = "/m/myCombinationInfo.do";
    		 }
 		</script>
	</t:putAttribute>

 <t:putAttribute name="contentAttr">
  <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">유무선결합<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="complete">
        <div class="c-icon c-icon--complete" aria-hidden="true">
          <span></span>
        </div>
        <p class="complete__heading">
          <b>유무선결합</b>신청이
          <br>
          <b>완료</b>되었습니다.
        </p>
      </div>
      <input type="hidden" name="combiYn" id="combiYn">
      <h3 class="c-heading c-heading--type2 u-mb--12">내 정보</h3><!-- [2021-12-02] 간격 유틸클래스 삭제 -->
      <div class="c-table">
        <table>
          <caption>내정보</caption>
          <colgroup>
            <col style="width: 30%">
            <col style="width: 70%">
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">휴대폰 번호</th>
              <td class="u-ta-left">${myCombinationResDto.svcNo}</td>
            </tr>
            <tr>
              <th scope="row">요금제</th>
              <td class="u-ta-left">${myCombinationResDto.socNm}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <h3 class="c-heading c-heading--type2 u-mb--12">유무선결합 회선</h3><!-- [2021-12-02] 간격 유틸클래스 삭제 -->
      <div class="c-table">
        <table>
          <caption>유무선결합 회선</caption>
          <colgroup>
            <col style="width: 30%">
            <col style="width: 70%">
          </colgroup>
          <tbody>
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
	               	   <td class="u-ta-left">${myCombinationResDto.reqSocNm}(${myCombinationResDto.combiSvcNo}) </td>
	               	</c:when>
	               	<c:otherwise>
	               	  <td class="u-ta-left">${myCombinationResDto.combiSvcNo} </td>
	               	</c:otherwise>
                </c:choose>
            </tr>
          </tbody>
        </table>
      </div>
      <hr class="c-hr c-hr--type2">
      <p class="c-bullet c-bullet--fyr u-co-gray">유무선 결합 완료 시 SMS가 발송됩니다. SMS를 수신하지 못하신 고객님은 고객센터(114)로 연락 바랍니다.</p>
      <div class="c-button-wrap">
        <a class="c-button c-button--full c-button--primary" href="/m/myCombinationInfo.do">확인</a>
      </div>
    </div>
  </div>
</t:putAttribute>
</t:insertDefinition>