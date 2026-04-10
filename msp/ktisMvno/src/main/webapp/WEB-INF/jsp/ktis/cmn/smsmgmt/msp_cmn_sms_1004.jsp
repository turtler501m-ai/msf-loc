<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_cmn_sms_1004.jsp
 * @Description : 전화번호그룹관리
 */
%>

<!-- 화면 배치 -->
<div class="row">
	<div id="FORM1" class="c5-5" >
		<div id="GRID1" class="section-half"></div>
	</div>
	<div id="FORM2" class="c4-5" >
		<div id="GRID2" class="section-half"></div>
	</div>
</div>
<div id="FORM3" class="section-box"></div>
<div id="FORM4" class="section-box"></div>
<div id="FORM5" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

    var DHX = {

		GRID1 : {
			css : {
				height : "590px"
			},

			title : "전화번호그룹",
			header : "그룹코드,그룹명,시작일자,종료일자,사용여부",
			columnIds : "grpId,grpNm,strtDt,endDt,useYn",
			colAlign : "left,left,center,center,center",
			widths : "110,120,100,100,72",
			colTypes : "ro,ro,roXd,roXd,ro",
			colSorting : "str,str,str,str,str",
			paging : true,
			pagingStyle : 1,
	        pageSize : 20,
	        showPagingAreaOnInit : true,

			buttons : {
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},

			//-----------------------------------------------
			// click list -> show detail
			//-----------------------------------------------
			onRowSelect : function(rowId, celInd) {

				var selectedData = PAGE.GRID1.getSelectedRowData();
				var url = ROOT + "/cmn/smsmgmt/smsSendPhoneGrpDtl.json";
	            PAGE.GRID2.list(url, selectedData);
			},

			checkSelectedButtons : ["btnMod"],
			onButtonClick : function(btnId, selectedData) {

				switch (btnId) {
					case "btnReg":
						mvno.ui.createForm("FORM3");
						PAGE.FORM3.clear();
						
						var today = new Date().format("yyyymmdd");
						var endDt = mvno.cmn.getAddDay(today, 30);

						PAGE.FORM3.setItemValue("strtDt", today);						
						PAGE.FORM3.setItemValue("endDt", endDt);
						
						mvno.ui.popupWindowById("FORM3", "전화번호그룹", 660, 230, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});

					break;
					
					case "btnMod":

						mvno.ui.createForm("FORM4");
						PAGE.FORM4.clear();
						PAGE.FORM4.disableItem("grpId");
						PAGE.FORM4.setFormData(selectedData);
						
						mvno.ui.popupWindowById("FORM4", "전화번호그룹", 660, 230, {
							onClose: function(win) {
								if (! PAGE.FORM4.isChanged()) return true;
								
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});

					break;
				}
			}
		},

		// 등록팝업
	 	FORM3 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:120, blockOffset:0}
				,{type:"block", list:[
					{type:"input", label:"그룹코드", width:120, name:"grpId", maxLength:10, required:true, validate: 'NotEmpty,ValidAplhaNumeric', offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"input", label:"그룹명", width:120, offsetLeft:40, name:"grpNm", required:true, maxLength:10}
				]}
				,{type:"block", blockOffset:0, offsetLeft:20, list: [
					{type:'calendar', name:'strtDt', label:'적용시작일자', dateFormat:'%Y-%m-%d', serverDateFormat:'%Y%m%d', value:'', calendarPosition:'right', width:120, required:true}
					,{type:"newcolumn"}
					,{type:'calendar', name:'endDt', label:'적용종료일자', dateFormat:'%Y-%m-%d', serverDateFormat:'%Y%m%d', offsetLeft:40, value:'', calendarPosition:'right', width:120, required:true}
				]}
				,{type:"block", offsetLeft:20, list:[
					{type:"select", width:80, label:"사용여부", name:"useYn"
						, options:[{value:"Y", text:"사용", selected:true},{value:"N", text:"미사용"}]}
				]}
			],

			buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label: "닫기" }
                }
			},

			onKeyUp : function(itemId,key) {
				var form = this;
				switch (itemId.name) {

					case "grpId":

						form.setItemValue(itemId.name, form.getItemValue(itemId.name).replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
						form.setItemValue(itemId.name, form.getItemValue(itemId.name).toUpperCase() );

						break;
				}

			},

			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {
					case "btnSave":// 등록 처리
                        mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/insertSmsSendPhoneGrp.json", form, function(result) {
                            mvno.ui.closeWindowById(form, true);
                            mvno.ui.notify("NCMN0001");
                            PAGE.GRID1.refresh();
                        });

						break;

					case "btnClose":
						mvno.ui.closeWindowById(form);
						break;
				}
			},

			onValidateMore : function (data) {
				if (this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)) {
					this.pushError("strtDt","적용일자","종료일이 시작일보다 클수 없습니다.");
					this.resetValidateCss();
				}
			}
		},

		// 수정팝업
	 	FORM4 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:120, blockOffset:0}
				,{type:"block", list:[
					{type:"input", label:"그룹코드", width:120, name:"grpId", maxLength:10, required:true, validate: 'NotEmpty,ValidAplhaNumeric', offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"input", label:"그룹명", width:120, offsetLeft:40, name:"grpNm", required:true, maxLength:10}
				]}
				,{type:"block", blockOffset:0, offsetLeft:20, list: [
					{type:'calendar', name:'strtDt', label:'적용시작일자', dateFormat:'%Y-%m-%d', serverDateFormat:'%Y%m%d', value:'', calendarPosition:'right', width:120, required:true}
					,{type:"newcolumn"}
					,{type:'calendar', name:'endDt', label:'적용종료일자', dateFormat:'%Y-%m-%d', serverDateFormat:'%Y%m%d', offsetLeft:40, value:'', calendarPosition:'right', width:120, required:true}
				]}
				,{type:"block", offsetLeft:20, list:[
					{type:"select", width:80, label:"사용여부", name:"useYn"
						, options:[{value:"Y", text:"사용", selected:true},{value:"N", text:"미사용"}]}
				]}
			],

			buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label: "닫기" }
                }
			},

			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {
					case "btnSave": // 수정 처리
						mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/updateSmsSendPhoneGrp.json", form, function(result) {
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0002");
							PAGE.GRID1.refresh();
						});

						break;

					case "btnClose":

						mvno.ui.closeWindowById(form);
						break;
				}
			},

			onValidateMore : function (data) {
				if (this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)) {
					this.pushError("strtDt","적용일자","종료일이 시작일보다 클수 없습니다.");
					this.resetValidateCss();
				}
			}
		},
		//---------------------------------------
		GRID2 : {
			css : {
				height : "590px",
				top : 0,
				bottom : 0
			},

			title : "그룹상세",
			header : "그룹코드,사용자ID,사용자명,전화번호,usrId,usrNm,mblphnNum",
			columnIds : "grpId,usrIdMsk,usrNmMsk,mblphnNumMsk,usrId,usrNm,mblphnNum",
			colAlign : "left,left,left,left,left,left,left",
			widths : "110,95,95,105,0,0,0",
			colTypes : "ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str",
			hiddenColumns:[4,5,6],
			paging : true,
			pagingStyle : 1,
			pageSize : 20,
			showPagingAreaOnInit : true,
			buttons : {
				right : {
					btnReg2 : { label : "기타등록" },
					btnReg : { label : "등록" },
					btnDel : { label : "삭제" }
				}
			},

			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {
					case "btnReg2":
						if ( PAGE.GRID1.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0107");  // 그룹을 먼저 선택하세요
							return;
						}
						mvno.ui.createForm("FORM5");
						PAGE.FORM5.clear();
						PAGE.FORM5.setItemValue("grpId",PAGE.GRID1.getSelectedRowData().grpId);
						PAGE.FORM5.setItemValue("grpNm",PAGE.GRID1.getSelectedRowData().grpNm);
						PAGE.FORM5.setItemValue("etc","Y");
						
						mvno.ui.popupWindowById("FORM5", "기타등록", 660, 210, {
							onClose: function(win) {
								if (! PAGE.FORM5.isChanged()) return true;
								
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
	
					break;
				
					case "btnReg":
						if ( PAGE.GRID1.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0107");  // 그룹을 먼저 선택하세요
							return;
						}
						// 등록을 누르면 사용자 찾는 팝업으로 연결.
						// 검색 결과가 들어와야 함. 팝업창에서 확인이 작동 안함.
						mvno.ui.popupWindow(ROOT+"/org/userinfomgmt/searchUserInfo.do", "사용자찾기", 860, 620, {
							param : {

                            callback : function(result) {
															mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/insertSmsSendPhoneGrpDtl.json", {"grpId":PAGE.GRID1.getSelectedRowData().grpId, "usrId": result.usrId } , function(result) {
																mvno.ui.notify("NCMN0001");
																PAGE.GRID2.refresh();
															});
														}
				                     }
						});
                        break;

					case "btnDel":
						if ( PAGE.GRID2.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0107");  // 그룹을 먼저 선택하세요
							return;
						}
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/smsmgmt/deleteSmsSendPhoneGrpDtl.json"
								, PAGE.GRID2.getSelectedRowData() , function(result) {
							mvno.ui.notify("NCMN0003");
							PAGE.GRID2.refresh();
						});
						break;

				}

			}
		},

		// 기타등록팝업
	 	FORM5 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:120, blockOffset:0}
				,{type:"block", list:[
					{type:"input", label:"그룹코드", width:120, name:"grpId", required:true, disabled:true, offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"input", label:"그룹명", width:120, offsetLeft:40, name:"grpNm", required:true, disabled:true}
				]}
				,{type:"block", list:[
					{type:"input", label:"사용자명", width:120, name:"usrNm", maxLength:10, required:true, offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"input", label:"전화번호", width:120, offsetLeft:40, name:"mblphnNum", required:true, maxLength:11, validate:'ValidNumeric'}
				]}
				,{type:"hidden", name:"etc"}
			],

			buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label: "닫기" }
                }
			},

			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {
					case "btnSave":// 등록 처리
						mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/insertSmsSendPhoneGrpDtl.json", form, function(result) {
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID2.refresh();
						});
						break;

					case "btnClose":
						mvno.ui.closeWindowById(form);
						break;
				}
			}
		},
	};

	var PAGE = {

		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",

		buttonAuth: ${buttonAuth.jsonString},

	    onOpen : function() {
	        mvno.ui.createGrid("GRID1");
	        mvno.ui.createGrid("GRID2");

	        var url1 = ROOT + "/cmn/smsmgmt/smsSendPhoneGrpList.json";
	        // 임시로 막음. 테스트 중
            PAGE.GRID1.list(url1, null,  {  callback: gridOnLoad });
//	        PAGE.GRID1.list("/cmn/authmgmt/usrGrpList.json", url1);


	    }
	};

	function gridOnLoad  ()
	{
		PAGE.GRID1.forEachRow(function(id){
			if (PAGE.GRID1.cellById(id,3).getValue()=="N")
			{
				PAGE.GRID1.setRowTextStyle(id,"color: red");
//				PAGE.GRID1.setCellTextStyle(id,3,"color: red");
			}
		});
	};

</script>


