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

// 조직 예치금 상세 페이지 이동 스크립트
function goAgentDepositList(agentId, createType)
	{
		var url = "/pps/hdofc/orgmgmt/AgentDepositMgmtDetailForm.do";
		var title = "예치금 상세"
		var menuId = "PPS1300004";

		var myTabbar = window.parent.mainTabbar;

        if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&agentId="+agentId+"&createType="+createType);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);
	}

<%@ include file="org.formitems" %>

// 주소 등록, 수정 구분
var jusoGubun = "";
var maskingYn = "${maskingYn}";				// 마스킹권한

var gMonthFristDay = "${startDate}";
        var DHX = {

            // 검색 조건
            FORM1 : {
                items : _FORMITEMS_['form1'],
                onButtonClick : function(btnId) {
                    var form = this;
                    switch (btnId) {
                        case "btnSearch":
                        	var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
        					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
        					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
        					
                            PAGE.GRID1.list(ROOT + "/pps/agency/orgmgmt/AgencyOrgMgmtList.json", form,  {
                                callback : function(){
                                	PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
                                	PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
                                }
                            });
                            
                            

                            break;
                    }
                }
            },

            // 조직 리스트 - GRID1
            GRID1 : {

                css : {
                    height : "500px"
                },
                buttons: {
                	hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					},
                    right: {
                       	  btnReg: { label: "등록"},
                       	  btnMod: { label: "수정"}
                    }
                 },
                title : "조회결과",
//                 header : "조직ID,조직명,상위조직ID,상위조직명,조직유형,조직레벨,조직상태,적용시작일,적용종료일",//9
//                 columnIds : "orgnId,orgnNm,hghrOrgnCd,hghrOrgnNm,typeNm,orgnLvlNm,orgnStatNm,applStrtDt,applCmpltDt",
//                 colAlign : "left,left,left,left,left,left,center,center,center",
//                 colTypes : "tree,ro,ro,ro,ro,ro,ro,roXd,roXd",
//                 colSorting : "str,str,str,str,str,str,str,str,str",
//                 treeId: "orgnId",
//                 widths : "174,138,90,138,70,70,70,90,90",
                
                /* header : "조직ID,조직명,상위조직ID,상위조직명,조직레벨,조직상태,적용시작일,적용종료일",//9
                columnIds : "orgnId,orgnNm,hghrOrgnCd,hghrOrgnNm,orgnLvlNm,orgnStatNm,applStrtDt,applCmpltDt",
                colAlign : "left,left,left,left,left,center,center,center",
                colTypes : "tree,ro,ro,ro,ro,ro,roXd,roXd",
                colSorting : "str,str,str,str,str,str,str,str",
                treeId: "orgnId",
                widths : "224,150,90,150,70,70,90,90",  */
                
                 header : "판매점ID,판매점명,대표자명,대표자전화,팩스,조직상태,적용시작일,적용종료일,등록일",//9
                 columnIds : "orgnId,orgnNm,rprsenNm,telnum,fax,orgnStatNm,applStrtDt,applCmpltDt,regDttm",
                 colAlign : "left,left,left,left,center,center,center,center,center",
                 colTypes : "ro,ro,ro,ro,ro,ro,roXd,roXd,roXd",
                 colSorting : "str,str,str,str,str,str,str,str,str",
                // treeId: "orgnId",
                 widths : "100,180,100,100,100,100,80,100,90",
//                 paging:true,
                onRowSelect : function(rowId, celInd) {

                    var rowData = "";
                    rowData = this.getRowData(rowId);
					
                },
                onButtonClick : function(btnId) {
                    var grid1 = PAGE.GRID1;
                    var fileLimitCnt = 3;
                    
                    switch (btnId) {
                    
                   		case "btnReg":
                       	   	mvno.ui.createForm("FORM3");
                       
	                        if(${loginInfo.sessionUserOrgnTypeCd} == "20" && ${loginInfo.sessionUserOrgnLvlCd}=="10"){
                  			
	                   			PAGE.FORM3.setItemValue("hghrOrgnCd", "${loginInfo.sessionUserOrgnId}");
	        					PAGE.FORM3.setItemValue("hghrOrgnNm", "${loginInfo.sessionUserOrgnNm}");
                  	
                   			}
	                        
	                        PAGE.FORM3.setItemValue("applCmpltDt","99991231");
	                        PAGE.FORM3.setItemValue("orgnStatCd","등록");
                      
	                        //var orgnType1="SU";
	                        //var orgnType2="SU1";
	                        //var orgnType3="1";
	                        //mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnType1.json", {orgnType1:orgnType1}, PAGE.FORM3, "typeDtlCd1");
	                      
	                        //mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeDtl.json", {orgnType2:orgnType2} , PAGE.FORM3, "typeDtlCd2");
	                        //mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeDtl.json", {orgnType3:orgnType3} , PAGE.FORM3, "typeDtlCd3");
	                      
	                        //PAGE.FORM3.setItemValue("openCrdtLoanYn","N");
	                        //PAGE.FORM3.setItemValue("typeDtlCd1","SU");
	                        //PAGE.FORM3.setItemValue("typeDtlCd2","SU1");
                       		//PAGE.FORM3.setItemValue("typeDtlCd3","");
                       
	                        PAGE.FORM3.setItemValue("hndstOrdYn", false);
	                	    PAGE.FORM3.setItemValue("hndstInvtrYn", false);
	                	    PAGE.FORM3.setItemValue("taxbillYn", false);
	                	    PAGE.FORM3.setItemValue("cmsnPrvdYn", false);
	                	    PAGE.FORM3.disableItem("hndstOrdYn");
	                	    PAGE.FORM3.disableItem("hndstInvtrYn");
	                	    PAGE.FORM3.disableItem("taxbillYn");
	                	    PAGE.FORM3.disableItem("cmsnPrvdYn");
                       
                       		PAGE.FORM3.clearChanged();
                       
	                        mvno.ui.popupWindowById("FORM3", "등록화면", 990, 500, {
	                           onClose: function(win) {
	                               if (! PAGE.FORM3.isChanged()) return true;
	                               mvno.ui.confirm("CCMN0005", function(){
	                                   win.closeForce();
	                               });
	                           }
	                        });

                       		break;
                                       
                   		case "btnMod":

	                       if(PAGE.GRID1.getSelectedRowData() == null)
	                       {
	                           mvno.ui.alert("ECMN0002");
	                           break;
	                       }
	                       
	                       mvno.ui.createForm("FORM4");
	                       var data = PAGE.GRID1.getSelectedRowData();
	                       
	                       PAGE.FORM4.setFormData(data); //수정인 경우 그리드의 값을 가져온다.
	                      
	                       /* 법인등록번호, 사업자번호, 주민번호, 전화번호, FAX , 휴대전화 */
	                       setInputDataAddHyphen(PAGE.FORM4, data, "corpRegNum", "rrnum");
	                       setInputDataAddHyphen(PAGE.FORM4, data, "rprsenRrnum", "rrnum");
	                       setInputDataAddHyphen(PAGE.FORM4, data, "bizRegNum", "bizRegNum");
	                       setInputDataAddHyphen(PAGE.FORM4, data, "telnum", "tel");
	                       setInputDataAddHyphen(PAGE.FORM4, data, "fax", "tel");
	                       setInputDataAddHyphen(PAGE.FORM4, data, "respnPrsnMblphn", "tel");
	                       
	                       var orgnType1 = PAGE.GRID1.getSelectedRowData().typeDtlCd1;
	                       var orgnType2 = PAGE.GRID1.getSelectedRowData().typeDtlCd2;
	                       var orgnType3 = PAGE.GRID1.getSelectedRowData().typeDtlCd3;
	                       
	                       mvno.cmn.ajax4lov( ROOT + "/pps/orgmgmt/selectOrgnTypeDtl.json", {orgnType1:orgnType1} , PAGE.FORM4, "typeDtlCd1");
	                       mvno.cmn.ajax4lov( ROOT + "/pps/orgmgmt/selectOrgnTypeDtl.json", {orgnType2:orgnType2} , PAGE.FORM4, "typeDtlCd2");
	                      
	                       //mvno.cmn.ajax4lov( ROOT + "/org/orgmgmt/selectOrgnTypeDtl.json", {orgnType3:orgnType3} , PAGE.FORM4, "typeDtlCd3");
	                       
	                       PAGE.FORM4.setItemValue("typeDtlCd1", PAGE.GRID1.getSelectedRowData().typeDtlCd1);
	                       PAGE.FORM4.setItemValue("typeDtlCd2", PAGE.GRID1.getSelectedRowData().typeDtlCd2);
	                       PAGE.FORM4.setItemValue("typeDtlCd3", PAGE.GRID1.getSelectedRowData().typeDtlCd3);

	                       PAGE.FORM4.clearChanged();
	                       
	                       mvno.ui.popupWindowById("FORM4", "수정화면", 990, 500, {
	                           onClose: function(win) {
	                               if (! PAGE.FORM4.isChanged()) return true;
	                               mvno.ui.confirm("CCMN0005", function(){
	                                   win.closeForce();
	                               });
	                           }
	                       });
	
	                       break;
	                       
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
							var url = "/pps/agency/orgmgmt/AgencyOrgMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS2600001",true,{postData:searchData});
							break;
                    	}
                }
            },         
            //조직 등록
            FORM3: {
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
                        	
                        	if(mvno.cmn.isEmpty("${loginInfo.sessionUserOrgnNm}") && mvno.cmn.isEmpty("${loginInfo.sessionUserOrgnId}")){
                            	mvno.ui.notify("상위대리점 아이디를 찾을 수 없습니다."); 
                            	return;
                            }
                        	
                        	mvno.cmn.ajax(ROOT + "/pps/orgmgmt/searchOrgnId.json", form, function(results) {
                        		var result = results.result;
                                var orgnId = result.orgnId;
                                form.setItemValue("orgnId", orgnId);
                                
                                mvno.cmn.ajax(ROOT + "/pps/orgmgmt/insertMgmt.json", form, function(result) {
                                	PAGE.GRID1.refresh();
                                    mvno.ui.closeWindowById(form, true);  
                                    mvno.ui.notify("NCMN0001");
                                    PAGE.FORM3.clear();
                                });
                            });
                        	
                        	
                        	//form = this;
                            
                            
                            break;
                        case "btnClose" :
                            mvno.ui.closeWindowById(form); 
                            PAGE.FORM3.clear();
                            break;
                        
                        case "btnPostFind":
                        	jusoGubun = "REG"
                            //우편번호 검색
                            mvno.ui.codefinder4ZipCd("FORM3", "zipcd", "addr1", "addr2");
                            
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
                    else if((!data.rprsenRrnum1 && data.rprsenRrnum2) || (data.rprsenRrnum1 && !data.rprsenRrnum2)){
                        
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
                    }
                }
            },

            //조직 수정
            FORM4: {
                items: _FORMITEMS_['form4'],
                buttons: {
                    center: {
                        btnMod: { label: "수정" },
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
                        	
                            mvno.cmn.ajax(ROOT + "/pps/orgmgmt/updateMgmt.json", form, function(result) {
                            	PAGE.GRID1.refresh();
                                mvno.ui.closeWindowById(form, true);  
                                mvno.ui.notify("NCMN0002");
                                PAGE.FORM4.clear();
                            });
                            
                            break;
                        case "btnClose" :
                            mvno.ui.closeWindowById(form);   
                            break;
                        
                        case "btnPostFind":
                        	jusoGubun = "MOD"
                            mvno.ui.codefinder4ZipCd("FORM4", "zipcd", "addr1", "addr2");
                            
                            break;                              
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
                    else if((!data.rprsenRrnum1 && data.rprsenRrnum2) || (data.rprsenRrnum1 && !data.rprsenRrnum2)){
                        
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
                //mvno.ui.createTabbar("TABBAR1");
		          if (maskingYn != "Y") {
		              mvno.ui.disableButton("GRID1", "btnMod");
		          }
                
                setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");
                
                var lovCode2="PPS0068";
                mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchType");

                
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

            var cnt = 0;

            for(var i = 0; i < arrStr.length; i++){
                cnt = i + 1;
                form.setItemValue(itemname + '' + cnt, arrStr[i]);
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
</script>
