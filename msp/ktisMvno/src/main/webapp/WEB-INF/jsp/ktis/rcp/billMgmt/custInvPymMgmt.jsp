<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   /**
    * @Class Name : custInvPymMgmt.jsp
    * @Description : 고객청구수납내역조회
    * @
    * @ 수정일     수정자 수정내용
    * @ ---------- ------ -----------------------------
    * @ 2017.12.14 이상직 최초
    * @Create Date : 2017.12.14
    */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
var curMnth = new Date().format("yyyymm");

var DHX = {
	//------------------------------------------------------------
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
			{type: "block",
				list : [
					{type:"select", width:200, label:"법인고객", name:"customerId", required:true},
					{type:"newcolumn"},
					{type:"calendar", width:80, label:"청구월", dateFormat:"%Y-%m", serverDateFormat:"%Y%m", name:"billYm", value:curMnth, offsetLeft:20, required:true},
					{type:"newcolumn"},
					{type:"input", width:120, label:"전화번호", name:"subscriberNo", offsetLeft:20}
				]
			},
			{type: "newcolumn"},
			{type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
		],
		onValidateMore : function (data){
		},
		onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/rcp/billMgmt/getCustInvPymMgmtList.json", form);
					break;
			}
		}
	},
	//-------------------------------------------------------------------------------------
	// 청구내역
	GRID1 : {
		css : {
			height : "560px"
		},
		title : "청구내역조회",
		header : "청구번호,서비스계약번호,서비스번호,고객명,실사용자,월정액,국내음성통화료,국제로밍이용료,데이터통화료,Moblie국제통화료,kt국제통화료,국제통화료,국제SMS이용료,기타청구금액,할인전부가세,할인금액,할인금액부가세,단말할부금,당월요금계,총청구금액,공급가액,부가세,전월청구금액,전월수납금액,요금제",
		columnIds : "ban,svcCntrNo,subscriberNo,customerLinkName,subLinkName,mavoiAmnt,rcp8002Amnt,rcp8003Amnt,rcp8004Amnt,rcp8005Amnt,rcp8006Amnt,rcp8007Amnt,rcp8008Amnt,etcAmnt,vatAmnt,discountAmnt,discountVat,eqistAmnt,invAmnt,totInvAmnt,supplyAmnt,supplyVat,lastInvAmnt,lastPymAmnt,lstRateNm",
		colAlign : "center,center,center,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,left",//8
		colTypes : "ro,ro,roXp,ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro",
		colSorting : "str,str,str,str,str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str",
		widths : "100,100,100,120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,120",
		paging: true,
		pageSize:20,
		buttons : {
			hright : {
				btnExcel : { label : "엑셀다운로드" },
			}
		},
		onButtonClick : function(btnId, selectedData) {
			var form = this;
			switch (btnId) {
				case "btnExcel":
					if(PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
					} else {
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download('/rcp/billMgmt/getCustInvPymListExcel.json'+"?menuId=MSP1000050", true, {postData:searchData});
						break; 
					}
			}
		}
	},
};

var PAGE = {
	title: "${breadCrumb.title}",
	breadcrumb: "${breadCrumb.breadCrumb}",
	
	buttonAuth: ${buttonAuth.jsonString},
	
	onOpen : function() {
		
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
// 		var date = new Date();
// 		var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
// 		var lastMonth = new Date(firstDay.setDate(firstDay.getDate() - 1));
		
// 		PAGE.FORM1.setItemValue("billYm", new String(lastMonth.getFullYear()) + new String(fnMonth(lastMonth.getMonth())));
		
		// 법인고객
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP8001"}, PAGE.FORM1, "customerId");
	}
};


function fnMonth(mon) {
	var month;
	mon = Number(mon);
	mon++;
	
	if(Number(mon) < 10) {
		month = "0"+mon;
	} else {
		month = mon;
	}
	
	return month;
}

</script>
