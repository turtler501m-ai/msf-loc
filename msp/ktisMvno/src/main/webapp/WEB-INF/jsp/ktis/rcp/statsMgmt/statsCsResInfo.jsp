<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : statsCsResInfo.jsp
     * @Description : 예약상담 신청현황
     * @
     * @ 수정일          수정자      수정내용
     * @ ------------ -------- -----------------------------
     * @ 2023.05.03     한수연      최초생성
     * @
     * @author : 한수연
     * @Create Date : 2023. 05. 03.
     */
%>

<!-- 고객 > 현황자료 > 예약상담 신청현황 -->
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="POPUP1">
    <div id="FORM2" class="section-box"></div>
    <div class="row">
        <div id="FORM3" class="section-half c3"></div>
        <div id="FORM4" class="section-half c7"></div>
    </div>
    <div id="FORM5"></div>
</div>
<div id="POPUP2">
    <div id="FORM6" class="section-box"></div>
    <div id="GRID2" class="section-full"></div>
</div>


<!-- Script -->
<script type="text/javascript">

    // === global variable setting START ===
    var pageObj= {
        today: null,
        dueDate: null,
        preSelDate: null,
        maxPerCnt: '${maxPerCnt}'
    };

    (function(){
        var today= new Date();
        pageObj.today= today;
        pageObj.dueDate= new Date(today.getFullYear(), today.getMonth()+3, 0);
    }());
    // === global variable setting END ===

    var DHX = {
        FORM1 : {
            items : [
                 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
                ,{type:"block", list:[
                   {type:"calendar", label:"예약일시", name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100, offsetLeft:10}
                  ,{type:"newcolumn"}
                  ,{type:"calendar", label:"~", name:"srchEndDt", value:"${srchEndDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelAlign:"center", labelWidth:10, width:100, offsetLeft:5}
                 ]}
                ,{type: "hidden", name: "btnCount1", value:"0"}, // 조회버튼 여러번 클릭 막기
                ,{type: "button", name: "btnSearch", value:"조회", className:"btn-search1"}
            ]
            ,onButtonClick : function(btnId) {
                var form = this;
                switch(btnId) {
                  case "btnSearch" :

                    var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1")) + 1;
                    PAGE.FORM1.setItemValue("btnCount1", btnCount2)

                    if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; // 로딩중 여러번 클릭 막기

                    PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getCsResInfoList.json", form, {
                        callback : function () {
                            PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
                        }
                    });

                    break;
                }
            }
            ,onValidateMore : function(data) {

                if (data.srchStrtDt > data.srchEndDt) {
                    PAGE.FORM1.setItemValue("btnCount1", 0); // 초기화 - 조회 버튼
                    this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
                }
                if(data.srchStrtDt != "" && data.srchEndDt == ""){
                    PAGE.FORM1.setItemValue("btnCount1", 0); // 초기화 - 조회 버튼
                    this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
                }
                if(data.srchEndDt != "" && data.srchStrtDt == ""){
                    PAGE.FORM1.setItemValue("btnCount1", 0); // 초기화 - 조회 버튼
                    this.pushError("srchEndDt", "조회기간", "조회기간을 입력하세요.");
                }
            }
        }
        // ----------------------------------------
        // 예약상담 신청현황
      , GRID1 : {
            css : {
                height : "565px"
            }
            , title : "조회결과"
            , header : "예약일시,상담요청시간대 코드,상담요청 시간대,허용 인원,상태값,예약 고객,예약 가능고객,종결 고객,미처리 고객,처리결과" // 10
            , columnIds : "csResDt,csResTm,csResTmNm,perCnt,resStat,resCnt,remainCnt,comCnt,uncomCnt,csStat" // 10
            , widths : "120,20,120,95,110,95,95,95,95,110" // 10
            , colAlign : "center,center,center,right,center,right,right,right,right,center" // 10
            , colTypes : "roXd,ro,ro,ron,ro,ron,ron,ron,ron,ro" // 10
            , colSorting : "str,str,str,str,str,str,str,str,str,str" // 10
            , hiddenColumns : [1]
            , paging : true
            , pageSize : 22
            , showPagingAreaOnInit : true
            , buttons : {
                hright : {
                  btnExcel : {label:"엑셀다운로드"}
                }
               ,right : {
                  btnMerge : {label:"상담예약 등록/수정"}
                }
              }
            , onRowDblClicked : function(rowId, celInd) {
                // 상세폼 호출
                this.callEvent('onButtonClick', ['btnDtl']);
            }
            , onButtonClick : function(btnId) {
                var grid = this;

                switch (btnId) {
                    case "btnExcel":
                        if(PAGE.GRID1.getRowsNum() == 0){
                            mvno.ui.alert("데이터가 존재하지 않습니다");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/stats/statsMgmt/getCsResListExcel.json?menuId=MSP1010046", true, {postData:searchData});
                        break;
                    case "btnMerge":
                        mvno.ui.createForm("FORM2"); // 예약일시폼
                        mvno.ui.createForm("FORM3"); // 오전폼
                        mvno.ui.createForm("FORM4"); // 오후폼
                        mvno.ui.createForm("FORM5"); // 버튼폼

                        PAGE.FORM2.clear();
                        PAGE.FORM3.clear();
                        PAGE.FORM4.clear();

                        // 상담예약 등록/수정 팝업 초기화
                        mvno.ui.enableButton("FORM5", "btnUpdate");
                        mvno.ui.enableButton("FORM5", "btnSave");
                        inputStatChange("FORM3", PAGE.FORM3._indexId, "N");
                        inputStatChange("FORM4", PAGE.FORM4._indexId, "N");

                        // 조회날짜에 등록된 허용인원수 가져오기
                        getCsResPerCntByDt({csResDt: null});

                        mvno.ui.popupWindowById("POPUP1", "예약상담 등록/수정", 600, 500, {
                            onClose: function(win) {

                                if (!PAGE.FORM3.isChanged() || !PAGE.FORM4.isChanged()){
                                    // 달력이 열려있는 경우 달력도 함께 닫기
                                    $(".dhtmlxcalendar_in_input").hide();
                                    return true;
                                }
                                mvno.ui.confirm("CCMN0005", function(){
                                    $(".dhtmlxcalendar_in_input").hide();
                                    win.closeForce();
                                });
                            }
                        });
                        break;
                    case "btnDtl":
                        var data= grid.getSelectedRowData();

                        if(data == null){
                            mvno.ui.alert("선택된 데이터가 없습니다");
                            return;
                        }

                        mvno.ui.createForm("FORM6");
                        mvno.ui.createGrid("GRID2");

                        PAGE.FORM6.clear();
                        PAGE.GRID2.clearAll();

                        // 조회 시점에 따라 예약고객수와 처리상태가 변경될 수 있으므로 FORM6데이터도 다시 세팅
                        mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getCsResInfoList.json", data, function(resultData) {

                            if(mvno.cmn.isEmpty(resultData.result.data.rows)) {
                                mvno.ui.alert("예약정보가 존재하지 않습니다.");
                                return;
                            }

                            PAGE.FORM6.setFormData(resultData.result.data.rows[0]);
                        }, {async:false});

                        PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getCsResDtlInfoList.json", data);

                        mvno.ui.popupWindowById("POPUP2", "예약상담 상세", 750, 480, {
                            onClose: function(win) {
                                win.closeForce();
                            }
                        });
                        break;
                }
            }
        }
        // ----------------------------------------
        // 예약상담 등록/수정 팝업
      , FORM2 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, offsetLeft: 10, blockOffset:0}
               ,{type:"block", list:[
                   {type:"calendar", label:"예약일시", name:"csResDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:110}
                ]}
            ]
           ,onChange: function (name, value) {

                switch (name) {
                    case "csResDt":

                        var selDate= new Date(value.getFullYear(), value.getMonth(), value.getDate());

                        // 등록,수정 조건 (익익월까지만 등록 가능_익익월이후의 날짜는 조회 불가, 오늘포함 이전날짜는 수정 및 등록 불가)
                        if(selDate > pageObj.dueDate){
                            mvno.ui.alert("익익월까지만 등록 가능합니다.", "warning", function(){
                                PAGE.FORM2.setItemValue("csResDt", pageObj.preSelDate); // 이전날짜로 되돌리기
                            });
                            return;
                        }else if(selDate <= pageObj.today){
                            // disabled 처리
                            mvno.ui.disableButton("FORM5", "btnUpdate");
                            mvno.ui.disableButton("FORM5", "btnSave");
                            inputStatChange("FORM3", PAGE.FORM3._indexId, "Y");
                            inputStatChange("FORM4", PAGE.FORM4._indexId, "Y");
                        }else{
                            // enabled 처리
                            mvno.ui.enableButton("FORM5", "btnUpdate");
                            mvno.ui.enableButton("FORM5", "btnSave");
                            inputStatChange("FORM3", PAGE.FORM3._indexId, "N");
                            inputStatChange("FORM4", PAGE.FORM4._indexId, "N");
                        }

                        // 조회날짜에 등록된 허용인원수 가져오기
                        getCsResPerCntByDt(PAGE.FORM2.getFormData(true));
                        break;
                }
            }
        } // FORM2 End
      , FORM3 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, offsetTop:3, offsetLeft:3, blockOffset:0}
               ,{type: "fieldset", label: "오전", name:"AMTIME", inputWidth:180, list:[
                   {type:"block", list:[
                      {type:"input", label:"08:00~08:30", name:"am01", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"08:30~09:00", name:"am02", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"09:00~09:30", name:"am03", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"09:30~10:00", name:"am04", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"10:00~10:30", name:"am05", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"10:30~11:00", name:"am06", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"11:00~11:30", name:"am07", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"11:30~12:00", name:"am08", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                ]}
            ]
        }  // FORM3 End
      , FORM4 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, offsetTop:3, offsetLeft:3, blockOffset:0}
               ,{type: "fieldset", label: "오후", name:"PMTIME", inputWidth:340, offsetLeft:15, list:[
                   {type:"block", list:[
                      {type:"input", label:"12:00~12:30", name:"pm01", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"16:00~16:30", name:"pm09", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40, offsetLeft:30}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"12:30~13:00", name:"pm02", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"16:30~17:00", name:"pm10", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40, offsetLeft:30}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"13:00~13:30", name:"pm03", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"17:00~17:30", name:"pm11", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40, offsetLeft:30}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"13:30~14:00", name:"pm04", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"17:30~18:00", name:"pm12", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40, offsetLeft:30}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"14:00~14:30", name:"pm05", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"18:00~18:30", name:"pm13", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40, offsetLeft:30}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"14:30~15:00", name:"pm06", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"18:30~19:00", name:"pm14", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40, offsetLeft:30}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"15:00~15:30", name:"pm07", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                  ,{type:"block", list:[
                      {type:"input", label:"15:30~16:00", name:"pm08", value: "0", numberFormat:"000,000,000,000", validate: "ValidInteger", width:40}
                   ]}
                ]}
            ]
        } // FORM4 End
      , FORM5 : {
            buttons : {
              center : {
                btnSave : {label:"저장"}
               ,btnUpdate: { label:"수정"}
               ,btnClose : {label:"닫기"}
              }
            }
           ,onButtonClick : function(btnId) {

                switch (btnId) {
                    case "btnSave": case "btnUpdate":

                        var alertMsg= (btnId == "btnSave") ? "저장되었습니다." : "수정되었습니다.";

                        var searchDate= PAGE.FORM2.getFormData(true);       // 예약일시
                        var amTime= PAGE.FORM3.getFormData(true, true);     // 오전폼 허용인원 (FORM3에만 존재하는 input값을 가져오기 위해 두번쨰 인자 추가)
                        var pmTime= PAGE.FORM4.getFormData(true, true);     // 오후폼 허용인원 (FORM4에만 존재하는 input값을 가져오기 위해 두번쨰 인자 추가)
                        var targetValue= "";

                        // 등록/수정 전 최대 허용가능 인원 수 검사
                        for(var i=0;i<PAGE.FORM3._indexId.length;i++){

                            targetValue= amTime[PAGE.FORM3._indexId[i]];

                            if(targetValue === null || targetValue === ""){
                                mvno.ui.alert("빈값은 입력 불가합니다.");
                                return;
                            }
                            if(parseInt(targetValue) > parseInt(pageObj.maxPerCnt)){
                                mvno.ui.alert("예약 시간대 별 최대 허용인원은 " +pageObj.maxPerCnt+ "명 입니다.");
                                return;
                            }
                            if(parseInt(targetValue) < 0){
                                mvno.ui.alert("음수값은 입력 불가합니다.");
                                return;
                            }
                        }

                        for(var i=0;i<PAGE.FORM4._indexId.length;i++){

                            targetValue= pmTime[PAGE.FORM4._indexId[i]];

                            if(targetValue === null || targetValue === ""){
                                mvno.ui.alert("빈값은 입력 불가합니다.");
                                return;
                            }
                            if(parseInt(targetValue) > parseInt(pageObj.maxPerCnt)){
                                mvno.ui.alert("예약 시간대 별 최대 허용인원은 " +pageObj.maxPerCnt+ "명 입니다.");
                                return;
                            }
                            if(parseInt(targetValue) < 0 ){
                                mvno.ui.alert("음수값은 입력 불가합니다.");
                                return;
                            }
                        }

                        var param=  Object.assign(searchDate,{amTime:amTime},{pmTime:pmTime});

                        mvno.cmn.ajax(ROOT + "/stats/statsMgmt/mergeCsResPerCntByDt.json", param, function() {
                            mvno.ui.alert(alertMsg);
                            getCsResPerCntByDt(searchDate);
                        },{async:false, dimmed:true});

                        break;

                    case "btnClose":
                        mvno.ui.closeWindowById("POPUP1");
                        break;
                }
            }
        } // FORM5 End
      , FORM6 : {
           items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, offsetTop:2, offsetLeft:5, blockOffset:0}
               ,{type:"block", list:[
                   {type:"calendar", label:"예약일시", name:"csResDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", readonly:true, disabled:true, width:110}
                  ,{type:"newcolumn"}
                  ,{type:"input", label:"접수 시간대", name:"csResTmNm", readonly:true, disabled:true, labelWidth:70, width:110, offsetLeft:20}
                ]}

               ,{type:"block", list:[
                   {type:"input", label:"허용 인원", name:"perCnt", numberFormat:"000,000,000,000", validate: "ValidInteger", readonly:true, disabled:true, width:40}
                  ,{type:"newcolumn"}
                  ,{type:"input", label:"예약 고객", name:"resCnt", numberFormat:"000,000,000,000", validate: "ValidInteger", readonly:true, disabled:true, width:40, offsetLeft:15}
                  ,{type:"newcolumn"}
                  ,{type:"input", label:"예약 가능고객", name:"remainCnt", numberFormat:"000,000,000,000", validate: "ValidInteger", readonly:true, disabled:true, labelWidth:80, width:40, offsetLeft:15}
                  ,{type:"newcolumn"}
                  ,{type:"input", label:"종결 고객", name:"comCnt", numberFormat:"000,000,000,000", validate: "ValidInteger", readonly:true, disabled:true, width:40, offsetLeft:15}
                  ,{type:"newcolumn"}
                  ,{type:"input", label:"미처리 고객", name:"uncomCnt", numberFormat:"000,000,000,000", validate: "ValidInteger", readonly:true, disabled:true, labelWidth:70, width:40, offsetLeft:15}
                ]}

               ,{type:"block", list:[
                   {type:"input", label:"상태값", name:"resStat", readonly:true, disabled:true, width:110}
                  ,{type:"newcolumn"}
                  ,{type:"input", label:"처리 결과", name:"csStat", readonly:true, disabled:true, labelWidth:70, width:110, offsetLeft:20}
                ]}
           ]
        } // FORM6 End
      , GRID2 : {
            css : {
                height : "180px"
            }
            ,title : "상세정보"
            ,header : "접수일시,고객명,계약번호,처리 결과, VOC(대),VOC(중),VOC(소),VOC(상세),AP 사용자명, AP계정, 처리일시, 순번" //12
            ,columnIds : "regstDt,cstmrNm,contractNum,csStat,vocFir,vocSec,vocThi,vocDtl,apNm,apId,endDttm, csResSeq" //12
            ,colAlign : "center,left,center,center,center,center,left,left,left,left,center,center" //12
            ,colTypes : "roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro" //12
            ,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str" //12
            ,widths : "120,80,100,100,120,120,120,150,100,100,180,20"
            ,paging: true
            , showPagingAreaOnInit : true
            , pageSize : 5
            //,multiCheckbox : true
            ,hiddenColumns : [11]
            ,buttons : {
                center : {
                  btnClose : {label : "닫기"}
                }
            }
            ,onButtonClick : function(btnId) {
                switch(btnId){
                    case "btnClose" :
                        mvno.ui.closeWindowById("POPUP2");
                        break;
                }
            }
        } //GRID2 End
    };

    // input 활성화 또는 비활성화 처리하기
    // formName:대상FORM 이름, nameArr: input 태그의 name 배열, disabledYn: Y(비활성화), N(활성화)
    function inputStatChange(formName, nameArr, disabledYn){

        if(!PAGE[formName]) return false;
        if(!nameArr || nameArr.length <= 0) return false;

        if(disabledYn == "Y"){
            for(var i=0;i<nameArr.length;i++){
                PAGE[formName].disableItem(nameArr[i]);
            }
            return true;
        }

        for(var i=0;i<nameArr.length;i++){
            PAGE[formName].enableItem(nameArr[i]);
        }
        return true;
    }



    // 특정 조회날짜 허용인원수 가져오기
    // param: {csResDt: yyyymmdd}
    function getCsResPerCntByDt(param){

        mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getCsResPerCntByDt.json", param, function(resultData) {

            //예약일시 세팅
            PAGE.FORM2.setItemValue("csResDt", resultData.csResDt);
            pageObj.preSelDate= resultData.csResDt; // 이전 선택 날짜 세팅

            if(mvno.cmn.isEmpty(resultData.result.data.rows)) {
                // 신규등록
                mvno.ui.hideButton("FORM5" , 'btnUpdate');
                mvno.ui.showButton("FORM5" , 'btnSave');

                // 초기값 0으로 세팅
                for(var i=0;i<PAGE.FORM3._indexId.length;i++){
                    PAGE.FORM3.setItemValue(PAGE.FORM3._indexId[i], "0");
                }

                for(var i=0;i<PAGE.FORM4._indexId.length;i++){
                    PAGE.FORM4.setItemValue(PAGE.FORM4._indexId[i], "0");
                }
                return;
            }

            // 수정
            mvno.ui.showButton("FORM5" , 'btnUpdate');
            mvno.ui.hideButton("FORM5" , 'btnSave');

            // 기존 인원수 세팅 (오전폼, 오후폼)
            PAGE.FORM3.setFormData(resultData.result.data.rows[0]);
            PAGE.FORM4.setFormData(resultData.result.data.rows[0]);
        }, {async:false});
    }


    var PAGE = {
        title: "${breadCrumb.title}"
        , breadcrumb: "${breadCrumb.breadCrumb}"
        , buttonAuth: ${buttonAuth.jsonString}
        , onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
        }
    };


</script>





