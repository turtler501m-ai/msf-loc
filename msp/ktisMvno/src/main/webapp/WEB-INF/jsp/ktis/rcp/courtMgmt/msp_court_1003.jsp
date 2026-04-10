<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM10" class="section-box"></div>
<div id="GRID20" class="section-full"></div>
<div id="GRID30" class="section-full"></div>
<div id="FORM40" class="section-box"></div>
<div id="GRID40" class="section-full"></div>

<div id="POPUP21">
	<div id="FORM21" class="section-box"></div>
</div>
<div id="POPUP31">
	<div id="FORM31" class="section-box"></div>
</div>
<div id="POPUP32">
	<div id="FORM32" class="section-box"></div>
</div>
<div id="POPUP41">
	<div id="FORM41" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
  	var maskingYn = '${maskingYn}';

    var DHX = {
        FORM10 : {
        	title : "기본정보",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
						{type:"block", list:[
								{type:"input", label:"식별번호", name:"searchRrn", width:120, maxLength:13, offsetLeft:24},
								{type:"newcolumn"},
								{type:"button", value:"검색", name:"btnSearch", width:30}
						    ]},
						{type:"block", list:[
						        {type:"input", label:"이름", name:"cstmrName", width:120, offsetLeft:24, maxLength:16, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"식별번호", name:"cstmrRrn", width:120, maxLength:13, offsetLeft:30, disabled:true},
						        {type:"newcolumn"},
						        {type:"select", label:"법정관리유형", name:"crTpCd", width:120, labelWidth:77, offsetLeft:30, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"진행상태", name:"tpJinh", width:120, labelWidth:53, offsetLeft:38, readonly:true, disabled:true}
						    ]},
						{type:"block", list:[
						        {type:"input", label:"관할법원", name:"crComp", width:120, offsetLeft:24, maxLength:16, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"판결번호", name:"noJudg", width:120, offsetLeft:30, maxLength:16, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"재판부", name:"judgPan", width:120, labelWidth:77, maxLength:16, offsetLeft:30, disabled:true},
						        {type:"newcolumn"},
						        {type:"select", label:"종결여부", name:"endYn", width:120, labelWidth:53, offsetLeft:38, disabled:true}
						    ]},
						{type:"block", list:[
						        {type:"input", label:"최초생성일", name:"firstDt", width:120, offsetLeft:24, readonly:true, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"최종변경일", name:"lastDt", width:120, offsetLeft:30, readonly:true, disabled:true}
						    ]},
						{type:"block", list:[
						        {type:"input", label:"총미납요금", name:"totalPric", width:120, offsetLeft:24, readonly:true, disabled:true, validate: "ValidInteger", numberFormat:"000,000,000,000"},
						        {type:"newcolumn"},
                                {type:'label', label:'원', name:'priceA', labelWidth:20, offsetLeft:3, disabled:true},
						        {type:"newcolumn"},
						        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"변제개시일", name:"rbStrtDt", calendarPosition:"bottom", readonly:true, width:120, offsetLeft:7, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"변제예정액", name:"rbEsti", width:120, labelWidth:77, offsetLeft:30, disabled:true, validate: "ValidInteger", numberFormat:"000,000,000,000", maxLength:9},
						        {type:"newcolumn"},
                                {type:'label', label:'원', name:'priceB', labelWidth:20, offsetLeft:3, disabled:true},
						        {type:"newcolumn"},
						        {type:"input", label:"변제율", name:"rbRate", width:120, labelWidth:53, offsetLeft:15, maxLength:6, disabled:true}
						    ]},
						{type:"block", list:[
						        {type:"input", label:"변제기간", name:"rbPrid", width:120, offsetLeft:24, maxLength:6, disabled:true},
						        {type:"newcolumn"},
						        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"계좌신고일", name:"acRegDt", calendarPosition:"bottom", readonly:true, disabled:true, width:120, offsetLeft:30},
						        {type:"newcolumn"},
						        {type:"input", label:"최종입금일", name:"lastInoutDt", width:120, labelWidth:77, offsetLeft:30, readonly:true, disabled:true},
						        {type:"newcolumn"},
						        {type:"button", value:"등록", name:"btnRgst", width:30, offsetLeft:161, disabled:true},
						        {type:"button", value:"수정", name:"btnModi", width:30, offsetLeft:161, disabled:true},
						        {type:"hidden", label:"법정관리고객시퀀스", name:"crSeq", width:10}
						    ]}
            ],

            onButtonClick : function(btnId) {
                var form = this;

                switch (btnId) {
                    case "btnSearch" :
                    	if(form.getItemValue("searchRrn").length != 13) {
                            mvno.ui.alert("식별번호 13자리를 입력해주세요.");
                            return;
                    	}
                    	
                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/chkCrCstmrRrn.json", form, function(resultdata) {
							var chkCstmrRrn = resultdata.result.chkCstmrRrn;
							if(chkCstmrRrn > 0) {
	                            mvno.ui.alert("기존에 등록한 고객 정보가 있습니다.");
	                            return;
							}
							form.hideItem("btnModi");
							form.showItem("btnRgst");
							form.enableItem("btnRgst");
							
							form.setItemValue("cstmrRrn", form.getItemValue("searchRrn"));
							
							form.enableItem("cstmrName");
							form.enableItem("crTpCd");
							form.enableItem("crComp");
							form.enableItem("noJudg");
							form.enableItem("judgPan");
							form.enableItem("endYn");
							form.enableItem("rbStrtDt");
							form.enableItem("rbEsti");
							form.enableItem("priceB");
							form.enableItem("rbRate");
							form.enableItem("rbPrid");
							form.enableItem("acRegDt");
							form.enableItem("btnRgst");
							form.setItemValue("endYn", "N");
                        });
                        break;
                        
                    case "btnRgst" :
                        if(form.getItemValue("cstmrName") == null || form.getItemValue("cstmrName") == "") {
                            mvno.ui.alert("이름을 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("cstmrRrn") == null || form.getItemValue("cstmrRrn") == "") {
                            mvno.ui.alert("식별번호를 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("crTpCd") == null || form.getItemValue("crTpCd") == "") {
                            mvno.ui.alert("법정관리유형을 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("crComp") == null || form.getItemValue("crComp") == "") {
                            mvno.ui.alert("관할법원을 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("noJudg") == null || form.getItemValue("noJudg") == "") {
                            mvno.ui.alert("판결번호를 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("endYn") == null || form.getItemValue("endYn") == "") {
                            mvno.ui.alert("종결여부를 입력해주세요.");
                            return;
                        }
                        
                        mvno.cmn.ajax4confirm("등록하시겠습니까?", ROOT + "/rcp/courtMgmt/regstCrCstmr.json", form, function(resultdata) {
                            mvno.ui.alert("등록되었습니다.");
                            
                            form.setItemValue("crSeq", resultdata.result.crSeq);
                            
                			form.hideItem("btnRgst");
                			form.showItem("btnModi");			
                			form.disableItem("searchRrn");
                			form.disableItem("btnSearch");
            				form.enableItem("btnModi");
                			
                			if(maskingYn != "Y" && maskingYn != "1") {
                				form.disableItem("cstmrName");
                				form.disableItem("cstmrRrn");
                				form.disableItem("crTpCd");
                				form.disableItem("crComp");
                				form.disableItem("noJudg");
                				form.disableItem("judgPan");
                				form.disableItem("endYn");
                				form.disableItem("rbStrtDt");
                				form.disableItem("rbEsti");
                				form.disableItem("priceB");
                				form.disableItem("rbRate");
                				form.disableItem("rbPrid");
                				form.disableItem("acRegDt");
                				form.disableItem("btnModi");
                			}
                			
                	    	PAGE.GRID20.list(ROOT + "/rcp/courtMgmt/getCrBondList.json", form);
                	    	PAGE.GRID30.list(ROOT + "/rcp/courtMgmt/getCrRcivList.json", form);
                	    	PAGE.GRID40.list(ROOT + "/rcp/courtMgmt/getCrInoutList.json", form);
                			
                            mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/insertLcInfo.json", form, function(resultdata) {
    							var chkIsLc = resultdata.result.chkIsLc;
    							if(chkIsLc == null || chkIsLc == "") {
    	                            return;
    							}
                            	
                                mvno.ui.alert("기존에 발급된 부채증명서 정보가 자동 등록되었습니다.");
                                
                            	PAGE.GRID20.clearAll();
                            	PAGE.GRID30.clearAll();
                            	PAGE.GRID20.list(ROOT + "/rcp/courtMgmt/getCrBondList.json", form);
                            	PAGE.GRID30.list(ROOT + "/rcp/courtMgmt/getCrRcivList.json", form);
                            	form.setItemValue("tpJinh", "부채증명서발급");
                            });
                        });

                        
                    	break;
                    	
                    case "btnModi" :
                        if(form.getItemValue("cstmrName") == null || form.getItemValue("cstmrName") == "") {
                            mvno.ui.alert("이름을 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("cstmrRrn") == null || form.getItemValue("cstmrRrn") == "") {
                            mvno.ui.alert("식별번호를 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("crTpCd") == null || form.getItemValue("crTpCd") == "") {
                            mvno.ui.alert("법정관리유형을 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("crComp") == null || form.getItemValue("crComp") == "") {
                            mvno.ui.alert("관할법원을 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("noJudg") == null || form.getItemValue("noJudg") == "") {
                            mvno.ui.alert("판결번호를 입력해주세요.");
                            return;
                        }
                        if(form.getItemValue("endYn") == null || form.getItemValue("endYn") == "") {
                            mvno.ui.alert("종결여부를 입력해주세요.");
                            return;
                        }
                        
                        mvno.cmn.ajax4confirm("수정하시겠습니까?", ROOT + "/rcp/courtMgmt/modifyCrCstmr.json", form, function(resultdata) {
                            mvno.ui.alert("수정되었습니다.");
                        });
                    	
                    	break;
                }
            },
            onKeyUp : function (inp, ev, name, value){
                var form = this;                

                form.setItemValue("searchRrn", form.getItemValue("searchRrn").replace(/[^0-9]/g,""));
                form.setItemValue("cstmrName", form.getItemValue("cstmrName").replace(/[0-9]/g,""));
                form.setItemValue("cstmrRrn", form.getItemValue("cstmrRrn").replace(/[^0-9]/g,""));
            }
        }

        , GRID20 : {
            css : {
                height : "170px"
            }
            , title : "채권내역" // 12
            , header : "채권번호,전화번호,가입일자,미납요금/원(A),잔여단말할부금/원(B),미납총액/원(A+B),등록자,등록일시,수정자,수정일시,가입일자DATE,채권시퀀스"
            , columnIds : "bondNum,mobileNo,openDt,unpdPrc,instPrc,sumPrc,regstId,regstDttm,rvisnId,rvisnDttm,openDate,crBondSeq"
            , widths : "100,100,100,125,125,125,65,150,65,150,10,10"
            , colAlign : "center,center,center,right,right,right,center,center,center,center,center,center"
            , colTypes : "ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro,ron"
            , colSorting : "str,str,str,int,int,int,str,str,str,str,str,int"
            //, multiCheckbox : true
            , hiddenColumns : [10,11]
            , paging : false
            , buttons : {
                right : {
                	btnDel : { label : "삭제"},
                    btnRgst : { label : "등록"}                    
                }
            },

            onRowDblClicked : function(rowId, celInd) {
                this.callEvent('onButtonClick', ['btnDtl']);
            },

            onButtonClick : function(btnId) {
                var grid = this;
                var rowId = grid.getSelectedRowId();

                switch (btnId) {
	                case 'btnDel' :
	                    if(rowId == null) {
	                        mvno.ui.alert("삭제하려는 목록을 선택하세요.");
	                        return;
	                    }
	
	                    mvno.cmn.ajax4confirm("해당 목록을 삭제 하시겠습니까?", ROOT + "/rcp/courtMgmt/deleteCrBond.json", {crBondSeq:grid.getRowAttribute(rowId, "crBondSeq")}, function(resultdata) {
	                        mvno.ui.alert("삭제되었습니다.");
	
	                        grid.refresh();
	                    });
	
	                    break;
                    
                    case 'btnRgst' :
                    	if(PAGE.FORM10.getItemValue("crSeq") == null || PAGE.FORM10.getItemValue("crSeq") == "") {
                            mvno.ui.alert("먼저 고객 등록을 완료해주세요.");
                            return;
                    	}
                    	
                        if(PAGE.GRID20.getRowsNum() >= 4) {
                            mvno.ui.alert("채권은 최대 4개까지만 등록할 수 있습니다.");
                            return;
                        }
                    	
                        mvno.ui.createForm("FORM21");
                    	PAGE.FORM21.clear();
                    	
                        PAGE.FORM21.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM21.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM21.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                        
                        mvno.ui.hideButton("FORM21", "btnModi");
                        mvno.ui.showButton("FORM21", "btnSave");
                        
                        PAGE.FORM21.enableItem("bondNum");
                        PAGE.FORM21.enableItem("openDt");
                        PAGE.FORM21.enableItem("telFn");
                        PAGE.FORM21.enableItem("telMn");
                        PAGE.FORM21.enableItem("telRn");
                        PAGE.FORM21.enableItem("unpdPrc");
                        PAGE.FORM21.enableItem("priceA");
                        PAGE.FORM21.enableItem("instPrc");
                        PAGE.FORM21.enableItem("priceB");
                        
                        mvno.ui.popupWindowById("POPUP21", "채권내역 등록", 600, 283, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                    	break;
                    	
                    case 'btnDtl' :
                        mvno.ui.createForm("FORM21");
                    	PAGE.FORM21.clear();
                    	
                        PAGE.FORM21.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM21.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM21.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                        PAGE.FORM21.setItemValue("crBondSeq", grid.getRowAttribute(rowId, "crBondSeq"));
                        
                        PAGE.FORM21.setItemValue("bondNum", grid.getRowAttribute(rowId, "bondNum"));
                        PAGE.FORM21.setItemValue("openDt", grid.getRowAttribute(rowId, "openDate"));
                        PAGE.FORM21.setItemValue("telFn", grid.getRowAttribute(rowId, "mobileNo").substring(0,3));
                        PAGE.FORM21.setItemValue("telMn", grid.getRowAttribute(rowId, "mobileNo").substring(4,8));
                        PAGE.FORM21.setItemValue("telRn", grid.getRowAttribute(rowId, "mobileNo").substring(9));
                        
                        /*금액이 0원인 데이터 수정 시도할 때 0을 그리드에서 가져오면 입력칸에서 0을 null로 인식하는 오류 발생.
                        0원일 때 값을 직접 넣어줌*/
                        if(grid.getRowAttribute(rowId, "unpdPrc") == 0) {
                        	PAGE.FORM21.setItemValue("unpdPrc", '0');
                        } else {
                        	PAGE.FORM21.setItemValue("unpdPrc", grid.getRowAttribute(rowId, "unpdPrc"));
                        }
                        if(grid.getRowAttribute(rowId, "instPrc") == 0) {
                        	PAGE.FORM21.setItemValue("instPrc", '0');
                        } else {
                        	PAGE.FORM21.setItemValue("instPrc", grid.getRowAttribute(rowId, "instPrc"));
                        }
                        
                        
                        
                        mvno.ui.hideButton("FORM21", "btnSave");
                        mvno.ui.showButton("FORM21", "btnModi");
            			                        
                        if(maskingYn != "Y" && maskingYn != "1") {
                            PAGE.FORM21.disableItem("bondNum");
                            PAGE.FORM21.disableItem("openDt");
                            PAGE.FORM21.disableItem("telFn");
                            PAGE.FORM21.disableItem("telMn");
                            PAGE.FORM21.disableItem("telRn");
                            PAGE.FORM21.disableItem("unpdPrc");
                            PAGE.FORM21.disableItem("priceA");
                            PAGE.FORM21.disableItem("instPrc");
                            PAGE.FORM21.disableItem("priceB");
                    		mvno.ui.disableButton("FORM21", "btnModi");
                        }
                        
                        mvno.ui.popupWindowById("POPUP21", "채권내역 수정", 600, 283, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                    	break;
                }
            }
        }
        
        , FORM21 : {
            title : "",
            items : [
                {type:'settings', position:'label-left', inputWidth:'auto'},
                {type:"block", blockOffset:0, list: [
                        {type:'input', label:'이름', name:'cstmrName', width:70, labelWidth:65, offsetLeft:23, maxLength:20, userdata:{align:"left"}, readonly:true, disabled:true},
                        {type:"newcolumn"},
                        {type:'input', label:'식별번호', name:'cstmrRrn', width:120, labelWidth:65, offsetLeft:93, maxLength:13, userdata:{align:"left"}, readonly:true, disabled:true},
                        {type:'hidden', label:'법정관리고객시퀀스', name:'crSeq', labelWidth:20, offsetLeft:3}	,	                        
                        {type:'hidden', label:'채권시퀀스', name:'crBondSeq', labelWidth:20, offsetLeft:3}		                        
                    ]},
                {type: "fieldset", label: "채권내역", inputWidth:530, list:[
	                    {type:"block", blockOffset:0, list: [
			                        {type:'input', label:'채권번호', name:'bondNum', width:150, labelWidth:67, offsetLeft:8, maxLength:16, userdata:{align:"left"}}          
	                    	]},
	                    {type:"block", blockOffset:0, list: [
				                    {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"openDt", label:"가입일자", calendarPosition:"bottom", readonly:true, width:100, labelWidth:67, offsetLeft:8},
				                    {type:"newcolumn"},
				                    {type:'input', label:'전화번호', name:'telFn', width:45, labelWidth:65, offsetLeft:63, maxLength:3, userdata:{align:"left"}},
				                    {type:"newcolumn"},
	                                {type:"input", label:"-", name:"telMn", width:45, labelWidth:8, offsetLeft:5, maxLength:4, userdata:{align:"left"}},
	                                {type:"newcolumn"},
	                                {type:"input", label:"-", name :"telRn", width:45, labelWidth:8, offsetLeft:5, maxLength:4, userdata:{align:"left"}}
				            ]},
                    	{type:"block", blockOffset:0, list: [
				                    {type:'input', label:'미납요금(A)', name:'unpdPrc', width:100, labelWidth:67, offsetLeft:8, validate:"ValidInteger", maxLength:9, numberFormat:"000,000,000,000"},
				                    {type:"newcolumn"},
			                        {type:'label', label:'원', name:'priceA', labelWidth:20, offsetLeft:3},
							        {type:"newcolumn"},
				                    {type:'input', label:'잔여단말할부금(B)', name:'instPrc', width:100, labelWidth:110, offsetLeft:40, validate:"ValidInteger", maxLength:9, numberFormat:"000,000,000,000"},
							        {type:"newcolumn"},
			                        {type:'label', label:'원', name:'priceB', labelWidth:20, offsetLeft:3}
					        ]}
                	]}
        		],
            buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnModi : { label : "수정" },
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
                var rowId = PAGE.GRID20.getSelectedRowId();

                switch (btnId) {
                    case 'btnSave' :
                    	var today = new Date().getTime();
                    	
                    	if(form.getItemValue("bondNum") == null || form.getItemValue("bondNum") == "") {
                            mvno.ui.alert("채권번호를 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("openDt") == null || form.getItemValue("openDt") == "") {
                            mvno.ui.alert("가입일자를 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("openDt").getTime() > today) {
                            mvno.ui.alert("가입일자는 현재 날짜를 초과할 수 없습니다.");
                            return;
                    	}
                    	                    	
                    	if(   form.getItemValue("telFn").length != 3 
                           || form.getItemValue("telMn").length != 4 
                           || form.getItemValue("telRn").length != 4 ) {
                            mvno.ui.alert("전화번호 11자리를 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("unpdPrc") == null || form.getItemValue("unpdPrc") == "") {
                            mvno.ui.alert("미납요금을 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("instPrc") == null || form.getItemValue("instPrc") == "") {
                            mvno.ui.alert("잔여단말할부금을 입력해주세요.");
                            return;
                    	}
                    	
                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/insertCrBond.json", form, function(resultdata) {
                        	mvno.ui.closeWindowById("POPUP21", true);
                            mvno.ui.alert("등록되었습니다.");
                    		
                            PAGE.GRID20.refresh();
                        });
                            
                        break;
                        
                    case 'btnModi' :
                    	var today = new Date().getTime();
                    	
                    	if(form.getItemValue("bondNum") == null || form.getItemValue("bondNum") == "") {
                            mvno.ui.alert("채권번호를 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("openDt") == null || form.getItemValue("openDt") == "") {
                            mvno.ui.alert("가입일자를 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("openDt").getTime() > today) {
                            mvno.ui.alert("가입일자는 현재 날짜를 초과할 수 없습니다.");
                            return;
                    	}
                    	
                    	if(   form.getItemValue("telFn").length != 3 
                           || form.getItemValue("telMn").length != 4 
                           || form.getItemValue("telRn").length != 4 ) {
                            mvno.ui.alert("전화번호 11자리를 입력해주세요.");
                            return;
                        }
                    	
                    	if(form.getItemValue("unpdPrc") == null || form.getItemValue("unpdPrc") == "") {
                            mvno.ui.alert("미납요금을 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("instPrc") == null || form.getItemValue("instPrc") == "") {
                            mvno.ui.alert("잔여단말할부금을 입력해주세요.");
                            return;
                    	}
                    	
                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/modifyCrBond.json", form, function(resultdata) {
                        	mvno.ui.closeWindowById("POPUP21", true);
                            mvno.ui.alert("수정되었습니다.");
                    		
                            PAGE.GRID20.refresh();
                        }); 
                    	
                    	break;

                    case 'btnClose' :
                        mvno.ui.closeWindowById(form, true);
                        break;
                }
            }
        }
        
        , GRID30 : {
            css : {
                height : "170px"
            }
            , title : "접수이력" // 17
            , header : "번호,접수일,기준일,진행상태,상세내용,파일등록,등록자,등록일시,수정자,수정일시,접수일DATE,기준일DATE,진행상태Cd,파일등록YN,파일경로,파일명,파일ID"
            , columnIds : "crRcivSeq,rcivDt,stanDt,tpJinh,strDtl,fileReg,regstId,regstDttm,rvisnId,rvisnDttm,rcivDate,stanDate,tpJinhCd,fileYn,filePath,fileName,fileId"
            , widths : "50,100,100,125,250,75,65,180,65,180,10,10,10,10,10,10,10"
            , colAlign : "center,center,center,center,left,center,center,center,center,center,center,center,center,center,center,center,center"
            , colTypes : "ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
            , colSorting : "int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
            //, multiCheckbox : true
            , hiddenColumns : [10, 11, 12, 13, 14, 15, 16]
            , paging : false
            , buttons : {
                right : {
                	btnFile : { label : "파일관리"},
                	btnDel : { label : "삭제"},
                    btnRgst : { label : "등록"}
                }
            },

            onRowDblClicked : function(rowId, celInd) {
                this.callEvent('onButtonClick', ['btnDtl']);
            },
            
            onRowSelect : function(rowId) {
            	mvno.ui.enableButton("GRID30", "btnFile");
            },

            onButtonClick : function(btnId) {
                var grid = this;
                var rowId = grid.getSelectedRowId();

                switch (btnId) {
                    case 'btnFile' :
                    	fileLimitCnt = 1;
                    	
	                    if(rowId == null) {
	                        mvno.ui.alert("목록을 선택하세요.");
	                        return;
	                    }
	                    
                        mvno.ui.createForm("FORM32");
                    	PAGE.FORM32.clear();
                    	
                        var uploader = PAGE.FORM32.getUploader("file_upload");
                        uploader.revive();
                    	
                        PAGE.FORM32.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM32.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM32.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                        PAGE.FORM32.setItemValue("crRcivSeq", grid.getRowAttribute(rowId, "crRcivSeq"));
                       
                        if(grid.getRowAttribute(rowId, "fileYn") == "Y") {
                  			PAGE.FORM32.setItemValue("fileId1", grid.getRowAttribute(rowId,"fileId"));
                  			PAGE.FORM32.setItemValue("fileName1", grid.getRowAttribute(rowId,"fileName"));
                           	PAGE.FORM32.enableItem("fileDown1");
                           	PAGE.FORM32.enableItem("fileDel1");
                           	PAGE.FORM32.hideItem("file_upload");
                           	
                           	PAGE.FORM32.setUserData("file_upload","limitCount",0);
                            PAGE.FORM32.setUserData("file_upload","limitsize",1000);
                        } else {
                  			PAGE.FORM32.setItemValue("fileId1", "");
                  			PAGE.FORM32.setItemValue("fileName1", "");
                           	PAGE.FORM32.disableItem("fileDown1");
                           	PAGE.FORM32.disableItem("fileDel1");
                           	PAGE.FORM32.showItem("file_upload");
                        }
                        
                        mvno.ui.popupWindowById("POPUP32", "파일관리", 568, 250, {
                            onClose: function(win) {
                            	uploader.reset();
                                win.closeForce();
                            }
                        });
                    	break;
                    	
	                case 'btnDel' :
	                    if(rowId == null) {
	                        mvno.ui.alert("삭제하려는 목록을 선택하세요.");
	                        return;
	                    }
	
	                    mvno.cmn.ajax4confirm("해당 목록을 삭제 하시겠습니까?", ROOT + "/rcp/courtMgmt/deleteCrRciv.json", {crRcivSeq:grid.getRowAttribute(rowId, "crRcivSeq")}, function(resultdata) {
	                        mvno.ui.alert("삭제되었습니다.");
	
	                        grid.refresh();
	                        mvno.ui.disableButton("GRID30", "btnFile");
	                    });
	
	                    break;
                    	
                    case 'btnRgst' :
                    	if(PAGE.FORM10.getItemValue("crSeq") == null || PAGE.FORM10.getItemValue("crSeq") == "") {
                            mvno.ui.alert("먼저 고객 등록을 완료해주세요.");
                            return;
                    	}
                        mvno.ui.createForm("FORM31");
                    	PAGE.FORM31.clear();
                    	
                        PAGE.FORM31.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM31.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM31.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                        
                        mvno.ui.hideButton("FORM31", "btnModi");
                        mvno.ui.showButton("FORM31", "btnSave");
                        
                        if(PAGE.FORM10.getItemValue("crTpCd") == "1") {
                			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0033", orderBy:"etc1"}, PAGE.FORM31, "tpJinh");
                        } else if(PAGE.FORM10.getItemValue("crTpCd") == "2") {
                			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0034", orderBy:"etc1"}, PAGE.FORM31, "tpJinh");
                        } else if(PAGE.FORM10.getItemValue("crTpCd") == "3") {
                			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0035", orderBy:"etc1"}, PAGE.FORM31, "tpJinh");
                        }
                        
                        mvno.ui.popupWindowById("POPUP31", "접수이력 등록", 549, 245, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                    	break;
                    	
                    case 'btnDtl' :
                        mvno.ui.createForm("FORM31");
                    	PAGE.FORM31.clear();
                    	
                        PAGE.FORM31.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM31.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM31.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                        PAGE.FORM31.setItemValue("crRcivSeq", grid.getRowAttribute(rowId, "crRcivSeq"));
                        
                        PAGE.FORM31.setItemValue("rcivDt", grid.getRowAttribute(rowId, "rcivDate"));
                        PAGE.FORM31.setItemValue("stanDt", grid.getRowAttribute(rowId, "stanDate"));
                        PAGE.FORM31.setItemValue("strDtl", grid.getRowAttribute(rowId, "strDtl"));
                        
                        mvno.ui.hideButton("FORM31", "btnSave");
                        mvno.ui.showButton("FORM31", "btnModi");   
                        
                        if(maskingYn != "Y" && maskingYn != "1") {
                            PAGE.FORM31.disableItem("rcivDt");
                            PAGE.FORM31.disableItem("stanDt");
                            PAGE.FORM31.disableItem("strDtl");
                            PAGE.FORM31.disableItem("tpJinh");
                    		mvno.ui.disableButton("FORM31", "btnModi");
                        }
                        
                        if(PAGE.FORM10.getItemValue("crTpCd") == "1") {
                			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0033", orderBy:"etc1"}, PAGE.FORM31, "tpJinh");
                        } else if(PAGE.FORM10.getItemValue("crTpCd") == "2") {
                			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0034", orderBy:"etc1"}, PAGE.FORM31, "tpJinh");
                        } else if(PAGE.FORM10.getItemValue("crTpCd") == "3") {
                			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0035", orderBy:"etc1"}, PAGE.FORM31, "tpJinh");
                        }
                        PAGE.FORM31.setItemValue("tpJinh", grid.getRowAttribute(rowId, "tpJinhCd"));
                        
                        mvno.ui.popupWindowById("POPUP31", "접수이력 수정", 549, 245, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                    	break;
                }
            }
        }
        
        , FORM31 : {
            title : "",
            items : [
                {type:'settings', position:'label-left', inputWidth:'auto'},
                {type:"block", blockOffset:0, list: [
                        {type:'input', label:'이름', name:'cstmrName', width:70, labelWidth:65, offsetLeft:23, maxLength:16, userdata:{align:"left"}, readonly:true, disabled:true},
                        {type:"newcolumn"},
                        {type:'input', label:'식별번호', name:'cstmrRrn', width:120, labelWidth:65, offsetLeft:60, maxLength:13, userdata:{align:"left"}, readonly:true, disabled:true},     
			            {type:'hidden', label:'법정관리고객시퀀스', name:'crSeq', labelWidth:20, offsetLeft:3},
			            {type:'hidden', label:'접수이력시퀀스', name:'crRcivSeq', labelWidth:20, offsetLeft:3}
                    ]},
                {type: "fieldset", label: "접수내용", inputWidth:482, list:[
	                    {type:"block", blockOffset:0, list: [
				                    {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"rcivDt", label:"접수일", calendarPosition:"bottom", readonly:true, width:110, labelWidth:65, offsetLeft:10},
				                    {type:"newcolumn"},
				                    {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"stanDt", label:"기준일", calendarPosition:"bottom", readonly:true, width:110, labelWidth:65, offsetLeft:20}
				            ]},
                    	{type:"block", blockOffset:0, list: [
				                    {type:'select', label:'진행상태', name:'tpJinh', width:110, labelWidth:65, offsetLeft:10},
				                    {type:"newcolumn"},
				                    {type:'input', label:'상세내용', name:'strDtl', width:160, labelWidth:65, offsetLeft:20, maxLength:660}
					        ]}
                	]}
        		],
            buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnModi : { label : "수정" },                    
                    btnClose : { label : "닫기" }
                }
            },
            onButtonClick : function(btnId) {
                var form = this;
                var rowId = PAGE.GRID30.getSelectedRowId();

                switch (btnId) {
                    case 'btnSave' :
                    	if(form.getItemValue("rcivDt") == null || form.getItemValue("rcivDt") == "") {
                            mvno.ui.alert("접수일을 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("stanDt") == null || form.getItemValue("stanDt") == "") {
                            mvno.ui.alert("기준일을 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("tpJinh") == null || form.getItemValue("tpJinh") == "") {
                            mvno.ui.alert("진행상태를 입력해주세요.");
                            return;
                    	}
                    	
                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/insertCrRciv.json", form, function(resultdata) {
                        	mvno.ui.closeWindowById("POPUP31", true);
                            mvno.ui.alert("등록되었습니다.");
                    		
                            PAGE.GRID30.refresh();
                            mvno.ui.disableButton("GRID30", "btnFile");                            
                        });
                        
                        break;
                        
                    case 'btnModi' :
                    	if(form.getItemValue("rcivDt") == null || form.getItemValue("rcivDt") == "") {
                            mvno.ui.alert("접수일을 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("stanDt") == null || form.getItemValue("stanDt") == "") {
                            mvno.ui.alert("기준일을 입력해주세요.");
                            return;
                    	}
                    	
                    	if(form.getItemValue("tpJinh") == null || form.getItemValue("tpJinh") == "") {
                            mvno.ui.alert("진행상태를 입력해주세요.");
                            return;
                    	}
                    	
                        mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/modifyCrRciv.json", form, function(resultdata) {
                        	mvno.ui.closeWindowById("POPUP31", true);
                            mvno.ui.alert("수정되었습니다.");
                    		
                            PAGE.GRID30.refresh();
                            mvno.ui.disableButton("GRID30", "btnFile");
                        }); 
                    	
                    	break;

                    case 'btnClose' :
                        mvno.ui.closeWindowById(form, true);
                        break;
                }
            }
        }
        
        , FORM32 : {
            title : "",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
                {type:"block", blockOffset:0, list: [
                        {type:'input', label:'이름', name:'cstmrName', width:70, labelWidth:65, offsetLeft:33, maxLength:16, userdata:{align:"left"}, readonly:true, disabled:true},
                        {type:"newcolumn"},
                        {type:'input', label:'식별번호', name:'cstmrRrn', width:120, labelWidth:65, offsetLeft:60, maxLength:13, userdata:{align:"left"}, readonly:true, disabled:true},
			            {type:'hidden', label:'법정관리고객시퀀스', name:'crSeq', labelWidth:20, offsetLeft:3},
			            {type:'hidden', label:'접수이력시퀀스', name:'crRcivSeq', labelWidth:20, offsetLeft:3}
                    ]},
                {type: "fieldset", label: "접수 관련 파일 첨부", inputWidth:500, list:[
						{type: "block", list: [
								{type: 'input', name: 'fileName1', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
								{type: 'hidden', name: 'fileId1', label: '파일ID'},
								{type: "newcolumn"},
								{type:"button", name: 'fileDown1', value:"다운로드"},
								{type: "newcolumn"},
								{type:"button", name: 'fileDel1', value:"삭제"},
							]},
						{type: "block", list: [
								{type: "upload", label:"파일업로드" ,name: "file_upload",width: 384 ,inputWidth: 330 ,url: "/rcp/courtMgmt/fileUpLoad.do" ,autoStart: true, offsetLeft:20, userdata: { limitCount : 1, limitsize: 1000, delUrl:"/rcp/courtMgmt/deleteFile2.json"} }
							]}
					]}
        		],
            buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label : "닫기" }
                }
            },
            onButtonClick : function(btnId) {
                var form = this;
                var rowId = PAGE.GRID30.getSelectedRowId();
                var uploader = PAGE.FORM32.getUploader("file_upload");

                switch (btnId) {
                    case 'btnSave' :
						mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/getFileUploadChk.json", form, function(result) {
							if(result.result.chkFileUpload == "Y") {
								mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/updateRcivFile.json", form, function(result) {
		                        	mvno.ui.closeWindowById(form, true);  
		                        	mvno.ui.notify("NCMN0015");
		                           
		                        	PAGE.GRID30.refresh();
		                        	uploader.reset();
		                       	});
							} else {
								mvno.ui.alert("파일을 첨부해주세요.");
							}
						});
                        break;
                        
                    case 'btnClose' :
                        mvno.ui.closeWindowById(form, true);
                        break;
                        
                    case "fileDown1" :
                    	mvno.cmn.download('/rcp/courtMgmt/downFileDtl.json?fileId=' + PAGE.FORM32.getItemValue("crRcivSeq"));
                    	break;
                           
                    case "fileDel1" :
   						mvno.ui.confirm("CCMN0008", function(){
                           	mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/deleteFileDtl.json",  {fileId:PAGE.FORM32.getItemValue("crRcivSeq")} , function(resultData) {
   	                        	mvno.ui.notify("NCMN0014");
   	                           	PAGE.FORM32.setItemValue("fileId1", "");
   	                          	PAGE.FORM32.setItemValue("fileName1", "");
   	                       		PAGE.FORM32.disableItem("fileDown1");
   	                       		PAGE.FORM32.disableItem("fileDel1");
   	                       		PAGE.FORM32.showItem("file_upload");
   	                       		
	   	                       	PAGE.FORM32.setUserData("file_upload","limitCount",1);
	                            PAGE.FORM32.setUserData("file_upload","limitsize",1000);
	                            PAGE.FORM32.setUserData("file_upload","delUrl","/rcp/courtMgmt/deleteFile2.json");
	                             
   	                       		PAGE.GRID30.refresh();
   	                       		uploader.reset();
                           	});
   						});
                           break;
                }
            }
        }
        
        , FORM40 : {
        	title : "계좌정보 및 입출금 정보",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
						{type:"block", list:[
								{type:"input", label:"가상계좌", name:"acctId", labelWidth:53, width:200, offsetLeft:15, maxLength:35, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"입금횟수", name:"inoutCnt", labelWidth:53, width:60, offsetLeft:30, maxLength:5, validate:"ValidInteger", readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"label", label:"회", name:"order", width:30, labelWidth:20, offsetLeft:3, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"입금액합계", name:"inoutTotal", width:120, offsetLeft:30, validate:"ValidInteger", maxLength:11, readonly:true, disabled:true, numberFormat:"000,000,000,000"},
								{type:"newcolumn"},
								{type:"label", label:"원", name:"price", width:30, labelWidth:20, offsetLeft:3, disabled:true},
								{type:'hidden', label:'가상계좌처리유형', name:'opCode', labelWidth:10, offsetLeft:3},
								{type:'hidden', label:'가상계좌구분', name:'gubun', labelWidth:10, offsetLeft:3},
								{type:'hidden', label:'가상계좌요청키', name:'orderKey', labelWidth:10, offsetLeft:3},
								{type:'hidden', label:'가상계좌은행코드', name:'vacBankCd', labelWidth:10, offsetLeft:3},
								{type:'hidden', label:'가상계좌번호', name:'vacId', labelWidth:10, offsetLeft:3},
								{type:'hidden', label:'입금가능금액', name:'payAmt', labelWidth:10, offsetLeft:3},
								{type:'hidden', label:'법정관리고객시퀀스', name:'crSeq', labelWidth:10, offsetLeft:3}
						]}
            ]
        }
        
        , GRID40 : {
            css : {
                height : "170px"
            }
            , title : "" // 12
            , header : "회차,입금자명,입출금일자,입금액(원),출금액(원),기타사항,등록자,등록일시,수정자,수정일시,입출금일자DATE,입출금시퀀스"
            , columnIds : "inoutOrder,inoutName,inoutDt,inPric,outPric,etcMemo,regstId,regstDttm,rvisnId,rvisnDttm,inoutDate,crInoutSeq"
            , widths : "50,100,100,90,90,250,65,180,65,180,10,10"
            , colAlign : "center,center,center,right,right,left,center,center,center,center,center,center"
            , colTypes : "ron,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ron"
            , colSorting : "int,str,str,int,int,str,str,str,str,str,str,int"
            //, multiCheckbox : true
            , hiddenColumns : [10, 11]
            , paging : false
            , buttons : {
                left : {
                    btnOpen : { label : "가상계좌발급"},
                    btnClose : { label : "가상계좌폐기"},
                    btnPrint : { label : "계좌번호신고서 출력"}
                },   	
                right : {
                	btnDel : { label : "삭제"},
                    btnRgst : { label : "등록"}
                }
            },

            onRowDblClicked : function(rowId, celInd) {
                this.callEvent('onButtonClick', ['btnDtl']);
            },

            onButtonClick : function(btnId) {
                var grid = this;
                var rowId = grid.getSelectedRowId();

                switch (btnId) {
                	case 'btnOpen' :
                    	if(PAGE.FORM10.getItemValue("crSeq") == null || PAGE.FORM10.getItemValue("crSeq") == "") {
                            mvno.ui.alert("먼저 고객 등록을 완료해주세요.");
                            return;
                    	}
                    	
                		PAGE.FORM40.setItemValue("orderKey", PAGE.FORM10.getItemValue("crSeq"));
                		PAGE.FORM40.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                		PAGE.FORM40.setItemValue("opCode", "REG");
                		
	                    mvno.cmn.ajax4confirm("가상계좌를 발급하시겠습니까?", ROOT + "/rcp/courtMgmt/manageVac.json", PAGE.FORM40, function(resultdata) {
	                        mvno.ui.alert("정상적으로 발급되었습니다.");
	                		
	                		PAGE.FORM40.setItemValue("acctId", resultdata.result.data.vacBankNm + " " + resultdata.result.data.vacId);

	            			PAGE.FORM40.enableItem("acctId");
	            			PAGE.FORM40.enableItem("inoutCnt");
	            			PAGE.FORM40.enableItem("inoutTotal");
	            			PAGE.FORM40.enableItem("order");
	            			PAGE.FORM40.enableItem("price");
	            			
	            	    	mvno.ui.disableButton("GRID40", "btnOpen");
	            	    	mvno.ui.enableButton("GRID40", "btnClose");
	            	    	mvno.ui.enableButton("GRID40", "btnPrint");
	                    });                		
                		
                		break;
                	
                	case 'btnClose' :
                		PAGE.FORM40.setItemValue("orderKey", PAGE.FORM10.getItemValue("crSeq"));
                		PAGE.FORM40.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                		PAGE.FORM40.setItemValue("opCode", "CAN");
                		
	                    mvno.cmn.ajax4confirm("가상계좌를 폐기하시겠습니까?", ROOT + "/rcp/courtMgmt/manageVac.json", PAGE.FORM40, function(resultdata) {
	                        mvno.ui.alert("정상적으로 폐기되었습니다.");
	
	            			PAGE.FORM40.disableItem("acctId");
	            			PAGE.FORM40.disableItem("inoutCnt");
	            			PAGE.FORM40.disableItem("inoutTotal");
	            			PAGE.FORM40.disableItem("order");
	            			PAGE.FORM40.disableItem("price");
	                        
	                		PAGE.FORM40.setItemValue("acctId", "");
	                		
	            	    	mvno.ui.disableButton("GRID40", "btnClose");
	            	    	mvno.ui.disableButton("GRID40", "btnPrint");
	            	    	mvno.ui.enableButton("GRID40", "btnOpen");
	                        
	                        grid.refresh();
	                    });            
    					
                		break;   
                		
                	case 'btnPrint' :
	                	mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/chkMaskingAcct.json", PAGE.FORM40, function(result) {
	                		if(PAGE.GRID20.getRowsNum() < 1){
	                			mvno.ui.alert("먼저 채권을 등록해주세요.");
		                        return;
	                		}
	                		
		                	if(maskingYn != "Y" && maskingYn != "1") {
		                		mvno.ui.alert("권한이 없어 계좌번호 신고서를 출력할 수 없습니다.");
		                		return;
		                	}

	                		
	                		if(PAGE.FORM40.getItemValue("acctId") == null || PAGE.FORM40.getItemValue("acctId") == ""){
	                			mvno.ui.alert("발급된 가상계좌가 없습니다.");
		                        return;
	                		}
	                		
	                		for(var i = 1; i <= PAGE.GRID20.getRowsNum(); i++) {
	                			if(PAGE.GRID20.getRowAttribute(i,"bondNum") == null || PAGE.GRID20.getRowAttribute(i,"bondNum") == "") {
	                				mvno.ui.alert("채권번호가 없는 데이터가 있습니다.");
	                				return;
	                			}
	                		}
	                		
	                		var today = new Date();
	                		var sYear = today.getFullYear();
	                	    var sMonth = today.getMonth() + 1;
	                	    var sDate = today.getDate();
	                	    
	                	    sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
	                	    sDate  = sDate > 9 ? sDate : "0" + sDate;
	                		
	                	    var dclrDate = sYear + sMonth + sDate;
	                	                    	    
							var msg = "계좌번호 신고서를 출력하시겠습니까?";
							mvno.ui.confirm(msg, function() {
								mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/updateDclrDate.json", {crSeq:PAGE.FORM10.getItemValue("crSeq"), dclrDate:dclrDate}, function(resultdata) {
									var rowData = resultdata.result;
				                    
									var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/rcp/crAcctReport.jrf" + "&arg=";
									param = encodeURI(param);

									var arg = "crSeq#" + PAGE.FORM10.getItemValue("crSeq") + "#";
									arg += "dclrYear#" + rowData.dclrYear + "#";
									arg += "dclrMonth#" + rowData.dclrMonth + "#";
									arg += "dclrDay#" + rowData.dclrDay + "#";
									
									arg = encodeURIComponent(arg);
									
									param = param + arg;
									
									mvno.ui.popupWindow("/cmn/report/report.do"+param, "계좌번호 신고서", 900, 700, {
										onClose: function(win) {
											win.closeForce();
										}
									});
								});
							});
	                	});
                		
                		break;                	              	
                	
	                case 'btnDel' :
	                    if(rowId == null) {
	                        mvno.ui.alert("삭제하려는 목록을 선택하세요.");
	                        return;
	                    }
	
	                    mvno.cmn.ajax4confirm("해당 목록을 삭제 하시겠습니까?", ROOT + "/rcp/courtMgmt/deleteCrInout.json", {crInoutSeq:grid.getRowAttribute(rowId, "crInoutSeq"),crSeq:PAGE.FORM10.getItemValue("crSeq")}, function(resultdata) {
	                        mvno.ui.alert("삭제되었습니다.");
	                        
	                		var rowData = resultdata.result.data;
	                		
	                		PAGE.FORM40.setItemValue("inoutCnt", rowData.inoutCnt);
	                		PAGE.FORM40.setItemValue("inoutTotal", rowData.inoutTotal);
	
	                        grid.refresh();
	                    });
	
	                    break;
                    
                    case 'btnRgst' :
                    	if(PAGE.FORM10.getItemValue("crSeq") == null || PAGE.FORM10.getItemValue("crSeq") == "") {
                            mvno.ui.alert("먼저 고객 등록을 완료해주세요.");
                            return;
                    	}
                    	
                        mvno.ui.createForm("FORM41");
                    	PAGE.FORM41.clear();
                    	
                        PAGE.FORM41.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM41.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM41.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));

                        mvno.ui.hideButton("FORM41", "btnModi");
                        mvno.ui.showButton("FORM41", "btnSave");

                        PAGE.FORM41.enableItem("inoutOrder");
                        PAGE.FORM41.enableItem("inoutDttm");
                        PAGE.FORM41.enableItem("inoutName");
                        PAGE.FORM41.enableItem("etcMemo");
                        PAGE.FORM41.enableItem("inoutPric");
                        PAGE.FORM41.enableItem("orderA");
                        PAGE.FORM41.enableItem("priceA");

                        mvno.ui.popupWindowById("POPUP41", "입출금정보 등록", 549, 272, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                    	
                    	break;
                    	
                    case 'btnDtl' :
                        mvno.ui.createForm("FORM41");
                    	PAGE.FORM41.clear();
                    	
                        PAGE.FORM41.setItemValue("cstmrName", PAGE.FORM10.getItemValue("cstmrName"));
                        PAGE.FORM41.setItemValue("cstmrRrn", PAGE.FORM10.getItemValue("cstmrRrn"));
                        PAGE.FORM41.setItemValue("crSeq", PAGE.FORM10.getItemValue("crSeq"));
                        PAGE.FORM41.setItemValue("crInoutSeq", grid.getRowAttribute(rowId, "crInoutSeq"));
                        
                        PAGE.FORM41.setItemValue("inoutOrder", grid.getRowAttribute(rowId, "inoutOrder"));
                        PAGE.FORM41.setItemValue("inoutDttm", grid.getRowAttribute(rowId, "inoutDate"));
                        PAGE.FORM41.setItemValue("inoutName", grid.getRowAttribute(rowId, "inoutName"));
                        PAGE.FORM41.setItemValue("etcMemo", grid.getRowAttribute(rowId, "etcMemo"));
                        
                        if(grid.getRowAttribute(rowId, "inPric") != null && grid.getRowAttribute(rowId, "inPric") != "")
                        	PAGE.FORM41.setItemValue("inoutPric", grid.getRowAttribute(rowId, "inPric"));
                        else
                        	PAGE.FORM41.setItemValue("inoutPric", grid.getRowAttribute(rowId, "outPric"));
                        	
                        mvno.ui.hideButton("FORM41", "btnSave");
                        mvno.ui.showButton("FORM41", "btnModi");
                        
                        if(maskingYn != "Y" && maskingYn != "1") {
                            PAGE.FORM41.disableItem("inoutOrder");
                            PAGE.FORM41.disableItem("inoutDttm");
                            PAGE.FORM41.disableItem("inoutName");
                            PAGE.FORM41.disableItem("etcMemo");
                            PAGE.FORM41.disableItem("inoutPric");
                            PAGE.FORM41.disableItem("orderA");
                            PAGE.FORM41.disableItem("priceA");
                    		mvno.ui.disableButton("FORM41", "btnModi");
                        }
                        
                        mvno.ui.popupWindowById("POPUP41", "입출금정보 수정", 549, 272, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                    	
                    	break;
                }
            }
        }
        
        , FORM41 : {
            title : "",
            items : [
                {type:'settings', position:'label-left', inputWidth:'auto'},
                {type:"block", blockOffset:0, list: [
                        {type:'input', label:'이름', name:'cstmrName', width:100, labelWidth:65, offsetLeft:23, maxLength:20, userdata:{align:"left"}, readonly:true, disabled:true},
                        {type:"newcolumn"},
                        {type:'input', label:'식별번호', name:'cstmrRrn', width:120, labelWidth:65, offsetLeft:40, maxLength:13, userdata:{align:"left"}, readonly:true, disabled:true},
                        {type:'hidden', label:'법정관리고객시퀀스', name:'crSeq', labelWidth:10, offsetLeft:3},
                        {type:'hidden', label:'입출금시퀀스', name:'crInoutSeq', labelWidth:10, offsetLeft:3}                        
                    ]},
                {type: "fieldset", label: "입출금 내역", inputWidth:482, list:[
	                    {type:"block", blockOffset:0, list: [
				                    {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"inoutDttm", label:"입출금일", calendarPosition:"bottom", readonly:true, width:100, labelWidth:65, offsetLeft:10},
				                	{type:"newcolumn"},
				                    {type:'input', label:'회차', name:'inoutOrder', width:50, labelWidth:65, offsetLeft:40, validate:"ValidInteger", maxLength:5},
				                    {type:"newcolumn"},
									{type:"label", label:"회", name:'orderA', width:30, labelWidth:20, offsetLeft:3}
				            ]},
                    	{type:"block", blockOffset:0, list: [
				                    {type:'input', label:'입출금액', name:'inoutPric', width:100, labelWidth:65, offsetLeft:10, validate:"ValidInteger", maxLength:9, numberFormat:"000,000,000,000"},
				                    {type:"newcolumn"},
									{type:"label", label:"원", name:'priceA', width:30, labelWidth:20, offsetLeft:3},
				                    {type:"newcolumn"},
				                    {type:'input', label:'입금자명', name:'inoutName', width:100, labelWidth:65, offsetLeft:17, maxLength:6}
					        ]},
	                    {type:"block", blockOffset:0, list: [
				                    {type:'input', label:'기타사항', name:'etcMemo', width:310, labelWidth:65, offsetLeft:10, maxLength:660}
				        	]}
                	]}
        		],
            buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnModi : { label : "수정" },
                    btnClose : { label : "닫기" }
                }
            },
            onButtonClick : function(btnId) {
                var form = this;
                var rowId = PAGE.GRID40.getSelectedRowId();

                switch (btnId) {
	                case 'btnSave' :
	                	if(form.getItemValue("inoutDttm") == null || form.getItemValue("inoutDttm") == "") {
	                        mvno.ui.alert("입출금일을 입력해주세요.");
	                        return;
	                	}
	                	if(form.getItemValue("inoutOrder") == null || form.getItemValue("inoutOrder") == "") {
	                        mvno.ui.alert("입출금액을 입력해주세요.");
	                        return;
	                	}	  
	                	if(form.getItemValue("inoutPric") == null || form.getItemValue("inoutPric") == "") {
	                        mvno.ui.alert("입출금액을 입력해주세요.");
	                        return;
	                	}
	                	if(form.getItemValue("inoutPric") == 0) {
	                        mvno.ui.alert("입력할 수 없는 입출금액입니다.");
	                        return;
	                	}
	                	if(form.getItemValue("inoutName") == null || form.getItemValue("inoutName") == "") {
	                        mvno.ui.alert("입금자명을 입력해주세요.");
	                        return;
	                	}
	                	
	                    mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/insertCrInout.json", form, function(resultdata) {
	                    	mvno.ui.closeWindowById("POPUP41", true);
	                        mvno.ui.alert("등록되었습니다.");
	                        
	                		var rowData = resultdata.result.data;
	                		
	                		PAGE.FORM40.setItemValue("inoutCnt", rowData.inoutCnt);
	                		PAGE.FORM40.setItemValue("inoutTotal", rowData.inoutTotal);
	                		
	                        PAGE.GRID40.refresh();                          
	                    });
	                    
	                    break;
	                    
	                case 'btnModi' :
	                	if(form.getItemValue("inoutDttm") == null || form.getItemValue("inoutDttm") == "") {
	                        mvno.ui.alert("입출금일을 입력해주세요.");
	                        return;
	                	}
	                	if(form.getItemValue("inoutOrder") == null || form.getItemValue("inoutOrder") == "") {
	                        mvno.ui.alert("입출금액을 입력해주세요.");
	                        return;
	                	}	  
	                	if(form.getItemValue("inoutPric") == null || form.getItemValue("inoutPric") == "") {
	                        mvno.ui.alert("입출금액을 입력해주세요.");
	                        return;
	                	}
	                	if(form.getItemValue("inoutPric") == 0) {
	                        mvno.ui.alert("입력할 수 없는 입출금액입니다.");
	                        return;
	                	}
	                	if(form.getItemValue("inoutName") == null || form.getItemValue("inoutName") == "") {
	                        mvno.ui.alert("입금자명을 입력해주세요.");
	                        return;
	                	}
	                	
	                    mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/modifyCrInout.json", form, function(resultdata) {
	                    	mvno.ui.closeWindowById("POPUP41", true);
	                        mvno.ui.alert("수정되었습니다.");
	                        
	                		var rowData = resultdata.result.data;
	                		
	                		PAGE.FORM40.setItemValue("inoutCnt", rowData.inoutCnt);
	                		PAGE.FORM40.setItemValue("inoutTotal", rowData.inoutTotal);
	                		
	                        PAGE.GRID40.refresh();
	                    }); 
	                	
	                	break;
	
	                case 'btnClose' :
	                    mvno.ui.closeWindowById(form, true);
	                    break;
                }
            },
            onKeyUp : function (inp, ev, name, value){
                var form = this;                

                form.setItemValue("inoutOrder", form.getItemValue("inoutOrder").replace(/[^0-9]/g,""));
            }
        }
    };

    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        onOpen : function() {
            mvno.ui.createForm("FORM10");
            mvno.ui.createGrid("GRID20");
            mvno.ui.createGrid("GRID30");
            mvno.ui.createForm("FORM40");
            mvno.ui.createGrid("GRID40");
            
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0031", orderBy:"etc1"}, PAGE.FORM10, "crTpCd");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0032", orderBy:"etc1"}, PAGE.FORM10, "endYn");
			
			PAGE.FORM10.setItemValue("crSeq", "${param.requestKey}");
			
			if(PAGE.FORM10.getItemValue("crSeq") != null && PAGE.FORM10.getItemValue("crSeq") != "") {
				form10ModiInit();
			} else {
				form10RgstInit();
			}
			
			grid20Init();
			grid30Init();
			form40Init();
			grid40Init();
        }
    };
    
    function form10ModiInit() {
    	var form = PAGE.FORM10;
    	var rowData = "";
    	
		mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/getCrCstmr.json", {crSeq:form.getItemValue("crSeq")}, function(resultData) {
			rowData = resultData.result.data;
			
			form.setItemValue("cstmrName", rowData.cstmrName);
			form.setItemValue("cstmrRrn", rowData.cstmrRrn);
			form.setItemValue("crTpCd", rowData.crTpCd);
			form.setItemValue("crComp", rowData.crComp);
			form.setItemValue("noJudg", rowData.noJudg);
			form.setItemValue("judgPan", rowData.judgPan);
			form.setItemValue("endYn", rowData.endYn);
			form.setItemValue("firstDt", rowData.firstDt);
			form.setItemValue("lastDt", rowData.lastDt);
			form.setItemValue("rbStrtDt", rowData.rbStrtDt);
			form.setItemValue("rbEsti", rowData.rbEsti);
			form.setItemValue("rbRate", rowData.rbRate);
			form.setItemValue("rbPrid", rowData.rbPrid);
			form.setItemValue("acRegDt", rowData.acRegDt);
			form.setItemValue("tpJinh", rowData.tpJinh);
			form.setItemValue("totalPric", rowData.totalPric);
			form.setItemValue("lastInoutDt", rowData.lastInoutDt);
			
			if(maskingYn == "Y" || maskingYn == "1") {
				form.enableItem("cstmrName");
				form.enableItem("cstmrRrn");
				form.enableItem("crTpCd");
				form.enableItem("crComp");
				form.enableItem("noJudg");
				form.enableItem("judgPan");
				form.enableItem("endYn");
				form.enableItem("rbStrtDt");
				form.enableItem("rbEsti");
				form.enableItem("priceB");
				form.enableItem("rbRate");
				form.enableItem("rbPrid");
				form.enableItem("acRegDt");
				form.enableItem("btnModi");
			}
			
			form.disableItem("searchRrn");
			form.disableItem("btnSearch");
			
			form.hideItem("btnRgst");
			form.showItem("btnModi");
		});
    }
    
    function form10RgstInit() {
    	var form = PAGE.FORM10;
    	
    	form.clear();
			
		form.disableItem("cstmrName");
		form.disableItem("cstmrRrn");
		form.disableItem("crTpCd");
		form.disableItem("crComp");
		form.disableItem("noJudg");
		form.disableItem("judgPan");
		form.disableItem("endYn");
		form.disableItem("firstDt");
		form.disableItem("lastDt");
		form.disableItem("rbStrtDt");
		form.disableItem("rbEsti");
		form.disableItem("priceB");
		form.disableItem("rbRate");
		form.disableItem("rbPrid");
		form.disableItem("acRegDt");
		form.disableItem("btnRgst");
		form.enableItem("searchRrn");
		form.enableItem("btnSearch");
		
		form.hideItem("btnModi");
		form.showItem("btnRgst");
		form.disableItem("btnRgst");
    }
    
    function grid20Init() {
    	var grid = PAGE.GRID20;
    	
    	grid.clearAll();
    	grid.list(ROOT + "/rcp/courtMgmt/getCrBondList.json", PAGE.FORM10);
    	
    }
    
    function grid30Init() {
    	var grid = PAGE.GRID30;
    	
    	grid.clearAll();
    	grid.list(ROOT + "/rcp/courtMgmt/getCrRcivList.json", PAGE.FORM10);
        
        mvno.ui.disableButton("GRID30", "btnFile");
    }
    
    function form40Init() {
    	var form = PAGE.FORM40;
    	var rowData = "";
    	form.clear();
    	
    	mvno.cmn.ajax(ROOT + "/rcp/courtMgmt/getCrInoutTotal.json", PAGE.FORM10, function(resultdata) {
    		rowData = resultdata.result.data;
    		
	    	form.setItemValue("inoutCnt", rowData.inoutCnt);
	    	form.setItemValue("inoutTotal", rowData.inoutTotal);
   		
    		if(rowData.vacId == " " || rowData.vacId == null || rowData.vacId == "") {
    			form.setItemValue("acctId", "");
    		} else {
    			form.setItemValue("acctId", rowData.vacBankNm + " " + rowData.vacId);
    		}  
   		
    		if(form.getItemValue("acctId") != null && form.getItemValue("acctId") != "") {
    			form.enableItem("acctId");
    			form.enableItem("inoutCnt");
    			form.enableItem("inoutTotal");
    			form.enableItem("order");
    			form.enableItem("price");
        		mvno.ui.disableButton("GRID40", "btnOpen");
        		mvno.ui.enableButton("GRID40", "btnClose");
        		mvno.ui.enableButton("GRID40", "btnPrint");
    		} else {
    			form.disableItem("acctId");
    			form.disableItem("inoutCnt");
    			form.disableItem("inoutTotal");    			
    			form.disableItem("order");    			
    			form.disableItem("price");
        		mvno.ui.disableButton("GRID40", "btnClose");
        		mvno.ui.disableButton("GRID40", "btnPrint");
        		mvno.ui.enableButton("GRID40", "btnOpen");
    		}
    	});   	
    	
		form.setItemValue("gubun", "COURT_RSV");
		form.setItemValue("vacBankCd", "11");
		form.setItemValue("payAmt", "1000000000");
    }
    
    function grid40Init() {
    	var grid = PAGE.GRID40;
    	grid.clearAll();
    	
    	grid.list(ROOT + "/rcp/courtMgmt/getCrInoutList.json", PAGE.FORM10);
    }
</script>
