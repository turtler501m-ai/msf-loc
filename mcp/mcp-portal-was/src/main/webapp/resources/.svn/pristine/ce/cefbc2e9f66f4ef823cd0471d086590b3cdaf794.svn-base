var checkRcnt = 0;
let today = new Date();
let yy = today.getFullYear() + '';
let cnvrtYear = yy.substring(2,4);

$(document).ready(function(){
	
	if($("#year").val()){
		getData($("#year").val());
	}else{
		getData(cnvrtYear);
	}
	
	$(document).on("click", "#snsShareBut", function(){
		openPage('snsSharePop', '/m/snsSharePop.do', '')
	});
	

});
 

$("#yearSelect").on("change", function(){
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
		,url:'/m/mstory/monthlyUsimListAjax.do'
		,data:dat
		,dataType:'json'
	}
	,function(data){

		$(".m-storage__select select").empty();
		$("#mainList").empty();
		if(swiper != null){
			swiper.destroy();
		}

		/*if(!data.monthlyUsimList || data.monthlyUsimList.length < 1){getData(year-1)}*/
		$.each(data.yearList, function(index, item){

			if(item === year){
				$(".m-storage__select select").append("<option selected>" + 20 + item + "</option>");
				checkRcnt = 1;
			}else{
				$(".m-storage__select select").append("<option>" + 20 + item + "</option>");
			}
		});
	/*	if(checkRcnt == 0){
			$(".m-storage__select select").prepend("<option selected>" + 20 + cnvrtYear + "</option>");
		}*/
		$.each(data.monthlyUsimList, function(idx, item){
			var html = '';
			html += "<div class='swiper-slide m-storage__swiper__cover' data-dialog-trigger='' onclick='goDetail(" + item.ntcartSeq + ")'>";
			html += "<a href='#' class='goDtl'>";
			if(item.mstoryAttDtoList){
				html += "<img src='" + item.mstoryAttDtoList.get(0).imgNm + "' alt='" + item.mstoryAttDtoList.get(0).imgDesc + "'>";
			}else{
				html +=  '<img src="' + item.thumbImgNm + '" alt="' + item.thumbImgDesc + '">';
			}
			html += "<div class='m-storage__swiper__title'>" + item.mm + "월 이야기</div>";
			html += "<div class='m-storage__swiper__desc'>" + item.ntcartTitle + "</div>";
			html += "</a>";
			html += "</div>";
			$("#mainList").append(html);
		});

		setSwiper();
		$("#param").val("?year=" + year);
	});
       
}	
	
function setSwiper(){
	swiper = new Swiper(".m-storage__swiper", {
		speed : 400,
		initialSlide : 0,
		spaceBetween : 0,
		slidesPerView : "auto",
		centeredSlides : true,
	});
}

function goDetail(seq){
	/*$("#main-content").append("<input type='hidden' name='ntcartSeq' value='" + seq + "'/>");
	$("input[name='yy']").val($("#yearSelect option:selected").val().substr(2,4))

	$("#mainForm").submit();*/
	
	var dat =ajaxCommon.getSerializedData({
		key : 'new',
		ntcartSeq : seq,
		yy : $("#yearSelect option:selected").val().substr(2,4)
	});
	
	openPage('pullPopup', '/m/mstory/monthlyUsimViewAjax.do', dat);
	
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
		
	});*/
	var mon = $("#mon").val();
	setDtlSwiper(mon);
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
		,url:'/m/mstory/monthlyUsimViewAjax.do'
		,data:dat
		,dataType:'text'
	}
	,function(data){
		$(".c-modal__body").remove();
		/*$('#pullModalContents').html(data);*/
		$(".c-modal__header").after(data);
		
		/*setDtlSwiper(mon);*/
		
	});
}

function setDtlSwiper(bas){

		dtlSwiper = new Swiper(".mstroy-view .swiper-container", {
			observeParents : true,
			observer : true,
			navigation : {
				nextEl : ".mstroy-view .swiper-next",
				prevEl : ".mstroy-view .swiper-prev",
			},
			pagination : {
				el : ".mstroy-view .swiper-pagination",
			},
		});

		dtlScSwiper = new Swiper(".mstroy-view__short-cut-swiper", {
			observeParents : true,
			observer : true,
			slidesPerView : 2,
			spaceBetween : 16,
			initialSlide : bas,
			navigation : {
				nextEl : ".swiper-next.short-cut",
				prevEl : ".swiper-prev.short-cut",
			},
		//- pagination: {
		//-   el: ".mstroy-view .swiper-pagination",
		//- },
		});
}


$(".c-tabs__button").on("click", function(){
	if($(this).attr("id") == 'monthlyM'){
		location.href = "/m/mstory/monthlyUsimList.do";
	}else{
		location.href = "/m/mstory/snsList.do";
	}
});
