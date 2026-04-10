<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div> <!-- 메인 -->
<div id="GRID2" class="section-full"></div> <!-- 상세 -->
<div id="POPUPREG"> <!-- 가족관계/구성원 등록 -->
    <div id="REGPARENT" class="section-box"></div>
    <div id="REGCHILD" class="section-box"></div>
</div>
<!-- Script -->
<script type="text/javascript">

    var DHX = {
        //------------------------------------------------------------
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
                {type:"block", list:[
                        {type:"calendar", width:100, label:"등록일자", name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:60, offsetLeft:10, readonly:true},
                        {type:"newcolumn"},
                        {type:"calendar", label:"~", name:"srchEndDt", value:"${srchEndDt}", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5, width:100, readonly:true},
                        {type:"newcolumn"},
                        {type:"select", label:"가족관계상태", name:"useYn", labelWidth:80, width:100, offsetLeft:20},
                        {type:"newcolumn"},
                        {type:"select", name:"searchGbn", label:"검색구분", width:120, offsetLeft:20},
                        {type:"newcolumn"},
                        {type:"input", width:100, name:"searchName"}
                    ]},
                {type:"button", value:"조회", name:"btnSearch", className:"btn-search1"},
            ],
            onValidateMore : function(data) {
                if ( data.srchStrtDt > data.srchEndDt ) {
                    this.pushError("srchStrtDt", "등록일자", "시작일자가 종료일자보다 클 수 없습니다.");
                }
                if ( data.srchEndDt != "" && data.srchStrtDt == "" ){
                    this.pushError("srchStrtDt", "등록일자", "시작일자를 입력하세요.");
                }
                if ( data.srchStrtDt != "" && data.srchEndDt == "" ){
                    this.pushError("srchEndDt", "등록일자", "종료일자를 입력하세요.");
                }

                if ( data.searchGbn != "" && data.searchName.trim() == "" ) {
                    this.pushError("searchName", "검색구분", "검색어를 입력하세요");
                    PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
                }
            },
            onButtonClick : function(btnId) {
                var form = this;

                switch (btnId) {
                    case "btnSearch":
                        PAGE.GRID1.list(ROOT + "/rcp/familyMgmt/getFamilyRelList.do", form);
                        PAGE.GRID2.clearAll();
                        break;
                }
            },
            onChange : function(name, value) {
                // 검색구분
                if ( name == "searchGbn" ) {
                    PAGE.FORM1.setItemValue("searchName", "");

                    if ( value == null || value == "" ) {
                        var endDate = new Date().format('yyyymmdd');
                        var time = new Date().getTime();
                        time = time - 1000 * 3600 * 24 * 7;
                        var startDate = new Date(time);

                        PAGE.FORM1.enableItem("srchStrtDt");
                        PAGE.FORM1.enableItem("srchEndDt");
                        PAGE.FORM1.enableItem("useYn");

                        PAGE.FORM1.setItemValue("srchStrtDt", startDate);
                        PAGE.FORM1.setItemValue("srchEndDt", endDate);

                        PAGE.FORM1.disableItem("searchName");
                    } else {
                        PAGE.FORM1.setItemValue("srchStrtDt", "");
                        PAGE.FORM1.setItemValue("srchEndDt", "");
                        PAGE.FORM1.setItemValue("useYn", "");

                        PAGE.FORM1.disableItem("srchStrtDt");
                        PAGE.FORM1.disableItem("srchEndDt");
                        PAGE.FORM1.disableItem("useYn");

                        PAGE.FORM1.enableItem("searchName");
                    }
                }
            }
        },
        // ----------------------------------------
        //가족관계 목록 조회
        GRID1 : {
            css : {
                height : "240px"
            },
            title : "조회결과",
            header : "가족관계시퀀스,서비스계약번호,고객명,생년월일,나이(만),휴대폰번호,가족관계상태,상태코드,시작일시,종료일시,종료사유,종료사유코드,종료메모,등록자,등록일시,수정자,수정일시",  // 17
            columnIds : "famSeq,svcCntrNo,subLinkName,birthDt,age,subscriberNo,useYnNm,useYn,strtDttm,endDttm,endCodeNm,endCode,endMsg,regstId,regstDttm,rvisnId,rvisnDttm",  // 17
            colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,left,center,center,center,center",  // 17
            colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro,ro,roXdt,ro,roXdt",  // 17
            colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",  // 17
            widths : "100,100,80,80,70,100,100,100,130,130,100,100,250,90,130,90,130",  // 17
            hiddenColumns : [0, 7, 11],
            paging : true,
            pageSize : 20,
            showPagingAreaOnInit : true,
            buttons : {
                hright : {
                    btnExcel : { label : "엑셀다운로드" }
                }
            },
            onRowSelect:function(rId, cId){
                PAGE.GRID2.clearAll();
                PAGE.GRID2.list(ROOT + "/rcp/familyMgmt/getFamilyMemberList.do", this.getSelectedRowData());
            },
            onButtonClick : function(btnId, selectedData) {
                var grid = this;
                switch (btnId) {
                    case "btnExcel" :
                        if ( PAGE.GRID1.getRowsNum() == 0 ) {
                            mvno.ui.alert("데이터가 존재하지 않습니다");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/rcp/familyMgmt/getFamilyRelListExcel.json?menuId=MSP1010023", true, {postData:searchData});

                        break;
                }
            }
        },
        // ----------------------------------------
        //가족관계 구성원 상세
        GRID2 : {
            css : {
                height : "240px"
            },
            title : "구성원상세",
            header : "가족관계시퀀스,번호,서비스계약번호,구성원유형,구성원유형코드,피부양자유형,피부양자유형코드,고객명,생년월일,나이(만),휴대폰번호,구성원상태,상태코드,시작일시,종료일시,종료사유,종료사유코드,종료메모,등록자,등록일시,수정자,수정일시",  // 22
            columnIds : "famSeq,seq,svcCntrNo,memTypeNm,memType,childTypeNm,childType,subLinkName,birthDt,age,subscriberNo,useYnNm,useYn,strtDttm,endDttm,endCodeNm,endCode,endMsg,regstId,regstDttm,rvisnId,rvisnDttm",  // 22
            colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,left,center,center,center,center",  // 22
            colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro,ro,roXdt,ro,roXdt",  // 22
            colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str", // 22
            widths : "100,50,100,100,100,100,100,80,80,70,100,80,100,130,130,100,100,250,90,130,90,130", // 22
            hiddenColumns : [0, 4, 6, 12, 16],
            paging : true,
            pageSize : 20,
            showPagingAreaOnInit : true,
            buttons : {
                hright : {
                    btnRelNew : { label : "가족관계 신규" },
                    btnRelCan : { label : "가족관계 종료" }
                },
                right : {
                    btnMemAdd : { label : "구성원 추가" },
                    btnMemCan : { label : "구성원 종료" }
                }
            }
            ,onButtonClick : function(btnId) {
                var grid = this;
                var famRow = PAGE.GRID1.getSelectedRowData();
                var memRow = PAGE.GRID2.getSelectedRowData();

                switch (btnId) {
                    case "btnRelNew" :
                        initPopupReg("NEW");
                        break;
                    case "btnRelCan" :
                        if ( famRow == null ) {
                            mvno.ui.alert("종료할 가족관계를 선택해주세요.");
                            return;
                        }

                        if ( famRow.useYn != 'Y' ) {
                            mvno.ui.alert("이미 종료된 가족관계입니다.");
                            return;
                        }

                        mvno.ui.prompt("가족관계 종료 사유", function(endMsg) {
                            var varData = {
                                famSeq : famRow.famSeq,
                                endMsg : endMsg
                            };

                            mvno.cmn.ajax(ROOT + "/rcp/familyMgmt/cancelFamilyRel.json", varData, function(result) {
                                if ( result.result.code == "OK" ) {
                                    mvno.ui.notify("NCMN0003");
                                    PAGE.GRID1.refresh();
                                    PAGE.GRID2.clearAll();
                                } else {
                                    mvno.ui.alert(result.result.msg);
                                }
                            });
                        }, { lines: 5,  maxLength : 80} );

                        break;
                    case "btnMemAdd" :
                        if ( famRow == null ) {
                            mvno.ui.alert("구성원을 추가할 가족관계를 선택해주세요.");
                            return;
                        }

                        if ( famRow.useYn != 'Y' ) {
                            mvno.ui.alert("이미 종료된 가족관계입니다.");
                            return;
                        }

                        initPopupReg("ADD");
                        PAGE.REGCHILD.setItemValue("famSeq", famRow.famSeq);
                        break;
                    case "btnMemCan" :
                        if ( memRow == null ) {
                            mvno.ui.alert("종료할 구성원을 선택해주세요.");
                            return;
                        }

                        if ( memRow.useYn != 'Y' ) {
                            mvno.ui.alert("이미 종료된 구성원입니다.");
                            return;
                        }

                        mvno.ui.prompt("구성원 종료 사유", function(endMsg) {
                            var varData = {
                                famSeq : memRow.famSeq,
                                seq : memRow.seq,
                                endMsg : endMsg
                            };

                            mvno.cmn.ajax(ROOT + "/rcp/familyMgmt/cancelFamilyMember.json", varData, function(result) {
                                if ( result.result.code == "OK" ) {
                                    mvno.ui.notify("NCMN0003");
                                    PAGE.GRID1.refresh();
                                    PAGE.GRID2.clearAll();
                                } else {
                                    mvno.ui.alert(result.result.msg);
                                }
                            });
                        }, { lines: 5,  maxLength : 80} );

                        break;
                }
            }
        },
        // ----------------------------------------
        //가족관계 구성원 등록(법정대리인)
        REGPARENT : {
            title : "법정대리인",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
                {type: "block", blockOffset: 0, list: [
                    {type: "input", label: "서비스계약번호", name: "parentSvcCntrNo", width: 120, labelWidth: 90, offsetLeft: 8, maxLength: 16, userdata: {align: "left"}, disabled:true},
                    {type: "newcolumn"},
                    {type: "button", value: "찾기",  name: "btnFindParent"},
                    {type: "newcolumn"},
                    {type: "input", label: "전화번호", name: "parentMobileNo", width: 120, labelWidth: 58, offsetLeft: 30.5, maxLength: 16, userdata: {align: "left"}, disabled:true}
                ]},
                {type: "block", blockOffset: 0, list: [
                    {type: "input", label: "고객명", name: "parentName", width: 120, labelWidth: 90, offsetLeft: 8, maxLength: 16, userdata: {align: "left"}, disabled:true},
                    {type: "newcolumn"},
                    {type: "input", label: "생년월일", name: "parentBirthDt", width: 120, labelWidth: 58, offsetLeft: 80, maxLength: 16, userdata: {align: "left"}, disabled:true}
                ]}
            ],
            onButtonClick : function(btnId) {
                var form = this;
                var regType = PAGE.REGCHILD.getItemValue("regType");

                switch (btnId) {
                    case "btnFindParent" :
                        mvno.ui.codefinder("CSTMR", function(result) {
                            if ( result.subStatus != "A" ) {
                                mvno.ui.alert("사용중인 고객만 등록할 수 있습니다.");
                                return;
                            }

                            if ( result.svcCntrNo != null && (result.svcCntrNo == PAGE.REGCHILD.getItemValue("childSvcCntrNo")) ) {
                                mvno.ui.alert("법정대리인과 피부양자를 동일한 고객으로 등록할 수 없습니다.");
                                return;
                            }

                            form.setItemValue("parentSvcCntrNo", result.svcCntrNo);
                            form.setItemValue("parentMobileNo", result.subscriberNo);
                            form.setItemValue("parentName", result.subLinkName);
                            form.setItemValue("parentBirthDt", result.birthDt);

                            PAGE.REGCHILD.setItemValue("memType", "10");
                            changeMemType();
                        });
                        break;
                }
            }
        },
        // ----------------------------------------
        //가족관계 구성원 등록(피부양자)
        REGCHILD : {
            title : "피부양자",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
                {type: "block", blockOffset: 0, list: [
                    {type: "input", label: "서비스계약번호", name: "childSvcCntrNo", width: 120, labelWidth: 90, offsetLeft: 8, maxLength: 16, userdata: {align: "left"}, disabled:true},
                    {type: "newcolumn"},
                    {type: "button", value: "찾기",  name: "btnFindChild"},
                    {type: "newcolumn"},
                    {type: "input", label: "전화번호", name: "childMobileNo", width: 120, labelWidth: 58, offsetLeft: 30.5, maxLength: 16, userdata: {align: "left"}, disabled:true}
                ]},
                {type: "block", blockOffset: 0, list: [
                    {type: "input", label: "고객명", name: "childName", width: 120, labelWidth: 90, offsetLeft: 8, maxLength: 16, userdata: {align: "left"}, disabled:true},
                    {type: "newcolumn"},
                    {type: "input", label: "생년월일", name: "childBirthDt", width: 120, labelWidth: 58, offsetLeft: 80, maxLength: 16, userdata: {align: "left"}, disabled:true},
                    {type: "hidden", label: "생년월일(8자리)", name: "childBirth", labelWidth: 20, offsetLeft: 8},
                ]},
                {type: "block", blockOffset: 0, list: [
                    {type: "select", label: "피부양자구분", name: "childType", width: 120, labelWidth: 90, offsetLeft: 8},
                    {type: "hidden", label: "등록구분(신규/추가)", name: "regType", labelWidth: 20, offsetLeft: 8},
                    {type: "hidden", label: "구성원구분(추가 시)", name: "memType", value: "10", labelWidth: 20, offsetLeft: 8},
                    {type: "hidden", label: "가족관계 시퀀스", name: "famSeq", labelWidth: 20, offsetLeft: 8}
                ]}
            ],
            buttons : {
                center : {
                    btnReg : { label : "저장" },
                    btnAdd : { label : "추가" },
                    btnClose : { label : "닫기" }
                }
            },
            onButtonClick : function(btnId) {
                var parentForm = PAGE.REGPARENT;
                var childForm = this;
                var regType = childForm.getItemValue("regType");

                switch (btnId) {
                    case "btnFindChild" :
                        mvno.ui.codefinder("CSTMR", function(result) {
                            if ( result.subStatus != "A" ) {
                                mvno.ui.alert("사용중인 고객만 등록할 수 있습니다.");
                                return;
                            }

                            if ( result.svcCntrNo != null && (result.svcCntrNo == parentForm.getItemValue("parentSvcCntrNo")) ) {
                                mvno.ui.alert("법정대리인과 피부양자를 동일한 고객으로 등록할 수 없습니다.");
                                return;
                            }

                            childForm.setItemValue("childSvcCntrNo", result.svcCntrNo);
                            childForm.setItemValue("childMobileNo", result.subscriberNo);
                            childForm.setItemValue("childName", result.subLinkName);
                            childForm.setItemValue("childBirthDt", result.birthDt);
                            childForm.setItemValue("childBirth", result.birth);

                            childForm.setItemValue("memType", "20");
                            changeMemType();
                        });
                        break;
                    case "btnReg" :
                        var parentSvcCntrNo = parentForm.getItemValue("parentSvcCntrNo");
                        var childSvcCntrNo = childForm.getItemValue("childSvcCntrNo");
                        var childType = childForm.getItemValue("childType");
                        var childBirth = childForm.getItemValue("childBirth");

                        if ( !parentSvcCntrNo ) {
                            mvno.ui.alert("법정대리인 정보가 존재하지 않습니다.");
                            return;
                        }
                        if ( !childSvcCntrNo ) {
                            mvno.ui.alert("피부양자 정보가 존재하지 않습니다.");
                            return;
                        }
                        if ( !childType ) {
                            mvno.ui.alert("피부양자구분을 선택해주세요.");
                            return;
                        }

                        var varData = {
                            parentSvcCntrNo : parentSvcCntrNo,
                            childSvcCntrNo : childSvcCntrNo,
                            childBirth : childBirth,
                            childType : childType
                        };

                        mvno.cmn.ajax(ROOT + "/rcp/familyMgmt/regstFamilyRel.json", varData, function(result) {
                            if (result.result.code == "OK") {
                                mvno.ui.closeWindowById("POPUPREG", true);
                                mvno.ui.notify("NCMN0004");
                                PAGE.GRID1.refresh();
                                PAGE.GRID2.clearAll();
                            } else {
                                mvno.ui.alert(result.result.msg);
                            }
                        });
                        break;
                    case "btnAdd" :
                        var parentSvcCntrNo = parentForm.getItemValue("parentSvcCntrNo");
                        var childSvcCntrNo = childForm.getItemValue("childSvcCntrNo");
                        var childBirth = childForm.getItemValue("childBirth");
                        var childType = childForm.getItemValue("childType");
                        var memType = childForm.getItemValue("memType");
                        var famSeq = childForm.getItemValue("famSeq");

                        if ( !parentSvcCntrNo && !childSvcCntrNo ) {
                            mvno.ui.alert("법정대리인이나 피부양자 정보가 존재하지 않습니다.");
                            return;
                        }
                        if ( memType == "20" && !childType ) {
                            mvno.ui.alert("피부양자구분을 선택해주세요.");
                            return;
                        }

                        var varData = {
                            parentSvcCntrNo : parentSvcCntrNo,
                            childSvcCntrNo : childSvcCntrNo,
                            childBirth : childBirth,
                            childType : childType,
                            memType : memType,
                            famSeq : famSeq
                        };

                        mvno.cmn.ajax(ROOT + "/rcp/familyMgmt/addFamilyMember.json", varData, function(result) {
                            if (result.result.code == "OK") {
                                mvno.ui.closeWindowById("POPUPREG", true);
                                mvno.ui.notify("NCMN0004");
                                PAGE.GRID2.refresh();
                            } else {
                                mvno.ui.alert(result.result.msg);
                            }
                        });
                        break;
                    case "btnClose" :
                        mvno.ui.closeWindowById("POPUPREG", true);
                        break;
                }
            }
        },
    };


    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        buttonAuth: ${buttonAuth.jsonString},

        onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createGrid("GRID2");

            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9045", orderBy:"etc1"}, PAGE.FORM1, "useYn");     // 회원상태
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9046", orderBy:"etc1"}, PAGE.FORM1, "searchGbn"); // 검색구분
            PAGE.FORM1.disableItem("searchName");
        }
    };

    function initPopupReg(regType) {
        mvno.ui.createForm("REGPARENT");
        mvno.ui.createForm("REGCHILD");
        PAGE.REGPARENT.clear();
        PAGE.REGCHILD.clear();

        mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9048"}, PAGE.REGCHILD, "childType");
        PAGE.REGCHILD.setItemValue("regType", regType);
        changeMemType();

        var regTitle = ""
        if ( regType == "NEW" ) {
            regTitle = "가족관계 신규";
            mvno.ui.hideButton("REGCHILD", "btnAdd");
            mvno.ui.showButton("REGCHILD", "btnReg");
        }
        if ( regType == "ADD" ) {
            regTitle = "구성원 추가";
            mvno.ui.hideButton("REGCHILD", "btnReg");
            mvno.ui.showButton("REGCHILD", "btnAdd");
        }

        mvno.ui.popupWindowById("POPUPREG", regTitle, 580, 360, {
            onClose: function(win) {
                win.closeForce();
            }
        });
    }

    function changeMemType() {
        var parentForm = PAGE.REGPARENT;
        var childForm = PAGE.REGCHILD;
        var regType = childForm.getItemValue("regType");
        var memType = childForm.getItemValue("memType");

        if ( regType != "ADD" ) return;

        if ( memType == "10" ) {
            childForm.setItemValue("childSvcCntrNo", "");
            childForm.setItemValue("childMobileNo", "");
            childForm.setItemValue("childName", "");
            childForm.setItemValue("childBrithDt", "");
            childForm.setItemValue("childType", "");

            childForm.disableItem("childType");
        }
        if ( memType == "20" ) {
            parentForm.setItemValue("parentSvcCntrNo", "");
            parentForm.setItemValue("parentMobileNo", "");
            parentForm.setItemValue("parentName", "");
            parentForm.setItemValue("parentBrithDt", "");

            childForm.enableItem("childType");
        }
    }

</script>