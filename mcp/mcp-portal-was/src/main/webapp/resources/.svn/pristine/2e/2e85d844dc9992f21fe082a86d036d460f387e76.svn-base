
var pageObj = {
    $listTb:null
    ,$divPaging:null
    ,pageNo:1
    ,searchValue:""
}
var pageNo = parseInt($("#pageNo").val(),10);

$(document).ready(function(){

	pageNo = parseInt($("#pageNo").val(),10);

    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
        if($('#eventBranch').val() == 'E'){
            location.href = "/event/eventBoardList.do";
        }else{
			location.href = "/event/cprtEventBoardList.do";
        }
    });

    $("#tab2").click(function() {
        $("#eventStatus").val('end');
        if($('#eventBranch').val() == 'E'){
			location.href = "/event/eventBoardEndList.do";
        }else{
			location.href = "/event/cprtEventBoardEndList.do";
        }
    });

    $("#tab3").click(function() {
        $("#eventStatus").val('win');
    });

	if(paramValid(pageNo) == false || isNaN(pageNo)){
		pageNo = 1;
	}

    getList(pageNo);

    //페이징 Click
    $(document).on("click",".c-paging__number",function(){
        var pageNo = parseInt($(this).attr("pageNo"),10) ;

		if(paramValid(pageNo) == false || isNaN(pageNo)){
			pageNo = 1;
		}
        getList(pageNo);
    });

    $(document).on("click",".c-button",function(){
        var pageNo = parseInt($(this).attr("pageNo"),10) ;

		if(paramValid(pageNo) == false || isNaN(pageNo)){
			pageNo = 1;
		}
        getList(pageNo);
    });

});

function goWinView(url) {
    ajaxCommon.createForm({
            method:"post"
            ,action: url
    });
    ajaxCommon.formSubmit();
};

var getList = function(pageNo){

	if(paramValid(pageNo) == false || isNaN(pageNo)){
		pageNo = 1;
	}

    $("#pageNo").val(pageNo);
    var recordCount = 10 ;
    var varData = ajaxCommon.getSerializedData({
        pageNo:pageNo
        , eventBranch: $('#eventBranch').val()
        , recordCount:recordCount
    });

    ajaxCommon.getItem({
        id:'getList'
        ,cache:false
        ,url:'/event/winnerListAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async: false
     }
     ,function(jsonObj){
         var listData = jsonObj.winnerList;
         var pageInfo = jsonObj.pageInfoBean;

         if(listData!=undefined && listData.length > 0 ){
             var listHtml = getRowTemplate(listData,pageInfo);
             if (listHtml != "") {
                 $('#listArea').html(listHtml);
             }
             ajaxCommon.setPortalPaging($('#paging'), pageInfo);

             if(pageInfo.totalCount <= 10){
                 $('#paging').hide();
             }
         } else {
             $('#listArea').append("<tr><td colspan='4'><p class='nodata'>조회결과가 없습니다.</p></td></tr>");
         }
         $('#subbody_loading').hide();
     });
    //$('#subbody_loading').hide();
};

var getRowTemplate = function(obj,pageInfo){
    var arr =[];

    for(var i= 0 ; i < obj.length; i++){
        var eventStartDt = obj[i].eventStartDt;
        var eventEndDt = obj[i].eventEndDt;
        var cretDt = new Date(obj[i].cretDt);
        var cnt = pageInfo.totalCount - ((pageInfo.pageNo-1) * pageInfo.recordCount) - i ;

        arr.push("<tr> \n");
        arr.push("  <td>"+cnt+"</td>\n");
        arr.push("  <td>              ");
        arr.push("    <a href='javascript:void(0)' onclick=goView('"+obj[i].ntcartSeq+"');>");
        arr.push("      <strong class='title c-text-ellipsis'>"+ obj[i].pwnrSubject +"</strong>");
        arr.push("    </a>            ");
        arr.push("  </td>             ");
        arr.push("  <td>"+ eventStartDt + " ~ " + eventEndDt +"</td>\n");
        arr.push("  <td>"+ cretDt.format("yyyy.MM.dd") + "</td>\n");
        arr.push("</tr>\n");
    }
    return arr.join('');
}

