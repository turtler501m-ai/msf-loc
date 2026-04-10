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
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="contentAttr">
	<div class="ly-content" id="main-content">
		<div class="ly-page-sticky">
			<h2 class="ly-page__head">My 포인트<button class="header-clone__gnb"></button></h2>
		</div>
		<h3 class="c-heading c-heading--type7 u-mt--32">조회회선</h3>
		<div class="c-form u-width--40p">
			<select class="c-select c-select--type3" id="cntrNo" onchange="selectCntr();">
			<c:forEach items="${cntrList}" var="cntrList">
				<option value="${cntrList.svcCntrNo}">${cntrList.cntrMobileNo}</option>
			</c:forEach>
			</select>
		</div>
		<hr class="c-hr c-hr--type3">
		<div class="c-item u-mt--40">
			<div class="c-item__title flex-type--between">포인트 현황<span class="c-text c-text--type4 u-ml--auto u-co-gray-7">${formatedNow} 기준</span></div>
			<div class="banner-balloon u-mt--12 u-mb--56">
				<p>
					<span class="c-text c-text--type5">My 포인트</span>
					<br>
					<b class="c-text c-text--type7" id="remainPoint">
					mypoint
					</b>
					<img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_05.png" alt="My 포인트">
				</p>
			</div>
		</div>
		<button class="c-button c-button--full c-button--mint--type2" onclick="openHistPopup()">포인트 이용/적립 내역 조회</button>
		<button class="c-button c-button--full c-button--mint u-mt--8" onclick="openGiftPopup();">포인트로 쿠폰 교환하기</button>
		<a href="/m/mypage/regServiceView.do" class="c-button c-button--full c-button--mint u-mt--8">포인트로 요금 할인받기</a>
		<p class="c-bullet c-bullet--fyr u-co-sub-4 u-mt--12">포인트로 요금 할인 시 부가서비스 ‘요금할인’ 서비스를 이용해 주세요.</p>
		<div class="c-box u-mt--40 u-mb--64">
			<div class="c-box--title">월별 포인트 이용 현황</div>
			<div class="bar-chart u-mt--24" id="bar_chart">
				<div class="badge-info c-text c-text--type6">
					<b id="displayYYYY">첫번째 조회년</b>
				</div>
			</div>
			<a id="tp_trigger" data-tpbox="#bar_chart_tp" style="display: none"></a>
			<div class="c-tooltip-box" id="bar_chart_tp" style="top: 5.93rem">
				<div class="c-tooltip-box__title">툴팁 타이틀</div>
				<div class="c-tooltip-box__content">툴팁 내용
					<br>툴팁 내용
				</div>
				<button class="c-tooltip-box__close" data-tpbox-close>
					<span class="c-hidden">툴팁닫기</span>
				</button>
			</div>
		</div>
	</div>
    <script>
    function exchange(){

    	if(!$('input[name=coupnCtgCd]').is(':checked')){
        	MCP.alert("상품이 선택되지 않았습니다.");
        	return false;
       	}
    	KTM.Confirm("포인트를 쿠폰과 교환하시겠습니까?", function (){
	    	var cntrNo = $('#cntrNo').val();
	    	var coupnCtgCd = $('input[name=coupnCtgCd]:checked').attr('id');
	    	var coupnCtgNm = $('input[name=coupnCtgCd]:checked').attr('coupnCtgNm');
	    	var exchPoint = $('input[name=coupnCtgCd]:checked').attr('exchPoint');
	    	var varData = ajaxCommon.getSerializedData({
				cntrNo : cntrNo,
				coupnCtgCd : coupnCtgCd,
				coupnCtgNm : coupnCtgNm,
				exchPoint : exchPoint
			});
	    	$.ajax({
				id: 'insertCouponGiftAjax',
				url: '/m/mypage/insertCouponGiftAjax.do',
				type: 'POST',
				data: '',
				dataType: "json",
				async: false,
				success: function(resp) {
					exchangeCallback(resp);
				}
			});
	    	this.close();
    	});
    }
    function exchangeCallback(resp){
    	if(resp.resultCode == "00000"){
    		if(resp.resultMsg != ""){
    			MCP.alert("정상적으로 구매 되었습니다."+resp.resultMsg);
    		} else {
    			MCP.alert("정상적으로 구매 되었습니다.");
    		}
    		
    		cntrNo = $('#cntrNo').val();
    		$.ajax({
				id: 'insertCouponGiftAjax',
				url: '/m/mypage/pointGiftRegView.do?cntrNo=' + cntrNo,
				type: 'POST',
				data: '',
				dataType: "text",
				async: false,
				success: function(resp) {
					'#pullModalContents';
				}
			});
    		MCP.init();
    	}else if(resp.resultCode == "00002" || resp.resultCode == "00003"){
    		MCP.alert("포인트가 부족합니다.");
		} else if (resp.resultCode == "10001") {
			MCP.alert("쿠폰정보가 없습니다.");
		} else if (resp.resultCode == "10002") {
			MCP.alert("쿠폰 재고 수량이 없습니다.");
		} else if (resp.resultCode == "10003") {
			MCP.alert("쿠폰 포인트 교환이 실패 하였습니다.");
    	}else if (resp.resultCode == "20001") {
			MCP.alert(resp.message, function(){
				location.href = resp.location;
				})
		} else if (resp.resultCode == "20002") {
			MCP.alert(resp.message, function(){
				location.href = resp.location;
				})
		}else{
    		MCP.alert("처리 중 문제가 발생했습니다.");
    	}
    }
    
    function openHistPopup(){
    	openPage('pullPopup','/m/mypage/myPointSaveHistList.do?cntrNo='+$('#cntrNo').val(),'');
    }
    
    function openGiftPopup(){
    	cntrNo = $('#cntrNo').val();
    	openPage('pullPopup','/m/mypage/pointGiftRegView.do?cntrNo='+cntrNo,'');
    }
    
    function selectCouponList(){
    	var v_totRemainPoint = $("#totRemainPoint").attr("data-totRemainPoint");
    	$.ajax({
			id: 'pointGiftRegList',
			url: '/m/mypage/pointGiftRegList.do?pageNo='+ $('#pageNo').val() + '&point=' + v_totRemainPoint,
			type: 'GET',
			data: '',
			dataType: "text",
			async: false,
			success: function(resp) {
				giftContentsCallback(resp);
			}
		});
    }
    
    function giftContentsCallback(resp){
    	
    	$('#contents').append(resp);
		$('#pageNo').val(parseInt($('#pageNo').val())+1);
	}
    function numberComma(comma) {
        return comma.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
    function myPointCallbackPopup(resp){
    	var myPoint = "0P";
		if(resp.result == "00001"){
			myPoint = numberComma(Number(resp.custPoint.totRemainPoint))+"P";
		}
		$('#remainPointPopup').html(myPoint);
    }
    
    function myPointCallback(resp){
    	var myPoint = "0P";
		if(resp.result == "00001"){
			myPoint = numberComma(Number(resp.custPoint.totRemainPoint))+"P";
		}
		$('#remainPoint').html(myPoint);
	}
	function myPointHistListCallback(resp){	
		var month= new Array();
	    var year= new Array();
	    var uPoint= new Array();
	    var sPoint= new Array();
	    var i = 0;
	    resp = resp.monthlyPointList;
		for(i=0;i<resp.length;i++){

			if(resp[i].mm == "01"){
	      		month[i] = resp[i].yyyy+"년"+resp[i].mm+"월";
	      	}else{
	      		month[i] = resp[i].mm+"월";
	      	}  	
	      	year[i] = resp[i].yyyy;
	      	uPoint[i] = resp[i].uPoint;
	      	sPoint[i] = resp[i].sPoint;
			
		}
		
		
		$('#bar_chart').html('  <div class="badge-info c-text c-text--type6"><b id="displayYYYY">첫번째 조회년</b></div>');
		$('#displayYYYY').html(year[0]);
		var chart = new window.KTM.BarChart(document.querySelector("#bar_chart"), {
	        categories: month,
	        dataset: [{
	            name: "누적사용",
	            color: "#ff5100",
	            data: uPoint,
	            tooltip: "누적사용 : {value}P",
	          },
	          {
	            name: "누적적립",
	            color: "#0573e8",
	            data: sPoint,
	            tooltip: "누적적립 : {value}P",
	          },
	        ],
	      });
	}
	function selectCntr(){
		$.ajax({
			id: 'myPointInfoAjax',
			url: '/m/mypage/myPointInfoAjax.do?cntrNo='+ $('#cntrNo').val(),
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
					url: '/m/mypage/myPointHistListAjax.do?cntrNo='+ $('#cntrNo').val(),
					type: 'GET',
					data: '',
					dataType: "json",
					async: false,
					success: function(resp) {
						myPointHistListCallback(resp);
					}
				});
	}
	function listContentsCallback(resp){
		if($(resp).hasClass('u-flex-between')){
			
			if($('#pageNo').val() == 1){
				$('#contents').html(resp);
			}else{
				$('#contents').append(resp);
			}
			
			$('#contents').find('.cntrMobileNo').html($('#popCntrNo option:selected').attr("moCntr"));
		}else{
			$('#contents').html(resp);
		}
		$('#pageNo').val(parseInt($('#pageNo').val())+1);
	}
	
	function selectDetailList(obj){
		var cntrNo = $('#popCntrNo').val();
		var pointYyyy = $('#pointYyyy').val();
		var pointMm = $('#pointMm').val();
		var pageNo = $('#pageNo').val();
		var pointTrtCd="";
		$('.pointCheck').attr("checked", false);
		$(obj).attr("checked", true);
		if($(obj).val() == 'ALL'){
			pointTrtCd = 'ALL';
		}else{
			pointTrtCd = $(obj).val(); 
		}
		
		var varData = ajaxCommon.getSerializedData({
			cntrNo : cntrNo,
			pointYyyy : pointYyyy,
			pointMm : pointMm,
			pointTrtCd : pointTrtCd,
			pageNo : pageNo
		});
		$.ajax({
			id: 'myPointSaveHistListHtml',
			url: '/m/mypage/myPointSaveHistListHtml.do',
			type: 'POST',
			data: varData,
			dataType: "text",
			async: false,
			success: function(resp) {
				listContentsCallback(resp);
			}
		});
	}
	
	function selectDetailListByDate(){
		$('.pointCheck').each(function() {
			if($(this).attr("checked") == "checked"){
				selectDetailList(this);
			}
		});
	}
    $(document).ready(function (){
    	selectCntr();
    });
  </script>
	</t:putAttribute>
</t:insertDefinition>
