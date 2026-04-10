<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_sgi_1002.jsp
	 * @Description : 단말상세조회 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.04 장익준 최초생성
	 * @
	 * @author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
 				,{type:"block", list:[
					{type : 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'baseDate', enabelDay:false, label: '연동월', value: "${currentMonth}", calendarPosition: 'bottom', readonly:true, width:100, offsetLeft:10, required:true}
				]}
				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
			]
				,onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/rcp/sgiMgmt/getSgiDmndDtlList.json", form);
						break;
					}
				}
			},

		//-------------------------------------------------------------
           GRID1 : {
			css : {
				height : "598px"
			},
			title : "조회결과",
			header : "업무구분,회사구분,보험청구의뢰번호,오류결과코드,보험관리번호,할부연차,납기일(1),납기일(2),납기일(3),납기일(4),납기일(5),납기일(6),납기일(7),납기일(8),납기일(9),납기일(10),납기일(11),납기일(12),할부원금(1),할부원금(2),할부원금(3),할부원금(4),할부원금(5),할부원금(6),할부원금(7),할부원금(8),할부원금(9),할부원금(10),할부원금(11),할부원금(12),할부이자(1),할부이자(2),할부이자(3),할부이자(4),할부이자(5),할부이자(6),할부이자(7),할부이자(8),할부이자(9),할부이자(10),할부이자(11),할부이자(12),가산금(1),가산금(2),가산금(3),가산금(4),가산금(5),가산금(6),가산금(7),가산금(8),가산금(9),가산금(10),가산금(11),가산금(12),미납할부원금(1),미납할부원금(2),미납할부원금(3),미납할부원금(4),미납할부원금(5),미납할부원금(6),미납할부원금(7),미납할부원금(8),미납할부원금(9),미납할부원금(10),미납할부원금(11),미납할부원금(12),미납할부이자(1),미납할부이자(2),미납할부이자(3),미납할부이자(4),미납할부이자(5),미납할부이자(6),미납할부이자(7),미납할부이자(8),미납할부이자(9),미납할부이자(10),미납할부이자(11),미납할부이자(12),미납가산금(1),미납가산금(2),미납가산금(3),미납가산금(4),미납가산금(5),미납가산금(6),미납가산금(7),미납가산금(8),미납가산금(9),미납가산금(10),미납가산금(11),미납가산금(12)", //90
			columnIds : "dutySctn,cmpnSctn,insrBillReqNo,errResultCd,grntInsrMngmNo,instYearNum,duedatDate1,duedatDate2,duedatDate3,duedatDate4,duedatDate5,duedatDate6,duedatDate7,duedatDate8,duedatDate9,duedatDate10,duedatDate11,duedatDate12,instAmt1,instAmt2,instAmt3,instAmt4,instAmt5,instAmt6,instAmt7,instAmt8,instAmt9,instAmt10,instAmt11,instAmt12,insrIntAmt1,insrIntAmt2,insrIntAmt3,insrIntAmt4,insrIntAmt5,insrIntAmt6,insrIntAmt7,insrIntAmt8,insrIntAmt9,insrIntAmt10,insrIntAmt11,insrIntAmt12,addAmt1,addAmt2,addAmt3,addAmt4,addAmt5,addAmt6,addAmt7,addAmt8,addAmt9,addAmt10,addAmt11,addAmt12,unpayInstAmt1,unpayInstAmt2,unpayInstAmt3,unpayInstAmt4,unpayInstAmt5,unpayInstAmt6,unpayInstAmt7,unpayInstAmt8,unpayInstAmt9,unpayInstAmt10,unpayInstAmt11,unpayInstAmt12,unpayInstIntAmt1,unpayInstIntAmt2,unpayInstIntAmt3,unpayInstIntAmt4,unpayInstIntAmt5,unpayInstIntAmt6,unpayInstIntAmt7,unpayInstIntAmt8,unpayInstIntAmt9,unpayInstIntAmt10,unpayInstIntAmt11,unpayInstIntAmt12,unpayInstAddAmt1,unpayInstAddAmt2,unpayInstAddAmt3,unpayInstAddAmt4,unpayInstAddAmt5,unpayInstAddAmt6,unpayInstAddAmt7,unpayInstAddAmt8,unpayInstAddAmt9,unpayInstAddAmt10,unpayInstAddAmt11,unpayInstAddAmt12",
			widths : "140,80,120,90,110,80,80,80,80,80,80,80,80,80,80,80,80,80,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90,80,80,80,80,80,80,80,80,80,80,80,80,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,100,100,100,100,100,100,100,100,100,100,100,100",
			colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right",
			colTypes : "ro,ro,ro,ro,ro,ro,roXd,roXd,roXd,roXd,roXd,roXd,roXd,roXd,roXd,roXd,roXd,roXd,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize:20,
			buttons : {
                hright : {
                     btnDnExcel : { label : "엑셀다운로드" }
                }
           },
   			onButtonClick : function(btnId, selectedData) {
  				var form = this;
  				switch (btnId) {
                  case "btnDnExcel":
                	  
                	  if(PAGE.GRID1.getRowsNum()== 0)
                	  {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
                	  }
                	  else {
                		  mvno.cmn.download('/rcp/sgimgmt/getSgiDmndDtlListEx.json?baseDate=' + PAGE.FORM1.getItemValue("baseDate",true) + "&menuId=MSP1040003",true);
                	  }
	                      break;   
                	  }
  				}
   			}
       };
			
var PAGE = {
    title: "${breadCrumb.title}",
    breadcrumb: "${breadCrumb.breadCrumb}",

    buttonAuth: ${buttonAuth.jsonString},
    
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");

	}

};

</script>