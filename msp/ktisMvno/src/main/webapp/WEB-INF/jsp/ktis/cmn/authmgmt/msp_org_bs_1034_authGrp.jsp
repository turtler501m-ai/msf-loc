<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1034.jsp
	 * @Description : 권한별 그룹 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.09.01     임지혜           최초 생성
	 * @
	 * @author : 임지혜
	 * @Create Date : 2014. 9. 1.
	 */
%>

	
	<div class="row">
   		<div id="FORM1" class="c5" >
   			<div id="GRID1" class="section-half" ></div>
   		</div>
   		<div id="FORM2" class="c5" >
	   		<div id="GRID2" class="section-half" ></div>
		</div>
	</div>
	<div id="FORM3" class="section-box" ></div>
	
	

    <!-- Script -->
    <script type="text/javascript">
    
    var DHX = {
		
		GRID1 : {
			css : {
				height : "580px"
			},

			
			// 가로 스크롤바 생기지 않음. redmine에 관련 내용 존재
			// http://14.49.28.88:3000/boards/1/topics/252
			
			title : "권한",
			header : "권한ID,권한명,권한설명,사용", 
			columnIds : "AUTH_ID,AUTH_NM,AUTH_DSC,USG_YN",
			colAlign : "left,left,left,center",
			widths : "100,140,178,32",
			colTypes : "ro,ro,ro,ro",
			colSorting : "str,str,str,str",
			paging : true,
			pagingStyle : 1,
            pageSize : 20,
            showPagingAreaOnInit : true,
			
			//-----------------------------------------------
			// click list -> show detail	
			//-----------------------------------------------
			onRowSelect : function(rowId, celInd) {
				
				selectedData = PAGE.GRID1.getSelectedRowData();
				console.log("selectedData:" + selectedData);

//				mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/authDetail.json", 
//								selectedData,
//								function(result) {
//									console.log("result.result.data.age:" + result.result.data);
//									PAGE.FORM3.setFormData(result.result.data); 
//								}
//							);
				
				
				var url = ROOT + "/cmn/authmgmt/grpAuthAsgnList.json";
	            PAGE.GRID2.list(url, selectedData);
			},
			
			buttons : {
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			
			checkSelectedButtons : ["btnMod"],
			onButtonClick : function(btnId, selectedData) {
                   
                   switch (btnId) {

                       case "btnReg":
                    	   
                    	   mvno.ui.createForm("FORM3");
                    	   PAGE.FORM3.clear();
                    	   PAGE.FORM3.setItemValue('inputMod', '등록');
                    	   
                    	   mvno.ui.popupWindowById("FORM3", "권한관리", 550, 300, {
                               onClose: function(win) {
//                                   if (! PAGE.FORM3.isChanged()) return true;
                                   if (! PAGE.FORM3.clearChanged()) return true
                                   mvno.ui.confirm("CCMN0005", function(){
                                       win.closeForce();
                                   });
                               }
                           }); 
                    	   
                    	   break;
                    	   
                    	   
                       case "btnMod":

                           mvno.ui.createForm("FORM3");
                           
                           PAGE.FORM3.setFormData(selectedData);
                           PAGE.FORM3.setItemValue('inputMod', '수정');
                           
                           mvno.ui.popupWindowById("FORM3", "권한관리", 550, 300, {
                               onClose: function(win) {
//                                   if (! PAGE.FORM3.isChanged()) return true;
                                   if (! PAGE.FORM3.clearChanged()) return true;
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
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				
				,{type:"block", list:[
					{type:"input", label:"등록/수정", width:150, name:"inputMod", disabled: true, maxLength:10}
				]}
				
				// 1
				,{type:"block", list:[
					,{type:"input", label:"권한ID", width:150, name:"AUTH_ID", required: true, validate: 'NotEmpty,ValidAplhaNumeric', maxLength:10}
					,{type:"newcolumn"}
					,{type:"input", label:"권한명", width:150, offsetLeft:20, name:"AUTH_NM", required: true, validate: 'NotEmpty', maxLength:100}
				]}

				// 2
				,{type:"block", list:[
					{type:"input", label:"권한설명", width:385, name:"AUTH_DSC", maxLength:250}
				]}

				// 3
				,{type:"block", list:[
					{type:"select", width:80, label:"사용여부", name:"USG_YN"
						, options:[{value:"Y", text:"사용", selected:true},{value:"N", text:"미사용"}], maxLength:10} 
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

					case "AUTH_ID":
						
						form.setItemValue(itemId.name, form.getItemValue(itemId.name).replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
						//form.setItemValue(itemId.name, form.getItemValue(itemId.name).toUpperCase() );
						
						break;	
				}
			
			},	
			
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnSave":
						
						if ( form.getItemValue('inputMod') == "등록"){
//						if (form.isNew()) { // 등록 처리
                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/authInsert.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);  
                                mvno.ui.notify("NCMN0001");
                                PAGE.GRID1.refresh();
                            });
                        }
                        else { // 수정 처리
                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/authUpdate.json", form, function(result) {
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
			},
			
//			onValidateMore: function(data) {
//				  validateMaxLength(this);
//			}
		}, 

		//---------------------------------------
		// 
		//---------------------------------------
		GRID2 : {
			css : {
				height : "580px"
			},

			title : "권한별 그룹",
			header : "권한 ID,그룹 ID,그룹명,그룹설명", 
			columnIds : "AUTH_ID,GRP_ID,GRP_NM,GRP_DSC",
			widths : "100,100,120,135",
			colAlign : "left,left,left,left",
			colTypes : "ro,ro,ro,ro",
			colSorting : "str,str,str,str",
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

				switch (btnId) {
					case "btnReg":
						if ( PAGE.GRID1.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0102");  // 권한을 먼저 선택하세요
							return;
						}
						selectedData = PAGE.GRID1.getSelectedRowData();
						console.log(selectedData.AUTH_ID);
						mvno.ui.popupWindow(ROOT + "/cmn/authmgmt/popGrpList.do?AUTH_ID=" + selectedData.AUTH_ID, "그룹찾기", 690, 540, {
							param : {
	                            callback : function(result) {
																mvno.cmn.ajax(ROOT + "/cmn/authmgmt/grpAuthAsgnInsert.json"
																, {"AUTH_ID":PAGE.GRID1.getSelectedRowData().AUTH_ID, "GRP_ID": result.GRP_ID } 
																, function(result) {
																	mvno.ui.notify("NCMN0001");
																	PAGE.GRID2.refresh();     
																});
															}							
							}
						});
						break;			
					case "btnDel":
						
						if(PAGE.GRID2.getSelectedRowData() == null)
						{
							mvno.ui.notify("ECMN0002");
							return;
						}
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/authmgmt/grpAuthAsgnDelete.json" , PAGE.GRID2.getSelectedRowData() , function(result) {
							mvno.ui.notify("NCMN0003");
							PAGE.GRID2.refresh();
						});
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
	        mvno.ui.createGrid("GRID1");
	        mvno.ui.createGrid("GRID2");
	        
            PAGE.GRID1.list("/cmn/authmgmt/authListPaging.json", null, { callback : gridOnLoad });
            
            
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


	