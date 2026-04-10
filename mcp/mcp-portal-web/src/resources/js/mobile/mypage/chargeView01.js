$(document).ready(function (){

	$(".realSrch").click(function(){
		ajaxCommon.createForm({
			method:"post"
			,action:"/m/mypage/chargeView03.do"
		});
		ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
		ajaxCommon.formSubmit();
	});

	$("#billMonth").on("change",function(){
		ajaxCommon.createForm({
			method:"post"
			,action:"/m/mypage/chargeView01.do"
		});

		ajaxCommon.attachHiddenElement("billMonth", $("#billMonth option:selected").val());
		ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
		ajaxCommon.formSubmit();
	});

});

function select(){

	ajaxCommon.createForm({
		method:"post"
		,action:"/m/mypage/chargeView01.do"
	});
	ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
	ajaxCommon.formSubmit();
}


//실시간요금조회
function chargeRealSearch(){

	var varData = ajaxCommon.getSerializedData({
 		 ncn : $("#btn_ncn2 option:selected").val()
	});


	ajaxCommon.getItem({
            id:'realChargeListAjax'
	        ,cache:false
	        ,url:'/m/mypage/realChargeListAjax.do'
	        ,data: varData
	        ,dataType:"json"
    }
    ,function(data){
		var data = data.resultMap;
		$("#rateNm").text(data.rateNm);

 		 var list = data.vo;
 		 if(list.length > 0){
			var revHtml = '';
		  	for(var i=0; i<list.length; i++){

				if(!list.length -1){
					revHtml +='<dl>';
					revHtml +='<dt>'+list[i].gubun+'</dt>';
					revHtml +='<dd>'+numberWithCommas(list[i].payment)+'원</dd>';
					revHtml +='</dl>';
				}else{
					revHtml +='<dl>';
					revHtml +='<dt><b>'+list[i].gubun+'</b></dt>';
					revHtml +='<dd>'+numberWithCommas(data.sumAmt)+'원</dd>';
					revHtml +='</dl>';
				}

			}

			$("#totalCnt").html(numberWithCommas(data.sumAmt)+"원");
			$("#searchTime").text(data.searchTime);
			$("#dateViewChk").html('<span class="u-co-mint">'+data.useDay+' 일</span>/ '+data.useTotalDay+'일');


	 		 var widthSize = parseInt(data.useDay)/parseInt(data.useTotalDay)*100;
			 var width = 100-Math.floor(widthSize,2);

	 		 $("#dateViewStyle").css("width",width+'%');
			 $(".dataYn").show();
			 $(".noDataYn").hide();

		  } else{
			revHtml +='<div class="c-nodata">';
		    revHtml +='<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
		    revHtml +='<p class="c-nodata__text">실시간 요금이 조회되지 않습니다.</p>';
		    revHtml +='</div>';
		     $(".dataYn").hide();
			 $(".noDataYn").show();
		  }

		  $("#rateView").show();
		  $("#realDtlView").html(revHtml);
	  }
  );
}

function detailView(id, messageLine){

	var varData = ajaxCommon.getSerializedData({
 		messageLine : messageLine
 		, ncn : $("#ctn option:selected").val()
 		, billMonth :$("#billMonth option:selected").val()
	});

	ajaxCommon.getItem({
            id:'chargeDetailItemAjax'
	        ,cache:false
	        ,url:'/m/mypage/chargeDetailItemAjax.do'
	        ,data: varData
	        ,dataType:"json"
    }
    ,function(data){

		if (data.returnCode=="00"){
			var list = data.result;
			var contents = '';

			 if (list.length > 0){
				for ( var i = 0; i < list.length; i++) {
				    var amt = numberWithCommas(list[i].amt);
					contents += '<li class="fee-sub-item">';
					contents += '<span class="fee-sub-title">'+list[i].descr+'</span>';
					contents += '<span class="fee-sub-charge c-black">'+amt+'원</span>';
					contents += ' </li>';
				}
				$("#"+id).html(contents);
			 }
		}else{
			MCP.alert(data.message);
		}
    });
}

