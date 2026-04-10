$(document).ready(function (){

	$(".chekPUllNn").hide();
	$(".chekPUllYn").show()

	btn_tab($("#selTab").val());

});

//당겨쓰기 내역조회
function chgHistList(){
	var varData = ajaxCommon.getSerializedData({
 		 ncn : $("#ctn option:selected").val()
	});
	ajaxCommon.getItem({
            id:'pullDataHistListAjax'
	        ,cache:false
	        ,url:'/m/mypage/pullDataHistListAjax.do'
	        ,data: varData
	        ,dataType:"json"
    }
    ,function(data){

		if(data.returnCode == "00"){
			var resultData = data.resultMap;
			$("#tdoayHist").text(resultData.today+"기준");
			var itemVOList = resultData.itemVOList.result;
			var itemHtml = '';

			if(itemVOList.length > 0){
				for(var idx=0; idx<itemVOList.length; idx++){
					itemHtml +=' <tr>';
					itemHtml +=' <td>'+itemVOList[idx].effectiveDate+'</td>';
					itemHtml +=' <td>'+itemVOList[idx].effectiveEndDate+'</td>';
					itemHtml +=' <td> +'+itemVOList[idx].memo+'</td>';
					itemHtml +=' </tr>';
				}

				$("#dataHistView").html(itemHtml);
				$(".histDataList").show();
			}else{
				$(".histDataList").hide();
				$(".histNoData").show();
			}

		}

	}
  );
}

//당겨쓰기 신청
function chgTotalUseTimeList(){
	var varData = ajaxCommon.getSerializedData({
 		 ncn : $("#ctn option:selected").val()
	});

;
	ajaxCommon.getItem({
            id:'totalUseTimeListAjax'
	        ,cache:false
	        ,url:'/m/mypage/totalUseTimeListAjax.do'
	        ,data: varData
	        ,dataType:"json"
    }
    ,function(data){

		if(data.returnCode == "00"){
			var resultData = data.resultMap;

			if(resultData.checkPass){
				$("#svcNm").text(resultData.rateNm);

				var remain = "0";

				var itemTelVO = resultData.itemTelVO.result;

				if(resultData.useTimeSvcLimit){
					$("#chk01").hide();
					$("#chk02").show();
					$(".cheklabel").hide();
				}else {
					$("#chk01").show();
					$("#chk02").hide();
					$(".cheklabel").show();
				}

				if(itemTelVO  != null){
					if(itemTelVO.strFreeMinRemain != null){
						if(itemTelVO.strFreeMinRemain == '999999999'){
							remain ='기본제공'
						} else{
							var gunNm = '';
							var freeMinRemain = itemTelVO.strFreeMinRemain;
							var freeMinRemaintTmp = '';
							if(freeMinRemain >= 1000){
								gunNm =  "GB";
								freeMinRemaintTmp = freeMinRemain/1024;
								remain = Number.parseFloat(freeMinRemaintTmp).toFixed(2)+gunNm;
							} else{
								gunNm = "MB";
								remain= freeMinRemain + gunNm;
							}
							var strFreeMinUse = itemTelVO.strFreeMinUse;
						 	var minGunNm = '';
						 	var outRemain ='';
							var strFreeMinUseTmp = '';

							if(strFreeMinUse >= 1000){
								minGunNm = "GB";
								strFreeMinUseTmp = strFreeMinUse/1024;
								outRemain = Number.parseFloat(strFreeMinUseTmp).toFixed(2)+minGunNm;
							} else{
								minGunNm = "MB";
								outRemain = strFreeMinUse+minGunNm;
							}
						}
					}
				}

				const element = document.getElementById('totalPercent');

				if(itemTelVO.strFreeMinUseInt != null){
					if((itemTelVO.strFreeMinRemainInt / itemTelVO.strFreeMinTotalInt)* 100 == 'NaN'){
						element.style.width = '0%';
					}else {
						var strtotal =(itemTelVO.strFreeMinRemainInt / itemTelVO.strFreeMinTotalInt)* 100
						element.style.width = strtotal+'%';
					}

					var data = itemTelVO.strSvcName.replaceAll('-합계','');
					$("#totalData").html(data);
					$("#outRemain").html('<span class="u-co-gray-8 u-fw--regular" >'+outRemain+"</span> / "+remain);
					$("#totalPull").val(remain);

				} else{
					element.style.width = '0%';
					$("#totalData").html('잔여데이터');
					$("#totalPull").val("0");
					$("#outRemain").html('<span class="u-co-gray-8 u-fw--regular" >0</span> / 0');

				}

				$("#todayData").text(resultData.month+" 사용가능 잔여 데이터 "+remain);

				//당겨쓰기 선택
				var useDataHtml ='';
				useDataHtml+='<option value="">선택</option>';

				if(!resultData.use100){
					useDataHtml += '<option value="PLLDAT100" selected>100MB</option>';
				}

				if(!resultData.use500){
					useDataHtml += '<option value="PLLDAT500">500MB</option>';
				 }
				 if(!resultData.use1G){
					useDataHtml += '<option value="PLLDAT01G">1GB</option>';
				 }
				 $(".c-form__select").addClass("has-value");
				 $("#useDataList").html(useDataHtml);
				// $("#chk01").show();
				  $(".chekPUllYn").show();
				 $(".chekPUllNn").hide();
				 $("#pullYn1").removeClass("c-box c-box--type5 c-flex c-flex--jfy-between");
				 $("#pullYn2").removeClass("c-box c-box--type5 c-flex c-flex--jfy-between");
			} else{
				$("#pullYn1").addClass("c-box c-box--type5 c-flex c-flex--jfy-between");
				$("#pullYn2").addClass("c-box c-box--type5 c-flex c-flex--jfy-between");
				$(".chekPUllNn").show();
				$(".chekPUllYn").hide()
				$("#chk01").hide();
			}
		}
	});
};

//데이터 당겨쓰기 신청
function btn_reg(){


	if($("#totalPull").val() == "0"){
		MCP.alert("신청 가능한 데이터 잔여량이 없습니다.");
		return false;
	}

	if($("#totalPull").val() == "0"){
		MCP.alert("신청 가능한 데이터 잔여량이 없습니다.");
		return false;
	}

	var varData = ajaxCommon.getSerializedData({
 		 ncn : $("#ctn option:selected").val()
 		,soc : $("#useDataList option:selected").val()

	});


	ajaxCommon.getItem({
            id:'realChargeListAjax'
	        ,cache:false
	        ,url:'/m/mypage/regSvcChgAjax.do'
	        ,data: varData
	        ,dataType:"json"
    }
    ,function(data){
		if(data.returnCode == "S") {
			MCP.alert("신청성공");

			ajaxCommon.createForm({
			    method:"post"
			    ,action:"/m/mypage/pullData01.do"
			});
			ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
			ajaxCommon.formSubmit();
       	} else {

       		MCP.alert(data.message);
       	}
		return;
	});
}

//요금제 변경으로 이동
function btn_farReg(){
	ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/farPricePlanView.do"
	});

	ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
	ajaxCommon.formSubmit();
}


//데이터 당겨쓰기
function btn_pullReg(){

	var useDataList =$("#useDataList option:selected").val();

	if(useDataList == ""){
		MCP.alert("신청할 당겨쓰기 용량이 없습니다.", function(){
			$("#useDataList").focus();
		});
		return false;
	}


	if($("#totalPull").val() == "0"){
		MCP.alert("신청 가능한 데이터 잔여량이 없습니다.");
		return false;
	}
	if($("#certifyYn").val()!="Y"){
		var parameterData = ajaxCommon.getSerializedData({
			 menuType	 : $("#menuType").val()
			 ,phoneNum 	 : $("#ctn option:selected").text()
			// ,contractNum :$("#ctn option:selected").val()
			,contractNum : $("#contractNum").val()

		});
		openPage('pullPopupByPost','/m/sms/smsAuthInfoPop.do',parameterData);
	} else{
		btn_reg()
	}
}


function btn_tab(tab){

	$("#selTab").val(tab);

	if(tab == "1"){
		chgTotalUseTimeList();
	}else if(tab == "2"){
		chgHistList()

	}
}

function select(){

	var tab = $("#selTab").val();
	if(tab == "1"){
		chgTotalUseTimeList();
	}else if(tab == "2"){
		chgHistList()
	}
}
