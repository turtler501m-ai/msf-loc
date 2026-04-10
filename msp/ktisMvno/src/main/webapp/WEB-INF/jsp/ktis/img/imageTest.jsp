<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript"> 

	function fnScanStart() {
		
		// --------------------------------------
		// 데이터 셋팅
		// 필수 값 
		// 1. resNo : 예약 번호
		// --------------------------------------
		
		
		// --------------------------------------
		// 호출
		// --------------------------------------
		document.frm.action = "scannerAppliaction.do";
		document.frm.method = "post";
		frm.submit();
		
	}
	
	
	function fnScanSearch() {
		
		// --------------------------------------
		// 데이터 셋팅
		// 필수값
		// 1. RES_NO : 예약번호
		// 선택 값
		// 1. PARENT_SCAN_ID : 이미 만들어진 서식지 아이디 
		// --------------------------------------
		document.getElementById("RES_NO").value = 	document.getElementById("resNo").value;
		document.getElementById("PARENT_SCAN_ID").value = 	document.getElementById("scanId").value;
		
		
		// --------------------------------------
		// 호출
		// --------------------------------------
		document.frm.action = "${scanSearch}";
		document.frm.method = "post";
		frm.submit();
	}
	
	
	function fnFaxSearch() {
		
		// --------------------------------------
		// 데이터 셋팅
		// 필수값
		// 1. RES_NO : 예약번호
		// 선택 값
		// 1. PARENT_SCAN_ID : 이미 만들어진 서식지 아이디 
		// --------------------------------------
		document.getElementById("RES_NO").value = 	document.getElementById("resNo").value;
		document.getElementById("PARENT_SCAN_ID").value = 	document.getElementById("scanId").value;

		
		// --------------------------------------
		// 호출
		// --------------------------------------
		document.frm.action = "${faxSerarch}";
		document.frm.method = "post";
		frm.submit();
		
	}
	
	function fnImageViewer() {
		
		
		// --------------------------------------
		// 데이터 셋팅
		// 필수값
		// 1. resNo : 예약번호
		// 2. callType : mcp : C, msp : S
		// --------------------------------------
		document.getElementById("resNo").value = 	document.getElementById("requestKey").value;
		
		// --------------------------------------
		// 호출
		// --------------------------------------
		document.frm.action = "/scannerViewer.do";
		document.frm.method = "post";
		frm.submit();
	}
	
</SCRIPT>
<body>

	<form action="" name="frm" id="frm">

		<input type="hidden" name="RES_NO" id="RES_NO" value="10226">
		<input type="hidden" name="PARENT_SCAN_ID" id="PARENT_SCAN_ID" value="dasdqwdq23123123">
		
		<table>
			<tr>
				<td>
				requestKey : <input type="text" id="requestKey" name="requestKey" value="10495" />
				ResNo  : <input type="text" id="resNo" name="resNo" value="100282" />
				scanId : <input type="text" id="scanId" name="scanId" />
				callType : <input type="text" id="callType" name="callType" value="C" /></td>
			</tr>

			<tr>
				<td><input type="button" id="scanStart" name="scanStart" value="스캔 실행" onclick="fnScanStart()" />
				<input type="button" id="imageViewer" name="imageViewer" value="이미지뷰어" onclick="fnImageViewer()" />
				<input type="button" id="scanSearch" name="scanSearch" value="스캔 검색" onclick="fnScanSearch()" />
				<input type="button" id="faxSearch" name="faxSearch" value="팩스 검색" onclick="fnFaxSearch()" /></td>
			</tr>
		</table>
	</form>

</body>
</html>