<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*" %>

<%
/**
 * @Class Name : msp_org_bs_1030_usrGrpHst.jsp
 * @Description : 사용자그룹이력로그 화면
 * @
 * @ 수정일     수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.02.09 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2015. 2. 09.
 */
%>
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full" ></div>
    
 
<!-- Script -->
<script type="text/javascript">
var gMonthFristDay = "${startDate}";
var gMonthLastDay = "${endDate}";

var DHX = {
		FORM1 : {
			items : [
						{ type : "settings", position : "label-left", labelAlign : "left",  blockOffset : 0 },
						{ type : "block", labelWidth : 30 , list : [
							{ type : "calendar" , name:"searchStrtDt",labelWidth : 50, label:"조회기간", id:"searchStrtDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100, value:gMonthFristDay, offsetLeft : 10 },
							{ type : "newcolumn" },
							{ type : "calendar" , name:"searchEndDt", id:"searchEndDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100, value:gMonthLastDay},
							{ type : "newcolumn", offset:30 }, 
							{ type : "input", label : "사용자",labelWidth : 40,  name : "searchUsrNm", width : 100, disabled : true}, 
							{ type : "newcolumn" }, 
							{ type : "input", label : "", name : "searchUsrId" , width : 120, disabled:true},
							{ type : "newcolumn" },
							{ type : "button", value : "찾기", name : "btnUseFind" }
						]},
						{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],				
				onButtonClick : function(btnId) {
					var form = this;
					
					switch (btnId) {
					
						case "btnUseFind" :
							mvno.ui.codefinder("USER", function(result) {
								form.setItemValue("searchUsrNm", result.usrNm);
						        form.setItemValue("searchUsrId", result.usrId);
							}); 
						   break;  
						case "btnSearch" :
							var url1 = ROOT + "/cmn/authmgmt/usrGrpHstList.json";
							PAGE.GRID1.list(url1, form);
							break;
					}
				},
                onValidateMore : function (data){
			         if((data.searchStrtDt && !data.searchEndDt) || (!data.searchStrtDt && data.searchEndDt) || (!data.searchStrtDt || !data.searchEndDt)){
			             this.pushError("data.searchStartDt","검색일자","일자를 선택하세요");
			         }
			         else if(data.searchStrtDt > data.searchEndDt){
			             
			             this.pushError("data.searchStrtDt","검색일자",["ICMN0004"]);
			         }
			     }
		},
		GRID1 : {
			css : {
				height : "575px"
			},
		title : "조회결과",
		header : "순번,사용자ID,사용자명,그룹ID,그룹명,그룹개요,변경일시,처리유형",//8
		columnIds : "seqNum,usrId,usrNm,grpId,grpNm,grpDsc,regstDttm,operTpye",
		widths : "60,100,100,125,125,150,150,123",
		colAlign : "center,left,left,left,left,left,center,center",
		colTypes : "ro,ro,ro,ro,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str",
		paging : true,
		pageSize : 20
		}
};

var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
		}
}
</script>