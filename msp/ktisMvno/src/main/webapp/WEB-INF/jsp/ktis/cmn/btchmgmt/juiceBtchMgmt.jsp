<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
   * @Class Name : msp_cls_bs_6005.jsp
   * @Description : 스케줄러로그조회
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

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var startDt = new Date().format("yyyymm") + "01";
	var today = new Date().format("yyyymmdd");
	var curMnth = new Date().format("yyyymm");
	
	var DHX = {
		// ----------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
				, {type:"select", label:"배치", labelWidth:40, name:"oprtSctnCd", width:150, offsetLeft:10}
				, {type:"newcolumn"}
				, {type:"block", list:[
					{type:"calendar", name:"oprtStrtDt", label:"조회기간", value:startDt, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100, readonly:true, offsetLeft:30}
					, {type:"newcolumn"}
					, {type:"calendar", name:"oprtEndDt", label:"~", value:today, labelAlign:"center", labelWidth:10, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100, readonly:true, offsetLeft:5}
				]}
				, {type:"newcolumn"}
				, {type:"select", label:"상태", name:"oprtStatCd", labelWidth:40, width:100, offsetLeft:30, required:true}
				//조회 버튼
				, {type:"newcolumn"}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
			]
			, onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					case "btnSearch":
						var url = ROOT + "/cms/batch/getJuiceBtchLogListAjax.json";
						PAGE.GRID1.list(url, form);
						break;
				}
			}
		}
		// ----------------------------------------
		, GRID1 : {
			css : {
				height : "590px"
			}
			//, title : "조회결과"
			, header : "처리상태,배치명,처리건수,파일명,실행시작일시,실행종료일시,파라미터"
			, columnIds : "oprtStatNm,cdDsc,dtlCnt,oprtFileNm,oprtStrtDt,oprtEndDt,remrk"
			, colAlign : "center,left,right,left,center,center,left"
			, widths : "80,200,80,300,150,150,80"
			, colTypes : "ro,ro,ron,ro,ro,ro,ro"
			, colSorting : "str,str,int,str,str,str,str"
			, paging : true
			, pageSize : 100
		}
	};
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			// 배치
			mvno.cmn.ajax4lov(ROOT+"/cms/batch/getJuiceBtchIdList.json", "", PAGE.FORM1, "oprtSctnCd");
			// 상태
			mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0110"}, PAGE.FORM1, "oprtStatCd");
		}
	};
</script>