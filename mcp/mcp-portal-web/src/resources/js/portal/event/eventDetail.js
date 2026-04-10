$(document).ready(function(){

    /*history.pushState(null, null,location.href);
    window.onpopstate = function (event){
        history.go("/event/eventDetail.do");
    }*/

    //공유하기 버튼
    $(document).on("click",".c-icon--share",function(){
        $("#param").val("?ntcartSeq=" + $('#ntcartSeq').val()
                        + "&sbstCtg=" + $('#sbstCtg').val()
                        + "&eventBranch=" + $('#eventBranch').val());

    });

    for (var i = 0; i < document.getElementsByClassName('flexible').length; i++) {
        var id = document.getElementsByClassName('flexible')[i].id;
        flexibleComponent(id);
        $("#codeLength").val(i+1);
    }



    var setBannerTemplate = function(obj,targetObj,index) {

        // <option value="RATEBE01" selected="">요금제제공량(데이터(노출문구))</option>
        let rateAdsvcCd = targetObj.data("rateAdsvcCd");
        let gubun = targetObj.attr("gubun");

        // 혜택요약 관련 정보
        let giftPrmtInfo = obj.rateGiftPrmtListDTO;
        let giftPrmtList = (giftPrmtInfo == null) ? null : giftPrmtInfo.rateGiftPrmtList;
        let giftPrmtWireList = (giftPrmtInfo == null) ? null : giftPrmtInfo.wireRateGiftPrmtList;
        let giftPrmtFreeList = (giftPrmtInfo == null) ? null : giftPrmtInfo.freeRateGiftPrmtList;
        let mainGiftPrmtList = (giftPrmtInfo == null) ? null : giftPrmtInfo.mainGiftPrmtList;
        let giftPrmtFlag = false;

        if (mainGiftPrmtList != null && mainGiftPrmtList.length > 0) {
                giftPrmtFlag = true;
        }

        var arr = [];

        // 혜택요약 존재 시 div태그 추가
        if(giftPrmtFlag){
          arr.push('<div class="rate-content-wrap">\n');
        }

        if(gubun == 'E'){
            arr.push(`<a class='rate-banner__content' href='/appForm/eSimSelfView.do?rateCd=${rateAdsvcCd}' title='${obj.rateAdsvcGdncBas.rateAdsvcNm} 페이지 이동'>\n`);
        }else{
             arr.push(`<a class='rate-banner__content' href='/appForm/appFormDesignView.do?rateCd=${rateAdsvcCd}&onOffType=5' title='${obj.rateAdsvcGdncBas.rateAdsvcNm} 페이지 이동'>\n`);
        }

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
        arr.push(`            <i class='c-icon c-icon--arrow-bold' aria-hidden='true'></i>\n`);
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
        arr.push(`                <span class='max-data-label'>\n`);
        if (obj.rateAdsvcBnfitGdncMap.RATEBE95?.rateAdsvcItemDesc) {
             arr.push(`<em>${obj.rateAdsvcBnfitGdncMap.RATEBE95.rateAdsvcItemDesc}</em>`);
        }
        arr.push(`                </span>\n`);
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

            //체감가 툴팁
            arr.push(`<div class="rate-banner__benefit">`);
            arr.push(`  <div class="perceived-price">`);
            arr.push(`    <button class="perceived-price-btn" role="button" data-tpbox="#${tooltipId}" aria-describedby="${tooltipId}__title" onclick="event.stopPropagation(); event.preventDefault();">`);
            arr.push(`      <b>체감가</b><i class="c-icon c-icon--tooltip" aria-hidden="true"></i>`);
            arr.push(`    </button>`);
            arr.push(`    <div class="c-tooltip-box" id="${tooltipId}" role="tooltip">`);
            arr.push(`      <div class="c-tooltip-box__title">체감가</div>`);
            arr.push(`      <div class="c-tooltip-box__content">프로모션 혜택을 ${data.customMonths}개월로 분할할 경우를 계산한 금액으로, 청구서상 실제 납부 금액과는 상이할 수 있습니다.</div>`);
            arr.push(`    </div>`);

            arr.push(`    <ul class="perceived-price-list">`);

            //제휴 혜택(없음 00이 아닌 경우에만)
            if (data.jehuPriceReflectCd !== "00") {
                arr.push(`      <li class="perceived-price-list__item">`);
                arr.push(`        <div class="perceived-img"><img src="${data.jehuImgUrl}" alt="${data.jehuPriceReflectNm}"></div>`);
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
            arr.push(`        <div class="perceived-img"><img src="${data.promotionImgUrl}" alt="프로모션 혜택"></div>`);
            arr.push(`        <div class="perceived-desc">`);
            arr.push(`          <b class="perceived-desc__title">프로모션 혜택</b>`);
            arr.push(`          <span>${promoAmtDisplay} ÷ ${data.customMonths}개월 = - 월 ${Number(data.promotionBenefitAmt).toLocaleString()} 원</span>`);
            arr.push(`        </div>`);
            arr.push(`        <div class="rate-banner__perceive-price">`);
            arr.push(`          <span>월 체감가</span>`);
            arr.push(`          <p>${Number(data.monthlyEffPrice).toLocaleString()}원</p>`);
            arr.push(`          <button class="perceived-price__tooltip-btn" role="button" data-tpbox="#perceivedTp${tooltipId}" aria-describedby="#perceivedTp${tooltipId}" onclick="event.stopPropagation(); event.preventDefault();">`);
            arr.push(`            <i class="c-icon c-icon--tooltip" aria-hidden="true"></i>`);
            arr.push(`          </button>`);
            arr.push(`          <div class="c-tooltip-box" id="perceivedTp${tooltipId}" role="tooltip" onclick="event.stopPropagation(); event.preventDefault();">`);
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

            //첫번째 혜택요약은 혜택보기 펼처짐
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
            arr.push('        <div class="c-accordion__head">\n');
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
            arr.push(`          <button class="runtime-data-insert c-accordion__button${isFirstGiftPrmt ? ' is-active' : ''}" type="button" aria-expanded="${isFirstGiftPrmt ? 'true' : 'false'}">\n`);
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
                const popupUrl = item.popupUrl || '';
                arr.push('                        <li class="summary-badge-list__item">\n');
                arr.push('                          <div class="summary-badge-list__item-img">\n');
                arr.push(`                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                arr.push('                          </div>\n');
                if (popupUrl) {
                    arr.push(`    					<p onclick="event.stopPropagation();window.open('${popupUrl}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`);
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

            //인터넷 혜택
            if (giftPrmtWireList.length > 0) {
              arr.push('                <div class="c-accordion__item">\n');
              arr.push('                  <div class="c-accordion__head">\n');
              arr.push('                    <strong class="c-accordion__title">\n');
              if (totalWirePrice > 0) {
                    arr.push(`              	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${totalWirePrice / 10000}</em>만원</p>\n`);
                  } else {
                    arr.push(`                  <p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${giftPrmtWireList.length}</em>개</p>\n`);
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
                const popupUrl = item.popupUrl || '';
                arr.push('                        <li class="summary-badge-list__item">\n');
                arr.push('                          <div class="summary-badge-list__item-img">\n');
                arr.push(`                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                arr.push('                          </div>\n');
                if (popupUrl) {
                    arr.push(`    					<p onclick="event.stopPropagation();window.open('${popupUrl}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`);
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

            //무료 혜택
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
                const popupUrl = item.popupUrl || '';
                arr.push('                        <li class="summary-badge-list__item">\n');
                arr.push('                          <div class="summary-badge-list__item-img">\n');
                arr.push(`                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                arr.push('                          </div>\n');
                if (popupUrl) {
                    arr.push(`    					<p onclick="event.stopPropagation();window.open('${popupUrl}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`);
                } else {
                    arr.push(`   					<p>${item.giftText}</p>\n`);
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



    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
/*        if($('#eventBranch').val() == 'E'){
            goEventView("/event/eventBoardList.do");
        }else{
            goEventView("/event/cprtEventBoardList.do");
        }*/
        goEventView("/event/eventBoardList.do");
    });

     $("#tab2").click(function() {
        $("#eventStatus").val('end');
        if($('#eventBranch').val() == 'E'){
            goEventView("/event/eventBoardEndList.do");
        }else{
            goEventView("/event/cprtEventBoardEndList.do");
        }
    });

    $("#tab3").click(function() {
        $("#eventStatus").val('win');
/*        if($('#eventBranch').val() == 'E'){
            goEventView("/event/winnerList.do");
        }else{
            goEventView("/event/cprtEventWinnerList.do");
        }*/
        goEventView("/event/winnerList.do");
    });


    var getRowTemplate = function(benefit){
        var arr =[];
        arr.push("<li class='event-summary__ltem'>\n");
        arr.push("  <div class='event-summary__info'>\n");
        if (benefit.benefitDtlImgNm != "" ) {
            arr.push("    <img src='"+benefit.benefitDtlImgNm+"' alt=''>\n");
        } else {
            arr.push("    <img src='/resources/images/portal/content/img_event_summary_noimage.png' alt=''>\n");
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

                let listObj = jsonObj.benefitDtlList;

                $tdtRelationList.empty();
                for (let benefit of listObj) {
                    var listHtml = getRowTemplate(benefit)  ;
                    $tdtRelationList.append(listHtml);
                }
            }
        })
    }();

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

});







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
   //kakao_share(eventImg, mobileLink, webLink);

    var ntcartSubject = $("#ntcartSubjectRepace").val() ;

    /*
    eventImg = "/resources/upload/fileBoard/202502150021110031NZ8dg.png";
    mobileLink = "/event/eventDetail.do?ntcartSeq=954" ;
    webLink ="/event/eventDetail.do?ntcartSeq=954" ;
    ntcartSubject = "친구초대 ID입력 후 가입 시 무려 8만원 혜택💰" ;
    */

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
    var v_link = "/event/eventDetail.do?ntcartSeq=" + $('#ntcartSeq').val()
                   + "&sbstCtg=" + $('#sbstCtg').val()
                   + "&eventBranch=" + $('#eventBranch').val();
    facebook_share(v_link);
}
// Line 공유하기
function lineShare(){
    var txt = $('.c-page--title').html() + " | kt M모바일 공식 다이렉트몰";
    var v_link = "/event/eventDetail.do?ntcartSeq=" + $('#ntcartSeq').val()
                   + "&sbstCtg=" + $('#sbstCtg').val()
                   + "&eventBranch=" + $('#eventBranch').val();
    line_share(txt,v_link);
}

function goView(ntcartSeq, sbstCtg){
    var eventBranch = $('#eventBranch').val();

    var url = "/event/eventDetail.do?ntcartSeq=" + ntcartSeq + "&sbstCtg=" + sbstCtg + "&eventBranch=" + eventBranch;
    location.href = url;


}


function flexibleComponent(componentId) {

    ajaxCommon.getItem({
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

            for(var i=0; i<$("#codeLength").val(); i++){
                   if(data.pHolder[i] != null){

                    $('#textareaA').attr('placeholder',unescapeHtml(data.pHolder));
                    var newLink = $('<a>', {
                        href: '/event/eventJoin.do?proMoNum='+$("#selEventVal").val(),
                        title: '댓글 이벤트 이동하기',
                        class: 'c-text-link--bluegreen',
                        text: '더 많은 댓글 보러가기'
                      });
                      $('#eventJoinLink').html(newLink);

                     if(data.cntrList != ""){
                          var newDiv = $('<div>', {
                              class: 'c-form__select u-mt--0'
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

                            newDiv.append(newSelect, newLabel);
                        $('#phoneSelect').html(newDiv);

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


// 이벤트 내용의 아코디언 기능
$(document).on('click','.flexible .c-accordion__button',function() {
    var hasClass = $(this).hasClass('is-active');
    if(hasClass){
        $(this).removeClass('is-active');
        $(this).parent().parent().find('.c-accordion__panel').stop().slideUp();
    } else {
        $(this).addClass('is-active');
        $(this).parent().parent().find('.c-accordion__panel').stop().slideDown();
    };
});


// 팝업의 탭 기능
$(document).on("click", ".c-modal .c-tabs__button", function () {
    var tabButton = $('.c-modal .c-tabs__button');
    var tabPanel = $('.c-modal .c-tabs__panel');
    var tabId = $(this).attr('id');

    $(tabButton).removeClass('is-active').attr({"aria-hidden": "false", "tabindex": "-1" });
    $(this).addClass('is-active').attr({"aria-hidden": "true", "tabindex": "0" });

    $(tabPanel).attr({"aria-hidden": "true", "tabindex": "-1" }).css("display", "none");
    $('.c-modal .c-tabs__panel[aria-labelledby =' + tabId + ']').attr({"aria-hidden": "false", "tabindex": "0"}).css("display", "block");
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

function planPop(rateCode,move){

    if (rateCode != null) {
        var varData = ajaxCommon.getSerializedData({
            rateAdsvcCd:rateCode
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
                if (jsonObj.rateAdsvcGdncBas !== null ) {
                    if(move == 'M'){
                                location.href = "/rate/rateList.do?eventRatePop=Y&rateAdsvcGdncSeq="+jsonObj.rateAdsvcGdncBas.rateAdsvcGdncSeq+"&rateAdsvcCd="+rateCode;
                    }else{
                        openPage('pullPopup', '/rate/rateLayer.do?rateAdsvcGdncSeq='+jsonObj.rateAdsvcGdncBas.rateAdsvcGdncSeq+'&rateAdsvcCd='+rateCode+'', '');
                    }
                }
            });
    }
}