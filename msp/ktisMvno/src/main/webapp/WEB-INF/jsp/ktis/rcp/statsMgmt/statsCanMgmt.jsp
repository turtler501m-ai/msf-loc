<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  

<script type="text/javascript">


var reqFlag = true;

function setCurrentMonthFirstDay(form, name)
{
	var today = new Date();
	var mm = today.getMonth(); //January is 0!
	var yyyy = today.getFullYear();

	form.setItemValue(name, new Date(yyyy,mm,01));
}

function setCurrentDate(form, name)
{
	form.setItemValue(name, new Date());
}

var DHX = {
	FORM1 : {
		title : ""
		,items : [ 
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
					,{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"해지일자", calendarPosition:"bottom", inputWidth : 100, offsetLeft:10} 
					,{type:"newcolumn", offset:3}
					,{type: "label", label:"~", labelWidth:10, labelAlign:"center"},
					,{type:"newcolumn"}
					,{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", calendarPosition:"bottom", inputWidth:100}
					,{type:"newcolumn"}
					,{type:"checkbox", label:"월별", name:"mthChk", offsetLeft:20, labelWidth:30}
					,{type:"newcolumn"}
					,{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"} 
				]
				,onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {

					case "btnSearch":
	
						PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getCanStatList.json", form);
						break;
					}
				},onValidateMore : function(data){
					if(data.strtDt == ''){
						this.pushError("strtDt", "시작일자", "필수 입력 항목입니다.");
					}
					if(data.endDt == ''){
						this.pushError("endDt", "종료일자", "필수 입력 항목입니다.");
					}
					if(data.strtDt > data.endDt){
						this.pushError("strtDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}
				}
	}
	,GRID1 : {

		css : {
			height : "573px"
		},
		title : "조회결과"
		,header : "일자,서비스구분,해지건수"
		,columnIds : "statDt,pppo,canCnt"
		,widths : "120,120,120"
		,colAlign : "center,center,right"
		,colTypes : "ro,ro,ron"
		,colSorting : "str,str,str"
		,paging : true
		,pageSize:20
	}
};

var PAGE = {
	title: "${breadCrumb.title}",
	buttonAuth: ${buttonAuth.jsonString},
	breadcrumb: "${breadCrumb.breadCrumb}",
	onOpen : function() {

		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		setCurrentMonthFirstDay(PAGE.FORM1, "strtDt");
		setCurrentDate(PAGE.FORM1, "endDt");

	}

};
</script>