<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1019.jsp
	 * @Description : 업체 관리
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.14 장익준 최초생성
	 * @ 2014.08.26 고은정 수정
	 * @author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
   <div id="GRID1"></div>
   <div id="FORM2" class="section-box"></div>
   <div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

	// 주소 등록, 수정 구분
	var jusoGubun = "";
	var maskingYn = "${maskingYn}";				// 마스킹권한
	
	var DHX = {
             //------------------------------------------------------------
             FORM1 : {
				items : [ 
				         {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},              
	                    {type: 'block', list: [
	                        {type : "input",label : "업체ID",name : "mnfctId" ,width:100 ,offsetLeft:10, labelWidth:40 },
	                        {type: 'newcolumn'},
	                        {type : "input",label : "업체명",name : "mnfctNm" ,width:100 ,offsetLeft:30, labelWidth:40 },
	                        {type: 'newcolumn'},
	                        {type : "select",label : "업체유형",name : "mnfctYn", width:100 ,offsetLeft:30, labelWidth:50},
	                    ]},
		                    							
	                    {type: 'newcolumn', offset:260},
	                    {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
// 	                    {type: 'newcolumn', offset:50},
// 	                    {type: 'button', value: '찾기', name: 'btnFind' , className:"btn-search1"},
					],

					onButtonClick : function(btnId) {
					    var form = this;

	                    switch (btnId) {

	                        case "btnSearch":

	                            PAGE.GRID1.list(ROOT + "/org/mnfctmgmt/mcfctMgmtList.json", form);
	                            break;
	                            
	                        case "btnFind":
	                            //제조사찾기팝업(외부호출) 테스트 코드
	                            mvno.ui.codefinder("MNFCT", function(result) {
	                                form.setItemValue("mnfctNm",       result.mnfctNm);
	                                form.setItemValue("mnfctYn",     result.mnfctYn);
	                            });
	                            break;
	                    }
					}

             },

		  // ----------------------------------------
	     GRID1 : {
	         css : {
	             height : "555px"
	         },
			title : "조회결과",
			header : "업체ID,업체명,업체유형,사업자등록번호,대표자명,전화번호,FAX번호,E-MAIL,우편번호,주소,주소상세",
			columnIds : "mnfctId,mnfctNm,mnfctYn,bizRegNum,rprsenNm,telnum,fax,email,zipcd,addr,dtlAddr",
			widths : "100,100,90,120,100,110,110,150,80,250,120",
 			colAlign : "center,left,center,left,left,left,left,left,center,left,left",
 			colTypes : "ro,ro,ro,roXr,ro,roXp,roXp,ro,roXz,ro,ro",
 			colSorting : "str,str,str,str,str,str,str,str,str,str,str",
 			paging:true,
 			pageSize:20,
			buttons : {
			    right : {
                       btnReg : { label : "등록" },
                       btnMod : { label : "수정" }
                   }
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
                // 수정버튼 누른것과 같이 처리?
                if (maskingYn == "Y") {
                    this.callEvent('onButtonClick', ['btnMod']);
                }
            },
            onButtonClick : function(btnId, selectedData) {

			    var form = this;
                   switch (btnId) {

                       case "btnReg":
                           mvno.ui.createForm("FORM2");
                           PAGE.FORM2.setFormData(selectedData);
                           
                           mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0010"}, PAGE.FORM2, "mnfctYn");
                           mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM2, "telnum1");
                           mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM2, "fax1");
                           
                           mvno.ui.popupWindowById("FORM2", "업체", 730, 280, {
                               onClose: function(win) {
                                   if (! PAGE.FORM2.isChanged()) return true;
                                   mvno.ui.confirm("CCMN0005", function(){
                                       win.closeForce();
                                   });
                               }
                           });                         
                           
                           break;
                           
                       case "btnMod":

                           mvno.ui.createForm("FORM3");
                           mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0010"}, PAGE.FORM3, "mnfctYn");
                           mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM3, "telnum1");
                           mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM3, "fax1");
                           
                           PAGE.FORM3.setFormData(selectedData);
                           
                           if(selectedData.mnfctYn == '공급사')
                           {
                        	   PAGE.FORM3.setItemValue("mnfctYn", "N");
                           }
                           else
                           {
                        	   PAGE.FORM3.setItemValue("mnfctYn", "Y");
                           }
                           
                               /* 사업자 번호, 전화번호, Fax 셋팅 */
                           setInputDataAddHyphen(PAGE.FORM3, selectedData, "telnum", "tel");
                           setInputDataAddHyphen(PAGE.FORM3, selectedData, "bizRegNum", "bizRegNum");
                           setInputDataAddHyphen(PAGE.FORM3, selectedData, "fax", "fax");
                           
                           PAGE.FORM3.clearChanged();
                           
                           mvno.ui.popupWindowById("FORM3", "업체", 730, 280, {
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
         
         // --------------------------------------------------------------------------
         //제조사 등록 팝업
         FORM2 : {
             title : "",
             items : [
                      {type: 'settings', position: 'label-left', labelWidth: '70', inputWidth: 'auto'},
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                      	{type: 'input', name: 'mnfctNm', label: '업체명',  width: 170, value: '' ,required: true,  maxLength:50},
                      	{type: "newcolumn"},
						{type: 'select', name: 'mnfctYn', label: '업체유형',  width: 170, required: true, offsetLeft: 60}
                      ]},
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
	                    {type: 'input', name: 'rprsenNm', label: '대표자명',  width: 170,  value: '' , maxLength:50},
	                    {type: "newcolumn"},
	                    {type: "block", blockOffset: 0, offsetLeft: 60, list: [
                        	{type: 'input', name: 'bizRegNum1', label: '사업자번호',  width: 50, value:'',validate: 'ValidNumeric', maxLength:3},
                            {type: "newcolumn"},
                            {type: 'input', name: 'bizRegNum2', label: '',  width: 50, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:2},
                            {type: "newcolumn"},
                            {type: 'input', name: 'bizRegNum3', label: '',  width: 62, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:5},
                        ]},
                      ]},                   
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                        {type: "block", blockOffset: 0, list: [
                           {type: 'select', name: 'telnum1', label: '전화번호',  width: 72},
                           {type: "newcolumn"},
                           {type: 'input', name: 'telnum2', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                           {type: "newcolumn"},
                           {type: 'input', name: 'telnum3', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                       ]},
                       {type: "newcolumn"},
                       {type: "block", blockOffset: 0, list: [
                           {type: 'select', name: 'fax1', label: 'FAX',  offsetLeft: 60, width: 72},
                           {type: "newcolumn"},
                           {type: 'input', name: 'fax2', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                           {type: "newcolumn"},
                           {type: 'input', name: 'fax3', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                       ]},                                                                                                                                                                                   
                      ]},
                      
                      {type: "block", blockOffset: 0, offsetLeft: 30,list: [
	                      {type: 'input', name: 'email', label: 'e-mail',  width: 170,  value: '' , maxLength:50,validate:"ValidEmail"},
	                      {type: "newcolumn"},
		                  {type: 'input', name: 'zipcd', label: '우편번호',  width: 170,  offsetLeft: 60, value: '' , disabled:true} ,
	                      {type: "newcolumn"},
	                      {type: 'button', value: '찾기', name: 'btnPostFind' } 
                      ]},
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                      {type: 'input', name: 'addr', label: '주소',  width: 200, value: '' , maxLength:200, disabled:true},
                      {type: "newcolumn"},
                      {type: 'input', name: 'dtlAddr', label: '상세주소',  width: 170, offsetLeft: 30,value: '' , maxLength:200} ]}
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

                     case "btnSave":
                         
                         mvno.cmn.ajax(ROOT + "/org/mnfctmgmt/insertMnfctMgmt.json", form, function(result) {
                             mvno.ui.closeWindowById(form, true);  
                             mvno.ui.notify("NCMN0001");
                             PAGE.GRID1.refresh();
                         });
                         break;

                     case "btnPostFind":
                    	 jusoGubun = "REG";
                         mvno.ui.codefinder4ZipCd("FORM2", "zipcd", "addr", "dtlAddr");
                         break;  
                         
                     case "btnClose" :
                         
                         mvno.ui.closeWindowById(form);   
                         break;                          

                 }

             },
             onValidateMore : function (data){
            	 if(!mvno.etc.checkPackNum(data.telnum1, data.telnum2 , data.telnum3)){
                     
                     this.pushError("data.telnum2","전화번호",["ICMN0011"]);
                 } 
            	 else if(data.telnum1 && !mvno.etc.checkPhoneNumber(data.telnum1+data.telnum2+data.telnum3)){
                     
                     this.pushError("data.telnum2","전화번호",["ICMN0012"]);
                 }
            	 else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
                     
                     this.pushError("data.fax2","FAX",["ICMN0011"]);
                 }	 
            	 else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
                     
                     this.pushError("data.fax2","FAX",["ICMN0012"]);
                 }
            	 else if(!mvno.etc.checkPackNum(data.bizRegNum1, data.bizRegNum2 , data.bizRegNum3)){
            		 
            		  this.pushError("data.bizRegNum1","사업자번호",["ICMN0011"]);
            		    
                 } 
             }
         },
         
         FORM3 : {
             title : "",
             items : [{type: 'settings', position: 'label-left', labelWidth: '70', inputWidth: 'auto'},
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                      	{type: 'input', name: 'mnfctNm', label: '업체명',  width: 170, value: '' ,required: true,  maxLength:50},
                      	{type: "newcolumn"},
						{type: 'select', name: 'mnfctYn', label: '업체유형',  width: 170, required: true, offsetLeft: 60}
                     ]},
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
	                      {type: 'input', name: 'rprsenNm', label: '대표자명',  width: 170,  value: '' , maxLength:50},
	                      {type: "newcolumn"},
	                      {type: "block", blockOffset: 0, offsetLeft: 60, list: [
                              {type: 'input', name: 'bizRegNum1', label: '사업자번호',  width: 50, value:'',validate: 'ValidNumeric', maxLength:3},
                              {type: "newcolumn"},
                              {type: 'input', name: 'bizRegNum2', label: '',  width: 50, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:2},
                              {type: "newcolumn"},
                              {type: 'input', name: 'bizRegNum3', label: '',  width: 62, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:5},
                          ]},
                      ]},                   
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                        {type: "block", blockOffset: 0, list: [
                           {type: 'select', name: 'telnum1', label: '전화번호',  width: 72},
                           {type: "newcolumn"},
                           {type: 'input', name: 'telnum2', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                           {type: "newcolumn"},
                           {type: 'input', name: 'telnum3', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                       ]},
                       {type: "newcolumn"},
                       {type: "block", blockOffset: 0, list: [
                           {type: 'select', name: 'fax1', label: 'FAX',  offsetLeft: 60, width: 72},
                           {type: "newcolumn"},
                           {type: 'input', name: 'fax2', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                           {type: "newcolumn"},
                           {type: 'input', name: 'fax3', label: '',  width: 45, offsetLeft: 0, value: '' ,validate: 'ValidNumeric', maxLength:4},
                       ]},                                                                                                                                                                                   
                      ]},
                      
                      {type: "block", blockOffset: 0, offsetLeft: 30,list: [
	                      {type: 'input', name: 'email', label: 'e-mail',  width: 170,  value: '' , maxLength:50,validate:"ValidEmail"},
	                      {type: "newcolumn"},
		                  {type: 'input', name: 'zipcd', label: '우편번호',  width: 170,  offsetLeft: 60, value: '' , disabled:true} ,
	                      {type: "newcolumn"},
	                      {type: 'button', value: '찾기', name: 'btnPostFind' } 
                      ]},
                      {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                      {type: 'input', name: 'addr', label: '주소',  width: 200, value: '' , maxLength:200, disabled:true},
                      {type: "newcolumn"},
                      {type: 'input', name: 'dtlAddr', label: '상세주소',  width: 170, offsetLeft: 30,value: '' , maxLength:200} ]}
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

                     case "btnSave":
                         
                         
                       mvno.cmn.ajax(ROOT + "/org/mnfctmgmt/updateMnfctMgmt.json", form, function(result) {
                           mvno.ui.closeWindowById(form, true);  
                           mvno.ui.notify("NCMN0002");
                           PAGE.GRID1.refresh();
                       });

                         break;

                     case "btnPostFind":
                    	 jusoGubun = "MOD";
                         mvno.ui.codefinder4ZipCd("FORM3", "zipcd", "addr", "dtlAddr");
                         break;  
                         
                     case "btnClose" :
                         
                         mvno.ui.closeWindowById(form);   
                         break;                          

                 }

             },
             onValidateMore : function (data){
            	 if(!mvno.etc.checkPackNum(data.telnum1, data.telnum2 , data.telnum3)){
                     
                     this.pushError("data.telnum2","전화번호",["ICMN0011"]);
                 } 
            	 else if(data.telnum1 && !mvno.etc.checkPhoneNumber(data.telnum1+data.telnum2+data.telnum3)){
                     
                     this.pushError("data.telnum2","전화번호",["ICMN0012"]);
                 }
            	 else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
                     
                     this.pushError("data.fax2","FAX",["ICMN0011"]);
                 }	 
            	 else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
                     
                     this.pushError("data.fax2","FAX",["ICMN0012"]);
                 }
            	 else if(!mvno.etc.checkPackNum(data.bizRegNum1, data.bizRegNum2 , data.bizRegNum3)){
            		 
            		 this.pushError("data.bizRegNum1","사업자번호",["ICMN0011"]);
            		    
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
          mvno.ui.createGrid("GRID1");
          if (maskingYn != "Y") {
              mvno.ui.disableButton("GRID1", "btnMod");
          }
          
          mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0010"}, PAGE.FORM1, "mnfctYn");

      }

  };
  
  function setInputDataAddHyphen(form, data, itemname, type){
	  
      if(!form) return null;
      if(data == null) return null;
      if(!itemname) return data;
       
       var retValue  = "";
      if(type == "fax"){
          retValue = mvno.etc.setTelNumHyphen(data[itemname]);
          
      } else if(type == "bizRegNum"){
          retValue = mvno.etc.setBizRegNumHyphen(data[itemname]);
          
      } else if(type == "tel"){
          retValue = mvno.etc.setTelNumHyphen(data[itemname]);
          
      }
      
       var arrStr = retValue.split("-");
       
       var cnt = 1;
       for(i = 0; i < arrStr.length; i++){
    	   form.setItemValue(itemname+cnt,arrStr[i]);
           cnt++;
       }
   }
  
	/* 주소 setting */
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		if(jusoGubun == "REG"){
			PAGE.FORM2.setItemValue("zipcd",zipNo);
			PAGE.FORM2.setItemValue("addr",roadAddrPart1);
			PAGE.FORM2.setItemValue("dtlAddr",addrDetail);
		}else if(jusoGubun == "MOD"){
			PAGE.FORM3.setItemValue("zipcd",zipNo);
			PAGE.FORM3.setItemValue("addr",roadAddrPart1);
			PAGE.FORM3.setItemValue("dtlAddr",addrDetail);
		}		
		
		jusoGubun = "";
	}
</script>