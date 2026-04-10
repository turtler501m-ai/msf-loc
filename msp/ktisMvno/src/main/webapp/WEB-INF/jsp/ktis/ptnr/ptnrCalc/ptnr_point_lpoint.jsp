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
										{type:"calendar", label:"연동년월", name:"searchYm", width:80, value:curMnth, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10}
										, {type:"newcolumn"}
										, {type:"input", label:"고객명", name:"custNm", width:100, offsetLeft:20}
										, {type:"newcolumn"}
										, {type:"select", label:"지급결과", name:"resultCd", width:100, offsetLeft:20}
					                	]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/ptnr/ptnrCalc/getPtnrCalcLpointList.json", form, {callback : function() {
								mvno.cmn.ajax(ROOT + "/ptnr/ptnrCalc/getPtnrCalcLpointListSum.json", form, function(resultData) {
									PAGE.FORM2.setItemValue("reqPointSum",resultData.result.data.rows[0].reqPointSum);
								});
							}});

							PAGE.GRID2.list(ROOT + "/ptnr/ptnrCalc/getPtnrCalcLpointSttlList.json", form);
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					if(name == "searchYm") {
						PAGE.FORM1.setItemValue("searchYm", curMnth);
					}

				},
				onValidateMore : function (data){
					if( (data.searchYm == "" || data.searchYm.trim() == "") && (data.subscriberNo == "" || data.subscriberNo.trim() == "") ){
						this.pushError("searchYm", "연동년월, 고객명", "검색할 값을 입력하세요");
						
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "400px"
				},
				title : "조회결과",
				header : "연동년월,사용년월,고객명,지급포인트,지급결과,지급결과내용",	//6
				columnIds : "linkYm,billYm,custNm,reqPoint,resultCd,resMsg",
				widths : "150,150,200,120,150,170",
				colAlign : "center,center,left,right,center,left",
				colTypes : "roXdm,roXdm,ro,ron,ro,ro",
				colSorting : "str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;
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
							mvno.cmn.download(ROOT + "/ptnr/ptnrCalc/getPtnrCalcLpointListEx.json?menuId=MSP6000028", true, {postData:searchData});
							break;
					}
				}
				
			},
			
			FORM2 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"center", labelWidth:150, blackOffset:0}
					, {type:"block", list:[
										{type:"input", label:"지급포인트 합계", name:"reqPointSum", width:200, offsetLeft:50, numberFormat:"0,000,000,000", validate:"ValidInteger"}
					                	]}
				],
			},
						

			GRID2 : {
				css : {
					height : "80px"
				},
				title : "정산내역",
				header : "지급포인트,적립수수료,부가세,소계,정산금액",	//5
				columnIds : "totReqPoint,acmlCmsn,acmlCmsnVat,totCmsn,sttlAmnt",
				widths : "190,190,190,190,190",
				colAlign : "right,right,right,right,right",
				colTypes : "ron,ron,ron,ron,ron",
				colSorting : "str,str,str,str,str",
				paging : true,
				pageSize:20,
				onButtonClick : function(btnId) {
					
				}
				
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
				mvno.ui.createGrid("GRID2");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0200",etc1:"gifti"}, PAGE.FORM1, "resultCd"); // 검색구분
			}
			
	};
    
</script>
