
var pageObj= {
    allMpayHistory: null
   ,preSelMonth: null
}

$(document).ready(function (){

  // 소액결제 내역 조회
  searchMpayHistory();

  // 한도변경 이벤트
  $("#changeLimit").click(function(){

    var varData = ajaxCommon.getSerializedData({
      ncn: $("#ctn option:selected").val()
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
          location.href = "/personal/auth.do";
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
  },function(data){

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/personal/auth.do";
      });
      return false;
    }

    // 날짜범위 세팅
    $("span[name=firstLastDay]").each(function(){
      if(mPayPeriod == $(this).attr("data-month")) $(this).show();
      else $(this).hide();
    });

    pageObj.allMpayHistory = data.mPayList;
    pageObj.preSelMonth = mPayPeriod;

    // 조회 월 총 이용금액
    var selTotalAmt= getTotalUseAmt();
    $("#selMonthAmt").html(numberWithCommas(selTotalAmt));
    $("#nowMonthAmt").html(numberWithCommas(selTotalAmt)+"원");

    // 소액결제 이용한도
    var lmtAmt = parseInt(data.tmonLmtAmt);
    var rmnAmt = (lmtAmt > 0) ? (lmtAmt - selTotalAmt) : 0;
    $("#tmonLmtAmt").html(numberWithCommas(lmtAmt)+"원");
    $("#rmndLmtAmt").html(numberWithCommas(rmnAmt)+"원");

    // 결제조회내역
    drawList = filterMpayHistory(type)
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
    html = '<tr><td colSpan="7">내역이 존재하지 않습니다.</td></tr>';
    $("#mPayListBody").html(html);
    return;
  }

  for(var item of drawList){
    var settlDtArr = item.settlDtFmt.split(" "); // yyyy-mm-dd hh:mi:ss

    html += `<tr>`;
    html += `  <td>${item.ptnrCpNm}</td>`;
    html += `  <td>${item.ptnrSvcNm}</td>`;
    html += `  <td>${item.settlNm}</td>`;
    html += `  <td>${item.ptnrPgNm}</td>`;
    html += `  <td class="u-fs-13">${settlDtArr[0]}<br/>${settlDtArr[1]}</td>`;
    html += `  <td>${numberWithCommas(item.amt)}원</td>`;
    html += `  <td>${item.payStatusNm}</td>`;
    html += `</tr>`;
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

/** 회선 조회 */
var select = function(){
  pageObj.preSelMonth = null;
  pageObj.allMpayHistory = null;
  searchMpayHistory();  // 소액결제 내역 조회
}