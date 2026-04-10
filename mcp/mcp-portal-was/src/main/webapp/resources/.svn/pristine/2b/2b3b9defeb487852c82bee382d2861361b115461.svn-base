
$(document).ready(function() {
    // 연관검색어 클릭시
    $(document).on('click', '#autoList > li', function(){
        $('#autoList').hide();
        //$('#searchInputText').val($(this).data("val"));
        $('#searchInp').val($(this).data("val"));
        $('#closePopup').trigger('click');
        commonSearch();
    });
    // 인기검색어 클릭시
    $(document).on('click', '#popularList > li > a', function(){
        $('#autoList').hide();
        //$('#searchInputText').val($(this).data("val"));
        $('#searchInp').val($(this).data("val"));
        $('#closePopup').trigger('click');
        commonSearch();
    });
    // 추천검색어 클릭시
    $(document).on('click', '#recommendList > a', function(){
        $('#autoList').hide();
        //$('#searchInputText').val($(this).data("val"));
        $('#searchInp').val($(this).data("val"));
        $('#closePopup').trigger('click');
        commonSearch();
    });

    $('#resetBtn').on('click', function(){
        $('#searchInp').val("");
        $('#searchInp').keyup();
    });

    //검색 버튼 클릭시
    $(document).on('click','#searchInputBtn', function(){
        $('#searchInputText').val($('#searchInp').val());
        commonSearch();
    });

    $(document).on('keyup', '#searchInp', function (key){
        if( key.keyCode == 13){
            $('#autoList').hide();
            $('#searchInputText').val($('#searchInp').val());
            $('#closePopup').trigger('click');
            commonSearch();
        }
	});

	$(document).on("click", "button.ly-header__search-btn", function(){
	    fnRecommendList();
	    fnPopularWordList();
	});

    $('#searchInp').on('keyup', function(){
        let srchText = $(this).val();
        if(srchText.length >= 2){
            $.ajax({
                type: "post",
                url: "/search/relationWordListAjax.do",
                dataType: "json",
                data: { "srchText" : srchText },
                success: function(data) {
                    //---------------------------
                    let autoResultHtml = "";
                    $('#autoList').empty();
                    for(let i=0; i<data.length; i++){
                        autoResultHtml +='<li class="search-auto__item" id="selectedAuto" data-val="'+data[i].crrlSearchWord+'" role="option" aria-selected="true">';
                        data[i].crrlSearchWord = data[i].crrlSearchWord.replace(srchText,'<span class="span u-co-red">'+srchText+'</span>');
                        autoResultHtml +=    '<a href="javascript:void(0);">' + data[i].crrlSearchWord+ '</a>';
                        autoResultHtml +='</li>';
                    }
                    $('#autoList').html(autoResultHtml);
                    $('#autoList').show();
                    //---------------------------
                    if(data.length == 0){
                        $('#autoList').attr('class','search-auto.no-data');
                    }else{
                        $('#autoList').attr('class','search-auto');
                    }
                }
            });

        } else {
            $('#autoList').hide();
            $('#autoList').empty();
        }
    });

});

function fnRecommendList(){
    $.ajax({
        type: "post",
        url: "/search/recommendWordListAjax.do",
        dataType: "json",
        data: {},
        success: function(data) {
            let resultHtml = "";
            $('#recommendList').empty();

            for(let i=0; i<data.length; i++){
                resultHtml += '<a class="c-button c-button-round--xsm c-button--white" href="javascript:void(0);" data-val="'+data[i].searchWord+'">'+ data[i].searchWord +'</a>';
            }

            if(data.length > 0){
                $('#recommendList').html(resultHtml);
            } else {
                $('#recommendList').html('<a class="c-button c-button-round--xsm c-button--white"> 해당데이터가 없습니다. </a>');
            }

            $('#recommendList').html(resultHtml);
        }
    });
}

function fnPopularWordList(){
    $.ajax({
        type: "post",
        url: "/search/popularWordListAjax.do",
        dataType: "json",
        data: {},
        success: function(data) {
            let resultHtml = "";
            $('#popularList').empty();
            for(let i=0; i<data.length; i++){
                let riseNum = parseInt(data[i].searchRiseNval);
                let riseVal = "";
                let hiddenHtml = "";
                if(riseNum > 0 ){
                    riseVal = "triangle-top--red";
                    hiddenHtml = "인기 검색어 순위 상승";
                } else if(riseNum < 0 ){
                    riseVal = "triangle-bottom--blue";
                    hiddenHtml = "인기 검색어 순위 하락";
                } else {
                    riseVal = "equal";
                    hiddenHtml = "인기 검색어 순위 동일";
                }
                resultHtml += '<li class="search-list__item">';
                resultHtml += '  <a class="search-list__anchor" href="javascript:void(0);" data-val="'+data[i].searchWord+'">';
                resultHtml += '    <img src="../../resources/images/portal/common/ico_number'+ (i+1) +'.svg" alt="'+ (i+1) +'위">';
                resultHtml += '    <span class="search-list__title">'+data[i].searchWord+'</span>';
                resultHtml += '    <i class="c-icon c-icon--'+riseVal+'" aria-hidden="true"></i>';
                resultHtml += '    <span class="c-hidden">'+ hiddenHtml +'</span>';
                resultHtml += '  </a>';
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
        ,action:'/search/searchResultView.do'
    });

    var searchVal = $('#searchInputText').val() ? $('#searchInputText').val() : $('#searchInp').val();
    
    ajaxCommon.attachHiddenElement('page', 1);
    ajaxCommon.attachHiddenElement('searchKeyword', searchVal);
    ajaxCommon.attachHiddenElement('searchCategory','ALL');
    ajaxCommon.formSubmit();
}

