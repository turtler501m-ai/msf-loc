<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1048.jsp
	 * @Description : 일반해지자관리
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
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},

				{type:"block", labelWidth:10, list:[ 
                {type: 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'searchStartDt', label: '검색월', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:80,offsetLeft:10},
				]},
				{type:"newcolumn", offset:20},
				{type:"block", labelWidth:75, list:[
				{type : "input",label : "사용자ID", width:80,name : "userid"}, 
				{type:"newcolumn", offset:20},
				{type : "input",label : "사용자명", width:80,name : "name"}, 
				{type:"newcolumn", offset:20},
				{type : "select",label : "LMS처리여부", width:80,name : "sendYn"}, 
				{type:"newcolumn", offset:20},
				{type : "select",label : "LMS처리결과", width:120,name : "sendRsn"}, 
				{type:"newcolumn", offset:20},
				]},

				{type:"newcolumn", offset:270},
				{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}  
				],
	
				onButtonClick : function(btnId) {
					var form = this;

					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/org/usercanmgmt/userCanMgmtList.json", form);
						break;
					}
				}
// 			     onValidateMore : function (data){
// 			         if((data.searchStartDt && !data.searchEndDt) || (!data.searchStartDt && data.searchEndDt)){
// 			             this.pushError("data.searchStartDt","검색일자","일자를 선택하세요");
// 			         }
// 			         else if(data.searchStartDt > data.searchEndDt){
			             
// 			             this.pushError("data.searchStartDt","검색일자",["ICMN0004"]);
// 			         }
// 			         else if(getDateDiff(data.searchStartDt, data.searchEndDt) > 30){
			        	 
// 			        	 this.pushError("data.searchStartDt","검색일자","한달이내로 선택하세요.");
// 			         }
// 			     }
			 },

			//-------------------------------------------------------------
           GRID1 : {
			css : {
				height : "600px"
			},
			title : "조회결과",
			header : "대상월,메일전송여부,LMS처리여부,LMS처리결과,사용자ID,사용자명,핸드폰번호,E-mail,가입일자,최종로그인일자,우편번호,주소,주소상세,수정자,수정일시",  //12
			columnIds : "regstDttm,resultYn,sendYn,sendRsn,userid,name,mobileNo,email,firstLoginDt,lastLoginDt,zipcd,address,addressDtl,rvisnNm,rvisnDttm",
			widths : "100,90,90,150,80,80,100,140,100,100,60,250,120,80,100",
			colAlign : "center,center,center,left,left,left,center,left,center,center,center,left,left,left,center",
			colTypes : "ro,ro,ro,ro,ro,ro,roXp,ro,roXd,roXd,ro,ro,ro,ro,roXd",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
// 			multiCheckbox: true,
			pageSize : 20,
			buttons : {
                hright : {
                     btnDnExcel : { label : "엑셀다운로드" }
                },
// 			    right : {
//                        btnReg : { label : "메일처리실행" },
//                        btnDel : { label : "메일미처리실행" }
//                    }
           },
   			onButtonClick : function(btnId, selectedData) {
  				var form = this;
  				switch (btnId) {
                  case "btnDnExcel":
                	  
                	  if(PAGE.GRID1.getRowsNum() == 0)
                	  {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
						
                	  }
                	  else
                	  {
                		  mvno.cmn.download('/org/usercanmgmt/userCanMgmtListEx.json?searchStartDt=' + PAGE.FORM1.getItemValue("searchStartDt",true) + "&searchEndDt=" + PAGE.FORM1.getItemValue("searchEndDt",true) + "&userid=" + PAGE.FORM1.getItemValue("userid",true) + "&name=" + PAGE.FORM1.getItemValue("name",true) + "&resultYn=" + PAGE.FORM1.getItemValue("resultYn",true)+"&menuId=MSP1030002",true);
                          break; 
                	  }
					case "btnReg" ://일괄저장
						var arrData = form.getCheckedRowData();
						
						if(arrData == null || arrData == '')
						{
							mvno.ui.alert("ECMN0003");//선택한(체크된) 데이터가 없습니다.
							return;
						}
						else
						{
							var sData = {
									 'userid'  : PAGE.FORM1.getItemValue("userid")
							};
							
							sData.items = arrData;
							
							mvno.ui.confirm("CCMN0015", function(){
								 mvno.cmn.ajax4json(ROOT + "/org/usercanmgmt/userCanMgmUpdateY.json", sData, function(result) {
									mvno.ui.notify("NCMN0001");
									PAGE.GRID1.refresh();
								});
							});
							break;
						}
					case "btnDel" ://일괄저장
						var arrData = form.getCheckedRowData();
						
						if(arrData == null || arrData == '')
						{
							mvno.ui.alert("ECMN0003");//선택한(체크된) 데이터가 없습니다.
							return;
						}
						else
						{
							var sData = {
									 'userid'  : PAGE.FORM1.getItemValue("userid")
							};
							
							sData.items = arrData;
							
							mvno.ui.confirm("CCMN0015", function(){
								 mvno.cmn.ajax4json(ROOT + "/org/usercanmgmt/userCanMgmUpdateN.json", sData, function(result) {
									mvno.ui.notify("NCMN0001");
									PAGE.GRID1.refresh();
								});
							});
							
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

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0237",orderBy:"etc6"}, PAGE.FORM1, "sendYn"); // 처리여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0236"}, PAGE.FORM1, "sendRsn"); // 처리결과
	   }

};

/**
 * 시작날짜, 끝날짜
 */
// function getDateDiff(date1, date2){
	
// 	var getDate1 = new Date(date1.substr(0,4), date1.substr(4,2)-1, date1.substr(6,2));
// 	var getDate2 = new Date(date2.substr(0,4), date2.substr(4,2)-1, date2.substr(6,2));
	
// 	var getDiffTime = getDate2.getTime() - getDate1.getTime();
	
// 	return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
// }
</script>