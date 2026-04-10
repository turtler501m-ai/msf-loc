;

let pageObj = {
    standardCount:10
    ,colorDataList:[]
}


$(document).ready(function() {


    if ($(".c-swiper .swiper-slide").length > 0) {

        let swiperCntCnt = $(".c-swiper .swiper-slide").length;

        if (swiperCntCnt >  1) {
            var swiper = new Swiper('.c-swiper', {
                loop: true,
                pagination: {
                    el: '.swiper-pagination',
                    type: 'fraction',
                }
                , autoplay: {
                    delay: 3000,
                    disableOnInteraction: false,
                }
                , on: {
                    init: function () {
                        var swiper = this;
                        var autoPlayButton = this.el.querySelector('.swiper-play-button');
                        if (typeof autoPlayButton != "undefined") {
                            autoPlayButton.addEventListener('click', function () {
                                if (autoPlayButton.classList.contains('play')) {
                                    autoPlayButton.classList.remove('play');
                                    autoPlayButton.classList.add('stop');
                                    swiper.autoplay.start();
                                } else {
                                    autoPlayButton.classList.remove('stop');
                                    autoPlayButton.classList.add('play');
                                    swiper.autoplay.stop();
                                }
                            });
                        }
                    }
                }
            });
        } else {
            var swiper = new Swiper('.c-swiper', {
                pagination: {
                    el: '.swiper-pagination',
                    type: 'fraction',
                }
            });


        }

    }



    //더보기
    $("#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__more > button").click(function() {
        viewMore();
    });





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


    /**
     폭목록 조회
     */
    var getList = function() {

        var varData = $("#phonelistfrm").serialize();

        ajaxCommon.getItemNoLoading({
                id: 'getExhibitList'
                , cache: false
                , url : "/m/product/phone/phoneAgrListAjax.do"
                , data : varData
                , dataType : "json"

            } , function(data) {
                var htmlStr = "";
                var resultCode = data.RESULT_CODE;
                let totalCount = 0;
                //var totalCount = data.totalCount;

                $('#product_list').empty();

                if(data.totalCount > 0 && resultCode == "00000") {
                    var listData = data.phoneListLte;
                    let viewIndex = 0;

                    $(listData).each(function(index) {
                        let htmlStr = "";
                        if(viewIndex < pageObj.standardCount){
                            htmlStr += '<li>';
                        }else{
                            htmlStr += '<li style="display:none;">';
                        }
                        htmlStr += '		<div class="c-card__box">';



                        htmlStr += '			<div class="c-flex">';
                        htmlStr += '				<div class="labels">';
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
                        htmlStr += '				</div>';
                        htmlStr += '			</div>';

                        htmlStr += '			<a href="javascript:saveScroll();openPage(\'spaLink\', \'/m/product/phone/phoneView.do?prodId='+this.prodId+'&rateCd='+this.rateCd+'\', \'\');">';
                        htmlStr += '				<div class="phone__img">';
                        htmlStr += '					<img src="'+this.imgPath+'" alt="">';
                        if (this.layerYn == 'Y') {
                            htmlStr += '<img src="'+ this.layerImageUrl +'" alt="imgLayout">';
                        }
                        htmlStr += '				</div>';

                        htmlStr += '				<div class="phone__info-2">';


                        if(this.sesplsYn == 'Y'){
                            htmlStr += '  				<span class="c-label--Network">'+emptyToTyphoon(this.prodCtgIdLabel)+'</span>';
                            htmlStr += '					<h4 class="phone__txt phone__txt--title">'+this.prodNm+'</h4>';
                            htmlStr += '					<div class="phone__txt phone__txt--month-price2">'+numberWithCommas(this.inUnitPric + this.inUnitPric * 0.1)+'원</div>';
                            htmlStr += '					<div class="phone__txt phone__txt--discount-price">';
                            htmlStr += '						<b>'+numberWithCommas(this.outUnitPric + this.outUnitPric * 0.1)+'</b>원';
                            htmlStr += ' 				</div>';
                            htmlStr += '					<div class="phone__txt phone__txt--total-price">(월 통신요금 '+numberWithCommas(this.payMnthAmt)+'원 부터)</div>';
                        }else{
                            htmlStr += '  				<span class="c-label--Network">'+emptyToTyphoon(this.prodCtgIdLabel)+'</span>';
                            htmlStr += '					<h4 class="phone__txt phone__txt--title-2">'+this.prodNm+'</h4>';
                            htmlStr += '					<p class="phone-list__infotxt">' + this.repRateNm +'</p>';
                            htmlStr += '					<div class="phone-list__priceinfo">';
                            htmlStr += '	                	<p>월 단말요금 <b>'+ numberWithCommas(this.payMnthAmt) +'</b> 원</p>';
                            htmlStr += '	                    <p>월 통신요금 <b>'+ numberWithCommas(this.baseAmt) + '</b> 원</p>';
                            htmlStr += '	                </div>';
                            htmlStr += '					<div class="phone-list__price"><span> 월<b>'+ numberWithCommas(this.payMnthAmt + this.baseAmt)+'</b>원</span></div>';
                        }

                        htmlStr += '						<div class="phone__colors">';
                        var rgbData = '#d3beac';
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
                            $(colorCodes).each(function() {

                                var atribValCd1 = this;
                                $(pageObj.colorDataList).each(function() {
                                    if(this.dtlCd == atribValCd1.trim()){
                                        rgbData = this.expnsnStrVal1;
                                    }
                                });
                                htmlStr += '						<span class="phone__dot" style="background-color: '+rgbData+'"></span>';
                            });
                        }
                        htmlStr += '							</div>';
                        htmlStr += '					</div>';
                        htmlStr += '				</a>';
                        htmlStr += '			</div>';
                        htmlStr += '		</li>';

                        //요금제 가격정보가 존재 해야 한다.
                        let chargeList = this.mspSaleSubsdMstListForLowPrice ;  //mspSaleSubsdMstListForLowPrice
                        if (chargeList != null && chargeList.length > 0 ) {
                            $('#product_list').append(htmlStr);
                            totalCount++;
                            viewIndex++;
                        }

                    });
                    $('#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__sort').show();
                    $("#moreBtn").show();
                    $("#totalCount").text(totalCount);
                }else{
                    htmlStr += '<div class="c-nodata">';
                    htmlStr += '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                    htmlStr += '<p class="c-nodata__text">검색 결과가 없습니다.</p>';
                    htmlStr += '</div>';

                    $('#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__sort').hide();
                    $("#moreBtn").hide();
                }



                $("#product_list > li > div > a > div.phone__img > img").each(function(){
                    $(this).error(function(){
                        $(this).unbind("error");
                        $(this).attr("src","/resources/images/mobile/content/img_240_no_phone.png");
                    });
                });

                initMore();

            }
        )



    }


    $("#bsSampleDesc > div >  input[name='radList']").click(function() {

        $("input[name='listOrderEnum']").val($(this).data('value'));
        var selRadioId = $(this).prop('id');
        var selRadioText = $("#bsSampleDesc > div >  label[for='"+selRadioId+"']").text();
        $("#orderButton").html('<span class="u-mr--4 fs-14">'+selRadioText+'</span>'+'<i class="c-icon c-icon--sort" aria-hidden="true"></i>');
        $("#bsSortList_close_button").click();
        getList();
    });

    getList();

})



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

    /**
     * 요금제 더보기 초기화
     */
    var initMore = function(){
        var mCount = $("#totalCount").text();
        if(mCount <= pageObj.standardCount){
            $("#currentViewCount").text(mCount);
        }else{
            $("#currentViewCount").text(pageObj.standardCount);
        }
        var cCount = $("#currentViewCount").text();
        var mCount = $("#totalCount").text();
        if(cCount == mCount){
            $("#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__more").hide();
        }

    };

    /**
     * 요금제 더보기 처리기능
     */
    var viewMore = function() {

        var cCount = $("#currentViewCount").text();
        var mCount = $("#totalCount").text();

        if (cCount == mCount) {
            MCP.alert('마지막 페이지입니다.');
            return;
        }
        //숨김 처리 되있는 요금제 리스트만 가져온다.

        var $li =$("#product_list").find("li[style*=none]");
        $.each($li,function(idx) {	//10개까지만 보이게 처리
            if (idx == pageObj.standardCount) {
                return false;
            }
            $(this).css("display","");
        });
        setCurrentCount();	//더보기 버튼의 숫자 카운트 변경
    };

    /**
     * 더보기 버튼의 숫자 변경처리
     */
    var setCurrentCount = function() {
        var currentViewCount = $("#product_list").find("li:visible").length
        var totalCount = $("#product_list").find("li").length;
        $("#currentViewCount").text(currentViewCount);
        $("#totalCount").text(totalCount);
        if(currentViewCount == totalCount){
            $("#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__more").hide();
        }

    };

