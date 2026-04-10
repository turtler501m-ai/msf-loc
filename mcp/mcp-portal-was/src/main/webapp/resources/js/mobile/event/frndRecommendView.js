;






$(document).ready(function(){
    let giftPrmtShownCount = 0;
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
        } else {
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

        setUlRecommendRateList(obj,targetObj,index);
    }

    var setUlRecommendRateList = function(obj,targetObj,index) {
        var arr = [];
        let rateAdsvcCd = targetObj.data("rateAdsvcCd");
        let gubun = targetObj.attr("gubun");


        arr.push(`<li class="rate-banner__item">\n`);
        arr.push(`    <div class="rate-content-wrap">\n`);
        if(gubun == 'E'){
            arr.push(`    <a class='rate-banner__content' href='/appForm/eSimSelfView.do?rateCd=${rateAdsvcCd}' title='${obj.rateAdsvcGdncBas.rateAdsvcNm} 페이지 이동'>\n`);
        } else {
            arr.push(`    <a class='rate-banner__content' href='/appForm/appFormDesignView.do?rateCd=${rateAdsvcCd}&onOffType=5' title='${obj.rateAdsvcGdncBas.rateAdsvcNm} 페이지 이동'>\n`);
        }


        if (obj.rateGdncBannerList.length > 0) {
            const item = obj.rateGdncBannerList[0];
            const bannerHTML = `
                <div class='rate-banner__label-wrap'>
                    <span class='rate-banner__label' style='color:${item.bannerColor}; background-color:${item.bannerBackColor};'>
                        <em>${item.bannerSbst}</em>
                    </span>
                </div>
            `;
            arr.push(bannerHTML);
        }

        arr.push(`            <div class="rate-banner__title-wrap">\n`);
        arr.push(`                <div class="rate-banner__title">\n`);
        arr.push(`                    <strong>${obj.rateAdsvcGdncBas.rateAdsvcNm}</strong>\n`);
        arr.push(`                </div>\n`);
        arr.push(`            </div>\n`);
        arr.push(`            <div class="rate-banner__detail">\n`);
        arr.push(`                <ul class="rate-banner__detail-list">\n`);
        arr.push(`                    <li class="rate-banner__detail-item">\n`);
        arr.push(`                        <span class="c-hidden">데이터</span>\n`);
        arr.push(`                        <div class="rate-banner__detail-info">\n`);
        if (obj.rateAdsvcBnfitGdncMap.RATEBE01?.rateAdsvcItemDesc) {
            arr.push(`                        <p>${obj.rateAdsvcBnfitGdncMap.RATEBE01.rateAdsvcItemDesc}</p>`);
        }

        if (obj.rateAdsvcBnfitGdncMap.RATEBE31?.rateAdsvcItemDesc) {
            arr.push(`                        <p>${obj.rateAdsvcBnfitGdncMap.RATEBE31.rateAdsvcItemDesc}</p>`);
        }
        arr.push(`                        </div>\n`);
        arr.push(`                    </li>\n`);
        arr.push(`                </ul>\n`);
        arr.push(`            </div>\n`);
        arr.push(`            <div class="rate-banner__detail-btn-wrap">\n`);
        arr.push(`                <div class="rate-banner__detail-btn">\n`);
        arr.push(`                    <span>${obj.rateAdsvcGdncBas.promotionAmtVatDesc}원<i class="c-icon c-icon--arrow-round-ty2" aria-hidden="true"></i></span>\n`);
        arr.push(`                </div>\n`);
        arr.push(`            </div>\n`);
        arr.push(`        </a>\n`);
        arr.push(`    </div>\n`);
        arr.push(`</li>\n`);

        $("#ulRecommendRateList").append(arr.join(''));

    }



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



    //이름 위 포인트
    function apply(el) {
        if (!el || el.dataset.wrapped === '1') return;

        const name = (el.dataset.name || el.textContent || '').trim();
        if (!name) return;

        el.dataset.name = name;
        el.textContent = '';

        for (const ch of name) {
            const s = document.createElement('span');
            s.textContent = ch;
            el.appendChild(s);
        }

        el.dataset.wrapped = '1';
    }


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


    function init() {
        document.querySelectorAll('.friend-name-wrap').forEach(apply);

        const obs = new MutationObserver(muts => {
            for (const m of muts) {
                const node = m.target.nodeType === 3 ? m.target.parentElement : m.target;
                const t = node?.closest?.('.friend-name-wrap');
                if (t) {
                    t.dataset.wrapped = '';
                    apply(t);
                }
            }
        });

        obs.observe(document.body, { subtree: true, childList: true, characterData: true });
    }

    init();

});




