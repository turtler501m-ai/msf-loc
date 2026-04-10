<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  

<script type="text/javascript">
<%@ include file = "../custMgmt/cust.formitems" %>


var reqFlag = true;
var sOrgnId="";
var sOrgnNm="";
var hghrOrgnCd = "";
var typeCd = '${orgnInfo.typeCd}';

var userId = '${loginInfo.userId}';
var userName = '${loginInfo.userName}';
var orgId = '${orgnInfo.orgnId}';
var orgNm = '${orgnInfo.orgnNm}';

var orgType = "";

if (typeCd == '20' || typeCd == '30' ) {
	reqFlag = true;
	sOrgnId = '${orgnInfo.orgnId}';
	sOrgnNm = '${orgnInfo.orgnNm}';
	if(typeCd == '30'){
		hghrOrgnCd = '${orgnInfo.hghrOrgnCd}';
	}
}

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
					,{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"조회기간", calendarPosition:"bottom", inputWidth : 100, offsetLeft:10} 
					,{type:"newcolumn", offset:3}
					,{type: "label", label:"~", labelWidth:10, labelAlign:"center"},
					,{type:"newcolumn"}
					,{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", calendarPosition:"bottom", inputWidth:100}
					,{type:"newcolumn"}
					,{type:"checkbox", label:"월별", name:"mthChk", offsetLeft:20, labelWidth:30}
					//,{type:"newcolumn"}
					//,{type:"checkbox", label:"요금제", name:"socChk", offsetLeft:20, labelWidth:40}
					//,{type:"newcolumn"}
					//,{type:"checkbox", label:"단말모델", name:"prdtChk", offsetLeft:20, labelWidth:50}
					//,{type:"newcolumn"}
					//,{type:"input", label:"대리점", name:"pAgentCode", value:sOrgnId, width:100,readonly:reqFlag, offsetLeft:20}
			        //,{type:"newcolumn"}
			        //,{type:"button", value:"찾기", name:"btnOrgFind", disabled:false}
			        //,{type:"newcolumn"}
			        //,{type:"input", name:"pAgentName",value:sOrgnNm, width:150, readonly: true}
			      	//,{type:"hidden",name:"hghrOrgnCd",value:hghrOrgnCd}
			      	//,{type:"hidden",name:"typeCd",value:typeCd}
					,{type:"newcolumn"}
					,{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"} 
				]
		,onButtonClick : function(btnId) {

			var form = this;

			switch (btnId) {
			
				case "btnOrgFind": 
					mvno.ui.codefinder("ORG", function(result) {
						form.setItemValue("pAgentCode", result.orgnId);
						form.setItemValue("pAgentName", result.orgnNm);
					});
				break;

				case "btnSearch":

					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getOpenStatList.json", form);
					break;

			}

		}
		,onValidateMore : function(data){
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
		,header : "일자,대리점명,개통건수,#cspan,#cspan,#cspan,정지건수,#cspan,#cspan,취소건수,#cspan,#cspan,해지건수,#cspan,#cspan,누적건수"
		,header2 : "#rspan,#rspan,신규,번호이동,서식지오류,소계,신규,번호이동,소계,신규,번호이동,소계,신규,번호이동,소계,#rspan"
		,columnIds : "statDt,orgnNm,nacOpenCnt,mnpOpenCnt,errOpenCnt,openCnt,nacStopCnt,mnpStopCnt,stopCnt,nacCanCnt,mnpCanCnt,canCnt,nacCloseCnt,mnpCloseCnt,closeCnt,acmlCnt"
		,widths : "120,200,80,80,100,80,80,80,80,80,80,80,80,80,80,100"
		,colAlign : "center,left,right,right,right,right,right,right,right,right,right,right,right,right,right,right"
		,colTypes : "ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron"
		,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
		,paging : true
		//,pagingStyle :2
		,pageSize:20
		,buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" } 
			}
		}
		,onButtonClick : function(btnId) {

			switch (btnId) {
				
				case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
					if(PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
					} else {
						var searchData =  PAGE.FORM1.getFormData(true);
						
						mvno.cmn.download('/stats/statsMgmt/statsCustListExcel.json'+"?menuId=MSP1000103",true,{postData:searchData});
					}
					break;

			}
		}
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