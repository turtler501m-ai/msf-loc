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
<title> 가입신청서 관리 </title>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />

<style type="text/css">
.write tr th {text-align:left;}
.webOnly{color:green;}
</style>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/mcp/ad_content.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/mcp/ad_layout.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/mcp/base.css' />" />

<script src="<c:url value='/lib/jquery/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript">
//<!--
var viewStateHtml = $('#viewState').html();

function updateState(r){
	//String requestKey, String requestStateCode, String dlvryNo, String openNo, String memo
	var id = $('.tr'+r).attr('id');
	
	var requestKey = $('#requestKey').val();
	var requestStateCode = id;
	var resNo = '';
	var dlvryNo = '';
	var openNo = '';
	var memo = '';
	
	if($('#resNo_'+id).length > 0){
		if($('#resNo_'+id).val().length < 1){
			if(!confirm('예약번호 없이 완료처리 하시겠습니까?')){
				return;
			}
		}else{
			if(!jQuery.isNumeric($('#resNo_'+id).val())){
				alert("예약번호를 숫자로만 입력하세요.");
				return;
			}
		}
		resNo = $('#resNo_'+id).val();
	}
	if($('#dlvryNo_'+id).length > 0){
		if($('#dlvryNo_'+id).val().length < 1){
			if(!confirm('송장번호 없이 완료처리 하시겠습니까?')){
				return;
			}
		}else{
			if(!jQuery.isNumeric($('#dlvryNo_'+id).val())){
				alert("송장번호를 숫자로만 입력하세요.");
				return;
			}
		}
		dlvryNo = $('#dlvryNo_'+id).val();
	}
	if($('#openNo_'+id).length > 0){
		if($('#openNo_'+id).val().length < 1){
			if(!confirm('개통번호 없이 완료처리 하시겠습니까?')){
				return;
			}
		}else{
			if( $('#openNo_'+id).val().length != 11 || !jQuery.isNumeric($('#openNo_'+id).val())){
				alert("개통번호 11자리를 숫자로만 입력하세요.");
				return;
			}
		}
		openNo = $('#openNo_'+id).val();
	}
	if($('#memo_'+id)) memo = $('#memo_'+id).val();
	
	if(confirm('완료처리 하시겠습니까?')){
		requestDWR.updateState(requestKey, requestStateCode, resNo, dlvryNo, openNo, memo, $('#userId').val(), $('#userIp').val(), {
			callback: function(result) {
				if(result > 0){
					alert('개통진행 상태가 변경되었습니다.');
				}else{
					alert('개통진행 상태 변경중 오류가 발생하였습니다.');
				}
			}, async:true
		});
		
		setTimeout('updateStateList()', 100);
	}
}

function rollbackState(requestStateKey){	
	if(confirm('현재 단계를 취소처리 하시겠습니까?')){
		var requestKey = $('#requestKey').val();

		requestDWR.rollBackState(requestKey, requestStateKey, {
			callback: function(result) {
				if(result > 0){
					alert('개통진행 상태가 변경되었습니다.');
				}else{
					alert('개통진행 상태 변경중 오류가 발생하였습니다.');
				}
			}, async:true
		});
		
		setTimeout('updateStateList()', 100);
	}
}

function updateStateList(){
	//
	$.blockUI({ 
			overlayCSS: { cursor:'cursor'},
            message: $('#viewState'), 
            css: { cursor:'cursor', top:  ($(window).height() - 400) /2 + 'px', left: ($(window).width() - 880) /2 + 'px', width: '880px' } 
        }); 
	
	var html = '';
	html += "<tr id=\"00\" class=\"tr1\">";
	html += "	<td class=\"td1\">접수</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"></td>";
	html += "	<td class=\"td4\"></td>";
	html += "	<td class=\"td5\"></td>";
	html += "	<td class=\"td6\"></td>";
	html += "	<td class=\"td7\"></td>";
	html += "</tr>";
	html += "<tr id=\"01\" class=\"tr2\">";
	html += "	<td class=\"td1\">접수완료</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"><input type=\"text\" id=\"resNo_01\" style=\"width:96%;\" maxlength=\"20\"/></td>";
	html += "	<td class=\"td4\"></td>";
	html += "	<td class=\"td5\"></td>";
	html += "	<td class=\"td6\"><input type=\"text\" id=\"memo_01\" style=\"width:96%;\" maxlength=\"25\"/></td>";
	html += "	<td class=\"td7\" style=\"text-align:center;\"></td>";
	html += "</tr>";
	html += "<tr id=\"10\" class=\"tr3\">";
	html += "	<td class=\"td1\">배송대기</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"></td>";
	html += "	<td class=\"td4\"></td>";
	html += "	<td class=\"td5\"></td>";
	html += "	<td class=\"td6\"><input type=\"text\" id=\"memo_10\" style=\"width:96%;\" maxlength=\"25\"/></td>";
	html += "	<td class=\"td7\" style=\"text-align:center;\"></td>";
	html += "</tr>";
	html += "<tr id=\"11\" class=\"tr4\">";
	html += "	<td class=\"td1\">배송중</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"></td>";
	html += "	<td class=\"td4\"><input type=\"text\" id=\"dlvryNo_11\" style=\"width:96%;\" maxlength=\"20\"/></td>";
	html += "	<td class=\"td5\"></td>";
	html += "	<td class=\"td6\"><input type=\"text\" id=\"memo_11\" style=\"width:96%;\" maxlength=\"25\"/></td>";
	html += "	<td class=\"td7\" style=\"text-align:center;\"></td>";
	html += "</tr>";
	html += "<tr id=\"12\" class=\"tr5\">";
	html += "	<td class=\"td1\">배송완료</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"></td>";
	html += "	<td class=\"td4\"></td>";
	html += "	<td class=\"td5\"></td>";
	html += "	<td class=\"td6\"><input type=\"text\" id=\"memo_12\" style=\"width:96%;\" maxlength=\"25\"/></td>";
	html += "	<td class=\"td7\" style=\"text-align:center;\"></td>";
	html += "</tr>";
	html += "<tr id=\"20\" class=\"tr6\">";
	html += "	<td class=\"td1\">개통대기</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"></td>";
	html += "	<td class=\"td4\"></td>";
	html += "	<td class=\"td5\"></td>";
	html += "	<td class=\"td6\"><input type=\"text\" id=\"memo_20\" style=\"width:96%;\" maxlength=\"25\"/></td>";
	html += "	<td class=\"td7\" style=\"text-align:center;\"></td>";
	html += "</tr>";
	html += "<tr id=\"21\" class=\"tr7\">";
	html += "	<td class=\"td1\">개통완료</td>";
	html += "	<td class=\"td2\"></td>";
	html += "	<td class=\"td3\"></td>";
	html += "	<td class=\"td4\"></td>";
	html += "	<td class=\"td5\"><input type=\"text\" id=\"openNo_21\" style=\"width:96%;\" maxlength=\"11\"/></td>";
	html += "	<td class=\"td6\"><input type=\"text\" id=\"memo_21\" style=\"width:96%;\" maxlength=\"25\"/></td>";
	html += "	<td class=\"td7\" style=\"text-align:center;\"></td>";
	html += "</tr>";
	$('#viewStateBody').html(html);
	
	requestDWR.selectRequestStateList($('#requestKey').val(), {
		callback: function(result) {
			if(result.length > 0){
				var i = 0;
				for(i=0; i<result.length; i++){
					//alert(result[i].memo);
					//alert(result[i].requestStateKey);
					$('#'+result[i].requestStateCode).css('background-color', '#efefef');
					$('#'+result[i].requestStateCode+' .td2').html(result[i].rdate);
					if(result[i].resNo != null) $('#'+result[i].requestStateCode+' .td3 input').attr('value', result[i].resNo);
					if(result[i].dlvryNo != null) $('#'+result[i].requestStateCode+' .td4 input').attr('value', result[i].dlvryNo);
					if(result[i].openNo != null) $('#'+result[i].requestStateCode+' .td5 input').attr('value', result[i].openNo);
					if(result[i].memo != null) $('#'+result[i].requestStateCode+' .td6 input').attr('value', result[i].memo);
				}
				if(result.length > 1){
					$('.tr'+(i) +' .td7').html("<input type='button' value='취소처리' onclick=\"rollbackState('"+result[i-1].requestStateKey+"')\"> ");
				}
				$('.tr'+(i+1) +' .td7').html("<input type='button' value='완료처리' onclick=\"updateState('"+(i+1)+"');\"> ");
				//본문의 개통진행 상태 selectbox update
				$('#requestStateCode').attr('value', result[i-1].requestStateCode);
			}
		}, async:false
	});
}

function goPrint(){
	window.open("/request/viewPrint.do?requestKey="+$('#requestKey').val(), "printWindow", "width=880, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
}

function viewUpdateAgentAndManager(requestKey, agentCode){
	//alert(requestKey + "/" + agentCode);
	var managerCode = "";
	var type01 = "";
	var type02 = "";
	var type03 = "";
	var type04 = "";
	var nm = "";
	var tel = "";
	var mobile = "";
	
	if(agentCode == 'A0001'){ //ktism.com
		managerCode = 'M0001';
		updateAgentAndManager(requestKey, agentCode, managerCode, type01, type02, type03, type04, nm, tel, mobile);
	}else{
		$.blockUI({ 
			overlayCSS: { cursor:'cursor'},
	        message: $('#viewManagerInfo'), 
	        css: { cursor:'cursor', top:  180 + 'px', left: ($(window).width() - 800) /2 + 'px', width: '800px' } 
	    });
		
		$('#nm').attr('value', '${requestVO.cstmrName}');
		$('#tel').attr('value', '${requestVO.cstmrTelFn}-${requestVO.cstmrTelMn}-${requestVO.cstmrTelRn}');
		$('#mobile').attr('value', '${requestVO.cstmrMobileFn}-${requestVO.cstmrMobileMn}-${requestVO.cstmrMobileRn}');
		
		$('#btnManagerInfo').unbind('click').bind('click', function(){
			if($('#managerCode').val().length < 4){
				alert('사원번호를 입력하세요.');
				return;
			}
			if($('#nm').val().length < 3){
				alert('이름을 입력하세요.');
				return;
			}
			var filter = /[^\-|^0-9]/;
			if($('#tel').val().length < 9 || filter.test($('#tel').val())){
				alert('전화번호를 확인하세요(숫자와 대쉬(-)만 허용됩니다.)');
				return;
			}
			if($('#mobile').val().length < 9 || filter.test($('#mobile').val())){
				alert('휴대폰번호를 확인하세요(숫자와 대쉬(-)만 허용됩니다.)');
				return;
			}
			
			managerCode = $('#managerCode').val();
			type01 = $('#type01').val();
			type02 = $('#type02').val();
			type03 = $('#type03').val();
			type04 = $('#type04').val();
			nm = $('#nm').val();
			tel = $('#tel').val();
			mobile = $('#mobile').val();
			
			updateAgentAndManager(requestKey, agentCode, managerCode, type01, type02, type03, type04, nm, tel, mobile);
		});
	}
}

function updateAgentAndManager(requestKey, agentCode, managerCode, type01, type02, type03, type04, nm, tel, mobile){
	if(confirm('모집 경로를 변경하시겠습니까?')){
		requestDWR.updateAgentAndManager(requestKey, agentCode, managerCode, type01, type02, type03, type04, nm, tel, mobile, {
			callback: function(result) {
				if(result > 0){
					alert('모집 경로가 변경되었습니다.');
					$.unblockUI();
					return;
				}else{
					alert('모집 경로 변경중 오류가 발생하였습니다.');
					return;				
				}
			}, async:false
		});	
	}
}

$(function(){
	
	$('#btnPstate').bind('click', function(){
		requestDWR.updatePstate($('#requestKey').val(), $('#pstate').val(), {
			callback: function(result) {
				if(result > 0){
					alert('신청서 상태가 변경되었습니다.');
					/*
					if($('#pstate').val() == '20'){ //관리자 삭제처리시 목록으로 이동
						$('#requestForm').attr('action', 'list.do');
						$('#requestForm').submit();
					}
					*/
					return;
				}else{
					alert('신청서 상태 변경중 오류가 발생하였습니다.');
					return;				
				}
			}, async:false
		});
	});
});
//-->
</script>
</head>
<body>

<div id="printArea">
<form:form commandName="requestVO" name="requestForm" id="requestForm" method="post" action="">
<form:hidden path="requestKey" />
<!-- 
<input type="hidden" id="userId" value="${userId}" />
<input type="hidden" id="userIp" value="${userIp}"/>
 -->

<input type="hidden" id="menuKey" name="menuKey" value="${requestSearchVO.menuKey }" />
<input type="hidden" id="searchSelect" name="searchSelect" value="${requestSearchVO.searchSelect}" />
<input type="hidden" id="searchValue" name="searchValue" value="${requestSearchVO.searchValue }" />

<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">기본 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">고객구분</th>
		<td>
			<b>
			<c:if test="${requestVO.cstmrType eq 'NA'}">내국인(19세이상)</c:if>
			<c:if test="${requestVO.cstmrType eq 'NM'}">내국인(미성년자)</c:if>
			<c:if test="${requestVO.cstmrType eq 'FN'}">외국인</c:if>
			<c:if test="${requestVO.cstmrType eq 'PP'}">개인사업자</c:if>
			<c:if test="${requestVO.cstmrType eq 'JP'}">법인사업자</c:if>
			</b>
		</td>
	</tr>
	<tr>
		<th scope="row">서비스구분</th>
		<td>
			<b>
			<c:if test="${requestVO.serviceType eq 'PP'}">선불</c:if>
			<c:if test="${requestVO.serviceType eq 'PO'}">후불</c:if>
			</b>
		</td>
	</tr>
	<tr>
		<th scope="row">요금제</th>
		<td><b>${requestVO.rateName } (코드:${requestVO.rateCode })</b></td>
	</tr>
	<tr>
		<th scope="row">업무구분</th>
		<td>
			<b>
			<c:if test="${requestVO.operType eq 'NAC3'}">신규가입</c:if>
			<c:if test="${requestVO.operType eq 'MNP3'}">번호이동</c:if>
			<c:if test="${requestVO.operType eq 'HCN3'}">기기변경</c:if>
			<c:if test="${requestVO.operType eq 'HDN3'}">보상기변3G</c:if>
			</b>
		</td>
	</tr>
	<tr>
		<th scope="row">고객명</th>
		<td><b>${requestVO.cstmrName}</b></td>
	</tr>
	
	<c:if test="${requestVO.cstmrType eq 'NA' or requestVO.cstmrType eq 'NM' or requestVO.cstmrType eq 'PP' or requestVO.cstmrType eq 'JP'}">
	<tr>
		<th scope="row">주민등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.cstmrNativeRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />
			-
			<c:out value="${fn:substring(a,6,13)}" />
			<%-- <c:out value="${fn:substring(a,6,13)}" /> --%>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${requestVO.cstmrType eq 'FN'}">
	<tr>
		<th scope="row">외국인 국적</th>
		<td>${requestVO.cstmrForeignerNation}</td>
	</tr>
	<tr>
		<th scope="row">외국인 여권번호</th>
		<td>${requestVO.cstmrForeignerPn}</td>
	</tr>
	<tr>
		<th scope="row">외국인 등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.cstmrForeignerRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />-<c:out value="${fn:substring(a,6,13)}" />
		</td>
	</tr>
	<tr>
		<th scope="row">외국인 체류기간</th>
		<td>${requestVO.cstmrForeignerSdate} ~ ${requestVO.cstmrForeignerEdate}</td>
	</tr>
	</c:if>
	
	<c:if test="${requestVO.cstmrType eq 'PP'}">
	<tr>
		<th scope="row">개인사업자 상호명</th>
		<td>${requestVO.cstmrPrivateCname}</td>
	</tr>
	<tr>
		<th scope="row">개인사업자 사업자번호</th>
		<td>
			<c:set var="a" value="${requestVO.cstmrPrivateNumber}" />
			<c:out value="${fn:substring(a,0,3)}" />-<c:out value="${fn:substring(a,3,5)}" />-<c:out value="${fn:substring(a,5,10)}" />
		</td>
	</tr>
	</c:if>
	
	<c:if test="${requestVO.cstmrType eq 'JP'}">
	<tr>
		<th scope="row">법인사업자 법인명</th>
		<td>${requestVO.cstmrJuridicalCname}</td>
	</tr>
	<tr>
		<th scope="row">법인사업자 등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.cstmrJuridicalRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />-<c:out value="${fn:substring(a,6,13)}" />
		</td>
	</tr>
	<tr>
		<th scope="row">법인사업자 사업자번호</th>
		<td>
			<c:set var="a" value="${requestVO.cstmrJuridicalNumber}" />
			<c:out value="${fn:substring(a,0,3)}" />-<c:out value="${fn:substring(a,3,5)}" />-<c:out value="${fn:substring(a,5,10)}" />
		</td>
	</tr>
	</c:if>
	
	<tr>
		<th scope="row">전화번호</th>
		<td>${requestVO.cstmrTel}</td>
	</tr>
	<tr>
		<th scope="row">휴대폰번호</th>
		<td>${requestVO.cstmrMobile}</td>
	</tr>	
	<tr>
		<th scope="row">메일</th>
		<td>
			${requestVO.cstmrMail}
			(메일수신 : 
			<c:choose>
				<c:when test="${requestVO.cstmrMailReceiveFlag eq 'Y'}" >예</c:when>
				<c:when test="${requestVO.cstmrMailReceiveFlag eq 'N'}" >아니오</c:when>
			</c:choose>
			)		
		</td>
	</tr>	
	<tr>
		<th scope="row">주소</th>
		<td>
		<c:set var="a" value="${requestVO.cstmrPost}" />
			(${fn:substring(a,0,3)}-${fn:substring(a,3,6)}) ${requestVO.cstmrAddr } ${requestVO.cstmrAddrDtl }
		</td>
	</tr>	
	<tr>
		<th scope="row">명세서수신유형</th>
		<td>
			<c:if test="${requestVO.cstmrBillSendCode eq 'LX'}">우편명세서</c:if>
			<c:if test="${requestVO.cstmrBillSendCode eq 'CB'}">이메일명세서</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">신청일자</th>
		<td>${requestVO.rdate} (IP : ${requestVO.rip})</td>
	</tr>
	<tr>
		<th scope="row">온라인 본인 인증방식</th>
		<td>
			<c:if test="${requestVO.onlineAuthType eq 'C'}">신용카드 인증</c:if>
			<c:if test="${requestVO.onlineAuthType eq 'M'}">휴대폰 인증</c:if>
			<c:if test="${requestVO.onlineAuthType eq 'X'}">범용 공인인증서 인증</c:if>
			<c:if test="${requestVO.onlineAuthType eq 'FY'}">FAX 인증(<b>인증 완료</b>)</c:if>
			<c:if test="${requestVO.onlineAuthType eq 'FN'}">FAX 인증(<b>인증 대기</b>)</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">온라인 본인 인증정보</th>
		<td>${requestVO.onlineAuthInfo}</td>
	</tr>
	<!-- 00=정상, 10=고객취소, 20=관리자삭제 -->
	<tr>
		<th scope="row">신청서 상태</th>
		<td>
			<form:select path="pstate" disabled="true">
				<form:option value="00">정상</form:option>
				<form:option value="10">고객취소</form:option>
				<form:option value="20">관리자삭제</form:option>
			</form:select>
			<!-- 
			 <input id="btnPstate" type="button" value="변경 적용" />
			 -->
		</td>
	</tr>
	<!-- 00=접수대기, 01=접수, 10=배송중, 20=개통대기, 21=개통완료  -->
	<tr>
		<th scope="row">개통진행 상태</th>
		<td>
			<form:select path="requestStateCode" disabled="true">
				<form:option value="00">접수</form:option>
				<form:option value="01">접수완료</form:option>
				<form:option value="10">배송대기</form:option>
				<form:option value="11">배송중</form:option>
				<form:option value="12">배송완료</form:option>
				<form:option value="20">개통대기</form:option>
				<form:option value="21">개통완료</form:option>
			</form:select>
			<!-- 
			<input id="btnViewState" type="button" value="진행상태  상세보기 & 변경" onclick="updateStateList();" />
			 -->
		</td>
	</tr>
	<tr>
		<th scope="row">모집 경로</th>
		<td>
			<form:select path="cntpntShopId" disabled="true">
				<form:option value="1100011741">ktis.com(기본)</form:option>
				<form:option value="1100011744">사내특판</form:option>
			</form:select>
			<!-- 
			<input type="button" value="모집경로 변경" onclick="viewUpdateAgentAndManager('${requestVO.requestKey}', $('#agentCode').val());" />
			 -->
		</td>
	</tr>
</tbody>
</table>
<br/>

<c:if test="${requestVO.cstmrType eq 'NM'}">
<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">미성년자 법정대리인 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">신청인과의 관계</th>
		<td>
			<c:choose>
				<c:when test="${requestVO.minorAgentRelation eq '01'}">부</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '02'}">모</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '03'}">후견인</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '04'}">연대보증인</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '05'}">가족</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '06'}">친척</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '07'}">친구</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '08'}">회사동료</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '10'}">그외</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '11'}">한정위탁</c:when>
				<c:when test="${requestVO.minorAgentRelation eq '12'}">지정위탁</c:when>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">법정대리인 성명</th>
		<td>${requestVO.minorAgentName}</td>
	</tr>
	<tr>
		<th scope="row">법정대리인 주민등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.minorAgentRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />
			-
			<c:out value="${fn:substring(a,6,13)}" />
		</td>
	</tr>
	<tr>
		<th scope="row">법정대리인 연락처</th>
		<td>${requestVO.minorAgentTel}</td>
	</tr>
</tbody>
</table>
<br/>
</c:if>


<c:if test="${requestVO.cstmrType eq 'JP'}">
<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">법인 - 대리인 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">대리인 성명</th>
		<td>
			${requestVO.jrdclAgentName}
		</td>
	</tr>
	<tr>
		<th scope="row">대리인 주민등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.jrdclAgentRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />
			-
			<c:out value="${fn:substring(a,6,13)}" />
		</td>
	</tr>
	<tr>
		<th scope="row">대리인 연락처</th>
		<td>${requestVO.jrdclAgentTel}</td>
	</tr>
</tbody>
</table>
<br/>
</c:if>


<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">배송 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">받는사람 성명</th>
		<td>${requestVO.dlvryName}
		</td>
	</tr>
	<tr>
		<th scope="row">받는사람 전화번호</th>
		<td>
			${requestVO.dlvryTel}
		</td>
	</tr>
	<tr>
		<th scope="row">받는사람 휴대폰번호</th>
		<td>
			${requestVO.dlvryMobile}
		</td>
	</tr>
	<tr>
		<th scope="row">받는사람 주소</th>
		<td>
			<c:set var="a" value="${requestVO.dlvryPost}" />
			(${fn:substring(a,0,3)}-${fn:substring(a,3,6)}) ${requestVO.dlvryAddr } ${requestVO.dlvryAddrDtl }
		</td>
	</tr>
	<tr>
		<th scope="row">받는사람 요청사항</th>
		<td>${requestVO.dlvryMemo}
		</td>
	</tr>
</tbody>
</table>
<br/>

<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">신청 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">구매유형</th>
		<td>
			<c:if test="${requestVO.reqBuyType eq 'UU'}">유심단독 구매</c:if>
			<c:if test="${requestVO.reqBuyType eq 'MM'}">단말 구매</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">휴대폰모델</th>
		<td>${requestVO.reqModelName}</td>
	</tr>
	<tr>
		<th scope="row">희망번호</th>
		<td>${requestVO.reqWantNumber}</td>
	</tr>
	<tr>
		<th scope="row">요금납부방법</th>
		<td>
			<c:if test="${requestVO.reqPayType eq 'C'}">신용카드</c:if>
			<c:if test="${requestVO.reqPayType eq 'D'}">자동이체</c:if>
			<c:if test="${requestVO.reqPayType eq 'R'}">지로</c:if>
			<c:if test="${requestVO.reqPayType eq 'VA'}">가상계좌</c:if>
			<c:if test="${requestVO.reqPayType eq 'AA'}">자동충전(계좌이체)</c:if>
		</td>
	</tr>
	<c:if test="${requestVO.reqPayType eq 'D' or requestVO.reqPayType eq 'AA'}">
	<tr>
		<th scope="row">은행</th>
		<td>
			<c:if test="${requestVO.reqBank eq '39'}">경남은행</c:if>
			<c:if test="${requestVO.reqBank eq '34'}">광주은행</c:if>
			<c:if test="${requestVO.reqBank eq '04'}">국민은행</c:if>
			<c:if test="${requestVO.reqBank eq '03'}">기업은행</c:if>
			<c:if test="${requestVO.reqBank eq '11'}">농협</c:if>
			<c:if test="${requestVO.reqBank eq '31'}">대구은행</c:if>
			<c:if test="${requestVO.reqBank eq '32'}">부산은행</c:if>
			<c:if test="${requestVO.reqBank eq '02'}">산업은행</c:if>
			<c:if test="${requestVO.reqBank eq '45'}">새마을금고</c:if>
			<c:if test="${requestVO.reqBank eq '07'}">수협중앙</c:if>
			<c:if test="${requestVO.reqBank eq '48'}">신협</c:if>
			<c:if test="${requestVO.reqBank eq '88'}">신한은행(구 조흥)</c:if>
			<c:if test="${requestVO.reqBank eq '05'}">외환은행</c:if>
			<c:if test="${requestVO.reqBank eq '20'}">우리은행</c:if>
			<c:if test="${requestVO.reqBank eq '71'}">우체국</c:if>
			<c:if test="${requestVO.reqBank eq '37'}">전북은행</c:if>
			<c:if test="${requestVO.reqBank eq '35'}">제주은행</c:if>
			<c:if test="${requestVO.reqBank eq '23'}">제일은행</c:if>
			<c:if test="${requestVO.reqBank eq '81'}">하나은행</c:if>
			<c:if test="${requestVO.reqBank eq '27'}">씨티은행</c:if>
			(${requestVO.reqBank})
		</td>
	</tr>
	<tr>
		<th scope="row">계좌번호</th>
		<td>${requestVO.reqAccountNumber}</td>
	</tr>
	<tr>
		<th scope="row">예금주</th>
		<td>${requestVO.reqAccountName}</td>
	</tr>
	<tr>
		<th scope="row">예금주 주민등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.reqAccountRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />
			-
			<c:out value="${fn:substring(a,6,13)}" />
			<%-- <c:out value="${fn:substring(a,6,13)}" /> --%>
		</td>
	</tr>
	<!-- 
	<tr>
		<th scope="row">예금주와의관계</th>
		<td>${requestVO.reqAccountRelation}</td>
	</tr>
	 -->
	</c:if>
	<c:if test="${requestVO.reqPayType eq 'C'}">
	<tr>
		<th scope="row">신용카드 번호</th>
		<td>
			[${requestVO.reqCardCompany}]
			<c:set var="a" value="${requestVO.reqCardNo}" />
			<c:out value="${fn:substring(a,0,4)}" />-<c:out value="${fn:substring(a,4,8)}" />-<c:out value="${fn:substring(a,8,12)}" />-<c:out value="${fn:substring(a,12,16)}" />
			(${requestVO.reqCardMm}월/${requestVO.reqCardYy}년)
			<%-- <c:out value="${fn:substring(a,4,8)}" /> <c:out value="${fn:substring(a,8,12)}" />--%>
		</td>
	</tr>
	<tr>
		<th scope="row">신용카드 명의자</th>
		<td>${requestVO.reqCardName}</td>
	</tr>
	<tr>
		<th scope="row">신용카드 명의자 주민등록번호</th>
		<td>
			<c:set var="a" value="${requestVO.reqCardRrn}" />
			<c:out value="${fn:substring(a,0,6)}" />
			-
			<c:out value="${fn:substring(a,6,13)}" />
			<%-- <c:out value="${fn:substring(a,6,13)}" /> --%>
		</td>
	</tr>
	</c:if>
	<c:if test="${requestVO.reqPayType eq 'AC'}">
	<tr>
		<th scope="row"><span class="webOnly">자동충전 방법</span></th>
		<td>
			<c:if test="${requestVO.reqAcType eq '01'}">잔액지정</c:if>
			<c:if test="${requestVO.reqAcType eq '02'}">날짜지정</c:if>
		</td>
	</tr>
		<c:if test="${requestVO.reqAcType eq '01'}">
		<tr>
			<th scope="row"><span class="webOnly">잔액지정</span></th>
			<td>${requestVO.reqAc01Balance}원 도달시 ${requestVO.reqAc01Amount}원 충전</td>
		</tr>
		</c:if>
		<c:if test="${requestVO.reqAcType eq '02'}">
		<tr>
			<th scope="row"><span class="webOnly">날짜지정방식</span></th>
			<td>매월 ${requestVO.reqAc02Day}일에 ${requestVO.reqAc02Amount}원 충전</td>
		</tr>
		</c:if>
	</c:if>
	<tr>
		<th scope="row"><span class="webOnly">번호연결서비스 사용</span></th>
		<td>
			<c:choose>
				<c:when test="${requestVO.reqGuideFlag eq 'Y'}">예</c:when>
				<c:otherwise>아니오</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th scope="row"><span class="webOnly">번호연결서비스 번호</span></th>
		<td>${requestVO.reqGuide}</td>
	</tr>
	<tr>
		<th scope="row"><span class="webOnly">무선데이터 이용</span></th>
		<td>
			<c:if test="${requestVO.reqWireType eq 'AY'}">이용</c:if>
			<c:if test="${requestVO.reqWireType eq 'AN'}">차단</c:if>
			<c:if test="${requestVO.reqWireType eq 'DN'}">데이터로밍 차단</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row"><span class="webOnly">부가서비스</span></th>
		<td>
			<c:choose>
				<c:when test="${empty requestVO.reqAddition}">없음</c:when>
				<c:otherwise>${requestVO.reqAddition}</c:otherwise>
			</c:choose>
		</td>
	</tr>	
</tbody>
</table>
<br/>

<c:if test="${requestVO.operType eq 'MNP3'}">
<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">번호이동 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">변경전 통신사</th>
		<td>${requestVO.moveCompany}</td>
	</tr>
	<tr>
		<th scope="row">이동할 전화번호</th>
		<td>${requestVO.moveMobile}</td>
	</tr>
	<tr>
		<th scope="row">인증번호 유형</th>
		<td>
			<c:if test="${requestVO.moveAuthType eq '2'}">단말기 일련번호</c:if>
			<c:if test="${requestVO.moveAuthType eq '3'}">요금납부 계좌번호</c:if>
			<c:if test="${requestVO.moveAuthType eq '5'}">요금납부 신용카드</c:if>
			<!-- <c:if test="${requestVO.moveAuthType eq '4'}">KT합산</c:if> -->
			뒤 4자리
		</td>
	</tr>
	<tr>
		<th scope="row">인증번호 4자리</th>
		<td>${requestVO.moveAuthNumber}</td>
	</tr>
	<tr>
		<th scope="row"><span class="webOnly">이번달사용요금 납부방법</span></th>
		<td>
			<c:if test="${requestVO.moveThismonthPayType eq 'SP'}">즉시납부</c:if>
			<c:if test="${requestVO.moveThismonthPayType eq 'NM'}">다음달요금합산</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row"><span class="webOnly">휴대폰 할부금 납부상태</span></th>
		<td>
			<c:if test="${requestVO.moveAllotmentStat eq 'FP'}">완납</c:if>
			<c:if test="${requestVO.moveAllotmentStat eq 'AD'}">할부지속</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row"><span class="webOnly">미환급액 요금상계 동의 여부</span></th>
		<td>
			<c:if test="${requestVO.moveRefundAgreeFlag eq 'Y'}">동의</c:if>
			<c:if test="${requestVO.moveRefundAgreeFlag eq 'N'}">동의안함</c:if>
		</td>
	</tr>
</tbody>
</table>
<br/>
</c:if>

<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">약관동의 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">개인정보 수집 동의</th>
		<td>
			<c:if test="${requestVO.clausePriCollectFlag eq 'Y'}">예</c:if>
			<c:if test="${requestVO.clausePriCollectFlag eq 'N'}">아니오</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">개인정보 제공 동의</th>
		<td>
			<c:if test="${requestVO.clausePriOfferFlag eq 'Y'}">예</c:if>
			<c:if test="${requestVO.clausePriOfferFlag eq 'N'}">아니오</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">고유식별정보 수집이용제공 동의</th>
		<td>
			<c:if test="${requestVO.clauseEssCollectFlag eq 'Y'}">예</c:if>
			<c:if test="${requestVO.clauseEssCollectFlag eq 'N'}">아니오</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">개인정보 위탁 동의</th>
		<td>
			<c:if test="${requestVO.clausePriTrustFlag eq 'Y'}">예</c:if>
			<c:if test="${requestVO.clausePriTrustFlag eq 'N'}">아니오</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">개인정보 광고전송 동의</th>
		<td>
			<c:if test="${requestVO.clausePriAdFlag eq 'Y'}">예</c:if>
			<c:if test="${requestVO.clausePriAdFlag eq 'N'}">아니오</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">신용정보 이용 동의</th>
		<td>
			<c:if test="${requestVO.clauseConfidenceFlag eq 'Y'}">예</c:if>
			<c:if test="${requestVO.clauseConfidenceFlag eq 'N'}">아니오</c:if>
		</td>
	</tr>
</tbody>
</table>

<br/>
<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
<caption><span></span></caption>
<colgroup>
	<col width="20%"/>
	<col width="80%"/>
</colgroup>
<thead>
	<tr>
		<th scope="col" colspan="2">내부솔루션 연동 정보</th>
	</tr>
</thead>
<tbody class="write">
	<tr>
		<th scope="row">내부솔루션 응답코드</th>
		<td>
			${requestVO.resCode}
			<c:choose>
				<c:when test="${requestVO.serviceType eq 'PO' and empty requestVO.resCode}">없음(후불제의 경우 연동안함)</c:when>
				<c:when test="${requestVO.resCode eq '000'}">(정상처리)</c:when>
				<c:otherwise>(오류발생)</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">내부솔루션 메세지 </th>
		<td>${requestVO.resMsg}</td>
	</tr>
	<tr>
		<th scope="row">내부솔루션 예약번호</th>
		<td>${requestVO.resNo}</td>
	</tr>
</tbody>
</table>
</form:form>
</div>

<p class="btn_list">
	<a href="#none" onclick="goPrint();"><img src="<c:url value='/images/mcp/btn_print.gif'/>" alt="인쇄" /></a>
	<a href="list.do"><img src="<c:url value='/images/mcp/btn_list.gif'/>" alt="목록" /></a>
</p>




<div id="viewState" style="display:none;">
	<div style="text-align:right;"><div style="float:right;width:60px;height:16px;background-color:#ccc;text-align:center;cursor:pointer;" onclick="$.unblockUI();"> 창닫기 </div></div>
	<table cellpadding="0" class="tb_base" summary="신청서를 관리하는 테이블입니다.">
	<caption><span></span></caption>
	<colgroup>
		<col width="10%"/>
		<col width="15%"/>
		<col width="13%"/>
		<col width="13%"/>
		<col width="13%"/>
		<col width="25%"/>
		<col width="11%"/>
	</colgroup>
	<thead>
		<tr>
			<th class="td1">진행상태</td>
			<th class="td2">처리일자</td>
			<th class="td3">예약번호</td>
			<th class="td4">송장번호</td>
			<th class="td5">개통번호</td>
			<th class="td6">메모</td>
			<th class="td7">관리</td>
		</tr>
	</thead>
	<tbody id="viewStateBody" class="write">
		<tr id="00" class="tr1">
			<td class="td1">접수</td>
			<td class="td2"></td>
			<td class="td3"></td>
			<td class="td4"></td>
			<td class="td5"></td>
			<td class="td6"></td>
			<td class="td7"></td>
		</tr>
		<tr id="01" class="tr2">
			<td class="td1">접수완료</td>
			<td class="td2"></td>
			<td class="td3"><input type="text" id="resNo_01" style="width:96%;" maxlength="20"/></td>
			<td class="td4"></td>
			<td class="td5"></td>
			<td class="td6"><input type="text" id="memo_01" style="width:96%;" maxlength="25"/></td>
			<td class="td7" style="text-align:center;"></td>
		</tr>
		<tr id="10" class="tr3">
			<td class="td1">배송대기</td>
			<td class="td2"></td>
			<td class="td3"></td>
			<td class="td4"></td>
			<td class="td5"></td>
			<td class="td6"><input type="text" id="memo_10" style="width:96%;" maxlength="25"/></td>
			<td class="td7" style="text-align:center;"></td>
		</tr>
		<tr id="11" class="tr4">
			<td class="td1">배송중</td>
			<td class="td2"></td>
			<td class="td3"></td>
			<td class="td4"><input type="text" id="dlvryNo_11" style="width:96%;" maxlength="20"/></td>
			<td class="td5"></td>
			<td class="td6"><input type="text" id="memo_11" style="width:96%;" maxlength="25"/></td>
			<td class="td7" style="text-align:center;"></td>
		</tr>
		<tr id="12" class="tr5">
			<td class="td1">배송완료</td>
			<td class="td2"></td>
			<td class="td3"></td>
			<td class="td4"></td>
			<td class="td5"></td>
			<td class="td6"><input type="text" id="memo_12" style="width:96%;" maxlength="25"/></td>
			<td class="td7" style="text-align:center;"></td>
		</tr>
		<tr id="20" class="tr6">
			<td class="td1">개통대기</td>
			<td class="td2"></td>
			<td class="td3"></td>
			<td class="td4"></td>
			<td class="td5"></td>
			<td class="td6"><input type="text" id="memo_20" style="width:96%;" maxlength="25"/></td>
			<td class="td7" style="text-align:center;"></td>
		</tr>
		<tr id="21" class="tr7">
			<td class="td1">개통완료</td>
			<td class="td2"></td>
			<td class="td3"></td>
			<td class="td4"></td>
			<td class="td5"><input type="text" id="openNo_21" style="width:96%;" maxlength="11"/></td>
			<td class="td6"><input type="text" id="memo_21" style="width:96%;" maxlength="25"/></td>
			<td class="td7" style="text-align:center;"></td>
		</tr>
	</tbody>
	</table>
</div>

<div id="viewManagerInfo" style="display:none;">
	<div style="text-align:right;"><div style="float:right;width:60px;height:16px;background-color:#ccc;text-align:center;cursor:pointer;" onclick="$.unblockUI();"> 창닫기 </div></div>
	<table cellpadding="0" class="tb_base" summary="">
		<caption><span></span></caption>
		<colgroup>
			<col width="13%"/>
			<col width="13%"/>
			<col width="13%"/>
			<col width="13%"/>
			<col width="13%"/>
			<col width="13%"/>
			<col width="13%"/>
			<col width="9%"/>
		</colgroup>
		<thead>
			<tr>
				<th class="td0">부문</td>
				<th class="td0">소속</td>
				<th class="td0">팀</td>
				<th class="td0"><span style="color:red;">*</span>사원번호</td>
				<th class="td0"><span style="color:red;">*</span>이름</td>
				<th class="td0"><span style="color:red;">*</span>전화번호</td>
				<th class="td0"><span style="color:red;">*</span>휴대폰번호</td>
				<th class="td0"> </td>
			</tr>
		</thead>
		<tbody id="viewManagerInfoBody" class="write">
			<tr id="00" class="tr1">
				<td><input type="text" id="type01" style="width:96%;" maxlength="20"/></td>
				<td><input type="text" id="type02" style="width:96%;" maxlength="20"/></td>
				<td><input type="text" id="type03" style="width:96%;" maxlength="20"/></td>
				<td><input type="text" id="managerCode" style="width:96%;" maxlength="20"/></td>
				<td><input type="text" id="nm" style="width:96%;" maxlength="20"/></td>
				<td><input type="text" id="tel" style="width:96%;" maxlength="20"/></td>
				<td><input type="text" id="mobile" style="width:96%;" maxlength="20"/></td>
				<td><input type="button" id="btnManagerInfo" value="확인" style="width:96%;" maxlength="20" /></td>
			</tr>
	</tbody>
	</table>
</div>

</body>
</html>