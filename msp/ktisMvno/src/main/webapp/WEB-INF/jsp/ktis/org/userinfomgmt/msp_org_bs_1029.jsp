<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1029.jsp
	 * @Description : 사용자 이력관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.09.02 고은정 최초생성
	 * @ 
	 * @author : 고은정
	 * @Create Date : 2014. 9. 2
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
   
<!-- Script -->
<script type="text/javascript">
 var DHX = {
            //------------------------------------------------------------
            FORM1 : {
                items : [
                   {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},              
                   {type: 'block', list: [
	                {type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:100, offsetLeft:10},
	                {type: 'newcolumn'},
	                {type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:50,width:100,offsetLeft:20},
	                {type: 'newcolumn'},
	                {type:"input", name:"orgnId", label:"조직", value: "", width:100, offsetLeft:20},
					{type:"newcolumn", offsetLeft:3},
					{type:"button", value:"검색", name:"btnOrgFind"},
					{type:"newcolumn", offsetLeft:3},
					{type:"input", name:"orgnNm", value:"", width:145, readonly: true}	                
                ]},
                {type: 'block', list: [
// 	                {type: 'select', name: 'attcSctnCd', label: '소속구분', userdata:{lov: 'CMN0003'},width:100, offsetLeft:10},
	                {type: 'select', name: 'attcSctnCd', label: '소속구분',width:100, offsetLeft:10},
	                {type: 'newcolumn'},
// 	                {type: 'select', name: 'dept', label: '부서/업체', userdata:{lov: 'CMN0006'},width:100,offsetLeft:20}
	                {type: 'select', name: 'dept', label: '부서/업체',width:100,offsetLeft:20}
// 	                {type: 'newcolumn'},
// 	                {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '등록일자', value:'', calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:20, value: "${startDate}"},          
// 	                {type: 'newcolumn'},
// 	                {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value:'', calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, value: "${endDate}"},
                ]},
                {type: 'newcolumn', offset:150},
                   {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
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
                           
                           PAGE.GRID1.list(ROOT + "/org/userinfomgmt/userInfoHstList.json", form);
                           break;
                           
                   }

               }
//                onValidateMore : function (data){
//                    if((data.searchStartDt && !data.searchEndDt) || (!data.searchStartDt && data.searchEndDt)){
//                    	this.pushError("data.searchStartDt","등록일자","일자를 선택하세요");
//                    }
//                    else if(data.searchStartDt > data.searchEndDt){
                       
//                    	 this.pushError("data.searchStartDt","등록일자",["ICMN0004"]);
//                    }
// 			        else if(getDateDiff(data.searchStartDt, data.searchEndDt) > 30){
			        	 
// 			        	 this.pushError("data.searchStartDt","등록일자","한달이내로 선택하세요.");
// 			         }                    
//                }
               
           },
				

        // ----------------------------------------
        //사용자이력리스트
           GRID1 : {
            css : {
                height : "560px"
            },
            title : "조회결과",
   			header : "순번,사용자ID,사용자명,조직명,소속구분,부서/업체명,비밀번호변경일시,사번,직위,직책,전화번호,핸드폰번호,FAX,이메일,마스킹권한여부,적용시작일자,적용종료일자,사용여부,삭제사유,수정자,수정일시",
			columnIds : "seqNum,usrId,usrNm,orgnNm,attcSctnCdNm,deptNm,passLastRvisnDt,presBiz,pos,odty,telNum,mblphnNum,fax,email,mskAuthYn,usgStrtDt,usgEndDt,usgYn,delRsn,rvisnNm,rvisnDttm",
 			    widths : "40,100,120,150,100,100,140,100,100,100,100,100,100,140,100,100,100,80,150,100,150",
				colAlign : "center,left,left,left,left,left,center,left,left,left,left,left,left,left,center,center,center,center,center,center,center",
               colTypes : "ro,ro,ro,ro,ro,ro,roXdt,ro,ro,ro,roXp,roXp,roXp,ro,ro,roXd,roXd,ro,ro,ro,ro",
               colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",               
               paging:true,
               pageSize:20,
				onRowDblClicked : function(rowId, celInd) {
				    mvno.ui.createForm("FORM2");
	
				    //상세내용화면
	                PAGE.FORM2.setFormData(PAGE.GRID1.getSelectedRowData());
				    
	                setLabelAddHyphen(PAGE.FORM2, PAGE.GRID1.getSelectedRowData(), "telNum", "tel");
	                setLabelAddHyphen(PAGE.FORM2, PAGE.GRID1.getSelectedRowData(), "mblphnNum", "tel");
	                setLabelAddHyphen(PAGE.FORM2, PAGE.GRID1.getSelectedRowData(), "fax", "tel");
	                   
	                PAGE.FORM2.setItemValue("passLastRvisnDt",mvno.etc.convertDateFormat(PAGE.GRID1.getSelectedRowData().passLastRvisnDt));
	                PAGE.FORM2.setItemValue("usgStrtDt",mvno.etc.convertDateFormat(PAGE.GRID1.getSelectedRowData().usgStrtDt));
	                PAGE.FORM2.setItemValue("usgEndDt",mvno.etc.convertDateFormat(PAGE.GRID1.getSelectedRowData().usgEndDt));
	                
                   mvno.ui.popupWindowById("FORM2", "사용자", 740, 410);         
               }
		},
           
           // --------------------------------------------------------------------------
           //사용자이력 상세화면
           FORM2 : {
               title : "",
               items : [
                    {type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
                    {type: "block", blockOffset: 0, offsetLeft: 30, list: [
                        {type: 'input', name: 'usrId', label: '사용자ID',  width: 140,  value: '', readonly:true },
                        {type: "newcolumn"},
                        {type: 'input', name: 'usrNm', label: '사용자명',  width: 140, offsetLeft: 60, value: '', readonly:true} 
                    ]},
                    {type: "block", blockOffset: 0, offsetLeft: 30,list: [
                        {type: 'input', name: 'orgnNm' , label: '조직명', width:140, readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'mskAuthYn', label: '마스킹권한여부', width:140,  offsetLeft: 60, readonly:true}  ,  
                    ]}, 
                    {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                        {type: 'input', name: 'attcSctnCdNm', label: '소속구분',  width: 140,  readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'deptNm', label: '부서/업체명',  width: 140, offsetLeft: 60,  readonly:true}    
                    ]},
                    {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                        {type: 'input', name: 'passLastRvisnDt', label: '비밀번호변경일시',  width: 140,  value: '' ,  readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'presBiz', label: '사번',  width: 140, offsetLeft: 60, value: '' ,  readonly:true}   
                    ]},                   
                    {type: "block", blockOffset: 0, offsetLeft: 30,list: [
                        {type: 'input', name: 'pos', label: '직위',  width: 140,  value: '' ,  readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'odty', label: '직책',  width: 140, offsetLeft: 60, value: '' ,  readonly:true}  
                    ]},   
                    {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                        {type: 'input', name: 'telNum', label: '전화번호',  width: 140,  value: '' ,  readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'mblphnNum', label: '핸드폰번호',  width: 140, offsetLeft: 60, value: '' ,  readonly:true} 
                    ]},  
                    {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                        {type: 'input', name: 'fax', label: 'FAX',  width: 140,  value: '' ,  readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'email', label: '이메일',  width: 140, offsetLeft: 60, value: '' ,  readonly:true} 
                    ]},
                    {type: "block", blockOffset: 0, offsetLeft: 30,list: [
                        {type: 'input', name: 'usgStrtDt' , label: '적용시작일자',  value:'',width: 140,readonly:true},
                        {type: "newcolumn"},
                        {type: 'input', name: 'usgEndDt', label: '적용종료일자',  value:'',width: 140, offsetLeft: 60,readonly:true}    
                    ]},
                    {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                      {type: 'input', name: 'usgYn', label: '사용여부',  width: 140, readonly:true},
                      {type: "newcolumn"},
                      {type: 'input', name: 'mngrRule', label: '관리자롤',  width: 140, offsetLeft: 60, readonly:true},  
                    ]},
                    {type: "block", blockOffset: 0,offsetLeft: 30, list: [
                      {type: 'input', name: 'rvisnNm', label: '수정자',  width: 140, readonly:true},
                      {type: "newcolumn"},
                      {type: 'input', name: 'rvisnDttm', label: '수정일시', width: 140,  offsetLeft: 60, readonly:true},  
                    ]},
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
           }
 };
 
    var PAGE = {
    		title : "${breadCrumb.title}",
    		breadcrumb : " ${breadCrumb.breadCrumb}",
            buttonAuth: ${buttonAuth.jsonString},

        onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");

    		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM1, "attcSctnCd"); // 소속구분
    		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0006",orderBy:"etc1"}, PAGE.FORM1, "dept"); // 부서/업체
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
      * 시작날짜, 끝날짜
      */
     function getDateDiff(date1, date2){
     	
     	var getDate1 = new Date(date1.substr(0,4), date1.substr(4,2)-1, date1.substr(6,2));
     	var getDate2 = new Date(date2.substr(0,4), date2.substr(4,2)-1, date2.substr(6,2));
     	
     	var getDiffTime = getDate2.getTime() - getDate1.getTime();
     	
     	return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
     }
</script>