<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
  	var maskingYn = '${maskingYn}';

    var DHX = {
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
                {type:"block", list:[
                        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"최초생성일", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10},
                        {type:"newcolumn"},
                        {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
                        {type:'newcolumn'},
                        {type:"select", label:"법정관리유형", name:"crTpCd", width:100, labelWidth:76, offsetLeft:25},
                        {type:"newcolumn"},
                        {type:"select", label:"진행상태", name:"tpJinh", width:110, labelWidth:53, offsetLeft:40, disabled:true, options:[{value:"", text:"- 전체 -"}]}
                    ]},
                {type:"block", list:[
	                    {type:"select", label:"검색구분", name:"searchCd", width:70, offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"이름"}, {value:"2", text:"식별번호"}, {value:"3", text:"판결번호"}]},
	                    {type:"newcolumn"},
	                    {type:"input", name:"searchVal", width:145, maxLength:30, disabled:true},
	                    {type:"newcolumn"},
	                    {type:"select", label:"종결여부", name:"endYn", width:100, labelWidth:76, offsetLeft:24},
                        {type:"hidden", label:"식별번호검색값", name:"searchRrn"}
                    ]},            	
                {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
            ],

            onButtonClick : function(btnId) {
                var form = this;

                switch (btnId) {
                    case "btnSearch":
                    	if(form.getItemValue("searchCd") == "2") {
                    		form.setItemValue("searchRrn", form.getItemValue("searchVal"));
                    	}
                    	
                        PAGE.GRID1.list(ROOT + "/rcp/courtMgmt/getCrCstmrList.json", form);

                        break;
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

                        form.setItemValue("searchVal", "");

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
                
                if(name == "crTpCd") {
                    if(value == "1") {
                        form.enableItem("tpJinh");
                        
            			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0033", orderBy:"etc1"}, PAGE.FORM1, "tpJinh");
                    } else if(value == "2") {
                        form.enableItem("tpJinh");
                        
            			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0034", orderBy:"etc1"}, PAGE.FORM1, "tpJinh");
                    } else if(value == "3") {
                        form.enableItem("tpJinh");
                        
            			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0035", orderBy:"etc1"}, PAGE.FORM1, "tpJinh");
                    } else {
                        form.disableItem("tpJinh");
                        
                        form.setItemValue("tpJinh", "");
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
            },
            onValidateMore : function (data){

                if(data.strtDt > data.endDt){
                    this.pushError("strtDt","최초생성일",["ICMN0004"]);
                }

                if( data.searchCd != "" && data.searchVal.trim() == ""){
                    this.pushError("searchVal", "검색어", "검색어를 입력하세요");
                    PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
                }
            }
        }

        , GRID1 : {
            css : {
                height : "540px"
            }
            , title : "조회결과" // 24
            , header : "최초생성일,최종변경일,최종입금일,이름,식별번호,법정관리유형,관할법원,판결번호,재판부,가상계좌은행,가상계좌,계좌신고일,미납총액(원),변제예정액(원),입금총액(원),진행상태,종결여부,변제개시일,변제율,변제기간,최초생성자,수정자,수정일시,시퀀스"
            , columnIds : "firstDt,lastDt,lastInoutDt,cstmrName,cstmrRrn,crTp,crComp,noJudg,judgPan,vacBankCd,vacId,acRegDt,totalPric,rbEsti,inoutTotal,tpJinh,endYn,rbStrtDt,rbRate,rbPrid,regstId,rvisnId,rvisnDttm,crSeq"
            , widths : "80,80,80,60,100,90,90,120,90,90,120,80,110,110,110,100,80,80,70,70,80,60,120,10"
            , colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,right,right,right,center,center,center,center,center,center,center,center,center"
            , colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ron"
            , colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,int,int,int,str,str,str,str,str,str,str,str,int"
            //, multiCheckbox : true
            , hiddenColumns : [23]
            , paging : true
            , showPagingAreaOnInit : true
            , pageSize : 20
            , buttons : {
                hright : {
                    btnDnExcel : { label : "엑셀다운로드"}
                },
                right : {
                    btnRgst : { label : "등록"},
                }
            },

            onRowDblClicked : function(rowId, celInd) {
                this.callEvent('onButtonClick', ['btnDtl']);
            },

            onButtonClick : function(btnId) {
                var grid = this;
                var rowId = grid.getSelectedRowId();

                switch (btnId) {
                    case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
                        if(grid.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                        } else {
                            var searchData =  PAGE.FORM1.getFormData(true);
                            mvno.cmn.download("/rcp/courtMgmt/getCrCstmrListByExcel.json?menuId=MSP1008002", true, {postData:searchData});
                        }
                        break;

                    case 'btnRgst' :     					
    					var url = "/rcp/courtMgmt/regstCrCstmr.do";
    					var menuId = "MSP1008003";
    					
    					var param = "?menuId=" + menuId + "&trgtMenuId=MSP1008002";
    					
    					var myTabbar = window.parent.mainTabbar;
    					
    					if (myTabbar.tabs(url)) {
    						myTabbar.tabs(url).setActive();
    					}else{
    						myTabbar.addTab(url, "법정관리고객 등록", null, null, true);
    					}
    					
    					myTabbar.tabs(url).attachURL(url + param);
    					
    					break;
                    	
                    case 'btnDtl' :                    	
    					if(grid.getSelectedRowData() == null){
    						mvno.ui.alert("선택된 데이터가 없습니다.");
    						return;
    					}
    					
    					var url = "/rcp/courtMgmt/regstCrCstmr.do";
    					var menuId = "MSP1008003"; //법정관리고객 등록
    					var trgtMenuId = "MSP1008002";
    					var tabTitle = "법정관리고객 등록";
    					
    					var param = "?menuId=" + menuId + "&requestKey=" + grid.getRowAttribute(rowId, "crSeq") + "&trgtMenuId=" + trgtMenuId;
    					
    					var myTabbar = window.parent.mainTabbar;
    					
    					if (myTabbar.tabs(url)) {
    						myTabbar.tabs(url).setActive();
    					}else{
    						myTabbar.addTab(url, tabTitle, null, null, true);
    					}
    					
    					myTabbar.tabs(url).attachURL(url + param);
    					
    					break;	//Row 더블클릭시 End
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
            
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0031", orderBy:"etc1"}, PAGE.FORM1, "crTpCd");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0032", orderBy:"etc1"}, PAGE.FORM1, "endYn");
        }
    };
</script>
