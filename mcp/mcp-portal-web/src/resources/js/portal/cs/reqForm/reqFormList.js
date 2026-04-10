$(document).ready(function() {

});

function goPaging(pageNo){

	var searchNm = $("#searchNm").val();

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/cs/reqFormList.do"
	});
	var searchNm = $("#searchNm").val().trim();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();

}

function searchBtn() {

	var searchNm = $("#searchNm").val();
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/cs/reqFormList.do"
	});

	ajaxCommon.attachHiddenElement("pageNo",1);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();
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
