<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 고객조회
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.10.16 최민 최초생성
 * @
 * @author : 최민
 * @Create Date : 2015. 10. 16.
 */
%>

<div id="FORM1" class="section-box"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:40, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"전체", name:"totCnt", width:40, readonly:true, offsetLeft:10, numberFormat:"000,000", userdata:{align:"right"}}
					, {type:"newcolumn"}
					, {type:"input", label:"사용중", name:"usgCnt", width:40, readonly:true, offsetLeft:20, numberFormat:"000,000", userdata:{align:"right"}}
					, {type:"newcolumn"}
					, {type:"input", label:"해지", name:"tmntCnt", width:40, readonlyt:true, offsetLeft:20, numberFormat:"000,000", userdata:{align:"right"}}
				]}
			]
		},
		GRID1 : {
			css : {
				height : "200px"
			},
			title : "개통이력조회",
			header : "계약번호,구매유형,업무구분,계약상태,개통일자,상태변경일자",
			columnIds : "contractNum,reqBuyTypeNm,operTypeNm,statusNm,openDt,tmntDt",
			widths : "80,80,80,80,100,100",
			colAlign : "center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,roXd,roXd",
			colSorting : "str,str,str,str,str,str",
			paging : false,
			buttons : {
				center : {
					btnClose : {label : "닫기"}
				}
			},
			onRowDblClicked : function(rowId, celInd) {
				// 선택버튼 누른것과 같이 처리
				this.callEvent('onButtonClick', ['btnClose']);
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnClose":
						mvno.ui.closeWindow();
						break;
				}
			}
		}
};

var PAGE = {
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		var ssn = getParams("ssn");
		if(ssn != ""){
			var params = {
					"cstmrNativeRrn" : ssn
				}
			PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getCheckCstmrList.json", params, {callback:function(){
				mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getCheckCstmr.json", params, function(resultData){
					var result = resultData.result.result[0];
					
					PAGE.FORM1.setItemValue("totCnt", result.totCnt);
					PAGE.FORM1.setItemValue("usgCnt", result.usgCnt);
					PAGE.FORM1.setItemValue("tmntCnt", result.tmntCnt);
				});
			}});
		}
		PAGE.FORM1.setItemFocus("totCnt");
	}
};

function getParams(key){
	var _parammap = {};
	document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
		function decode(s) {
			return decodeURIComponent(s.split("+").join(" "));
		}

		_parammap[decode(arguments[1])] = decode(arguments[2]);
	});

	return _parammap[key];
}

function fncDeCode(param)
{
	var sb = '';
	var pos = 0;
	var flg = true;

	if(param != null)
	{
		if(param.length>1)
		{
			while(flg)
			{
				var sLen = param.substring(pos,++pos);
				var nLen = 0;
				try
				{
					nLen = parseInt(sLen);
				}
				catch(e)
				{
					nLen = 0;
				}
				var code = '';
				if((pos+nLen)>param.length)
					code = param.substring(pos);
				else
					code = param.substring(pos,(pos+nLen));
				pos  += nLen;
				sb += String.fromCharCode(code);
				if(pos >= param.length)
					flg = false;
			}
		}
	}
	return sb;
}

$(document).ready(function() {
	$("body").keydown(function (event) {if(event.keyCode == 27) {mvno.ui.closeWindow();}});
});
</script>
