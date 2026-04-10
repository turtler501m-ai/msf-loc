


$(document).ready(function() {

	$('#introContents').css('width', $(window).width()-30);
    $('#introContents').css('height', $(window).height()-10);

	$("#sevenEleven").click(function(){
		if ("" == $("#introLayer ._detail").html()) {
            showIntro();
        }
		fn_mobile_layerOpen('introLayer');
    });

    var isSimpleApply = function() {

    	var rtnObj = false ;
        ajaxCommon.getItem({
            id:'getFormDesc'
            ,cache:false
            ,async:false
            ,url:'/appform/isSimpleApply.do'
            ,data: ""
            ,dataType:"json"
        }
        ,function(jsonObj){
            rtnObj = jsonObj ;
        });

        return rtnObj ;
    }
    
    
    $("._linkForm").click(function() {
        var cntpntShopId = $(this).attr("cntpntShopId");
        $("#cntpntShopId").val(cntpntShopId);
        if ("" == $("#introLayer ._detail").html()) {
            showIntro();
        }
        fn_mobile_layerOpen('introLayer');
    });

    $("#btn5GSimpleReq").click(function() {
        
        var rtnObj = {} ;
        ajaxCommon.getItem({
            id:'getFormDesc'
            ,cache:false
            ,async:false
            ,url:'/appform/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
        }
        ,function(jsonObj){
            rtnObj = jsonObj ;
        });
        
        if (rtnObj.IsMnpTime) {
            ajaxCommon.createForm({
                method:"post"
                ,action:"/m/request/partnerRequestForm.do"
             });

            var cntpntShopId = $("#cntpntShopId").val();

            ajaxCommon.attachHiddenElement("a",cntpntShopId);
            ajaxCommon.attachHiddenElement("prdtSctnCd",PRDT_SCTN_CD.FIVE_G_FOR_MSP);
            ajaxCommon.formSubmit();
        } else {
            alert("번호이동이 가능한 시간이 아닙니다. \n※셀프개통 가능시간 \n번호이동 - 10:00~19:00");
        }

    });

    $("#btnLteSimpleReq").click(function() {
        if (isSimpleApply()) {
            ajaxCommon.createForm({
                method:"post"
                ,action:"/m/request/partnerRequestForm.do"
             });

            var cntpntShopId = $("#cntpntShopId").val();

            ajaxCommon.attachHiddenElement("a",cntpntShopId);
            ajaxCommon.attachHiddenElement("prdtSctnCd",PRDT_SCTN_CD.LTE_FOR_MSP);
            ajaxCommon.formSubmit();
        } else {
            alert("셀프개통 가능한 시간이 아닙니다. \n※셀프개통 가능시간 \n번호이동 - 10:00~19:00 \n신규가입 - 09:00~21:00");
        }
    });

    //약관정보 패치
    var getFormDesc = function(cdGroupId1,cdGroupId2) {
    	var varData = ajaxCommon.getSerializedData({
    		cdGroupId1:cdGroupId1
    		,cdGroupId2:cdGroupId2
    	});
    	var rtnObj;
    	ajaxCommon.getItem({
    		id:'getFormDesc'
    		,cache:false
    		,async:false
    		,url:'/getFormDescAjax.do'
    		,data: varData
    		,dataType:"json"
    	}
    	,function(jsonObj){
    		rtnObj = jsonObj ;
    	});

    	return rtnObj ;
    };

    var showIntro = function() {
    	  var formDtlDTO = getFormDesc("FormEtcCla","SimpleOpenIntro");
          if (formDtlDTO != null) {
              $("#introLayer ._title").html(formDtlDTO.formNm);
              $("#introLayer ._detail").html(formDtlDTO.docContent);

              if ($("#introLayer ._detail").find('table')){
                  var table_width = $("#introLayer ._detail").find('table').css('width');
                  $("#introLayer ._detail").css('width',table_width);
              }

           }
    };

});
