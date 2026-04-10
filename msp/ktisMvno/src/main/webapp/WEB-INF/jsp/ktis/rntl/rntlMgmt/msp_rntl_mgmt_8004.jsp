<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_rntl_mgmt_8004.jsp
 * @Description : 월정산내역
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.05.08  	강무성		  최초생성
 * @ 
 * @author : 강무성
 * @Create Date : 2017.05.08
 */
%>

<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="GRID2" class="section-full"></div>

<script type="text/javascript">
	var dt = new Date();
	var curtMnth = new Date(dt.getFullYear(), dt.getMonth(), 1).format("yyyymm");
	
	var DHX = {
			FORM1 : {
				items : [
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}
							, {type:"block", list:[
								{type:"calendar", label:"정산월", name:"baseDt", value:curtMnth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true}
								, {type: 'newcolumn'}
								, {type:"select", width:150, offsetLeft:15, labelWidth:40, label:"대리점",name:"orgnId"}
								, {type: 'newcolumn'}
// 								, {type:"select", width:100, offsetLeft:15, label:"검색구분",name:"searchGbn", userdata:{lov:"RNTL003"}, offsetLeft:10}
								, {type:"select", width:100, offsetLeft:15, label:"검색구분",name:"searchGbn", offsetLeft:10}
								, {type:"newcolumn"}
								, {type:"input", width:115, name:"searchName"}
								, {type:"newcolumn"}
// 								, {type:"select", width:100, offsetLeft:15, label:"렌탈상태",name:"rntlStatus", userdata:{lov:"RNTL002"}}
								, {type:"select", width:100, offsetLeft:15, label:"렌탈상태",name:"rntlStatus"}
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
									
									PAGE.GRID1.list(ROOT + "/rntl/rntlMgmt/getMnthCalcList.json", form, {
										callback : function () {
											PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
											PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										}
									});
									
									break;
							}
						},
						onChange : function(name, value) {//onChange START
							// 검색구분
							if(name == "searchGbn") {
								PAGE.FORM1.setItemValue("searchName", "");

								if(value == null || value == "") {
									
									//PAGE.FORM1.enableItem("baseDt");
									
									PAGE.FORM1.setItemValue("searchName", "");
									
									//PAGE.FORM1.setItemValue("baseDt", curtMnth);
									
									PAGE.FORM1.disableItem("searchName");
								}
								else {
									//PAGE.FORM1.setItemValue("baseDt", "");
									
									//PAGE.FORM1.disableItem("baseDt");
									
									PAGE.FORM1.enableItem("searchName");
								}
							}
						},
						onValidateMore : function (data){
							
							if( data.searchGbn != "" && data.searchName.trim() == ""){
								this.pushError("searchName", "검색어", "검색어를 입력하세요");
								PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						}
			},
			
			GRID1 : {
				css : {
						height : "350px"
				},
				title : "조회결과",
				header : "정산월,대리점,계약번호,개통일자,반납일자,단말모델ID,단말모델코드,단말모델명,렌탈상태,매입단가,렌탈비용,정산일수(누적),정산금액(누적),정산일수(당월),정산금액(당월),환수일수,환수금액,검수비용,매각금액,잔존가액,매입보장가",
				columnIds : "prvdYm,agncyNm,contractNum,openDt,rtrnDt,intmMdlId,intmMdlCd,intmMdlNm,rntlStat,buyAmnt,rentalCost,prvdTotDay,prvdTotAmnt,prvdMonDay,prvdMonAmnt,backUsgDay,backAmnt,virfyCost,saleAmnt,remainAmnt,grntAmnt",
				colAlign : "center,center,center,center,center,center,left,left,center,right,right,right,right,right,right,right,right,right,right,right,right",
				colTypes : "roXdm,ro,ro,roXd,roXd,ro,ro,ro,ro,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				widths : "80,120,80,100,100,100,140,140,80,80,80,100,100,100,100,80,80,80,80,80,80",
				hiddenColumns: [5],
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
							orgnId : PAGE.FORM1.getItemValue("orgnId"),
							prvdYm : sdata.prvdYm,
							contractNum : sdata.contractNum
					}
					
					PAGE.GRID2.list(ROOT + "/rntl/rntlMgmt/getMnthCalcDtlList.json", data);
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
							mvno.cmn.download(ROOT + "/rntl/rntlMgmt/getMnthCalcListExcel.json?menuId=MSP8000004",true,{postData:searchData});
							
							break;
					}//switch End
				}// onButtonClick
			}, //End GRID1
			
			GRID2 : {
				css : {
						height : "200px"
				},
				title : "상세조회결과",
				header : "계약번호,단말모델ID,단말모델코드,단말모델명,적용월,전월잔존가액,사용일수,정산금액,환수일수,환수금액,검수비용,매각금액,잔존가액,매입보장가",
				columnIds : "contractNum,intmMdlId,intmMdlCd,intmMdlNm,prvdYm,prvRemainAmnt,usgDay,prvdAmnt,backUsgDay,backAmnt,virfyCost,saleAmnt,remainAmnt,grntAmnt",
				colAlign : "center,center,left,left,center,right,right,right,right,right,right,right,right,right",
				colTypes : "ro,ro,ro,ro,roXdm,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				widths : "80,80,140,140,80,90,80,80,80,80,80,80,80,80",
				hiddenColumns: [1],
				paging: false,
				pageSize:20
			} //End GRID2
		}; // end dhx
		
		var PAGE = {
				 title : "${breadCrumb.title}",
				 breadcrumb : "${breadCrumb.breadCrumb}",  
				 buttonAuth: ${buttonAuth.jsonString},
				 onOpen:function() {
					mvno.ui.createForm("FORM1");
					mvno.ui.createGrid("GRID1");
					mvno.ui.createGrid("GRID2");
					
					PAGE.FORM1.disableItem("searchName");
					
					mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0228"}, PAGE.FORM1, "orgnId");

					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2031"}, PAGE.FORM1, "searchGbn"); // 검색구분
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2030",orderBy:"etc6"}, PAGE.FORM1, "rntlStatus"); // 렌탈상태
				}
			};
		
</script>