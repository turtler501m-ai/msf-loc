<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<div id="POPUP1">
	<div id="FORM2" class="section-box"></div>
	<div id="GRID2" class="section-full"></div>
	<div id="FORM3" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
  	var maskingYn = '${maskingYn}';

    var DHX = {
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
                {type:"block", list:[
                        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"등록일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10},
                        {type:"newcolumn"},
                        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
                        {type:'newcolumn'},
                        {type:"select", label:"검색구분", name:"searchCd", width:90, offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"이름"}, {value:"2", text:"식별번호"}]},
                        {type:"newcolumn"},
                        {type:"input", name:"searchVal", width:125, maxLength:13, disabled:true},
                        {type:"newcolumn"},
                        {type:"select", label:"진행상태", name:"lcStatCd", width:90, offsetLeft:20},
                        {type:"hidden", label:"식별번호검색값", name:"searchRrn"}
                    ]},
                {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
            ],

            onButtonClick : function(btnId) {
                var form = this;

                switch (btnId) {
                    case "btnSearch":
                    	if(form.getItemValue("searchCd") == "2") {
                    		form.setItemValue("searchRrn", form.getItemValue("searchVal"));
                    	}
                    	
                        PAGE.GRID1.list(ROOT + "/rcp/courtMgmt/getCourtLcMgmtList.json", form);

                        break;
                }
            },
            onValidateMore : function (data){
                if(data.strtDt > data.endDt){
                    this.pushError("strtDt","등록일자",["ICMN0004"]);
                }
                if( data.searchCd != "" && data.searchVal.trim() == ""){
                    this.pushError("searchVal", "검색어", "검색어를 입력하세요");
                    PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
                }
            },
            onChange : function(name, value) {//onChange START
                var form = this;
                // 검색구분
                if(name == "searchCd") {
                    form.setItemValue("searchVal", "");

                    if(value == null || value == "") {
                        var searchEndDt = new Date().format('yyyymmdd');
                        var time = new Date().getTime();
                        time = time - 1000 * 3600 * 24 * 7;
                        var searchStartDt = new Date(time);

                        // 등록일자 Enable
                        form.enableItem("strtDt");
                        form.enableItem("endDt");

                        form.setItemValue("strtDt", searchStartDt);
                        form.setItemValue("endDt", searchEndDt);

                        form.disableItem("searchVal");
                    }
                    else {
                        form.setItemValue("strtDt", "");
                        form.setItemValue("endDt", "");

                        // 등록일자 Disable
                        form.disableItem("strtDt");
                        form.disableItem("endDt");

                        form.enableItem("searchVal");
                    }
                }
            },
            onKeyUp : function (inp, ev, name, value){
                var form = this;

                var searchCd = form.getItemValue("searchCd");

                if(name == "searchVal"){
                    if(searchCd == "2") {
                        var val = form.getItemValue("searchVal");
                        form.setItemValue("searchVal",val.replace(/[^0-9]/g,""));
                    }
                }
            }
        }

        , GRID1 : {
            css : {
                height : "565px"
            }
            , title : "조회결과" // 16
            , header : "번호,발급번호,진행상태,이름,식별번호,미납요금/원(A),단말할부금/원(B),합계/원(A+B),증명서 발급자,증명서 발급일시,출력여부,등록자,등록일시,수정자,수정일시,주소"
            , columnIds : "lcSeq,lcNum,lcStatCd,cstmrName,cstmrRrn,unpdPrc,instPrc,total,issueId,issueDttm,printYn,regstId,regstDttm,rvisnId,rvisnDttm,cstmrAddr"
            , widths : "60,150,80,80,120,120,120,120,100,150,80,60,150,60,150,10"
            , colAlign : "center,center,center,center,center,right,right,right,center,center,center,center,center,center,center,center"
            , colTypes : "ron,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro"
            , colSorting : "int,str,str,str,str,int,int,int,str,str,str,str,str,str,str,str"
            //, multiCheckbox : true
            , hiddenColumns : [15]
            , paging : true
            , showPagingAreaOnInit : true
            , pageSize : 20
            , buttons : {
                hright : {
                    btnPrnt : { label : "부채증명서 출력"},
                    btnDnExcel : { label : "엑셀다운로드"}
                },
                right : {
                    btnRgst : { label : "부채증명서 등록"},
                    btnDscrd : { label : "부채증명서 폐기"}
                }
            },

            onRowDblClicked : function(rowId, celInd) {
                this.callEvent('onButtonClick', ['btnDtl']);
            },

            onButtonClick : function(btnId) {
                var grid = this;
                var rowId = grid.getSelectedRowId();
				var data = grid.getSelectedRowData();
				
                switch (btnId) {
	                case 'btnPrnt' :
                    	mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/chkMaskingLc.json", PAGE.FORM1, function(result) {
    						if(data == null) {
    							mvno.ui.alert("ECMN0002");
    							return;
    						}
    						
    	               		if(grid.getRowAttribute(rowId, "printYn") != "가능") {
    	                        mvno.ui.alert("먼저 부채증명서 발급을 진행해주세요.");
    	                        return;
    	               		}
    	               		
    	                	if(maskingYn != "Y" && maskingYn != "1") {
    	                		mvno.ui.alert("권한이 없어 부채증명서를 출력할 수 없습니다.");
    	                		return;
    	                	}
    	               		
    						var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/rcp/courtLcReport.jrf" + "&arg=";
    						param = encodeURI(param);
    						
    						var arg = "lcSeq#" + data.lcSeq + "#";
    						arg = encodeURIComponent(arg);
    						
    						param = param + arg;
    						
    						var msg = "부채증명서를 출력하시겠습니까?";
    						mvno.ui.confirm(msg, function() {
    							mvno.ui.popupWindow("/cmn/report/report.do"+param, "부채증명서", 900, 700, {
    								onClose: function(win) {
    									win.closeForce();
    								}
    							});
    						});
                    	});
	
	                    break;

                    case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
                        if(grid.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                        } else {
                            var searchData =  PAGE.FORM1.getFormData(true);
                            mvno.cmn.download("/rcp/courtMgmt/getCourtLcMgmtListByExcel.json?menuId=MSP1008001", true, {postData:searchData});
                        }
                        break;

                    case 'btnRgst' :
                        mvno.ui.createForm("FORM2");
                        mvno.ui.createGrid("GRID2");
                        mvno.ui.createForm("FORM3");
                        
                		mvno.ui.enableButton("FORM3", "btnAdd");
                		mvno.ui.enableButton("FORM3", "btnDel");
                		mvno.ui.enableButton("FORM3", "btnSave");
                		mvno.ui.enableButton("FORM3", "btnUpdate");
                		mvno.ui.enableButton("GRID2", "btnIss");

                        mvno.ui.hideButton("FORM3" , 'btnUpdate');
                        mvno.ui.showButton("FORM3" , 'btnSave');

                        PAGE.FORM2.enableItem("cstmrNameB")
                        PAGE.FORM2.enableItem("cstmrRrnB")
                        PAGE.FORM2.enableItem("cstmrAddrA")

                        PAGE.FORM2.clear();
                        PAGE.GRID2.clearAll();
                        PAGE.FORM3.clear();

                        PAGE.FORM3.setItemValue("isEnable", "N");
                        PAGE.FORM3.disableItem("telFn");
                        PAGE.FORM3.disableItem("telMn");
                        PAGE.FORM3.disableItem("telRn");
                        PAGE.FORM3.disableItem("openDt");
                        PAGE.FORM3.disableItem("unpdPrc");
                        PAGE.FORM3.disableItem("instPrc");
                        PAGE.FORM3.disableItem("priceA");
                        PAGE.FORM3.disableItem("priceB");

                        mvno.ui.popupWindowById("POPUP1", "상세화면", 805, 500, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                        break;
                        
                    case 'btnDscrd' :
                    	if(grid.getRowAttribute(rowId, "lcStatCd") == "폐기") {
                    		mvno.ui.alert("이미 폐기된 부채증명서입니다.");
                            return;
                    	}
                    	
                    	mvno.cmn.ajax4confirm("부채증명서를 폐기하시겠습니까?"
    										, ROOT + "/rcp/courtMgmt/discardLcMst.json"
				    						, {lcMstSeq:grid.getRowAttribute(rowId, "lcSeq")}
				    						, function(resultdata)
   						{
				    		mvno.ui.alert("부채증명서가 폐기되었습니다.");
    						PAGE.GRID1.refresh();
                    	});
                    	
                    	break;

                    case 'btnDtl' :
                        if(grid.getSelectedRowData() == null){
                            mvno.ui.alert("선택된 데이터가 없습니다.");
                            return;
                        }
                        mvno.ui.createForm("FORM2");
                        mvno.ui.createGrid("GRID2");
                        mvno.ui.createForm("FORM3");

                        PAGE.FORM2.clear();
                        PAGE.GRID2.clearAll();
                        PAGE.FORM3.clear();

                        PAGE.FORM3.setItemValue("isEnable", "N");
                        PAGE.FORM3.disableItem("telFn");
                        PAGE.FORM3.disableItem("telMn");
                        PAGE.FORM3.disableItem("telRn");
                        PAGE.FORM3.disableItem("openDt");
                        PAGE.FORM3.disableItem("unpdPrc");
                        PAGE.FORM3.disableItem("instPrc");
                        PAGE.FORM3.disableItem("priceA");
                        PAGE.FORM3.disableItem("priceB");

                        PAGE.FORM2.setItemValue("cstmrNameB", grid.getRowAttribute(rowId, "cstmrName"));
                        PAGE.FORM2.setItemValue("cstmrRrnB", grid.getRowAttribute(rowId, "cstmrRrn"));
                        PAGE.FORM2.setItemValue("cstmrAddrA", grid.getRowAttribute(rowId, "cstmrAddr"));
                        PAGE.FORM2.disableItem("cstmrNameB");
                        PAGE.FORM2.disableItem("cstmrRrnB");
                        PAGE.FORM2.disableItem("cstmrAddrA");

                        mvno.ui.showButton("FORM3" , 'btnUpdate');
                        mvno.ui.hideButton("FORM3" , 'btnSave');

                        PAGE.FORM3.setItemValue("lcMstSeq", grid.getRowAttribute(rowId, "lcSeq"));
                        
                        if(grid.getRowAttribute(rowId, "lcStatCd") == "발급완료" || grid.getRowAttribute(rowId, "lcStatCd") == "폐기") {
                        	mvno.ui.disableButton("FORM3", "btnAdd");
                    		mvno.ui.disableButton("FORM3", "btnDel");
                    		mvno.ui.disableButton("FORM3", "btnSave");
                    		mvno.ui.disableButton("FORM3", "btnUpdate");
                    		mvno.ui.disableButton("GRID2", "btnIss");
                        } else{
                    		mvno.ui.enableButton("FORM3", "btnAdd");
                    		mvno.ui.enableButton("FORM3", "btnDel");
                    		mvno.ui.enableButton("FORM3", "btnSave");
                    		mvno.ui.enableButton("FORM3", "btnUpdate");
                    		mvno.ui.enableButton("GRID2", "btnIss");
                        }
                        
                        if(grid.getRowAttribute(rowId, "lcStatCd") == "발급완료") {
                    	    PAGE.FORM3.setItemValue("printYn", "가능");
                    	    mvno.ui.enableButton("GRID2", "btnDscrd");
                    	    mvno.ui.enableButton("GRID2", "btnPrnt");
                        } else{
                    	    PAGE.FORM3.setItemValue("printYn", "불가능");
                    	    mvno.ui.disableButton("GRID2", "btnDscrd");
                    	    mvno.ui.disableButton("GRID2", "btnPrnt");
                        }

                        PAGE.GRID2.list(ROOT + "/rcp/courtMgmt/getCourtLcMgmtDtlList.json", PAGE.FORM3)

                        mvno.ui.popupWindowById("POPUP1", "상세화면", 805, 500, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });

                        break;
                }
            }
        }

        , FORM2 : {
            title : "기본정보",
            items : [
                {type:'settings', position:'label-left', inputWidth:'auto'},
                    {type:"block", blockOffset:0, list: [
                            {type:'input', name:'cstmrNameB', label:'이름', width:70, labelWidth:30, offsetLeft:10, maxLength:8},
                            {type:"newcolumn"},
                            {type:'input', name:'cstmrRrnB', label:'식별번호', width:120, labelWidth:50, offsetLeft:20, validate: "ValidInteger", maxLength: 13, userdata:{align:"left"}},
                            {type:"newcolumn"},
                            {type:'input', name:'cstmrAddrA', label:'주소', width:335, labelWidth:30, offsetLeft:20, maxLength:300}
                        ]}
                ],

            onKeyUp : function (inp, ev, name, value){
                var form = this;
                var val1 = form.getItemValue("cstmrNameB");
                var val2 = form.getItemValue("cstmrRrnB");

                form.setItemValue("cstmrNameB",val1.replace(/[0-9]/g,""));
                form.setItemValue("cstmrRrnB",val2.replace(/[^0-9]/g,""));
            },
        }

        , GRID2 : {
            css : {
                height : "155px"
            }
            , title : "미납목록" // 11
            , header : "번호,전화번호,가입일자,미납요금/원(A),단말할부금/원(B),합계/원(A+B),등록자,등록일시,수정자,수정일시,가입일자DATE"
            , columnIds : "lcDtlSeq,mobileNo,openDt,unpdPrc,instPrc,sumPrc,regstId,regstDttm,rvisnId,rvisnDttm,openDate"
            , widths : "60,120,100,100,110,100,80,130,80,130,10"
            , colAlign : "center,center,center,right,right,right,center,center,center,center,center"
            , colTypes : "ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro"
            , colSorting : "str,str,str,int,int,int,str,str,str,str,str"
            //, multiCheckbox : true
            , hiddenColumns: [10]
            , buttons : {
                hright : {
                    btnIss: {label: "부채증명서 발급"},
                    btnPrnt: {label: "부채증명서 출력"}
                }
            },
            onButtonClick : function(btnId) {
                var grid = this;

                switch (btnId) {
                    case 'btnIss' :
                        PAGE.FORM3.setItemValue("telFn", "");
                        PAGE.FORM3.setItemValue("telMn", "");
                        PAGE.FORM3.setItemValue("telRn", "");
                        PAGE.FORM3.setItemValue("openDt", "");
                        PAGE.FORM3.setItemValue("unpdPrc", "");
                        PAGE.FORM3.setItemValue("instPrc", "");
                    	
                    	if(grid.getRowsNum() < 1) {
                            mvno.ui.alert("먼저 미납목록을 등록해주세요.");
                            return;
                    	}
                    	
                    	mvno.cmn.ajax4confirm(	"부채증명서를 발급하시면 수정할 수 없습니다. 발급하시겠습니까?"
                    							, ROOT + "/rcp/courtMgmt/issueLcMst.json"
                    							, PAGE.FORM3
                    							, function(resultdata)
                    	{
                    		mvno.ui.alert("부채증명서 발급이 완료되었습니다.");
                    		
							PAGE.GRID1.refresh();
                    	    PAGE.GRID2.refresh();
                    		
                    	    PAGE.FORM3.setItemValue("printYn", "가능");
                    		mvno.ui.enableButton("GRID2", "btnPrnt");    
                    		mvno.ui.disableButton("FORM3", "btnAdd");
                    		mvno.ui.disableButton("FORM3", "btnDel");
                    		mvno.ui.disableButton("FORM3", "btnSave");
                    		mvno.ui.disableButton("FORM3", "btnUpdate");
                    		mvno.ui.disableButton("GRID2", "btnDscrd");    
                    		mvno.ui.disableButton("GRID2", "btnIss");
                    		
                            PAGE.FORM3.setItemValue("telFn", "");
                            PAGE.FORM3.setItemValue("telMn", "");
                            PAGE.FORM3.setItemValue("telRn", "");
                            PAGE.FORM3.setItemValue("openDt", "");
                            PAGE.FORM3.setItemValue("unpdPrc", "");
                            PAGE.FORM3.setItemValue("instPrc", "");

                            PAGE.FORM3.setItemValue("isEnable", "N");
                            PAGE.FORM3.disableItem("telFn");
                            PAGE.FORM3.disableItem("telMn");
                            PAGE.FORM3.disableItem("telRn");
                            PAGE.FORM3.disableItem("openDt"); 
                            PAGE.FORM3.disableItem("unpdPrc");
                            PAGE.FORM3.disableItem("instPrc");
                            PAGE.FORM3.disableItem("priceA");
                            PAGE.FORM3.disableItem("priceB");
                    	});
                    	
                        break;

                    case 'btnPrnt' :
                    	mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/chkMaskingLc.json", PAGE.FORM3, function(result) {
                       		if(PAGE.FORM3.getItemValue("printYn") != "가능") {
                                mvno.ui.alert("먼저 부채증명서 발급을 진행해주세요.");
                                return;
                       		}
    	               		
    						if(PAGE.FORM3.getItemValue("lcMstSeq") == null || PAGE.FORM3.getItemValue("lcMstSeq") == "") {
    							mvno.ui.alert("ECMN0002");
    							return;
    						}
    						
    	                	if(maskingYn != "Y" && maskingYn != "1") {
    	                		mvno.ui.alert("권한이 없어 부채증명서를 출력할 수 없습니다.");
    	                		return;
    	                	}
    						
    						var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/rcp/courtLcReport.jrf" + "&arg=";
    						param = encodeURI(param);
    						
    						var arg = "lcSeq#" + PAGE.FORM3.getItemValue("lcMstSeq") + "#";
    						arg = encodeURIComponent(arg);
    						
    						param = param + arg;
    						
    						var msg = "부채증명서를 출력하시겠습니까?";
    						mvno.ui.confirm(msg, function() {
    							mvno.ui.popupWindow("/cmn/report/report.do"+param, "부채증명서", 900, 700, {
    								onClose: function(win) {
    									win.closeForce();
    								}
    							});
    						});
                    	});

                        break;
                }
            },
            onRowSelect : function(rowId) {
                var grid = this;
           		if(PAGE.FORM3.getItemValue("printYn") == "가능") {
                    return;
           		}
                
                PAGE.FORM3.setItemValue("isEnable", "N");
                PAGE.FORM3.disableItem("telFn");
                PAGE.FORM3.disableItem("telMn");
                PAGE.FORM3.disableItem("telRn");
                PAGE.FORM3.disableItem("openDt");
                PAGE.FORM3.disableItem("unpdPrc");
                PAGE.FORM3.disableItem("instPrc");
                PAGE.FORM3.disableItem("priceA");
                PAGE.FORM3.disableItem("priceB");

                if(maskingYn == "Y" || maskingYn == "1") {
                    PAGE.FORM3.setItemValue("isEnable", "Y");
                    PAGE.FORM3.enableItem("telFn");
                    PAGE.FORM3.enableItem("telMn");
                    PAGE.FORM3.enableItem("telRn");
                    PAGE.FORM3.enableItem("openDt");
                    PAGE.FORM3.enableItem("unpdPrc");
                    PAGE.FORM3.enableItem("instPrc");
                    PAGE.FORM3.enableItem("priceA");
                    PAGE.FORM3.enableItem("priceB");
                }

                PAGE.FORM3.setItemValue("telFn", grid.getRowAttribute(rowId, "mobileNo").substring(0, 3));
                PAGE.FORM3.setItemValue("telMn", grid.getRowAttribute(rowId, "mobileNo").substring(4, 8));
                PAGE.FORM3.setItemValue("telRn", grid.getRowAttribute(rowId, "mobileNo").substring(9));
                PAGE.FORM3.setItemValue("openDt", grid.getRowAttribute(rowId, "openDate"));
                PAGE.FORM3.setItemValue("cstmrName", PAGE.FORM2.getItemValue("cstmrNameB"));
                PAGE.FORM3.setItemValue("cstmrRrn", PAGE.FORM2.getItemValue("cstmrRrnB"));
                PAGE.FORM3.setItemValue("cstmrAddr", PAGE.FORM2.getItemValue("cstmrAddrA"));
                
                /*금액이 0원인 데이터 수정 시도할 때 0을 그리드에서 가져오면 입력칸에서 0을 null로 인식하는 오류 발생.
                  0원일 때 값을 직접 넣어줌*/
                if(grid.getRowAttribute(rowId, "unpdPrc") == 0) {
                	PAGE.FORM3.setItemValue("unpdPrc", '0');
                } else {
                    PAGE.FORM3.setItemValue("unpdPrc", grid.getRowAttribute(rowId, "unpdPrc"));
                }
                if(grid.getRowAttribute(rowId, "instPrc") == 0) {
                	PAGE.FORM3.setItemValue("instPrc", '0');
                } else {
                    PAGE.FORM3.setItemValue("instPrc", grid.getRowAttribute(rowId, "instPrc"));
                }

                mvno.ui.showButton("FORM3" , 'btnUpdate');
                mvno.ui.hideButton("FORM3" , 'btnSave');
            }

        }

        , FORM3 : {
            title : "",
            items : [
                {type:'settings', position:'label-left', inputWidth:'auto'},
                {type:"block", blockOffset:0, list: [
                        {type:"block", blockOffset:0, list: [
                                {type:'input', label:'전화번호', name:'telFn', width:44, labelWidth:70, offsetLeft:20, validate:"ValidInteger", maxLength:3, userdata:{align:"left"}},
                                {type:"newcolumn"},
                                {type:"input", label:"-", name:"telMn", width:45, labelWidth:8, offsetLeft:5, validate:"ValidInteger", maxLength:4, userdata:{align:"left"}},
                                {type:"newcolumn"},
                                {type:"input", label:"-", name :"telRn", width:45, labelWidth:8, offsetLeft:5, validate:"ValidInteger", maxLength:4, userdata:{align:"left"}},
                                {type:"newcolumn"},
                                {type:'calendar', dateFormat:'%Y-%m-%d', serverDateFormat:'%Y%m%d', label:'가입일자', name:'openDt', calendarPosition:"bottom", readonly:true, labelWidth:85, width:100, offsetLeft:70}
                            ]},
                        {type:"block", blockOffset:0, list: [
                                {type:'input', label:'미납요금(A)', name:'unpdPrc', width:129, labelWidth:70, offsetLeft:20, validate:"ValidInteger", maxLength:9, numberFormat:"000,000,000", userdata:{align:"right"}},
                                {type:"newcolumn"},
                                {type:'label', label:'원', name:'priceA', labelWidth:20, offsetLeft:3},
                                {type:"newcolumn"},
                                {type:'input', label:'단말할부금(B)', name:'instPrc', labelWidth:85, width:129, offsetLeft:89, validate:"ValidInteger", maxLength:9, numberFormat:"000,000,000", userdata:{align:"right"}},
                                {type:"newcolumn"},
                                {type:'label', label:'원', name:'priceB', labelWidth:20, offsetLeft:3},
                                {type:"hidden", label:"이름", name:"cstmrName"},
                                {type:"hidden", label:"식별번호", name:"cstmrRrn"},
                                {type:"hidden", label:"주소", name:"cstmrAddr"},
                                {type:"hidden", label:"부채증명서시퀀스", name:"lcMstSeq"},
                                {type:"hidden", label:"미납목록시퀀스", name:"lcDtlSeq"},
                                {type:"hidden", label:"합계", name:"totalPrc", validate:"ValidInteger"},
                                {type:"hidden", label:"출력여부", name:"printYn"},
                                {type:"hidden", label:"활성화YN", name:"isEnable"}
                            ]},
                    ]}
            ],
            buttons : {
                left : {
                    btnAdd : { label : "신규추가" },
                    btnDel : { label : "삭제" }
                },
                right : {
                    btnSave : { label : "저장" },
                    btnUpdate: { label : "수정" },
                    btnClose : { label : "닫기" }
                }
            },
            onKeyUp : function (inp, ev, name, value){
                var form = this;                

                form.setItemValue("telFn", form.getItemValue("telFn").replace(/[^0-9]/g,""));
                form.setItemValue("telMn", form.getItemValue("telMn").replace(/[^0-9]/g,""));
                form.setItemValue("telRn", form.getItemValue("telRn").replace(/[^0-9]/g,""));
            },
            onButtonClick : function(btnId) {
                var form = this;
                var rowId = PAGE.GRID2.getSelectedRowId();

                switch (btnId) {
                    case 'btnAdd' :
                    	
                        if(PAGE.GRID2.getRowsNum() >= 4) {
                            mvno.ui.alert("최대 4개까지만 등록할 수 있습니다.");
                            return;
                        }

                        form.setItemValue("telFn", "");
                        form.setItemValue("telMn", "");
                        form.setItemValue("telRn", "");
                        form.setItemValue("openDt", "");
                        form.setItemValue("unpdPrc", "");
                        form.setItemValue("instPrc", "");

                        form.setItemValue("cstmrName", PAGE.FORM2.getItemValue("cstmrNameB"));
                        form.setItemValue("cstmrRrn", PAGE.FORM2.getItemValue("cstmrRrnB"));
                        form.setItemValue("cstmrAddr", PAGE.FORM2.getItemValue("cstmrAddrA"));
                        
                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/checkLcMst.json", {cstmrRrn:form.getItemValue("cstmrRrn"),lcMstSeq:form.getItemValue("lcMstSeq")}, function(resultdata) {

                        	if(form.getItemValue("cstmrName") == null || form.getItemValue("cstmrName") == "") {
	                            mvno.ui.alert("이름을 입력해주세요.");
	                            return;
	                        }
	                        if(form.getItemValue("cstmrRrn") == null || form.getItemValue("cstmrRrn") == "") {
	                            mvno.ui.alert("식별번호를 입력해주세요.");
	                            return;
	                        }
	                        if(form.getItemValue("cstmrAddr") == null || form.getItemValue("cstmrAddr") == "") {
	                            mvno.ui.alert("주소를 입력해주세요.");
	                            return;
	                        }
	
	                        PAGE.FORM3.setItemValue("isEnable", "Y");
	                        form.enableItem("telFn");
	                        form.enableItem("telMn");
	                        form.enableItem("telRn");
	                        form.enableItem("openDt");
	                        form.enableItem("unpdPrc");
	                        form.enableItem("instPrc");
	                        form.enableItem("priceA");
	                        form.enableItem("priceB");
	
	                        mvno.ui.hideButton("FORM3" , 'btnUpdate');
	                        mvno.ui.showButton("FORM3" , 'btnSave');
	                        PAGE.FORM2.disableItem("cstmrNameB")
	                        PAGE.FORM2.disableItem("cstmrRrnB")
	                        PAGE.FORM2.disableItem("cstmrAddrA")
	                        
	                        });

                        break;

                    case 'btnDel' :
                        if(rowId == null || rowId == "") {
                            mvno.ui.alert("삭제하려는 목록을 선택하세요.");
                            return;
                        }

                        form.setItemValue("lcDtlSeq", PAGE.GRID2.getRowAttribute(rowId, "lcDtlSeq"));

                        mvno.cmn.ajax4confirm("해당 목록을 삭제 하시겠습니까?", ROOT + "/rcp/courtMgmt/deleteLcDtl.json", form, function(resultdata) {
                            mvno.ui.alert("NCMN0003");

                            PAGE.GRID1.refresh();
                            PAGE.GRID2.refresh();

                            form.setItemValue("telFn", "");
                            form.setItemValue("telMn", "");
                            form.setItemValue("telRn", "");
                            form.setItemValue("openDt", "");
                            form.setItemValue("unpdPrc", "");
                            form.setItemValue("instPrc", "");

                            PAGE.FORM3.setItemValue("isEnable", "N");
                            form.disableItem("telFn");
                            form.disableItem("telMn");
                            form.disableItem("telRn");
                            form.disableItem("openDt");
                            form.disableItem("unpdPrc");
                            form.disableItem("instPrc");
                            form.disableItem("priceA");
                            form.disableItem("priceB");

                        });

                        break;

                    case 'btnSave' :
                    	var today = new Date().getTime();
                    	
                        if (form.getItemValue("isEnable") == "N") {
                            mvno.ui.alert("신규추가 버튼을 눌러주세요.");
                            return;
                        }
                    	if(   form.getItemValue("telFn").length != 3 
                           || form.getItemValue("telMn").length != 4 
                           || form.getItemValue("telRn").length != 4 ) {
                            mvno.ui.alert("전화번호 11자리를 입력해주세요.");
                            return;
                        }
                        if (form.getItemValue("openDt") == null || form.getItemValue("openDt") == "") {
                            mvno.ui.alert("가입일자를 입력해주세요.");
                            return;
                        }
                    	if(form.getItemValue("openDt").getTime() > today) {
                            mvno.ui.alert("가입일자는 현재 날짜를 초과할 수 없습니다.");
                            return;
                    	}
                        if (form.getItemValue("unpdPrc") == null || form.getItemValue("unpdPrc") == "") {
                            mvno.ui.alert("미납요금을 입력해주세요.");
                            return;jxa
                        }
                        if (form.getItemValue("instPrc") == null || form.getItemValue("instPrc") == "") {
                            mvno.ui.alert("단말할부금을 입력해주세요.");
                            return;
                        }


                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/insertLcList.json", form, function(resultdata) {
                            mvno.ui.alert("등록되었습니다.");
                            
                            form.setItemValue("lcMstSeq", resultdata.result.lcMstSeq);
                            
                            PAGE.GRID1.clearAll();
                            PAGE.GRID2.clearAll();
                            
                            PAGE.GRID1.list(ROOT + "/rcp/courtMgmt/getCourtLcMgmtList.json", PAGE.FORM1);
                            PAGE.GRID2.list(ROOT + "/rcp/courtMgmt/getCourtLcMgmtDtlList.json", form);

                            form.setItemValue("telFn", "");
                            form.setItemValue("telMn", "");
                            form.setItemValue("telRn", "");
                            form.setItemValue("openDt", "");
                            form.setItemValue("unpdPrc", "");
                            form.setItemValue("instPrc", "");

                            PAGE.FORM3.setItemValue("isEnable", "N");
                            form.disableItem("telFn");
                            form.disableItem("telMn");
                            form.disableItem("telRn");
                            form.disableItem("openDt");
                            form.disableItem("unpdPrc");
                            form.disableItem("instPrc");
                            form.disableItem("priceA");
                            form.disableItem("priceB");

                            PAGE.FORM2.disableItem("cstmrNameB");
                            PAGE.FORM2.disableItem("cstmrRrnB");
                            PAGE.FORM2.disableItem("cstmrAddrA");

                        });

                        break;

                    case 'btnUpdate' :
                    	var today = new Date().getTime();
                    	
                        if (form.getItemValue("isEnable") == "N") {
                            mvno.ui.alert("수정 권한이 없습니다.");
                            return;
                        }
                    	if(   form.getItemValue("telFn").length != 3 
                           || form.getItemValue("telMn").length != 4 
                           || form.getItemValue("telRn").length != 4 ) {
                            mvno.ui.alert("전화번호 11자리를 입력해주세요.");
                            return;
                    	}
                        if (form.getItemValue("openDt") == null || form.getItemValue("openDt") == "") {
                            mvno.ui.alert("가입일자를 입력해주세요.");
                            return;
                        }
                    	if(form.getItemValue("openDt").getTime() > today) {
                            mvno.ui.alert("가입일자는 현재 날짜를 초과할 수 없습니다.");
                            return;
                    	}
                        if (form.getItemValue("unpdPrc") == null || form.getItemValue("unpdPrc") == "") {
                            mvno.ui.alert("미납요금을 입력해주세요.");
                            return;
                        }
                        if (form.getItemValue("instPrc") == null || form.getItemValue("instPrc") == "") {
                            mvno.ui.alert("단말할부금을 입력해주세요.");
                            return;
                        }
                        form.setItemValue("lcDtlSeq", PAGE.GRID2.getRowAttribute(rowId, "lcDtlSeq"));

                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/updateLcDtl.json", form, function(resultdata) {
                            mvno.ui.alert("수정되었습니다.");

                            PAGE.GRID1.refresh();
                            PAGE.GRID2.refresh();

                            form.setItemValue("telFn", "");
                            form.setItemValue("telMn", "");
                            form.setItemValue("telRn", "");
                            form.setItemValue("openDt", "");
                            form.setItemValue("unpdPrc", "");
                            form.setItemValue("instPrc", "");

                            PAGE.FORM3.setItemValue("isEnable", "N");
                            form.disableItem("telFn");
                            form.disableItem("telMn");
                            form.disableItem("telRn");
                            form.disableItem("openDt");
                            form.disableItem("unpdPrc");
                            form.disableItem("instPrc");
                            form.disableItem("priceA");
                            form.disableItem("priceB");

                            PAGE.FORM2.disableItem("cstmrNameB")
                            PAGE.FORM2.disableItem("cstmrRrnB")
                            PAGE.FORM2.disableItem("cstmrAddrA")
                        });

                        break;

                    case 'btnClose' :
                        mvno.ui.closeWindowById(form, true);
                        break;
                }
            }
        }
    };

    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0015",orderBy:"etc1"}, PAGE.FORM1, "lcStatCd");	// 부채증명서 발급 상태
        }
    };
</script>
