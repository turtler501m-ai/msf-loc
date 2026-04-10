<%--
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<title>kt M모바일</title>

<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/nmcpMenu.js"></script>
	    <script type="text/javascript" src="/resources/js/jquery.placeholder.js"></script>
	    <script type="text/javascript" src="/resources/js/common.js"></script>
	    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
	    <script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
	    <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
	    <script type="text/javascript" src="/resources/js/pcPopup.js"></script>
	    <script type="text/javascript" src="/resources/js/webReady.js"></script>
		<script type="text/javascript" src="/resources/js/swipe.js"></script>
		<script type="text/javascript" src="/resources/js/swipe-banner.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.bxslider.min.js"></script>
		<script type="text/javascript" src="/resources/js/owl.carousel.js"></script>
	    
	    <script type="text/javascript">

	  
	    
		    if (navigator.userAgent.indexOf('MSIE') != -1)
		    	var detectIEregexp = /MSIE (\d+\.\d+);/ //test for MSIE x.x
		    else // if no "MSIE" string in userAgent
		    	var detectIEregexp = /Trident.*rv[ :]*(\d+\.\d+)/ //test for rv:x.x or rv x.x where Trident string exists
	
		    if (detectIEregexp.test(navigator.userAgent)){ //if some form of IE
		    	var ieversion=new Number(RegExp.$1); // capture x.x portion and store as a number
		    	 
		    	if (ieversion>=8) { 
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/common_ie.css">');
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/style_ie.css">');
		    	} else {
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/common_ie.css">');
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/style_ie.css">');
		    	} 
		    } else {
		    	document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/common_new.css">');
		    	document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/style_new2.css">');
		    }
	    </script>
        <!--  <script type="text/javascript" src="/resources/js/common/googleAnalyti.js"></script>  --> 

<head>
    <script>
        $(document).ready(function () {

        	$('#searchBtn').click(function(){
        		$('input[name=pageNo]').val("1");
        		$('#usimList').submit();
        	});
        });

        var searchSubmit = function(pageNo) {
			$("input[name='pageNo']").val(pageNo);
			$('#usimList').submit();
		};
    </script>
</head>
<body>
	<form:form commandName="mmobileEtcDto" id="usimList" name="usimList" method="post" action="termlUsimSearch.do">
	<input type="hidden" name="pageNo"  value="${pageInfoBean.pageNo}" />
	<div class="dim-popup">
		<div class="pop-container">
			 <div class="popup_top">
				<div class="popup_title">단말별 유심형태 확인 하기</div>
			</div>
			<div class="popup_content" style="overflow-y:scroll; -ms-overflow-y:scroll">
				<button type="button" class="btn_search float_right" id="searchBtn" title="검색"></button>
				<input class="height_34px placeholder float_right" title="검색어를 입력" type="text" name="searchVal" id="searchVal" value="${searchVal}" placeholder="검색어를 입력해 주세요." />
				<select id="searchOption" name="searchOption" title="선택" class="height_34px float_right">
					<option value="makr" <c:if test="${searchOption eq 'makr'}">selected</c:if>>제조사</option>
					<option value="model" <c:if test="${searchOption eq 'model'}">selected</c:if>>모델명</option>
					<option value="prod" <c:if test="${searchOption eq 'prod'}">selected</c:if>>상품명</option>
				</select>
				<div class="clear_both"></div>
				<div class="info_list_table margin_top_20">
					<table summary="제조사,모델명,상품명,출시일,유심종류 테이블입니다." style="font-size: 14;">
						<caption class="hidden">단말별 유심형태</caption>
						<colgroup>
							<col width="18%" />
							<col width="18%"/>
							<col width=""/>
							<col width="18%"/>
							<col width="18%"/>
						</colgroup>
						<thead>
							<tr>
								<th scope="col">제조사</th>
								<th scope="col">모델명</th>
								<th scope="col">상품명</th>
								<th scope="col">출시일</th>
								<th scope="col">유심종류</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(mmobileEtcList) eq 0 }">
									<tr>
										<td colspan="5">검색결과가 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${mmobileEtcList}" var="list" varStatus="cnt">
										<tr>
											<td>${list.makrNm}</td>
											<td>${list.modelNm}</td>
											<td>${list.prodNm}</td>
											<td>${list.comotDate}</td>
											<td>${list.usimNm}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<c:if test="${fn:length(mmobileEtcList) ne 0 }">
					<div class="board_list_table_paging margin_bottom_50">
						<nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="searchSubmit"/>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	</form:form>
</body>
--%>