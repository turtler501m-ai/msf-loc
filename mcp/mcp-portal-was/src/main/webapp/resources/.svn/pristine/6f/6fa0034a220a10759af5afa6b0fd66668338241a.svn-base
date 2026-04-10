var checkRcnt = 0;
let today = new Date();
let yy = today.getFullYear() + '';
let cnvrtYear = yy.substring(2,4);
var coverflowSwiper;
var detailSwiper;


$(document).ready(function(){
	if($("#year").val()){
		getData($("#year").val());
	}else{
		getData(cnvrtYear);
	}
	
	$(document).on("click", "#snsShareBut", function(){
		
		openPage('snsSharePop', '/snsSharePop.do', '')
	});
	
	
});


$("#searchMonthlyUsim").on("click", function(){
	getData($("#yearSelect option:selected").val().substr(2,4));
});

var list = [];
var swiper;
var mon;
var dtlSwiper;
var dtlScSwiper;




function getData(year){

	var dat =ajaxCommon.getSerializedData({
		YY : year
	});
	
	ajaxCommon.getItem({
		id:'monthlyUsimListAjax'
		,cache:false
		,url:'/mstory/monthlyUsimListAjax.do'
		,data:dat
		,dataType:'json'
	}
	,function(data){

		if(!data.monthlyUsimList || data.monthlyUsimList.length == 0){
			location.href="/mstory/snsList.do?status=noData";
		}
		
		$("#yearSelect").empty();
		$("#mainList").empty();
		
/*		if(swiper != null){\
			swiper.destroy();
		}*/

		/*if(!data.monthlyUsimList || data.monthlyUsimList.length < 1){getData(year-1)}*/
		$.each(data.yearList, function(index, item){
			if(item === year){
				$("#yearSelect").append("<option selected>" + 20 + item + "</option>");
				checkRcnt = 1;
			}else{
				$("#yearSelect").append("<option>" + 20 + item + "</option>");
			}
		});
	/*	if(checkRcnt == 0){
			$(".m-storage__select select").prepend("<option selected>" + 20 + cnvrtYear + "</option>");
		}*/
		$.each(data.monthlyUsimList, function(idx, item){
			var html = '';
			console.log(item.mstoryAttDtoList);
			html += '<a class="swiper-slide" href="#n" onclick="goDetail(' + item.ntcartSeq + ')">';
	        html +=  '<div class="m-storage-cover__img">';
	        if(item.mstoryAttDtoList){
	        	html +=  '<img src="' + item.mstoryAttDtoList.get(0).imgNm + '" alt="' + item.mstoryAttDtoList.get(0).imgDesc + '">';
	        }else{
				html +=  '<img src="' + item.thumbImgNm + '" alt="' + item.thumbImgDesc + '">';
			}
			/*html +=  '<img src="https://placeimg.com/260/364/any" alt="' + item.thumbImgDesc + '">';
			*/
	        html +=  '</div>';
	        html +=  '<div class="m-storage-cover__month">' + item.mm + '월 이야기</div>';
	        html +=  '<div class="m-storage-cover__title">' + item.ntcartTitle + '</div>';
	        html +=  '</a>';
		$("#mainList").append(html);
		});
		if(coverflowSwiper){
			coverflowSwiper.update();
		}else{
			setSwiper();
		}
		$("#param").val("?year=" + year);

		
	});
       
}	
	
function setSwiper(){
/*	document.addEventListener('UILoaded', function() {
      var select = document.querySelector('#yearSelect');
      select.addEventListener('change', function() {
        alert('select update', select.value);
        // 년도에 따른 컨텐츠 업데이트 후 coverflowSwiper update 처리.
        coverflowSwiper.update();
      });*/
      coverflowSwiper = KTM.swiperA11y('.m-storage-cover .swiper-container', {
        init: function() {
          // 초기화 직후 다음 콜스텍에서 이전/다음버튼 탭으로 접근 못하도록 처리
          setTimeout(function() {
            var nextBtn = document.querySelector('.m-storage-cover .swiper-button-next');
            var prevBtn = document.querySelector('.m-storage-cover .swiper-button-prev');
            nextBtn.setAttribute('tabindex', '-1');
            prevBtn.setAttribute('tabindex', '-1');
          }, 0);
        },
        effect: 'coverflow',
        centeredSlides: true,
        slidesPerView: 'auto',
        initialSlide: 0,
        coverflowEffect: {
          rotate: 0,
          stretch: 0,
          depth: 150,
          modifier: 1.5,
          slideShadows: false,
        },
        navigation: {
          nextEl: '.m-storage-cover .swiper-button-next',
          prevEl: '.m-storage-cover .swiper-button-prev',
        },
      });
 /*   });	*/
}

function goDetail(seq){

	var dat =ajaxCommon.getSerializedData({
		ntcartSeq : seq,
		yy : $("#yearSelect option:selected").val().substr(2,4),
		key : 'new'
	});
	/*$("#pullModalContents").html('');*/
	openPage('pullPopup', '/mstory/monthlyUsimViewAjax.do', dat);
	
	/*if(detailSwiper){
		detailSwiper.update();
	}else{
		 setDtlSwiper();
    }*/
    
	/*detailSwiper = null;*/
	setDtlSwiper();
	
	/*ajaxCommon.getItem({
		id:'monthlyUsimViewAjax'
		,cache:false
		,url:'/m/mstory/monthlyUsimViewAjax.do'
		,data:dat
		,dataType:'json'
	}
	,function(data){

		$("#detailImgSwiper, #title, #date, #hit, #detailList").empty();
		if(dtlSwiper){
			dtlSwiper.destroy();
		}
		if(dtlScSwiper){
			dtlScSwiper.destroy();
		}
		$("#title").append("[" + data.mstoryListDto.mstoryDto.mm + "월] " + data.mstoryListDto.mstoryDto.ntcartTitle);
		var date = data.mstoryListDto.mstoryDto.ntcartDate
		$("#date").append(date.substring(0,4) + "." + date.substring(4,6) + "." + date.substring(6,8));
		$("#hit").append("조회 | " + data.mstoryListDto.mstoryDto.hitCnt);
		
		$.each(data.mstoryListDto.mstoryAttDtoList, function(index, item){
				$("#detailImgSwiper").append("<div class='swiper-slide'>" + 
										"<img src='" + item.imgNm +"' alt=''>" +
									"</div>");
		});
		
		$.each(data.mstoryListDto.mstoryDetailList, function(idx, item){
			if(item.mm == data.mstoryListDto.mstoryDto.mm){
				mon = idx - 1;
			}else{	
				$("#detailList").append("<div class='swiper-slide' onclick='goDetail(" + item.ntcartSeq + ")'>" +
											"<a href='#n'>" + 
												"<img src='" + item.thumbImgNm + "' alt=''>" + 
												"<strong class='u-block fs-14 u-co-gray-7 u-ta-center'>" + item.mm + "월</strong>" +
											"</a>" +
										"</div>");
			}
		});
		
		setDtlSwiper(mon);
		
	});
	*/
}

function setNewPopData(seq){
	var dat =ajaxCommon.getSerializedData({
		ntcartSeq : seq,
		yy : $("#yearSelect option:selected").val().substr(2,4),
		key : 'old'
	});
	
	ajaxCommon.getItem({
		id:'monthlyUsimViewAjax'
		,cache:false
		,url:'/mstory/monthlyUsimViewAjax.do'
		,data:dat
		,dataType:'text'
	}
	,function(data){
		$(".c-modal__body").remove();
		/*$('#pullModalContents').html(data);*/
		$(".c-modal__header").append(data);
		
		setDtlSwiper(mon);
		
	});
}

function setDtlSwiper(){

		detailSwiper = new KTM.swiperA11y('.m-storage-detail .swiper-container', {
	        centeredSlides: true,
	        observeParents: true,
	        observer: true,
	        width: 424,
	        height: 595,
	        init: function() {
	          // 초기화 직후 다음 콜스텍에서 이전/다음버튼 탭으로 접근 못하도록 처리
	          setTimeout(function() {
	            var nextBtn = document.querySelector('.m-storage-detail .swiper-button-next');
	            var prevBtn = document.querySelector('.m-storage-detail .swiper-button-prev');
	            nextBtn.setAttribute('tabindex', '-1');
	            prevBtn.setAttribute('tabindex', '-1');
	          }, 0);
	        },
	        navigation: {
	          nextEl: '.m-storage-detail .swiper-button-next',
	          prevEl: '.m-storage-detail .swiper-button-prev',
	        },
	        pagination: {
	          el: '.swiper-pagination',
	        },
	      });
		
}


$(".c-tabs__button").on("click", function(){
	if($(this).attr("id") == 'monthlyM'){
		location.href = "/mstory/monthlyUsimList.do";
	}else{
		location.href = "/mstory/snsList.do";
	}
});
