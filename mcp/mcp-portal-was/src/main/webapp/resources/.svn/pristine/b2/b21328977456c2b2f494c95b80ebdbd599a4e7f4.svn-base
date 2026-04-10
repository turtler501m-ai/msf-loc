/**
 *
 */
$(document).ready(function(){

	var ncn = $("#ctn option:selected").val();
	getBillInfo(ncn);

	$("#selCategory").on("change", function(){
		if(this.value == 'MB'){
			$("#cateMobile").removeClass("u-hide");
			$("#cateEmail").addClass("u-hide");
			$("#catePost").addClass("u-hide");
		}else if(this.value == 'CB'){
			$("#cateMobile").addClass("u-hide");
			$("#cateEmail").removeClass("u-hide");
			$("#catePost").addClass("u-hide");
		}else if(this.value == 'LX'){
			$("#cateMobile").addClass("u-hide");
			$("#cateEmail").addClass("u-hide");
			$("#catePost").removeClass("u-hide");
		}else{
			$("#cateMobile").removeClass("u-hide");
			$("#cateEmail").addClass("u-hide");
			$("#catePost").addClass("u-hide");
		}
	});

	$("#Fbill01").click(function(){

		ajaxCommon.createForm({
			method:"post"
			,action:"/m/mypage/billWayReSend.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});

});

function select(){
	var ncn = $("#ctn option:selected").val();
	getBillInfo(ncn);
}

function getBillInfo(ncn){

	var varData = ajaxCommon.getSerializedData({
		ncn : ncn
    });

	ajaxCommon.getItem({
	    id:'myBillInfoViewAjax'
	    ,cache:false
	    ,async:false
	    ,url:'/m/mypage/myBillInfoViewAjax.do'
	    ,data: varData
	    ,dataType:"text"
	},function(data){

		var returnCode = data.returnCode;
		var message = data.message;
		var resultMap = data.resultMap;
		var reqTypeNm;
		var reqType;
		var blaAddr;
		var dueDay;

		if(returnCode =="00"){

			if(data.payMethod == '지로'){
				blaAddr = data.blAddr;
				reqTypeNm = '청구지';
			}else if(data.billTypeCd == 'CB'){
				//blaAddr = data.email;
				blaAddr = data.maskedEmail;
				reqTypeNm = '메일주소';
			}else if(data.billTypeCd == 'MB'){
				blaAddr = data.ctn;
				reqTypeNm = '휴대폰번호';
			}else{
				blaAddr = data.addr;
				reqTypeNm = '청구지';
			}
			$("#postNo").val(data.zipCode);
			$("#address2").val(data.sAddr);
			$("#address1").val(data.pAddr);

			if(data.billTypeCd == 'CB'){
				reqType = '이메일 명세서';
				$("#selCategory option[value='CB']").prop("selected", true);
				$("#cateEmail").removeClass("u-hide");
				$("#cateMobile").addClass("u-hide");
				$("#catePost").addClass("u-hide");
				$("#emailAdr").val(data.email);
			}else if(data.billTypeCd == 'LX'){
				reqType = '우편 명세서';
				$("#selCategory option[value='LX']").prop("selected", true);
				$("#cateEmail").addClass("u-hide");
				$("#cateMobile").addClass("u-hide");
				$("#catePost").removeClass("u-hide");
				$("#postNo").val(data.zipCode);
				$("#address2").val(data.sAddr);
				$("#address1").val(data.pAddr);
			}else if(data.billTypeCd == 'MB'){
				reqType = '모바일 명세서(MMS)';
				$("#selCategory option[value='MB']").prop("selected", true);
				$("#cateEmail").addClass("u-hide");
				$("#cateMobile").removeClass("u-hide");
				$("#catePost").addClass("u-hide");
			}else{
				reqType = '모바일 명세서(MMS)';
				$("#selCategory option[value='MB']").prop("selected", true);
				$("#cateEmail").addClass("u-hide");
				$("#cateMobile").removeClass("u-hide");
				$("#catePost").addClass("u-hide");
			}

			if(data.billCycleDueDay == '99'){
				dueDay = '말일';
			}else{
				dueDay = data.billCycleDueDay + "일";
			}

			var html = '';
			$(".info-box").html('');
			var payMethod = $.trim(data.payMethod);
			if(payMethod == '자동이체'){
				html += '<dl>';
				html += '		<dt>납부방법</dt>';
				html += '		<dd>' + data.payMethod + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>납부계정정보</dt>';
				html += '		<dd>' + data.blBankAcctNo + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>납부기준일</dt>';
				html += '		<dd>' + dueDay + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>명세서유형</dt>';
				html += '		<dd>' + reqType + '</dd>';
				html += '</dl>';
				if(data.billTypeCd != 'MB'){
					html += '<dl>';
					html += '		<dt>' + reqTypeNm + '</dt>';
					html += '		<dd>' + blaAddr + '</dd>';
					html += '</dl>';
				}

			}else if(payMethod == '지로'){
				html += '<dl>';
				html += '		<dt>납부방법</dt>';
				html += '		<dd>' + data.payMethod + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>납부기준일</dt>';
				html += '		<dd>' + dueDay + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>명세서유형</dt>';
				html += '		<dd>' + reqType + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>' + reqTypeNm + '</dt>';
				html += '		<dd>' + blaAddr + '</dd>';
				html += '</dl>';

			}else if(payMethod == '신용카드'){
				html += '<dl>';
				html += '		<dt>납부방법</dt>';
				html += '		<dd>' + data.payMethod + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>카드번호</dt>';
				html += '		<dd>' + data.prevCardNo + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>납부기준일</dt>';
				html += '		<dd>' + dueDay + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>명세서유형</dt>';
				html += '		<dd>' + reqType + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>' + reqTypeNm + '</dt>';
				html += '		<dd>' + blaAddr + '</dd>';
				html += '</dl>';

			}else if(payMethod == '간편결제'){
				html += '<dl>';
				html += '		<dt>납부방법</dt>';
				html += '		<dd>' + data.payMethod + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>납부기준일</dt>';
				html += '		<dd>' + dueDay + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>명세서유형</dt>';
				html += '		<dd>' + reqType + '</dd>';
				html += '</dl>';
				html += '<dl>';
				html += '		<dt>' + reqTypeNm + '</dt>';
				html += '		<dd>' + blaAddr + '</dd>';
				html += '</dl>';
			}

		} else {
			html += '<dl>';
			html += '		<dt>납부방법</dt>';
			html += '		<dd>-</dd>';
			html += '</dl>';
			html += '<dl>';
			html += '		<dt>납부기준일</dt>';
			html += '		<dd>-</dd>';
			html += '</dl>';
			html += '<dl>';
			html += '		<dt>명세서유형</dt>';
			html += '		<dd>-</dd>';
			html += '</dl>';
		}
		$(".info-box").html(html);

	});
}

function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
	$("#postNo").val(zipNo);//도로명주소
	$("#address1").val(roadAddrPart1);//상세주소
	$("#address2").val(addrDetail);//우편번호
	fn_mobile_layerClose('newlayer3');
}


function changeSubmit() {
	var billType = $("#selCategory").val();

	var email = "";
	var cfrm = "";
	var varData = "";
	var cmpltMsg = "";
	if (billType == "CB") {

		if($('#emailAdr').val() == ''){
			MCP.alert('이메일주소를 입력해 주세요.');
			$('#emailAdr').focus();
			return;
		}

		if($("#emailAdr").val().length < 8) {
			MCP.alert("이메일주소를 올바르게 입력해 주세요.")
			$("#emailAdr").focus();
			return;
		}

		// 정규식 - 이메일 유효성 검사
		var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if(!regEmail.test($("#emailAdr").val())){
			MCP.alert("이메일주소를 이메일형식에 맞게 입력해 주세요.");
			return false;
		}
		email = $("#emailAdr").val();

		cmpltMsg = "e-mail청구서 변경 요청을 완료 하였습니다.";
		cfrm = new KTM.Confirm("e-mail 청구서를 변경하시겠습니까?", function(){
			varData = ajaxCommon.getSerializedData({
				billTypeCd : billType,
				billAdInfo : email,
				ncn : $("#ctn option:selected").val()
			});
			changeBill(cfrm, varData, cmpltMsg);
			this.close();
		});

	} else if (billType == "LX") {

		if($('#postNo').val().length < 5 || !jQuery.isNumeric($('#postNo').val())){
			alert("우편번호를 확인해 주세요");
			$('#postNo').focus();
			return false;
		}

		if($('#address1').val().length < 10){
			alert('주소를 확인해 주세요.');
			$('#address1').focus();
			return false;
		}
		if($('#address2').val().length < 1){
			alert('나머지 주소를 확인해 주세요.');
			$('#address2').focus();
			return false;
		}

		cmpltMsg = "주소변경 요청을 완료 하였습니다.";
		cfrm = new KTM.Confirm("우편 청구서 주소를 변경하시겠습니까?",function(){
			varData = ajaxCommon.getSerializedData({
				billTypeCd : billType,
				zip : $('#postNo').val(),
				addr1 : $('#address1').val(),
				addr2 : $('#address2').val(),
				ncn : $("#ctn option:selected").val()
			});
			changeBill(cfrm, varData, cmpltMsg);
			this.close();
		});

	} else if (billType == "MB") {

		cmpltMsg = "MMS명세서 변경 요청을 완료 하였습니다.";
		cfrm = new KTM.Confirm("MMS명세서를 변경하시겠습니까?", function(){
			varData = ajaxCommon.getSerializedData({
				billTypeCd : billType,
				ncn : $("#ctn option:selected").val()
			});

			changeBill(cfrm, varData, cmpltMsg);
			this.close();

		});
	}
}

function changeBill(cfrm, varData, cmpltMsg){
	if(cfrm){
        ajaxCommon.getItem({
            id:'billChgAjax'
            ,cache:false
            ,url:'/mypage/billChgAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async : false
         }
         ,function(jsonObj){
        	 if(jsonObj.returnCode == "00") {
                 MCP.alert(cmpltMsg,function(){
 					location.reload();
 				});
             }else{
                 MCP.alert(jsonObj.message);
             }
         });
    }
}
