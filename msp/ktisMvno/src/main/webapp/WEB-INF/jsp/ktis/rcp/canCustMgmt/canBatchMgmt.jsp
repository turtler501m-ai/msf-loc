<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>

<script type="text/javascript">

    var DHX = {
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                , {type:"block", list:[
                        {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '등록일자', labelWidth:60, calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
                        , {type: 'newcolumn'}
                        , {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
                        , {type: 'newcolumn'}
                        , {type:"select", width:100, label:"검색조건",labelWidth:60,name:"pSearchGbn", offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계약번호"}, {value:"2", text:"전화번호"}]}
                        , {type:"newcolumn"}
                        , {type:"input", width:120, name:"pSearchName",disabled: true}
                        , {type: 'newcolumn'}
                        , {type:"select", width:80, label:"처리상태", labelWidth:60, name:"procStatus", offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"N", text:"요청대기"}, {value:"P", text:"요청 중"}, {value:"Y", text:"요청완료"}, {value:"E", text:"해지실패"}, {value:"C", text:"해지완료"}, {value:"S", text:"대상제외"}]}
                    ]}
                // 조회버튼
                , {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
            ],
            onValidateMore : function(data) {
                if (data.searchStartDt > data.searchEndDt) {
                    this.pushError("searchStartDt", "직권해지일자", "시작일자가 종료일자보다 클 수 없습니다.");
                }
                if(!mvno.cmn.isEmpty(data.pSearchGbn) && mvno.cmn.isEmpty(data.pSearchName)) {
                    this.pushError(["pSearchGbn"],"검색구분","검색어를 입력하십시오.");
                }
            },
            onButtonClick : function(btnId) {
                var form = this;
                switch (btnId) {
                    case "btnSearch" :
                        PAGE.GRID1.list(ROOT + "/rcp/canCustMgmt/getCanBatchList.json", form);
                        break;
                }

            },
            onChange : function(name, value){
                var form = this;
                switch (name){
                    case "pSearchGbn" :
                        form.setItemValue("pSearchName", "");
                        if(value != ""){
                            form.enableItem("pSearchName");
                        } else {
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
            ,header : "등록일자,계약번호,전화번호,회선상태,해지처리일,처리상태,해지코드,해지불가시 사유,처리자,처리일시"//9
            ,columnIds : "regstDttm,contractNum,subscriberNo,subStatus,tcpRsltDt,procStatus,canCode,canErrorMsg,reqNm,procDttm"//9
            ,widths : "100,100,120,80,100,80,120,250,80,150"//9
            ,colAlign : "center,center,center,center,center,center,center,left,center,center"//9
            ,colTypes : "ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro"//9
            ,colSorting : "str,str,str,str,str,str,str,str,str,str"//9
            ,paging : true
            ,pageSize:20
            ,showPagingAreaOnInit : true
            , buttons : {
                hright : {
                    btnExcel:{ label:"엑셀다운로드"}
                },
                left : {
                    btnExcelUpload : { label : "일괄등록" },
                    btnExcelSample : { label : "일괄등록양식" }
                },
                right : {
                    btnCanBatch : { label : "일괄해지실행" }
                },
            }
            ,onButtonClick : function(btnId) {
                switch(btnId){

                    case "btnExcel":
                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/rcp/canCustMgmt/getCanBatchListExcelDown.json?menuId=MSP1060001", true, {postData:searchData});
                        break;

                    case "btnExcelUpload":

                        mvno.ui.createForm("FORM2");
                        PAGE.FORM2.clear();

                        var fileName;

                        PAGE.FORM2.attachEvent("onUploadFile", function(realName, serverName) {
                            PAGE.FORM2.setItemValue("fileName", serverName);

                        });

                        PAGE.FORM2.attachEvent("onFileRemove",function(realName, serverName) {
                            PAGE.FORM2.setItemValue("fileName", "");
                            return true;
                        });

                        var uploader = PAGE.FORM2.getUploader("file_upload1");
                        uploader.revive();
                        uploader.reset();

                        mvno.ui.popupWindowById("FORM2", "직권해지 엑셀업로드", 540, 200, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.isChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });
                        break;

                    case "btnExcelSample":
                        mvno.cmn.download("/rcp/canCustMgmt/getCanBatchExcelDown.json");
                        break;

                    case "btnCanBatch":
                        var confirmMsg = "요청대기 건에 대한 <br/> 일괄 해지를 실행하시겠습니까?";
                        mvno.ui.confirm(confirmMsg, function () {
                            mvno.cmn.ajax(ROOT + "/rcp/canCustMgmt/retryCanBatchRequest.json", "", function (result) {
                                PAGE.GRID1.refresh();
                            });
                        });
                        break;
                }
            }
        }
        , FORM2 : {
            title : "<font size='2' color='red'>(엑셀 업로드시 시간이 다소 소요될 수 있습니다.)</font>",
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0,  inputWidth: 'auto'},
                {type: "fieldset", label: "엑셀업로드", list:[
                        {type: "block", offsetLeft: 20, list: [
                                { type: "upload" , label: "" , name: "file_upload1" ,width:390, inputWidth: 320, url: "/rcp/dlvyMgmt/regParExcelUp.do" , userdata: { limitSize:30000 } , autoStart: true }
                            ]},
                        {type:"block", offsetLeft:20, list:[
                                {type:"hidden", name: "fileName"}
                            ]},
                    ]},
            ]
            , buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label : "닫기" }
                }
            }
            , onButtonClick : function(btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)

                switch (btnId) {

                    case "btnSave" :

                        if(mvno.cmn.isEmpty(form.getItemValue("fileName"))){
                            mvno.ui.alert("첨부된 파일이 없습니다");
                            return;
                        }
                        var userOptions = {timeout:180000};

                        mvno.cmn.ajax(ROOT + "/rcp/canCustMgmt/insertCanBatch.json", form, function(resultData) {
                            if(resultData.result.code == "NOK") {
                                mvno.ui.alert(resultData.result.msg);
                                return;
                            }
                            PAGE.FORM2.clear();
                            mvno.ui.closeWindowById("FORM2",true);
                            PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                        }, userOptions);

                        break;

                    case "btnClose":

                        mvno.ui.confirm("나가시겠습니까?", function() {
                            mvno.ui.closeWindowById("FORM2");
                            PAGE.FORM2.clear();
                        });

                        break;
                }
            }

        }
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
</script>