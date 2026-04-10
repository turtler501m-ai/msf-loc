;

const pageObj = {
    allRateList:[]
    , selRateList:[]
    , temRateList:[]
    , rateRateListVal:['A']// 선택한 값...
    , rateGigtListVal:['A']// 선택한 값...
    , rateDataVal:''// 선택한 값...
    , rateCallVal:''// 선택한 값...
    , rateSmsVal :''
    , ratePriceMinVal:0
    , ratePriceMaxVal:100
    , rateDataMinVal:0
    , rateDataMaxVal:100
    , ratePriceMin:0
    , ratePriceMax:100
    , rateDataMin:0
    , rateDataMax:100
    , allRateCnt:0
    , temRateCnt:0
    , selectDomVal:["","",""]
} ;



// rsliders 에서 가격 맵핑
const priceMap = new Map([
    [0, 0]
    ,[20, 5000]
    ,[40, 10000]
    ,[60, 20000]
    ,[80, 30000]
    ,[100, 1000000]
]);

const dataMap = new Map([
    [0, 0]
    ,[20, 7]
    ,[40, 10]
    ,[60, 15]
    ,[80, 100]
    ,[100, 10000]
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
                    $('#rateComparison1, #rateComparison2, #rateComparison3').append(`<option value="${rateDtl.rateAdsvcCd}" data-ratedtl='${JSON.stringify(rateDtl)}'>${rateDtl.rateAdsvcNm}</option>`);
                    $('#rateComparison1').find("option").last().data(rateDtl);
                    $('#rateComparison2').find("option").last().data(rateDtl);
                    $('#rateComparison3').find("option").last().data(rateDtl);
                    spCnt++;
                });
              }
        });

        if(rset=='N'){
            let allRateDtlList = [];

            pageObj.selRateList.forEach(rateGroupItem => {
                allRateDtlList.push(...rateGroupItem.rateDtlList);
            });

            const validList = allRateDtlList.filter(rateDtl => {
                const val = rateDtl.rateAdsvcGdncBas?.promotionAmtVatDesc;
                return val !== undefined && val !== null && !isNaN(parseFloat(val));
            });

            // 작은 순서대로 정렬
            const sortedList = [...validList].sort((a, b) =>
                extractNumber(a.rateAdsvcGdncBas.promotionAmtVatDesc) - extractNumber(b.rateAdsvcGdncBas.promotionAmtVatDesc)
            );
            const uniqueSortedList = [];

            sortedList.forEach(item => {
                const price = extractNumber(item.rateAdsvcGdncBas.promotionAmtVatDesc);
                const isDuplicate = uniqueSortedList.some(
                    existing => extractNumber(existing.rateAdsvcGdncBas.promotionAmtVatDesc) === price
                );
                if (!isDuplicate) {
                    uniqueSortedList.push(item);
                 }
            });

            const top3 = uniqueSortedList.slice(0, 3);
            // top3Map 생성
            const top3Map = {};
            top3.forEach((item, index) => {
                if (item?.rateAdsvcCd) {
                    top3Map[`#rateComparison${index + 1}`] = item.rateAdsvcCd;
                }
            });

            // top3Map에서 key만 추출해서 selectIds 생성
            const selectIds = Object.keys(top3Map);

            // 1. 각 select 박스에 전체 옵션 한 번씩 넣기 (selected는 아직 없음)
            selectIds.forEach(selectId => {
                $(selectId).empty();
            });
            const addedRateAdsvcCdSet = new Set();

            pageObj.selRateList.forEach(rateGroupItem => {
                if (rateGroupItem.rateDtlList.length > 0) {
                    selectIds.forEach(selectId => {
                        $(selectId).append(`<optgroup label="${rateGroupItem.rateAdsvcCtgNm}">`);
                    });

                    const validRateDtlList = rateGroupItem.rateDtlList.filter(rateDtl => {
                        return (
                            rateDtl.rateAdsvcCd &&
                            rateDtl.rateAdsvcNm &&
                            rateDtl.rateAdsvcGdncBas &&
                            rateDtl.rateAdsvcGdncBas.promotionAmtVatDesc != null
                        );
                    });

                    validRateDtlList.forEach(rateDtl => {
                        if (addedRateAdsvcCdSet.has(rateDtl.rateAdsvcCd)) return;

                        selectIds.forEach(selectId => {
                            const isSelected = top3Map[selectId] === rateDtl.rateAdsvcCd;
                            const optionHtml = `<option value="${rateDtl.rateAdsvcCd}" data-ratedtl='${JSON.stringify(rateDtl)}' ${isSelected ? 'selected' : ''}>${rateDtl.rateAdsvcNm}</option>`;
                            $(selectId).find(`optgroup[label="${rateGroupItem.rateAdsvcCtgNm}"]`).last().append(optionHtml);
                            $(selectId).find("option").last().data(rateDtl); // jQuery data 객체 설정
                        });
                        // 중복 방지를 위해 Set에 추가
                        addedRateAdsvcCdSet.add(rateDtl.rateAdsvcCd);
                    });
                }
            });
        } else {
            //공통코드 에서 기본값으로 설정
            let rateCompDefault01 = $("#rateCompDefault01").val();
            let rateCompDefault02 = $("#rateCompDefault02").val();
            let rateCompDefault03 = $("#rateCompDefault03").val();


            if (rateCompDefault01 != null && rateCompDefault01 != "" && $("#rateComparison1").find("option[value='" + rateCompDefault01 + "']").length > 0 ) {
                $("#rateComparison1").val(rateCompDefault01).trigger("change");
            }
            if (rateCompDefault02 != null && rateCompDefault02 != ""  && $("#rateComparison2").find("option[value='" + rateCompDefault02 + "']").length > 0 ) {
                $("#rateComparison2").val(rateCompDefault02).trigger("change");
            }
            if (rateCompDefault03 != null && rateCompDefault03 != ""  && $("#rateComparison3").find("option[value='" + rateCompDefault03 + "']").length > 0 ) {
                $("#rateComparison3").val(rateCompDefault03).trigger("change");
            }
        }
        $('#spCnt').html(spCnt);
    }



    $('#rateComparison1, #rateComparison2, #rateComparison3').change(function(){
        let thisVal = $(this).val();
        const selectedOption = $(this).find(":selected");
        let jsonStr = selectedOption.attr("data-ratedtl");
        let objData;
        if (jsonStr) {
           try {
               objData = JSON.parse(jsonStr);
           } catch (e) {
               //console.error("JSON 파싱 오류:", e);
           }
        } else {
           //console.warn("data-ratedtl 속성이 없습니다.");
        }

        // 이후 비교 영역 렌더링 등 필요한 작업
        let thisId = $(this).attr("id") ;// rateComparison1
        const subLength = 10;

        if ("rateComparison1" == thisId) {
            if(objData != null && Object.keys(objData).length > 0){
                $('#rate-comp__list-item1').html(getBasicTemplate(objData));
                $('#rate-comp__list-item01').html(getAdditionTemplate(objData));
                $("#rate-comp-info_01").removeClass("no-data").html(getTemplate(objData));

                if (objData.rateGiftPrmtListDTO != null && typeof objData.rateGiftPrmtListDTO === 'object' && objData.rateGiftPrmtListDTO.totalPrice > 0 ) {
                    $('#rate-comp__list-item11').html(getTemplateGift(objData.rateGiftPrmtListDTO));
                } else {
                    $('#rate-comp__list-item11').empty();
                }
            } else {
                $("#rate-comp-info_01").html(noDataTemplate()).addClass("no-data");
                $('#rate-comp__list-item1').empty();
                $('#rate-comp__list-item01').empty();
                $('#rate-comp__list-item11').empty();
            }
        } else if ("rateComparison2" == thisId) {
            if(objData != null && Object.keys(objData).length > 0){
                $('#rate-comp__list-item2').html(getBasicTemplate(objData));
                $('#rate-comp__list-item02').html(getAdditionTemplate(objData));
                $("#rate-comp-info_02").removeClass("no-data").html(getTemplate(objData));

                if (objData.rateGiftPrmtListDTO != null && typeof objData.rateGiftPrmtListDTO === 'object' && objData.rateGiftPrmtListDTO.totalPrice > 0 ) {
                    $('#rate-comp__list-item12').html(getTemplateGift(objData.rateGiftPrmtListDTO));
                } else {
                    $('#rate-comp__list-item12').empty();
                }
            } else {
                $("#rate-comp-info_02").html(noDataTemplate()).addClass("no-data");
                $('#rate-comp__list-item2').empty();
                $('#rate-comp__list-item02').empty();
                $('#rate-comp__list-item12').empty();
            }
        } else {
            if(objData != null && Object.keys(objData).length > 0){
                $('#rate-comp__list-item3').html(getBasicTemplate(objData));
                $('#rate-comp__list-item03').html(getAdditionTemplate(objData));
                $("#rate-comp-info_03").removeClass("no-data").html(getTemplate(objData));

                if (objData.rateGiftPrmtListDTO != null && typeof objData.rateGiftPrmtListDTO === 'object' && objData.rateGiftPrmtListDTO.totalPrice > 0 ) {
                    $('#rate-comp__list-item13').html(getTemplateGift(objData.rateGiftPrmtListDTO));
                } else {
                    $('#rate-comp__list-item13').empty();
                }
            } else{
                $("#rate-comp-info_03").html(noDataTemplate()).addClass("no-data");
                $('#rate-comp__list-item3').empty();
                $('#rate-comp__list-item03').empty();
                $('#rate-comp__list-item13').empty();
            }
        }

        equalizeHeights([
            '.rate-comp__info-data',
            '.rate-comp__info-data__sub',
            '.rate-comp__info-call__sub',
            '.rate-comp__info-item-partner',
            '.rate-comp__info-item-solo',
            '.rate-comp__info-item-data',
            '.rate-comp__info-item-partner__text',
            '.rate-comp__info-item-partner__text2',
            '.max-data-label'
          ], subLength);
    });

    const setHeightLi =  function() {
        equalizeHeights([
            '.rate-comp__info-data',
            '.rate-comp__info-data__sub',
            '.rate-comp__info-call__sub',
            '.rate-comp__info-item-partner',
            '.rate-comp__info-item-solo',
            '.rate-comp__info-item-data',
            '.rate-comp__info-item-partner__text',
            '.rate-comp__info-item-partner__text2',
            '.max-data-label'
        ], 10)
    }

    $("#rateCompDefault,#rateCompAdd,#btnRateGift").click(function(){
        setTimeout( setHeightLi, 500);
    });

    $("#btnFilter").click(function(){
        var el = document.querySelector('#rateComparisonModal');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

        $('[data-target="section1"]').trigger("click");
        // $("[name=rateComparisonRateGroup][value='"+pageObj.rateRateVal+"']").prop("checked","checked");

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
            window.KTM.RangeSliderMulti.getInstances()[0]._element.noUiSlider.set([pageObj.rateDataMinVal, pageObj.rateDataMaxVal]);

        } else {
            $("#divDataSlider").hide();
        }
        window.KTM.RangeSliderMulti.getInstances()[1]._element.noUiSlider.set([pageObj.ratePriceMinVal, pageObj.ratePriceMaxVal]);


        // setFilter();

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

        if ($("#rateComparison3 option[value='" + pageObj.selectDomVal[2] + "']").length > 0) {
            $("#rateComparison3").val(pageObj.selectDomVal[2]).trigger("change");
        } else {
            $('#rateComparison3').eq(0).trigger("change");
        }

        if (pageObj.allRateCnt != pageObj.temRateCnt) {
            $("#btnFilter").addClass("is-active");
        } else {
            $("#btnFilter").removeClass("is-active");
        }
        KTM.Dialog.closeAll();
    });







   // $("#rateComparisonPriceGroupTitle").siblings(".c-range.multi")

    /*
     * let rSlider = document.querySelector("#rateComparisonPriceGroupTitle +
     * .c-range.multi") // 멀티 슬라이더는 이벤트 이름이 ui.rsliders.change 입니다.
     * rSlider.addEventListener("ui.rsliders.change", function(e) {
     */

    // 클릭 이벤트 감지
    /*
     * rSlider.addEventListener("click", function(e) { console.log("Slider
     * Clicked!"); console.dir(e); });
     */

    $(document).on("ui.rsliders.change", "#section2 .c-range.multi", function(e){
        pageObj.rateDataMin =  e.value[0] ;
        pageObj.rateDataMax =  e.value[1] ;

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
        window.KTM.RangeSliderMulti.getInstances()[0]._element.noUiSlider.set([minVal, maxVal]); // 최소값
                                                                                                    // 10,
                                                                                                    // 최대값
                                                                                                    // 50으로
                                                                                                    // 변경
    });



    $(document).on("ui.rsliders.change", "#section5 .c-range.multi", function(e){

        pageObj.ratePriceMin =  e.value[0] ;
        pageObj.ratePriceMax =  e.value[1] ;

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
        window.KTM.RangeSliderMulti.getInstances()[1]._element.noUiSlider.set([minVal, maxVal]); // 최소값
                                                                                                    // 10,
                                                                                                    // 최대값
                                                                                                    // 50으로
                                                                                                    // 변경
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

        if ($("#rateComparisonModal").is(":hidden")) {
            return;
        }
        pageObj.temRateList = JSON.parse(JSON.stringify(pageObj.allRateList));

        // 요금제
        let checkedValues = $("[name=rateComparisonRateGroup]:checked").map(function() {
            return $(this).val();
        }).get();

        if (!checkedValues.includes("A")) {
            // 연령특화 <=== 이것만 특히 한것..
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

        // Data
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

                        // 2. rateAdsvcItemDesc가 존재하고 숫자로 변환 가능하며, 범위 내에 있는 경우
                        // 포함
                        const rawValue = rateDtl.rateAdsvcBnfitGdncMap.RATEBE21?.rateAdsvcItemDesc;
                        const thisDataVal = Number(rawValue);

                        const isValueValid = !isNaN(thisDataVal) && thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                        return isCtgValid && isValueValid;
                    });
                });
            } else {
                // data-rate-ctg 01 아닌것..
                /*
                 * <input type="checkbox" id="unlimited" name="rateCheck"
                 * class=" width_25" value="01"> <label for="newSticker"
                 * class="fontBold"> 무제한</label>
                 */
                let minDataVal = 0;
                let maxDataVal = 0;

                minDataVal = dataMap.get(pageObj.rateDataMin) * gigabyteCnt;
                maxDataVal = dataMap.get(pageObj.rateDataMax) * gigabyteCnt;

                pageObj.temRateList.forEach(rateObj => {
                    // rateDtlList가 존재하고 배열인지 확인
                    if (!Array.isArray(rateObj.rateDtlList)) {
                        return; // 존재하지 않으면 처리하지 않음
                    }
                    rateObj.rateDtlList = rateObj.rateDtlList.filter(rateDtl => {
                        const isCtgValid = rateDtl.rateAdsvcGdncBas.rateCtg.split(",").includes("01");

                        // 2. rateAdsvcItemDesc가 존재하고 숫자로 변환 가능하며, 범위 내에 있는 경우
                        // 포함
                        const rawValue = rateDtl.rateAdsvcBnfitGdncMap.RATEBE21?.rateAdsvcItemDesc;
                        const thisDataVal = Number(rawValue);

                        const isValueValid = !isNaN(thisDataVal) && thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                        return isCtgValid && isValueValid;
                    })
                });
            }

        }

        // 통화
        const $rateComparisonCall = $("[name=rateComparisonCallGroup]:checked");
        let callFilterVal = $rateComparisonCall.val()
         $("[name=rateComparisonCallGroup]").click(function() {
                $('[data-target="section4"]').trigger("click");
            });
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
                        /*
                         * if (thisDataVal >= minDataVal && thisDataVal <=
                         * maxDataVal) { console.log("[CALL] minVal["+minVal+"]
                         * maxVal["+maxVal+"] minDataVal["+minDataVal+"]
                         * thisDataVal["+thisDataVal+"]
                         * maxDataVal["+maxDataVal+"]"); }
                         */
                        // 최소값~최대값 범위에 있는 경우 유지, 그렇지 않으면 삭제
                        return thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                    }
                    // rateAdsvcItemDesc가 없으면 삭제
                    return false;
                });
            });

        }


        // 문자
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

                        // console.log("[CALL] minVal["+minVal+"]
                        // maxVal["+maxVal+"] minDataVal["+minDataVal+"]
                        // thisDataVal["+thisDataVal+"]
                        // maxDataVal["+maxDataVal+"]");

                        /*
                         * if (thisDataVal >= minDataVal && thisDataVal <=
                         * maxDataVal) { console.log("[CALL] minVal["+minVal+"]
                         * maxVal["+maxVal+"] minDataVal["+minDataVal+"]
                         * thisDataVal["+thisDataVal+"]
                         * maxDataVal["+maxDataVal+"]"); }
                         */
                        // 최소값~최대값 범위에 있는 경우 유지, 그렇지 않으면 삭제
                        return thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                    }
                    // rateAdsvcItemDesc가 없으면 삭제
                    return false;
                });
            });
        }
        $("[name=rateComparisonPriceGroup]").click(function() {
            $('[data-target="section6"]').trigger("click");
        });


        // 가격 obj.rateAdsvcGdncBas.promotionAmtVatDesc
        // console.log(" pageObj.ratePriceMin["+pageObj.ratePriceMin+"]
        // pageObj.ratePriceMax["+pageObj.ratePriceMax+"] ");
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
                    let rawValue = rateDtl.rateAdsvcGdncBas.promotionAmtVatDesc.replace(/,/g, "");
                    // 숫자로 변환 가능한지 확인
                    let thisDataVal = Number(rawValue); // NaN 방지
                    if (isNaN(thisDataVal)) {
                        return false; // 숫자로 변환되지 않으면 삭제
                    }
                    // console.log("[RATE] minDataVal["+minDataVal+"]
                    // thisDataVal["+thisDataVal+"] maxDataVal["+maxDataVal+"]
                    // ");

                    return thisDataVal >= minDataVal && thisDataVal <= maxDataVal;
                    // rateAdsvcItemDesc가 없으면 삭제
                    return false;

                });
            });
        }

        // 추가 혜택
        const $rateComparisonGift = $("[name=rateComparisonGiftGroup]:checked");
        let giftFilterVal = $rateComparisonGift.val();


        let checkedGiftValues = $("[name=rateComparisonGiftGroup]:checked").map(function() {
            return $(this).val();
        }).get();

        if (!checkedGiftValues.includes("A")) {
            if (checkedGiftValues.includes("07")) { // 데이터 함께쓰기 주기 , 받기
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

    const noDataTemplate = function(){
        var arr = [];
        arr.push(`<div>\n`);
        arr.push(`    <i class="c-icon c-icon--rate-comparison" aria-hidden="true"></i>\n`);
        arr.push(`    <p>비교하실 요금제를<br>선택해 주세요.</p>\n`);
        arr.push(`</div>\n`);
        return arr.join('');
    }

    const getTemplate = function(obj) {
        var arr = [];
        arr.push(`<div class="rate-comp-price"><p>${obj.rateAdsvcGdncBas.promotionAmtVatDesc}<span>원</span></p></div>\n`);
        arr.push(`<div class="rate-comp-info__button">\n`);
        arr.push(`    <button class="c-button c-button--full c-button--mint" onclick="location.href='/appForm/appFormDesignView.do?rateCd=${obj.rateAdsvcCd}&onOffType=5'">가입하기</button>\n`);
        arr.push(`</div>\n`);
        return arr.join('');
    }

    const getBasicTemplate = function(obj) {
        var arr = [];
        var ktWifiTxt = $("#ktWifiTxt").val();
        arr.push(`
            <ul class="rate-comp__info">
                <li class="rate-comp__info-item">
                    <i class="c-icon c-icon--rate-comp-data" aria-hidden="true"></i>\n`);
        arr.push(`      <span class="max-data-label">\n`);
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE95?.rateAdsvcItemDesc) {
            arr.push(`    <em>${obj.rateAdsvcBnfitGdncMap.RATEBE95.rateAdsvcItemDesc}</em>`);
        }
        arr.push(`     </span>\n`);
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE01?.rateAdsvcItemDesc) {
            arr.push(`    <p class="rate-comp__info-data">${obj.rateAdsvcBnfitGdncMap.RATEBE01.rateAdsvcItemDesc}</p>`);
        } else {
            arr.push(`     <p class="rate-comp__info-data"></p>`);
        }
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE31?.rateAdsvcItemDesc) {
            arr.push(`      <span class="rate-comp__info-data__sub">${obj.rateAdsvcBnfitGdncMap.RATEBE31.rateAdsvcItemDesc}</span>\n`);
        } else {
            arr.push(`<span class="rate-comp__info-data__sub"></span>\n`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">\n`);
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE02?.rateAdsvcItemDesc) {
            arr.push(`<p>음성&nbsp${obj.rateAdsvcBnfitGdncMap.RATEBE02.rateAdsvcItemDesc}</p>\n`);
        } else {
            arr.push(`<p></p>\n`);
        }
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE32?.rateAdsvcItemDesc) {
            arr.push(`<span class="rate-comp__info-call__sub">${obj.rateAdsvcBnfitGdncMap.RATEBE32.rateAdsvcItemDesc}</span>\n`);
        } else {
          arr.push(`<span class="rate-comp__info-call__sub"></span>\n`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">\n`);
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE03?.rateAdsvcItemDesc) {
            arr.push(`     <p>문자&nbsp${obj.rateAdsvcBnfitGdncMap.RATEBE03.rateAdsvcItemDesc}</p>\n`);
        } else {
            arr.push(`     <p></p>\n`);
        }
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE33?.rateAdsvcItemDesc) {
            arr.push(`      <span>${obj.rateAdsvcBnfitGdncMap.RATEBE33.rateAdsvcItemDesc}</span>\n`);
        } else{
            arr.push(`     <span></span>\n`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">\n`);
        arr.push(`<div class="rate-comp__info-item-partner"\n>`);
        if(obj?.rateAdsvcBnfitGdncMap?.RATEBE11?.rateAdsvcItemImgNm){
            arr.push(`<img src="${obj.rateAdsvcBnfitGdncMap.RATEBE11.rateAdsvcItemImgNm}" />`);
            arr.push(`</div>`);
        }else{
            arr.push(`</div>`);
        }
        if (obj?.rateAdsvcBnfitGdncMap?.RATEBE11?.rateAdsvcItemDesc) {
            arr.push(`<p class="rate-comp__info-item-partner__text"><b>${obj.rateAdsvcBnfitGdncMap.RATEBE11.rateAdsvcItemDesc}</b></p>\n`);
        } else {
            arr.push(`     <p><b>-</b></p>\n`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">\n`);
        if (obj?.rateGdncPropertyMap?.["09"]?.propertyCode) {
            arr.push(`        <p>${ktWifiTxt}&nbsp;<em>무료</em></p>\n`);
        } else{
            arr.push(`        <p><em>-</em></p>`);
        }
        arr.push(`</li>\n`);
        arr.push(`</ul>\n`);
        return arr.join('');
    }

    const getAdditionTemplate = function(obj) {
        var arr = [];
        var soloTxt = $("#soloTxt").val();
        var dataShareTxt = $("#dataShareTxt").val();
        var dataTogetTxt = $("#dataTogetTxt").val();
        var tripleSaleTxt = $("#tripleSaleTxt").val();
        var dataCouponTxt = $("#dataCouponTxt").val();

        arr.push(`
                      <ul class="rate-comp__info">
                          <li class="rate-comp__info-item">
                          <div class="rate-comp__info-item-solo">
                            `);
        if (obj?.rateGdncPropertyMap?.["01"]?.propertySbst) {
            arr.push(`
          <i class="c-icon c-icon--rate-comp-solo" aria-hidden="true"></i>
          </div>\n
          <p><b>${soloTxt}</b></p>\n`); //아무나 SOLO 결합
            arr.push(`<span>${obj.rateGdncPropertyMap["01"]?.propertySbst}</span>\n`);
        } else {
            arr.push(`</div>\n
            <p><b>-</b></p>`);
            arr.push(`<span></span>\n`);
        }
           arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">`);
        if (obj?.rateGdncPropertyMap?.["06"]?.propertyCode) {
             arr.push(`        <p><b>${dataShareTxt}</b></p>\n`);
             arr.push(`        <span>무료</span>\n`);
        } else{
            arr.push(`        <p><b>-</b></p>`);
            arr.push(`        <span></span>`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">`);
        if (obj?.rateGdncPropertyMap?.["07"]?.propertyCode) {
            arr.push(`        <p><b>데이터 함께쓰기</b></p>\n`);
            arr.push(`      <span>매월 2GB 주기</span>\n`);
        }else if(obj?.rateGdncPropertyMap?.["08"]?.propertyCode) {
        arr.push(`        <p><b>데이터 함께쓰기</b></p>\n`);
        arr.push(`      <span>매월 2GB 받기</span>\n`);
        }else {
            arr.push(`        <p><b>-</b></p>`);
            arr.push(`        <span></span>`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">\n`);
        if (obj?.rateGdncPropertyMap?.["05"]?.propertyCode) {
            arr.push(`        <p><b>${tripleSaleTxt}</b></p>\n`);
            arr.push(`      <span>해당</span>\n`);
        } else {
            arr.push(`        <p><b>-</b></p>`);
            arr.push(`        <span></span>`);
        }
        arr.push(`</li>\n`);
        arr.push(`<li class="rate-comp__info-item">\n`);
        arr.push(`<div class="rate-comp__info-item-data">\n`);
        if (obj?.rateGdncPropertyMap?.["02"]?.propertySbst) {
            arr.push(`<i class="c-icon c-icon--rate-comp-coupon" aria-hidden="true"></i>\n`);
            arr.push(`</div>\n`);
            arr.push(`<p><b>${dataCouponTxt}</b></p>\n`);
            arr.push(`        <span>${obj.rateGdncPropertyMap["02"]?.propertySbst}</span>\n`);
        } else {
            arr.push(`</div>\n`);
            arr.push(`        <p><b>-</b></p>\n`);
            arr.push(`       <span></span>\n`);
        }
        arr.push(`</li>\n`);
        arr.push(`</ul>\n`);
        return arr.join('');
    }

    const getTemplateGift = function(giftPrmtInfo) {

        var giftTotalPrice = giftPrmtInfo.totalPrice;
        var giftPrmtList =  giftPrmtInfo.rateGiftPrmtList;
        var fallbackUrl = "/resources/images/portal/content/img_phone_noimage.png";

        var arr = [];
        arr.push(`<ul class="rate-comp__info">`);
        arr.push(`    <li class="rate-comp__info-item">`);
        arr.push(`        <div class="rate-comp-price"><p class="u-fs-36 u-fw--bold">${giftTotalPrice / 10000}만원</p></div>`);
        arr.push(`        <p class="u-mt--0">가입혜택(최대)</p>`);
        arr.push(`    </li>`);

        giftPrmtList.forEach(item => {
            if (item.giftPrice > 0) {
                arr.push(`    <li class="rate-comp__info-item">`);
                arr.push(`        <div class="rate-comp__info-item-partner" >`);
                arr.push(`            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">`);
                arr.push(`        </div>`);
                arr.push(`        <p class="rate-comp__info-item-partner__text2" ><b>${item.giftText}</b></p>`);
                arr.push(`    </li>`);
            }
        });

        arr.push(`</ul>`);
        return arr.join('');
    }

    function equalizeHeights(groupSelectors, subLength) {

        groupSelectors.forEach(selector => {
            const rows = [
                document.querySelectorAll(`.rate-comp__list-item:nth-child(1) ${selector}`),
                document.querySelectorAll(`.rate-comp__list-item:nth-child(2) ${selector}`),
                document.querySelectorAll(`.rate-comp__list-item:nth-child(3) ${selector}`)
            ].filter(row => row.length);

            // 요소가 없으면 패스
            if (!rows.length) return;


            for (let i = 0; i < subLength; i++) {
                const targets = rows.map(row => row[i]).filter(Boolean);
                if (!targets.length) continue;
                targets.forEach(el => {
                    el.style.height = '';
                });

                const maxHeight = Math.max(...targets.map(el => el.offsetHeight));
                targets.forEach(el => {
                    el.style.height = maxHeight + 'px';
                });
            }
        });
    }

      // DOM이 다 로드된 후 실행
    window.addEventListener('load', () => {
        const subLength = 10;

        equalizeHeights([
            '.rate-comp__info-data',
            '.rate-comp__info-data__sub',
            '.rate-comp__info-call__sub',
            '.rate-comp__info-item-partner',
            '.rate-comp__info-item-solo',
            '.rate-comp__info-item-data',
            '.rate-comp__info-item-partner__text',
            '.rate-comp__info-item-partner__text2',
            '.max-data-label'
        ], subLength);
    });


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
        threshold: 0.7 // 섹션이 70% 보일 때 활성화
    });

    sections.forEach(section => observer.observe(section));


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


        });
    } ();




    const checkSelect = function () {
        let rateComparison1 = $("#rateComparison1").val();
        if (rateComparison1 !=null && rateComparison1 !="") {
            let onclickValue = $("#rate-comp-info_01 button").attr("onclick");

            // 따옴표 제거 후 실제 URL만 추출
            let url = onclickValue.match(/'([^']+)'/)[1];

            // URL 객체로 파싱
            let params = new URLSearchParams(url.split("?")[1]);
            let rateCd = params.get("rateCd");

            if (rateComparison1 !=rateCd ) {
                //$("#rateComparison1").val(rateComparison1).trigger("change");
                //공통코드 에서 기본값으로 설정
                let rateCompDefault01 = $("#rateCompDefault01").val();
                if (rateCompDefault01 != null && rateCompDefault01 != "" && $("#rateComparison1").find("option[value='" + rateCompDefault01 + "']").length > 0 ) {
                    $("#rateComparison1").val(rateCompDefault01).trigger("change");
                }

            }
        }

        let rateComparison2 = $("#rateComparison2").val();
        if (rateComparison2 !=null && rateComparison2!="") {
            let onclickValue = $("#rate-comp-info_02 button").attr("onclick");

            // 따옴표 제거 후 실제 URL만 추출
            let url = onclickValue.match(/'([^']+)'/)[1];

            // URL 객체로 파싱
            let params = new URLSearchParams(url.split("?")[1]);
            let rateCd = params.get("rateCd");

            if (rateComparison2 !=rateCd ) {
                //$("#rateComparison2").val(rateComparison2).trigger("change");
                let rateCompDefault02 = $("#rateCompDefault02").val();
                if (rateCompDefault02 != null && rateCompDefault02 != ""  && $("#rateComparison2").find("option[value='" + rateCompDefault02 + "']").length > 0 ) {
                    $("#rateComparison2").val(rateCompDefault02).trigger("change");
                }
            }
        }
        let rateComparison3 = $("#rateComparison3").val();
        if (rateComparison3 !=null && rateComparison3!="") {
            let onclickValue = $("#rate-comp-info_03 button").attr("onclick");

            // 따옴표 제거 후 실제 URL만 추출
            let url = onclickValue.match(/'([^']+)'/)[1];

            // URL 객체로 파싱
            let params = new URLSearchParams(url.split("?")[1]);
            let rateCd = params.get("rateCd");

            if (rateComparison3 !=rateCd ) {
                //$("#rateComparison3").val(rateComparison3).trigger("change");
                let rateCompDefault03 = $("#rateCompDefault03").val();
                if (rateCompDefault03 != null && rateCompDefault03 != ""  && $("#rateComparison3").find("option[value='" + rateCompDefault03 + "']").length > 0 ) {
                    $("#rateComparison3").val(rateCompDefault03).trigger("change");
                }
            }
        }

    }

    setTimeout(checkSelect, 500);



});