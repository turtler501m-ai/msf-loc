$(document).ready(function() {

    //이벤트 선택 시 상세페이지로
    $(document).on("click","#goDetail",function() {

        let ntcartSeq  = $(this).attr("ntcartSeq");
        let linkTarget = $(this).attr("linkTarget");
        let linkUrlAdr  = $.trim($(this).attr("linkUrlAdr"));

        if (linkUrlAdr != "") {
            if(linkTarget == 'Y'){
                window.open('about:blank').location.href=linkUrlAdr;
                KTM.LoadingSpinner.hide();
            } else {
                location.href = linkUrlAdr;
            }
        } else {
            ajaxCommon.createForm({
                method:"get"
                ,action:"/event/eventDetail.do",
                target: "_blank"
            });
            ajaxCommon.attachHiddenElement("ntcartSeq",ntcartSeq);
            ajaxCommon.attachHiddenElement("sbstCtg","E");
            ajaxCommon.attachHiddenElement("eventBranch","E");
            ajaxCommon.formSubmit();
        }

    });
});