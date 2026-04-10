;

var v_ctgList; // xml 카테고리 목록
var v_proList; // xml 상품 목록
var v_rateAdsvcDivCd = "RATE";

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
$(document).ready(function(){

	history.pushState(null, null,location.href);
	window.onpopstate = function (event){
		history.go("/m/search/searchResultView.do");
	}

	if(searchTotal != 0 && searchKeyword != ''){
        insertSrchTxt();
    }

	//더보기 샘플 페이지입니다.
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
	        ,url:'/m/search/commonSearchAjax.do'
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
					innerHtml += '<li class="phone-box-wrap__item">';
					innerHtml += '    <a href="/m/product/phone/phoneView.do?prodId=' + phoneItem[i]._source.prodId + '&hndsetModelId=' + phoneItem[i]._source.hndsetModelId + '">';
					innerHtml += '        <div class="phone-box">';
					innerHtml += '            <img class="phone-img" src="'+ phoneItem[i]._source.imgPath +'">';
					innerHtml += '            <strong class="c-text c-text--type2 u-mt--24">';
					innerHtml += '                <b>' + phoneItem[i]._source.prodNm + '</b>';
					innerHtml += '            </strong>';
					innerHtml += '        </div>';
					innerHtml += '    </a>';
					innerHtml += '</li>';
				}
				$('#phoneResult').append(innerHtml);

				var currentCnt = $('#phoneResult').find('li').length;

				if(currentCnt == totalCnt){
					$('#phoneMoreBtn').hide();
				}

				$('#phoneMoreBtnTxt').text(currentCnt + '/' + totalCnt);
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
            ,url:'/m/search/commonSearchAjax.do'
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
                    innerHtml += '    <a class="c-list__anchor" href="javascript:void(0);" onclick="rateView(\''+ rateItem[i]._source.rateAdsvcGdncSeq + '\',\'' + rateItem[i]._source.rateAdsvcCd + '\');">';
                    innerHtml += '        <strong class="c-list__title c-text-ellipsis">';
                    innerHtml += '            '+ rateItem[i]._source.rateAdsvcNm + ' ';
                    innerHtml += '        </strong>';
                    innerHtml += '        <div class="c-flex c-flex--jfy-between"> ';
                    innerHtml += '        <span class="c-list__sub u-fw--regular">월 기본료(VAT 포함)</span> ';
                    innerHtml += '        <span class="u-ml--auto c-text c-text--type4">';
                    if(rateItem[i]._source.promotionAmtVatDesc == null || rateItem[i]._source.promotionAmtVatDesc == ''){
                        innerHtml += '          <span class="u-ml--8 u-co-mint fs-16"> ';
                        innerHtml += '              '+ rateItem[i]._source.mmBasAmtVatDesc +' ';
                        innerHtml += '          </span> ';
                    }else{
                        innerHtml += '          <span class="c-text c-text--strike u-co-gray-7 u-fw--regular"> ';
                        innerHtml += '              '+ rateItem[i]._source.mmBasAmtVatDesc +' ';
                        innerHtml += '          </span> ';
                        innerHtml += '          <span class="u-ml--8 u-co-mint fs-16"> ';
                        innerHtml += '              '+ rateItem[i]._source.promotionAmtVatDesc +' ';
                        innerHtml += '          </span> ';
                    }
                    innerHtml += '        </span> ';
                    innerHtml += '        </div> ';
                    innerHtml += '    </a>';
                    innerHtml += '</li>';
                }

                $('#rateResult').append(innerHtml);

                var currentCnt = $('#rateResult').find('li').length;

                if(currentCnt == totalCnt){
                    $('#rateMoreBtn').hide();
                }

                $('#rateMoreBtnTxt').text(currentCnt + '/' + totalCnt);
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
            ,url:'/m/search/commonSearchAjax.do'
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
                    innerHtml += '  <div class="c-accordion__head" data-acc-header id="acc_header_b'+adsvcCnt+'">                           ';
                    innerHtml += '    <button class="c-accordion__button" type="button" aria-expanded="false">';
                    innerHtml += '      <p class="c-text c-text--type4 u-mb--8 u-co-gray-7">                  ';
                    innerHtml += '        ' +adsvcItem[i]._source.rateAdsvcCtgNm+' ';
                    innerHtml += '      </p>                                                                  ';
                    innerHtml += '      ' +adsvcItem[i]._source.rateAdsvcNm+' ';
                    innerHtml += '    </button>                                                               ';
                    innerHtml += '  </div>                                                                    ';
                    innerHtml += '  <div class="c-accordion__panel expand c-expand" id="acc_content_b'+adsvcCnt+'">                          ';
                    innerHtml += '    <div class="c-accordion__inside">                                       ';
                    innerHtml += '    <div class="c-extraservice--box">';
                    innerHtml += '    <ul>';
                    for(var j=0; j < adsvcItemXml.length; j++){
						if(adsvcItemXml[j]._source.rateAdsvcGdncSeq == adsvcItem[i]._source.rateAdsvcGdncSeq){
							if(adsvcItemXml[j].ADDSV101 != undefined){
								innerHtml += '      <li>서비스 정의    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV101.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV102 != undefined){
								innerHtml += '      <li>서비스 이용요금(VAT포함)    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV102.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV103 != undefined){
								innerHtml += '      <li>서비스 신청방법    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV103.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV104 != undefined){
								innerHtml += '      <li>서비스 이용방법    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV104.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV105 != undefined){
								innerHtml += '      <li>서비스 유의사항    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV105.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV106 != undefined){
								innerHtml += '      <li>서비스 선택유형    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV106.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV107 != undefined){
								innerHtml += '      <li>서비스 제공내용    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV107.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV108 != undefined){
								innerHtml += '      <li>서비스 신청(가입 및 해지)방법    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV108.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
							if(adsvcItemXml[j].ADDSV199 != undefined){
								innerHtml += '      <li>직접입력</strong>    ';
			                    innerHtml += '          ' +adsvcItemXml[j].ADDSV199.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'') +' ';
			                    innerHtml += '      </li>   											';
							}
						}
					}
                    innerHtml += '    </ul>';
                    innerHtml += '    </div>';
                    innerHtml += '    </div>';
                    innerHtml += '  </div>';
                    innerHtml += '</li>';

                    adsvcCnt++;
                }
                $('#adsvcResult').append(innerHtml);

                var currentCnt = $('.adsvcAccordion').length;

                if(currentCnt == totalCnt){
                    $('#adsvcMoreBtn').hide();
                }

                $('#adsvcMoreBtnTxt').text(currentCnt + '/' + totalCnt);

                $('.adsvcAccordion').each(function (index){

					if(index > adsvcAccordionCnt){
						var el = document.querySelector('#' + $(this).attr('id'));
						var instance = KTM.Accordion.getInstance(el) ? KTM.Accordion.getInstance(document.querySelector(el)) : new KTM.Accordion(el);
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
            ,url:'/m/search/commonSearchAjax.do'
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
                    innerHtml += '<li class="c-accordion__item boardAccordion" id="board'+boardCnt+'">';
					innerHtml += '    <div class="c-accordion__head is-qtype" data-acc-header>';
					innerHtml += '        <span class="p-prefix">Q</span>';
					innerHtml += '        <button class="c-accordion__button" type="button" aria-expanded="false">';
					innerHtml += '        <div class="c-accordion__title">';
/*
					if(highLightSubject != ''){
						innerHtml += 			subjectSplit;
						innerHtml += '			<span class="u-co-mint">&nbsp;' + highLightSub2 + '</span>';
						innerHtml += 			subjectSplit2;
					}else{
						innerHtml += 			boardSubject;
					}
*/
					innerHtml += 			boardSubject;

					innerHtml += '        </div>';
					innerHtml += '        </button>';
					innerHtml += '    </div>';
					innerHtml += '    <div class="c-accordion__panel c-expand expand">';
					innerHtml += '        <div class="c-accordion__inside box-answer">';
					innerHtml += '            <div class="box-prefix">A</div>';
					innerHtml += '            <div class="box-content">';
					innerHtml += 				boardContent;
					innerHtml += '            </div>';
					innerHtml += '        </div>';
					innerHtml += '    </div>';
					innerHtml += '</li>';

					boardCnt++;
                }

                $('#boardResult').append(innerHtml);

                var currentCnt = $('#boardResult').find('li').length;

                if(currentCnt == totalCnt){
                    $('#boardMoreBtn').hide();
                }

                $('#boardMoreBtnTxt').text(currentCnt + '/' + totalCnt);
                $('.boardAccordion').each(function (index){

					if(index > boardAccordionCnt){
						var el = document.querySelector('#' + $(this).attr('id'));
						var instance = KTM.Accordion.getInstance(el) ? KTM.Accordion.getInstance(document.querySelector(el)) : new KTM.Accordion(el);
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
        url: '/m/search/insertSearchTxtAjax.do',
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
    openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'', '');
}

