<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<title>kt M모바일</title>

<link rel="stylesheet" type="text/css" href="/resources/css/front/common_new.css">

<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/swipe.js"></script>
<script type="text/javascript" src="/resources/js/jquery.placeholder.js"></script>
<!--[if !IE | gte IE 8]> -->
<link rel="stylesheet" type="text/css" href="/resources/css/front/style_new2.css">
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <title>kt M mobile</title>

    <link rel="stylesheet" type="text/css" href="/resources/css/front/common_new.css">

    <script type="text/javascript" src="/resources/js/swipe.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.placeholder.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/front/style_new2.css">
    <script>
        $(document).ready(function () {
        	$('.btn_center_reg_red').click(function(){
        		window.open("about:blank","_self").close();
        	});
        });

        function viewPrint(){
     		window.print();
    	}

        function excelDownload() {
    		$('#frm').attr('target', "tmpFrm").submit();
    	}

    </script>
</head>
<body>
<!-- 	<div class="dim-popup"> -->
	<div id="popup_container">
		<div class="pop-container">
				<div class="popup_print_title">납부내역조회</div>
				<div class="button_area">
					※납부내역 조회는 최근 6개월 이내까지 가능합니다.
					<button title="엑셀저장" type="button" class="btn_excel" onclick="excelDownload();" style="float: right;margin-left: 15px;">엑셀저장</button>
					<button title="납부내역 인쇄" type="button" class="btn_print" onclick="viewPrint();" style="float: right;">납부내역 인쇄</button>
				</div>
			<div class="popup_content">
				<div class="board_detail_table user">
		        	<table summary="고객명,휴대폰 번호로 이루어진 고객정보로 구성된 표">
		        		<caption class="mypage_title_sub text_align_left">고객사항</caption>
		        		<colgroup>
	              			<col width="140px" />
							<col />
							<col width="140px" />
							<col />
	                   	</colgroup>
		        		<tr>
		        			<th scope="row">고객명</th>
		        			<td>${searchVO.userName}</td>
		        			<th scope="row">휴대폰번호</th>
		        			<td>${ctn}</td>
		        		</tr>
		        	</table>
		        </div>
				<div class="info_list_table">
					<table summary="납부일자,납부방법,납부금액 테이블">
						<caption class="mypage_title_sub text_align_left">최근 6개월간 납부하신 이력입니다.</caption>
						<colgroup>
							<col width="33%" />
							<col width="34%" />
							<col width="33%" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col">납부일자</th>
								<th scope="col">납부방법</th>
								<th scope="col">납부금액</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								 <c:when test="${fn:length(vo.itemPay) == 0 }">
									<tr>
										<td colspan="4">조회된 납부 이력이 없습니다.</td>
									</tr>
								 </c:when>
								 <c:otherwise>
									<c:forEach items="${vo.itemPay }" var="item">
										<tr>
											<td>
												<c:set value="${item.payMentDate }" var="oDate"/>
												<c:set value="${fn:substring(oDate,0,8)}" var="parseDate" />
												<fmt:parseDate value="${parseDate}" pattern="yyyyMMdd" var="parseDate1"/>
												<fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd" var="resultDate"/>
												${resultDate}
											</td>
											<td>${item.payMentMethod }</td>
											<td>
												<fmt:formatNumber value="${item.payMentMoney}" type="number"/> 원
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
                 </div>
                 <div class="text_align_center"><button type="button" class="btn_center_reg_red">확인</button></div>
			</div>
		</div>
	</div>
<form:form id="frm" name="frm" method="post" action="/mypage/chargeViewPopExcelDownload.do">
	<input type="hidden" id="ncn" name="ncn" value="${searchVO.ncn}">
	<input type="hidden" id="ctn" name="ctn" value="${searchVO.ctn}">
	<input type="hidden" id="custId" name="custId" value="${searchVO.custId}">
</form:form>
<iFrame title="빈프레임" id="tmpFrm" name="tmpFrm" style="display:none"></iFrame>
</body>