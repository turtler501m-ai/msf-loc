<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : usimOrgnPrdtRel.jsp
	 * @Description : m2m usim 유통 조직 id 별 제품 관계 관리 화면
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
	</div>
</div>

<!-- 제품등록화면 배치 -->
<div id="POPUP_PRDT" >
	<div id="FORM4"  class="section-box"></div>
 	<div id="IMAGE4" ></div> 
	<div id="FORM4btn"  class="section-full"></div>
</div>

<!-- 제품이미지 -->
<div id="POPUP_IMAGE" >
 	<div id="IMAGE5" style="text-align:center;"></div> 
	<div id="FORM5btn"  class="section-full"></div>
</div>

    <!-- Script -->
    <script type="text/javascript">

    var DHX = {

		GRID1 : {
			css : {
				height : "590px"
			},

			title : "유통 조직",
			header : "유통조직ID,유통조직명",
			columnIds : "orgnId,orgnNm",
			colAlign : "center,center",
			widths : "200,255",
			colTypes : "ro,ro",
			colSorting : "str,str",
			paging : true,
			pagingStyle : 1,
	        pageSize : 20,
	        showPagingAreaOnInit : true,


			//-----------------------------------------------
			// click list -> show detail
			//-----------------------------------------------
			onRowSelect : function(rowId, celInd) {

				var selectedData = PAGE.GRID1.getSelectedRowData();

				var url = ROOT + "/m2m/usimOrgnPrdtRel/usimPrdtRelList.json";
	            PAGE.GRID2.list(url, selectedData);
			}


		},

		//---------------------------------------
		GRID2 : {
			css : {
				height : "590px",
				top : 0,
				bottom :0
			},

			title : "유통 조직별 제품",
			header : "대표제품ID,제품명,제품설명,등록자,등록일",
			columnIds : "rprsPrdtId,prdtNm,prdtDesc,usrNm,rvisnDttm",
			colAlign : "left,left,left,left,left",
			widths : "90,90,90,60,125",
			colTypes : "ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
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

						mvno.ui.createForm("FORM4");
						mvno.ui.createForm("FORM4btn");

						PAGE.FORM4.clear();

						mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getMdlIdComboList.json", "", PAGE.FORM4, "rprsPrdtId"); // 제품정보
						
						var rprsPrdtId = PAGE.FORM4.getItemValue("rprsPrdtId");

						mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mMdl.json", {rprsPrdtId:rprsPrdtId}, function(result) {
							
							PAGE.FORM4.setItemValue("prdtNm",result.result.data.rows[0].prdtNm);
							PAGE.FORM4.setItemValue("outUnitPric",result.result.data.rows[0].outUnitPric);
							PAGE.FORM4.setItemValue("prdtDesc",result.result.data.rows[0].prdtDesc);


							var fileName = "";
							fileName = result.result.data.rows[0].fileNm;

							// 첫번째파일
							
							if(!mvno.cmn.isEmpty(result.result.data.rows[0].fileNm)){
								fileName = result.result.data.rows[0].fileNm;
								
								$("#IMAGE4").css("width","220px"); 
								$("#IMAGE4").css("height","190px"); 
								$("#IMAGE4").css("overflow","hidden"); 

								//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
								var img = '<img id="img_usim" src="/m2m/usimPrdtMng/getImageFile.do?rprsPrdtId=' + rprsPrdtId + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
								
								$("#IMAGE4").html(img);

								var myImg = document.getElementById("img_usim");
								myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
								{
									
									var divObject = $("#IMAGE4");
									var imgObject = $("#img_usim");
									var divAspect = 190 / 220; // div의가로세로비는알고있는값이다
									var imgAspect = imgObject.height() / imgObject.width();
									
									if (imgAspect <= divAspect) {
									    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
									    var imgWidthActual = divObject.offsetHeight / imgAspect;
									    
									    var imgWidthToBe = divObject.offsetHeight / divAspect;
									    var marginLeft = -Math.round((imgWidthActual - imgWidthToBe) / 2);

									    $("#img_usim").css("width","96%");
									    $("#img_usim").css("height","auto");
									    $("#img_usim").css("margin-left",marginLeft + 'px;');
									} else {
									    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
									    $("#img_usim").css("width","auto");
									    $("#img_usim").css("height","96%");
									    $("#img_usim").css("margin-left","0");
									    
									}
								    $("#img_usim").css("border","2px solid lightgray");
								    
									$("#IMAGE4").css("height",imgObject.height() + 12); 

								}

								$("#FORM4btn_btnImage").show();

							}else{
								$("#IMAGE4").css("width","0px"); 
								$("#IMAGE4").css("height","0px"); 

								$("#IMAGE4").html("");

								$("#FORM4btn_btnImage").hide();
								
							}

						
						});

						
						
						
						PAGE.FORM4.clearChanged();

						mvno.ui.popupWindowById("POPUP_PRDT", "제품추가", 470, 450, {//450, 350
							onClose: function(win) {

								
								win.closeForce();
							}
						});
												
						
						
						break;

					case "btnDel":

						var data = PAGE.GRID2.getSelectedRowData();
						
						if(data == null){

							mvno.ui.alert("유통조직 별 제품 정보가 선택되지 않았습니다.");
							return
						}
						
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/m2m/usimOrgnPrdtRel/usimPrdtRelDelete.json"
								, PAGE.GRID2.getSelectedRowData() , function(result) {
							mvno.ui.notify("NCMN0003");
							PAGE.GRID2.refresh();
						});
						break;

				}


			}
		},
		
		// --------------------------------------------------------------------------
		//제품추가화면
		FORM4 : {
			title : "",
			items : [
				 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
				 {type:"block", blockOffset:0,  list: [
						{type:'select', name:'rprsPrdtId', label:'제품', width:100, required: true},
						//{type:'input', name:'prdtCode', label:'제품', width:150, required: true},
						{type:"newcolumn"},
						{type:'hidden', name:'prdtNm', width:200 }
				 ]},

				 {type:"block", list:[
						//{type:'input', name:'ordCnt', label:'수량', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger" },
						//{type:"newcolumn"},
						{type:'input', name:'outUnitPric', label:'단가(VAT별도)', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger" , readonly:true },
						//{type:"newcolumn"},
						//{type:'input', name:'amt', label:'총금액(VAT별도)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true},
						//{type:"newcolumn"},
						//{type:'input', name:'amtVat', label:'총금액(VAT포함)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true},
						{type:"newcolumn"},
						{type:'input', name:'prdtDesc', label:'제품상세', width:270, readonly:true}
				 ]}

			],
			buttons : {
				/*
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}*/
			},
			onChange : function(name, value) {//onChange START
				var form = this;

				switch(name){
				case "rprsPrdtId" :
					if(value != null && value != "" && value.trim() != ""){

						mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mMdl.json", {rprsPrdtId:value}, function(result) {
							
							//PAGE.FORM4.setItemValue("prdtCode",result.result.data.rows[0].prdtCode);
							PAGE.FORM4.setItemValue("prdtNm",result.result.data.rows[0].prdtNm);
							PAGE.FORM4.setItemValue("outUnitPric",result.result.data.rows[0].outUnitPric);
							PAGE.FORM4.setItemValue("prdtDesc",result.result.data.rows[0].prdtDesc);
							
							//var amt = PAGE.FORM5.getItemValue("ordCnt") * PAGE.FORM5.getItemValue("outUnitPric") ;
							//var amtVat = amt * 1.1;

							//PAGE.FORM5.setItemValue("amt",amt);
							//PAGE.FORM5.setItemValue("amtVat",amtVat);

							var fileName = "";
							fileName = result.result.data.rows[0].fileNm;
							

							// 첫번째파일
							if(!mvno.cmn.isEmpty(result.result.data.rows[0].fileNm)){
								fileName = result.result.data.rows[0].fileNm;

								$("#IMAGE4").css("width","220px"); 
								$("#IMAGE4").css("height","190px"); 
								$("#IMAGE4").css("overflow","hidden"); 

								//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
								var img = '<img id="img_usim" src="/m2m/usimPrdtMng/getImageFile.do?rprsPrdtId=' + value + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
								
								$("#IMAGE4").html(img);

								var myImg = document.getElementById("img_usim");
								myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
								{
									
									var divObject = $("#IMAGE4");
									var imgObject = $("#img_usim");
									var divAspect = 190 / 220; // div의가로세로비는알고있는값이다
									var imgAspect = imgObject.height() / imgObject.width();
									
									if (imgAspect <= divAspect) {
									    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
									    var imgWidthActual = divObject.offsetHeight / imgAspect;
									    
									    var imgWidthToBe = divObject.offsetHeight / divAspect;
									    var marginLeft = -Math.round((imgWidthActual - imgWidthToBe) / 2);

									    $("#img_usim").css("width","96%");
									    $("#img_usim").css("height","auto");
									    $("#img_usim").css("margin-left",marginLeft + 'px;');
									} else {
									    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
									    $("#img_usim").css("width","auto");
									    $("#img_usim").css("height","96%");
									    $("#img_usim").css("margin-left","0");
									    
									}
								    $("#img_usim").css("border","2px solid lightgray");
								    
									$("#IMAGE4").css("height",imgObject.height() + 12); 

								}

								$("#FORM4btn_btnImage").show();
								
							}else{
								$("#IMAGE4").css("width","0px"); 
								$("#IMAGE4").css("height","0px"); 

								$("#IMAGE4").html("");

								$("#FORM4btn_btnImage").hide();
								
							}
						});
						
						
					} else {
						
					}

					break;
				
				}

			},
			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnSave":
						
						
						var rprsPrdtId = PAGE.FORM5.getItemValue("rprsPrdtId");
						
						

						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null ){

							mvno.ui.alert("유통조직이 선택되지 않았습니다.");
							return;
						}

						
						var orgnId = data.orgnId;
						
						
						mvno.cmn.ajax4confirm("CCMN0001", ROOT + "/m2m/usimOrgnPrdtRel/usimPrdtRelInsert.json"
								, {orgnId:orgnId,rprsPrdtId:rprsPrdtId} , function(result) {
							mvno.ui.notify("NCMN0001");
							PAGE.GRID2.refresh();
						});

						
						
						
						mvno.ui.closeWindowById(form);
						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;

				}
			},
			onValidateMore : function (data){

			}
		},	

		// --------------------------------------------------------------------------
		//제품추가화면 button
		FORM4btn : {
			title : "",
			items : [
				/*
				 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
				 {type:"block", blockOffset:0,  list: [
						{type:'select', name:'rprsPrdtId', label:'대표제품ID', width:70, required: true},
						//{type:'input', name:'prdtCode', label:'제품', width:150, required: true},
						{type:"newcolumn"},
						{type:'input', name:'prdtNm', width:200 }
				 ]},

				 {type:"block", list:[
						//{type:'input', name:'ordCnt', label:'수량', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger" },
						//{type:"newcolumn"},
						{type:'input', name:'outUnitPric', label:'단가(VAT별도)', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger" , readonly:true },
						//{type:"newcolumn"},
						//{type:'input', name:'amt', label:'총금액(VAT별도)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true},
						//{type:"newcolumn"},
						//{type:'input', name:'amtVat', label:'총금액(VAT포함)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true},
						{type:"newcolumn"},
						{type:'input', name:'prdtDesc', label:'제품상세', width:270, readonly:true}
				 ]}
				 */

			],
			buttons : {
				center : {
					btnImage : {label : "이미지확대" },
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onChange : function(name, value) {//onChange START
				/*
				var form = this;

				switch(name){
				case "rprsPrdtId" :
					if(value != null && value != "" && value.trim() != ""){

						mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mMdl.json", {rprsPrdtId:value}, function(result) {
							
							//PAGE.FORM5.setItemValue("prdtCode",result.result.data.rows[0].prdtCode);
							PAGE.FORM5.setItemValue("prdtNm",result.result.data.rows[0].prdtNm);
							PAGE.FORM5.setItemValue("outUnitPric",result.result.data.rows[0].outUnitPric);
							PAGE.FORM5.setItemValue("prdtDesc",result.result.data.rows[0].prdtDesc);
							
							//var amt = PAGE.FORM5.getItemValue("ordCnt") * PAGE.FORM5.getItemValue("outUnitPric") ;
							//var amtVat = amt * 1.1;

							//PAGE.FORM5.setItemValue("amt",amt);
							//PAGE.FORM5.setItemValue("amtVat",amtVat);

						});
						
						
					} else {
						
					}

					break;
				
				}*/

			},
			onButtonClick : function(btnId) {
				var form = PAGE.FORM4; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnSave":
						
						
						var rprsPrdtId = PAGE.FORM4.getItemValue("rprsPrdtId");
						
						

						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null ){

							mvno.ui.alert("유통조직이 선택되지 않았습니다.");
							return;
						}

						
						var orgnId = data.orgnId;
						
						
						mvno.cmn.ajax4confirm("CCMN0001", ROOT + "/m2m/usimOrgnPrdtRel/usimPrdtRelInsert.json"
								, {orgnId:orgnId,rprsPrdtId:rprsPrdtId} , function(result) {
							mvno.ui.notify("NCMN0001");
							PAGE.GRID2.refresh();
						});

						
						
						
						mvno.ui.closeWindowById(form);
						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;

					case "btnImage" :
						

						mvno.ui.createForm("FORM5btn");
						
						
						var rprsPrdtId = PAGE.FORM4.getItemValue("rprsPrdtId");


						$("#IMAGE5").css("width","700px"); 
						$("#IMAGE5").css("height","640px"); 
						$("#IMAGE5").css("overflow","hidden"); 

						//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
						var img = '<img id="img_usim_big" src="/m2m/usimPrdtMng/getImageFile.do?rprsPrdtId=' + rprsPrdtId + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
						
						$("#IMAGE5").html(img);

						var myImg = document.getElementById("img_usim_big");
						myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
						{
							
							var divObject = $("#IMAGE5");
							var imgObject = $("#img_usim_big");
							var divAspect = 640 / 700; // height/width   div의가로세로비는알고있는값이다
							var imgAspect = imgObject.height() / imgObject.width();
							
							if (imgAspect <= divAspect) {
							    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
							    $("#img_usim_big").css("width","96%");
							    $("#img_uimg_usim_bigsim").css("height","auto");
							    $("#img_usim_big").css("margin-left", '0px;');
							} else {
							    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
							    $("#img_usim_big").css("width","auto");
							    $("#img_usim_big").css("height","96%");
							    $("#img_usim_big").css("margin-left","0");
							    
							}
						    $("#img_usim_big").css("border","2px solid lightgray");
						    
							$("#IMAGE5").css("height",imgObject.height() + 12); 

						}
						
						
						mvno.ui.popupWindowById("POPUP_IMAGE", "이미지확대", 750, 750, {
							onClose: function(win) {

								win.closeForce();
								
							}
						});
						
						break;

				}
			},
			onValidateMore : function (data){

			}
		},	

		//-이미지확대 화면 버튼-----------------------------------------
		FORM5btn : {
			title : "",
			items : [
			],
			buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			},
			onChange : function(name, value) {//onChange START

			},
			onButtonClick : function(btnId) {
				var form = PAGE.FORM5btn; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;
				}
			},
			onValidateMore : function (data){

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

	        var url1 = ROOT + "/m2m/usimOrgnPrdtRel/usimOrgnList.json";
	        // 임시로 막음. 테스트 중
            PAGE.GRID1.list(url1, null,  {  callback: gridOnLoad });
//	        PAGE.GRID1.list("/cmn/authmgmt/usrGrpList.json", url1);


	    }
	};

	function gridOnLoad  ()
	{
		/*
		PAGE.GRID1.forEachRow(function(id){
			if (PAGE.GRID1.cellById(id,3).getValue()=="N")
			{
				PAGE.GRID1.setRowTextStyle(id,"color: red");
//				PAGE.GRID1.setCellTextStyle(id,3,"color: red");
			}
		});
		*/
	};

    </script>


