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
			title : ""
			,items : [ 
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}
						,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자", labelWidth:50, value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""} 
						,{type : "newcolumn", offset:3}
						,{type:"block", list:[
												{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
												,{type: "label", label: "~"},
						                   	 ]}
						,{type : "newcolumn", offset:3}
						,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""}
						,{type : "newcolumn", offset:10} 
						,{type : "select",label : "처리구분",name : "resStatus", width:90}
						,{type : "newcolumn", offset:10}
						,{type : "select",label : "처리상태",name : "searchStatus", width:90}
						,{type : "newcolumn", offset:15}	
						,{type : "select",label:"검색",name : "searchType",width:90,labelWidth:40}	
						,{type : "newcolumn", offset:3}
						,{type : "input",name : "searchNm", width:100,maxlength:20}
						,{type:"newcolumn", offset:20}
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
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/CnslDtlsMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;

				}

			}
			,onValidateMore : function(data){

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
			},
			title : "조회결과"
			,header : "계약번호,상담고객명,요청구분,처리구분,요청일자,요청내용,처리상태,처리내용,접수자,처리자,처리일자"
			,columnIds : "contractNum,reqUserName,reqTypeNm,resStatusNm,regDate,reqBody,statusNm,resBody,regAdminNm,resAdminNm,resEndDate"
			,widths : " 80,150,80,80,120,350,80,350,100,100,70"
			,colAlign : "center,left,center,center,center,left,center,left,left,left,center"
			,colTypes : "link,ro,ro,ro,roXd,ro,ro,ro,ro,ro,roXd"
			,colSorting : "str,str,str,str,str,str,str,str,str,str,str"
			,paging : true
			,pageSize :15
			,pagingStyle :2
			,buttons : {
				
				hright : {
					btnDnExcel : { label : "엑셀다운로드" } 
				}
			}
			,onButtonClick : function(btnId) {

				switch (btnId) {

				case "btnDnExcel":
					var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
					if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
					
					var url = "/pps/hdofc/custmgmt/CnslDtlsMgmtListExcel.json";
					 var searchData =  PAGE.FORM1.getFormData(true);

					 console.log("searchData",searchData);
					
				 	 mvno.cmn.download(url+"?menuId=PPS1100002",true,{postData:searchData});
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
			setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json", {searchLovCd :"PPS0045"}, PAGE.FORM1, "resStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json", {searchLovCd :"PPS0046"}, PAGE.FORM1, "searchStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0047"}, PAGE.FORM1, "searchType");
			

		}

	};

</script>