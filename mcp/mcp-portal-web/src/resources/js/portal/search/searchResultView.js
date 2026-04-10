
// 휴대폰 페이징
var phonePageNo = 1;
var ratePageNo = 1;
var adsvcPageNo = 1;
var boardPageNo = 1;
var searchKeyword = $('#searchKeyword').val();
var boardCnt = $('#boardResult').find('li').length;
var adsvcCnt = $('#adsvcResult').find('li').length;

const searchMenu = parseInt($("#searchMenu").val(),10);
const searchPhone = parseInt($("#searchPhone").val(),10);
const searchRate = parseInt($("#searchRate").val(),10);
const searchAdsvc = parseInt($("#searchAdsvc").val(),10);
const searchEvent = parseInt($("#searchEvent").val(),10);
const searchBoard =parseInt($("#searchBoard").val(),10);
const searchTotal = searchMenu + searchPhone + searchRate +
                    searchAdsvc + searchEvent + searchBoard;
$(document).ready(function() {

    history.pushState(null, null,location.href);
    window.onpopstate = function (event){
        history.go("/search/searchResultView.do");
    }

    if(searchTotal != 0 && searchKeyword != ''){
        insertSrchTxt();
    }

    $(document).on('keyup', '#searchInputText',function(key){
        if(key.keyCode == 13){
            $('#searchInputText').val($('#searchInputText').val());
            commonSearch();
        }
    });

    $(document).on('click', '.c-button--primary',function(){
        $('#searchInputText').val($('#searchInputText').val());
        commonSearch();
    });

    //휴대폰 더보기
    $('#phoneMoreBtn').on('click', function (){
        var searchCategory = 'phone';
        phonePageNo++;
        var varData = ajaxCommon.getSerializedData({
            page : phonePageNo
            , searchKeyword : searchKeyword
            , searchCategory : searchCategory
        });

        ajaxCommon.getItem({
            id:'commonSearchAjax'
            ,cache:false
            ,url:'/search/commonSearchAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(obj){
            if(obj.data.hits.hits.length > 0){
                var phoneItem = obj.data.hits.hits;
                var innerHtml = '';
                var totalCnt = obj.data.hits.total.value;
                for(var i=0; i<phoneItem.length; i++){
                    innerHtml += '<li class="phone-list__item">';
                    innerHtml += '  <a class="phone-list__anchor" href="/product/phone/phoneView.do?prodId='+ phoneItem[i]._source.prodId + '&hndsetModelId=' + phoneItem[i]._source.hndsetModelId  +' ">';
                    innerHtml += '    <div class="phone-list__image" aria-hidden="true">';
                    innerHtml += '      <img src="'+ phoneItem[i]._source.imgPath +'" alt="' + phoneItem[i]._source.prodNm + '실물 사진">';
                    innerHtml += '    </div>';
                    innerHtml += '    <span class="phone-list__title">' + phoneItem[i]._source.prodNm + '</span>';
                    innerHtml += '  </a>';
                    innerHtml += '</li>';
                }
                $('#phoneResult').append(innerHtml);

                var currentCnt = $('#phoneResult').find('li').length;

                if(currentCnt == totalCnt){
                    $('#phoneMoreBtn').hide();
                }

                $('#phoneMoreCurrent').text(currentCnt);
                $('#phoneMoreTotal').text(totalCnt);
            }
        });
    });

    //요금제 더보기
    $('#rateMoreBtn').on('click', function (){
        var searchCategory = 'rate';
        ratePageNo++;
        var varData = ajaxCommon.getSerializedData({
            page : ratePageNo
            , searchKeyword : searchKeyword
            , searchCategory : searchCategory
        });

        ajaxCommon.getItem({
            id:'commonSearchAjax'
            ,cache:false
            ,url:'/search/commonSearchAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(obj){
            if(obj.data.hits.hits.length > 0){
                var rateItem = obj.data.hits.hits;
                var innerHtml = '';
                var totalCnt = obj.data.hits.total.value;
                for(var i=0; i<rateItem.length; i++){
                    innerHtml += '<li class="c-list__item">';
                    innerHtml += '  <a class="c-list__anchor" href="javascript:void(0);" onclick="rateView(\''+ rateItem[i]._source.rateAdsvcGdncSeq + '\',\'' + rateItem[i]._source.rateAdsvcCd + '\');">';
                    innerHtml += '    <span class="c-list__title">';
                    innerHtml += '        '+ rateItem[i]._source.rateAdsvcNm + ' ';
                    innerHtml += '    </span>';
                    innerHtml += '    <div class="price">';
                    if(rateItem[i]._source.promotionAmtVatDesc == null || rateItem[i]._source.promotionAmtVatDesc == ''){
                        innerHtml += '      <span class="price__item">';
                        innerHtml += '        <span class="c-hidden">월 기본료(VAT 포함)</span>';
                        innerHtml += '        <b>'+ rateItem[i]._source.mmBasAmtVatDesc +'</b>원';
                        innerHtml += '      </span>';
                    }else{
                        innerHtml += '      <span class="price__item u-td-line-through">';
                        innerHtml += '        <span class="c-hidden">월 기본료(VAT 포함)</span>'+ rateItem[i]._source.mmBasAmtVatDesc +' 원';
                        innerHtml += '      </span>';
                        innerHtml += '      <span class="price__item">';
                        innerHtml += '        <span class="c-hidden">프로모션 요금 월 기본료(VAT 포함)</span>';
                        innerHtml += '        <b>'+ rateItem[i]._source.promotionAmtVatDesc +'</b>원';
                        innerHtml += '      </span>';
                    }
                    innerHtml += '    </div>';
                    innerHtml += '  </a>';
                    innerHtml += '</li>';
                }

                $('#rateResult').append(innerHtml);

                var currentCnt = $('#rateResult').find('li').length;

                if(currentCnt == totalCnt){
                    $('#rateMoreBtn').hide();
                }
                $('#rateMoreCurrent').text(currentCnt);
                $('#rateMoreTotal').text(totalCnt);
            }
        });
    });

    //부가서비스 더보기
    $('#adsvcMoreBtn').on('click', function (){
        var searchCategory = 'adsvc';
        adsvcPageNo++;
        var varData = ajaxCommon.getSerializedData({
            page : adsvcPageNo
            , searchKeyword : searchKeyword
            , searchCategory : searchCategory
        });

        ajaxCommon.getItem({
            id:'commonSearchAjax'
            ,cache:false
            ,url:'/search/commonSearchAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(obj){
            if(obj.data.hits.hits.length > 0){
                var adsvcItem = obj.data.hits.hits;
                var adsvcItemXml = obj.adsvcXml;
                var innerHtml = '';
                var totalCnt = obj.data.hits.total.value;
                var adsvcAccordionCnt = $('.adsvcAccordion').length -1;

                for(var i=0; i<adsvcItem.length; i++){

                    innerHtml += '<li class="c-accordion__item adsvcAccordion" id="adsvc'+adsvcCnt+'">                                              ';
                    innerHtml += '  <div class="c-accordion__head">                           ';
                    //innerHtml += '    <div class="c-accordion__image">                                        ';
                    //innerHtml += '      <img src="/resources/images/portal/common/ico_mo_call.svg" alt="">';
                    //innerHtml += '    </div>                                                                  ';
                    innerHtml += '		<div class="product__title-wrap">';
					innerHtml += '    		<span class="product__sub">';
					innerHtml += '        		'+ adsvcItem[i]._source.rateAdsvcCtgNm;
					innerHtml += '    		</span>';
					innerHtml += '    		<span class="product__title">';
					innerHtml += '        		'+ adsvcItem[i]._source.rateAdsvcNm;
					innerHtml += '    		</span>';
					innerHtml += '		</div>';

                    innerHtml += '    	<div class="product__text">';
                    if(adsvcItem[i]._source.mmBasAmtVatDesc != ''){
                        if(adsvcItem[i]._source.mmBasAmtVatDesc == '무료제공' || adsvcItem[i]._source.mmBasAmtVatDesc == '무료'){
                            innerHtml += '       <span class="u-co-point-4">';
                            innerHtml += '          <b>' + adsvcItem[i]._source.mmBasAmtVatDesc+ '</b>';
                            innerHtml += '       </span>';
                        } else {
                            innerHtml += '      <div class="product__sub">VAT 포함</div>';
                            innerHtml += '       <span class="u-co-sub-4">';
                            innerHtml += '          <b>' + adsvcItem[i]._source.mmBasAmtVatDesc+ '</b>원';
                            innerHtml += '       </span>';
						}
					} else {
                            innerHtml += '       <span class="u-co-point-4">';
                            innerHtml += '          <b>' + adsvcItem[i]._source.mmBasAmtVatDesc+ '</b>';
                            innerHtml += '       </span>';
					}
					innerHtml += '		</div>';

					innerHtml += '		<button class="runtime-data-insert c-accordion__button" id="acc_header_b'+adsvcCnt+'" type="button" aria-expanded="false" aria-controls="acc_content_b'+adsvcCnt+'">';
					innerHtml += '    		<span class="c-hidden">'+ adsvcItem[i]._source.rateAdsvcNm + ' 상세열기</span>';
					innerHtml += '		</button>'
                    innerHtml += '  </div>                                                                    ';
                    innerHtml += '  <div class="c-accordion__panel expand c-expand" id="acc_content_b'+adsvcCnt+'" aria-labelledby="acc_header_b'+adsvcCnt+'">                          ';
                    innerHtml += '    <div class="c-accordion__inside">                                       ';
                    innerHtml += '    	<div class="product__content">                                        ';
                    innerHtml += '    		<ul class="c-text-list c-bullet c-bullet--dot">                   ';

                    for(var j=0; j < adsvcItemXml.length; j++){
						if(adsvcItemXml[j]._source.rateAdsvcGdncSeq == adsvcItem[i]._source.rateAdsvcGdncSeq){
							if(adsvcItemXml[j].ADDSV101 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 정의  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV101.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV102 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 이용요금(VAT포함)  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV102.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV103 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 신청방법  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV103.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV104 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 이용방법  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV104.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV105 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 유의사항  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV105.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV106 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 선택유형  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV106.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV107 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 제공내용  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV107.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV108 != undefined){
								innerHtml += '  <li class="c-text-list__item">서비스 신청(가입 및 해지)방법  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV108.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
							if(adsvcItemXml[j].ADDSV199 != undefined){
								innerHtml += '  <li class="c-text-list__item">직접입력  ';
			                    innerHtml += '      <ul class="c-text-list c-bullet c-bullet--hyphen">                            ';
			                    innerHtml += '      	<li class="c-text-list__item u-co-gray">                            ';
			                    innerHtml += '          	' +adsvcItemXml[j].ADDSV199.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +'									  ';
			                    innerHtml += '      	</li>                                                             ';
			                    innerHtml += '      </ul>                                                             ';
			                    innerHtml += '  </li>                                                             ';
							}
						}
					}
                    innerHtml += '    	</div>                                                                  ';
                    innerHtml += '    </div>                                                                  ';
                    innerHtml += '  </div>                                                                    ';
                    innerHtml += '</li>    																	  ';

                    adsvcCnt++;
                }

                $('#adsvcResult').append(innerHtml);

                var currentCnt = $('.adsvcAccordion').length;

                if(currentCnt == totalCnt){
                    $('#adsvcMoreBtn').hide();
                }
                $('#adsvcMoreCurrent').html('<span class="c-hidden">현재 노출된 항목</span>' + currentCnt);
                $('#adsvcMoreTotal').html('<span class="c-hidden">전체 항목</span>' + totalCnt);

                $('.adsvcAccordion').each(function (index){
					if(index > adsvcAccordionCnt){
						var el = document.querySelector('#' + $(this).attr('id'));
						var instance = KTM.Accordion.getInstance(el) ? KTM.Accordion.getInstance(el) : new KTM.Accordion(el);
						instance.update();
					}
				});
            }
        });
    });

    //자주묻는 질문 더보기
    $('#boardMoreBtn').on('click', function (){
        var searchCategory = 'board';
        boardPageNo++;
        var varData = ajaxCommon.getSerializedData({
            page : boardPageNo
            , searchKeyword : searchKeyword
            , searchCategory : searchCategory
        });

        ajaxCommon.getItem({
            id:'commonSearchAjax'
            ,cache:false
            ,url:'/search/commonSearchAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false

        }
        ,function(obj){

            if(obj.data.hits.hits.length > 0){
                var boardItem = obj.data.hits.hits;
                var innerHtml = '';
                var totalCnt = obj.data.hits.total.value;
                var highLightSubject = '';
                var highLightSub1 = '';
                var highLightSub2 = '';
                var boardSubject = '';
                var subjectSplit = '';
                var subjectSplit2 = '';
                var boardContent = '';

                var boardAccordionCnt = $('#boardResult').find('li').length -1;

                for(var i=0; i<boardItem.length; i++){
                    boardSubject = boardItem[i]._source.boardSubject;
                    boardContent = boardItem[i]._source.boardContents.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/&quot;/gi, '\"').replace(/&amp;/gi,'&');
                    if(boardItem[i].highlight != undefined){
	                    highLightSubject = boardItem[i].highlight.boardSubject[0] ? boardItem[i].highlight.boardSubject[0] : '';
	                    highLightSub1 = highLightSubject.split('>')[1] ? highLightSubject.split('>')[1] : '';
	                    highLightSub2 = highLightSub1.split('<')[0] ? highLightSub1.split('<')[0] : '';
	                    subjectSplit = boardSubject.split(highLightSub2)[0];
	                    subjectSplit2 = boardSubject.split(highLightSub2)[1];
                    }

                    innerHtml +='<li class="c-accordion__item boardAccordion" id="board'+boardCnt+'">';
					innerHtml +='    <div class="c-accordion__head">';
					innerHtml +='        <span class="question-label">Q<span class="c-hidden">질문</span>';
					innerHtml +='        </span>';
					innerHtml +='        <span class="c-accordion__title">';
/*
					if(highLightSubject != ''){
						innerHtml += 			subjectSplit;
						innerHtml += '			<span class="u-co-red">&nbsp;' + highLightSub2 + '</span>';
						innerHtml += 			subjectSplit2;
					}else{
						innerHtml += 			boardSubject;
					}
*/
					innerHtml += 			boardSubject;

					innerHtml +='        </span>';
					innerHtml +='        <button class="acc_headerA1 runtime-data-insert c-accordion__button" type="button" aria-expanded="false" aria-controls="acc_contentA'+boardCnt+'">';
					innerHtml +='        <span class="c-hidden">' + boardSubject + ' 상세열기</span>';
					innerHtml +='        </button>';
					innerHtml +='    </div>';
					innerHtml +='    <div class="c-accordion__panel expand" id="acc_contentA'+boardCnt+'" aria-labelledby="acc_headerA'+boardCnt+'">';
					innerHtml +='        <div class="c-accordion__inside">';
					innerHtml +='        <span class="box-prefix">A</span>';
					innerHtml +='        <div class="box-content">';
					innerHtml +=			boardContent;
					innerHtml +='        </div>';
					innerHtml +='    </div>';
					innerHtml +='</li>';

					boardCnt++;
                }

                $('#boardResult').append(innerHtml);

                var currentCnt = $('#boardResult').find('li').length;

                if(currentCnt == totalCnt){
                    $('#boardMoreBtn').hide();
                }

                $('#boardMoreCurrent').html('<span class="c-hidden">현재 노출된 항목</span>' + currentCnt);
                $('#boardMoreTotal').html('<span class="c-hidden">전체 항목</span>' + totalCnt);

                $('.boardAccordion').each(function (index){

					if(index > boardAccordionCnt){
						var el = document.querySelector('#' + $(this).attr('id'));
						var instance = KTM.Accordion.getInstance(el) ? KTM.Accordion.getInstance(el) : new KTM.Accordion(el);
						instance.update();
					}
				});
            }
        });
    });

});


//휴대폰 -> 공시지원금 단말 바로가기
//요금제 -> 유심 요금제(목록) 바로가기
//부가서비스 -> 부가서비스 페이지 바로가기
//진행중인 이벤트 -> 진행중 이벤트(목록) 바로가기
//자주묻는 질문 -> 자주묻는 질문 페이지 바로가기
//통합검색 후 결과뿌려줄때 필요.
function listView(url){
    ajaxCommon.createForm({
            method:"post"
            ,action: url
    });
    ajaxCommon.formSubmit();
}

function insertSrchTxt(){
    var varData = "";

    varData = ajaxCommon.getSerializedData({
        searchTotal: searchTotal,
        searchKeyword: searchKeyword
    });

    ajaxCommon.getItem({
        id: 'insertSearchTxtAjax',
        cache: false,
        url: '/search/insertSearchTxtAjax.do',
        data: varData,
        dataType: "json",
        async: false
    },
    function(jsonObj) {
        var resultCode = jsonObj.resultCode;
        if(resultCode == 'P0000'){
            //alert('인기검색어 인서트 성공');
        }
    })
}

function rateView(rateAdsvcGdncSeq,rateAdsvcCd){
    openPage('pullPopup', '/rate/rateLayer.do?rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'', '');
}
