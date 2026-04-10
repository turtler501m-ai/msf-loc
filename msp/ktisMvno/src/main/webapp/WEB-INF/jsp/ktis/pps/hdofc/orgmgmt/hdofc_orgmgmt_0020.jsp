<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_orgmgmt_0020.jsp
   * @Description : 본사  조직관리  온라인주문내역
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2009.02.01            최초 생성
   *
   * author 실행환경 개발팀
   * since 2009.02.01
   *
   * Copyright (C) 2009 by MOPAS  All right reserved.
   */
   %>
	
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div>  
	<div id="GROUP1">
		<div id="FORM2" class="section-search" ></div>
		
	</div>
	
	<!-- Script -->
	<script type="text/javascript">
	function goOnlineUpdate(orderSeqStr,pSize,pIdx)
	{	
		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var orderSeq = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("orderSeq")).getValue();
			
			if(orderSeqStr == orderSeq){
				rowId = i;
				break;
			}
		 }
		rowId = rowId - startSize;
		mvno.ui.createForm("FORM2");
		var rowData = PAGE.GRID1.getRowData(rowId+1);
		
		PAGE.FORM2.setItemValue("agentOrderSeq",rowData.orderSeq);
		PAGE.FORM2.setItemValue("status",rowData.status);
		PAGE.FORM2.setItemValue("adminMemo",rowData.adminMemo);
		
		mvno.ui.popupWindowById("FORM2", "온라인주문", 500, 300, {
            onClose: function(win) {
            	if (! PAGE.FORM2.isChanged()) return true;
            	mvno.ui.confirm("CCMN0005", function(){
            		win.closeForce();
            	});
            }
        });

		
	}

	

		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type : "input",label:"대리점",name : "searchAgentStr",width:100,maxLength:20},	// KimWoong
				                 {type : "newcolumn", offset:3},
				                 {type:"block", list:[
										{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
										,{type: "label", label: ":"}
				                 ]},
				                 {type : "newcolumn", offset:3},
				                 {type : "select",name : "searchAgentNm", width:200}, // KimWoong
				                 {type : "newcolumn"},
				                 {type : "select",label :"처리상태",name : "searchStatus", width:100,userdata: { lov: "PPS0033"}}
							 ]},// 1row
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type : "select",label:"검색",name : "searchType",width:100, userdata: { lov: "PPS0034"}},	
				                   	{type : "newcolumn", offset:3},
				                   	{type:"block", list:[
	   									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	   									,{type: "label", label: ":"}
	   			                   	 ]},	 
	   			                    {type : "newcolumn", offset:3},
	   			                   	{type : "input",name : "searchNm", width:200,maxLength:20}
	   			                 	
								
							 ]},// 2row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search2"} 
						],
						onKeyUp : function(inp, ev, name, value)
						{
							if(ev.keyCode == 13)
							{
								switch(name)
								{
									case "searchAgentStr":
										var agentStr  = this.getItemValue("searchAgentStr");
										var selType ="popPin";
										//alert(agentStr);
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
											{ 
											  selectType : selType , 
										      searchAgentStr : agentStr
											} 
										     , PAGE.FORM1, "searchAgentNm");

									        var agentOption =  this.getOptions("searchAgentNm");
							        
									           if(agentOption.length==1)
										       {
									        	   //alert("ㄷ대리점이 존재하지 않습니다.");
									           }else if(agentOption.length==2)
										        {

											        var selValue = agentOption[1].value;

									        	   this.setItemValue("searchAgentNm",selValue);
									           }

								      
										break;
										
									default :
										if (this.getItemType(name) == 'input'){
											//대리점검색시 엔터클릭시 검색안되도록
											this.setItemFocus("btnSearchPps");	// input focus 가 남아있으면.. Enter 키가 계속먹음.
											this.callEvent("onButtonClick", ["btnSearchPps"]);		// 조회버튼 이름은 'btnSearch' 로!
										}
									
										break;

										
								
								}	
							}
						},

						onButtonClick : function(btnId) {

							var form = this;
		
							switch (btnId) {
		
								case "btnSearchPps":
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
									
									PAGE.GRID1.list(ROOT + " /pps/hdofc/orgmgmt/OnlineOrderMgmtList.json", form, {
										callback : function () {
											PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
											PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										}
									});
									
									break;
		
							}

						}
			},

			// ----------------------------------------
			GRID1 : {

				css : {
					height : "550px"
				},
				title : "조회결과",
				header : "대리점명,요청일자,품목,모델명,수량,희망배송일자,배송주소,담당자,메모,처리상태,본사관리자,관리자메모,완료일자,주문번호,일련번호",
				columnIds : "agentName,reqDate,orderItemNm,modelNm,amount,shipDate,shipAddress,opNm,agentMemo,statusNm,bonsaAdminId,adminMemo,finishDate,orderSeq,linenum",
				widths : "150,70,100,100,100,90,200,100,200,90,100,100,200,70,0,0", //총 1500
				colAlign : "left,center,left,left,right,center,left,left,left,center,left,left,center,left,left",
				colTypes : "ro,ro,ro,ro,ron,ro,ro,ro,ro,link,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,int,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				 onButtonClick : function(btnId, selectedData) {

					switch (btnId) {

						case "btnDnExcel":
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
							
							var totCnt = PAGE.GRID1.getRowsNum();
							if(totCnt <= 0){
								mvno.ui.notify("출력건수가 없습니다.");
							   return;
							}
							//alert(totCnt);
							if(totCnt>5000)
							{
								mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
							   
							}
							
							var url = "/pps/hdofc/orgmgmt/OnlineOrderMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);

							 	console.log("searchData",searchData);
							
						  		mvno.cmn.download(url+"?menuId=PPS1300002",true,{postData:searchData});
							break;
							
						
						
					}
				}
			},

			//---------------------------------------
			
			FORM2 : {

				title : "온라인주문",
				buttons : {
					center : {
						btnOrderSubmit : { label : "수정" }, 
						btnOrderPopClose : { label : "닫기" }
					}
				},
				items : [
					{type: 'hidden', name: 'agentOrderSeq', value: ''},
					{type:"block", width:70,height:15, list:[
													{type:"label", label:"상태"}
													,{type:"label", label:"메모"}
					                      					]
					}
					,{type:"newcolumn"}
					,{type:"block", width:300, height:15, list:[
															{type:"block", list:[
													        	{type:"settings", position:"label-right", labelWidth:"auto"}
													        	,{type: "radio", name: "status", value: "0", label: "접수", checked: true}
													          	,{type:"newcolumn", offset:10}
													          	,{type: "radio", name: "status", value: "1", label: "처리중"}
													          	,{type:"newcolumn", offset:10}
													          	,{type: "radio", name: "status", value: "2", label: "처리완료"}
																]
															}
															,{type:"input", name: "adminMemo", width:300, maxlength:20} 	//메모
															
					                       					]
					}
							
				]
				,onButtonClick : function(btnId, selectedData) {

					switch (btnId) {

					case "btnOrderSubmit":
						var form = this;
						var data = form.getFormData(true);
						
						var url = ROOT + "/pps/hdofc/orgmgmt/ppsOnlineOrderAdminProc.json";
						mvno.cmn.ajax(
								url, 
								{

									orderSeq : data.orderSeq,
									status : data.status,
									
									adminMemo : data.adminMemo
								}
								, function(results) {
									 var result = results.result;
	                                  var retCode = result.oRetCode;
	                                  var retMsg = result.oRetMsg;
	                                  
	                                  mvno.ui.notify(retMsg);
	                                  if(retCode == "0000") {
	                            		  PAGE.GRID1.refresh();
	                            		  mvno.ui.closeWindowById(form, true);
	                                  }
								});	
						break;
					case "btnOrderPopClose":
						mvno.ui.closeWindowById(this);
						break;
					}
				}
			}

		};

		var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("orderSeq" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);

				var selType ="popPin";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentNm");
				

			}

		};
		
		
	</script>
