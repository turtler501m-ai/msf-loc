<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_cmn_bs_0005.jsp
     * @Description : 파일 이력 확인 화면
     * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2016.08.02 장익준 최초생성
     * @
     * @author : 장익준
     * @Create Date : 2016. 08. 02.
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
                         	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
                         	{type:"block", list:[
                         	                        {type:"input", label:"대리점", name: "orgnId", width:100, offsetLeft:10,maxLength:10, required:true}
                         	                       	, {type:"newcolumn"}
                         	                       	, {type:"button", value:"찾기", name:"btnOrgFind"}
                         	                       	, {type:"newcolumn"}
                         	                       	, {type:"input", name: "orgnNm", width:120,readonly: true}
                         	                        , {type: 'newcolumn'}
// 						                            , {type:"select", width:100, label:"검색구분",name:"searchGbn", userdata:{lov:"CMN0108"}, offsetLeft:21, required:true}
						                            , {type:"select", width:100, label:"검색구분",name:"searchGbn", offsetLeft:21, required:true}
						                            , {type:"newcolumn"}
						                            , {type:"input", width:100, name:"searchName",maxLength:30}
						                           ]
                         	},      

            				{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
            				{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
            				{type : "newcolumn",offset : 50},
                         	
                         	{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
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
							
							var url = ROOT + "/cmn/payinfo/fileList.json";
							
							PAGE.GRID1.list(url, form, {
								callback : function () {
								//	PAGE.FORM2.clear();
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});							
								
						//  20161021 기존소스
						//	PAGE.GRID1.list(ROOT + "/cmn/payinfo/fileList.json", form);
                           	break;
					}
			},
			onValidateMore : function (data) {
				
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
					}
				}
    		
		},

        // ----------------------------------------
		GRID1 : {
			css : {
				height : "580px"
			},
				title : "조회결과",
				header : "파일구분,계약번호,고객명,전화번호,은행명,계좌번호,파일명,등록자,등록일자", //9
				columnIds : "fileType,contractNum,subLinkName,subscriberNo,bankCdNm,bankCdNo,fileName,regstId,regstDttm",//9
				colAlign : "center,center,left,center,center,left,center,left,center",//9
				colTypes : "ro,ro,ro,roXp,ro,ro,ro,ro,roXdt",//9
				colSorting : "str,str,str,str,str,str,str,str,str",//9
				widths : "80,100,120,100,100,120,280,120,150",//9
				paging:true,
				pageSize:20,
               	onRowDblClicked : function(rowId, celInd) {
               		this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
               	},
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
               	onButtonClick : function(btnId) {
               		var form = this; // 혼란방지용으로 미리 설정 (권고)
               		
					switch (btnId) {						
						case "btnSearch":
							PAGE.GRID1.list(ROOT + "/cmn/payinfo/fileList.json", form);
                           	break;
                           	
                           
                       	case "btnRead":

                     	   	var data = PAGE.GRID1.getSelectedRowData();
                     	   	
                     	    if (data.regstDttm == null) break;
                     	    
                     	   	mvno.ui.createForm("FORM3");
                          	PAGE.FORM3.clear();
                          	
                            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9015",orderBy:"etc6"}, PAGE.FORM3, "fileTypeCd"); // 검색구분	

                          	PAGE.FORM3.setFormData(data);
                     	   	mvno.ui.popupWindowById("FORM3", "파일정보", 730, 320);
                            
                           	break;
                          	
                           	
                        case "btnDnExcel":
                        	
    						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
    						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
    						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
                        	
    						if(PAGE.GRID1.getRowsNum() == 0) {
    							mvno.ui.alert("데이터가 존재하지 않습니다.");
    							return;
    						}
    						
    						var searchData =  PAGE.FORM1.getFormData(true);
    						mvno.cmn.download(ROOT + "/cmn/payinfo/searchFileInfoEx.json?menuId=MSP1020240", true, {postData:searchData});
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
							{type: "input", name: "subLinkName", label: "고객명", width: 100, validate:"NotEmpty", maxLength: 10, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "subscriberNo", label: "전화번호", width: 100, validate: "NotEmpty", maxLength: 11, disabled:true,offsetLeft:10}
						]},
						{type: "block", list: [
// 							{type:"select", width:100, label:"파일유형",name:"fileTypeCd", userdata:{lov:"CMN0110"}, required:true, disabled:true,offsetLeft:10},
							{type:"select", width:100, label:"파일유형",name:"fileTypeCd", required:true, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "bankCdNm", label: "은행명", width: 100, validate:"NotEmpty", maxLength: 10, disabled:true,offsetLeft:10},
							{type : "newcolumn" },
							{type: "input", name: "bankCdNo", label: "계좌번호", width: 100, validate: "NotEmpty", maxLength: 11, disabled:true,offsetLeft:10}							
						]},
					]},						
					{type: "fieldset", label: "파일정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:90},
						{type:"block", list:[
							{type : "hidden",name:"fileId"},
							{type : "input" , name:"fileName", label:"파일명", width:370, disabled:true},
							{type : "newcolumn" },
							{type : "button" , name:"fileDown1", value: "다운로드"}							
						]}
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
                    case "fileDown1" :
                    	 mvno.cmn.download('/cmn/payinfo/downFileSec.json?fileId=' + PAGE.FORM3.getItemValue("fileId"));
                         break;           
                         
  
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
		        
                mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "searchGbn"); // 검색구분		        
	    	}
	};

</script>