;


VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.certifyYn = "SMS인증 후 가능한 서비스입니다.";
VALIDATE_NOT_EMPTY_MSG.chkAgreeA = "개인정보약관에 동의해주세요.";
VALIDATE_NOT_EMPTY_MSG.chkMstoreAgree = "개인정보 제 3자 제공에 동의해주세요.";
VALIDATE_NOT_EMPTY_MSG.openMthdCd_01 = "친구에게 추천해 주고 싶은 개통 방법은<br> 무엇인가요?";
VALIDATE_NOT_EMPTY_MSG.rateComparison1 = "친구에게 맞는 요금제 3개를 선택해 주세요!";
VALIDATE_NOT_EMPTY_MSG.rateComparison2 = "친구에게 맞는 요금제 3개를 선택해 주세요!";
VALIDATE_NOT_EMPTY_MSG.rateComparison3 = "친구에게 맞는 요금제 3개를 선택해 주세요!";


VALIDATE_COMPARE_MSG = {};
VALIDATE_COMPARE_MSG.rateComparison1 = "친구에게 맞는 요금제 3개 중 동일한<br> 요금제 정보가 포함되어 있습니다.";



var interval ;



var cnt = 180;
var timer;

window.pageObj = window.pageObj || {};

Object.assign(window.pageObj, {
    eventId: "",
    allRateList: [],
    selRateList: [],
    temRateList: [],
    rateRateListVal: ['A'],
    rateGigtListVal: ['A'],
    rateDataVal: '',
    rateCallVal: '',
    rateSmsVal: '',
    ratePriceMinVal: 0,
    ratePriceMaxVal: 100,
    rateDataMinVal: 0,
    rateDataMaxVal: 100,
    ratePriceMin: 0,
    ratePriceMax: 100,
    rateDataMin: 0,
    rateDataMax: 100,
    allRateCnt: 0,
    temRateCnt: 0,
    selectDomVal: ["", "", ""]
    ,commendId:""
    ,commendNm:""
    ,commendObj:{}
});

window.pageObj.rateReady = window.pageObj.rateReady || new Promise((resolve, reject) => {
    window.pageObj._resolveRateReady = resolve;
    window.pageObj._rejectRateReady = reject;
});

//rsliders 에서 가격 맵핑
const priceMap = new Map([
    ["0", 0]
    ,["20", 5000]
    ,["40", 10000]
    ,["60", 20000]
    ,["80", 30000]
    ,["100", 1000000]
]);

const dataMap = new Map([
    ["0", 0]
    ,["20", 7]
    ,["40", 10]
    ,["60", 15]
    ,["80", 100]
    ,["100", 10000]
]);



$(document).ready(function(){

    function extractNumber(str) {
        if (!str) return NaN;
        // 쉼표와 원 문자 제거하고 숫자 파싱
        return parseFloat(String(str).replace(/[^0-9.]/g, ''));
    }


    const setSelect = function(rset) {

        $('#rateComparison1, #rateComparison2, #rateComparison3').empty().append(`<option value="">요금제를 선택해주세요</option>\n`);
        let spCnt =0 ; // 요금제 건수
        pageObj.selRateList.forEach(function(rateGroupItem){
            if (rateGroupItem.rateDtlList.length > 0) {
                $('#rateComparison1, #rateComparison2, #rateComparison3').append(`<optgroup label="${rateGroupItem.rateAdsvcCtgNm}">\n`);
                rateGroupItem.rateDtlList.forEach(rateDtl => {
                    $('#rateComparison1, #rateComparison2, #rateComparison3').append(`<option value="${rateDtl.rateAdsvcCd}" >${rateDtl.rateAdsvcNm}</option>`);
                    $('#rateComparison1').find("option").last().data(rateDtl);
                    $('#rateComparison2').find("option").last().data(rateDtl);
                    $('#rateComparison3').find("option").last().data(rateDtl);
                    spCnt++;
                });
            }
        });
        $('#spCnt').html(spCnt);
    }

    $("#btnFilter").click(function(){
        var el = document.querySelector('#rateComparisonModal');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

        $('[data-target="section1"]').trigger("click");
        //$("[name=rateComparisonRateGroup][value='"+pageObj.rateRateVal+"']").prop("checked","checked");

        $("[name=rateComparisonRateGroup]").each(function() {
            if (pageObj.rateRateListVal.includes($(this).val())) {
                $(this).prop("checked", true);
            } else {
                $(this).prop("checked", false); // 필요하면 해제
            }
        });

        $("[name=rateComparisonGiftGroup]").each(function() {
            if (pageObj.rateGigtListVal.includes($(this).val())) {
                $(this).prop("checked", true);
            } else {
                $(this).prop("checked", false); // 필요하면 해제
            }
        });
        $("[name=rateComparisonDataGroup][value='"+pageObj.rateDataVal+"']").prop("checked","checked");
        $("[name=rateComparisonCallGroup][value='"+pageObj.rateCallVal+"']").prop("checked","checked");
        $("[name=rateComparisonSmsGroup][value='"+pageObj.rateSmsVal+"']").prop("checked","checked");

        if ("1"== pageObj.rateDataVal) {
            $("#divDataSlider").show();
            //window.KTM.RangeSliderMulti.getInstances()[0]._updateValues(pageObj.rateDataMinVal, pageObj.rateDataMaxVal);

            const sliderElement = window.KTM.RangeSliderMulti.getInstances()[0]._element; // 슬라이더 요소 선택
            const minInput = sliderElement.querySelector('input[type="range"]:nth-of-type(1)');
            const maxInput = sliderElement.querySelector('input[type="range"]:nth-of-type(2)');

            minInput.value = pageObj.rateDataMinVal; // 최소값 변경
            maxInput.value = pageObj.rateDataMaxVal; // 최대값 변경

            minInput.dispatchEvent(new Event('input', { bubbles: true }));
            maxInput.dispatchEvent(new Event('input', { bubbles: true }));
        } else {
            $("#divDataSlider").hide();
        }
        //window.KTM.RangeSliderMulti.getInstances()[1]._updateValues(pageObj.ratePriceMinVal, pageObj.ratePriceMaxVal);  //setFilter(); 호출...


        const sliderElement2 = window.KTM.RangeSliderMulti.getInstances()[1]._element; // 슬라이더 요소 선택
        const minInput2 = sliderElement2.querySelector('input[type="range"]:nth-of-type(1)');
        const maxInput2 = sliderElement2.querySelector('input[type="range"]:nth-of-type(2)');

        minInput2.value = pageObj.ratePriceMinVal; // 최소값 변경
        maxInput2.value = pageObj.ratePriceMaxVal; // 최대값 변경

        minInput2.dispatchEvent(new Event('input', { bubbles: true }));
        maxInput2.dispatchEvent(new Event('input', { bubbles: true }));




    });

    $("#btnReset").click(function(){
        pageObj.rateRateListVal = ['A'];
        pageObj.rateDataVal = "";
        pageObj.rateCallVal = "";
        pageObj.rateSmsVal = "";
        pageObj.ratePriceMinVal = 0;
        pageObj.ratePriceMaxVal =100;
        pageObj.rateGigtListVal= ['A'];
        pageObj.rateDataMinVal = 0;
        pageObj.rateDataMaxVal = 100;
        pageObj.selRateList = pageObj.allRateList ;
        $("#btnFilter").removeClass("is-active");
        setSelect('Y');

    });


    $("#btnSetList").click(function(){
        if (pageObj.temRateCnt === 0) {
            MCP.alert('조건에 맞는 결과가 없어요.');
            return ;
        }

        pageObj.rateRateListVal = $("[name=rateComparisonRateGroup]:checked").map(function() {
            return $(this).val();
        }).get();

        pageObj.rateGigtListVal = $("[name=rateComparisonGiftGroup]:checked").map(function() {
            return $(this).val();
        }).get();

        pageObj.selRateList = pageObj.temRateList ;
        pageObj.rateDataVal = $("[name=rateComparisonDataGroup]:checked").val();
        pageObj.rateCallVal = $("[name=rateComparisonCallGroup]:checked").val();
        pageObj.rateSmsVal = $("[name=rateComparisonSmsGroup]:checked").val();
        pageObj.ratePriceMinVal = pageObj.ratePriceMin;
        pageObj.ratePriceMaxVal = pageObj.ratePriceMax;
        pageObj.rateDataMinVal = pageObj.rateDataMin;
        pageObj.rateDataMaxVal = pageObj.rateDataMax;

        setSelect('N');

        if ($("#rateComparison1 option[value='" + pageObj.selectDomVal[0] + "']").length > 0) {
            $("#rateComparison1").val(pageObj.selectDomVal[0]).trigger("change");
        } else {
            $('#rateComparison1').eq(0).trigger("change");
        }

        if ($("#rateComparison2 option[value='" + pageObj.selectDomVal[1] + "']").length > 0) {
            $("#rateComparison2").val(pageObj.selectDomVal[1]).trigger("change");
        } else {
            $('#rateComparison2').eq(0).trigger("change");
        }

        if (pageObj.allRateCnt != pageObj.temRateCnt) {
            $("#btnFilter").addClass("is-active");
        } else {
            $("#btnFilter").removeClass("is-active");
        }
        KTM.Dialog.closeAll();
    });


    $(document).on("ui.rsliders.change", "#section2 .c-range.multi", function(e){
        pageObj.rateDataMin =  e.value[0] ;
        pageObj.rateDataMax =  e.value[1] ;
        //console.log("minVal["+pageObj.rateDataMin+"] maxVal["+pageObj.rateDataMax+"] ");

        $("[name=rateComparisonInfinityDataGroup]").each(function(index){
            let minBtnVal =  parseInt( $(this).data("minVal"),10) ;
            let maxBtnVal =  parseInt( $(this).data("maxVal"),10) ;
            if (pageObj.rateDataMin == minBtnVal &&  pageObj.rateDataMax == maxBtnVal) {
                $(this).prop("checked",true)
            } else {
                $(this).prop("checked",false)
            }
        });

        setFilter();
    });


    $("[name=rateComparisonInfinityDataGroup]").click(function() {
        let minVal =  parseInt( $(this).data("minVal"),10) ;
        let maxVal =  parseInt( $(this).data("maxVal"),10) ;
        //console.log("minVal["+minVal+"] maxVal["+maxVal+"] ");
        //window.KTM.RangeSliderMulti.getInstances()[0]._updateValues(minVal, maxVal);  //setFilter(); 호출...

        const sliderElement = window.KTM.RangeSliderMulti.getInstances()[0]._element; // 슬라이더 요소 선택
        const minInput = sliderElement.querySelector('input[type="range"]:nth-of-type(1)');
        const maxInput = sliderElement.querySelector('input[type="range"]:nth-of-type(2)');

        minInput.value = minVal; // 최소값 변경
        maxInput.value = maxVal; // 최대값 변경

        minInput.dispatchEvent(new Event('input', { bubbles: true }));
        maxInput.dispatchEvent(new Event('input', { bubbles: true }));
    });



    $(document).on("ui.rsliders.change", "#section5 .c-range.multi", function(e){
        pageObj.ratePriceMin =  e.value[0] ;
        pageObj.ratePriceMax =  e.value[1] ;

        //console.log("minVal["+pageObj.ratePriceMin +"] maxVal["+pageObj.ratePriceMax+"] ");
        $("[name=rateComparisonPriceGroup]").each(function(index){
            let minBtnVal =  parseInt( $(this).data("minVal"),10) ;
            let maxBtnVal =  parseInt( $(this).data("maxVal"),10) ;
            if (pageObj.ratePriceMin == minBtnVal &&  pageObj.ratePriceMax == maxBtnVal) {
                $(this).prop("checked",true)
            } else {
                $(this).prop("checked",false)
            }
        });

        setFilter();
    });


    $("[name=rateComparisonPriceGroup]").click(function() {
        let minVal =  parseInt( $(this).data("minVal"),10) ;
        let maxVal =  parseInt( $(this).data("maxVal"),10) ;
        //window.KTM.RangeSliderMulti.getInstances()[1]._updateValues(minVal, maxVal);
        //window.KTM.RangeSliderMulti.getInstances()[1]._element
        const sliderElement = window.KTM.RangeSliderMulti.getInstances()[1]._element; // 슬라이더 요소 선택
        const minInput = sliderElement.querySelector('input[type="range"]:nth-of-type(1)');
        const maxInput = sliderElement.querySelector('input[type="range"]:nth-of-type(2)');

        minInput.value = minVal; // 최소값 변경
        maxInput.value = maxVal; // 최대값 변경

        minInput.dispatchEvent(new Event('input', { bubbles: true }));
        maxInput.dispatchEvent(new Event('input', { bubbles: true }));
    });

    $("[name=rateComparisonCallGroup],[name=rateComparisonSmsGroup] ").click(function() {
        setFilter();
    });



    $("[name=rateComparisonRateGroup]").click(function() {
        let thisVal =  $(this).val();
        if ("A"==thisVal) {
            $("[name=rateComparisonRateGroup]:not(:first)").prop("checked", false);
        } else {
            $("[name=rateComparisonRateGroup]:first").prop("checked", false);
        }

        let checkedCount = $("[name=rateComparisonRateGroup]:checked").length;

        if (0 == checkedCount || 4 ==checkedCount ) {
            $("[name=rateComparisonRateGroup]:not(:first)").prop("checked", false);
            $("[name=rateComparisonRateGroup]:first").prop("checked", true);
        }

        setFilter();
        $('[data-target="section2"]').trigger("click");
    });

    $("[name=rateComparisonGiftGroup]").click(function() {
        let thisVal =  $(this).val();
        if ("A"==thisVal) {
            $("[name=rateComparisonGiftGroup]:not(:first)").prop("checked", false);
        } else {
            $("[name=rateComparisonGiftGroup]:first").prop("checked", false);
        }

        let checkedCount = $("[name=rateComparisonGiftGroup]:checked").length;

        if (0 == checkedCount  ) {
            $("[name=rateComparisonGiftGroup]:not(:first)").prop("checked", false);
            $("[name=rateComparisonGiftGroup]:first").prop("checked", true);
        }

        setFilter();
    });

    $("[name=rateComparisonDataGroup]").click(function() {
        let thisVal = $(this).val()
        if ("1" == thisVal) {
            $("#divDataSlider").show();
        } else {
            $("#divDataSlider").hide();
        }
        setFilter();
        $('[data-target="section3"]').trigger("click");
    });

    const setFilter = function() {


        pageObj.temRateList = JSON.parse(JSON.stringify(pageObj.allRateList));

        //요금제
        let checkedValues = $("[name=rateComparisonRateGroup]:checked").map(function() {
            return $(this).val();
        }).get();

        if (!checkedValues.includes("A")) {
            //연령특화  <=== 이것만 특히 한것..
            if (checkedValues.includes("99")) {
                if (checkedValues.length > 1) {
                    pageObj.temRateList = pageObj.temRateList.filter(rateObj => {
                        const isIncludesCtgValid = checkedValues.includes(rateObj.upRateAdsvcCtgCd);
                        let isRateAdsvcValid = false;

                        // 또는 조건 이기 때문에 ture 이면 필터 할 필요 없음
                        // false 조건에서 연령 특화(05) 존재 하며 필터 처리후 삭제 하지 않음
                        if (!isIncludesCtgValid && Array.isArray(rateObj.rateDtlList)) {
                            rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => rateDtl.rateAdsvcGdncBas.rateCtg.split(",").includes("05"));
                            if (rateObj.rateDtlList.length  > 0) {
                                isRateAdsvcValid = true;
                            }
                        }
                        return isIncludesCtgValid || isRateAdsvcValid;
                    });
                } else {
                    pageObj.temRateList.forEach(rateObj => {
                        // rateDtlList가 존재하고 배열인지 확인
                        if (!Array.isArray(rateObj.rateDtlList)) {
                            return; // 존재하지 않으면 처리하지 않음
                        }
                        rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => rateDtl.rateAdsvcGdncBas.rateCtg.split(",").includes("05"));
                    });
                }
            } else {
                pageObj.temRateList = pageObj.temRateList.filter(rateObj =>
                    checkedValues.includes(rateObj.upRateAdsvcCtgCd)
                );
            }
        }

        //Data
        const $rateComparisonData = $("[name=rateComparisonDataGroup]:checked");
        let dateFilterVal = $rateComparisonData.val();
        if (dateFilterVal != "") {
            const gigabyteCnt = 1048576;

            if ("1" != dateFilterVal) {
                let minVal =  $rateComparisonData.data("minVal");
                let maxVal =  $rateComparisonData.data("maxVal");
                let minDataVal = 0;
                let maxDataVal = 0;

                if (minVal == "999") {
                    minDataVal = 999999999 ;
                } else {
                    minDataVal = parseInt(minVal,10) * gigabyteCnt;
                }

                if (maxVal == "999") {
                    maxDataVal = 999999999 ;
                } else {
                    maxDataVal = parseInt(maxVal,10) * gigabyteCnt;
                }
                // data-rate-ctg 01 아닌것..
                pageObj.temRateList.forEach(rateObj => {
                    // rateDtlList가 존재하고 배열인지 확인
                    if (!Array.isArray(rateObj.rateDtlList)) {
                        return; // 존재하지 않으면 처리하지 않음
                    }
                    rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                        // 1. rateCtg가 "01"을 포함하지 않거나, null 또는 undefined인 경우 포함
                        const isCtgValid = !rateDtl.rateAdsvcGdncBas.rateCtg ||
                            !rateDtl.rateAdsvcGdncBas.rateCtg.split(",").includes("01");

                        // 2. rateAdsvcItemDesc가 존재하고 숫자로 변환 가능하며, 범위 내에 있는 경우 포함
                        const rawValue = rateDtl.rateAdsvcBnfitGdncMap.RATEBE21?.rateAdsvcItemDesc;
                        const thisDataVal = Number(rawValue);

                        const isValueValid = !isNaN(thisDataVal) && thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                        return isCtgValid && isValueValid;
                    });
                });
            } else {
                // data-rate-ctg 01 아닌것..
                /*
                   <input type="checkbox" id="unlimited" name="rateCheck" class=" width_25" value="01">
                    <label for="newSticker" class="fontBold"> 무제한</label>
                 */
                let minDataVal = 0;
                let maxDataVal = 0;

                minDataVal = dataMap.get(String(pageObj.rateDataMin)) * gigabyteCnt;
                maxDataVal = dataMap.get(String(pageObj.rateDataMax)) * gigabyteCnt;

                pageObj.temRateList.forEach(rateObj => {
                    // rateDtlList가 존재하고 배열인지 확인
                    if (!Array.isArray(rateObj.rateDtlList)) {
                        return; // 존재하지 않으면 처리하지 않음
                    }
                    rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                        const isCtgValid = rateDtl.rateAdsvcGdncBas.rateCtg.split(",").includes("01");

                        // 2. rateAdsvcItemDesc가 존재하고 숫자로 변환 가능하며, 범위 내에 있는 경우 포함
                        const rawValue = rateDtl.rateAdsvcBnfitGdncMap.RATEBE21?.rateAdsvcItemDesc;
                        const thisDataVal = Number(rawValue);

                        const isValueValid = !isNaN(thisDataVal) && thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                        return isCtgValid && isValueValid;

                    })
                });
            }

        }

        //통화
        const $rateComparisonCall = $("[name=rateComparisonCallGroup]:checked");
        $("[name=rateComparisonCallGroup]").click(function() {
            $('[data-target="section4"]').trigger("click");
        });
        let callFilterVal = $rateComparisonCall.val()
        if (callFilterVal != "") {
            const minuteCnt = 60;
            let minVal =  $rateComparisonCall.data("minVal");
            let maxVal =  $rateComparisonCall.data("maxVal");
            let minDataVal = 0;
            let maxDataVal = 0;

            if (minVal == "999") {
                minDataVal = 999999999 ;
            } else {
                minDataVal = parseInt(minVal,10) * minuteCnt;
            }

            if (maxVal == "999") {
                maxDataVal = 999999999 ;
            } else {
                maxDataVal = parseInt(maxVal,10) * minuteCnt;
            }

            pageObj.temRateList.forEach(rateObj => {
                // rateDtlList가 존재하고 배열인지 확인
                if (!Array.isArray(rateObj.rateDtlList)) {
                    return; // 존재하지 않으면 처리하지 않음
                }

                rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                    // rateAdsvcItemDesc가 존재하는지 확인
                    if (rateDtl.rateAdsvcBnfitGdncMap.RATEBE22?.rateAdsvcItemDesc) {
                        // rateAdsvcItemDesc가 존재하는지 확인
                        let rawValue = rateDtl.rateAdsvcBnfitGdncMap.RATEBE22?.rateAdsvcItemDesc;

                        // 숫자로 변환 가능한지 확인
                        let thisDataVal = Number(rawValue); // NaN 방지

                        if (isNaN(thisDataVal)) { return false;  }

                        // 최소값~최대값 범위에 있는 경우 유지, 그렇지 않으면 삭제
                        return thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                    }
                    // rateAdsvcItemDesc가 없으면 삭제
                    return false;
                });
            });
            $('[data-target="section4"]').trigger("click");
        }


        //문자
        const $rateComparisonSms = $("[name=rateComparisonSmsGroup]:checked");
        let smsFilterVal = $rateComparisonSms.val();
        $("[name=rateComparisonSmsGroup]").click(function() {
            $('[data-target="section5"]').trigger("click");
        });


        if (smsFilterVal != "") {
            let minVal =  $rateComparisonSms.data("minVal");
            let maxVal =  $rateComparisonSms.data("maxVal");
            let minDataVal = 0;
            let maxDataVal = 0;

            if (minVal == "999") {
                minDataVal = 999999999 ;
            } else {
                minDataVal = parseInt(minVal,10) ;
            }

            if (maxVal == "999") {
                maxDataVal = 999999999 ;
            } else {
                maxDataVal = parseInt(maxVal,10) ;
            }

            pageObj.temRateList.forEach(rateObj => {
                // rateDtlList가 존재하고 배열인지 확인
                if (!Array.isArray(rateObj.rateDtlList)) {
                    return; // 존재하지 않으면 처리하지 않음
                }

                rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                    // rateAdsvcItemDesc가 존재하는지 확인
                    if (rateDtl.rateAdsvcBnfitGdncMap.RATEBE23?.rateAdsvcItemDesc) {
                        // rateAdsvcItemDesc가 존재하는지 확인
                        let rawValue = rateDtl.rateAdsvcBnfitGdncMap.RATEBE23?.rateAdsvcItemDesc;

                        // 숫자로 변환 가능한지 확인
                        let thisDataVal = Number(rawValue); // NaN 방지

                        if (isNaN(thisDataVal)) { return false;  }


                        // 최소값~최대값 범위에 있는 경우 유지, 그렇지 않으면 삭제
                        return thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                    }
                    // rateAdsvcItemDesc가 없으면 삭제
                    return false;
                });
            });
            $('[data-target="section5"]').trigger("click");
        }
        $("[name=rateComparisonPriceGroup]").click(function() {
            $('[data-target="section6"]').trigger("click");
        });
        //가격 obj.rateAdsvcGdncBas.promotionAmtVatDesc
        //console.log(" pageObj.ratePriceMin["+pageObj.ratePriceMin+"]  pageObj.ratePriceMax["+pageObj.ratePriceMax+"] ");

        if (pageObj.ratePriceMin != 0 || pageObj.ratePriceMax != 100 ) {

            let minDataVal = priceMap.get(pageObj.ratePriceMin);
            let maxDataVal = priceMap.get(pageObj.ratePriceMax);


            pageObj.temRateList.forEach(rateObj => {
                // rateDtlList가 존재하고 배열인지 확인
                if (!Array.isArray(rateObj.rateDtlList)) {
                    return; // 존재하지 않으면 처리하지 않음
                }

                rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                    // rateAdsvcItemDesc가 존재하는지 확인
                    let rawValue = rateDtl.rateAdsvcGdncBas.promotionAmtVatDesc.replace(/,/g, "");;

                    // 숫자로 변환 가능한지 확인
                    let thisDataVal = Number(rawValue); // NaN 방지
                    if (isNaN(thisDataVal)) {
                        return false; // 숫자로 변환되지 않으면 삭제
                    }
                    //console.log("[RATE] minDataVal["+minDataVal+"] thisDataVal["+thisDataVal+"] maxDataVal["+maxDataVal+"] ");

                    return thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                    // rateAdsvcItemDesc가 없으면 삭제
                    return false;
                });
            });
        }

        //추가 혜택
        const $rateComparisonGift = $("[name=rateComparisonGiftGroup]:checked");
        let giftFilterVal = $rateComparisonGift.val();


        let checkedGiftValues = $("[name=rateComparisonGiftGroup]:checked").map(function() {
            return $(this).val();
        }).get();

        if (!checkedGiftValues.includes("A")) {
            if (checkedGiftValues.includes("07")) { //데이터 함께쓰기 주기 , 받기
                checkedGiftValues.push('08');
            }

            pageObj.temRateList.forEach(rateObj => {
                if (!Array.isArray(rateObj.rateDtlList)) {
                    return;
                }

                rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                    // checkedGiftValues 배열 중 하나라도 rateGdncPropertyMap에 존재하는지 확인
                    return checkedGiftValues.some(giftVal => rateDtl.rateGdncPropertyMap[giftVal]?.propertyCode);
                });
            });
        }

        countTemRate();
    }

    const countTemRate = function () {
        pageObj.temRateCnt = pageObj.temRateList.reduce((sum, rateObj) => sum + rateObj.rateDtlList.length, 0);
        $('#btnSetList').html(`총 <span>${pageObj.temRateCnt}</span>건 적용`);
    }


    const buttons = document.querySelectorAll('.review-filter .c-filter__inner button');
    const sections = document.querySelectorAll('section');

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const targetId = button.getAttribute('data-target');
            const targetSection = document.getElementById(targetId);
            targetSection.scrollIntoView({ behavior: 'smooth' });
        });
    });

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const activeButton = document.querySelector(`.review-filter .c-filter__inner button[data-target="${entry.target.id}"]`);

                buttons.forEach(btn => btn.classList.remove('is-active'));
                activeButton.classList.add('is-active');
            }
        });
    }, {
        threshold: 0.5 // 섹션이 70% 보일 때 활성화
    });

    sections.forEach(section => observer.observe(section));



    // 요금제 SELECT BOX 함수 끝!!


    const $linkType = $('input[name="linkTypeCd"]');

    $linkType.on('change', function () {
        const isType01 = this.value === "01";
        if (isType01) {
            const linkTypeCd = pageObj.commendObj?.linkTypeCd;
            if ( linkTypeCd === "99") {
                MCP.alert("[기본추천 링크]로 변경이 지원되지 않습니다.");
                $linkType.filter("[value='99']").prop("checked", true);
                return ;
            }
        }
        $("#divLinkType_02").toggle(!isType01);
    });

    $("#btnLinkCopy").click(function(){
        const value = document.getElementById("recommend").value;
        const pageType = $(this).data("page");

        if (value === "") {
            if (pageType != null && "mng" == pageType) {
                MCP.alert("복사할 정보가 없습니다. ");
                return;
            } else {
                MCP.alert("복사할 정보가 없습니다. <br/>SMS인증 후 가능한 서비스입니다.");
                return;
            }
        }

        if (navigator.clipboard) {
            navigator.clipboard.writeText(value);
        } else {
            const input = document.getElementById("recommend");
            input.select();
            document.execCommand("copy");
        }

        MCP.alert("친구초대 링크를 복사했습니다.");
    });

    $("#btnfrndKakaoShare").click(function(){

        if (pageObj.commendId ==="") {
            MCP.alert("공유할 정보가 없습니다. <br/>SMS인증 후 가능한 서비스입니다.");
            return;
        }
        if(!Kakao.isInitialized()){
            Kakao.init('e302056d1b213cb21f683504be594f25');
        }

        const linkTypeCd = pageObj.commendObj?.linkTypeCd;
        if ( linkTypeCd === "99") {
            Kakao.Link.sendCustom({
                templateId: 130343
                ,templateArgs: {
                    description: pageObj.commendId
                    ,inviteuser: pageObj.commendNm
                },
            });
        } else {
            Kakao.Link.sendCustom({
                templateId: 74824
                ,templateArgs: {
                    description: pageObj.commendId
                },
            });
        }
    });

    $("#btnfrndUpdate").click(function(){
        validator.config={};
        validator.config['certifyYn'] = 'isNonEmptyAndCheckY';
        validator.config['chkAgreeA'] = 'isNonEmpty';
        validator.config['chkMstoreAgree'] = 'isNonEmpty';

        var linkTypeCd = $('input[name="linkTypeCd"]:checked').val() || "";
        if ("99" === linkTypeCd) {
            validator.config['openMthdCd_01'] = 'isNonEmpty';
            validator.config['rateComparison1&rateComparison2&rateComparison3'] = 'isIsNotEqualsTriple';
        }
        //name="linkTypeCd"

        if (validator.validate()) {

            if (pageObj.commendId != "") {
                KTM.Confirm('친구초대 정보를<br/> 변경하시겠습니까?'
                    ,function () {
                        this.close();
                        frndRecommendPrd();
                    }
                    ,function(){
                        this.close();
                    }
                );
            } else {
                KTM.Confirm('친구초대 정보를<br/> 저장하시겠습니까?'
                    ,function () {
                        this.close();
                        frndRecommendPrd();
                    }
                    ,function(){
                        this.close();
                    }
                );
            }

        } else {
            MCP.alert(validator.getErrorMsg());
        }
    });

    var frndRecommendPrd = function() {
        var linkTypeCd = $('input[name="linkTypeCd"]:checked').val() || "";
        var openMthdCd,commendSocCode01, commendSocCode02,commendSocCode03;

        if ("01" === linkTypeCd) {
            openMthdCd ="";
            commendSocCode01 = "";
            commendSocCode02 = "";
            commendSocCode03 = "";
        } else {
            openMthdCd =$('input[name="openMthdCd"]:checked').val() || "";
            commendSocCode01 = $('#rateComparison1').val();
            commendSocCode02 = $('#rateComparison2').val();
            commendSocCode03 = $('#rateComparison3').val();
        }
        var varData = ajaxCommon.getSerializedData({
            name:$("#custNm").val()
            ,phone:$("#phoneNum").val()
            ,mstoreTermAgree:$("#chkMstoreAgree").is(":checked") ? "Y" : "N"
            ,linkTypeCd:linkTypeCd
            ,openMthdCd:openMthdCd
            ,commendSocCode01:commendSocCode01
            ,commendSocCode02:commendSocCode02
            ,commendSocCode03:commendSocCode03
        });

        ajaxCommon.getItem({
                id:'frndSendAjax'
                ,cache:false
                ,url:'/event/frndRecommendPrdAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(data){
                fn_hideLoading();

                if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){  // STEP 오류
                    $("input[name='recommend']").val(data.RESULT_MSG);
                    return false;
                }

                pageObj.commendId = "";
                pageObj.commendNm = "";
                pageObj.commendObj = {};

                if( data.RESULT_CODE =="00000" ){
                    $("#divRecommend").show();
                    $("input[name='recommend']").val(data.LINK_URL);
                    pageObj.commendObj = data.COMMEND_OBJ;
                    pageObj.commendId = data.COMMEND_OBJ.commendId;
                    pageObj.commendNm = data.COMMEND_NM;

                    const linkTypeCd = pageObj.commendObj?.linkTypeCd;
                    if ( linkTypeCd === "99") {
                        $("input[name='linkTypeCd'][value='01']").prop("disabled", true);
                    }

                } else if(data.RESULT_CODE =="00001"){
                    MCP.alert("휴대폰 번호가 다릅니다. 다시 인증해 주세요.");
                    return false;
                } else if(data.RESULT_CODE =="00002"){
                    MCP.alert("다시 인증해 주세요.");
                    return false;
                } else if(data.RESULT_CODE =="00003"){
                    MCP.alert("친구추천 이벤트는 kt M모바일 고객인 경우에만 이용 가능합니다.");
                    return false;
                } else if(data.RESULT_CODE =="00004"){
                    MCP.alert("요청하신 번호는 이벤트 참여가 불가합니다.");
                    return false;
                } else if(data.RESULT_CODE =="00005"){
                    MCP.alert("추천 아이디 생성에 실패 했습니다.");
                    return false;
                } else if(data.RESULT_CODE =="00009"){
                    MCP.alert("개인정보 제 3자 제공에 동의해주세요.");
                    return false;
                } else if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){ // STEP 오류
                    MCP.alert(data.RESULT_MSG);
                    return false;
                } else {
                    MCP.alert("문제가 발생 하였습니다.<br>잠시후 다시 시도 하시기 바랍니다.");
                    return false;
                }
            });
    };




    const getCtgList = function () {
        var varData = ajaxCommon.getSerializedData({
            rateAdsvcDivCd:"RATE"
        });

        ajaxCommon.getItemNoLoading({
            id:'getCtgList'
            ,cache:false
            ,url:'/rate/getCtgRateAllListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            pageObj.allRateList = jsonObj;
            pageObj.selRateList = jsonObj;
            pageObj.allRateCnt =  pageObj.allRateList.reduce((sum, rateObj) => sum + rateObj.rateDtlList.length, 0);
            setSelect('Y');
            pageObj._resolveRateReady(jsonObj);   //  준비 완료 신호
        });
    } ();


    getBannerList();
});

        //배너 호출 ajax
function getBannerList() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg: 'E002'
    });

    ajaxCommon.getItem({
        id: 'bannerListAjax',
        cache: false,
        async: false,
        url: '/m/bannerListAjax.do',
        data: varData,
        dataType: "json"
    }, function(result) {
        if (result.result.length > 0) {
            for (var i = 0; i < 2; i++) {
                var item = result.result[i];
                var html = "";

                html += "<li class='friend-banner-list__item' style='background-color:" + item.bgColor + "'>";
                html += "<a href='javascript:void(0);' " +
                            "addr='" + item.mobileLinkUrl + "' " +
                            "target-type='" + item.linkTarget + "' " +
                            "data-key='" + item.bannSeq + "' " +
                            "data-ctg='" + item.bannCtg + "'>";
                html += "<img src='" + item.mobileBannImgNm + "' alt='" + item.imgDesc + "'>";
                html += "</a>";
                html += "</li>";

                $(".friend-banner-list").append(html);
            }

            $(".friend-banner-list a").on('click', function(e) {
                e.preventDefault();

                var addr = $(this).attr("addr");
                var target = $(this).attr("target-type");
                var key = $(this).attr("data-key");
                var ctg = $(this).attr("data-ctg");

                insertBannAccess(key, ctg);

                if (target === 'P') {
                    var parameterData = ajaxCommon.getSerializedData({
                        eventPopTitle: '친구초대 이벤트'
                    });
                    $("#eventPop").remove();
                    openPage('eventPop', addr, parameterData);
                } else if (target === 'Y') {
                    window.open(addr, '_blank');
                } else {
                    location.href = addr;
                }
            });
        }
    });
}



/**
 * 약관 팝업 클릭 시 eventId 세팅
 */
var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

/**
 * 약관 동의 후 닫기
 */
var targetTermsAgree= function() {
    $('#' + pageObj.eventId).prop('checked', 'checked');
};


function btn_reg() {
    var varData = ajaxCommon.getSerializedData({
        name:$("#custNm").val()
        ,phone:$("#phoneNum").val()
    });

    ajaxCommon.getItem({
            id:'frndSendAjax'
            ,cache:false
            ,url:'/event/getFrndRecommendAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(data){
            fn_hideLoading();

            if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){  // STEP 오류
                $("input[name='recommend']").val(data.RESULT_MSG);
                return false;
            }

            if( data.RESULT_CODE =="00000" ){
                if (data.COMMEND_OBJ != null && data.COMMEND_OBJ !== "" && data.COMMEND_OBJ !== "null") {
                    $("#divRecommend").show();
                    pageObj.commendObj = data.COMMEND_OBJ;
                    pageObj.commendId = data.COMMEND_OBJ.commendId;
                    pageObj.commendNm = data.COMMEND_NM;

                    const linkTypeCd = pageObj.commendObj?.linkTypeCd;
                    if ( linkTypeCd === "99") {
                        $("input[name='linkTypeCd'][value='01']").prop("disabled", true);
                    }


                    $(`input[name='linkTypeCd'][value='${data.COMMEND_OBJ.linkTypeCd}']`).prop("checked", true).trigger("change"); // 필요하면
                    $(`input[name='openMthdCd'][value='${data.COMMEND_OBJ.openMthdCd}']`).prop("checked", true);
                    $("#rateComparison1").val(data.COMMEND_OBJ.commendSocCode01);
                    $("#rateComparison2").val(data.COMMEND_OBJ.commendSocCode02);
                    $("#rateComparison3").val(data.COMMEND_OBJ.commendSocCode03);
                    $("#recommend").val(data.LINK_URL);
                } else {
                    pageObj.commendId = "";
                    pageObj.commendNm = "";
                    pageObj.commendObj = {};
                }

            } else if(data.RESULT_CODE =="00001"){
                MCP.alert("휴대폰 번호가 다릅니다. 다시 인증해 주세요.");
                return false;
            } else if(data.RESULT_CODE =="00002"){
                MCP.alert("다시 인증해 주세요.");
                return false;
            } else if(data.RESULT_CODE =="00003"){
                MCP.alert("친구추천 이벤트는 kt M모바일 고객인 경우에만 이용 가능합니다.");
                return false;
            } else if(data.RESULT_CODE =="00004"){
                MCP.alert("요청하신 번호는 이벤트 참여가 불가합니다.");
                return false;
            } else if(data.RESULT_CODE =="00005"){
                MCP.alert("추천 아이디 생성에 실패 했습니다.");
                return false;
            } else if(data.RESULT_CODE =="00009"){
                MCP.alert("개인정보 제 3자 제공에 동의해주세요.");
                return false;
            } else if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){ // STEP 오류
                MCP.alert(data.RESULT_MSG);
                return false;
            } else {
                MCP.alert("문제가 발생 하였습니다.<br>잠시후 다시 시도 하시기 바랍니다.");
                return false;
            }
        });
}

