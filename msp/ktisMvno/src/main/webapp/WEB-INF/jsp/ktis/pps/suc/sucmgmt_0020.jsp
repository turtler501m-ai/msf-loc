<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="FORM1" class="section-search"></div>

<!-- 조직 리스트 그리드 -->
<div id="GRID1"></div>


 <!-- Script -->
<script type = "text/javascript">
	<%@ include file = "suc.formitems" %>
	var isCntpShop = false;
	var cntpntShopId="";
	var cntpntShopNm="";
	var typeCd = '${orgnInfo.typeCd}';
	if (typeCd == '20' || typeCd == '30' ) {
		isCntpShop = true;
		if(typeCd == '20'){
			cntpntShopId = '${orgnInfo.orgnId}';
			cntpntShopNm = '${orgnInfo.orgnNm}';
		}else{
			isAgent = true;
			cntpntShopId = '${orgnInfo.hghrOrgnCd}';
			cntpntShopNm = '${orgnInfo.hghrOrgnNm}';
			pAgentCode = '${orgnInfo.orgnId}';
			pAgentName = '${orgnInfo.orgnNm}';
		}
	}

	var userId = '${loginInfo.userId}';
	var userName = '${loginInfo.userName}';
	var orgId = '${orgnInfo.orgnId}';
	var orgNm = '${orgnInfo.orgnNm}';

	var DHX = {
	// 검색 조건
		FORM1 : {
			items : [
				{type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0},
				{type : "block",
					list : [
							{type : "calendar",width : 120,label : "처리일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "sysRdateS", offsetLeft:30},
							{type : "newcolumn"},
							{type : "calendar",label : "~",name : "sysRdateE",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 120},
							{type : "newcolumn"},
							{type : "select",width : 120,offsetLeft : 20,label : "검색구분",name : "searchGbn",userdata : {lov : "PPS0061"}, offsetLeft:30},
							{type : "newcolumn"},
							{type : "input",width : 120, name : "searchName", offsetLeft:15}
					]
				},
				{type : "button",name : "btnSearch",value : "조회",className : "btn-search1"}
			],
			onButtonClick : function (btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						if (!this.validate()) return;
						var url = ROOT + "/pps/sucMgmt/getSucStatisticsListAjax.json";

						PAGE.GRID1.list(url, form, {
							callback : function () {
								
							}
						});
						
					break;
					case "btnOrgFind2":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("cntpntShopId", result.orgnId);
							form.setItemValue("cntpntShopNm", result.orgnNm);
						});
					break;	
				}
			}
		},
		//리스트 - GRID1
		GRID1 : {
			css : {height : "550px"},
			title : "통계리스트",
			header : "정책ID,정책명,요금제,사용기간,처리대상건수,전액소진완료,진행중,잔액소진실패,처리일자",
			columnIds : "setupId,setupNm,serviceNm,expireCnt,rowCnt,succCnt,ingCnt,failCnt,batchDt",
			widths : "90,120,100,100,110,110,110,110,110",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str",
			colAlign : "center,center,center,center,center,center,center,center,center",
			paging : true,
			multiCheckbox: false,
			//checkSelectedButtons : ["btnDtl"],
			onRowDblClicked : function (rowId, celInd) {
			var grid = this;
			},
			
			onRowSelect : function (rowId, celInd) {
				
			},
			buttons : {
				/* hright : {
					btnDnExcel : {label : "엑셀다운로드",auth:"A-SUC-DOWN"}
				}, 
				right : {
                	btnDel : { label : "삭제" }
                }
				*/
           },
           onButtonClick : function (btnId, selectedData) {
				var form = this;
	            if(btnId == "btnDel"){
	            	
	            }
           }
           
		} // 리스트 - GRID1
		
	};

var PAGE = {
	title : "${breadCrumb.title}",
	breadcrumb : "${breadCrumb.breadCrumb}", 
	//buttonAuth: ${buttonAuth.jsonString},
	onOpen : function () {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		var sysRdateE = new Date().format('yyyymmdd');
		var time = new Date().getTime();
		time = time - 1000 * 3600 * 24 * 7;
		var sysRdateS = new Date(time);

		PAGE.FORM1.setItemValue("sysRdateS", sysRdateS);
		PAGE.FORM1.setItemValue("sysRdateE", sysRdateE);
		
		
	}
};


</script>
