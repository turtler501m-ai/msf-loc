<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
	
<!-- Script -->
<script type="text/javascript">

	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}

	function setCurrentDate(form, name)
	{
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
	}

	function goCustInfoData(contractNum)
	{
		var url = "/pps/hdofc/custmgmt/HdofcCustMgmtInfoDetailForm.do";
		var title = "고객정보상세"
		var menuId = "PPS1100007";

		var myTabbar = window.parent.mainTabbar;

		if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&contractNum="+contractNum);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);
	}

	var DHX = {
	
		FORM1 : {
			items : [ 
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}
					 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "사용일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""} 
					 ,{type : "newcolumn"}
					 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""} 
					 ,{type : "newcolumn", offset:30} 
					 ,{type : "select",label : "구분",name : "searchCallGubun", labelWidth:30, width: 100}
					 ,{type : "newcolumn", offset:30}
					 ,{type:"block", list:[
										{type : "select",label : "검색 ",name : "searchType", labelWidth:30, width: 100},
										,{type : "newcolumn",offset:3},
										,{type: "input", label: ":", labelWidth: 5, labelAlign: "left", name: "searchNm", width: 100,maxlength:20}						 
					 ]}
					 ,{type:"newcolumn", offset:30}
					 ,{type : 'hidden', name: "btnCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
					 ,{type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
					,{type : "button",name : "btnSearch",value : "조회",className:"btn-search1"} 
			]
			,onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
				
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/PrepaidCdrDailyMgmt.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
				}
	
			}
			,onValidateMore : function(data){

				if(data.startDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "사용일자", "사용일자을 입력해주세요.");
				}
				
				if(data.endDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "사용일자", "사용일자을 입력해주세요.");
				}
				
				if(data.startDt > data.endDt)
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
				}
				
				 if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("searchType", "검색조건", "을 선택해주세요");							
				}

				 if(data.searchType=="contract_num" )
				 {
					
					  if(data.searchNm.match(/^\d+$/ig) == null){
						  PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						  PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						  this.pushError("searchNm","계약번호","숫자만 입력하세요");
					   }
							
				  }

				if(data.searchType=="subscriber_no")
				{
					if(data.searchNm.match(/^\d+$/ig) == null)
					{
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						this.pushError("searchNm","CTN","숫자만 입력하세요");
					}
				}
					
			}
		}
		,GRID1 : {
	
			css : {
				height : "550px"
			}
			,title : "조회결과"
			,header : "통화구분,계약번호,사용일자,시작시간,종료시간,누적사용량(K),과금액,기존잔액,데이터잔량,데이터만료일,소속대리점"
			,columnIds : "callGubunNm,contractNum,accessDt,startTime,endTime,pktkbyte,charge,remains,dataRemains,dataExpire,agentNm"
			,widths : "80,80,100,80,80,80,70,70,100,80,130"
			,colAlign : "center,center,center,center,center,right,right,right,right,center,left"
			,colTypes : "ro,link,ro,ro,ro,ron,ron,ron,ron,ro,ro"
			,colSorting : "str,str,str,str,str,int,int,int,int,str,str"
			,paging : true
			,pageSize :15
			,pagingStyle :2
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }, 
				
				}
			}
			,onButtonClick : function(btnId) {
	
				switch (btnId) {
					case "btnDnExcel":
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						var totCnt = PAGE.GRID1.getRowsNum();
						if(totCnt <= 0){
							mvno.ui.notify("출력건수가 없습니다.");
						   return;
						}
						//alert(totCnt);
						if(totCnt>5000)
						{
							mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
						   
						}
						var url = "/pps/hdofc/custmgmt/PrepaidCdrDailyMgmtExcel.json";
						var searchData =  PAGE.FORM1.getFormData(true);
	
						 console.log("searchData",searchData);
						
					  	mvno.cmn.download(url+"?menuId=PPS1100006",true,{postData:searchData});
						break;
				}
			}
		}
					
	};

	var PAGE = {

		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth: ${buttonAuth.jsonString},
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0051"}, PAGE.FORM1, "searchCallGubun");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0018"}, PAGE.FORM1, "searchType");

		}
	};
		
</script>