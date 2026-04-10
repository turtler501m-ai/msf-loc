<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
	
	<script type="text/javascript">
		function btnRegDtl(param){
			openPage('termsInfoPop','/termsPop.do',param);
		}

   		
	</script>
</t:putAttribute>

 <t:putAttribute name="contentAttr">

	    <div class="ly-content" id="main-content">
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">유무선결합<button class="header-clone__gnb"></button>
	        </h2>
	      </div>
	      <form name="frm" id="frm" method="post" action="/m/loginForm.do">
			<input type="hidden" name="uri" id="uri" value="/m/content/myCombinationView.do"/>
			
		  </form>
	      <div class="c-title-wrap c-title-wrap--flex">
	        <h3 class="c-heading c-heading--type1"> KT-알뜰폰
	          <br>유무선결합이란?
	        </h3>
	        <c:set var="combiInfo" value="${nmcp:getCodeList(Constants.INFO_CD)}" />
	      	<c:forEach var="item" items="${combiInfo}" varStatus="status">
	      		<c:if test = "${Constants.COMBI_INFO_CD eq item.dtlCd}">
	      			 <button class="c-button c-button--sm c-button--white" onclick="btnRegDtl('cdGroupId1=${Constants.INFO_CD}&cdGroupId2=${item.dtlCd}');">자세히 보기</button>	
	      		</c:if>
	        </c:forEach> 
	      </div>
	      <hr class="c-hr c-hr--type2">
	      <p class="c-text c-text--type2 u-co-gray">kt M모바일 상품을 이용 중인 고객이 동일 명의로 KT 유/무선 상품을 사용중일 경우 결합하여 할인 혜택을 제공하는 서비스입니다.</p>
	      <div class="c-box u-mt--40">
	        <img class="center-img" src="/resources/images/mobile/content/img_combination.png" alt="">
	      </div>
	      <c:if test="${!nmcp:hasLoginUserSessionBean()}">
	      <p class="c-text c-text--type3 u-mt--40 fw-midium u-ta-center">로그인 후 사용하실 수 있습니다.</p>
	      <div class="c-button-wrap">
	        <a class="c-button c-button--full c-button--primary" href="javascript:void(0);" onclick="$('#frm').submit();">로그인</a>
	      </div>
	      </c:if>
	    </div>

</t:putAttribute>
</t:insertDefinition>