
var pageObj= {
   allMpayHistory: null
  ,preSelMonth: null
}

$(document).ready(function (){

  $(".footer-info").siblings("button").hide();

  $("#monthTitle option:eq(0)").prop("selected", true); // 최근날짜
  $("#mPayStatus option:eq(0)").prop("selected", true); // 전체

  // 소액결제 내역 조회
  searchMpayHistory();

  // 한도변경 이벤트
  $("#changeLimit").click(function(){

    // 한도변경 가능 여부 조회
    var varData = ajaxCommon.getSerializedData({
      ncn:$("#ctn option:selected").val()
    });

    ajaxCommon.getItem({
       id: 'possibleChangeMpayLimitAjax'
      ,cache: false
      ,url: '/personal/possibleChangeMpayLimitAjax.do'
      ,data: varData
      ,dataType: "json"
    },function(data){

      // 개인화 URL 정보 미존재
      if(data.RESULT_CODE == "0001"){
        MCP.alert(data.RESULT_MSG, function(){
          location.href = "/m/personal/auth.do";
        });
        return false;
      }

      if(data.resultCd == "S"){
        window.open('https://ips.kt.com/olleh/lupin/molleh/mLimitMvno/mLimit.do', "_blank" );
      }else{
        var errMsg =  ajaxCommon.isNullNvl(data.resultMsg, "시스템 장애로 인하여 통신이 원활하지 않습니다.");
        MCP.alert(errMsg);
      }
    });
  });

}); // end of document.ready ---------------------------

/** 소액결제 내역 조회 */
var searchMpayHistory = function(){

  var drawList = null;

  // 검색조건
  var mPayPeriod = $("#monthTitle").val();
  var type = $("#mPayStatus").val();

  // 조회내역 존재 시 필터링
  if(pageObj.preSelMonth != null && pageObj.preSelMonth == mPayPeriod){
    drawList = filterMpayHistory(type);
    drawMPayList(drawList);
    return;
  }

  var varData = ajaxCommon.getSerializedData({
     svcCntrNo : $("#ctn option:selected").val()
    ,mPayPeriod : mPayPeriod
  });

  ajaxCommon.getItem({
     id: 'selectMPayListAjax'
    ,cache: false
    ,url: '/personal/selectMPayListAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data) {

    // 개인화 URL 정보 미존재
    if (data.RESULT_CODE == "0001") {
      MCP.alert(data.RESULT_MSG, function () {
        location.href = "/m/personal/auth.do";
      });
      return false;
    }

    // 날짜범위 세팅
    $("span[name=firstLastDay]").each(function () {
      if (mPayPeriod == $(this).attr("data-month")) $(this).show();
      else $(this).hide();
    });

    pageObj.allMpayHistory = data.mPayList;
    pageObj.preSelMonth = mPayPeriod;

    // 조회 월 총 이용금액
    var selTotalAmt = getTotalUseAmt();
    $("#selMonthAmt").html(numberWithCommas(selTotalAmt));
    $("#nowMonthAmt").html(numberWithCommas(selTotalAmt) + "원");

    // 소액결제 이용한도
    var lmtAmt = parseInt(data.tmonLmtAmt);
    var rmnAmt = (lmtAmt > 0) ? (lmtAmt - selTotalAmt) : 0;
    $("#tmonLmtAmt").html(numberWithCommas(lmtAmt) + "원");
    $("#rmndLmtAmt").html(numberWithCommas(rmnAmt) + "원");

    // 결제조회내역
    drawList = filterMpayHistory(type); // 검색 조건에 맞게 데이터 추출
    drawMPayList(drawList);
  });
}

/** 소액결제 내역 필터 */
var filterMpayHistory = function(type){

  // 전체조회
  if(pageObj.allMpayHistory == null || type == "ALL"){
    return pageObj.allMpayHistory;
  }

  var drawList = pageObj.allMpayHistory.filter(item => item.payStatus == type);
  return drawList;
}

/** 소액결제 내역 화면 출력 */
var drawMPayList = function(drawList){

  var html = "";
  $("#mPayListBody").html("");

  if(drawList == null || drawList.length == 0){
    html= '<tr><td colspan="4">내역이 존재하지 않습니다.</td></tr>';
    $("#mPayListBody").html(html);
    return;
  }

  for(var item of drawList){
    html += '<tr>';
    html +=  '<td>' + item.ptnrSvcNm + '</td>';
    html +=  '<td>' + item.settlDtFmt + '</td>';
    html +=  '<td>' + numberWithCommas(item.amt) + '원</td>';
    html +=  '<td>';
    html +=  	'<button class="c-button c-button--underline u-co-sub-4 fs-13" onclick="showDetailPay(\'' + item.ptnrCpNm + '\',\'' + item.ptnrSvcNm + '\',\'' + item.settlNm + '\',\'' + item.ptnrPgNm + '\',\'' + item.settlDtFmt + '\',\'' + item.amt + '\',\'' + item.payStatusNm + '\');" data-dialog-trigger="#micropay-detail_dialog">상세</button>';
    html +=  '</td>';
    html += '</tr>';
  }

  $("#mPayListBody").html(html);
}

/** 조회 월 이용 총 금액 */
var getTotalUseAmt = function(){

  var selMonthAmt = 0;

  if(pageObj.allMpayHistory == null){
    return selMonthAmt;
  }

  for(var item of pageObj.allMpayHistory){
    if(item.payStatus == "FULLCNCL" || item.payStatus == "PRTLCNCL"){
      selMonthAmt += parseInt(item.amt) * (-1);
    }else{
      selMonthAmt += parseInt(item.amt);
    }
  }

  return selMonthAmt;
}

/** 소액결제내역 상세 팝업 */
var showDetailPay = function(ptnrCpNm, ptnrSvcNm, settlNm, ptnrPgNm, settlDtFmt, amt, payStatusNm){

  $("#onePtnrCpNm").html(ptnrCpNm);
  $("#onePtnrSvcNm").html(ptnrSvcNm);
  $("#oneSettlNm").html(settlNm);
  $("#onePtnrPgNm").html(ptnrPgNm);
  $("#oneSettlDtFmt").html(settlDtFmt);
  $("#oneAmt").html(numberWithCommas(amt) + "원");
  $("#onePayStatusNm").html(payStatusNm);
}

/** 회선 조회 */
var select = function(){
  pageObj.preSelMonth = null;
  pageObj.allMpayHistory = null;
  searchMpayHistory();  // 소액결제 내역 조회
}