<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_rntl_mgmt_8003.jsp
 * @Description : 매입매각내역
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.04.27  	강무성		  최초생성
 * @ 
 * @author : 강무성
 * @Create Date : 2017.04.27
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
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							, {type:"block", list:[
								{type:"calendar", label:"정산월", name:"baseDt", value:curtMnth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true}
								, {type: 'newcolumn'}
								, {type:"select", width:150, offsetLeft:15, labelWidth:50, label:"대리점",name:"orgnId"}
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
									
									PAGE.GRID1.list(ROOT + "/rntl/rntlMgmt/getPurchList.json", form, {
										callback : function () {
											
											PAGE.GRID2.list(ROOT + "/rntl/rntlMgmt/getSaleList.json", form, {
												callback : function () {
													PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
													PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
												}
											});
										}
									});
									
									break;
							}
						}
			},
			
			GRID1 : {
				css : {
						height : "290px"
				},
				title : "매입내역",
				header : "정산월,대리점,단말모델코드,단말모델명,매입단가,매입수량,매입합계,물류비용,지급합계",
				columnIds : "prvdYm,agncyNm,intmMdlCd,intmMdlNm,buyAmnt,buyCnt,buySumAmnt,deplrictCost,buyPayAmnt",
				colAlign : "center,center,left,left,right,right,right,right,right",
				colTypes : "roXdm,ro,ro,ro,roXns,roXns,roXns,roXns,roXns",
				colSorting : "str,str,str,str,str,str,str,str,str",
				widths : "80,100,120,150,100,80,100,100,100",
				paging: false,
				pageSize:20,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
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
							
							if(PAGE.GRID1.getRowsNum() == 0 && PAGE.GRID2.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/rntl/rntlMgmt/getPurchSaleListExcel.json?menuId=MSP8000003",true,{postData:searchData});
							
							break;
					}//switch End
				}// onButtonClick
			}, //End GRID1
			
			GRID2 : {
				css : {
						height : "290px"
				},
				title : "매각내역",
				header : "정산월,대리점,단말모델코드,단말모델명,매입단가,매각수량,매각금액,검수비용,실매각금액,정산금액",
				columnIds : "prvdYm,agncyNm,intmMdlCd,intmMdlNm,buyAmnt,saleCnt,saleAmnt,virfyCost,salePayAmnt,prvdAmnt",
				colAlign : "center,center,left,left,right,right,right,right,right,right",
				colTypes : "roXdm,ro,ro,ro,roXns,roXns,roXns,roXns,roXns,roXns",
				colSorting : "str,str,str,str,str,str,str,str,str,str",
				widths : "80,100,120,150,100,80,100,100,100,100",
				paging: false,
				pageSize:20,
				buttons : {
					hright : {
						//btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				onButtonClick : function(btnId, selectedData) {
					var form = this;
					switch (btnId) {
						case 'btnDnExcel' :
							
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
							mvno.cmn.download(ROOT + "/rntl/rntlMgmt/getSaleListExcel.json?menuId=MSP8000003",true,{postData:searchData});
							
							break;
					}//switch End
				}// onButtonClick
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
					
					mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0228"}, PAGE.FORM1, "orgnId");
				}
			};
		
</script>