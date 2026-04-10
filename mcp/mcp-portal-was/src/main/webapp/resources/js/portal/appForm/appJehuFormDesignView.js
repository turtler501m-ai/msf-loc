var pageInfo = {
    simpleApplyObj :{}
}

$(window).load(function (){
    $(document).on('change','[name=operType]',function() {
        if($('#radOpenType1').is(':checked') ){
            if(!selfTimeChk()) {
                return;
            }
        }
        initSocCodeList();
        getJoinUsimPrice();
        getGiftList();
        nextStepBtnChk();
        //요금제
    });
    //버튼 ajax 이벤트 세팅(최상단)
    $("input[name=dataType]").on('change',function() {

        var prdtNm = $('#prdtNm').val();  // 상품
        var dataType = $(':radio[name="dataType"]:checked').val();
        $('#bottomTitle1').text(dataType + " / " + prdtNm);
        initView();
        if (getSalePlcyInfo()) {
            initSocCodeList();
            getJoinUsimPrice();
        }
        $('#bottomTitle2').text('');
        $('#divGift').empty();
        $('.giftInfoTxt').hide();


        //2022.10.18 wooki
        //셀프개통 시간을 체크
        var simpleApplyObj;
        ajaxCommon.getItemNoLoading({
            id:'isSimpleApply'
            ,cache:false
            ,url:'/appForm/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            simpleApplyObj = jsonObj ;
        });

        //셀프개통이 선택되어 있고 번호이동이 불가능한 시간이면 번호이동은 disabled 처리
        if($('#radOpenType1').is(':checked') && !simpleApplyObj.IsMnpTime){
            $('input[name=operType][operName="번호이동"]').attr('disabled','disabled');
        }
        nextStepBtnChk();
    });

    $('input[name=radOpenType]').on('change', function (){
        if($(this).attr('id') == 'radOpenType1'){
            $('#selfTxt').show();
            $('#counselTxt').hide();
            $('input[name=dataType]').each(function (){
                if($(this).val() == '3G'){
                    $(this).removeProp('checked').attr('disabled', 'disabled');
                }
            });
            $("._noSelf").hide();
        } else {
            $('#selfTxt').hide();
            $('#counselTxt').show();
            $('#newTxt').show();
            if($('#mnp3Yn').val() == 'Y') {
                $('input[name=operType][operName="번호이동"]').removeAttr('disabled');
            }
            if($('#nac3Yn').val() == 'Y') {
                $('input[name=operType][operName="신규가입"]').removeAttr('disabled');
            }
            $("._noSelf").show();
        }
        $('input[name=operType]:checked').trigger('change');
        nextStepBtnChk();
    });


    $('#cstmrType1').trigger('click');
    if($('#selfOpenYn').val() == 'Y'){ //셀프개통이 가능시 셀프개통으로 선택
        $('#radOpenType1').prop('checked', true).trigger('change');
    } else {
        $('#radOpenType2').prop('checked', true).trigger('change');
    }

    $('input[name="operType"]:not(:disabled)').first().prop('checked', true).trigger('change');
    $('input[name="dataType"]:not(:disabled)').first().prop('checked', true).trigger('change');

    if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
        $('input[name=dataType]').each(function (){
            if($(this).val() == '3G'){
                $(this).removeProp('checked').attr('disabled', 'disabled');
            }
        });
    }

    $('#evntCdPrmt').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            e.preventDefault();
            $('#evntCdPrmtBtn').click();
        }
    });

    /* 요금제 혜택 아코디언 */
    $(document)
    .off('click', '.c-accordion__item > .c-accordion__head .c-accordion__button')
    .on('click', '.c-accordion__item > .c-accordion__head .c-accordion__button', function() {
        var $btn = $(this);
        var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
        var isActive = $btn.hasClass('is-active');

        $btn.toggleClass('is-active', !isActive);
        $btn.attr('aria-expanded', !isActive);

        $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
        $panel.attr('aria-hidden', isActive);
    });
})

var operTypeTxtToggle = function (){
    if($('input[name=operType]:checked').val() == 'MNP3'){
        $('#movTxt').show();
        $('#newTxt').hide();
    } else {
        $('#newTxt').show();
        $('#movTxt').hide();
    }
    getGiftList();
}

var selfTimeChk = function (){
    if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
        var simpleApplyObj;
        ajaxCommon.getItemNoLoading({
            id:'isSimpleApply'
            ,cache:false
            ,url:'/appForm/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            simpleApplyObj = jsonObj ;
        });
        var operType = $('input[name=operType]:checked').val();

        if(!simpleApplyObj.IsMnpTime){
            $('input[name=operType][operName="번호이동"]').attr('disabled','disabled');
        }

        if(operType == undefined || "MNP3" == operType){
            if(!simpleApplyObj.IsMnpTime){
                MCP.alert("셀프 개통 가능한 시간은 신규(08:00~21:50),</br>번호이동(10:00~19:50) 입니다.", function (){
                    $('input[name=operType][operName="신규가입"]').trigger('click');
                });
                return false;
            } else {
                return true;
            }
        } else {

            if (("MNP3" == operType && !simpleApplyObj.IsMnpTime) || ("NAC3" == operType && !simpleApplyObj.IsMacTime )) {
                MCP.alert("셀프 개통 가능한 시간은 신규(08:00~21:50),</br>번호이동(10:00~19:50) 입니다.", function (){
                    $('input[name=operType][operName="신규가입"]').trigger('click');
                    $('#movTxt').hide();
                    $('#newTxt').show();
                });
                return false;
            }else{
                return true;
            }

        }
    } else {
        return true;
    }
}

// 신규(유심)가입 제한 IP 확인
var selfIpChk = function(){

    var operType = $(':radio[name="operType"]:checked').val();
    if(operType != 'NAC3') return true;
    var ipChkResult = false;

    ajaxCommon.getItemNoLoading({
        id:'selfIpChk'
        ,cache:false
        ,url:'/appForm/selfIpChk.do'
        ,data: ""
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        ipChkResult = (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS);
    });

    if(!ipChkResult){
        $('#cstmrType1').trigger('click');
        // 상담사 개통으로 진행
        MCP.alert("상담사 개통으로만 신청 가능합니다.<br>상담사 개통 신청으로 진행합니다.", function(){
            $('#radOpenType2').focus();
            $('#radOpenType2').trigger('click');
            $('#counselTxt').show();
        });
    }

    return ipChkResult;
}


var fnSelfClick = function (){

    var cstmrType = $('[name=cstmrType]:checked').val();
    if($('input[name=cstmrType]:checked').val() == undefined) {
        MCP.alert("고객 유형을 선택 하시기 바랍니다.", function (){
            $('input[name=cstmrType]').eq(0).focus();
        });
        return ;
    }

    var dataType = $('[name=dataType]:checked').val();
    if ("LTE" != dataType && "5G" != dataType) {
        MCP.alert("유심유형을 LTE , 5G 중에 선택 하시기 바랍니다.", function (){
            $('input[name=dataType]').eq(0).focus();
        });
        return ;
    }

    var operType = $(':radio[name="operType"]:checked').val()
    if ("MNP3" != operType && "NAC3" != operType) {
        MCP.alert("가입유형을 번호이동 , 신규 중에 선택 하시기 바랍니다. ", function (){
            $('input[name=operType]').eq(0).focus();
        });
        return ;
    }

    // 요금제
    var socCode = $("#selSocCode").val();
    if(socCode== '' || socCode == undefined ){
        MCP.alert("요금제를 선택 하시기 바랍니다.", function (){
            $("#selSocCode").focus();
        });
        return;
    }

    // 이벤트 코드
    if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
        MCP.alert("입력하신 이벤트 코드의 사용가능 여부를 확인 바랍니다.", function(){
            $("#evntCdPrmt").focus();
        });
        return;
    }

    // 셀프개통 시간 체크 + 셀프개통 신규(유심) IP 체크
    if(selfTimeChk() && selfIpChk()){
        $("onOffType").val("5");

        var varData = ajaxCommon.getSerializedData({grpCd: "pSimpleAuth"});
        ajaxCommon.getItemNoLoading({
                id: 'getAuthCodeListAjax'
                ,cache: false
                ,url: '/getCodeListAjax.do'
                ,data: varData
                ,dataType: "json"
                ,async: false
            }
            ,function(objList){
                var authList= [];
                if(objList != null){
                    for(var i=0; i<objList.length; i++){
                        authList.push(objList[i].dtlCdNm);
                    }
                    $("#authListDesc").html(authList.join(", "));
                }else{
                    $("#authListDesc").html("선택 가능한 본인인증 방법이 없습니다");
                }

                $('#chkModalPop').trigger('click');
            });
    }
}

//서식지로 이동
var goAppFrom = function() {
    $("#onOffType").val("3");

    if($('input[name=cstmrType]:checked').val() == undefined){
        MCP.alert('고객 유형을 선택해 주세요.', function (){
            $('input[name=cstmrType]').eq(0).focus();
        });
        return false;
    }

    if($('input[name=radOpenType]:checked').val() == undefined){
        MCP.alert('개통 유형을 선택해 주세요.', function (){
            $('input[name=radOpenType]').eq(0).focus();
        });
        return false;
    }

    if($('input[name=operType]:checked').val() == undefined){
        MCP.alert('가입 유형을 선택해 주세요.', function (){
            $('input[name=operType]').eq(0).focus();
        });
        return false;
    }

    if($('input[name=dataType]:checked').val() == undefined){
        MCP.alert('서비스 유형을 선택해 주세요.', function (){
            $('input[name=dataType]').eq(0).focus();
        });
        return false;
    }

    // 요금제
    var socCode = $("#selSocCode").val();
    if(socCode== '' || socCode == undefined ){
        MCP.alert("요금제를 선택 하시기 바랍니다.", function (){
            $("#selSocCode").focus();
        });
        return;
    }

    // 이벤트 코드
    if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
        MCP.alert("입력하신 이벤트 코드의 사용가능 여부를 확인 바랍니다.", function(){
            $("#evntCdPrmt").focus();
        });
        return;
    }

    $("#authListDesc").html("신용카드, 네이버 인증서, 카카오 본인인증, PASS 인증서, toss 인증서, 범용인증서");
    $('#chkModalPop').trigger('click');
}

var confirmNextStep = function (type){

    //임시저장 처리
    var varData = {};
    var onOffType = $('input[name=radOpenType]:checked').val();
    var cntpntShopId = $('#cntpntShopId').val();
    var cstmrType = $('input:radio[name=cstmrType]:checked').val();

    varData = ajaxCommon.getSerializedData({
        preUrlType : '1'
        , operType : $('input[name=operType]:checked').val()		//가입유형(번이,신규)
        , cntpntShopId : cntpntShopId                               //접점코드
        , rateCd : $('#selSocCode').val()							//요금제코드
        , prdtSctnCd : $('input[name=dataType]:checked').val()		//서비스유형(LTE,3G,5G)
        , reqBuyType : 'UU'											//UU:유심단독구매 MM:단말구매
        , modelId : $('#prdtId').val()			                    //모델코드(ex:K7006037) prdtId ==> modelId
        , serviceType : 'PO'										//PP: 선불, PO : 후불
        , socCode : $('#selSocCode').val()						    //요금제코드(ex:PL20B9416)
        , modelSalePolicyCode : $('#salePlcyCd').val()				//정책코드
        , onOffType : onOffType                         			//구분코드 5 온라인(셀프개통) 0 온라인
        , tmpStep : '0'												//임시저장 단계 0 : 요금제설계 --> 가입신청중에 표시 생략, 1 : 본인확인 및 약관동의, 2 : 유심정보 및 신분증 확인, 3 : 가입신청 정보, 4 : 부가서비스 정보, 5 : 납부정보 및 가입정보 확인
        , settlAmt : $('#totalPrice').text().replace(/,/gi, '')		//임시 최종 결제 금액
        , cstmrType : cstmrType
        , sesplsYn : 'N'
        , sesplsProdId : ''
        , usimKindsCd : ''
        , uploadPhoneSrlNo : '0'
        , evntCdPrmt : $.trim($("#evntCdSeq").val())
    });

    ajaxCommon.getItem({
            id:'insertAppFormTempSave'
            ,cache:false
            ,url:'/appForm/insertAppFormTempSave.do'
            ,data: varData
            ,dataType:"json"
            ,async:false

        }
        ,function(data){
            if(data.resultCd == '0000'){;

                ajaxCommon.createForm({
                    method : 'post'
                    ,action : '/appForm/appJehuForm.do'
                });
                ajaxCommon.attachHiddenElement("requestKey",data.requestTempKey);
                ajaxCommon.formSubmit();
            }else{
                MCP.alert(data.msg);
            }
        });
}

//dataType(서비스유형) 변경 시 판매정책 다시 가져오기
var getSalePlcyInfo = function(){

    var rtnVal = true;

    var dataType = $(':radio[name="dataType"]:checked').val();
    var cntpntShopId = $('#cntpntShopId').val();
    var varData = ajaxCommon.getSerializedData({
        plcySctnCd: dataType
        , cntpntShopId : cntpntShopId
    });

    ajaxCommon.getItemNoLoading({
        cache:false
        ,url:'/appform/getSalePlcyInfo.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
            $("#salePlcyCd").val(jsonObj.salePlcyCd);
            $("#enggMnthCnt").val(jsonObj.enggMnthCnt);
            $("#prdtId").val(jsonObj.prdtId);
            $("#prdtNm").val(jsonObj.prdtNm);
        } else {
            rtnVal = false ;
            MCP.alert(jsonObj.RESULT_MSG, function (){
                location.reload();
            });
        }
    });
    return rtnVal ;
};


var initView = function() {
    //$('#bottomRateType').html("");
    //$('#bottomRateType2').html("");
    $('#bottomTitle').html("");
    $('#searchNm').val('');

    $('#dcAmt').html('0 원');
    $('#addDcAmt').html('0 원');
    $('#baseAmt').html('0 원');
    $('#totalPrice').html("0");
    $('#totalPrice2').html("0");
    $('#totalPriceBottom').html("0");
    $('#promotionDcAmtTxt').html("0 원");
    $("#joinPriceTxt").html("0 원");
}


//판매 요금제 별 가입비/유심비 면제 여부 조회
var getJoinUsimPriceInfo = function() {
    var cntpntShopId = $('#cntpntShopId').val();
    var socCode = $("#selSocCode").val();
    var varData = "";

    if(CONTPNT_SHOP_ID.BASE == cntpntShopId) {
        varData = ajaxCommon.getSerializedData({
            dtlCd:socCode
            ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfo
        });
    }else{
        varData = ajaxCommon.getSerializedData({
            dtlCd:cntpntShopId
            ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfoOther
        });
    }

    ajaxCommon.getItem({
            id:'getJoinUsimPriceInfo'
            ,cache:false
            ,url:'/getCodeNmAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            if (jsonObj != null && jsonObj.expnsnStrVal1 == "Y") {
                $("#joinPriceIsPay").val("Y");
            } else {
                $("#joinPriceIsPay").val("N");
            }
            getJoinUsimPrice();
        });
}

var getJoinUsimPrice = function() {
    var operType = $(':radio[name="operType"]:checked').val() ;
    var dataType = $(':radio[name="dataType"]:checked').val();
    if (dataType == undefined) {
        return ;
    }
    //가입비조회
    var varData = ajaxCommon.getSerializedData({
        dataType: dataType
        , operType : operType
        ,rateCd : ''
    });

    ajaxCommon.getItem({
        id:'getUsimInfo'
        ,cache:false
        ,url:'/usim/selectUsimBasJoinPriceAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        if ('0001' ==  jsonObj.RESULT_CODE) {
            //번호이동 수수료
            if(operType==OPER_TYPE.MOVE_NUM){
                $('#move_01').show();
                $('#move_02').show();
                $('#moveYn').val('Y');
            }else{
                $('#move_01').hide();
                $('#move_02').hide();
                $('#moveYn').val('N');
            }

            var joinUsimPrice = jsonObj.selectJoinUsimPrice;
            if (joinUsimPrice != undefined && joinUsimPrice.length > 0) {
                $("#joinPrice").val(numberWithCommas(joinUsimPrice[0].joinPrice));
            }
            showJoinUsimPrice();
        }
    });
}

var showJoinUsimPrice = function() {
    var joinPriceIsPay = $("#joinPriceIsPay").val();

    if (joinPriceIsPay == "Y") {
        $("#joinPriceTxt").html(numberWithCommas($("#joinPrice").val()) + ' 원');
    } else {
        $("#joinPriceTxt").html('<span class="c-text u-td-line-through">' + numberWithCommas($("#joinPrice").val()) + ' 원</span>(무료)');
    }
}

var nextStepBtnChk = function (){

    // 이벤트코드 프로모션 추가
    var evntCdPrmtValidation = true;
    if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
        evntCdPrmtValidation = false;
    }

    if($('input[name=radOpenType]:checked').val() != undefined && $('input[name=operType]:checked').val() != undefined
        && $('input[name=dataType]:checked').val() != undefined && $('#selSocCode').val() != undefined
        && evntCdPrmtValidation){
        $('#nextStepBtn').removeClass('is-disabled');
    }else{
        $('#nextStepBtn').addClass('is-disabled');
    }

    if($('input[name=operType]:checked').attr('operName') != undefined){
        $('#joinInfoDl1').show().find('#joinInfo1').text($('input[name=operType]:checked').attr('operName'));
    }else{
        $('#joinInfoDl1').hide().find('#joinInfo1').text('');
    }
    if($('#selSocCode').val() != undefined){
        $('#joinInfoDl5').show().find('#joinInfo5').text();
        $('#bottomTitle').show().text();
    }else{
        $('#joinInfoDl5').hide().find('#joinInfo5').text('');
        $('#bottomTitle').hide().text('');
    }
    if($('input[name=dataType]:checked').val() != undefined){
        $('#joinInfoDl4').show().find('#joinInfo4').text($('input[name=dataType]:checked').val());
    }else{
        $('#joinInfoDl4').hide().find('#joinInfo4').text('');
    }
}

var nextStep = function (){
    if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
        fnSelfClick();
    }else{
        goAppFrom();
    }
}


var getGiftList = function (){
    var onOffType  = ajaxCommon.isNullNvl($('input[name=radOpenType]:checked').val(), '');    // 모집경로
    var operType   = ajaxCommon.isNullNvl($('input[name=operType]:checked').val(), '');  // 가입유형
    var reqBuyType = 'UU'; // 구매유형
    var agrmTrm    = ajaxCommon.isNullNvl($("#enggMnthCnt").val() , '0'); // 약정기간
    var rateCd     = ajaxCommon.isNullNvl($('#selSocCode').val(), '');    // 요금제
    var orgnId     = ajaxCommon.isNullNvl($('#cntpntShopId').val(), '');    // 조직
    var modelId    = ajaxCommon.isNullNvl($('#prdtId').val(), '');        // 제품

    if(onOffType == '' || operType == '' || reqBuyType == '' || agrmTrm == '' || rateCd == '' || modelId == '') { return false; }
    var varData = ajaxCommon.getSerializedData({
        onOffType  : onOffType
        , operType   : operType
        , reqBuyType : reqBuyType
        , agrmTrm    : agrmTrm
        , rateCd     : rateCd
        , orgnId     : orgnId
        , modelId    : modelId
    });

    ajaxCommon.getItem({
            id : 'getGiftList'
            , cache : false
            , async : false
            , url : '/benefit/giftBasListWithEvntCdAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(jsonObj){

          var $divGift = $('#divGift');
          $divGift.empty();

          // 이벤트 코드 프로모션 처리
          if(rateCd != $("#evntCdPrmtSoc").val()){
            initEvntCdPrmt();
            $("#evntCdPrmtSoc").val(rateCd);
            if(jsonObj.evntCdPrmtYn == "Y") $("#evntCdPrmtWrap").show();
          }

          // 혜택요약 관련 정보
          var giftPrmtList = jsonObj.rateGiftPrmtList;
          var giftPrmtWireList = jsonObj.wireRateGiftPrmtList;
          var giftPrmtFreeList = jsonObj.freeRateGiftPrmtList;
          var mainGiftPrmtList = jsonObj.mainGiftPrmtList;

          //기존 혜택요약(모바일)
          var giftTotalPrice = jsonObj.totalPrice;

          //인터넷 혜택요약
          var totalWirePrice = jsonObj.totalWirePrice || 0;

          //무료 혜택요약
          var totalFreeCount = jsonObj.totalFreeCount || 0;

          // 요금제 혜택요약 미존재
          if(mainGiftPrmtList == null || mainGiftPrmtList.length <= 0){
              $divGift.hide();
              //$('.giftInfoTxt').hide();
              return;
          }

          // 요금제 혜택요약 존재
          var innerHtml = "";
          var totalMainPrice = jsonObj.totalMainPrice;
          var totalMainCount = jsonObj.totalMainCount;
          var fallbackUrl = "/resources/images/portal/content/img_phone_noimage.png";

          innerHtml += "<h3 class='c-form__title--type2'>가입 사은품</h3>\n";
          innerHtml += "<p class='gift-tit__sub'>선택 사은품이 있는 경우 신청 마지막 단계에서 선택 가능</p>\n";
          innerHtml += "<hr class='gift-hr'>\n";

             innerHtml += `<div class="u-mb--24" id="eventCdDiv" style="display: none;">\n`
              innerHtml += `<p>이벤트 코드 <span class="u-co-red">"<em id="eventCdName"></em>"</span>이/가 적용되었습니다.</p>\n`;
              innerHtml += `<p class="u-fs-18 u-fw--bold" id="eventExposedText"></p>\n`;
              innerHtml += `</div>\n`

          if(0 < totalMainPrice){
              innerHtml += `<p class="summary-badge-list__title">최대 <span class="u-co-sub-4"><em>${totalMainPrice / 10000}</em>만원</span> 가입 혜택</p>\n`;
          }else{
              innerHtml += `<p class="summary-badge-list__title">최대 <span class="u-co-sub-4"><em>${totalMainCount}</em>개</span> 가입 혜택</p>\n`;
          }

          innerHtml += '<div class="c-accordion--summary sub-category">\n'
          innerHtml += '  <div class="c-accordion__box">\n';

          // 모바일 혜택
          if (giftPrmtList.length > 0) {
              innerHtml += '                <div class="c-accordion__item mobile">\n';
              innerHtml += '                  <div class="c-accordion__head">\n';
              innerHtml += '                    <strong class="c-accordion__title">\n';
              innerHtml += `                      <p><span class="rate-banner__benefit-label"><em>모바일 혜택</em></span>최대 <em>${giftTotalPrice / 10000}</em>만원</p>\n`;
              innerHtml += '                    </strong>\n';
              innerHtml += '                    <button class="c-accordion__button is-active" type="button" aria-expanded="true">\n';
              innerHtml += '                      <span class="c-hidden">펼쳐보기</span>\n';
              innerHtml += '                    </button>\n';
              innerHtml += '                  </div>\n';
              innerHtml += '                  <div class="c-accordion__panel expand" aria-hidden="false" style="display: block;">\n';
              innerHtml += '                    <div class="c-accordion__inside">\n';
              innerHtml += '                      <ul class="summary-badge-list">\n';

              giftPrmtList.forEach(item => {
                  const popupUrl = item.popupUrl || '';
                  innerHtml += '                        <li class="summary-badge-list__item">\n';
                  innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                  innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                  innerHtml += '                          </div>\n';
                  if (popupUrl) {
                      innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrl}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
                  } else {
                      innerHtml += `    					<p>${item.giftText}</p>\n`;
                  }
                  innerHtml += '                        </li>\n';
              });

              innerHtml += '                      </ul>\n';
              innerHtml += '                    </div>\n';
              innerHtml += '                  </div>\n';
              innerHtml += '                </div>\n';
          }

          // 인터넷 혜택
          if (giftPrmtWireList.length > 0) {
              innerHtml += '                <div class="c-accordion__item">\n';
              innerHtml += '                  <div class="c-accordion__head">\n';
              innerHtml += '                    <strong class="c-accordion__title">\n';
              if (totalWirePrice > 0) {
                  innerHtml += `              	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${totalWirePrice / 10000}</em>만원</p>\n`;
              } else {
                  innerHtml += `                  <p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${giftPrmtWireList.length}</em>개</p>\n`;
              }
              innerHtml += '                    </strong>\n';
              innerHtml += '                    <button class="c-accordion__button" type="button" aria-expanded="false">\n';
              innerHtml += '                      <span class="c-hidden">펼쳐보기</span>\n';
              innerHtml += '                    </button>\n';
              innerHtml += '                  </div>\n';
              innerHtml += '                  <div class="c-accordion__panel expand" aria-hidden="true">\n';
              innerHtml += '                    <div class="c-accordion__inside">\n';
              innerHtml += '                      <ul class="summary-badge-list">\n';

              giftPrmtWireList.forEach(item => {
                  const popupUrl = item.popupUrl || '';
                  innerHtml += '                        <li class="summary-badge-list__item">\n';
                  innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                  innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                  innerHtml += '                          </div>\n';
                  if (popupUrl) {
                      innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrl}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
                  } else {
                      innerHtml += `    					<p>${item.giftText}</p>\n`;
                  }
                  innerHtml += '                        </li>\n';
              });

              innerHtml += '                      </ul>\n';
              innerHtml += '                    </div>\n';
              innerHtml += '                  </div>\n';
              innerHtml += '                </div>\n';
          }

          // 무료 혜택
          if (giftPrmtFreeList.length > 0) {
              innerHtml += '                <div class="c-accordion__item">\n';
              innerHtml += '                  <div class="c-accordion__head">\n';
              innerHtml += '                    <strong class="c-accordion__title">\n';
              innerHtml += '                      <p><span class="rate-banner__benefit-label"><em>&thinsp;&nbsp;&nbsp;추가 혜택&thinsp;&nbsp;</em></span><em>엠모바일 고객 한정</em></p>\n';
              innerHtml += '                    </strong>\n';
              innerHtml += '                    <button class="c-accordion__button" type="button" aria-expanded="false">\n';
              innerHtml += '                      <span class="c-hidden">펼쳐보기</span>\n';
              innerHtml += '                    </button>\n';
              innerHtml += '                  </div>\n';
              innerHtml += '                  <div class="c-accordion__panel expand" aria-hidden="true">\n';
              innerHtml += '                    <div class="c-accordion__inside">\n';
              innerHtml += '                      <ul class="summary-badge-list">\n';

              giftPrmtFreeList.forEach(item => {
                  const popupUrl = item.popupUrl || '';
                  innerHtml += '                        <li class="summary-badge-list__item">\n';
                  innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                  innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                  innerHtml += '                          </div>\n';
                  if (popupUrl) {
                      innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrl}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
                  } else {
                      innerHtml += `   					<p>${item.giftText}</p>\n`;
                  }
                  innerHtml += '                        </li>\n';
              });

              innerHtml += '                      </ul>\n';
              innerHtml += '                    </div>\n';
              innerHtml += '                  </div>\n';
              innerHtml += '                </div>\n';
          }

          innerHtml += '  </div>\n'
          innerHtml += '</div>\n';

          $divGift.html(innerHtml);
          $divGift.show();
          //$('.giftInfoTxt').show();
          KTM.initialize();
        });
}

$("input:radio[name=cstmrType]").click(function(){
    var thisVal = $(this).val();
    if (thisVal == CSTMR_TYPE.NM) {
        $("._isTeen").show();
        $("._isForeigner").hide();
        $("._isLocal").hide();
        $('#radOpenType1').attr('disabled', 'disabled');
        $('#radOpenType2').trigger('click');
    }else if (thisVal == CSTMR_TYPE.FN) {
        $("._isTeen").hide();
        $("._isForeigner").show();
        $("._isLocal").hide();
        $('#radOpenType1').attr('disabled', 'disabled');
        $('#radOpenType2').trigger('click');
    }else{
        $("._isTeen").hide();
        $("._isForeigner").hide();
        $("._isLocal").show();
        if($('#selfOpenYn').val() == 'Y') {
            $('#radOpenType1').removeAttr('disabled');
        }
    }

});

/** 요금제 조회 */
var initSocCodeList = function(){
    var salePlcyCd =  $("#salePlcyCd").val();
    var agrmTrm = ajaxCommon.isNullNvl($("#enggMnthCnt").val(), "0");
    var modelMonthlyVal = ajaxCommon.isNullNvl($("#modelMonthly").val(), "0");
    var selSocCode =  $("#selSocCode").val() || $("#socCode").val();

    var varData = ajaxCommon.getSerializedData({
        salePlcyCd:    salePlcyCd   // 정책
        ,prdtId:       $('#prdtId').val()                // 상품
        ,orgnId:       $("#cntpntShopId").val()          // 조직
        ,operType:     $('input[name=operType]:checked').val()               // 가입유형
        ,rateCd:       $("#socLimitYn").val() == "Y" ? $("#socCode").val() :  ""   // 요금제
        ,onOffType:    $('input[name=radOpenType]:checked').val()   // 개통유형
        ,noArgmYn:     "Y"
        ,agrmTrm:      agrmTrm                 // 약정
        ,plcySctnCd:   "02"                    // 정책구분 (02: 유심)
        ,modelMonthly: modelMonthlyVal         // 할부
    });

    ajaxCommon.getItemNoLoading({
            id:'listChargeInfoAjax'
            ,url:'/msp/listChargeInfoAjax.do'
            ,data: varData
            ,dataType:"json"
            ,cache:false
        }
        ,function(objList){
            if (objList.length > 0) {

                // 초기화
                $("#selSocCode option").remove();
                $('#selSocCode').removeAttr('disabled');
                for (var i=0 ; i < objList.length ; i++) {
                    var arr = [];
                    arr.push("<option value="+objList[i].rateCd+" agrmTrm="+objList[i].agrmTrm+" rateNm='"+objList[i].rateNm+"'");
                    arr.push(" >" +objList[i].rateNm+"</option>");
                    $("#selSocCode").append(arr.join(''));
                    $("#selSocCode option").last().data(objList[i]);
                }

                // 진입 요금제 선택처리
                $("#selSocCode").parent().addClass('has-value');
                $("#selSocCode").val(selSocCode);

                // 진입요금제가 선택되지 않는 경우 has-value 클래스 제거
                // 진입요금제가 선택된 경우 플로팅바의 요금제 내용 세팅
                if($("#selSocCode").val() == null){
                    $("#selSocCode").parent().removeClass('has-value');
                    //이벤트 코드 초기화
                    initEvntCdPrmt();
                }else{
                    setSocCodeInfo();  // 요금제 화면 세팅
                    getGiftList();     // 사은품 조회
                }
                nextStepBtnChk();
            } else {
                MCP.alert("요금제 정보가 없습니다.",function (){
                    $("#selSocCode option").remove();
                    $("#selSocCode").prop("disabled", true);
                    $(".nextStepBtn").prop("disabled", true);  // 가입하기 버튼 비활성화
                    window.close();
                });
            }
        });
}

/** 요금제 변경 이벤트 */
$("#selSocCode").change(function() {
    setSocCodeInfo();       // 요금제 화면 세팅
    getJoinUsimPriceInfo();
    getGiftList();          // 사은품 조회
    nextStepBtnChk();
});

/** 요금제 화면 세팅 */
var setSocCodeInfo = function(){

    var itemObj = $("#selSocCode").find("option:selected").data();
    var rateNm = itemObj.rateNm;
    rateNm = ajaxCommon.isNullNvl(rateNm, "-");

    var operNm = $('input[name=operType]:checked').attr("operName");
    var salePlcyCd = ajaxCommon.isNullNvl(itemObj.salePlcyCd, "");
    $('#salePlcyCd').val(salePlcyCd);

    $('#bottomTitle2').html(operNm + ' / ' + rateNm);
    $("#bottomTitle").html(rateNm);
    $('#joinInfoDl5').show().find('#joinInfo5').text(rateNm);

    $("#totalPrice2").html(numberWithCommas(itemObj.payMnthChargeAmt));                // 월 통신요금
    $("#baseAmt").html(numberWithCommas(itemObj.baseVatAmt)+" 원");                    // 기본요금
    $("#dcAmt").html(numberMinusWithCommas(itemObj.dcVatAmt)+" 원");                   // 기본할일
    $("#addDcAmt").html(numberMinusWithCommas(itemObj.addDcVatAmt)+" 원");             // 요금할인
    $("#promotionDcAmtTxt").html(numberMinusWithCommas(itemObj.promotionDcAmt)+" 원"); // 프로모션 할인

    var modelMonthlyVal = ajaxCommon.isNullNvl($("#modelMonthly").val(), "0");
    if ("0" == modelMonthlyVal) {
        $("#totalPrice").html(numberWithCommas(itemObj.payMnthChargeAmt));             // 월 납부금액
        $("#totalPriceBottom").html(numberWithCommas(itemObj.payMnthChargeAmt));       // 월 납부금액
    } else {
        $("#totalPrice").html(numberWithCommas(itemObj.payMnthAmt+itemObj.payMnthChargeAmt));       // 월 납부금액
        $("#totalPriceBottom").html(numberWithCommas(itemObj.payMnthAmt+itemObj.payMnthChargeAmt)); // 월 납부금액
    }
}

// 이벤트코드 검증
var checkEvntCdPrmt = function(){

    var rateCd = ajaxCommon.isNullNvl($('#selSocCode').val(), "");
    if(rateCd == ""){
        MCP.alert("요금제를 먼저 선택해 주세요.");
        return false;
    }

    if($("#isEvntCdPrmtAuth").val() == "1"){
        MCP.alert("이미 이벤트 코드가 확인 되었습니다.");
        return false;
    }

    var evntCdPrmt = $.trim($("#evntCdPrmt").val());
    if(evntCdPrmt == ""){
        MCP.alert("이벤트 코드를 입력해 주세요.");
        return false;
    }

    var varData = ajaxCommon.getSerializedData({
        evntCdPrmt : evntCdPrmt
        ,rateCd : rateCd
    });

    ajaxCommon.getItem({
        id : 'checkEvntCdAjax',
        cache : false,
        url : '/benefit/checkEvntCdAjax.do',
        data : varData,
        dataType : 'json'
    },function(jsonObj) {

        if(jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
            MCP.alert("이벤트 코드가 확인 되었습니다.");
            $("#evntCdPrmt").prop("disabled", true);
            $('#evntCdPrmtBtn').addClass('is-disabled');
            $("#isEvntCdPrmtAuth").val("1");
            $("#eventCdName").text(evntCdPrmt);
            $("#eventCdDiv").show();
            $("#eventExposedText").text(jsonObj.getEventVal.exposedText);
            $("#evntCdSeq").val(jsonObj.getEventVal.ecpSeq);
            $('#evntCdTryBtn').removeClass('is-disabled');
            nextStepBtnChk();
        }else{
            MCP.alert("이벤트 코드가 일치하지 않습니다.<br>확인 후 정확하게 입력해 주세요.");
        }
    });
}

// 이벤트코드 프로모션 관련 내용 초기화
function initEvntCdPrmt(){
    $("#evntCdPrmtWrap").hide();
    $("#evntCdPrmt").val("");
    $("#evntCdPrmt").parent().removeClass("has-value");
    $("#evntCdPrmt").prop("disabled", false);
    $("#evntCdPrmtBtn").removeClass('is-disabled');
    $("#isEvntCdPrmtAuth").val("");
    $("#evntCdPrmtSoc").val("");
}

//이벤트코드 재입력
function tryEvntCdPrmt(){
    $("#evntCdPrmt").prop("disabled", false);
    $("#isEvntCdPrmtAuth").val("");
    $("#evntCdPrmt").val("");
    $("#eventCdDiv").hide();
    $("#evntCdTryBtn").addClass('is-disabled');
    $("#evntCdPrmtBtn").removeClass('is-disabled');
}