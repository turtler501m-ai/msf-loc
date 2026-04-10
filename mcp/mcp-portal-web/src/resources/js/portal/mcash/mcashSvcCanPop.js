// 회선 해지
function mcashSvcCan() {

    KTM.Confirm("M쇼핑할인 서비스를 해지하시겠습니까?", function() {
        this.close();   // confirm 팝업창 닫기

        ajaxCommon.getItem({
            id: 'checkMcashUserAjax',
            url: '/mcash/checkMcashUserAjax.do',
            dataType: 'json',
            cache: false
        }, function(jsonObj) {
            if (jsonObj.mcashUserYn != "Y") {
                MCP.alert("M쇼핑할인 서비스 가입 고객만 가능합니다.");
            } else {
                ajaxCommon.getItem({
                    id: 'svcCanMcashUser',
                    url: '/mcash/svcCanMcashUserAjax.do',
                    dataType: 'json',
                    cache: false,
                    async: false
                }, function(jsonObj) {
                    if (jsonObj.rsltCd == "200001") {
                        MCP.alert("해지가 정상적으로 처리 되었습니다.", function() {
                            KTM.Dialog.closeAll();  // 모달 닫기(포탈)
                            location.reload();
                        });
                    } else {
                        MCP.alert("해지 처리 중 오류가 발생했습니다.<br>다시 시도해 주세요.");
                    }
                });
            }
        });
    });
}