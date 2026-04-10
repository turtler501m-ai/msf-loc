;

var addDivCd = "G";

var pageObj = {
    soc:""
}



$(document).ready(function (){

    addRegSvcSearch();

    $("#tab1").click(function(){
        addRegSvcSearch();
    });

    $("#tab2").click(function(){
        regSvcSearch();
    });

    $("#btnPrdSvcCan").click(function(){
        if (pageObj.soc == "") {
            MCP.alert("부가서비스 선택 하시기 바랍니다. ");
            return;
        }
        var varData = ajaxCommon.getSerializedData({
            rateAdsvcCd: pageObj.soc
            , ncn: $("#ctn option:selected").val()
        });

        ajaxCommon.getItem({
            id:'moscRegSvcCanChgAjax'
            ,cache:false
            ,url:'/m/mypage/moscRegSvcCanChgAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if(jsonObj.RESULT_CODE == 'S'){

                MCP.alert("부가서비스 해지가 완료되었습니다.",function(){
                    addRegSvcSearch();
                });
            } else if(jsonObj.RESULT_CODE == 'E'){
                MCP.alert(jsonObj.message);
            }
        });

    });

});


 function select(){
     $(".c-tabs__button").each(function(idx,val){
         if($(this).hasClass("is-active")){
             if(idx==0){
                 addRegSvcSearch();
             } else {
                 regSvcSearch();
             }
         }
     });
 }

//탭 컨트롤
function btn_tab(type){

     if(type == "1"){
         addRegSvcSearch();
     }else if(type == "2"){
         regSvcSearch();
     }
}

//부가서비스 설명 상세
function rateCdDtl(type, indexType){

    var rateAdsvcCd = "";

    if(type == 1){
        rateAdsvcCd = $("#rateCdFr"+indexType).val();
    } else if(type == 2){
        rateAdsvcCd = $("#rateCdNo"+indexType).val();
    }

    var varData = ajaxCommon.getSerializedData({
         rateAdsvcCd : rateAdsvcCd
    });


    ajaxCommon.getItem({
        id:'getContRateAdditionAjax'
        ,cache:false
        ,url:'/m/mypage/getContRateAdditionAjax.do'
        ,data: varData
        ,dataType:"json"
     },function(jsonObj){
        var data = jsonObj.rateDtl;
        if(type == 1){
            if(data != ''){
                $("#freeRateContents"+indexType).html(data.rateAdsvcBasDesc);
            } else {
                $("#freeRateContents"+indexType).html('-');
            }
        } else 	if(type == 2){
            if(data != ''){
                $("#nofreeRateContents"+indexType).html(data.rateAdsvcBasDesc);
            } else {
                $("#nofreeRateContents"+indexType).html('-');
            }
        }
    });
}

/*부가서비스 신청 조회*/
function regSvcSearch(){

    var varData = ajaxCommon.getSerializedData({
          ncn : $("#ctn option:selected").val()
          , addDivCd : addDivCd
    });

     ajaxCommon.getItem({
            id:'addSvcListAjax'
            ,cache:false
            ,url:'/m/mypage/addSvcListAjax.do'
            ,data: varData
            ,dataType:"json"
     }
     ,function(jsonObj){
        var freeData = jsonObj.listC;
        var noFreeData = jsonObj.listA;
        var freeHtml ='';
        var noFreeHtml = '';

        //무료부가서비스
        if(freeData.length > 0){

            for(var i=0; i<freeData.length; i++){
                if(freeData[i].useYn != 'Y'){
                    freeHtml += '<li class="c-accordion__item freeAccordion"  id="freeAccordion'+i+'" freeIdx='+i+'>';
                    freeHtml += '	<div class="c-accordion__head">';
                    freeHtml += '		<div class="c-accordion__check">';
                    freeHtml += '			<input id="rateCdFr'+i+'" type="hidden" name="rateCd" value='+freeData[i].rateCd+'>';
                    freeHtml += '			<input class="c-radio" id="nm'+i+'" type="radio" name="chkAS">';
                    freeHtml += '			<label class="c-label" for="nm'+i+'">'+freeData[i].rateNm+'</label>';
                    freeHtml += '		</div>';
                    freeHtml += '		<button class="c-accordion__button u-ta-right" type="button" href="javascript:void(0);" onclick="rateCdDtl(1,\''+i+'\');" aria-expanded="false" data-acc-header="#nm2'+i+'" >';
                    freeHtml += '			<div class="c-accordion__trigger"> </div>';
                    freeHtml += '		</button>';
                    freeHtml += '	</div>';
                    freeHtml += '	<div class="c-accordion__panel expand c-expand" id="nm2'+i+'">';
                    freeHtml += '		<div class="c-accordion__inside">';
                    freeHtml += '		<div class="c-text c-text--type3" id="freeRateContents'+i+'"></div>';
                    freeHtml += '	</div>';
                    freeHtml += '</div>';
                    freeHtml += '</li>';
                }
            }

            $(".freeDataList").html(freeHtml);
            $(".noDataFree").hide();

        } else {
            $(".freeDataList").html('');
            $("#freeDataYn").hide();
            $(".noDataFree").show();
        }

        //유료서비스
        if(noFreeData.length > 0){
            for(var idx=0; idx<noFreeData.length; idx++){
                if(noFreeData[idx].useYn != 'Y'){
                    noFreeHtml += '<li class="c-accordion__item noFreeAccordion"  id="noFreeAaccordion'+idx+'" nofreeIdx='+idx+'>';
                    noFreeHtml += '	<div class="c-accordion__head">';
                    noFreeHtml += '		<div class="c-accordion__check">';
                    noFreeHtml += '			<input id="rateCdNo'+idx+'" type="hidden" name="rateCd" value='+noFreeData[idx].rateCd+'>';
                    noFreeHtml += '			<input class="c-radio" id="noFree'+idx+'" type="radio" name="chkAS">';
                    noFreeHtml += '			<label class="c-label" for="noFree'+idx+'">'+noFreeData[idx].rateNm+'</label>';
                    noFreeHtml += '		</div>';
                    noFreeHtml += '		<button class="c-accordion__button u-ta-right" type="button" href="javascript:void(0);" onclick="rateCdDtl(2,\''+idx+'\');"  aria-expanded="false" data-acc-header="#noFree2'+idx+'" >';
                    noFreeHtml += '			<div class="c-accordion__trigger">';
                    noFreeHtml += '				<span class="c-text c-text--type4" id="baseVatAmt_'+idx+'">'+numberWithCommas(noFreeData[idx].baseVatAmt)+'원</span>';
                    noFreeHtml += '			</div>';
                    noFreeHtml += '		</button>';
                    noFreeHtml += '	</div>';
                    noFreeHtml += '	<div class="c-accordion__panel expand c-expand" id="noFree2'+idx+'">';
                    noFreeHtml += '		<div class="c-accordion__inside">';
                    noFreeHtml += '			<div class="c-text c-text--type3" id="nofreeRateContents'+idx+'"></div>';
                    noFreeHtml += '		</div>';
                    noFreeHtml += '	</div>';
                    noFreeHtml += '</li>';
                }

            }

            $(".noFreeDataList").html(noFreeHtml);
            $(".noDataFreeView").hide();

        } else{
            $(".noFreeDataList").html('');
            $(".noFreeDataYn").hide();
            $(".noDataFreeView").show();
        }

        if(noFreeData.length == 0 && freeData.length == 0){
            $("#btn_regSvc").prop("disabled",true);
        }

     });

     //통화중대기 설정/해제(무료) 아코디언 내용
     var rateAdData = ajaxCommon.getSerializedData({
         rateAdsvcGdncSeq : '179',
         btnYn : 'Y'
     });

     ajaxCommon.getItem({
             id:'adsvcContentAjax'
             ,cache:false
             ,url:'/m/rate/adsvcContent.do'
             ,data: rateAdData
             ,dataType:"html"
         }
         ,function(jsonObj){
             $('#callWaiting').html(jsonObj)
         });

}

//부가서비스 해지
function btn_regCancel(selRegNm, soc){
    if($("#underAge").val() == "true"){
        MCP.alert("미성년자의 경우 고객센터를 통해서만 해지가 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
            return false;
        });
    }else{
        var innerHtml = selRegNm +" 부가서비스를 해지하시겠습니까?";
        //soc ="1sdsd";
        ajaxCommon.getItemNoLoading({
                id:'getAdsvcDtl'
                ,cache:false
                ,async:false
                ,url:'/xmlInfo/getRateAdsvcDtlAjax.do'
                ,data: "rateAdsvcCd="+soc
                ,dataType:"json"
            }
            ,function(jsonObj){
                var gdncDtlList = jsonObj.DATA_OBJ;

                if (gdncDtlList != null && gdncDtlList != "") {
                    var gdncDtlObj = gdncDtlList.filter(item => item.rateAdsvcItemCd === "ADDSV109");

                    if (gdncDtlObj.length > 0  && gdncDtlObj[0].rateAdsvcItemSbst != "") {
                        innerHtml = gdncDtlObj[0].rateAdsvcItemSbst;
                    }
                }

            });

        $("#divRegSvcCan .c-modal__body").html(innerHtml);

        setTimeout(function(){
            var el = document.querySelector('#divRegSvcCan');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        }, 500);

        pageObj.soc = soc;

    }
 }

 function btn_regChg(selRegNm, soc, flag, baseVatAmt){

    var parameterData = ajaxCommon.getSerializedData({
            ncn :$("#ctn option:selected").val()
            ,rateAdsvcCd : soc
            ,rateAdsvcNm : selRegNm
            ,flag : flag
            ,baseVatAmt : baseVatAmt+"원"
    });

    openPage('pullPopupByPost','/m/mypage/addSvcViewPop.do',parameterData);
}


/** 이용중인 부가서비스 조회 */
function addRegSvcSearch(){
    var varData = ajaxCommon.getSerializedData({
        ncn :$("#ctn option:selected").val()
        , addDivCd : addDivCd
    });

     ajaxCommon.getItem({
            id:'myAddSvcListAjax'
            ,cache:false
            ,url:'/m/mypage/myAddSvcListAjax.do'
            ,data: varData
            ,dataType:"json"

     }
     ,function(jsonObj){

        var html = '';
        var data = jsonObj.outList;
        //var data = 0;
        if(data.length > 0){
            for(var index =0; index <data.length; index++){
                if(data[index].socRateVat >= 0){
                    html += '<tr>';
                    html += '<td class="u-ta-center">'+data[index].socDescription+'</td>'
                    if(data[index].socRateValue == 'Free'){
                        html += '<td class="u-ta-center">무료</td>';
                    }else if(data[index].rateCd == 'PL2078760'){
                        html += '<td class="u-ta-center">'+numberWithCommas(data[index].socRateVatValue) +' 원 / 1일</td>';
                    } else {
                        html += '<td class="u-ta-center">월 '+numberWithCommas(data[index].socRateVatValue) +' 원</td>';
                    }

                    html += '<td class="u-ta-center">';
                    if(data[index].updateFlag == 'Y'){
                        html += '<div class="u-mb--16">';
                        html += ' <a class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:void(0);" onclick="btn_regChg(\''+data[index].socDescription+'\',\''+data[index].soc+'\',\''+data[index].updateFlag+'\',\''+numberWithCommas(data[index].socRateVatValue)+'\');">변경</a>';
                        html += '</div>';
                    }
                    if(data[index].onlineCanYn == 'Y'){
                        html += '<div>';
                        html += ' <a class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:void(0);" onclick="btn_regCancel(\''+data[index].socDescription+'\',\''+data[index].soc+'\');">해지</a>';
                        html += '<input type="hidden"  value='+data[index].canCmnt +'/>';
                        html += '</div>';
                    }

                    html += '</td">';
                    html += '</tr>';
                    $("#addSvcList").html(html);
                }
            }

        } else{
            $(".regSvcYn").show();
            $(".regDataYn").hide();
        }
    });
}



//부가서비스 신청 팝업
function btn_regSvc(){


    var rateAdsvcFree = "";
    var rateAdsvcNoFree = "";
    var rateAdsvcCd = "";
    var rateAdsvcNm = "";
    var baseVatAmt = "";
    var freeChk = "";
    var nofreeChk = "";

    $(".freeAccordion").each(function(){
        var idx = $(this).attr("freeIdx");
        freeChk =$("#nm"+idx).is(':checked');

        if(freeChk){
            rateAdsvcFree = $("#rateCdFr"+idx).val();
            rateAdsvcNm =  $("label[for='nm"+idx+"']").text();
        }
    });

    $(".noFreeAccordion").each(function(){
        var numIndex = $(this).attr("nofreeIdx");
        nofreeChk =$("#noFree"+numIndex).is(':checked');
        if(nofreeChk){
            rateAdsvcNoFree = $("#rateCdNo"+numIndex).val();
            baseVatAmt = $("#baseVatAmt_"+numIndex).text();
            rateAdsvcNm = $("label[for='noFree"+numIndex+"']").text();
        }

    });

    if(rateAdsvcNoFree == "" && rateAdsvcFree == ""){
        MCP.alert("가입하실 부가서비스를 선택해 주세요.");
        return;
    }

    if(rateAdsvcFree != "" ){
        rateAdsvcCd = rateAdsvcFree;
        rateAdsvcNm = rateAdsvcNm;
    }

    if(rateAdsvcNoFree != "" ){
        rateAdsvcCd = rateAdsvcNoFree;
        baseVatAmt = baseVatAmt;
        rateAdsvcNm = rateAdsvcNm;
    }

    var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
            ,rateAdsvcCd : rateAdsvcCd
            ,rateAdsvcNm : rateAdsvcNm
            ,baseVatAmt : baseVatAmt
    });

    if($("#underAge").val() == "true"){
        MCP.alert("미성년자의 경우 고객센터를 통해서만 가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
            return false;
        });
    }else{
        openPage('pullPopupByPost','/m/mypage/addSvcViewPop.do',parameterData);
    }
}

//통화중대기 설정/해제(무료)
const $checkbox = $("#callWaiting0");
const $modal = $("#regServiceModal");

$checkbox.on("click", function() {
      if ($(this).is(":checked")) {
          var el = document.querySelector('#regServiceModal');
          var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
          modal.open();
      }
    });
