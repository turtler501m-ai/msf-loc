<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
/**
* @Class Name : msp_org_bs_1042.jsp
* @Description :
* @Modification Information
*
*   수정일         수정자                   수정내용
*  -------    --------    ---------------------------
*  2009.02.01            최초 생성
*
* author
* since
*
* Copyright (C) 2009 by MOPAS  All right reserved.
*/
%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full" ></div>


<!-- Script -->
<script type="text/javascript">

	var DHX = {

		// ----------------------------------------
		FORM1 : {
			items : [
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 60, blockOffset : 0 },
				{ type : "block", labelWidth : 30 , list : [
					{ type : "input", name : "orgnId", label : "조직", width : 100, offsetLeft : 10 },
					{ type : "newcolumn" },
					{ type : "button", value : "검색", name : "btnOrgFind" },
					{ type : "newcolumn" },
					{ type : "input", name : "orgnNm", width : 150, readonly : true },
					{ type : "newcolumn", offset : 50 },
					{ type : "calendar" , name:"searchStrtDt", label:"조회기간", id:"searchStrtDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100 },
					{ type : "newcolumn" },
					{ type : "calendar" , name:"searchEndDt", id:"searchEndDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100 },
				]},
				{ type : "block", labelWidth : 30 , list : [
					{ type : "input", label : "사용자이름", name : "searchUsrNm" , offsetLeft : 10, maxLength : 50, width : 150},
					{ type : "newcolumn", offset : 204 },
					{ type : "select", label : "메뉴", name : "searchMenuList", id : "searchMenuList", width : 235 },
				]},
				{ type : "button",name : "btnSearch",value : "조회", className:"btn-search2" }
			],

			onButtonClick : function(btnId) {

				var form = this;

				switch (btnId) {

					case "btnSearch":

						var url1 = ROOT + "/cmn/menuaccesssrch/selecList.json";

						PAGE.GRID1.list(url1, form);
						break;

					case "btnOrgFind" :

						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break;

				}

			}
		},

		// ----------------------------------------
		GRID1 : {

			css : {
				height : "573px"
			},
			title : "메뉴접속",
			header : "사용자ID,사용자명,조직ID,조직명,메뉴 ID,메뉴명,접속일시",
			columnIds : "USR_ID,USR_NM,ORGN_ID,ORGN_NM,MENU_ID,MENU_NM,CONN_DT",
			widths : "120,120,120,140,100,180,150",
			colAlign : "left,left,left,left,center,left,left,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize : 20,
			//onRowSelect : function(rowID, celInd) {
			//    console.log("Row Selected ..." + rowID + ':' + celInd);
				// 아마도 Popup Window 처리...(다양한 Pattern 처리)
			//},


		}


	};

	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",

		buttonAuth: ${buttonAuth.jsonString},

		onOpen : function() {

			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			var today = new Date();
			PAGE.FORM1.setItemValue("searchStrtDt", "" + today.getFullYear() + ('0' + (today.getMonth() + 1)).slice(-2)  + ('0' + today.getDate()).slice(-2) );
			PAGE.FORM1.setItemValue("searchEndDt", "" + today.getFullYear() + ('0' + (today.getMonth() + 1)).slice(-2)  + ('0' + today.getDate()).slice(-2) );


			mvno.cmn.ajax4lov(ROOT + "/cmn/codefind/cmnMenuMst.json", {usgYn:"Y"}, PAGE.FORM1, "searchMenuList");

		}

	};


</script>

