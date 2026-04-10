<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

	function viewPrint() {

           /*  var inbody = document.body.innerHTML; // 이전 body 영역 저장
            window.onbeforeprint = function () { // 프린트 화면 호출 전 발생하는 이벤트

            	$("#FirstPop").append($("#cmodalbody").html());
            	$(".c-modal__content").remove();
                //document.body.innerHTML = document.getElementById('cmodalbody').innerHTML; // 원하는 영역 지정
            }
            window.onafterprint = function () { // 프린트 출력 후 발생하는 이벤트
                document.body.innerHTML = inbody; // 이전 body 영역으로 복구
                $("#FirstPop").remove();

            } */
           window.print();





	}

</script>


 <div class="c-modal__content" >
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="modalJoinInfoTitle">납부내역 인쇄</h2>
		<button class="c-button no-print" data-dialog-close="">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body" id="cmodalbody">
		<div class="certi-box">
			<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--0">고객사항</h3>
			<div class="c-button-wrap c-button-wrap--right print-btn">
				<button class="c-button c-button--sm c-button--white c-button-round--sm" onclick="viewPrint();">
					<i class="c-icon c-icon--print" aria-hidden="true"></i> <span>인쇄</span>
				</button>
			</div>
			<div class="c-table u-mt--32 u-mb--16">
				<table>
					<caption>고객명, 휴대폰번호를 포함한 고객정보</caption>
					<colgroup>
						<col>
						<col>
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">고객명</th>
							<th scope="row">휴대폰번호</th>
							<th scope="row">공급자 등록번호</th>
						</tr>
						<tr>
							<td>${searchVO.userName}</td>
							<td>${searchVO.ctn}</td>
							<td>133-81-43410</td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--32">
				요금납부 내역</h3>
			<div class="c-table u-mt--32 u-mb--16">
				<table>
					<caption>납부일자, 납부방법, 납부금액을 포함한 납부내역 정보</caption>
					<colgroup>
						<col>
						<col>
						<col>
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
							<c:when test="${itemPay ne null and !empty itemPay}">
								<c:forEach var="itemPay" items="${itemPay}">
									<tr class="page-break">
										<td>${itemPay.payMentDate}</td>
										<td>${itemPay.payMentMethod}</td>
										<td>${itemPay.payMentMoney} 원</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="3">
										<div class="c-nodata">
											<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
											<p class="c-nodata__text">납부내역이 없습니다.</p>
										</div>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="certi-box--bottom u-ta-center u-pt--40">
				<p class="c-text c-text--fs16">상기 금액을 납부하였음을 확인합니다.</p>
				<p class="c-text c-text--fs14 u-co-gray u-mt--8">
					<fmt:formatDate value="${nmcp:getDateToCurrent(0)}" 	pattern="yyyy년 MM월 dd일" />
				</p>
				<p class="c-text c-text--fs16 u-mt--12">
					<b>주식회사 케이티엠모바일</b>
				</p>
				<i class="certi-box__image" aria-hidden="true">
					<img src="/resources/images/portal/content/img_stamp.png" alt="">
				</i>
			</div>
		</div>
	</div>
</div>

