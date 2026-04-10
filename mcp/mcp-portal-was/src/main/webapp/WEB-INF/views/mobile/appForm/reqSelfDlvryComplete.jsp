<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
    	<script type="text/javascript">
	    	if($('#replaceYn').val() == 'Y'){
				location.replace(location.origin + location.pathname);
	    	}else{
	    		$('#main-content').show();
		    }

	    	history.pushState(null, null,location.href);
	    	window.onpopstate = function (event){   	   				
	    		var newForm = $('<form></form>');
	    		newForm.attr('name','newForm');
	    		newForm.attr('method','post');
	    		newForm.attr('action','/m/appForm/reqSelfDlvry.do');
	    		newForm.appendTo('body');
	    		newForm.submit();
		    }
	
	    	window.onpageshow = function (evt){
	    		if(evt.persisted || window.performance.navigation.type == 2){
	    			location.href = '/m/appForm/reqSelfDlvry.do';
	    	    }
	    	}

    	</script>
	</t:putAttribute>
	<t:putAttribute name="bottomScript">
		<!-- naver DA script  통계  전환페이지 설정 -->
		<script type="text/javascript">
			if(window.wcs){
				// (5) 결제완료 전환 이벤트 전송
				var _conv = {}; // 이벤트 정보를 담을 object 생성
				_conv.type = "custom002";  // object에 구매(purchase) 이벤트 세팅
				wcs.trans(_conv); // 이벤트 정보를 담은 object를 서버에 전송
			}
		</script>
		<!-- naver DA script  통계  전환페이지 end -->
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content" style="display:none;">
			<input type="hidden" id="replaceYn" value="${replaceYn}"/>
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					유심구매
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="complete">
				<div class="c-icon c-icon--complete" aria-hidden="true">
					<span></span>
				</div>
				<p class="complete__heading">
					<b>유심구매</b>가 정상적으로 <br> <b>완료</b>되었습니다.
				</p>
				<p class="complete__text">
					내 소비패턴에 맞춘 실속 있는 <br>다양한 요금제를 찾아보세요.
				</p>
			</div>
			<div class="c-button-wrap">
				<a class="c-button c-button--full c-button--gray" href="/m/order/orderList.do">신청조회</a>
				<a class="c-button c-button--full c-button--primary" href="/m/rate/rateList.do">요금제 알아보기</a>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>