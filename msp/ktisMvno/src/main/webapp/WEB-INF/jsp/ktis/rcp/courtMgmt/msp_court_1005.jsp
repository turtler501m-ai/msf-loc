<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
					{type:"block", list:[
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"입금일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
						{type:"newcolumn"},
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
						{type:'newcolumn'},
						{type:"select", label:"검색구분", name:"searchCd", width:75, offsetLeft:40, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계좌번호"}, {value:"2", text:"식별번호"}]},
						{type:"newcolumn"},
						{type:"input", name:"searchVal", width:125, maxLength:30},
                        {type:"hidden", label:"식별번호검색값", name:"searchRrn"}
					]},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
				],	
				
				onButtonClick : function(btnId) {
					var form = this;
					
					switch (btnId) {
						case "btnSearch":
	                    	if(form.getItemValue("searchCd") == "2") {
	                    		form.setItemValue("searchRrn", form.getItemValue("searchVal"));
	                    	}
	                    	
							PAGE.GRID1.list(ROOT + "/rcp/courtMgmt/getCourtDpstList.json", form);
							
							break;
					}
				},
				onChange : function(name, value) {//onChange START
					// 검색구분
					if(name == "searchCd") {
						PAGE.FORM1.setItemValue("searchVal", "");
		
						if(value == null || value == "") {
							var searchEndDt = new Date().format('yyyymmdd');
							var time = new Date().getTime();
							time = time - 1000 * 3600 * 24 * 7;
							var searchStartDt = new Date(time);
		
							// 신청일자 Enable
							PAGE.FORM1.enableItem("strtDt");
							PAGE.FORM1.enableItem("endDt");
		
							PAGE.FORM1.setItemValue("searchVal", "");
		
							PAGE.FORM1.setItemValue("strtDt", searchStartDt);
							PAGE.FORM1.setItemValue("endDt", searchEndDt);
		
							PAGE.FORM1.disableItem("searchVal");					
						}
						else {
							PAGE.FORM1.setItemValue("strtDt", "");
							PAGE.FORM1.setItemValue("endDt", "");
							
							// 신청일자 Disable
							PAGE.FORM1.disableItem("strtDt");
							PAGE.FORM1.disableItem("endDt");
							
							PAGE.FORM1.enableItem("searchVal");
						}	
					}			
				},	
				onKeyUp : function (inp, ev, name, value){
				
					var searchCd = PAGE.FORM1.getItemValue("searchCd");
			
					if(name == "searchVal"){
						if(searchCd == "1" || searchCd == "2") {	
							var val = PAGE.FORM1.getItemValue("searchVal");
							PAGE.FORM1.setItemValue("searchVal",val.replace(/[^0-9]/g,""));
						}
					}
				},
	            onValidateMore : function (data){

	                if(data.strtDt > data.endDt){
	                    this.pushError("strtDt","입금일자",["ICMN0004"]);
	                }

	                if( data.searchCd != "" && data.searchVal.trim() == ""){
	                    this.pushError("searchVal", "검색어", "검색어를 입력하세요");
	                    PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
	                }
	            }
			}
	
		, 	GRID1 : {
				css : {
					height : "580px"
				}
				, title : "조회결과" // 12
				, header :"입금일자,가상계좌은행,가상계좌,가상계좌 신고일,이름,법정관리유형,관할법원,판결번호,변제기간,입금액(원),입금자명,입금회차"
				, columnIds : "inoutDt,vacBankNm,vacId,vacDt,cstmrName,crTp,crComp,noJudg,rbPrid,totalPric,inoutName,inoutOrder"
				, widths : "90,80,120,100,60,100,100,120,80,90,110,70"
				, colAlign : "center,center,center,center,center,center,center,center,center,right,center,center"
				, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ro,ron"
				, colSorting : "str,str,str,str,str,str,str,str,str,int,str,int"
				//, multiCheckbox : true
	            , paging : true
	            , showPagingAreaOnInit : true
	            , pageSize : 20
				, buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드"}
					},
				},
				onButtonClick : function(btnId) {
					var grid = this;
					
					switch (btnId) {
						case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
							} else {
								var searchData =  PAGE.FORM1.getFormData(true);
								mvno.cmn.download("/rcp/courtMgmt/getCourtDpstListByExcel.json?menuId=MSP1008005", true, {postData:searchData});
							}
							break;
					}
				}
			}
	
	}

	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			 onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
			}
	};	



</script>