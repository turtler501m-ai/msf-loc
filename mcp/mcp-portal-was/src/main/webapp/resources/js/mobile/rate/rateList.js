;

var pageObj = {
    ctgList:[]
    ,prodCount:-1
    ,mainIndexCnt:1
    ,subIndexCnt:0
    ,mainSubindex:[]
    ,rateCompareList:[]
    ,orderEnum:"RECOMM_ROW"
    ,compareCnt:0
}


$(document).ready(function(){

    $("#modalArs").addClass("rate-detail-view");

    if($("#eventRatePop").val() == "Y"){

        var eventRateSeq = $("#eventRateSeq").val();
        var eventRateCd = $("#eventRateCd").val();

        setTimeout(function(){
               openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcGdncSeq='+eventRateSeq+'&rateAdsvcCd='+eventRateCd+'', '');

               $(".c-button").click(function() {
                   history.replaceState({}, null, location.pathname);
         });

        }, 200);

    }

    var getCtgList = function () {
        var varData = ajaxCommon.getSerializedData({
            rateAdsvcDivCd:"RATE"
        });

        ajaxCommon.getItemNoLoading({
            id:'getCtgList'
            ,cache:false
            ,url:'/rate/getCtgXmlAllListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            pageObj.ctgList = jsonObj;
            let html = "";
            let indexCnt = 1;
            $.each(pageObj.ctgList, function(idx, item) {
                if(item.depthKey == 1 && item.ctgOutputCd == "CO00130001") {
                    html += '<button class="c-tabs__button fs-12 _mainTabs" href="javascript:void(0);" data-rate-adsvc-ctg-cd="'+item.rateAdsvcCtgCd+'" data-index-cnt="'+indexCnt+'"  >'+item.rateAdsvcCtgNm+'</button>';
                    indexCnt++;
                    pageObj.mainSubindex.push(0);
                }
            });
            $("#mainTab").append(html);
        });
    } ();

    var getProdCount = function(rateAdsvcCtgCd){
        let prodCount = 0;
        $.each(pageObj.ctgList, function(idx, item) {
            if(item.depthKey == 2 && rateAdsvcCtgCd == item.upRateAdsvcCtgCd) {
                prodCount += item.relCnt;
            }
        });
        return prodCount;
    }

    var getSubTabPanel = function(rateAdsvcCtgCd) {
        let html = "";
        let indexCnt = 1;
        $.each(pageObj.ctgList, function(idx, item) {
            if(item.depthKey == 2 && rateAdsvcCtgCd == item.upRateAdsvcCtgCd) {
                html += '<button id="subTab_'+idx+'"  data-rate-adsvc-ctg-cd="'+item.rateAdsvcCtgCd +'" class="c-tabs__button _subTabs" data-index-cnt="'+indexCnt+'"  >'+item.rateAdsvcCtgNm +'</button>' ;
                indexCnt++;
            }
        });
        $('#subTabPanel').html(html);
    }

    var setListPanel = function (rateAdsvcCtgCd) {
        var arr =[];

        arr.push('<div id="listPanel_'+pageObj.mainIndexCnt+'_'+pageObj.subIndexCnt+'" className="c-tabs__panel ">\n');
        arr.push('    <div class="c-accordion c-accordion--type1 blue-inside u-mb--24">\n');
        arr.push('        <ul class="c-accordion__box" id="accordion_'+pageObj.mainIndexCnt+'_'+pageObj.subIndexCnt+'"></ul>\n');
        arr.push('    </div>\n');
        arr.push('</div>\n');

        $("#divDraw").append(arr.join(''));
        setTimeout(getRateListPanel(rateAdsvcCtgCd), 1000);
    }

    var getRateListPanel = function(rateAdsvcCtgCd) {
        let tempIndex = "_"+ pageObj.mainIndexCnt+"_"+pageObj.subIndexCnt;
        let $listPanel = $("#listPanel"+tempIndex);
        $('div[id^=listPanel_]').hide();

        //div 존재하며
        if ($listPanel.length > 0) {
            $listPanel.show();
            //return;
        } else {
            //각각에 div 생성
            setListPanel(rateAdsvcCtgCd);
        }
        let $targetDom = $listPanel.find(".c-accordion__box");



        if ($targetDom .html() != "") {
            return;
        }

        let html = "";
        let checkDepth =  2;
        if (pageObj.prodCount > 0) {
            checkDepth =  2;
        }  else {
            checkDepth =  3;
        }
        var arr =[];
        $.each(pageObj.ctgList, function(idx, item) {
            if(item.depthKey == checkDepth && rateAdsvcCtgCd == item.upRateAdsvcCtgCd) {

                arr.push('<li class="c-accordion__item">\n');
                arr.push('	<div class="c-accordion__head" data-acc-header>\n');
                if (item.rateAdsvcCtgImgNm != "") {
                    arr.push('	<div class="c-accordion__image">\n');
                    arr.push('	    <img src="'+item.rateAdsvcCtgImgNm+'" alt="'+item.rateAdsvcCtgImgNm+'">\n');
                    arr.push('	</div>\n');
                }
                arr.push('		<button class="c-accordion__button" type="button"  data-rate-adsvc-ctg-cd="'+item.rateAdsvcCtgCd+'" aria-expanded="false" >'+item.rateAdsvcCtgNm+' \n');
                arr.push('			<p class="c-text c-text--type4 u-mt--8">'+item.rateAdsvcCtgBasDesc+'</p>\n');
                arr.push('		</button>\n');
                arr.push('	</div><!-- 유심 요금제 상품 리스트 아코디언-->\n');
                arr.push('	<div class="c-accordion__panel expand c-expand" >\n');
                arr.push('		  <div class="c-accordion__inside"></div>\n');
                arr.push('	</div>\n');
                arr.push('</li>\n');
            }
        });

        $targetDom.html(arr.join(''));
        $listPanel.show();
        MCP.init();
    }

    var getRowTemplate = function(objList , viewType){
        var arr =[];
        arr.push('    <div class="rate-content event-summary">\n');
        arr.push('    <ul class="rate-content__list">\n');

        for(var i = 0 ; i < objList.length ; i++) {
            var prod = objList[i];
            var bnfitList = prod.bnfitList;
            var bnfitList2 = prod.bnfitList2;
            var stickerCtg = prod.stickerCtg;

            // 혜택요약 관련 정보
            var giftPrmtInfo = prod.rateGiftPrmtListDTO;
            var giftPrmtList = (giftPrmtInfo == null) ? null : giftPrmtInfo.rateGiftPrmtList;
            let giftPrmtWireList = (giftPrmtInfo == null) ? null : giftPrmtInfo.wireRateGiftPrmtList;
            let giftPrmtFreeList = 	(giftPrmtInfo == null) ? null : giftPrmtInfo.freeRateGiftPrmtList;
            let mainGiftPrmtList = (giftPrmtInfo == null) ? null : giftPrmtInfo.mainGiftPrmtList;
            var giftPrmtFlag = false;

            if(mainGiftPrmtList != null && 0 < mainGiftPrmtList.length && viewType != 2){
                giftPrmtFlag = true;
            }

            arr.push('    <li class="rate-content__item">\n');

            // 혜택요약 존재 시 div태그 추가
            if(giftPrmtFlag){
                arr.push('  <div class="rate-content-wrap">\n');
            }

            arr.push('        <a href="javascript:void(0);" class="rate-info__wrap" data-dialog-trigger="#modalProduct">\n');
            arr.push('            <!--요금제 타이틀 영역 -->\n');
            arr.push('            <div class="rate-info__title">\n');
            arr.push('                <div class="rate-sticker-wrap">\n');
            if (stickerCtg.indexOf("01") > -1) {
                arr.push('                   <span class="rate-sticker bluegreen">NEW</span>\n');
            }

            if (stickerCtg.indexOf("02") > -1) {
                arr.push('                   <span class="rate-sticker red">BEST</span>\n');
            }
            arr.push('                </div>\n');
            arr.push('                <strong>'+prod.rateAdsvcNm+'</strong>\n');
            //arr.push('                  <p >'+'xmlDataCnt:'+prod.xmlDataCnt+'#xmlQosCnt:'+prod.xmlQosCnt+' </p>\n');
            arr.push('                <i class="c-icon c-icon--arrow-gray4" aria-hidden="true"></i>\n');
            arr.push('            </div>\n');
            arr.push('            <!--데이터, 음성, 문자 영역 -->\n');
            arr.push('            <div class="rate-detail">\n');
            arr.push('                <ul class="rate-detail__list">\n');

            if ( !isEmpty(prod.bnfitDataItemImgNm)  )  {
                arr.push('                <li class="rate-detail__item">\n');
                arr.push('                    <span>\n');
                arr.push('                        <i class="c-icon c-icon--data" aria-hidden="true"></i>\n');
                arr.push('                    </span>\n');
                arr.push('                    <span class="c-hidden">데이터</span>\n');
                arr.push('                    <div class="rate-detail__info">\n');
                if (!isEmpty(prod.bnfitData )  )  {
                     arr.push('                                 <span class="max-data-label">\n');
                     if ( !isEmpty(prod.maxDataDelivery)) {
                         arr.push('                            <em>'+prod.maxDataDelivery+'</em>\n')
                     }
                     arr.push('                                 </span>\n');
                    arr.push('                    <p>'+prod.bnfitData+'</p>\n');
                    if ( !isEmpty(prod.promotionBnfitData)) {
                        arr.push('                <p>'+prod.promotionBnfitData+'</p>\n')
                    }
                } else {
                    arr.push('                    <p>-</p>\n');
                }
                arr.push('                    </div>\n');
                arr.push('                </li>\n');
            }
            if (!isEmpty(prod.bnfitVoiceItemImgNm)  )  {
                arr.push('                <li class="rate-detail__item">\n');
                arr.push('                    <span>\n');
                arr.push('                        <i class="c-icon c-icon--call-gray" aria-hidden="true"></i>\n');
                arr.push('                    </span>\n');
                arr.push('                    <span class="c-hidden">음성</span>\n');
                arr.push('                    <div class="rate-detail__info ">\n');
                if (!isEmpty(prod.bnfitVoice))  {
                    arr.push('                    <p>'+prod.bnfitVoice+'</p>\n');
                    if ( !isEmpty(prod.promotionBnfitVoice)) {
                        arr.push('                <p>'+prod.promotionBnfitVoice+'</p>\n')
                    }
                } else {
                    arr.push('                    <p>-</p>\n');
                }
                arr.push('                    </div>\n');
                arr.push('                </li>\n');
            }

            if (!isEmpty(prod.bnfitSmsItemImgNm))  {
                arr.push('                <li class="rate-detail__item">\n');
                arr.push('                    <span>\n');
                arr.push('                        <i class="c-icon c-icon--msg" aria-hidden="true"></i>\n');
                arr.push('                    </span>\n');
                arr.push('                    <span class="c-hidden">문자</span>\n');
                arr.push('                    <div class="rate-detail__info">\n');
                if (!isEmpty(prod.bnfitSms))  {
                    arr.push('                    <p>'+prod.bnfitSms+'</p>\n');
                    if ( !isEmpty(prod.promotionBnfitSms)) {
                        arr.push('                <p>'+prod.promotionBnfitSms+'</p>\n')
                    }
                } else {
                    arr.push('                    <p>-</p>\n');
                }
                arr.push('                    </div>\n');
                arr.push('                </li>\n');
            }

            arr.push('                </ul>\n');
            arr.push('            </div>\n');

            if ((bnfitList != null && bnfitList.length > 0) || (bnfitList2 != null  && bnfitList2.length) ) {
                arr.push('            <!--제공 아이템 영역 -->\n');
                arr.push('        <div class="rate-info__gift">\n');
                if (bnfitList != null && bnfitList.length > 0)  {
                    arr.push('         <!--제휴 혜택 노출 문구 -->\n');
                    $.each(bnfitList, function(idx, sutItem) {
                        arr.push('    <p class="rate-info__gift-info">'+sutItem.rateAdsvcItemDesc+'</p>\n');
                    });
                }

                if (bnfitList2 != null  && bnfitList2.length > 0)  {
                    arr.push('        <!--혜택 안내 노출 문구 -->\n');
                    $.each(bnfitList2, function(idx, sutItem) {
                        arr.push('    <p class="rate-info__present-info">'+sutItem.rateAdsvcItemDesc+'</p>\n');
                    });
                }
                arr.push('        </div>\n');
            }


            arr.push('            <!--가격 영역-->\n');
            arr.push('            <div class="rate-price">\n');
            arr.push('                <div class="rate-price-wrap">\n');
            arr.push('                    <!--월 기본료 -->\n');
            arr.push('                    <div class="rate-price__item">\n');
            if (!isEmpty(prod.mmBasAmtVatDesc ) && !isEmpty(prod.promotionAmtVatDesc))  {
                arr.push('                    <p class="rate-price__title through">\n');
                arr.push('                        <span class="c-hidden">월 기본료(VAT 포함)</span>\n');
                arr.push('                        <b>'+prod.mmBasAmtVatDesc+'</b> 원\n');
                arr.push('                    </p>\n');
            }
            arr.push('                        <p class="rate-price__info">\n');
            if (!isEmpty(prod.mmBasAmtVatDesc) && !isEmpty(prod.promotionAmtVatDesc))  {
                arr.push('                        <b>'+prod.promotionAmtVatDesc+'</b> 원\n');
            } else {
                arr.push('                        <b>'+prod.mmBasAmtVatDesc+'</b> 원\n');
            }
            arr.push('                        </p>\n');
            arr.push('                    </div>\n');

            if (!isEmpty(prod.contractAmtVatDesc))  {
                arr.push('                <!--약정시 기본료 -->\n');
                arr.push('                <div class="rate-price__item">\n');
                arr.push('                    <p class="rate-price__title">약정시 기본료</p>\n');
                arr.push('                    <p class="rate-price__info">\n');
                arr.push('                        <b>'+prod.contractAmtVatDesc+'</b> 원\n');
                arr.push('                    </p>\n');
                arr.push('                </div>\n');
            }

            if (!isEmpty(prod.rateDiscntAmtVatDesc))  {
                arr.push('               <!--요금할인시 기본료 -->\n');
                arr.push('               <div class="rate-price__item">\n');
                arr.push('                   <p class="rate-price__title">요금할인시 기본료</p>\n');
                arr.push('                   <p class="rate-price__info">\n');
                arr.push('                       <b>'+prod.rateDiscntAmtVatDesc+'</b> 원\n');
                arr.push('                   </p>\n');
                arr.push('               </div>\n');
            }

            if (!isEmpty(prod.seniorDiscntAmtVatDesc))  {
                arr.push('              <div class="rate-price__item">\n');
                arr.push('                  <p class="rate-price__title">시니어할인시 기본료</p>\n');
                arr.push('                  <p class="rate-price__info">\n');
                arr.push('                      <b>'+prod.seniorDiscntAmtVatDesc+'</b> 원\n');
                arr.push('                  </p>\n');
                arr.push('              </div>\n');
            }


            arr.push('                </div>\n');
            arr.push('            </div>\n');
            arr.push('        </a>\n');

            // AI 추천 버튼이 표출되는 경우 요금제 비교함 미표출
            if($("#isAIRecommendUrl").val() !== "Y") {
                if (viewType == 2) {
                    arr.push('    <div class="rate-delete">\n');
                    arr.push('        <button class="_btnCompareDelete">\n');
                    arr.push('            <i class="c-icon c-icon--trash" aria-hidden="true"></i>\n');
                    arr.push('            <span class="c-hidden">비교함에서 삭제하기-' + prod.rateAdsvcNm + '</span>\n');
                    arr.push('        </button>\n');
                    arr.push('    </div>\n');
                } else {
                    arr.push('    <!-- 비교함 영역 -->\n');
                    arr.push('    <div class="rate-compare">\n');
                    arr.push('        <input type="checkbox" class="c-checkbox _btnCompare" id="chkBadgeAA_' + prod.rateAdsvcCtgCd + '_' + prod.rateAdsvcGdncSeq + '" value="' + prod.rateAdsvcGdncSeq + '"  >\n');
                    arr.push('        <label class="c-card__badge-label" for="chkBadgeAA_' + prod.rateAdsvcCtgCd + '_' + prod.rateAdsvcGdncSeq + '">\n');
                    arr.push('            <span class="c-hidden">비교하기</span>\n');
                    arr.push('        </label>\n');
                    arr.push('    </div>\n');
                }
            }

            // 혜택요약 존재 시 div태그 닫기 + 헤택요약 정보 그리기
            if(giftPrmtFlag){
                arr.push('         </div>\n');

                //기존 혜택요약(모바일)
                var giftTotalPrice = giftPrmtInfo.totalPrice;

                //인터넷 혜택요약
                var totalWirePrice = giftPrmtInfo.totalWirePrice || 0;

                //무료 혜택요약
                var totalFreeCount = giftPrmtInfo.totalFreeCount || 0;

                //대표 혜택요약(메인 아코디언)
                var totalMainPrice = giftPrmtInfo.totalMainPrice;
                var totalMainCount = giftPrmtInfo.totalMainCount;
                var giftPrmtThumbList = mainGiftPrmtList.slice(0,3);
                var fallbackUrl = "/resources/images/portal/content/img_phone_noimage.png";

                arr.push(`<div class="summary-accordion">\n`);
                arr.push(`  <div class="c-accordion--summary">\n`);
                arr.push(`    <div class="c-accordion__box">\n`);
                arr.push(`      <div class="c-accordion__item">\n`);
                arr.push(`        <div class="c-accordion__head">\n`);
                arr.push(`          <strong class="c-accordion__title">\n`);
                arr.push(`            <ul class="summary-badge">\n`);

                giftPrmtThumbList.forEach(item => {
                    arr.push(`          <li class="summary-badge__item">\n`);
                    arr.push(`            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`);
                    arr.push(`          </li>\n`);
                });

                arr.push(`            </ul>\n`);

                if(0 < totalMainPrice){
                    arr.push(`        <p>최대 <span class="u-co-sub-4"><em>${totalMainPrice / 10000}</em>만원</span> 혜택보기</p>\n`);
                }else{
                    arr.push(`        <p>최대 <span class="u-co-sub-4"><em>${totalMainCount}</em>개</span> 혜택보기</p>\n`);
                }

                arr.push(`          </strong>\n`);
                arr.push(`          <button class="c-accordion__button" type="button" aria-expanded="false">\n`);
                arr.push(`            <span class="c-hidden">펼쳐보기</span>\n`);
                arr.push(`          </button>\n`);
                arr.push(`        </div>\n`);

                arr.push(`        <div class="c-accordion__panel expand" aria-hidden="true">\n`);
                arr.push(`          <div class="c-accordion__inside">\n`);
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
                        const popupUrlMo = item.popupUrlMo || '';
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
                        const popupUrlMo = item.popupUrlMo || '';
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
                        const popupUrlMo = item.popupUrlMo || '';
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

            arr.push('    </li>\n');
        }
        arr.push('    </ul>\n');
        arr.push('    </div>\n');
        return arr.join('');
    }

    var isEmpty = function(obj) {
        if (obj == null || obj == "") {
            return true;
        } else {
            return false;
        }
    }

    //1단계 메뉴 tab 이벤트
    $(document).on('click', '._mainTabs', function () {
        $('#mainTab > button').removeClass('is-active');
        $(this).addClass('is-active');

        //2Depth 요금제 카테고리 목록 조회
        var rateAdsvcCtgCd = $(this).data("rateAdsvcCtgCd");
        pageObj.mainIndexCnt= $(this).data("indexCnt");
        /*console.log("rateAdsvcCtgCd===>" + rateAdsvcCtgCd)
        console.log("getProdCount===>" + getProdCount(rateAdsvcCtgCd))*/

        //console.log("pageObj.mainIndexCnt===>" + pageObj.mainIndexCnt)

        pageObj.prodCount = getProdCount(rateAdsvcCtgCd);

         if (pageObj.prodCount == 0) {
             getSubTabPanel(rateAdsvcCtgCd);
             $("button[id^=subTab_]").eq(pageObj.mainSubindex[pageObj.mainIndexCnt-1]).trigger("click");
         } else {
             pageObj.subIndexCnt= 0;
             $('#subTabPanel').html("");
             getRateListPanel(rateAdsvcCtgCd);
        }
    });

    //2단계 메뉴 tab 이벤트
    $(document).on('click', 'button[id^=subTab_]', function () {
        $('button[id^=subTab_]').removeClass('is-active');
        $(this).addClass('is-active');
        $('button[id^=subTab_]').attr('aria-hidden',"false");
        $(this).attr('aria-hidden',"true");
        var rateAdsvcCtgCd = $(this).data("rateAdsvcCtgCd");
        pageObj.subIndexCnt= $(this).data("indexCnt");
        pageObj.mainSubindex[pageObj.mainIndexCnt-1] = pageObj.subIndexCnt -1;
        //console.log("pageObj.subIndexCnt===>" + pageObj.subIndexCnt)
        //console.log("rateAdsvcCtgCd===>" + rateAdsvcCtgCd)

        getRateListPanel(rateAdsvcCtgCd);
    });




    //accordion 이벤트
    $(document).on('click', '.c-accordion__button', function () {
        var rateAdsvcCtgCd = $(this).data("rateAdsvcCtgCd");
        var index = $(this).data("index");
        var $targetObj = $(this).parent().parent().find('.c-accordion__inside')
        let contentHtml = $targetObj.html();

        if (contentHtml == "" ) {
            var varData = ajaxCommon.getSerializedData({
                rateAdsvcCtgCd:rateAdsvcCtgCd
            });

            ajaxCommon.getItemNoLoading({
                id:'rateContent'
                ,cache:false
                ,url:'/rate/rateContentAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            },function(jsonList){
                $targetObj.html(getRowTemplate(jsonList , 1));
                //$("#acc_content_a"+index).data(jsonList);
                // console.log("rate-info__wrap.length ===>"+ $targetObj.find(".rate-info__wrap").length );
                // console.log("rate-jsonList.length ===>"+jsonList.length );

                //data 설정
                for(i = 0 ; i < jsonList.length ; i++) {
                    $targetObj.find(".rate-info__wrap").eq(i).data(jsonList[i]);
                }

            });

            MCP.init();
        }
    });


    //요금제 선택 이벤트
    $(document).on('click', '.rate-info__wrap', function () {
        let rateObj = $(this).data();
        let rateAdsvcCtgCd = rateObj.rateAdsvcCtgCd;
        let rateAdsvcGdncSeq = rateObj.rateAdsvcGdncSeq;
        let rateAdsvcCd = rateObj.rateAdsvcCd;

        openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcCtgCd='+rateAdsvcCtgCd+'&rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'', '');
    });

    var setRateCompareList = function() {
        pageObj.compareCnt = $("._btnCompare:checked").length ;
        pageObj.rateCompareList= [];

        if (pageObj.compareCnt  > 0) {
            $(".rate-btn__cnt").html(pageObj.compareCnt);
            $(".rate-btn__cnt").removeClass("cnt_none");
            $('.btn-sort').removeClass('is-active').removeClass('sort');

            $('._btnCompare:checked').each(function (){
                let rateObj = $(this).parent().parent().find(".rate-info__wrap").data();
                pageObj.rateCompareList.push(rateObj);
            });
        } else {
            if ($("#rateCompareModal").is(":visible")) {
                KTM.Dialog.closeAll();
            }
            $(".rate-btn__cnt").addClass("cnt_none");
            $(".rate-btn__cnt").html(0);
        }
    }

    var viewRateCompareList = function() {
        var $targetObj = $("#rateCompareModal");
        $targetObj.find(".rate-content").html(getRowTemplate(pageObj.rateCompareList,2));
        //console.dir(pageObj.rateCompareList);
        //data 설정
        for(var i = 0 ; i < pageObj.rateCompareList.length ; i++) {
            $targetObj.find(".rate-info__wrap").eq(i).data(pageObj.rateCompareList[i]);
        }
    }


    $(document).on('click', '._btnCompare', function () {

        if ($(this).is (':checked') && pageObj.compareCnt > 19 ) {
            MCP.alert("요금제보관함이 가득 찼습니다.(20건 제한)<br/> 다른 요금제를 추가로 넣으시려면, 이미 담긴 요금제를 요금제보관함에서 비워 주세요.");
            $(this).removeProp('checked');
        } else {
            setRateCompareList();
        }

        //console.log("pageObj.rateCompareList.length==>"+ pageObj.rateCompareList.length);
    });


    $(document).on('click', '._btnCompareDelete', function () {
        let rateObj = $(this).parent().parent().find(".rate-info__wrap").data();
        $("#chkBadgeAA_"+rateObj.rateAdsvcCtgCd+"_"+rateObj.rateAdsvcGdncSeq).removeProp('checked');
        setRateCompareList();
        viewRateCompareList();
    });


    $("#btnCompareView").click(function(){
        if (pageObj.compareCnt  > 0) {
            viewRateCompareList();
            var el = document.querySelector('#rateCompareModal');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        } else {
                MCP.alert("요금제 선택 영역에서 비교하고 싶은 요금제를 담아주세요.");
        }
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

        if ("XML_CHARGE_ROW" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                //프로모션 요금이 있을때는 우선적으로 적용하고, 없다면 월 기본료 기준으
                var c1 , c2 ;

                if (isEmpty(a.promotionAmtVatDesc)) {
                    c1 = a.mmBasAmtVatDesc.replace(/,/gi, '') ;
                } else {
                    c1 = a.promotionAmtVatDesc.replace(/,/gi, '') ;
                }

                if (isEmpty(b.promotionAmtVatDesc)) {
                    c2 = b.mmBasAmtVatDesc.replace(/,/gi, '') ;
                } else {
                    c2 = b.promotionAmtVatDesc.replace(/,/gi, '') ;
                }

                var intC1 =parseInt(c1);
                var intC2= parseInt(c2);

                if (isNaN(intC1)) { intC1 = 0; }
                if (isNaN(intC2)) { intC2 =  0; }

                if (intC1 > intC2) return 1;
                else if (intC2  > intC1)return -1;
                else return 0;
            });
        } else if("XML_CHARGE_HIGH" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                //프로모션 요금이 있을때는 우선적으로 적용하고, 없다면 월 기본료 기준으
                var c1 , c2 ;

                if (isEmpty(a.promotionAmtVatDesc)) {
                    c1 = a.mmBasAmtVatDesc.replace(/,/gi, '') ;
                } else {
                    c1 = a.promotionAmtVatDesc.replace(/,/gi, '') ;
                }

                if (isEmpty(b.promotionAmtVatDesc)) {
                    c2 = b.mmBasAmtVatDesc.replace(/,/gi, '') ;
                } else {
                    c2 = b.promotionAmtVatDesc.replace(/,/gi, '') ;
                }
                var intC1 =parseInt(c1);
                var intC2= parseInt(c2);

                if (isNaN(intC1)) { intC1 = 0; }
                if (isNaN(intC2)) { intC2 =  0; }

                if (intC1 > intC2) return -1;
                else if (intC2  > intC1) return 1;
                else return 0;
            });
        } else if("XML_DATA_ROW" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                var c1 = a.xmlDataCnt ;
                var c2 = b.xmlDataCnt ;
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
                    var c3 = a.xmlQosCnt ;
                    var c4 = b.xmlQosCnt ;
                    if (c3 == undefined || c3 == "") { c3 = "0";    }
                    if (c4 == undefined || c4 == "") { c4 = "0";    }
                    var intC3 = 0 ;
                    var intC4 = 0;
                    if (!isNaN(c3)) {
                        intC3 =parseInt(c3);
                        //console.log("a.rateNm11["+a.rateNm+"]==>" + intC3);
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
            pageObj.rateCompareList.sort(function (a, b) {
                var c1 = a.xmlDataCnt ;
                var c2 = b.xmlDataCnt ;
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
                    var c3 = a.xmlQosCnt ;
                    var c4 = b.xmlQosCnt ;
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
        }  else if ("XML_VOICE_ROW" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                var c1 = a.xmlCallCnt ;
                var c2 = b.xmlCallCnt ;
                if (c1 > c2) return 1;
                else if (c2  > c1)return -1;
                else return 0;
            });
        } else if("XML_VOICE_HIGH" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                var c1 = a.xmlCallCnt ;
                var c2 = b.xmlCallCnt ;

                if (c1 > c2) return -1;
                else if (c2  > c1) return 1;
                else return 0;
            });
        } else if("CHARGE_NM_ROW" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                if (a.rateAdsvcNm   > b.rateAdsvcNm) return 1;
                else if (b.rateAdsvcNm > a.rateAdsvcNm) return -1;
                else return 0;
            });
        } else if("CHARGE_NM_HIGH" == orderValue) {
            pageObj.rateCompareList.sort(function (a, b) {
                if (a.rateAdsvcNm   > b.rateAdsvcNm) return -1;
                else if (b.rateAdsvcNm > a.rateAdsvcNm) return 1;
                else return 0;
            });
        }

        viewRateCompareList();

    });


    $(".c-tabs__button").eq(0).trigger("click");

    /* 요금제 혜택 아코디언 */
    $(document).on('click', '.c-accordion--summary > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button', function() {
        var $btn = $(this);
        var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
        var isActive = $btn.hasClass('is-active');

        $btn.toggleClass('is-active', !isActive);
        $btn.attr('aria-expanded', !isActive);

        $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
        $panel.attr('aria-hidden', isActive);
    });

});


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
                let v_reviewId = item.reviewId;
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
                    if(v_platFormCd == "A") {
                        innerHTML += "		          <a class='fs-14 c-text-link--blue u-ml--0 u-mb--2' href='javascript:appOutSideOpen('"+v_snsInfo+"');' title='새창열림'>SNS 공유 사용후기 자세히 보기</a>";
                    } else if(v_platFormCd != "A") {
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
                innerHTML += "        <button type='button' aria-expanded='false' onclick='increaseHit(this, "+v_requestKey+", "+v_reviewId+"); return false;'>";
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

// 사용후기 조회수 증가
function increaseHit(obj, key, reviewId){
    var isExpanded = $(obj).parent().parent(".review__item").find("div[id^='content_']").hasClass("is-active");
    if(isExpanded == true) {
        return;
    }
    var varData = ajaxCommon.getSerializedData({
         requestKey : key,
         reviewId   : reviewId
    });

    ajaxCommon.getItem({
        id : 'modifyReviewHitAjax'
        , cache : false
        , async : false
        , url : '/m/requestReView/modifyReviewHitAjax.do'
        , data : varData
        , dataType : "json"
    }
    ,function(result){
        console.log("조회수 완료");
    });
}


