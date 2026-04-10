<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>

<div id="GROUP1" >   <!--style="display:none;" -->
    <div id="FORM2" class="section-box"></div>
    <div class="row">
        <div id="GRID2" class="c5"></div>
        <div id="GRID3" class="c5"></div>
    </div>
    <div id="FORM3"></div>
</div>

<script type="text/javascript">

    var DHX = {
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                , {type:"block", list:[
                        {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '등록기간', labelWidth:60, calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
                        , {type: 'newcolumn'}
                        , {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
                        , {type: 'newcolumn'}
                        , {type:"select", width:100, label:"검색구분",labelWidth:60,name:"pSearchGbn", offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계약번호"}, {value:"2", text:"고객명"}, {value:"3", text:"휴대폰번호"}, {value:"4", text:"IMEI"}]}
                        , {type:"newcolumn"}
                        , {type:"input", width:120, name:"pSearchName",disabled: true}
                        , {type: 'newcolumn'}
                        , {type:"select", width:100, label:"단말상태", labelWidth:60, name:"openYn", offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"Y", text:"개통가능"}, {value:"N", text:"개통불가"}]}
                    ]}
                // 조회버튼
                , {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
            ],
            onValidateMore : function(data) {
                if (data.searchStartDt > data.searchEndDt) {
                    this.pushError("searchStartDt", "등록기간", "시작일자가 종료일자보다 클 수 없습니다.");
                }
                if(!mvno.cmn.isEmpty(data.pSearchGbn) && mvno.cmn.isEmpty(data.pSearchName)) {
                    this.pushError(["pSearchGbn"],"검색구분","검색어를 입력하십시오.");
                }
            },
            onButtonClick : function(btnId) {
                var form = this;
                switch (btnId) {
                    case "btnSearch" :
                        PAGE.GRID1.list(ROOT + "/voc/abuse/getAbuseInfoList.json", form);
                        break;
                }

            },
            onChange : function(name, value){
                var form = this;
                switch (name){
                    case "pSearchGbn" :
                        form.setItemValue("pSearchName", "");
                        if(value != ""){
                            form.disableItem("searchStartDt");
                            form.disableItem("searchEndDt");
                            form.setItemValue("searchStartDt", "");
                            form.setItemValue("searchEndDt", "");
                            form.enableItem("pSearchName");
                        } else {
                            //조회기간 세팅
                            var endDate = new Date().format('yyyymmdd');
                            var time = new Date().getTime();
                            time = time - 1000 * 3600 * 24 * 7;
                            var startDate = new Date(time);
                            form.enableItem("searchStartDt");
                            form.enableItem("searchEndDt");
                            form.setItemValue("searchStartDt", startDate);
                            form.setItemValue("searchEndDt", endDate);
                            form.disableItem("pSearchName");
                        }
                        break;
                }
            },
        }

        ,GRID1 : {
            css : {
                height : "560px"
            },
            title : "조회 결과"
            ,header : "등록일자,계약번호,고객명,휴대폰번호,구매유형,업무구분,최초요금제코드,최초요금제,현재요금제코드,현재요금제,상태,해지사유,모집경로,대리점,개통일자,해지일자,유심접점,최초유심접점,본인인증방식,최초유심일련번호,단말상태,유심구분,최초ESIM여부,단말모델명,단말일련번호,최초단말모델명,최초단말일련번호,등록사유,등록일시,등록자,수정일시,수정자,등록사유코드,부정사용주장시퀀스,단말상태코드,상세사유"//36
            ,columnIds : "regstDt,contractNum,subLinkName,subscriberNo,reqBuyTypeNm,operTypeNm,fstRateCd,fstRateNm,lstRateCd,lstRateNm,subStatusNm,canRsn,onOffTypeNm,openAgntNm,lstComActvDate,canDate,usimOrgNm,fstUsimOrgNm,authType,fstUsimNo,openYnNm,usimType,fstEsimYn,lstModelNm,lstModelSrlNum,fstModelNm,fstModelSrlNum,reasonNm,regstDttm,regstNm,rvisnDttm,rvisnNm,reason,mstSeq,openYn,reasonDesc"//36
            ,widths : "100,100,100,100,100,100,100,200,100,200,100,100,100,180,100,100,100,100,100,180,100,100,100,100,200,100,200,100,180,100,180,100,0,0,0,0"//36
            ,colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"//36
            ,colTypes : "ro,ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"//36
            ,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"//36
            ,hiddenColumns : [32,33,34,35]
            ,paging : true
            ,pageSize:20
            ,showPagingAreaOnInit : true
            , buttons : {
                hright : {
                    btnExcel:{ label:"엑셀다운로드"}
                },
                right : {
                    btnReg : { label : "등록" },
                    btnMod : { label : "수정" }
                },
            }
            , checkSelectedButtons : ["btnMod"]
            , onRowDblClicked : function(rowId, celInd) {
                this.callEvent('onButtonClick', ['btnMod']);
            }
            ,onButtonClick : function(btnId) {
                switch(btnId){

                    case "btnExcel":
                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/voc/abuse/getAbuseInfoListExcelDown.json?menuId=VOC1003030", true, {postData:searchData});
                        break;

                    case "btnReg":
                        mvno.ui.createForm("FORM2");
                        mvno.ui.createGrid("GRID2");
                        mvno.ui.createGrid("GRID3");
                        mvno.ui.createForm("FORM3");

                        PAGE.FORM2.clear();
                        PAGE.GRID2.clearAll();
                        PAGE.GRID3.clearAll();

                        PAGE.FORM2.setFormData(true);
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0284"}, PAGE.FORM2, "reason");

                        PAGE.FORM2.enableItem("btnFind");
                        PAGE.FORM2.enableItem("searchContractNum");
                        mvno.ui.hideButton("GRID2" , 'btnDel');
                        mvno.ui.showButton("FORM3" , 'btnSave');
                        mvno.ui.hideButton("FORM3" , 'btnMod');

                        mvno.ui.popupWindowById("GROUP1", "부정사용주장 단말설정", 700, 600, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.isChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });
                        break;

                    case "btnMod" :

                        var rowData = PAGE.GRID1.getSelectedRowData();

                        if(rowData == null){
                            mvno.ui.notify("선택된 데이터가 없습니다.");
                            return;
                        }

                        mvno.ui.createForm("FORM2");
                        mvno.ui.createGrid("GRID2");
                        mvno.ui.createGrid("GRID3");
                        mvno.ui.createForm("FORM3");

                        PAGE.GRID2.clearAll();
                        PAGE.GRID3.clearAll();
                        PAGE.FORM2.clear();

                        // 콤보세팅
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0284"}, PAGE.FORM2, "reason");

                        PAGE.FORM2.setFormData(rowData);

                        PAGE.FORM2.setItemValue("searchContractNum", rowData.contractNum);  //계약번호 세팅
                        PAGE.FORM2.setItemValue("reason", rowData.reason);  //등록사유 세팅
                        PAGE.FORM2.setItemValue("reasonDesc", rowData.reasonDesc);  //등록사유 상세 세팅

                        PAGE.FORM2.disableItem("btnFind");

                        if(rowData.reason == "09"){
                            PAGE.FORM2.enableItem("reasonDesc");
                        }else{
                            PAGE.FORM2.disableItem("reasonDesc");
                        }
                        PAGE.FORM2.setItemValue("openYn", rowData.openYn);  //단말상태 세팅

                        PAGE.FORM2.disableItem("searchContractNum");
                        mvno.ui.showButton("GRID2" , 'btnDel');
                        mvno.ui.showButton("FORM3" , 'btnMod');
                        mvno.ui.hideButton("FORM3" , 'btnSave');

                        //imei 목록 세팅
                        PAGE.GRID2.list(ROOT + "/voc/abuse/getAbuseImeiList.json", {mstSeq:PAGE.FORM2.getItemValue("mstSeq")}, {timeout:180000, callback: function() {
                                for(var idx=0; idx < PAGE.GRID2.getRowsNum(); idx++) {
                                    PAGE.GRID2.cells(PAGE.GRID2.getRowId(idx), 0).setValue(idx+1);
                                }
                                PAGE.GRID2.setEditable(false);
                            }});

                        mvno.ui.popupWindowById("GROUP1", "부정사용주장 단말설정", 700, 600, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.isChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });
                        break;
                }
            }
        }

        , FORM2 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
                {type: "fieldset", label: "계약정보", name:"subInfo", inputWidth:600, list:[
                        {type:"block", list:[
                                {type:"input", name:"searchContractNum", label:"계약번호", width:120, offsetLeft:20},
                                {type:"newcolumn"},
                                {type:"button", name:"btnFind", value:"검색"}
                            ]},
                        {type:"hidden", name:"contractNum"},
                        {type:"block", list:[
                                {type:"input", name:"subLinkName", label:"고객명", width:100, offsetLeft:20, readonly:true, disabled:true},
                                {type:"newcolumn"},
                                {type:"input", name:"subStatusNm", label:"계약상태", width:100, offsetLeft:20, readonly:true, disabled:true},
                                {type:"newcolumn"},
                                {type:"calendar", name:"lstComActvDate", label:"개통일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y-%m-%d", width:100, offsetLeft:20, readonly:true, disabled:true}
                            ]},
                        {type : 'hidden', name: "mstSeq"},
                        {type:"block", list:[
                                {type:"input", name:"reqBuyTypeNm", label:"구매유형", width:100, offsetLeft:20, readonly:true, disabled:true},
                                {type:"newcolumn"},
                                {type:"input", name:"operTypeNm", label:"업무구분", width:100, offsetLeft:20, readonly:true, disabled:true},
                                {type:"newcolumn"},
                                {type:"input", name:"onOffTypeNm", label:"모집경로", width:100, offsetLeft:20, readonly:true, disabled:true}
                            ]}
                    ]},
                {type: "fieldset", label: "부정사용주장 단말", name:"reuseRip", inputWidth:600, list:[
                        {type:"block", list:[
                                {type:"select", name:"openYn", label:"단말상태",  width:120, offsetLeft:20, options:[ {value:"N", text:"개통불가"}, {value:"Y", text:"개통가능"}]}
                            ]},
                        {type:"block", list:[
                                {type:"select", name:"reason", label:"등록사유",  width:120, offsetLeft:20}
                            ]},
                        {type:"block", list:[
                                {type:"input", rows:3, name:"reasonDesc", label:"상세사유", width:400, offsetLeft:20, disabled:true}
                            ]},
                    ]}
            ],
            onButtonClick : function(btnId) {
                var form = this; // 혼란방지용으로 미리 설정 (권고)
                switch (btnId) {
                    case "btnFind" :
                        if(mvno.cmn.isEmpty(form.getItemValue("searchContractNum"))) {
                            mvno.ui.alert("검색어를 입력하세요.");
                            return;
                        }
                        var sdata = {contractNum : form.getItemValue("searchContractNum")};
                        mvno.cmn.ajax(ROOT + "/voc/abuse/getContractInfo.json", sdata, function(resultData) {
                            var data = resultData.result.data.rows[0];
                            if(data != null){
                                form.setItemValue("contractNum",	data.contractNum);
                                form.setItemValue("subLinkName",	data.subLinkName);
                                form.setItemValue("subStatusNm",			data.subStatusNm);
                                form.setItemValue("lstComActvDate",			data.lstComActvDate);
                                form.setItemValue("reqBuyTypeNm",		data.reqBuyTypeNm);
                                form.setItemValue("operTypeNm",			data.operTypeNm);
                                form.setItemValue("onOffTypeNm",			data.onOffTypeNm);
                            } else {
                                mvno.ui.alert("계약정보가 존재하지 않습니다.");
                                return;
                            }
                        });
                        break;
                }
            },
            onChange: function (name, value) {
                var form = this;

                switch(name) {
                    case "reason":
                        if(value == "09") {
                            form.enableItem("reasonDesc");
                        } else {
                            form.disableItem("reasonDesc");
                            form.setItemValue("reasonDesc", "");
                        }
                        break;
                }
            }
        },

        GRID2 : {
            css : {
                height : "100px"
                ,width : "300px"
            }
            ,title : "단말정보"
            ,header : "번호,IMEI"
            ,columnIds : "idx,imei"
            ,colAlign : "center,left"
            ,colTypes : "ro,ed"
            ,colSorting : "str,int"
            ,widths : "50,230"
            ,paging: false
            ,buttons : {
                left : {
                    btnDel : { label : "삭제" }
                }
            }
            ,onButtonClick : function(btnId) {

                var grid = this;

                switch(btnId){

                    case "btnDel" :
                        if(PAGE.GRID2.getSelectedRowId() == null){
                            mvno.ui.alert("선택된 데이터가 없습니다.");
                            return;
                        }

                        grid.deleteRow(grid.getSelectedRowId());
                        for(var idx=0; idx < PAGE.GRID2.getRowsNum(); idx++) {
                            PAGE.GRID2.cells(PAGE.GRID2.getRowId(idx), 0).setValue(idx+1);
                        }
                        mvno.ui.alert("삭제되었습니다.");
                        break;
                }
            }
        },

        GRID3 : {
            css : {
                height : "100px"
                ,width : "300px"
            }
            ,title : "신규 입력"
            ,header : "번호,IMEI(15자리)"
            ,columnIds : "idx,imei"
            ,colAlign : "center,left"
            ,colTypes : "ro,ed"
            ,colSorting : "str,int"
            ,widths : "50,230"
            ,paging: false
            ,buttons : {
                left : {
                    btnAdd : { label : "추가" },
                    btnDel : { label : "삭제" }
                }
            }
            ,onEditCell : function(state, id, idx, value, oldValue) {
                if (state == 2 && value) {
                    if (!isInteger(value) || !isLength15(value)) {
                        PAGE.GRID3.cells(id, idx).setValue(value.replace(/[^0-9]/g, '').substring(0, 15));
                    }

                    if (!isInteger(value)) {
                        mvno.ui.alert("imei는 숫자만 입력가능합니다.");
                        return true;
                    }
                    if (!isLength15(value)) {
                        mvno.ui.alert("imei 15자리를 입력해주세요.");
                        return true;
                    }
                }
                return true;
            }
            ,onButtonClick : function(btnId) {

                var grid = this;

                switch(btnId){
                    case "btnAdd" :
                        if(PAGE.GRID2.getRowsNum() + PAGE.GRID3.getRowsNum() == 10){
                            mvno.ui.alert("최대 10건만 등록이 가능합니다.");
                            return;
                        }

                        grid.appendRow();
                        for(var idx=0; idx < PAGE.GRID3.getRowsNum(); idx++) {
                            PAGE.GRID3.cells(PAGE.GRID3.getRowId(idx), 0).setValue(idx+1);
                        }
                        break;

                    case "btnDel" :
                        if(PAGE.GRID3.getSelectedRowId() == null){
                            mvno.ui.alert("선택된 데이터가 없습니다.");
                            return;
                        }

                        grid.deleteRow(grid.getSelectedRowId());
                        for(var idx=0; idx < PAGE.GRID3.getRowsNum(); idx++) {
                            PAGE.GRID3.cells(PAGE.GRID3.getRowId(idx), 0).setValue(idx+1);
                        }
                        mvno.ui.alert("삭제되었습니다.");
                        break;
                }
            }
        },

        FORM3  : {
            title : "",
            buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnMod : { label : "수정" },
                    btnClose : { label : "닫기" }
                }
            },
            onButtonClick : function(btnId) {

                var form = this;

                switch(btnId){
                    case "btnSave" :

                        if (!validateAbuseInfo()) {
                            return;
                        }

                        if (!validateAbuseImeiList()) {
                            return;
                        }

                        var arrObj = getImeiList(PAGE.GRID3);

                        var data = {
                            contractNum : PAGE.FORM2.getItemValue("contractNum"),
                            newImeiList : arrObj,
                            openYn: PAGE.FORM2.getItemValue("openYn"),
                            reason: PAGE.FORM2.getItemValue("reason"),
                            reasonDesc: PAGE.FORM2.getItemValue("reasonDesc")
                        };

                        var confirmMsg = "등록하시겠습니까?";
                        mvno.ui.confirm(confirmMsg, function() {
                            mvno.cmn.ajax4json(ROOT + "/voc/abuse/insertAbuseInfo.json", data, function(result) {
                                if (result.result.code == "OK") {
                                    mvno.ui.closeWindowById("GROUP1", true);
                                    mvno.ui.alert("등록이 완료되었습니다.",'', function() {
                                        PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                                    });
                                }
                            });
                        });
                        break;

                    case "btnMod" :

                        if (!validateAbuseInfo()) {
                            return;
                        }

                        if (!validateAbuseImeiList()) {
                            return;
                        }

                        var arrOrgObj = getImeiList(PAGE.GRID2);
                        var arrNewObj = getImeiList(PAGE.GRID3);

                        var data = {
                            mstSeq : PAGE.FORM2.getItemValue("mstSeq"),
                            newImeiList : arrNewObj,
                            orgImeiList : arrOrgObj,
                            openYn: PAGE.FORM2.getItemValue("openYn"),
                            reason: PAGE.FORM2.getItemValue("reason"),
                            reasonDesc: PAGE.FORM2.getItemValue("reasonDesc")
                        };

                        var confirmMsg = "수정하시겠습니까?";
                        mvno.ui.confirm(confirmMsg, function() {
                            mvno.cmn.ajax4json(ROOT + "/voc/abuse/updateAbuseInfo.json", data, function(result) {
                                if (result.result.code == "OK") {
                                    mvno.ui.closeWindowById("GROUP1", true);
                                    mvno.ui.alert("수정이 완료되었습니다.",'', function() {
                                        PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                                    });
                                }
                            });
                        });
                        break;

                    case "btnClose" :
                        mvno.ui.closeWindowById("GROUP1");
                        break;
                }
            }
        },
    };

    var PAGE = {
        title: "${breadCrumb.title}",
        buttonAuth: ${buttonAuth.jsonString},
        breadcrumb: "${breadCrumb.breadCrumb}",
        onOpen : function() {

            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");

            var lstComActvDateT = new Date().format('yyyymmdd');
            var time = new Date().getTime();
            time = time - 1000 * 3600 * 24 * 7;
            var lstComActvDateF = new Date(time);

            PAGE.FORM1.setItemValue("searchStartDt", lstComActvDateF);
            PAGE.FORM1.setItemValue("searchEndDt", lstComActvDateT);

        }
    };

    function validateAbuseInfo() {
        if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("reason"))) {
            mvno.ui.alert("등록사유를 선택해 주세요.");
            return false;
        }

        if (PAGE.FORM2.getItemValue("reason") == "09" && mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("reasonDesc"))) {
            mvno.ui.alert("상세 사유를 입력해 주세요.");
            return false;
        }

        if (PAGE.GRID2.getRowsNum() + PAGE.GRID3.getRowsNum() == 0) {
            mvno.ui.alert("imei를 최소 1건 이상 입력해 주세요.");
            return false;
        }

        if (PAGE.GRID2.getRowsNum() + PAGE.GRID3.getRowsNum() > 10) {
            mvno.ui.alert("imei를 최대 10건 이하로 입력해 주세요.");
            return false;
        }

        return true;
    }

    function validateAbuseImeiList() {
        for (var idx = 0; idx < PAGE.GRID3.getRowsNum(); idx++) {
            var rowId = PAGE.GRID3.getRowId(idx);
            var rowNum = PAGE.GRID3.cells(rowId, 0).getValue();
            var arrData = PAGE.GRID3.cells(rowId, 1).getValue();

            if (arrData == "" || arrData == null) {
                mvno.ui.alert("신규 입력[" + rowNum + "번]<br />imei를 입력해 주세요.");
                return false;
            }

            if (arrData.match(/^\d+$/ig) == null) {
                mvno.ui.alert("신규 입력[" + rowNum + "번]<br />imei는 숫자만 입력 가능합니다.");
                return false;
            }
            if (arrData.length != 15) {
                mvno.ui.alert("신규 입력[" + rowNum + "번]<br />imei 15자리를 입력해 주세요.");
                return false;
            }
        }
        return true;
    }

    function getImeiList(grid) {
        var imeiList = [];
        grid.forEachRow(function (id) {
            var imei = grid.cells(id, 1).getValue();
            imeiList.push(imei);
        });
        return imeiList;
    }

    function isLength15(value) {
        return value.length == 15;
    }

    function isInteger(value) {
        var regex = /^[0-9]+$/;
        return regex.test(value);
    }

</script>