<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html PUBLIC "-/W3C/DTD XHTML 1.0 Transitional/EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<title> 가입신청서 목록 </title>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />

<link type="text/css" rel="stylesheet" href="<c:url value='/css/mcp/ad_content.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/mcp/ad_layout.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/mcp/base.css' />" />
<link type="text/css" rel="stylesheet" media="screen" href="<c:out value='/lib/jquery/jQuery-UI-Date-Range-Picker-master/ui.daterangepicker.css' />" />

<script src="<c:url value='/lib/jquery/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:out value='/lib/jquery/jquery-ui-1.10.2.custom/i18n/jquery.ui.datepicker-ko.js' />"></script>
<script type="text/javascript" src="<c:out value='/lib/jquery/jquery-ui-1.10.2.custom/js/jquery-ui-1.10.2.custom.js'/>"></script>

<script type="text/javascript" src="<c:out value='/lib/jquery/jQuery-UI-Date-Range-Picker-master/date.js' />"></script>
<script type="text/javascript" src="<c:out value='/lib/jquery/jQuery-UI-Date-Range-Picker-master/daterangepicker.jQuery.js' />"></script>
<script type="text/javascript" src="<c:out value='/lib/jquery/jQuery-Validation-Engine-master/js/languages/jquery.validationEngine-ko.js'/>"></script>
<script type="text/javascript" src="<c:out value='/lib/jquery/jQuery-Validation-Engine-master/js/jquery.validationEngine.js'/>"></script>

<script type="text/javascript">
//<!--
$(function() {
	$('#searchPstate').attr('value', '00');

	$('#searchStartDate, #searchEndDate').daterangepicker({
		dateFormat : 'yy/mm/dd',
		datepickerOptions : {
			ignoreValidate:[$('#searchStartDate'), $('#searchEndDate')]
		}
	});
    
});

//조회
function searchList(){
	$("#currentPageNo").val(1);
	$("#searchForm").attr("action", "/request/list.do").submit();;
}

//상세페이지
function fnView(seq){
	var action = "/request/view.do";
	$('#requestKey').attr('value', seq);
	$('#searchForm').attr('action', action).submit();
}

//페이징
function cfnPageLink(page) {
	$("#searchForm").attr("action", "/request/list.do");
	var frm = $("#currentPageNo").parent("form");
	$("#currentPageNo").val(page);
	frm.submit();
}

//엑셀다운로드
function excelDownload(){
	$("#searchForm").attr("action", "/request/excelList.do").submit();
}

//-->
</script>
</head>
<body>

<form:form commandName="requestSearchVO" id="searchForm" method="post" action="/request/list.do" >
<form:hidden path="currentPageNo"/>
<form:hidden path="searchPstate" />
<form:hidden path="searchAgentCode" />
<form:hidden path="requestKey" />

<div id="board_header">
	<!-- 페이지 정보 -->
	<div id="page_info">
		<p>Total <strong>${requestSearchVO.totalRecordCount }</strong>&nbsp; Page <strong> <span>${requestSearchVO.currentPageNo }</span></strong></p>
	</div>
	<!-- //페이지 정보 -->
	<!-- 검색영역 -->
	
	<div>
		<fieldset id="board_search">
		
		<h4 class="hidden">검색</h4>
		<div id="board_sel">
		
			<form:input path="searchStartDate" cssClass="input_box" cssStyle="width:80px" readonly="false"/> ~
			<form:input path="searchEndDate" cssClass="input_box" cssStyle="width:80px" readonly="false"/>
			
			<form:select path="searchServiceType" title="선택하세요" cssClass="va_m">
				<form:option value="" label="서비스선택" />
				<form:option value="PP" label="선불" />
				<form:option value="PO" label="후불" />
			</form:select>
			
			<form:select path="searchCntpntShopId" title="선택하세요" cssClass="va_m">
				<form:option value="" label="구분" />
				<form:option value="1100011741">온라인</form:option>
				<form:option value="1100011744">사내특판</form:option>
			</form:select>
			<%--
			<form:select path="searchRequestStateCode" title="선택하세요" cssClass="va_m">
				<form:option value="" label="진행상태" />
				<form:option value="00">접수</form:option>
				<form:option value="01">접수완료</form:option>
				<form:option value="10">배송대기</form:option>
				<form:option value="11">배송중</form:option>
				<form:option value="12">배송완료</form:option>
				<form:option value="20">개통대기</form:option>
				<form:option value="21">개통완료</form:option>
			</form:select>
			--%>
			<form:select path="searchSelect" title="선택하세요" cssClass="va_m">
				<form:option value="uniqueAll" label="고객식별번호" />
				<%-- <form:option value="" label="" /> --%>
				<form:option value="cstmrName" label="고객명" />
				<form:option value="cstmrPrivateCname" label="개인사업자 상호명" />
				<form:option value="cstmrJuridicalCname" label="법인사업자 법인명" />
				<form:option value="cstmrTelRn" label="전화번호 뒷자리" />
				<form:option value="cstmrMobileRn" label="휴대폰번호 뒷자리" />
				<form:option value="cstmrAddr" label="주소" />
				<form:option value="cstmrMail" label="이메일" />				
				<form:option value="resNo" label="예약번호" />
			</form:select>
			<form:input path="searchValue" size="20" cssClass="validate[funcCall[searchCheck]] input_box" cssStyle="ime-mode:active;width:100px;" />
			<input type="image" src="<c:url value='/images/mcp/btn_board_search.gif'/>" id="검색" class="va_t"  onclick="searchList();"/>
		</div>
	</fieldset>
		
	<!-- 버튼영역 -->
	<fieldset id="board_search">
	
	<h4 class="hidden">버튼</h4>
	<div id="board_sel">
		<input type="button" class="button_box" value="액셀저장" onclick="excelDownload();">
	</div>
	</fieldset>
	<!-- //버튼영역 -->
	
	<!-- //검색영역 -->
</div>
<!-- //board_header -->
<table cellpadding="0" class="tb_base" summary="수정 및 삭제 여부테이블 입니다.">
	<caption><span>목록</span></caption>
	<colgroup>
	<col width="5%" />
	<col width="8%" />
	<col width="10%"/>
	<col width="10%" />
	<col width="10%" />
	<col width="9%" />
	<col width="18%" />
	<col width="8%"/>
	<col width="7%"/>
	<col width="8%"/>
	<col width="7%"/>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">고객명</th>
			<th scope="col">주민번호</th>
			<th scope="col">휴대폰번호</th>
			<th scope="col">서비스구분</th>
			<th scope="col">구분</th>
			<th scope="col">요금제</th>
			<th scope="col">신청일자</th> 
			<th scope="col">진행상태</th>
			<th scope="col">처리상태</th>
			<th scope="col">관리</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${requestList }" var="item" varStatus="i">
			<c:set var="a" value="${item.cstmrNativeRrn}" />
			<c:set var="b" value="${item.rdate}" />
		<tr>
			<td scope="row">${requestSearchVO.totalRecordCount-((requestSearchVO.currentPageNo-1)*requestSearchVO.recordCountPerPage)-(i.count-1) }</td>
			<td style="text-align: center;">${item.cstmrName}</td>
			<td style="text-align: center;">
				<c:if test="${item.cstmrType ne 'FN'}"><c:out value="${fn:substring(a,0,6)}" />-<c:out value="${fn:substring(a,6,13)}" /></c:if>
			</td>
			<td style="text-align: center;">${item.cstmrMobile}</td>
			<td style="text-align: center;">
				<c:choose>
					<c:when test="${item.serviceType eq 'PP'}">선불</c:when>
					<c:when test="${item.serviceType eq 'PO'}">후불</c:when>
				</c:choose>
			</td>
			<td style="text-align: center;">
			<c:choose>
				<c:when test="${item.cntpntShopId eq '1100011741'}">온라인</c:when>
				<c:when test="${item.cntpntShopId eq '1100011744'}">사내특판</c:when>
				<c:when test="${item.cntpntShopId eq '1100011745'}">프리피아</c:when>
			</c:choose>
			</td>
			<td style="text-align: left;">${item.rateName }</td>
			<td style="text-align: center;"><c:out value="${fn:substring(b,0,10)}" /></td>
			<td style="text-align: center;">
			<c:choose>
				<c:when test="${item.requestStateCode eq '00'}">접수</c:when>
				<c:when test="${item.requestStateCode eq '01'}">접수완료</c:when>
				<c:when test="${item.requestStateCode eq '10'}">배송대기</c:when>
				<c:when test="${item.requestStateCode eq '11'}">배송중</c:when>
				<c:when test="${item.requestStateCode eq '12'}">배송완료</c:when>
				<c:when test="${item.requestStateCode eq '20'}">개통대기</c:when>
				<c:when test="${item.requestStateCode eq '21'}">개통완료</c:when>
			</c:choose>
			</td>
			<td style="text-align: center;">
				<c:choose>
					<c:when test="${item.pstate eq '00'}">정상</c:when>
					<c:when test="${item.pstate eq '10'}">고객취소</c:when>
					<c:when test="${item.pstate eq '20'}">관리자삭제</c:when>
				</c:choose>
			</td>
			<td>
				<a id="view" href="#none" onclick="fnView('${item.requestKey }'); return false;">
					<img src="<c:url value='/images/mcp/btn_view.gif'/>" alt="보기" />
				</a> 
			</td>
		</tr>
		</c:forEach>
		
		<c:if test="${empty requestList }">
		<tr>
			<td colspan="10">
				검색된 정보가 없습니다.
			</td>
		</tr>		
		</c:if>
		
	</tbody>
</table>
<!-- 페이징 -->
<ui:pagination paginationInfo="${requestSearchVO }" type="image" jsFunction="cfnPageLink" />
<!-- //페이징 -->

</form:form>
</body>
</html>