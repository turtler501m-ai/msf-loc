
$(document).ready(function (){
    getLodinData();

    // 트리플할인 신청 완료 후 처음으로 버튼 클릭
    if($("#doneReturn").val() == "Y"){
        $("#discountSearch").trigger('click');
        history.replaceState({}, null, location.pathname);
    }

});

//조회버튼 클릭
function select(){

    ajaxCommon.createForm({
        method:"post"
        ,action:"/content/ktDcView.do"
    });
    var ncn = $("#ctn option:selected").val();
    ajaxCommon.attachHiddenElement("ncn",ncn);
    ajaxCommon.formSubmit();
}

function getLodinData(){

    var ncn = $("#ctn option:selected").val();
    var varData = ajaxCommon.getSerializedData({
        ncn : ncn
    });

    ajaxCommon.getItem({
        id:'ktDcViewAjax'
        ,cache:false
        ,url:'/content/ktDcViewAjax.do'
        ,data: varData
        ,dataType:"json"
     }
     ,function(jsonObj){

         var resultCode = jsonObj.RESULT_CODE;
         var message = jsonObj.message;
         if(resultCode=="E"){
             MCP.alert(message);
             return false;
         }
     });
}

//할인 가능 조회 버튼 클릭
function discountSearch() {

    var ncn = $("#ctn option:selected").val();
    var htmlArea = "";
    var varData = ajaxCommon.getSerializedData({
        ncn : ncn
       ,rateCd : $('#prvRateCode').val()
    });

    ajaxCommon.getItem({
        id:'discountSearchAjax'
        ,cache:false
        ,url:'/content/discountSearchAjax.do'
        ,data: varData
        ,dataType:"json"
     }
     ,function(jsonObj){
         KTM.LoadingSpinner.show();
         var resultCode = jsonObj.RESULT_CODE;
         var resultMsg = jsonObj.RESULT_MSG;
         if(resultCode=="E"){
             KTM.LoadingSpinner.hide();
             MCP.alert(resultMsg);
             return false;
         }else if(resultCode =="1001"){
             KTM.LoadingSpinner.hide();
             MCP.alert(resultMsg);
             return false;
         }else if(resultCode =="1002"){
             KTM.LoadingSpinner.hide();
             MCP.alert(resultMsg);
             return false;
         }else if(resultCode =="1003"){
             KTM.LoadingSpinner.hide();
             MCP.alert(resultMsg);
             return false;
         }else if(resultCode =="1004"){
             $('#discountSearch').hide();
             $("#tripleDcAcc").html("");
             $('#tdc-target').show();
             $("#custId").val(jsonObj.custId);
             $("#myCtn").val(jsonObj.ctn);
             $("#prcsMdlInd").val(jsonObj.prcsMdlInd);
             $("#additionCd").val(jsonObj.additionCd);
             $("#combYn").val(jsonObj.combYn);

             for(var i =0; i<jsonObj.conrNmList.length; i++){

                var parts = jsonObj.conrNmList[i].split(',');
                var formattedDate = parts[1].slice(0, 4) + '-' + parts[1].slice(4, 6) + '-' + parts[1].slice(6, 8);

                 htmlArea += '<tr>';
                 htmlArea += '<td>';
                 htmlArea += '<input class="c-radio" id="tripleDcItem'+i+'" type="radio" name="tripleDcItem">';
                 htmlArea += '<label class="c-label" for="tripleDcItem'+i+'"><span class="c-hidden">kt인터넷</span></label>';
                 htmlArea += '</td>';
                 htmlArea += '<td id="tripleDcItem'+i+'s" data-id="'+parts[2]+'">'+parts[0]+'</td>';
                 htmlArea += '<td id="tripleDcItem'+i+'d">'+formattedDate+'</td>';
                htmlArea += '</tr>';
             }

             $("#tripleDcAcc").append(htmlArea);
             $('#addServiceName').text(jsonObj.resCode[0].rateNm);
             $('#soc').val(jsonObj.resCode[0].soc);
             $('#baseAmt').val(jsonObj.resCode[0].baseAmt);
             $('#additionCd').val(jsonObj.resCode[0].rateCd);
             KTM.LoadingSpinner.hide();
         }else if(resultCode =="1005"){
             KTM.LoadingSpinner.hide();
             $('#discountSearch').hide();
             $('#tdc-notarget').show();
         }else if(resultCode =="1006"){
             KTM.LoadingSpinner.hide();
             $('#discountSearch').hide();
             $('#tdc-allNotarget').show();
         }else if(resultCode =="1007"){
             KTM.LoadingSpinner.hide();
             MCP.alert(resultMsg);
             return false;
         }

     });

}

function accApplication() {

    if(!$('input[name=tripleDcItem]').is(':checked')){
        MCP.alert("신청 가능한 인터넷 상품을 선택해주세요.");
        return false;
       }

    KTM.Confirm("트리플할인을 신청하시겠습니까?", function(){

        var ktInternetId = '';
        var installDd = '';

         $('input[name="tripleDcItem"]').each(function() {
                if ($(this).is(':checked')) {
                     var inputId = $(this).attr("id");
                     ktInternetId = $('#'+inputId+'s').attr("data-id");
                     installDd = $('#'+inputId+'d').text().replace(/-/g, '');
                }
            });

          var ncn = $("#ctn option:selected").val();
          var custId = $("#custId").val();
          var ctn = $("#myCtn").val();
          var prcsMdlInd = $("#prcsMdlInd").val();
          var soc = $("#soc").val();
          var additionCd = $("#additionCd").val();
          var contractNum = $("#contractNum").val();
          var rateNm = $('#addServiceName').text();
          var baseAmt = $('#baseAmt').val();
          var rateCd = $('#prvRateCode').val();
          var combYn = $("#combYn").val();
          var varData = ajaxCommon.getSerializedData({
                ncn : ncn
               ,ctn : ctn
               ,custId : custId
               ,prcsMdlInd : prcsMdlInd
               ,ktInternetId : ktInternetId
               ,soc : soc
               ,contractNum : contractNum
               ,rateNm : rateNm
               ,baseAmt : baseAmt
               ,rateCd : rateCd
               ,installDd : installDd
               ,additionCd : additionCd
               ,combYn : combYn
            });

            ajaxCommon.getItem({
                id:'ktDcViewAjax'
                ,cache:false
                ,url:'/content/insertKtDcAjax.do'
                ,data: varData
                ,dataType:"json"
             }
             ,function(jsonObj){
                 KTM.LoadingSpinner.show();
                 var resultCode = jsonObj.RESULT_CODE;
                 var resultMsg = jsonObj.RESULT_MSG;

                 if(resultCode == "00000"){
                     KTM.LoadingSpinner.hide();
                     MCP.alert("트리플 할인이 적용되었습니다.<br>적용된 내역은 익월 명세서를 통해 확인 가능합니다.", function (){
                         $('#prvRateId').hide();
                         $('#ktDcStart').hide();
                         $('#KtDcDone').show();
                     });
                 }else if(resultCode == "0000"){
                     KTM.LoadingSpinner.hide();
                     MCP.alert("트리플 할인이 적용되었습니다.<br>적용된 내역은 익월 명세서를 통해 확인 가능합니다.", function (){
                         $('#prvRateId').hide();
                         $('#ktDcStart').hide();
                         $('#KtDcDone2').show();
                     });
                 }else if(resultCode == "00001"){
                     KTM.LoadingSpinner.hide();
                     MCP.alert(resultMsg);
                 }else if(resultCode == "00002"){
                     KTM.LoadingSpinner.hide();
                     MCP.alert(resultMsg);
                 }else if(resultCode == "30000"){
                     KTM.LoadingSpinner.hide();
                     $('#prvRateId').hide();
                     $('#ktDcStart').hide();
                     $('#KtDcDone2').show();
                 }

             });

        this.close();
    });

}

function goBack() {
    location.href = "/content/ktDcView.do?doneReturn=Y";
}


