
$(document).ready(function () {

    //자급제 보상 서비스 소개 HTML
    ajaxCommon.getItem({
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=INFO&cdGroupId2=COMB00011",
            async: false
        }
        ,function(data) {
            var inHtml = unescapeHtml(data.docContent);
            $('#main-content').html(inHtml);

            // 로그인 여부로 신청하기 버튼 문구 변경
            if ($("input[name=loginYn]").val() == 'Y') {
                $("#goReqRwd a").html('자급제 보상 서비스 신청하기');
            }

            // 자급제 보상 서비스 신청 안내 HTML
            ajaxCommon.getItem({
                    url: "/termsPop.do",
                    type: "GET",
                    dataType: "json",
                    data: "cdGroupId1=INFO&cdGroupId2=COMB00013",
                    async: false
                }
                ,function(data){
                    inHtml = unescapeHtml(data.docContent);
                    $('#rwdGuide').html(inHtml);

                    // accordion 클릭 이벤트 부여
                    KTM.initialize();
                });
        });
});

var fileInfo = function(boardSeq) {

    var varData = ajaxCommon.getSerializedData({
        boardSeq:boardSeq
    });

    ajaxCommon.getItem({
            id:'selectFileInfo'
            ,cache:false
            ,url:'/content/fileInfo.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            var attSeq = jsonObj.fileInfo.attSeq;
            var boardSeq = jsonObj.fileInfo.boardSeq;

            if(attSeq !=0 || boardSeq!=0) {
                fileDownLoad(attSeq,'1',fileDownCallBack,boardSeq)
            }
        });

}

var fileDownCallBack = function(boardSeq) {
    var varData = ajaxCommon.getSerializedData({
        boardSeq : boardSeq
    });
    ajaxCommon.getItem({
        id:'reqFormDownloadCountUpdate'
        ,cache:false
        ,url:'/cs/reqFormDownloadCountUpdateAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(){});
};
