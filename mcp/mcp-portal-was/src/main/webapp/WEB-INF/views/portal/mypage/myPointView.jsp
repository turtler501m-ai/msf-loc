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
<t:insertDefinition name="layoutGnbDefault">
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">My 포인트</h2>
			</div>
			<div class="c-row c-row--lg">
				<div class="c-heading-wrap">
					<h3 class="c-heading c-heading--fs20">조회할 회선을 선택해 주세요.</h3>
					<div class="c-heading-wrap__sub">
						<div class="c-form c-form--line">
							<label class="c-label c-hidden" for="cntrNo">회선 선택</label>
							<select class="c-select" id="cntrNo">
								<c:forEach items="${cntrList}" var="cntrList">
									<option value="${cntrList.svcCntrNo}">${cntrList.cntrMobileNo}</option>
								</c:forEach>
							</select>
						</div>
						<button class="c-button c-button-round--xsm c-button--primary u-ml--10" onclick="selectCntr();">조회</button>
					</div>
				</div>
				<hr class="c-hr c-hr--title">
				<div class="point">
					<h3 class="point__title">
						<span class="point__sub">${formatedNow} 기준</span>회원님의 포인트 현황입니다.
					</h3>
					<p class="point__text">
						My 포인트<b id="remainPoint">mypoint</b>
					</p>
					<div class="c-button-wrap c-button-wrap--left">
						<button class="c-button c-button-round--md c-button--mint--type2" type="button" onclick="openHistPopup()">이용/적립 내역 조회</button>
						<button class="c-button c-button-round--md c-button--mint u-ml--12" type="button" onclick="openGiftPopup();">쿠폰 교환하기</button>
						<a href="/mypage/regServiceView.do" class="c-button c-button-round--md c-button--mint u-ml--12" type="button">요금 할인받기</a>
					</div>
					<p class="c-bullet c-bullet--fyr u-co-mint u-mt--12">포인트로 요금 할인 시 부가서비스 ‘요금할인’ 서비스를 이용해 주세요.</p>
				</div>
				<h3 class="c-heading c-heading--fs16">월별 포인트 이용 현황</h3>

				
				
				<div class="chart chart--bar">
					<div id="bar_chart" style="width: 41.25rem">
					<b id="displayYYYY">첫번째 조회년</b>
					</div>
					<a id="tp_trigger" data-tpbox="#bar_chart_tp" style="display: none"></a>
					<div class="chart__box" id="bar_chart_tp">
						<div class="chart__title">0000년 0월</div>
						<div class="chart__content">
							툴팁 내용 <br>툴팁 내용
						</div>
					</div>
				</div>
				<div class="c-table c-hidden">
					<table>
						<caption>월별, 사용 포인트, 적립 포인트로 구성된 월별 포인트 이용 현황</caption>
						<colgroup>
							<col style="width: 33.33%">
							<col style="width: 33.33%">
							<col style="width: 33.33%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">월별</th>
								<th scope="col">사용 포인트</th>
								<th scope="col">적립 포인트</th>
							</tr>
						</thead>
						<tbody id="tableHtml">
						</tbody>
					</table>
				</div>
			</div>
		</div>
 		  </t:putAttribute>
		<t:putAttribute name="scriptHeaderAttr">
		<script>
			function exchange() {

				if (!$('input[name=coupnCtgCd]').is(':checked')) {
					MCP.alert("상품이 선택되지 않았습니다.");
					return false;
				}

				KTM.Confirm("포인트를 쿠폰과 교환하시겠습니까?", function() {
					var cntrNo = $('#cntrNo').val();
					var coupnCtgCd = $('input[name=coupnCtgCd]:checked').attr(
							'id');
					var coupnCtgNm = $('input[name=coupnCtgCd]:checked').attr(
							'coupnCtgNm');
					var exchPoint = $('input[name=coupnCtgCd]:checked').attr(
							'exchPoint');
					var varData = ajaxCommon.getSerializedData({
						cntrNo : cntrNo,
						coupnCtgCd : coupnCtgCd,
						coupnCtgNm : coupnCtgNm,
						exchPoint : exchPoint
					});

					$.ajax({
						id: 'insertCouponGiftAjax',
						url: '/mypage/insertCouponGiftAjax.do',
						type: 'POST',
						data: varData,
						dataType: "json",
						async: false,
						success: function(resp) {
							exchangeCallback(resp);
						}
					});
					this.close();
				});

			}
			function exchangeCallback(resp) {
				if (resp.resultCode == "00000") {
					if(resp.resultMsg != ""){
		    			MCP.alert("정상적으로 구매 되었습니다."+resp.resultMsg);
		    		} else {
		    			MCP.alert("정상적으로 구매 되었습니다.");
		    		}

					cntrNo = $('#cntrNo').val();
					$.ajax({
						id: 'insertCouponGiftAjax',
						url: '/mypage/pointGiftRegView.do?cntrNo=' + cntrNo,
						type: 'POST',
						data: '',
						dataType: "text",
						async: false,
						success: function(resp) {
							'#pullModalContents';
						}
					});
					MCP.init();

				} else if (resp.resultCode == "00002" || resp.resultCode == "00003") {
					MCP.alert("포인트가 부족합니다.");
				} else if (resp.resultCode == "10001") {
					MCP.alert("쿠폰정보가 없습니다.");
				} else if (resp.resultCode == "10002") {
					MCP.alert("쿠폰 재고 수량이 없습니다.");
				} else if (resp.resultCode == "10003") {
					MCP.alert("쿠폰 포인트 교환이 실패 하였습니다.");
				} else if (resp.resultCode == "20001") {
					MCP.alert(resp.message, function(){
						location.href = resp.location;
						})
				} else if (resp.resultCode == "20002") {
					MCP.alert(resp.message, function(){
						location.href = resp.location;
						})
				} else {
					MCP.alert("처리 중 문제가 발생했습니다.");
				}
			}

			function openHistPopup() {

				var parameterData = ajaxCommon.getSerializedData({
					cntrNo : $("#cntrNo").val()
				});

				openPage('pullPopup', '/mypage/myPointSaveHistList.do',parameterData, 1);

			}

			function openGiftPopup() {
				var parameterData = ajaxCommon.getSerializedData({
					cntrNo : $("#cntrNo").val()
				});

				openPage('pullPopup','/mypage/pointGiftRegView.do',parameterData, 1);

			}

			function selectCouponList() {
				var v_totRemainPoint = $("#totRemainPoint").attr("data-totRemainPoint");
				$.ajax({
					id: 'pointGiftRegList',
					url: '/mypage/pointGiftRegList.do?pageNo='+ $('#pageNo').val() + '&point=' + v_totRemainPoint,
					type: 'GET',
					data: '',
					dataType: "text",
					async: false,
					success: function(resp) {
						giftContentsCallback(resp);
					}
				});
			}

			function giftContentsCallback(resp) {
				$('#contents').append(resp);
				$('#pageNo').val(parseInt($('#pageNo').val()) + 1);
			}
			function numberComma(comma) {
				return comma.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}

			function myPointCallbackPopup(resp) {
				var myPoint = "0P";
				if (resp.result == "00001") {
					myPoint = numberComma(Number(resp.custPoint.totRemainPoint))
							+ "P";
				}
				$('#remainPointPopup').html(myPoint);
			}

			function myPointCallback(resp) {
				var myPoint = "0P";
				if(resp.result == "00000"){
					MCP.alert("조회할 정보가 없습니다.");
				}else if (resp.result == "00001") {
					myPoint = numberComma(Number(resp.custPoint.totRemainPoint))
							+ "P";
				}else{
					MCP.alert(resp.message, function(){
							location.href = resp.location;
						});
				}
				$('#remainPoint').html(myPoint);
			}
			function myPointHistListCallback(resp) {
				var month = new Array();
				var year = new Array();
				var uPoint = new Array();
				var sPoint = new Array();
				var i = 0;
				if(resp.result == "00000"){
					var tableHtml = "";
					resp = resp.monthlyPointList;
					for (i = 0; i < resp.length; i++) {
	
						//if(resp[i].mm == "01"){
						month[i] = resp[i].yyyy + "년 " + resp[i].mm + "월";
						//}else{
						//	month[i] = resp[i].mm+"월";
						//}  	
						year[i] = resp[i].yyyy;
						uPoint[i] = resp[i].uPoint;
						sPoint[i] = resp[i].sPoint;

						tableHtml += '<tr>';
						tableHtml += '<th scope="row">'+month[i]+'</th>';
						tableHtml += '<td>'+uPoint[i]+'P</td>';
						tableHtml += '<td>'+sPoint[i]+'P</td>';
						tableHtml += '</tr>';
						
	
					}
					$('#tableHtml').html(tableHtml);
					$('#bar_chart').html('  <div class="badge-info c-text c-text--type6"></div>');
					//$('#bar_chart').html('  <div class="badge-info c-text c-text--type6"><b id="displayYYYY">첫번째 조회년</b></div>');
					$('#displayYYYY').html(year[0]);
					var chart = new window.KTM.BarChart(document.querySelector("#bar_chart"), {
						categories : month,
						dataset : [ {
							name : "누적사용",
							color : "#ff5100",
							pattern: 0,
							data : uPoint,
							tooltip : "누적사용 : {value}P",
						}, {
							name : "누적적립",
							color : "#0573e8",
							pattern: 1,
							data : sPoint,
							tooltip : "누적적립 : {value}P",
						}, ],
					});
				}else if(resp.result == "00001"){
					MCP.alert(resp.message, function(){
							location.href = resp.location;
						})
				}else if(resp.result == "00002"){
					MCP.alert(resp.message, function(){
						location.href = resp.location;
					})
				}
			}
			function selectCntr() {
				$.ajax({
					id: 'myPointInfoAjax',
					url: '/mypage/myPointInfoAjax.do?cntrNo='+ $('#cntrNo').val(),
					type: 'GET',
					data: '',
					dataType: "json",
					async: false,
					success: function(resp) {
						myPointCallback(resp);
					}
				});

				$.ajax({
					id: 'myPointHistListAjax',
					url: '/mypage/myPointHistListAjax.do?cntrNo='+ $('#cntrNo').val(),
					type: 'GET',
					data: '',
					dataType: "json",
					async: false,
					success: function(resp) {
						myPointHistListCallback(resp);
					}
				});

			}
			function listContentsCallback(resp) {
				if ($(resp).hasClass('u-flex-between')) {

					if ($('#pageNo').val() == 1) {
						$('#contents').html(resp);
					} else {
						$('#contents').append(resp);
					}
					$('#contents').find('.cntrMobileNo').html(
							$('#popCntrNo option:selected').attr("moCntr"));
				} else {
					$('#contents').html(resp);
				}
				$('#pageNo').val(parseInt($('#pageNo').val()) + 1);
			}

			function selectDetailList(obj) {
				var cntrNo = $('#popCntrNo').val();
				var pointYyyy = $('#pointYyyy').val();
				var pointMm = $('#pointMm').val();
				var pageNo = $('#pageNo').val();
				var pointTrtCd = "";
				console.log("2");
				
				if ($(obj).val() == 'ALL') {
					pointTrtCd = 'ALL';
				} else {
					pointTrtCd = $(obj).val();
				}
				console.log('selectDetailList: ', obj);
				var varData = ajaxCommon.getSerializedData({
					cntrNo : cntrNo,
					pointYyyy : pointYyyy,
					pointMm : pointMm,
					pointTrtCd : pointTrtCd,
					pageNo : pageNo
				});
				$.ajax({
					id: 'myPointSaveHistListHtml',
					url: '/mypage/myPointSaveHistListHtml.do',
					type: 'POST',
					data: varData,
					dataType: "text",
					async: false,
					success: function(resp) {
						listContentsCallback(resp);
					}
				});
			}
			
			function selectDetailListByDate() {
				selectDetailList($("input[name='pointCheck']:checked"));
			}
			$(document).ready(function() {
				selectCntr();
			});
		</script>
  </t:putAttribute>

	
</t:insertDefinition>