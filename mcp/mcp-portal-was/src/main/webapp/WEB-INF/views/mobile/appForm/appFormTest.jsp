<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.placeholder.js"></script>
		<script src="${smartroScriptUrl}?version=${today}"></script>
		<script type="text/javascript">
	    	history.pushState(null, null,location.href);
		    window.onpopstate = () => {
		      history.go(1);
		      location.href = '/appFormTestFirst.do';
		    };
			function goPay() {
		        // 스마트로페이 초기화
		        smartropay.init({
		            mode: 'STG'		// STG: 테스트, REAL: 운영
		        });
	
		        // 스마트로페이 결제요청
		        // 모바일용
		        smartropay.payment({
		            FormId : 'tranMgr'				// 폼ID
		        });
		    };
		</script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head" onclick="javascript:goPay();">
					유심구매하기
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			
			<form id="tranMgr" name="tranMgr" method="post">
		    </form>
		</div>
	</t:putAttribute>
</t:insertDefinition>