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
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "발송일자", labelWidth:50, value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:5},  
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", label : "~", labelWidth:10, value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:50},
						 {type : "select",label : "검색 ",name : "searchType",width:100,labelWidth:30},
						 {type : "newcolumn", offset:5},
						 {type: "input", label: ":", labelWidth: 5, labelAlign: "left", name: "searchNm", width: 100,maxlength:20},
						 {type:"newcolumn", offset:20},
						 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : "button",name : "btnSearch",value : "조회",className:"btn-search1"} 
					]
			,onButtonClick : function(btnId) {
	
					var form = this;
	
					switch (btnId) {
					
						case "btnSearch":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/CustSmsDtlsMgmtOld.json", form, {
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
			,header : "계약번호,발신번호,착신번호,전송결과,결과메세지,전송일자,전송내용"
			,columnIds : "contractNum,callingNumber,calledNumber,smsResultCode,smsResultNm,smsSendDate,smsMsg"
			,widths    :  "80,100,100,100,100,120,350"
			,colAlign : "center,center,left,center,left,center,left"
			,colTypes : "link,roXp,roXp,ro,ro,roXd,ro"
			,colSorting : "str,str,str,str,str,str,str"
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
						var url = "/pps/hdofc/custmgmt/CustSmsDtlsMgmtExcelOld.json";
						 var searchData =  PAGE.FORM1.getFormData(true);

						 console.log("searchData",searchData);
						
					  	mvno.cmn.download(url+"?menuId=PPS1100003",true,{postData:searchData});
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

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0018"}, PAGE.FORM1, "searchType");
		}
	};
		
</script>