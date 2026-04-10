$(document).ready(function(){

    $("#tabE").click(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/mypage/couponListOut.do"
        });
        var ncn = $("#ctn option:selected").val();
        ajaxCommon.attachHiddenElement("ncn",ncn);
        ajaxCommon.formSubmit();
    });

});

//조회회선 선택
function select(){
    ajaxCommon.createForm({
        method:"post"
        ,action:"/m/mypage/couponList.do"
    });
    var ncn = $("#ctn option:selected").val();
    ajaxCommon.attachHiddenElement("ncn",ncn);
    ajaxCommon.formSubmit();
}

//상세보기
function showDtl(coupnNo, coupnCtgCd){

    var parameterData = ajaxCommon.getSerializedData({
        coupnNo : coupnNo,
        coupnCtgCd : coupnCtgCd
    });
    openPage('pullPopup', '/m/mypage/couponDtlPop.do', parameterData, '1');
}


function copyCouponNum(cuponNum){

    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    textarea.value = cuponNum;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    MCP.alert("쿠폰번호가 복사되었습니다.");
}