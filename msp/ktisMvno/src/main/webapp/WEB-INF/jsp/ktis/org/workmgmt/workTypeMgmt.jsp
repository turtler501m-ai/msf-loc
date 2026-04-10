<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : workTypeMgmt.jsp
   * @Description : 업무유형별 업무관리 화면
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2025.09.02      최초생성
   * @Create Date : 2025.09.02
   */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<div id="GROUP1" style="display: none;">
  <div class="row">
    <div id="GRID2"></div>
  </div>
  <div id="FORM2"></div>
</div>

<div id="GROUP2" style="display: none;">
  <div class="row">
    <div id="GRID3"></div>
  </div>
  <div id="FORM3"></div>
</div>

<div id="GROUP3" style="display: none;">
  <div class="row">
    <div id="GRID4"></div>
  </div>
  <div id="FORM4"></div>
</div>

<div id="FORM5" class="section-box"></div>


<div id="GROUP4" style="display: none;">
  <div id="FORM6" class="section-search"></div>
  <div class="row">
    <div id="GRID6" class="c3"></div>
    <div id="GRID7" class="c3-5"></div>
    <div id="GRID8" class="c3-5"></div>
  </div>
  <div id="GRID9"></div>
</div>



<!-- Script -->
<script type="text/javascript">
    var today = new Date().format("yyyymmdd");
    var DHX = {
        // 검색
        FORM1 : {
            items : [
                { type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
                ,{ type : "block", list : [
                        {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchBaseDate', label: '기준일자', calendarPosition: 'bottom' ,width:100, offsetLeft:10 ,required: true }
                        , {type: "newcolumn", offset:20}
                        , {type:"input", width:305, label:"업무명", name: "workTmplNm", offsetLeft:20}
                        , {type: "newcolumn", offset:20}
                        , {type:"input", width:120, label:"수취서류", name: "itemNm", offsetLeft:20}
                    ]}
                ,{ type: "block", list : [
                        {type:"select", width:100, label:"업무유형", name:"workId", offsetLeft:10}
                        , {type: "newcolumn", offset:20}
                        , {type:"input", width:100, label:"명의자", name:"ownerNm", offsetLeft:20}
                        , {type: "newcolumn", offset:20}
                        , {type:"input", width:100, label:"요청자", name:"rqstrNm", offsetLeft:10}
                        , {type: "newcolumn", offset:20}
                        , {type:"select", width:120, label:"사용여부", name:"useYn", offsetLeft:20}
                    ]}
                ,{ type : "button", name: "btnSearch",value : "조회", className:"btn-search2" }
            ]
            , onValidateMore : function(data){
                if(data.searchBaseDate == null || data.searchBaseDate == "") {
                    this.pushError("searchBaseDate","기준일자","기준일자를 입력해주세요.");
                    return;
                }
            }
            , onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {
                    case "btnSearch":

                        var url1 = ROOT + "/org/workMgmt/getWorkTmplList.json";

                        PAGE.GRID1.list(url1, form);

                        break;
                }
            }
        }//FORM1 End
        , GRID1 : {
            css : {
                height : "530px"
            }
            ,title : "업무목록"
            ,header : "유형코드,업무명,사용여부,업무유형,명의자구분,요청자구분,서류수,등록자,등록일"
            ,columnIds :"workTmplId,workTmplNm,useYnNm,workNm,ownerNm,rqstrNm,docCnt,cretNm,cretDt"
            ,widths :"100,220,60,150,100,100,50,80,80"
            ,colAlign :"Center,left,Center,Center,Center,Center,Center,Center,Center"
            ,colTypes :"ro,ro,ro,ro,ro,ro,ro,ro,roXd"
            ,colSorting :"str,str,str,str,str,str,str,str,str"
            //,hiddenColumns : "5"
            ,paging : true
            ,pageSize : 20
            ,buttons : {
                hright : {
                    btnExcel : { label : "엑셀다운로드" }
                },
                left : {
                    btnItemReg : {label : "수취서류구분" }
                    ,btnOwnerReg : { label : "명의자구분" }
                    ,btnRqstrReg : { label : "요청자구분" }
                },
                right : {
                    btnReg : { label : "등록" }
                    ,btnMod : { label : "수정" }
                }
            }

            ,onRowDblClicked : function(rowId, celInd) {

                mvno.ui.createForm("FORM5");
                PAGE.FORM5.clear();

                mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM5, "useYn");
                var sData = PAGE.GRID1.getSelectedRowData();
                var itemNms = sData.itemNm.split(",");
                var reqYns = sData.requestYn.split(",");

                var itemNmReqY = [];
                var itemNmReqN = [];

                for (idx = 0; idx < itemNms.length; idx++){
                    var itemNm = itemNms[idx];

                    if(reqYns[idx] == "Y"){
                        itemNmReqY.push(itemNm);
                    } else {
                        itemNmReqN.push(itemNm);
                    }
                }

                var fData = {
                    workNm : sData.workNm
                    ,workTmplNm : sData.workTmplNm
                    ,ownerNm : sData.ownerNm
                    ,rqstrNm : sData.rqstrNm
                    ,useYn : sData.useYn
                    ,itemNmReqY : itemNmReqY.join(",")
                    ,itemNmReqN : itemNmReqN.join(",\n")
                }
                PAGE.FORM5.setFormData(fData, true)

                mvno.ui.popupWindowById("FORM5", "업무상세보기", 930, 400, {
                    onClose: function(win) {
                        if (!PAGE.FORM5.isChanged()) {
                            return true;
                        }
                        mvno.ui.confirm("CCMN0005", function() {
                            win.closeForce();
                        });
                    }
                });
            }
            ,onButtonClick : function(btnId, selectedData) {

                var grid = this;

                switch (btnId) {

                    case "btnExcel" :

                        if(PAGE.GRID1.getRowsNum() == 0){
                            mvno.ui.alert("데이터가 존재하지 않습니다");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/org/workMgmt/getWorkTmplListExcel.json?menuId=MSP2002301", true, {postData:searchData});
                        break;

                    case "btnItemReg" :
                        mvno.ui.createGrid("GRID2");
                        mvno.ui.createForm("FORM2");

                        PAGE.GRID2.clearAll();
                        PAGE.FORM2.setFormData(true);

                        PAGE.GRID2.list(ROOT + "/org/workMgmt/getWorkItemList.json", "");

                        PAGE.FORM2.setItemLabel("btnSave", "등록");
                        PAGE.FORM2.clearChanged();


                        mvno.ui.popupWindowById("GROUP1", "수취서류구분 등록", 930, 750, {
                            onClose: function(win) {
                                if (!PAGE.FORM2.isChanged()) {
                                    return true;
                                }
                                mvno.ui.confirm("CCMN0005", function() {
                                    win.closeForce();
                                });
                            }
                            ,maximized:true
                        });

                        break;

                    case "btnOwnerReg" :
                        mvno.ui.createGrid("GRID3");
                        mvno.ui.createForm("FORM3");

                        PAGE.GRID3.clearAll();
                        PAGE.FORM3.setFormData(true);

                        PAGE.GRID3.list(ROOT + "/org/workMgmt/getWorkOwnerList.json", "");

                        PAGE.FORM3.setItemLabel("btnSave", "등록");
                        PAGE.FORM3.clearChanged();

                        mvno.ui.popupWindowById("GROUP2", "명의자구분 등록", 930, 750, {
                            onClose: function(win) {
                                if (!PAGE.FORM3.isChanged()) {
                                    return true;
                                }
                                mvno.ui.confirm("CCMN0005", function() {
                                    win.closeForce();
                                });
                            }
                            ,maximized:true
                        });

                        break;

                    case "btnRqstrReg" :
                        mvno.ui.createGrid("GRID4");
                        mvno.ui.createForm("FORM4");

                        PAGE.GRID4.clearAll();
                        PAGE.FORM4.setFormData(true);

                        PAGE.GRID4.list(ROOT + "/org/workMgmt/getWorkRqstrList.json", "");

                        PAGE.FORM4.setItemLabel("btnSave", "등록");
                        PAGE.FORM4.clearChanged();

                        mvno.ui.popupWindowById("GROUP3", "요청자구분 등록", 930, 750, {
                            onClose: function(win) {
                                if (!PAGE.FORM4.isChanged()) {
                                    return true;
                                }
                                mvno.ui.confirm("CCMN0005", function() {
                                    win.closeForce();
                                });
                            }
                            ,maximized:true
                        });

                        break;

                    case "btnReg" :

                        openWorkPopup("reg");

                        break;

                    case "btnMod" :

                        var sData = PAGE.GRID1.getSelectedRowData();

                        if(sData == null){
                            mvno.ui.alert("ECMN0003");
                            return;
                        }
                        openWorkPopup("mod", sData);

                        break;
                }
            }
        }
        ,GRID2 : {
            css : {
                height : "520px"
            },
            title : "수취서류구분 리스트",
            header : "선택,수취서류ID,수취서류구분,신청서여부,등록자,등록일,수정자,수정일",
            columnIds : "rowCheck,itemId,itemNm,requestYn,cretNm,cretDt,amdNm,amdDt",
            colAlign : "center,left,left,center,center,center,center,center",
            colTypes : "ch,ro,ro,ro,ro,roXd,ro,roXd",
            colSorting : "str,str,str,str,str,str,str,str",
            widths : "50,100,430,80,80,80,80,80",
            hiddenColumns:[1],
            onRowSelect : function(rowId, celInd) {
                var rowData = this.getRowData(rowId);
                PAGE.FORM2.setFormData(rowData);
                PAGE.FORM2.setItemValue("requestYn", rowData.requestYn == "Y" ? 1 : 0);
                PAGE.FORM2.setItemLabel("btnSave", "수정");
            }
            ,buttons: {
                right: {
                    btnDelete: {label: "삭제"}
                }
            }
            , onButtonClick: function (btnId) {

                switch (btnId) {
                    case "btnDelete" :

                        var chkData = PAGE.GRID2.getCheckedRowData();

                        if (chkData.length < 1) {
                            mvno.ui.alert("삭제할 서류를 선택하세요.");
                            return;
                        }

                        mvno.ui.confirm("선택한 서류를 삭제 하시겠습니까?", function () {
                            var arrChk = [];
                            for (var idx = 0; idx < chkData.length; idx++) {

                                var data = {
                                    itemId: chkData[idx].itemId
                                    ,itemNm: chkData[idx].itemNm
                                }
                                arrChk.push(data);
                            }

                            var sData = {
                                itemList: arrChk
                            }

                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/updateWorkItemEnd.json", sData, function (result) {
                                PAGE.GRID2.list(ROOT + "/org/workMgmt/getWorkItemList.json", "");
                                mvno.ui.notify("NCMN0003");
                                PAGE.FORM2.clearChanged();
                                PAGE.FORM2.setItemLabel("btnSave", "등록");
                                PAGE.FORM2.setFormData("true");
                            });
                        });

                        break;
                }
            }
        }
        ,FORM2 : {
            title : "수취서류구분 등록",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                ,{type:"block", list:[
                        {type:"input", width:540, label:"서류명", name:"itemNm", labelWidth : 70, required: true, maxLength: 100}
                        ,{type:"newcolumn", offset:20}
                        ,{type:"checkbox", width: 50, label:"신청서여부", name: "requestYn"}
                        ,{type:"newcolumn", offset:20}
                        ,{type:"button", value: "초기화", name:"btnReset"}
                        ,{type:"newcolumn", offset:0}
                        ,{type:"button", value: "등록", name:"btnSave"}
                        ,{type:"newcolumn", offset:0}
                        ,{type:"button", value: "닫기", name:"btnClose"}
                        ,{type:"hidden", name: "itemId", label: "서류ID"}
                    ]}
            ]
            ,onButtonClick : function(btnId) {

                switch(btnId){

                    case "btnReset" :

                        PAGE.FORM2.setItemLabel("btnSave", "등록");
                        PAGE.FORM2.setFormData("true");
                        PAGE.FORM2.clearChanged();

                        break;

                    case "btnSave" :

                        if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("itemNm"))){
                            mvno.ui.alert("서류명을 입력하세요.");
                            return;
                        }

                        var sData = {
                            itemNm : PAGE.FORM2.getItemValue("itemNm")
                            ,requestYn : PAGE.FORM2.isItemChecked("requestYn") ? "Y" : "N"
                            ,itemId : PAGE.FORM2.getItemValue("itemId")
                        }

                        mvno.ui.confirm("서류를 저장하시겠습니까?", function() {
                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/saveWorkItem.json", sData, function(result) {
                                PAGE.GRID2.list(ROOT + "/org/workMgmt/getWorkItemList.json", "");
                                mvno.ui.notify("NCMN0004");

                                PAGE.FORM2.setItemLabel("btnSave", "등록");
                                PAGE.FORM2.setFormData("true");
                                PAGE.FORM2.clearChanged();
                            });
                        });

                        break;

                    case "btnClose" :

                        mvno.ui.closeWindowById("GROUP1");

                        break;
                }
            }
        }
        ,GRID3 : {
            css: {
                height: "520px"
            },
            title: "명의자구분 리스트",
            header: "선택,명의자구분ID,명의자구분,등록자,등록일,수정자,수정일",
            columnIds: "rowCheck,ownerId,ownerNm,cretNm,cretDt,amdNm,amdDt",
            colAlign: "center,left,left,center,center,center,center",
            colTypes: "ch,ro,ro,ro,roXd,ro,roXd",
            colSorting: "str,str,str,str,str,str,str",
            widths: "50,100,515,80,80,80,80",
            hiddenColumns: [1],
            onRowSelect: function (rowId, celInd) {
                var rowData = this.getRowData(rowId);
                PAGE.FORM3.setFormData(rowData);
                PAGE.FORM3.setItemLabel("btnSave", "수정");
            }
            , buttons: {
                right: {
                    btnDelete: {label: "삭제"}
                }
            }
            , onButtonClick: function (btnId) {
                switch (btnId) {
                    case "btnDelete" :

                        var chkData = PAGE.GRID3.getCheckedRowData();

                        if (chkData.length < 1) {
                            mvno.ui.alert("삭제할 명의자를 선택하세요.");
                            return;
                        }

                        mvno.ui.confirm("선택한 명의자를 삭제 하시겠습니까?", function () {
                            var arrChk = [];
                            for (var idx = 0; idx < chkData.length; idx++) {
                                var data = {
                                    ownerId: chkData[idx].ownerId
                                    ,ownerNm : chkData[idx].ownerNm
                                }
                                arrChk.push(data);
                            }

                            var sData = {
                                ownerList: arrChk
                            }

                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/updateWorkOwnerEnd.json", sData, function (result) {
                                PAGE.GRID3.list(ROOT + "/org/workMgmt/getWorkOwnerList.json", "");
                                mvno.ui.notify("NCMN0003");

                                PAGE.FORM3.setItemLabel("btnSave", "등록");
                                PAGE.FORM3.setFormData("true");
                                PAGE.FORM3.clearChanged();
                            });
                        });

                        break;
                }
            }
        }
        ,FORM3 : {
            title : "명의자구분 등록",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                ,{type:"block", list:[
                        {type:"input", width:640, label:"명의자", name:"ownerNm", labelWidth : 70, required: true, maxLength: 20}
                        ,{type:"newcolumn", offset:20}
                        ,{type:"button", value: "초기화", name:"btnReset"}
                        ,{type:"newcolumn", offset:0}
                        ,{type:"button", value: "등록", name:"btnSave"}
                        ,{type:"newcolumn", offset:0}
                        ,{type:"button", value: "닫기", name:"btnClose"}
                        ,{type: "hidden", name: "ownerId", label: "명의자ID"}
                    ]}
            ]
            ,onButtonClick : function(btnId) {

                switch(btnId){

                    case "btnReset" :

                        PAGE.FORM3.setItemLabel("btnSave", "등록");
                        PAGE.FORM3.setFormData("true");
                        PAGE.FORM3.clearChanged();

                        break;

                    case "btnSave" :

                        if(mvno.cmn.isEmpty(PAGE.FORM3.getItemValue("ownerNm"))){
                            mvno.ui.alert("명의자를 입력하세요.");
                            return;
                        }

                        var sData = {
                            ownerNm : PAGE.FORM3.getItemValue("ownerNm")
                            ,ownerId : PAGE.FORM3.getItemValue("ownerId")
                        }

                        mvno.ui.confirm("명의자를 저장하시겠습니까?", function() {
                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/saveWorkOwner.json", sData, function(result) {
                                PAGE.GRID3.list(ROOT + "/org/workMgmt/getWorkOwnerList.json", "");
                                mvno.ui.notify("NCMN0004");

                                PAGE.FORM3.setItemLabel("btnSave", "등록");
                                PAGE.FORM3.setFormData("true");
                                PAGE.FORM3.clearChanged();
                            });
                        });

                        break;

                    case "btnClose" :

                        mvno.ui.closeWindowById("GROUP2");

                        break;
                }
            }
        }
        ,GRID4 : {
            css: {
                height: "520px"
            },
            title: "요청자구분 리스트",
            header: "선택,요청자ID,요청자구분,등록자,등록일,수정자,수정일",
            columnIds: "rowCheck,rqstrId,rqstrNm,cretNm,cretDt,amdNm,amdDt",
            colAlign: "center,left,left,center,center,center,center",
            colTypes: "ch,ro,ro,ro,roXd,ro,roXd",
            colSorting: "str,str,str,str,str,str,str",
            widths: "50,100,515,80,80,80,80",
            hiddenColumns: [1],
            onRowSelect: function (rowId, celInd) {
                var rowData = this.getRowData(rowId);
                PAGE.FORM4.setFormData(rowData);
                PAGE.FORM4.setItemLabel("btnSave", "수정");
            }
            , buttons: {
                right: {
                    btnDelete: {label: "삭제"}
                }
            }
            , onButtonClick: function (btnId) {
                switch (btnId) {
                    case "btnDelete" :
                        var chkData = PAGE.GRID4.getCheckedRowData();

                        if (chkData.length < 1) {
                            mvno.ui.alert("삭제할 요청자를 선택하세요.");
                            return;
                        }

                        mvno.ui.confirm("선택한 요청자를 삭제 하시겠습니까?", function () {
                            var arrChk = [];
                            for (var idx = 0; idx < chkData.length; idx++) {
                                var data = {
                                    rqstrId: chkData[idx].rqstrId
                                    ,rqstrNm : chkData[idx].rqstrNm
                                }
                                arrChk.push(data);
                            }

                            var sData = {
                                rqstrList: arrChk
                            }

                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/updateWorkRqstrEnd.json", sData, function (result) {
                                PAGE.GRID4.list(ROOT + "/org/workMgmt/getWorkRqstrList.json", "");
                                mvno.ui.notify("NCMN0003");
                                PAGE.FORM4.setItemLabel("btnSave", "등록");
                                PAGE.FORM4.setFormData("true");
                                PAGE.FORM4.clearChanged();
                            });
                        });

                        break;
                }
            }
        }
        ,FORM4 : {
            title : "요청자구분 등록",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                ,{type:"block", list:[
                        {type:"input", width:640, label:"요청자", name:"rqstrNm", labelWidth : 70, required: true, maxLength: 30}
                        ,{type:"newcolumn", offset:20}
                        ,{type:"button", value: "초기화", name:"btnReset"}
                        ,{type:"newcolumn", offset:0}
                        ,{type:"button", value: "등록", name:"btnSave"}
                        ,{type:"newcolumn", offset:0}
                        ,{type:"button", value: "닫기", name:"btnClose"}
                        ,{type: "hidden", name: "rqstrId", label: "요청자ID"}
                    ]}
            ]
            ,onButtonClick : function(btnId) {

                switch(btnId){

                    case "btnReset" :
                        PAGE.FORM4.setItemLabel("btnSave", "등록");
                        PAGE.FORM4.setFormData("true");
                        PAGE.FORM4.clearChanged();
                        break;

                    case "btnSave" :

                        if(mvno.cmn.isEmpty(PAGE.FORM4.getItemValue("rqstrNm"))){
                            mvno.ui.alert("요청자를 입력하세요.");
                            return;
                        }

                        var sData = {
                            rqstrNm : PAGE.FORM4.getItemValue("rqstrNm")
                            ,rqstrId : PAGE.FORM4.getItemValue("rqstrId")
                        }

                        mvno.ui.confirm("요청자를 저장하시겠습니까?", function() {
                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/saveWorkRqstr.json", sData, function(result) {
                                PAGE.GRID4.list(ROOT + "/org/workMgmt/getWorkRqstrList.json", "");
                                mvno.ui.notify("NCMN0004");
                                PAGE.FORM4.setItemLabel("btnSave", "등록");
                                PAGE.FORM4.setFormData("true");
                                PAGE.FORM4.clearChanged();
                            });
                        });

                        break;

                    case "btnClose" :

                        mvno.ui.closeWindowById("GROUP3");

                        break;
                }
            }
        }
        ,FORM5 : {
            items : [
                { type : "settings", position : "label-left", labelAlign : "left",  blockOffset : 0 }
                ,{ type : "block", list : [
                        {type:"input", width:290, label:"업무유형", name:"workNm", offsetLeft:10, disabled:true, labelWidth : 110}
                        ,{type:"newcolumn", offset:24}
                        ,{type:"select", width:290, label:"사용여부", name:"useYn", offsetLeft:10, labelWidth: 80, disabled:true}
                    ]}
                ,{ type : "block", list : [
                        {type:"input", width:700, label:"업무명", name: "workTmplNm", offsetLeft:10, disabled:true, labelWidth : 110}
                    ]}
                ,{ type : "block", list : [
                        {type:"input", width:290, label:"명의자구분", name: "ownerNm", offsetLeft:10, disabled:true, labelWidth : 110}
                        , {type:"newcolumn", offset:24}
                        , {type:"input", width:290, label:"요청자구분", name: "rqstrNm", offsetLeft:10, labelWidth: 80, disabled:true}
                    ]}
                , { type : "block", list : [
                        {type:"input", width:700, label:"수취서류 -신청서", name: "itemNmReqY", offsetLeft:10, disabled:true, labelWidth : 110}
                    ]}
                ,{ type : "block", list : [
                        {type:"input", width:700, label:"수취서류 -그외서류", name: "itemNmReqN", offsetLeft:10, rows : 7, disabled:true, labelWidth : 110}
                    ]}
            ]
            ,buttons: {
                center: {
                    btnMod: {label: "수정"}
                    ,btnClose: {label: "닫기"}
                }
            }

            ,onButtonClick : function(btnId) {

                switch(btnId){

                    case "btnMod" :

                        var sData = PAGE.GRID1.getSelectedRowData();

                        if(sData == null){
                            mvno.ui.alert("ECMN0003");
                            return;
                        }
                        openWorkPopup("mod", sData);

                        break;

                    case "btnClose" :

                        mvno.ui.closeWindowById("FORM5");

                        break;
                }
            }
        }
        ,FORM6 : {
            title : "업무정보 (업무유형별 요청자에 따라 수취서류가 다를 경우 각각 별개로 업무등록 필요)",
            items : [
                { type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
                ,{ type : "block", list : [
                        {type:"select", width:100, label:"업무유형", name:"workId", offsetLeft:10, required: true }
                        , {type: "newcolumn", offset:20}
                        , {type:"input", width:100, label:"업무명", name: "workTmplId", offsetLeft:20, required: true, maxLength: 50}
                        , {type: "newcolumn"}
                        , {type:"input", width:200, label:"", name: "workTmplNm", offsetLeft:20}
                        , {type: "newcolumn", offset:20}
                        , {type:"select", width:100, label:"사용여부", name: "useYn", offsetLeft:20, required: true}
                        , {type: "hidden", name: "endDt", label: "종료일시"}
                    ]
                }
            ]
        }
        ,GRID6 : {
            css : {
                height : "250px"
            },
            title : "명의자구분 (다중선택가능)",
            header : "선택,명의자구분ID,명의자구분,등록자,등록일,수정자,수정일",
            columnIds : "rowCheck,ownerId,ownerNm,cretNm,cretDt,amdNm,amdDt",
            colAlign : "center,left,left,left,left,left,left",
            colTypes : "ch,ro,ro,ro,roXd,ro,roXd",
            colSorting : "str,str,str,str,str,str,str",
            widths : "50,100,200,100,80,100,80",
            hiddenColumns:[1,3,4,5,6],
        }
        ,GRID7 : {
            css : {
                height : "250px"
            },
            title : "요청자구분 (다중선택가능)",
            header : "선택,요청자ID,요청자구분,등록자,등록일,수정자,수정일",
            columnIds : "rowCheck,rqstrId,rqstrNm,cretNm,cretDt,amdNm,amdDt",
            colAlign : "center,left,left,left,left,left,left",
            colTypes : "ch,ro,ro,ro,roXd,ro,roXd",
            colSorting : "str,str,str,str,str,str,str",
            widths : "50,100,235,100,80,100,80",
            hiddenColumns:[1,3,4,5,6],
        }
        ,GRID8 : {
            css : {
                height : "250px"
            },
            title : "수취서류 (다중선택가능 최대 10개)",
            header : "선택,수취서류ID,수취서류구분,신청서여부,등록자,등록일,수정자,수정일",
            columnIds : "rowCheck,itemId,itemNm,requestYn,cretNm,cretDt,amdNm,amdDt",
            colAlign : "center,left,left,left,left,left,left,left",
            colTypes : "ch,ro,ro,ro,ro,roXd,ro,roXd",
            colSorting : "str,str,str,str,str,str,str,str",
            widths : "50,100,235,100,100,80,100,80",
            hiddenColumns:[1,3,4,5,6,7],
        }
        ,GRID9 : {
            css : {
                height : "240px"
            },
            title : "문자템플릿 (다중선택가능)",
            header : "선택,템플릿ID,템플릿명,문자제목,발신자번호",
            columnIds : "rowCheck,smsTemplateId,smsTemplateNm,subject,callback",
            colAlign : "center,center,left,left,center",
            colTypes : "ch,ro,ro,ro,ro",
            colSorting : "str,str,str,str,str",
            widths : "50,110,310,310,120",
            hiddenColumns:[],
            buttons : {
                center : {
                    btnSave : { label : "저장" }
                    ,btnClose : { label : "닫기"}
                }
            }
            ,onButtonClick : function(btnId) {

                switch(btnId){

                    case "btnSave" :

                        var now = new Date().format("yyyymmddhhmiss");
                        var endDt = PAGE.FORM6.getItemValue("endDt");
                        var workTmplId = PAGE.FORM6.getItemValue("workTmplId")

                        if(!mvno.cmn.isEmpty(workTmplId) && endDt < now) {
                            mvno.ui.alert("이미 종료된 업무는 수정할 수 없습니다");
                            return;
                        }

                        if(mvno.cmn.isEmpty(PAGE.FORM6.getItemValue("workTmplNm"))){
                            mvno.ui.alert("업무명을 입력하세요");
                            return;
                        }
                        if(PAGE.GRID6.getCheckedRowData().length < 1){
                            mvno.ui.alert("명의자구분을 선택하세요.");
                            return;
                        }
                        if(PAGE.GRID7.getCheckedRowData().length < 1){
                            mvno.ui.alert("요청자구분을 선택하세요.");
                            return;
                        }
                        if(PAGE.GRID8.getCheckedRowData().length < 1){
                            mvno.ui.alert("수취서류를 선택하세요.");
                            return;
                        }
                        if(PAGE.GRID8.getCheckedRowData().length > 10){
                            mvno.ui.alert("수취서류는 최대 10개까지만 선택할 수 있습니다.");
                            return;
                        }
                        if(PAGE.GRID9.getCheckedRowData().length < 1){
                            mvno.ui.alert("문자템플릿을 선택하세요.");
                            return;
                        }

                        mvno.ui.confirm("업무를 저장 하시겠습니까?", function() {

                            var arrOwner = [];
                            var arrOwnerData = PAGE.GRID6.getCheckedRowData();

                            for(var idx=0; idx<arrOwnerData.length; idx++) {
                                var data = {
                                    ownerId: arrOwnerData[idx].ownerId
                                    ,ownerNm: arrOwnerData[idx].ownerNm
                                    ,status : arrOwnerData[idx].status
                                }
                                arrOwner.push(data);
                            }

                            var arrRqstr = [];
                            var arrRqstrData = PAGE.GRID7.getCheckedRowData();

                            for(var idx=0; idx<arrRqstrData.length; idx++) {
                                var data = {
                                    rqstrId: arrRqstrData[idx].rqstrId
                                    ,rqstrNm: arrRqstrData[idx].rqstrNm
                                    ,status: arrRqstrData[idx].status
                                }
                                arrRqstr.push(data);
                            }

                            var arrItem = [];
                            var arrItemData = PAGE.GRID8.getCheckedRowData();

                            for(var idx=0; idx<arrItemData.length; idx++) {
                                var data = {
                                    itemId: arrItemData[idx].itemId
                                    ,itemNm: arrItemData[idx].itemNm
                                    ,status: arrItemData[idx].status
                                }
                                arrItem.push(data);
                            }

                            var arrSms = [];
                            var arrSmsData = PAGE.GRID9.getCheckedRowData();

                            for(var idx=0; idx<arrSmsData.length; idx++) {
                                var data = {
                                    smsTemplateId: arrSmsData[idx].smsTemplateId
                                }
                                arrSms.push(data);
                            }

                            var sData = {
                                workTmplId : workTmplId
                                ,workId : PAGE.FORM6.getItemValue("workId")
                                ,workTmplNm : PAGE.FORM6.getItemValue("workTmplNm")
                                ,useYn : PAGE.FORM6.getItemValue("useYn")
                                ,endDt : endDt
                                ,itemList : arrItem
                                ,ownerList : arrOwner
                                ,rqstrList : arrRqstr
                                ,smsTemplateList : arrSms

                            }

                            mvno.cmn.ajax4json(ROOT + "/org/workMgmt/saveWorkTmpl.json", sData, function(result) {
                                if(result.result.code =="OK"){
                                    PAGE.FORM6.clearChanged();
                                    mvno.ui.closeWindowById("GROUP4");
                                    mvno.ui.closeWindowById("FORM5");
                                    mvno.ui.notify("NCMN0004");
                                    PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                                }
                            });
                        });

                        break;

                    case "btnClose" :
                        mvno.ui.closeWindowById("GROUP4");
                        break;
                }
            }
        }
    }
    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        buttonAuth: ${buttonAuth.jsonString},
        onOpen:function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");

            PAGE.FORM1.setItemValue("searchBaseDate", today);
            mvno.cmn.ajax4lov( ROOT + "/org/workMgmt/getWorkIdList.json", {}, PAGE.FORM1, "workId");
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM1, "useYn");
        }
    };

    function openWorkPopup(mode, sData) {

        var now = new Date().format("yyyymmddhhmiss");

        mvno.ui.createForm("FORM6");
        mvno.ui.createGrid("GRID6"); 	//명의자
        mvno.ui.createGrid("GRID7");	//요청자
        mvno.ui.createGrid("GRID8");	//수취서류
        mvno.ui.createGrid("GRID9");	//문자템플릿

        // 초기화
        PAGE.GRID6.clearAll();
        PAGE.GRID7.clearAll();
        PAGE.GRID8.clearAll();
        PAGE.GRID9.clearAll();
        PAGE.GRID6.setEditable(true);
        PAGE.GRID7.setEditable(true);
        PAGE.GRID8.setEditable(true);
        PAGE.GRID9.setEditable(true);
        PAGE.FORM6.enableItem("workId");
        PAGE.FORM6.enableItem("workTmplNm");
        PAGE.FORM6.enableItem("useYn");
        mvno.ui.showButton("GRID9", 'btnSave');

        mvno.cmn.ajax4lov( ROOT + "/org/workMgmt/getWorkIdList.json", {}, PAGE.FORM6, "workId");
        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM6, "useYn");

        var param = (mode == "mod" ? {workTmplId:sData.workTmplId} : "");
        PAGE.GRID6.list(ROOT + "/org/workMgmt/getWorkOwnerList.json", param);
        PAGE.GRID7.list(ROOT + "/org/workMgmt/getWorkRqstrList.json", param);
        PAGE.GRID8.list(ROOT + "/org/workMgmt/getWorkItemList.json",  param);
        PAGE.GRID9.list(ROOT + "/org/workMgmt/getWorkSmsList.json",   param);

        if(mode === "mod") {
            PAGE.FORM6.setFormData(
                {workId: sData.workId
                    ,workTmplId: sData.workTmplId
                    ,workTmplNm: sData.workTmplNm
                    ,useYn: sData.useYn
                    ,endDt: sData.endDt
                }, true);
        } else { // reg
            PAGE.FORM6.setFormData(true); // 초기화
        }
        PAGE.FORM6.disableItem("workTmplId");
        PAGE.FORM6.clearChanged();

        // endDt 체크: 종료된 데이터는 모두 읽기전용으로
        if (mode === "mod" && sData.endDt < now) {
            PAGE.FORM6.disableItem("workId");
            PAGE.FORM6.disableItem("workTmplNm");
            PAGE.FORM6.disableItem("useYn");

            PAGE.GRID6.setEditable(false);
            PAGE.GRID7.setEditable(false);
            PAGE.GRID8.setEditable(false);
            PAGE.GRID9.setEditable(false);

            mvno.ui.hideButton("GRID9", 'btnSave');
        }

        mvno.ui.popupWindowById("GROUP4", (mode == "mod" ? "업무수정" : "업무등록"), 930, 750, {
            onClose: function(win) {
                if (!PAGE.FORM6.isChanged()) {
                    return true;
                }
                mvno.ui.confirm("CCMN0005", function() {
                    win.closeForce();
                });
            }
            ,maximized:true
        });
    }

</script>