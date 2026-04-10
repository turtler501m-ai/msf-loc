<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : statsRealTimeMgmt.jsp
	 * @Description : 실시간 개통현황 조회
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2016.04.06  최초생성
	 * @ 
	 * @author : 한상욱
	 * @Create Date : 2016.04.06
	 */
%>
<div id="FORM0" class="section-search" ></div>
<div id="GROUP1">
	<div class="row">
		<div id="FORM1" class="c5 section-box"></div>
		<div id="FORM2" class="c5 section-box"></div>
	</div>
</div>
<div id="GROUP2">
	<div class="row">
		<div id="GRID1" class="c5"></div>
		<div id="GRID2" class="c5"></div>
	</div>
</div>
<div id="GROUP3">
	<div class="row">
		<div id="FORM3" class="c5 section-box"></div>
		<div id="FORM4" class="c5 section-box"></div>
	</div>
</div>
<div id="GROUP4">
	<div class="row">
		<div id="GRID3" class="c5"></div>
		<div id="GRID4" class="c5"></div>
	</div>
</div>
<div id="GROUP5">
	<div class="row">
		<div id="GRID5" class="c5"></div>
		<div id="GRID6" class="c5"></div>
	</div>
</div>
<div id="GROUP6" >
	<div class="row">
		<div id="GRID7" class="c5" style="margin-top: -165px" ></div>
	</div>
</div>
<div id="GROUP7">
	<div class="row">
		<div id="GRID8" class="c5"></div>
		<div id="GRID9" class="c5" style="margin-top: -85px" ></div>
	</div>
</div>

<!-- <p style="margin-top: 20px;" align="right"> -->
<!-- 	자동 새로 고침...<span id="wTimer" style="color: black; font-family: 돋움; font-weight:bold;">(0초)</span> -->
<!-- </p> -->
	
<script type="text/javascript">
	var oTimer;
	var timerVal = <%=request.getAttribute("timer")%>;

	var DHX = {
		FORM0 : {
			/* css : { height : "30px"}, */
			title : "",
			items : [	
			         	/* {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
						{type:"block", blockOffset:0, list: [
							{type : "text", name : "timeLabel", label : "ttt", value : "TEST"}
			         	]}, */
			         	{type : "input", label:"업데이트 시간", name: "refreshTime", userdata: {align: "center"}, width:130},
						{type : "button", name : "btnRefesh", value : "새로고침", className : "btn-search1"}
			        ],
			onButtonClick : function(btnId) {
		
				var form = this;
		
				switch (btnId) {
				
					case "btnRefesh" : 
						getStats();
					break;
		
				}
		
			}
		},
		
		FORM1 : {
			title : "선불개통건수",
			items : [
					  {type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0}
					, {type:"block", blockOffset:0, list: [
						 {type:"input", label:"전일개통", name: "ppDay", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:5, labelWidth: 48, disable:true}
						,{type:"newcolumn"}
						,{type:"input", label:"당월개통", name: "ppMonth", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"총누적", name: "ppTotal", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 40, readOnly:true}
					  ]}
					]
		},
		
		FORM2 : {
			title : "선불가입건수",
			items : [
					  {type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0}
					, {type:"block", blockOffset:0, list: [
						 {type:"input", label:"일반유심", name: "ppJoin1", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:5, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"단기유심", name: "ppJoin2", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"소계", name: "ppJoinTotal", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 40, readOnly:true}
					  ]}
					]
		},
		
		FORM3 : {
			title : "후불개통건수",
			items : [
					  {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
					, {type:"block", blockOffset:0, list: [
						 {type:"input", label:"전일개통", name: "poDay", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:5, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"당월개통", name: "poMonth", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"총누적", name: "poTotal", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 40, readOnly:true}
					  ]}
					]
		},
		
		FORM4 : {
			title : "후불가입건수",
			items : [
					  {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
					, {type:"block", blockOffset:0, list: [
						 {type:"input", label:"유심단독", name: "poJoin1", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:5, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"단말결합", name: "poJoin2", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 48, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"소계", name: "poJoinTotal", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 40, readOnly:true}
					  ]}
			]
		},
				
		GRID1 : {
			css : { height : "112px"},
			title : "선불3G가입현황",
			header : "가입경로,구매유형,통신유형,가입건수,해지건수", 
			columnIds : "onOffType,reqBuyType,dataType,nac,can",
			colAlign : "center,center,center,center,center",
			colTypes : "ro,ro,ro,ron,ron",
			widths : "100,110,80,75,75",
			colSorting : "str,str,str,str,str"
		},
		
		GRID2 : {
			css : { height : "112px"},
			title : "선불LTE가입현황",
			header : "가입경로,구매유형,통신유형,가입건수,해지건수", 
			columnIds : "onOffType2,reqBuyType2,dataType2,nac2,can2",
			colAlign : "center,center,center,center,center",
			colTypes : "ro,ro,ro,ron,ron",
			widths : "100,110,80,75,75",
			colSorting : "str,str,str,str,str"
		},
				
		GRID3 : {
			css : { height : "274px"},
			title : "후불신규가입현황",
			header : "가입경로,구매유형,통신유형,가입건수,해지건수", 
			columnIds : "onOffType,reqBuyType,dataType,nac,can",
			colAlign : "center,center,center,center,center",
			colTypes : "ro,ro,ro,ron,ron",
			widths : "100,110,80,75,75",
			colSorting : "str,str,str,str,str"
		},
		
		GRID4 : {
			css : { height : "274px"},
			title : "후불번호이동현황",
			header : "가입경로,구매유형,통신유형,가입건수,해지건수", 
			columnIds : "onOffType2,reqBuyType2,dataType2,nac2,can2",
			colAlign : "center,center,center,center,center",
			colTypes : "ro,ro,ro,ron,ron",
			widths : "100,110,80,75,75",
			colSorting : "str,str,str,str,str"
		},
				
		GRID5 : {
			css : { height : "156px"},
			title : "후불총실적현황",
			header : "구분,건수", 
			columnIds : "onOffType,reqBuyType",
			colAlign : "center,center",
			colTypes : "ro,ron",
			widths : "224,224",
			colSorting : "str,str"
		},
		
		GRID6 : {
			css : { height : "318px"},
			title : "후불채널별 실적현황",
			header : "채널,건수",
			columnIds : "onOffType,reqBuyType",
			colAlign : "center,center",
			colTypes : "ro,ron",
			widths : "224,224",
			colSorting : "str,str"
		},
		
		GRID7 : {
			css : { height : "210px"},
			title : "후불조직별 실적현황",
			header : "구분,건수", 
			columnIds : "onOffType,reqBuyType",
			colAlign : "center,center",
			colTypes : "ro,ron",
			widths : "224,224",
			colSorting : "str,str"
		},
		
		GRID8 : {
			css : { height : "302px"},
			title : "요금제별 가입현황",
			header : "통신유형,요금제명,건수", 
			columnIds : "dataType,rateNm,rateCnt",
			colAlign : "center,center,center",
			colTypes : "ro,ro,ron",
			widths : "108,224,99",
			colSorting : "str,str,str"
		},
		
		GRID9 : {
			css : { height : "387px"},
			title : "대리점별 가입현황",
			header : "대리점명,건수", 
			columnIds : "agntNm,agntCnt",
			colAlign : "center,center",
			colTypes : "ro,ron",
			widths : "224,207",
			colSorting : "str,str"
		}
	};
		
	var PAGE = {
			
		title: "${breadCrumb.title}",
		breadcrumb: "${breadCrumb.breadCrumb}",
		onOpen : function() {
			mvno.ui.createForm("FORM0");
			mvno.ui.createForm("FORM1");
			mvno.ui.createForm("FORM2");
			mvno.ui.createForm("FORM3");
			mvno.ui.createForm("FORM4");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			mvno.ui.createGrid("GRID3");
			mvno.ui.createGrid("GRID4");
			mvno.ui.createGrid("GRID5");
			mvno.ui.createGrid("GRID6");
			mvno.ui.createGrid("GRID7");
			mvno.ui.createGrid("GRID8");
			mvno.ui.createGrid("GRID9");
			
			getStats();
			//refreshTimer();
			
		}
	};
	
	function getStats(){
		mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getStatsRealTime.json", PAGE.FORM1, function(resultData) {
			PAGE.GRID1.clearAll();
			PAGE.GRID2.clearAll();
			PAGE.GRID3.clearAll();
			PAGE.GRID4.clearAll();
			PAGE.GRID5.clearAll();
			PAGE.GRID6.clearAll();
			PAGE.GRID7.clearAll();
			
			PAGE.GRID1.parse(resultData.result.data.rows, "js");
			PAGE.GRID2.parse(resultData.result.data.rows, "js");
			PAGE.GRID3.parse(resultData.result.data.rows, "js");
			PAGE.GRID4.parse(resultData.result.data.rows, "js");
			PAGE.GRID5.parse(resultData.result.data.rows, "js");
			PAGE.GRID6.parse(resultData.result.data.rows, "js");
			PAGE.GRID7.parse(resultData.result.data.rows, "js");
			
			PAGE.FORM0.setItemValue("refreshTime",	resultData.result.data.rows[29].nac);
			PAGE.FORM0.disableItem("refreshTime");
			
			PAGE.FORM1.setItemValue("ppDay",		resultData.result.data.rows[9].onOffType);
			PAGE.FORM1.setItemValue("ppMonth",		resultData.result.data.rows[9].dataType);
			PAGE.FORM1.setItemValue("ppTotal",		resultData.result.data.rows[9].can);
			PAGE.FORM1.disableItem("ppDay");
			PAGE.FORM1.disableItem("ppMonth");
			PAGE.FORM1.disableItem("ppTotal");
			
			PAGE.FORM2.setItemValue("ppJoin1",		resultData.result.data.rows[10].can);
			PAGE.FORM2.setItemValue("ppJoin2",		resultData.result.data.rows[10].onOffType2);
			PAGE.FORM2.setItemValue("ppJoinTotal",	Number(resultData.result.data.rows[10].can) + Number(resultData.result.data.rows[10].onOffType2));
			PAGE.FORM2.disableItem("ppJoin1");
			PAGE.FORM2.disableItem("ppJoin2");
			PAGE.FORM2.disableItem("ppJoinTotal");
			
			PAGE.FORM3.setItemValue("poDay",		resultData.result.data.rows[8].dataType);
			PAGE.FORM3.setItemValue("poMonth",		resultData.result.data.rows[8].can);
			PAGE.FORM3.setItemValue("poTotal",		resultData.result.data.rows[8].onOffType2);
			PAGE.FORM3.disableItem("poDay");
			PAGE.FORM3.disableItem("poMonth");
			PAGE.FORM3.disableItem("poTotal");
			
			PAGE.FORM4.setItemValue("poJoin1",		resultData.result.data.rows[10].onOffType);
			PAGE.FORM4.setItemValue("poJoin2",		resultData.result.data.rows[10].dataType);
			PAGE.FORM4.setItemValue("poJoinTotal",	Number(resultData.result.data.rows[10].onOffType) + Number(resultData.result.data.rows[10].dataType));
			PAGE.FORM4.disableItem("poJoin1");
			PAGE.FORM4.disableItem("poJoin2");
			PAGE.FORM4.disableItem("poJoinTotal");
						
			//GRID1, GRID2 SETTING
			for (i=1;i<12;i++){
				PAGE.GRID1.deleteRow(i);
				PAGE.GRID2.deleteRow(i);
			}
			for (i=14;i<=resultData.result.data.rows.length;i++){
				PAGE.GRID1.deleteRow(i);
				PAGE.GRID2.deleteRow(i);
			}
			//GRID3, GRID4 SETTING
			for (i=9;i<=resultData.result.data.rows.length;i++){
				PAGE.GRID3.deleteRow(i);
				PAGE.GRID4.deleteRow(i);
			}
			//GRID5 SETTING
			for (i=1;i<14;i++){
				PAGE.GRID5.deleteRow(i);
			}
			for (i=17;i<=resultData.result.data.rows.length;i++){
				PAGE.GRID5.deleteRow(i);
			}
			//GRID6 SETTING
			for (i=1;i<17;i++){
				PAGE.GRID6.deleteRow(i);
			}
			for (i=26;i<=resultData.result.data.rows.length;i++){
				PAGE.GRID6.deleteRow(i);
			}
			//GRID7 SETTING
			for (i=1;i<26;i++){
				PAGE.GRID7.deleteRow(i);
			}
			for (i=31;i<=resultData.result.data.rows.length;i++){
				PAGE.GRID7.deleteRow(i);
			}
			
			var joinSum1 = 0;
			var joinSum2 = 0;
			var joinSum3 = 0;
			var joinSum4 = 0;
			var joinSum5 = 0;
			var joinSum6 = 0;
			var joinSum7 = 0;
			var outSum1 = 0;
			var outSum2 = 0;
			var outSum3 = 0;
			var outSum4 = 0;
			
			//GRID1, GRID2 합계
			for (i=11;i<=12;i++){
			    joinSum1 += Number(resultData.result.data.rows[i].nac);
				joinSum2 += Number(resultData.result.data.rows[i].nac2);
				outSum1 += Number(resultData.result.data.rows[i].can);
				outSum2 += Number(resultData.result.data.rows[i].can2);
			}
			//GRID3, GRID4 합계
			for (i=0;i<8;i++){
				joinSum3 += Number(resultData.result.data.rows[i].nac);
				joinSum4 += Number(resultData.result.data.rows[i].nac2);
				outSum3 += Number(resultData.result.data.rows[i].can);
				outSum4 += Number(resultData.result.data.rows[i].can2);
			}
			//GRID5 합계
			for (i=13;i<=15;i++){
				joinSum5 += Number(resultData.result.data.rows[i].reqBuyType);
			}
			//GRID6 합계
			for (i=16;i<=24;i++){
				joinSum6 += Number(resultData.result.data.rows[i].reqBuyType);
			}
			//GRID7 합계
			for (i=25;i<=29;i++){
				joinSum7 += Number(resultData.result.data.rows[i].reqBuyType);
			}
			
			PAGE.GRID1.addRow(200,["합계","","",joinSum1,outSum1],3);
			PAGE.GRID2.addRow(200,["합계","","",joinSum2,outSum2],3);
			PAGE.GRID3.addRow(200,["합계","","",joinSum3,outSum3],9);
			PAGE.GRID4.addRow(200,["합계","","",joinSum4,outSum4],9);
			PAGE.GRID5.addRow(200,["합계",joinSum5],4);
			PAGE.GRID6.addRow(200,["합계",joinSum6],10);
			PAGE.GRID7.addRow(200,["합계",joinSum7],6);
			
			PAGE.GRID1.enableColSpan(true);
			PAGE.GRID2.enableColSpan(true);
			PAGE.GRID3.enableColSpan(true);
			PAGE.GRID4.enableColSpan(true);
			PAGE.GRID1.setColspan(200,0,3);
			PAGE.GRID2.setColspan(200,0,3);
			PAGE.GRID3.setColspan(200,0,3);
			PAGE.GRID4.setColspan(200,0,3);
			
			PAGE.GRID1.cells(200,0).setTextColor('red');
			PAGE.GRID1.cells(200,3).setTextColor('red');
			PAGE.GRID1.cells(200,4).setTextColor('red');
			
			PAGE.GRID2.cells(200,0).setTextColor('red');
			PAGE.GRID2.cells(200,3).setTextColor('red');
			PAGE.GRID2.cells(200,4).setTextColor('red');
			
			PAGE.GRID3.cells(200,0).setTextColor('red');
			PAGE.GRID3.cells(200,3).setTextColor('red');
			PAGE.GRID3.cells(200,4).setTextColor('red');
			
			PAGE.GRID4.cells(200,0).setTextColor('red');
			PAGE.GRID4.cells(200,3).setTextColor('red');
			PAGE.GRID4.cells(200,4).setTextColor('red');
			
			PAGE.GRID5.cells(200,0).setTextColor('red');
			PAGE.GRID5.cells(200,1).setTextColor('red');
			
			PAGE.GRID6.cells(200,0).setTextColor('red');
			PAGE.GRID6.cells(200,1).setTextColor('red');
			
			PAGE.GRID7.cells(200,0).setTextColor('red');
			PAGE.GRID7.cells(200,1).setTextColor('red');
		});
		
		mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getRateStats.json", PAGE.FORM1, function(resultData) {
			PAGE.GRID8.clearAll();
			
			PAGE.GRID8.parse(resultData.result.data.rows, "js");
			
			var joinSum8 = 0;
			
			//GRID8 합계
			for (i=0;i<resultData.result.data.rows.length;i++){
				joinSum8 += Number(resultData.result.data.rows[i].rateCnt);
			}
			
			PAGE.GRID8.addRow(200,["합계","",joinSum8],(resultData.result.data.rows.length+1));
			
			
			PAGE.GRID8.enableColSpan(true);
			PAGE.GRID8.setColspan(200,0,2);
			PAGE.GRID8.cells(200,0).setTextColor('red');
			PAGE.GRID8.cells(200,2).setTextColor('red');
		});
		
		mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getAgntStats.json", PAGE.FORM1, function(resultData) {
			PAGE.GRID9.clearAll();
			
			PAGE.GRID9.parse(resultData.result.data.rows, "js");
			
			var joinSum9 = 0;
			
			//GRID9 합계
			for (i=0;i<resultData.result.data.rows.length;i++){
				joinSum9 += Number(resultData.result.data.rows[i].agntCnt);
			}
			
			PAGE.GRID9.addRow(200,["합계",joinSum9],(resultData.result.data.rows.length+1));
			
			
			PAGE.GRID9.enableColSpan(true);
			PAGE.GRID9.cells(200,0).setTextColor('red');
			PAGE.GRID9.cells(200,1).setTextColor('red');
		});
	}
	
	function refreshTimer() {
        clearInterval(oTimer);

        var waitMax = timerVal;

        oTimer = setInterval(function() {
            if(waitMax == 0) {
                clearInterval(oTimer);
                getStats();
                refreshTimer();
            }

            $('#wTimer').html('('+waitMax+'초)');
            waitMax --;
        }, 1000);
    }
</script>