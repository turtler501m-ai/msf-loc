<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
  /**
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<!-- 화면 배치 -->

	<div id="FORM7" class="section-pagescope"></div><!-- 하단 페이지 버튼 class="section-pagescope" 삽입 -->

	<!-- Script -->

	<script type="text/javascript">
	
//	function onload_Handler()
//	{
//		alert("aaaaaaaaaaaaaa");
//		                    var url = ROOT + "/rcp/rcpReq/rcpReqInfoAjax.json";
//                            
//                            PAGE.GRID1.list(url, form);
//                            break; 
//
//	}
//	window.onload=onload_Handler ;
	

	
		var DHX = {
			FORM0 : {
				 title: "고객정보",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:100, blockOffset:0}
					,{type:"block", list:[
						{type:"select", width:100, label:"고객구분", labelAlign:"right", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"select", width:100, label:"서비스구분", labelAlign:"right",offsetLeft:55, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"select", width:100, label:"요금제", labelAlign:"right", offsetLeft:55,options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
					]}
					,{type:"block", list:[
						{type:"select", width:100, label:"업무구분", labelAlign:"right", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input",  label:"고객명", labelAlign:"right",width:100,offsetLeft:55}
						,{type:"newcolumn"}
						,{type:"input", width:60, label:"주민번호", labelAlign:"right",offsetLeft:55}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",labelAlign:"center",labelWidth:5}
					]}
					,{type:"block", list:[
						{type:"input",  label:"메일",width:101}
						,{type:"newcolumn"}
						,{type:"input", label:"@",name:"", labelAlign:"center", labelWidth:10, offsetLeft:5, width:135}
						,{type:"newcolumn"}
						,{type:"select", width:100,  options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type: "label", labelWidth:10, position:"label-left",  list:[
						        							{type:"settings",position:"label-left", labelWidth:40}
						        							,{type: "radio", name: "", value: "", label: "수신", checked: true}
						        							,{type:"newcolumn"}
						        							,{type: "radio", name: "", value: "", label: "미수신"}
						        							]}

					]}
					,{type:"block", list:[
						{type:"input",  label:"주소",width:255}
						,{type:"newcolumn"}
						,{type:"input", label:"",name:"",  width:257}
						,{type:"newcolumn"}
						,{type:"button", value: "주소찾기"}
					]}
					,{type:"block", list:[
						{type:"select", label:"명세서수신유형",width:100,  options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"전화번호", offsetLeft:55, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}	
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"휴대폰",labelAlign:"right", labelWidth:99, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
					]}
					,{type:"block", list:[
					     {type:"input",  label:"신청일자",width:101,readonly:true}
					  	,{type:"newcolumn"}				
					    ,{type:"input",  label:"본인인증방식",offsetLeft:55,value:"신용카드",width:101,readonly:true}
					  	,{type:"newcolumn"}				
					    ,{type:"input",  label:"본인인증정보",offsetLeft:55,value:"ReqNO:",width:101,readonly:true}
					]}
					,{type:"block", list:[
					     {type:"select", label:"신청진행상태",width:80,  options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}	
					    ,{type:"button", value: "변경적용"}
						,{type:"newcolumn"}				
					    ,{type:"select", label:"개통진행상태",width:80,offsetLeft:2,  options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}	
						,{type:"button", value: "변경적용"}
						,{type:"newcolumn"}	
						 ,{type:"select", label:"모집경로",width:80,offsetLeft:2,  options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]} 					 					 
					]}
					
					,{type:"block", list:[
					    {type:"input",  label:"서식지",width:101,readonly:true}
						,{type:"newcolumn"}	
					    ,{type:"input", width:101,readonly:true}
						,{type:"newcolumn"}	
					    ,{type:"input", width:101,readonly:true}
						,{type:"newcolumn"}	
					    ,{type:"input", width:101,readonly:true}
						,{type:"newcolumn"}	
					    ,{type:"input", width:101,readonly:true}
						]}
					,{type:"block", list:[
										    {type:"label",readonly:true}
											]}						
				]
			 },
             FORM1 : {
				 title: "배송정보",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:100, blockOffset:0}
					,{type:"block", list:[
						{type:"input", width:60, label:"받으시는 분"}
						,{type:"newcolumn"}
						,{type:"checkbox", width:40, label:"고객상동", position:"label-right", labelAlign:"left", labelWidth:60}
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"전화번호", offsetLeft:10, options:[{value:"1", text:"010", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"휴대폰",labelAlign:"right", labelWidth:98, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
					]},
					,{type:"block", list:[
						{type:"input",  label:"주소",width:255}
						,{type:"newcolumn"}
						,{type:"input", label:"",name:"",  width:257}
						,{type:"newcolumn"}
						,{type:"button", value: "주소찾기"}
					]}
					,{type:"block", list:[
						{type:"input", width:590, label:"요청사항"}

					]}
					,{type:"block", list:[
										    {type:"label",readonly:true}
											]}			
				]
			 },

			 FORM2 : {
				 title: "요금납부정보",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:100, blockOffset:0}
					,{type:"block", list:[
						{type:"select", width:100, label:"요금납부방법", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"select", width:103, label:"은행명 및 계좌번호", labelWidth:120, offsetLeft:35, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:150, label:""}
					]},
					,{type:"block", list:[
						{type:"input", width:60, label:"예금주 성명"}
						,{type:"newcolumn"}
						,{type:"checkbox", width:60, label:"타인납부", position:"label-right", labelAlign:"left", labelWidth:50}
						,{type:"newcolumn"}
						,{type:"input", width:60, label:"예금주 주민번호", labelAlign:"right",offsetLeft:20}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",labelAlign:"center",labelWidth:5}
	
					]}
					,{type:"block", list:[
										    {type:"label",readonly:true}
											]}		

				]
			 },

			 FORM3 : {
				 title: "가입 신청정보",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:100, blockOffset:0}
					,{type:"block", list:[
						{type:"select", width:100, label:"구매유형", options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input",  label:"휴대폰모델", labelAlign:"right",width:100,offsetLeft:95}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:"가입희망번호",offsetLeft:15, labelAlign:"right"}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
					
					]},
					,{type:"block", list:[
						{type:"checkbox",width:10, label:"번호연결서비스", position:"label-left", labelAlign:"right"}
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"",labelAlign:"right", labelWidth:98, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type: "label",label:"무선데이터",offsetLeft:50, labelWidth:100, labelAlign:"right",position:"label-left",  list:[
						  						        							{type:"settings",position:"label-left", labelWidth:25}
						  						        							,{type: "radio", name: "", value: "", label: "이용",labelWidth:30,width:30, checked: true}
						  						        							,{type:"newcolumn"}
						  						        							,{type: "radio", name: "", value: "", label: "차단",labelWidth:30,width:30}
						  						        							,{type:"newcolumn"}
						  						        							,{type: "radio", name: "", value: "", label: "데이터로밍 차단",labelWidth:100,width:150}
						  						        							]}
						
					]}
					,{type:"block", list:[
						{type:"input", label:"부가서비스",name:"",  width:590}
						,{type:"newcolumn"}
						,{type:"button", value: "부가서비스"}
					]}
					,{type:"block", list:[
											{type:"input", label:"단말정보",name:"",  width:590}
					]}		
					,{type:"block", list:[
										    {type:"label",readonly:true}
											]}	
				]
			 },

			 FORM4 : {
				 title: "번호이동정보",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:100, blockOffset:0}
					,{type:"block", list:[
						{type:"select", width:50, label:"전화번호",labelAlign:"right", labelWidth:99, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"select", width:100, label:"변경전 통신사", labelAlign:"right",offsetLeft:37, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"select", width:100, label:"이번달 사용요금", labelAlign:"right",offsetLeft:15, options:[{value:"1", text:"항목1", selected:true},{value:"2", text:"항목2"}]}
								
					]},
					,{type:"block", list:[
						{type:"select", width:50, label:"휴대폰 할부금",labelAlign:"right", labelWidth:99, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type: "label", label:"미환급액 요금상계", labelWidth:100, position:"label-left",offsetLeft:145,  list:[
						  						        							{type:"settings",position:"label-right", labelWidth:40}
						  						        							,{type: "radio", name: "", value: "", label: "동의", checked: true}
						  						        							,{type:"newcolumn"}
						  						        							,{type: "radio", name: "", value: "", label: "미동의"}
						  						        							]}		
						]}
					,{type:"block", list:[
						{type:"select", width:120,labelWidth:100, label:"인증유형",labelAlign:"right", options:[{value:"1", text:"휴대폰 일련번호", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:200, label:""}						
					]}
					,{type:"block", list:[
										    {type:"label",readonly:true}
											]}						

				]
			 },

			 FORM5 : {
				 title: "법정대리인",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"right",labelWidth:100, blockOffset:0}
					,{type:"block", list:[
							{type:"input", width:60, label:"받으시는 분"}
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"관계",labelAlign:"right", labelWidth:30, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:60, label:"대리인 주민번호", labelAlign:"right",offsetLeft:50}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",labelAlign:"center",labelWidth:5}	
						,{type:"newcolumn"}
						,{type:"select", width:50, label:"연락처",labelAlign:"right", labelWidth:65, options:[{value:"1", text:"011", selected:true},{value:"2", text:"항목2"}]}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:""}						
					]}
					,{type:"block", list:[
										    {type:"label",readonly:true}
											]}		
				]
			 },

			 FORM6 : {
				 //title: "약관 동의정보",
				 items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0}
					,{type:"block", list:[
						{type:"input", width:200, label:"임시"}
					]}
				]
			 },

			 FORM7 : {
					buttons : {
						right : {
							btnDelete : { label : "삭제" },
							btnSave : { label : "확인" },
							btnCancel : { label : "취소" }
						}
					}
			 },

		};

        var PAGE = {
        	title : "후불가입신청관리",
        	breadcrumb: "Home > 신청관리 > 후불가입신청관리 ",
        	
			onOpen : function() {
				mvno.ui.createForm("FORM0");
				mvno.ui.createForm("FORM1");
				mvno.ui.createForm("FORM2");
				mvno.ui.createForm("FORM3");
				mvno.ui.createForm("FORM4");
				mvno.ui.createForm("FORM5");
				mvno.ui.createForm("FORM6");
				mvno.ui.createForm("FORM7");
				 
			}
		}
	</script>






	<!-- Script -->
	<script type="text/javascript">
/*
		dhtmlx.message({ text:"Test Message", expire:-1});
		dhtmlx.message({ text:"Test Error Message", expire:-1, type:"error"});
		dhtmlx.confirm({ 
			title:"Title Here", text:"Test confirm",
			callback:function(){
				dhtmlx.alert("Test alert", function(){
					dhtmlx.confirm({ type:"confirm-error", title:"Title Here", text:"Test confirm" });
				});	
			}
		});
*/
	</script>



