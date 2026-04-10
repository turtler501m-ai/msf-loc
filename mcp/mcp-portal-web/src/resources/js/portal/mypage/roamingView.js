;

var addDivCd = "R";
//var addDivCd = "G";

var pageObj = {
    soc : ""
    , prodHstSeq : ""
}



$(document).ready(function (){

    addRegSvcSearch();

    $("#tab1").click(function(){
        addRegSvcSearch();
        $("#listHr").show();
    });
    $("#tab2").click(function(){
        regSvcSearch();
        $("#listHr").hide();
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
            MCP.alert("해외 로밍 선택 하시기 바랍니다. ");
            return;
        }
        var varData = ajaxCommon.getSerializedData({
            rateAdsvcCd : pageObj.soc
            , prodHstSeq : pageObj.prodHstSeq
            , ncn : $("#ctn option:selected").val()
        });

        ajaxCommon.getItem({
                id:'moscRegSvcCanChgAjax'
                ,cache:false
                ,url:'/mypage/roamingCanChgAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if(jsonObj.RESULT_CODE == 'S'){

                    MCP.alert("해외 로밍 해지가 완료되었습니다.",function(){
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

//로밍 신청
function regSvcSearch(){

    var varData = ajaxCommon.getSerializedData({
        addDivCd : addDivCd
        ,ncn : $("#ctn option:selected").val()
    });

    ajaxCommon.getItem({
            id:'addSvcListAjax'
            ,cache:false
            ,url:'/mypage/regRoamListAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            var list = jsonObj.list;
            var html = '';

            //로밍 목록
            if(list.length > 0){

                for(var i=0; i<list.length; i++){
                    if(list[i].selfYn == 'Y'){

                        html += '<li class="c-accordion__item listAccordion"  id="listAccordion'+i+'" idx='+i+'>';
                        html += '<div class="c-accordion__head">';
                        html += ' <div class="c-accordion__check">';
                        html += '<input id="rateCd'+i+'" type="hidden" name="rateCd" value='+list[i].rateAdsvcCd+'>';
                        html += '<input id="rateSeq'+i+'" type="hidden" name="rateSeq" value='+list[i].rateAdsvcGdncSeq+'>';
                        html += '<input class="c-radio" id="nm'+i+'"  type="radio" name="radServ">';
                        html += '<label class="c-label" for="nm'+i+'">'+list[i].rateAdsvcNm+'</label>';
                        html += '</div>';
                        html += '<button class="runtime-data-insert c-accordion__button" id="acc_headerA'+i+'" type="button" aria-expanded="false" onclick="rateCdDtl(\''+i+'\');" aria-controls="acc_contentA'+i+'">';
                        html += '<span class="c-hidden">'+list[i].rateAdsvcNm+' 상세정보 펼쳐보기</span>';
                        html += '</button>';
                        if(list[i].freeYn == 'Y'){
                            html += '<span class="c-accordion__amount" id="baseVatAmt_'+i+'">무료제공</span>';
                        }else if(list[i].freeYn != 'Y' && list[i].dateType == '1' && list[i].usePrd != 0 && list[i].usePrd != ""){
                            html += '<span class="c-accordion__amount" id="baseVatAmt_'+i+'">'+numberWithCommas(list[i].mmBasAmtVatDesc)+'원 / '+list[i].usePrd+'일</span>';
                        }else if(list[i].freeYn != 'Y' && list[i].dateType == '3' && list[i].rateAdsvcNm == '중국/일본 알뜰 로밍' && list[i].usePrd != 0 && list[i].usePrd != ""){
                            html += '<span class="c-accordion__amount" id="baseVatAmt_'+i+'">'+numberWithCommas(list[i].mmBasAmtVatDesc)+'원 / '+list[i].usePrd+'일</span>';
                        } else {
                            html += '<span class="c-accordion__amount" id="baseVatAmt_'+i+'">'+numberWithCommas(list[i].mmBasAmtVatDesc)+'원</td>';
                        }
                        html += '</div>';
                        html += '<div class="c-accordion__panel expand" id="acc_contentA'+i+'" aria-labelledby="acc_headerA'+i+'">';
                        html += ' <div class="c-accordion__inside" id="rateContents'+i+'"></div>';
                        html += '</div>';
                        html += '</li>';
                        html += '</li>';
                    }

                }

                $(".roamingList").html(html);
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


            }else {
                $(".roamingList").html('');
                $("#roamingDataYn").hide();
            }

            document.addEventListener('UILoaded', function() {
                KTM.datePicker('#range.flatpickr-input', {
                    mode: 'range',
                });
            });

            $(".headTitle").show();
        });
}


//로밍 설명 상세
function rateCdDtl(indexType){
    var rateAdsvcCd = $("#rateCd"+indexType).val();

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
        if(data != ''){
            $("#rateContents"+indexType).html(data.rateAdsvcBasDesc);
        } else {
            $("#rateContents"+indexType).html('-');
        }
    });
}


/** 이용중인 로밍 조회 */
function addRegSvcSearch(){

    var varData = ajaxCommon.getSerializedData({
        addDivCd : addDivCd
        ,ncn : $("#ctn option:selected").val()
    });

    ajaxCommon.getItem({
        id:'myAddSvcListAjax'
        ,cache:false
        ,url:'/mypage/myRoamJoinListAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(jsonObj){
        var html = '';
        var data =jsonObj.outList;
        var prodInfoList = jsonObj.prodInfoList;

        if (prodInfoList.length > 0) {
            for (var index = 0; index < prodInfoList.length; index++) {
                var strtDt = data[index].strtDt;
                var endDt = data[index].endDt;
                if (prodInfoList[index].dateType == '1' || prodInfoList[index].dateType == '3' || prodInfoList[index].dateType == '4') { // dataType 1 or 3이면 종료일 가공한 값
                    endDt = data[index].endDttm;
                }

                html += '<tr>';
                html += '<td>';
                html += prodInfoList[index].rateAdsvcNm;

                //1-1) 시작일시 ~ 종료일시(ex.2023.07.01 11:00 ~ 2023.07.10 11:00)
                if (prodInfoList[index].dateType == '2' || prodInfoList[index].dateType == '3' || prodInfoList[index].dateType == '4') {
                    html += '<br/>';
                    html += '<span class="product__sub u-mt--8 u-mb--0">(';
                    html += strtDt.substring(0, 4) + '.' + strtDt.substring(4, 6) + '.' + strtDt.substring(6, 8) + ' ' + strtDt.substring(8, 10) + ':' + strtDt.substring(10, 12) + ' ~';
                    if (prodInfoList[index].dateType == '3' || prodInfoList[index].dateType == '4') {
                        html += '</span>';
                        html += '<span class="product__sub u-mb--0">';
                        html += endDt.substring(0, 4) + '.' + endDt.substring(4, 6) + '.' + endDt.substring(6, 8) + ' ' + endDt.substring(8, 10) + ':' + endDt.substring(10, 12);
                    }
                    html += ')</span>';
                }
                //1-2) 시작일자 ~ 종료일자(ex.2023.07.01 ~ 2023.07.10)
                if (prodInfoList[index].dateType == '1') {
                    html += '<br/>';
                    html += '<span class="product__sub u-mt--8 u-mb--0">(';
                    html += strtDt.substring(0, 4) + '.' + strtDt.substring(4, 6) + '.' + strtDt.substring(6, 8) + ' ~';
                    html += '</span>';
                    html += '<span class="product__sub u-mb--0">';
                    html += endDt.substring(0, 4) + '.' + endDt.substring(4, 6) + '.' + endDt.substring(6, 8);
                    html += ')</span>';
                }

                //2-1) 대표상품은 신청한 서브회선 마스킹 처리하여 표시(ex. 010-****-1234)
                if (prodInfoList[index].lineType == 'M') {
                    var ctnList = data[index].shareSubCtnList;
                    if(ctnList != null && ctnList.length > 0) {
                        for (var i = 0; i < ctnList.length; i++) {
                            var ctn = ctnList[i];
                            var cnt = i + 1;
                            html += '<span class="product__sub u-mb--0">서브회선' + cnt + ' : ';
                            html += ctn;
                            html += '</span>';
                        }
                    }
                }

                //2-2) 서브상품은 연관된 대표회선 마스킹 처리하여 표시(ex. 010-****-1234)
                if (prodInfoList[index].lineType == 'S') {
                    var ctn = data[index].shareMainCtn;
                    if (ctn) {
                        html += '<span class="product__sub u-mb--0">대표회선 : ';
                        html += ctn;
                        html += '</span>';
                    }
                }

                html += '</td>';

                if (prodInfoList[index].freeYn == "Y") {
                    html += '<td>무료제공</td>';
                } else if (prodInfoList[index].freeYn != "Y" && prodInfoList[index].dateType == '1' && prodInfoList[index].usePrd != 0 && prodInfoList[index].usePrd != "") {
                    html += '<td>' + numberWithCommas(prodInfoList[index].mmBasAmtVatDesc) + '원 / ' + prodInfoList[index].usePrd + '일</td>';
                } else {
                    html += '<td>' + numberWithCommas(prodInfoList[index].mmBasAmtVatDesc) + '원</td>';
                }

                html += '<td>';
                html += '<div class="c-button-wrap c-button-wrap--underline">';
                if (data[index].updateFlag == 'Y') {
                    html += '<button class="c-button c-button--underline c-button--sm u-co-sub-4"  type="button" onclick="btn_regChg(\'' + prodInfoList[index].rateAdsvcGdncSeq + '\',\'Y\',\'' + data[index].prodHstSeq + '\');"> 변경<span class="c-hidden">로밍 변경 팝업 열기</span></button>';
                }
                if (data[index].onlineCanYn == 'Y') {
                    html += '<button class="c-button c-button--underline c-button--sm u-co-sub-4" type="button" onclick="btn_regCancel(\'' + data[index].socDescription + '\',\'' + data[index].soc + '\',\'' + data[index].prodHstSeq + '\');">해지</button>';
                    html += '<input type="hidden"  value=' + data[index].canCmnt + '/>';
                }
                html += '</div>';
                html += '</td>';
                html += '</tr>';

            }

            $("#addSvcList").html(html);
            $(".noAddSvcList").hide();
            $("#addSvcData").show();
        } else {

            $(".noAddSvcList").show();
            $("#addSvcData").hide();
        }

    });
}


function errorCall(){
    alert("11");

}
//로밍 신청 팝업
function btn_regSvc(){

    var rateAdsvc = "";
    var rateAdsvcNm = "";
    var roamingChk = "";

    $(".listAccordion").each(function(e){
        var idx = $(this).attr("idx");
        roamingChk =$("#nm"+idx).is(':checked');

        if(roamingChk){
            rateAdsvc = $("#rateSeq"+idx).val();
            rateAdsvcNm =  $("label[for='nm"+idx+"']").text();
        }

    });

    if(rateAdsvc == ""){
        MCP.alert("가입하실 로밍 서비스를 선택해 주세요.");
        return;
    }

    if(rateAdsvc != "" ){
        var rateAdsvcGdncSeq = rateAdsvc;

    }


    var parameterData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val()
        ,rateAdsvcGdncSeq : rateAdsvcGdncSeq
    });

    if($("#underAge").val() == "true"){
        MCP.alert("미성년자의 경우 고객센터를 통해서만<br> 가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
            return false;
        });
    }else{
        openPage('pullPopup','/mypage/roamingViewPop.do',parameterData, 1);
    }
}

//로밍 변경
function btn_regChg(rateAdsvcGdncSeq,flag,prodHstSeq){

    var parameterData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val(),
        rateAdsvcGdncSeq : rateAdsvcGdncSeq,
        flag : flag,
        prodHstSeq : prodHstSeq
    });

    openPage('pullPopup','/mypage/roamingViewPop.do',parameterData, 1);
}

//로밍 해지
function btn_regCancel(selRegNm, soc, prodHstSeq){
  if($("#underAge").val() == "true"){
        MCP.alert("미성년자의 경우 고객센터를 통해서만<br> 해지가 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
            return false;
        });
    }else{
        var innerHtml = selRegNm +" 로밍 서비스를 해지하시겠습니까?";
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
        pageObj.prodHstSeq = ajaxCommon.isNullNvl(prodHstSeq, "");
    }
}