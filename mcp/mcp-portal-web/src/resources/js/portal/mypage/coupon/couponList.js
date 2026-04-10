$(document).ready(function(){

});

function select(){
    ajaxCommon.createForm({
        method:"post"
        ,action:"/mypage/couponList.do"
    });
    var ncn = $("#ctn option:selected").val();
    ajaxCommon.attachHiddenElement("ncn",ncn);
    ajaxCommon.formSubmit();
}

function showDtl(coupnNo, coupnCtgCd){

    var parameterData = ajaxCommon.getSerializedData({
        coupnNo : coupnNo,
        coupnCtgCd : coupnCtgCd
    });
    openPage('pullPopup', '/mypage/couponDtlPop.do', parameterData, '1');
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