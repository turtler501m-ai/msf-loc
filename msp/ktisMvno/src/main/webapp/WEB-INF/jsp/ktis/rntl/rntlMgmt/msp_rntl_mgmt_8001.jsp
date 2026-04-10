<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_rntl_mgmt_8001.jsp
 * @Description : 매입기준관리
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.04.21  	강무성		  최초생성
 * @ 
 * @author : 강무성
 * @Create Date : 2017.04.21
 */
%>

<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="GRID2" class="section-full"></div>
<div id="FORM2" class="section-box"></div>

<script type="text/javascript">
	var dt = new Date();
	var curtMnth = new Date(dt.getFullYear(), dt.getMonth(), 1).format("yyyymm");
	
	var DHX = {
			FORM1 : {
				items : [
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							, {type:"block", list:[
								{type:"calendar", label:"적용월", name:"baseDt", value:curtMnth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true}
								, {type: 'newcolumn'}
								, {type:"select", width:150, offsetLeft:15, labelWidth:50, label:"대리점",name:"orgnId"}
								, {type: 'newcolumn'}
								, {type:"select", width:150, offsetLeft:15, labelWidth:70, label:"단말모델명",name:"prdtId"}
							]}
							//버튼 여러번 클릭 막기 변수
							, {type : 'hidden', name: "btnCount1", value:"0"} 
							, {type : 'hidden', name: "btnExcelCount1", value:"0"}
							// 조회버튼
							, {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
						],
						onXLE : function() {},
						onButtonClick : function(btnId) {
							var form = this;
							
							switch (btnId) {
								case "btnSearch":
									
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
										return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
									}
									
									PAGE.GRID2.clearAll();
									
									PAGE.GRID1.list(ROOT + "/rntl/rntlMgmt/getPurchStandList.json", form, {
										callback : function () {
											PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
											PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										}
									});
									
									break;
							}
						}
			},
			
			GRID1 : {
				css : {
						height : "350px"
				},
				title : "조회결과",
				header : "대리점,적용월,단말모델코드,물류처리비,매입가,월별렌탈비,검수비,최저매입보장가,등록자,등록일시,수정자,수정일시",
				columnIds : "agncyNm,strtYm,prdtCode,deplrictCost,buyAmnt,rentalCost,virfyCost,grntAmnt,regId,regDttm,rvisnId,rvisnDttm",
				colAlign : "center,center,left,right,right,right,right,right,center,center,center,center",
				colTypes : "ro,roXdm,ro,roXns,roXns,roXns,roXns,roXns,ro,roXdt,ro,roXdt",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
				widths : "120,80,120,80,80,80,80,110,100,140,100,140",
				paging: true,
				pageSize:20,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				onRowSelect:function(rId, cId){
					
					var grid = this;
					
					var sdata = grid.getSelectedRowData();
					var data = {
							baseDt : sdata.strtYm,
							orgnId : sdata.orgnId,
							prdtId : sdata.prdtId
					}
					
					PAGE.GRID2.list(ROOT + "/rntl/rntlMgmt/getPurchStandDtlList.json", data);
				},
				onButtonClick : function(btnId, selectedData) {
					var form = this;
					switch (btnId) {
						case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
							
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
							mvno.cmn.download(ROOT + "/rntl/rntlMgmt/getPurchStandListExcel.json?menuId=MSP8000001",true,{postData:searchData});
							
							break;
					}//switch End
				}// onButtonClick
			}, //End GRID1
			
			GRID2 : {
				css : {
						height : "150px"
				},
				title : "상세조회결과",
				header : "대리점,적용월,종료월,단말모델코드,물류처리비,매입가,월별렌탈비,검수비,최저매입보장가,등록자,등록일시,수정자,수정일시,조직ID,단말모델ID",
				columnIds : "agncyNm,strtYm,endYm,prdtCode,deplrictCost,buyAmnt,rentalCost,virfyCost,grntAmnt,regId,regDttm,rvisnId,rvisnDttm,orgnId,prdtId",
				colAlign : "center,center,center,left,right,right,right,right,right,center,center,center,center,center,center",
				colTypes : "ro,roXdm,roXdm,ro,roXns,roXns,roXns,roXns,roXns,ro,roXdt,ro,roXdt,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				widths : "120,80,80,120,80,80,80,80,110,100,140,100,140,80,80",
				hiddenColumns: [13,14],
				paging: false,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnUpt : { label : "수정" }
					}
				},
				onButtonClick : function(btnId, selectedData) {
					var form = this;
					switch (btnId) {
						case 'btnReg' :
							
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();
							
							PAGE.FORM2.setItemValue("baseDt", curtMnth);
							
							mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0228"}, PAGE.FORM2, "orgnId");
							
							mvno.cmn.ajax4lov(ROOT+"/rcp/rcpMgmt/getRcpCommon.json", {grpId:"RENTAL", reqBuyType:"MM"}, PAGE.FORM2, "prdtId");
							
							PAGE.FORM2.setItemValue("trtmTypeCd", "REG");
							
							PAGE.FORM2.enableItem("orgnId");
							PAGE.FORM2.enableItem("baseDt");
							PAGE.FORM2.enableItem("prdtId");
							
							mvno.ui.showButton("FORM2" , 'btnInit');
							
							PAGE.FORM2.clearChanged();
							
							mvno.ui.popupWindowById("FORM2", "매입기준등록", 610, 270, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
							
							break;
							
						case 'btnUpt' :
							
							if(PAGE.GRID1.getSelectedRowData() == null) {
								mvno.ui.alert("수정 대상을 선택해 주세요.");
								return;
							}
							
							var rowData = PAGE.GRID1.getSelectedRowData();
							
							if(rowData.strtYm < curtMnth) {
								mvno.ui.alert("과거월은 수정 할 수 없습니다.");
								return;
							}
							
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();
							
							mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0228"}, PAGE.FORM2, "orgnId");
							
							mvno.cmn.ajax4lov(ROOT+"/rcp/rcpMgmt/getRcpCommon.json", {grpId:"RENTAL", reqBuyType:"MM"}, PAGE.FORM2, "prdtId");
							
							PAGE.FORM2.setItemValue("orgnId", rowData.orgnId);
							PAGE.FORM2.setItemValue("baseDt", rowData.strtYm);
							PAGE.FORM2.setItemValue("prdtId", rowData.prdtId);
							PAGE.FORM2.setItemValue("deplrictCost", rowData.deplrictCost);
							PAGE.FORM2.setItemValue("buyAmnt", rowData.buyAmnt);
							PAGE.FORM2.setItemValue("rentalCost", rowData.rentalCost);
							PAGE.FORM2.setItemValue("virfyCost", rowData.virfyCost);
							PAGE.FORM2.setItemValue("grntAmnt", rowData.grntAmnt);
							
							PAGE.FORM2.setItemValue("trtmTypeCd", "UPT");
							
							PAGE.FORM2.disableItem("orgnId");
							PAGE.FORM2.disableItem("baseDt");
							PAGE.FORM2.disableItem("prdtId");
							
							mvno.ui.hideButton("FORM2" , 'btnInit');
							
							PAGE.FORM2.clearChanged();
							
							mvno.ui.popupWindowById("FORM2", "매입기준수정", 610, 270, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
							
							break;
					}//switch End
				}// onButtonClick
			} //End GRID2
			
			, FORM2 : {
				items : [
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							, {type:"block", list:[
								{type:"select", width:150, offsetLeft:10, labelWidth:80, label:"대리점",name:"orgnId", required:true}
								, {type: 'newcolumn'}
								, {type:"calendar", label:"적용월", name:"baseDt", width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:30, labelWidth:100, required:true}
							]}
							, {type:"block", list:[
								{type:"select", width:150, offsetLeft:10, labelWidth:80, label:"단말모델명",name:"prdtId", required:true}
								, {type: 'newcolumn'}
								, {type:"input", width:150, offsetLeft:30, labelWidth:100, label:"물류처리비",name:"deplrictCost", validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
							]}
							, {type:"block", list:[
								{type:"input", width:150, offsetLeft:10, labelWidth:80, label:"매입가",name:"buyAmnt", validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
								, {type: 'newcolumn'}
								, {type:"input", width:150, offsetLeft:30, labelWidth:100, label:"월렌탈비",name:"rentalCost", validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
							]}
							, {type:"block", list:[
								{type:"input", width:150, offsetLeft:10, labelWidth:80, label:"검수비",name:"virfyCost", validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
								, {type: 'newcolumn'}
								, {type:"input", width:150, offsetLeft:30, labelWidth:100, label:"최저매입보장가",name:"grntAmnt", validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
							]}
							, {type : 'hidden', name: "trtmTypeCd"} 
						]
						, buttons : {
							left : {
								btnInit : { label : "초기화" }
							}
							, right : {
								btnReg : { label : "저장" }
								, btnClose : { label : "닫기" }
							}
						}
						, onButtonClick : function(btnId) {
							var form = this;
							var bUptYN = form.getItemValue("trtmTypeCd") == "UPT"  ? true : false;
							
							switch (btnId) {
								case "btnInit":
									
									form.clear();
									
									form.setItemValue("baseDt", curtMnth);
									
									break;
									
								case "btnReg":
									
									var baseDtFM = new Date(form.getItemValue("baseDt")).format("yyyymm");
									
									if(baseDtFM < curtMnth){
										mvno.ui.alert("과거월은 입력 할 수 없습니다.");
										return;
									}
									
									mvno.cmn.ajax(ROOT + "/rntl/rntlMgmt/savPurchStandByInfo.json", form, function(){
										mvno.ui.notify("NCMN0004");
										
										if(bUptYN){
											mvno.ui.closeWindowById("FORM2");
											
											PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
										}else{
											form.clear();
											
											form.setItemValue("baseDt", curtMnth);
											
											form.clearChanged();
										}
									});
									
									break;
									
								case "btnClose":
									mvno.ui.closeWindowById("FORM2");
									
									PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
									//PAGE.GRID1.refresh({ selectRow : true });
									
									break;
							}
						}
			} //FORM2 End
		}; // end dhx
		
		var PAGE = {
				 title : "${breadCrumb.title}",
				 breadcrumb : "${breadCrumb.breadCrumb}",  
				 buttonAuth: ${buttonAuth.jsonString},
				 onOpen:function() {
					mvno.ui.createForm("FORM1");
					mvno.ui.createGrid("GRID1");
					mvno.ui.createGrid("GRID2");
					
					mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0228"}, PAGE.FORM1, "orgnId");
					
					mvno.cmn.ajax4lov(ROOT+"/rcp/rcpMgmt/getRcpCommon.json", {grpId:"RENTAL", reqBuyType:"MM"}, PAGE.FORM1, "prdtId");
				}
			};
		
</script>