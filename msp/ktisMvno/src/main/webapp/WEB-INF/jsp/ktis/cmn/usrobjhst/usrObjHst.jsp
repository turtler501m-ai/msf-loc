<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : usrObjHst.jsp
     * @Description : 
     * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2018.04.18 박준성
     * @
     * @author : 박준성
     * @Create Date : 2018.04.18
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="FORM2" class="section-box"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

    var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
				, {type:"block", list:[
					{type:"select", width:100, label:"일자구분", name:"dateGbn", offsetLeft:10, labelWidth:80, required:true}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"pSearchStartDt", value:"${startDate}", calendarPosition:"bottom", readonly:true ,width:100,offsetLeft:7}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"pSearchEndDt", label:"~", value:"${endDate}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", width:150, offsetLeft:50, label:"소유자",name:"pOwner", labelWidth:80, value:"MSP_WAS"}
				]}
				, {type:"block", list:[
					{type:"input", width:330, label:"OBJECT 명",name:"pObjName", labelWidth:80, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"select", width:150, offsetLeft:50, label:"OBJECT 타입",name:"pObjType", labelWidth:80, value:"PROCEDURE"}
				]}                   	
				, {type:"hidden", name: "DWNLD_RSN", value:"" , placeholder:"검색조건을 이용하여 엑셀자료를 생성합니다."} //엑셀다운로드 사유 2018-03-08 권오승
				, {type:"hidden", name: "btnCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
				, {type:"hidden", name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
				         	
				, {type:"button", value:"조회", name:"btnSearch", className:"btn-search2"}
			]
    
			, onButtonClick : function(btnId) {

				var form = this;

					switch (btnId) {
						case "btnSearch":
							
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							if (!this.validate()) return;							
							
							var url = ROOT + "/cmn/usrobjhst/getObjHstList.json";
							
							PAGE.GRID1.list(url, form, {
								callback : function () {
								//	PAGE.FORM2.clear();
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});							
								
							break;
					}
			}
			, onValidateMore : function (data) {
				if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
					this.pushError("dateGbn", "일자구분", "종료일자가 시작일보다 클수 없습니다.");
					this.resetValidateCss();
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
			}
    		
		},

		GRID1 : {
			css : {
				height : "530px"
			},
			title : "조회결과",
			header : "소유자,OBJECT타입,OBJECT명,최초등록일자,수정일자,등록일자", //6
			columnIds : "owner,objectType,objectName,created,lastDdlTime,regstDttm", //6
			colAlign : "left,left,left,left,left,left", //6
			colTypes : "ro,ro,ro,ro,ro,ro",//6
			colSorting : "str,str,str,str,str,str",//6
			widths : "90,120,250,160,160,160",//6
			paging:true,
			pageSize:20,
// 			buttons : {
// 				hright : {
// 					btnDnExcel : { label : "엑셀다운로드" }
// 				}
// 			},
            onRowDblClicked : function(rowId, celInd) {
           		this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
           	},

            onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
              		
				switch (btnId) {

					case "btnRead":
						var data = PAGE.GRID1.getSelectedRowData();
												
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						
						PAGE.FORM2.setFormData(data);
						mvno.cmn.ajax(ROOT + "/cmn/usrobjhst/getObjSource.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
							PAGE.FORM2.setItemValue("sourceText", resultData.result.data.rows[0].sourceText);
						});
						
						mvno.ui.popupWindowById("FORM2", "소스", 960, 750);
						
						break;
				}
            }
		},
		FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelWidth:30}
				, {type: "block", list: [
					{type:"input", width:200, offsetLeft:5, label:"이름",name:"objectName", readonly:true}
				]}
				, {type: "block", list: [
					{type:"input", label:"소스", name:"sourceText", width:830, rows:42, offsetLeft:5, readonly:true}
				]}
			]
			, buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				var fileLimitCnt = 1;

				switch (btnId) {
					case "btnClose" :
						mvno.ui.closeWindowById(form);   
					break;
                	}
			},
		}

    };
    
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
	        buttonAuth: ${buttonAuth.jsonString},
	    	onOpen : function() {
		        mvno.ui.createForm("FORM1");
		        mvno.ui.createGrid("GRID1");

		        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0240"}, PAGE.FORM1, "dateGbn"); // 일자구분
		        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0238"}, PAGE.FORM1, "pOwner"); // 소유자
		        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0239"}, PAGE.FORM1, "pObjType"); // object타입
	    	}
	};

</script>