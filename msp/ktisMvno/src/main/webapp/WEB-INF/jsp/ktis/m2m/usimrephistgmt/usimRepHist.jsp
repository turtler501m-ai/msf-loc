<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blackOffset:0}
					, {type:"block", list:[
						{type:"input", label:"주문번호", name:"ordId", width:120, labelWidth:50, offsetLeft:0},
						{type:"newcolumn"},
						{type:"calendar", label:"주문월", name:"ordDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:70, offsetLeft:20 },
						{type:"newcolumn"},
						{type:"select", label:"USIM제품", name:"rprsPrdtId", width:120, labelWidth:60, offsetLeft:20},
						{type:"newcolumn"},
						{type:"select", label:"배송상태", name:"ordStatus", width:100, labelWidth:60, offsetLeft:20}
					  ]}
					, {type:"block", list:[
						{type:"calendar", label:"인수월", name:"takeDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:50 },
						{type:"newcolumn"},
						{type:"calendar", label:"인수희망월", name:"willTakeDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:70, offsetLeft:40  },
						{type:"newcolumn"},
						{type:"calendar", label:"발송월", name:"sendDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:60, offsetLeft:20  },
						{type:"newcolumn"},
						{type:"select", label:"배송지명", name:"areaNm", width:120, labelWidth:60, offsetLeft:40}
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
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							

							PAGE.GRID1.list(ROOT + "/m2m/usimRepHist/usimRepHistList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}});
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					
				},
				onValidateMore : function (data){

				}
			},
			
			GRID1 : {
				css : {
					height : "550px"
				},
				title : "USIM 주문 관리",
				header : "주문번호,보고서종류,출력일자,출력자,주문일자,출고일자,인수일자,배송상태,발주사,대표제품코드,제품명,단가(VAT별도),수량,총 금액(VAT별도),총 금액(VAT포함),배송지담당자,유선전화번호,핸드폰전화번호,배송지명,등록자",	//17
				columnIds : "ordId,reportCdNm,reportDatetime,reportUsrNm,ordDate,sendDate,takeDate,ordStatusNm,orgnNm,rprsPrdtId,prdtNm,outUnitPric,ordCnt,pric,pricVat,maskMngNm,mngPhn,mngMblphn,areaNmText,usrNm",
				widths : "100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
				colAlign : "center,center,center,center,center,center,center,center,center,center,center,right,right,right,right,center,center,center,center,center",
				colTypes : "ro,ro,ro,ro,roXd,roXd,roXd,ro,ro,ro,ro,ron,ron,ron,ron,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				hiddenColumns:[],
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				checkSelectedButtons : [],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					this.callEvent('onButtonClick', ['btnMod']);
				},
				onRowSelect : function (rowId, celInd) {
					
					
				},
				onButtonClick : function(btnId, selectedData) {
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
							mvno.cmn.download(ROOT + "/m2m/usimRepHist/usimRepHistListEx.json?menuId=M2M1000021", true, {postData:searchData});
							break;	

					}
					
				
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

				PAGE.FORM1.setItemValue("ordDate",today);

				//mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getOrgIdComboList.json", "", PAGE.FORM1, "orgnId"); // 발주사
				mvno.cmn.ajax4lov( ROOT + "/m2m/usimRepHist/getAreaNmComboList.json", "", PAGE.FORM1, "areaNm"); // 배송지명
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0001"}, PAGE.FORM1, "ordStatus");// 배송상태
//				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0002"}, PAGE.FORM1, "reportCd");// 보고서종류

				mvno.cmn.ajax4lov( ROOT + "/m2m/usimRepHist/getMdlIdComboList.json", "", PAGE.FORM1, "rprsPrdtId"); // 대표제품ID
			}
			
	};
	
	
	
	
	
</script>
