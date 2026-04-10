
var pageObj= {
	allMpayHistory: null // 결제내역
	, preSelMonth: null // 이전에 선택한 조회기간
	, preSelCnt: null // 이전에 선택한 조회회선
} 

$(document).ready(function (){
	
	// 최초 소액결제 내역 조회
	var searchDate= $("#monthTitle option:first").val();
	pageObj.preSelCnt= $("#ctn option:selected").val(); 
	searchMpayHistory(searchDate);
	
	// 한도변경 버튼 클릭
	$("#changeLimit").click(function(){
		
		// 회선 변경 후 조회버튼 클릭하지 않는경우 기존 선택값으로 되돌리기
		if(pageObj.preSelCnt!=null){
			$("#ctn").val(pageObj.preSelCnt).prop("selected",true);	
		}
		
		// 한도변경 가능 여부 조회
		var varData = ajaxCommon.getSerializedData({
        	ncn:$("#ctn option:selected").val()
    	});
    	
    	ajaxCommon.getItem({
	        id:'possibleChangeMpayLimitAjax'
	        ,cache:false
	        ,url:'/mypage/possibleChangeMpayLimitAjax.do'
	        ,data: varData
	        ,dataType:"json"
    	}
    	,function(jsonObj){
      		if(jsonObj.resultCd=="S"){
				// 한도변경 페이지 이동
				window.open('https://ips.kt.com/olleh/lupin/molleh/mLimitMvno/mLimit.do', "_blank" ); 
	    		return;
			}else{
				
				if(!jsonObj.resultMsg){
					MCP.alert("시스템 장애로 인하여 통신이 원활하지 않습니다.");					
				}else{
					MCP.alert(jsonObj.resultMsg);	
				}
				return;	
			}

    	});
    	
	}); // end of event-------------------
	
	

}); // end of $(document).ready(function (){-------------------------

// myCommCtn.jsp에서 회선 > 조회버튼 클릭 시 select 함수를 호출한다 
function select(){
	// 회선 변경 시 조회 기간 초기화
	pageObj.preSelMonth= null;
	pageObj.preSelCnt= null;
	
	// 소액결제 이용한도 초기화 
	var searchDate= $("#monthTitle option:first").val();
	searchMpayHistory(searchDate); // 소액결제 내역 조회 호출
}

// 소액결제 내역 조회
function searchMpayHistory(date){
    var drawList= null;
    var type= $("#mPayStatus option:selected").val(); // 검색 조건
   
    var mPayPeriod= date; // 최초조회인 경우 date 존재
    if(typeof date == "undefined" || date == null){
		mPayPeriod= $("#monthTitle option:selected").val(); 
	}
	
	// 회선 변경 후 조회버튼 클릭하지 않는경우 기존 선택값으로 되돌리기
	if(pageObj.preSelCnt!=null){
		$("#ctn").val(pageObj.preSelCnt).prop("selected",true);	
	}
	
	// 이전과 같은 조회기간인 경우 조회하지 않고 바로 데이터 필터링
	if(pageObj.preSelMonth != null && pageObj.preSelMonth == mPayPeriod){
		
		drawList= filterMpayHistory(pageObj.allMpayHistory, type); // 검색 조건에 맞게 데이터 추출
		drawMPayList(drawList); // 화면에 데이터 그리기
		return;		
	}
    
    var varData = ajaxCommon.getSerializedData({
    	svcCntrNo : $("#ctn option:selected").val(), 
    	mPayPeriod : mPayPeriod
    });

    ajaxCommon.getItem({
            id:'selectMPayListAjax'
            ,cache:false
            ,url:'/mypage/selectMPayListAjax.do'
            ,data: varData
            ,dataType:"json"
    }
    ,function(data){
    
		// 날짜 범위 출력
		var dateList= data.dateList;
		// 최초조회인 경우 조회 옵션 > 날짜 세팅
		if(typeof date != "undefined" && date != null){
			var dateMonthHtml="";
			var datePeriodHtml= "";
			for(var month=0;month<dateList.length;month++){
				
				var year= dateList[month].monthTitle.substring(0,4);
				var eachmonth= dateList[month].monthTitle.substring(4,6);
				dateMonthHtml+= '<option value="'+dateList[month].monthTitle+'">'+year+'년 '+eachmonth+'월</option>';
			
				datePeriodHtml+= '<span class="u-co-gray" name="firstLastDay" data-month="'+dateList[month].monthTitle+'" style="display:none">';
	          	datePeriodHtml+= dateList[month].monthFirstDay+' ~ '+dateList[month].monthLastDay;
	          	datePeriodHtml+= '</span>';
			}
			
			$("#monthTitle").html(dateMonthHtml);
			$("#datePeriod").html(datePeriodHtml);
			
			// 조회 초기화 
			// 이용내역 조회 기간, 상태 초기화 
			$("#monthTitle option:eq(0)").prop("selected",true); // 최근날짜
			$("#mPayStatus option:eq(0)").prop("selected",true); // 전체	
			type= $("#mPayStatus option:selected").val(); // 검색 조건 초기화
		}
		
		// 날짜 범위 출력
		$("span[name=firstLastDay]").each(function(){
			var divValue = $(this).attr("data-month");
            if (mPayPeriod == divValue) {
                $(this).show();
            } else {
                $(this).hide();
            }
	    });
	
		// 리스트 출력
		var isEmpty= data.isEmpty;
		var mPayList= data.mPayList;
		var tmonLmtAmt= data.tmonLmtAmt;
		
		pageObj.allMpayHistory= mPayList;
		pageObj.preSelMonth= mPayPeriod;
		pageObj.preSelCnt= $("#ctn option:selected").val(); 
		
		if(!isEmpty == "N"){ // 결제내역이 존재하지 않는 경우 , 로그인 세션이 없는 경우 
			drawMPayList(drawList, type);
			$("#selMonthAmt").html("0");
			
			// 최초조회인 경우 이번달 소액결제 한도, 이번 달 결제금액, 이번달 잔여한도 세팅		
			if(typeof date != "undefined" && date != null){
				$("#nowMonthAmt").html("0원");
				var tmonLmtAmt= parseInt(tmonLmtAmt);
				var rmndLmtAmt= 0;
				if(tmonLmtAmt > 0){
					rmndLmtAmt= tmonLmtAmt - 0;
				}
				$("#tmonLmtAmt").html(numberWithCommas(tmonLmtAmt)+"원");
				$("#rmndLmtAmt").html(numberWithCommas(rmndLmtAmt)+"원");
			}
		}else{ // 결제내역이 존재하는 경우
			
			// 1) 총 이용금액 세팅
			var selTotalAmt= getTotalUseAmt(mPayList);
			$("#selMonthAmt").html(numberWithCommas(selTotalAmt));
			
			// 2) 내역 출력
			drawList= filterMpayHistory(mPayList, type); // 검색 조건에 맞게 데이터 추출
			drawMPayList(drawList); // 화면에 데이터 그리기		
			
			// 3) 최초조회인 경우 이번달 소액결제 한도, 이번 달 결제금액, 이번달 잔여한도 세팅		
			if(typeof date != "undefined" && date != null){
				$("#nowMonthAmt").html(numberWithCommas(selTotalAmt)+"원");
				var tmonLmtAmt= parseInt(tmonLmtAmt);
				var rmndLmtAmt= 0;
				if(tmonLmtAmt > 0){
					rmndLmtAmt= tmonLmtAmt - selTotalAmt;
				}
				
				$("#tmonLmtAmt").html(numberWithCommas(tmonLmtAmt)+"원");
				$("#rmndLmtAmt").html(numberWithCommas(rmndLmtAmt)+"원");
			}
		}
      }
  );
        
} // end of searchMpayHistory------------------------

// 소액결제 내역 조회 > 필터에 맞게 데이터 추출
function filterMpayHistory(mPayList, type){
	
	var drawList= []; // 화면에 보일 데이터
	
	if(type=="ALL"){ // "ALL"인 경우 필터 필요 없음
		return mPayList;
	}

	for(var i=0;i<mPayList.length;i++){ // 데이터 필터링
		var each= mPayList[i];
		if(each.payStatus== type){
			drawList.push(each);
		}
	}
	
	return drawList;
} // end of filterMpayHistory-------------------------

// 화면에 리스트 출력
function drawMPayList(drawList){
	
	var contentHtml= "";
	$("#mPayListBody").html("");
	
	if(drawList== null || drawList.length== 0){ // 데이터가 없는 경우
		contentHtml= '<tr><td colspan="7">내역이 존재하지 않습니다.</td></tr>';
	}else{ // 데이터가 존재하는 경우

		for(var i=0;i<drawList.length;i++){
			var item= drawList[i];
			var settlDt= item.settlDtFmt.split(" "); // 0번째 날짜, 1번째 시간
			contentHtml+=  '<tr>';
            contentHtml+=  	'<td>'+item.ptnrCpNm+'</td>';
            contentHtml+=  	'<td>'+item.ptnrSvcNm+'</td>';
            contentHtml+=  	'<td>'+item.settlNm+'</td>';
            contentHtml+=  	'<td>'+item.ptnrPgNm+'</td>';
            contentHtml+=  	'<td class="u-fs-13">'+settlDt[0]+'<br/>'+settlDt[1]+'</td>';
            contentHtml+=  	'<td>'+numberWithCommas(item.amt)+'원</td>'; // numberWithCommas > jqueryCommon.js
            contentHtml+=  	'<td>'+item.payStatusNm+'</td>';
            contentHtml+=  '</tr>';
            
		}
	}
	
	$("#mPayListBody").html(contentHtml);

} // end of drawMPayList-------------------------------

// 해당 월의 총 이용금액
function getTotalUseAmt(mPayList){ // mPayList는 해당 월에 대한 모든 결제내역 
	
	var selMonthAmt= 0; // 조회한 월의 이용금액
	
	if(mPayList==null || typeof mPayList == "undefined" ){
		return selMonthAmt;
	}
	
	for(var i=0;i<mPayList.length;i++){
		var item= mPayList[i];
		var statusCd= item.payStatus; // 결제상태코드
		
        // 결제 취소인 경우 마이너스 처리
        if(statusCd=="FULLCNCL" || statusCd== "PRTLCNCL"){
			selMonthAmt+= parseInt(item.amt) * (-1);
		}else{
			selMonthAmt+= parseInt(item.amt);
		}
	}
	
	return selMonthAmt;
} // end of getTotalUseAmt-------------------