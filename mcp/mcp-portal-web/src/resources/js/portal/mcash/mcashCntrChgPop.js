// 회선 변경
function mcashCntrChg() {
    var isCheck = $("input[name=serviceNum]").is(":checked");
    var chkCertiAgree = $("#chkCertiAgree").is(":checked");

    // 변경할 회선 선택 확인
    if (!isCheck) {
        MCP.alert("변경할 회선을 선택해 주세요.");
        return;
    }

    // 혜택 받기 동의 선택 확인
    if (!chkCertiAgree) {
        MCP.alert("혜택 받기 동의를 선택해 주세요.");
        return;
    }

    var varData = ajaxCommon.getSerializedData({
        svcCntrNo: $('input[name=serviceNum]:checked').val()
    });

    ajaxCommon.getItem({
        id: 'cntrChgMcashUser',
        url: '/mcash/cntrChgMcashUserAjax.do',
        dataType: 'json',
        data: varData,
        cache: false,
        async: false
    }, function(jsonObj) {
        if (jsonObj.rsltCd == "200001") {
            MCP.alert("변경이 완료 되었습니다.", function() {
                KTM.Dialog.closeAll();
                location.reload();
            });
        } else {
            MCP.alert("회선 변경 중 오류가 발생했습니다.<br>다시 시도해 주세요.");
        }
    });
}

function changeBtnDisable() {
    // 1. 변경 회선 선택
    if (!$("input[name=serviceNum]:checked").length) {
        $("#mcashCntrChg").prop("disabled", true);
        return;
    }

    // 2. 혜택 받기 동의
    if (!$("#chkCertiAgree").is(":checked")) {
        $("#mcashCntrChg").prop("disabled", true);
        return;
    }

    $("#mcashCntrChg").prop("disabled", false);
}