$(document).ready(function (){

	//전화번호 검색
	$("#btnSearch").click(function(){
		var hopeNum = $("#hopeNum").val();
		if(hopeNum == "" || hopeNum.length < 4){
			MCP.alert("희망번호를 입력하세요.");
			return false;
		}

		var ncn = $("#ctn option:selected").val();
		var varData = ajaxCommon.getSerializedData({
			ncn : ncn,
			chkCtn : hopeNum
	    });

		ajaxCommon.getItem({
	        id:'numChgeListAjax'
	        ,cache:false
	        ,url:'/mypage/numChgeListAjax.do'
	        ,data: varData
	        ,dataType:"json"
	     }
		,function(data){
			if(data.returnCode == "00") {
				$("#selNumDivArea").empty();
				var list = data.list;
				var searchCnt = data.searchCnt;
				var contents="";
				if(list == null || list.length == 0){
					MCP.alert('선택 가능한 번호가 없습니다.<br/>다른 번호로 입력하시기 바랍니다.');
					return false;
				}
				$("#searchCnt").text(data.searchCnt+"회");
				for ( var i = 0; i < list.length; i++) {

					contents+='<input class="c-radio" id="radSelNum'+i+'" type="radio" name="radSelNum" ';
					contents+=' data-market = "'+list[i].marketGubun +'"';
					contents+=' data-ctn = "'+list[i].ctn +'"';
					contents+=' data-sctn = "'+list[i].sctn +'"';
					contents+=' value="'+list[i].orignCtn+'">';
					contents+='<label class="c-label u-mb--20" for="radSelNum'+i+'">'+list[i].ctn+'</label>';
				}

				$("#selNumDivArea").html(contents);
				$('[name=ctn]').prop("checked",false);
				$("#selPhoneNumCfrmBtn").prop("disabled",true);
				var el = document.querySelector("#modalNumInquiry");
				var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
				modal.open();

			}else if(data.returnCode == "33"){
				MCP.alert(data.message);
				return false;
			}else {
				MCP.alert(data.message,function(){
					ajaxCommon.createForm({
						method:"post"
						,action:"/mypage/numberView01.do"
					});
					var ncn = $("#ctn option:selected").val();
					ajaxCommon.attachHiddenElement("ncn",ncn);
					ajaxCommon.formSubmit();
				});
			}
		});

	});

	//상품 체크박스 선택시
	$(document).on("click", "#selNumDivArea > input[type='radio']", function(){
		$("#selPhoneNumCfrmBtn").prop("disabled",false);
	});


	setInputFilter(document.getElementById("hopeNum"), function(value) {
		return /^\d*\.?\d*$/.test(value);
	});


});


function selPhoneNum(){

	if($("input[name='radSelNum']:checked").length <= 0){
		MCP.alert("전화번호를 선택해주세요.");
		return;
	}

	$("#preSelPhoneDivArea").hide();
	$("#postSelPhoneDivArea").show();
	var radioId = $("input:radio[name=radSelNum]:checked").attr("id");
	$("#selPhoneNumSpanArea").text($("label[for='"+radioId+"']").text());

	var ctnVal = $("input:radio[name=radSelNum]:checked").val();
	var ctn = $("input:radio[name=radSelNum]:checked").data("ctn");
	var sctn = $("input:radio[name=radSelNum]:checked").data("sctn");
	var markGubun = $("input:radio[name=radSelNum]:checked").data("market");


	$("#resvHkCtn").val(ctnVal);
	$("#resvHkSCtn").val(sctn);
	$("#resvHkMarketGubun").val(markGubun);

	$("#modalNumInquiry button.c-button .c-hidden").click();
}

function selPhoneNumCancel(){

  	$("#preSelPhoneDivArea").show();
  	$("#postSelPhoneDivArea").hide();
	$("#selPhoneNumSpanArea").text('');

	$("#hopeNum").val('');
	$("#resvHkCtn").val("");
	$("#resvHkSCtn").val("");
	$("#resvHkMarketGubun").val("");
	$("#btnSearch").prop("disabled",true);
}

function goPhoneNumChange(){

	if($("#resvHkCtn").val()==""){
		$("#hopeNum").focus();
		MCP.alert("희망번호를 입력하세요.");
		return false;
	}

	KTM.Confirm("변호변경을 하시겠습니까?", function (){
		this.close();
		var ncn = $("#ctn option:selected").val();
		var resvHkCtn = $("#resvHkCtn").val();
		var resvHkSCtn = $("#resvHkSCtn").val();
		var resvHkMarketGubun = $("#resvHkMarketGubun").val();

		var varData = ajaxCommon.getSerializedData({
			ncn : ncn,
			resvHkCtn : resvHkCtn,
			resvHkSCtn : resvHkSCtn,
			resvHkMarketGubun : resvHkMarketGubun
	    });

		ajaxCommon.getItem({
	        id:'numChgeChgAjax'
	        ,cache:false
	        ,url:'/mypage/numChgeChgAjax.do'
	        ,data: varData
	        ,dataType:"json"
	     }
		,function(data){
			if(data.returnCode == "00") {

				MCP.alert('번호 변경이 완료되었습니다.',function(){
					ajaxCommon.createForm({
						method:"post"
						,action:"/mypage/numberView01.do"
					});

					ajaxCommon.attachHiddenElement("ncn",ncn);
					ajaxCommon.formSubmit();
				});
			} else {
				MCP.alert(data.message);
				return false;
			}
		});
	});

}

function setInputFilter(textbox, inputFilter) {
	  ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
	    textbox.addEventListener(event, function() {
	      if (inputFilter(this.value)) {
	        this.oldValue = this.value;
	        this.oldSelectionStart = this.selectionStart;
	        this.oldSelectionEnd = this.selectionEnd;
	      } else if (this.hasOwnProperty("oldValue")) {
	        this.value = this.oldValue;
	        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	      } else {
	        this.value = "";
	      }
		  if(this.value.length == 4){
			$("#btnSearch").prop("disabled",false);
		  }else{
			$("#btnSearch").prop("disabled",true);
		  }

	    });
	  });
	}

function select(){
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/mypage/numberView02.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}


