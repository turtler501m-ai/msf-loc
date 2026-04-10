<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
<div id="FORM2" class="section-box"></div>  
	
<!-- Script -->
<script type="text/javascript">
	
	var DHX = {
		
		FORM1:{
			title:""
			,items:[ 
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
					//,{type:"calendar",dateFormat:"%Y-%m-%d",serverDateFormat:"%Y%m%d",name:"startDt",label:"등록일자", labelWidth:50, value:"",enableTime:false,calendarPosition:"bottom",inputWidth:100,value:"", offsetLeft:10} 
					//,{type:"newcolumn", offset:3}
					//,{type:"label", label:"~"},
					//,{type:"newcolumn", offset:3}
					//,{type:"calendar",dateFormat:"%Y-%m-%d",serverDateFormat:"%Y%m%d",name:"endDt", value:"",enableTime:false,	calendarPosition:"right",inputWidth:100,value:""}
					//,{type:"newcolumn", offset:10} 
					,{type:"select", label:"서비스유형", name:"serviceType", userdata:{lov:"RCP0002"}}
					,{type:"newcolumn"}
					,{type:"select",label:"검색구분", name:"searchCd", options:[{value:"", text:"-전체-"}, {value:"10", text:"휴대폰번호", selected:true},{value:"20", text:"고객명"}], offsetLeft:20}
					,{type:"newcolumn"}	
					,{type:"input", name:"searchVal", width:100}
					,{type:"newcolumn"}
					,{type:"select",label:"계약상태", name:"subStatus", userdata:{lov:"RCP0020"}, offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"checkbox", label:"미동의", labelWidth:45, name:"trnsAgrmYn", value:"N", offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"button",name:"btnSearch",value:"조회",className:"btn-search1"} 
			]
			,onButtonClick:function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/custTrnsMgmt/getCustTrnsListAjax.json", form);
						break;

				}

			}
			,onValidateMore:function(data){

				//if(data.startDt > data.endDt){
					//this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
				//}
				
				if(data.searchCd != "" && data.searchVal == ""){
					this.pushError("searchVal", "검색어", "검색어를 입력하세요");
				}else{
					if(data.searchCd == "10" && data.searchVal.match(/^\d+$/ig) == null){
						this.pushError("searchVal", "휴대폰번호", "숫자만 입력하세요");
					}
				}
			}
		}
		,GRID1:{

			css:{
				height:"590px"
			},
			title:"조회결과"
			,header:"계약번호,고객명,생년월일,성별,서비스유형,휴대폰번호,계약상태,동의여부,등록일시,구매유형,약정개월수,할부개월수,확인자명"
			,columnIds:"contractNum,custNm,ssn1,gender,serviceTypeNm,svcTelNo,subStatusNm,trnsAgrmNm,trnsAgrmDttm,buyTypeNm,enggMnthCnt,modelMonthly,cnslNm"
			,widths:"100,150,100,80,100,120,100,80,120,80,80,80,100"
			,colAlign:"center,left,center,center,center,center,center,center,center,center,right,right,center"
			,colTypes:"ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			,colSorting:"str,str,str,str,str,str,str,str,str,str,str,str,str"
			,paging:true
			,pageSize:20
			,buttons:{
				hright:{
					btnDnExcel:{ label:"엑셀다운로드" } 
				}
			}
			,onButtonClick:function(btnId) {
				var form = this;
				
				switch (btnId) {

					case "btnDnExcel":
						//console.log(PAGE.GRID1.getRowsNum());
						
						var recordCnt = PAGE.GRID1.getRowsNum();
						if(recordCnt == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						}else{
							if(recordCnt >= 10000){
								mvno.ui.alert("10000건 이상 다운로드인 경우 운영팀에 요청하세요.");
								return;
							}
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/rcp/custTrnsMgmt/getCustTrnsListEx.json?menuId=MSP3010000", true, {postData:searchData});
						}
						
						break;
				}
			}
			,onRowDblClicked:function (rowId, celInd) {
				
				var form = this;
				selectedData = this.getSelectedRowData();
	
				mvno.ui.createForm("FORM2");
				PAGE.FORM2.setFormData(selectedData);
				
				mvno.ui.popupWindowById("FORM2", "고객미동의등록", 540, 340, {
					onClose:function (win) {
						if (!PAGE.FORM2.clearChanged())
							return true
							mvno.ui.confirm("CCMN0005", function () {
								win.closeForce();
							});
					}
				});
	
			}
		},
		FORM2:{
			items:[
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
				, {type:"block", list:[
             			{type:"input", name:"contractNum", label:"계약번호", offsetLeft:10, readonly:true}
             			, {type:"newcolumn"}
             			, {type:"input", name:"serviceTypeNm", label:"서비스구분", offsetLeft:20, readonly:true}
           		]}
				, {type:"block", list:[
             			{type:"input", name:"custNm", label:"고객명", offsetLeft:10, readonly:true}
             			, {type:"newcolumn"}
             			, {type:"calendar", name:"openDt", label:"개통일", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:20, disabled:true}
             			//, {type:'calendar', name: 'applStrtDt', label: '적용시작일자', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', calendarPosition: 'bottom', width:100, readonly:true, required:true, offsetLeft:10}
           		]}
				, {type:"block", list:[
             			{type:"input", name:"buyTypeNm", label:"구매유형", offsetLeft:10, readonly:true}
             			, {type:"newcolumn"}
             			, {type:"input", name:"svcTelNo", label:"휴대폰번호", offsetLeft:20, readonly:true}
           		]}
				, {type:"block", list:[
             			{type:"input", name:"enggMnthCnt", label:"약정개월수", offsetLeft:10, readonly:true}
             			, {type:"newcolumn"}
             			, {type:"input", name:"modelMonthly", label:"할부개월수", offsetLeft:20, readonly:true}
           		]}
				, {type:"block", list:[
             			{type:"checkbox", name:"trnsAgrmYn", label:"미동의", value:"N", offsetLeft:10}
            			, {type:"newcolumn"}
            			, {type:"select", name:"procMthdCd", label:"처리방법", offsetLeft:132, width:134, options:[{value:"", text:"-선택-", selected:true}, {value:"10", text:"해지"}, {value:"20", text:"(타사)번호이동"}]}
           		]}
				,{type:"input", name:"trnsMemo", label:"미동의사유", offsetLeft:10, width:357, rows:3, maxLength:150}
			],
			buttons:{
				center:{
					btnSave:{label:"저장"}
					, btnClose:{label:"닫기"}
				}
			},
			onValidateMore:function (data){
				if(data.trnsAgrmYn != "N"){
					this.pushError("trnsAgrmYn", "미동의", "미동의를 선택하세요");
				}
				if(data.procMthdCd == ""){
					this.pushError("procMthdCd", "처리방법", "미동의 처리방법을 선택하세요");
				}
				if(data.trnsMemo == ""){
					this.pushError("trnsMemo", "미동의사유", "미동의사유를 입력하세요");
				}
			},
			onButtonClick:function (btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnSave":
						
						mvno.cmn.ajax(ROOT + "/rcp/custTrnsMgmt/setCustTrnsAgrmAjax.json", form, function(result) {
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
						});
						
						break;
						
					case "btnClose":
						mvno.ui.closeWindowById(PAGE.FORM2, true); // id 대신 form 도 가능
						break;
				}
			}
		}

	};

	var PAGE = {
		title:"사업이관고객동의",
		breadcrumb:"Home > 사업이관고객동의",
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
		}
	};

</script>