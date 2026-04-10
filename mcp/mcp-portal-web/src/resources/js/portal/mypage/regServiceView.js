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

    $('button[id^=tab]').each(function(){
        $('button[id^=tab]').removeAttr("tabindex");
        $('button[id^=tab]').removeAttr("aria-controls");
        setTimeout(function(){
            $('button[id^=tab]').first().trigger("click");
        },10);
        var hasClass = $(this).hasClass("is-active");
        if(hasClass){
            $(this).attr('aria-selected',"true");
        }else {
            $(this).attr('aria-selected',"false");
        }
    });

    $('button[id^=tab]').on("click", function(){
        $('button[id^=tab]').removeClass('is-active');
        $('button[id^=tab]').attr('aria-selected',"false");
        $(this).addClass('is-active');
        $(this).attr('aria-selected',"true");
        var tabId = $(this).attr('id');
        $('.c-tabs__panel').removeClass('u-block');
        $('.c-tabs__panel[aria-labelledby =' + tabId + ']').addClass('u-block');
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
                ,url:'/mypage/moscRegSvcCanChgAjax.do'
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

//부가서비스 신청
function regSvcSearch(){

    var varData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val()
        , addDivCd : addDivCd
    });

    ajaxCommon.getItem({
            id:'addSvcListAjax'
            ,cache:false
            ,url:'/mypage/addSvcListAjax.do'
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
                    freeHtml += '<div class="c-accordion__head">';
                    freeHtml += ' <div class="c-accordion__check">';
                    freeHtml += '<input id="rateCdFr'+i+'" type="hidden" name="rateCd" value='+freeData[i].rateCd+'>';
                       freeHtml += '<input class="c-radio" id="nm'+i+'"  type="radio" name="radServ">';
                    freeHtml += '<label class="c-label" for="nm'+i+'">'+freeData[i].rateNm+'</label>';
                    freeHtml += '</div>';
                    freeHtml += '<button class="runtime-data-insert c-accordion__button" id="acc_headerA'+i+'" type="button" aria-expanded="false" onclick="rateCdDtl(1,\''+i+'\');" aria-controls="acc_contentA'+i+'">';
                    freeHtml += '<span class="c-hidden">'+freeData[i].rateNm+' 상세정보 펼쳐보기</span>';
                    freeHtml += '</button>';
                    freeHtml += '</div>';
                    freeHtml += '<div class="c-accordion__panel expand" id="acc_contentA'+i+'" aria-labelledby="acc_headerA'+i+'">';
                    freeHtml += ' <div class="c-accordion__inside" id="freeRateContents'+i+'"></div>';
                    freeHtml += '</div>';
                    freeHtml += '</li>';
                }

            }

            $(".freeDataList").html(freeHtml);
            $(".noDataFree").hide();

            $(".runtime-data-insert").click(function(){
                var hasClass = $(this).hasClass("is-active");
                var title = $(this).siblings().find(".c-label").text();
                if(hasClass){
                    $(this).children().remove();
                    $(this).append("<span class='c-hidden'>" + title + " 상세정보 펼쳐보기</span>");
                } else {
                    $(this).children().remove();
                    $(this).append("<span class='c-hidden'>" + title + " 상세정보 접기</span>");
                }
            });


        } else {
            $(".freeDataList").html('');
            $("#freeDataYn").hide();
            $(".noDataFree").show();
        }

        //유료서비스
        if(noFreeData.length > 0){
            for(var idx=0; idx<noFreeData.length; idx++){
                if(noFreeData[idx].useYn != 'Y'){
                    noFreeHtml += '<li class="c-accordion__item noFreeAccordion" id="noFreeAaccordion'+idx+'" nofreeIdx='+idx+'>';
                    noFreeHtml += '<div class="c-accordion__head">';
                    noFreeHtml += '<div class="c-accordion__check">';
                    noFreeHtml += '<input id="rateCdNo'+idx+'" type="hidden" name="rateCd" value='+noFreeData[idx].rateCd+'>';
                    noFreeHtml += '<input class="c-radio" id="noFree'+idx+'"  type="radio" name="radServ">';
                    noFreeHtml += '<label class="c-label" for="noFree'+idx+'">'+noFreeData[idx].rateNm+'</label>';
                    noFreeHtml += '</div>';
                    noFreeHtml += '<button class="runtime-data-insert c-accordion__button" id="acc_headerB'+idx+'" type="button"  onclick="rateCdDtl(2,\''+idx+'\');"   aria-expanded="false" aria-controls="acc_contentB'+idx+'">';
                    noFreeHtml += '<span class="c-hidden">'+noFreeData[idx].rateNm+' 상세정보 펼쳐보기</span>';
                    noFreeHtml += '</button>';
                    noFreeHtml += '<span class="c-accordion__amount" id="baseVatAmt_'+idx+'">'+numberWithCommas(noFreeData[idx].baseVatAmt)+'원</span>';
                    noFreeHtml += '</div>';
                    noFreeHtml += '<div class="c-accordion__panel expand" id="acc_contentB'+idx+'" aria-labelledby="acc_headerB'+idx+'">';
                    noFreeHtml += '<div class="c-accordion__inside" id="nofreeRateContents'+idx+'"></div>';
                    noFreeHtml += '</div>';
                    noFreeHtml += ' </li>';
                }
            }

            $(".noFreeDataList").html(noFreeHtml);
            $(".noDataFreeView").hide();

            $(".runtime-data-insert").click(function(){
                var hasClass = $(this).hasClass("is-active");
                var title = $(this).siblings().find(".c-label").text();
                if(hasClass){
                    $(this).children().remove();
                    $(this).append("<span class='c-hidden'>" + title + " 상세정보 펼쳐보기</span>");
                } else {
                    $(this).children().remove();
                    $(this).append("<span class='c-hidden'>" + title + " 상세정보 접기</span>");
                }
            });


        } else{
            $(".noFreeDataList").html('');
            $(".noFreeDataYn").hide();
            $(".noDataFreeView").show();
        }

        document.addEventListener('UILoaded', function() {
          KTM.datePicker('#range.flatpickr-input', {
            mode: 'range',
          });
        });

        $(".headTitle").show();
    });

    //통화중대기 설정/해제(무료) 아코디언 내용
    var rateAdData = ajaxCommon.getSerializedData({
        rateAdsvcGdncSeq : '179',
        btnYn : 'Y'
    });

    ajaxCommon.getItem({
            id:'adsvcContentAjax'
            ,cache:false
            ,url:'/rate/adsvcContent.do'
            ,data: rateAdData
            ,dataType:"html"
        }
        ,function(jsonObj){
            $('#callWaiting').html(jsonObj)
        });

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
        ,url:'/mypage/getContRateAdditionAjax.do'
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

/** 이용중인 부가서비스 조회 */
function addRegSvcSearch(){

    var varData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val()
        , addDivCd : addDivCd
    });

    ajaxCommon.getItem({
        id:'myAddSvcListAjax'
            ,cache:false
            ,url:'/mypage/myAddSvcListAjax.do'
            ,data: varData
            ,dataType:"json"
    }
    ,function(jsonObj){

        var html = '';
        var data =jsonObj.outList;

        if(data.length > 0){
            for(var index =0; index <data.length; index++){
                if(data[index].socRateVat >= 0){
                    html += '<tr>';
                    html += '<td>'+data[index].socDescription+'</td>';
                    if(data[index].socRateValue == 'Free'){
                        html += '<td>무료</td>';
                    }else if(data[index].rateCd == data[index].vatYn){
                        html += '<td>'+numberWithCommas(data[index].socRateVatValue) +' 원 / 1일</td>';
                    } else {
                        html += '<td>월 '+numberWithCommas(data[index].socRateVatValue) +' 원</td>';
                    }

                    html += '<td>';

                    html += '<div class="c-button-wrap c-button-wrap--underline">';
                    if(data[index].updateFlag == 'Y'){
                        html += '<button class="c-button c-button--underline c-button--sm u-co-sub-4"  type="button" onclick="btn_regChg(\''+data[index].socDescription+'\',\''+data[index].soc+'\',\''+data[index].updateFlag+'\',\''+numberWithCommas(data[index].socRateVatValue)+'\');"> 변경<span class="c-hidden">부가서비스 변경 팝업 열기</span>';
                        html += '</button>';
                    }
                    if(data[index].onlineCanYn == 'Y'){
                        html +='<button class="c-button c-button--underline c-button--sm u-co-sub-4" type="button" onclick="btn_regCancel(\''+data[index].socDescription+'\',\''+data[index].soc+'\');">해지</button>';
                        html += '<input type="hidden"  value='+data[index].canCmnt +'/>';

                    }
                    html += '</div>';
                    html +='</td>';
                    html += '</tr>';
                }
            }
            $("#addSvcList").html(html);
            $(".noAddSvcList").hide();
            $("#addSvcData").show();
        }else{

            $(".noAddSvcList").show();
            $("#addSvcData").hide();
        }

    });
}

function errorCall(){
    alert("11");

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


    $(".freeAccordion").each(function(e){
        var idx = $(this).attr("freeIdx");
        freeChk =$("#nm"+idx).is(':checked');

        if(freeChk){
            rateAdsvcFree = $("#rateCdFr"+idx).val();
            rateAdsvcNm =  $("label[for='nm"+idx+"']").text();
        }

    });

    $(".noFreeAccordion").each(function(index){

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
        MCP.alert("미성년자의 경우 고객센터를 통해서만<br> 가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
            return false;
        });
    }else{
        openPage('pullPopup','/mypage/addSvcViewPop.do',parameterData, 1);
    }
}

//부가서비스 변경
function btn_regChg(selRegNm, soc, flag, baseVatAmt){

    var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
            ,rateAdsvcCd : soc
            ,rateAdsvcNm : selRegNm
            ,flag : flag
            ,baseVatAmt : baseVatAmt+"원"
    });

    openPage('pullPopup','/mypage/addSvcViewPop.do',parameterData, 1);
}

//부가서비스 해지
function btn_regCancel(selRegNm, soc){

    if($("#underAge").val() == "true"){
        MCP.alert("미성년자의 경우 고객센터를 통해서만<br> 해지가 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
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
