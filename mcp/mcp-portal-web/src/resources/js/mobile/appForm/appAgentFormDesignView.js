
$(document).ready(function() {

    var wrap = document.querySelector('.ly-wrap');
    wrap.classList.add('use-bs');

    initSocCodeList(true);          // 요금제 조회
    initOperTypeInfo();             // 가입유형 화면 세팅
    initDataTypeInfo();             // 서비스유형 화면 세팅
    getJoinUsimPriceInfo();         // 가입비 면제여부 조회

    /** 가입유형 변경 이벤트 */
    $("input:radio[name=operTypeRadio]").change(function() {
        initSocCodeList(false);      // 요금제 조회
        initOperTypeInfo();          // 가입유형 화면 세팅
        getJoinUsimPrice();          // 가입비 조회
    });

    /** 요금제 변경 이벤트 */
    $("#selSocCode").change(function() {
        setSocCodeInfo();       // 요금제 화면 세팅
        getJoinUsimPriceInfo(); // 가입비 면제여부 조회
        getGiftList();          // 사은품 조회
    });

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

});  // end of document.ready-------------------------------------


/** 가입가능한 가입유형 존재여부 체크 */
var isEnableOperType = function() {
    var operTypeCnt = $("input:radio[name=operTypeRadio]").length;
    var disabledCnt = $("input:radio[name=operTypeRadio]:disabled").length;
    return operTypeCnt > disabledCnt;
}

/** 셀프개통 가능 시간 체크 */
var selfTimeChk = function() {

    var simpleApplyObj;
    ajaxCommon.getItemNoLoading({
        id: 'isSimpleApply'
        ,url: '/m/appForm/isSimpleApplyAjax.do'
        ,data: ""
        ,dataType: "json"
        ,cache: false
        ,async: false
    }, function(jsonObj) {
        simpleApplyObj = jsonObj;
    });

    var alertMessage = '';
    var selOperType = $('input[name=operTypeRadio]:checked').val();

    if(simpleApplyObj.IsMacTime){
        $('input[name=operTypeRadio][operName="신규가입"]').prop('disabled', false);
    }

    if(simpleApplyObj.IsMnpTime){
        $('input[name=operTypeRadio][operName="번호이동"]').prop('disabled', false);
    }

    if (!simpleApplyObj.IsMnpTime) {
        if (simpleApplyObj.IsMacTime) {
            alertMessage = '신규 가입만 가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.';
        } else {
            alertMessage = '셀프개통이 불가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.';
            $('.nextStepBtn').prop('disabled', true);  // 가입하기 버튼 비활성화
        }

        // 번호이동이 불가한 경우 '신규가입' 선택으로 변경
        if(selOperType == "MNP3" && $("#operTypeRadio2").length > 0){
            $("#operTypeRadio2").trigger("click");
        }
    }

    if(alertMessage){
        MCP.alert(alertMessage, function () {
            if(!isEnableOperType()) {
                $('.nextStepBtn').prop('disabled', true);  // 가입하기 버튼 비활성화
                window.close();
            }
        });
    }

}

/** 신규(유심)가입 제한 IP 확인 */
var selfIpChk = function(){

    var operType = $(":radio[name=operTypeRadio]:checked").val();

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

        if(!ipChkResult){
            // 번호이동이 가능한 경우, 번호이동 신청으로 진행
            // 번호이동이 불가한 경우, alert 후 창닫기
            var isMnpDisabled = $("#operTypeRadio1").length == 0 || $("#operTypeRadio1").prop("disabled");

            if(isMnpDisabled){
                MCP.alert("셀프개통 이용이 불가합니다.", function(){
                    $(".nextStepBtn").prop("disabled", true);
                    window.close();
                });
            }else{
                MCP.alert("신규가입 신청이 불가합니다.<br>번호이동 신청으로 진행합니다.", function(){
                    $("#operTypeRadio1").focus();
                    $("#operTypeRadio1").trigger("click");
                })
            }
        }
    });

    return ipChkResult;
}

/** 요금제 조회 */
var initSocCodeList = function(isInit){

    var agrmTrm = ajaxCommon.isNullNvl($("#enggMnthCnt").val(), "0");
    var modelMonthlyVal = ajaxCommon.isNullNvl($("#modelMonthly").val(), "0");
    var selSocCode =  $("#selSocCode").val() || $("#socCode").val();

    var varData = ajaxCommon.getSerializedData({
        salePlcyCd:   $("#modelSalePolicyCode").val()   // 정책
        ,prdtId:       $("#prdtId").val()                // 상품
        ,orgnId:       $("#cntpntShopId").val()          // 조직
        ,operType:     $('input[name=operTypeRadio]:checked').val()                // 가입유형
        ,rateCd:       $("#socLimitYn").val() == "Y" ? $("#socCode").val() :  ""   // 요금제
        ,onOffType:    $("#onOffType").val()   // 개통유형
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
    }, function(objList) {
        if (objList.length > 0) {

            // 초기화
            $("#selSocCode option").remove();

            for (var i = 0; i < objList.length; i++) {
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
                // 이벤트코드 초기화
                initEvntCdPrmt();
            }else{
                setSocCodeInfo();  // 요금제 화면 세팅
                getGiftList();     // 사은품 조회
            }

            if(isInit){
                selfTimeChk();     // 셀프개통 시간 체크
            }

        } else {
            MCP.alert("요금제 정보가 없습니다.",function (){
                $(".nextStepBtn").prop("disabled", true);  // 가입하기 버튼 비활성화
                window.close();
            });
        }
    });
}

/** 요금제 화면 세팅 */
var setSocCodeInfo = function(){

    var itemObj = $("#selSocCode").find("option:selected").data();

    $("#totalPrice2").html(numberWithCommas(itemObj.payMnthChargeAmt));
    $("#baseAmt").html(numberWithCommas(itemObj.baseVatAmt)+" 원");
    $("#dcAmt").html(numberMinusWithCommas(itemObj.dcVatAmt)+" 원");
    $("#addDcAmt").html(numberMinusWithCommas(itemObj.addDcVatAmt)+" 원");
    $("#promotionDcAmtTxt").html(numberMinusWithCommas(itemObj.promotionDcAmt)+" 원");

    var modelMonthlyVal = ajaxCommon.isNullNvl($("#modelMonthly").val(), "0");
    if ("0" == modelMonthlyVal) {
        $("#totalPrice").html(numberWithCommas(itemObj.payMnthChargeAmt));        // 월 납부금액
        $("#totalPriceBottom").html(numberWithCommas(itemObj.payMnthChargeAmt));  // 월 납부금액
    } else {
        $("#totalPrice").html(numberWithCommas(itemObj.payMnthAmt+itemObj.payMnthChargeAmt));       // 월 납부금액
        $("#totalPriceBottom").html(numberWithCommas(itemObj.payMnthAmt+itemObj.payMnthChargeAmt)); // 월 납부금액
    }

    var rateNm = itemObj.rateNm;
    rateNm = ajaxCommon.isNullNvl(rateNm, "-");

    var operNm = $('input[name=operTypeRadio]:checked').attr("operName"); // 가입유형
    var prdtNm = $('#prdtNm').val();  // 상품
    var dataType = ajaxCommon.isNullNvl($("input[name='dataType']:checked").val(), ''); // 서비스유형

    var bottomTitle = dataType + " / " + prdtNm + " / " + operNm + " / " + rateNm
    $("#bottomTitle").html(bottomTitle);
}

/** 서비스유형 화면 세팅 */
var initDataTypeInfo = function() {

    var rateNm = $("#selSocCode option:selected").attr("rateNm");         // 요금제
    rateNm = ajaxCommon.isNullNvl(rateNm, "-");

    var operNm = $('input[name=operTypeRadio]:checked').attr("operName"); // 가입유형
    var prdtNm = $('#prdtNm').val();  // 상품
    var dataType = ajaxCommon.isNullNvl($("input[name='dataType']:checked").val(), ''); // 서비스유형

    if(dataType == ''){
        $("input[name='dataType']").eq(0).prop('checked',true);
        dataType = $("input[name='dataType']:checked").val();
    }

    var bottomTitle = dataType + " / " + prdtNm + " / " + operNm + " / " + rateNm
    $("#bottomTitle").html(bottomTitle);
}

/** 가입유형 화면 세팅 */
var initOperTypeInfo = function(){

    var rateNm = $("#selSocCode option:selected").attr("rateNm");         // 요금제
    rateNm = ajaxCommon.isNullNvl(rateNm, "-");

    var operNm = $('input[name=operTypeRadio]:checked').attr("operName"); // 가입유형
    var prdtNm = $('#prdtNm').val();  // 상품
    var dataType = ajaxCommon.isNullNvl($("input[name='dataType']:checked").val(), ''); // 서비스유형

    var bottomTitle = dataType + " / " + prdtNm + " / " + operNm + " / " + rateNm
    $("#bottomTitle").html(bottomTitle);

    // 가입유형
    var operType = $("input:radio[name=operTypeRadio]:checked").val();
    if (operType == OPER_TYPE.MOVE_NUM) {
        $("#newTxt").hide();
        $("#movTxt").show();
    } else {
        $("#movTxt").hide();
        $("#newTxt").show();
    }
}

/** 사은품 프로모션 조회 */
var getGiftList = function(){

    var onOffType  = ajaxCommon.isNullNvl($('input[name=radOpenType]:checked').val(), '');    // 모집경로
    var operType   = ajaxCommon.isNullNvl($('input[name=operTypeRadio]:checked').val(), '');  // 가입유형
    var reqBuyType = 'UU'; // 구매유형
    var agrmTrm    = ajaxCommon.isNullNvl($("#enggMnthCnt").val() , '0'); // 약정기간
    var rateCd     = ajaxCommon.isNullNvl($('#selSocCode').val(), '');    // 요금제
    var orgnId     = ajaxCommon.isNullNvl($('#cntpntShopId').val(), '');  // 조직
    var modelId    = ajaxCommon.isNullNvl($('#prdtId').val(), '');        // 제품

    if(onOffType == '' || operType == '' || rateCd == '' || orgnId == '' || modelId == '') {
        return false;
    }

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
              $('.giftInfoListHr').hide();
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

          innerHtml += `<div class="u-mb--16" id="eventCdDiv" style="display: none;">\n`
          innerHtml += `<p>이벤트 코드 <span class="u-co-red">"<em id="eventCdName"></em>"</span>이/가 적용되었습니다.</p>\n`;
          innerHtml +=`<p class="u-fs-18 u-fw--bold" id="eventExposedText"></p>\n`;
          innerHtml += `</div>\n`

          if(0 < totalMainPrice){
              innerHtml += `<p class="summary-badge-list__title">최대 <span class="u-co-sub-4"><em>${totalMainPrice / 10000}</em>만원</span> 가입 혜택</p>\n`;
          }else{
              innerHtml += `<p class="summary-badge-list__title">최대 <span class="u-co-sub-4"><em>${totalMainCount}</em>개</span> 가입 혜택</p>\n`;
          }

          innerHtml += '<div class="c-accordion--summary sub-category">\n';
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
                  const popupUrlMo = item.popupUrlMo || '';
                  innerHtml += '                        <li class="summary-badge-list__item">\n';
                  innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                  innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                  innerHtml += '                          </div>\n';
                  if (popupUrlMo) {
                      innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
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
                  innerHtml += `                	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${totalWirePrice / 10000}</em>만원</p>\n`;
              } else {
                  innerHtml += `                	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${giftPrmtWireList.length}</em>개</p>\n`;
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
                  const popupUrlMo = item.popupUrlMo || '';
                  innerHtml += '                        <li class="summary-badge-list__item">\n';
                  innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                  innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                  innerHtml += '                          </div>\n';
                  if (popupUrlMo) {
                      innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
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
                  const popupUrlMo = item.popupUrlMo || '';
                  innerHtml += '                        <li class="summary-badge-list__item">\n';
                  innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                  innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                  innerHtml += '                          </div>\n';
                  if (popupUrlMo) {
                      innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
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

          innerHtml += '  </div>\n';
          innerHtml += '</div>\n';

          $divGift.html(innerHtml);
          $divGift.show();
          $('.giftInfoListHr').show();
          //$('.giftInfoTxt').show();
          MCP.init();
        });
}

/** 가입비 면제 여부 조회 */
var getJoinUsimPriceInfo = function() {

    var cntpntShopId = $("#cntpntShopId").val();
    var socCode = $("#socCode").val();  // 진입 요금제코드
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
            ,url:'/m/getCodeNmAjax.do'
            ,data: varData
            ,dataType:"json"
            ,cache:false
            ,async:false
        }
        ,function(jsonObj){

            if(jsonObj != null && jsonObj.expnsnStrVal1 == "Y"){
                $("#joinPriceIsPay").val("Y");  // 가입비 필수
            }else{
                $("#joinPriceIsPay").val("N");
            }

            getJoinUsimPrice();  // 가입비 조회
        });
}

/** 가입비 조회 */
var getJoinUsimPrice = function() {

    var operType = ajaxCommon.isNullNvl($('input[name=operTypeRadio]:checked').val(), ''); // 가입유형
    var dataType = ajaxCommon.isNullNvl($("input[name='dataType']:checked").val(), '');    // 서비스유형

    if (operType == '' || dataType == '' ) {
        return false;
    }

    var varData = ajaxCommon.getSerializedData({
        dataType : dataType
        ,operType : operType
        ,rateCd   : ''
    });

    ajaxCommon.getItem({
        id:'getUsimInfo'
        ,url:'/m/usim/selectUsimBasJoinPriceAjax.do'
        ,data: varData
        ,dataType:"json"
        ,cache:false
        ,async:false
    },function(jsonObj){
        if ('0001' ==  jsonObj.RESULT_CODE) {

            if(operType == OPER_TYPE.MOVE_NUM){ //번호이동 수수료
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

            showJoinUsimPrice();  // 가입비 화면 세팅
        }
    });
}

/** 가입비 화면 세팅 */
var showJoinUsimPrice = function() {

    var joinPriceIsPay = $("#joinPriceIsPay").val();
    var joinPrice = $('#joinPrice').val();

    if (joinPriceIsPay == "Y") {
        $("#joinPriceTxt").html(numberWithCommas(joinPrice)+" 원");
    } else {
        $("#joinPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(joinPrice)+" 원</span>(무료)");
    }
}

/** 가입하기 버튼 클릭 이벤트 */
var fnSelfClick = function (){

    // 서비스유형
    var dataType = $("input[name='dataType']:checked").val();
    if ("LTE" != dataType && "5G" != dataType) {
        MCP.alert("유심유형을 LTE , 5G 중에 선택 하시기 바랍니다.", function (){
            $("input[name=dataType]").eq(0).focus();
        });
        return;
    }

    // 가입유형
    var operType = $(":radio[name=operTypeRadio]:checked").val();
    if ("MNP3" != operType && "NAC3" != operType) {
        MCP.alert("가입유형을 번호이동 , 신규 중에 선택 하시기 바랍니다. ", function (){
            $('input[name=operTypeRadio]').eq(0).focus();
        });
        return;
    }

    // 요금제
    var socCode = $("#selSocCode").val();
    if(socCode== '' || socCode == undefined ){
        MCP.alert("요금제를 선택 하시기 바랍니다.", function() {
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

    // 셀프개통 신규(유심)가입 제한 IP
    if(!selfIpChk()){
        return;
    }

    $("#onOffType").val("7");
    $("#enggMnthCnt").val("0");

    // 선택가능한 본인인증 수단 조회
    var varData = ajaxCommon.getSerializedData({grpCd: "mSimpleAuth"});
    ajaxCommon.getItemNoLoading({
        id: 'getAuthCodeListAjax',
        url: '/getCodeListAjax.do',
        data: varData,
        dataType: "json",
        cache: false,
        async: false
    }, function(objList) {
        if (objList != null) {
            $("#authListDesc").html(objList.map(item => item.dtlCdNm).join(", "));
        }else{
            $("#authListDesc").html("선택 가능한 본인인증 방법이 없습니다");
        }

        $('#chkModalPop').trigger('click');
    });
}

/** 가입 전 확인 팝업 내 가입하기 버튼 클릭 이벤트 */
var confirmNextStep = function(){

    //임시저장 처리
    var varData = ajaxCommon.getSerializedData({
        preUrlType : '1'
        , operType : $('input[name=operTypeRadio]:checked').val()	// 가입유형
        , cntpntShopId : $('#cntpntShopId').val()                   // 접점코드
        , rateCd : $('#selSocCode').val()							// 요금제코드
        , prdtSctnCd : $('input[name=dataType]:checked').val()	    // 서비스유형
        , reqBuyType : 'UU'											// UU:유심단독구매
        , modelId : $('#prdtId').val()			                    // 모델코드
        , prodId : ''
        , modelMonthly : $('#modelMonthly').val()	                // 약정유형
        , serviceType : 'PO'										// PO : 후불
        , socCode : $('#selSocCode').val()							// 요금제코드
        , modelSalePolicyCode : $('#modelSalePolicyCode').val()		// 정책코드
        , onOffType : $('input[name=radOpenType]:checked').val()	// 개통유형
        , tmpStep : '0'												// 임시저장 단계
        , settlAmt : $('#totalPrice').text().replace(/,/gi, '')		// 임시 최종 결제 금액
        , sesplsYn : 'N'
        , sesplsProdId : ''
        , usimKindsCd : ''
        , uploadPhoneSrlNo : '0'
        , openMarketId : $("#openMarketId").val()
        , evntCdPrmt : $.trim($("#evntCdSeq").val())
        , reqModelName : $.trim($("#reqModelName").val())
        , sprtTp : $.trim($("#sprtTp").val())
    });

    ajaxCommon.getItem({
        id:'insertAppFormTempSave'
        ,url:'/m/appForm/insertAppFormTempSave.do'
        ,data: varData
        ,dataType:"json"
        ,cache:false
        ,async:false
    }, function(data){

        if (data.resultCd == '0000') {

            ajaxCommon.createForm({
                method: "post"
                ,action: '/m/appForm/appAgentForm.do'
            });

            ajaxCommon.attachHiddenElement("requestKey", data.requestTempKey);
            ajaxCommon.formSubmit();

        } else {
            MCP.alert(data.msg);
        }
    });
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