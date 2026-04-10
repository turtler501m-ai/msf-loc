<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : K-Note 조회 팝업
 * @
 * @Create Date : 2024. 02. 15.
 */
%>

<!-- header -->
<div class="page-header">
    <h1>K-Note조회팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2"></div>
<div id="FORM3"></div>
<!-- Script -->
<script type="text/javascript">

    var typeCd = "${loginInfo.userOrgnTypeCd}";
    var orgnId = "${loginInfo.userOrgnId}";
    var orgnNm = "${loginInfo.userOrgnNm}";

    var today = new Date().format("yyyymmdd");
    var baseDt = mvno.cmn.getAddDay(today, -7);

var DHX = {
    FORM1 : {
        items : [
            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
            , {type:"block", list:[
                {type:"calendar", width:100, label:"등록일자", labelWidth:60, name:"retvStrtDt",  dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10, readonly:true},
                {type:"newcolumn"},
                {type:"calendar", width:100, label:"~", name:"retvEndDt",   dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5,  readonly:true},
                {type:"newcolumn"}
              ]
            },
            {type : "block", list : [
                {type:"input", label:"접점", name:"cntpntCd", width:100, labelWidth:60, offsetLeft:10, maxLength:10, required: true},
                {type:"newcolumn"},
                {type:"button", name:"btnOrgFind", value:"찾기"},
                {type:"button", name:"btnOrgFind2", value:"찾기"},
                {type:"newcolumn"},
                {type:"input", name:"cntpntNm", width:100, readonly:true},
                {type:"newcolumn"},
                {type:"select", name:"svcApyTrtStatCd", label:"처리상태",labelWidth:60, offsetLeft:10, width:100}, //
                {type:"newcolumn"}
              ]
            },
            {type:"newcolumn", offset:100},
            {type :"button", name :"btnSearch", value : "조회", className:"btn-search2"},
            {type:"hidden", width:60, label:"", name:"appEventCd"},  // 이벤트코드
        ]
        ,onValidateMore : function (data){
            if(mvno.validator.dateCompare(data.retvStrtDt, data.retvEndDt) > 15) this.pushError("data.retvStrtDt", "조회기간", "조회기간은 15일 이내만 가능합니다.");
            if(data.retvStrtDt > data.retvEndDt) this.pushError("data.retvStrtDt","조회기간","조회시작일자가 종료일자보다 클 수 없습니다.");
            if(data.retvStrtDt > today) this.pushError("data.retvStrtDt","조회시작일자","오늘 이전 날짜만 허용됩니다");
            if(data.retvEndDt > today) this.pushError("data.retvEndDt","조회종료일자","오늘 이전 날짜만 허용됩니다");
        }
        , onButtonClick : function(btnId) {

            var form = this;

            switch (btnId) {
                case "btnSearch": //조회

                    form.setItemValue("appEventCd", "FS0"); //서식지 목록 조회
                    PAGE.GRID1.list("/rcp/rcpMgmt/osstKnoteCall.json", form)

                    break;

                case "btnOrgFind": // 접점 찾기
                    mvno.ui.codefinder("ORG", function(result) {
                        form.setItemValue("cntpntCd", result.orgnId);
                        form.setItemValue("cntpntNm", result.orgnNm);
                    });
                    break;
                case "btnOrgFind2": // 접점 찾기
                    mvno.ui.codefinder("KNOTEORG", function(result) {
                        form.setItemValue("cntpntCd", result.orgnId);
                        form.setItemValue("cntpntNm", result.orgnNm);
                    });
                    break;
            }
        }
    },
    GRID1 : {
        css : {
            height : "300px"
        },
        title : "K-Note이력조회",
        header : "신청서ID,신청유형,등록일시,처리상태코드,처리상태,고객명,고객유형코드,고객유형,대리점코드,접점코드,접점명",
        columnIds : "frmpapId,operTypeNm,wapplRegDate,svcApyTrtStatCd,svcApyTrtStatNm,custNm,custTypeNm,custTypeNm2,mngmAgncId,cntpntCd,cntpntNm",
        widths : "200,80,120,80,80,100,80,80,100,100,130",
        colAlign : "center,center,center,center,center,center,center,center,center,center,center",
        colTypes : "ro,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro",
        colSorting : "str,str,str,str,str,str,str,str,str,str,str",
        paging : false,
        hiddenColumns : "3,6,8,9",
        onRowDblClicked : function(rowId, celInd) {
            // 선택버튼 누른것과 같이 처리
            PAGE.FORM2.callEvent('onButtonClick', ['btnUse']);
        },
    },
    FORM2 : {
        items : [
            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, blockOffset:0}
            , {type:"block", list:[
                    {type:"button", name:"btnCstmrChk", value:"고객정보확인"},
                    {type:"newcolumn"},
                    {type:"button", name:"btnUse", value:"사용하기"},
                    {type:"newcolumn"},
                    {type:"select", name:"frmpapStatCd", label:"",width:80,  offsetLeft:484, required : true},
                    {type:"newcolumn"},
                    {type:"button", name:"btnMod", value:"상태변경"},
                    {type:"newcolumn"},
                    {type:"hidden", width:60, label:"", name:"mngmAgncId"},
                    {type:"hidden", width:60, label:"", name:"cntpntCd"},
                    {type:"hidden", width:60, label:"", name:"frmpapId"},
                    {type:"hidden", width:60, label:"", name:"appEventCd"},
                ]
            }
        ]
        ,onButtonClick : function(btnId) {

            var data = PAGE.GRID1.getSelectedRowData();

            switch (btnId) {
                case "btnCstmrChk": //고객정보확인

                    if(data == null){
                        mvno.ui.alert("ECMN0002");
                        return;
                    }

                    PAGE.FORM2.setItemValue("appEventCd", "FS1");			// 서식지 상태조회
                    PAGE.FORM2.setItemValue("mngmAgncId", data.mngmAgncId);	// 대리점코드
                    PAGE.FORM2.setItemValue("cntpntCd",   data.cntpntCd);	// 접점코드
                    PAGE.FORM2.setItemValue("frmpapId",   data.frmpapId);	// 서식지아이디

                    mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstKnoteCall.json", PAGE.FORM2, function(resultData) {
                        if (resultData.result.code == "OK") {
                            var data = resultData.result.data;
                            mvno.ui.createForm("FORM3");
                            mvno.ui.popupWindowById("FORM3", "고객정보확인", 680, 500);
                            PAGE.FORM3.setItemValue("frmpapId",data.frmpapId);     //서식지아이디
                            PAGE.FORM3.setItemValue("titl",data.titl);             //제목
                            PAGE.FORM3.setItemValue("mngmAgncId",data.mngmAgncId); //관리대리점코드
                            PAGE.FORM3.setItemValue("mngmAgncNm",data.mngmAgncNm); //관리대리점명
                            PAGE.FORM3.setItemValue("orgnId",data.orgnId);         //대리점코드 (orgnId)

                            PAGE.FORM3.setItemValue("onlineCustTrtSttusChgCd",data.onlineCustTrtSttusChgCd);   //신청유형
                            PAGE.FORM3.setItemValue("wapplRegDate",           dataFormat(data.wapplRegDate));   //등록일시
                            PAGE.FORM3.setItemValue("operTypeNm",             data.operTypeNm);                //신청유형명
                            PAGE.FORM3.setItemValue("fxdformIngrsCdNm",       data.fxdformIngrsCdNm);          //판매경로명
                            PAGE.FORM3.setItemValue("userId",                 data.userId);                    //판매자아이디
                            PAGE.FORM3.setItemValue("userNm",                 data.userNm);                    //판매자명

                            PAGE.FORM3.setItemValue("cntpntCd",data.cntpntCd);     //접점코드
                            PAGE.FORM3.setItemValue("cntpntNm",data.cntpntNm);     //접점코드명

                            PAGE.FORM3.setItemValue("custIdntNoIndNm",data.custIdntNoIndNm);   //명의자식별구분
                            PAGE.FORM3.setItemValue("custTypeNm",data.custTypeNm);             //명의자고객유형
                            PAGE.FORM3.setItemValue("nflCustNm",data.nflCustNm);               //명의자고객명
                            PAGE.FORM3.setItemValue("nflCustIdfyNo",data.nflCustIdfyNo);       //명의자식별번호 (주민번호)
                            PAGE.FORM3.setItemValue("viewNflCustIdfyNo",dataFormat(data.nflCustIdfyNo));          //명의자식별번호 뷰 (주민번호)

                            PAGE.FORM3.setItemValue("custNm",data.custNm);                     //고객명
                            PAGE.FORM3.setItemValue("realEvdnDataNm",data.realEvdnDataNm);     //실명인증증빙자료구분명
                            PAGE.FORM3.setItemValue("realEvdnDataCd",data.realEvdnDataCd);     //실명인증증빙자료구분코드
                            PAGE.FORM3.setItemValue("simpCstmrType", data.simpCstmrType);      //개통간소화고객구분

                            PAGE.FORM3.setItemValue("realCustIdntNo",data.realCustIdntNo);     //실명인증식별번호
                            PAGE.FORM3.setItemValue("realIssuDate",data.realIssuDate);         //실명인증발급일자
                            PAGE.FORM3.setItemValue("viewRealCustIdntNo",dataFormat(data.realCustIdntNo));     //실명인증식별번호 뷰 (발급번호)
                            PAGE.FORM3.setItemValue("viewRealIssuDate",dataFormat(data.realIssuDate));         //실명인증발급일자 뷰

                            PAGE.FORM3.setItemValue("opnYn",data.opnYn);                       //개통여부
                            PAGE.FORM3.setItemValue("svcApyTrtSttusCd",data.svcApyTrtSttusCd); //처리상태코드
                            PAGE.FORM3.setItemValue("svcApyTrtStatNm",data.svcApyTrtStatNm);   //처리상태
                            PAGE.FORM3.setItemValue("svcContId",data.svcContId);               //서비스계약아이디
                            PAGE.FORM3.setItemValue("saleCmpnId",data.saleCmpnId);             //사업자코드
                        } else {
                        	mvno.ui.alert(resultData.result.msg);
                        }
                    });
                    break;

                case "btnUse": //사용하기

                    if(data == null){                      //대상 Row 선택유무
                        mvno.ui.alert("ECMN0002");
                        return;
                    }
                    if(data.svcApyTrtStatCd != 2){         //처리상태가 [진행]이 아닌경우
                         mvno.ui.alert("처리상태를 [진행]으로 변경후 다시 시도해주세요")
                         return;
                    }

                    // K-Note대상이미지와 신청서 매핑
                    mvno.ui.confirm("해당 서식지를 사용하시겠습니까?", function() {
                        PAGE.FORM2.setItemValue("appEventCd", "FS1");                // 서식지 상태조회
                        PAGE.FORM2.setItemValue("mngmAgncId", data.mngmAgncId);      // 대리점코드
                        PAGE.FORM2.setItemValue("cntpntCd", data.cntpntCd);        // 접점코드
                        PAGE.FORM2.setItemValue("frmpapId", data.frmpapId);        // 서식지아이디
                        mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstKnoteCall.json", PAGE.FORM2, function (resultData) {
                            if (resultData.result.code == "OK") {
                                var data = resultData.result.data;

                                var param = {
                                    frmpapId: data.frmpapId,                        //서식지아이디
                                    mngmAgncId: data.mngmAgncId,                    //관리대리점코드
                                    mngmAgncNm: data.mngmAgncNm,                    //관리대리점명
                                    orgnId: data.orgnId,                            //대리점코드 (orgnId)
                                    onlineCustTrtSttusChgCd: data.onlineCustTrtSttusChgCd,   //신청유형
                                    cntpntCd: data.cntpntCd,                        //접점코드
                                    nflCustNm: data.nflCustNm,                      //명의자고객
                                    nflCustIdfyNo: data.nflCustIdfyNo,              //명의자식별번호      (주민번호)
                                    realEvdnDataCd: data.realEvdnDataCd,            //실명인증증빙자료구분 (주민등록증,운전면허증)
                                    realIssuDate: data.realIssuDate,                //실명인증발급일자
                                    simpCstmrType: data.simpCstmrType,              //개통간소화고객구분 (내국인/외국인)
                                    realCustIdntNo: data.realCustIdntNo,            //실명인증식별번호   (발급번호)
                                }
                                mvno.ui.closeWindow(param);
                            }
                            mvno.ui.notify(resultData.result.msg);
                        });
                    });
                    break;

                case "btnMod": //상태변경

                    if(data == null){
                        mvno.ui.alert("ECMN0002");
                        return;
                    }

                    // 동일한 상태로의 변경방지
                    if((data.svcApyTrtStatCd == "1" && PAGE.FORM2.getItemValue("frmpapStatCd") == "R") ||       //접수->접수
                        (data.svcApyTrtStatCd == "2" && PAGE.FORM2.getItemValue("frmpapStatCd") == "P") ||      //진행->진행
                        (data.svcApyTrtStatCd == "4" && PAGE.FORM2.getItemValue("frmpapStatCd") == "C"))        //취소->취소
                        {
                        mvno.ui.alert("동일한 상태로 변경할수 없습니다.");
                        return;
                    }


                    // 완료상태의 경우 상태변경방지
                    if(data.svcApyTrtStatCd == "3"){
                        mvno.ui.alert("[완료] 상태인 경우 상태를 변경할수 없습니다.");
                        return;
                    }

                    mvno.ui.confirm("CCMN0002", function() {

                        PAGE.FORM2.setItemValue("appEventCd",    "FS2");                // 서식지 상태 변경
                        PAGE.FORM2.setItemValue("mngmAgncId",    data.mngmAgncId);      // 대리점코드
                        PAGE.FORM2.setItemValue("cntpntCd",      data.cntpntCd);        // 접점코드
                        PAGE.FORM2.setItemValue("frmpapId",      data.frmpapId);        // 서식지아이디
                        mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstKnoteCall.json", PAGE.FORM2, function(resultData) {
                            if(resultData.result.code == "OK") {
                                mvno.ui.alert("처리상태가 변경되었습니다");
                                PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                            }

                            else mvno.ui.alert(resultData.result.msg);
                        });
                    });

                    break;
            }
        }
    },
    FORM3 : {
        items: [
            {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 120, blockOffset: 0}
            ,{type: "fieldset", label: "고객정보확인", name:"REQUEST", inputWidth:600, list:[
                ,{type: "block", list: [
                    {type: "input", label: "서식지아이디",    name: "frmpapId", width: 405, offsetLeft: 20, readonly: true},
                    {type: "input", label: "제목",          name: "titl", width: 405, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "관리대리점코드",  name: "mngmAgncId", width: 130, offsetLeft: 20, readonly: true}, //KTorgnId
                    {type: "newcolumn"},
                    {type: "input", label: "관리대리점명",    name: "mngmAgncNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "신청유형",    name: "operTypeNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "처리상태",    name: "svcApyTrtStatNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "등록일시",       name: "wapplRegDate", width: 130, offsetLeft: 20, readonly: true },
                    {type: "newcolumn"},
                    {type: "input", label: "판매경로명",     name: "fxdformIngrsCdNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "판매자아이디",    name: "userId", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "판매자명",      name: "userNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "접점코드",      name: "cntpntCd", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "접점코드명",     name: "cntpntNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "명의자고객유형", name: "custTypeNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "명의자식별구분명", name: "custIdntNoIndNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "명의자고객명",    name: "nflCustNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "명의자식별번호", name: "viewNflCustIdfyNo", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "고객명",       name: "custNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "실명인증식별번호", name: "viewRealCustIdntNo", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "실명인증증빙자료명", name: "realEvdnDataNm", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "실명인증발급일자", name: "viewRealIssuDate", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "개통여부",      name: "opnYn", width: 130, offsetLeft: 20, readonly: true},
                    {type: "newcolumn"},
                    {type: "input", label: "서비스계약아이디", name: "svcContId", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "관리대리점ID",    name: "orgnId", width: 130, offsetLeft: 20, readonly: true},  //orgnId
                    {type: "hidden", label: "신청유형코드",    name: "onlineCustTrtSttusChgCd", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "처리상태코드",    name: "svcApyTrtSttusCd", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "개통간소화고객구분", name: "simpCstmrType", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "명의자식별구분코드", name: "custIdntNoIndCd", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "실명인증증빙자료코드", name: "realEvdnDataCd", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "실명인증식별번호", name: "realCustIdntNo", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "실명인증발급일자", name: "realIssuDate", width: 130, offsetLeft: 20, readonly: true},
                    {type: "hidden", label: "명의자식별번호", name: "nflCustIdfyNo", width: 130, offsetLeft: 20, readonly: true},
                ]},
            ]}
        ],
        buttons : {
            center : {
                btnUse : { label: "사용하기" }
                ,btnClose : { label: "닫기" }
            }
        },
        onButtonClick : function(btnId) {

            var form = this;

            switch (btnId) {

                case "btnUse":

                    if(form.getItemValue("svcApyTrtSttusCd") != 2){ //처리상태가 [진행]이 아닌경우
                        mvno.ui.alert("처리상태[진행]으로 변경후 다시 시도해주세요.")
                        return;
                    }
                    mvno.ui.confirm("해당 서식지를 사용하시겠습니까?", function() {
                        var param = {
                            frmpapId: form.getItemValue("frmpapId"),                  //서식지아이디
                            mngmAgncId: form.getItemValue("mngmAgncId"),              //관리대리점코드
                            mngmAgncNm: form.getItemValue("mngmAgncNm"),              //관리대리점명
                            orgnId: form.getItemValue("orgnId"),                      //대리점코드 (orgnId)
                            onlineCustTrtSttusChgCd: form.getItemValue("onlineCustTrtSttusChgCd"),   //신청유형
                            cntpntCd: form.getItemValue("cntpntCd"),                  //접점코드
                            nflCustNm: form.getItemValue("nflCustNm"),                //명의자고객
                            nflCustIdfyNo: form.getItemValue("nflCustIdfyNo"),        //명의자식별번호  (주민번호)
                            realEvdnDataCd: form.getItemValue("realEvdnDataCd"),      //실명인증증빙자료구분 (주민등록증)
                            realIssuDate: form.getItemValue("realIssuDate"),          //실명인증발급일자
                            simpCstmrType: form.getItemValue("simpCstmrType"),        //개통간소화고객구분 (내국인/외국인)
                            realCustIdntNo: form.getItemValue("realCustIdntNo"),      //실명인증식별번호   (발급번호)
                        }
                        mvno.ui.closeWindow(param);
                    });

                    break;

                case "btnClose":
                    mvno.ui.closeWindowById(form);
                    break;
            }
        }
    }
};

var PAGE = {
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
        mvno.ui.createForm("FORM2");

        //날짜 세팅
        PAGE.FORM1.setItemValue("retvStrtDt", baseDt);
        PAGE.FORM1.setItemValue("retvEndDt", today);

        // if (typeCd != '10'){ // 대리점인 경우
        //     PAGE.FORM1.setItemValue("mngmAgncId", orgnId);
        //     PAGE.FORM1.setItemValue("mngmAgncNm", orgnNm);
        //     PAGE.FORM1.disableItem("mngmAgncId");
        //     PAGE.FORM1.disableItem("mngmAgncNm");
        //     PAGE.FORM1.disableItem("btnOrgFind");
        // }
        if (typeCd != '10') { // 대리점인 경우
            PAGE.FORM1.hideItem("btnOrgFind");
        } else {
            PAGE.FORM1.hideItem("btnOrgFind2");
        }

        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0272"}, PAGE.FORM1, "svcApyTrtStatCd");
        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0273",orderBy:"etc1"}, PAGE.FORM2, "frmpapStatCd");
        PAGE.FORM1.setItemValue("svcApyTrtStatCd", 1); // 처리상태 접수를 기본으로

        PAGE.FORM2.setItemValue("frmpapStatCd", "R"); // 처리상태 복구(진행->접수)를 기본으로

	}
};

    //데이터 변환
    function dataFormat(data) {
        var formatData="";
        if(data.length == 8){                 // YYYYMMDD(문자열) 년월일
            formatData = data.substring(0,4)+"-"+data.substring(4,6)+"-"+data.substring(6,8);
        }
        else if(data.length == 12){           // 운전면허번호(문자열)
            formatData = data.substring(0,2)+"-"+data.substring(2,4)+"-"+data.substring(4,10)+"-"+data.substring(10,12);
        }
        else if(data.length == 13){           // 주민등록번호(문자열)
            formatData = data.substring(0,6)+"-"+data.substring(6,13);
        }
        else if(data.length == 14){           // YYYYMMDDHHMMSS(문자열) 년월일시
            formatData = data.substring(0,4)+"-"+data.substring(4,6)+"-"+data.substring(6,8)+" "+data.substring(8,10)+":"+data.substring(10,12)+":"+data.substring(12,14);
        }
        else{
            formatData = data;
        }
        return formatData;
    }
</script>
