<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1001.jsp
	 * @Description : 조직 관리 화면
	 * @
	 * @ 수정일        수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.14 장익준 최초생성
	 * @
	 * @author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
%>

<!-- 조직 조회 폼 --> 
<div id="FORM1" class="section-search"></div>

<!-- 조직 등록 폼 -->
<div id="FORM3" class="section-box"></div>

<!-- 조직 수정 폼 -->
<div id="FORM4" class="section-box"></div>

<!-- 선불 조직 정보 등록 폼 -->
<div id="FORM22" class="section-search"></div>

<!-- 선불 조직 정보 수정 폼 -->
<div id="FORM23" class="section-search"></div>

<!-- 조직파일관리 폼 -->
<div id="FORM25" class="section-box"></div> 

<!-- 담보파일관리 폼 -->
<div id="FORM26" class="section-box"></div> 

<!-- 조직 리스트 그리드 -->
<div id="GRID1"></div>

<!-- 탭바 선언 -->
<div id="TABBAR1"></div>

<!-- 조직 상세 -->
<div id="TABBAR1_a1" style="display:none">
	<div id="FORM2" class="section-box"></div>
</div>

<!-- 조직 이력 -->
<div id="TABBAR1_a2" style="display:none">
	<div id="GRID2"></div>
</div>

<!-- 조직 선불 정보 -->
<div id="TABBAR1_a9" style="display:none">
	<div id="FORM21" class="section-box"></div>
</div>

<!-- 판매점 정보 -->
<!-- <div id="TABBAR1_a10" style="display:none"> -->
<!-- 	<div id="GRID9"></div> -->
<!-- </div> -->

<!-- Script -->
<script type="text/javascript">


<%@ include file="org_bs_2001.formitems" %>

// 주소 등록, 수정 구분
var jusoGubun = "";

//var gMonthFristDay = "${startDate}";

var DHX = {
	// 검색 조건
	FORM1 : {
		items : _FORMITEMS_['form1'],
		onButtonClick : function(btnId) {
			var form = this;
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list4tree(ROOT + "/org/orgmgmt/treeListOrgMgmt.json", form,  {
						callback : function(){
							PAGE.FORM2.clear();
						}
					});
					//포커스
					PAGE.TABBAR1.tabs("a1").setActive();
					break;
			}
		}
	}
	// 조직 리스트 - GRID1
	,GRID1 : {
		css : {
			height : "300px"
		},
		title : "조회결과",
		header : "조직ID,조직명,상위조직ID,상위조직명,조직레벨,조직상태,적용시작일,적용종료일",//9
		columnIds : "orgnId,orgnNm,hghrOrgnCd,hghrOrgnNm,orgnLvlNm,orgnStatNm,applStrtDt,applCmpltDt",
		colAlign : "left,left,left,left,center,center,center,center",
		colTypes : "tree,ro,ro,ro,ro,ro,roXd,roXd",
		colSorting : "str,str,str,str,str,str,str,str",
		treeId: "orgnId",
		treeSubId: "hghrOrgnCd",
		widths : "224,150,90,150,70,70,90,90",
		onRowSelect : function(rowId, celInd) {
			var rowData = "";
			rowData = this.getRowData(rowId);
			
			if (PAGE.FORM2){
				PAGE.FORM2.setFormData(rowData);
				/* 법인등록번호, 사업자번호, 주민번호, 전화번호, FAX , 휴대전화 */
				setLabelAddHyphen(PAGE.FORM2, rowData, "corpRegNum", "rrnum");
				//setLabelAddHyphen(PAGE.FORM2, rowData, "rprsenRrnum", "rrnum");
				setLabelAddHyphen(PAGE.FORM2, rowData, "bizRegNum", "bizRegNum");
				//setLabelAddHyphen(PAGE.FORM2, rowData, "telnum", "tel");
				//setLabelAddHyphen(PAGE.FORM2, rowData, "fax", "tel");
				setLabelAddHyphen(PAGE.FORM2, rowData, "respnPrsnMblphn", "tel");
				showBtn4OrgnStat("FORM2");
			}
			
			var tab = PAGE.TABBAR1.tabs("a1");
			tab.refreshIframe(rowData);
			//포커스
			PAGE.TABBAR1.tabs("a1").setActive();
		}
	}
	//탭 구성
	,TABBAR1: {
		title : "상세내역",
		css : { height : "330px" },
		tabs: [
			{ id: "a1", text: "조직상세", active: true },
			{ id: "a2", text: "조직이력" },
			{ id: "a9", text: "선불 조직 정보" }
// 			{ id: "a10", text: "판매점 정보" }
		],
		sideArrow: false,
		onSelect: function (id, lastId, isFirst) {
			var rowData = PAGE.GRID1.getSelectedRowData();
			
			//조직 선택여부
			if(rowData == null && id != 'a1'){
				mvno.ui.alert("ORGN0006");
				return false;
			}
			
			switch (id) {
				//조직 상세 내역
				case "a1":
					mvno.ui.createForm("FORM2");
					if (rowData) {
						PAGE.FORM2.setFormData(rowData);
						// 법인번호
						setLabelAddHyphen(PAGE.FORM2, rowData, "corpRegNum", "rrnum");
						// 대표자주민번호
						//setLabelAddHyphen(PAGE.FORM2, rowData, "rprsenRrnum", "rrnum");
						// 사업자번호
						setLabelAddHyphen(PAGE.FORM2, rowData, "bizRegNum", "bizRegNum");
						// 대표전화번호
						//setLabelAddHyphen(PAGE.FORM2, rowData, "telnum", "tel");
						// FAX번호
						//setLabelAddHyphen(PAGE.FORM2, rowData, "fax", "tel");
						// 영업담당 전화번호
						setLabelAddHyphen(PAGE.FORM2, rowData, "respnPrsnMblphn", "tel");
						showBtn4OrgnStat("FORM2");
					}
					
					break;
				//조직 이력
				case "a2":
					mvno.ui.createGrid("GRID2");
					if(rowData == null)
					{
						break;
					}
					else
					{
						PAGE.GRID2.list(ROOT + "/org/orgmgmt/listOrgnHst.do", PAGE.FORM2);
					}
					break;
				// 조직 선불 정보
				case "a9":
					if(rowData == null)
					{
						break;
					}
					else
					{
						if("SU" == rowData.typeDtlCd1)
						{
							mvno.ui.createForm("FORM21");
							
							var selectedOrgnId = rowData.orgnId;
							
							PAGE.FORM21.disableItem("agentId");
							PAGE.FORM21.disableItem("agentNm");
							PAGE.FORM21.disableItem("adminId");
							
							//PAGE.FORM21.disableItem("deposit");
							//PAGE.FORM21.disableItem("virAccountId");
							PAGE.FORM21.disableItem("recordDate");
							PAGE.FORM21.disableItem("agentDocFlag");
							
							var lovCode="PPS_0078";
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM21, "agentDocFlag");
							
							var url = ROOT +"/org/orgmgmt/ppsOrgnInfo.json";
							mvno.cmn.ajax(
								url,
								{
									orgnId : selectedOrgnId
								},
								function(result) {
									var result = result.result;
									var rCode = result.code;
									var msg = result.msg;
									if(rCode == "OK") {
										PAGE.FORM21.setFormData(result.data);
									} else {
										mvno.ui.notify(msg);
									}
								});
						} else {
							mvno.ui.alert('선불 대리점이 아닙니다.');
							break;
						}
					}
					break;
				//판매점 정보
// 				case "a10":
// 					mvno.ui.createGrid("GRID9");
// 					if(rowData == null)
// 					{
// 						break;
// 					}
// 					else
// 					{
// 						PAGE.GRID9.list(ROOT + "/org/shopmgmt/getMappingShopList.json", PAGE.FORM2);
// 					}
// 					break;
			}
		}
	}
	//조직 상세 버튼
	,FORM2: {
		items: _FORMITEMS_['form2'],
		buttons: {
            left: {
                     btnFileMgmt1: { label: "조직파일관리"},
                     btnFileMgmt2: { label: "담보파일관리"}
                 },			
			right: {
				btnReg: { label: "등록"},
				btnMod: { label: "수정"}
			}
		},
		checkSelectedButtons : ["btnMod"],
                onButtonClick : function(btnId, selectedData) {
                    var form = this;
                    var fileLimitCnt = 3;
                    
                    switch (btnId) {
                    case "btnFileMgmt1" :
                    	
                    	
                    	fileLimitCnt = 3;

                        if(PAGE.GRID1.getSelectedRowData() == null)
                        {
                            mvno.ui.alert("ECMN0002");
                            break;
                        }                        	
                        mvno.ui.createForm("FORM25");
                        PAGE.FORM25.clear();
                        PAGE.FORM25.showItem("file_upload1");
                        
                        var data = PAGE.GRID1.getSelectedRowData();
                        PAGE.FORM25.setFormData(data);
                        
                        
                        mvno.cmn.ajax(ROOT + "/org/common/getFile1.json", form, function(resultData) {
                        	var totCnt = resultData.result.fileTotalCnt;
                        	if( parseInt(totCnt) > 0){
                                PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-parseInt(totCnt));
                                PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
                                PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
                            }
                        	
                        	if(parseInt(totCnt) == fileLimitCnt){
                                PAGE.FORM25.hideItem("file_upload1");
                        	}
                        	
                            if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
                            	PAGE.FORM25.setItemValue("fileName1", resultData.result.fileNm);
                            	PAGE.FORM25.setItemValue("fileId1", resultData.result.fileId); 
                            	PAGE.FORM25.enableItem("fileDown1");
                                PAGE.FORM25.enableItem("fileDel1");
                            } else {
                            	PAGE.FORM25.disableItem("fileDown1");
                            	PAGE.FORM25.disableItem("fileDel1");
                            }
                     	   
                            if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
                                PAGE.FORM25.setItemValue("fileName2", resultData.result.fileNm1);
                                PAGE.FORM25.setItemValue("fileId2", resultData.result.fileId1);
                                PAGE.FORM25.enableItem("fileDown2");
                                PAGE.FORM25.enableItem("fileDel2");
                            } else {
                                PAGE.FORM25.disableItem("fileDown2");
                                PAGE.FORM25.disableItem("fileDel2");
                            }
                            
                            if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
                                PAGE.FORM25.setItemValue("fileName3", resultData.result.fileNm2);
                                PAGE.FORM25.setItemValue("fileId3", resultData.result.fileId2);
                                PAGE.FORM25.enableItem("fileDown3");
                                PAGE.FORM25.enableItem("fileDel3");
                            } else {
                                PAGE.FORM25.disableItem("fileDown3");
                                PAGE.FORM25.disableItem("fileDel3");
                            }
                            
                        });
                        
                        
                        var uploader = PAGE.FORM25.getUploader("file_upload1");
                        
                        uploader.revive();

                        mvno.ui.popupWindowById("FORM25", "파일관리", 600, 400, {
                     	    onClose2: function(win) {
                     	        uploader.reset();
                     	    }
                        });
                        break;

                    case "btnFileMgmt2" :
                    	
                    	
                    	fileLimitCnt = 3;
                    	                    	
                        if(PAGE.GRID1.getSelectedRowData() == null)
                        {
                            mvno.ui.alert("ECMN0002");
                            break;
                        }                        	
                        mvno.ui.createForm("FORM26");
                        PAGE.FORM26.clear();
                        
                        var data = PAGE.GRID1.getSelectedRowData();
                        PAGE.FORM26.setFormData(data);
                        PAGE.FORM26.showItem("file_upload2");
                        
                        mvno.cmn.ajax(ROOT + "/org/common/getFile2.json", form, function(resultData) {
                        	var totCnt = resultData.result.fileTotalCnt;
                        	if( parseInt(totCnt) > 0){
                                PAGE.FORM26.setUserData("file_upload2","limitCount",fileLimitCnt-parseInt(totCnt));
                                PAGE.FORM26.setUserData("file_upload2","limitsize",1000);
                                PAGE.FORM26.setUserData("file_upload2","delUrl","/org/common/deleteFile2.json");
                            }
                        	
                        	if(parseInt(totCnt) == fileLimitCnt){
                                PAGE.FORM26.hideItem("file_upload2");
                        	}
                        	
                            if(!mvno.cmn.isEmpty(resultData.result.fileNm3)){
                            	PAGE.FORM26.setItemValue("fileName4", resultData.result.fileNm3);
                            	PAGE.FORM26.setItemValue("fileId4", resultData.result.fileId3); 
                            	PAGE.FORM26.enableItem("fileDown4");
                                PAGE.FORM26.enableItem("fileDel4");
                            } else {
                            	PAGE.FORM26.disableItem("fileDown4");
                            	PAGE.FORM26.disableItem("fileDel4");
                            }
                     	   
                            if(!mvno.cmn.isEmpty(resultData.result.fileNm4)){
                                PAGE.FORM26.setItemValue("fileName5", resultData.result.fileNm4);
                                PAGE.FORM26.setItemValue("fileId5", resultData.result.fileId4);
                                PAGE.FORM26.enableItem("fileDown5");
                                PAGE.FORM26.enableItem("fileDel5");
                            } else {
                                PAGE.FORM26.disableItem("fileDown5");
                                PAGE.FORM26.disableItem("fileDel5");
                            }
                            
                            if(!mvno.cmn.isEmpty(resultData.result.fileNm5)){
                                PAGE.FORM26.setItemValue("fileName6", resultData.result.fileNm5);
                                PAGE.FORM26.setItemValue("fileId6", resultData.result.fileId5);
                                PAGE.FORM26.enableItem("fileDown6");
                                PAGE.FORM26.enableItem("fileDel6");
                            } else {
                                PAGE.FORM26.disableItem("fileDown6");
                                PAGE.FORM26.disableItem("fileDel6");
                            }
                            
                        });
                        
                        
                        var uploader2 = PAGE.FORM26.getUploader("file_upload2");
                        
                        uploader2.revive();

                        mvno.ui.popupWindowById("FORM26", "파일관리", 600, 400, {
                     	    onClose2: function(win) {
                     	        uploader2.reset();
                     	    }
                        });
                        break;
				// 조직등록
				case "btnReg":
					mvno.ui.createForm("FORM3");
					
					mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnType1.json", "", PAGE.FORM3, "typeDtlCd1");
					//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0012"}, PAGE.FORM3, "openCrdtLoanYn");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0019"}, PAGE.FORM3, "typeDtlCd1");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0007"}, PAGE.FORM3, "bizTypeCd");
					//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM3, "telnum1");
					//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM3, "fax1");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0055"}, PAGE.FORM3, "orgnGrd");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0005"}, PAGE.FORM3, "orgnStatCd");
					
					PAGE.FORM3.setFormData(selectedData);
					PAGE.FORM3.setItemValue("applCmpltDt","99991231");
					PAGE.FORM3.setItemValue("orgnStatCd", "1");
					
					PAGE.FORM3.clearChanged();
					
					mvno.ui.popupWindowById("FORM3", "등록화면", 810, 350, {
						onClose: function(win) {
							if (! PAGE.FORM3.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
					
					break;
				// 조직수정
				case "btnMod":
					if(PAGE.GRID1.getSelectedRowData() == null)
					{
						mvno.ui.alert("ECMN0002");
						break;
					}
					mvno.ui.createForm("FORM4");
					var data = PAGE.GRID1.getSelectedRowData();
					var orgnType1 = PAGE.GRID1.getSelectedRowData().typeDtlCd1;
					var orgnType2 = PAGE.GRID1.getSelectedRowData().typeDtlCd2;
					var orgnType3 = PAGE.GRID1.getSelectedRowData().typeDtlCd3;
					
					mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeDtl.json", {orgnType1:orgnType1} , PAGE.FORM4, "typeDtlCd1");
					mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeDtl.json", {orgnType2:orgnType2} , PAGE.FORM4, "typeDtlCd2");
					mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeDtl.json", {orgnType3:orgnType3} , PAGE.FORM4, "typeDtlCd3");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0005"}, PAGE.FORM4, "orgnStatCd");
					//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0012"}, PAGE.FORM4, "openCrdtLoanYn");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0007"}, PAGE.FORM4, "bizTypeCd");
					//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM4, "telnum1");
					//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM4, "fax1");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0055"}, PAGE.FORM4, "orgnGrd");
					
					PAGE.FORM4.setFormData(data); //수정인 경우 그리드의 값을 가져온다.
					
					PAGE.FORM4.setItemValue("typeDtlCd1", PAGE.GRID1.getSelectedRowData().typeDtlCd1);
					PAGE.FORM4.setItemValue("typeDtlCd2", PAGE.GRID1.getSelectedRowData().typeDtlCd2);
					PAGE.FORM4.setItemValue("typeDtlCd3", PAGE.GRID1.getSelectedRowData().typeDtlCd3);
					
					/* 법인등록번호, 사업자번호, 주민번호, 전화번호, FAX , 휴대전화 */
					setInputDataAddHyphen(PAGE.FORM4, data, "corpRegNum", "rrnum");
					//setInputDataAddHyphen(PAGE.FORM4, data, "rprsenRrnum", "rrnum");
					setInputDataAddHyphen(PAGE.FORM4, data, "bizRegNum", "bizRegNum");
					//setInputDataAddHyphen(PAGE.FORM4, data, "telnum", "tel");
					//setInputDataAddHyphen(PAGE.FORM4, data, "fax", "tel");
					setInputDataAddHyphen(PAGE.FORM4, data, "respnPrsnMblphn", "tel");
					
					//판매점 이하 조직일 경우 조직 부가정보를 disable 처리 한다.
					if(parseInt(data.orgnLvlCd) == 20)
					{
						/* PAGE.FORM4.setItemValue("hndstOrdYn", false);
						PAGE.FORM4.disableItem("hndstOrdYn");
						PAGE.FORM4.setItemValue("hndstInvtrYn", false);
						PAGE.FORM4.disableItem("hndstInvtrYn");
						PAGE.FORM4.setItemValue("taxbillYn", false);
						PAGE.FORM4.disableItem("taxbillYn");
						PAGE.FORM4.setItemValue("cmsnPrvdYn", false);
						PAGE.FORM4.disableItem("cmsnPrvdYn"); */
						PAGE.FORM4.setItemValue("mclubYn", false);
						PAGE.FORM4.disableItem("mclubYn");
					}else{
						/* PAGE.FORM4.enableItem("hndstOrdYn");
						PAGE.FORM4.enableItem("hndstInvtrYn");
						PAGE.FORM4.enableItem("taxbillYn");
						PAGE.FORM4.enableItem("cmsnPrvdYn"); */
						PAGE.FORM4.enableItem("mclubYn");
					}
					
					PAGE.FORM4.clearChanged();
					
					mvno.ui.popupWindowById("FORM4", "수정화면", 810, 350, {
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
	}
            //파일관리 (조직)
           , FORM25: {
                items: _FORMITEMS_['form25'],
                buttons: {
                    center: {
                        btnSave: { label: "확인" },
                        btnClose: { label: "닫기" }
                    }
                },
                onChange : function (name, value){
                    var form = this;
                },
                onButtonClick : function(btnId) {

                    var form = this; // 혼란방지용으로 미리 설정 (권고)
                    var fileLimitCnt = 3;
                    
                    switch (btnId) {
                        case "btnSave":
                            mvno.cmn.ajax(ROOT + "/org/common/insertMgmt.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);  
                                mvno.ui.notify("NCMN0015");
                                PAGE.FORM25.clear();
                            });
                            break;
                        case "btnClose" :
                            mvno.ui.closeWindowById(form);
                            break;
                        case "fileDown1" :
                        	 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId1"));
                             break;
                        case "fileDown2" :
	                       	 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId2"));
	                            break;       
                        case "fileDown3" :
	                       	 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId3"));
	                            break;
                        case "fileDel1" :
                             mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId1"),orgnId:form.getItemValue("orgnId"),attSctn:'ORG'} , function(resultData) {
                                mvno.ui.notify("NCMN0014");
                                PAGE.FORM25.setItemValue("fileName1", "");
                                PAGE.FORM25.disableItem("fileDown1");
                                PAGE.FORM25.disableItem("fileDel1");
                                
                                PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                                PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
                                PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
                                PAGE.FORM25.showItem("file_upload1");
                             });
	                            break;
                        case "fileDel2" :
                            mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId2"),orgnId:form.getItemValue("orgnId"),attSctn:'ORG'} , function(resultData) {
                                mvno.ui.notify("NCMN0014");
                                PAGE.FORM25.setItemValue("fileName2", "");
                                PAGE.FORM25.disableItem("fileDown2");
                                PAGE.FORM25.disableItem("fileDel2");

                                PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                                PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
                                PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
                                PAGE.FORM25.showItem("file_upload1");

                             });
                            break;
                        case "fileDel3" :
                            mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId3"),orgnId:form.getItemValue("orgnId"),attSctn:'ORG'} , function(resultData) {
                                mvno.ui.notify("NCMN0014");
                                PAGE.FORM25.setItemValue("fileName3", "");
                                PAGE.FORM25.disableItem("fileDown3");
                                PAGE.FORM25.disableItem("fileDel3");
                                
                                PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                                PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
                                PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
                                PAGE.FORM25.showItem("file_upload1");

                             });
                            break;
                    }
                },
                onValidateMore : function (data){
                	//
                }
            },
            //파일관리
            FORM26: {
                items: _FORMITEMS_['form26'],
                buttons: {
                    center: {
                        btnSave: { label: "확인" },
                        btnClose: { label: "닫기" }
                    }
                },
                onChange : function (name, value){
                    var form = this;
                },
                onButtonClick : function(btnId) {

                    var form = this; // 혼란방지용으로 미리 설정 (권고)
                    var fileLimitCnt = 3;
                    
                    switch (btnId) {
                        case "btnSave":
                            mvno.cmn.ajax(ROOT + "/org/common/insertMgmt.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);  
                                mvno.ui.notify("NCMN0015");
                                PAGE.FORM26.clear();
                            });
                            break;
                        case "btnClose" :
                            mvno.ui.closeWindowById(form);   
                            break;
                        case "fileDown4" :
                        	 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM26.getItemValue("fileId4"));
                             break;
                        case "fileDown5" :
	                       	 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM26.getItemValue("fileId5"));
	                            break;       
                        case "fileDown6" :
	                       	 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM26.getItemValue("fileId6"));
	                            break;
                        case "fileDel4" :
                             mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM26.getItemValue("fileId4"),orgnId:form.getItemValue("orgnId"),attSctn:'MRT'} , function(resultData) {
                                mvno.ui.notify("NCMN0014");
                                PAGE.FORM26.setItemValue("fileName4", "");
                                PAGE.FORM26.disableItem("fileDown4");
                                PAGE.FORM26.disableItem("fileDel4");
                                
                                PAGE.FORM26.setUserData("file_upload2","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                                PAGE.FORM26.setUserData("file_upload2","limitsize",1000);
                                PAGE.FORM26.setUserData("file_upload2","delUrl","/org/common/deleteFile2.json");
                                PAGE.FORM26.showItem("file_upload2");
                             });
	                            break;
                        case "fileDel5" :
                            mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM26.getItemValue("fileId5"),orgnId:form.getItemValue("orgnId"),attSctn:'MRT'} , function(resultData) {
                                mvno.ui.notify("NCMN0014");
                                PAGE.FORM26.setItemValue("fileName5", "");
                                PAGE.FORM26.disableItem("fileDown5");
                                PAGE.FORM26.disableItem("fileDel5");

                                PAGE.FORM26.setUserData("file_upload2","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                                PAGE.FORM26.setUserData("file_upload2","limitsize",1000);
                                PAGE.FORM26.setUserData("file_upload2","delUrl","/org/common/deleteFile2.json");
                                PAGE.FORM26.showItem("file_upload2");

                             });
                            break;
                        case "fileDel6" :
                            mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM26.getItemValue("fileId6"),orgnId:form.getItemValue("orgnId"),attSctn:'MRT'} , function(resultData) {
                                mvno.ui.notify("NCMN0014");
                                PAGE.FORM26.setItemValue("fileName6", "");
                                PAGE.FORM26.disableItem("fileDown6");
                                PAGE.FORM26.disableItem("fileDel6");
                                
                                PAGE.FORM26.setUserData("file_upload2","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                                PAGE.FORM26.setUserData("file_upload2","limitsize",1000);
                                PAGE.FORM26.setUserData("file_upload2","delUrl","/org/common/deleteFile2.json");
                                PAGE.FORM26.showItem("file_upload2");

                             });
                            break;
                    }
                },
                onValidateMore : function (data){
                	//
                }
            }	
	//조직 등록
	,FORM3: {
		items: _FORMITEMS_['form3'],
		buttons: {
			center: {
				btnSave: { label: "저장" },
				btnClose: { label: "닫기" }
			}
		},
		onChange : function (name, value){
			var form = this;
			//대리점유형,대리점유형상세,채널상세 설정
			if(name == "typeDtlCd1"){
				form.reloadOptions("typeDtlCd2",[{label: "", value:""}]);
				form.reloadOptions("typeDtlCd3",[{label: "", value:""}]);
				if( !mvno.cmn.isEmpty(value)){
					var orgnType2 = value;
					mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeChg.json", {orgnType2:orgnType2} , PAGE.FORM3, "typeDtlCd2");
				}
			} else if(name == "typeDtlCd2"){
				form.reloadOptions("typeDtlCd3",[{label: "", value:""}]);
				var orgnType3 = value;
				mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeChg.json", {orgnType3:orgnType3} , PAGE.FORM3, "typeDtlCd3");
			}
			else if(name == "orgnId"){
				PAGE.FORM3.setItemValue("btnExistFlag","0");
			}
		},
		onButtonClick : function(btnId) {
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			
			switch (btnId) {
				case "btnSave":
					mvno.cmn.ajax(ROOT + "/org/orgmgmt/insertMgmt.json", form, function(result) {
						mvno.ui.closeWindowById(form, true);
						mvno.ui.notify("NCMN0001");
						
						var rId = PAGE.GRID1.getSelectedRowId();
						var pId = PAGE.GRID1.getParentId(rId);
						
						if(mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("orgnNm"))
								&& mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("typeCd"))
								&& mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("orgnLvlCd"))
								&& mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("orgnStatCd")))
						{
							if(rId){
								PAGE.GRID1.refresh4tree(pId);
							}
						} else {
							console.log('all');
							PAGE.GRID1.refresh4tree();
						}
						
						PAGE.FORM2.clear();
					});
					break;
				case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;
				case "btnUseFind":
					mvno.ui.codefinder("USER", function(result) {
						form.setItemValue("respnPrsnNm", result.usrNm);
						form.setItemValue("respnPrsnId", result.usrId);
						//form.setItemValue("respnPrsnMblphn", result.mblphnNum);
						//form.setItemValue("respnPrsnEmail", result.email);
					});
					break;
				case "btnOrgFind":
					//상위조직검색
					mvno.ui.codefinder("ORGHEAD", function(result) {
						form.setItemValue("hghrOrgnCd", result.orgnId);
						form.setItemValue("hghrOrgnNm", result.orgnNm);
						
						form.enableItem("orgnId");
						form.enableItem("btnExist");
						form.setItemValue("orgnLvlCd",result.orgnLvlCd);
						form.setItemValue("orgnStatCd", "가동");
						PAGE.FORM3.setItemValue("reqFlag", "true");
						
						if(parseInt(result.orgnLvlCd) == 5)
						{
							form.setItemValue("orgnStatCd", "등록");
						}
						else
						{
							form.setItemValue("orgnStatCd", "가동");
						}
						
						//판매점 이하 조직일 경우 조직 부가정보를 disable 처리 한다.
						if(parseInt(result.orgnLvlCd) == 10 || parseInt(result.orgnLvlCd) == 20)
						{
							/* form.setItemValue("hndstOrdYn", false);
							form.disableItem("hndstOrdYn");
							form.setItemValue("hndstInvtrYn", false);
							form.disableItem("hndstInvtrYn");
							form.setItemValue("taxbillYn", false);
							form.disableItem("taxbillYn");
							form.setItemValue("cmsnPrvdYn", false);
							form.disableItem("cmsnPrvdYn"); */
							form.setItemValue("mclubYn", false);
							form.disableItem("mclubYn");
						}
						else
						{
							/* form.enableItem("hndstOrdYn");
							form.enableItem("hndstInvtrYn");
							form.enableItem("taxbillYn");
							form.enableItem("cmsnPrvdYn"); */
							form.enableItem("mclubYn");
						}
					});
					break;
				/* case "btnPostFind":
					jusoGubun = "REG";
					//우편번호 검색
					mvno.ui.codefinder4ZipCd("FORM3", "zipcd", "addr1", "addr2");
					break; */
				case "btnExist":
					//조직ID 중복 체크
					if(PAGE.FORM3.getItemValue("orgnId") != ""){
						mvno.cmn.ajax(ROOT + "/org/orgmgmt/isExistOrgnId.json", {orgnId:PAGE.FORM3.getItemValue("orgnId")}, function(resultData) {
							if(resultData.result.resultCnt > 0){
								mvno.ui.alert("ICMN0008");
								form.setItemValue("btnExistFlag","0");
							}else{
								//사용가능
								mvno.ui.alert("ICMN0007");
								form.setItemValue("btnExistFlag","1");
							}
						});
					} else {
						mvno.ui.alert("ORGN0011");
					}
					break;
			}
		},
		onValidateMore : function (data){
			var agncyFlag = false;
			if(data.orgnLvlCd == 5 || data.orgnLvlCd == 10 || data.orgnLvlCd == 20){
				agncyFlag = true;
			}
			if(!data.hghrOrgnCd){
				this.pushError("data.hghrOrgnCd","상위조직명",["ORGN0006"]);
			}
			else if( agncyFlag && !data.orgnId){
				this.pushError("data.orgnId","조직ID",["ICMN0001"]);
			}
			else if( agncyFlag && data.orgnId && data.orgnId.length < 10){
				this.pushError("data.orgnId","조직ID",["ORGN0011"]);
			}
			else if(agncyFlag && data.orgnId && data.btnExistFlag == "0"){
				this.pushError("data.orgnId","조직ID",["ICMN0010"]);
			}
			/* else if(data.openCrdtLoanYn == 'Y' && !data.openCrdtLoanStrtDt){
				this.pushError("data.openCrdtLoanStrtDt","개통여신시작일",["ORGN0010"]);
			} */
			else if(agncyFlag && data.bizTypeCd == ''){
				this.pushError("data.bizTypeCd","사업자구분",["ICMN0001"]);
			}
			else if(agncyFlag && (!data.bizRegNum1 || !data.bizRegNum2 || !data.bizRegNum3)){
				this.pushError("data.bizRegNum1","사업자등록번호",["ICMN0001"]);
			}
			else if(!mvno.etc.checkPackNum(data.bizRegNum1, data.bizRegNum2 , data.bizRegNum3)){
				this.pushError("data.bizRegNum1","사업자등록번호",["ICMN0011"]);
			}
			else if((!data.corpRegNum1 && data.corpRegNum2) || (data.corpRegNum1 && !data.corpRegNum2)){
				this.pushError("data.corpRegNum1","법인등록번호",["ICMN0011"]);
			}
			/* else if((!data.rprsenRrnum1 && data.rprsenRrnum2) || (data.rprsenRrnum1 && !data.rprsenRrnum2)){
				this.pushError("data.rprsenRrnum1","대표자주민번호",["ICMN0011"]);
			}
			else if(data.rprsenRrnum1 && data.rprsenRrnum2 && !mvno.etc.checkRrNum(data.rprsenRrnum1 + data.rprsenRrnum2)){
				this.pushError("data.rprsenRrnum1","대표자주민번호",["ICMN0012"]);
			}
			else if(!mvno.etc.checkPackNum(data.telnum1, data.telnum2 , data.telnum3)){
				this.pushError("data.telnum2","대표전화번호",["ICMN0011"]);
			}
			else if(data.telnum1 && !mvno.etc.checkPhoneNumber(data.telnum1+data.telnum2+data.telnum3)){
				this.pushError("data.telnum2","대표전화번호",["ICMN0012"]);
			}
			else if(agncyFlag && (!data.fax1 || !data.fax2 || !data.fax3)){
				this.pushError("data.fax1","FAX",["ORGN0010"]);
			}
			else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
				this.pushError("data.fax2","FAX",["ICMN0011"]);
			}
			else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
				this.pushError("data.fax2","FAX",["ICMN0012"]);
			} */
			else if(agncyFlag && !data.respnPrsnId){
				this.pushError("data.hghrOrgnCd","담당자이름",["ORGN0012"]);
			}
		}
	}
	//조직 수정
	,FORM4: {
		items: _FORMITEMS_['form4'],
		buttons: {
			center: {
				btnMod: { label: "저장" },
				btnClose: { label: "닫기" }
			}
		},
		onChange : function (name, value){
			var form = this;
			
			//대리점유형,대리점유형상세,채널상세 설정
			if(name == "typeDtlCd1"){
				form.reloadOptions("typeDtlCd2",[{label: "", value:""}]);
				form.reloadOptions("typeDtlCd3",[{label: "", value:""}]);
				if( !mvno.cmn.isEmpty(value)){
					var orgnType2 = value;
					mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeChg.json", {orgnType2:orgnType2} , PAGE.FORM4, "typeDtlCd2");
				}
			} else if(name == "typeDtlCd2"){
				form.reloadOptions("typeDtlCd3",[{label: "", value:""}]);
				var orgnType3 = value;
				mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeChg.json", {orgnType3:orgnType3} , PAGE.FORM4, "typeDtlCd3");
			}
			else if(name == "orgnId"){
				PAGE.FORM3.setItemValue("btnExistFlag","0");
			}
		},
		onButtonClick : function(btnId) {
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			switch (btnId) {
				case "btnMod":
					/* if(form.getItemValue("openCrdtLoanYn") != 'Y'){
						form.setItemValue("openCrdtLoanStrtDt","");
					} */
					mvno.cmn.ajax(ROOT + "/org/orgmgmt/updateMgmtNew.json", form, function(result) {
						mvno.ui.closeWindowById(form, true);
						mvno.ui.notify("NCMN0002");
						
						var rId = PAGE.GRID1.getSelectedRowId();
						var pId = PAGE.GRID1.getParentId(rId);
						
						if(mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("orgnNm"))
								&& mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("typeCd"))
								&& mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("orgnLvlCd"))
								&& mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("orgnStatCd")))
						{
							if(pId){
								PAGE.GRID1.refresh4tree(pId);
							} else {
								PAGE.GRID1.refresh4tree();
							}
						} else {
							PAGE.GRID1.refresh4tree();
						}
						PAGE.FORM2.clear();
					});
					break;
				case "btnClose" :
					mvno.ui.closeWindowById(form);
					break;
				case "btnUseFind":
					mvno.ui.codefinder("USER", function(result) {
						form.setItemValue("respnPrsnNm", result.usrNm);
						form.setItemValue("respnPrsnId", result.usrId);
						/* form.setItemValue("respnPrsnMblphn", result.mblphnNum);
						form.setItemValue("respnPrsnEmail", result.email); */
					});
					break;
				case "btnOrgFind":
					mvno.ui.codefinder("ORGHEAD", function(result) {
						form.setItemValue("hghrOrgnCd", result.orgnId);
						form.setItemValue("hghrOrgnNm", result.orgnNm);
					});
					break;
				/* case "btnPostFind":
					jusoGubun = "MOD";
					mvno.ui.codefinder4ZipCd("FORM4", "zipcd", "addr1", "addr2");
					break; */
			}
		},
		onValidateMore : function (data){
			var agncyFlag = false;
			if(data.orgnLvlCd == 10 || data.orgnLvlCd == 20){
				agncyFlag = true;
			}
			if(agncyFlag && data.bizTypeCd == ''){
				this.pushError("data.bizTypeCd","사업자구분",["ICMN0001"]);
			}
			/* else if(data.openCrdtLoanYn == 'Y' && !data.openCrdtLoanStrtDt){
				this.pushError("data.openCrdtLoanStrtDt","개통여신시작일",["ORGN0010"]);
			} */
			else if(agncyFlag && (!data.bizRegNum1 || !data.bizRegNum2 || !data.bizRegNum3)){
				this.pushError("data.bizRegNum1","사업자등록번호",["ICMN0001"]);
			}
			else if(!mvno.etc.checkPackNum(data.bizRegNum1, data.bizRegNum2 , data.bizRegNum3)){
				this.pushError("data.bizRegNum1","사업자등록번호",["ICMN0011"]);
			}
			else if((!data.corpRegNum1 && data.corpRegNum2) || (data.corpRegNum1 && !data.corpRegNum2)){
				this.pushError("data.corpRegNum1","법인등록번호",["ICMN0011"]);
			}
			else if(data.corpRegNum1 && data.corpRegNum2 && !mvno.etc.checkCorpRegNum(data.corpRegNum1 + data.corpRegNum2)){
				this.pushError("data.corpRegNum1","법인등록번호",["ICMN0012"]);
			}
			/* else if((!data.rprsenRrnum1 && data.rprsenRrnum2) || (data.rprsenRrnum1 && !data.rprsenRrnum2)){
				this.pushError("data.rprsenRrnum1","대표자주민번호",["ICMN0011"]);
			}
			else if(data.rprsenRrnum1 && data.rprsenRrnum2 && !mvno.etc.checkRrNum(data.rprsenRrnum1 + data.rprsenRrnum2)){
				this.pushError("data.rprsenRrnum1","대표자주민번호",["ICMN0012"]);
			}
			else if(!mvno.etc.checkPackNum(data.telnum1, data.telnum2 , data.telnum3)){
				this.pushError("data.telnum2","대표전화번호",["ICMN0011"]);
			}
			else if(data.telnum1 && !mvno.etc.checkPhoneNumber(data.telnum1+data.telnum2+data.telnum3)){
				this.pushError("data.telnum2","대표전화번호",["ICMN0012"]);
			}
			else if(agncyFlag && (!data.fax1 || !data.fax2 || !data.fax3)){
				this.pushError("data.fax1","FAX",["ORGN0010"]);
			}
			else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
				this.pushError("data.fax2","FAX",["ICMN0011"]);
			}
			else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
				this.pushError("data.fax2","FAX",["ICMN0012"]);
			} */
			else if(agncyFlag && !data.respnPrsnId){
				this.pushError("data.hghrOrgnCd","담당자이름",["ORGN0012"]);
			}
		}
	}
	//조직 이력 리스트
	,GRID2: {
		css: {
			height: "240px",
			width: "951px"
		},
		header : "순번,조직명,상위조직명,조직유형,조직레벨,조직상태,대리점권역,대리점유형,대리점유형상세,채널상세,사업자구분,사업자등록번호,법인등록번호,담당자명,시작일,종료일,수정자,수정일시", //17
		columnIds : "hstSeq,orgnNm,hghrOrgnNm,typeNm,orgnLvlNm,orgnStatNm,orgnGrdNm,typeDtlCdNm1,typeDtlCdNm2,typeDtlCdNm3,bizTypeNm,bizRegNum,corpRegNum,respnPrsnNm,applStrtDt,applCmpltDt,rvisnNm,rvisnDttm",//17
		colAlign : "center,left,left,center,center,center,center,center,center,left,center,center,center,center,center,center,center,center",//17
		colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXr,roXr,ro,roXd,roXd,ro,roXdt",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "80,150,150,100,100,100,100,100,100,100,100,100,100,100,100,100,100,130",
		paging:true,
		onRowDblClicked : function(rowId, celInd) {
		}
	}
	//조직 선불 정보
	,FORM21: {
		items: _FORMITEMS_['form21'],
		buttons: {
			right: {
				//btnReg: { label: "등록"},
				btnMod: { label: "수정"}
			}
		},
		onButtonClick : function(btnId) {
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			switch (btnId) {
				case "btnMod":
					var data = PAGE.FORM21.getFormData(true);
					mvno.ui.createForm("FORM23");
					
					var lovCode="PPS_0078";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM23, "agentDocFlag");
					
					PAGE.FORM23.setFormData(data);
					PAGE.FORM23.setItemValue("opCode", "EDT" );
					PAGE.FORM23.setItemValue("rentalFee", "0" );
					
					PAGE.FORM23.disableItem("agentId");
					PAGE.FORM23.disableItem("agentNm");
					PAGE.FORM23.disableItem("adminId");
					PAGE.FORM23.disableItem("deposit");
					PAGE.FORM23.disableItem("virAccountId");
					PAGE.FORM23.disableItem("recordDate");
					
					PAGE.FORM23.clearChanged();
					
					mvno.ui.popupWindowById("FORM23", "등록화면", 840, 370, {
						onClose: function(win) {
							if (! PAGE.FORM23.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
					break;
			}
		}
	}
	// 선불 조직 정보 수정 팝업
	,FORM23: {
		items: _FORMITEMS_['form23'],
		buttons: {
			right: {
				btnMod: { label: "수정"},
				btnClose: { label: "닫기"}
			}
		},
		onButtonClick : function(btnId) {
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			
			switch (btnId) {
				case "btnMod":
					var url = ROOT +"/org/orgmgmt/ppsOrgnChgProc.json";
					mvno.cmn.ajax(
								url,
								form,
								function(results) {
									console.log(results);
									var result = results.result;
									var rCode = result.oRetCd;
									var msg = result.oRetMsg;
									if(rCode == "0000") {
										PAGE.FORM21.setItemValue("dataRate",result.dataRate);
										PAGE.FORM21.setItemValue("depositRate",result.depositRate);
										PAGE.FORM21.setItemValue("pps35DepositRate",result.pps35DepositRate);
										PAGE.FORM21.setItemValue("pinBuyRate",result.pinBuyRate);
										PAGE.FORM21.setItemValue("rentalFee",result.rentalFee);
										PAGE.FORM21.setItemValue("ktAgencyId",result.ktAgencyId);
										PAGE.FORM21.setItemValue("agentDocFlag",result.agentDocFlag);
										mvno.ui.closeWindowById(form, true);
									}else{
										mvno.ui.notify(msg);
									}
								});
					break;
				case "btnClose":
					mvno.ui.closeWindowById(form);
					break;
				case "btnVACChange":
					mvno.ui.createForm("FORM24");
					
					var data = PAGE.FORM23.getFormData(true);
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM24, "virBankNm");
					
					PAGE.FORM24.setItemValue("acntnum", data.virAccountId);
					PAGE.FORM24.setItemValue("bankcd", data.virBankNm);
					PAGE.FORM24.setItemValue("virBankNm", data.virBankCd);
					PAGE.FORM24.setItemValue("orgOrgnId", data.agentId);
					
					mvno.ui.popupWindowById("FORM24", "가상계좌변경", 480, 320, {
						onClose: function(win) {
							if (! PAGE.FORM24.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
					break;
				case "btnDepositChange":
					var orgnId = form.getItemValue("agentId");
					goAgentDepositList(orgnId,"BONSA");
					break;
			}
		}
	}
	// 선불가상계좌
	,FORM24 : {
		items : [
			{type: 'hidden', name: 'contractNum', value: ''}
			,{type: 'hidden', name: 'bankName', value: ''}
			,{type: 'hidden', name: 'orgOrgnId', value: ''}
			,{type: "fieldset", label: "가상계좌정보", inputWidth:400, height : 50, offsetRight:5, list:[
				{type: 'settings', position: 'label-left', labelWidth: 160, inputWidth: 'left'}
				,{type:"block", list:[
					{type: 'settings', position: 'label-left',inputWidth: 'left'}
					,{type:"input", label:"은행명", name: "bankcd", labelWidth:100, width:150, labelAlign : "left"}
					,{type:"input", label:"계좌번호", name: "acntnum", labelWidth:100, width:150, labelAlign : "left",validate:"ValidNumeric"}
				]}
			]}
			,{type: "fieldset", label: "가상계좌변경", inputWidth:400, height : 50, offsetLeft:5, list:[
				{type: 'settings', position: 'label-left', inputWidth: 'left'},
				,{type:"block", list:[
					{type : "select", label:"은행선택", name : "virBankNm",  labelWidth: 100, width:150, labelAlign : "left"},
					,{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:12, blockOffset:0}
						,{type:"label", label:""}
					]}//공백처리
					,{type:"newcolumn"}
					,{type:"button", value:"변경", name:"btnVacChange"}
				]}
			]}
		],
		buttons: {
			center: {
				btnClose: { label: "닫기"}
			}
		}
		,onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnVacChange":
					var data = this.getFormData(true);
					var op_code_str ="CHANGE";
					var op_mode_str = 'A';
					var user_type_str = 'A';
					//var user_number_str=PAGE.FORM2.getItemValue("contractNum");
					var req_bank_name_str = data.virBankNm;
					var req_org_orgn_id = data.orgOrgnId;
					var comment = '선택한 은행으로 가상계좌를 변경 하시겠습니까?';
					
					if(req_bank_name_str == "")
					{
						mvno.ui.alert("은행을 선택하세요.");
						return;
					}
					
					if(confirm(comment))
					{
						var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
						mvno.cmn.ajax(
									url,
									{
										opCode : op_code_str,
										opMode : op_mode_str,
										userType : user_type_str,
										reqBankCode : req_bank_name_str,
										orgOrgnId : req_org_orgn_id
									}
									, function(results) {
										var result = results.result;
										var retCode = result.retCode;
										var msg = result.retMsg;
										mvno.ui.notify(msg);
										
										if(retCode == "0000") {
											var url = ROOT +"/org/orgmgmt/ppsOrgnInfo.json";
											var selectedOrgnId = PAGE.FORM24.getItemValue("orgOrgnId");
											
											mvno.cmn.ajax(
														url,
														{
															orgnId : selectedOrgnId
														}
														, function(results) {
															var result = results.result;
															var retCode = result.code;
															var msg = result.retMsg;
															var data = result.data;
															
															if(retCode == "OK") {
																if(data.virAccountId!=null&& data.virAccountId!="")
																{
																	PAGE.FORM24.setItemValue("acntnum",data.virAccountId);
																	PAGE.FORM21.setItemValue("virAccountId",data.virAccountId);
																	PAGE.FORM23.setItemValue("virAccountId",data.virAccountId);
																}
																
																if(data.virBankNm!=null && data.virBankNm!="")
																{
																	PAGE.FORM24.setItemValue("bankcd",data.virBankNm);
																}
																
																if(data.virBankCd!=null && data.virBankCd!="")
																{
																	PAGE.FORM24.setItemValue("virBankNm",data.virBankCd);
																}
															}else{
																mvno.ui.notify(msg);
															}
														});
										}
									});
					}
					break;
				case"btnClose":
					mvno.ui.closeWindowById(form, true);
					break;
			}
		}
	}
	//판매점 정보
// 	,GRID9: {
// 		css: {
// 			height: "285px",
// 			width: "951px"
// 		},
// 		header : "조직ID,조직명,대표자,사업자등록번호,조직상태,전화번호,FAX번호,매핑시작일시,매핑종료일시,적용시작일자,적용종료일자", //11
// 		columnIds : "orgnId,orgnNm,rprsenNm,bizRegNum,orgnStatNm,telnum,fax,relStrtDt,relEndDt,applStrtDt,applCmpltDt",//11
// 		colAlign : "center,left,left,center,center,center,center,center,center,center,center",//11
// 		colTypes : "ro,ro,ro,roXr,ro,roXp,roXp,roXdt,roXdt,roXd,roXd",
// 		colSorting : "str,str,str,str,str,str,str,str,str,,str,str",
// 		widths : "100,150,80,120,80,100,100,130,130,120,120",
// 		paging:true,
// 		onRowDblClicked : function(rowId, celInd) {
// 		}
// 	}
};

var PAGE = {
	title: "${breadCrumb.title}",
	breadcrumb: "${breadCrumb.breadCrumb}",
	buttonAuth: ${buttonAuth.jsonString},
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createTabbar("TABBAR1");
		
		//조직레벨
		mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/listCmnCdOrgnLvl.json", {orgnLvlCd:"${loginInfo.userOrgnLvlCd}"}, PAGE.FORM1, "orgnLvlCd");
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0104"}, PAGE.FORM1, "typeCd");
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0006"}, PAGE.FORM1, "orgnLvlCd");
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0005"}, PAGE.FORM1, "orgnStatCd");
		PAGE.FORM1.setItemValue("userOrgnLvlCd" , "${loginInfo.userOrgnLvlCd}");
		//조직유형
		//mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/listCmnCdOrgnType.json", {orgnLvlCd:"${loginInfo.userOrgnLvlCd}"}, PAGE.FORM1, "typeCd");
	}
};
	
/**
 * 데이터에 하이픈(-) 추가.
 * param : form object
 * param : grid select data object
 * param : item name string
 * param : parsing type (rrnum, bizRegNum, tel)
 */
function setLabelAddHyphen(form, data, itemname, type){
	if(!form) return null;
	if(data == null) return null;
	if(!itemname) return data;
	
	var retValue  = "";
	
	if(type == "rrnum"){
		retValue = mvno.etc.setRrnumHyphen(data[itemname]);
	} else if(type == "bizRegNum"){
		retValue = mvno.etc.setBizRegNumHyphen(data[itemname]);
	} else if(type == "tel"){
		retValue = mvno.etc.setTelNumHyphen(data[itemname]);
	}
	
	form.setItemValue(itemname,retValue);
}

/**
 *하이픈(-)들어가는 데이터 input box에 셋팅
 * param : form object
 * param : grid select data object
 * param : item name string
 * param : parsing type (rrnum, bizRegNum, tel)
 */
function setInputDataAddHyphen(form, data, itemname, type){
	if(!form) return null;
	if(data == null) return null;
	if(!itemname) return data;
	
	var retValue  = "";
	
	if(type == "rrnum"){
		retValue = mvno.etc.setRrnumHyphen(data[itemname]);
	} else if(type == "bizRegNum"){
		retValue = mvno.etc.setBizRegNumHyphen(data[itemname]);
	} else if(type == "tel"){
		retValue = mvno.etc.setTelNumHyphen(data[itemname]);
	}
	var arrStr = retValue.split("-");
	var cnt = 1;
	for(i = 0; i < arrStr.length; i++){
		 eval("form.setItemValue('"+itemname+cnt+"','"+arrStr[i]+"');");
		 cnt++;
	}
}

/**
 * 조직의 조직상태에 따라 버튼 표시여부
 * param : section name (form, grid)
 */
function showBtn4OrgnStat(sectionName){
	if(PAGE.GRID1.getSelectedRowData() == null) return;
	if(PAGE.GRID1.getSelectedRowData().orgnStatCd == "1" || PAGE.GRID1.getSelectedRowData().orgnStatCd == "4"){
		//활성화
		if(sectionName == "FORM2"){
			/* mvno.ui.showButton("FORM2" , "btnUpExcel");
			mvno.ui.showButton("FORM2" , "btnDnExcel"); */
			mvno.ui.showButton("FORM2" , "btnMod");
		} else {
			mvno.ui.showButton(sectionName , "btnReg");
			mvno.ui.showButton(sectionName , "btnMod");
			/* if(sectionName == "FORM6"){
				PAGE.FORM6.showItem("mrtgTypeCd");
			} */
		}
	} else {
		//비활성화
		if(sectionName == "FORM2"){
			/* mvno.ui.hideButton("FORM2" , "btnUpExcel");
			mvno.ui.hideButton("FORM2" , "btnDnExcel"); */
			mvno.ui.hideButton("FORM2" , "btnMod");
		} else {
			mvno.ui.hideButton(sectionName , "btnReg");
			mvno.ui.hideButton(sectionName , "btnMod");
			/* if(sectionName == "FORM6"){
				PAGE.FORM6.hideItem("mrtgTypeCd");
			} */
		}
	}
}

/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
	if(jusoGubun == "REG"){
		PAGE.FORM3.setItemValue("zipcd",zipNo);
		PAGE.FORM3.setItemValue("addr1",roadAddrPart1);
		PAGE.FORM3.setItemValue("addr2",addrDetail);
	}else if(jusoGubun == "MOD"){
		PAGE.FORM4.setItemValue("zipcd",zipNo);
		PAGE.FORM4.setItemValue("addr1",roadAddrPart1);
		PAGE.FORM4.setItemValue("addr2",addrDetail);
	}
	
	jusoGubun = "";
}

// 선불조직 예치금 상세 페이지 이동
function goAgentDepositList(agentId, createType)
{
	var url = "/pps/hdofc/orgmgmt/AgentDepositMgmtDetailForm.do";
	var title = "예치금 상세"
	var menuId = "PPS1300004";
	var myTabbar = window.parent.mainTabbar;
	if(myTabbar.tabs(url)) {
		myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
	}
	myTabbar.addTab(url, title, null, null, true);
	myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&agentId="+agentId+"&createType="+createType);
	// 잘안보여서.. 일단 살짝 Delay 처리
	setTimeout(function() {
		//mainLayout.cells("b").progressOff();
	}, 100);
}

</script>
