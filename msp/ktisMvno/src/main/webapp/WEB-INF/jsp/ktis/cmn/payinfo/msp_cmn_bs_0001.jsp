<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_cmn_bs_0001.jsp
     * @Description : 오프라인 대리점 및 변경 고객에 대한 CMS 동의서 관리 화면
     * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2016.07.11 장익준 최초생성
     * @
     * @author : 장익준
     * @Create Date : 2016. 07. 11.
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

    var DHX = {
            //------------------------------------------------------------
            FORM1 : {
                items : [
                         	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
                         	, {type:"block", list:[
// 												   {type:"select", width:100, label:"일자구분",name:"dateGbn", userdata:{lov:"CMN0109"}, offsetLeft:10, required:true}
												   {type:"select", width:100, label:"일자구분",name:"dateGbn", offsetLeft:10, required:true}
												   , {type: 'newcolumn'}
												   , {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
                         	                       , {type: 'newcolumn'}
                         	                       , {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
                         	                       , {type:"newcolumn"}
//                          	                       , {type:"select", width:100, offsetLeft:12, label:"등록구분",name:"regYn", userdata:{lov:"CMN0107"}}
                         	                       , {type:"select", width:100, offsetLeft:12, label:"등록구분",name:"regYn"}
						                           ]
                         	},
                         	{type:"block", list:[
                         	                        {type:"input", label:"대리점", name:"orgnId", width:100, offsetLeft:10, maxLength:10}
                         	                       	, {type:"newcolumn"}
                         	                       	, {type:"button", value:"찾기", name:"btnOrgFind"}
                         	                       	, {type:"newcolumn"}
                         	                       	, {type:"input", name:"orgnNm", width:100, readonly: true}
                         	                        , {type: 'newcolumn'}
// 						                            , {type:"select", width:100, label:"검색구분",name:"searchGbn", userdata:{lov:"CMN0108"}, offsetLeft:91}
						                            , {type:"select", width:100, label:"검색구분",name:"searchGbn", offsetLeft:91}
						                            , {type:"newcolumn"}
						                            , {type:"input", width:100, name:"searchName",maxLength:30}
						                           ]
                         	},                         	
                         	{type : 'hidden', name: "DWNLD_RSN", value:"" , placeholder:"검색조건을 이용하여 엑셀자료를 생성합니다."}, //엑셀다운로드 사유 2018-03-08 권오승
            				{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
            				{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
            				{type : "newcolumn",offset : 50},    
                         	
            				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
               ],
               onButtonClick : function(btnId) {

                   var form = this;

                   switch (btnId) {
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;
							
						case "btnSearch":
							
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							if (!this.validate()) return;							
							
							var url = ROOT + "/cmn/payinfo/orgpayinfo.json";
							
							PAGE.GRID1.list(url, form, {
								callback : function () {
								//	PAGE.FORM2.clear();
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});							
								
						// 20161021 기존소스
						// PAGE.GRID1.list(ROOT + "/cmn/payinfo/orgpayinfo.json", form);
                           	break;
					}
			},
			onValidateMore : function (data) {
				if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
					this.pushError("dateGbn", "일자구분", "종료일자가 시작일보다 클수 없습니다.");
					this.resetValidateCss();
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
				
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색구분", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				} 
			},
			onChange : function(name, value) {
				var form = this;
						
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					if(value == null || value == "" ){
						var searchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchStartDt = new Date(time);
						
						// 개통일자 Enable
						PAGE.FORM1.enableItem("searchStartDt");
						PAGE.FORM1.enableItem("searchEndDt");
						
						PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
						PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
						
						PAGE.FORM1.enableItem("dateGbn");	
					} else {
						// 개통일자 Disable
						PAGE.FORM1.disableItem("searchStartDt");
						PAGE.FORM1.disableItem("searchEndDt");
						
						PAGE.FORM1.setItemValue("searchStartDt", "");
						PAGE.FORM1.setItemValue("searchEndDt", "");
						
						PAGE.FORM1.disableItem("dateGbn");	
					}
				}
			}
    		
		},

        // ----------------------------------------
        //오프라인등록리스트
		GRID1 : {
			css : {
				height : "530px"
			},
			title : "조회결과",
			header : "순번,청구번호,등록구분,등록일자,유효일자,계약번호,개통구분,상태,고객명,전화번호,생년월일,개통일자,납부방법,대리점명,주소,검증상태,검증일시,검증내용", //17
			columnIds : "kftcEvidenceSeq,ban,regYnNm,rgstDt,validDt,contractNum,onOffTypeNm,subStatusNm,custName,telNo,userSsn,lstComActvDate,blBillingMethodNm,openAgntNm,addr,approvalCdNm,approvalDt,approvalMsg",//17
			colAlign : "center,center,center,center,center,center,center,center,left,center,center,center,center,left,left,center,center,left",//17
			colTypes : "ro,ro,ro,roXd,roXd,ro,ro,ro,ro,roXp,ro,roXd,ro,ro,ro,ro,roXdt,ro",//17
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",//17
			widths : "60,60,60,80,80,80,60,80,100,100,100,100,120,250,250,80,120,250",//17
			hiddenColumns : "0,1,4",
			paging:true,
			pageSize:20,
			buttons : {
				hright : {
					//btnDnExcel : { label : "엑셀다운로드" }
					btnDnExcel : { label : "자료생성" }
				},
				right : {
					btnReg: {label: "동의서등록"}
				},				   
				left : {
					btnMod: {label: "동의서검증"}
				}
			},
            onRowDblClicked : function(rowId, celInd) {
           		this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
           	},

            onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
              		
				switch (btnId) {
					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						//대용량데이터 엑셀다운로드 변경(batch) 2018.03.08 권오승
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						if (!PAGE.FORM1.validate()) return;	
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}else {		
							var searchData =  PAGE.FORM1.getFormData(true);
							var searchGbn = searchData.searchGbn;
							var searchName = searchData.searchName;
							var regYn = searchData.regYn;
							var searchStartDt = searchData.searchStartDt;
							var searchEndDt = searchData.searchEndDt;
							var orgnId = searchData.orgnId;
							var dateGbn = searchData.dateGbn;
                            mvno.cmn.downloadCallback(function(result) {
                                PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                                mvno.cmn.ajax(ROOT + "/cmn/payinfo/getPayInfoListExcelDownload.json?menuId=MSP1020200", PAGE.FORM1.getFormData(true), function(result){
                                    mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                                });
                            });
						}				

						break;
                        
					case "btnMod":
						if(PAGE.GRID1.getSelectedRowData() == null) {
							mvno.ui.alert("ECMN0002");
							break;
						}
						if(PAGE.GRID1.getSelectedRowData().regYn == "N") {
							mvno.ui.alert("동의서등록 후 검증가능합니다.");
							break;
						}
						
						mvno.ui.createForm("FORM4");
						PAGE.FORM4.clear();
				        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0211",etc1:"1"}, PAGE.FORM4, "approvalCd");
						
						var data = PAGE.GRID1.getSelectedRowData();
						PAGE.FORM4.setFormData(data);
						
						mvno.ui.popupWindowById("FORM4", "동의서검증", 750, 200);
						break;
						
					case "btnReg":
						if(PAGE.GRID1.getSelectedRowData() == null) {
							mvno.ui.alert("ECMN0002");
                            break;
						}
						
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						mvno.cmn.ajax4lov(ROOT+"/cmn/payinfo/getRateComboList.json", "", PAGE.FORM2, "dpstBankCd");
				        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9015",orderBy:"etc6"}, PAGE.FORM2, "evidenceTypeCd"); // 파일유형
						
						var data = PAGE.GRID1.getSelectedRowData();
                        PAGE.FORM2.setFormData(data);
                        
                        var uploader = PAGE.FORM2.getUploader("file_upload");
                        uploader.revive();
                        
                        if (data.regstDttm != null) {
                        	mvno.cmn.ajax(ROOT + "/cmn/payinfo/getFile.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
                        		if(!mvno.cmn.isEmpty(resultData.result.realFileNm)) {
                        			PAGE.FORM2.setItemValue("fileId1", resultData.result.kftcEvidenceSeq);
                        			PAGE.FORM2.setItemValue("fileName1", resultData.result.realFileNm);
                        			PAGE.FORM2.setItemValue("rgstInflowCd", resultData.result.rgstInflowCd);
                        			PAGE.FORM2.setItemValue("dpstBankCd", resultData.result.dpstBankCd);
                        			PAGE.FORM2.setItemValue("dpstAcntNum", resultData.result.dpstAcntNum);
                        			PAGE.FORM2.setItemValue("dpstPrsnNm", resultData.result.dpstPrsnNm);
                        			PAGE.FORM2.setItemValue("evidenceTypeCd", resultData.result.evidenceTypeCd);
                        			PAGE.FORM2.enableItem("fileDown1");
                        			PAGE.FORM2.enableItem("fileDel1");
                        			PAGE.FORM2.hideItem("file_upload");
                        			PAGE.FORM2.enableItem("evidenceTypeCd");
                        			PAGE.FORM2.disableItem("rgstInflowCd");
                        		}
                        	});
                        	
                        	mvno.ui.popupWindowById("FORM2", "동의서등록", 730, 380, {
                        		onClose2: function(win) {
                        			uploader.reset();
                        		}
                        	});
                        } else {
                        	PAGE.FORM2.setItemValue("fileId1", "");
                        	PAGE.FORM2.setItemValue("fileName1", "");
                        	PAGE.FORM2.disableItem("fileDown1");
                        	PAGE.FORM2.disableItem("fileDel1");
                        	PAGE.FORM2.showItem("file_upload");
                        	PAGE.FORM2.enableItem("evidenceTypeCd");
                        	PAGE.FORM2.enableItem("rgstInflowCd");
                        	
                        	mvno.ui.popupWindowById("FORM2", "등록", 730, 370, {
                        		onClose2: function(win) {
                        			uploader.reset();
                        		}
                        	});
                        }
                        
                        break;
                        
					case "btnRead":
						var data = PAGE.GRID1.getSelectedRowData();
						
						if (data.regstDttm == null) break;
						
						mvno.ui.createForm("FORM3");
						PAGE.FORM3.clear();
						mvno.cmn.ajax4lov(ROOT+"/cmn/payinfo/getRateComboList.json", "", PAGE.FORM3, "dpstBankCd");
				        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9015",orderBy:"etc6"}, PAGE.FORM3, "evidenceTypeCd"); // 파일유형
						
						PAGE.FORM3.setFormData(data);
						mvno.cmn.ajax(ROOT + "/cmn/payinfo/getFile.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
							if(!mvno.cmn.isEmpty(resultData.result.realFileNm)) {
								PAGE.FORM3.setItemValue("fileId1", resultData.result.kftcEvidenceSeq);
								PAGE.FORM3.setItemValue("fileName1", resultData.result.realFileNm);
								PAGE.FORM3.setItemValue("rgstInflowCd", resultData.result.rgstInflowCd);
								PAGE.FORM3.setItemValue("dpstBankCd", resultData.result.dpstBankCd);
								PAGE.FORM3.setItemValue("dpstAcntNum", resultData.result.dpstAcntNum);
								PAGE.FORM3.setItemValue("dpstPrsnNm", resultData.result.dpstPrsnNm);
								PAGE.FORM3.setItemValue("evidenceTypeCd", resultData.result.evidenceTypeCd);
							}
						});
						
						mvno.ui.popupWindowById("FORM3", "읽기", 730, 320);
						
						break;
				}
            }
		},
        FORM2 : {
        	items : [
						{type: "fieldset", label: "기본정보", inputWidth:650, list:[
							{type:"settings", position:"label-left", labelWidth:60},
							{type: "block", list: [
								{type: "input", name: "contractNum", label: "계약번호", width: 100, validate:"NotEmpty", maxLength: 10, required:true,disabled:true,offsetLeft:10},
								{type : "newcolumn" },
								{type: "input", name: "custName", label: "고객명", width: 100, validate:"NotEmpty", maxLength: 10, required:true,disabled:true,offsetLeft:10},
								{type : "newcolumn" },
								{type: "input", name: "telNo", label: "전화번호", width: 100, validate: "NotEmpty", maxLength: 11, required:true,disabled:true,offsetLeft:10}
							]},
							{type: "block", list: [
// 								{type:"select", width:100, label:"파일유형",name:"evidenceTypeCd", userdata:{lov:"CMN0110"}, required:true, disabled:true,offsetLeft:10},
								{type:"select", width:100, label:"파일유형",name:"evidenceTypeCd", required:true, disabled:true,offsetLeft:10},
							]},
							{type:"block", list:[
								{type:"select", name:"dpstBankCd", label: "입금은행", width:100, required:true,disabled:false,offsetLeft:10}
								, {type:"newcolumn"}
								, {type:"input", name:"dpstAcntNum", label:"계좌번호", width:150, offsetLeft:10, validate: "NotEmpty,ValidNumeric", required:true, readonly:false, maxLength: 30}
								, {type:"newcolumn"}
								, {type:"input", name:"dpstPrsnNm", label:"입금자", width:100, offsetLeft:10, required:true,readonly:false, maxLength: 30}
							]},
						]},
						{type: "fieldset", label: "파일정보", inputWidth:650, list:[
							{type:"settings", position:"label-left", labelWidth:90},
							{type:"block", list:[
								{type:"hidden",name:"fileId1"},
								{type : "input" , name:"fileName1", label:"파일명", width:370, disabled:true, required:true},
								{type : "newcolumn" },
								{type : "button" , name:"fileDown1", value: "다운로드"},
								{type : "newcolumn" },
								{type : "button" , name:"fileDel1", value: "삭제"}
							]},
							{type: "block", list: [
								{type: "upload", label:"파일업로드" ,name: "file_upload", width: 535 ,inputWidth: 330 ,url: "/cmn/payinfo/fileUpLoad.do" ,autoStart: true, userdata: { limitCount : 1, limitsize: 1000, delUrl:"/cmn/payinfo/deleteFile2.json"} }						                       
							]},
							{type:"hidden",name:"startDate"},
							{type:"hidden",name:"endDate"}
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
					var fileLimitCnt = 1;

					switch (btnId) {
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/cmn/payinfo/getFileUploadChk.json", form, function(result) {
							if(result.result.chkFileUpload == "Y") {
								mvno.cmn.ajax(ROOT + "/cmn/payinfo/insertOffline.json", form, function(result) {
		                            mvno.ui.closeWindowById(form, true);  
		                            mvno.ui.notify("NCMN0015");
		                            PAGE.FORM2.clear();

		                            PAGE.GRID1.refresh();
		                        });
							} else {
								mvno.ui.alert("파일첨부 하세요.");
							}
						});
                        break;
                    case "btnClose" :
                        mvno.ui.closeWindowById(form);   
                        break;
                    case "fileDown1" :
                    	 mvno.cmn.download('/cmn/payinfo/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId1"));
                         break;
                    case "fileDel1" :
                    	mvno.ui.confirm("CCMN0008", function(){
							mvno.cmn.ajax(ROOT + "/cmn/payinfo/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId1")} , function(resultData) {
	                            mvno.ui.notify("NCMN0014");
	                            PAGE.FORM2.setItemValue("fileId1", "");
	                           	PAGE.FORM2.setItemValue("fileName1", "");
	                           	PAGE.FORM2.setItemValue("dpstBankCd", "");
	             			    PAGE.FORM2.setItemValue("dpstAcntNum", "");
	             			    PAGE.FORM2.setItemValue("dpstPrsnNm", "");
	                        	PAGE.FORM2.disableItem("fileDown1");
	                        	PAGE.FORM2.disableItem("fileDel1");
	                        	PAGE.FORM2.showItem("file_upload");
	                        	PAGE.FORM2.enableItem("evidenceTypeCd");
	                        	PAGE.FORM2.enableItem("rgstInflowCd");
	                            
	                        	PAGE.GRID1.refresh();
							});
                    	});
						break;
                	}
	           }
			},
			FORM3 : {
				items : [
					{type: "fieldset", label: "기본정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:60},
						{type: "block", list: [
							{type: "input", name: "contractNum", label: "계약번호", width: 100, validate:"NotEmpty", maxLength: 10, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "custName", label: "고객명", width: 100, validate:"NotEmpty", maxLength: 10, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "telNo", label: "전화번호", width: 100, validate: "NotEmpty", maxLength: 11, disabled:true,offsetLeft:10}
						]},
						{type: "block", list: [
// 							{type:"select", width:100, label:"파일유형",name:"evidenceTypeCd", userdata:{lov:"CMN0110"}, required:true, disabled:true,offsetLeft:10},
							{type:"select", width:100, label:"파일유형",name:"evidenceTypeCd", required:true, disabled:true,offsetLeft:10},
						]},
						{type:"block", list:[
							{type:"select", name:"dpstBankCd", label: "입금은행", width:100, disabled:true,offsetLeft:10}
							, {type:"newcolumn"}
							, {type:"input", name:"dpstAcntNum", label:"계좌번호", width:150, offsetLeft:10, disabled:true }
							, {type:"newcolumn"}
							, {type:"input", name:"dpstPrsnNm", label:"입금자", width:100, offsetLeft:10, disabled:true}
						]},
					]},						
					{type: "fieldset", label: "파일정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:90},
						{type:"block", list:[
							{type:"hidden",name:"fileId1"},
							{type : "input" , name:"fileName1", label:"파일명", width:370, disabled:true}
						]},
						{type:"hidden",name:"startDate"},
						{type:"hidden",name:"endDate"}
					]}
				],
				buttons : {
			    	center : {
			            btnClose : { label: "닫기" }
			        }
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					
					switch (btnId) {
					
                    case "btnClose" :
                        mvno.ui.closeWindowById(form);   
                        break;
                	}
	           }
			},
			FORM4 : {
				items : [
					{type: "fieldset", label: "기본정보", inputWidth:690, list:[
						{type:"settings", position:"label-left", labelWidth:60},
						{type: "block", list: [
                       
							{type: "input", name: "contractNum", label: "계약번호", width: 80, validate:"NotEmpty", maxLength: 10, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "custName", label: "고객명", width: 100, validate:"NotEmpty", maxLength: 10, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "telNo", label: "전화번호", width: 80, validate: "NotEmpty", maxLength: 11, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
//                             {type: "select", name: "approvalCd", label: "검증상태", width: 77, userdata:{lov:"CMN0113"}, offsetLeft:20, required: true},
                            {type: "select", name: "approvalCd", label: "검증상태", width: 77, offsetLeft:20, required: true},
                            {type: "hiodden", name: "kftcEvidenceSeq"}
							
						]},
						{type: "block", list: [
						  // {type: "input", name: "approvalMsg", label: "사유", width: 569, offsetLeft:10, required: true, maxLength:100}
						   {type: "input", name: "approvalMsg", label: "사유", width: 569, offsetLeft:10, maxLength:100}
					]},						
				]}],
				buttons : {
			    	center : {
						btnSave : { label : "저장" },
			            btnClose : { label: "닫기" }
			        }
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					var fileLimitCnt = 1;

					switch (btnId) {
					case "btnSave":
					//validation 체크 20180313	
					  if (!this.validate()) return;
					  
                      mvno.cmn.ajax(ROOT + "/cmn/payinfo/updateCheYn.json", form, function(result) {
                            mvno.ui.closeWindowById(form, true);  
                            mvno.ui.notify("NCMN0015");
                            PAGE.FORM4.clear();
                            
                            PAGE.GRID1.refresh();
                        });
                        break;
                    case "btnClose" :
                        mvno.ui.closeWindowById(form);   
                        break;
					}
	           },
	           //validation체크 부적격의 경우에만 사유가 필수 값 20180313 kwon
	           onValidateMore : function (data) {
					//approvalCd 0000 : 적격   //0001 : 부적격
					if( data.approvalCd == "0001" && data.approvalMsg.trim() == ""){	//부적격
						this.pushError("approvalMsg", "필수", "사유는 입력하세요.");
						this.resetValidateCss();
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
		        
		        if("${SESSION_USER_ORGN_TYPE_CD}" != "10") {
		        	PAGE.FORM1.setItemValue("orgnId", "${SESSION_USER_ORGN_ID}");
		        	PAGE.FORM1.setItemValue("orgnNm", "${SESSION_USER_ORGN_NM}");

		        	PAGE.FORM1.disableItem("btnOrgFind");
		        	PAGE.FORM1.setReadonly("orgnId", true);
		        	PAGE.FORM1.setReadonly("orgnNm", true);
		        }

		        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9014",etc1:"1"}, PAGE.FORM1, "dateGbn"); // 일자구분
		        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9012",orderBy:"etc6"}, PAGE.FORM1, "regYn"); // 등록구분
		        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "searchGbn"); //검색구분
	    	}
	};

</script>