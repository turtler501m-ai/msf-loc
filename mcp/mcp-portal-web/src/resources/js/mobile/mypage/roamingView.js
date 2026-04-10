;

var addDivCd = "R";

var pageObj = {
    soc : ""
    , prodHstSeq : ""
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
                ,url:'/m/mypage/roamingCanChgAjax.do'
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

//탭 컨트롤
function btn_tab(type){

    if(type == "1"){
        addRegSvcSearch();
    }else if(type == "2"){
        regSvcSearch();
    }
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
        ,url:'/m/mypage/getContRateAdditionAjax.do'
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

/*로밍 신청 조회*/
function regSvcSearch(){

    var varData = ajaxCommon.getSerializedData({
        addDivCd : addDivCd
        ,ncn : $("#ctn option:selected").val()
    });

    ajaxCommon.getItem({
            id:'roamingListAjax'
            ,cache:false
            ,url:'/m/mypage/regRoamListAjax.do'
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
                        html += '	<div class="c-accordion__head">';
                        html += '		<div class="c-accordion__check">';
                        html += '			<input id="rateCd'+i+'" type="hidden" name="rateCd" value='+list[i].rateAdsvcCd+'>';
                        html += '			<input id="rateSeq'+i+'" type="hidden" name="rateSeq" value='+list[i].rateAdsvcGdncSeq+'>';
                        html += '			<input class="c-radio" id="nm'+i+'" type="radio" name="radServ">';
                        html += '			<label class="c-label" for="nm'+i+'">'+list[i].rateAdsvcNm+'</label>';
                        html += '		</div>';
                        html += '		<button class="c-accordion__button u-ta-right" type="button" href="javascript:void(0);" onclick="rateCdDtl(\''+i+'\');" aria-expanded="false" data-acc-header="#nm2_'+i+'" >';
                        html += '			<div class="c-accordion__trigger">';
                        if(list[i].freeYn == 'Y'){
                            html += '				<span class="c-text c-text--type4" id="baseVatAmt_'+i+'" style="white-space: nowrap;">무료제공</span>';
                        }else if(list[i].freeYn != 'Y' && list[i].dateType == '1' && list[i].usePrd != '0' && list[i].usePrd != null){
                            html += '				<span class="c-text c-text--type4" id="baseVatAmt_'+i+'" style="white-space: nowrap;">'+numberWithCommas(list[i].mmBasAmtVatDesc)+'원<br> / ' + list[i].usePrd +'일</span>';
                        }else if(list[i].freeYn != 'Y' && list[i].dateType == '3' && list[i].rateAdsvcNm == '중국/일본 알뜰 로밍' && list[i].usePrd != 0 && list[i].usePrd != ""){
                            html += '				<span class="c-text c-text--type4" id="baseVatAmt_'+i+'" style="white-space: nowrap;">'+numberWithCommas(list[i].mmBasAmtVatDesc)+'원<br> / ' + list[i].usePrd +'일</span>';
                        }else{
                            html += '				<span class="c-text c-text--type4" id="baseVatAmt_'+i+'" style="white-space: nowrap;">'+numberWithCommas(list[i].mmBasAmtVatDesc)+'원</span>';
                        }
                        html += '			</div>';
                        html += '		</button>';
                        html += '	</div>';
                        html += '	<div class="c-accordion__panel expand c-expand" id="nm2_'+i+'">';
                        html += '		<div class="c-accordion__inside">';
                        html += '		<div class="c-text c-text--type3" id="rateContents'+i+'"></div>';
                        html += '	</div>';
                        html += '</div>';
                        html += '</li>';
                    }
                }

                $(".roamingList").html(html);
                $(".noDataFree").hide();

            }

            if(list.length == 0){
                $("#btn_regSvc").prop("disabled",true);
            }

        });
}

//로밍 해지
function btn_regCancel(selRegNm, soc , prodHstSeq){
    if($("#underAge").val() == "true"){
        MCP.alert("미성년자의 경우 고객센터를 통해서만 해지가 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
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

//로밍 변경
function btn_regChg(rateAdsvcGdncSeq,flag,prodHstSeq){

    var parameterData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val(),
        rateAdsvcGdncSeq : rateAdsvcGdncSeq,
        flag : flag,
        prodHstSeq : prodHstSeq
    });

    openPage('pullPopupByPost','/m/mypage/roamingViewPop.do',parameterData);
}


/** 이용중인 로밍 조회 */
function addRegSvcSearch(){
    var varData = ajaxCommon.getSerializedData({
        addDivCd : addDivCd
        ,ncn :$("#ctn option:selected").val()
    });

    ajaxCommon.getItem({
        id:'myAddSvcListAjax'
        ,cache:false
        ,url:'/m/mypage/myRoamJoinListAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(jsonObj){
        var html = '';
        var data = jsonObj.outList;
        var prodInfoList = jsonObj.prodInfoList;
        if (prodInfoList.length > 0) {
            for (var index = 0; index < prodInfoList.length; index++) {
                var strtDt = data[index].strtDt;
                var endDt = data[index].endDt;
                if (prodInfoList[index].dateType == '1' || prodInfoList[index].dateType == '3' || prodInfoList[index].dateType == '4') { // dataType 1 or 3이면 종료일 가공한 값
                    endDt = data[index].endDttm;
                }

                html += '<tr>';
                html += '<td class="u-ta-center">';
                html += prodInfoList[index].rateAdsvcNm;

                //1-1) 시작일시 ~ 종료일시(ex.2023.07.01 11:00 ~ 2023.07.10 11:00)
                if (prodInfoList[index].dateType == '2' || prodInfoList[index].dateType == '3' || prodInfoList[index].dateType == '4') {
                    html += '<br/>';
                    html += '<span class="c-textarea-wrap__sub" style="position: sticky;">(';
                    html += strtDt.substring(0, 4) + '.' + strtDt.substring(4, 6) + '.' + strtDt.substring(6, 8) + ' ' + strtDt.substring(8, 10) + ':' + strtDt.substring(10, 12) + ' ~';
                    if (prodInfoList[index].dateType == '3' || prodInfoList[index].dateType == '4') {
                        html += '</span>';
                        html += '<br/>';
                        html += '<span class="c-textarea-wrap__sub" style="position: sticky;">';
                        html += endDt.substring(0, 4) + '.' + endDt.substring(4, 6) + '.' + endDt.substring(6, 8) + ' ' + endDt.substring(8, 10) + ':' + endDt.substring(10, 12);
                    }
                    html += ')</span>';
                }
                //1-2) 시작일자 ~ 종료일자(ex.2023.07.01 ~ 2023.07.10)
                if (prodInfoList[index].dateType == '1') {
                    html += '<br/>';
                    html += '<span class="c-textarea-wrap__sub" style="position: sticky;">(';
                    html += strtDt.substring(0, 4) + '.' + strtDt.substring(4, 6) + '.' + strtDt.substring(6, 8) + ' ~';
                    html += '</span>';
                    html += '<br/>';
                    html += '<span class="c-textarea-wrap__sub" style="position: sticky;">';
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
                            html += '<br/>';
                            html += '<span class="c-textarea-wrap__sub" style="position: sticky;">서브회선' + cnt + ' : ';
                            html += ctn;
                            html += '</span>';
                        }
                    }
                }

                //2-2) 서브상품은 연관된 대표회선 마스킹 처리하여 표시(ex. 010-****-1234)
                if (prodInfoList[index].lineType == 'S') {
                    var ctn = data[index].shareMainCtn;
                    if (ctn) {
                        html += '<br/>';
                        html += '<span class="c-textarea-wrap__sub" style="position: sticky;">대표회선 : ';
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

                html += '<td class="u-ta-center">';
                if (data[index].updateFlag == 'Y') {
                    html += '<div class="u-mb--16">';
                    //html += ' <a class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:void(0);"  onclick="btn_regChg(\''+prodInfoList.rateAdsvcGdncSeq+'\','+data[index].updateFlag+'\);">변경</a>';
                    html += ' <a class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:void(0);"  onclick="btn_regChg(\'' + prodInfoList[index].rateAdsvcGdncSeq + '\',\'Y\',\'' + data[index].prodHstSeq + '\');">변경</a>';
                    html += '</div>';
                }
                if (data[index].onlineCanYn == 'Y') {
                    html += '<div>';
                    html += ' <a class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:void(0);" onclick="btn_regCancel(\'' + data[index].socDescription + '\',\'' + data[index].soc + '\',\'' + data[index].prodHstSeq + '\');">해지</a>';
                    html += '<input type="hidden"  value=' + data[index].canCmnt + '/>';
                    html += '</div>';
                }

                html += '</td">';
                html += '</tr>';
            }

            $("#addSvcList").html(html);
            $(".regSvcYn").hide();
            $(".regDataYn").show();
        } else {
            $(".regSvcYn").show();
            $(".regDataYn").hide();
        }
    });
}



//로밍 신청 팝업
function btn_regSvc(){

    var rateAdsvc = "";
    var rateAdsvcNm = "";
    var roamingChk = "";

    $(".listAccordion").each(function(){
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
        MCP.alert("미성년자의 경우 고객센터를 통해서만 가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
            return false;
        });
    }else{
        openPage('pullPopupByPost','/m/mypage/roamingViewPop.do',parameterData);
    }
}

