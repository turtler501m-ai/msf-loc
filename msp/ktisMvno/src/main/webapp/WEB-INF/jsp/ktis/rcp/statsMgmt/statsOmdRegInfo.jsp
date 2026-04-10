<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : statsOmdRegInfo.jsp
	 * @Description : OMD단말 등록 현환
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2021.11.16 권오승 최초생성
	 * @Create Date : 2021.11.16.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div><!-- 메인 -->
 <!-- Script -->
<script type="text/javascript">
	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
				 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
				 , {type:"block", list:[
					 {type : "calendar",width : 100,label : "신청일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "srchStrtDt", offsetLeft:10, readonly:true},
						{type : "newcolumn"},
						{type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
						, {type: 'newcolumn'}
						,{type: 'select', name: 'successYn', label: '처리결과', width:100, offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"Y", text:"성공"}, {value:"N", text:"실패"}]}
						, {type: 'newcolumn'}
						, {type : "select",width : 100 , label : "검색구분",name : "searchGbn" ,  offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"IMEI"}, {value:"2", text:"EID"}]}
						, {type : "newcolumn"}
						, {type : "input",width : 100,name : "searchName", disabled:true}
					]},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
				],
				onValidateMore : function(data) {
					if (data.srchStrtDt > data.srchEndDt) {
						this.pushError("srchStrtDt", "신청일자", "시작일자가 종료일자보다 클 수 없습니다.");
					}
					if(data.srchStrtDt != "" && data.srchStrtDt == ""){
						this.pushError("srchStrtDt", "신청일자", "조회기간을 입력하세요.");
					}
					if(data.srchEndDt != "" && data.srchEndDt == ""){
						this.pushError("srchEndDt", "신청일자", "조회기간을 입력하세요.");
					}
					if( data.searchGbn != "" && data.searchName.trim() == ""){
						this.pushError("searchName", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
					}
					
				},
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
								PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getOmdRegList.json", form);
								break;
					}
								
				}, 
				onChange : function(name, value) {
					var form = this;
					
					// 검색구분
					if(name == "searchGbn") {
						PAGE.FORM1.setItemValue("searchName", "");
						
						if(value == null || value == "") {

							var endDate = new Date().format('yyyymmdd');
							var time = new Date().getTime();
							time = time - 1000 * 3600 * 24 * 7;
							var startDate = new Date(time);
							
							PAGE.FORM1.enableItem("srchStrtDt");
							PAGE.FORM1.enableItem("srchEndDt");
							
							PAGE.FORM1.setItemValue("srchStrtDt", startDate);
							PAGE.FORM1.setItemValue("srchEndDt", endDate);
							
							PAGE.FORM1.disableItem("searchName");
						}
						else {
							PAGE.FORM1.setItemValue("srchStrtDt", "");
							PAGE.FORM1.setItemValue("srchEndDt", "");
							
							PAGE.FORM1.disableItem("srchStrtDt");
							PAGE.FORM1.disableItem("srchEndDt");
							
							PAGE.FORM1.enableItem("searchName");
						}
					}
				}
					
			
		},
		// ----------------------------------------
		//사용자리스트
		GRID1 : {
			css : {
					height : "590px"
			},
			title : "조회결과",
			header : "신청일자,모델명,이벤트종류,일련번호,결과코드,결과메시지,결과,IMEI1,IMEI2,EID",
			columnIds : "sysRdate,reqModelName,evntCd,reqPhoneSn,rsltCd,rsltMsg,rsltNm,imei1,imei2,eid",
			colAlign : "center,left,center,center,center,left,center,center,center,center",
			colTypes : "roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			widths : "130,100,80,120,80,200,60,120,120,240",
			//hiddenColumns:[0 , 1],
			//multiCheckbox : true,
			paging: true,
			pageSize:20,
			buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				var grid = this;
				switch (btnId) {
						case "btnExcel" :
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/stats/statsMgmt/getOmdRegListExcel.json?menuId=MSP1010042", true, {postData:searchData}); 
							
							break;	
				}
			}
		}
	};

	var PAGE = {
		 title : "${breadCrumb.title}",
		 breadcrumb : " ${breadCrumb.breadCrumb}",
		 buttonAuth: ${buttonAuth.jsonString},
		 onOpen : function() {
			 
			 mvno.ui.createForm("FORM1");
			 mvno.ui.createGrid("GRID1");
			 
			 
			var endDate = new Date().format('yyyymmdd');
			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 7;
			var startDate = new Date(time);

			PAGE.FORM1.setItemValue("srchStrtDt", startDate);
			PAGE.FORM1.setItemValue("srchEndDt", endDate);

		 }
	};

</script>