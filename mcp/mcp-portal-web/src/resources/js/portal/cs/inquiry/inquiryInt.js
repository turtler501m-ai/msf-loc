let pageObj = {
    mypageLink:""
}

$(document).ready(function(){

    $("#saveBtn").prop("disabled",true);

    $("#tab2").click(function(){
        var certifyYn = ajaxCommon.isNullNvl($("#certifyYn").val(),"");
        if(certifyYn=="Y"){
            if(!$("#chkAgree").is(":checked")){
                MCP.alert("정보수집 동의에 체크해 주세요.");
                return false;
            }
        }
        location.href="/cs/csInquiryIntHist.do";
    });


    // 인증 후 확인버튼
    $("#applyBtn").click(function(){
        if(!$("#chkAgree").is(":checked")){
            MCP.alert("정보수집 동의에 체크해 주세요.");
            return false;
        }
        ajaxCommon.createForm({
            method:"post"
            ,action:"/cs/csInquiryInt.do"
        });
        ajaxCommon.formSubmit();
    });

    // 체크박스
    $("#chkAgree").change(function(){
        var certifyYn = $("#certifyYn").val();
        if($(this).is(":checked") && certifyYn=="Y"){
            $("#applyBtn").prop("disabled",false);
        } else {
            $("#applyBtn").prop("disabled",true);
        }
    });

    // 문의 유형 selectbox
    $("#qnaCtg").change(function(){

        var grpCd = $("#qnaCtg option:selected").val();
        var varData = ajaxCommon.getSerializedData({
            grpCd : grpCd
        });

        if(grpCd==""){
            $("#qnaSubCtgCd").html("<option value=''>세부 유형을 선택해주세요</option>");
            $("#guidance").html("").hide();
            $("#qnaContent").text("");
            return false;
        }

        // 세부유형 가져오기
        getCdTable(varData);

    });


    $("#btnMypageLink").click(function(){
        if (pageObj.mypageLink != null && pageObj.mypageLink != "") {
            location.href= pageObj.mypageLink ;
        }
    });

    // 세부 유형
    $("#qnaSubCtgCd").change(function(){

        var cdGroupId = $("#qnaSubCtgCd option:selected").attr("cdgroupid");
        var value = $("#qnaSubCtgCd option:selected").val();

        //해지 세부 유형 선택 시 팝업 띄우기
        if (cdGroupId === "UI0013" && value === "UI00130200") {
            openModal("#cancelPopup");
            return false;
        }

        // 세부유형
        var cdGroupId1 = "BOARDINFO";
        var cdGroupId2 = $("#qnaSubCtgCd option:selected").val();
        if(cdGroupId2 !=""){

            var varData = ajaxCommon.getSerializedData({
                cdGroupId1 : cdGroupId1
                ,cdGroupId2 : cdGroupId2
            });
            getFormDtl(varData,1);

            // 문의내용
            cdGroupId1 = "BOARDFORM";
            var varData = ajaxCommon.getSerializedData({
                cdGroupId1 : cdGroupId1
                ,cdGroupId2 : cdGroupId2
            });
            getFormDtl(varData,2);

            var mypageLink = $("#qnaSubCtgCd option:selected").data("mypageLink");
            //console.log("mypageLink==>"+mypageLink);

            if (mypageLink != null  && mypageLink != "") {
                pageObj.mypageLink = mypageLink ;
                openModal("#modalMypageInfo");
                /*
                KTM.Confirm('선택하신 항목은 바로 처리하실 수 있습니다.<br/>해당 페이지로 이동하시겠습니까?'
                    , function () {
                        location.href= mypageLink ;
                        return ;
                    },function() {
                        return ;
                    },   {
                        confirmText: "\uC608",
                        cancelText: "\uC544\uB2C8\uC694"
                    });

                 */
            }
        } else {
            $("#guidance").html("").hide();
            $("#qnaContent").text("");
        }
    });

    // 문의 내용 글자막기
    $("#qnaContent").keyup(function(){
        var content = $(this).val();
        if(content.length > 1000) {
            alert("글자수는 1,000자로 이내로 제한됩니다.");
            $(this).val($(this).val().substring(0, 1000));
        }
        $("#textAreaSbstSize").html($(this).val().length + '/1,000자');
    });

    // 문의 등록하기 버튼
    $("#saveBtn").click(function(){

        var qnaCtg = $("#qnaCtg option:selected").val();
        if(qnaCtg==""){
            MCP.alert("문의 유형을 선택해 주세요.",function(){
                $("#qnaCtg").focus();
            });
            return false;
        }
        var qnaSubCtgCd = ajaxCommon.isNullNvl($("#qnaSubCtgCd option:selected").val(),"");
        if(qnaSubCtgCd==""){
            MCP.alert("세부 유형을 선택해 주세요.",function(){
                $("#qnaSubCtgCd").focus();
            });
            return false;
        }
        var qnaSubject = $("#qnaSubject").val();
        if(qnaSubject==""){
            MCP.alert("제목을 입력해 주세요.",function(){
                $("#qnaSubject").focus();
            });
            return false;
        }
        var qnaContent = $("#qnaContent").val();
        if(qnaContent==""){
            MCP.alert("문의 내용을 입력해 주세요.",function(){
                $("#qnaContent").focus();
            });
            return false;
        }
        var cntrMobileNo = $("#cntrMobileNo").val();
        var svcCntrNo = $("#cntrMobileNo option:selected").attr("svc_cntr_no");
        // var userDivision = $("#userDivision").val();
        // if(userDivision=="00"){
        // 	cntrMobileNo = $("#cntrMobileNo").attr("cntrMobileNo");
        // }
        if(cntrMobileNo==""){
            MCP.alert("휴대폰 번호를 입력해 주세요.",function(){
                $("#cntrMobileNo").focus();
            });
            return false;
        } else {
            if(cntrMobileNo.length < 10){
                MCP.alert(" 휴대폰 번호가 올바르지 않습니다.",function(){
                    $("#cntrMobileNo").focus();
                });
                return false;
            }
        }
        var smsSendYN = "N";
        if($("#qnaSmsSendYn").is(":checked")){
            smsSendYN = "Y";
        }

        if(!confirm("문의하시겠습니까?")) {
            return false;
        }

        // 전송전 버튼 비활성화
        $("#saveBtn").prop("disabled",true);

        var varData = ajaxCommon.getSerializedData({
            qnaCtg : qnaCtg,
            qnaSubCtgCd : qnaSubCtgCd,
            qnaSubject : qnaSubject,
            qnaContent : qnaContent,
            mobileNo : cntrMobileNo,
            svcCntrNo : svcCntrNo, // 정회원일때 번호찾는 용도
            smsSendYN : smsSendYN
        });
        ajaxCommon.getItem({
            id:'insertInquiryWriteForm'
            ,cache:false
            ,url:'/cs/insertInquiryWriteFormAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            var inquiryInt =  jsonObj.inquiryInt;
            if(inquiryInt>0){
                MCP.alert("고객님의 문의가 정상적으로 접수되었습니다.\n문의에 대한 답변은 [내 문의 확인하기]에서 확인하실 수 있습니다.",function(){
                    setTimeout(function(){
                        location.reload();
                    }, 500);
                });

            } else {
                MCP.alert("고객님의 문의 접수에 실패 했습니다.",function(){
                    setTimeout(function(){
                        location.reload();
                    }, 500);
                });
            }
        });
    });

    // 숫자만 입력가능1
    $(".numOnly2").on("input paste", function (){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
        // $(this).attr("cntrMobileNo",$(this).val());
    });

    //////////////////////////////////// 버튼 이벤트 ///////////////////////////////////////////
    try{
        // 저장버튼
        $("#qnaCtg,#qnaSubCtgCd,#cntrMobileNo").change(function(){
            btnChk();
        });
        $("#qnaSubject,#qnaContent,#cntrMobileNo").keyup(function(){
            btnChk();
        });
    }catch(e){
        console.log("error");
    }
});

function btn_reg(){
    if($("#chkAgree").is(":checked")){
        $("#applyBtn").prop("disabled",false);
    }
}


function btnChk(){

    var qnaCtg = $("#qnaCtg option:selected").val();
    var qnaSubCtgCd = $("#qnaSubCtgCd option:selected").val();
    var qnaSubject = $("#qnaSubject").val();
    var qnaContent = $("#qnaContent").val();
    var cntrMobileNo = $("#cntrMobileNo").val();
    // var userDivision = $("#userDivision").val();
    // if(userDivision=="00"){
    // 	cntrMobileNo = $("#cntrMobileNo").attr("cntrMobileNo");
    // }
    if(qnaCtg!="" && qnaSubCtgCd!="" && qnaSubject !="" && qnaContent !="" && cntrMobileNo !="" && cntrMobileNo.length>10){
        $("#saveBtn").prop("disabled",false);
    } else {
        $("#saveBtn").prop("disabled",true);
    }
}

//약관동의
function btnRegDtl(param){

    openPage('termsPop','/termsPop.do',param);
}

function targetTermsAgree() {
    $("#chkAgree").prop("checked",true);
    var certifyYn = $("#certifyYn").val();
    if(certifyYn=="Y"){
        $("#applyBtn").prop("disabled",false);
    } else {
        $("#applyBtn").prop("disabled",true);
    }

}

// 세부유형
function getCdTable(varData){

    ajaxCommon.getItem({
        id:'getCodeListAjax'
        ,cache:false
        ,url:'/getCodeListAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }
    ,function(jsonObj){

        var rtnList = jsonObj;
        var ctgHtml = "<option value=''>세부 유형을 선택해 주세요</option>";

        if(rtnList !=null){
            ctgHtml += rtnList.map(dtlObj=>{
                return `<option value="${dtlObj.dtlCd}" cdGroupId="${dtlObj.cdGroupId}" data-mypage-link="${dtlObj.expnsnStrVal1}"   >${dtlObj.dtlCdNm}</option>`;
            }).join('');
        }

        /*
        if(rtnList !=null){
            for(var i=0; i<rtnList.length; i++){
                ctgHtml +="<option value='"+rtnList[i].dtlCd+"' cdGroupId='"+rtnList[i].cdGroupId+"' data-tab-header=#tabB1-panel >"+rtnList[i].dtlCdNm+"</option>";
            }
        }*/
        $("#qnaSubCtgCd").html(ctgHtml);

    });

}

// 문의유형안내 , 문의내용 placeholder
function getFormDtl(varData,no){

    ajaxCommon.getItem({
        id:'getCodeListAjax'
        ,cache:false
        ,url:'/termsPop.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }
    ,function(jsonObj){

        var docContent = ajaxCommon.isNullNvl(jsonObj.docContent,"");
        if(no==1){
            if(docContent !=""){
                $("#guidance").html(docContent).show();
            } else {
                $("#guidance").html("").hide();
            }

        } else {
            if(docContent !=""){
                $("#qnaContent").text(docContent);
                $("#textAreaSbstSize").html(docContent.length + '/1,000자');
            } else {
                $("#qnaContent").text("");
                $("#textAreaSbstSize").html(0 + '/1,000자');
            }
        }
    });
}

function openModal(selector) {
    const el = $(selector)[0];
    const modal = KTM.Dialog.getInstance(el) || new KTM.Dialog(el);
    modal.open();
}
