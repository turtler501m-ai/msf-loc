;


let pageObj = {
    colorDataList:[]
}
$(document).ready(function(){
    document.addEventListener('UILoaded', function() {
        KTM.swiperA11y('#swiperOpenMarket', {
            autoplay: {
                delay: 3000,
                disableOnInteraction: false,
            },
            loop: $("#swiperOpenMarket .swiper-wrapper .swiper-slide").length > 1,  // 상단 배너 루프 처리
        });
    });
    setTimeout(function(){
        $('#swiperOpenMarket .swiper-slide').removeAttr('tabindex');
    },100);



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
            getList();
        }
    });

    var getList = function() {
        $("#prodCtgId").val($("input[name='radNetwork']:checked").val());
        $("#makrCd").val($("input[name='radProduct']:checked").val());

        var varData = $("#phonelistfrm").serialize();

        ajaxCommon.getItemNoLoading({
            id: 'getExhibitList'
            , cache: false
            , url: "/m/product/phone/phoneAgrListAjax.do"
            , data: varData
            , dataType: "json"
        } , function(data) {
            var resultCode = data.RESULT_CODE;
            var totalCount = data.totalCount;

            $('#phoneListUlArea').empty();

            if(totalCount > 0 && resultCode == "00000") {
                $("#phoneListNoDataArea").hide();
                var listData = data.phoneListLte;

                $(listData).each(function(index) {

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
                        var prodId = this.prodId;
                        $(colorCodes).each(function() {
                            var atribValCd1 = this;
                            $(pageObj.colorDataList).each(function() {
                                if(this.dtlCd == atribValCd1.trim()){
                                    rgbData = this.expnsnStrVal1;
                                    rgbName = this.dtlCdNm;
                                }
                            });
                            htmlStr += '			<button class="phone-list__dot" style="background-color: '+rgbData+'" role="presentation" aria-describedby="color_tooltip_c'+colorIdNum+'_'+prodId+'">휴대폰 색상';
                            htmlStr += '				<div class="c-tooltip-box c-tooltip-box--auto" id="color_tooltip_c'+colorIdNum+'_'+prodId+'" role="tooltip">'+rgbName+'</div>';
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
                    htmlStr += '			</div>';
                    htmlStr += '	</li>';

                    //요금제 가격정보가 존재 해야 한다.
                    let chargeList = this.mspSaleSubsdMstListForLowPrice ;  //mspSaleSubsdMstListForLowPrice
                    if (chargeList != null && chargeList.length > 0 ) {
                        $('#phoneListUlArea').append(htmlStr);
                    }

                    //$('#phoneListUlArea').html(htmlStr);


                });
                $('#phoneSortDivArea').show();
            }else{
                $("#phoneListNoDataArea").show();
                $('#phoneSortDivArea').hide();
            }

            $("#phoneListUlArea > li > div > a > div.phone-list__panel > div.phone-list__image > img").each(function(){
                $(this).error(function(){
                    $(this).unbind("error");
                    $(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
                });
            });
        });
    };

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
            pageObj.colorDataList = data.result;
        });
    }();


    getList();


});



    function fn_go_banner(linkUrl,bannSeq, bannCtg ,tgt){
        insertBannAccess(bannSeq,bannCtg);
        if(tgt.trim() == '_blank'){
            window.open(linkUrl);
        }else{
            window.location.href  = linkUrl;
        }

    }

    function emptyToTyphoon(value){
        if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
            return '-';
        } else{
            return value;
        }
    }

    function go_phoneView(prodId, rateCd){

        document.phoneViewfrm.prodId.value = prodId;
        document.phoneViewfrm.rateCd.value = rateCd;
        document.phoneViewfrm.submit();

    }
