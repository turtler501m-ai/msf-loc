
var pageObj = {
    chkProductList:[]
    , phoneList:[]
}


var standardCount = 10; //더보기 기준값
var selectColVal_0 = ''; //폰비교 선택값
var selectColVal_1 = ''; //폰비교 선택값
var selectColVal_2 = '';
var compareCnt = 0;
var colorData;
var allPhoneListData;

$(document).ready(function(){


    initColorData();

    //$("#makrCd").val($("input[name='radProduct']:checked").val());/

    $("input[name='chkProduct']:checked").each(function (){
        pageObj.chkProductList.push($(this).val());
    });

    $("input[name='chkProduct']:checked").change(function(){

        let thidId = $(this).attr("id");


        if ("chkProduct_0" == thidId) {
            //전체선택
            if ($(this).is(':checked')) {
                $("#chkProduct_1").prop("checked", true);
                $("#chkProduct_2").prop("checked", true);
                $("#chkProduct_3").prop("checked", true);
            } else {
                $("#chkProduct_1").prop("checked", false);
                $("#chkProduct_2").prop("checked", false);
                $("#chkProduct_3").prop("checked", false);
            }
        } else {
            //전체선택
            if ($(this).is(':checked')) {
                if ($("#chkProduct_1").is(':checked') && $("#chkProduct_2").is(':checked') && $("#chkProduct_3").is(':checked')) {
                    $("#chkProduct_0").prop("checked", true);
                }
            } else {
                $("#chkProduct_0").prop("checked", false);
            }
        }

        pageObj.chkProductList = [];
        $("input[name='chkProduct']:checked").each(function (){
            pageObj.chkProductList.push($(this).val());
        });

        viewPhoneList();
    });


    $(".sortBtn").click(function() {
        var that = $(this);
        var isClass = that.hasClass("is-active");
        if(!isClass){
            $(".sortBtn").each(function(){
                $(this).removeClass("is-active");
                $(this).children("span").remove();
            });
            that.addClass("is-active");
            var orderText = that.text();
            that.html(orderText + '<span class="c-hidden">선택됨</span>');
            $("input[name='listOrderEnum']").val(that.attr('data-value'));
            callPhoneList();
        }
    });

    //상품 체크박스 선택시
    $(document).on("click", "input[id^='chkBadge']", function(){

        compareItem($(this));
    });

    //비교상품 폰선택
    $(document).on("change", "select[id^='phoneSelect_']", function(){
        var phoneSelectId = $(this).attr("id");
        var lastIdx = phoneSelectId.lastIndexOf("_");
        var compareIdx = parseInt(phoneSelectId.substring(lastIdx+1,phoneSelectId.length));

        if($(this).val() == ''){
            initCompareItem(compareIdx);
            return;
        }

        $("#rateSelect_"+compareIdx).prop("disabled",false);
        var rateCd = $("#"+phoneSelectId+" option:selected").data("reprate");

        $("#phone_button_"+compareIdx).prop("classList","c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32");
        callCompareAjaxProd($(this).val(),compareIdx,rateCd,"");

    });

    //비교상품 요금제 선택
    $(document).on("change", "select[id^='rateSelect_']", function(){
        var selId = $(this).attr("id");
        var lastIdx = selId.lastIndexOf("_");
        var compareIdx = parseInt(selId.substring(lastIdx+1,selId.length));

        var prodId = $("select[name='phoneSelect_"+compareIdx+"']").val();

        if (prodId ==  null) {
            MCP.alert("핸드폰을 선택해 주세요.");
            $("select[name='rateSelect_"+compareIdx+"']").val("");
            return;
        }

        var selectColVal = $("select[name='colorSelect_"+compareIdx+"']").val();
        if (selectColVal ==  null) {
            selectColVal = "";
        }

        callCompareAjaxProd(prodId,compareIdx,$(this).val(),selectColVal);

    });

    //비교상품 컬러/용량 선택
    $(document).on("change", "select[id^='colorSelect_']", function(){
        var selId = $(this).attr("id");
        var lastIdx = selId.lastIndexOf("_");
        var compareIdx = parseInt(selId.substring(lastIdx+1,selId.length));

        var prodId = $("select[name='phoneSelect_"+compareIdx+"']").val();
        if (prodId ==  null) {
            MCP.alert("핸드폰을 선택해 주세요.");
            return;
        }
        var rateCd = $("select[name='rateSelect_"+compareIdx+"']").val();
        if (rateCd ==  null) {
            rateCd = "";
        }
        if(compareIdx == 0){
            selectColVal_0 = $(this).val();
        }else if(compareIdx == 1){
            selectColVal_1 = $(this).val();
        }else if(compareIdx == 2){
            selectColVal_2 = $(this).val();
        }
        callCompareAjaxProd(prodId,compareIdx,rateCd,$(this).val());

    });

    //가입하기 버튼 클릭
    $(document).on("click", "button[id^='phone_button_']", function(){

        var btnId = $(this).attr("id");
        var lastIdx = btnId.lastIndexOf("_");
        var compareIdx = btnId.substring(lastIdx+1,btnId.length);

        var prodId = $("select[name='phoneSelect_"+compareIdx+"']").val();
        var colorVal = $("select[name='colorSelect_"+compareIdx+"']").val();
        var rateSelectVal = $("select[name='rateSelect_"+compareIdx+"']").val();
        if(prodId == null || prodId == ''){
            MCP.alert("핸드폰을 선택해 주세요.");
            return;
        }

        if(colorVal == null || colorVal == ''){
            MCP.alert("컬러를 선택해 주세요.");
            return;
        }
        var colorText = $("select[name='colorSelect_"+compareIdx+"'] option:checked").text();
        if(colorText.indexOf('품절') > -1){
            MCP.alert("선택하신 용량/색상은 품절 되었습니다.");
            return;
        }

        var sesplsYn = $("select[name='rateSelect_"+compareIdx+"']").data("sesplsYn");
        if(sesplsYn == 'N' && (rateSelectVal == null || rateSelectVal == '')){
            MCP.alert("요금제를 선택해 주세요.");
        }
        var outprc = $("#outprc_"+compareIdx).data('outprc');
        var prodCtgType = $("#ptype_"+compareIdx).data('ptype');

        document.appForm.prodCtgType.value = prodCtgType;
        document.appForm.outUnitPric.value = outprc;
        document.appForm.selPric.value = outprc;
        document.appForm.rateCd.value = rateSelectVal;
        document.appForm.hndsetModelId.value = colorVal;
        document.appForm.prodId.value = prodId;
        document.appForm.submit();


    });

    //비교상품 팝업 선택
    $("#compareBtn").click(function(){

        if (compareCnt< 1) {
            MCP.alert('비교하실 상품을 선택해 주세요.');
            return;
        }
        callChargeList();
        callPhoneCompareList();

    });

    //폰목록 조회
    callPhoneList();

    //휴대폰비교하기 폰 셀렉트박스 초기화
    initSelectPhoneList();

});

function comapareSessionUpdate(){

    sessionProc('','','','','removeAll',0);
    var sessionIdx  = 0;
    for(var i = 0 ; i < 3 ; i++){
        var prodId = $("select[name='phoneSelect_"+i+"']").val();
        //console.log('idx : ' +i + 'prodId : ' + prodId);
        if (prodId !=  null && prodId != '') {
            var rateCd = $("select[name='rateSelect_"+i+"']").val();
            if (rateCd ==  null) {
                rateCd = "";
            }
            var selectColVal = $("select[name='colorSelect_"+i+"']").val();
            if (selectColVal ==  null) {
                selectColVal = "";
            }
            sessionProc(prodId,rateCd,selectColVal,'','add',sessionIdx);
            sessionIdx ++;
        }
    }
}

function go_phoneView(prodId, rateCd){

    document.phoneViewfrm.prodId.value = prodId;
    document.phoneViewfrm.rateCd.value = rateCd;
    document.phoneViewfrm.submit();

}

var callChargeList = function() {

    allClearCompareItem();

    //세션에 저장되어있는 목록 조회
    sessionProc('','','','','search',0);

};

/**
 비교할 상품의 해당되는 요금제의 가격정보를 가져온다.
 @param prodId : 상품 아이디
 @param idx    : 순서
 @param rateCd : 요금제코드
 */
var callCompareAjaxProd = function(prodId,idx,rateCd,hndsetModelId) {
    var varData = ajaxCommon.getSerializedData({
        prodId : prodId
        ,rateCd : rateCd
        ,hndsetModelId : hndsetModelId
    });
    ajaxCommon.getItem({
        id : 'phoneListCompareAjax',
        cache : false,
        url : '/m/product/phone/phoneListCompareAjax.do',
        data : varData,
        async : false,
        dataType : "json"
    }, function(jsonObj) {
        if (typeof jsonObj.RESULT_MSG != "undefined") {
            alert(jsonObj.RESULT_MSG);
            return false;
        }

        //휴대폰 선택
        $("#phoneSelect_"+idx).val(prodId);
        var sesplsYn = jsonObj.phoneProdBas.sesplsYn; //자급제여부

        var prodCtgId = jsonObj.phoneProdBas.prodCtgId; //자급제여부

        var agrmTrmBase = jsonObj.phoneProdBas.agrmTrmBase ? jsonObj.phoneProdBas.agrmTrmBase : 24;

        var prodCtgType = '01';
        if(sesplsYn == 'Y'){
            prodCtgType = '03';
        }else if(prodCtgId == '04'){
            prodCtgType = '02';
        }

        $("#rateSelect_"+idx+" > option").remove();
        $("#rateSelect_"+idx).append('<option label="요금제 선택" disabled>요금제 선택</option>');
        var mspRateCd = '';
        var firtRateFlag = true;
        $.each(jsonObj.chargeList,function(chargeListIdx,data) {

            //$.each(jsonObj.baseChargeList,function(baseChargeListIdx,baseChargedata) {

                //if(baseChargedata.rateCd == data.rateCd){
                    if(firtRateFlag){
                        mspRateCd = data.rateCd;
                        firtRateFlag = false;
                    }
                    /*var optionHtml = '<option value="'+data.rateCd+'">'+data.rateNm+'</option>';
                    $("#rateSelect_"+idx).append(optionHtml);*/
                    if(agrmTrmBase == data.agrmTrm){
                        let selected = '';
                        if(rateCd != '' && rateCd == data.rateCd){
                            selected += ' selected';
                        }
                        let optionHtml = '<option value="' + data.rateCd + '"' + selected + '>' +
                        (data.rateAdsvcNm ? data.rateAdsvcNm : data.rateNm) + '</option>';

                        $("#rateSelect_"+idx).append(optionHtml);
                    }
                //}

            //});


        });

        if(rateCd != ''){
            mspRateCd = rateCd;
        }

        if(sesplsYn == 'Y'){

            $("#rateSelect_"+idx).val("");
            $("#rateSelect_"+idx).prop("disabled",true);
            $("#rateSelect_"+idx).data("sesplsYn","Y");

            dtHtml = '<dl class="charge-box"></dl>';
            $("#phone_dl_"+idx).html(dtHtml);

            $("#phone_li_"+idx).html('');
            var phoneLiHtml = '';
            phoneLiHtml += '<dl class="phone-comparison__info">';
            phoneLiHtml += '<dt>출고가</dt>';
            phoneLiHtml += '<dd>'+numberWithCommas(jsonObj.phoneProdBas.inUnitPric)+'원</dd>';
            phoneLiHtml += '</dl>';
            phoneLiHtml += '<dl class="phone-comparison__info">';
            phoneLiHtml += '<dt>판매가</dt>';
            phoneLiHtml += '<dd>'+numberWithCommas(jsonObj.phoneProdBas.outUnitPric)+'원</dd>';
            phoneLiHtml += '</dl>';

            $("#phone_li_"+idx).html(phoneLiHtml);

        }else{

            $.each(jsonObj.chargeList,function(chargeListIdx,data) {
                if (mspRateCd == data.rateCd && agrmTrmBase == data.agrmTrm) {
                    if (data.sprtTp === 'KD') {
                        setphoneLiData(data,idx);
                        return false;
                    }

                    if (data.sprtTp === 'PM') {
                        setphoneLiData(data,idx);
                        return false;
                    }

                }
            });

        }

        var phoneCompLiHtml = '';
        $("#phone_comp_li_"+idx).html('');
        phoneCompLiHtml += '<dl id="ptype_'+idx+'" data-ptype="'+prodCtgType+'"  class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>네트워크</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.prodCtgIdLabel)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl id="outprc_'+idx+'" data-outprc="'+jsonObj.phoneProdBas.outUnitPric+'" class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>디스플레이</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyDisp)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>크기</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntySize)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>무게</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyWeight)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>메모리</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyMemr)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>배터리</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyBtry)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>카메라</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyCam)+'</dd>';
        phoneCompLiHtml += '</dl>';
        $("#phone_comp_li_"+idx).html(phoneCompLiHtml);

        var sntyCd = '';
        var atribValNm2 = '';
        $("#colorSelect_"+idx+" > option").remove();
        $.each(jsonObj.phoneProdBas.phoneSntyBasDtosList,function(phoneSntyBasDtosListIdx,data) {
            var sdLbel = "";
            if (data.sdoutYn == 'Y') {
                sdLbel = "[품절]";
            }

            if (phoneSntyBasDtosListIdx == 0){
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;

            }

            var optionHtml = '<option value="'+data.hndsetModelId+'"';

            if(idx == 0 && selectColVal_0 != '' && selectColVal_0 == data.hndsetModelId){
                optionHtml += 'selected >';
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;
            }else if(idx == 1 && selectColVal_1 != '' && selectColVal_1 == data.hndsetModelId){
                optionHtml += 'selected >';
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;
            }else if(idx == 2 && selectColVal_2 != '' && selectColVal_2 == data.hndsetModelId){
                optionHtml += 'selected >';
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;
            }else{
                optionHtml += '>';
            }
            optionHtml += data.atribValNm2+'/'+data.atribValNm1+sdLbel+'</option>';

            $("#colorSelect_"+idx).append(optionHtml);
        });

        $.each(jsonObj.phoneProdBas.phoneProdImgDtoList,function(phoneProdImgDtoDataIdx,phoneProdImgDtoData) {
            if(phoneProdImgDtoData.sntyColorCd == sntyCd){
                $("#phone_img_div_"+idx).html('');
                var imgHtml = '';
                imgHtml += '<div class="phone-box u-mt--32">';
                imgHtml += '<img src="'+phoneProdImgDtoData.phoneProdImgDetailDtoList[0].imgPath+'" alt="'+jsonObj.phoneProdBas.prodNm+' 실물 사진">';
                imgHtml += '<strong class="phone-box__title c-text-ellipsis">';
                imgHtml += '<b>'+jsonObj.phoneProdBas.prodNm+'</b>';
                imgHtml += '</strong>';
                imgHtml += '<p class="phone-box__text">'+atribValNm2+'</p>';
                imgHtml += '<div class="phone-box__colors u-mt--16">';

                var rgbData = '#d3beac';
                $(colorData).each(function() {
                    if(this.dtlCd == phoneProdImgDtoData.sntyColorCd){
                        rgbData = this.expnsnStrVal1;
                    }
                });

                imgHtml += '<span class="phone-box__dot" style="background:'+rgbData+' "></span>';
                imgHtml += '<span class="box__sub">'+phoneProdImgDtoData.atribVal+'</span>';
                imgHtml += '</div>';
                imgHtml += '</div>';

                $("#phone_img_div_"+idx).html(imgHtml);
            }
        });
        $("span[id^='phone_img_div_'] > div > img").each(function(){
            $(this).error(function(){
                $(this).unbind("error");
                $(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
            });
        });

        for(var i = 0 ; i < 3 ; i++){
            if($("#rateSelect_"+i).data("sesplsYn") != 'Y'){

                if(emptyToTyphoon($("#phoneSelect_"+i).val()) != '-' && emptyToTyphoon($("#colorSelect_"+i).val()) != '-' && emptyToTyphoon($("#rateSelect_"+i).val()) != '-'){
                    $("#phone_button_"+i).prop("disabled",false);
                }else{
                    $("#phone_button_"+i).prop("disabled",true);
                }

            }else{
                if(emptyToTyphoon($("#phoneSelect_"+i).val()) != '-' && emptyToTyphoon($("#colorSelect_"+i).val()) != '-'){
                    $("#phone_button_"+i).prop("disabled",false);
                }else{
                    $("#phone_button_"+i).prop("disabled",true);
                }
            }

        }

        comapareSessionUpdate();
    });
};

function setphoneLiData(data,idx){
    var desc = data.mspRateMstDto.rmk;
    desc = desc.replace(/<br>/g, "");
    $("#rateSelect_" + idx).data("sesplsYn", "N");
    $("#rateSelect_" + idx).val(data.mspRateMstDto.rateCd);

    $("#phone_dl_" + idx).html('');
    var dtHtml = '';
    dtHtml += '<dl class="charge-box">';
    dtHtml += '<dt class="charge-box__title">' + data.mspRateMstDto.rateNm + '</dt>';
    dtHtml += '<dd class="charge-box__text">';
    dtHtml += '  <span>음성: ' + data.freeCallCntByMobile + '</span>';
    dtHtml += '  <span>문자: ' + data.freeSmsCntByMobile + '</span>';
    dtHtml += '  <span>데이터: ' + data.freeDataCntByMobile + '</span>';
    dtHtml += '</dd>';
    dtHtml += '</dl>';
    $("#phone_dl_" + idx).html(dtHtml);

    $("#phone_li_" + idx).html('');
    var phoneLiHtml = '';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt>출고가</dt>';
    phoneLiHtml += '<dd>' + numberWithCommas(data.hndstAmt) + '원</dd>';
    phoneLiHtml += '</dl>';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt>기본요금</dt>';
    phoneLiHtml += '<dd>월 ' + numberWithCommas(data.payMnthChargeAmt) + '원</dd>';
    phoneLiHtml += '</dl>';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt>할부원금</dt>';
    phoneLiHtml += '<dd>' + numberWithCommas(data.instAmt) + '원</dd>';
    phoneLiHtml += '</dl>';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt> 월 납부금액';
    phoneLiHtml += '<br>(월 단말요금 + 월 통신요금)</dt>';
    phoneLiHtml += '<dd> 단말 할인 시 ';
    phoneLiHtml += ' 월 ' + numberWithCommas(data.payMnthChargeAmt + data.payMnthAmt) + '원';
    phoneLiHtml += '</dd>';
    phoneLiHtml += '</dl>';
    $("#phone_li_" + idx).html(phoneLiHtml);
}


/**
 비교하기 상품 레이어 영역에 모든값을 초기화 처리시킨다.
 */
var allClearCompareItem = function() {

    //var compareCnt = $("#product_list > li > div > div > span > input[type='checkbox']:checked").length;
    for(var idx = 0 ; idx < 3 ; idx++){

        $("#phoneSelect_"+idx).val("");
        $("#rateSelect_"+idx+" > option ").remove();
        $("#rateSelect_"+idx).append('<option label="요금제 선택" disabled="" selected="">요금제 선택</option>');


        noPhoneHtml = '';
        noPhoneHtml += '<div class="c-box c-box--dotted c-box--dotted--type1 u-mt--32">';
        noPhoneHtml += '<div class="c-box__inner">';
        noPhoneHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
        noPhoneHtml += '<p class="c-text c-text--fs14 u-mt--12 u-co-gray">휴대폰을';
        noPhoneHtml += '<br>선택해 주세요.';
        noPhoneHtml += '</p>';
        noPhoneHtml += '</div>';
        noPhoneHtml += '</div>';

        $("#phone_img_div_"+idx).html(noPhoneHtml);
        noRateHtml = '';
        noRateHtml += '<div class="c-box c-box--dotted c-box--dotted--type2 u-mt--32">';
        noRateHtml += '<div class="c-box__inner">';
        noRateHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
        noRateHtml += '<p class="c-text c-text--fs14 u-mt--12 u-co-gray">요금제를';
        noRateHtml += '<br>선택해 주세요.';
        noRateHtml += '</p>';
        noRateHtml += '</div>';
        noRateHtml += '</div>';

        $("#colorSelect_"+idx+" > option ").remove();
        $("#colorSelect_"+idx).append('<option value="" label="용량/색상">용량/색상</option>');
        $("#colorSelect_"+idx+" > option:eq(1)").prop("selected", true);
        $("#phone_dl_"+idx).html(noRateHtml);

        noLiHtml = '';
        noLiHtml += '<dl class="phone-comparison__info">';
        noLiHtml += '<dt>출고가</dt>';
        noLiHtml += '<dd>-</dd>';
        noLiHtml += '</dl>';
        noLiHtml += '<dl class="phone-comparison__info">';
        noLiHtml += '<dt>기본요금</dt>';
        noLiHtml += '<dd>-</dd>';
        noLiHtml += '</dl>';
        noLiHtml += '<dl class="phone-comparison__info">';
        noLiHtml += '<dt>할부원금</dt>';
        noLiHtml += '<dd>-</dd>';
        noLiHtml += '</dl>';
        noLiHtml += '<dl class="phone-comparison__info">';
        noLiHtml += '<dt> 월 납부금액';
        noLiHtml += '-';
        noLiHtml += '</dt>';
        noLiHtml += '<dd>-</dd>';
        noLiHtml += '</dl>';
        $("#phone_li_"+idx).html(noLiHtml);

        noCompLiHtml = '';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>네트워크</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>디스플레이</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>크기</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>무게</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>메모리</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>배터리</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        noCompLiHtml += '<dl class="phone-comparison__info">';
        noCompLiHtml += '<dt>카메라</dt>';
        noCompLiHtml += '<dd>-</dd>';
        noCompLiHtml += '</dl>';
        $("#phone_comp_li_"+idx).html(noCompLiHtml);
        $("#phone_button_"+idx).prop("classList","c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32");
        $("#phone_button_"+idx).prop("disabled",false);

    }

};

/**
 비교하기 휴대폰영역을 초기화.
 */
var initSelectPhoneList = function() {

    try {

        var varData = ajaxCommon.getSerializedData({
        });
        $.ajax({
            type:"post",
            url : "/m/product/phone/phoneListAllAjax.do",
            data : varData,
            dataType : "json",
            async:false,
            error: function(){
                return;
            },
            complete: function(){

            },
            success:	function(data){
                var resultCode = data.RESULT_CODE;
                var totalCount = data.totalCount;

                for(var phoneSelectIdx = 0 ; phoneSelectIdx < 3 ; phoneSelectIdx++){
                    $("#phoneSelect_"+phoneSelectIdx+" > option").remove();

                    $("#phoneSelect_"+phoneSelectIdx).append('<option value="">휴대폰 선택</option>');
                    if(totalCount > 0 && resultCode == "00000") {
                        allPhoneListData = data.phoneListLte;
                        var listData = data.phoneListLte;
                        $(listData).each(function() {
                            var otpionHtml = '<option lable="'+this.prodNm+'" value="'+this.prodId+'" data-reprate="'+this.repRate+'">'+this.prodNm+'</option>';
                            $("#phoneSelect_"+phoneSelectIdx).append(otpionHtml);
                        });
                    }
                }

            }

        });

    }
    catch(e){
        alert(e.message);
    }
    finally {
    }

};

/**
 비교하기 영역을 초기화 처리시킨다.
 */
var initCompareItem = function(idx) {

    noPhoneHtml = '';
    noPhoneHtml += '<div class="c-box c-box--dotted c-box--dotted--type1 u-mt--32">';
    noPhoneHtml += '<div class="c-box__inner">';
    noPhoneHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
    noPhoneHtml += '<p class="c-text c-text--fs14 u-mt--12 u-co-gray">휴대폰을';
    noPhoneHtml += '<br>선택해 주세요.';
    noPhoneHtml += '</p>';
    noPhoneHtml += '</div>';
    noPhoneHtml += '</div>';
    $("#phone_img_div_"+idx).html(noPhoneHtml);
    noRateHtml = '';
    noRateHtml += '<div class="c-box c-box--dotted c-box--dotted--type2 u-mt--32">';
    noRateHtml += '<div class="c-box__inner">';
    noRateHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
    noRateHtml += '<p class="c-text c-text--fs14 u-mt--12 u-co-gray">요금제를';
    noRateHtml += '<br>선택해 주세요.';
    noRateHtml += '</p>';
    noRateHtml += '</div>';
    noRateHtml += '</div>';
    $("#colorSelect_"+idx+" > option ").remove();
    $("#colorSelect_"+idx).append('<option label="용량/색상" disabled>용량/색상</option>');
    $("#phone_dl_"+idx).html(noRateHtml);
    noLiHtml = '';
    noLiHtml += '<dl class="phone-comparison__info">';
    noLiHtml += '<dt>출고가</dt>';
    noLiHtml += '<dd>-</dd>';
    noLiHtml += '</dl>';
    noLiHtml += '<dl class="phone-comparison__info">';
    noLiHtml += '<dt>기본요금</dt>';
    noLiHtml += '<dd>-</dd>';
    noLiHtml += '</dl>';
    noLiHtml += '<dl class="phone-comparison__info">';
    noLiHtml += '<dt>할부원금</dt>';
    noLiHtml += '<dd>-</dd>';
    noLiHtml += '</dl>';
    noLiHtml += '<dl class="phone-comparison__info">';
    noLiHtml += '<dt> 월 납부금액';
    noLiHtml += '-';
    noLiHtml += '</dt>';
    noLiHtml += '<dd>-</dd>';
    noLiHtml += '</dl>';
    $("#phone_li_"+idx).html(noLiHtml);

    noCompLiHtml = '';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>네트워크</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>디스플레이</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>크기</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>무게</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>메모리</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>배터리</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    noCompLiHtml += '<dl class="phone-comparison__info">';
    noCompLiHtml += '<dt>카메라</dt>';
    noCompLiHtml += '<dd>-</dd>';
    noCompLiHtml += '</dl>';
    $("#phone_comp_li_"+idx).html(noCompLiHtml);
    $("#phone_button_"+idx).prop("classList","c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32 is-disabled");
    $("#rateSelect_"+idx).val("");
    $("#rateSelect_"+idx).prop("disabled",true);
    sessionProc('','','','','remove',idx);

};

/**
 비교팝업 호출
 */
function callPhoneCompareList(){

    var el = document.querySelector("#phone-compare-modal");
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();

}

/**
 폭목록 조회
 */
var callPhoneList = function() {

    try {

        $("#prodCtgId").val($("input[name='radNetwork']:checked").val());
        //$("#makrCd").val($("input[name='radProduct']:checked").val());

        var varData = $("#phonelistfrm").serialize();

        $.ajax({
            type:"post",
            // url : "/m/product/phone/phoneListAjax.do",
            url : "/m/product/phone/phoneAgrListAjax.do",
            data : varData,
            dataType : "json",
            error: function(){
                return;
            },
            complete: function(){

            },
            success:	function(data){
                var resultCode = data.RESULT_CODE;
                var totalCount = data.totalCount;

                pageObj.phoneList = [];
                if(totalCount > 0 && resultCode == "00000") {
                    $("#phoneListNoDataArea").hide();
                    var listData = data.phoneListLte;

                    pageObj.phoneList = listData;
                    viewPhoneList();

                    //$('#phoneSortDivArea').show();
                } else {
                    $("#phoneListNoDataArea").show();
                    //$('#phoneSortDivArea').hide();
                }


                $("#phoneListUlArea > li > div > a > div.phone-list__panel > div.phone-list__image > img").each(function(){
                    $(this).error(function(){
                        $(this).unbind("error");
                        $(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
                    });
                });

                //휴대폰비교하기 초기화
                sessionProc('','','','','',0);

            }

        });

    }
    catch(e){
        alert(e.message);
    }
    finally {
    }

};



    var viewPhoneList = function() {

        $('#phoneListUlArea').empty();
        $(pageObj.phoneList).each(function(index) {

            if (pageObj.chkProductList.includes(this.makrCdEtc)) {

                let htmlStr = '<li class="c-card">';
                htmlStr += '		<div class="c-card__box">';
                htmlStr += '			<a class="phone-list__anchor" href="javascript:;" onClick="go_phoneView(\''+this.prodId+'\',\''+this.rateCd+'\')" title="휴대폰 상세로 이동">';
                htmlStr += '				<div class="phone-list__panel">';
                htmlStr += '					<div class="phone-list__labels">';
                var stckTypeTop = emptyToTyphoon(this.stckTypeTop);
                if(stckTypeTop.indexOf('01') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type1">사은품</span>'; }
                if(stckTypeTop.indexOf('02') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type2">NEW</span>';	}
                if(stckTypeTop.indexOf('12') > -1){	htmlStr += '<span class="c-sticker c-sticker--type12">신규출시</span>';	}
                if(stckTypeTop.indexOf('03') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type3">최고인기</span>';	}
                if(stckTypeTop.indexOf('04') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type4">인기</span>';	}
                if(stckTypeTop.indexOf('05') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type5">일시품절</span>';	}
                if(stckTypeTop.indexOf('06') > -1){	htmlStr += '<span class="c-sticker c-sticker--type6">품절임박</span>';	}
                if(stckTypeTop.indexOf('07') > -1){	htmlStr += '<span class="c-sticker c-sticker--type7">공시확대</span>';		}
                if(stckTypeTop.indexOf('08') > -1){	htmlStr += '<span class="c-sticker c-sticker--type8">출고가인하</span>';		}
                if(stckTypeTop.indexOf('09') > -1){	htmlStr += '<span class="c-sticker c-sticker--type9">단독판매</span>';	}
                if(stckTypeTop.indexOf('10') > -1){	htmlStr += '<span class="c-sticker c-sticker--type10">리퍼폰</span>';	}
                if(stckTypeTop.indexOf('11') > -1){	htmlStr += '<span class="c-sticker c-sticker--type11">전시폰</span>';	}
                if(stckTypeTop.indexOf('13') > -1){	htmlStr += '<span class="c-sticker c-sticker--type13">한정판매</span>';	}

                htmlStr += '					</div>';
                htmlStr += '					<div class="phone-list__image" aria-hidden="true">';
                if('-' == emptyToTyphoon(this.imgPath)){
                    htmlStr += '					<img src="/resources/images/portal/content/img_phone_noimage.png" alt="">';
                }else{
                    htmlStr += '					<img src="'+this.imgPath+'" alt="'+this.prodNm+' 실물 사진">';
                    if (this.layerYn == 'Y') {
                        htmlStr += '<img src="'+ this.layerImageUrl +'" alt="imgLayout">';
                    }
                }
                htmlStr += 					'</div>';

                htmlStr += '					<div class="phone-list__colors">';
                var rgbData = '#d3beac';
                var rgbName = '';
                var arrColor = new Array;
                if(this.colorCodes != null){
                    var colorCodes = this.colorCodes.split(',');
                    // 중복제거
                    $.each(colorCodes,function(idx,val){
                        var atribValCd1 = colorCodes[idx];
                        if(arrColor.indexOf(atribValCd1) == -1){
                            arrColor.push(atribValCd1);
                        }
                    });
                    colorCodes = arrColor;
                    // 중복제거 끝

                    var colorIdNum = 1;
                    $(colorCodes).each(function() {
                        var atribValCd1 = this;
                        $(colorData).each(function() {
                            if(this.dtlCd == atribValCd1.trim()){
                                rgbData = this.expnsnStrVal1;
                                rgbName = this.dtlCdNm;
                            }
                        });
                        htmlStr += '			<button class="phone-list__dot" style="background-color: '+rgbData+'" role="presentation" aria-describedby="color_tooltip_c'+colorIdNum+'_'+index+'">휴대폰 색상';
                        htmlStr += '				<div class="c-tooltip-box c-tooltip-box--auto" id="color_tooltip_c'+colorIdNum+'_'+index+'" role="tooltip">'+rgbName+'</div>';
                        htmlStr += '			</button>';
                        colorIdNum ++;
                    });
                }
                htmlStr += '				</div>';
                htmlStr += '			</div>';


                if(this.sesplsYn == 'Y'){
                    htmlStr += '			<div class="phone-list__info">';
                    htmlStr += '  			<span class="c-label--Network">'+emptyToTyphoon(this.prodCtgIdLabel)+'</span>';
                    htmlStr += '  			<h4 class="phone-list__title c-text-ellipsis">'+this.prodNm+'</h4>';
                    htmlStr += '  			<strong class="phone-list__text">';
                    htmlStr += '  				<b class="u-fs-18">'+numberWithCommas(this.outUnitPric + this.outUnitPric * 0.1)+' 원</b>';
                    htmlStr += '  				<span class="c-text c-text--fs15 u-co-gray-7 u-td-line-through u-ml--6">'+numberWithCommas(this.inUnitPric + this.inUnitPric * 0.1)+'</span>';
                    htmlStr += '  			</strong>';
                    htmlStr += '            <p class="phone-list__sub">(월 통신요금 '+numberWithCommas(this.payMnthAmt)+'원 부터)</p>';
                }else{
                    htmlStr += '			<div class="phone-list__info">';
                    htmlStr += '  			<span class="c-label--Network">'+emptyToTyphoon(this.prodCtgIdLabel)+'</span>';
                    htmlStr += '				<h4 class="phone-list__title-2 c-text-ellipsis">'+this.prodNm+'</h4>';
                    htmlStr += '				<p class="phone-list__infotxt">' + this.repRateNm +'</p>';
                    htmlStr += '			    <p class="phone-list__priceinfo">월 단말요금 <b>'+ numberWithCommas(this.payMnthAmt) +'</b>원 + 월 통신요금 <b>'+ numberWithCommas(this.baseAmt) + '</b>원</p>';
                    htmlStr += '			    <div class="phone-list__price">월<strong>'+ numberWithCommas(this.payMnthAmt + this.baseAmt)+'</strong>원</div>';
                }


                htmlStr += '				</div>';
                htmlStr += '			</a>';

                htmlStr += '			<div class="c-card__badge-2">';
                var recommendRateArr = '';
                var recommendRate = '';
                if(this.sesplsYn != 'Y'){
                    recommendRate = 'KSLTEMSTD#KD'; //추천요금제 없으면 일단..LTE표준으로 임시처리
                    if(this.recommendRate != null){
                        recommendRateArr = this.recommendRate.split(',');
                        recommendRate = recommendRateArr[0];
                    }
                }
                htmlStr += '				<input class="c-checkbox" data-value="'+this.prodCtgId+'"  data-prodId="'+this.prodId+'" data-rateCd="'+this.rateCd+'" data-recrate="'+recommendRate+'"  id="chkBadge0'+index+'" type="checkbox">';
                htmlStr += '				<label class="c-card__badge-label" for="chkBadge0'+index+'">';
                htmlStr += '					<span class="c-hidden">'+this.prodNm+' 비교하기</span>';
                htmlStr += '				</label>';
                htmlStr += '			</div>';
                htmlStr += '	</li>';

                //요금제 가격정보가 존재 해야 한다.
                let chargeList = this.mspSaleSubsdMstListForLowPrice ;  //mspSaleSubsdMstListForLowPrice
                if (chargeList != null && chargeList.length > 0 ) {
                    $('#phoneListUlArea').append(htmlStr);
                }
            }

        });
        if ($('#phoneListUlArea').html() == "") {
            $("#phoneListNoDataArea").show();
            $(".phone-sort-wrap__inner").hide();
        } else {
            $("#phoneListNoDataArea").hide();
            $(".phone-sort-wrap__inner").show();
        }
    }
/**
 체크된 상품이 1개일경우 해당 상품의 해당하는
 정책의 요금제 정보 SELECT option값들을 생성한다.
 */
var makeRateListAjax = function() {

    var varData = ajaxCommon.getSerializedData({

    });
    ajaxCommon.getItem(
        {
            id : 'phoneListCompareAjax',
            cache : false,
            url : '/m/product/phone/phoneRateListAjax.do',
            data : varData,
            dataType : "json"
        },

        function(jsonObj) {

            for(var i = 0 ; i < 3 ; i++){
                $("#rateSelect_"+i+" > option").remove();
                $("#rateSelect_"+i).append('<option label="요금제 선택" disabled>요금제 선택</option>');

                $.each(jsonObj.mspRateMstList,function(idx,data) {
                    var optionHtml = '<option value="'+data.rateCd+'">'+data.rateNm+'</option>';
                    $("#rateSelect_"+i).append(optionHtml);
                });
            }
        }
    );

};

//휴대폰 비교 체크 여부에 따라 세션에 정보수정
var compareItem = function(data) {

    if (data.is(':checked') && compareCnt >= 3) {
        MCP.alert('휴대폰 비교는 3개까지 가능합니다.');
        data.prop("checked",false);
        return;
    }
    var recRate = '';
    if(data.is(':checked')){
        recRate = data.data("recrate");
        if(recRate != '' && recRate.length > 9){
            recRate = recRate.substring(0,9);
        }
        sessionProc(data.data("prodid"),data.data("ratecd"),'','','add',compareCnt);
    }else{
        sessionProc(data.data("prodid"),'','','','remove',compareCnt);
    }



};

//휴대폰 비교정보 세션에 정보수정
function sessionProc(prodId,rateCd,hndsetModelId,sesplsYn,proc,idx) {

    try {

        var varData = ajaxCommon.getSerializedData({
            prodId : prodId
            ,rateCd : rateCd
            ,hndsetModelId : hndsetModelId
            ,sesplsYn : sesplsYn
            ,process : proc
            ,idx : idx
        });

        $.ajax({
            type:"post",
            url : "/m/product/phone/phoneCompareProcAjax.do",
            data : varData,
            dataType : "json",
            async : false,
            error: function(){
                return;
            },
            complete: function(){

            },
            success:	function(jsonObj){
                compareCnt = jsonObj.phoneCompareSaveCnt;
                var compareCntHtml = '';
                compareCntHtml += '<span class="c-hidden">'+jsonObj.phoneCompareSaveCnt+'</span>';
                compareCntHtml += '<i class="c-icon c-icon--num'+jsonObj.phoneCompareSaveCnt+'" aria-hidden="true"></i>';
                $("#phoneInBoxArea").html(compareCntHtml);
                if(jsonObj.phoneComapreData != null){
                    $("input[id^='chkBadge']").prop("checked",false);
                    $.each(jsonObj.phoneComapreData,function(idx,data) {
                        if(data != null && data.prodId != ''){
                            $("input[id^='chkBadge']").each(function(){
                                if(data.prodId == $(this).data("prodid")){
                                    $(this).prop("checked",true);
                                }
                            });
                        }

                        if(proc == 'search'){
                            callCompareAjaxProd(data.prodId,idx,data.rateCd,"");
                        }
                    });
                    makePhoneComparisonBoxArea(jsonObj.phoneComapreData);
                }
            }
        });

    }
    catch(e){
        alert(e.message);
    }
    finally {
    }
}

function makePhoneComparisonBoxArea(list){
    $("#phoneComparisonBoxArea").empty();
    var boxHtml = '';
    for(var i = 0 ; i < 3 ; i++){
        if(typeof list[i] == "object"){
            boxHtml += '<div class="phone-comparison-box__item">';
            boxHtml += '<div class="phone-comparison-box__image" aria-hidden="true">';
            var phoneData;
            $(allPhoneListData).each(function() {
                if(this.prodId == list[i].prodId){
                    phoneData = this;
                }
            });
            boxHtml += '<img src="'+phoneData.imgPath+'" alt="'+phoneData.prodNm+' 실물 사진">';
            boxHtml += '</div>';
            boxHtml += '<div class="phone-comparison-box__text">'+phoneData.prodNm+'</div>';
            boxHtml += '<button type="button" class="c-button" href="javascript:void(0);" onClick="sessionProc(\'\',\'\',\'\',\'\',\'remove\',\''+list[i].idx+'\');">';
            boxHtml += '<span class="sr-only">'+phoneData.prodNm+'-삭제</span>';
            boxHtml += '<i class="c-icon c-icon--delete-ty2" aria-hidden="true"></i>';
            boxHtml += '</button>';
            boxHtml += '</div>';
        }else{
            boxHtml += '<div class="phone-comparison-box__item dummy">';
            boxHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
            boxHtml += '<p class="c-text c-text--fs14 u-co-gray u-mt--12">휴대폰을';
            boxHtml += '<br> 선택해 주세요';
            boxHtml += '</p>';
            boxHtml += '</div>';
        }

    }
    $("#phoneComparisonBoxArea").html(boxHtml);


    $("#phoneComparisonBoxArea > div > div.phone-comparison-box__image > img").each(function(){
        $(this).error(function(){
            $(this).unbind("error");
            $(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
        });
    });
}

function emptyToTyphoon(value){

    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return '-';
    } else{
        return value;
    }

}

function fn_go_banner(linkUrl,bannSeq, bannCtg ,tgt){
    insertBannAccess(bannSeq,bannCtg);
    if(tgt.trim() == '_blank'){
        window.open(linkUrl);
    }else{
        window.location.href  = linkUrl;
    }

}

var initColorData = function(){
    var varData = ajaxCommon.getSerializedData({
        grpCd : 'GD0008'
    });

    ajaxCommon.getItemNoLoading({
        id:'getComCodeColroListAjax'
        ,cache:false
        ,url:'/m/getComCodeListAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(data){
        colorData = data.result;

    });

}