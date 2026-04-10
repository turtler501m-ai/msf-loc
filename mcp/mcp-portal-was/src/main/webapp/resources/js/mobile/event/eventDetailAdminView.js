;

$(document).ready(function(){

    //공유하기 버튼
    $(document).on("click",".c-icon--share",function(){
        detailView();

    });

    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
/*        if($('#eventBranch').val() == 'E'){
            goEventView("/m/event/eventList.do");
        }else{
            goEventView("/m/event/cprtEventBoardList.do");
        }*/
        goEventView("/m/event/eventList.do");
    });

     $("#tab2").click(function() {
        $("#eventStatus").val('end');
        if($('#eventBranch').val() == 'E'){
            goEventView("/m/event/eventBoardEndList.do");
        }else{
            goEventView("/m/event/cprtEventBoardEndList.do");
        }
    });

    $("#tab3").click(function() {
        $("#eventStatus").val('win');
/*        if($('#eventBranch').val() == 'E'){
            goEventView("/m/event/winnerList.do");
        }else{
            goEventView("/m/event/cprtEventWinnerList.do");
        }*/
        goEventView("/m/event/winnerList.do");
    });

    for (var i = 0; i < document.getElementsByClassName('flexible').length; i++) {
        var id = document.getElementsByClassName('flexible')[i].id;
        flexibleComponentByDate(id);
        $("#codeLength").val(i+1);
    }

    var setBannerTemplate = function(obj,targetObj,index) {

        // <option value="RATEBE01" selected="">요금제제공량(데이터(노출문구))</option>
        let rateAdsvcCd = targetObj.data("rateAdsvcCd");

        // 혜택요약 관련 정보
        let giftPrmtInfo = obj.rateGiftPrmtListDTO;
        let giftPrmtList = (giftPrmtInfo == null) ? null : giftPrmtInfo.rateGiftPrmtList;
        let giftPrmtWireList = (giftPrmtInfo == null) ? null : giftPrmtInfo.wireRateGiftPrmtList;
        let giftPrmtFreeList = 	(giftPrmtInfo == null) ? null : giftPrmtInfo.freeRateGiftPrmtList;
        let mainGiftPrmtList = (giftPrmtInfo == null) ? null : giftPrmtInfo.mainGiftPrmtList;
        let giftPrmtFlag = false;

        if (mainGiftPrmtList != null && mainGiftPrmtList.length > 0){
            giftPrmtFlag = true;
        }

        var arr = [];

        // 혜택요약 존재 시 div태그 추가
        if(giftPrmtFlag){
            arr.push('<div class="rate-content-wrap">\n');
        }

        arr.push(`<a class='rate-banner__content' href='/m/appForm/appFormDesignView.do?rateCd=${rateAdsvcCd}' title='${obj.rateAdsvcGdncBas.rateAdsvcNm} 페이지 이동'>\n`);

        if (obj.rateGdncBannerList.length > 0)  {
            const bannerLabels = obj.rateGdncBannerList.map(item => `
                <span class='rate-banner__label' style='color: ${item.bannerColor}; background-color: ${item.bannerBackColor};' >
                    <em>${item.bannerSbst}</em>
                </span>
            `).join('');
            const bannerHTML = `<div class='rate-banner__label-wrap'>${bannerLabels}</div>`;
            arr.push(bannerHTML);
        }

        arr.push(`    <div class='rate-banner__title-wrap'>\n`);
        arr.push(`        <div class='rate-banner__title'>\n`);
        arr.push(`            <strong>${obj.rateAdsvcGdncBas.rateAdsvcNm}</strong>\n`);   //promotionAmtVatDesc
        arr.push(`        </div>\n`);
        arr.push(`        <div class='rate-banner__price'>\n`);
        arr.push(`            <p class='rate-banner__price-basic'>\n`);
        arr.push(`                <span class='c-hidden'>월 기본료(VAT 포함)</span>\n`);
        arr.push(`                <em>${obj.rateAdsvcGdncBas.mmBasAmtVatDesc}</em>원\n`);
        arr.push(`            </p>\n`);
        arr.push(`            <p class='rate-banner__price-promotion'>\n`);
        arr.push(`                월 <b>${obj.rateAdsvcGdncBas.promotionAmtVatDesc}</b>원\n`);
        arr.push(`            </p>\n`);
        arr.push(`            <i class='c-icon c-icon--arrow-black' aria-hidden='true'></i>\n`);
        arr.push(`        </div>\n`);
        arr.push(`    </div>\n`);
        arr.push(`    <div class='rate-banner__detail'>\n`);
        arr.push(`        <ul class='rate-banner__detail-list'>\n`);
        arr.push(`            <li class='rate-banner__detail-item'>\n`);
        arr.push(`                <span>\n`);
        arr.push(`                    <i class='c-icon c-icon--data-type3' aria-hidden='true'></i>\n`);
        arr.push(`                </span>\n`);
        arr.push(`                <span class='c-hidden'>데이터</span>\n`);
        arr.push(`                <div class='rate-banner__detail-info'>\n`);
        if (obj.rateAdsvcBnfitGdncMap.RATEBE01?.rateAdsvcItemDesc) {
            arr.push(`<p>${obj.rateAdsvcBnfitGdncMap.RATEBE01.rateAdsvcItemDesc}</p>`);
        }

        if (obj.rateAdsvcBnfitGdncMap.RATEBE31?.rateAdsvcItemDesc) {
            arr.push(`<p>${obj.rateAdsvcBnfitGdncMap.RATEBE31.rateAdsvcItemDesc}</p>`);
        }

        arr.push(`                </div>\n`);
        arr.push(`            </li>\n`);
        arr.push(`            <li class='rate-banner__detail-item'>\n`);
        arr.push(`                <span>\n`);
        arr.push(`                    <i class='c-icon c-icon--call-type3' aria-hidden='true'></i>\n`);
        arr.push(`                </span>\n`);
        arr.push(`                <span class='c-hidden'>음성</span>\n`);
        arr.push(`                <div class='rate-banner__detail-info '>\n`);

        if (obj.rateAdsvcBnfitGdncMap.RATEBE02?.rateAdsvcItemDesc) {
            arr.push(`<p>${obj.rateAdsvcBnfitGdncMap.RATEBE02.rateAdsvcItemDesc}</p>`);
        }

        if (obj.rateAdsvcBnfitGdncMap.RATEBE32?.rateAdsvcItemDesc) {
            arr.push(`<p>${obj.rateAdsvcBnfitGdncMap.RATEBE32.rateAdsvcItemDesc}</p>`);
        }
        arr.push(`                </div>\n`);
        arr.push(`            </li>\n`);
        arr.push(`            <li class='rate-banner__detail-item'>\n`);
        arr.push(`                <span>\n`);
        arr.push(`                    <i class='c-icon c-icon--msg-type3' aria-hidden='true'></i>\n`);
        arr.push(`                </span>\n`);
        arr.push(`                <span class='c-hidden'>문자</span>\n`);
        arr.push(`                <div class='rate-banner__detail-info'>\n`);
        if (obj.rateAdsvcBnfitGdncMap.RATEBE03?.rateAdsvcItemDesc) {
            arr.push(`<p>${obj.rateAdsvcBnfitGdncMap.RATEBE03.rateAdsvcItemDesc}</p>`);
        }
        if (obj.rateAdsvcBnfitGdncMap.RATEBE33?.rateAdsvcItemDesc) {
            arr.push(`<p>${obj.rateAdsvcBnfitGdncMap.RATEBE33.rateAdsvcItemDesc}</p>`);
        }
        arr.push(`                </div>\n`);
        arr.push(`            </li>\n`);
        arr.push(`        </ul>\n`);
        arr.push(`    </div>\n`);


        //요금제 체감가
        if (obj.rateGdncEffPriceDtl?.useYn === "Y") {
            const data = obj.rateGdncEffPriceDtl;
            let tooltipId = `benefitTp${index}`;

            arr.push(`<div class="rate-banner__benefit">`);
            arr.push(`  <div class="perceived-price">`);
            arr.push(`    <div class="perceived-price-btn-wrap">`);
            arr.push(`      <button class="perceived-price-btn" role="button" data-tpbox="#${tooltipId}">`);
            arr.push(`        <b>체감가</b><i class="c-icon c-icon--tooltip" aria-hidden="true"></i>`);
            arr.push(`      </button>`);
            arr.push(`      <div class="c-tooltip-box" id="${tooltipId}">`);
            arr.push(`        <div class="c-tooltip-box__title">체감가</div>`);
            arr.push(`        <div class="c-tooltip-box__content">프로모션 혜택을 ${data.customMonths}개월로 분할할 경우를 계산한 금액으로, 청구서상 실제 납부 금액과는 상이할 수 있습니다.</div>`);
            arr.push(`        <button class="c-tooltip-box__close" data-tpbox-close>`);
            arr.push(`          <span class="c-hidden">툴팁닫기</span>`);
            arr.push(`        </button>`);
            arr.push(`      </div>`);
            arr.push(`    </div>`);

            arr.push(`    <ul class="perceived-price-list">`);

            //제휴 혜택 (있을 경우만)
            if (data.jehuPriceReflectCd !== "00") {
                arr.push(`      <li class="perceived-price-list__item">`);
                arr.push(`        <div class="perceived-img">`);
                arr.push(`          <img src="${data.jehuImgUrl}" alt="${data.jehuPriceReflectNm}">`);
                arr.push(`        </div>`);
                arr.push(`        <div class="perceived-desc">`);
                arr.push(`          <b class="perceived-desc__title">${data.jehuPriceReflectNm}</b>`);
                arr.push(`          <span>- 월 ${Number(data.jehuBenefitAmt).toLocaleString()} 원</span>`);
                arr.push(`        </div>`);
                arr.push(`      </li>`);
            }

            //프로모션 혜택
            const promoAmt = Number(data.promotionAmt);
            const promoAmtDisplay = `${promoAmt / 10000}만원`;
            const afterAmt = (parseInt(String(obj.rateAdsvcGdncBas?.promotionAmtVatDesc || '').replace(/[^0-9]/g, ''), 10) || 0) - (Number(data.jehuBenefitAmt) || 0);

            arr.push(`      <li class="perceived-price-list__item">`);
            arr.push(`        <div class="perceived-img">`);
            arr.push(`          <img src="${data.promotionImgUrl}" alt="프로모션">`);
            arr.push(`        </div>`);
            arr.push(`        <div class="perceived-desc">`);
            arr.push(`          <b class="perceived-desc__title">프로모션 혜택</b>`);
            arr.push(`          <span>${promoAmtDisplay}÷${data.customMonths}개월 = - 월 ${Number(data.promotionBenefitAmt).toLocaleString()} 원</span>`);
            arr.push(`        </div>`);
            arr.push(`        <div class="rate-banner__perceive-price">`);
            arr.push(`          <span>월 체감가</span>`);
            arr.push(`          <p>${Number(data.monthlyEffPrice).toLocaleString()}원</p>`);
            arr.push(`          <button class="perceived-price__tooltip-btn" role="button" data-tpbox="#perceivedTp${tooltipId}"><i class="c-icon c-icon--tooltip" aria-hidden="true"></i></button>`);
            arr.push(`          <div class="c-tooltip-box" id="perceivedTp${tooltipId}" onclick="event.stopPropagation(); event.preventDefault();">`);
            arr.push(`            <div class="perceived-price__tooltip">`);
            arr.push(`              <div class="perceived-price__tooltip-plan">`);
            arr.push(`                <div class="perceived-price__tooltip-plan-item">`);
            arr.push(`                  <span>${data.customMonths}개월간</span>`);
            arr.push(`                  <p>${Number(data.monthlyEffPrice).toLocaleString()}원</p>`);
            arr.push(`                </div>`);
            arr.push(`                <div class="perceived-price__tooltip-plan-item">`);
            arr.push(`                  <i class="c-icon c-icon--arrow-type2" aria-hidden="true"></i>`);
            arr.push(`                </div>`);
            arr.push(`                <div class="perceived-price__tooltip-plan-item">`);
            arr.push(`                  <span>${data.customMonths}개월 이후</span>`);
            arr.push(`                  <p>${afterAmt.toLocaleString()}원</p>`);
            arr.push(`                </div>`);
            arr.push(`              </div>`);
            arr.push(`              <div class="perceived-price__tooltip-note">`);
            arr.push(`                <p>이 요금제는 약정이 없습니다.</p>`);
            arr.push(`              </div>`);
            arr.push(`            </div>`);
            arr.push(`            <button class="c-tooltip-box__close" data-tpbox-close>`);
            arr.push(`              <span class="c-hidden">툴팁닫기</span>`);
            arr.push(`            </button>`);
            arr.push(`          </div>`);
            arr.push(`        </div>`);
            arr.push(`      </li>`);
            arr.push(`    </ul>`);
            arr.push(`  </div>`);
            arr.push(`</div>`);
        }


        arr.push(`</a>\n`);

        //첫번째 혜탹요약은 혜택보기 펼처짐
        let isFirstGiftPrmt = false;

        // 혜택요약 존재 시 div태그 닫기 + 헤택요약 정보 그리기
        if(giftPrmtFlag){
            arr.push(`</div>\n`);

            //첫번째 혜탹요약은 혜택보기 펼처짐
            isFirstGiftPrmt = giftPrmtShownCount === 0;
            giftPrmtShownCount++;

            //기존 혜택요약(모바일)
            var giftTotalPrice = giftPrmtInfo.totalPrice;

            //인터넷 혜택요약
            var totalWirePrice = giftPrmtInfo.totalWirePrice || 0;

            //무료 혜택요약
            var totalFreeCount = giftPrmtInfo.totalFreeCount || 0;

            //대표 혜택요약(메인 아코디언)
            var giftPrmtThumbList = mainGiftPrmtList.slice(0,3);
            var totalMainPrice = giftPrmtInfo.totalMainPrice;
            var totalMainCount = giftPrmtInfo.totalMainCount;
            var fallbackUrl = "/resources/images/portal/content/img_phone_noimage.png";

            arr.push('<div class="summary-accordion">\n');
            arr.push('  <div class="c-accordion--summary">\n');
            arr.push('    <div class="c-accordion__box">\n');
            arr.push('      <div class="c-accordion__item">\n');
            arr.push(`        <div class="c-accordion__head">\n`);
            arr.push('          <strong class="c-accordion__title">\n');
            arr.push('            <ul class="summary-badge">\n');

            giftPrmtThumbList.forEach(item => {
                arr.push('          <li class="summary-badge__item">\n');
                arr.push(`            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                arr.push('          </li>\n');
            });

            arr.push('            </ul>\n');

            if(0 < totalMainPrice){
                arr.push(`        <p>최대 <span class="u-co-sub-4"><em>${totalMainPrice / 10000}</em>만원</span> 혜택보기</p>\n`);
            }else{
                arr.push(`        <p>최대 <span class="u-co-sub-4"><em>${totalMainCount}</em>개</span> 혜택보기</p>\n`);
            }

            arr.push('          </strong>\n');
            arr.push(`          <button class="c-accordion__button${isFirstGiftPrmt ? ' is-active' : ''}" type="button" aria-expanded="false">\n`);
            arr.push('              <span class="c-hidden">펼쳐보기</span>\n');
            arr.push('          </button>\n');
            arr.push('        </div>\n');

            arr.push(`        <div class="c-accordion__panel expand" aria-hidden="${isFirstGiftPrmt ? 'false' : 'true'}"${isFirstGiftPrmt ? ' style="display: block;"' : ''}>\n`);
            arr.push('          <div class="c-accordion__inside">\n');
            arr.push('            <div class="c-accordion--summary sub-category">\n');
            arr.push('              <div class="c-accordion__box">\n');

            // 모바일 혜택
            if (giftPrmtList.length > 0) {
                arr.push('                <div class="c-accordion__item mobile">\n');
                arr.push('                  <div class="c-accordion__head">\n');
                arr.push('                    <strong class="c-accordion__title">\n');
                arr.push(`                      <p><span class="rate-banner__benefit-label"><em>모바일 혜택</em></span>최대 <em>${giftTotalPrice / 10000}</em>만원</p>\n`);
                arr.push('                    </strong>\n');
                arr.push('                    <button class="c-accordion__button is-active" type="button" aria-expanded="true">\n');
                arr.push('                      <span class="c-hidden">펼쳐보기</span>\n');
                arr.push('                    </button>\n');
                arr.push('                  </div>\n');
                arr.push('                  <div class="c-accordion__panel expand" aria-hidden="false" style="display: block;">\n');
                arr.push('                    <div class="c-accordion__inside">\n');
                arr.push('                      <ul class="summary-badge-list">\n');

                giftPrmtList.forEach(item => {
                    const popupUrlMo = item.popupUrlMo ? item.popupUrlMo.replace('/m/event/eventDetail.do', '/m/event/eventDetailAdminView.do') : '';
                    arr.push('                        <li class="summary-badge-list__item">\n');
                    arr.push('                          <div class="summary-badge-list__item-img">\n');
                    arr.push(`                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                    arr.push('                          </div>\n');
                    if (popupUrlMo) {
                        arr.push(`    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`);
                    } else {
                        arr.push(`    					<p>${item.giftText}</p>\n`);
                    }
                    arr.push('                        </li>\n');
                });

                arr.push('                      </ul>\n');
                arr.push('                    </div>\n');
                arr.push('                  </div>\n');
                arr.push('                </div>\n');
            }

            // 인터넷 혜택
            if (giftPrmtWireList.length > 0) {
                arr.push('                <div class="c-accordion__item">\n');
                arr.push('                  <div class="c-accordion__head">\n');
                arr.push('                    <strong class="c-accordion__title">\n');
                if (totalWirePrice > 0) {
                    arr.push(`                	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${totalWirePrice / 10000}</em>만원</p>\n`);
                } else {
                    arr.push(`                	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${giftPrmtWireList.length}</em>개</p>\n`);
                }
                arr.push('                    </strong>\n');
                arr.push('                    <button class="c-accordion__button" type="button" aria-expanded="false">\n');
                arr.push('                      <span class="c-hidden">펼쳐보기</span>\n');
                arr.push('                    </button>\n');
                arr.push('                  </div>\n');
                arr.push('                  <div class="c-accordion__panel expand" aria-hidden="true">\n');
                arr.push('                    <div class="c-accordion__inside">\n');
                arr.push('                      <ul class="summary-badge-list">\n');

                giftPrmtWireList.forEach(item => {
                    const popupUrlMo = item.popupUrlMo ? item.popupUrlMo.replace('/m/event/eventDetail.do', '/m/event/eventDetailAdminView.do') : '';
                    arr.push('                        <li class="summary-badge-list__item">\n');
                    arr.push('                          <div class="summary-badge-list__item-img">\n');
                    arr.push(`                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                    arr.push('                          </div>\n');
                    if (popupUrlMo) {
                        arr.push(`    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`);
                    } else {
                        arr.push(`    					<p>${item.giftText}</p>\n`);
                    }
                    arr.push('                        </li>\n');
                });

                arr.push('                      </ul>\n');
                arr.push('                    </div>\n');
                arr.push('                  </div>\n');
                arr.push('                </div>\n');
            }

            // 무료 혜택
            if (giftPrmtFreeList.length > 0) {
                arr.push('                <div class="c-accordion__item">\n');
                arr.push('                  <div class="c-accordion__head">\n');
                arr.push('                    <strong class="c-accordion__title">\n');
                arr.push('                      <p><span class="rate-banner__benefit-label"><em>&thinsp;&nbsp;&nbsp;추가 혜택&thinsp;&nbsp;</em></span><em>엠모바일 고객 한정</em></p>\n');
                arr.push('                    </strong>\n');
                arr.push('                    <button class="c-accordion__button" type="button" aria-expanded="false">\n');
                arr.push('                      <span class="c-hidden">펼쳐보기</span>\n');
                arr.push('                    </button>\n');
                arr.push('                  </div>\n');
                arr.push('                  <div class="c-accordion__panel expand" aria-hidden="true">\n');
                arr.push('                    <div class="c-accordion__inside">\n');
                arr.push('                      <ul class="summary-badge-list">\n');

                giftPrmtFreeList.forEach(item => {
                    const popupUrlMo = item.popupUrlMo ? item.popupUrlMo.replace('/m/event/eventDetail.do', '/m/event/eventDetailAdminView.do') : '';
                    arr.push('                        <li class="summary-badge-list__item">\n');
                    arr.push('                          <div class="summary-badge-list__item-img">\n');
                    arr.push(`                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                    arr.push('                          </div>\n');
                    if (popupUrlMo) {
                        arr.push(`    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`);
                    } else {
                        arr.push(`    					<p>${item.giftText}</p>\n`);
                    }
                    arr.push('                        </li>\n');
                });

                arr.push('                      </ul>\n');
                arr.push('                    </div>\n');
                arr.push('                  </div>\n');
                arr.push('                </div>\n');
            }

            arr.push('              </div>\n');
            arr.push('            </div>\n');
            arr.push('          </div>\n');
            arr.push('        </div>\n');
            arr.push('      </div>\n');
            arr.push('    </div>\n');
            arr.push('  </div>\n');
            arr.push('</div>\n');
        }

        targetObj.html(arr.join(''));
    }

    // 배너 동적 형성 대상 존재 시 div.rate-banner 태그에 event-summary 클래스 추가
    if(0 < $(".rate-banner__item").length){
        $(".rate-banner-list").parent().addClass("event-summary");
    }

    //첫번째 혜탹요약은 혜택보기 펼처짐
    let giftPrmtShownCount = 0;

    //배너 동적 형성
    $(".rate-banner__item").each(function (index){
        var _that = $(this);
        var rateAdsvcCd = $(this).data("rateAdsvcCd");
        if (rateAdsvcCd != null) {
            var varData = ajaxCommon.getSerializedData({
                rateAdsvcCd:rateAdsvcCd
               ,adminEventDate: $('#adminEventDate').val() + "0000"
            });

            ajaxCommon.getItemNoLoading({
                    id:'getAdsvcDtl'
                    ,cache:false
                    ,async:false
                    ,url:'/rate/getRateDtlInfoAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (jsonObj.rateAdsvcGdncBas !== null && typeof  jsonObj.rateAdsvcGdncBas === 'object') {
                        setBannerTemplate(jsonObj,_that,index);
                    }

                });
        }
    });




    var getRowTemplate = function(benefit){
        var arr =[];
        arr.push("<li class='event-summary__ltem'>\n");
        arr.push("  <div class='event-summary__info'>\n");
        if (benefit.benefitDtlImgNm != "" ) {
            arr.push("    <img src='"+benefit.benefitDtlImgNm+"' alt=''>\n");
        } else {
            arr.push("    <img src='/resources/images/mobile/content/img_event_summary_noimage.png ' alt=''>\n");
        }
        arr.push("    <div class='event-summary__text'>\n");
        arr.push("      <strong>"+benefit.benefitDtlNm+"</strong>\n");
        arr.push("      <p>"+benefit.benefitDtlDesc+"</p>\n");
        arr.push("    </div>\n");
        arr.push("  </div>\n");
        arr.push("</li>\n");
        return arr.join('');
    }


    var getEventBenefit = function() {

        let ntcartSeq = $("[name=ntcartSeq]").eq(0).val();

        if (ntcartSeq == null || ntcartSeq == "") {
            return;
        }

        var varData = ajaxCommon.getSerializedData({
            ntcartSeq:ntcartSeq
        });

        ajaxCommon.getItemNoLoading({
            id:'getEventBenefitAjax'
            ,cache:false
            ,url:'/event/getEventBenefitAjax.do'
            ,data: varData
            ,dataType:"json"
        }, function(jsonObj){

            var $tdtRelationList = $("#eventSummaryModal .event-summary__list");

            if (jsonObj == null || jsonObj.benefitDtlList.length > 0) {
                $("#divEventSummary").show();
                $("#eventSummaryTitle").html("혜택 요약<span>"+ jsonObj.pstngStartDate +" ~ "+ jsonObj.pstngEndDate  +"</span");

                let listObj = jsonObj.benefitDtlList ;

                $tdtRelationList.empty();
                for (let benefit of listObj) {
                    var listHtml = getRowTemplate(benefit)  ;
                    $tdtRelationList.append(listHtml);
                }
            }
        })
    }();


});

function detailView(){
    $("#param").val("?ntcartSeq=" + $('#ntcartSeq').val() + "&sbstCtg=" + $('#sbstCtg').val() + "&eventBranch=" + $('#eventBranch').val());

}

function share(){
    $('#bsSample1').show();
}

function goList(){

    var pageNo = $("#pageNo").val();
    if(paramValid(pageNo) == false || isNaN(pageNo)){
        pageNo = 1;
    }

    location.href = "/m/event/eventList.do?order="+$("#order").val()+"&chkChoice="+$("#chkChoice").val()+"&selectEvt="+$("#selectEvt").val()+"&pageNo="+pageNo+"&sbstCtg="+$("#sbstCtg").val();
}

//탭 이동
function goEventView(url){
    ajaxCommon.createForm({
        method:"post"
        ,action: url
    });
    ajaxCommon.formSubmit();
}

// 카카오톡 공유하기


function kakaoShareNe() {
    var eventImg = $("#listImg").val();
    let ntcartSeq = $('#ntcartSeq').val();
    var mobileLink = "/m/event/eventDetail.do?ntcartSeq=" + ntcartSeq;
    var webLink = "/event/eventDetail.do?ntcartSeq=" + ntcartSeq;
    var ntcartSubject = $("#ntcartSubjectRepace").val() ;


    var varData = ajaxCommon.getSerializedData({
        trtmRsltSmst:ntcartSeq
        ,prcsSbst:webLink
    });

    ajaxCommon.getItem({
            id:'frndSendAjax'
            ,cache:false
            ,url:'/event/setKaKaoShareEventAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            let uetSeq = jsonObj.UET_SEQ;
            var v_link = "https://www.ktmmobile.com";
            var v_img = v_link + eventImg;
            var v_mobileLink = v_link + mobileLink;
            var v_webLink = v_link + webLink;

            let paraObj = {
                objectType: 'feed',
                content: {
                    title: ntcartSubject,
                    description: '',
                    imageUrl:v_img,
                    link: {
                        mobileWebUrl: v_mobileLink,
                        webUrl: v_webLink,
                    },
                },
                buttons: [
                    {
                        title: '자세히 보기',
                        link : {
                            mobileWebUrl : v_mobileLink,
                            webUrl : v_webLink,
                        },
                    },
                ],
                serverCallbackArgs: {
                    strUetSeq: uetSeq +'' , // 사용자 정의 파라미터 설정
                },
            }



            kakaoShareV2(paraObj);

        });


}
// facebook 공유하기
function facebookShare(){
    var v_link = "/m/event/eventDetail.do?ntcartSeq=" + $('#ntcartSeq').val()
                   + "&sbstCtg=" + $('#sbstCtg').val()
                   + "&eventBranch=" + $('#eventBranch').val();
    facebook_share(v_link);
}
// Line 공유하기
function lineShare(){
    var txt = $('.ly-page__head').text().trim() + " | kt M모바일 공식 다이렉트몰";
    var v_link = "/m/event/eventDetail.do?ntcartSeq=" + $('#ntcartSeq').val()
                   + "&sbstCtg=" + $('#sbstCtg').val()
                   + "&eventBranch=" + $('#eventBranch').val();
    line_share(txt,v_link);
}

function goView(ntcartSeq, sbstCtg){
    var eventBranch = $('#eventBranch').val();
    var adminEventDate = $('#adminEventDate').val();

    var url = "/m/event/eventDetailAdminView.do?ntcartSeq=" + ntcartSeq + "&sbstCtg=" + sbstCtg + "&eventBranch=" + eventBranch + "&adminEventDate=" + adminEventDate;
    location.href = url;
}

function flexibleComponentByDate(componentId) {

    ajaxCommon.getItem({
            url: "/termsPopByDate.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=INFOPRMT&cdGroupId2=" + componentId + "&adminEventDate=" + $("#adminEventDate").val(),
            async: false
        }
        ,function(data) {
            if (data.docContent != null) {
                var inHtml = unescapeHtml(data.docContent);
                $('#' + componentId).html(inHtml);
            }

            for(var i=0; i<$("#codeLength").val(); i++){
                if(data.pHolder[i] != null){

                 $('#textareaA').attr('placeholder',unescapeHtml(data.pHolder));
                 var newLink = $('<a>', {
                     href: '/m/event/eventJoin.do?proMoNum='+$("#selEventVal").val(),
                     title: '댓글 이벤트 이동하기',
                     class: 'c-text-link--bluegreen c-flex--inline',
                     text: '더 많은 댓글 보러가기'
                   });
                   $('#eventJoinLink').html(newLink);

                  if(data.cntrList != ""){
                      var newHasVal = $('<div>',{
                          class : 'c-form__group',
                          'role':'group',
                          'aria-labelledby' : 'inpG'
                      });


                      var newDiv = $('<div>', {
                           class: 'c-form__select has-value',
                           'data-function-initialized' : 'initialized'
                         });

                         var newSelect = $('<select>', {
                           class: 'c-select',
                           id: 'selectA',
                           name: 'phone'
                         }).append($('<option>', {
                           value: '',
                           'data-maskedtelno': '',
                           text: ''
                         }));

                         var newLabel = $('<label>', {
                           class: 'c-form__label',
                           for: 'selectA',
                           text: '휴대폰 번호'
                         });

                         newDiv.append(newSelect,newLabel);
                         newHasVal.append(newDiv);
                     $('#phoneSelect').html(newHasVal);

                     data.cntrList.forEach(function(item) {
                         if(item.subStatus == "A" && item.cntrMobileNo !== ""){
                             $('#selectA').append($('<option>', {
                                 value: item.cntrMobileNo,
                                 'data-maskedtelno': item.mkMobileNo,
                                 text: item.mkMobileNo
                             }));
                         }
                     });
                     $('#selectA option[value=""]').remove();
                  }
             }
         }


        }
    );
}


//이벤트 내용의 아코디언 기능
$(document)
.off('click','.flexible .c-accordion__button')
.on('click','.flexible .c-accordion__button',function() {
    $(this).parent().next().stop().slideToggle(300);
    $(this).parent().toggleClass('is-active');
});


//팝업의 탭 기능
$(document).on("click", ".c-modal .c-tabs__button", function () {
    var tabButton = $('.c-modal .c-tabs__button');
    var tabPanel = $('.c-modal .c-tabs__panel');
    var tabId = $(this).attr('id');

    $(tabButton).removeClass('is-active').attr({"aria-hidden": "false", "tabindex": "-1" });
    $(this).addClass('is-active').attr({"aria-hidden": "true", "tabindex": "0" });

    $(tabPanel).attr({"aria-hidden": "true", "tabindex": "-1" }).css("display", "none");
    $('.c-modal .c-tabs__panel[aria-labelledby =' + tabId + ']').attr({"aria-hidden": "false", "tabindex": "0"}).css("display", "block");
});

/* 요금제 혜택 아코디언 */
$(document)
.off('click', '.c-accordion--summary > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button')
.on('click', '.c-accordion--summary > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button', function() {
    var $btn = $(this);
    var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
    var isActive = $btn.hasClass('is-active');

    $btn.toggleClass('is-active', !isActive);
    $btn.attr('aria-expanded', !isActive);

    $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
    $panel.attr('aria-hidden', isActive);
});


function getEventPopupAjax(popupSeq){
    var varData = ajaxCommon.getSerializedData({
        popupSeq : popupSeq
   });

    if (popupSeq == null || popupSeq == "") {
        return;
    }

    var varData = ajaxCommon.getSerializedData({
        popupSeq:popupSeq
    });

    ajaxCommon.getItemNoLoading({
        id:'getEventPopupAjax'
        ,cache:false
        ,url:'/event/getEventPopupAjax.do'
        ,data: varData
        ,dataType:"json"
    }, function(jsonObj){
        let innerHtml = "";
        innerHtml = jsonObj.popupSbst;

        //화면에 표시
        $("#eventEditorDiv").html(innerHtml);
        //팝업 실행
        var me = document.querySelector('#eventModalEditor');
        var editModal = new KTM.Dialog(me);
        editModal.open();
    });
}

function ktDcFeeBtn() {

    var varData = ajaxCommon.getSerializedData({
        cdGroupId1: "INFO",
        cdGroupId2: "COMB00014"
    });

    ajaxCommon.getItemNoLoading({
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: varData,
            async: false
        }
        ,function(data) {
            if (data.docContent != null) {
                var incdNm = data.dtlCdNm;
                var inContent = unescapeHtml(data.docContent);
                $('#ktDtlCdNm').html(incdNm);
                $('#ktDocContent').html(inContent);

                // 첫 번째 탭 클릭 이벤트 강제 실행
                var firstTab = $('.c-modal .c-tabs__button').first();
                firstTab.trigger("click");
            }
        }
    );
}