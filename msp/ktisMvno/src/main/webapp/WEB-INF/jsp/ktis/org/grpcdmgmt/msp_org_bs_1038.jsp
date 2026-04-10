<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1038.jsp
	 * @Description : 공통 코드 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.28 고은정 최초생성
	 * @ 
	 * @author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div class="row" ><!-- 2단 이상 구성시 class="row" div 추가 -->
       <div id="GRID1" class="c5"></div><!-- c3=30%, c5=50% -->
       <div id="FORM2" class="c5 section-box" style="margin-top:25px;"></div>
   </div>
   <div class="row" ><!-- 2단 이상 구성시 class="row" div 추가 -->
       <div id="GRID2" class="c5"></div><!-- c3=30%, c5=50% -->
       <div id="FORM3" class="c5 section-box" style="margin-top:25px;"></div>
   </div>
<!--    <div id="FORM4" class="section-pagescope"> -->
   
<!-- Script -->
<script type="text/javascript">
 var DHX = {
            //------------------------------------------------------------
            FORM1 : {
                items : [
                   {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},              
                   {type : "input",label : "그룹명",name : "grpKorNm" ,width:100, value:'', offsetLeft:10, labelWidth:40 },
                   {type: 'newcolumn'},
//                    {type : "select",label : "업무구분",name : "dutySctn", width:100 ,offsetLeft:30, userdata:{lov:'CMN0023'}, labelWidth:50},
                   {type : "select",label : "업무구분",name : "dutySctn", width:100 ,offsetLeft:30, labelWidth:50},
                   {type: 'newcolumn'},
//                    {type: 'select', name: 'usgYn', label: '사용여부', userdata:{lov: 'CMN0011'} ,width:100,offsetLeft:30, labelWidth:50},
                   {type: 'select', name: 'usgYn', label: '사용여부' ,width:100,offsetLeft:30, labelWidth:50}, 
                   {type: 'newcolumn', offset:220},
                   {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
               ],
			onButtonClick : function(btnId) {

                   var form = this;

                   switch (btnId) {

                       case "btnSearch":
                    	   PAGE.FORM2.clear();
                           PAGE.GRID2.clearAll();
                           PAGE.FORM3.clear();
                           
                           PAGE.GRID1.list(ROOT + "/org/grpcdmgmt/listGrpMgmt.json", form);
                           break;
                   }

               }
           },
				

        // ----------------------------------------
           GRID1 : {
            css : {
                height : "260px"
            },
            title : "그룹코드 조회결과",
			header : "그룹ID,그룹한글명,그룹영문명,컬럼한글명,컬럼영문명,업무구분,사용여부,그룹설명,비고",
			columnIds : "grpId,grpKorNm,grpEngNm,colKorNm,colEngNm,dutySctnNm,usgYn,grpDsc,remrk",
			widths : "100,120,120,100,100,80,80,150,100",
			colAlign : "center,left,left,left,left,center,center,left,left",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str",
			onRowSelect : function(rowId, celInd) {
			    var rowData = this.getRowData(rowId);

                //그룹코드 선택 시 상세코드리스트 호출
			    PAGE.GRID2.list(ROOT + "/org/grpcdmgmt/listGrpCdMgmt.json", {grpId:rowData.grpId});
			    PAGE.FORM2.setFormData(rowData);
			    PAGE.FORM3.clear();
			    PAGE.FORM3.setItemValue("grpId",rowData.grpId);
			    
               }
           },
           
		// --------------------------------------------------------------------------
		//그룹코드 등록,수정 입력폼
           FORM2 : {
               title : "",
               items : [
                 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0},
                 {type: "block", blockOffset: 0, list: [
                 {type: 'input', name: 'grpId', label: '그룹ID',  width: 120,  value: '' , required: true, maxLength:10},
                 {type: "newcolumn"},
                 {type: 'button', value: '중복', name: 'btnExist'} ,  
                 {type: 'hidden', value: '0', name: 'btnExistFlag'}]},                 
                 {type: "block", blockOffset: 0, list: [
                 {type: 'input', name: 'grpKorNm', label: '그룹한글명',  width: 120,  value: '' , required: true, maxLength:20},
                 {type: "newcolumn"},
                 {type: 'input', name: 'grpEngNm', label: '그룹영문명',  width: 120, offsetLeft: 30, value: '' , maxLength:20}   ]},
                 {type: "block", blockOffset: 0, list: [
                 {type: 'input', name: 'colKorNm', label: '컬럼한글명',  width: 120,  value: '', maxLength:20 },
                 {type: "newcolumn"},
                 {type: 'input', name: 'colEngNm', label: '컬럼영문명',  width: 120, offsetLeft: 30, value:'' ,maxLength:20},   ]},
                 {type: "block", blockOffset: 0, list: [
//                  {type: 'select', name: 'dutySctn', label: '업무구분',  width: 120,  userdata:{lov:'CMN0023'}, required: true},
                 {type: 'select', name: 'dutySctn', label: '업무구분',  width: 120, required: true},
                 {type: "newcolumn"},
//                  {type: 'select', name: 'usgYn', label: '사용여부',  width: 120, offsetLeft: 30, userdata:{ lov:'CMN0011'} , required: true}   ]},
                 {type: 'select', name: 'usgYn', label: '사용여부',  width: 120, offsetLeft: 30, required: true}   ]},  
                 {type: 'input', name: 'grpDsc', label: '그룹설명',  width: 354, value: '' , maxLength:30},
                 {type: 'input', name: 'remrk', label: '비고',  width: 354,  value: '' , maxLength:30},
                ],

                buttons : {
	               	left : {
	               		btnInit : {label : "초기화"}
	               	},
                   right : {
                       btnReg : { label : "등록" },
                       btnMod : { label : "수정" }
//                        btnDel : { label : "삭제" }
                   }
               },
               onButtonClick : function(btnId) {

                   var form = this; 
                   
                   switch (btnId) {
                   case "btnExist":
	                  //그룹코드ID 중복체크
                      if(PAGE.FORM2.getItemValue("grpId") != ""){
	                       mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/isExistGrpCdMgmt.json", {grpId:PAGE.FORM2.getItemValue("grpId")}, function(resultData) {
		                       if(resultData.result.resultCnt > 0){
			                       //중복
		                           mvno.ui.alert("ICMN0008");
		                           form.setItemValue("btnExistFlag","0"); 
		                       }else{
			                       //사용가능
		                           mvno.ui.alert("ICMN0007");
			                       form.setItemValue("btnExistFlag","1");
			                   } 
	                       });
                      } else {
                          mvno.ui.alert("그룹ID를 입력하세요.");
	                   }
                      break;                   
                       case "btnInit":
                    	    PAGE.FORM2.clear();
                    	    PAGE.GRID2.clearAll();
                    	    PAGE.FORM3.clear();
                    	    break;
                	   
                       case "btnReg":
	                       	mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/existKorNmGrpMgmt.json", form, function(resultData) {
	                       		var result = resultData.result;
	                       		if(result.data > 0){
	                       		    mvno.ui.alert("ICMN0009");
	                       		} else {
	                       			mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/insertGrpMgmt.json", form, function(result) {
	                                       mvno.ui.notify("NCMN0001");
	                                       PAGE.GRID1.refresh();
	                                       PAGE.FORM2.clear();
	                                   });		
	                       		}
	                           });
                           
                           break;

                       case "btnMod" :
                           if(PAGE.GRID1.getSelectedRowData() != null){
                            mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/updateGrpMgmt.json", form, function(result) {
                                mvno.ui.notify("NCMN0002");
                                PAGE.GRID1.refresh();
                                PAGE.FORM2.clear();
                            });
                           } else {
                               mvno.ui.notify("ICMN0005");
                           }    
                           break;        
                                             
                       case "btnDel":
                           if(PAGE.GRID1.getSelectedRowData() != null){
                            mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/org/grpcdmgmt/deleteGrpMgmt.json", {
                                grpId : PAGE.GRID1.getSelectedRowData().grpId
                            }, function(result) {
                                mvno.ui.notify("NCMN0003");
                                PAGE.GRID1.refresh();
                                PAGE.FORM2.clear();
                                PAGE.GRID2.refresh();
                                   PAGE.FORM3.clear();
                            });
                           } else {
                               mvno.ui.notify("ICMN0005");
                           }   
                           break;

                   }

               },
               onChange : function(name, value){
            	    
            	   if(name == "grpId"){
            		   PAGE.FORM2.setItemValue("btnExistFlag","0");
            	   }
               },
               onValidateMore : function (data){
            	   var form = this; 
                   if(data.btnExistFlag == "0"){
                       this.pushError("data.btnExistFlag","그룹ID",["ICMN0010"]);
                   } else if(!data.grpEngNm || !mvno.etc.validEng(data.grpEngNm)){
                	   this.pushError("data.grpEngNm","그룹영문명","영문만 입력하세요");
                   } else if(data.colEngNm && !mvno.etc.validEng(data.colEngNm)){
                	   
                       this.pushError("data.colEngNm","컬럼영문명","영문만 입력하세요");
                   }
               }
           },

        // --------------------------------------------------------------------------
        //상세코드 리스트
           GRID2 : {
               css : {
                   height : "260px"
               },
               title : "상세코드 조회결과",
               header : "그룹ID,코드값,코드내용,사용여부,기타1,기타2,기타3,기타4,기타5",
               columnIds : "grpId,cdVal,cdDsc,usgYn,etc1,etc2,etc3,etc4,etc5",
               widths : "100,70,120,80,100,100,100,100,100",
               colAlign : "center,left,left,center,left,left,left,left,left",
            colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
            colSorting : "str,str,str,str,str,str,str,str,str",
               onRowSelect : function(rowId, celInd) {
                   var rowData = this.getRowData(rowId);
                   
                   PAGE.FORM3.setFormData(rowData);
                   PAGE.FORM3.setItemValue("cdValOld",rowData.cdVal);
               }
           },

        // --------------------------------------------------------------------------
        //상세코드 등록,수정 입력폼
           FORM3 : {
               title : "",
               items : [
                    {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0},
                    {type: "block", blockOffset: 0, list: [
                    {type: 'input', name: 'grpId', label: '그룹ID',  width: 120,  value: '' ,disabled:true},
                    {type: "newcolumn"},
                    {type: 'input', name: 'cdVal', label: '코드값',  width: 122, offsetLeft: 24, value: '' , maxLength:20,validate: 'NotEmpty', required: true},   
                    {type: 'hidden', name: 'cdValOld', label: '이전코드값',  value: ''}
                    ]},
                    {type: 'input', name: 'cdDsc', label: '코드내용',  width: 350,  value: '', maxLength:30,required: true, validate: 'NotEmpty' },
                    {type: 'input', name: 'etc1', label: '기타1',  width: 350,  value: '', maxLength:30 },
                    {type: 'input', name: 'etc2', label: '기타2',  width: 350,  value: '', maxLength:30 },
                    {type: 'input', name: 'etc3', label: '기타3',  width: 350,  value: '', maxLength:30 },
                    {type: 'input', name: 'etc4', label: '기타4',  width: 350,  value: '', maxLength:30 },
                    {type: 'input', name: 'etc5', label: '기타5',  width: 350,  value: '', maxLength:30 },
//                     {type: 'select', name: 'usgYn', label: '사용여부',  width: 120, userdata:{ lov:'CMN0011'}, required: true }
                    {type: 'select', name: 'usgYn', label: '사용여부',  width: 120, required: true }
                ],

                buttons : {
                   left : {
                	   btnInit : {label : "초기화"}
                   },
                   right : {
                       btnReg : { label : "등록" },
                       btnMod : { label : "수정" }
//                        btnDel : { label : "삭제" }
                   }
               },
               onButtonClick : function(btnId) {

                   var form = this; 
                   
                   switch (btnId) {
	                   case "btnInit":
	                	   PAGE.FORM3.setItemValue("cdDsc");
	                       PAGE.FORM3.setItemValue("cdVal");
	                       PAGE.FORM3.setItemValue("cdValOld");
	                       PAGE.FORM3.setItemValue("etc1");
	                       PAGE.FORM3.setItemValue("etc2");
	                       PAGE.FORM3.setItemValue("etc3");
	                       PAGE.FORM3.setItemValue("etc4");
	                       PAGE.FORM3.setItemValue("etc5");
	                       break;
	                       
                       case "btnReg":
                           if(PAGE.GRID1.getSelectedRowData() != null){
                            mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/insertGrpCdMgmt.json", form, function(result) {
                                mvno.ui.notify("NCMN0001");
                                PAGE.GRID2.refresh();
                                //PAGE.FORM3.clear();
                            });
                           } else {
                               mvno.ui.notify("ICMN0005");
                           }
                           break;

                       case "btnMod" :
                           if(PAGE.GRID2.getSelectedRowData() != null){
                            mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/updateGrpCdMgmt.json", form, function(result) {
                                mvno.ui.notify("NCMN0002");
                                PAGE.GRID2.refresh();
                                PAGE.FORM3.clear();
                            });
                           } else {
                               mvno.ui.notify("ICMN0006");
                           }
                           break;        
                                             
                       case "btnDel":
                           if(PAGE.GRID2.getSelectedRowData() != null){
                            mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/org/grpcdmgmt/deleteGrpCdMgmt.json", {
                                grpId : PAGE.GRID2.getSelectedRowData().grpId, cdVal: PAGE.GRID2.getSelectedRowData().cdVal	                                
                            }, function(result) {
                                mvno.ui.notify("NCMN0003");
                                PAGE.GRID2.refresh();
                                PAGE.FORM3.clear();
                            });
                           } else {
                               mvno.ui.notify("ICMN0006");
                           }
                           break;

                   }

               }
           },
           FORM4 : {
               title : "",
                buttons : {
                   left : {
                       btnLOV : { label : "코드반영" }
                   }
               },
               onButtonClick : function(btnId) {

                   var form = this; 
                   
                   switch (btnId) {

                       case "btnLOV":
                       	mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/fileWriteLOV.json", "", function(result) {
                       		console.log(result);
                       		mvno.ui.notify("NCMN0006");
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

            mvno.ui.createForm("FORM1");
            mvno.ui.createForm("FORM2");
            mvno.ui.createForm("FORM3");
            //mvno.ui.createForm("FORM4");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createGrid("GRID2");
            
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0023"}, PAGE.FORM1, "dutySctn"); // 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM1, "usgYn"); // 사용여부
            
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0023"}, PAGE.FORM2, "dutySctn"); // 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM2, "usgYn"); // 사용여부

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM3, "usgYn"); // 사용여부
			
        }

    };
    
</script>