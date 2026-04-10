<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1030.jsp
	 * @Description : 사용자 그룹 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.09.01     임지혜           최초 생성
	 * @
	 * @author : 임지혜
	 * @Create Date : 2014. 9. 1.
	 */
%>


    <!-- 화면 배치 -->
    <div class="row">
   		<div id="FORM1" class="c5" >
   			<div id="GRID1" class="section-half"></div>
   		</div>
   		<div id="FORM2" class="c5" >
   			<div id="GRID2" class="section-half"></div>
  <!--  	<div id="FORM4" style="margin:0px"></div> -->
   		</div>
	</div>
	<div id="FORM3" class="section-box"></div>

    <!-- Script -->
    <script type="text/javascript">

    var DHX = {

		GRID1 : {
			css : {
				height : "590px"
			},

			title : "그룹",
			header : "그룹ID,그룹명,그룹개요,사용",
			columnIds : "GRP_ID,GRP_NM,GRP_DSC,USG_YN",
			colAlign : "left,left,left,center",
			widths : "100,150,173,32",
			colTypes : "ro,ro,ro,ro",
			colSorting : "str,str,str,str",
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
				//console.log("selectedData:" + selectedData);

				// 그룹 목록 선택 -> 그룹별 사용자
//				mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/usrGrpDetail.json",
//								selectedData,
//								function(result) {
//									console.log("result.result.data.age:" + result.result.data);
//									PAGE.FORM3.setFormData(result.result.data);
//								}
//							);

				var url = ROOT + "/cmn/authmgmt/usrGrpAsgnList.json";
	            PAGE.GRID2.list(url, selectedData);
			},

			checkSelectedButtons : ["btnMod"],
			onButtonClick : function(btnId, selectedData) {

                   switch (btnId) {

                       case "btnReg":
                       case "btnMod":

                           mvno.ui.createForm("FORM3");

                           PAGE.FORM3.setFormData(selectedData);

                           mvno.ui.popupWindowById("FORM3", "그룹", 550, 270, {
                               onClose: function(win) {
                                   if (! PAGE.FORM3.isChanged()) return true;
                                   mvno.ui.confirm("CCMN0005", function(){
                                       win.closeForce();
                                   });
                               }
                           });

                           break;
                   }
               }
		},

	 	FORM3 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}

				// 1
				,{type:"block", list:[
					{type:"input", label:"그룹ID", width:150, name:"GRP_ID", maxLength:10, required:true, validate: 'NotEmpty,ValidAplhaNumeric'}
					,{type:"newcolumn"}
					,{type:"input", label:"그룹명", width:150, offsetLeft:20, name:"GRP_NM"}
				]}

				// 2
				,{type:"block", list:[
					{type:"input", label:"그룹개요", width:375, name:"GRP_DSC"}
				]}

				// 3
				,{type:"block", list:[
					{type:"select", width:80, label:"사용여부", name:"USG_YN"
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

					case "GRP_ID":

						form.setItemValue(itemId.name, form.getItemValue(itemId.name).replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
						form.setItemValue(itemId.name, form.getItemValue(itemId.name).toUpperCase() );

						break;
				}

			},

			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
// 				alert(btnId);
				switch (btnId) {
					case "btnSave":

						if (form.isNew()) { // 등록 처리
                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/usrGrpInsert.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);
                                mvno.ui.notify("NCMN0001");
                                PAGE.GRID1.refresh();
                            });
                        }
                        else { // 수정 처리
                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/usrGrpUpdate.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);
                                mvno.ui.notify("NCMN0002");
                                PAGE.GRID1.refresh();
                            });

                        }
						break;

					case "btnClose":

						mvno.ui.closeWindowById(form);
						break;
				}
			}
		},

		FORM4 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}

				// 1
				,{type:"block", list:[
					{type:"input", label:"그룹ID", width:150, name:"GRP_ID", maxLength:10, required:true, validate: 'NotEmpty,ValidAplhaNumeric'}
					,{type:"newcolumn"}
					,{type:"input", label:"사용자ID", width:150, offsetLeft:20, name:"USR_ID"}
				]}
			]
		},

		//---------------------------------------
		GRID2 : {
			css : {
				height : "590px",
				top : 0,
				bottom :0
			},

			title : "그룹별 사용자",
			header : "그룹ID,부서명,사용자ID,사용자명,USR_ID",
			columnIds : "GRP_ID,ORGN_NM,USR_ID_MSK,USR_NM,USR_ID",
			colAlign : "left,left,left,left,left",
			widths : "100,135,100,120,0",
			colTypes : "ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			hiddenColumns:[4],
			paging : true,
			pagingStyle : 1,
			pageSize : 20,
			showPagingAreaOnInit : true,
			buttons : {
				right : {
					btnReg : { label : "등록" },
					btnDel : { label : "삭제" }
				}
			},

			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
// 				alert(btnId);
				switch (btnId) {
					case "btnReg":

						// 등록을 누르면 사용자 찾는 팝업으로 연결.
						// 검색 결과가 들어와야 함. 팝업창에서 확인이 작동 안함.
						mvno.ui.popupWindow(ROOT+"/org/userinfomgmt/searchUserInfo.do", "사용자찾기", 860, 620, {
							 param : {

                            callback : function(result) {
															mvno.cmn.ajax(ROOT + "/cmn/authmgmt/usrGrpAsgnInsert.json", {"GRP_ID":PAGE.GRID1.getSelectedRowData().GRP_ID, "USR_ID": result.usrId } , function(result) {
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
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/authmgmt/usrGrpAsgnDelete.json"
								, PAGE.GRID2.getSelectedRowData() , function(result) {
							mvno.ui.notify("NCMN0003");
							PAGE.GRID2.refresh();
						});
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

	        var url1 = ROOT + "/cmn/authmgmt/usrGrpList.json";
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


