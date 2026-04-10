<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1045.jsp
	 * @Description : 청구/수납 조회 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.22 장익준 최초생성
	 * @
	 * @author : 장익준
	 * @Create Date : 2014. 8. 22.
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
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},

				{type:"block", labelWidth:30 , list:[ 
				{type: 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'billYm', enableDay:false, label: '기준월', value:"${currentMonth}", calendarPosition: 'bottom', readonly:true ,width:80, offsetLeft:10}]},
				{type:"newcolumn", offset:20},
				
				{type:"block", labelWidth:90 , list:[ 
//                 {type : "select",label : "유형",name : "pymnCd", userdata: { lov: 'ORG0045'}, width:80,required:true },
                {type : "select",label : "유형",name : "pymnCd", width:80,required:true },
				{type:"newcolumn", offset:20},
// 				{type : "input",label : "판매회사코드", width:80,name : "slsCmpnCd", maxLength:10}, 
// 				{type:"newcolumn", offset:20},
// 				{type : "input",label : "서비스계약번호", width:80,name : "svcCntrNo", maxLength:10}, 
				{type : "input",label : "계약번호", width:100,name : "contractNum", maxLength:10, required:true}, 
				{type:"newcolumn", offset:20},
				{type : "input",label : "청구계정번호", width:100,name : "billAcntNo", maxLength:11}, 
				{type:"newcolumn", offset:87},
				]},
				{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 

				],
	
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/org/rqstmgmt/rqstMgmtList.json", form);
						break;
					}
				}
			},

		//-------------------------------------------------------------
           GRID1 : {
			css : {
				height : "600px"
			},
			title : "조회결과",
			header : "청구월,계약번호,서비스계약번호,청구계정번호,청구항목코드,청구항목코드명,청구반영구분명,수납코드명,수납대리점ID,실수납일자,적용일자,수납방법코드,수납방법명,수납세부방법명,납기일자,청구수납금액,부가세,조정사유코드,조정사유명",
			columnIds : "billYm,contractNum,svcCntrNo,billAcntNo,billItemCd,itemCdNm,billRflcIndNm,pymnNm,pymnAgncId,blpymDate,aplyDate,pymnMthdCd,pymnMthdNm,pymnDtlMthdNm,duedatDate,pymnAmnt,vatAmnt,adjsRsnCd,adjsRsnNm",
			widths : "80,100,100,100,80,150,120,80,100,100,100,100,150,150,100,100,100,100,100",
			colAlign : "center,center,center,center,center,left,left,center,center,center,center,center,left,left,center,right,right,center,left",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,ro,ro,ro,roXd,ron,ron,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
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
                	  else
                	  {
                		  
	                	  if(mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("contractNum")))
	                	  {
	                		  if(mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("billAcntNo")))
	                		  {
	                			  mvno.ui.alert("서비스계약번호,청구계정번호 항목을 넣어주세요.");
	                		  }
	                		  else
	                		  {
	                			  
	// 								mvno.ui.confirm("CCMN0013", function(){
	// 									mvno.cmn.ajax(ROOT + "/org/rqstmgmt/excelDownProc1.json", PAGE.FORM1, function(result) {
	// 										mvno.ui.notify("NCMN0008");
	// 									});
	// 								});
	
	// 								var thisMonth = PAGE.FORM1.getItemValue("billYm").getFullYear()+""+fnMonth(PAGE.FORM1.getItemValue("billYm").getMonth());
// 	                			    window.open('/org/rqstmgmt/excelDownProc1.json?billYm=' + PAGE.FORM1.getItemValue("billYm",true) + "&svcCntrNo=" + PAGE.FORM1.getItemValue("svcCntrNo") + "&billAcntNo=" + PAGE.FORM1.getItemValue("billAcntNo"));
	                			    mvno.cmn.download('/org/rqstmgmt/excelDownProc1.json?billYm=' + PAGE.FORM1.getItemValue("billYm",true) + "&pymnCd=" + PAGE.FORM1.getItemValue("pymnCd") + "&contractNum=" + PAGE.FORM1.getItemValue("contractNum") + "&billAcntNo=" + PAGE.FORM1.getItemValue("billAcntNo")+"&menuId=MSP1000005",true);
									
	                		  }
	                	  }
	                	  else
	                	  {
	// 							mvno.ui.confirm("CCMN0013", function(){
	// 								mvno.cmn.ajax(ROOT + "/org/rqstmgmt/excelDownProc1.json", PAGE.FORM1, function(result) {
	// 									mvno.ui.notify("NCMN0008");
	// 								});
	// 							});
	//               			  _d_('/org/rqstmgmt/excelDownProc1.json');
	
// 	                		  window.open('/org/rqstmgmt/excelDownProc1.json?billYm=' + PAGE.FORM1.getItemValue("billYm",true) + "&svcCntrNo=" + PAGE.FORM1.getItemValue("svcCntrNo") + "&billAcntNo=" + PAGE.FORM1.getItemValue("billAcntNo"));
                              mvno.cmn.download('/org/rqstmgmt/excelDownProc1.json?billYm=' + PAGE.FORM1.getItemValue("billYm",true) + "&pymnCd=" + PAGE.FORM1.getItemValue("pymnCd") + "&contractNum=" + PAGE.FORM1.getItemValue("contractNum") + "&billAcntNo=" + PAGE.FORM1.getItemValue("billAcntNo")+"&menuId=MSP1000005",true);

	                	  }
	                      break;   
                	  }
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

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1001",orderBy:"etc6"}, PAGE.FORM1, "pymnCd"); // 유형

	}

};

</script>