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
                        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"조회일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10},
                        {type:"newcolumn"},
                        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5}
					]},
					{type: 'button', value: '조회', name: 'btnSearch', className:"btn-search1"}
				],	
				
				onButtonClick : function(btnId) {
					var form = this;
					
					switch (btnId) {
						case "btnSearch":
							PAGE.GRID1.list(ROOT + "/rcp/courtMgmt/getCourtSttcList.json", form);
							
							break;
					}
				},
			    onValidateMore : function (data){
			
			        if(data.strtDt > data.endDt){
			            this.pushError("strtDt","조회일자",["ICMN0004"]);
			        }
			    }
			}
	
		, 	GRID1 : {
				css : {
					height : "600px"
				}
				, title : "조회결과" // 27
				, header :"기준일자,총계,회생,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,파산,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,상속한정승인,#cspan,#cspan,등록자,등록일시,수정자,수정일시"
				, header2 : "#rspan,#rspan,계,진행중,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,종결,#cspan,#cspan,계,진행중,#cspan,#cspan,종결,#cspan,#cspan,계,진행중,종결,#rspan,#rspan,#rspan,#rspan"
				, header3 : "#rspan,#rspan,#rspan,소계,부채증명서발급,금지명령,개시결정,변제계획안수정,공탁중,기타,소계,면책,폐지/취소,#rspan,소계,파산선고,기타,소계,면책,폐지/취소,#rspan,기타,완료,#rspan,#rspan,#rspan,#rspan"
				, columnIds : "regstDt,total,totalRg,sumRg,lcRg,ncRg,ccRg,rpRg,dpRg,etcRg,sumRgEnd,immRgEnd,dcRgEnd,totalBr,sumBr,brBr,etcBr,sumBrEnd,immBrEnd,dcBrEnd,totalEnt,sumEnt,sumEntEnd,regstId,regstDttm,rvisnId,rvisnDttm"
				, widths : "80,49,49,49,101,66,66,101,49,49,49,49,76,49,49,66,49,49,49,76,49,49,49,80,120,80,120"
				, colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
				, colTypes : "ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro,ro,ro"
				, colSorting : "str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str,str,str,str"
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
								mvno.cmn.download("/rcp/courtMgmt/getCourtSttcListByExcel.json?menuId=MSP1008004", true, {postData:searchData});
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