$(document).ready(function(){

	$(".ocrImg").change(function(){

		var image = $(this).val();
		if(image ==""){
			alert("이미지 파일을 선택해 주세요.");
			return false;
		}

		var ext = image.split(".").pop().toLowerCase();
		if($.inArray(ext, ["jpg", "jpeg", "png", "tif","tiff", "bmp"]) == -1) {
			MCP.alert("첨부파일은 이미지 파일만 등록 가능합니다.");
			$(".ocrImg").val("");
			return false;
		}

		var pattern =  /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
	    var fileName = image.split('\\').pop().toLowerCase();
	    if(pattern.test(fileName) ){
	    	MCP.alert('파일명에 특수문자가 포함되어 있습니다.');
	    	$(".ocrImg").val("");
	    	return false;
	    }

		var formData = new FormData();
		formData.append("image", $(".ocrImg")[0].files[0]);

		KTM.LoadingSpinner.show();
		ajaxCommon.getItemFileUp({
			id : 'getOcrImgReadyAjax'
			, cache : false
			, async : false
			, url : '/getOcrImgReadyAjax.do'
			, data : formData
			, dataType : "json"
		}
	    ,function(result){
	    	KTM.LoadingSpinner.hide();
	    	var retCode = result.retCode;
	    	var retMsg = result.retMsg;
	    	var uploadPhoneSrlNo = result.uploadPhoneSrlNo;
	    	var eid = "";
	    	var imei1 = "";
	    	var imei2 = "";
            if(retCode != '0000'){
            	$("#errTxt").html(retMsg).show();
            	$("#eidTxt, #imei1Txt, #imei2Txt").text("");
            	$("#uploadPhoneSrlNo").val("");
            	$("#eid,#imei1,#imei2").val("").parent().removeClass("has-value");
            } else {
            	$("#errTxt").html("").hide();
            	eid = result.eid;
            	imei1 = result.imei1;
            	imei2 = result.imei2;
            	$("#eidTxt").text(eid);
            	$("#imei1Txt").text(imei1);
            	$("#imei2Txt").text(imei2);
            	$("#uploadPhoneSrlNo").val(uploadPhoneSrlNo);
            }
            $(".ocrImg").val("");
            var el = document.querySelector('#esim-check-modal');
    		var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    		modal.open();
	    });

	});

	// 팝업에 확인
	$("#setBtn").click(function(){
		$("#eid").val($("#eidTxt").text()).parent().addClass("has-value");
		$("#imei1").val($("#imei1Txt").text()).parent().addClass("has-value");;
		$("#imei2").val($("#imei2Txt").text()).parent().addClass("has-value");;
		 $(".ocrImg").val("");
	});

	// 다시등록하기
	$("#reSetBtn").click(function(){
		$(".ocrImg").val("");
		$("#errTxt").html("");
		$("#eidTxt, #imei1Txt, #imei2Txt").text("");
		$("#uploadPhoneSrlNo").val("");
		$("#eid,#imei1,#imei2").val("").parent().removeClass("has-value");
	});

	// 다음
	$("#nextStep").click(function(){
		var uploadPhoneSrlNo = Number(ajaxCommon.isNullNvl($("#uploadPhoneSrlNo").val(),0));
		if( uploadPhoneSrlNo > 0 ){
			location.href="/appForm/appFormDesignView.do?prdtIndCd=10&uploadPhoneSrlNo="+uploadPhoneSrlNo;
		} else {
			MCP.alert("이미지등록하여 eid/imei/imei2 값을 확인해 주세요.");
			return false;
		}

	});

//	$("#mp").click(function(){
//
//		var varData = ajaxCommon.getSerializedData({
//			imei1 : $("#imei1").val()
//			,imei2 : $("#imei2").val()
//			,eid : $("#eid").val()
//			,uploadPhoneSrlNo : Number($("#uploadPhoneSrlNo").val())
//		});
//
//		ajaxCommon.getItem({
//	        id:'eSimChkAjax'
//	        ,cache:false
//	        ,url:'/appForm/eSimChkAjax.do'
//	        ,data: varData
//	        ,dataType:"json"
//	        ,async:false
//	    }
//
//		,function(jsonObj){
//			alert("1");
//
//		});
//
//
//
//	});

});
