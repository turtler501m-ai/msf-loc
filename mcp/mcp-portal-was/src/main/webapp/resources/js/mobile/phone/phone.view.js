;



var pageObj = {
    colorData:[]
    , initPhoneData:[]
    , selSntyColorCd:""
    , frstRadCd:""
    , settlYn:"N"
    , settlMnpYn:"N"
    , agrmTrmList:["12","24","30","36"] //단말기 할부 가능 한것들...
    , asAgrmTrmVal:""
    , initBestRate:"0"
    , badgeList:[]
    , tabButIndex:"0"
    , orderEnum:"RECOMM_ROW"
    , chargelist:[]
    , ktTripleDcAmt:0
    , ktTripleDcRateCd:""
    , bestRateArr:[]
    , recommendRate :"" //추전 요금제
    , recommendRateNoargm :"" // 무약정 추전 요금제
    , selRateCtg:"X"
    , usimPriceBase:""
    , usimPrice5G:""
    , instAmt :0
    , chooseRateInfo :{}   //선택한 요금제
    , cprtDcAmt :0 // 카드 할인 금액
    , totalVatPrice:0
    , reviewNo: 0
    , reviewPageNo :1
    , sdoutYn:"N"
}




$(document).ready(function() {


    var initColorData = function(){
        var varData = ajaxCommon.getSerializedData({
            grpCd : 'GD0008'
        });
        ajaxCommon.getItemNoLoading({
            id:'getComCodeListAjax'
            ,cache:false
            ,url:'/m/getComCodeListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(data){
            pageObj.colorData = data.result;
        });
    }();


    //DOM EVENT START ##################

    $('button[id^=tabMain]').on("click", function(){
        var tabId = $(this).attr('id');
        if ("tabMain3" == tabId && "" == $("#reviewBoard").html()) {
            getReViewData(pageObj.reviewPageNo);
        }
    });

    $(document).on("change", "input:radio[name='radGA']", function(){
        pageObj.selSntyColorCd = $(this).val();
        findHndsetModelId($(this).val(),$("#atribValCd2").val());
        getPhoneData(false);
        $("#colorNm").text($(this).attr("atribValNm1"));
    });

    // 더보기
    $("#moreBtn").click(function(){
        var reViewCurrent = Number($(".review_fold").length);
        var reViewTotal = Number($("#reViewTotal").text());
        if(reViewCurrent >= reViewTotal){
            alert("마지막 페이지 입니다.");
            return false;
        } else {
            pageObj.reviewPageNo++;
            getReViewData(pageObj.reviewPageNo);
            $(window).scrollTop($("#moreBtn").offset().top-800);
        }
    });


    $('input[name=operType]').click(function(){
        var thisVal = $(this).val();
        if(thisVal == 'NAC3' && pageObj.settlYn =="Y"){
            MCP.alert('신규가입 시 단말기 구매는 즉시결제만<br/> 가능 합니다.');
        }

        setAgrmTrm();
        if (!isEmpty(pageObj.chooseRateInfo) ) {
            getChargelist();
        } else {
            initView();
            nextStepBtnChk();
        }
    });

    $("input[name=dataType]").on('change',function() {
        if (!isEmpty(pageObj.chooseRateInfo) ) {
            getChargelist();
        } else {
            initView();
        }
    });


    $('input[name=instNom]').on('change', function (){
        var thisVal = $(this).val();

        if(thisVal == '0'){
            //할인 유형.. disabled
            $('input[name=saleTy]').removeProp('checked');
            $('input[name=saleTy]').attr('disabled', 'disabled');
        }else{
            $('input[name=saleTy]').removeAttr('disabled');
            $('#chkDiscountType1').prop('checked', 'checked');
        }
        setAgrmTrm();

        if (!isEmpty(pageObj.chooseRateInfo) ) {
            getChargelist();
        } else {
            initView();
        }
    });


    $(document).on('change','[name=agrmTrm]',function() {
        pageObj.asAgrmTrmVal=$(this).val() ;

        if (!isEmpty(pageObj.chooseRateInfo) ) {
            getChargelist();
        }
    });


    $('input[name=saleTy]').on('change', function (){
        if (!isEmpty(pageObj.chooseRateInfo) ) {
            getChargelist();
        } else {
            initView();
        }
    });

    $('._paymentPop').on('click', function (){
        if($('input[name=operType]:checked').val() == undefined || $('input[name=operType]:checked').val() == ''){
            MCP.alert('가입 유형을 선택해주세요.', function (){
                $('#operType0').focus();
            });
            return false;
        }

        $('#paymentSelectBtn').addClass('is-disabled');
        //버튼 초기화
        $('._rateTabM1').removeClass('is-active');

        //var bestRate = $('#rdoBestRate').val();
        var bestRate = pageObj.initBestRate;
        $('#rdoBestRate').val(pageObj.initBestRate);
        if (bestRate == "1") {
            $('._rateTabM1').eq(0).addClass('is-active');
            pageObj.tabButIndex = "1";
        } else {
            $('._rateTabM1').eq(1).addClass('is-active');
            pageObj.tabButIndex = "0";
        }

        pageObj.badgeList =[];//추천 요금제 초기화
        pageObj.orderEnum="RECOMM_ROW";
        $(".btn-sort").removeClass('is-active');
        $(".btn-sort").removeClass('sort');

        //요금제 그룹 버튼 표현 여부 처리
        //- 유심 , 단말기(무약정)    공통코드 1번 표현
        //- 단말기(약정)            공통고트 2번 표현
        // 단말기
        var instNom = $('input[name="instNom"]:checked').val();
        if (instNom == "0") {
            viewRateCtgGroup(1);
        } else {
            viewRateCtgGroup(2);
        }

        //선택한 요금제 초기화
        pageObj.chooseRateInfo = {};

        //요금제 레이어 팝어 오픈
        var el = document.querySelector('#paymentSelect');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

        //요금제 리스트 레이어 표현
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();

        //요금제 그룹 확대 버튼 보기 초기화
        $('.btn-category__expand').addClass('is-active');
        $('.btn-category__list').removeClass('short');
        $('.btn-category__list').removeClass('is-active');
        $('#btnCateroryView').attr('aria-expanded','false');

        //요금제 그룹 확대 버튼 보기 표현 여부
        setTimeout(function() {
            var Width1 = $('.btn-category__inner').width();
            var Width2 = $('.btn-category__group').width();

            if (Width1 > Width2) {
                $('.btn-category__expand').removeClass('is-active')
                $('.btn-category__list').addClass('short')
            }
        },100);

        getChargelist();

    });


    //전체 , 추천 클릭
    $('._rateTabM1').on('click', function (){
        $('._rateTabM1').removeClass('is-active');
        $(this).addClass('is-active');
        $('#searchNm').val('');

        let bestRate =$(this).data("bestRate");
        pageObj.pageNo = 1;
        pageObj.tabButIndex = bestRate ;
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();

        //charegListHtml();
        if (bestRate == "0") {
            //추천요금제 true
            $('.rate-content__item').hide();
            $('.rate-content__item[data-is-rate-best="true"]').show();
            $('#rdoBestRate').val(bestRate);
            $(".c-card__badge-label").removeClass("compare");
            if ($('#rateContentCategory > .rate-content__item:visible').length <1) {
                $("#ulIsEmpty").show();
                $('#ulIsEmpty > .rate-content__item').show();
                $("#rateContentCategory").hide();
            }
        } else if (bestRate == "1") {
            //전체요금제
            $('.rate-content__item').show();
            $('#rdoBestRate').val(bestRate);
            $(".c-card__badge-label").removeClass("compare");
            if ($('#rateContentCategory > .rate-content__item:visible').length <1) {
                $("#ulIsEmpty").show();
                $('#ulIsEmpty > .rate-content__item').show();
                $("#rateContentCategory").hide();
            }

        } else if (bestRate == "-2") {
            var selRateCtg = $(this).data("rateCtg");
            pageObj.selRateCtg = selRateCtg ;
            $(".c-card__badge-label").removeClass("compare");
            $('#rateContentCategory > .rate-content__item').each(function (){
                var gropRateCtg = $(this).find(".c-checkbox--box").data("expnsnStrVal1");
                if(isEmpty(gropRateCtg)){
                    gropRateCtg = "";
                }

                if (gropRateCtg.indexOf(selRateCtg) > -1) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            })

            if ($('#rateContentCategory > .rate-content__item:visible').length <1) {
                $("#ulIsEmpty").show();
                $('#ulIsEmpty > .rate-content__item').show();
                $("#rateContentCategory").hide();
            }
        } else {
            //비교하기
            $checkBadge = $("input:checkbox[id^=chkBadgeAA_]:checked");
            var checkCnt = $checkBadge.length ;
            $(".c-card__badge-label").addClass("compare");
            if (checkCnt > 0) {
                $('.rate-content__item').each(function (){
                    var isBadge ;
                    isBadge = $(this).data("isBadge");
                    if (isBadge=="ture") {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                })
            } else {
                MCP.alert('요금제 선택 영역에서 비교하고 싶은<br> 요금제를 담아주세요.');
                var bestRate2 = $('#rdoBestRate').val();
                if (bestRate2 == "1") {
                    $('._rateTabM1').eq(0).trigger('click');
                } else {
                    $('._rateTabM1').eq(1).trigger('click');
                }
            }
        }
    });

    $('#evntCdPrmt').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            e.preventDefault();
            $('#evntCdPrmtBtn').click();
        }
    });

    $('#searchNm').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            e.preventDefault();
            $('#searchRatePlan').click();
        }
    });

    $('#searchClear').on('click', function (){
        $('#searchNm').val('');
        $('#searchRatePlan').click();
    });

    $('#searchRatePlan').on('click', function(){
        let keyword = $('#searchNm').val().trim().replace(/\s+/g, '').toLowerCase();

        // 무조건 전체 탭으로 전환
        $('._rateTabM1').removeClass('is-active');
        $('._rateTabM1[data-best-rate="1"]').addClass('is-active');

        // 기타 UI 초기화
        $('#filterAllYn').val('N');
        pageObj.pageNo = 1;
        pageObj.tabButIndex = 1;
        $(".section-survey").hide();
        $("._survey__content2").hide();
        $('._question').hide();
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();
        $(".btn-sort-wrap").show();
        $("#rateBtnMyrate").removeClass('is-active');

        // 전체 항목 보이기 + 필터링
        let matchCount = 0;

        $('#rateContentCategory > .rate-content__item').each(function () {
            let rateName = $(this).find('.rate-info__title strong').text().trim().replace(/\s+/g, '').toLowerCase();

            if (rateName.indexOf(keyword) !== -1) {
                $(this).show();
                matchCount++;
            } else {
                $(this).hide();
            }
        });

        // 결과 없을 경우
        if (matchCount === 0) {
            $('#ulIsEmpty').show();
            $('#rateContentCategory').hide();
        } else {
            $('#ulIsEmpty').hide();
            $('#rateContentCategory').show();
        }
    });

    $('#btnCateroryView').click(function(){
        $(this).parent().toggleClass('is-active')
        var hasClass = $('.btn-category__list').hasClass('is-active');
        if(hasClass){
            $(this).attr('aria-expanded','true');
        }else {
            $(this).attr('aria-expanded','false');
        }
    });

    //비교함 담기
    $(document).on('click', 'input:checkbox[id^=chkBadgeAA_]', function () {
        var checkCnt = $("input:checkbox[id^=chkBadgeAA_]:checked").length ;
        if (checkCnt > 20) {
            MCP.alert('비교함에는 최대 20개의 요금제만 담을 수 있습니다.');
            $(this).removeProp('checked');
        } else {

            //$('#ckBox').is(':checked');
            if ($(this).is(':checked')) {
                $(this).parent().parent().data("is-badge","ture");
                //console.dir($(this).parent().parent().data());
            } else {
                checkCnt = checkCnt -1 ;
                $(this).parent().parent().data("is-badge","false");
                if(pageObj.tabButIndex == "-1") {  //비교함
                    $(this).parent().parent().hide();
                    if($("input:checkbox[id^=chkBadgeAA_]:checked").length < 1) {
                        var bestRate = $('#rdoBestRate').val();
                        if (bestRate == "1") {
                            $('._rateTabM1').eq(0).trigger('click');
                        } else {
                            $('._rateTabM1').eq(1).trigger('click');
                        }

                    }
                }
            }

        }
        if (checkCnt < 0) {
            checkCnt = 0;
        }

        pageObj.badgeList =[];
        $("input:checkbox[id^=chkBadgeAA_]:checked").each(function (){
            var thisValue = $(this).val()+"_" + $(this).data("rateAdsvcCd");
            pageObj.badgeList.push(thisValue);
        })
        $("#rateBtnCompar .rate-btn__cnt").html($("input:checkbox[id^=chkBadgeAA_]:checked").length);
    });



    $('.btn-sort').click(function(){
        $(this).siblings().removeClass('is-active');
        $(this).siblings().removeClass('sort');
        $(this).toggleClass('is-active');
        $(this).addClass('sort');

        var orderValue =  $(this).data("orderValue");

        if ($(this).hasClass("is-active")) {
            orderValue += "_HIGH";
        } else {
            orderValue += "_ROW";
        }

        pageObj.orderEnum = orderValue;

        if ("CHARGE_ROW" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                var c1 = a.payMnthChargeAmt + a.payMnthAmt ;
                var c2 = b.payMnthChargeAmt + b.payMnthAmt ;

                if (c1 > c2) return 1;
                else if (c2  > c1)return -1;
                else return 0;
            });
        } else if("CHARGE_HIGH" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                var c1 = a.payMnthChargeAmt + a.payMnthAmt ;
                var c2 = b.payMnthChargeAmt + b.payMnthAmt ;

                if (c1 > c2) return -1;
                else if (c2  > c1) return 1;
                else return 0;
            });
        } else if("XML_DATA_ROW" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.xmlDataCnt ;
                var c2 = b.mspRateMstDto.xmlDataCnt ;
                if (c1 == undefined || c1 == "") { c1 = "0";    }
                if (c2 == undefined || c2 == "") { c2 = "0";    }
                var intC1 = 0 ;
                var intC2 = 0;

                if (!isNaN(c1)) {
                    intC1 =parseInt(c1);
                }

                if (!isNaN(c2)) {
                    intC2 = parseInt(c2);
                }
                if (intC1   > intC2) return 1;
                else if (intC2 > intC1) return -1;
                else {
                    var c3 = a.mspRateMstDto.xmlQosCnt ;
                    var c4 = b.mspRateMstDto.xmlQosCnt ;
                    if (c3 == undefined || c3 == "") { c3 = "0";    }
                    if (c4 == undefined || c4 == "") { c4 = "0";    }
                    var intC3 = 0 ;
                    var intC4 = 0;
                    if (!isNaN(c3)) {
                        intC3 =parseInt(c3);
                    }
                    if (!isNaN(c4)) {
                        intC4 = parseInt(c4);
                    }
                    if (intC3   > intC4) return 1;
                    else if (intC4 > intC3) return -1;
                    else return 0;
                }
            });
        } else if("XML_DATA_HIGH" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.xmlDataCnt ;
                var c2 = b.mspRateMstDto.xmlDataCnt ;
                if (c1 == undefined || c1 == "") { c1 = "0";    }
                if (c2 == undefined || c2 == "") { c2 = "0";    }
                var intC1 = 0 ;
                var intC2 = 0;

                if (!isNaN(c1)) {
                    intC1 =parseInt(c1);
                }

                if (!isNaN(c2)) {
                    intC2 = parseInt(c2);
                }
                if (intC1   > intC2) return -1;
                else if (intC2 > intC1) return 1;
                else {
                    var c3 = a.mspRateMstDto.xmlQosCnt ;
                    var c4 = b.mspRateMstDto.xmlQosCnt ;
                    if (c3 == undefined || c3 == "") { c3 = "0";    }
                    if (c4 == undefined || c4 == "") { c4 = "0";    }
                    var intC3 = 0 ;
                    var intC4 = 0;
                    if (!isNaN(c3)) {
                        intC3 =parseInt(c3);
                    }
                    if (!isNaN(c4)) {
                        intC4 = parseInt(c4);
                    }
                    if (intC3   > intC4) return -1;
                    else if (intC4 > intC3) return 1;
                    else return 0;
                }
            });
        } else if("VOICE_ROW" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.freeCallCnt ;
                var c2 = b.mspRateMstDto.freeCallCnt ;
                var intC1 = 0 ;
                var intC2 = 0;

                if (isNaN(c1)) {
                    if (c1.indexOf("기본") > -1 || c1.indexOf("무제한") > -1 ) {
                        intC1 =  999999;
                    } else {
                        intC1 =  0;
                    }
                } else {
                    intC1 =parseInt(c1);
                }

                if (isNaN(c2)) {
                    if (c2.indexOf("기본") > -1 || c2.indexOf("무제한") > -1 ) {
                        intC2 =  999999;
                    } else {
                        intC2 =  0;
                    }
                } else {
                    intC2 = parseInt(c2);
                }

                if (intC1   > intC2) return 1;
                else if (intC2 > intC1) return -1;
                else return 0;
            });
        } else if("VOICE_HIGH" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.freeCallCnt ;
                var c2 = b.mspRateMstDto.freeCallCnt ;
                var intC1 = 0 ;
                var intC2 = 0 ;

                if (isNaN(c1)) {
                    if (c1.indexOf("기본") > -1 || c1.indexOf("무제한") > -1 ) {
                        intC1 =  999999;
                    } else {
                        intC1 =  0;
                    }
                } else {
                    intC1 =parseInt(c1);
                }

                if (isNaN(c2)) {
                    if (c2.indexOf("기본") > -1 || c2.indexOf("무제한") > -1 ) {
                        intC2 =  999999;
                    } else {
                        intC2 =  0;
                    }
                }else {
                    intC2 = parseInt(c2);
                }

                if (intC1   > intC2) return -1;
                else if (intC2 > intC1) return 1;
                else return 0;
            });
        }  else if("CHARGE_NM_ROW" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                if (a.rateNm   > b.rateNm) return 1;
                else if (b.rateNm > a.rateNm) return -1;
                else return 0;
            });
        } else if("CHARGE_NM_HIGH" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                if (a.rateNm   > b.rateNm) return -1;
                else if (b.rateNm > a.rateNm) return 1;
                else return 0;
            });
        } else if("XML_CHARGE_ROW_ROW" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                if (a.xmlPayMnthAmt   > b.xmlPayMnthAmt) return 1;
                else if (b.xmlPayMnthAmt > a.xmlPayMnthAmt) return -1;
                else return 0;
            });
        } else if("XML_CHARGE_ROW_HIGH" == orderValue) {
            pageObj.chargelist.sort(function (a, b) {
                if (a.xmlPayMnthAmt   > b.xmlPayMnthAmt) return -1;
                else if (b.xmlPayMnthAmt > a.xmlPayMnthAmt) return 1;
                else return 0;
            });
        }

        charegListView();
    });


    $(document).on('click','._rateDetailPop',function() {
        var rateAdsvcGdncSeq = $(this).attr("rateAdsvcGdncSeq");
        var rateAdsvcCd = $(this).attr("rateAdsvcCd");

        if (rateAdsvcGdncSeq == "0") {
            MCP.alert("해당 요금제의 상세정보는 준비중입니다.");
        } else {
            openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'&btnDisplayYn=N','');
        }
    });

    $(document).on('click','[name=selectRate]',function() {
        var thisData = $(this).data();
        //console.dir(thisData);
        $('#paymentSelectBtn').removeClass('is-disabled');
    });

    $('#paymentSelectBtn').on('click', function (){
        if($('input:radio[name=selectRate]:checked').length == 0){
            MCP.alert('요금제를 선택해 주세요.');
            return;
        }else{
            setRateInfo($('input[name=selectRate]:checked'));
        }
    });

    $("input:radio[name=ktTripleDcAmt]").change(function(){
        selectUsimBasJoinPriceAjax(false);
    });


    $('#chkCardSelect').change(function(){
        var $thisOption= $("#chkCardSelect option:selected")
        const thisVal = $thisOption.val();
        var arr =[];
        pageObj.cprtDcAmt = 0;

        if (thisVal != "") {
            var cardImg = $thisOption.data("cardimg");
            var dcFormlCd =  $thisOption.data("dcFormlCd");
            var dcAmt =  $thisOption.data("dcAmt");
            var dcLimitAmt =  $thisOption.data("dcLimitAmt");
            if(dcFormlCd == 'PCT'){
                if (pageObj.totalVatPrice >  0) {
                    pageObj.cprtDcAmt = pageObj.totalVatPrice * (Number(dcAmt) / 100);
                    if(pageObj.cprtDcAmt > Number(dcLimitAmt)){
                        pageObj.cprtDcAmt = Number(dcLimitAmt);
                    }
                    pageObj.cprtDcAmt = Math.floor(pageObj.cprtDcAmt);
                } else {
                    MCP.alert("요금제를 선택하시기 바랍니다.");
                }
            }else if( dcFormlCd == 'WON'){
                pageObj.cprtDcAmt = Number(dcAmt);
            }

            if ($('#serverNm').length > 0 && "LOCAL" ==  $('#serverNm').val()) {
                cardImg = "https://www.ktmmobile.com" + cardImg;
            }

            arr.push('    <img src="'+cardImg+'" alt="제휴카드 이미지">\n');
            arr.push('    <div class="c-form__label-wrap">\n');
            arr.push('        <p class="c-form__label-title">'+$thisOption.data("cardname")+'</p>\n');
            if ("" != $thisOption.data("carddesc1") ) {
                arr.push('        <p class="c-form__label-text">'+$thisOption.data("carddesc1")+'</p>\n');
            }
            if ("" != $thisOption.data("carddesc2") ) {
                arr.push('        <p class="c-form__label-text">'+$thisOption.data("carddesc2")+'</p>\n');
            }
            arr.push('    </div>\n');
            $("#chkCardSelectLable").html(arr.join(''));

            //2026.03 제휴카드 선택 시 안내사항 팝업
            $("#simpleDialog ._detail").html("※ 꼭 확인해주세요.</br></br>" +
                "      ① 제휴카드 발급은 휴대폰 개통 후 각 제휴카드 발급 사이트를 통해 별도로 신청 하셔야 합니다.</br></br>" +
                "      ② 카드 수령 후  납부방법은 자동으로 등록되지 않습니다.</br>" +
                "      카드 발급 후 마이페이지 > 요금조회 및 납부 > 납부방법 변경에서 변경 해주셔야만 할인 적용이 가능합니다.");
            $("._simpleDialogButton").hide();
            $("#cautionFlag").prop("checked", false);
            $("#divCheckCaution").show();
            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();

        } else {
            arr.push('    <img src="/resources/images/portal/common/ico_card_no.svg" alt="제휴카드 이미지">\n');
            arr.push('    <div class="c-form__label-wrap">\n');
            arr.push('        <p class="c-form__label-title">제휴카드 할인 선택</p>\n');
            arr.push('    </div>\n');
            $("#chkCardSelectLable").html(arr.join(''));
        }

        $('#cprtCardPrice').html(numberMinusWithCommas(pageObj.cprtDcAmt) + ' 원');
        if (!isEmpty(pageObj.chooseRateInfo) ) {
            selectUsimBasJoinPriceAjax(false);
        }

        var chkCardHeight = $("#chkCardSelectLable .c-form__label-wrap").height();
        $("#chkCardSelect").parent().height(chkCardHeight + 40);
    });

    //2026.03 제휴카드 안내사항 확인
    $("#cautionCheck").click(function() {
        if(!$('#cautionFlag').is(':checked')){
            MCP.alert("유의사항 이해여부 확인 바랍니다.");
            return false;
        }else {
            //KTM.Dialog.closeAll();
            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.close();
        }
    });

    $('#btnGift').click(function(){

            //var getGiftList = function (){
            var onOffType = "3";
            var operType = (!$('input[name=operType]:checked').val()) ? '' : $('input[name=operType]:checked').val();
            var agrmTrm = $('input[name=instNom]:checked').val();
            var rateCd = (!$('#socCode').val()) ? '' : $('#socCode').val();
            var orgnId = (!$('#initOrgnId').val()) ? '1100011741' : $('#initOrgnId').val();
            var reqBuyType = 'MM';

            var modelId = $("#hndsetModelId").val();
            var eventExposedText =  $('#eventExposedText').val();
            var eventCdName = $('#eventCdName').val();

            if (rateCd == '' ) {
                MCP.alert("요금제를 선택하시기 바랍니다.");
                return false;
            }

            var varData = ajaxCommon.getSerializedData({
                onOffType : onOffType
                , operType : operType
                , reqBuyType : reqBuyType
                , agrmTrm : agrmTrm
                , rateCd : rateCd
                , orgnId : orgnId
                , modelId : modelId
            });


            if(onOffType == '' || operType == '' || reqBuyType == '' || agrmTrm == '' || rateCd == '' || modelId == '') { return false; }

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
                        MCP.alert("가입 사은품 정보가 없습니다.");
                        return;
                    }

                    // 요금제 혜택요약 존재
                    var innerHtml = "";
                    var totalMainPrice = jsonObj.totalMainPrice;
                    var totalMainCount = jsonObj.totalMainCount;
                    var fallbackUrl = "/resources/images/portal/content/img_phone_noimage.png";

                    innerHtml += "<p class='gift-tit__sub'>선택 사은품이 있는 경우 신청 마지막 단계에서 선택 가능</p>\n";
                    innerHtml += "<hr class='gift-hr'>\n";

                    if(eventCdName != ''){
                        innerHtml += `<div class="u-mb--16" id="eventCdDiv">\n`
                        innerHtml += `<p>이벤트 코드 <span class="u-co-red">"${eventCdName}"</span>이/가 적용되었습니다.</p>\n`;
                        innerHtml +=`<p class="u-fs-18 u-fw--bold">${eventExposedText}</p>\n`;
                        innerHtml += `</div>\n`
                    }
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
                    MCP.init();

                    //사은품 레이어 팝어 오픈
                    var el = document.querySelector('#giftSelect');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();

                });
    });

    $("._btnJoinPage").click( function(){

        if ("Y" == pageObj.sdoutYn ) {
            MCP.alert('선택하신 용량/색상은 품절 되었습니다.');
            return false;
        }
        if($('input[name=operType]:checked').val() == undefined){
            MCP.alert('가입 유형을 선택해 주세요.', function (){
                $('input[name=operType]').eq(0).focus();
            });
            return false;
        }



        if ($('#socCode').val() == '' || $('#socCode').val() == undefined || !$('#radPayTypeB').is(':visible')){
            MCP.alert("요금제를 선택 하시기 바랍니다.", function (){
                $('#radPayTypeBtn').focus();
            });
            return ;
        }

        if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
            MCP.alert("입력하신 이벤트 코드의 사용가능 여부를 확인 바랍니다.", function(){
                $("#evntCdPrmt").focus();
            });
            return;
        }

        /*
        if($('input[name=operType]:checked').val() == 'NAC3' && pageObj.settlYn =="Y"   ){
            //즉시결제... 체크 하기 않음
        } else {
            if($('input[name=instNom]:checked').val() != '0' && $('input[name=instNom]:checked').val() != $('#agrmTrm').val() && $('#agrmTrm').val() != '0'){
                MCP.alert('약정기간과 할부기간이 일치하지 않습니다.');
                return ;
            }
        }*/

        $("#authListDesc").html("신용카드, 네이버 인증서, 카카오 본인인증, PASS 인증서, toss 인증서, 범용인증서");


        var el = document.querySelector('#signup-dialog');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

    });

    //DOM EVENT END ##################

    //요금제 표현 START################
    var getChargelist = function(isInit) {
        pageObj.pageNo = 1;

        var orderEnum = pageObj.orderEnum ;
        var varData = ajaxCommon.getSerializedData({
            prodId:$('#fprodId').val()
            ,operType:$(':radio[name="operType"]:checked').val()
            ,instNom:$('input[name="instNom"]:checked').val()
            ,modelMonthly : $('#agrmTrm').val()
            ,hndsetModelId:$("#hndsetModelId").val()
            ,orderEnum:orderEnum
            ,onOffType:'3' //모바일
            ,noAgrmYn : 'N'
            ,plcySctnCd : FOR_MSP.PHONE
        });

        ajaxCommon.getItem(
            {
                id:'getChargeList'
                ,cache:false
                ,url:'/m/product/phone/phoneViewAjax.do'
                ,data: varData
                ,dataType:'json'
                ,async:false
            }
            , function(jsonObj) {
                pageObj.chargelist = jsonObj.chargeXmlList;
                charegListHtml();
                optionCharge();
            });

        $("#rateBtnCompar .rate-btn__cnt").html("0");
    };


    var charegListHtml = function () {
        var rateList = pageObj.chargelist;
        initView();

        var cardCnt = 0;
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();
        $('#rateContentCategory').empty();

        if(rateList != null && rateList != '' && rateList.length > 0) {
            var dataType = $(':radio[name="dataType"]:checked').val();
            //추천요금제 적용
            var instNom = $('input[name="instNom"]:checked').val();
            if (instNom != "0") {
                pageObj.bestRateArr = pageObj.recommendRate.split(',');
            } else {
                //무약정 추천 요금제
                pageObj.bestRateArr = pageObj.recommendRateNoargm.split(',');
            }

            for (var i = 0; i < rateList.length; i++) {

                var isRateArr = false ;
                //추천요금제 적용
                var rateCdSp =rateList[i].rateCd + "#"+ rateList[i].sprtTp;
                if(pageObj.bestRateArr && pageObj.bestRateArr.length > 0){
                    for(var j=0; j<pageObj.bestRateArr.length; j++){
                        if(pageObj.bestRateArr[j] == rateCdSp) {
                            isRateArr = true ;
                            break;
                        }
                    }
                }
                var saleTyYn = false;
                if($('#chkDiscountType1:checked').val() == rateList[i].sprtTp){
                    saleTyYn = true;
                }
                if($('#chkDiscountType2:checked').val() == rateList[i].sprtTp){
                    saleTyYn = true;
                }

                if(($('#chkDiscountType1').is(':checked') || $('#chkDiscountType2').is(':checked')) && !saleTyYn){
                    continue;
                }

                if(  rateList[i].agrmTrm != $('input[name=instNom]:checked').val()){
                    continue;
                }

                //단말이면서 정책이 LTE5G(45)일때 dataType에 따른 요금제 노출
                if ( $('#prdtSctnCd').val() == '45') {
                    if (rateList[i].mspRateMstDto.dataType != dataType) {
                        continue;
                    }
                }

                $('#rateContentCategory').append(getRowTemplate(rateList[i],cardCnt,isRateArr) );
                $('#chkBoxAA_'+cardCnt).data(rateList[i]);
                cardCnt++;
            }
            $('#cardTotalCnt').val(cardCnt);
        }

        if ($('#rateContentCategory > .rate-content__item:visible').length <1) {
            $("#ulIsEmpty").show();
            $('#ulIsEmpty > .rate-content__item').show();
            $("#rateContentCategory").hide();
        }

        if (!isEmpty(pageObj.chooseRateInfo) ) {
            //console.dir(pageObj.chooseRateInfo);
            //console.dir("pageObj.chooseRateInfo.rateCd==>" + pageObj.chooseRateInfo.rateCd);
            var choseRateCd = pageObj.chooseRateInfo.rateCd;

            if ($("input[name=selectRate][value="+choseRateCd+"]").length > 0) {
                $("input[name=selectRate][value="+choseRateCd+"]").each(function(){
                    $(this).trigger('click');
                    $('#paymentSelectBtn').trigger('click');
                })
            } else {
                pageObj.chooseRateInfo = {} ;// 초기화
            }
        }
        nextStepBtnChk();
    }

    var charegListView = function (isInit) {
        var rateList = pageObj.chargelist;
        var cardCnt = 0;

        if(rateList != null && rateList != '' && rateList.length > 0) {
            var dataType = $(':radio[name="dataType"]:checked').val();
            var filterShow = false;
            var rdoBestRate = $("#rdoBestRate").val();
            var innerHTML = '';
            var filterCnt = 0;
            $('#rateContentCategory').empty();

            for (var i = 0; i < rateList.length; i++) {
                var isRateArr = false ;
                //추천요금제 적용
                var rateCdSp =rateList[i].rateCd + "#"+ rateList[i].sprtTp;
                if (pageObj.bestRateArr && pageObj.bestRateArr.length > 0) {
                    for(var j=0; j< pageObj.bestRateArr.length; j++){
                        if(pageObj.bestRateArr[j] == rateCdSp) {
                            isRateArr = true ;
                            break;
                        }
                    }
                }

                var saleTyYn = false;
                if($('#chkDiscountType1:checked').val() == rateList[i].sprtTp){
                    saleTyYn = true;
                }

                if($('#chkDiscountType2:checked').val() == rateList[i].sprtTp){
                    saleTyYn = true;
                }

                if(($('#chkDiscountType1').is(':checked') || $('#chkDiscountType2').is(':checked')) && !saleTyYn){
                    continue;
                }

                if( rateList[i].agrmTrm != $('input[name=instNom]:checked').val()){
                    continue;
                }

                //단말이면서 정책이 LTE5G(45)일때 dataType에 따른 요금제 노출
                if ( $('#prdtSctnCd').val() == '45') {
                    if (rateList[i].mspRateMstDto.dataType != dataType) {
                        continue;
                    }
                }

                $('#rateContentCategory').append(getRowTemplate(rateList[i],cardCnt,isRateArr) );
                $('#chkBoxAA_'+cardCnt).data(rateList[i]);
                cardCnt++;
            }
        }
    }

    var getRowTemplate = function(rateObj ,cardCnt ,isRateArr){
        var arr =[];
        var rdoBestRate = $("#rdoBestRate").val();
        let tempStyle= "";
        let isBadge = "false";
        let chkStr = rateObj.rateAdsvcGdncSeq + "_" + rateObj.rateCd ;  //rateObj.rateAdsvcGdncSeq 값이 없는 것이 존재함 오류 발생

        if (pageObj.tabButIndex == "-1" ) {

            if (pageObj.badgeList.includes(chkStr)) {
                tempStyle= "";
            } else {
                tempStyle= "display:none;"
            }
        } else if(rdoBestRate == "0" && !isRateArr) {
            // 추천 요금제 View
            tempStyle= "display:none;"
        } if (pageObj.tabButIndex == "-2" ) {
            var gropRateCtg = rateObj.expnsnStrVal1;
            if(isEmpty(gropRateCtg)){
                gropRateCtg = "";
            }

            if (gropRateCtg.indexOf(pageObj.selRateCtg) > -1) {
                tempStyle= "";
            } else {
                tempStyle= "display:none;"
            }
        }

        if (pageObj.badgeList.includes(chkStr)) {
            isBadge = "ture";
        }

        arr.push('<li class="rate-content__item" style="'+tempStyle+'" data-is-rate-best="'+isRateArr+'" data-is-badge="'+isBadge+'"  >\n');
        arr.push('    <input class="c-checkbox c-checkbox--box" id="chkBoxAA_'+cardCnt+'" type="radio" name="selectRate" value="'+rateObj.rateCd +'">\n');
        arr.push('        <label class="c-card__box-label" for="chkBoxAA_'+cardCnt+'"></label>\n');
        arr.push('        <div class="rate-info__wrap">\n');
        arr.push('            <!--요금제 타이틀 영역 -->\n');
        arr.push('            <div class="rate-info__title">\n');
        arr.push('                <div class="rate-sticker-wrap">\n');
        if (isRateArr) {
            arr.push('                <span class="c-text-label c-text-label--mint-type1">추천</span>\n');
        }
        if ( rateObj.sprtTp == 'KD'){
            arr.push('                 <span class="c-text-label c-text-label--blue-type1">단말할인</span>\n');
        } else if( rateObj.sprtTp == 'PM'){
            arr.push('                 <span class="c-text-label c-text-label--blue-type1">요금할인</span>\n');
        }
        arr.push('                </div>\n');
        if (!isEmpty(pageObj.rateAdsvcNm)){
            arr.push('            <strong>'+rateObj.rateAdsvcNm +'</strong>\n');
            //arr.push('            <p >'+rateObj.rateAdsvcData +'(xmlDataCnt:'+rateObj.mspRateMstDto.xmlDataCnt+'#xmlQosCnt:'+rateObj.mspRateMstDto.xmlQosCnt+') </p>\n');
        }else{
            arr.push('            <strong>'+rateObj.rateNm +'</strong>\n');
            //arr.push('            <p >'+rateObj.rateAdsvcData +'(xmlDataCnt:'+rateObj.mspRateMstDto.xmlDataCnt+'#xmlQosCnt:'+rateObj.mspRateMstDto.xmlQosCnt+') </p>\n');
        }

        var rateNm = pageObj.rateAdsvcNm;
        if(isEmpty(rateNm)){
            rateNm = pageObj.rateNm;
        }

        arr.push('                <a class="_rateDetailPop" href="javascript:void(0);" title="'+rateNm+' 상세내용 팝업 열기" rateAdsvcGdncSeq="'+ rateObj.rateAdsvcGdncSeq  +'"  rateAdsvcCd="' + rateObj.rateCd  +'"  >\n');
        arr.push('                    <i class="c-icon c-icon--arrow-gray4" aria-hidden="true"></i>\n');
        arr.push('                </a>\n');
        arr.push('            </div>\n');
        arr.push('            <!--데이터, 음성, 문자 영역 -->\n');
        arr.push('            <div class="rate-detail">\n');
        arr.push('                <ul class="rate-detail__list">\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--data" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">데이터</span>\n');
        arr.push('                        <div class="rate-detail__info">\n');
        if(!isEmpty(rateObj.rateAdsvcData)){
            arr.push('                        <p>'+rateObj.rateAdsvcData +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeDataCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromData)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromData +'</p>\n');
        }
        // arr.push('                            <p>+5GB (아무나결합)</p>\n');
        // arr.push('                            <p>+100GB (데이득)</p>\n');
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--call-gray" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">음성</span>\n');
        arr.push('                        <div class="rate-detail__info ">\n');
        if(!isEmpty(rateObj.rateAdsvcCall)){
            arr.push('                        <p>'+rateObj.rateAdsvcCall +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeCallCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromCall)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromCall +'</p>\n');
        }
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--msg" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">문자</span>\n');
        arr.push('                        <div class="rate-detail__info">\n');
        if(!isEmpty(rateObj.rateAdsvcSms)){
            arr.push('                        <p>'+rateObj.rateAdsvcSms +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeSmsCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromSms)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromSms +'</p>\n');
        }
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                </ul>\n');
        arr.push('            </div>\n');
        arr.push('            <!--제공 아이템 영역 -->\n');
        arr.push('            <div class="rate-info__gift">\n');
        if (!isEmpty(rateObj.rateAdsvcAllianceBnfit) ){
            arr.push('            <!--제휴 혜택 노출 문구 -->\n');
            arr.push('            <p class="rate-info__gift-info">'+ rateObj.rateAdsvcAllianceBnfit.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'').replace(/(<([^>]+)>)/ig,'') +'</p>\n');
        }

        if (!isEmpty(rateObj.rateAdsvcBnfit) ){
            arr.push('            <!--혜택 안내 노출 문구 -->\n');
            arr.push('             <p class="rate-info__present-info-info">'+ rateObj.rateAdsvcBnfit.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'').replace(/(<([^>]+)>)/ig,'') +'</p>\n');
        }

        arr.push('            </div>\n');

        arr.push('            <!--가격 영역-->\n');
        arr.push('            <div class="rate-price">\n');
        arr.push('                <div class="rate-price-wrap">\n');

        var dummyPrice = Number(rateObj.payMnthChargeAmt);
        if($("#agrmTrm").val() != '0'){
            dummyPrice = Number(rateObj.payMnthChargeAmt) + Number(rateObj.payMnthAmt);
        }
        arr.push('                 <!--단말 요금제 -->\n');
        arr.push('                 <div class="rate-price__item">\n');
        arr.push('                     <p class="rate-price__title product">\n');
        arr.push('                         월 단말요금 <b>' + numberWithCommas(rateObj.payMnthAmt) + '</b> 원 + 월 통신요금 <b>' + numberWithCommas(rateObj.payMnthChargeAmt) + '</b> 원\n');
        arr.push('                     </p>\n');
        arr.push('                     <p class="rate-price__info">\n');
        arr.push('                         <b>' + numberWithCommas(dummyPrice) + '</b> 원\n');
        arr.push('                     </p>\n');
        arr.push('                 </div>\n');
        arr.push('                </div>\n');
        arr.push('            </div>\n');
        arr.push('        </div>\n');
        arr.push('        <!-- 비교함 영역 -->\n');
        arr.push('        <div class="rate-compare">\n');
        if (isBadge == "ture") {
            arr.push('        <input type="checkbox" class="c-checkbox" id="chkBadgeAA_'+cardCnt+'" value="'+ rateObj.rateAdsvcGdncSeq  +'" data-rate-adsvc-cd="' + rateObj.rateCd  +'"  checked  >\n');
        } else {
            arr.push('        <input type="checkbox" class="c-checkbox" id="chkBadgeAA_'+cardCnt+'" value="'+ rateObj.rateAdsvcGdncSeq  +'" data-rate-adsvc-cd="' + rateObj.rateCd  +'"  >\n');
        }
        if (pageObj.tabButIndex == "-1" ) {
            arr.push('        <label class="c-card__badge-label compare" for="chkBadgeAA_'+cardCnt+'">\n');
            arr.push('            <span class="c-hidden">'+rateObj.rateNm +' 비교하기</span>\n');
            arr.push('        </label>\n');
        } else {
            arr.push('        <label class="c-card__badge-label" for="chkBadgeAA_'+cardCnt+'">\n');
            arr.push('            <span class="c-hidden">'+rateObj.rateNm +' 비교하기</span>\n');
            arr.push('        </label>\n');
        }
        arr.push('        </div>\n');
        arr.push('</li>\n');
        return arr.join('');
    }


    var viewRateCtgGroup = function (type) {
        if (type == "1") {
            $('._rateCtg').each(function (){
                let tempVal = $(this).data("rateCtgGroup1");
                if (tempVal =="Y") {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            })
        } else {
            $('._rateCtg').each(function (){
                let tempVal = $(this).data("rateCtgGroup2");
                if (tempVal =="Y") {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            })
        }
    }


    var setRateInfo = function (obj, noCloseYn){
        var thisData = obj.data();
        var rateAdsvcNm = thisData.rateAdsvcNm;
        var phoneBuyPhrase = thisData.phoneBuyPhrase

        pageObj.chooseRateInfo = thisData;

        if(isEmpty(rateAdsvcNm)){
            rateAdsvcNm = thisData.rateNm;
        }

        var dataCnt = thisData.rateAdsvcData;
        var callCnt = thisData.rateAdsvcCall;
        var smsCnt = thisData.rateAdsvcSms;
        var dataProm = isEmpty(thisData.rateAdsvcPromData)  ? '' : '(' + thisData.rateAdsvcPromData + ')';
        var callProm = isEmpty(thisData.rateAdsvcPromCall)  ? '' : '(' + thisData.rateAdsvcPromCall + ')';
        var smsProm = isEmpty(thisData.rateAdsvcPromSms)  ? '' : '(' + thisData.rateAdsvcPromSms + ')';
        var salePlcyCd = isEmpty(thisData.salePlcyCd)  ? '' :  thisData.salePlcyCd ;

        //KT인터넷 트리플할인 할인금액 확인
        if (obj.val() != pageObj.ktTripleDcRateCd) {

            pageObj.ktTripleDcRateCd =  obj.val();
            //2번 호출 방지
            var varData = ajaxCommon.getSerializedData({
                rateCd : obj.val()
            });

            ajaxCommon.getItemNoLoading({
                id:'getKtTripleDcAmt'
                ,cache:false
                ,url:'/msp/getKtTripleDcAmtAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            },function(ktTripleDcAmt){
                pageObj.ktTripleDcAmt = ktTripleDcAmt;
                if (0 < ktTripleDcAmt) {
                    $('._ktTripleDcCss').show();
                    $('#laKtTripleDcAmt_02').html("요금할인 24개월<br/>(-"+numberWithCommas(pageObj.ktTripleDcAmt)+"원)");

                } else {
                    $('._ktTripleDcCss').hide();
                    $('#laKtTripleDcAmt_02').html("요금할인 24개월(0원)");
                }
            });
        }

        $('#choicePay').text(rateAdsvcNm);
        $('#choicePayTxt').text('데이터 ' + dataCnt + dataProm + ' | 음성 ' + callCnt + callProm + ' | 문자 ' + smsCnt + smsProm);
        $('#choicePayTxt2').text(phoneBuyPhrase);
        $('#radPayTypeA').hide();
        $('#radPayTypeB').show();
        $('#socCode').val(obj.val());
        $('#salePlcyCd').val(salePlcyCd);
        selectUsimBasJoinPriceAjax(true);
        nextStepBtnChk();
        $('.c-icon--close').trigger('click');

        evntCdPrmtAreaYn(obj.val());
    }

    var selectUsimBasJoinPriceAjax = function( isInit){
        var thisData = pageObj.chooseRateInfo;
        var rateNm = thisData.rateNm ;
        var baseAmt = thisData.baseVatAmt; // 20230807 부가세포함 가격으로 변경
        var totalVatPrice = 0;
        var promotionDcAmt = thisData.promotionDcAmt;
        var promotionAmtVatDesc = thisData.promotionAmtVatDesc ;
        var payMnthChargeAmt = 0;
        var agrmTrmVal = $("#agrmTrm").val();

        if (isInit == undefined ) {
            isInit = true ;
        }

        var totalVatPrice = Number(thisData.payMnthChargeAmt);
        if($("#agrmTrm").val() != '0'){
            totalVatPrice = Number(thisData.payMnthChargeAmt) + Number(thisData.payMnthAmt);
        }
        payMnthChargeAmt = thisData.payMnthChargeAmt;
        totalVatPrice = Math.ceil(totalVatPrice)+'';

        if (typeof totalVatPrice == 'string') {
            totalVatPrice = totalVatPrice.replace(/,/gi, '');
        }

        var dcAmt = (isEmpty(thisData.dcVatAmt)) ?  0 : thisData.dcVatAmt;
        var addDcAmt = thisData.addDcVatAmt;
        var text = "";

        //$("#initRateCd").val(obj.val());
        if (isInit) {
            getJoinUsimPriceInfo(pageObj.chooseRateInfo.rateCd);
        }


        var prdtSctnVal = '5G';
        if($('#prdtSctnCd').val() == '03') {
            prdtSctnVal = '3G';
        } else if ($('#prdtSctnCd').val() == '04') {
            prdtSctnVal = 'LTE';
        } else if ($('#prdtSctnCd').val() == '45') {
            prdtSctnVal = $('input[name=dataType]:checked').val();
        }
        $('#bottomTitle').text(prdtSctnVal + ' / ' + $('input[name=operType]:checked').next().text() + ' / ' + rateNm);

        if(agrmTrmVal == '0'){
            $('#payMnthAmt').text(numberWithCommas(thisData.payMnthAmt));
        }else{
            $('#payMnthAmt').text(numberWithCommas(Number(thisData.payMnthAmt)));
        }
        $('#hndstAmt').text(numberWithCommas(thisData.hndstAmt) + ' 원');
        $('#subsdAmt').text('-' + numberWithCommas(thisData.subsdAmt) + ' 원');
        $('#agncySubsdAmt').text('-' + numberWithCommas(thisData.agncySubsdAmt) + ' 원');
        $('#finstAmt').text(numberWithCommas(thisData.instAmt) + ' 원');
        pageObj.instAmt = thisData.instAmt ; // 단말기 할부원금
        $('#totalFinstCmsn').text(numberWithCommas(thisData.totalInstCmsn) + ' 원');
        $('#hndstPayAmt').text(numberWithCommas(thisData.hndstPayAmt) + ' 원');

        //일시납부
        if(agrmTrmVal == '0'  ){
            $("#finstAmt").siblings("dt").text("즉시결제 금액");
            $("#totalFinstCmsn").parent().hide();  //총할부수수료
        } else {
            $("#finstAmt").siblings("dt").text("할부원금");
            $("#totalFinstCmsn").parent().show();  //총할부수수료
        }

        $('#totalPriceBottom').html(numberWithCommas(totalVatPrice) + '<span class="fs-14 fw-normal"> 원</span>');
        $('#totalPrice').html(numberWithCommas(totalVatPrice));
        $('#totalPrice2').html(numberWithCommas(payMnthChargeAmt));
        $('#baseAmt').html(numberWithCommas(baseAmt)+' 원');
        $('#promotionDcAmtTxt').html(numberMinusWithCommas(promotionDcAmt) + ' 원');
        pageObj.totalVatPrice = totalVatPrice;

        let totalDcAmt = 0;
        //totalPrice  오른쪽 최종 금액 팝업 레이어
        //totalPrice2 월예상 납부금액  팝업 레이어 닫고 .. 표현 하는것..
        if ($("#ktTripleDcAmt_02").is(':checked') ) {
            totalDcAmt += pageObj.ktTripleDcAmt ;
            $('#ktTripleDcAmtTxt').html(numberMinusWithCommas(pageObj.ktTripleDcAmt)+ ' 원');
        } else {
            $('#ktTripleDcAmtTxt').html("0 원");
        }
        totalDcAmt += pageObj.cprtDcAmt ;

        if(totalDcAmt  > 0){
            if(totalDcAmt  >= totalVatPrice){
                $('#totalPrice').html('0');
                $('#totalPrice2').html('0');
                $('#totalPriceBottom').html('0');
            }else{
                $('#totalPrice').html(numberWithCommas(payMnthChargeAmt - totalDcAmt + Number(thisData.payMnthAmt)));   //$(obj).attr('payMnthAmt')
                $('#totalPrice2').html(numberWithCommas(payMnthChargeAmt));
                $('#totalPriceBottom').html(numberWithCommas(payMnthChargeAmt - totalDcAmt + Number(thisData.payMnthAmt)));
            }
        } else {
            $('#totalPrice').html(numberWithCommas(payMnthChargeAmt + Number(thisData.payMnthAmt)));   //$(obj).attr('payMnthAmt')
            $('#totalPrice2').html(numberWithCommas(payMnthChargeAmt));
            $('#totalPriceBottom').html(numberWithCommas(payMnthChargeAmt  + Number(thisData.payMnthAmt)));
        }

        $('.bottomTop').show();
        if(dcAmt == '0'){
            $('#bottomDefDc').hide();
        }else{
            $('#bottomDefDc').show();
            $('#dcAmt').html(numberMinusWithCommas(dcAmt)+' 원');
        }

        if(addDcAmt == '0'){
            $('#bottomPriceDc').hide();
        }else{
            $('#bottomPriceDc').show();
            $('#addDcAmt').html(numberMinusWithCommas(addDcAmt)+' 원');
        }
    };

    ///요금제 관련 END ################

    //가입비 유심비 설정 START###############
    var optionCharge = function() {
        var operType = $(':radio[name="operType"]:checked').val();
        var prdtSctnVal = "";
        if ($('#prdtSctnCd').val() == '03') {
            prdtSctnVal = '3G';
        } else if($('#prdtSctnCd').val() == '04') {
            prdtSctnVal = 'LTE';
        } else if ($('#prdtSctnCd').val() == '45') {
            prdtSctnVal = $('input[name=dataType]:checked').val();
        } else {
            prdtSctnVal = '5G';
        }

        var varData = ajaxCommon.getSerializedData({
            operType : operType,
            prdtSctnCd : prdtSctnVal
        });

        ajaxCommon.getItemNoLoading({
            id : 'getUsimChargeAjax',
            cache : false,
            url : '/m/product/phone/getUsimChargeAjax.do',
            data : varData,
            dataType : 'json'
        }, function(jsonObj) {
            if(operType==OPER_TYPE.MOVE_NUM){
                $('#move_01').show();
                $('#move_02').show();
                $('#moveYn').val('Y');
            }else{
                $('#move_01').hide();
                $('#move_02').hide();
                $('#moveYn').val('N');
            }

            $("#joinPrice").val(numberWithCommas(jsonObj.joinPrice));
            $("#usimPrice").val(numberWithCommas(jsonObj.usimPrice));
            showJoinUsimPrice();
        });
    };

    //판매 요금제 별 가입비/유심비 면제 여부 조회
    var getJoinUsimPriceInfo = function(socCode) {
        var varData = ajaxCommon.getSerializedData({
            dtlCd:socCode
            ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfo
        });

        ajaxCommon.getItemNoLoading({
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

                if (jsonObj != null && jsonObj.expnsnStrVal2 == "Y") {
                    $("#usimPriceIsPay").val("Y");
                } else {
                    $("#usimPriceIsPay").val("N");
                }
                showJoinUsimPrice();
            });
    }

    var showJoinUsimPrice = function() {
        var joinPriceIsPay = $("#joinPriceIsPay").val();
        var usimPriceIsPay = $("#usimPriceIsPay").val();
        var usimPrice = $("#usimPrice").val();
        var joinPriceVal = 0;
        var usimPriceVal = 0;
        var movePriceVal = 0;

        if (joinPriceIsPay == "Y") {
            joinPriceVal = $('#joinPrice').val();
            $("#joinPriceTxt").html($("#joinPrice").val()+" 원");
        } else {
            $("#joinPriceTxt").html('<span class="c-text c-text--strike">' + numberWithCommas($("#joinPrice").val()) + ' 원</span>(무료)');
        }

        if (usimPriceIsPay == "Y") {
            usimPriceVal = usimPrice.replace(/,/, '');
            $("#usimPriceTxt").html(usimPrice+" 원");
        } else {
            $("#usimPriceTxt").html('<span class="c-text c-text--strike">'+usimPrice+' 원</span>(무료)');
        }

        if($('#moveYn').val() == 'Y'){
            movePriceVal = 800;
        }

        if(!$('#move_02').is(':visible') && joinPriceIsPay != 'Y' && usimPriceIsPay != 'Y'){
            $('#bottomMiddle').hide();
        }else{
            $('.bottomMiddle').show();
        }
    }


    //가입비 유심비 설정  END################


    /////////사은품 START///////////////////////////////////

    //////// 사은품 END////////////////////////////////////////////

    // 핸드폰 이미지 정보 #########################################
    var getPhoneData = function(isInit) {
        var hndsetModelIdVal = $("#hndsetModelId").val();
        var varData = ajaxCommon.getSerializedData({
            prodId:$("#fprodId").val()
            ,hndsetModelId:hndsetModelIdVal
            ,onOffType:"3" //모바일
        });
        ajaxCommon.getItemNoLoading({
                id:'getPhoneData'
                ,cache:false
                ,url:'/m/product/phone/phoneViewDataAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(jsonObj) {
                if(isInit){
                    pageObj.initPhoneData = jsonObj.phoneProdBas.phoneSntyBasDtosList;
                    initAtribArea();
                } else {
                    //품정 여부 확인
                    jsonObj.phoneProdBas.phoneSntyBasDtosList.forEach(phoneSntyBas=> {
                        if (phoneSntyBas.hndsetModelId == hndsetModelIdVal ) {
                            pageObj.sdoutYn = phoneSntyBas.sdoutYn;
                        }
                    });
                }

                var dataSettlYn = jsonObj.phoneProdBas.settlYn.trim() + "#N";
                var dataSettlYnList = dataSettlYn.split("#");

                pageObj.settlYn = dataSettlYnList[0].trim() == "" ? "N" : dataSettlYnList[0].trim() ;
                pageObj.settlMnpYn = dataSettlYnList[1].trim() == "" ? "N" : dataSettlYnList[1].trim() ;
                pageObj.recommendRate = jsonObj.phoneProdBas.recommendRate;
                pageObj.recommendRateNoargm = jsonObj.phoneProdBas.recommendRateNoargm;


                var phoneImgList = jsonObj.phoneProdBas.phoneProdImgDtoList ;
                var bgLayoutImg = jsonObj.phoneProdBas.layerYn == 'Y' ? jsonObj.phoneProdBas.layerImageUrl : '';
                var fprodNm = $("#fprodNm").val();
                var $slider = $('#main-content > div.phone-detail.c-expand > div > div.swiper-wrapper');
                $slider.empty();
                for (var imgIdx=0; imgIdx < phoneImgList.length; imgIdx++) {
                    if (phoneImgList[imgIdx].sntyColorCd == pageObj.selSntyColorCd ) {
                        var imgDetailList = phoneImgList[imgIdx].phoneProdImgDetailDtoList ;

                        for (var i=0; i < imgDetailList.length; i++) {
                            if (imgDetailList[i].imgTypeCd != "04" && !isEmpty(imgDetailList[i].imgPath)) {
                                if ($('#serverNm').length > 0 && "LOCAL" ==  $('#serverNm').val()) {
                                    imgDetailList[i].imgPath = "https://www.ktmmobile.com" + imgDetailList[i].imgPath;
                                }

                                if (!isEmpty(bgLayoutImg)) {
                                    $slider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' />" +
                                        "<img src='" + bgLayoutImg + "' alt='" + fprodNm + "_layout' /></div>");
                                } else {
                                    $slider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' /></div>");
                                }
                            }
                        }
                        break;
                    }
                }



                if ("Y" == pageObj.sdoutYn ) {
                    //???
                    setTimeout(function() {
                        MCP.alert('선택하신 용량/색상은 품절 되었습니다.', function (){
                            location.href= '/product/phone/phoneList.do';
                        });
                    },500);
                    $("._btnJoinPage").addClass('is-disabled');
                }

                $("#main-content > div.phone-detail.c-expand > div > div.swiper-wrapper > div > img").each(function(){
                    $(this).error(function(){
                        $(this).unbind("error");
                        $(this).attr("src","/resources/images/mobile/content/img_240_no_phone.png");
                    });
                });
                initSwiper();
            });

    };

    //용량 색상 정보 세팅
    var initAtribArea = function(){

        var frstRadCd = '';
        var volumeCd = '';
        var valCd = new Array;
        var valNm = new Array;
        var dataList = new Array;
        var sdoutYnArr = new Array;
        if(pageObj.initPhoneData !=null){
            $.each(pageObj.initPhoneData,function(idx,val){
                var atribValCd2 = pageObj.initPhoneData[idx].atribValCd2;
                var atribValNm2 = pageObj.initPhoneData[idx].atribValNm2;
                var sdoutYn = pageObj.initPhoneData[idx].sdoutYn;
                dataList.push({'atribValCd2':atribValCd2,'sdoutYn':sdoutYn});

                if(valCd.indexOf(atribValCd2) == -1){
                    valCd.push(atribValCd2);
                    valNm.push(atribValNm2);
                }
            });

            // 품절 여부 판단.동일 용량의 모든제품이 품절이여야 품절 노출시킴
            // 중복제거 후 컬러배열
            $.each(valCd,function(idx,val){
                var voulnm = valCd[idx];
                var totCnt = 0;
                var soldOutCnt = 0;
                $.each(dataList,function(i,v){ // 용량 등록한 모든데이터
                    var atribValCd3 = dataList[i].atribValCd2;
                    var sdoutYn1 = dataList[i].sdoutYn;
                    if(voulnm==atribValCd3){
                        totCnt++;
                        if(sdoutYn1=="Y"){
                            soldOutCnt++;
                        }
                    }
                });

                if(totCnt > 0 && totCnt==soldOutCnt){
                    sdoutYnArr.push("[품절]");
                } else {
                    sdoutYnArr.push("");
                }
            });
            // 품절 여부 판단.동일 용량의 모든제품이 품절이여야 품절 노출시킴 끝

            for(var i =0; i < valCd.length; i++){
                $("#atribValCd2").append('<option label="'+valNm[i]+sdoutYnArr[i]+'" value="'+valCd[i]+'">'+valNm[i]+'</option>');
            }
        }

        setColorHtml();
        //findHndsetModelId(pageObj.frstRadCd,frstVlCd);
    }

    var setColorHtml = function() {
        var radANum = 0;
        var radioHtml = '';

        $("#colorGroupDiv").empty();
        $("#colorNm").text("");
        if(pageObj.initPhoneData !=null){

            $.each(pageObj.initPhoneData,function(idx,phoneData){
                var atribValCd1 = phoneData.atribValCd1
                var atribValCd2 = phoneData.atribValCd2
                var stdOutYn = phoneData.sdoutYn;
                var atribValNm1 = phoneData.atribValNm1;

                if(stdOutYn != 'Y'){
                    radioHtml+='<input class="c-radio" id="radGA'+radANum+'" type="radio" name="radGA" atribValNm1="'+atribValNm1+'" value="'+atribValCd1+'" data-hndset-model-id="'+phoneData.hndsetModelId+'" > ';

                    var rgbData = '#192f41';
                    $(pageObj.colorData).each(function() {
                        if(this.dtlCd == atribValCd1){
                            rgbData = this.expnsnStrVal1;
                        }
                    });

                    radioHtml+='<label class="c-label" for="radGA'+radANum+'"><span class="c-hidden">'+atribValNm1+'</span><span style="background-color: '+rgbData+'"></span></label>';
                    radANum++;
                }
            });
        }

        if ("" == radioHtml) {
            $("#colorGroupDiv").parent().hide();
            pageObj.sdoutYn = "Y";
        } else {
            $("#colorGroupDiv").html(radioHtml);
            var hndsetModelId = $("#hndsetModelId").val();
            if (!isEmpty(hndsetModelId) ) {
                $("input:radio[name='radGA'][data-hndset-model-id='"+hndsetModelId+"']").prop('checked', true);
                pageObj.selSntyColorCd = $("input:radio[name='radGA'][data-hndset-model-id='"+hndsetModelId+"']").val();
            }

            //필요 없는데 .. 혹시 .. 기본 값 ...
            if( !$("input:radio[name='radGA']").is(":checked")){
                var $radGADom =  $("input:radio[name='radGA']").eq(0);
                $radGADom .prop('checked', true);
                pageObj.selSntyColorCd = $radGADom .val();
                $("#hndsetModelId").val($radGADom .data("hndsetModelId"));
                $("#colorNm").text($$radGADom .attr("atribValNm1"));
            } else {
                $("#colorNm").text($("input:radio[name='radGA']:checked").attr("atribValNm1"));
            }
        }

    }



    //휴대폰 상세 내용 동적 표현 START ##################
    var flexibleComponent = function (componentId) {
        ajaxCommon.getItemNoLoading({
                url: "/termsPop.do",
                type: "GET",
                dataType: "json",
                data: "cdGroupId1=INFOPRMT&cdGroupId2=" + componentId,
                async: false
            }
            ,function(data) {
                if (data.docContent != null) {
                    var inHtml = unescapeHtml(data.docContent);
                    $('#' + componentId).html(inHtml);
                }
            }
        );
    }

    for (var i = 0; i < document.getElementsByClassName('flexible').length; i++) {
        var id = document.getElementsByClassName('flexible')[i].id;
        flexibleComponent(id);
    }
    //휴대폰 상페 내용 동적 표현 처리 END ##################

    var findHndsetModelId = function(atribValCd1, atribValCd2){
        for (var dataIdx= 0; dataIdx < pageObj.initPhoneData.length; dataIdx++) {
            if(atribValCd1 == pageObj.initPhoneData[dataIdx].atribValCd1 && atribValCd2 == pageObj.initPhoneData[dataIdx].atribValCd2){
                $("#hndsetModelId").val(pageObj.initPhoneData[dataIdx].hndsetModelId);
                break;
            }
        }
    }

    var setAgrmTrm = function (){

        $("#agrmTrm option").remove();
        //신규?? 결제 여부 ??
        if($('input[name=operType]:checked').val() == 'NAC3' && pageObj.settlYn =="Y"  ) {
            $("#agrmTrm").append("<option label='일시납부' value='0'>일시납부</option>");
        } else {
            //무약정 여부
            let instNomVal =  $('input[name=instNom]:checked').val() ;
            if (instNomVal == '0') {

                if($('input[name=operType]:checked').val() == 'MNP3' && pageObj.settlMnpYn =="Y"  ) {
                    $("#agrmTrm").append("<option label='일시납부' value='0'>일시납부</option>");
                    return;
                }  else if ($('input[name=operType]:checked').val() == 'MNP3' && pageObj.settlMnpYn =="A"  ) {
                    $("#agrmTrm").append("<option label='일시납부' value='0'>일시납부</option>");
                }

                pageObj.agrmTrmList.forEach( agrmTrmListVal => {
                    $('input[name=instNom]').each(function (x, obj){
                        if (agrmTrmListVal == $(obj).val()) {
                            $("#agrmTrm").append("<option value='"+$(obj).val()+"'>"+$(obj).val()+"개월</option>");
                        }
                    });
                });

                if ($('input[name=operType]:checked').val() == 'MNP3' && pageObj.settlMnpYn =="A"  ) {
                    $('#agrmTrm').val('0');
                } else {
                    if (pageObj.asAgrmTrmVal !=null && "" == pageObj.asAgrmTrmVal) {
                        $('#agrmTrm').val('24');
                    } else {
                        $('#agrmTrm').val(pageObj.asAgrmTrmVal);
                    }
                }
            } else {
                if($('input[name=operType]:checked').val() == 'MNP3' && pageObj.settlMnpYn =="Y"  ) {
                    $("#agrmTrm").append("<option label='일시납부' value='0'>일시납부</option>");
                    return;
                }
                $("#agrmTrm").append("<option value='"+instNomVal+"'>"+instNomVal+"개월</option>");

                if ($('input[name=operType]:checked').val() == 'MNP3' && pageObj.settlMnpYn =="A"  ) {
                    $("#agrmTrm").append("<option label='일시납부' value='0'>일시납부</option>");
                }
            }
        }
    }


    var initView = function() {
        $('#socCode').val('');
        $('#radPayTypeA').show();
        $('#radPayTypeB').hide();
        $('#choicePay').text('');
        $('#choicePayTxt').text('');
        $('#bottomTitle').text('');
        $('#searchNm').val('');

        $('#dcAmt').html('0 원');
        $('#addDcAmt').html('0 원');
        $('#baseAmt').html('0 원');
        $('#totalPrice').html("0");
        $('#totalPrice2').html("0");
        $('#totalPriceBottom').html("0" + '<span class="fs-14 fw-normal" id="totalPriceBottom"> 원</span>');
        $('#promotionDcAmtTxt').html("0 원");
        $("#joinPriceTxt").html("0 원");
        $("#usimPriceTxt").html("0 원");

        //kt 트리플 할인 초기화
        $('#ktTripleDcAmtTxt').html("0 원");
        $('#ktTripleDcAmt_01').prop("checked",true);
        pageObj.ktTripleDcAmt = 0;
        pageObj.ktTripleDcRateCd = "";
        $('._ktTripleDcCss').hide();

        initEvntCdPrmt();
    }


    var nextStepBtnChk = function (){

        // 이벤트코드 프로모션 추가
        var evntCdPrmtValidation = true;
        if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
            evntCdPrmtValidation = false;
        }

        if($('input[name=radOpenType]:checked').val() != undefined && $('input[name=operType]:checked').val() != undefined
            && $('input[name=instNom]:checked').val() != undefined && $('#radPayTypeB').is(':visible')
            && evntCdPrmtValidation){
            $('#nextStepBtn').removeClass('is-disabled');
        } else {
            $('#nextStepBtn').addClass('is-disabled');
        }
    }

    //START
    $("#modalArs").addClass("rate-detail-view");
    var initInstNom = $("#initInstNom").val();
    if ("0" == initInstNom) {
        $('input[name=instNom]').eq(0).trigger('change');
    }
    getPhoneData(true);

    setAgrmTrm();




    if($('#initRateCd').val() != ''){
        pageObj.chooseRateInfo.rateCd = $('#initRateCd').val();
        pageObj.initBestRate = "1";
        getChargelist();
    } else {
        pageObj.initBestRate = "0";
        nextStepBtnChk();
    }

    //로컬 이미지 처리 ..
    if ($('#serverNm').length > 0 && "LOCAL" ==  $('#serverNm').val()) {
        $('img').each(function(){
            var thisScr = $(this).attr("src");
            if (thisScr.indexOf("www.ktmmobile.com") < 0) {
                var valSrc = "https://www.ktmmobile.com" +  thisScr
                $(this).attr("src",valSrc)
            }
        });
    }

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

    $('#evntCdPrmt').on('keyup', nextStepBtnChk);

});

    var confirmNextStep = function (type){

        //임시저장 처리
        var onOffType = "3";
        var sesplsYn = 'N';
        var cntpntShopId = $('#initOrgnId').val();
        var agrmTrmVal = $('#agrmTrm').val();
        var prdtSctnCd = '5G';
        if ($('#prdtSctnCd').val() == '04') {
            prdtSctnCd = 'LTE';
        } else if ($('#prdtSctnCd').val() == '03') {
            prdtSctnCd = '3G';
        } else if ($('#prdtSctnCd').val() == '45') {
            prdtSctnCd = $('input[name=dataType]:checked').val();
        }

        //일시 납부
        var settlWayCd = "";
        if(agrmTrmVal =="0"  && pageObj.instAmt > 0){
            settlWayCd = "01";
        }

        var varData = ajaxCommon.getSerializedData({
            prodId : $('#fprodId').val()								//상품아이디(ex:3628)
            , operType : $('input[name=operType]:checked').val()		//가입유형(번이,신규)
            , cntpntShopId : cntpntShopId                               //접점코드
            , modelId : $('#hndsetModelId').val()		//모델코드(ex:K7006037) prdtId ==> modelId
            , modelMonthly : $('#agrmTrm').val()						//단말기할부기간
            , sprtTp : (!$('input[name=saleTy]:checked').val()) ? '' : $('input[name=saleTy]:checked').val()			//지원금유형 (단말할인:KD , 요금할인:PM)
            , serviceType : 'PO'										//PP: 선불, PO : 후불
            , socCode : $('#socCode').val()								//요금제코드(ex:PL20B9416)
            , enggMnthCnt : $("input[name='instNom']:checked").val()	//약정
            , reqBuyType : 'MM'											//UU:유심단독구매 MM:단말구매
            , onOffType : onOffType
            , tmpStep : '0'												//임시저장 단계 0 : 요금제설계 --> 가입신청중에 표시 생략, 1 : 본인확인 및 약관동의, 2 : 유심정보 및 신분증 확인, 3 : 가입신청 정보, 4 : 부가서비스 정보, 5 : 납부정보 및 가입정보 확인
            , settlAmt : $('#totalPrice').text().replace(/,/gi, '')		//임시 최종 결제 금액
            , modelSalePolicyCode : $('#salePlcyCd').val()				//정책코드
            , prdtSctnCd : prdtSctnCd									//03:3G, 04:LTE, 08:5G
            , sesplsYn : sesplsYn
            , settlWayCd : settlWayCd
            , evntCdPrmt : $.trim($("#evntCdSeq").val())
        });



        ajaxCommon.getItem({
            id:'insertAppFormTempSave'
            ,cache:false
            ,url:'/appForm/insertAppFormTempSave.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        } ,function(data){
            if(data.resultCd == '0000'){
                var url = '/m/appForm/appForm.do';
                if(type == 'noMember' || type == 'member'){
                    ajaxCommon.createForm({
                        method : 'post'
                        ,action : url
                    });
                    ajaxCommon.attachHiddenElement("requestKey",data.requestTempKey);
                    ajaxCommon.formSubmit();
                }else {
                    ajaxCommon.createForm({
                        method : 'post'
                        ,action : '/m/loginForm.do'
                    });
                    ajaxCommon.attachHiddenElement("redirectUrl",url+'?requestKey=' + data.requestTempKey);
                    ajaxCommon.formSubmit();
                }
            } else{
                MCP.alert(data.msg);
            }
        });
    }

    function initSwiper(){

        var swipeGuideWrap = new Swiper('#phoneSwiper', {
            navigation: {
                nextEl: '.swiper-next',
                prevEl: '.swiper-prev',
            },
            pagination: {
                el: '.swiper-pagination',
            },
        });

    }


// 사용 후기 처리    ##################

    var viewDesc = function(total,requestReviewDtoList, conNumList, isLogin){
        $("#nodata").remove();

        var htmlArea = "";

        if(requestReviewDtoList !=null && requestReviewDtoList.length>0){
            var reviewImgList = "";
            var regNm = "";
            var reqBuyTypeNm = "";
            var eventCd = "";
            var eventNm = "";
            var sysRdateDd = "";
            var reviewSbst = "";
            var snsInfo = "";
            var commendYn = ""; // 추천,비추천
            var ntfYn = ""; // best
            var contractNum = "";
            var ranking = 0;
            var requestKey ="";
            var reviewQuestionList = "";

            for( var i=0; i<requestReviewDtoList.length; i++ ){

                reviewImgList = requestReviewDtoList[i].reviewImgList;
                regNm = requestReviewDtoList[i].mkRegNm;
                reqBuyTypeNm = requestReviewDtoList[i].reqBuyTypeNm;
                eventCd = requestReviewDtoList[i].eventCd;
                eventNm = requestReviewDtoList[i].eventNm;
                sysRdateDd = requestReviewDtoList[i].sysRdateDd;
                reviewSbst = requestReviewDtoList[i].reviewSbst;
                snsInfo = ajaxCommon.isNullNvl(requestReviewDtoList[i].snsInfo,"");
                commendYn = requestReviewDtoList[i].commendYn;
                ntfYn = requestReviewDtoList[i].ntfYn;
                contractNum = requestReviewDtoList[i].contractNum;
                ranking = requestReviewDtoList[i].ranking;
                requestKey = requestReviewDtoList[i].requestKey;
                reviewQuestionList = requestReviewDtoList[i].reviewQuestionList;


                htmlArea += "<li class='review__item'>";
                htmlArea += "<div class='label-wrap'>";
                if(commendYn =="1"){
                    if(ranking < 4){
                        if(ntfYn == 1){
                            htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                        }else{
                            htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                        }
                    }else{
                        if(ntfYn == 1){
                            htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                        }else{
                            htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                        }
                    }
                } else {
                    if(ranking < 4){
                        if(ntfYn == 1){
                            htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                        }else{
                            htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                        }
                    }else{
                        if(ntfYn == 1){
                            htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                        }else{
                            htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                        }
                    }
                }

                htmlArea += "</div>";
                htmlArea += "<h3 class='review__title'>" + reqBuyTypeNm+"/"+eventNm + "</h3>";
                htmlArea += "<div class='review__info'>";


                if (regNm != "") {
                    htmlArea += "<span class='review__user'>" + regNm+"</span>";
                    htmlArea += "<span class='review__date'>" + sysRdateDd + "</span>";
                } else {
                    htmlArea += "<span class='review__user'>" + sysRdateDd + "</span>";
                }

                htmlArea += "</div>";

                htmlArea += '<ul class="review-summary">';
                if(reviewQuestionList !=null && reviewQuestionList.length>0){
                    for(var j = 0; j < reviewQuestionList.length; j ++){
                        htmlArea += '<li class="review-summary__item">';
                        htmlArea += '<div class="review-summary__question">' +reviewQuestionList[j].questionMm +'</div>';
                        htmlArea += '<div class="review-summary__answer">' + reviewQuestionList[j].answerDesc+'</div>';
                        htmlArea += '</li>';

                    }
                }

                htmlArea += '</ul>';
                htmlArea += "<div class='review__content fs-13 c-text-ellipsis--type3' id='ccontentM_" + pageObj.reviewNo + "'>";
                htmlArea += "<pre>"+reviewSbst+"<pre>";

                if(snsInfo !=""){
                    var platFormCd = $("#platFormCd").val();
                    htmlArea += "			<p class='u-mt--8'>";
                    if(snsInfo.indexOf("http") > -1){

                        if(platFormCd == 'A'){
                            htmlArea += "		<a class='review__button'  href=\"javascript:appOutSideOpen('"+snsInfo+"');\" class='snslink'>SNS 공유 사용후기 보기</a></p>";
                        } else {
                            htmlArea += "		<a class='review__button'  href='"+snsInfo+"' target='_blank' class='snslink'>SNS 공유 사용후기 보기</a></p>";
                        }
                    } else {
                        if(platFormCd == 'A'){
                            htmlArea += "		<a class='review__button'  href=\"javascript:appOutSideOpen('https://"+snsInfo+"');\" class='snslink'>SNS 공유 사용후기 보기</a></p>";
                        }else{
                            htmlArea += "		<a class='review__button' href='https://"+snsInfo+"' target='_blank' class='snslink'>SNS 공유 사용후기 보기</a></p>";
                        }
                    }
                }
                htmlArea += "		</div>";
                if(reviewImgList !=null && reviewImgList.length>0){
                    htmlArea += "<div class='review__image' id='ccontentM_img_" + pageObj.reviewNo + "'>";
                    if(reviewImgList.length > 1){
                        htmlArea += "<span class='c-text-label c-text-label--darkgray img-count'>+"+ (reviewImgList.length-1) +"</span>"
                    }
                    for(var k = 0; k < reviewImgList.length; k ++){
                        htmlArea += '<div class="review__image__item">';
                        htmlArea += "<img src='" + reviewImgList[k].filePathNm + "' alt='" + reviewImgList[k].filaAlt + "' onerror='this.src=\"/resources/images/mobile/content/img_review_noimage.png\"'>";
                        htmlArea += '</div>';
                    }
                    htmlArea += "</div>";
                }

                if(reviewImgList !=null && reviewImgList.length>0){
                    htmlArea += "<div class='review__button-wrap' id='" + requestKey + "' data-toggle-class='is-active' data-toggle-target='#ccontentM_img_" + pageObj.reviewNo + "|#ccontentM_" + pageObj.reviewNo + "'>";
                }else{
                    htmlArea += "<div class='review__button-wrap' id='" + requestKey + "' data-toggle-class='is-active' data-toggle-target='#ccontentM_" + pageObj.reviewNo + "'>";
                }
                htmlArea += "<button type='button' aria-expanded='false'>";
                htmlArea += "<i class='c-icon c-icon--arrow-down-bold' aria-hidden='true'></i>";
                htmlArea += "</button>";
                htmlArea += "</div>";
                htmlArea += "</li>";
                pageObj.reviewNo ++;
            }
            // append
            $("#reviewBoard").append(htmlArea);
            $("#moreBtn").show();
            $("#selEventCd").show();
            $("#recommendInfo").show();
            window.KTM.initialize();
        } else {
            $("#moreBtn").hide();
            $("#selEventCd").hide();
            $("#recommendInfo").hide();
            htmlArea += "<div class='c-nodata' id='nodata'>" +
                "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                "<p class='c-nodata__text'>조회 결과가 없습니다.</p>" +
                "</div>";
            $("#reviewBody").prepend(htmlArea);
        }



        // 더보기
        var reViewCurrent = $(".review__item").length;

        $("#reViewCurrent").text(reViewCurrent); // 현재 노출
        $("#reViewTotal").text(total);
        if(reViewCurrent == total){$("#moreBtn").hide();}
        $(".imgTag").each(function(){
            $(this).error(function(){
                $(this).unbind("error");
                $(this).attr("src","/resources/images/requestReview/noimage.png");
            });
        });

    }

    var getReViewData = function(pageNo){
        var eventCd = '';
        var varData = ajaxCommon.getSerializedData({
            pageNo : pageNo
            ,eventCd : eventCd
            , onlyMine : false
            , prodId : $("#fprodId").val()
            , recordCount : 10
        });

        ajaxCommon.getItem({
            id:'reviewDataAjax'
            ,cache:false
            ,url:'/m/requestReView/reviewDataAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            var total = jsonObj.total;
            var requestReviewDtoList = jsonObj.requestReviewDtoList;
            var conNumList = jsonObj.conNumList;
            var isLogin = jsonObj.isLogin;
            var eventTotal = jsonObj.eventTotal;
            var eventList = jsonObj.eventList;
            var selectHtml = "";

            viewDesc(total,requestReviewDtoList, conNumList, isLogin);
        });
    }

    // 사용후기 조회수 증가
    function increaseHit(obj, key){
        if($(obj).attr("aria-expanded") == "true") {
            return;
        }

    }


// 사용 후기 처리    끝 ##################


// 요금제 검색 > 상세보기 > 사용후기 START###########

    var count = 10;

    // 사용후기 목록 조회
    function goPageAjax() {
        var pageNo = $('#pageNo').html();
        var rateCd = $('#rateCd').html();
        var varData = ajaxCommon.getSerializedData({
            pageNo : pageNo,
            rateCd : rateCd
        });

        $.ajax({
            id : 'faqGetPagingAjax',
            type : 'POST',
            cache : false,
            url : '/m/rate/requestReviewGetPagingAjax.do',
            data : varData,
            dataType : "json",
            success : function(data) {
                $('#btnHide').show();

                let innerHTML = "";
                let v_requestReviewList = data.requestReviewDtoList;

                $.each(v_requestReviewList, function(idx, item) {
                    let v_reviewImgList = item.reviewImgList
                    let v_eventNm = item.eventNm;
                    let v_regNm = item.mkRegNm;
                    let v_sysRdateDd = item.sysRdateDd;
                    let v_reviewSbst = item.reviewSbst;
                    let v_requestKey = item.requestKey;
                    let v_commendYn = ajaxCommon.isNullNvl(item.commendYn, "");
                    let v_ntfYn = ajaxCommon.isNullNvl(item.ntfYn, "");
                    let v_snsInfo = ajaxCommon.isNullNvl(item.snsInfo, "");
                    //let v_platFormCd = v_platFormCd;

                    innerHTML += "<li class='review__item'>";
                    innerHTML += "    <div class='label-wrap'>";
                    if(v_commendYn == "1") {
                        innerHTML += "        <span class='c-text-label c-text-label--mint-type1'>추천</span>";
                    }
                    if(v_ntfYn == "1") {
                        innerHTML += "        <span class='c-text-label c-text-label--red'>BEST</span>";
                    }
                    innerHTML += "    </div>";
                    innerHTML += "    <h3 class='review__title'>"+v_eventNm+"</h3>";
                    innerHTML += "    <div class='review__info'>";
                    innerHTML += "        <span class='review__user'>"+v_regNm+"</span>";
                    innerHTML += "        <span class='review__date'>"+v_sysRdateDd+"</span>";
                    innerHTML += "    </div>";

                    if(v_reviewImgList != null && v_reviewImgList != "") {
                        $.each(v_reviewImgList, function(index, value) {
                            let v_filePathNm = value.filePathNm;
                            innerHTML += "       	<div class='review__image__item' id='content_img_"+(count+1)+"'>";
                            innerHTML += "               	<img src='"+v_filePathNm+"' alt=''>";
                            innerHTML += "       	</div>";
                        });
                    }
                    innerHTML += "    <div class='review__content fs-13 c-text-ellipsis--type3' id='content_"+(count+1)+"'>";
                    innerHTML += 		  v_reviewSbst;

                    if(v_snsInfo != "") {
                        innerHTML += "            <p class='u-mt--8'>";
                        if(platFormCd == "A") {
                            innerHTML += "		          <a class='fs-14 c-text-link--blue u-ml--0 u-mb--2' href='javascript:appOutSideOpen('"+v_snsInfo+"');' title='새창열림'>SNS 공유 사용후기 자세히 보기</a>";
                        } else if(platFormCd != "A") {
                            innerHTML += "		           <a class='fs-14 c-text-link--blue u-ml--0 u-mb--2' href='"+v_snsInfo+"' target='_blank' title='새창열림'>SNS 공유 사용후기 자세히 보기</a>";
                        }
                        innerHTML += "           </p>";
                    }
                    innerHTML += "    </div>";

                    let dataToggleTarget = "";
                    if(v_reviewImgList != null && v_reviewImgList != "") {
                        dataToggleTarget = "#content_img_"+(count+1)+"|#content_"+(count+1);
                    } else {
                        dataToggleTarget = "#content_"+(count+1);
                    }
                    innerHTML += "    <div class='review__button-wrap' data-toggle-class='is-active' data-toggle-target='"+dataToggleTarget+"'>";
                    innerHTML += "        <button type='button' aria-expanded='false' onclick='increaseHit(this,"+v_requestKey+"); return false;'>";
                    innerHTML += "            <i class='c-icon c-icon--arrow-down-bold' aria-hidden='true'></i>";
                    innerHTML += "        </button>";
                    innerHTML += "    </div>";
                    innerHTML += "</li>";

                    count += 1;
                });

                $('#requestReviewList').append(innerHTML);
                MCP.init();

                /* 더보기에 게시글 개수 setting */
                if ($('#total').html() < 10) {
                    $('#btnHide').hide();
                }
                $("#pagingCnt").html(count + "/" + $('#total').html());
                if (parseInt(count) >= parseInt($('#total').html())) {
                    $('#moreList').hide();
                }
            }
        });
    };

    // 사용후기 더보기
    function goNextPage() {
        let pageNo = parseInt($('#pageNo').html());
        let maxPage = $('#maxPage').html();
        if (parseInt(pageNo) + 1 > parseInt(maxPage)) {
            alert('마지막 페이지입니다.');
            return;
        }

        $('#pageNo').html(parseInt($('#pageNo').html()) + 1);

        goPageAjax();
    }


// 요금제 검색 > 상세보기 > 사용후기 END ###########

    var getShareUrl = function (){
        var prodId =  $('#fprodId').val();
        var operType= $('input[name=operType]:checked').val() != undefined ? $('input[name=operType]:checked').val() : '';	//가입유형(번이,신규)
        var modelId = $('#hndsetModelId').val();	//모델코드(ex:K7006037) prdtId ==> modelId
        var instNom	= $('input[name="instNom"]:checked').val()	!= undefined ? $('input[name="instNom"]:checked').val() : ''; //약정유형
        var rateCd= $('#socCode').val() != undefined ? $('#socCode').val() : '';											//요금제코드(ex:PL20B9416)

        var shareUrl = location.pathname + '?prodId=' + prodId + '&operType=' + operType + '&hndsetModelId=' + modelId + '&rateCd=' + rateCd  + '&instNom=' + instNom ;

        //https://www.ktmmobile.com/appForm/appFormDesignView.do?prodId=4770&hndsetModelId=K7035913&rateCd=KISUSEV14&instNom=24&operType

        return shareUrl;
    }
    // facebook 공유하기
    function facebookShare(){
        //facebook_share(document.location.href);
        $("#meta_og_url").attr("content", location.origin + getShareUrl());
        facebook_share(getShareUrl());
    }
    // kakaostory 공유하기
    function kakaoShare(){
        //kakaostory_share(document.location.href);
        //kakao_share($('title').text(), getShareUrl());
        $("#meta_og_url").attr("content", location.origin + getShareUrl());
        kakao_share('/resources/images/portal/common/share.png', getShareUrl(), getShareUrl());
    }
    // Line 공유하기
    function lineShare(){
        //line_share(document.location.href);
        //line_share(getShareUrl());
        $("#meta_og_url").attr("content", location.origin + getShareUrl());
        line_share($('title').text(), getShareUrl());
    }

    function copyUrl(){
        var url = location.origin + getShareUrl();
        var input = document.createElement("input");
        document.body.appendChild(input);
        input.value = url;
        input.select();
        document.execCommand("copy");
        document.body.removeChild(input);
        MCP.alert('URL이 복사되었습니다.');
    }

var isEmpty = function(value){
    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return true
    }else{
        return false
    }
};


// 이벤트코드 검증
var checkEvntCdPrmt = function(){

    var rateCd = ajaxCommon.isNullNvl($('#socCode').val(), "");
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
            $("#evntCdPrmtSave").val($("#evntCdPrmt").val());
            $("#eventCdName").val(evntCdPrmt);
            $("#eventExposedText").val(jsonObj.getEventVal.exposedText);
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

var evntCdPrmtAreaYn = function(rateCd){

    var varData = ajaxCommon.getSerializedData({
        rateCd : rateCd
    });

    ajaxCommon.getItem({
        id:'giftBasListYnWithEvntCdAjax'
        ,cache:false
        ,url:'/benefit/giftBasListYnWithEvntCdAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(jsonObj){
        if (jsonObj.eventRateYn == "Y") {
            $("#evntCdPrmtWrap").show();
        }else{
            $("#evntCdPrmtWrap").hide();

        }
    });

}

//이벤트코드 재입력
function tryEvntCdPrmt(){
    $("#evntCdPrmt").prop("disabled", false);
    $("#isEvntCdPrmtAuth").val("");
    $("#evntCdPrmt").val("");
    $("#eventCdDiv").hide();
    $("#evntCdTryBtn").addClass('is-disabled');
    $("#evntCdPrmtBtn").removeClass('is-disabled');
    $("#eventCdName").val("");
}