<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

				<table id="dataArea">
					<caption>지급사유, 사은품 명, 신청일을 포함한 사은품 신청 내역</caption>
					<colgroup>
						<col style="width: 350px">
						<col style="width: 350px">
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">지급사유</th>
							<th scope="col">사은품 명</th>
							<th scope="col">신청일</th>
						</tr>
					</thead>
					<tbody id="liDataArea">
					<c:choose>
					<c:when test="${!empty giftCustList}">
						<c:forEach var="giftCustListVar" items="${giftCustList}">
						<tr>
							<td>${giftCustListVar.prmtNm }</td>
							<td>${giftCustListVar.prdtNm }</td>
							<td>${giftCustListVar.regstDttm }</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<td colspan="3">
							<div class="c-nodata">
								<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
								<p class="c-noadat__text">조회 결과가 없습니다.</p>
							</div>
						</td>
					</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				<input type="hidden" name="pageNo" id="pageNo" value="${giftCustListVar.pageNo}" />
				<c:if test="${!empty giftCustList}">
				<c:if test="${totalCount >= 10}">
				<div class="c-button-wrap u-mt--8" id="addBtnArea">
					<button class="c-button c-button--full fs-14" id="moreBtn">더보기<span class="c-button__sub u-co-gray-7 u-mr--8" id="pageNav"><span id="listCount">${listCount}</span>/<span id="totalCount">${totalCount}</span></span>
						<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
						<span id="maxPage" style="display: none;"></span>
					</button>
				</div>
				</c:if>
				</c:if>
				<script>
	$(document).ready(function(){
		$("#moreBtn").click(function(){
			var listCount = ${listCount};
			var totalCount = ${totalCount};
			if(listCount >= totalCount){
				alert("마지막 페이지 입니다.");
				return false;
			} else {
				var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
				myGiftListAjax(pageNo+1);
				$("#pageNo").val(pageNo+1);
			}
		});
	});
	var myGiftListAjax = function(pageNo) {

		$("#pageNo").val(pageNo);
		var pageNo = pageNo;

		var varData = ajaxCommon.getSerializedData({
			pageNo : pageNo,
		});

		ajaxCommon.getItem({
			id:'myGiftListAjax'
			,cache:false
			,url:'/simple/myGiftListAjax.do'
			,data: varData
			,dataType:"json"
			,async:false
		},function(jsonObj){
			
			var totalCount = jsonObj.totalCount; // 총갯수
			var giftCustList = jsonObj.giftCustList; // 조회데이터
			var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수

			viewDesc(totalCount,giftCustList,listCount);
		});
	}
	var viewDesc = function(totalCount,giftCustList,listCount){
		var htmlArea = "";

		if(giftCustList !=null && giftCustList.length>0 ){

			var prmtNm =  "";
			var prdtNm = "";
			var regstDttm = "";

			for(var i=0; i<giftCustList.length; i++){

				prmtNm =  giftCustList[i].prmtNm;
				prdtNm = giftCustList[i].prdtNm;
				regstDttm =  giftCustList[i].regstDttm;

				htmlArea += '	<tr>';
				htmlArea += '		<td>'+ prmtNm +'</td>';
				htmlArea += '		<td>'+ prdtNm +'</td>';
				htmlArea += '		<td>'+ regstDttm +'</td>';
				htmlArea += '	</tr>';
			}
			$("#liDataArea").append(htmlArea);
			$("#dataArea").show();
			$(".c-nodata").hide();
			$("#listCount").text(listCount); // 현재 노출
			$("#totalCount").text(totalCount);
			if(listCount < totalCount){
				$("#addBtnArea").show();
			} else {
				$("#addBtnArea").hide();
			}
		} else {
			//initView();
		}
		var accEl = $("#dataArea")[0];
	}
	function initView(){

		$("#dataArea").hide();
		$("#liDataArea").html("");
		$(".c-nodata").show();
		$("#listCount").text(0); // 현재 노출
		$("#totalCount").text(0);
		$("#addBtnArea").hide();
	}
	</script>