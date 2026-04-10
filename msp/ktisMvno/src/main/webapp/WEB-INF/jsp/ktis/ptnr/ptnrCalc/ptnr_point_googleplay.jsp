<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var curMnth = new Date().format("yyyymm");

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
				                        {type:"calendar", label:"정산월", name:"fromSearchYm", width:80, value:curMnth, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10}
				                        , {type:"newcolumn"}
				                        , {type:"calendar", label:"~", name:"toSearchYm", width:80, value:curMnth, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, labelWidth:15}
				                        , {type:"newcolumn"}
										, {type:"select", label:"구매유형", name:"svcType", width:100, offsetLeft:20}
				                        , {type:"newcolumn"}
										, {type:"select", label:"할인율", name:"dcCd", width:100, offsetLeft:20}
/*
				                        , {type:"input", label:"전화번호", name:"subscriberNo", width:100, offsetLeft:20}
*/										
										, {type:"newcolumn"}
										, {type:"select", label:"지급결과", name:"resultCd", width:100, offsetLeft:20}
					                	]}
					,{type:"block", list:[
						{type: "select",width : 80,offsetLeft : 10,label : "검색구분",name : "searchCd"}
						,{type: "newcolumn"}
						,{type: "input",width : 200,name : "searchVal"}
					]},

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han

							PAGE.GRID1.list(ROOT + "/ptnr/ptnrCalc/getPtnrCalcGooglePlayList.json", form, {callback : function() {
								mvno.cmn.ajax(ROOT + "/ptnr/ptnrCalc/getPtnrCalcGooglePlayListSum.json", form, function(resultData) {
									PAGE.FORM2.setItemValue("reqPointSum",resultData.result.data.rows[0].reqPointSum);
									PAGE.FORM2.setItemValue("calPointSum",resultData.result.data.rows[0].calPointSum);

									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
									
								});
							}});
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START

				},
				onValidateMore : function (data){
					if( data.fromSearchYm == "" || data.toSearchYm == "" ){
						this.pushError("fromSearchYm", "구매월", "검색할 값을 입력하세요");
						
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "530px"
				},
				title : "조회결과",
				header : "전화번호,고객명,계약번호,구매일,정산월,구매유형,구매유형명,할인율,지급포인트,지급결과,거래번호",	//6
				columnIds : "subscriberNo,cusNm,contractNum,regstDt,linkYm,svcTypeNm,svcNm,dcCdNm,reqPoint,resultCd,dealNum",
				widths : "90,90,80,80,70,80,120,50,70,70,140",
				colAlign : "center,left,center,center,center,left,left,center,right,left,center",
				colTypes : "ro,ro,ro,roXd,roXdm,ro,ro,ro,ron,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				onButtonClick : function(btnId) {
					switch (btnId) {
					case "btnDnExcel":
						
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
							return; //버튼 최초 클릭일때만 조회가능하도록
						}
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/ptnr/ptnrCalc/getPtnrCalcGooglePlayListEx.json?menuId=MSP6000033", true, {postData:searchData});
						break;	


					}
				}
				
			},
			
			FORM2 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"center", labelWidth:150, blackOffset:0}
					, {type:"block", list:[
										{type:"input", label:"지급포인트 합계", name:"reqPointSum", width:200, offsetLeft:50, numberFormat:"0,000,000,000", validate:"ValidInteger"}
										, {type:"newcolumn"}
										, {type:"input", label:"정산금액 합계", name:"calPointSum", width:200, offsetLeft:30, numberFormat:"0,000,000,000", validate:"ValidInteger"}
					                	]}
				],
			},
						
			
	};

	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createForm("FORM2");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0200",etc1:"googleplay"}, PAGE.FORM1, "resultCd"); // 검색구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP00015"}, PAGE.FORM1, "svcType"); // 구매유형
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP00016"}, PAGE.FORM1, "dcCd");    // 할인율
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd"); // 검색구분
				
				
			}
			
	};
    
</script>
