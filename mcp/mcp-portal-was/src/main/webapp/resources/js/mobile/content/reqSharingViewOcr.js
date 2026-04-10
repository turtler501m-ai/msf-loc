let OCRtoken ;
var tmOcrint;
var OCRtype;

$(document).ready(function(){

	$('._ocrRecord').click(function(){
		KTM.LoadingSpinner.show();
		var mobileAppChk = $("#mobileAppChk").val();
		OCRtype = $(this).data('type');

		if (mobileAppChk == "A") {
			requestPermission('PIC', 'review');
		} else {
			review();
		}

		KTM.LoadingSpinner.hide();
	});

});

function review(){
	$.ajax({
		type: 'post',
		url: '/m/getOcrReadyAjax.do',
		data: {type:OCRtype},
		success: function(result) {
			if(result.returnCode == '0000'){
				var data = JSON.parse(result.message);

				var openUrl = data._links._start_page.href ;

				if ( openUrl == undefined ){
					MCP.alert('다시 시도해주세요.');
				} else {
					var OcrPopupStatus = window.open(openUrl);
					OCRtoken = data.token;

					var tm = setTimeout(function(){}, 5000);
					clearTimeout(tm);

					tmOcrint = setInterval(function(){
						if(OcrPopupStatus.closed){
							resultOcr();
						}
					}, 1000);
					//결과 자동호출

				}
			} else {
				MCP.alert(result.message);
			}
		}
	});
}

function resultOcr(){
	$.ajax({
		type: 'post',
		url: '/m/getOcrResultAjax.do',
		data: { token: OCRtoken },
		success: function(result) {
			var chk = JSON.parse(result.message);
			if(result.returnCode == '0000'){

				var idCardType = chk.idCardType;

				if (idCardType == "usim") {

					var reqUsimSn = "";

					if(chk.modified.usim_code != undefined) {
						reqUsimSn = chk.modified.usim_code ;
						if (reqUsimSn.length > 19) {
							reqUsimSn = reqUsimSn.substring(0,19);
						}
					} else {
						alert("jsonObj.modified.usim_code [undefined]");
						return;
					}
					$("#iccId").val(reqUsimSn);

				}

				//$("#iccId").val(chk.modified.number);
			} else {
				MCP.alert(result.message);
			}

			clearInterval(tmOcrint);

		}
	});
}
