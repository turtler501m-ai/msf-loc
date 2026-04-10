
$(document).ready(function() {

    // 연관검색어 클릭시
    $(document).on('click', '#autocompleteResult > li', function(){
        $('#autocompleteResult').hide();
        //$('#searchViewInput').val($(this).data("val"));
        $('#searchPopupText').val($(this).data("val"));
        $('#closePopup').trigger('click');

        commonSearch();
    });
	// 인기검색어 클릭시
	$(document).on('click', '#popularList > li > a', function(){
		$('#autocompleteResult').hide();
		//$('#searchViewInput').val($(this).data("val"));
		$('#searchPopupText').val($(this).data("val"));
		$('#closePopup').trigger('click');

		commonSearch();
	});
	// 추천검색어 클릭시
	$(document).on('click', '#recommendList > a', function(){
		$('#autocompleteResult').hide();
		//$('#searchViewInput').val($(this).data("val"));
		$('#searchPopupText').val($(this).data("val"));
		$('#closePopup').trigger('click');

		commonSearch();
	});

	$('#searchPopupTextReset').on('click', function(){
		$('#searchPopupText').val("");
		$('#searchPopupText').keyup();
	});

	//검색 버튼 클릭시
    $(document).on('click','#searchNewButton', function(){
        $('#searchViewInput').val($('#searchPopupText').val());
        commonSearch();
    });

	$(document).on('keydown', '#searchPopupText',function(key){
        if( key.keyCode == 13){
            $('#autocompleteResult').hide();
            $('#searchViewInput').val($('#searchPopupText').val());
            $('#closePopup').trigger('click');

            commonSearch();
        }
    });

	$(document).on("click", "a.ly-header__search", function(){
	    fnRecommendList();
	    fnPopularWordList();
	});

	$('#searchPopupText').on('keyup', function(){
		let srchText = $(this).val();
		if(srchText.length >= 2){
			$.ajax({
				type: "post",
				url: "/m/search/relationWordListAjax.do",
				dataType: "json",
				data: { "srchText" : srchText },
				success: function(data) {
					//---------------------------
					let autoResultHtml = "";
					$('#autocompleteResult').empty();
					for(let i=0; i<data.length; i++){
						let tmpVal = data[i].crrlSearchWord;
						data[i].crrlSearchWord = data[i].crrlSearchWord.replace(srchText,'<span class="u-co-mint">'+srchText+'</span>');
						autoResultHtml +='<li class="autocomplete__item" data-val="'+tmpVal+'">';
			            autoResultHtml +='  <a href="javascript:void(0);" onclick="return false">';
			            autoResultHtml +='    <i class="c-icon c-icon--search-gray" aria-hidden="true"></i>';
			            autoResultHtml +='    <span class="c-text c-text--type7 u-ml-12">';
				        autoResultHtml +=       data[i].crrlSearchWord;
			            autoResultHtml +='    </span>';
			            autoResultHtml +='  </a>';
			            autoResultHtml +='</li>';
					}
					$('#autocompleteResult').html(autoResultHtml);
					$('#autocompleteResult').show();
					//---------------------------
				}
			});

		} else {
			$('#autocompleteResult').hide();
			$('#autocompleteResult').empty();
		}
	});

});



function fnRecommendList(){
	$.ajax({
		type: "post",
		url: "/m/search/recommendWordListAjax.do",
		dataType: "json",
		data: {},
		success: function(data) {
			//recommendList
			let resultHtml = "";
			$('#recommendList').empty();

			for(let i=0; i<data.length; i++){
				resultHtml += '<a class="c-button c-button--sm c-button--mint-type2" href="javascript:void(0);" onclick="return false" data-val="'+data[i].searchWord+'">'+ data[i].searchWord +'</a>';
			}

			if(data.length > 0){
				$('#recommendList').html(resultHtml);
			} else {
				$('#recommendList').html('<a class="c-button c-button--sm c-button--mint-type2"> 해당데이터가 없습니다. </a>');
			}

			$('#recommendList').html(resultHtml);
		}
	});
}
function fnPopularWordList(){
	$.ajax({
		type: "post",
		url: "/m/search/popularWordListAjax.do",
		dataType: "json",
		data: {},
		success: function(data) {
			// popularList

			let resultHtml = "";
			$('#popularList').empty();
			for(let i=0; i<data.length; i++){
				let riseNum = parseInt(data[i].searchRiseNval);
				let riseVal = "";
				if(riseNum > 0 ){
					riseVal = " up";
				} else if(riseNum < 0 ){
					riseVal = " down";
				} else {
					riseVal = " even";
				}
				resultHtml += '<li class="popular-keyword__item'+ riseVal +'">';
                resultHtml += '<a href="javascript:void(0);" onclick="return false"  data-val="'+data[i].searchWord+'">';
                resultHtml += '  <span class="popular-keyword__num">'+ (i+1) +'</span>';
                resultHtml += '  <span class="popular-keyword__text">'+data[i].searchWord+'</span>';
                resultHtml += '</a>';
                resultHtml += '</li>';
			}
			if(data.length > 0){
				$('#popularList').html(resultHtml);
			} else {
				$('#popularList').html("해당 데이터가 없습니다.");
			}

		}
	});
}

// 통합검색 결과 뿌려주기
function commonSearch(){

    ajaxCommon.createForm({
        method:'post'
        ,action:'/m/search/searchResultView.do'
    });

    var searchVal = $('#searchViewInput').val() ? $('#searchViewInput').val() : $('#searchPopupText').val();

    ajaxCommon.attachHiddenElement('page', 1);
    ajaxCommon.attachHiddenElement('searchKeyword', searchVal);
    ajaxCommon.attachHiddenElement('searchCategory','ALL');
    ajaxCommon.formSubmit();
}


